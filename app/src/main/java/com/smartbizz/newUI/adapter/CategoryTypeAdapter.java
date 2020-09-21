package com.smartbizz.newUI.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.smartbizz.newUI.pojo.CategoryType;

import java.util.List;

public class CategoryTypeAdapter<T> extends ArrayAdapter<T> {

    private Context mContext;
    private List<T> objects;


    public CategoryTypeAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
        mContext = context;
        this.objects = objects;
    }

    // Affects default (closed) state of the spinner
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        T object = objects.get(position);

        if (object instanceof CategoryType) {
            view.setText(((CategoryType) object).getName());
        }

        if (position == 0) {
            view.setTextColor(ContextCompat.getColor(mContext, com.smartbizz.R.color.colorHint));
        } else {
            view.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
        }
        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        T object = objects.get(position);
         if (object instanceof CategoryType) {
            view.setText(((CategoryType) object).getName());
        }

        if (position != 0) {
            view.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
            view.setFocusable(false);
            view.setEnabled(false);
            view.setClickable(false);
        } else {
            view.setTextColor(ContextCompat.getColor(mContext, com.smartbizz.R.color.colorHint));
            view.setFocusable(true);
            view.setEnabled(true);
            view.setClickable(true);
        }
        return view;
    }
}