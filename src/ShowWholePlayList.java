import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class ShowWholePlayList extends JFrame {
    JLabel headingLabel;
    JLabel addDel;
    JPanel libraryPanel;
    JLayeredPane topPane;
    JLabel backGround;
    int font=20;

    JButton addSongs,delSong;
    String[] options={"Home","Library"};
    JComboBox comboBox=new JComboBox<>(options);
    JLabel direct;

    queueLinkedList<String> PlayListNames= new queueLinkedList<>();
    queueLinkedList<JButton>PlayLists=new queueLinkedList<>();
    Border border=BorderFactory.createLineBorder(new Color(123,50,250),2);
    private void nameReader(String name){
        try {
            BufferedReader getMusicName= new BufferedReader(new FileReader(name+".txt"));
            String line;
            while ((line=getMusicName.readLine())!=null){
                PlayListNames.add(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    ShowWholePlayList(String name){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(520, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        nameReader(name);
        topPane=new JLayeredPane();
        // topPane.setBounds(0,0,520,75);
        topPane.setPreferredSize(new Dimension(520,75));

        direct=new JLabel();
        direct.setBounds(430,5,75,20);
        direct.setLayout(new BorderLayout());

        comboBox.setFont(new Font("Times New Roman",Font.BOLD,12));
        comboBox.setBorder(border);
        comboBox.setForeground(Color.WHITE);
        comboBox.setBackground(Color.BLACK);
        comboBox.setEditable(true);
        comboBox.getEditor().getEditorComponent().setBackground(Color.BLACK);
        comboBox.getEditor().getEditorComponent().setForeground(Color.WHITE);
        comboBox.addActionListener(e -> {
            if(comboBox.getSelectedItem()=="Home"){
                this.dispose();
                new MainPage();
            }
            if(comboBox.getSelectedItem()=="Library"){
                this.dispose();
                new Library();
            }
        });


        direct.add(comboBox);


        backGround=new JLabel();
        backGround.setBackground(Color.BLACK);
        backGround.setIcon(new ImageIcon("Images/header.jpg"));

        backGround.setBounds(0,0,520,75);


        headingLabel=new JLabel();
        headingLabel.setBounds(155,10,200,50);
        //  headingLabel.setOpaque(true);
        headingLabel.setBackground(Color.BLACK);
        topPane.add(direct,Integer.valueOf(2));
        topPane.add(headingLabel,Integer.valueOf(2));
        topPane.add(backGround,Integer.valueOf(0));



        headingLabel.setText(name);
        headingLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        headingLabel.setForeground(new Color(174,137,238));
        headingLabel.setHorizontalAlignment(JButton.CENTER);

        libraryPanel=new JPanel();
        libraryPanel.setPreferredSize(new Dimension(520,395));
        libraryPanel.setBackground(Color.BLACK);


        queueNode<String> curr= new queueNode<>();
        curr=PlayListNames.head;
        while (curr!=null){
            PlayLists.add(new JButton(curr.data));
            curr=curr.next;
        }
        addSongs=new JButton("Add a new Song");
        addSongs.setFocusable(false);
        addSongs.setIcon(new ImageIcon("Images/addSong.png"));
        addSongs.setPreferredSize(new Dimension(200,40));
        addSongs.setBackground(Color.BLACK);
        addSongs.setFont(new Font("Times New Roman",Font.BOLD,font));
        addSongs.setForeground(Color.WHITE);
       // addSongs.setIcon(new ImageIcon("plus.png"));
        addSongs.setHorizontalAlignment(JButton.LEFT);
        addSongs.setIconTextGap(20);
        addSongs.setBorder(border);

        delSong=new JButton("Delete a Song");
        delSong.setIcon(new ImageIcon("Images/delSong.png"));
        delSong.setFocusable(false);
        delSong.setPreferredSize(new Dimension(200,40));
        delSong.setBackground(Color.BLACK);
        delSong.setFont(new Font("Times New Roman",Font.BOLD,font));
        delSong.setForeground(Color.WHITE);
        //delSong.setIcon(new ImageIcon("plus.png"));
        delSong.setHorizontalAlignment(JButton.LEFT);
        delSong.setIconTextGap(20);
        delSong.setBorder(border);
        delSong.addActionListener(e -> {
            this.dispose();
            new delSong(name);
        });



        queueNode<JButton>currButton=new queueNode<>();
        currButton=PlayLists.head;
        while (currButton!=null){
            libraryPanel.add(currButton.data);
            currButton=currButton.next;
        }
        libraryPanel.setLayout(new GridLayout(PlayLists.size,1,5,6));
        //  libraryPanel.add(addSongs);
        currButton=PlayLists.head;
        while (currButton!=null){
            currButton.data.setFocusable(false);
            currButton.data.setPreferredSize(new Dimension(510,40));
            currButton.data.setBackground(Color.BLACK);
            currButton.data.setFont(new Font("Times New Roman",Font.BOLD,font));
            currButton.data.setForeground(Color.WHITE);
            currButton.data.setIcon(new ImageIcon("Images/collectionlogo3.png"));
            currButton.data.setHorizontalAlignment(JButton.LEFT);
            currButton.data.setIconTextGap(20);
            currButton.data.setBorder(border);
            currButton=currButton.next;
        }


        currButton=PlayLists.head;
        while (currButton!=null){
            queueNode<JButton> finalCurrButton=currButton;
            currButton.data.addActionListener(e -> {
                this.dispose();
              new PlayList(name,finalCurrButton.data.getText());
            });
            currButton=currButton.next;
        }
        addSongs.addActionListener(e -> {
            this.dispose();
            new ADDSongs(name);

        });
        addDel=new JLabel();
        addDel.setPreferredSize(new Dimension(520,40));
        addDel.setOpaque(true);
        addDel.setLayout(new BorderLayout());
        addDel.setBackground(Color.BLACK);
        addDel.add(addSongs,BorderLayout.WEST);
        addDel.add(delSong,BorderLayout.EAST);


        this.add(topPane,BorderLayout.NORTH);
        this.add(libraryPanel,BorderLayout.CENTER);
        this.add(addDel,BorderLayout.SOUTH);

        ImageIcon mainIcon=new ImageIcon("Images/mainIcon2.jpg");
        this.setTitle("Music Player +");
        this.setIconImage(mainIcon.getImage());
        this.setResizable(false);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new ShowWholePlayList("Gym");
    }
}
