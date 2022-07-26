package com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
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
        holder.calendarView.setSelectionColor(cContext.getResources().getColor(R.color.transparent));
        holder.calendarView.addDecorator(new CalendarDayDecorator(1, calendars.get(position).getCalendarDays(), cContext));

        /** Initialize **/{
            holder.calendarName.setText(calendars.get(position).getCalendarName());
        }

        /** Configure Calendar View Select Days **/{

            holder.calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                    if (calendars.get(holder.getAdapterPosition()).getCalendarDays().contains(holder.calendarView.getSelectedDate())) {
                        calendars.get(holder.getAdapterPosition()).getCalendarDays().remove(holder.calendarView.getSelectedDate());
                        holder.calendarView.removeDecorators();
                        holder.calendarView.addDecorator(new CalendarDayDecorator(1, calendars.get(holder.getAdapterPosition()).getCalendarDays(), cContext));
                        Toast.makeText(cContext, "Size of Dates : " + calendars.get(holder.getAdapterPosition()).getCalendarDays().size() +
                                "\n Selected Date : " + holder.calendarView.getSelectedDate(), Toast.LENGTH_SHORT).show();
                        System.out.println(calendars.get(holder.getAdapterPosition()).getCalendarDays());
                        saveData();
                    } else {
                        calendars.get(holder.getAdapterPosition()).getCalendarDays().add(holder.calendarView.getSelectedDate());
                        holder.calendarView.addDecorator(new CalendarDayDecorator(1, calendars.get(holder.getAdapterPosition()).getCalendarDays(), cContext));

                        Toast.makeText(cContext, "Size of Dates : " + calendars.get(holder.getAdapterPosition()).getCalendarDays().size() +
                                "\n Selected Date : " + holder.calendarView.getSelectedDate(), Toast.LENGTH_SHORT).show();
                        System.out.println(calendars.get(holder.getAdapterPosition()).getCalendarDays());
                        saveData();
                    }
                }
            });

        }

        /** Configuring Delete button **/{
            holder.deleteCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(cContext, R.style.AlertDialog);
                        builder.setTitle(calendars.get(position).getCalendarName());
                        builder.setMessage("Delete Reminder");
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.calendarView.removeDecorators();
                                calendars.remove(holder.getAdapterPosition());
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
                        Toast.makeText(cContext, "Something went wrong, Try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
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

    private void saveData() {
        SharedPreferences sharedPreferences = cContext.getSharedPreferences("calendarsSharedPreferences", cContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(calendars);
        editor.putString("calendars", json);
        editor.apply();
    }
}
