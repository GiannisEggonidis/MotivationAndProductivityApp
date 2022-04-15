package com.ioannis_engonidis_thesis.motivationandproductivityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private RecyclerView notificationPanelRecView;
    private RecyclerView.Adapter notificationPanelAdapter;
    private NotificationPanelRecViewAdapter adapter;
    private Spinner hoursSpinner;
    private ImageButton addNotificationPanel;
    private ArrayList<NotificationPanel> notificationPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        notificationPanelRecView = findViewById(R.id.notificationPanelRecView);
        addNotificationPanel = findViewById(R.id.addNotificationPanel);

        loadData();

        addNotificationPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationPanel.add(new NotificationPanel(10, "Notification", false,
                        "1","30", false, false,
                        false, false, false,
                        false, false));
                saveData();
                refreshActivity();
            }
        });

        NotificationPanelRecViewAdapter adapter = new NotificationPanelRecViewAdapter(this);
        adapter.setNotificationPanel(notificationPanel);

        notificationPanelRecView.setLayoutManager(new LinearLayoutManager(this));
        notificationPanelRecView.setAdapter(adapter);

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationPanel);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<NotificationPanel>>() {
        }.getType();
        notificationPanel = gson.fromJson(json, type);

        if (notificationPanel == null) {
            notificationPanel = new ArrayList<>();
        }
    }

    private void refreshActivity() {
        overridePendingTransition(0, 0);
        Intent refresh = new Intent(this, MainActivity.class);
        overridePendingTransition(0, 0);
        startActivity(refresh);//Start the same Activity
        overridePendingTransition(0, 0);
        finish(); //finish Activity.
        overridePendingTransition(0, 0);
    }


}