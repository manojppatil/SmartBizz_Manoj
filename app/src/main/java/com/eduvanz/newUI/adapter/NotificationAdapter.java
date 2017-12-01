package com.eduvanz.newUI.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;
import com.eduvanz.newUI.pojo.NotificationData;

import java.util.ArrayList;

/**
 * Created by nikhil on 30/11/17.
 */

public class NotificationAdapter extends BaseAdapter {
    ArrayList<NotificationData> mData;
    Context mContext;
    LayoutInflater mInflator;

    Typeface fontAwesome;

    public NotificationAdapter(Context allOrders, ArrayList<NotificationData> mOrderdata) {
        mContext = allOrders;
        mData = mOrderdata;
        mInflator = LayoutInflater.from(mContext);
        fontAwesome = Typeface.createFromAsset(mContext.getAssets(), "fontawesome-webfont.ttf");
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public NotificationData getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
//        if (view == null) {
        final TextView textViewArrowDown;
        final TextView message,time;
        TextView bulletIcon;
            view = mInflator.inflate(R.layout.custom_notification, viewGroup, false);
        textViewArrowDown = (TextView) view.findViewById(R.id.texView_borrower_arrowdown1);
        bulletIcon = (TextView) view.findViewById(R.id.bulletIcon);
        bulletIcon.setTypeface(fontAwesome);
        textViewArrowDown.setTypeface(fontAwesome);
        message = (TextView) view.findViewById(R.id.textMessages);
        message.setTypeface(MainApplication.typefaceFont);
        time = (TextView) view.findViewById(R.id.timeOfMessage);
        final NotificationData item = getItem(i);
        message.setText(item.textOfMessage);
        time.setText(item.timeOfMessage);
        time.setTypeface(MainApplication.typefaceFont);
        textViewArrowDown.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(item.isSelected)
                {
                    textViewArrowDown.setText(mContext.getString(R.string.down));
                    message.setMaxLines(2);
                    item.isSelected=false;
                }else
                    {
                    textViewArrowDown.setText(mContext.getString(R.string.up));
                    message.setMaxLines(10);
                    item.isSelected=true;
                }
            }
        });
        Log.e(MainApplication.TAG, "getView: "+item.textOfMessage );

        return view;
    }


}