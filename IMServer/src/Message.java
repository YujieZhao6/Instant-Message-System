/**
 * Created by courage on 4/4/16.
 */
public class Message {
    private String type;
    private String sender;
    private String accept;
    private String time;
    private String message;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {

        return message;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public String getSender() {
        return sender;
    }

    public String getAccept() {
        return accept;
    }

    public String getTime() {
        return time;
    }

    public String toString(){
        return type+"@"+sender+"@"+accept+"@"+message+"@"+time;
    }
}
