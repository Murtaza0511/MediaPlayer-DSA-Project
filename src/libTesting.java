import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class libTesting extends JFrame {
    JLabel headingLabel;

   JPanel libraryPanel;
   JLayeredPane topPane;
    JLabel backGround;
    int font=20;

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


     libTesting(){
         this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         this.setSize(520, 500);
         this.setLocationRelativeTo(null);
         this.setLayout(new BorderLayout());
         nameReader();
         topPane=new JLayeredPane();
         topPane.setBounds(0,0,520,75);


        backGround=new JLabel();
        backGround.setBackground(Color.BLACK);
        backGround.setIcon(new ImageIcon("Images/libraryBg2.png"));

        backGround.setBounds(0,0,520,75);


         headingLabel=new JLabel();
         headingLabel.setBounds(155,10,200,50);
         headingLabel.setOpaque(true);
         headingLabel.setBackground(Color.BLACK);



         headingLabel.setText("Library");
         headingLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
         headingLabel.setForeground(new Color(174,137,238));
         headingLabel.setHorizontalAlignment(JButton.CENTER);

         libraryPanel=new JPanel();
         libraryPanel.setPreferredSize(new Dimension(520,395));
         libraryPanel.setBackground(Color.BLACK);


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


        queueNode<JButton>currButton=new queueNode<>();
        currButton=PlayLists.head;
        while (currButton!=null){
            libraryPanel.add(currButton.data);
            currButton=currButton.next;
        }
         currButton=PlayLists.head;
         while (currButton!=null){
              currButton.data.setFocusable(false);
              currButton.data.setPreferredSize(new Dimension(520,40));
              currButton.data.setBackground(Color.BLACK);
              currButton.data.setFont(new Font("Times New Roman",Font.BOLD,font));
              currButton.data.setForeground(Color.WHITE);
              currButton.data.setIcon(new ImageIcon("Images/musicButton.png"));
              //currButton.data.setVerticalTextPosition(JButton.CENTER);
             currButton.data.setHorizontalAlignment(JButton.LEFT);
             currButton.data.setIconTextGap(20);
             currButton.data.setBorder(border);

             currButton=currButton.next;
         }


         currButton=PlayLists.head;
         while (currButton!=null){
             queueNode<JButton> finalCurrButton=currButton;
             currButton.data.addActionListener(e -> {
                 //testing test=new testing(finalCurrButton.data.getText());
                 System.out.println("ok");
             });
             currButton=currButton.next;
         }








         this.setVisible(true);
     }

    public static void main(String[] args) {
        new libTesting();
    }
}
