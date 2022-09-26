package com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar;

import static java.lang.String.valueOf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CalendarRecViewAdapter extends RecyclerView.Adapter<CalendarRecViewAdapter.ViewHolder> {

    private String TAG = "CalendarRecViewAdapter";

    private ArrayList<Calendar> calendars = new ArrayList<>();
    private Context cContext;

    int[] colorIcons = {R.drawable.spinner_blue, R.drawable.spinner_green, R.drawable.spinner_red, R.drawable.spinner_orange, R.drawable.spinner_yellow};


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

        /** Initialize **/{
            holder.calendarView.setSelectionColor(cContext.getResources().getColor(R.color.transparent));
            holder.calendarView.addDecorator(new CalendarDayDecorator(calendars.get(position).getPickColor()
                    , calendars.get(position).getCalendarDays(), cContext));
            holder.calendarName.setText(calendars.get(position).getCalendarName());
            holder.calendarView.removeDecorators();
            holder.calendarView.addDecorator(new CalendarDayDecorator(calendars.get(position).getPickColor()
                    , calendars.get(position).getCalendarDays(), cContext));
            holder.countDaysText.setText(R.string.countDaysText + String.valueOf(calendars.get(position).getCalendarDays().size()));

            holder.calendarView.setTitleFormatter(new MonthArrayTitleFormatter(cContext.getResources().getTextArray(R.array.custom_months)));
            holder.calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(cContext.getResources().getTextArray(R.array.custom_weekdays)));

        }

        /** Configure Spinner **/{
            SpinnerItemAdapter spinnerItemAdapter = new SpinnerItemAdapter(cContext, colorIcons);
            holder.pickColor.setAdapter(spinnerItemAdapter);
            holder.pickColor.setBackground(cContext.getResources().getDrawable(colorIcons[calendars.get(position).getPickColor()]));
            holder.pickColor.setSelection(calendars.get(position).getPickColor(), true);
            holder.pickColor.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    holder.pickColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {

                            holder.pickColor.setBackground(cContext.getResources().getDrawable(colorIcons[i]));
                            holder.pickColor.setSelection(i);
                            calendars.get(holder.getPosition()).setPickColor(i);
                            saveData();

                            holder.calendarView.removeDecorators();
                            holder.calendarView.addDecorator(new CalendarDayDecorator(calendars.get(holder.getPosition()).getPickColor()
                                    , calendars.get(holder.getLayoutPosition()).getCalendarDays(), cContext));

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    return false;
                }
            });

            saveData();

        }

        /** Configure Calendar Name **/{
            holder.calendarName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    calendars.get(holder.getAdapterPosition()).setCalendarName(valueOf(holder.calendarName.getText()));
                    saveData();
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    calendars.get(holder.getAdapterPosition()).setCalendarName(valueOf(holder.calendarName.getText()));
                    saveData();
                }
            });
        }

        /** Configure Calendar View Select Days **/{

            holder.calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
                @Override
                public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                    if (calendars.get(holder.getAdapterPosition()).getCalendarDays().contains(holder.calendarView.getSelectedDate())) {
                        calendars.get(holder.getAdapterPosition()).getCalendarDays().remove(holder.calendarView.getSelectedDate());
                        holder.calendarView.removeDecorators();
                        holder.calendarView.addDecorator(new CalendarDayDecorator(calendars.get(holder.getAbsoluteAdapterPosition()).getPickColor()
                                , calendars.get(holder.getAdapterPosition()).getCalendarDays(), cContext));

                        System.out.println(calendars.get(holder.getAdapterPosition()).getCalendarDays() + "Counted Days " + calendars.get(position).getCalendarDays().size());
                        holder.countDaysText.setText(R.string.countDaysText + valueOf(calendars.get(position).getCalendarDays().size()));
                        saveData();
                    } else {
                        calendars.get(holder.getAdapterPosition()).getCalendarDays().add(holder.calendarView.getSelectedDate());
                        holder.calendarView.removeDecorators();
                        holder.calendarView.addDecorator(new CalendarDayDecorator(calendars.get(holder.getAbsoluteAdapterPosition()).getPickColor()
                                , calendars.get(holder.getAdapterPosition()).getCalendarDays(), cContext));

                        System.out.println(calendars.get(holder.getAdapterPosition()).getCalendarDays() + "Counted Days " + calendars.get(position).getCalendarDays().size());
                        holder.countDaysText.setText(R.string.countDaysText + valueOf(calendars.get(position).getCalendarDays().size()));
                        saveData();

                    }
                }
            });

        }

        /** Configure Delete button **/{
            holder.deleteCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        AlertDialog.Builder builder = new AlertDialog.Builder(cContext, R.style.AlertDialog);
                        builder.setTitle(calendars.get(position).getCalendarName());
                        builder.setMessage(R.string.delete_calendar);
                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.calendarView.removeDecorators();
                                calendars.remove(holder.getPosition());
                                notifyDataSetChanged();
                                saveData();
                                holder.activity.hideButton();
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
        private TextView countDaysText;
        private Spinner pickColor;
        private ImageButton deleteCalendar;
        private MaterialCalendarView calendarView;

        private CalendarActivity activity = (CalendarActivity)cContext;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countDaysText = itemView.findViewById(R.id.countDaysText);
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
