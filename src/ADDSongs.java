import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.*;

public class ADDSongs extends JFrame {
    JLabel headingLabel;

    JPanel libraryPanel;
    JLayeredPane topPane;
    JLabel backGround;
    JButton Done ;
    int font=20;

    queueLinkedList<String> SongNames= new queueLinkedList<>();
    queueLinkedList<String> GetSong= new queueLinkedList<>();
    queueLinkedList<JButton>PlayLists=new queueLinkedList<>();
    Border border=BorderFactory.createLineBorder(new Color(123,50,250),2);
    private void nameReader(){
        try {
            BufferedReader getMusicName= new BufferedReader(new FileReader("name.txt"));
            String line;
            while ((line=getMusicName.readLine())!=null){
                SongNames.add(line);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void GettingSongs(String name){
        try {
            BufferedReader getMusicName= new BufferedReader(new FileReader(name+".txt"));
            String line;
            while ((line=getMusicName.readLine())!=null){
                GetSong.add(line);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    ADDSongs(String name){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(520, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        nameReader();
        GettingSongs(name);
        topPane=new JLayeredPane();
        topPane.setPreferredSize(new Dimension(520,75));
        backGround=new JLabel();
        backGround.setBackground(Color.BLACK);
        backGround.setIcon(new ImageIcon("Images/header.jpg"));

        backGround.setBounds(0,0,520,75);


        headingLabel=new JLabel();
        headingLabel.setBounds(50,10,400,50);
        //  headingLabel.setOpaque(true);
        headingLabel.setBackground(Color.BLACK);



        headingLabel.setText("Select Musics for playlist ");
        headingLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        headingLabel.setForeground(new Color(174,137,238));
        headingLabel.setHorizontalAlignment(JButton.CENTER);

        libraryPanel=new JPanel();
        libraryPanel.setPreferredSize(new Dimension(520,395));
        libraryPanel.setBackground(Color.BLACK);


        topPane.add(headingLabel,Integer.valueOf(2));
        topPane.add(backGround,Integer.valueOf(0));




        queueNode<String> curr= new queueNode<>();
        curr=SongNames.head;
        while (curr!=null){
            PlayLists.add(new JButton(curr.data));
            curr=curr.next;
        }
        Done=new JButton("Done");
        Done=new JButton("Done");
        Done.setFocusable(false);
        Done.setForeground(Color.WHITE);
        Done.setBackground(Color.BLACK);
        Done.setFont(new Font("Times New Roman",Font.BOLD,20));
        Done.setPreferredSize(new Dimension(400,20));
        Done.setBorder(BorderFactory.createLineBorder(new Color(123,50,250),2));


        queueNode<JButton>currButton=new queueNode<>();
        currButton=PlayLists.head;
        while (currButton!=null){
            libraryPanel.add(currButton.data);
            currButton=currButton.next;
        }
        libraryPanel.setLayout(new GridLayout(PlayLists.size,1,5,6));
        currButton=PlayLists.head;
        while (currButton!=null){
            currButton.data.setFocusable(false);
            currButton.data.setPreferredSize(new Dimension(510,40));
            currButton.data.setBackground(Color.BLACK);
            currButton.data.setFont(new Font("Times New Roman",Font.BOLD,font));
            currButton.data.setForeground(Color.WHITE);
            currButton.data.setIcon(new ImageIcon("Images/musicButton.png"));
            currButton.data.setHorizontalAlignment(JButton.LEFT);
            currButton.data.setIconTextGap(20);
            currButton.data.setBorder(border);
            currButton=currButton.next;
        }


        currButton=PlayLists.head;
        while (currButton!=null){
            queueNode<JButton> finalCurrButton=currButton;
            currButton.data.addActionListener(e -> {
                GetSong.add(finalCurrButton.data.getText());
                addSongs(name);
            });
            currButton=currButton.next;
        }
        Done.addActionListener(e -> {
            this.dispose();
            new ShowWholePlayList(name);
        });


        this.add(topPane,BorderLayout.NORTH);
        this.add(libraryPanel,BorderLayout.CENTER);
        this.add(Done,BorderLayout.SOUTH);
        ImageIcon mainIcon=new ImageIcon("Images/mainIcon2.jpg");
        this.setTitle("Music Player +");
        this.setIconImage(mainIcon.getImage());
        this.setResizable(false);

        this.setVisible(true);


    }
    private void addSongs(String name){
        queueNode<String>curr=new queueNode<>();
        curr=GetSong.head;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(name+".txt"));
            writer.write(curr.data);
            curr=curr.next;
            while (curr!=null){
                writer.write("\n"+curr.data);
                curr=curr.next;
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new ADDSongs("Soft");
    }
}
