package com.smartbizz.newUI.view;

import android.app.Dialog;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.smartbizz.R;
import com.smartbizz.Util.Constants;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.Util.PreferenceManager;
import com.smartbizz.newUI.network.NetworkManager;
import com.smartbizz.newUI.newViews.WebViewSMSService;
import com.google.android.material.bottomsheet.BottomSheetDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class SenderIdBottomSheet extends BaseBottomSheet {

    private TextView tvRegisterSMSService;
    private Button btnRequest;

    public SenderIdBottomSheet() {
        // Required empty public constructor
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        this.dialog = dialog;

        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
        bottomSheetDialog.setContentView(R.layout.fragment_sender_bottom_sheet);
        tvRegisterSMSService = bottomSheetDialog.findViewById(R.id.tvRegisterSMSService);
        btnRequest = bottomSheetDialog.findViewById(R.id.btnRequest);
        disableDragging(bottomSheetDialog);

        String message1 = "click here";

        SpannableString spannableString = new SpannableString(message1);

        spannableString.setSpan(spannableString, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.blue)), 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        String message = "Hey, " + PreferenceManager.getString(activity, Constants.PrefKeys.FIRST_NAME) + " looks you are not registered with SMS service please," + spannableString + " for self activation";
        tvRegisterSMSService.setText(message);

        tvRegisterSMSService.setOnClickListener(v -> {
            Intent intent = new Intent(activity, WebViewSMSService.class);
            startActivity(intent);
        });

        btnRequest.setOnClickListener(v -> {
            DialogUtil.showProgressDialog(activity);
            NetworkManager.getInstance(activity).getSMSServiceRequest(activity, response -> {
                DialogUtil.dismissProgressDialog();
                if (response.isSuccess()) {
                    makeToast(response.getMessage());
                } else {
                    makeToast(response.getMessage());
                }
            });
        });

    }

}

