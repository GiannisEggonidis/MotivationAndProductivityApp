package com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder.NotificationPanel;

import java.util.ArrayList;

public class WeeklyReminderRecViewAdapter extends RecyclerView.Adapter<WeeklyReminderRecViewAdapter.ViewHolder> {
    // TODO: 09/06/2022 Set Weekly Reminder object
    private String TAG = "WeeklyReminderPanelRecViewAdapter";

    private ArrayList<WeeklyReminder> weeklyReminder = new ArrayList<>();
    private Context wContext;

    public WeeklyReminderRecViewAdapter(Context wContext) {
        this.wContext = wContext;
    }

    @NonNull
    @Override
    public WeeklyReminderRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyReminderRecViewAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setWeeklyReminder(ArrayList<WeeklyReminder> weeklyReminder) {
        this.weeklyReminder = weeklyReminder;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText weeklyReminderName;
        private TextView weeklyReminderHour;
        private Switch weeklyReminderSwitch;
        private ImageButton deleteWeeklyReminderPanel;
        private CheckBox weeklyReminderMondayCheckBox, weeklyReminderTuesdayCheckBox, weeklyReminderWednesdayCheckBox, weeklyReminderThursdayCheckBox, weeklyReminderFridayCheckBox, weeklyReminderSaturdayCheckBox, weeklyReminderSundayCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            weeklyReminderName = itemView.findViewById(R.id.weeklyReminderName);
            weeklyReminderHour = itemView.findViewById(R.id.weeklyReminderHour);
            weeklyReminderSwitch = itemView.findViewById(R.id.weeklyReminderSwitch);
            deleteWeeklyReminderPanel = itemView.findViewById(R.id.deleteWeeklyReminderPanel);
            weeklyReminderMondayCheckBox = itemView.findViewById(R.id.weeklyReminderMondayCheckBox);
            weeklyReminderTuesdayCheckBox = itemView.findViewById(R.id.weeklyReminderTuesdayCheckBox);
            weeklyReminderWednesdayCheckBox = itemView.findViewById(R.id.weeklyReminderWednesdayCheckBox);
            weeklyReminderThursdayCheckBox = itemView.findViewById(R.id.weeklyReminderThursdayCheckBox);
            weeklyReminderFridayCheckBox = itemView.findViewById(R.id.weeklyReminderFridayCheckBox);
            weeklyReminderSaturdayCheckBox = itemView.findViewById(R.id.weeklyReminderSaturdayCheckBox);
            weeklyReminderSundayCheckBox = itemView.findViewById(R.id.weeklyReminderSundayCheckBox);

        }
    }
}
