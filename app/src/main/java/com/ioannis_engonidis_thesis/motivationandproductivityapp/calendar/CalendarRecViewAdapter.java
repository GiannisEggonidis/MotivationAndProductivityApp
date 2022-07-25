package com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import java.util.ArrayList;

public class CalendarRecViewAdapter extends RecyclerView.Adapter<CalendarRecViewAdapter.ViewHolder> {
    private String TAG = "CalendarRecViewAdapter";

    private ArrayList<Calendar> calendars = new ArrayList<>();
    private Context cContext;

    public CalendarRecViewAdapter(Context cContext) {
        this.cContext = cContext;
    }

    public void setCalendars(ArrayList<Calendar> calendars) {
        this.calendars = calendars;
    }

    @NonNull
    @Override
    public CalendarRecViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_cardview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarRecViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
//        holder.calendarView.setSelectionColor(cContext.getResources().getColor(R.color.transparent));

        /** Initialize **/{
            holder.calendarName.setText(calendars.get(position).getCalendarName());
        }
    }

    @Override
    public int getItemCount() {
        return calendars.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private EditText calendarName;
        private Spinner pickColor;
        private ImageButton deleteCalendar;
        private MaterialCalendarView calendarView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            calendarName = itemView.findViewById(R.id.calendarName);
            pickColor = itemView.findViewById(R.id.pickColor);
            deleteCalendar = itemView.findViewById(R.id.deleteCalendar);
            calendarView = itemView.findViewById(R.id.calendarView);

        }
    }
}
