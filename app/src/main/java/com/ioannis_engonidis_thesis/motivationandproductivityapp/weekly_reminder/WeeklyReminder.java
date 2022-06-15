package com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder;

public class WeeklyReminder {
    boolean weeklyReminderSwitch, weeklyReminderMondayCheckBox, weeklyReminderTuesdayCheckBox, weeklyReminderWednesdayCheckBox, weeklyReminderThursdayCheckBox,
            weeklyReminderFridayCheckBox, weeklyReminderSaturdayCheckBox, weeklyReminderSundayCheckBox;
    private String weeklyReminderName,weeklyReminderHour;
    private int weeklyReminderId;
    private long weeklyReminderHourMs;

    public WeeklyReminder(boolean weeklyReminderSwitch
            , boolean weeklyReminderMondayCheckBox
            , boolean weeklyReminderTuesdayCheckBox
            , boolean weeklyReminderWednesdayCheckBox
            , boolean weeklyReminderThursdayCheckBox
            , boolean weeklyReminderFridayCheckBox
            , boolean weeklyReminderSaturdayCheckBox
            , boolean weeklyReminderSundayCheckBox
            , String weeklyReminderName, String weeklyReminderHour
            , int weeklyReminderId
            ,long weeklyReminderHourMs) {
        this.weeklyReminderSwitch = weeklyReminderSwitch;
        this.weeklyReminderMondayCheckBox = weeklyReminderMondayCheckBox;
        this.weeklyReminderTuesdayCheckBox = weeklyReminderTuesdayCheckBox;
        this.weeklyReminderWednesdayCheckBox = weeklyReminderWednesdayCheckBox;
        this.weeklyReminderThursdayCheckBox = weeklyReminderThursdayCheckBox;
        this.weeklyReminderFridayCheckBox = weeklyReminderFridayCheckBox;
        this.weeklyReminderSaturdayCheckBox = weeklyReminderSaturdayCheckBox;
        this.weeklyReminderSundayCheckBox = weeklyReminderSundayCheckBox;
        this.weeklyReminderName = weeklyReminderName;
        this.weeklyReminderHour = weeklyReminderHour;
        this.weeklyReminderId = weeklyReminderId;
        this.weeklyReminderHourMs = weeklyReminderHourMs;
    }

    @Override
    public String toString() {
        return "WeeklyReminder{" +
                "weeklyReminderSwitch=" + weeklyReminderSwitch +
                ", weeklyReminderMondayCheckBox=" + weeklyReminderMondayCheckBox +
                ", weeklyReminderTuesdayCheckBox=" + weeklyReminderTuesdayCheckBox +
                ", weeklyReminderWednesdayCheckBox=" + weeklyReminderWednesdayCheckBox +
                ", weeklyReminderThursdayCheckBox=" + weeklyReminderThursdayCheckBox +
                ", weeklyReminderFridayCheckBox=" + weeklyReminderFridayCheckBox +
                ", weeklyReminderSaturdayCheckBox=" + weeklyReminderSaturdayCheckBox +
                ", weeklyReminderSundayCheckBox=" + weeklyReminderSundayCheckBox +
                ", weeklyReminderName='" + weeklyReminderName + '\'' +
                ", weeklyReminderHour='" + weeklyReminderHour + '\'' +
                ", weeklyReminderId=" + weeklyReminderId +
                '}';
    }

    public boolean isWeeklyReminderSwitch() {
        return weeklyReminderSwitch;
    }

    public void setWeeklyReminderSwitch(boolean weeklyReminderSwitch) {
        this.weeklyReminderSwitch = weeklyReminderSwitch;
    }

    public boolean isWeeklyReminderMondayCheckBox() {
        return weeklyReminderMondayCheckBox;
    }

    public void setWeeklyReminderMondayCheckBox(boolean weeklyReminderMondayCheckBox) {
        this.weeklyReminderMondayCheckBox = weeklyReminderMondayCheckBox;
    }

    public boolean isWeeklyReminderTuesdayCheckBox() {
        return weeklyReminderTuesdayCheckBox;
    }

    public void setWeeklyReminderTuesdayCheckBox(boolean weeklyReminderTuesdayCheckBox) {
        this.weeklyReminderTuesdayCheckBox = weeklyReminderTuesdayCheckBox;
    }

    public boolean isWeeklyReminderWednesdayCheckBox() {
        return weeklyReminderWednesdayCheckBox;
    }

    public void setWeeklyReminderWednesdayCheckBox(boolean weeklyReminderWednesdayCheckBox) {
        this.weeklyReminderWednesdayCheckBox = weeklyReminderWednesdayCheckBox;
    }

    public boolean isWeeklyReminderThursdayCheckBox() {
        return weeklyReminderThursdayCheckBox;
    }

    public void setWeeklyReminderThursdayCheckBox(boolean weeklyReminderThursdayCheckBox) {
        this.weeklyReminderThursdayCheckBox = weeklyReminderThursdayCheckBox;
    }

    public boolean isWeeklyReminderFridayCheckBox() {
        return weeklyReminderFridayCheckBox;
    }

    public void setWeeklyReminderFridayCheckBox(boolean weeklyReminderFridayCheckBox) {
        this.weeklyReminderFridayCheckBox = weeklyReminderFridayCheckBox;
    }

    public boolean isWeeklyReminderSaturdayCheckBox() {
        return weeklyReminderSaturdayCheckBox;
    }

    public void setWeeklyReminderSaturdayCheckBox(boolean weeklyReminderSaturdayCheckBox) {
        this.weeklyReminderSaturdayCheckBox = weeklyReminderSaturdayCheckBox;
    }

    public boolean isWeeklyReminderSundayCheckBox() {
        return weeklyReminderSundayCheckBox;
    }

    public void setWeeklyReminderSundayCheckBox(boolean weeklyReminderSundayCheckBox) {
        this.weeklyReminderSundayCheckBox = weeklyReminderSundayCheckBox;
    }

    public String getWeeklyReminderName() {
        return weeklyReminderName;
    }

    public void setWeeklyReminderName(String weeklyReminderName) {
        this.weeklyReminderName = weeklyReminderName;
    }

    public String getWeeklyReminderHour() {
        return weeklyReminderHour;
    }

    public void setWeeklyReminderHour(String weeklyReminderHour) {
        this.weeklyReminderHour = weeklyReminderHour;
    }

    public int getWeeklyReminderId() {
        return weeklyReminderId;
    }

    public void setWeeklyReminderId(int weeklyReminderId) {
        this.weeklyReminderId = weeklyReminderId;
    }

    public long getWeeklyReminderHourMs() {
        return weeklyReminderHourMs;
    }

    public void setWeeklyReminderHourMs(long weeklyReminderHourMs) {
        this.weeklyReminderHourMs = weeklyReminderHourMs;
    }
}
