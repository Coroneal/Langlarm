package com.example.test.langlarm.alarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Alarm {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    private int hour;
    private int minute;
    private boolean isTurnedOn;

    public Alarm(int hour, int minute, boolean isTurnedOn) {
        this.hour = hour;
        this.minute = minute;
        this.isTurnedOn = isTurnedOn;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public void setTurnedOn(boolean turnedOn) {
        isTurnedOn = turnedOn;
    }

    public long getTimeInMillis(){

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, getHour()); // For 1 PM or 2 PM
        calendar.set(Calendar.MINUTE, getMinute());
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTimeInMillis();
    }

    public String getFormattedTime(){
        return dateFormat.format(getTimeInMillis());
    }

}
