import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AddFriendInterface extends JFrame {

    private JPanel contentPane;
    private JTextField userNameTextField;
    private JButton addNewFriendButton;

    public JTextField getUserNameTextField() {
        return userNameTextField;
    }

    public JButton getAddNewFriendButton() {
        return addNewFriendButton;
    }

    public AddFriendInterface() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(400, 250, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Please input the UserName you want to add",JLabel.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 18));
        lblNewLabel.setBounds(30, 21, 397, 77);
        contentPane.add(lblNewLabel);

        userNameTextField = new JTextField();
        userNameTextField.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        userNameTextField.setBounds(148, 108, 144, 38);
        contentPane.add(userNameTextField);
        userNameTextField.setColumns(10);

        addNewFriendButton = new JButton("ADD");
        addNewFriendButton.setBounds(160, 191, 117, 43);
        contentPane.add(addNewFriendButton);
    }

}
