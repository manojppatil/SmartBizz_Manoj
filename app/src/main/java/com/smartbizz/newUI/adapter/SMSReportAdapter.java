package com.smartbizz.newUI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.smartbizz.R;
import com.smartbizz.newUI.pojo.mSMS;

import java.util.List;

public class SMSReportAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_MOVIE = 0;

    static Context context;
    List<mSMS> mAttendanceList;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    public SMSReportAdapter(Context context, List<mSMS> mAttendanceList) {
        this.context = context;
        this.mAttendanceList = mAttendanceList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        return new MovieHolder(inflater.inflate(R.layout.view_smsreport_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

//        if (getItemViewType(position) == TYPE_MOVIE) {
        ((MovieHolder) holder).bindData(mAttendanceList.get(position), position);
        //}
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {

//        return TYPE_MOVIE;
        return position;
    }

    @Override
    public int getItemCount() {
        return mAttendanceList.size();
    }

    static class MovieHolder extends RecyclerView.ViewHolder {

        TextView txtMobile, txtDescription, txtStatus;
        ImageView ivStar1, ivStar2, ivStar3;

        RelativeLayout relTableRow;

        public MovieHolder(View itemView) {
            super(itemView);
            relTableRow = itemView.findViewById(R.id.relTableRow);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtStatus = itemView.findViewById(R.id.txtStatus);
        }

        void bindData(mSMS mSMS, int position) {

            if (!mSMS.getMobile().equals("")) {
                String newString = mSMS.getMobile().substring(mSMS.getMobile().length()-4);
                txtMobile.setText(newString);
            }

            if (!mSMS.getStatus_desc().equals("")) {
                txtDescription.setText(mSMS.getStatus_desc());
            }

            if (!mSMS.getStatus().equals("")) {
                txtStatus.setText(mSMS.getStatus());
            }

            if (position % 2 != 0) {
                relTableRow.setBackgroundColor(context.getResources().getColor(R.color.colorTblBlue));
            }

//            if (mSMS.getIs_sunday().equalsIgnoreCase("true")) {
//                relTableRow.setBackgroundColor(context.getResources().getColor(R.color.colorTblPink));
//            }
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
