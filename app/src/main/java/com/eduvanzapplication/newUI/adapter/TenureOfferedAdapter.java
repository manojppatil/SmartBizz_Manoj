package com.eduvanzapplication.newUI.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.pojo.Mforoferedloan;
import com.eduvanzapplication.newUI.pojo.Mforrequestedloan;

import java.util.Collections;
import java.util.List;


public class TenureOfferedAdapter extends RecyclerView.Adapter<TenureOfferedAdapter.DataObjectHolder> {

    private static Single1ClickListener sClickListener;
    private static int sSelected = -1;

    private List<Mforoferedloan> horizontalList = Collections.emptyList();
    private Context context;

    public TenureOfferedAdapter(List<Mforoferedloan> mforrequestedloanArrayList, AppCompatActivity activity) {
        this.horizontalList = mforrequestedloanArrayList;
        this.context = activity;
    }

    static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtTenure, txtRoi, txtLoanAmount, txtEMIAmount;
        RadioButton mRadioButton;

        public DataObjectHolder(View itemView) {
            super(itemView);
            this.txtTenure = (TextView) itemView.findViewById(R.id.txtTenure);
//            this.txtRoi = (TextView) itemView.findViewById(R.id.txtRoi);
            this.txtLoanAmount = (TextView) itemView.findViewById(R.id.txtLoanAmount);
            this.txtEMIAmount = (TextView) itemView.findViewById(R.id.txtEMIAmount);
//            this.mRadioButton = (RadioButton) itemView.findViewById(R.id.rbOffered);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            sSelected = getAdapterPosition();
            sClickListener.onItemClickListener(getAdapterPosition(), view, mRadioButton.getId());
        }
    }

    public void selectedItem() {
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(Single1ClickListener clickListener) {
        sClickListener = clickListener;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tenure_offered_card_view, parent, false);

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

//        holder.mRadioButton.setChecked(sSelected == position);
        if (position == 2){
            holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.blue1));
        }

    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }

    public interface Single1ClickListener {
           void onItemClickListener(int position, View view, int id);
    }

}
