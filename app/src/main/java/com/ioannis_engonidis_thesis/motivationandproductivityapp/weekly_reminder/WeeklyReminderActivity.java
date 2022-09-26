package com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.HideAddButton;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar.CalendarActivity;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder.MainActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class WeeklyReminderActivity extends AppCompatActivity implements HideAddButton {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;

    private RecyclerView weeklyReminderPanelRecView;
    private WeeklyReminderRecViewAdapter adapter = new WeeklyReminderRecViewAdapter(this);
    private ImageButton addWeeklyReminderPanel, languagesButton, enButton, grButton;
    private Dialog languageDialog;

    private ArrayList<WeeklyReminder> weeklyReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
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

        /** Language Button  **/
        {
            languagesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    materialDesignFAM.close(true);
                    languageAlertDialog();
                }
            });
        }

        /** Add new Weekly panel Button **/
        {
            addWeeklyReminderPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    materialDesignFAM.close(true);
                    if (weeklyReminder.size() >= 5) {
                        Toast.makeText(WeeklyReminderActivity.this, getString(R.string.maxWeekly), Toast.LENGTH_SHORT).show();
                    } else {
                        /** Generating new ID for reminder **/
                        int maxValue = 100;
                        int indexOfMaxValue = 0;
                        if (weeklyReminder.size() != 0) {
                            for (int i = 0; i < weeklyReminder.size(); i++) {
                                if (weeklyReminder.get(i).getWeeklyReminderId() > maxValue) {
                                    indexOfMaxValue = i;
                                }
                            }
                            maxValue = weeklyReminder.get(indexOfMaxValue).getWeeklyReminderId() + 1;
                        }


                        weeklyReminder.add(new WeeklyReminder(false, false, false, false
                                , false, false, false, false, getString(R.string.weeklyReminderName)
                                , "17:00", maxValue, 61200000));
                        saveData();
                        adapter.notifyDataSetChanged();
                        if (weeklyReminder.size() >= 5) {
                            Animation fadeOut = AnimationUtils.loadAnimation(WeeklyReminderActivity.this, R.anim.fade_anim);
                            addWeeklyReminderPanel.setVisibility(View.INVISIBLE);
                            addWeeklyReminderPanel.startAnimation(fadeOut);
                        }
                    }
                }
            });
            if (weeklyReminder.size() >= 5){
                addWeeklyReminderPanel.setVisibility(View.INVISIBLE);
            }else addWeeklyReminderPanel.setVisibility(View.VISIBLE);

        }
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

    private void createNotificationChannel(String channelID, String channelName) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String desc = "No description";
            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(desc);
            NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

    }

    private void initializeViews() {
        weeklyReminderPanelRecView = findViewById(R.id.weeklyReminderPanelRecView);
        addWeeklyReminderPanel = findViewById(R.id.addWeeklyReminderPanel);
        languagesButton = findViewById(R.id.languagesButton);
        enButton = findViewById(R.id.enButton);
        grButton = findViewById(R.id.grButton);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);
    }

    private void menuClickFunction() {
        materialDesignFAM.setClosedOnTouchOutside(true);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                Intent start = new Intent(WeeklyReminderActivity.this, MainActivity.class);
                overridePendingTransition(0, 0);
                startActivity(start);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(WeeklyReminderActivity.this, getString(R.string.repeating_menu_toast), Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(WeeklyReminderActivity.this, getString(R.string.weekly_menu_toast), Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0, 0);
                Intent start = new Intent(WeeklyReminderActivity.this, CalendarActivity.class);
                overridePendingTransition(0, 0);
                startActivity(start);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(WeeklyReminderActivity.this, getString(R.string.calendar_menu_toast), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onBackPressed() {
        overridePendingTransition(0, 0);
        this.startActivity(new Intent(WeeklyReminderActivity.this, MainActivity.class));
        overridePendingTransition(0, 0);

        return;
    }

    private void languageAlertDialog() {

        languageDialog = new Dialog(WeeklyReminderActivity.this, R.style.AlertDialog);
        languageDialog.setContentView(R.layout.language_dialog);
        languageDialog.setTitle("Language");

        enButton = (ImageButton) languageDialog.findViewById(R.id.enButton);
        grButton = (ImageButton) languageDialog.findViewById(R.id.grButton);

        enButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WeeklyReminderActivity.this, "English", Toast.LENGTH_SHORT).show();
                setLocale("en");
                WeeklyReminderActivity.this.recreate();
                languageDialog.cancel();
            }
        });

        grButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WeeklyReminderActivity.this, "Ελληνικά", Toast.LENGTH_SHORT).show();
                setLocale("el");
                WeeklyReminderActivity.this.recreate();
                languageDialog.cancel();
            }
        });

        languageDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        //save data to shared preferences
        SharedPreferences.Editor editor = getSharedPreferences("Language", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences preferences = getSharedPreferences("Language", Activity.MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "");
        setLocale(language);
    }

    @Override
    public void hideButton() {
        addWeeklyReminderPanel.setVisibility(View.VISIBLE);
    }
}