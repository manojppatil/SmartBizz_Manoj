package com.smartbizz.newUI.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.smartbizz.R;
import com.smartbizz.newUI.pojo.ContactGroup;
import com.smartbizz.newUI.pojo.MCategory;

import java.util.List;

public class SpinnerAdapter<T> extends ArrayAdapter<T> {

    private Context mContext;
    private List<T> objects;


    public SpinnerAdapter(Context context, int resource, List<T> objects) {
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
         if (object instanceof MCategory) {
            view.setText(((MCategory) object).getName());
        }else if (object instanceof ContactGroup) {
            view.setText(((ContactGroup) object).getGroupName());
        }
        if (position == 0) {
            view.setTextColor(ContextCompat.getColor(mContext, R.color.colorHint));
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
         if (object instanceof MCategory) {
            view.setText(((MCategory) object).getName());
        }else if (object instanceof ContactGroup) {
            view.setText(((ContactGroup) object).getGroupName());
        }
        if (position != 0) {
            view.setTextColor(ContextCompat.getColor(mContext, android.R.color.black));
            view.setFocusable(false);
            view.setEnabled(false);
            view.setClickable(false);
        } else {
            view.setTextColor(ContextCompat.getColor(mContext, R.color.colorHint));
            view.setFocusable(true);
            view.setEnabled(true);
            view.setClickable(true);
        }
        return view;
    }
}