/**
 * Created by courage on 4/7/16.
 */
public class EmotionsInfo {
    public static String[] emotions = new String[96];
    public static void loadEmotion(){
        for(int i=0;i<emotions.length;i++){
            emotions[i] = "*" + i +"*";
        }
    }
}
