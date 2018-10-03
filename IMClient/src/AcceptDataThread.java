import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

/**
 * Created by courage on 4/5/16.
 */
public class AcceptDataThread extends Thread {
    private Socket dataSocket;
    private ChatInterface chatInterface;
    private String userName;
    private PrintWriter request;
    private BufferedReader response;
    private String serverIp;
    String workSpace = "/Users/courage/Desktop/IMclient";
    public AcceptDataThread(Socket dataSocket, ChatInterface chatInterface,String userName,PrintWriter request,BufferedReader response,String serverIP){
        this.dataSocket = dataSocket;
        this.chatInterface = chatInterface;
        this.userName = userName;
        this.request = request;
        this.response = response;
        this.serverIp = serverIP;
    }
    public void run(){
        try {
            BufferedReader readMessage = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
            PrintWriter sentResult = new PrintWriter(new OutputStreamWriter(dataSocket.getOutputStream()));//为了响应
            while(true) {
                String message = readMessage.readLine();
                String[] info = message.split("@");
                //在本地保存聊天纪录
                File dire = new File(workSpace+"/"+userName);
                File[] recordFiles = dire.listFiles();
                File recordFile = null;
                for(int i=0;i<recordFiles.length;i++){
                    if(comTowS(recordFiles[i].getName().split("\\.")[0],info[1])){
                        recordFile = recordFiles[i];
                    }
                }
                if(recordFile==null){
                    recordFile = new File(workSpace+"/"+userName+"/"+info[1]+".txt");
                    recordFile.createNewFile();
                }
                FileWriter record = new FileWriter(recordFile,true);
                record.write(message+"\n");
                record.close();
                String from = info[1];
                String chat = info[3];
                String time = info[4];
                //判断是否需要下载文件
                if(chat.startsWith("DownLoad:")){
                    request.println("106@"+chat);
                    request.flush();
                    String potrInfo = response.readLine();
                    Socket downloadSocket = new Socket(serverIp,Integer.parseInt(potrInfo.split("@")[1]));
                    OutputStream fileOutputStream = new FileOutputStream(workSpace+"/"+userName+"/"+chat.split(":")[1]);
                    InputStream fileInputStream = downloadSocket.getInputStream();
                    byte[] byte1 = new byte[1024];
                    int read1;
                    while((read1 = fileInputStream.read(byte1))!= -1){
                        fileOutputStream.write(byte1,0,read1);
                    }
                    fileInputStream.close();
                    fileOutputStream.close();
                    downloadSocket.close();
                }
                sentResult.println("finish");
                sentResult.flush();
                //显示聊天
                String showMessage = "From " + from.split(",")[0] + "\n" + time + "\n" + chat+ "\n\n";
                JTextPane textPane = chatInterface.getGetMessagePane();
                StyledDocument docs = textPane.getStyledDocument();//获得文本对象
                SimpleAttributeSet keyWord = new SimpleAttributeSet();
                StyleConstants.setBackground(keyWord, new Color(253,198,217));
                docs.insertString(docs.getLength(),showMessage,keyWord);
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (BadLocationException b){
            b.printStackTrace();
        }
    }

    //比较连个字符串所含名字是不是一样
    //防止在群聊的时候不同的人发来消息分开储存
    public static boolean comTowS(String a,String b){
        String []a1 = a.split(",");
        String []b1 = b.split(",");
        if(a1.length != b1.length){
            return false;
        }else {
            for(int i=0;i<a1.length;i++){
                if(a1[i].equals("")){
                    return false;
                }
                if(!b.contains(a1[i])){
                    return false;
                }
            }
        }
        return true;
    }

}
