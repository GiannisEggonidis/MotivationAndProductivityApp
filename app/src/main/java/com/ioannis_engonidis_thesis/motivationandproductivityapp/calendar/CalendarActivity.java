package com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar;

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
import com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder.WeeklyReminderActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CalendarActivity extends AppCompatActivity {
    ImageButton addCalendar;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;

    private RecyclerView calendarRecView;
    private CalendarRecViewAdapter adapter = new CalendarRecViewAdapter(this);
    private ArrayList<Calendar> calendars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        initializeViews();
        menuClickFunction();
        loadData();


        adapter.setCalendars(calendars);
        calendarRecView.setLayoutManager(new LinearLayoutManager(this));
        calendarRecView.setAdapter(adapter);

        /** Animated Background Configuration **/
        {
            ConstraintLayout constraintLayout = findViewById(R.id.calendar_layout);
            AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();
        }

        /** Add new repeating panel Button **/
        addCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendars.size() >= 4) {
                    Toast.makeText(CalendarActivity.this, "Calendars Limit Reached", Toast.LENGTH_SHORT).show();
                } else {
                    /** Generating new ID for reminder **/
                    int maxValue = 0;
                    int indexOfMaxValue = 0;
                    if (calendars.size() != 0) {
                        for (int i = 0; i < calendars.size(); i++) {
                            if (calendars.get(i).getCalendarID() > maxValue) {
                                indexOfMaxValue = i;
                            }
                        }
                        maxValue = calendars.get(indexOfMaxValue).getCalendarID() + 1;
                    }

                    ArrayList<CalendarDay> calendarDays = new ArrayList<>();

                    calendars.add(new Calendar(maxValue, 0, "Calendar", calendarDays));
                    saveData();

                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    public void onBackPressed() {
        overridePendingTransition(0, 0);
        this.startActivity(new Intent(CalendarActivity.this, MainActivity.class));
        overridePendingTransition(0, 0);

        return;
    }

    private void initializeViews() {
        addCalendar = findViewById(R.id.addCalendar);
        calendarRecView = findViewById(R.id.calendarRecView);
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
                Intent start = new Intent(CalendarActivity.this, MainActivity.class);
                overridePendingTransition(0, 0);
                startActivity(start);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(CalendarActivity.this, "Repeating Reminder Menu", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                Intent start = new Intent(CalendarActivity.this, WeeklyReminderActivity.class);
                overridePendingTransition(0, 0);
                startActivity(start);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(CalendarActivity.this, "Weekly Reminder Menu", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CalendarActivity.this, "Calendar Activity Tracker", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                Intent refresh = new Intent(CalendarActivity.this, SettingsActivity.class);
                overridePendingTransition(0, 0);
                startActivity(refresh);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(CalendarActivity.this, "Settings Menu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("calendarsSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calendars);
        editor.putString("calendars", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("calendarsSharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("calendars", null);
        Type type = new TypeToken<ArrayList<Calendar>>() {
        }.getType();
        calendars = gson.fromJson(json, type);

        if (calendars == null) {
            calendars = new ArrayList<>();
        }
    }
}