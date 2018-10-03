import java.util.ArrayList;

/**
 * Created by courage on 4/4/16.
 */
public class MessgeDAO {
    public static ArrayList<Message> messageDAO = new ArrayList<>();

    public static ArrayList<Message> getMessage(String userName){
        ArrayList<Message> sendMessage = new ArrayList<>();
        for(int i=0;i< messageDAO.size();i++){
            if(messageDAO.get(i).getAccept().equals(userName)){
                sendMessage.add(messageDAO.get(i));
                messageDAO.remove(i);
                i--;
            }
        }
        return sendMessage;
    }

}
