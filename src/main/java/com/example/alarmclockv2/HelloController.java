package com.example.alarmclockv2;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.File;

public class HelloController {
    int hrs = 0;
    int minutes = 0;
    File file = new File("/C:/Users/Gedeon/Desktop/Locke's Files/AlarmClock v2/target/classes/com/example/alarmclockv2/default_alarm.wav");
    @FXML
    public Label hours;
    @FXML
    public Label mins;
    @FXML
    public TextField hrfield;
    @FXML
    public TextField minfield;
    @FXML
    public Text alarmstatus;
    @FXML
    protected void onSetClicked(){
        try {
            hrs = Integer.parseInt(hrfield.getText());
            minutes = Integer.parseInt(minfield.getText());
            HelloApplication.setAlarm(hrs,minutes,file);
            String formattedminutes = minutes < 10?String.format("0%d",minutes):String.format("%d",minutes);
            alarmstatus.setText(String.format("An alarm is set for %d:%s.",hrs,formattedminutes));
        } catch(NumberFormatException e){
            hrs = 0;
            minutes = 0;
            HelloApplication.setAlarm(hrs,minutes,file);
            alarmstatus.setText("An alarm is set for midnight.");
        }
    }
    @FXML
    protected void onKillClicked(){
        try {
            HelloApplication.killAlarm();
            alarmstatus.setText("You can sleep in =D");
            System.out.print("\nAlarm removed successfully!\n");
        } catch (NullPointerException e){
            alarmstatus.setText("You can sleep in =D");
        }
    }
    @FXML
    protected void onSelectClicked(){
        file = HelloApplication.filechooser();
        System.out.format("\nthe audio file is %s\n",file.toString());
    }
}