package com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar.CalendarActivity;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.settings.SettingsActivity;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder.WeeklyReminderActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout mainLayout;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;

    private RecyclerView notificationPanelRecView;
    private NotificationPanelRecViewAdapter adapter = new NotificationPanelRecViewAdapter(this);
    private ImageButton addNotificationPanel, languagesButton, enButton, grButton;
    private Dialog languageDialog;


    private ArrayList<NotificationPanel> notificationPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initializeViews();
        loadData();
        menuClickFunction();

        Toast toast = new Toast(getApplicationContext());


        adapter.setNotificationPanel(notificationPanel);
        notificationPanelRecView.setLayoutManager(new LinearLayoutManager(this));
        notificationPanelRecView.setAdapter(adapter);

        /** Animated Background Configuration **/
        {
            ConstraintLayout constraintLayout = findViewById(R.id.main_layout);
            AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
            animationDrawable.setEnterFadeDuration(2000);
            animationDrawable.setExitFadeDuration(4000);
            animationDrawable.start();
        }

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

        /** Add new repeating panel Button **/
        {
            addNotificationPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (notificationPanel.size() >= 2) {
                        Toast.makeText(MainActivity.this, getString(R.string.maxRepeating), Toast.LENGTH_SHORT).show();
                    } else {
                        /** Generating new ID for reminder **/
                        int maxValue = 1;
                        int indexOfMaxValue = 0;
                        if (notificationPanel.size() != 0) {
                            for (int i = 0; i < notificationPanel.size(); i++) {
                                if (notificationPanel.get(i).getId() > maxValue) {
                                    indexOfMaxValue = i;
                                }
                            }
                            maxValue = notificationPanel.get(indexOfMaxValue).getId() + 1;
                        }
                        AlarmManager manager = (AlarmManager) MainActivity.this.getSystemService(ALARM_SERVICE);
                        Intent intent = new Intent(MainActivity.this, NotificationPanelReceiver.class);

                        notificationPanel.add(new NotificationPanel(maxValue, 0, getString(R.string.notificationName), false,
                                false, false,
                                false, false, false,
                                false, false, false, 28800000, "08:00", 72000000, "20:00"));
                        saveData();
                        if (notificationPanel.size()>=2){
                            addNotificationPanel.setVisibility(View.INVISIBLE);
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            });

        }
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
        Type type = new TypeToken<ArrayList<NotificationPanel>>() {
        }.getType();
        notificationPanel = gson.fromJson(json, type);
//        notificationPanel = gson.fromJson(json, (Type) NotificationPanel.class);

        if (notificationPanel == null) {
            notificationPanel = new ArrayList<>();
        }
    }

    private void createNotificationChannel(String channelID, String channelName) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String desc = "No description";
            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel channel = new NotificationChannel(channelID, channelName, importance);
            channel.setDescription(desc);
            NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

    }

    private void menuClickFunction() {

        materialDesignFAM.setClosedOnTouchOutside(true);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, getString(R.string.repeating_menu_toast), Toast.LENGTH_SHORT).show();

            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                Intent refresh = new Intent(MainActivity.this, WeeklyReminderActivity.class);
                overridePendingTransition(0, 0);
                startActivity(refresh);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(MainActivity.this, getString(R.string.weekly_menu_toast), Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0, 0);
                Intent start = new Intent(MainActivity.this, CalendarActivity.class);
                overridePendingTransition(0, 0);
                startActivity(start);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(MainActivity.this, getString(R.string.calendar_menu_toast), Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                Intent refresh = new Intent(MainActivity.this, SettingsActivity.class);
                overridePendingTransition(0, 0);
                startActivity(refresh);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(MainActivity.this, "Settings Menu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeViews() {
        notificationPanelRecView = findViewById(R.id.notificationPanelRecView);
        addNotificationPanel = findViewById(R.id.addNotificationPanel);
        languagesButton = findViewById(R.id.languagesButton);
        enButton = findViewById(R.id.enButton);
        grButton = findViewById(R.id.grButton);

        mainLayout = findViewById(R.id.main_layout);

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);
        floatingActionButton5 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item5);
    }

    @Override
    //Back button closes app in main activity
    public void onBackPressed() {
        super.onBackPressed();
        exitFromApp();
    }

    private void exitFromApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void languageAlertDialog() {

        languageDialog = new Dialog(MainActivity.this, R.style.AlertDialog);
        languageDialog.setContentView(R.layout.language_dialog);
        languageDialog.setTitle("Language");

        enButton = (ImageButton) languageDialog.findViewById(R.id.enButton);
        grButton = (ImageButton) languageDialog.findViewById(R.id.grButton);

        enButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "English", Toast.LENGTH_SHORT).show();
                setLocale("en");
                MainActivity.this.recreate();
                languageDialog.cancel();
            }
        });

        grButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Ελληνικά", Toast.LENGTH_SHORT).show();
                setLocale("el");
                MainActivity.this.recreate();
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

}