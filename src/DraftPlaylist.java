import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class DraftPlaylist extends JFrame {
    JButton playButton;
    JButton pauseButton;
    JButton loopButton;
    JButton nextButton;
    JButton previousButton;
    JProgressBar progressBar;
    Timer timer;
    TimerTask timerTask;
    boolean running=false;
    int index=0;
    String[] musics={"speed drive", "Cupid", "Christmas Tree", "Colde"};
    boolean isPaused, isLooping;
    Clip clip;


    DraftPlaylist() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 700);
        this.setLocationRelativeTo(null);

        //buttons
        playButton    =new JButton("Play");
        pauseButton   =new JButton("Pause");
        loopButton    =new JButton("Loop");
        nextButton    =new JButton("Next");
        previousButton=new JButton("Previous");


        playButton.addActionListener(e -> {
            playMusic();
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
        progressBar=new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);


        isPaused =false;
        isLooping=false;

        this.add(playButton);
        this.add(pauseButton);
        this.add(nextButton);
        this.add(previousButton);
        this.add(progressBar);
        this.setLayout(new FlowLayout());
        this.setVisible(true);
    }

    private void playMusic() {
        beginTimer();

        if (clip != null && clip.isRunning()) {
            clip.stop();
        }

        try {
            File file=new File(musics[index] + ".wav");
            AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
            clip=AudioSystem.getClip();
            clip.open(audioStream);
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();
            //System.out.println(TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondLength()));
            //  fill();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void pauseMusic() {
        cancelTimer();
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPaused=true;
            pauseButton.setText("Resume");
        } else if (clip != null && isPaused) {
            clip.start();
            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            isPaused=false;
            pauseButton.setText("Pause");
        }
    }

    private void loopMusic() {
        isLooping=!isLooping;
        if (isLooping) {

            loopButton.setText("Stop Loop");
            if (clip.isRunning()) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                loopButton.setText("Loop");
                if (clip.isRunning()) {
                    clip.loop(0);
                }
            }
        }
    }

    private void nextMusic() {
        if (index < musics.length - 1) {
            index++;
            clip.stop();
            if (running) {
                cancelTimer();
            }
            try {
                File file=new File(musics[index] + ".wav");
                AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
                clip=AudioSystem.getClip();
                clip.open(audioStream);
                if (isLooping) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                playMusic();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                index=0;
                clip.stop();
                if (running) {
                    cancelTimer();
                }
                File file=new File(musics[index] + ".wav");
                AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
                clip=AudioSystem.getClip();
                clip.open(audioStream);
                if (isLooping) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                playMusic();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void previousMusic() {

        if (index > 0) {
            index--;
            clip.stop();
            if (running) {
                cancelTimer();
            }
            try {
                File file=new File(musics[index] + ".wav");
                AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
                clip=AudioSystem.getClip();
                clip.open(audioStream);
                if (isLooping) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                playMusic();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
            try {
                index=musics.length - 1;
                clip.stop();
                if (running) {
                    cancelTimer();
                }
                File file=new File(musics[index] + ".wav");
                AudioInputStream audioStream=AudioSystem.getAudioInputStream(file);
                clip=AudioSystem.getClip();
                clip.open(audioStream);
                if (isLooping) {
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
                playMusic();
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
                System.out.println(current);
                //System.out.println(current/end);
                progressBar.setValue((int) (current));
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
}