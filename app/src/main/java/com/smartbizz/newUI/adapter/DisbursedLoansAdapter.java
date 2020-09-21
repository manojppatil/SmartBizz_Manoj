package com.smartbizz.newUI.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbizz.R;
import com.smartbizz.Util.CollectionUtil;
import com.smartbizz.newUI.interfaces.OnApplicationEditListener;
import com.smartbizz.newUI.pojo.Category;

import java.util.List;


public class DisbursedLoansAdapter extends RecyclerView.Adapter<DisbursedLoansAdapter.ViewHolder> {
    private List<Category> applicationsList;
    private Context context;
    private OnApplicationEditListener onApplicationEditListener;

    public DisbursedLoansAdapter(Context context, List<Category> applicationsList) {
        this.applicationsList = applicationsList;
        this.context = context;
    }

    public DisbursedLoansAdapter(Context context, OnApplicationEditListener onApplicationEditListener, List<Category> applicationsList) {
        this.applicationsList = applicationsList;
        this.context = context;
        this.onApplicationEditListener = onApplicationEditListener;
    }

    @Override
    public void onBindViewHolder(final @NonNull DisbursedLoansAdapter.ViewHolder viewHolder, final int position) {
        Category application = CollectionUtil.get(applicationsList, position);
        if (application != null) {
            viewHolder.txtCategoryName.setText(application.getName());
            viewHolder.txtCategoryDesc.setText(application.getDescription());

            if(application.getIsActive() != null){
                if(application.getIsActive().equalsIgnoreCase("1")){
                    viewHolder.ivStatus.setVisibility(View.VISIBLE);
                }
            }
        }

    }


    @Override
    public int getItemCount() {
        return CollectionUtil.size(applicationsList);
    }

    @NonNull
    @Override
    public DisbursedLoansAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_disbursed_loan_list_item, viewGroup,
                false);
        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtCategoryName,txtCategoryDesc;
        ImageView ivStatus;

        Button btnEdit, btnContinue;

        ViewHolder(final View inflate) {
            super(inflate);
//            txtCategoryName = inflate.findViewById(R.id.txtCategoryName);
//            txtCategoryDesc = inflate.findViewById(R.id.txtCategoryDesc);
            ivStatus = inflate.findViewById(R.id.ivStatus);


            btnEdit = inflate.findViewById(R.id.btnEdit);
            btnContinue = inflate.findViewById(R.id.btnContinue);
        }
    }
}

