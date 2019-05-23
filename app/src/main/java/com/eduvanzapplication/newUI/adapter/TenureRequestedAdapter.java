package com.eduvanzapplication.newUI.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.pojo.Mforrequestedloan;

import java.util.Collections;
import java.util.List;

public class TenureRequestedAdapter extends RecyclerView.Adapter<TenureRequestedAdapter.DataObjectHolder> {

    private static SingleClickListener sClickListener;
    private static int sSelected = -1;

    List<Mforrequestedloan> horizontalList = Collections.emptyList();
    Context context;

    public TenureRequestedAdapter(List<Mforrequestedloan> mforrequestedloanArrayList, Context activity) {
        this.horizontalList = mforrequestedloanArrayList;
        this.context = activity;
    }

    static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTenure, txtEMIAmount, txtRoi, txtLoanAmount;
        RadioButton mRadioButton;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.txtTenure = itemView.findViewById(R.id.txtTenure);
            this.txtEMIAmount = itemView.findViewById(R.id.txtEMIAmount);
            this.txtRoi = itemView.findViewById(R.id.txtRoi);
            this.txtLoanAmount = itemView.findViewById(R.id.txtLoanAmount);
            this.mRadioButton = itemView.findViewById(R.id.rbrequested);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            sSelected = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view, mRadioButton.getId());
//          mRadioButton.setChecked(true);
        }
    }

    public void selectedItem() {
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(SingleClickListener clickListener) {
        sClickListener = clickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tenure_requested_card_view, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.txtTenure.setText(horizontalList.get(position).tenure+" Months");
//        holder.txtRoi.setText(horizontalList.get(position).emi_amount);
        holder.txtLoanAmount.setText(horizontalList.get(position).loan_amount);
        holder.txtEMIAmount.setText(horizontalList.get(position).emi_amount);
//        holder.mRadioButton.setText(horizontalList.get(position).applicationid);

        if (sSelected == position){
            holder.mRadioButton.setChecked(true);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.blue1));
        }else {
            holder.mRadioButton.setChecked(false);
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    public interface SingleClickListener {
           void onItemClickListener(int position, View view, int id);
    }

}
