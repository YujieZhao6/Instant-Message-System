import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SignUpInterface extends JFrame {

    private JPanel contentPane;
    private JTextField userNameTest;
    private JTextField passWordTest;
    private JButton signUpButton;


    public SignUpInterface() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 250, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSignUp = new JLabel("Sign Up");
        lblSignUp.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
        lblSignUp.setBounds(170, 9, 100, 60);
        contentPane.add(lblSignUp);

        JLabel userNameLabel = new JLabel("UserName:");
        userNameLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        userNameLabel.setBounds(90, 85, 99, 39);
        contentPane.add(userNameLabel);

        userNameTest = new JTextField();
        userNameTest.setColumns(10);
        userNameTest.setBounds(209, 89, 147, 32);
        contentPane.add(userNameTest);

        JLabel passWordLabel = new JLabel("PassWord:");
        passWordLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        passWordLabel.setBounds(88, 145, 95, 39);
        contentPane.add(passWordLabel);

        passWordTest = new JTextField();
        passWordTest.setColumns(10);
        passWordTest.setBounds(209, 146, 147, 32);
        contentPane.add(passWordTest);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(161, 209, 117, 41);
        contentPane.add(signUpButton);

    }

    public JButton getSignUpButton(){
        return  signUpButton;
    }

    public String getUserName(){
        return  userNameTest.getText();
    }

    public  String getPassword(){
        return  passWordTest.getText();
    }

}
