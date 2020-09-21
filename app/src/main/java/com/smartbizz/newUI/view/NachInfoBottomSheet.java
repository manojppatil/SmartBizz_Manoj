package com.smartbizz.newUI.view;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbizz.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class NachInfoBottomSheet extends BaseBottomSheet {


    public NachInfoBottomSheet() {
        // Required empty public constructor
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        this.dialog = dialog;

        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog;
        bottomSheetDialog.setContentView(R.layout.fragment_nachinfo_bottom_sheet);
        disableDragging(bottomSheetDialog);

    }

}
