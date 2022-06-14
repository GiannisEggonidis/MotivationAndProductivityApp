package com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder.MainActivity;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.settings.SettingsActivity;

public class WeeklyReminderActivity extends AppCompatActivity {

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4, floatingActionButton5;

    private RecyclerView weeklyReminderPanelRecView;
    private WeeklyReminderRecViewAdapter adapter = new WeeklyReminderRecViewAdapter(this);
    private ImageButton addWeeklyReminderPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_reminder);
        initializeViews();
        menuClickFunction();
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

    private void menuClickFunction(){
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

    public void onBackPressed()
    {
        overridePendingTransition(0, 0);
        this.startActivity(new Intent(WeeklyReminderActivity.this,MainActivity.class));
        overridePendingTransition(0, 0);

        return;
    }
}