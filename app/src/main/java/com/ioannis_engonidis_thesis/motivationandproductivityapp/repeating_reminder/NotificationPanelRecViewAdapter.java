package com.ioannis_engonidis_thesis.motivationandproductivityapp.repeating_reminder;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar.CalendarDayDecorator;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar.SpinnerItemAdapter;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.weekly_reminder.WeeklyReminderReceiver;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NotificationPanelRecViewAdapter extends RecyclerView.Adapter<NotificationPanelRecViewAdapter.ViewHolder> {
    private String TAG = "NotificationPanelRecViewAdapter";

    private ArrayList<NotificationPanel> notificationPanel = new ArrayList<>();
    private Context mContext;
    private static long weekMs = 604800000;


    public NotificationPanelRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_panel_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        Intent myIntent = new Intent(mContext, NotificationPanelReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                mContext, 1, myIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Intent bootIntent = new Intent(mContext, NotificationPanelRecViewAdapter.class);
        if ("android.intent.action.BOOT_COMPLETED".equals(bootIntent.getAction())) {
            scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());
        }

        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

        /** Configuring Buttons **/{
            holder.intervalSpinner.setSelection(notificationPanel.get(position).getPickInterval());
            holder.notificationName.setText(notificationPanel.get(position).getNotificationName());
            holder.scheduleSwitch.setChecked(notificationPanel.get(position).isScheduleSwitch());
            holder.untilHours.setText(notificationPanel.get(position).getUntilHoursTxt());
            holder.fromHours.setText(notificationPanel.get(position).getFromHoursTxt());
            holder.enableNotifSwitch.setChecked(notificationPanel.get(position).isNotificationSwitch());
            holder.monCheckBox.setChecked(notificationPanel.get(position).isMondayCheckBox());
            holder.tueCheckBox.setChecked(notificationPanel.get(position).isTuesdayCheckBox());
            holder.wedCheckBox.setChecked(notificationPanel.get(position).isWednesdayCheckBox());
            holder.thuCheckBox.setChecked(notificationPanel.get(position).isThursdayCheckBox());
            holder.friCheckBox.setChecked(notificationPanel.get(position).isFridayCheckBox());
            holder.satCheckBox.setChecked(notificationPanel.get(position).isSaturdayCheckBox());
            holder.sunCheckBox.setChecked(notificationPanel.get(position).isSundayCheckBox());
            if (notificationPanel.get(position).isScheduleSwitch() == false) {
                holder.scheduleTime.setVisibility(View.GONE);
                holder.checkBoxes.setVisibility(View.GONE);
            }
        }

        /** Configure Spinner **/{

            holder.intervalSpinner.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    holder.intervalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                            holder.intervalSpinner.setSelection(i);
                            notificationPanel.get(holder.getPosition()).setPickInterval(i);
                            saveData();
                            scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    return false;
                }
            });


        }

        /** Configure Schedule Switch**/{
            holder.scheduleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked == true) {
                        notificationPanel.get(position).setScheduleSwitch(true);
                        holder.scheduleTime.setVisibility(View.VISIBLE);
                        holder.checkBoxes.setVisibility(View.VISIBLE);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    } else {
                        notificationPanel.get(position).setScheduleSwitch(false);
                        holder.scheduleTime.setVisibility(View.GONE);
                        holder.checkBoxes.setVisibility(View.GONE);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    }
                }
            });
        }

        /** Configure Schedule Time **/{
            Calendar subcalendar = Calendar.getInstance();
            subcalendar.set(Calendar.HOUR_OF_DAY, 0);
            subcalendar.set(Calendar.MINUTE, 0);
            subcalendar.set(Calendar.SECOND, 0);
            subcalendar.set(Calendar.MILLISECOND, 0);
            Date subDate = subcalendar.getTime();
            holder.fromHours.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar c = Calendar.getInstance();
                    int hours = c.get(Calendar.HOUR_OF_DAY);
                    int mins = c.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
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
                            holder.fromHours.setText(time);
                            notificationPanel.get(position).setFromHoursTxt(time);
                            Date date = calendar.getTime();
                            notificationPanel.get(position).setFromHours(date.getTime() - subDate.getTime());
                            saveData();
                            scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                        }
                    }, hours, mins, true);
                    timePickerDialog.show();

                }

            });

            holder.untilHours.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar c = Calendar.getInstance();
                    int hours = c.get(Calendar.HOUR_OF_DAY);
                    int mins = c.get(Calendar.MINUTE);
                    TimePickerDialog timePickerDialog = new TimePickerDialog(mContext, new TimePickerDialog.OnTimeSetListener() {
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
                            holder.untilHours.setText(time);
                            notificationPanel.get(position).setUntilHoursTxt(time);
                            Date date = calendar.getTime();
                            notificationPanel.get(position).setUntilHours(date.getTime() - subDate.getTime());
                            saveData();
                            scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                        }
                    }, hours, mins, true);
                    timePickerDialog.show();

                }

            });
        }

        /** Configuring Delete button **/{
            holder.deleteNotificationPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
                        builder.setTitle(notificationPanel.get(position).getNotificationName());
                        builder.setMessage("Delete Reminder?");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelNotification((notificationPanel.get(position).getId() + 1) * 10000);

                                for (int i = 1; i <= 48; i++) {
                                    cancelNotification((notificationPanel.get(position).getId() * 10000000) + 1000000 + i);
                                    cancelNotification((notificationPanel.get(position).getId() * 10000000) + 2000000 + i);
                                    cancelNotification((notificationPanel.get(position).getId() * 10000000) + 3000000 + i);
                                    cancelNotification((notificationPanel.get(position).getId() * 10000000) + 4000000 + i);
                                    cancelNotification((notificationPanel.get(position).getId() * 10000000) + 5000000 + i);
                                    cancelNotification((notificationPanel.get(position).getId() * 10000000) + 6000000 + i);
                                    cancelNotification((notificationPanel.get(position).getId() * 10000000) + 7000000 + i);
                                }

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    notificationManager.deleteNotificationChannel(String.valueOf(notificationPanel.get(position).getId())+100);
                                }

                                notificationPanel.remove(holder.getAdapterPosition());
                                notifyDataSetChanged();

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
                        Toast.makeText(mContext, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        /** Configure Notification Switch **/{
            holder.enableNotifSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.enableNotifSwitch.isChecked()) {
                        notificationPanel.get(position).setNotificationSwitch(true);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    } else {
                        notificationPanel.get(position).setNotificationSwitch(false);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    }
                }
            });
        }

        /** Configure notificationName **/{
            holder.notificationName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    notificationPanel.get(holder.getAdapterPosition()).setNotificationName(String.valueOf(holder.notificationName.getText()));
                    saveData();

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                }
            });
        }

        /** Configure weekdays checkboxes **/{
            holder.monCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.monCheckBox.isChecked()) {
                        notificationPanel.get(position).setMondayCheckBox(true);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    } else {
                        notificationPanel.get(position).setMondayCheckBox(false);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    }
                }
            });
            holder.tueCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.tueCheckBox.isChecked()) {
                        notificationPanel.get(position).setTuesdayCheckBox(true);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    } else {
                        notificationPanel.get(position).setTuesdayCheckBox(false);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    }
                }
            });
            holder.wedCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.wedCheckBox.isChecked()) {
                        notificationPanel.get(position).setWednesdayCheckBox(true);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    } else {
                        notificationPanel.get(position).setWednesdayCheckBox(false);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    }
                }
            });
            holder.thuCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.thuCheckBox.isChecked()) {
                        notificationPanel.get(position).setThursdayCheckBox(true);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    } else {
                        notificationPanel.get(position).setThursdayCheckBox(false);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    }
                }
            });
            holder.friCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.friCheckBox.isChecked()) {
                        notificationPanel.get(position).setFridayCheckBox(true);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    } else {
                        notificationPanel.get(position).setFridayCheckBox(false);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    }
                }
            });
            holder.satCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.satCheckBox.isChecked()) {
                        notificationPanel.get(position).setSaturdayCheckBox(true);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    } else {
                        notificationPanel.get(position).setSaturdayCheckBox(false);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    }
                }
            });
            holder.sunCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.sunCheckBox.isChecked()) {
                        notificationPanel.get(position).setSundayCheckBox(true);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    } else {
                        notificationPanel.get(position).setSundayCheckBox(false);
                        saveData();
                        scheduleNotification(notificationPanel.get(position).getNotificationName(), notificationPanel.get(position).getId(), notificationPanel.get(position).getPickInterval(), notificationPanel.get(position).isNotificationSwitch(), notificationPanel.get(position).isMondayCheckBox(), notificationPanel.get(position).isTuesdayCheckBox(), notificationPanel.get(position).isWednesdayCheckBox(), notificationPanel.get(position).isThursdayCheckBox(), notificationPanel.get(position).isFridayCheckBox(), notificationPanel.get(position).isSaturdayCheckBox(), notificationPanel.get(position).isSundayCheckBox(), notificationPanel.get(position).isScheduleSwitch(), notificationPanel.get(position).getFromHours(), notificationPanel.get(position).getUntilHours());

                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notificationPanel.size();
    }

    public void setNotificationPanel(ArrayList<NotificationPanel> notificationPanel) {
        this.notificationPanel = notificationPanel;
        notifyDataSetChanged();
    }

    private void scheduleNotification(String notificationName, int notificationID, int pickInterval
            , boolean notificationSwitch
            , boolean mondayCheckBox
            , boolean tuesdayCheckBox
            , boolean wednesdayCheckBox
            , boolean thursdayCheckBox
            , boolean fridayCheckBox
            , boolean saturdayCheckBox
            , boolean sundayCheckBox
            , boolean scheduleSwitch
            , long fromHours
            , long untilHours) {

        String idToString = String.valueOf(notificationID);
        createNotificationChannel(idToString + 100, notificationName);
        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Date currentTime = Calendar.getInstance().getTime();
        Calendar fromCalendar = Calendar.getInstance();
        Calendar untilCalendar = Calendar.getInstance();
        long fromMS;
        long untilMS;

        int fromHour = Math.toIntExact(fromHours / 1000 / 60 / 60 % 24);
        int fromMinutes = Math.toIntExact(fromHours / 1000 / 60 % 60);

        int untilHour = Math.toIntExact(untilHours / 1000 / 60 / 60 % 24);
        int untilMinutes = Math.toIntExact(untilHours / 1000 / 60 / 60 % 24);

        Intent intent = new Intent(mContext, NotificationPanelReceiver.class);
        intent.putExtra("repeatingTitle", notificationName);
        intent.putExtra("notificationID", notificationID);

        if (notificationSwitch) {

            if (scheduleSwitch) {

                /** Monday **/
                if (mondayCheckBox) {
                    fromCalendar.set(Calendar.DAY_OF_WEEK, 2);
                    fromCalendar.set(Calendar.HOUR_OF_DAY, fromHour);
                    fromCalendar.set(Calendar.MINUTE, fromMinutes);
                    fromCalendar.set(Calendar.SECOND, 0);
                    fromCalendar.set(Calendar.MILLISECOND, 0);
                    fromMS = fromCalendar.getTime().getTime();

                    untilCalendar.set(Calendar.DAY_OF_WEEK, 2);
                    untilCalendar.set(Calendar.HOUR_OF_DAY, untilHour);
                    untilCalendar.set(Calendar.MINUTE, untilMinutes);
                    untilCalendar.set(Calendar.SECOND, 0);
                    untilCalendar.set(Calendar.MILLISECOND, 0);
                    untilMS = fromCalendar.getTime().getTime();

                    long triggerTime;
                    int counter;

                    switch (pickInterval) {
                        case 0:
                            triggerTime = fromMS + (1000 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 1000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 3);
                            }
                            break;
                        case 1:
                            triggerTime = fromMS + (1000 * 60 * 60);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 1000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60);
                            }
                            break;
                        case 2:
                            triggerTime = fromMS + (1000 * 60 * 60 * 2);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 1000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 2);
                            }
                            break;
                        case 3:
                            triggerTime = fromMS + (1000 * 60 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 1000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 3);
                            }
                            break;
                        case 4:
                            triggerTime = fromMS + (1000 * 60 * 60 * 4);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 1000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 4);
                            }
                            break;
                        case 5:
                            triggerTime = fromMS + (1000 * 60 * 60 * 5);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 1000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 5);
                            }
                            break;
                        case 6:
                            triggerTime = fromMS + (1000 * 60 * 60 * 6);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 1000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 6);
                            }
                            break;
                    }

                } else {
                    for (int i = 1; i <= 48; i++) {
                        cancelNotification((notificationID * 10000000) + 1000000 + i);
                    }
                }

                /** Tuesday **/
                if (tuesdayCheckBox) {
                    fromCalendar.set(Calendar.DAY_OF_WEEK, 3);
                    fromCalendar.set(Calendar.HOUR_OF_DAY, fromHour);
                    fromCalendar.set(Calendar.MINUTE, fromMinutes);
                    fromCalendar.set(Calendar.SECOND, 0);
                    fromCalendar.set(Calendar.MILLISECOND, 0);
                    fromMS = fromCalendar.getTime().getTime();

                    untilCalendar.set(Calendar.DAY_OF_WEEK, 3);
                    untilCalendar.set(Calendar.HOUR_OF_DAY, untilHour);
                    untilCalendar.set(Calendar.MINUTE, untilMinutes);
                    untilCalendar.set(Calendar.SECOND, 0);
                    untilCalendar.set(Calendar.MILLISECOND, 0);
                    untilMS = fromCalendar.getTime().getTime();

                    long triggerTime;
                    int counter;

                    switch (pickInterval) {
                        case 0:
                            triggerTime = fromMS + (1000 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 2000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 3);
                            }
                            break;
                        case 1:
                            triggerTime = fromMS + (1000 * 60 * 60);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 2000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60);
                            }
                            break;
                        case 2:
                            triggerTime = fromMS + (1000 * 60 * 60 * 2);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 2000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 2);
                            }
                            break;
                        case 3:
                            triggerTime = fromMS + (1000 * 60 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 2000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 3);
                            }
                            break;
                        case 4:
                            triggerTime = fromMS + (1000 * 60 * 60 * 4);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 2000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 4);
                            }
                            break;
                        case 5:
                            triggerTime = fromMS + (1000 * 60 * 60 * 5);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 2000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 5);
                            }
                            break;
                        case 6:
                            triggerTime = fromMS + (1000 * 60 * 60 * 6);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 2000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 6);
                            }
                            break;
                    }

                } else {
                    for (int i = 1; i <= 48; i++) {
                        cancelNotification((notificationID * 10000000) + 2000000 + i);
                    }
                }

                /** Wednesday **/
                if (wednesdayCheckBox) {
                    fromCalendar.set(Calendar.DAY_OF_WEEK, 4);
                    fromCalendar.set(Calendar.HOUR_OF_DAY, fromHour);
                    fromCalendar.set(Calendar.MINUTE, fromMinutes);
                    fromCalendar.set(Calendar.SECOND, 0);
                    fromCalendar.set(Calendar.MILLISECOND, 0);
                    fromMS = fromCalendar.getTime().getTime();

                    untilCalendar.set(Calendar.DAY_OF_WEEK, 4);
                    untilCalendar.set(Calendar.HOUR_OF_DAY, untilHour);
                    untilCalendar.set(Calendar.MINUTE, untilMinutes);
                    untilCalendar.set(Calendar.SECOND, 0);
                    untilCalendar.set(Calendar.MILLISECOND, 0);
                    untilMS = fromCalendar.getTime().getTime();

                    long triggerTime;
                    int counter;

                    switch (pickInterval) {
                        case 0:
                            triggerTime = fromMS + (1000 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 3000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 3);
                            }
                            break;
                        case 1:
                            triggerTime = fromMS + (1000 * 60 * 60);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 3000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60);
                            }
                            break;
                        case 2:
                            triggerTime = fromMS + (1000 * 60 * 60 * 2);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 3000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 2);
                            }
                            break;
                        case 3:
                            triggerTime = fromMS + (1000 * 60 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 3000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 3);
                            }
                            break;
                        case 4:
                            triggerTime = fromMS + (1000 * 60 * 60 * 4);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 3000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 4);
                            }
                            break;
                        case 5:
                            triggerTime = fromMS + (1000 * 60 * 60 * 5);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 3000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 5);
                            }
                            break;
                        case 6:
                            triggerTime = fromMS + (1000 * 60 * 60 * 6);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 3000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 6);
                            }
                            break;
                    }

                } else {
                    for (int i = 1; i <= 48; i++) {
                        cancelNotification((notificationID * 10000000) + 3000000 + i);
                    }
                }

                /** Thursday **/
                if (thursdayCheckBox) {
                    fromCalendar.set(Calendar.DAY_OF_WEEK, 5);
                    fromCalendar.set(Calendar.HOUR_OF_DAY, fromHour);
                    fromCalendar.set(Calendar.MINUTE, fromMinutes);
                    fromCalendar.set(Calendar.SECOND, 0);
                    fromCalendar.set(Calendar.MILLISECOND, 0);
                    fromMS = fromCalendar.getTime().getTime();

                    untilCalendar.set(Calendar.DAY_OF_WEEK, 5);
                    untilCalendar.set(Calendar.HOUR_OF_DAY, untilHour);
                    untilCalendar.set(Calendar.MINUTE, untilMinutes);
                    untilCalendar.set(Calendar.SECOND, 0);
                    untilCalendar.set(Calendar.MILLISECOND, 0);
                    untilMS = fromCalendar.getTime().getTime();

                    long triggerTime;
                    int counter;

                    switch (pickInterval) {
                        case 0:
                            triggerTime = fromMS + (1000 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 4000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 3);
                            }
                            break;
                        case 1:
                            triggerTime = fromMS + (1000 * 60 * 60);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 4000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60);
                            }
                            break;
                        case 2:
                            triggerTime = fromMS + (1000 * 60 * 60 * 2);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 4000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 2);
                            }
                            break;
                        case 3:
                            triggerTime = fromMS + (1000 * 60 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 4000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 3);
                            }
                            break;
                        case 4:
                            triggerTime = fromMS + (1000 * 60 * 60 * 4);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 4000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 4);
                            }
                            break;
                        case 5:
                            triggerTime = fromMS + (1000 * 60 * 60 * 5);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 4000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 5);
                            }
                            break;
                        case 6:
                            triggerTime = fromMS + (1000 * 60 * 60 * 6);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 4000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 6);
                            }
                            break;
                    }

                } else {
                    for (int i = 1; i <= 48; i++) {
                        cancelNotification((notificationID * 10000000) + 4000000 + i);
                    }
                }

                /** Friday **/
                if (fridayCheckBox) {
                    fromCalendar.set(Calendar.DAY_OF_WEEK, 6);
                    fromCalendar.set(Calendar.HOUR_OF_DAY, fromHour);
                    fromCalendar.set(Calendar.MINUTE, fromMinutes);
                    fromCalendar.set(Calendar.SECOND, 0);
                    fromCalendar.set(Calendar.MILLISECOND, 0);
                    fromMS = fromCalendar.getTime().getTime();

                    untilCalendar.set(Calendar.DAY_OF_WEEK, 6);
                    untilCalendar.set(Calendar.HOUR_OF_DAY, untilHour);
                    untilCalendar.set(Calendar.MINUTE, untilMinutes);
                    untilCalendar.set(Calendar.SECOND, 0);
                    untilCalendar.set(Calendar.MILLISECOND, 0);
                    untilMS = fromCalendar.getTime().getTime();

                    long triggerTime;
                    int counter;

                    switch (pickInterval) {
                        case 0:
                            triggerTime = fromMS + (1000 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 5000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 3);
                            }
                            break;
                        case 1:
                            triggerTime = fromMS + (1000 * 60 * 60);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 5000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60);
                            }
                            break;
                        case 2:
                            triggerTime = fromMS + (1000 * 60 * 60 * 2);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 5000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 2);
                            }
                            break;
                        case 3:
                            triggerTime = fromMS + (1000 * 60 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 5000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 3);
                            }
                            break;
                        case 4:
                            triggerTime = fromMS + (1000 * 60 * 60 * 4);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 5000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 4);
                            }
                            break;
                        case 5:
                            triggerTime = fromMS + (1000 * 60 * 60 * 5);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 5000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 5);
                            }
                            break;
                        case 6:
                            triggerTime = fromMS + (1000 * 60 * 60 * 6);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 5000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 6);
                            }
                            break;
                    }

                } else {
                    for (int i = 1; i <= 48; i++) {
                        cancelNotification((notificationID * 10000000) + 5000000 + i);
                    }
                }

                /** Saturday **/
                if (saturdayCheckBox) {
                    fromCalendar.set(Calendar.DAY_OF_WEEK, 7);
                    fromCalendar.set(Calendar.HOUR_OF_DAY, fromHour);
                    fromCalendar.set(Calendar.MINUTE, fromMinutes);
                    fromCalendar.set(Calendar.SECOND, 0);
                    fromCalendar.set(Calendar.MILLISECOND, 0);
                    fromMS = fromCalendar.getTime().getTime();

                    untilCalendar.set(Calendar.DAY_OF_WEEK, 7);
                    untilCalendar.set(Calendar.HOUR_OF_DAY, untilHour);
                    untilCalendar.set(Calendar.MINUTE, untilMinutes);
                    untilCalendar.set(Calendar.SECOND, 0);
                    untilCalendar.set(Calendar.MILLISECOND, 0);
                    untilMS = fromCalendar.getTime().getTime();

                    long triggerTime;
                    int counter;

                    switch (pickInterval) {
                        case 0:
                            triggerTime = fromMS + (1000 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 6000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 3);
                            }
                            break;
                        case 1:
                            triggerTime = fromMS + (1000 * 60 * 60);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 6000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60);
                            }
                            break;
                        case 2:
                            triggerTime = fromMS + (1000 * 60 * 60 * 2);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 6000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 2);
                            }
                            break;
                        case 3:
                            triggerTime = fromMS + (1000 * 60 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 6000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 3);
                            }
                            break;
                        case 4:
                            triggerTime = fromMS + (1000 * 60 * 60 * 4);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 6000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 4);
                            }
                            break;
                        case 5:
                            triggerTime = fromMS + (1000 * 60 * 60 * 5);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 6000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 5);
                            }
                            break;
                        case 6:
                            triggerTime = fromMS + (1000 * 60 * 60 * 6);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 6000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 6);
                            }
                            break;
                    }

                } else {
                    for (int i = 1; i <= 48; i++) {
                        cancelNotification((notificationID * 10000000) + 6000000 + i);
                    }
                }

                /** Sunday **/
                if (sundayCheckBox) {
                    fromCalendar.set(Calendar.DAY_OF_WEEK, 1);
                    fromCalendar.set(Calendar.HOUR_OF_DAY, fromHour);
                    fromCalendar.set(Calendar.MINUTE, fromMinutes);
                    fromCalendar.set(Calendar.SECOND, 0);
                    fromCalendar.set(Calendar.MILLISECOND, 0);
                    fromMS = fromCalendar.getTime().getTime();

                    untilCalendar.set(Calendar.DAY_OF_WEEK, 1);
                    untilCalendar.set(Calendar.HOUR_OF_DAY, untilHour);
                    untilCalendar.set(Calendar.MINUTE, untilMinutes);
                    untilCalendar.set(Calendar.SECOND, 0);
                    untilCalendar.set(Calendar.MILLISECOND, 0);
                    untilMS = fromCalendar.getTime().getTime();

                    long triggerTime;
                    int counter;

                    switch (pickInterval) {
                        case 0:
                            triggerTime = fromMS + (1000 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 7000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 3);
                            }
                            break;
                        case 1:
                            triggerTime = fromMS + (1000 * 60 * 60);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 7000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60);
                            }
                            break;
                        case 2:
                            triggerTime = fromMS + (1000 * 60 * 60 * 2);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 7000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 2);
                            }
                            break;
                        case 3:
                            triggerTime = fromMS + (1000 * 60 * 60 * 3);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 7000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 3);
                            }
                            break;
                        case 4:
                            triggerTime = fromMS + (1000 * 60 * 60 * 4);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 7000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 4);
                            }
                            break;
                        case 5:
                            triggerTime = fromMS + (1000 * 60 * 60 * 5);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 7000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 5);
                            }
                            break;
                        case 6:
                            triggerTime = fromMS + (1000 * 60 * 60 * 6);
                            counter = 1;
                            while (triggerTime <= untilMS) {
                                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                                        mContext
                                        , (notificationID * 10000000) + 7000000 + counter
                                        , intent
                                        , PendingIntent.FLAG_CANCEL_CURRENT
                                );

                                if (currentTime.getTime() >= triggerTime) {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime + weekMs
                                            , weekMs
                                            , pendingIntent);
                                } else {
                                    manager.setRepeating(AlarmManager.RTC_WAKEUP
                                            , triggerTime
                                            , weekMs
                                            , pendingIntent);
                                }
                                counter++;
                                triggerTime = triggerTime + (1000 * 60 * 60 * 6);
                            }
                            break;
                    }

                } else {
                    for (int i = 1; i <= 48; i++) {
                        cancelNotification((notificationID * 10000000) + 7000000 + i);
                    }
                }

            } else {
                PendingIntent pendingIntent = PendingIntent.getBroadcast(
                        mContext
                        , (notificationID + 1) * 10000
                        , intent
                        , PendingIntent.FLAG_CANCEL_CURRENT);

                switch (pickInterval) {
                    case 0:
                        manager.setRepeating(AlarmManager.RTC_WAKEUP
                                , currentTime.getTime()
                                , 1000 * 60 * 3
                                , pendingIntent);
                        break;
                    case 1:
                        manager.setRepeating(AlarmManager.RTC_WAKEUP
                                , currentTime.getTime()
                                , 1000 * 60 * 60
                                , pendingIntent);
                        break;
                    case 2:
                        manager.setRepeating(AlarmManager.RTC_WAKEUP
                                , currentTime.getTime()
                                , 1000 * 60 * 60 * 2
                                , pendingIntent);
                        break;
                    case 3:
                        manager.setRepeating(AlarmManager.RTC_WAKEUP
                                , currentTime.getTime()
                                , 1000 * 60 * 60 * 3
                                , pendingIntent);
                        break;
                    case 4:
                        manager.setRepeating(AlarmManager.RTC_WAKEUP
                                , currentTime.getTime()
                                , 1000 * 60 * 60 * 4
                                , pendingIntent);
                        break;
                    case 5:
                        manager.setRepeating(AlarmManager.RTC_WAKEUP
                                , currentTime.getTime()
                                , 1000 * 60 * 60 * 5
                                , pendingIntent);
                        break;
                    case 6:
                        manager.setRepeating(AlarmManager.RTC_WAKEUP
                                , currentTime.getTime()
                                , 1000 * 60 * 60 * 6
                                , pendingIntent);
                        break;

                }
            }

        } else {
            cancelNotification((notificationID + 1) * 10000);

            for (int i = 1; i <= 48; i++) {
                cancelNotification((notificationID * 10000000) + 1000000 + i);
                cancelNotification((notificationID * 10000000) + 2000000 + i);
                cancelNotification((notificationID * 10000000) + 3000000 + i);
                cancelNotification((notificationID * 10000000) + 4000000 + i);
                cancelNotification((notificationID * 10000000) + 5000000 + i);
                cancelNotification((notificationID * 10000000) + 6000000 + i);
                cancelNotification((notificationID * 10000000) + 7000000 + i);
            }
        }

    }

    private void cancelNotification(int notificationID) {
        Intent intent = new Intent(mContext, NotificationPanelReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                mContext
                , notificationID
                , intent
                , PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager manager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    private void createNotificationChannel(String channelID, String channelName) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String desc = "No description";
            NotificationChannel channel = new NotificationChannel(channelID, " " + channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(desc);
            NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private Spinner intervalSpinner;
        private EditText notificationName;
        private CheckBox monCheckBox, tueCheckBox, wedCheckBox, thuCheckBox, friCheckBox, satCheckBox, sunCheckBox;
        private SwitchCompat enableNotifSwitch, scheduleSwitch;
        private TextView notifyEvery, fromHours, untilHours;
        private ImageButton deleteNotificationPanel;
        private RelativeLayout scheduleTime, checkBoxes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.notificationPanelParent);
            intervalSpinner = itemView.findViewById(R.id.intervalSpinner);
            notificationName = itemView.findViewById(R.id.notificationName);
            monCheckBox = itemView.findViewById(R.id.mondayCheckBox);
            tueCheckBox = itemView.findViewById(R.id.tuesdayCheckBox);
            wedCheckBox = itemView.findViewById(R.id.wednesdayCheckBox);
            thuCheckBox = itemView.findViewById(R.id.thursdayCheckBox);
            friCheckBox = itemView.findViewById(R.id.fridayCheckBox);
            satCheckBox = itemView.findViewById(R.id.saturdayCheckBox);
            sunCheckBox = itemView.findViewById(R.id.sundayCheckBox);
            enableNotifSwitch = itemView.findViewById(R.id.enableNotificationSwitch);
            notifyEvery = itemView.findViewById(R.id.notifyEvery);
            fromHours = itemView.findViewById(R.id.fromHours);
            untilHours = itemView.findViewById(R.id.untilHours);
            scheduleSwitch = itemView.findViewById(R.id.scheduleSwitch);
            checkBoxes = itemView.findViewById(R.id.checkBoxes);
            scheduleTime = itemView.findViewById(R.id.scheduleTime);
            deleteNotificationPanel = itemView.findViewById(R.id.deleteNotificationPanel);

        }

    }

    private void saveData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("repeatingSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationPanel);
        editor.putString("repeating_reminder", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("repeatingSharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("repeating_reminder", null);
        Type type = new TypeToken<ArrayList<NotificationPanel>>() {
        }.getType();
        notificationPanel = gson.fromJson(json, type);

        if (notificationPanel == null) {
            notificationPanel = new ArrayList<>();
        }
    }
}