package com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar;

import android.content.Context;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

public class CalendarDayDecorator implements DayViewDecorator {

    Context cContext;
    private int color;
    private HashSet<CalendarDay> dates;

    public CalendarDayDecorator(Context cContext, int color, HashSet<CalendarDay> dates) {
        this.cContext = cContext;
        this.color = color;
        this.dates = dates;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }


    @Override
    public void decorate(DayViewFacade view) {

    }
}
