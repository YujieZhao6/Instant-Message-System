import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class SuccessInterface extends JFrame {

    private JPanel contentPane;

    public SuccessInterface() {
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setBounds(480, 300, 300, 200);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Success!",JLabel.CENTER);
        lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 25));
        lblNewLabel.setBounds(85, 23, 119, 55);
        contentPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("Yes");
        btnNewButton.setBounds(107, 106, 61, 37);
        contentPane.add(btnNewButton);
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
            }
        });
    }

}