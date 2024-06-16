import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

public class CollectionP extends JFrame {
    JLabel headingLabel;
      JPanel panel;
      JLayeredPane topPane;
      JLabel backGround;
      JPanel popUp;
    Clip clip;
    boolean isPaused, isLooping;
     JButton play,pause,forward,backward;
    JLabel textField=new JLabel();
    JLabel direct;
    int font=15;
   String[] options={"Home","Library"};
   JComboBox comboBox=new JComboBox<>(options);
    Border border=BorderFactory.createLineBorder(new Color(123,50,250),2);
  queueLinkedList<JButton> collectionMusics=new queueLinkedList<>();
    queueLinkedList<String>musicNames=new queueLinkedList<>();
    private void musicNameReader(String name){
        try {
            BufferedReader getMusicName= new BufferedReader(new FileReader(name+".txt"));
            String line;
            while ((line=getMusicName.readLine())!=null){
                musicNames.add(line);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    CollectionP()  {
     //   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(520, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        musicNameReader("name");
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
                clip.stop();
                new MainPage();
            }
            if(comboBox.getSelectedItem()=="Library"){
                this.dispose();
                clip.stop();
                new Library();
            }
        });


        direct.add(comboBox);
        

        topPane=new JLayeredPane();
     //   topPane.setBounds(0,0,520,75);
        topPane.setPreferredSize(new Dimension(520,60));


        backGround=new JLabel();
        backGround.setBackground(Color.BLACK);
        backGround.setIcon(new ImageIcon("Images/header.jpg"));

        backGround.setBounds(0,0,520,75);


        headingLabel=new JLabel();
        headingLabel.setBounds(155,10,200,50);
       // headingLabel.setOpaque(true);
        headingLabel.setBackground(Color.BLACK);



        headingLabel.setText("Collection");
        headingLabel.setFont(new Font("Times New Roman",Font.BOLD,30));
        headingLabel.setForeground(new Color(174,137,238));
        headingLabel.setHorizontalAlignment(JButton.CENTER);

        panel=new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setPreferredSize(new Dimension(520,275));
       // panel.setLayout(new GridLayout(3,3,10,10));




        queueNode<String> curr= new queueNode<>();
        curr=musicNames.head;
        while (curr!=null){
            collectionMusics.add(new JButton(curr.data));
            curr=curr.next;
        }
        panel.setLayout(new GridLayout(collectionMusics.size,1,10,5));
        queueNode<JButton>currButton=new queueNode<>();
        currButton=collectionMusics.head;
        while (currButton!=null){
            panel.add(currButton.data);
            currButton=currButton.next;
        }
        currButton=collectionMusics.head;
        while (currButton!=null){
            currButton.data.setFocusable(false);
            currButton.data.setPreferredSize(new Dimension(200,100));
            currButton.data.setBackground(Color.BLACK);
            currButton.data.setFont(new Font("Times New Roman",Font.BOLD,font));
            currButton.data.setForeground(Color.WHITE);
            currButton.data.setIcon(new ImageIcon("Images/collectionlogo3.png"));
//            currButton.data.setHorizontalTextPosition(JButton.CENTER);
//            currButton.data.setVerticalTextPosition(JButton.BOTTOM);
            currButton.data.setHorizontalAlignment(JButton.LEFT);
           // currButton.data.setIconTextGap(10);
            currButton.data.setIconTextGap(4);
            currButton.data.setBorder(border);
            currButton=currButton.next;
        }
        currButton=collectionMusics.head;
        while (currButton!=null){
            queueNode<JButton> finalCurrButton=currButton;
            currButton.data.addActionListener(e -> {
                playMusic(finalCurrButton.data.getText());
            });

            currButton=currButton.next;
        }


        Border popPanel=BorderFactory.createLineBorder(new Color(174,137,238),0);
        popUp=new JPanel();
        popUp.setPreferredSize(new Dimension(520,50));
        popUp.setBackground(new Color(174,137,238));
        popUp.setLayout(new FlowLayout(FlowLayout.CENTER,5,15));
        popUp.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));

        pause=new JButton();
        pause.setIcon(new ImageIcon("Images/pauseC.png"));
        pause.setPreferredSize(new Dimension(75,25));
        pause.setFont(new Font("Times New Roman",Font.BOLD,font));
        pause.setForeground(Color.BLACK);
        pause.setHorizontalTextPosition(JButton.CENTER);
        pause.setVerticalTextPosition(JButton.CENTER);
        pause.setBackground(new Color(174,137,238));
        pause.setBorder(popPanel);
        pause.setFocusable(false);
        pause.addActionListener(e -> {
            pauseMusic();
        });
        
        forward=new JButton();
        forward.setIcon(new ImageIcon("Images/fastC.png"));
        forward.setPreferredSize(new Dimension(75,25));
        forward.setFont(new Font("Times New Roman",Font.BOLD,font));
        forward.setForeground(Color.black);
        forward.setHorizontalTextPosition(JButton.CENTER);
        forward.setVerticalTextPosition(JButton.CENTER);
        forward.setBackground(new Color(174,137,238));
        forward.setBorder(popPanel);
        forward.setFocusable(false);
        forward.addActionListener(e -> {
            Forward();
        });


        backward=new JButton();
        backward.setIcon(new ImageIcon("Images/fastCR.png"));
        backward.setPreferredSize(new Dimension(75,25));
        backward.setFont(new Font("Times New Roman",Font.BOLD,font));
        backward.setForeground(Color.BLACK);
        backward.setHorizontalTextPosition(JButton.CENTER);
        backward.setVerticalTextPosition(JButton.CENTER);
        backward.setBackground(new Color(174,137,238));
        backward.setBorder(popPanel);
        backward.setFocusable(false);
        backward.addActionListener(e -> {
            BackWard();
        });

        textField.setPreferredSize(new Dimension(250,30));

       // textField.setBorder(BorderFactory.createLineBorder(new Color(123,50,250),2));
        textField.setForeground(Color.BLACK);
        textField.setFont(new Font("Times New Roman",Font.BOLD,20));
        textField.setBackground(new Color(174,137,238));
        textField.setOpaque(true);
       // textField.setBounds(0,0,100,50);
        textField.setIcon(new ImageIcon("Images/collectionlogo4.png"));
        textField.setText("Currently Not playing");



        popUp.add(textField);
        popUp.add(backward);
        popUp.add(pause);
        popUp.add(forward);






        topPane.add(direct,Integer.valueOf(2));
        topPane.add(headingLabel,Integer.valueOf(2));
        topPane.add(backGround,Integer.valueOf(0));




        this.add(topPane,BorderLayout.NORTH);
        this.add(panel,BorderLayout.CENTER);
        this.add(popUp,BorderLayout.SOUTH);









        ImageIcon mainIcon=new ImageIcon("Images/mainIcon2.jpg");
       // this.pack();
        this.setResizable(false);
        this.setTitle("Music Player +");
        this.setIconImage(mainIcon.getImage());
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new CollectionP();
    }
    public void playMusic(String name) {


        if (clip != null && clip.isRunning()) {
            clip.stop();
        }

        try {
            File file=new File(name + ".wav");
            AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
            clip=AudioSystem.getClip();
            clip.open(audioStream);
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();
            textField.setText(name);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private void pauseMusic() {
        //cancelTimer();
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPaused=true;
            pause.setIcon(new ImageIcon("Images/playC.png"));;
        } else if (clip != null && isPaused) {
            clip.start();
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            isPaused=false;
            pause.setIcon(new ImageIcon("Images/pauseC.png"));
        }
    }
    private  void Forward(){
        if(clip.isRunning()) {
         //   progressBar.setValue((int) TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition() + 10));
            clip.setMicrosecondPosition(clip.getMicrosecondPosition() + TimeUnit.SECONDS.toMicros(10));
        }
    }
    public  void BackWard(){
        if(clip.isRunning() &&((int) TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition()))>=10){
           // progressBar.setValue((int) TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition() - 10));
            clip.setMicrosecondPosition(clip.getMicrosecondPosition() - TimeUnit.SECONDS.toMicros(10));
        }else{
            if(clip.isRunning()){
             //   progressBar.setValue(0);
                clip.setMicrosecondPosition(0);
            }
        }
    }
}
