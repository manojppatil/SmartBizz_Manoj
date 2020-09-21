package com.smartbizz.newUI.view;

import android.app.Dialog;

import androidx.fragment.app.Fragment;

import com.smartbizz.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class SMSSuccessBottomSheet extends BaseBottomSheet {


    public SMSSuccessBottomSheet() {
        // Required empty public constructor
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        this.dialog = dialog;

        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
        bottomSheetDialog.setContentView(R.layout.fragment_smssuccess_bottom_sheet);
        disableDragging(bottomSheetDialog);

    }

}
