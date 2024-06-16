import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MainPage extends JFrame {

    JLayeredPane base;
    JLabel  backGround;
    JLabel logo;
    JLabel name;
    JLabel buttons;
    JButton collection,library;
    Border border=BorderFactory.createLineBorder(new Color(123,50,250),2);
    int font=18;
    MainPage(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(520, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        base=new JLayeredPane();
        base.setPreferredSize(new Dimension(520,500));
        backGround=new JLabel();
        backGround.setBackground(Color.BLACK);
        backGround.setIcon(new ImageIcon("Images/MainPage.jpg"));
        backGround.setBounds(0,0,520,500);

        logo=new JLabel();
        logo.setBackground(Color.BLACK);
        logo.setIcon(new ImageIcon("Images/newMain.jpg"));
        logo.setBounds(175,65,150,130);

        name=new JLabel();
        name.setBackground(Color.BLACK);
        name.setIcon(new ImageIcon("Images/musicPlayer.png"));
        name.setBounds(145,200,230,75);

        buttons=new JLabel();
        buttons.setBackground(Color.BLACK);
      //  buttons.setIcon(new ImageIcon("musicPlayer.png"));
        buttons.setBounds(130,300,230,75);
        buttons.setLayout(new BorderLayout());
        collection=new JButton("Collection");

        collection.setFont(new Font("Times New Roman",Font.BOLD,font));
        collection.setBorder(border);
        collection.setBackground(Color.BLACK);
        collection.setForeground(Color.WHITE);
        collection.setFocusable(false);
        collection.addActionListener(e -> {
            this.dispose();
            new CollectionP();
        });


        library=new JButton("Library");
        library.setFont(new Font("Times New Roman",Font.BOLD,font));
        library.setBorder(border);
        library.setBackground(Color.BLACK);
        library.setForeground(Color.WHITE);
        library.setFocusable(false);
        library.addActionListener(e -> {
            this.dispose();
            new Library();
        });
        
        
        buttons.add(collection,BorderLayout.NORTH);
        buttons.add(library,BorderLayout.SOUTH);


        base.add(backGround,Integer.valueOf(0));
        base.add(logo,Integer.valueOf(1));
        base.add(name,Integer.valueOf(1));
        base.add(buttons,Integer.valueOf(1));


        this.add(base);
        ImageIcon mainIcon=new ImageIcon("Images/mainIcon2.jpg");
        this.setTitle("Music Player +");
        this.setIconImage(mainIcon.getImage());
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new MainPage();
    }
}
