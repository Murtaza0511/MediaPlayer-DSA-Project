import javax.swing.*;
import java.awt.*;

public class collectName extends JFrame {
    JLabel label;
    JPanel musicName;
    JLayeredPane Pane;
    JButton Done;
    JTextField textField=new JTextField();
    collectName(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 300);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        textField.setPreferredSize(new Dimension(400,50));
        textField.setOpaque(true);
        textField.setBackground(Color.BLACK);
        textField.setForeground(new Color(174,137,238));
        textField.setFont(new Font("Times New Roman",Font.BOLD,40));
        textField.setBorder(BorderFactory.createLineBorder(new Color(123,50,250),2));
        textField.setCaretColor(new Color(123,50,250));
        label=new JLabel();
        label.setPreferredSize(new Dimension(100,50));
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        label.setText("Enter Name For PlayList");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setForeground(new Color(174,137,238));
        label.setFont(new Font("Times New Roman",Font.BOLD,30));


        Done=new JButton("Done");
        Done.setFocusable(false);
        Done.setForeground(Color.WHITE);
        Done.setBackground(Color.BLACK);
        Done.setFont(new Font("Times New Roman",Font.BOLD,20));
        Done.setPreferredSize(new Dimension(400,20));
        Done.setBorder(BorderFactory.createLineBorder(new Color(123,50,250),2));




        musicName=new JPanel();
        musicName.setOpaque(true);
        musicName.setBackground(Color.BLACK);
     //   musicName.setForeground(new Color(174,137,238));
        musicName.setFont(new Font("Times New Roman",Font.BOLD,40));
        musicName.setBounds(50,40,400,200);
        musicName.setLayout(new BorderLayout(10,10));

        musicName.add(label,BorderLayout.NORTH);
        musicName.add(textField,BorderLayout.CENTER);
        musicName.add(Done,BorderLayout.SOUTH);
        ImageIcon icon= new ImageIcon("Images/playlistMain2.png");
        JLabel label =new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.black);
        label.setIcon(icon);
        label.setBounds(0,0,520,365);
        Done.addActionListener(e -> {
            new ADDPLayList(textField.getText());
        });


        Pane=new JLayeredPane();
        Pane.setBounds(0,0,520,365);
        Pane.add(musicName,Integer.valueOf(2));
        Pane.add(label,Integer.valueOf(0));
        this.add(Pane);
        ImageIcon mainIcon=new ImageIcon("Images/mainIcon2.jpg");
        this.setTitle("Music Player +");
        this.setIconImage(mainIcon.getImage());
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new collectName();
    }

}
