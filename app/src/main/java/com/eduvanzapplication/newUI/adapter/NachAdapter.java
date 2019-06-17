package com.eduvanzapplication.newUI.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.pojo.MNach;

import java.util.ArrayList;
import java.util.List;

public class NachAdapter extends RecyclerView.Adapter<NachAdapter.ViewHolder> {
    List<MNach> list = new ArrayList();

    public List<MNach> getSpots() {
        return list;
    }

    Context context;
    FragmentActivity activity;

    public void setSpots(List<MNach> list) {
        this.list = list;
    }

    public NachAdapter(List<MNach> list, Context context, FragmentActivity activity) {
        this.list = list;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public NachAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.nachlist_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final MNach mNach = list.get(i);

//            viewHolder.txtNachId.setText("0"+mNach.emi_no);
        viewHolder.txtUmrnNum.setText(mNach.umrn_num);
        if (mNach.until_cancel.equals("0")) {
            viewHolder.txtEndDate.setText(mNach.end_date);
        } else if (mNach.until_cancel.equals("1")) {
            viewHolder.txtEndDate.setText("Until Cancel");
        }
        viewHolder.txtStatus.setText(mNach.status);
        if (mNach.status.toLowerCase().contains("registered")) {
            viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.border_circular_green_filled));

        } else {
            viewHolder.txtStatus.setTextColor(context.getResources().getColor(R.color.darkblue));
            viewHolder.txtStatus.setBackground(context.getResources().getDrawable(R.drawable.border_circular_yellow_filled));

        }
        viewHolder.txtPersonName.setText(" " + mNach.person_name);
        viewHolder.txtAmount.setText(" " + mNach.amount);
        viewHolder.txtFrequency.setText(" " + mNach.frequency);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNachId, txtUmrnNum, txtEndDate, txtStatus, txtPersonName, txtAmount, txtFrequency;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNachId = itemView.findViewById(R.id.txtNachId);
            txtUmrnNum = itemView.findViewById(R.id.txtUmrnNum);
            txtEndDate = itemView.findViewById(R.id.txtEndDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            txtPersonName = itemView.findViewById(R.id.txtPersonName);
            txtAmount = itemView.findViewById(R.id.txtAmount);
            txtFrequency = itemView.findViewById(R.id.txtFrequency);

        }
    }
}

