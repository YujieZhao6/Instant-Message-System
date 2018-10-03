import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;

public class CreateGroupInterface extends JFrame {

    private JPanel contentPane;
    private JButton okButton;
    private JList friendsList;

    public CreateGroupInterface() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(400, 250, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        friendsList = new JList();
        friendsList.setBounds(127, 59, 187, 173);
        contentPane.add(friendsList);

        JLabel lblNewLabel = new JLabel("Select the friends you want to creat a group",JLabel.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        lblNewLabel.setBounds(39, 6, 383, 41);
        contentPane.add(lblNewLabel);

        okButton = new JButton("Ok");
        okButton.setBounds(160, 244, 117, 29);
        contentPane.add(okButton);
    }

    public JButton getOkButton() {
        return okButton;
    }

    public JList<String> getFriendsList() {
        return friendsList;
    }


}
