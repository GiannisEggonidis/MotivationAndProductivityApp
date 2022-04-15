package com.ioannis_engonidis_thesis.motivationandproductivityapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class NotificationPanelRecViewAdapter extends RecyclerView.Adapter<NotificationPanelRecViewAdapter.ViewHolder>  {
    private String TAG = "NotificationPanelRecViewAdapter";

    private ArrayList<NotificationPanel> notificationPanel = new ArrayList<>();
    private Context mContext;

    public NotificationPanelRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_panel_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        Log.d(TAG,"onBindViewHolder: Called");

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
                notificationPanel.remove(position);
                notifyDataSetChanged();
                Toast.makeText(mContext, "Panel Removed", Toast.LENGTH_SHORT).show();
                saveData();

            }
        });

        /** Configure Notification Switch on change save data **/
        holder.enableNotifSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.enableNotifSwitch.isChecked()){
                    notificationPanel.get(position).setNotificationSwitch(true);
                }else {
                    notificationPanel.get(position).setNotificationSwitch(false);
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
                notificationPanel.get(position).setNotificationName(String.valueOf(holder.notificationName.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /** Configure notification frequency on change save data**/
        holder.hoursEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                notificationPanel.get(position).setHours(String.valueOf(holder.hoursEditText.getText()));
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
                notificationPanel.get(position).setMinutes(String.valueOf(holder.minutesEditText.getText()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /** Configure weekdays checkboxes on change save data **/
        holder.monCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.monCheckBox.isChecked()){
                    notificationPanel.get(position).setMondayCheckBox(true);
                }else {
                    notificationPanel.get(position).setMondayCheckBox(false);
                }
            }
        });
        holder.tueCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.tueCheckBox.isChecked()){
                    notificationPanel.get(position).setTuesdayCheckBox(true);
                }else {
                    notificationPanel.get(position).setTuesdayCheckBox(false);
                }
            }
        });
        holder.wedCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.wedCheckBox.isChecked()){
                    notificationPanel.get(position).setWednesdayCheckBox(true);
                }else {
                    notificationPanel.get(position).setWednesdayCheckBox(false);
                }
            }
        });
        holder.thuCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.thuCheckBox.isChecked()){
                    notificationPanel.get(position).setThursdayCheckBox(true);
                }else {
                    notificationPanel.get(position).setThursdayCheckBox(false);
                }
            }
        });
        holder.friCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.friCheckBox.isChecked()){
                    notificationPanel.get(position).setFridayCheckBox(true);
                }else {
                    notificationPanel.get(position).setFridayCheckBox(false);
                }
            }
        });
        holder.satCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.satCheckBox.isChecked()){
                    notificationPanel.get(position).setSaturdayCheckBox(true);
                }else {
                    notificationPanel.get(position).setSaturdayCheckBox(false);
                }
            }
        });
        holder.sunCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.sunCheckBox.isChecked()){
                    notificationPanel.get(position).setSundayCheckBox(true);
                }else {
                    notificationPanel.get(position).setSundayCheckBox(false);
                }
            }
        });
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
        private EditText notificationName , hoursEditText , minutesEditText ;
        private CheckBox monCheckBox ,tueCheckBox,wedCheckBox,thuCheckBox,friCheckBox,satCheckBox,sunCheckBox;
        private Switch enableNotifSwitch;
        private TextView notifyEvery;
        private ImageButton deleteNotificationPanel ;

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

    private void saveData(){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notificationPanel);
        editor.putString("task list",json);
        editor.apply();
    }
}
