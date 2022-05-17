package com.ioannis_engonidis_thesis.motivationandproductivityapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificationPanelReceiver extends BroadcastReceiver {
    private String title = "test", channelID = "1";
    private int notificationID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        Notification notification = new NotificationCompat.Builder(context, "channelID")
                .setSmallIcon(R.drawable.ic_add_notification)
                .setContentTitle(intent.getStringExtra("title"))
                .setContentText("Test")
                .build();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(intent.getIntExtra("notificationID", notificationID), notification);
    }
}
