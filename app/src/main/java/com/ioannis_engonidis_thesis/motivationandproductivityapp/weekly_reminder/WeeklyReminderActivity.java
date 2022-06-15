package com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder.MainActivity;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder.NotificationPanel;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder.NotificationPanelReceiver;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.settings.SettingsActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class WeeklyReminderActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;

    private RecyclerView weeklyReminderPanelRecView;
    private WeeklyReminderRecViewAdapter adapter = new WeeklyReminderRecViewAdapter(this);
    private ImageButton addWeeklyReminderPanel;

    private ArrayList<WeeklyReminder> weeklyReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_reminder);
        /** Animated Background Configuration **/
        {
            ConstraintLayout constraintLayout = findViewById(R.id.weeklyReminder_layout);
            AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();
        }
        initializeViews();
        loadData();
        menuClickFunction();
        adapter.setWeeklyReminder(weeklyReminder);
        weeklyReminderPanelRecView.setLayoutManager(new LinearLayoutManager(this));
        weeklyReminderPanelRecView.setAdapter(adapter);

        /** Add new Weekly panel Button **/
        addWeeklyReminderPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (weeklyReminder.size() >= 10) {
                    Toast.makeText(WeeklyReminderActivity.this, "Reminder Limit Reached", Toast.LENGTH_SHORT).show();
                } else {
                    /** Generating new ID for reminder **/
                    int maxValue = 0;
                    int indexOfMaxValue = 0;
                    if (weeklyReminder.size() != 0) {
                        for (int i = 0; i < weeklyReminder.size(); i++) {
                            if (weeklyReminder.get(i).getWeeklyReminderId() > maxValue) {
                                indexOfMaxValue = i;
                            }
                        }
                        maxValue = weeklyReminder.get(indexOfMaxValue).getWeeklyReminderId() + 1;
                    }


                    weeklyReminder.add(new WeeklyReminder(false,false,false,false
                    ,false,false,false,false,"Reminder"
                    ,"17:00",maxValue,61200000));
                    saveData();
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("weeklySharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(weeklyReminder);
        editor.putString("weekly_reminder", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("weeklySharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("weekly_reminder", null);
        Type type = new TypeToken<ArrayList<WeeklyReminder>>() {
        }.getType();
        weeklyReminder = gson.fromJson(json, type);

        if (weeklyReminder == null) {
            weeklyReminder = new ArrayList<>();
        }
    }

    private void initializeViews() {
        weeklyReminderPanelRecView = findViewById(R.id.weeklyReminderPanelRecView);
        addWeeklyReminderPanel = findViewById(R.id.addWeeklyReminderPanel);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item5);
    }

    private void menuClickFunction() {
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                Intent start = new Intent(WeeklyReminderActivity.this, MainActivity.class);
                overridePendingTransition(0, 0);
                startActivity(start);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(WeeklyReminderActivity.this, "Repeating Reminder Menu", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WeeklyReminderActivity.this, "Weekly Reminder Menu", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                Intent refresh = new Intent(WeeklyReminderActivity.this, SettingsActivity.class);
                overridePendingTransition(0, 0);
                startActivity(refresh);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(WeeklyReminderActivity.this, "Settings Menu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onBackPressed() {
        overridePendingTransition(0, 0);
        this.startActivity(new Intent(WeeklyReminderActivity.this, MainActivity.class));
        overridePendingTransition(0, 0);

        return;
    }
}