import sun.jvm.hotspot.oops.Array;
import sun.jvm.hotspot.ui.SysPropsPanel;
import sun.jvm.hotspot.ui.action.MemoryAction;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by courage on 4/3/16.
 */
public class Client {
    public static void main(String args[]){
        //载入表情
        EmotionsInfo.loadEmotion();
        //设置服务器和端口
        String serverIP = "127.0.0.1";
        int serverPort = 5211;
        String workSpace = "/Users/courage/Desktop/IMclient";
        try{
            Socket commandSocket = new Socket(serverIP,serverPort);
            //命令流的建立
            BufferedReader response = new BufferedReader(new InputStreamReader(commandSocket.getInputStream()));
            PrintWriter request = new PrintWriter(new OutputStreamWriter(commandSocket.getOutputStream()));
            //显示登陆界面
            LoginInterface loginInterface = new LoginInterface();
            loginInterface.setVisible(true);
            //注册按钮操作
            JButton signUpButton = loginInterface.getSingupButton();
            signUpButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    loginInterface.setVisible(false);
                    SignUpInterface signUpInterface = new SignUpInterface();
                    signUpInterface.setVisible(true);
                    JButton confirmButton = signUpInterface.getSignUpButton();
                    confirmButton.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            try {
                                String userName = signUpInterface.getUserName();
                                String passWord = signUpInterface.getPassword();
                                request.println("100@"+userName+"@"+passWord);
                                request.flush();
                                signUpInterface.setVisible(false);
                                loginInterface.setVisible(true);
                                String responseCommand = response.readLine();

                            }catch (IOException e1){
                                e1.printStackTrace();
                            }
                        }
                    });
                }
            });

            //登陆按钮操作
            JButton loginButton = loginInterface.getLohinButton();
            loginButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        String userName = loginInterface.getUserName();
                        String password = loginInterface.getPassWord();
                        request.println("101@"+userName+"@"+password);
                        request.flush();
                        String loginConfirm ;//登陆成功信息
                        loginConfirm = response.readLine();
                        Socket datasocket;//消息传输端口
                        if(loginConfirm.split("@")[0].equals("1")){
                            //创建一个用户自己的目录 存放聊天记录消息
                            File userFile = new File(workSpace+"/"+userName);
                            if(!userFile.exists()){
                                userFile.mkdir();
                            }
                            //创建数据流
                            datasocket = new Socket(serverIP,Integer.parseInt(loginConfirm.split("@")[1]));
                            ChatInterface chatInterface = new ChatInterface();
                            AcceptDataThread acceptDataThread = new AcceptDataThread(datasocket,chatInterface,userName,request,response,serverIP);
                            acceptDataThread.start();
                            loginInterface.setVisible(false);
                            //创建聊天窗口
                            chatInterface.setVisible(true);
                            chatInterface.getUserNameLabel().setText(userName);
                            //聊天按钮
                            JButton chatButton = chatInterface.getChatButton();
                            chatButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    //
                                    chatInterface.getCreateGroupButton().setVisible(false);
                                    //
                                    chatInterface.getStatus1Label().setText("Talks");
                                    File userChatFileDirectory = new File(workSpace+"/"+userName);
                                    String []chatfilesName = userChatFileDirectory.list();
                                    int num =0;//文件头不为空的文件数目
                                    for(int i=0;i<chatfilesName.length;i++){
                                        chatfilesName[i] = chatfilesName[i].split("\\.")[0];//除去.txt好看
                                        if(chatfilesName[i].equals("")){
                                            num++;
                                        }
                                    }
                                    String []showNameList = Arrays.copyOfRange(chatfilesName,num,chatfilesName.length);
                                    JList showList = chatInterface.getShowList();
                                    showList.setListData(showNameList);
                                    showList.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            try{
                                                chatInterface.getGetMessagePane().setText("");
                                                File chatFile = new File(workSpace+"/"+userName+"/"+showList.getSelectedValue().toString()+".txt");
                                                BufferedReader readChatRecord = new BufferedReader(new FileReader(chatFile));
                                                String getRecordLine;
                                                JTextPane textPane = chatInterface.getGetMessagePane();
                                                StyledDocument docs = textPane.getStyledDocument();//获得文本对象
                                                //不同身份的显示消息格式不同
                                                SimpleAttributeSet keyWord1 = new SimpleAttributeSet();
                                                SimpleAttributeSet keyWord2 = new SimpleAttributeSet();
                                                StyleConstants.setBackground(keyWord1, new Color(212,237,245));
                                                StyleConstants.setBackground(keyWord2, new Color(253,198,217));
                                                while ((getRecordLine = readChatRecord.readLine())!=null){
                                                    String []getInfo = getRecordLine.split("@");
                                                    String showMessage;
                                                    chatInterface.getStatus2Label().setText("Talk with "+showList.getSelectedValue().toString());
                                                    if(getInfo[1].equals(userName)){
                                                        showMessage = "To " + getInfo[2] + "\n" +getInfo[4] + "\n" +getInfo[3]+ "\n\n";
                                                        docs.insertString(docs.getLength(),showMessage,keyWord1);
                                                    }else {
                                                        showMessage = "From " + getInfo[1] + "\n" + getInfo[4] + "\n" + getInfo[3]+ "\n\n";
                                                        docs.insertString(docs.getLength(),showMessage,keyWord2);
                                                    }
                                                }
                                            }catch (FileNotFoundException f){
                                                f.printStackTrace();
                                            }catch (IOException io){
                                                io.printStackTrace();
                                            }catch (BadLocationException bb){
                                                bb.printStackTrace();
                                            }
                                        }
                                    });

                                }
                            });
                            //好友列表
                            JButton friendsButton = chatInterface.getFriendsButton();
                            friendsButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    //
                                    chatInterface.getCreateGroupButton().setVisible(false);
                                    //
                                    chatInterface.getStatus1Label().setText("Friends");
                                    request.println("102@"+userName);
                                    request.flush();
                                    try{
                                        String messageContainFriens = response.readLine();
                                        String []frinedsList = messageContainFriens.split(",");
                                        JList showList = chatInterface.getShowList();
                                        showList.setListData(frinedsList);
                                        showList.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseClicked(MouseEvent e) {
                                                //刷新聊天窗口
                                                chatInterface.getStatus2Label().setText(showList.getSelectedValue().toString());
                                                chatInterface.getGetMessagePane().setText("");

                                            }
                                        });
                                    }catch (IOException mcf){
                                        mcf.printStackTrace();
                                    }
                                }
                            });
                            //添加好友
                            JButton addFriendButton = chatInterface.getAddFriendButton();
                            addFriendButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    AddFriendInterface addFriendInterface = new AddFriendInterface();
                                    addFriendInterface.setVisible(true);
                                    JButton sendNewFriendName = addFriendInterface.getAddNewFriendButton();
                                    sendNewFriendName.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            String addNewFriends = addFriendInterface.getUserNameTextField().getText();
                                            request.println("104@"+userName+"@"+addNewFriends);
                                            request.flush();
                                            try{
                                                String findFriendResult = response.readLine();
                                                if(findFriendResult.equals("1")){
                                                    new SuccessInterface().setVisible(true);
                                                }else {
                                                    new FailedInterface().setVisible(true);
                                                }

                                            }catch (IOException ef){
                                                ef.printStackTrace();
                                            }

                                        }
                                    });

                                }
                            });
                            //添加群聊
                            JButton groupButton = chatInterface.getGroupButton();
                            groupButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    JButton createGroupButton = chatInterface.getCreateGroupButton();
                                    createGroupButton.setVisible(true);
                                    createGroupButton.addMouseListener(new MouseAdapter() {
                                        @Override
                                        public void mouseClicked(MouseEvent e) {
                                            //获取好友列表
                                            request.println("102@"+userName);
                                            request.flush();
                                            try{
                                                String messageContainFriends = response.readLine();
                                                String []frinedsList = messageContainFriends.split(",");
                                                CreateGroupInterface createGroupInterface = new CreateGroupInterface();
                                                createGroupInterface.setVisible(true);
                                                JList jfriendsList = createGroupInterface.getFriendsList();
                                                jfriendsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                                                jfriendsList.setListData(frinedsList);

                                                JButton okButton = createGroupInterface.getOkButton();
                                                okButton.addMouseListener(new MouseAdapter() {
                                                    @Override
                                                    public void mouseClicked(MouseEvent e) {
                                                        try{
                                                            StringBuilder groupFileName = new StringBuilder();
                                                            int []groupFriendsIndex = jfriendsList.getSelectedIndices();
                                                            for(int i=0;i<groupFriendsIndex.length;i++){
                                                                groupFileName.append(frinedsList[i]+",");
                                                            }
                                                            File groupFile = new File(workSpace+"/"+userName+"/"+groupFileName.toString()+".txt");
                                                            if(!groupFile.exists()){
                                                                groupFile.createNewFile();
                                                            }
                                                            createGroupInterface.setVisible(false);

                                                        }catch (IOException f){
                                                            f.printStackTrace();
                                                        }
                                                    }
                                                });
                                            }catch (IOException mcf){
                                                mcf.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            });
                            //发送消息
                            JButton sentButton = chatInterface.getSentButton();
                            sentButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    JTextPane sentMessageTextPane = chatInterface.getSentMessagePane();
                                    String sentMessage = sentMessageTextPane.getText();//聊天内容
                                    System.out.println(sentMessage);
                                    sentMessageTextPane.setText("");
                                    String date = getTime();//时间
                                    JList friendsList = chatInterface.getShowList();
                                    String sentTo = friendsList.getSelectedValue().toString();
                                    String messageToServer = "103@"+userName+"@"+sentTo+"@"+sentMessage+"@"+date;
                                    request.println(messageToServer);
                                    request.flush();
                                    try{
                                        String showMessage = "To " + sentTo + "\n" +date + "\n" +sentMessage+ "\n\n";
                                        //插入内容
                                        JTextPane textPane = chatInterface.getGetMessagePane();
                                        StyledDocument docs = textPane.getStyledDocument();//获得文本对象
                                        SimpleAttributeSet keyWord = new SimpleAttributeSet();
                                        StyleConstants.setBackground(keyWord, new Color(212,237,245));
                                        docs.insertString(docs.getLength(),showMessage,keyWord);
                                        //本地纪录
                                        File recordFile = new File(workSpace+"/"+userName+"/"+sentTo+".txt");
                                        if(!recordFile.exists()){
                                            recordFile.createNewFile();
                                        }
                                        FileWriter record = new FileWriter(recordFile,true);
                                        record.write("103@"+userName+"@"+sentTo+"@"+sentMessage+"@"+date+"\n");
                                        record.close();
                                        //
                                    }catch (BadLocationException b){
                                        b.printStackTrace();
                                    }catch (IOException o){
                                        o.printStackTrace();
                                    }

                                }
                            });

                            //传输文件
                            JButton transFileButton = chatInterface.getFileButton();
                            transFileButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    try{
                                        JFileChooser chooser = new JFileChooser();
                                        chooser.showOpenDialog(null);
                                        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                                        File transFile = chooser.getSelectedFile();
                                        if(transFile != null){
                                            //先上传到服务器
                                            request.println("105@"+transFile.getName());
                                            request.flush();
                                            String listenPortInfo = response.readLine();
                                            InputStream fileInputStream = new FileInputStream(transFile);
                                            Socket fileUploadSocket = new Socket(serverIP,Integer.parseInt(listenPortInfo.split("@")[1]));
                                            OutputStream creatUploadFile = fileUploadSocket.getOutputStream();
                                            byte[] bytes = new byte[1024];
                                            int read;
                                            while((read = fileInputStream.read(bytes))!= -1){
                                                creatUploadFile.write(bytes,0,read);
                                            }
                                            creatUploadFile.close();
                                            fileInputStream.close();

                                            //然后给在选中的人发消息,让接收者去请求获得文件
                                            request.println("103@"+userName+"@"+chatInterface.getShowList().getSelectedValue().toString()+"@"+"DownLoad:"+transFile.getName()+"@"+getTime());
                                            request.flush();
                                        }

                                    }catch (IOException o){
                                        o.printStackTrace();
                                    }
                                }
                            });

                            //添加表情
                            JButton emotionButton = chatInterface.getEmotionButton();
                            emotionButton.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    new EmotionInterface(chatInterface.getSentMessagePane()).setVisible(true);
                                }
                            });


                            //为发消息和接受消息的文本框添加监听 改变表情
                            JTextPane sentMessagePane = chatInterface.getSentMessagePane();
                            sentMessagePane.getDocument().addDocumentListener(new DocumentListener() {
                                @Override
                                public void insertUpdate(final DocumentEvent de) {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        public void run() {
                                            try {
                                                StyledDocument doc = (StyledDocument) de.getDocument();
                                                int start = Utilities.getRowStart(sentMessagePane, Math.max(0, de.getOffset()-1));
                                                int end = Utilities.getWordStart(sentMessagePane, de.getOffset() + de.getLength());

                                                String text = doc.getText(start, end - start);

                                                for (String emotion : EmotionsInfo.emotions) {//for each emoticon

                                                    int i = text.indexOf(emotion);
                                                    while (i >= 0) {
                                                        final SimpleAttributeSet attrs = new SimpleAttributeSet(doc.getCharacterElement(start + i).getAttributes());
                                                        if (StyleConstants.getIcon(attrs) == null) {

                                                            StyleConstants.setIcon(attrs, (new ImageIcon(this.getClass().getResource("/emotion/"+emotion.split("\\*")[1]+".gif"))));

                                                            doc.remove(start + i, emotion.length());
                                                            doc.insertString(start + i, emotion, attrs);
                                                        }
                                                        i = text.indexOf(emotion, i + emotion.length());
                                                    }
                                                }
                                            } catch (BadLocationException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    });

                                }

                                @Override
                                public void removeUpdate(DocumentEvent e) {

                                }

                                @Override
                                public void changedUpdate(DocumentEvent e) {

                                }
                            });

                            JTextPane getMessagePane = chatInterface.getGetMessagePane();
                            getMessagePane.getDocument().addDocumentListener(new DocumentListener() {
                                @Override
                                public void insertUpdate(final DocumentEvent de) {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        public void run() {
                                            try {
                                                StyledDocument doc = (StyledDocument) de.getDocument();
                                                int start = Utilities.getRowStart(getMessagePane, Math.max(0, de.getOffset() - 1));
                                                int end = Utilities.getWordStart(getMessagePane, de.getOffset() + de.getLength());

                                                String text = doc.getText(start, end - start);

                                                for (String emotion : EmotionsInfo.emotions) {//for each emoticon

                                                    int i = text.indexOf(emotion);
                                                    while (i >= 0) {
                                                        final SimpleAttributeSet attrs = new SimpleAttributeSet(doc.getCharacterElement(start + i).getAttributes());
                                                        if (StyleConstants.getIcon(attrs) == null) {

                                                            StyleConstants.setIcon(attrs, (new ImageIcon(this.getClass().getResource("/emotion/"+emotion.split("\\*")[1]+".gif"))));

                                                            doc.remove(start + i, emotion.length());
                                                            doc.insertString(start + i, emotion, attrs);
                                                        }
                                                        i = text.indexOf(emotion, i + emotion.length());
                                                    }
                                                }
                                            } catch (BadLocationException ex) {
                                                ex.printStackTrace();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void removeUpdate(DocumentEvent e) {

                                }

                                @Override
                                public void changedUpdate(DocumentEvent e) {

                                }
                            });


                        }else {
                            new FailedInterface().setVisible(true);
                        }
                    }catch (IOException e2){
                    }
                }
            });
        }catch (IOException e){
        }
    }


    public static String getTime(){
        Date date  = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = simpleDateFormat.format(date);
        return  time;
    }
}
