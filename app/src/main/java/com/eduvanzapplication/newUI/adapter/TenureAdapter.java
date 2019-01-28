//package com.eduvanzapplication.newUI.adapter;
//
//import android.content.Context;
//import android.support.v4.app.FragmentActivity;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RadioButton;
//import android.widget.TextView;
//
//import com.eduvanzapplication.R;
//import com.eduvanzapplication.Util.Data;
//import com.eduvanzapplication.newUI.pojo.MLeads;
//import com.eduvanzapplication.newUI.pojo.Mforoferedloan;
//import com.eduvanzapplication.newUI.pojo.Mforrequestedloan;
//
//import java.util.Collections;
//import java.util.List;
//
//
////public class TenureAdapter extends RecyclerView.Adapter<TenureAdapter.MyViewHolder> {
////
////    List<Data> horizontalList = Collections.emptyList();
////    Context context;
////
////
////    public TenureAdapter(List<Data> horizontalList, Context context) {
////        this.horizontalList = horizontalList;
////        this.context = context;
////    }
////
////    public class MyViewHolder extends RecyclerView.ViewHolder {
////        TextView txtTenure, txtEmiNo, txtLoanAmount, txtEMIAmount;
////        RadioButton mRadioButton;
////
////
////        public MyViewHolder(View view) {
////            super(view);
////            txtTenure = (TextView) view.findViewById(R.id.txtTenure);
////            txtEmiNo = (TextView) view.findViewById(R.id.txtEmiNo);
////            txtLoanAmount = (TextView) view.findViewById(R.id.txtLoanAmount);
////            txtEMIAmount = (TextView) view.findViewById(R.id.txtEMIAmount);
////            mRadioButton = (RadioButton) view.findViewById(R.id.single_list_item_check_button);
////        }
////    }
////
////
////    @Override
////    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
////        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tenure_card_view, parent, false);
////
////        return new MyViewHolder(itemView);
////    }
////
////    @Override
////    public void onBindViewHolder(final MyViewHolder holder, final int position) {
////
////        holder.txtTenure.setText(horizontalList.get(position).tenure);
////        holder.txtEmiNo.setText(horizontalList.get(position).noofemi);
////        holder.txtLoanAmount.setText(horizontalList.get(position).loanamount);
////        holder.txtEMIAmount.setText(horizontalList.get(position).emiamount);
////        holder.mRadioButton.setText(horizontalList.get(position).applicationid);
////
////    }
////
////    @Override
////    public int getItemCount() {
////        return horizontalList.size();
////    }
////}
//
//
//
//public class TenureAdapter extends RecyclerView.Adapter<TenureAdapter.DataObjectHolder> {
//
//    private static SingleClickListener sClickListener;
//    private static int sSelected = -1;
//
//    List<Mforrequestedloan> horizontalList = Collections.emptyList();
//    Context context;
//
//
//    public TenureAdapter(List<Mforoferedloan> horizontalList, Context context) {
//        this.horizontalList = horizontalList;
//        this.context = context;
//    }
//
//    public TenureAdapter(List<Mforrequestedloan> mforrequestedloanArrayList, FragmentActivity activity) {
//    }
//
//    static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//
//        TextView txtTenure, txtRoi, txtLoanAmount, txtEMIAmount;
//        RadioButton mRadioButton;
//
//        public DataObjectHolder(View itemView) {
//            super(itemView);
//            this.txtTenure = (TextView) itemView.findViewById(R.id.txtTenure);
//            this.txtRoi = (TextView) itemView.findViewById(R.id.txtRoi);
//            this.txtLoanAmount = (TextView) itemView.findViewById(R.id.txtLoanAmount);
//            this.txtEMIAmount = (TextView) itemView.findViewById(R.id.txtEMIAmount);
//            this.mRadioButton = (RadioButton) itemView.findViewById(R.id.single_list_item_check_button);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            sSelected = getAdapterPosition();
//            sClickListener.onItemClickListener(getAdapterPosition(), view);
//        }
//    }
//
//    public void selectedItem() {
//        notifyDataSetChanged();
//    }
//
//    public void setOnItemClickListener(SingleClickListener clickListener) {
//        sClickListener = clickListener;
//    }
//
//    @Override
//    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.tenure_card_view, parent, false);
//
//        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
//        return dataObjectHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(DataObjectHolder holder, int position) {
//        holder.txtTenure.setText(horizontalList.get(position).tenure);
//        holder.txtRoi.setText(horizontalList.get(position).emi_amount);
//        holder.txtLoanAmount.setText(horizontalList.get(position).loan_amount);
//        holder.txtEMIAmount.setText(horizontalList.get(position).emi_amount);
////        holder.mRadioButton.setText(horizontalList.get(position).applicationid);
//
//        holder.mRadioButton.setChecked(sSelected == position);
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return horizontalList.size();
//    }
//
//    public interface SingleClickListener {
//           void onItemClickListener(int position, View view);
//    }
//
//}
