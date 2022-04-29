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
import android.view.animation.OvershootInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3,floatingActionButton4,floatingActionButton5;

    private RecyclerView notificationPanelRecView;
    private RecyclerView.Adapter notificationPanelAdapter;
    private NotificationPanelRecViewAdapter adapter;
    private ImageButton addNotificationPanel;

    private ArrayList<NotificationPanel> notificationPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        notificationPanelRecView = findViewById(R.id.notificationPanelRecView);
        addNotificationPanel = findViewById(R.id.addNotificationPanel);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item5);

        loadData();

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Toast.makeText(MainActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();

            }
        });

        addNotificationPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificationPanel.add(new NotificationPanel(10, "Notification", false,
                        "1","30", false, false,
                        false, false, false,
                        false, false));
                saveData();
                Toast.makeText(MainActivity.this, "Total Panels : "+notificationPanel.size(), Toast.LENGTH_SHORT).show();
                refreshActivity();
            }
        });

        NotificationPanelRecViewAdapter adapter = new NotificationPanelRecViewAdapter(this);
        adapter.setNotificationPanel(notificationPanel);

        notificationPanelRecView.setLayoutManager(new LinearLayoutManager(this));
        notificationPanelRecView.setAdapter(adapter);

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("repeatingSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationPanel);
        editor.putString("task list", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("repeatingSharedPreferences", MODE_PRIVATE);
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