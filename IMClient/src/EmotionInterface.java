import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EmotionInterface extends JFrame {

    private JPanel contentPane;
    private JTextPane sentMessageTextPane;

    public EmotionInterface(JTextPane sentMessageTextPane) {
        this.sentMessageTextPane = sentMessageTextPane;
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 360, 240);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel emotionPanel = new JPanel();
        emotionPanel.setBounds(0, 0, 360, 218);
        contentPane.add(emotionPanel);
        emotionPanel.setLayout(new GridLayout(8, 12, 0, 0));

        for(int i=0;i<96;i++){
            JLabel emotion = new JLabel();
            emotion.addMouseListener(new MouseListener() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub
                    Document docs = sentMessageTextPane.getDocument();//获得文本对象
                    try {
                        docs.remove(docs.getLength()-1,1);
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }

                    dispose();
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub

                }


                @Override
                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    Document docs = sentMessageTextPane.getDocument();//获得文本对象
                    SimpleAttributeSet keyWord = new SimpleAttributeSet();
                    try {
                        String emo = "*"+emotion.getName()+"*"+"  ";
                        docs.insertString(docs.getLength(),emo,keyWord);
                    } catch (BadLocationException e1) {
                        e1.printStackTrace();
                    }
                }


                @Override
                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub

                }
            });
            emotion.setName(""+i);
            emotion.setIcon(new ImageIcon(this.getClass().getResource("/emotion/"+i+".gif")));
            emotion.setSize(new Dimension(30, 30));
            emotionPanel.add(emotion);
        }
    }
}