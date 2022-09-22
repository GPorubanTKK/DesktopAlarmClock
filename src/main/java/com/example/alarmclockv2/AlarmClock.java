package com.example.alarmclockv2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.sound.sampled.*;
import java.io.*;
import java.time.LocalTime;

public class AlarmClock extends Application {
    static myTimer alarmThread = null;
    public static Stage mainstage = null;
    @Override
    public void start(Stage stage) throws IOException{
        mainstage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(AlarmClock.class.getResource("MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 400);
        stage.setTitle("Alarm Clock");
        stage.setScene(scene);
        stage.setMaxHeight(600);
        stage.setMaxWidth(720);
        stage.setMaximized(true);
        screenClock clock = new screenClock((Text) scene.getRoot().lookup("#time"));
        clock.setDaemon(true);
        clock.start();
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
    public static void setAlarm(int h, int m, InputStream file, Text statfield){
        alarmThread = new myTimer(h,m,file,statfield);
        alarmThread.setDaemon(true);
        alarmThread.start();
        System.out.format("\nAlarm object: %s \n",alarmThread.toString());
    }
    public static void killAlarm(){
        alarmThread.isActive = false;
        alarmThread.stopMusic(alarmThread.audioclip);
        alarmThread = null;
    }
    public static File filechooser(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open an audio file");
        return chooser.showOpenDialog(mainstage);
    }
}
class myTimer extends Thread {
    private int hrs;
    private int mins;
    private InputStream file;
    private Text txt;
    public Clip audioclip = null;
    public boolean isActive = true;
    public myTimer(int hrs, int mins, InputStream file,Text txt){
        this.hrs = hrs;
        this.mins = mins;
        this.file = file;
        this.txt = txt;
    }
    public void run() {
        audioclip = null;
        while (isActive) {
            int truehrs = LocalTime.now().getHour();
            int truemins = LocalTime.now().getMinute();
            if ((hrs == truehrs) && (mins == truemins)) {
                try {
                    txt.setText("Alarm Status: ALARM ACTIVE");
                    audioclip = AudioSystem.getClip();
                    audioclip.open(AudioSystem.getAudioInputStream(file));
                    startMusic(audioclip);
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            }
        }
    }
    public void stopMusic(Clip clip){
        clip.stop();
        clip.flush();
        clip.close();
    }
    public void startMusic(Clip clip){
        clip.start();
    }
}

class screenClock extends Thread{
    private Text field;
    private int hrs;
    private String mins;
    private String secs;
    public screenClock(Text field){
        this.field = field;
    }
    public void run(){
        while(true){
            hrs = LocalTime.now().getHour();
            mins = LocalTime.now().getMinute() < 10?String.format("0%d",LocalTime.now().getMinute()):String.format("%d",LocalTime.now().getMinute());
            secs = LocalTime.now().getSecond() < 10?String.format("0%d",LocalTime.now().getSecond()):String.format("%d",LocalTime.now().getSecond());
            field.setText(String.format("Time: %d:%s:%s",hrs,mins,secs));
        }
    }
}