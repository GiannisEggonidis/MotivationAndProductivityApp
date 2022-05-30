package com.ioannis_engonidis_thesis.motivationandproductivityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.content.Context;
import android.app.AlarmManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;

    private RecyclerView notificationPanelRecView;
    private NotificationPanelRecViewAdapter adapter = new NotificationPanelRecViewAdapter(this);
    private ImageButton addNotificationPanel;

    private ArrayList<NotificationPanel> notificationPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        /** Animated Background Configuration **/
        {
            ConstraintLayout constraintLayout = findViewById(R.id.main_layout);
            AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();
        }

        initializeViews();
        loadData();

        adapter.setNotificationPanel(notificationPanel);

        notificationPanelRecView.setLayoutManager(new LinearLayoutManager(this));
        notificationPanelRecView.setAdapter(adapter);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                Toast.makeText(MainActivity.this, "Menu clicked", Toast.LENGTH_SHORT).show();

            }
        });

        /** Add new panel Button **/
        addNotificationPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationPanel.size() >= 10) {
                    Toast.makeText(MainActivity.this, "Reminder Limit Reached", Toast.LENGTH_SHORT).show();
                } else {
                    /** Generating new ID for reminder **/
                    int maxValue = 0;
                    int indexOfMaxValue = 0;
                    if (notificationPanel.size() != 0) {
                        for (int i = 0; i < notificationPanel.size(); i++) {
                            if (notificationPanel.get(i).getId() > maxValue) {
                                indexOfMaxValue = i;
                            }
                        }
                        maxValue = notificationPanel.get(indexOfMaxValue).getId() + 1;
                    }
                    AlarmManager manager = (AlarmManager)MainActivity.this.getSystemService(ALARM_SERVICE);
                    Intent intent = new Intent(MainActivity.this,NotificationPanelReceiver.class);

                    notificationPanel.add(new NotificationPanel(maxValue, "Reminder", false,
                            "0", "30", false, false,
                            false, false, false,
                                false, false));
                    saveData();
//                    Toast.makeText(MainActivity.this, "Created New Reminder\n" + "Total Reminders : " + notificationPanel.size(), Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("repeatingSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationPanel);
        editor.putString("repeating_reminder", json);
        editor.apply();

    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("repeatingSharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("repeating_reminder", null);
        Type type = new TypeToken<ArrayList<NotificationPanel>>() {}.getType();
        notificationPanel = gson.fromJson(json,type);
//        notificationPanel = gson.fromJson(json, (Type) NotificationPanel.class);

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

    private void initializeViews(){
        notificationPanelRecView = findViewById(R.id.notificationPanelRecView);
        addNotificationPanel = findViewById(R.id.addNotificationPanel);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item5);
    }

}