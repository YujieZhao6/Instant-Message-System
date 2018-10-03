/**
 * Created by courage on 4/3/16.
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;

public class LoginInterface extends JFrame{
    private JPanel contentPane;
    private JTextField userNameTest;
    private JPasswordField passWordText;
    private JButton signinButton;
    private JButton singupButton;


    public LoginInterface() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(400, 250, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("UserName:");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        lblNewLabel.setBounds(79, 83, 99, 39);
        contentPane.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("PassWord:");
        lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(79, 134, 95, 39);
        contentPane.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("George IM");
        lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
        lblNewLabel_2.setBounds(166, 16, 116, 54);
        contentPane.add(lblNewLabel_2);

        userNameTest = new JTextField();
        userNameTest.setBounds(210, 87, 147, 32);
        contentPane.add(userNameTest);
        userNameTest.setColumns(10);

        passWordText = new JPasswordField();
        passWordText.setBounds(210, 138, 147, 33);
        contentPane.add(passWordText);
        passWordText.setColumns(10);

        signinButton = new JButton("Sign In");
        signinButton.setBounds(113, 204, 87, 39);
        contentPane.add(signinButton);

        singupButton = new JButton("Sign Up");
        singupButton.setBounds(252, 204, 87, 39);
        contentPane.add(singupButton);
    }

    public JButton getLohinButton(){
        return  signinButton;
    }

    public  JButton getSingupButton(){
        return  singupButton;
    }

    public String getUserName(){
        return userNameTest.getText();
    }

    public  String getPassWord(){
        return new String(passWordText.getPassword());
    }
}
