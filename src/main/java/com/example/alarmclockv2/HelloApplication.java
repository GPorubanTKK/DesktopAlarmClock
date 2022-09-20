package com.example.alarmclockv2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

public class HelloApplication extends Application {
    static myTimer alarmThread = null;
    public static Stage mainstage = null;
    @Override
    public void start(Stage stage) throws IOException {
        mainstage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 400);
        stage.setTitle("Alarm Clock");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
    public static void setAlarm(int h, int m, File file){
        String time = LocalTime.now().toString().substring(0,LocalTime.now().toString().indexOf("."));
        int hours = Integer.parseInt(time.substring(0,2));
        int mins = Integer.parseInt(time.substring(3,5));
        System.out.format("the alarm will go off at %d hrs and %d mins.\nthe time is %s.\nthe hr count is %d.\nthe min count is %d.\naudio file %s",h,m,time,hours,mins,"/C:/Users/Gedeon/Desktop/Locke's Files/AlarmClock v2/target/classes/com/example/alarmclockv2/default_alarm.wav");
        System.out.println();
        alarmThread = new myTimer(h,m,file);
        alarmThread.start();
        System.out.format("\nAlarm object: %s \n",alarmThread.toString());
    }
    public static void killAlarm(){
        alarmThread.isActive = false;
        alarmThread = null;
    }
    public static File filechooser(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open an audio file");
        File returnfile = chooser.showOpenDialog(mainstage);
        return returnfile;
    }
}
class myTimer extends Thread {
    private int hrs;
    private int mins;
    private File file;
    public boolean isActive = true;
    public myTimer(int hrs, int mins, File file){
        this.hrs = hrs;
        this.mins = mins;
        this.file = file;
    }
    public void run() {
        while (isActive) {
            int truehrs = Integer.parseInt(LocalTime.now().toString().substring(0, 2));
            int truemins = Integer.parseInt(LocalTime.now().toString().substring(3, 5));
            if ((hrs == truehrs) && (mins == truemins)) {
                try {
                    Clip audioclip = AudioSystem.getClip();
                    audioclip.open(AudioSystem.getAudioInputStream(file));
                    audioclip.start();
                } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.print("terminated.");
                return;
            }
        }
        System.out.print("terminated.");
    }
}