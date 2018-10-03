import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

import java.awt.Font;

public class ChatInterface extends JFrame {

    private JPanel contentPane;
    private JButton chatButton;
    private JButton friendsButton;
    private JButton groupButton;
    private JButton addFriendButton;
    private JButton emotionButton;
    private JButton fileButton;
    private JButton sentButton;
    private JButton cancelButton;
    private JButton createGroupButton;
    private JLabel status1Label;
    private JLabel status2Label;

    private JLabel userNameLabel;
    private JTextPane sentMessageArea;
    private JTextPane getMessageArea;
    private JList showList;

    private int xx, yy;
    private boolean isDraging = false;


    public ChatInterface() {
        this.getRootPane().putClientProperty("Window.alpha", new Float(0.85f));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setBounds(300, 150,650, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        //contentPane.setBackground(new Color(0,0,0,22));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        JPanel panel = new JPanel();
        Color qqStausBackground = new Color(43, 188, 230);
        panel.setBackground(qqStausBackground);
        panel.setBounds(0, 0, 86, 500);
        contentPane.add(panel);
        panel.setLayout(null);
        panel.setOpaque(true);

        chatButton = new JButton("");
        chatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        ImageIcon chatIcon = new ImageIcon("chat.png");
        Image imgChat = chatIcon.getImage();
        Image newimgChat = imgChat.getScaledInstance(36,36,java.awt.Image.SCALE_SMOOTH);
        chatIcon = new ImageIcon(newimgChat);
        chatButton.setIcon(chatIcon);
        chatButton.setBounds(20, 145, 40, 38);
        panel.add(chatButton);

        friendsButton = new JButton("");
        ImageIcon friendsIcon = new ImageIcon("friends.png");
        Image imgfriends = friendsIcon.getImage();
        Image newimgfriends = imgfriends.getScaledInstance(36,36,java.awt.Image.SCALE_SMOOTH);
        friendsIcon = new ImageIcon(newimgfriends);
        friendsButton.setIcon(friendsIcon);
        friendsButton.setBounds(20, 220, 40, 38);
        panel.add(friendsButton);



        groupButton = new JButton("");
        ImageIcon groupIcon = new ImageIcon("group.png");
        Image imgGroup = groupIcon.getImage();
        Image newimgGroup = imgGroup.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
        groupIcon = new ImageIcon(newimgGroup);
        groupButton.setIcon(groupIcon);
        groupButton.setBounds(20, 296, 40, 38);
        panel.add(groupButton);

        addFriendButton = new JButton("");
        ImageIcon addFriendIcon = new ImageIcon("addFriend.png");
        Image imgaddFriend = addFriendIcon.getImage();
        Image newimgaddFriend = imgaddFriend.getScaledInstance(40,40,java.awt.Image.SCALE_SMOOTH);
        addFriendIcon = new ImageIcon(newimgaddFriend);
        addFriendButton.setIcon(addFriendIcon);
        addFriendButton.setBounds(21, 426, 40, 38);
        panel.add(addFriendButton);

        JButton closeButton = new JButton("");
        ImageIcon closeIcon = new ImageIcon("close.png");
        Image imgClose = closeIcon.getImage();
        Image newimgClose = imgClose.getScaledInstance(16,16,java.awt.Image.SCALE_SMOOTH);
        closeIcon = new ImageIcon(newimgClose);
        closeButton.setIcon(closeIcon);
        closeButton.setBounds(6, 6, 15, 14);
        panel.add(closeButton);
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
        JButton smallButton = new JButton("");
        ImageIcon smallIcon = new ImageIcon("small.png");
        Image imgsmall = smallIcon.getImage();
        Image newimgsmall = imgsmall.getScaledInstance(16,16,java.awt.Image.SCALE_SMOOTH);
        smallIcon = new ImageIcon(newimgsmall);
        smallButton.setIcon(smallIcon);
        smallButton.setBounds(24, 5, 15, 14);
        panel.add(smallButton);
        smallButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }
        });
        showList = new JList();
        showList.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        showList.setForeground(Color.BLACK);
        showList.setBackground(new Color(230, 230, 230));
        showList.setFixedCellHeight(50);
        showList.setBounds(86, 54, 138, 446);
        DefaultListCellRenderer renderer =  (DefaultListCellRenderer)showList.getCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        contentPane.add(showList);

        JPanel panel_1 = new JPanel();
        panel_1.setBounds(225, 366, 421, 133);
        panel_1.setBackground(new Color(230, 230, 230));
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        emotionButton = new JButton("");
        emotionButton.setBounds(16, 3, 29, 26);
        panel_1.add(emotionButton);
        ImageIcon emotionIcon = new ImageIcon("emotion.png");
        Image imgemotion = emotionIcon.getImage();
        Image newimgemotion = imgemotion.getScaledInstance(25,25,java.awt.Image.SCALE_SMOOTH);
        emotionIcon = new ImageIcon(newimgemotion);
        emotionButton.setIcon(emotionIcon);

        fileButton = new JButton("");
        fileButton.setBounds(57, 3, 29, 26);
        panel_1.add(fileButton);
        ImageIcon fileIcon = new ImageIcon("file.png");
        Image imgfile = fileIcon.getImage();
        Image newimgfile = imgfile.getScaledInstance(25,25,java.awt.Image.SCALE_SMOOTH);
        fileIcon = new ImageIcon(newimgfile);
        fileButton.setIcon(fileIcon);

        sentMessageArea = new JTextPane();
        sentMessageArea.setBounds(2, 31, 350, 101);
        panel_1.add(sentMessageArea);


        sentButton = new JButton("Sent");
        sentButton.setBounds(354, 46, 63, 29);
        panel_1.add(sentButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
        cancelButton.setBounds(354, 76, 63, 29);
        panel_1.add(cancelButton);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(225, 52, 420, 311);
        contentPane.add(jScrollPane);

        getMessageArea = new JTextPane();
        getMessageArea.setEditable(false);
        getMessageArea.setBounds(225, 54, 420, 309);
        jScrollPane.setViewportView(getMessageArea);

        JPanel panel_2 = new JPanel();
        panel_2.setBounds(86, 0, 560, 53);
        contentPane.add(panel_2);
        panel_2.setBackground(new Color(230, 230, 230));
        panel_2.setLayout(null);

        status1Label = new JLabel("",JLabel.CENTER);
        status1Label.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        status1Label.setBounds(20, 10, 100, 30);
        panel_2.add(status1Label);

        status2Label = new JLabel("",JLabel.CENTER);
        status2Label.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        status2Label.setBounds(157, 10, 341, 30);
        panel_2.add(status2Label);

        createGroupButton = new JButton("");
        ImageIcon createGroupIcon = new ImageIcon("createGroup.png");
        Image imgcreateGroup = createGroupIcon.getImage();
        Image newimgcreateGroup = imgcreateGroup.getScaledInstance(23,23,java.awt.Image.SCALE_SMOOTH);
        createGroupIcon = new ImageIcon(newimgcreateGroup);
        createGroupButton.setIcon(createGroupIcon);
        createGroupButton.setBounds(517, 19, 23, 21);
        createGroupButton.setVisible(false);
        panel_2.add(createGroupButton);

        JLabel welcomeLabel = new JLabel("Welcome",JLabel.CENTER);
        welcomeLabel.setBounds(6, 56, 74, 32);
        panel.add(welcomeLabel);

        userNameLabel = new JLabel("George",JLabel.CENTER);
        userNameLabel.setBounds(6, 80, 74, 32);
        panel.add(userNameLabel);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                isDraging = true;
                xx = e.getX();
                yy = e.getY();
            }
            public void mouseReleased(MouseEvent e){
                isDraging = false;
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDraging) {
                    int left = getLocation().x;
                    int top = getLocation().y;
                    setLocation(left + e.getX() - xx, top + e.getY() - yy);
                }
            }
        });
    }

    public JButton getChatButton(){
        return chatButton;
    }

    public  JButton getFriendsButton(){
        return friendsButton;
    }

    public JButton getGroupButton(){
        return  groupButton;
    }

    public JButton getAddFriendButton(){
        return  addFriendButton;
    }

    public JButton getEmotionButton() {
        return emotionButton;
    }

    public JButton getFileButton() {
        return fileButton;
    }

    public JButton getSentButton() {
        return sentButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public JButton getCreateGroupButton() {
        return createGroupButton;
    }

    public JLabel getStatus1Label() {
        return status1Label;
    }

    public JLabel getStatus2Label() {
        return status2Label;
    }

    public JTextPane getSentMessagePane() {
        return sentMessageArea;
    }

    public JTextPane getGetMessagePane() {
        return getMessageArea;
    }


    public JList getShowList() {
        return showList;
    }

    public JLabel getUserNameLabel() {
        return userNameLabel;
    }

}
