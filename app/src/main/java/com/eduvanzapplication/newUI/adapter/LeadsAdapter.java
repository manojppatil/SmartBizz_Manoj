package com.eduvanzapplication.newUI.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.newViews.EligibilityCheck;
import com.eduvanzapplication.newUI.newViews.LoanApplication;
import com.eduvanzapplication.newUI.pojo.MLeads;

import java.util.Collections;
import java.util.List;


public class LeadsAdapter extends RecyclerView.Adapter<LeadsAdapter.MyViewHolder> {

    List<MLeads> horizontalList = Collections.emptyList();
    Context context;


    public LeadsAdapter(List<MLeads> horizontalList, Context context) {
        this.horizontalList = horizontalList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtFName, txtLName, txtlafid, txtInstitute, txtLafStatus, txtLafDate, txtAddress,
                txtLoanAmt, txtCourseAmt, txtCouseName;
        ImageView imgLafStatus;
        CardView card_view;

        public MyViewHolder(View view) {
            super(view);
            txtFName = (TextView) view.findViewById(R.id.txtFName);
            txtLName = (TextView) view.findViewById(R.id.txtLName);
            txtlafid = (TextView) view.findViewById(R.id.txtlafid);
            txtInstitute = (TextView) view.findViewById(R.id.txtInstitute);
            txtLafStatus = (TextView) view.findViewById(R.id.txtLafStatus);
            txtLafDate = (TextView) view.findViewById(R.id.txtLafDate);
            txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            txtLoanAmt = (TextView) view.findViewById(R.id.txtLoanAmt);
            txtCourseAmt = (TextView) view.findViewById(R.id.txtCourseAmt);
            txtCouseName = (TextView) view.findViewById(R.id.txtCouseName);
            imgLafStatus = (ImageView) view.findViewById(R.id.imgLafStatus);

            card_view = (CardView) view.findViewById(R.id.card_view);

            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (txtInstitute.getTag().toString().toLowerCase().equals("") || txtInstitute.getTag().toString().toLowerCase().contains("null")) {

                        Intent intent = new Intent(context, EligibilityCheck.class);
                        Bundle bundle = new Bundle();
                        MainApplication.lead_id = card_view.getTag().toString();
                        MainApplication.application_id = txtlafid.getText().toString();
                        bundle.putString("lead_id", card_view.getTag().toString());
                        bundle.putString("application_id", txtlafid.getText().toString());
                        bundle.putString("fillinstutute", txtlafid.getText().toString());
                        intent.putExtras(bundle);
                        context.startActivity(intent);

                    }else {

//                        Intent intent = new Intent(context, EligibilityCheck.class);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("lead_id", card_view.getTag().toString());
//                        bundle.putString("application_id", txtlafid.getText().toString());
//                        bundle.putString("fillinstutute", txtlafid.getText().toString());
//                        intent.putExtras(bundle);
//                        context.startActivity(intent);

                        Intent intent = new Intent(context, LoanApplication.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("lead_id", card_view.getTag().toString());
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.applications_card_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.txtFName.setText(horizontalList.get(position).first_name);
        holder.txtLName.setText(horizontalList.get(position).last_name);
        holder.txtlafid.setText(horizontalList.get(position).application_id);
        holder.txtInstitute.setText(horizontalList.get(position).institute_name);
        if (horizontalList.get(position).status_name.toString().toLowerCase().contains("pending")) {
//            holder.imgLafStatus.setBackground(context.getResources().getDrawable(R.drawable.ic_exclamation_circle));
            holder.imgLafStatus.setImageResource(R.drawable.ic_exclamation_circle);
            holder.imgLafStatus.setColorFilter(ContextCompat.getColor(context, R.color.colorYellow), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
//                holder.imgLafStatus.setBackground(context.getResources().getDrawable(R.drawable.ic_check_circle));
            holder.imgLafStatus.setImageResource(R.drawable.ic_check_circle);
            holder.imgLafStatus.setColorFilter(ContextCompat.getColor(context, R.color.colorGreen), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        holder.txtLafStatus.setText(horizontalList.get(position).status_name);//  lead_sub_status              "status_name": "LAF Pending",
        try {
            holder.txtLafDate.setText(horizontalList.get(position).created_date_time.toString().substring(0, 10));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.txtAddress.setText(horizontalList.get(position).location_name);
        holder.txtLoanAmt.setText(horizontalList.get(position).requested_loan_amount);
        holder.txtCourseAmt.setText(horizontalList.get(position).course_cost);
        holder.txtCouseName.setText(horizontalList.get(position).course_name);
        holder.card_view.setTag(horizontalList.get(position).lead_id.toString());
        holder.txtInstitute.setTag(horizontalList.get(position).institute_name.toString());

    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

}
