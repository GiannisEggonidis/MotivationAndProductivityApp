package com.ioannis_engonidis_thesis.motivationandproductivityapp.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ioannis_engonidis_thesis.motivationandproductivityapp.R;

public class SpinnerItemAdapter extends BaseAdapter {

    Context context;
    int[] colorIcons;

    public SpinnerItemAdapter(Context context, int[] colorIcons) {
        this.context = context;
        this.colorIcons = colorIcons;
    }

    @Override
    public int getCount() {
        return colorIcons.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        view = LayoutInflater.from(context).inflate(R.layout.spinner_item_layout, viewGroup, false);
        ImageView imageVIew = view.findViewById(R.id.imageIcon);

        imageVIew.setImageResource(colorIcons[i]);

        return view;
    }
}
