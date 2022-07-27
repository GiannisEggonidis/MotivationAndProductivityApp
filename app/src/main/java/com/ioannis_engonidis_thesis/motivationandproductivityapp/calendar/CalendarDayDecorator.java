package com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar;

import android.content.Context;
import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import androidx.core.content.ContextCompat;

import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class CalendarDayDecorator implements DayViewDecorator {

    Context dContext;
    private int color;
    private HashSet<CalendarDay> dates;

    public CalendarDayDecorator(int color, Collection<CalendarDay> dates, Context context) {
        this.dContext = context;
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }


    @Override
    public void decorate(DayViewFacade view) {

        view.addSpan(new ForegroundColorSpan(Color.BLACK));
        switch (color) {
            case 0:
                view.setSelectionDrawable(ContextCompat.getDrawable(dContext, R.drawable.spinner__blue));
                break;
            case 1:
                view.setSelectionDrawable(ContextCompat.getDrawable(dContext, R.drawable.spinner__green));
                break;
            case 2:
                view.setSelectionDrawable(ContextCompat.getDrawable(dContext, R.drawable.spinner__red));
                break;
            case 3:
                view.setSelectionDrawable(ContextCompat.getDrawable(dContext, R.drawable.spinner__orange));
                break;
            case 4:
                view.setSelectionDrawable(ContextCompat.getDrawable(dContext, R.drawable.spinner__yellow));
                break;
            default:
                view.setSelectionDrawable(ContextCompat.getDrawable(dContext, R.drawable.spinner__blue));

        }


    }
}
