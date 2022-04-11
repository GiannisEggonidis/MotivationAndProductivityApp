package com.ioannis_engonidis_thesis.motivationandproductivityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView notificationPanelRecView;
    private NotificationPanelRecViewAdapter adapter;
    private Spinner hoursSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        notificationPanelRecView = findViewById(R.id.notificationPanelRecView);

        ArrayList<NotificationPanel> notificationPanel = new ArrayList<>();
        notificationPanel.add(new NotificationPanel(1, "Pills"));
        notificationPanel.add(new NotificationPanel(2, "Drink Water"));
        notificationPanel.add(new NotificationPanel(3, "Drink Vitamins"));
        notificationPanel.add(new NotificationPanel(4, "Check Email"));

        NotificationPanelRecViewAdapter adapter = new NotificationPanelRecViewAdapter(this);
        adapter.setNotificationPanel(notificationPanel);

        notificationPanelRecView.setLayoutManager(new LinearLayoutManager(this));
        notificationPanelRecView.setAdapter(adapter);

    }


}