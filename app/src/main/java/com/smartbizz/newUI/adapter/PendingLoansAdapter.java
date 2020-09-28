package com.smartbizz.newUI.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.smartbizz.R;
import com.smartbizz.Util.CollectionUtil;
import com.smartbizz.newUI.interfaces.OnApplicationEditListener;
import com.smartbizz.newUI.network.ApiConstants;
import com.smartbizz.newUI.pojo.Requests;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PendingLoansAdapter extends RecyclerView.Adapter<PendingLoansAdapter.ViewHolder> {
    private List<Requests> applicationsList;
    private Context context;
    private OnApplicationEditListener onApplicationEditListener;

    public PendingLoansAdapter(Context context, OnApplicationEditListener onApplicationEditListener, List<Requests> applicationsList) {
        this.applicationsList = applicationsList;
        this.context = context;
        this.onApplicationEditListener = onApplicationEditListener;
    }

    @Override
    public void onBindViewHolder(final @NonNull PendingLoansAdapter.ViewHolder viewHolder, final int position) {
        Requests application = CollectionUtil.get(applicationsList, position);
        if (application != null) {
            Picasso.with(context).load(ApiConstants.BASE_URL+"/"+application.getImgPath()).placeholder(context.getResources().getDrawable(R.drawable.ic_placeholder_banner)).into(viewHolder.ivView);
            viewHolder.txtCategory.setText(application.getName());
//            viewHolder.etRemarks.setText(application.getComment());
            if(application.getImgAcceptanceStatus() != null){
                if(application.getImgAcceptanceStatus().equalsIgnoreCase("1")){
                    viewHolder.ivStatus.setVisibility(View.VISIBLE);
                    if(application.getImgPath() != null){
                        viewHolder.ivView.setTag(ApiConstants.BASE_URL+"/"+application.getImgPath());
                    }
                }
                if(application.getImgPath() != null){
                    viewHolder.btnEdit.setVisibility(View.VISIBLE);
                    viewHolder.btnContinue.setVisibility(View.VISIBLE);
                }else {
                    viewHolder.ivView.setTag(ApiConstants.BASE_URL+"/"+application.getImgPath());
                }

            }
        }
        viewHolder.ivView.setOnClickListener(view -> {
            Requests object = (Requests) view.getTag();
            if (viewHolder.ivView.getTag() != null) {
//                Intent intent = new Intent(context, EditImageActivity.class);
//                intent.putExtra("imgUri", viewHolder.ivView.getTag().toString());
//                context.startActivity(intent);
            }
        });

        viewHolder.btnEdit.setTag(application);
        viewHolder.btnContinue.setTag(application);
        viewHolder.btnEdit.setOnClickListener(view -> onApplicationEditListener.onEdit(view.getTag()));

        viewHolder.btnContinue.setOnClickListener(view -> onApplicationEditListener.onContinue(view.getTag()));
    }


    @Override
    public int getItemCount() {
        return CollectionUtil.size(applicationsList);
    }

    @NonNull
    @Override
    public PendingLoansAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_pending_loan_list_item, viewGroup,
                false);
        return new ViewHolder(view);
    }

//if iamgepath && imageaccepted == null
//Accept  useraction request_id 1
//Reject useraction 0
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCategory;
        ImageView ivView,ivStatus;
        EditText etRemarks;
        CheckBox checkbox;
        Button btnEdit, btnContinue;

        ViewHolder(final View inflate) {
            super(inflate);
            txtCategory = inflate.findViewById(R.id.txtCategory);
            ivView = inflate.findViewById(R.id.ivView);
            ivStatus = inflate.findViewById(R.id.ivStatus);
//            etRemarks = inflate.findViewById(R.id.etRemarks);
//            checkbox = inflate.findViewById(R.id.checkbox);

            btnEdit = inflate.findViewById(R.id.btnEdit);
            btnContinue = inflate.findViewById(R.id.btnContinue);
        }
    }
}

