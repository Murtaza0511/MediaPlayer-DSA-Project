
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Time;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class testing extends JFrame {

  JButton playButton;
  JButton pauseButton;
  JButton loopButton;
  JButton nextButton;
  JButton previousButton;
  JSlider volume;
  JProgressBar progressBar;
  JButton forwardButton;
  JButton backwardButton;
  JButton shuffleButton;
  Timer timer;
  TimerTask timerTask;
  boolean running=false;
  private int currentValue = 0;
  javax.swing.Timer timer2;

  int index=0;
  String[] musics={"speed drive","Cupid","Christmas Tree","Colde"};
  boolean isPaused,isLooping;
  Clip clip;
  DoublyNode<String>currMusic=new DoublyNode<>();

DoublyLinkedList<String>musicNames=new DoublyLinkedList<>();

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

   testing(String name){
       this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setSize(500,700);
       this.setLocationRelativeTo(null);
       musicNameReader(name);
       currMusic=musicNames.head;


       //buttons
       playButton=new JButton("Play");
       pauseButton=new JButton("Pause");
       loopButton = new JButton("Loop");
       nextButton=new JButton("Next");
       previousButton=new JButton("Previous");
       forwardButton=new JButton(">");
       backwardButton=new JButton("<");
       shuffleButton=new JButton("@");
       volume=new JSlider(0,100);
       progressBar=new JProgressBar(0,100);
       progressBar.setValue(0);
       progressBar.setStringPainted(true);

       volume.addChangeListener(new ChangeListener() {
           @Override
           public void stateChanged(ChangeEvent e) {


           }
       });
       shuffleButton.addActionListener(e -> {
           shuffleMusics();
       });
       playButton.addActionListener(e -> {

               playMusic();



       });
       forwardButton.addActionListener(e -> {
           Forward();
       });
       backwardButton.addActionListener(e -> {
           BackWard();
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

       isPaused=false;
       isLooping=false;


       this.add(playButton);
      this.add(pauseButton);
       this.add(nextButton);
       this.add(previousButton);
       this.add(loopButton);
       this.add(progressBar);
       this.add(forwardButton);
       this.add(backwardButton);
       this.add(shuffleButton);
       this.setLayout(new FlowLayout());




       this.setVisible(true);

   }
   private  void playMusic(){


       if(clip!=null && clip.isRunning()){
            clip.stop();


        }

       try {

               File file=new File(currMusic.data + ".wav");
               AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
               clip=AudioSystem.getClip();
               clip.open(audioStream);
               if (isLooping) {
                   clip.loop(Clip.LOOP_CONTINUOUSLY);
               }

               clip.start();
               beginTimer();


       } catch (Exception e) {
           throw new RuntimeException(e);
       }

   }

   private void pauseMusic(){
       cancelTimer();
       if(clip!=null && clip.isRunning()){
           clip.stop();
           isPaused=true;
           pauseButton.setText("Resume");
       } else if (clip!=null && isPaused) {
           clip.start();
           if(isLooping){
               clip.loop(Clip.LOOP_CONTINUOUSLY);
           }
           isPaused=false;
           pauseButton.setText("Pause");
       }
   }

   private void loopMusic(){
       isLooping=!isLooping;
       if(isLooping){
           loopButton.setText("Stop Loop");
           if(clip.isRunning()){
               clip.loop(Clip.LOOP_CONTINUOUSLY);
           }
           else {
               loopButton.setText("Loop");
               if(clip.isRunning()){
                   clip.loop(0);
               }
           }
       }
   }
   private void nextMusic(){
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
               File file = new File(currMusic.data+".wav");
               AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
               clip=AudioSystem.getClip();
               clip.open(audioStream);
               if(isLooping){
                   clip.loop(Clip.LOOP_CONTINUOUSLY);
               }
               playMusic();
           } catch (Exception e) {
               throw new RuntimeException(e);
           }

       }else {
           try {
               currMusic=musicNames.head;
               clip.stop();
               if(running){
                   cancelTimer();
               }
               File file = new File(currMusic.data+".wav");
               AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
               clip=AudioSystem.getClip();
               clip.open(audioStream);
               if(isLooping){
                   clip.loop(Clip.LOOP_CONTINUOUSLY);
               }
               playMusic();
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
       }
   }
   private void previousMusic(){

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
               AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
               clip=AudioSystem.getClip();
               clip.open(audioStream);
               if(isLooping){
                   clip.loop(Clip.LOOP_CONTINUOUSLY);
               }
               playMusic();
           } catch (Exception e) {
               throw new RuntimeException(e);
           }

       }else {
           try {

               currMusic=musicNames.tail;
               clip.stop();
               if(running){
                   cancelTimer();
               }
               File file = new File(currMusic.data+".wav");
               AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
               clip=AudioSystem.getClip();
               clip.open(audioStream);
               if(isLooping){
                   clip.loop(Clip.LOOP_CONTINUOUSLY);
               }
               playMusic();
           } catch (Exception e) {
               throw new RuntimeException(e);
           }
       }
   }

   public  void beginTimer(){
       timer=new Timer();
       timerTask=new TimerTask() {
           @Override
           public void run() {
            running=true;
            double current=TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition());
            double end=TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondLength());
               progressBar.setValue((int) (current));
               progressBar.setString(" ");
               if((current/end)==1){
                cancelTimer();
               }

           }
       };
        timer.scheduleAtFixedRate(timerTask,0,1000);


   }
   public void cancelTimer(){
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
    private void shuffleMusics(){
        String temp =currMusic.data;
        currMusic.data=musicNames.head.data;
        musicNames.head.data=temp;


        Random random=new Random();
        for (int i=musics.length-1; i >1 ; i--) {
            int randomIndex=random.nextInt(1+i);
                                temp=musics[i];
            musics[i]= musics[randomIndex];
            musics[randomIndex]=temp;
        }
    }

}
