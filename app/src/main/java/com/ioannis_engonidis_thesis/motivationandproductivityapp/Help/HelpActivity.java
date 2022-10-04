package com.ioannis_engonidis_thesis.motivationandproductivityapp.Help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar.CalendarActivity;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder.MainActivity;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder.WeeklyReminderActivity;

import java.util.Locale;

public class HelpActivity extends AppCompatActivity {
    ImageButton languagesButton, enButton, grButton;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4;

    private Dialog languageDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        initializeViews();
        menuClickFunction();

        /** Animated Background Configuration **/
        {
            ConstraintLayout constraintLayout = findViewById(R.id.help_layout);
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
                    languageAlertDialog();
                }
            });
        }


    }

    private void menuClickFunction() {
        materialDesignFAM.setClosedOnTouchOutside(true);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                Intent start = new Intent(HelpActivity.this, MainActivity.class);
                overridePendingTransition(0, 0);
                startActivity(start);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(HelpActivity.this, getString(R.string.repeating_menu_toast), Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(0, 0);
                Intent start = new Intent(HelpActivity.this, WeeklyReminderActivity.class);
                overridePendingTransition(0, 0);
                startActivity(start);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(HelpActivity.this, getString(R.string.weekly_menu_toast), Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0, 0);
                Intent start = new Intent(HelpActivity.this, CalendarActivity.class);
                overridePendingTransition(0, 0);
                startActivity(start);//Start the same Activity
                overridePendingTransition(0, 0);
                Toast.makeText(HelpActivity.this, getString(R.string.calendar_menu_toast), Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HelpActivity.this, getString(R.string.help_menu_toast), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void languageAlertDialog() {

        languageDialog = new Dialog(HelpActivity.this, R.style.AlertDialog);
        languageDialog.setContentView(R.layout.language_dialog);
        languageDialog.setTitle("Language");

        enButton = (ImageButton) languageDialog.findViewById(R.id.enButton);
        grButton = (ImageButton) languageDialog.findViewById(R.id.grButton);

        enButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HelpActivity.this, "English", Toast.LENGTH_SHORT).show();
                setLocale("en");
                HelpActivity.this.recreate();
                languageDialog.cancel();
            }
        });

        grButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HelpActivity.this, "Ελληνικά", Toast.LENGTH_SHORT).show();
                setLocale("el");
                HelpActivity.this.recreate();
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

    private void initializeViews() {

        languagesButton = findViewById(R.id.languagesButton);
        enButton = findViewById(R.id.enButton);
        grButton = findViewById(R.id.grButton);
        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        floatingActionButton4 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item4);

    }

}