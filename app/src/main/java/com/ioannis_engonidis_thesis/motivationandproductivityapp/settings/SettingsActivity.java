package com.ioannis_engonidis_thesis.motivationandproductivityapp.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder.MainActivity;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder.WeeklyReminderActivity;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class SettingsActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;
    TextView startActiveHours, endActiveHours;
    long startHour, endHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initializeViews();
        menuClickFunction();
        setTimeClickFunction();

        loadData("startHour", startHour);
        loadData("endHour", endHour);

        Toast.makeText(this, "start: "+startHour+"\nend: "+endHour, Toast.LENGTH_SHORT).show();

    }

    private void menuClickFunction() {
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                overridePendingTransition(0, 0);
                Intent refresh = new Intent(SettingsActivity.this, MainActivity.class);
                overridePendingTransition(0, 0);
                startActivity(refresh);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(SettingsActivity.this, "Repeating Reminder Menu", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                Intent refresh = new Intent(SettingsActivity.this, WeeklyReminderActivity.class);
                overridePendingTransition(0, 0);
                startActivity(refresh);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(SettingsActivity.this, "Weekly Reminder Menu", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SettingsActivity.this, "Settings Menu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViews() {
        startActiveHours = findViewById(R.id.startActiveHours);
        endActiveHours = findViewById(R.id.endActiveHours);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item5);
    }

    private void setTimeClickFunction() {
        Calendar subcalendar = Calendar.getInstance();
        subcalendar.set(Calendar.HOUR_OF_DAY, 0);
        subcalendar.set(Calendar.MINUTE, 0);
        subcalendar.set(Calendar.SECOND, 0);
        subcalendar.set(Calendar.MILLISECOND, 0);
        Date subDate = subcalendar.getTime();
        startActiveHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int mins = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.setTimeZone(TimeZone.getDefault());

                        SimpleDateFormat format = new SimpleDateFormat("kk:mm");
                        String time = format.format(calendar.getTime());
                        startActiveHours.setText(time);
                        Date date = calendar.getTime();
                        startHour = date.getTime() - subDate.getTime();
                        saveData(startHour, "startHour");
                    }
                }, hours, mins, true);
                timePickerDialog.show();
            }
        });
        endActiveHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int hours = c.get(Calendar.HOUR_OF_DAY);
                int mins = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(SettingsActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.setTimeZone(TimeZone.getDefault());

                        SimpleDateFormat format = new SimpleDateFormat("kk:mm");
                        String time = format.format(calendar.getTime());
                        endActiveHours.setText(time);
                        Date date = calendar.getTime();
                        endHour = date.getTime() - subDate.getTime();
                        saveData(endHour, "endHour");
                    }
                }, hours, mins, true);
                timePickerDialog.show();
            }
        });
    }

    private void saveData(long hour, String list) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(list,hour);
        editor.apply();
    }

    // TODO: 07/06/2022 loadData returns 0 
    private void loadData(String list, long hour) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        hour = preferences.getLong(list, 1);
    }

}