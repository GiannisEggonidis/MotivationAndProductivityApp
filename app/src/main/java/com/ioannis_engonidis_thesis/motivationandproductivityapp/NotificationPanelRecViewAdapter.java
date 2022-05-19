package com.ioannis_engonidis_thesis.motivationandproductivityapp;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.NOTIFICATION_SERVICE;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class NotificationPanelRecViewAdapter extends RecyclerView.Adapter<NotificationPanelRecViewAdapter.ViewHolder> {
    private String TAG = "NotificationPanelRecViewAdapter";

    private ArrayList<NotificationPanel> notificationPanel = new ArrayList<>();
    private Context mContext;

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
        //testing id generator
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"ID: "+ String.valueOf(notificationPanel.get(position).getId())+"\n"+notificationPanel.get(position).getNotificationName(), Toast.LENGTH_SHORT).show();

//                loadData();
                String notificationID = String.valueOf(notificationPanel.get(position).getId());
                createNotificationChannel(notificationID,notificationPanel.get(position).getNotificationName());
                scheduleNotification(notificationPanel.get(position).getNotificationName()
                                    , notificationPanel.get(position).getId());
            }
        });

        /** Configuring Buttons  **/
        holder.notificationName.setText(notificationPanel.get(position).getNotificationName());
        holder.hoursEditText.setText(notificationPanel.get(position).getHours());
        holder.minutesEditText.setText(notificationPanel.get(position).getMinutes());
        holder.enableNotifSwitch.setChecked(notificationPanel.get(position).isNotificationSwitch());
        holder.monCheckBox.setChecked(notificationPanel.get(position).isMondayCheckBox());
        holder.tueCheckBox.setChecked(notificationPanel.get(position).isTuesdayCheckBox());
        holder.wedCheckBox.setChecked(notificationPanel.get(position).isWednesdayCheckBox());
        holder.thuCheckBox.setChecked(notificationPanel.get(position).isThursdayCheckBox());
        holder.friCheckBox.setChecked(notificationPanel.get(position).isFridayCheckBox());
        holder.satCheckBox.setChecked(notificationPanel.get(position).isSaturdayCheckBox());
        holder.sunCheckBox.setChecked(notificationPanel.get(position).isSundayCheckBox());

        /** Configuring Delete button **/
        holder.deleteNotificationPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.AlertDialog);
                    builder.setTitle(notificationPanel.get(position).getNotificationName());
                    builder.setMessage("Delete Reminder");
                    builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                notificationManager.deleteNotificationChannel(String.valueOf(notificationPanel.get(position).getId()));
                            }
                            notificationPanel.remove(holder.getAdapterPosition());
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
                    //Set negative button text attributes
                    nbutton.setTextColor(Color.BLACK);
                    nbutton.setTextSize(18);
                    nbutton.setWidth(30);
                    Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                    //Set positive button text attributes
                    pbutton.setTextColor(Color.BLACK);
                    pbutton.setTextSize(18);
                    pbutton.setWidth(30);

                } catch (Exception e) {
                    Toast.makeText(mContext, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
                }

            }
        });

        /** Configure Notification Switch on change save data **/
        holder.enableNotifSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.enableNotifSwitch.isChecked()) {
                    notificationPanel.get(position).setNotificationSwitch(true);
                    saveData();

                } else {
                    notificationPanel.get(position).setNotificationSwitch(false);
                    saveData();
                }
            }
        });

        /** Configure notificationName on change save data **/
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

            }
        });

        /** Configure notification frequency on change save data**/{
            holder.hoursEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    notificationPanel.get(holder.getAdapterPosition()).setHours(String.valueOf(holder.hoursEditText.getText()));
                    saveData();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            holder.minutesEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    notificationPanel.get(holder.getAdapterPosition()).setMinutes(String.valueOf(holder.minutesEditText.getText()));
                    saveData();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        /** Configure weekdays checkboxes on change save data **/{
            holder.monCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.monCheckBox.isChecked()) {
                        notificationPanel.get(position).setMondayCheckBox(true);
                        saveData();
                    } else {
                        notificationPanel.get(position).setMondayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.tueCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.tueCheckBox.isChecked()) {
                        notificationPanel.get(position).setTuesdayCheckBox(true);
                        saveData();
                    } else {
                        notificationPanel.get(position).setTuesdayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.wedCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.wedCheckBox.isChecked()) {
                        notificationPanel.get(position).setWednesdayCheckBox(true);
                        saveData();
                    } else {
                        notificationPanel.get(position).setWednesdayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.thuCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.thuCheckBox.isChecked()) {
                        notificationPanel.get(position).setThursdayCheckBox(true);
                        saveData();
                    } else {
                        notificationPanel.get(position).setThursdayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.friCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.friCheckBox.isChecked()) {
                        notificationPanel.get(position).setFridayCheckBox(true);
                        saveData();
                    } else {
                        notificationPanel.get(position).setFridayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.satCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.satCheckBox.isChecked()) {
                        notificationPanel.get(position).setSaturdayCheckBox(true);
                        saveData();
                    } else {
                        notificationPanel.get(position).setSaturdayCheckBox(false);
                        saveData();
                    }
                }
            });
            holder.sunCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (holder.sunCheckBox.isChecked()) {
                        notificationPanel.get(position).setSundayCheckBox(true);
                        saveData();
                    } else {
                        notificationPanel.get(position).setSundayCheckBox(false);
                        saveData();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private EditText notificationName, hoursEditText, minutesEditText;
        private CheckBox monCheckBox, tueCheckBox, wedCheckBox, thuCheckBox, friCheckBox, satCheckBox, sunCheckBox;
        private SwitchCompat enableNotifSwitch;
        private TextView notifyEvery;
        private ImageButton deleteNotificationPanel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.notificationPanelParent);
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
            hoursEditText = itemView.findViewById(R.id.hoursEditText);
            minutesEditText = itemView.findViewById(R.id.minutesEditText);
            deleteNotificationPanel = itemView.findViewById(R.id.deleteNotificationPanel);


        }

    }

    private void saveData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("repeatingSharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationPanel);
        editor.putString("task list", json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("repeatingSharedPreferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<NotificationPanel>>() {
        }.getType();
        notificationPanel = gson.fromJson(json, type);

        if (notificationPanel == null) {
            notificationPanel = new ArrayList<>();
        }
    }

    private void scheduleNotification(String notificationTitle, int notificationID) {
        Intent intent = new Intent(mContext, NotificationPanelReceiver.class);
        intent.putExtra("title", notificationTitle);
        intent.putExtra("notificationID", notificationID);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, notificationID, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        long currentTime = System.currentTimeMillis() + 3000;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, currentTime, pendingIntent);
        }
    }

    private void createNotificationChannel(String channelID,String channelName) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String desc = "No description";
            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel channel = new NotificationChannel(channelID, channelName, importance);
            channel.setDescription(desc);
            NotificationManager manager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

    }
}