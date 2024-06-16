import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Library extends JFrame {
    JLabel headingLabel;

    JPanel libraryPanel;
    JLayeredPane topPane;
    JLabel backGround;
    int font=20;
    
    JButton addPlayList;
    String[] options={"Home","Collection"};
    JComboBox comboBox=new JComboBox<>(options);
    JLabel direct;

    queueLinkedList<String> PlayListNames= new queueLinkedList<>();
    queueLinkedList<JButton>PlayLists=new queueLinkedList<>();
    Border border=BorderFactory.createLineBorder(new Color(123,50,250),2);
    private void nameReader(){
        try {
            BufferedReader getMusicName= new BufferedReader(new FileReader("PlayListNames.txt"));
            String line;
            while ((line=getMusicName.readLine())!=null){
                PlayListNames.add(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    Library(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(520, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        nameReader();
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
            if(comboBox.getSelectedItem()=="Collection"){
                this.dispose();
                new CollectionP();
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



        headingLabel.setText("Library");
        headingLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        headingLabel.setForeground(new Color(174,137,238));
        headingLabel.setHorizontalAlignment(JButton.CENTER);

        libraryPanel=new JPanel();
        libraryPanel.setPreferredSize(new Dimension(520,395));
        libraryPanel.setBackground(Color.BLACK);

        topPane.add(direct,Integer.valueOf(2));
        topPane.add(headingLabel,Integer.valueOf(2));
        topPane.add(backGround,Integer.valueOf(0));



        this.add(topPane);
        this.add(libraryPanel,BorderLayout.SOUTH);
        queueNode<String> curr= new queueNode<>();
        curr=PlayListNames.head;
        while (curr!=null){
            PlayLists.add(new JButton(curr.data));
            curr=curr.next;
        }
        addPlayList=new JButton("Add a new PlayList");
        addPlayList.setFocusable(false);
        addPlayList.setPreferredSize(new Dimension(510,40));
        addPlayList.setBackground(Color.BLACK);
        addPlayList.setFont(new Font("Times New Roman",Font.BOLD,font));
        addPlayList.setForeground(Color.WHITE);
        addPlayList.setIcon(new ImageIcon("Images/playlistAdd.png"));
        addPlayList.setHorizontalAlignment(JButton.LEFT);
        addPlayList.setIconTextGap(20);
        addPlayList.setBorder(border);
        


        queueNode<JButton>currButton=new queueNode<>();
        currButton=PlayLists.head;
        while (currButton!=null){
            libraryPanel.add(currButton.data);
            currButton=currButton.next;
        }
        libraryPanel.setLayout(new GridLayout(PlayLists.size,1,5,6));
      //  libraryPanel.add(addPlayList);
        currButton=PlayLists.head;
        while (currButton!=null){
            currButton.data.setFocusable(false);
            currButton.data.setPreferredSize(new Dimension(510,40));
            currButton.data.setBackground(Color.BLACK);
            currButton.data.setFont(new Font("Times New Roman",Font.BOLD,font));
            currButton.data.setForeground(Color.WHITE);
            currButton.data.setIcon(new ImageIcon("Images/playlistButton.png"));
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
                new ShowWholePlayList(finalCurrButton.data.getText());
            });
            currButton=currButton.next;
        }
        addPlayList.addActionListener(e -> {
                this.dispose();
                new collectName();

        });


        this.add(topPane,BorderLayout.NORTH);
        this.add(libraryPanel,BorderLayout.CENTER);
        this.add(addPlayList,BorderLayout.SOUTH);

        ImageIcon mainIcon=new ImageIcon("Images/mainIcon2.jpg");
        this.setTitle("Music Player +");
        this.setIconImage(mainIcon.getImage());
        this.setResizable(false);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Library();
    }

}
