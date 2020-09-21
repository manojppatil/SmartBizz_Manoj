package com.smartbizz.newUI.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.smartbizz.R;
import com.smartbizz.Util.Constants;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.Util.FileUtil;
import com.smartbizz.Util.PreferenceManager;
import com.smartbizz.newUI.network.NetworkManager;

public class SMSSenderIDUpdateFragment extends BaseFragment implements View.OnClickListener {
    View view;

    private EditText etApplicationId;
    private TextView txtSenderId;
    private Button btnSubmit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_senderid, container, false);
        etApplicationId = view.findViewById(R.id.etApplicationId);
        txtSenderId = view.findViewById(R.id.txtSenderId);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);

        if(PreferenceManager.getString(activity, Constants.PrefKeys.SENDERID).equalsIgnoreCase("null") || PreferenceManager.getString(activity, Constants.PrefKeys.SENDERID).equalsIgnoreCase("null"))
        {
        }else{
            txtSenderId.setText(PreferenceManager.getString(activity, Constants.PrefKeys.SENDERID));
        }

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btnSubmit:

                if(etApplicationId.getText().toString().length()<1)
                {
                    makeToast("Please Enter Application Id");
                    return;
                }
                DialogUtil.showProgressDialog(activity);
                NetworkManager.getInstance(activity).putSenderId(activity, etApplicationId.getText().toString().trim(), response -> {
                    DialogUtil.dismissProgressDialog();
                    if (response.isSuccess()) {
                        etApplicationId.setText("");
                        makeToast(response.getMessage());
                    } else {
                        makeToast(response.getMessage());
                    }
                });

                break;

        }

    }

}