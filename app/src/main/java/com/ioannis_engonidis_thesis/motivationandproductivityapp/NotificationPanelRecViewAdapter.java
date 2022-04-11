package com.ioannis_engonidis_thesis.motivationandproductivityapp;

import android.content.Context;
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
//        LayoutInflater inflater = LayoutInflater.from(mContext);
//        View view = inflater.inflate(R.layout.notification_panel_cardview,null,false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_panel_cardview,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        Log.d(TAG,"onBindViewHolder: Called");
        holder.notificationName.setText(notificationPanel.get(position).getNotificationName());

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
        private EditText notificationName ;
        private CheckBox monCheckBox ,tueCheckBox,wedCheckBox,thuCheckBox,friCheckBox,satCheckBox,sunCheckBox;
        private Switch enableNotifSwitch;
        private TextView notifyEvery;
        private Spinner hoursSpinner;
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
            hoursSpinner = (Spinner) itemView.findViewById(R.id.hoursSpinner);
            deleteNotificationPanel = itemView.findViewById(R.id.deleteNotificationPanel);


        }

    }
}
