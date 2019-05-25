package com.eduvanzapplication.newUI.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.newViews.LoanTabActivity;
import com.eduvanzapplication.newUI.pojo.MLeads;

import java.util.ArrayList;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder>{
    List<MLeads> list = new ArrayList();

    public List<MLeads> getSpots() {
        return list;
    }
     Context context;
    FragmentActivity activity;

    public void setSpots(List<MLeads> list) {
        this.list = list;
    }

    public CardStackAdapter(List<MLeads> list, Context context, FragmentActivity activity ){
        this.list = list;
        this.context = context;
        this.activity = activity;
    }
    @NonNull
    @Override
    public CardStackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.applications_card_view,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
            final MLeads mLeads = list.get(i);

            viewHolder.txtLeadfName.setText(mLeads.first_name);
            viewHolder.txtLeadlName.setText(mLeads.last_name);
            viewHolder.txtApplicationId.setText(mLeads.application_id);
            viewHolder.txtCity.setText(mLeads.location_name);
            viewHolder.txtCourseFee.setText(new String("\t").concat(mLeads.course_cost));
            viewHolder.txtCourseNmae.setText(mLeads.course_name);
            viewHolder.txtDate.setText(new String("\t").concat(mLeads.created_date_time));
            viewHolder.txtLoanAmount.setText(new String("\t").concat(mLeads.requested_loan_amount));
            viewHolder.txtUniversity.setText(mLeads.institute_name);
            viewHolder.txtMessage.setText("\tPending. Complete your process in order to get your loan instantly ");
            viewHolder.txtMessage.setSelected(true);
            viewHolder.txtStatus1.setText("\t"+ mLeads.status_name);
            viewHolder.txtStatus2.setText("\tStatus 2");

            viewHolder.itemView.setTag(mLeads.lead_id);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView  txtLeadfName,txtLeadlName,txtMessage, txtApplicationId, txtStatus1, txtDate, txtStatus2,
                txtUniversity, txtCourseNmae,txtCity,txtCourseFee,txtLoanAmount ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtLeadfName = itemView.findViewById(R.id.txtLeadfName);
            txtLeadlName = itemView.findViewById(R.id.txtLeadlName);
            txtMessage= itemView.findViewById(R.id.txtMessage);
            txtApplicationId = itemView.findViewById(R.id.txtApplicationId);
            txtStatus1 = itemView.findViewById(R.id.txtStatus1);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus2 = itemView.findViewById(R.id.txtStatus2);
            txtUniversity = itemView.findViewById(R.id.txtUniversity);
            txtCourseNmae = itemView.findViewById(R.id.txtCourseNmae);
            txtCity = itemView.findViewById(R.id.txtCity);
            txtCourseFee= itemView.findViewById(R.id.txtCourseFee);
            txtLoanAmount = itemView.findViewById(R.id.txtLoanAmount);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(CardStackAdapter.class.getSimpleName(),"LEADID -"+itemView.getTag().toString());

                    try {
                        context.startActivity(new Intent(context, LoanTabActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra("lead_id", itemView.getTag().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }
}

