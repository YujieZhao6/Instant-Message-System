
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

/**
 * Created by courage on 4/3/16.
 */
public class ClientThread extends Thread{
    private Socket acceptSocket;
    private String serverWorkSpace = "/Users/courage/Desktop/IMserver";

    public ClientThread(Socket acceptSocket){
        this.acceptSocket = acceptSocket;
    }

    public void run(){
        try {
            String userName = ""; //用户名
            //命令接发流
            BufferedReader request = new BufferedReader(new InputStreamReader(acceptSocket.getInputStream()));
            PrintWriter response = new PrintWriter(new OutputStreamWriter(acceptSocket.getOutputStream()));
            String getRequest;
            while(true){
                getRequest = request.readLine();
                String []command = getRequest.split("@");//拆分请求
                switch (command[0]){
                    case "100"://注册
                        //将用户消息写入服务器用户列表文件
                        FileWriter fileWriter = new FileWriter(serverWorkSpace+"/user.txt",true);
                        File userFile = new File(serverWorkSpace+"/"+command[1]+".txt");
                        //为用户创建用户专有的文件去储存自己的好友
                        if(!userFile.exists()){
                            userFile.createNewFile();
                        }
                        fileWriter.write(command[1]+"@"+command[2]+"\n");
                        fileWriter.flush();
                        fileWriter.close();
                        response.println("1");
                        response.flush();
                        break;
                    case "101"://登陆
                        BufferedReader fileReader = new BufferedReader(new FileReader(serverWorkSpace+"/user.txt"));
                        String userInformation;
                        String loginResult = "0";
                        userName = command[1];
                        while ((userInformation = fileReader.readLine())!= null){
                            if(userInformation.split("@")[0].equals(userName)&&userInformation.split("@")[1].equals(command[2]))
                                loginResult = "1";
                        }
                        if(loginResult.equals("1")){
                            Random a = new Random();
                            //创建数据传输端口
                            ServerSocket dataSocketListen;
                            dataSocketListen = openPort();

                            response.println(loginResult +"@"+dataSocketListen.getLocalPort());
                            response.flush();
                            Socket dataSocket = dataSocketListen.accept();
                            //新开传输数据线程
                            SentDataThread sentDataThread = new SentDataThread(dataSocket,userName);
                            sentDataThread.start();
                        }else {
                            response.println(loginResult +"@"+0);
                            response.flush();
                        }
                        //
                        break;
                    case "102"://获取好友消息列表
                        BufferedReader findFriedsList = new BufferedReader(new FileReader(serverWorkSpace+"/"+command[1]+".txt"));
                        String getFriend;
                        StringBuilder sendFriendList = new StringBuilder();
                        while ((getFriend = findFriedsList.readLine())!=null){
                            sendFriendList.append(getFriend+",");
                        }
                        findFriedsList.close();
                        response.println(sendFriendList.toString());
                        response.flush();
                        break;
                case "103"://消息传输
                    String []messageInfo = getRequest.split("@");
                    String []friendsOfGroup = messageInfo[2].split(",");
                    for(int i=0;i<friendsOfGroup.length;i++){
                        Message addMessage = new Message();
                        String groups = messageInfo[1];//在群组中发送消息的人为第一个
                        for(int j=0;j<friendsOfGroup.length;j++){
                            if(j!=i){
                                groups = groups + ","+friendsOfGroup[j];
                            }
                        }
                        addMessage.setSender(groups);
                        addMessage.setAccept(friendsOfGroup[i]);
                        addMessage.setType("103");
                        addMessage.setMessage(messageInfo[3]);
                        addMessage.setTime(messageInfo[4]);
                        MessgeDAO.messageDAO.add(addMessage);


                        System.out.println(addMessage.toString());
                    }
                    break;
                    case "104"://加好友
                        BufferedReader findFrieds = new BufferedReader(new FileReader(serverWorkSpace+"/user.txt"));
                        String findFriendsResult = "0";
                        //command 正好是请求的好友名
                        String friendName;
                        while ((friendName= findFrieds.readLine())!= null){
                            if(friendName.split("@")[0].equals(command[2]))
                                findFriendsResult = "1";
                        }
                        findFrieds.close();
                        if(findFriendsResult.equals("1")){
                            //好友存在的话 更新自己和好友的好友列表
                            FileWriter writerFriends = new FileWriter(serverWorkSpace+"/"+userName+".txt",true);
                            writerFriends.write(command[2]+"\n");
                            writerFriends.close();
                            FileWriter writertoFriendsList = new FileWriter(serverWorkSpace+"/"+command[2]+".txt",true);
                            writertoFriendsList.write(command[1]+"\n");
                            writertoFriendsList.close();
                            response.println(findFriendsResult);
                            response.flush();
                        }else{
                            response.println(findFriendsResult);
                            response.flush();
                        }
                        break;
                    case "105":// 获取所传传文件
                            String upLoadFileName = command[1];
                            ServerSocket fileSocketListen = openPort();
                            //收到请求后将数据端口返回给用户
                            response.println("1@"+fileSocketListen.getLocalPort());
                            response.flush();
                            Socket getFileSocket = fileSocketListen.accept();
                            fileSocketListen.close();//关闭监听端口
                            OutputStream fileOutputStream = new FileOutputStream(serverWorkSpace+"/"+upLoadFileName);
                            InputStream fileInputStream = getFileSocket.getInputStream();
                            byte[] byte1 = new byte[1024];
                            int read1;
                            while((read1 = fileInputStream.read(byte1))!= -1){
                                fileOutputStream.write(byte1,0,read1);
                            }
                            fileInputStream.close();
                            fileOutputStream.close();
                            getFileSocket.close();//传输完毕关闭端口
                        break;
                    case "106"://给用户下载文件
                        String downloadFileName = command[1];
                        ServerSocket downloadSocketListen = openPort();
                        response.println("1@"+downloadSocketListen.getLocalPort());
                        response.flush();
                        Socket downloadSocket = downloadSocketListen.accept();
                        downloadSocketListen.close();//关闭监听端口
                        OutputStream downloadFileOutPutStream = downloadSocket.getOutputStream();
                        InputStream downloadFileInPutStream = new FileInputStream(serverWorkSpace+"/"+downloadFileName.split(":")[1]);
                        byte[] bytes = new byte[1024];
                        int read;
                        while((read = downloadFileInPutStream.read(bytes))!= -1){
                            downloadFileOutPutStream.write(bytes,0,read);
                        }
                        downloadFileInPutStream.close();
                        downloadFileOutPutStream.close();
                        downloadSocket.close();//传输完毕关闭端口
                        break;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static ServerSocket openPort(){
        int port_high;
        int port_low;
        Random a = new Random();
        //创建数据传输端口
        ServerSocket dataSocketListen;
        while(true){
            port_high = 1+ a.nextInt(20);
            port_low = 100 + a.nextInt(1000);
            try {
                dataSocketListen = new ServerSocket(port_high*256 + port_low);
                break;
            }catch (IOException e){
                continue;
            }
        }
        return dataSocketListen;
    }

}
