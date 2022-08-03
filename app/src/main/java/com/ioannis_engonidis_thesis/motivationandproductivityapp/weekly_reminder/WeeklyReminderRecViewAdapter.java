package com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
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
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder.NotificationPanelReceiver;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.settings.SettingsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class WeeklyReminderRecViewAdapter extends RecyclerView.Adapter<WeeklyReminderRecViewAdapter.ViewHolder> {
    private String TAG = "WeeklyReminderPanelRecViewAdapter";
    private static long weekMs = 604800000;

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
    public void onBindViewHolder(@NonNull WeeklyReminderRecViewAdapter.ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

        /** Initialize **/{
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
        }

        /** Configuring Delete button **/{
            holder.deleteWeeklyReminderPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(wContext, R.style.AlertDialog);
                        builder.setTitle(weeklyReminder.get(position).getWeeklyReminderName());
                        builder.setMessage("Delete Reminder?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /**Cancel Notification before Deleting Weekly Reminder **/
                                for (int i = 1; i < 8; i++) {
                                    cancelNotification(weeklyReminder.get(position).getWeeklyReminderId() + (i * 1000));
                                }
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
        }

        /** Configure Notification Switch **/{
            holder.weeklyReminderSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderSwitch.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderSwitch(true);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                        String notificationID = String.valueOf(weeklyReminder.get(position).getWeeklyReminderId());
                    } else {
                        weeklyReminder.get(position).setWeeklyReminderSwitch(false);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    }
                }
            });
        }

        /** Configure notificationName **/{
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
                    weeklyReminder.get(holder.getAdapterPosition()).setWeeklyReminderName(String.valueOf(holder.weeklyReminderName.getText()));
                    saveData();
                    scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());
                }
            });
        }

        /** Configure weekdays checkboxes **/{
            holder.weeklyReminderMondayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderMondayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderMondayCheckBox(true);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    } else {
                        weeklyReminder.get(position).setWeeklyReminderMondayCheckBox(false);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    }
                }
            });
            holder.weeklyReminderTuesdayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderTuesdayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderTuesdayCheckBox(true);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    } else {
                        weeklyReminder.get(position).setWeeklyReminderTuesdayCheckBox(false);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    }
                }
            });
            holder.weeklyReminderWednesdayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderWednesdayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderWednesdayCheckBox(true);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    } else {
                        weeklyReminder.get(position).setWeeklyReminderWednesdayCheckBox(false);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    }
                }
            });
            holder.weeklyReminderThursdayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderThursdayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderThursdayCheckBox(true);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    } else {
                        weeklyReminder.get(position).setWeeklyReminderThursdayCheckBox(false);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    }
                }
            });
            holder.weeklyReminderFridayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderFridayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderFridayCheckBox(true);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    } else {
                        weeklyReminder.get(position).setWeeklyReminderFridayCheckBox(false);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    }
                }
            });
            holder.weeklyReminderSaturdayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderSaturdayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderSaturdayCheckBox(true);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    } else {
                        weeklyReminder.get(position).setWeeklyReminderSaturdayCheckBox(false);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    }
                }
            });
            holder.weeklyReminderSundayCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.weeklyReminderSundayCheckBox.isChecked()) {
                        weeklyReminder.get(position).setWeeklyReminderSundayCheckBox(true);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    } else {
                        weeklyReminder.get(position).setWeeklyReminderSundayCheckBox(false);
                        saveData();
                        scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                    }
                }
            });
        }

        /** Configure weeklyReminderHour **/{

            Calendar subcalendar = Calendar.getInstance();
            subcalendar.set(Calendar.HOUR_OF_DAY, 0);
            subcalendar.set(Calendar.MINUTE, 0);
            subcalendar.set(Calendar.SECOND, 0);
            subcalendar.set(Calendar.MILLISECOND, 0);
            Date subDate = subcalendar.getTime();
            holder.weeklyReminderHour.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar c = Calendar.getInstance();
                    int hours = c.get(Calendar.HOUR_OF_DAY);
                    int mins = c.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(wContext, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            calendar.set(Calendar.MINUTE, minute);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);
                            calendar.setTimeZone(TimeZone.getDefault());

                            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                            String time = format.format(calendar.getTime());
                            holder.weeklyReminderHour.setText(time);
                            weeklyReminder.get(position).setWeeklyReminderHour(time);
                            Date date = calendar.getTime();
                            weeklyReminder.get(position).setWeeklyReminderHourMs(date.getTime() - subDate.getTime());
                            saveData();
                            scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

                        }
                    }, hours, mins, true);
                    timePickerDialog.show();
                    scheduleWeeklyReminder(weeklyReminder.get(position).getWeeklyReminderName(), weeklyReminder.get(position).getWeeklyReminderId(), weeklyReminder.get(position).getWeeklyReminderHourMs(), weeklyReminder.get(position).isWeeklyReminderSwitch(), weeklyReminder.get(position).isWeeklyReminderMondayCheckBox(), weeklyReminder.get(position).isWeeklyReminderTuesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderWednesdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderThursdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderFridayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSaturdayCheckBox(), weeklyReminder.get(position).isWeeklyReminderSundayCheckBox());

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

    private void scheduleWeeklyReminder(String weeklyReminderTitle, int weeklyReminderID, long weeklyReminderHourMs
            , boolean weeklyReminderSwitch
            , boolean weeklyReminderMondayCheckBox
            , boolean weeklyReminderTuesdayCheckBox
            , boolean weeklyReminderWednesdayCheckBox
            , boolean weeklyReminderThursdayCheckBox
            , boolean weeklyReminderFridayCheckBox
            , boolean weeklyReminderSaturdayCheckBox
            , boolean weeklyReminderSundayCheckBox
    ) {
        String idToString = String.valueOf(weeklyReminderID);
        createNotificationChannel(idToString, weeklyReminderTitle);
        AlarmManager manager = (AlarmManager) wContext.getSystemService(Context.ALARM_SERVICE);
        Date currentTime = Calendar.getInstance().getTime();
        Calendar scheduleCalendar = Calendar.getInstance();
        long scheduledMS;

        int hour = Math.toIntExact(weeklyReminderHourMs / 1000 / 60 / 60 % 24);
        int minutes = Math.toIntExact(weeklyReminderHourMs / 1000 / 60 % 60);

        Intent intent = new Intent(wContext, WeeklyReminderReceiver.class);
        intent.putExtra("weeklyTitle", weeklyReminderTitle);
        intent.putExtra("weeklyReminderID", weeklyReminderID);

        if (weeklyReminderSwitch) {

            if (weeklyReminderMondayCheckBox) {
                scheduleCalendar.set(Calendar.DAY_OF_WEEK, 2);
                scheduleCalendar.set(Calendar.HOUR_OF_DAY, hour);
                scheduleCalendar.set(Calendar.MINUTE, minutes);
                scheduleCalendar.set(Calendar.SECOND, 0);
                scheduleCalendar.set(Calendar.MILLISECOND, 0);
                scheduledMS = scheduleCalendar.getTime().getTime();

                if (scheduledMS < currentTime.getTime()) {

                    scheduledMS = scheduledMS + weekMs;
                }
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        wContext
                        , weeklyReminderID + 1000
                        , intent
                        , PendingIntent.FLAG_CANCEL_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                            , scheduledMS - 40000
                            , weekMs - 60000
                            , pendingIntent);
                    System.out.println("Mon Schedule Ms : " + scheduledMS + "\n" + "Current Time Ms : " + currentTime.getTime());
                    System.out.println("Scheduled - Current Time : " + (scheduledMS - currentTime.getTime()));
                }


            } else {
                cancelNotification(weeklyReminderID + 1000);
            }
            if (weeklyReminderTuesdayCheckBox) {
                scheduleCalendar.set(Calendar.DAY_OF_WEEK, 3);
                scheduleCalendar.set(Calendar.HOUR_OF_DAY, hour);
                scheduleCalendar.set(Calendar.MINUTE, minutes);
                scheduleCalendar.set(Calendar.SECOND, 0);
                scheduleCalendar.set(Calendar.MILLISECOND, 0);
                scheduledMS = scheduleCalendar.getTime().getTime();

                if (scheduledMS < currentTime.getTime()) {
                    scheduledMS = scheduledMS + weekMs;
                }

                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        wContext
                        , weeklyReminderID + 2000
                        , intent
                        , PendingIntent.FLAG_CANCEL_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                            , scheduledMS - 40000
                            , weekMs - 60000
                            , pendingIntent);
                    System.out.println("Tue Schedule Ms : " + scheduledMS + "\n" + "Current Time Ms : " + currentTime.getTime());
                    System.out.println("Scheduled - Current Time : " + (scheduledMS - currentTime.getTime()));
                }


            } else {
                cancelNotification(weeklyReminderID + 2000);
            }
            if (weeklyReminderWednesdayCheckBox) {
                scheduleCalendar.set(Calendar.DAY_OF_WEEK, 4);
                scheduleCalendar.set(Calendar.HOUR_OF_DAY, hour);
                scheduleCalendar.set(Calendar.MINUTE, minutes);
                scheduleCalendar.set(Calendar.SECOND, 0);
                scheduleCalendar.set(Calendar.MILLISECOND, 0);
                scheduledMS = scheduleCalendar.getTime().getTime();

                if (scheduledMS < currentTime.getTime()) {

                    scheduledMS = scheduledMS + weekMs;
                }
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        wContext
                        , weeklyReminderID + 3000
                        , intent
                        , PendingIntent.FLAG_CANCEL_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                            , scheduledMS - 40000
                            , weekMs - 60000
                            , pendingIntent);
                    System.out.println("Wed Schedule Ms : " + scheduledMS + "\n" + "Current Time Ms : " + currentTime.getTime());
                    System.out.println("Scheduled - Current Time : " + (scheduledMS - currentTime.getTime()));
                }


            } else {
                cancelNotification(weeklyReminderID + 3000);
            }
            if (weeklyReminderThursdayCheckBox) {
                scheduleCalendar.set(Calendar.DAY_OF_WEEK, 5);
                scheduleCalendar.set(Calendar.HOUR_OF_DAY, hour);
                scheduleCalendar.set(Calendar.MINUTE, minutes);
                scheduleCalendar.set(Calendar.SECOND, 0);
                scheduleCalendar.set(Calendar.MILLISECOND, 0);
                scheduledMS = scheduleCalendar.getTime().getTime();

                if (scheduledMS < currentTime.getTime()) {

                    scheduledMS = scheduledMS + weekMs;
                }
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        wContext
                        , weeklyReminderID + 4000
                        , intent
                        , PendingIntent.FLAG_CANCEL_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                            , scheduledMS - 40000
                            , weekMs - 60000
                            , pendingIntent);
                    System.out.println("Thu Schedule Ms : " + scheduledMS + "\n" + "Current Time Ms : " + currentTime.getTime());
                    System.out.println("Scheduled - Current Time : " + (scheduledMS - currentTime.getTime()));
                }


            } else {
                cancelNotification(weeklyReminderID + 4000);
            }
            if (weeklyReminderFridayCheckBox) {
                scheduleCalendar.set(Calendar.DAY_OF_WEEK, 6);
                scheduleCalendar.set(Calendar.HOUR_OF_DAY, hour);
                scheduleCalendar.set(Calendar.MINUTE, minutes);
                scheduleCalendar.set(Calendar.SECOND, 0);
                scheduleCalendar.set(Calendar.MILLISECOND, 0);
                scheduledMS = scheduleCalendar.getTime().getTime();

                if (scheduledMS < currentTime.getTime()) {

                    scheduledMS = scheduledMS + weekMs;
                }
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        wContext
                        , weeklyReminderID + 5000
                        , intent
                        , PendingIntent.FLAG_CANCEL_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                            , scheduledMS - 40000
                            , weekMs - 60000
                            , pendingIntent);
                    System.out.println("Fri Schedule Ms : " + scheduledMS + "\n" + "Current Time Ms : " + currentTime.getTime());
                    System.out.println("Scheduled - Current Time : " + (scheduledMS - currentTime.getTime()));
                }


            } else {
                cancelNotification(weeklyReminderID + 5000);
            }
            if (weeklyReminderSaturdayCheckBox) {
                scheduleCalendar.set(Calendar.DAY_OF_WEEK, 7);
                scheduleCalendar.set(Calendar.HOUR_OF_DAY, hour);
                scheduleCalendar.set(Calendar.MINUTE, minutes);
                scheduleCalendar.set(Calendar.SECOND, 0);
                scheduleCalendar.set(Calendar.MILLISECOND, 0);
                scheduledMS = scheduleCalendar.getTime().getTime();

                if (scheduledMS < currentTime.getTime()) {

                    scheduledMS = scheduledMS + weekMs;
                }
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        wContext
                        , weeklyReminderID + 6000
                        , intent
                        , PendingIntent.FLAG_CANCEL_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                            , scheduledMS - 40000
                            , weekMs - 60000
                            , pendingIntent);
                    System.out.println("Sat Schedule Ms : " + scheduledMS + "\n" + "Current Time Ms : " + currentTime.getTime());
                    System.out.println("Scheduled - Current Time : " + (scheduledMS - currentTime.getTime()));
                }


            } else {
                cancelNotification(weeklyReminderID + 6000);
            }
            if (weeklyReminderSundayCheckBox) {
                scheduleCalendar.set(Calendar.DAY_OF_WEEK, 1);
                scheduleCalendar.set(Calendar.HOUR_OF_DAY, hour);
                scheduleCalendar.set(Calendar.MINUTE, minutes);
                scheduleCalendar.set(Calendar.SECOND, 0);
                scheduleCalendar.set(Calendar.MILLISECOND, 0);
                scheduledMS = scheduleCalendar.getTime().getTime();

                if (scheduledMS < currentTime.getTime()) {

                    scheduledMS = scheduledMS + weekMs;
                }
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        wContext
                        , weeklyReminderID + 7000
                        , intent
                        , PendingIntent.FLAG_CANCEL_CURRENT);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                            , scheduledMS - 40000
                            , weekMs - 60000
                            , pendingIntent);
                    System.out.println("Sun Schedule Ms : " + scheduledMS + "\n" + "Current Time Ms : " + currentTime.getTime());
                    System.out.println("Scheduled - Current Time : " + (scheduledMS - currentTime.getTime()));
                }


            } else {
                cancelNotification(weeklyReminderID + 7000);
            }

        } else {
            for (int i = 1; i < 8; i++) {
                cancelNotification(weeklyReminderID + (i * 1000));
            }
        }
    }

    private void cancelNotification(int notificationID) {
        Intent intent = new Intent(wContext, WeeklyReminderReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                wContext
                , notificationID
                , intent
                , PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager manager = (AlarmManager) wContext.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    private void createNotificationChannel(String channelID, String channelName) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String desc = "No description";
            NotificationChannel channel = new NotificationChannel(channelID, " " + channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(desc);
            NotificationManager manager = (NotificationManager) wContext.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

    }

}

