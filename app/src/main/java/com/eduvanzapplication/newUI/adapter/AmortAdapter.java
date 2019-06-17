package com.eduvanzapplication.newUI.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.newViews.LoanTabActivity;
import com.eduvanzapplication.newUI.pojo.MLoanEmis;

import java.util.ArrayList;
import java.util.List;

public class AmortAdapter extends RecyclerView.Adapter<AmortAdapter.ViewHolder>{
    List<MLoanEmis> list = new ArrayList();

    public List<MLoanEmis> getSpots() {
        return list;
    }
     Context context;
    FragmentActivity activity;

    public void setSpots(List<MLoanEmis> list) {
        this.list = list;
    }

    public AmortAdapter(List<MLoanEmis> list, Context context, FragmentActivity activity ){
        this.list = list;
        this.context = context;
        this.activity = activity;
    }
    @NonNull
    @Override
    public AmortAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.amortlist_card_view,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
            final MLoanEmis mLoanEmis = list.get(i);

            viewHolder.txtEmiNo.setText("0"+mLoanEmis.emi_no);
            viewHolder.txtEmiAmount.setText(mLoanEmis.emi_amount);
            viewHolder.txtDueBy.setText(mLoanEmis.proposed_payment_date);
            viewHolder.txtPaymentDate.setText(mLoanEmis.actual_payment_date);
            viewHolder.txtPaymentStatus.setText(" "+ mLoanEmis.statusMessage);
            if(mLoanEmis.status.equals("0")) {
                viewHolder.txtBtnText.setText(" " + "Pre Pay");
            }
            else {
                viewHolder.txtBtnText.setText(" " + "EMI History");
            }
            viewHolder.linPayBtn.setTag(mLoanEmis.loan_emi_id);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtEmiNo,txtEmiAmount,txtDueBy, txtPaymentDate, txtPaymentStatus,txtBtnText;
        LinearLayout linPayBtn;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtEmiNo = itemView.findViewById(R.id.txtEmiNo);
            txtEmiAmount = itemView.findViewById(R.id.txtEmiAmount);
            txtDueBy= itemView.findViewById(R.id.txtDueBy);
            txtPaymentDate = itemView.findViewById(R.id.txtPaymentDate);
            txtPaymentStatus = itemView.findViewById(R.id.txtPaymentStatus);
            txtBtnText = itemView.findViewById(R.id.txtBtnText);
            linPayBtn = itemView.findViewById(R.id.linPayBtn);

            linPayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(AmortAdapter.class.getSimpleName(),"LEADID -"+itemView.getTag().toString());

//                    try {
//                        context.startActivity(new Intent(context, LoanTabActivity.class)
//                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                .putExtra("lead_id", itemView.getTag().toString()));
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                }
            });

        }
    }
}

