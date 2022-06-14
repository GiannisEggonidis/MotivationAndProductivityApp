package com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weekly_reminder_panel_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeeklyReminderRecViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");

        /** Configuring Buttons **/
        holder.weeklyReminderName.setText(weeklyReminder.get(position).getWeeklyReminderName());
        holder.weeklyReminderHour.setText(weeklyReminder.get(position).getWeeklyReminderHour());
        holder.weeklyReminderSwitch.setChecked(weeklyReminder.get(position).isWeeklyReminderSwitch());
        holder.weeklyReminderMondayCheckBox.setChecked(weeklyReminder.get(position).isWeeklyReminderMondayCheckBox());
        holder.weeklyReminderTuesdayCheckBox.setChecked(weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox());
        holder.weeklyReminderWednesdayCheckBox.setChecked(weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox());
        holder.weeklyReminderThursdayCheckBox.setChecked(weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox());
        holder.weeklyReminderFridayCheckBox.setChecked(weeklyReminder.get(position).isWeeklyReminderFridayCheckBox());
        holder.weeklyReminderSaturdayCheckBox.setChecked(weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox());
        holder.weeklyReminderSundayCheckBox.setChecked(weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

        /** Configuring Delete button **/
        holder.deleteWeeklyReminderPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(wContext, R.style.AlertDialog);
                    builder.setTitle(weeklyReminder.get(position).getWeeklyReminderName());
                    builder.setMessage("Delete Reminder");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            weeklyReminder.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();
//                            Toast.makeText(mContext, "Reminder Removed\nTotal Reminders : " + notificationPanel.size(), Toast.LENGTH_SHORT).show();
                            saveData();
                        }
                    });
                    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                    /** Set negative button text attributes **/
                    nbutton.setTextColor(Color.BLACK);
                    nbutton.setTextSize(18);
                    nbutton.setWidth(30);
                    Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                    /** Set positive button text attributes **/
                    pbutton.setTextColor(Color.BLACK);
                    pbutton.setTextSize(18);
                    pbutton.setWidth(30);

                } catch (Exception e) {
                    Toast.makeText(wContext, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /** Configure Notification Switch **/
        holder.weeklyReminderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.weeklyReminderSwitch.isChecked()) {
                    weeklyReminder.get(position).setWeeklyReminderSwitch(true);
                    saveData();

                    String notificationID = String.valueOf(weeklyReminder.get(position).getWeeklyReminderId());
                } else {
                    weeklyReminder.get(position).setWeeklyReminderSwitch(false);
                    saveData();
                }
            }
        });

        /** Configure notificationName **/
        holder.weeklyReminderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                weeklyReminder.get(holder.getAdapterPosition()).setWeeklyReminderName(String.valueOf(holder.weeklyReminderName.getText()));
                saveData();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /** Configure weekdays checkboxes **/{
            holder.weeklyReminderMondayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderMondayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderMondayCheckBox(true);
                        saveData();
                    } else {
                        weeklyReminder.get(position).setWeeklyReminderMondayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.weeklyReminderTuesdayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderTuesdayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderTuesdayCheckBox(true);
                        saveData();
                    } else {
                        weeklyReminder.get(position).setWeeklyReminderTuesdayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.weeklyReminderWednesdayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderWednesdayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderWednesdayCheckBox(true);
                        saveData();
                    } else {
                        weeklyReminder.get(position).setWeeklyReminderWednesdayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.weeklyReminderThursdayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderThursdayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderThursdayCheckBox(true);
                        saveData();
                    } else {
                        weeklyReminder.get(position).setWeeklyReminderThursdayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.weeklyReminderFridayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderFridayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderFridayCheckBox(true);
                        saveData();
                    } else {
                        weeklyReminder.get(position).setWeeklyReminderFridayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.weeklyReminderSaturdayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderSaturdayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderSaturdayCheckBox(true);
                        saveData();
                    } else {
                        weeklyReminder.get(position).setWeeklyReminderSaturdayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.weeklyReminderSundayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderSundayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderSundayCheckBox(true);
                        saveData();
                    } else {
                        weeklyReminder.get(position).setWeeklyReminderSundayCheckBox(false);
                        saveData();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return weeklyReminder.size();
    }

    public void setWeeklyReminder(ArrayList<WeeklyReminder> weeklyReminder) {
        this.weeklyReminder = weeklyReminder;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText weeklyReminderName;
        private TextView weeklyReminderHour;
        private SwitchCompat weeklyReminderSwitch;
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

    private void saveData() {
        SharedPreferences sharedPreferences = wContext.getSharedPreferences("weeklySharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(weeklyReminder);
        editor.putString("weekly_reminder", json);
        editor.apply();
    }

}
