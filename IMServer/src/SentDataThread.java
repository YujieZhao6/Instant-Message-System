import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by courage on 4/5/16.
 */
public class SentDataThread extends Thread {
    private Socket dataSocket;
    private String name;
    public SentDataThread(Socket dataSocket,String name){
        this.dataSocket = dataSocket;
        this.name = name;
    }

    public void run(){
        try {
            PrintWriter sendMessage = new PrintWriter(new OutputStreamWriter(dataSocket.getOutputStream()));
            BufferedReader getAcceptResult = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
            while (true){
                try {
                    //去消息队列中找到自己要发送的消息
                    ArrayList<Message> sentMessage = MessgeDAO.getMessage(name);
                    for(int i=0;i<sentMessage.size();i++){
                        sendMessage.println(sentMessage.get(i).toString());
                        sendMessage.flush();
                        getAcceptResult.readLine();
                        System.out.println(sentMessage.get(i).toString());
                    }
                    //找完后线程休眠1秒再下一次寻找
                    this.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
