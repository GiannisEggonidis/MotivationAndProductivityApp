package com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;

public class Calendar {

    private int pickColor,calendarID;
    private String calendarName;
    private ArrayList<CalendarDay> calendarDays;

    public Calendar(int calendarID,int pickColor, String calendarName, ArrayList<CalendarDay> calendarDays) {
        this.pickColor = pickColor;
        this.calendarName = calendarName;
        this.calendarDays = calendarDays;
        this.calendarID = calendarID;
    }

    @Override
    public String toString() {
        return "Calendar{" +
                "pickColor=" + pickColor +
                ", calendarID=" + calendarID +
                ", calendarName='" + calendarName + '\'' +
                ", calendarDays=" + calendarDays +
                '}';
    }

    public int getCalendarID() {
        return calendarID;
    }

    public void setCalendarID(int calendarID) {
        this.calendarID = calendarID;
    }

    public int getPickColor() {
        return pickColor;
    }

    public void setPickColor(int pickColor) {
        this.pickColor = pickColor;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public ArrayList<CalendarDay> getCalendarDays() {
        return calendarDays;
    }

    public void setCalendarDays(ArrayList<CalendarDay> calendarDays) {
        this.calendarDays = calendarDays;
    }
}
