import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by courage on 4/3/16.
 */
public class ListenPort {
    public static void main(String args[]){
        try{
            ServerSocket listenPort = new ServerSocket(5211);
            while(true){
                //等待用户连接,有人连接就为其新开一个端口
                Socket acceptSocket = listenPort.accept();

                ClientThread clientThread = new ClientThread(acceptSocket);
                clientThread.start();
            }


        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
