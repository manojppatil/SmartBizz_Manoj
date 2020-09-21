package com.smartbizz.newUI.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.smartbizz.R;
import com.smartbizz.Util.Constants;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.newUI.adapter.PendingLoansAdapter;
import com.smartbizz.newUI.interfaces.OnApplicationEditListener;
import com.smartbizz.newUI.network.NetworkManager;
import com.smartbizz.newUI.newViews.DesignActivity;
import com.smartbizz.newUI.pojo.Requests;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PendingApplicationsFragment extends BaseFragment implements OnApplicationEditListener {
    private RecyclerView rvApplications;

    public PendingApplicationsFragment() {
        // Required empty public constructor
    }

    public static PendingApplicationsFragment newInstance(List<Requests> list) {
        PendingApplicationsFragment fragment = new PendingApplicationsFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.Extras.APPLICATIONS, (Serializable) list);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending_applications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setAdapter();
    }

    private void initViews(View view) {
        rvApplications = view.findViewById(R.id.rvApplications);
    }

    private void setAdapter() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            List<Requests> pendingLoansList = (List<Requests>) bundle.getSerializable(Constants.Extras.APPLICATIONS);
            if (pendingLoansList != null) {
                PendingLoansAdapter pendingLoansAdapter = new PendingLoansAdapter(activity, this, pendingLoansList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
                rvApplications.setLayoutManager(linearLayoutManager);
                rvApplications.setAdapter(pendingLoansAdapter);
                rvApplications.setNestedScrollingEnabled(true);
            }
        }
    }


    @Override
    public void onEdit(Object object) {
        if (object instanceof Requests) {
            Requests application = (Requests) object;

                DialogUtil.showProgressDialog(activity);
                NetworkManager.getInstance(activity).putUserAction(activity,application.getRequestId(),"0", response -> {
                    DialogUtil.dismissProgressDialog();
                    if (response.isSuccess()) {
                        makeToast(response.getResponse().optString("result"));
                        ((DesignActivity)activity).getApplicationList();

                    }else{
                        makeToast(response.getResponse().optString("result"));
                    }
                });
            }

    }

    @Override
    public void onContinue(Object object) {
        if (object instanceof Requests) {
            Requests application = (Requests) object;

            DialogUtil.showProgressDialog(activity);
            NetworkManager.getInstance(activity).putUserAction(activity,application.getRequestId(),"1", response -> {
                DialogUtil.dismissProgressDialog();
                if (response.isSuccess()) {
                    makeToast(response.getResponse().optString("result"));
                    ((DesignActivity)activity).getApplicationList();
                }else{
                    makeToast(response.getResponse().optString("result"));
                }
            });

        }
    }
}
