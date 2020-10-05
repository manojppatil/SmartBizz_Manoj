package com.smartbizz.newUI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbizz.R;
import com.smartbizz.newUI.newViews.SmsTemplateActivity;
import com.smartbizz.newUI.pojo.SMSTemplates;

import java.util.List;

public class SmsTemplateAdapter extends RecyclerView.Adapter<SmsTemplateAdapter.ViewHolder> {
    Context context;
    List<SMSTemplates> smsTemplatesList;

    public SmsTemplateAdapter(SmsTemplateActivity context, List<SMSTemplates> smsTemplates) {
        this.context = context;
        this.smsTemplatesList = smsTemplates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sms_tempcat_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SMSTemplates smsTemplates = smsTemplatesList.get(position);
        holder.smscat_name.setText(smsTemplates.getCategory());
    }

    @Override
    public int getItemCount() {
        return smsTemplatesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView smscat_icon;
        TextView smscat_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            smscat_icon = itemView.findViewById(R.id.smscat_icon);
            smscat_name = itemView.findViewById(R.id.smscat_name);
        }
    }
}
