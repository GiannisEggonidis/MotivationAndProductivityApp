package com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;

public class WeeklyReminderReceiver extends BroadcastReceiver {
    // TODO: 09/06/2022 Weekly Reminder Broadcast Receiver

    private String channelID = "1";
    private int weeklyReminderID = 1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        channelID = String.valueOf(intent.getIntExtra("weeklyReminderID", 1));
        NotificationChannel channel = manager.getNotificationChannel(channelID);
        String channelName = (String) channel.getName();

        Notification notification = new NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_repeating_reminder_notification_icon)
                .setContentTitle(channelName)
                .setContentText("Weekly Reminder")
                .build();

        manager.notify(intent.getIntExtra("weeklyReminderID", weeklyReminderID), notification);
    }
}
