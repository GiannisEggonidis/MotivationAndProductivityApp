package com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder;

import android.app.AlarmManager;
import android.content.Intent;
import android.widget.SpinnerAdapter;

public class NotificationPanel {
    private int id, pickInterval;
    private String notificationName, fromHoursTxt, untilHoursTxt;
    private long fromHours,untilHours;
    boolean notificationSwitch, mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox, thursdayCheckBox,
            fridayCheckBox, saturdayCheckBox, sundayCheckBox, scheduleSwitch;

    public NotificationPanel(int id,int pickInterval, String notificationName, boolean notificationSwitch,
                             boolean mondayCheckBox, boolean tuesdayCheckBox, boolean wednesdayCheckBox,
                             boolean thursdayCheckBox, boolean fridayCheckBox, boolean saturdayCheckBox, boolean sundayCheckBox, boolean scheduleSwitch,
                             long fromHours,String fromHoursTxt, long untilHours,String untilHoursTxt) {

        this.id = id;
        this.pickInterval = pickInterval;
        this.notificationName = notificationName;
        this.notificationSwitch = notificationSwitch;
        this.mondayCheckBox = mondayCheckBox;
        this.tuesdayCheckBox = tuesdayCheckBox;
        this.wednesdayCheckBox = wednesdayCheckBox;
        this.thursdayCheckBox = thursdayCheckBox;
        this.fridayCheckBox = fridayCheckBox;
        this.saturdayCheckBox = saturdayCheckBox;
        this.sundayCheckBox = sundayCheckBox;
        this.scheduleSwitch = scheduleSwitch;
        this.fromHours = fromHours;
        this.fromHoursTxt = fromHoursTxt;
        this.untilHours = untilHours;
        this.untilHoursTxt = untilHoursTxt;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPickInterval() {
        return pickInterval;
    }

    public void setPickInterval(int pickInterval) {
        this.pickInterval = pickInterval;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
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

    public long getFromHours() {
        return fromHours;
    }

    public void setFromHours(long fromHours) {
        this.fromHours = fromHours;
    }

    public String getFromHoursTxt() {
        return fromHoursTxt;
    }

    public void setFromHoursTxt(String fromHoursTxt) {
        this.fromHoursTxt = fromHoursTxt;
    }

    public long getUntilHours() {
        return untilHours;
    }

    public void setUntilHours(long untilHours) {
        this.untilHours = untilHours;
    }

    public String getUntilHoursTxt() {
        return untilHoursTxt;
    }

    public void setUntilHoursTxt(String untilHoursTxt) {
        this.untilHoursTxt = untilHoursTxt;
    }

    public boolean isScheduleSwitch() {
        return scheduleSwitch;
    }

    public void setScheduleSwitch(boolean scheduleSwitch) {
        this.scheduleSwitch = scheduleSwitch;
    }

    @Override
    public String toString() {
        return "NotificationPanel{" +
                "id=" + id +
                ", pickInterval=" + pickInterval +
                ", notificationName='" + notificationName + '\'' +
                ", fromHoursTxt='" + fromHoursTxt + '\'' +
                ", untilHoursTxt='" + untilHoursTxt + '\'' +
                ", fromHours=" + fromHours +
                ", untilHours=" + untilHours +
                ", notificationSwitch=" + notificationSwitch +
                ", mondayCheckBox=" + mondayCheckBox +
                ", tuesdayCheckBox=" + tuesdayCheckBox +
                ", wednesdayCheckBox=" + wednesdayCheckBox +
                ", thursdayCheckBox=" + thursdayCheckBox +
                ", fridayCheckBox=" + fridayCheckBox +
                ", saturdayCheckBox=" + saturdayCheckBox +
                ", sundayCheckBox=" + sundayCheckBox +
                ", scheduleSwitch=" + scheduleSwitch +
                '}';
    }
}
