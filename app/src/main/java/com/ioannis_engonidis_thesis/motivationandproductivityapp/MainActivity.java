package com.ioannis_engonidis_thesis.motivationandproductivityapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        notificationPanelRecView = findViewById(R.id.notificationPanelRecView);
        addNotificationPanel = findViewById(R.id.addNotificationPanel);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item5);

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

        addNotificationPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notificationPanel.size() >= 10) {
                    Toast.makeText(MainActivity.this, "Reminder Limit Reached", Toast.LENGTH_SHORT).show();
                } else {
                    notificationPanel.add(new NotificationPanel(10, "Reminder", false,
                            "1", "30", false, false,
                            false, false, false,
                            false, false));
                    saveData();
                    Toast.makeText(MainActivity.this, "Created New Reminder\n" + "Total Reminders : " + notificationPanel.size(), Toast.LENGTH_SHORT).show();
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