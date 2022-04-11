package com.ioannis_engonidis_thesis.motivationandproductivityapp;

public class NotificationPanel {
    private int id;
    private String notificationName ;

    public NotificationPanel(int id, String notificationName) {
        this.id = id;
        this.notificationName = notificationName;

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

    @Override
    public String toString() {
        return "NotificationPanel{" +
                "id=" + id +
                ", notificationName='" + notificationName + '\'' +
                '}';
    }
}
