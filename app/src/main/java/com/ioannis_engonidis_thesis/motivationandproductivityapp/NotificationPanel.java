package com.ioannis_engonidis_thesis.motivationandproductivityapp;

import android.app.AlarmManager;
import android.widget.SpinnerAdapter;

public class NotificationPanel {
    private int id;
    private String notificationName, hours, minutes;
    boolean notificationSwitch, mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox, thursdayCheckBox,
            fridayCheckBox, saturdayCheckBox, sundayCheckBox;
    private AlarmManager alarmManager;

    public NotificationPanel(int id){
        this.id = id;
    }

    public NotificationPanel(int id, String notificationName, boolean notificationSwitch, String hours, String minutes,
                             boolean mondayCheckBox, boolean tuesdayCheckBox, boolean wednesdayCheckBox,
                             boolean thursdayCheckBox, boolean fridayCheckBox, boolean saturdayCheckBox, boolean sundayCheckBox, AlarmManager alarmManager) {
        this.id = id;
        this.notificationName = notificationName;
        this.notificationSwitch = notificationSwitch;
        this.hours = hours;
        this.minutes = minutes;
        this.mondayCheckBox = mondayCheckBox;
        this.tuesdayCheckBox = tuesdayCheckBox;
        this.wednesdayCheckBox = wednesdayCheckBox;
        this.thursdayCheckBox = thursdayCheckBox;
        this.fridayCheckBox = fridayCheckBox;
        this.saturdayCheckBox = saturdayCheckBox;
        this.sundayCheckBox = sundayCheckBox;
        this.alarmManager = alarmManager;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public boolean isNotificationSwitch() {
        return notificationSwitch;
    }

    public void setNotificationSwitch(boolean notificationSwitch) {
        this.notificationSwitch = notificationSwitch;
    }

    public boolean isMondayCheckBox() {
        return mondayCheckBox;
    }

    public void setMondayCheckBox(boolean mondayCheckBox) {
        this.mondayCheckBox = mondayCheckBox;
    }

    public boolean isTuesdayCheckBox() {
        return tuesdayCheckBox;
    }

    public void setTuesdayCheckBox(boolean tuesdayCheckBox) {
        this.tuesdayCheckBox = tuesdayCheckBox;
    }

    public boolean isWednesdayCheckBox() {
        return wednesdayCheckBox;
    }

    public void setWednesdayCheckBox(boolean wednesdayCheckBox) {
        this.wednesdayCheckBox = wednesdayCheckBox;
    }

    public boolean isThursdayCheckBox() {
        return thursdayCheckBox;
    }

    public void setThursdayCheckBox(boolean thursdayCheckBox) {
        this.thursdayCheckBox = thursdayCheckBox;
    }

    public boolean isFridayCheckBox() {
        return fridayCheckBox;
    }

    public void setFridayCheckBox(boolean fridayCheckBox) {
        this.fridayCheckBox = fridayCheckBox;
    }

    public boolean isSaturdayCheckBox() {
        return saturdayCheckBox;
    }

    public void setSaturdayCheckBox(boolean saturdayCheckBox) {
        this.saturdayCheckBox = saturdayCheckBox;
    }

    public boolean isSundayCheckBox() {
        return sundayCheckBox;
    }

    public void setSundayCheckBox(boolean sundayCheckBox) {
        this.sundayCheckBox = sundayCheckBox;
    }

    public AlarmManager getAlarmManager() {
        return alarmManager;
    }

    public void setAlarmManager(AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
    }

    @Override
    public String toString() {
        return "NotificationPanel{" +
                "id=" + id +
                ", hours=" + hours +
                ", minutes=" + minutes +
                ", notificationName='" + notificationName + '\'' +
                ", notificationSwitch=" + notificationSwitch +
                ", mondayCheckBox=" + mondayCheckBox +
                ", tuesdayCheckBox=" + tuesdayCheckBox +
                ", wednesdayCheckBox=" + wednesdayCheckBox +
                ", thursdayCheckBox=" + thursdayCheckBox +
                ", fridayCheckBox=" + fridayCheckBox +
                ", saturdayCheckBox=" + saturdayCheckBox +
                ", sundayCheckBox=" + sundayCheckBox +
                '}';
    }

}
