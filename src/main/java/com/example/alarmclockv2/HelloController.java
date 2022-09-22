package com.example.alarmclockv2;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class HelloController {
    int hrs = 0;
    int minutes = 0;
    ClassLoader loader = AlarmClock.class.getClassLoader();
    BufferedInputStream file = (BufferedInputStream) loader.getResourceAsStream("default.wav");
    @FXML
    public TextField hrfield;
    @FXML
    public TextField minfield;
    @FXML
    public Text alarmstatus;
    @FXML
    public Text time;
    @FXML
    protected void onSetClicked(){
        try {
            hrs = Integer.parseInt(hrfield.getText());
            minutes = Integer.parseInt(minfield.getText());
            AlarmClock.setAlarm(hrs,minutes,file,alarmstatus);
            alarmstatus.setText(String.format("An alarm is set for %d:%s.",hrs,minutes < 10?String.format("0%d",minutes):String.format("%d",minutes)));
        } catch(NumberFormatException e) {
            hrs = 0;
            minutes = 0;
            AlarmClock.setAlarm(hrs, minutes, file, alarmstatus);
            alarmstatus.setText("An alarm is set for midnight.");
        }
    }
    @FXML
    protected void onKillClicked(){
        try {
            AlarmClock.killAlarm();
            alarmstatus.setText("You can sleep in =D");
        } catch (NullPointerException e){
            alarmstatus.setText("You can sleep in =D");
        }
    }
    @FXML
    protected void onSelectClicked() throws FileNotFoundException {
        try {
            file = new BufferedInputStream(new FileInputStream(AlarmClock.filechooser()));
        } catch (FileNotFoundException e){
            file = (BufferedInputStream) loader.getResourceAsStream("default.wav");
        }
    }
}