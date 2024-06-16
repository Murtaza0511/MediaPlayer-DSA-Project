import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class PlayList extends JFrame {
    int font=12;

    int buttonH=25;
    Image background;
    JButton forwardButton;
    JButton backwardButton;
    JButton playButton;
    JButton pauseButton;
    JButton loopButton;
    JButton nextButton;
    JButton previousButton;
    JButton shuffleButton;
    JProgressBar progressBar;
    JLabel musicName;
    Timer timer;
    TimerTask timerTask;
    boolean running=false;
    int index=0;
    String[] musics={"505","speed drive", "Cupid", "Christmas Tree", "Colde"};
    boolean isPaused, isLooping;
    Clip clip;
    JLayeredPane Pane;
    String[] options={"Home","Library","PlayList"};
    JComboBox comboBox=new JComboBox<>(options);
    JLabel direct;

    DoublyNode<String>currMusic=new DoublyNode<>();
    DoublyLinkedList<String>musicNames=new DoublyLinkedList<>();
    Border border=BorderFactory.createLineBorder(new Color(123,50,250),2);
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


    PlayList(String name,String songName){
        background=new ImageIcon("playlistMain2.png").getImage();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(520, 500);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        musicNameReader(name);
        currMusic=search(songName);
       // System.out.println(currMusic.data);

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
            if(comboBox.getSelectedItem()=="PlayList"){
                this.dispose();
                clip.stop();
                new ShowWholePlayList(name);
            }
        });


        direct.add(comboBox);









        JPanel panelButton=new JPanel();
        musicName=new JLabel();
       // musicName.setOpaque(true);
        musicName.setBackground(Color.BLACK);
        musicName.setForeground(new Color(174,137,238));
        musicName.setFont(new Font("Times New Roman",Font.BOLD,30));
        musicName.setBounds(0,150,260,50);
      //  musicName.setHorizontalAlignment();


        JPanel panel=new JPanel();
        panel.setOpaque(true);
        panel.setBackground(Color.BLUE);
        panel.setBounds(50,50,25,25);
        //ImageIcon icon= new ImageIcon("playlistMain2.png");
        ImageIcon icon= new ImageIcon("Images/moon.jpg");
        JLabel label =new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.black);
        label.setIcon(icon);
        label.setBounds(0,0,520,365);


        Pane=new JLayeredPane();
        Pane.setBounds(0,0,520,365);



       /// Pane.add(panelMain,Integer.valueOf(0));
        Pane.add(direct,Integer.valueOf(2));
        Pane.add(musicName,Integer.valueOf(2));
        Pane.add(label,Integer.valueOf(0));



        panelButton.setBackground(Color.BLACK);
        panelButton.setPreferredSize(new Dimension(520,98));

        this.add(Pane);

      //  this.add(panelMain,BorderLayout.NORTH);
        this.add(panelButton,BorderLayout.SOUTH);




        //buttons
        playButton    =new JButton("Play");
        pauseButton   =new JButton();
        loopButton    =new JButton();
        nextButton    =new JButton();
        previousButton=new JButton();
        forwardButton=new JButton();
        backwardButton=new JButton();
        shuffleButton=new JButton();

        playButton.setFocusable(false);
        pauseButton.setFocusable(false);
        nextButton.setFocusable(false);
        previousButton.setFocusable(false);
        loopButton.setFocusable(false);
        forwardButton.setFocusable(false);
        backwardButton.setFocusable(false);
        shuffleButton.setFocusable(false);


        Border border=BorderFactory.createLineBorder(new Color(123,50,250),2);
        Border borderPanel=BorderFactory.createLineBorder(Color.white,0);
        playButton.setBorder(border);
        playButton.setPreferredSize(new Dimension(75,buttonH));
        playButton.setBackground(Color.BLACK);
        playButton.setFont(new Font("Times New Roman",Font.BOLD,font));
        playButton.setForeground(Color.WHITE);
        playButton.setHorizontalTextPosition(JButton.CENTER);
        playButton.setVerticalTextPosition(JButton.CENTER);

         pauseButton.setBorder(borderPanel);
        pauseButton.setPreferredSize(new Dimension(75,buttonH));
        pauseButton.setBackground(Color.BLACK);
        pauseButton.setIcon(new ImageIcon("Images/pauseButton.png"));
        pauseButton.setFont(new Font("Times New Roman",Font.BOLD,font));
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setHorizontalTextPosition(JButton.CENTER);
        pauseButton.setVerticalTextPosition(JButton.CENTER);

        nextButton.setBorder(borderPanel);
        nextButton.setPreferredSize(new Dimension(75,buttonH));
        nextButton.setBackground(Color.BLACK);
        nextButton.setIcon(new ImageIcon("Images/nextButton.png"));
        nextButton.setFont(new Font("Times New Roman",Font.BOLD,font));
        nextButton.setForeground(Color.WHITE);
        nextButton.setHorizontalTextPosition(JButton.CENTER);
        nextButton.setVerticalTextPosition(JButton.CENTER);

        previousButton.setBorder(borderPanel);
        previousButton.setPreferredSize(new Dimension(75,buttonH));
        previousButton.setBackground(Color.BLACK);
        previousButton.setIcon(new ImageIcon("Images/prevButton.png"));
        previousButton.setFont(new Font("Times New Roman",Font.BOLD,font));
        previousButton.setForeground(Color.WHITE);
        previousButton.setHorizontalTextPosition(JButton.CENTER);
        previousButton.setVerticalTextPosition(JButton.CENTER);

        loopButton.setBorder(borderPanel);
        loopButton.setPreferredSize(new Dimension(75,buttonH));
        loopButton.setBackground(Color.BLACK);
        loopButton.setIcon(new ImageIcon("Images/loopButton.png"));
        loopButton.setFont(new Font("Times New Roman",Font.BOLD,font));
        loopButton.setForeground(Color.WHITE);
        loopButton.setHorizontalTextPosition(JButton.CENTER);
        loopButton.setVerticalTextPosition(JButton.CENTER);

        forwardButton.setBorder(borderPanel);
        forwardButton.setPreferredSize(new Dimension(75,buttonH));
        forwardButton.setBackground(Color.BLACK);
        forwardButton.setIcon(new ImageIcon("Images/forButton.png"));
        forwardButton.setFont(new Font("Times New Roman",Font.BOLD,font));
        forwardButton.setForeground(Color.WHITE);
        forwardButton.setHorizontalTextPosition(JButton.CENTER);
        forwardButton.setVerticalTextPosition(JButton.CENTER);

        backwardButton.setBorder(borderPanel);
        backwardButton.setPreferredSize(new Dimension(75,buttonH));
        backwardButton.setBackground(Color.BLACK);
        backwardButton.setIcon(new ImageIcon("Images/backButton.png"));
        backwardButton.setFont(new Font("Times New Roman",Font.BOLD,font));
        backwardButton.setForeground(Color.WHITE);
        backwardButton.setHorizontalTextPosition(JButton.CENTER);
        backwardButton.setVerticalTextPosition(JButton.CENTER);

        shuffleButton.setBorder(borderPanel);
       // shuffleButton.setPreferredSize(new Dimension(75,buttonH));
        shuffleButton.setBounds(500,450,100,buttonH);
        shuffleButton.setBackground(Color.BLACK);
        shuffleButton.setIcon(new ImageIcon("Images/shuffleButton.png"));
        shuffleButton.setFont(new Font("Times New Roman",Font.BOLD,font));
        shuffleButton.setForeground(Color.WHITE);
        shuffleButton.setHorizontalTextPosition(JButton.CENTER);
        shuffleButton.setVerticalTextPosition(JButton.CENTER);



        playButton.addActionListener(e -> {
           // playMusic();
        });
        pauseButton.addActionListener(e -> {
            pauseMusic();
        });

        loopButton.addActionListener(e -> {
            loopMusic();
        });

        nextButton.addActionListener(e -> {
            nextMusic();
        });

        previousButton.addActionListener(e -> {
            previousMusic();
        });
        forwardButton.addActionListener(e -> {
            Forward();
        });
        backwardButton.addActionListener(e -> {
            BackWard();
        });

        shuffleButton.addActionListener(e -> {
            shuffle();
        });
        progressBar=new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(123,50,250));
        progressBar.setBackground(Color.BLACK);
        progressBar.setBorder(border);
        progressBar.setPreferredSize(new Dimension(500,10));
        progressBar.setString(" ");



        isPaused =false;
        isLooping=false;
        panelButton.setLayout(new FlowLayout(FlowLayout.CENTER,55,5));
        panelButton.add(progressBar);
       // panelButton.add(playButton);
        panelButton.add(previousButton);
        panelButton.add(pauseButton);
        panelButton.add(nextButton);

        panelButton.add(loopButton);
        panelButton.add(backwardButton);
        panelButton.add(forwardButton);
        panelButton.add(shuffleButton);
        playMusic(songName);

        ImageIcon mainIcon=new ImageIcon("Images/mainIcon2.jpg");
        this.setTitle("Music Player +");
        this.setIconImage(mainIcon.getImage());
        this.setResizable(false);
        this.setVisible(true);
    }



    private void playMusic(String name) {


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
            musicName.setText(name);

            beginTimer();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private void shuffleMusics(){
        String temp = musics[index];
        musics[index]=musics[0];
        musics[0]=temp;
        Random random=new Random();
        for (int i=musics.length-1; i >1 ; i--) {
            int randomIndex=random.nextInt(1+i);
            temp=musics[i];
           musics[i]= musics[randomIndex];
           musics[randomIndex]=temp;
        }
    }

    private void pauseMusic() {
        cancelTimer();
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPaused=true;
            pauseButton.setIcon(new ImageIcon("Images/playButton.png"));
        } else if (clip != null && isPaused) {
            clip.start();
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            isPaused=false;
            pauseButton.setIcon(new ImageIcon("Images/pauseButton.png"));
           // pauseButton.setText("Pause");
        }
    }

    private void loopMusic() {
        isLooping=!isLooping;

        if (isLooping) {


            if (clip.isRunning()) {
                progressBar.setValue(0);
                clip.setMicrosecondPosition(0);
                clip.loop(Clip.LOOP_CONTINUOUSLY);


            } else {

                if (clip.isRunning()) {
                    clip.loop(0);
                }
            }
        }
    }

    private void nextMusic() {
        pauseButton.setIcon(new ImageIcon("Images/pauseButton.png"));
        if(currMusic!=null){
            currMusic=currMusic.next;
            clip.stop();
            if(running){
                cancelTimer();
            }
            try {
                if(currMusic==null){
                    currMusic=musicNames.head;
                    clip.stop();
                    if(running){
                        cancelTimer();
                    }
                }
                File file=new File(currMusic.data+ ".wav");
                AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
                clip=AudioSystem.getClip();
                clip.open(audioStream);
                musicName.setText(currMusic.data);
                if (isLooping) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                playMusic(currMusic.data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                currMusic=musicNames.head;
                clip.stop();
                if(running){
                    cancelTimer();
                }
                File file = new File(currMusic.data+".wav");
                AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
                clip=AudioSystem.getClip();
                musicName.setText(currMusic.data);
                clip.open(audioStream);
                if (isLooping) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                playMusic(currMusic.data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void previousMusic() {
        pauseButton.setIcon(new ImageIcon("Images/pauseButton.png"));
        if(currMusic!=null){
            currMusic=currMusic.prev;
            clip.stop();
            if(running){
                cancelTimer();
            }
            try {
                if(currMusic==null){
                    currMusic=musicNames.tail;
                    clip.stop();
                    if(running){
                        cancelTimer();
                    }
                }
                File file = new File(currMusic.data+".wav");
                AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
                clip=AudioSystem.getClip();
                musicName.setText(currMusic.data);
                clip.open(audioStream);
                if (isLooping) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                playMusic(currMusic.data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                currMusic=musicNames.tail;
                clip.stop();
                if(running){
                    cancelTimer();
                }
                File file=new File(currMusic.data + ".wav");
                AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
                clip=AudioSystem.getClip();
                musicName.setText(currMusic.data);
                clip.open(audioStream);
                if (isLooping) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                playMusic(currMusic.data);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void beginTimer() {
        timer    =new Timer();

        timerTask=new TimerTask() {
            @Override
            public void run() {
                running=true;
                double current=TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition());
                double end=TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondLength());
                progressBar.setValue((int)((current/end)*100.0f) );
                progressBar.setString(" ");
                if ((current / end) == 1) {
                    cancelTimer();
                }

            }
        };
        timer.scheduleAtFixedRate(timerTask, 0, 1000);

    }

    public void cancelTimer() {
        running=false;
        timer.cancel();
    }
    private  void Forward(){
        if(clip.isRunning()) {
            progressBar.setValue((int) TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition() + 10));
            clip.setMicrosecondPosition(clip.getMicrosecondPosition() + TimeUnit.SECONDS.toMicros(10));
        }
    }
    public  void BackWard(){
        if(clip.isRunning() &&((int) TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition()))>=10){
            progressBar.setValue((int) TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition() - 10));
            clip.setMicrosecondPosition(clip.getMicrosecondPosition() - TimeUnit.SECONDS.toMicros(10));
        }else{
            if(clip.isRunning()){
                progressBar.setValue(0);
                clip.setMicrosecondPosition(0);
            }
        }
    }

    public void sort(){
        DoublyNode<String>curr,index;
        for(curr=musicNames.head;curr!=null;curr=curr.next){
            for(index=curr.next;index!=null;index=index.next){
                if(curr.num>index.num){
                    String temp=curr.data;
                    curr.data=index.data;
                    index.data=temp;

                    int tempNum=curr.num;
                    curr.num=index.num;
                    index.num=tempNum;
                }
            }
        }
    }
    private Random random=new Random();
    public void assignRandomNum(){
        DoublyNode<String>curr=musicNames.head;
        while (curr!=null){
            curr.num=random.nextInt(0,DoublyNode.count);
            curr=curr.next;
        }
    }

    public void shuffle(){
        assignRandomNum();
        sort();
    }

   public DoublyNode<String> search(String songName){
        DoublyNode<String>curr=musicNames.head;
        while (curr!=null){
            if(curr.data.equals(songName)){
               // System.out.println(curr.data);
                break;
            }
            curr=curr.next;
        }
        return curr;
    }

    public static void main(String[] args) {
        new PlayList("Gym","505");
    }

 

}
