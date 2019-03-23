package com.eduvanzapplication.newUI.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eduvanzapplication.R;

import static com.airbnb.lottie.model.layer.Layer.LayerType.Text;
import static com.eduvanzapplication.R.*;
import static com.eduvanzapplication.R.color.red;


public class PostApprovalDocFragment extends Fragment {

    static View view;

    TextView txtProcessingFee;
    public PostApprovalDocFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(layout.fragment_postapprovaldoc, container, false);
        txtProcessingFee = view.findViewById(id.txtProcessingFee);

//        SpannableString string = new SpannableString("\u25CF Processing Fee");
//        string.setSpan(new BulletSpan(40, 20), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        SpannableString string1 = new SpannableString("Text with\nBullet point");
//        string1.setSpan(new BulletSpan(40, red, 20), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        txtProcessingFee.setText(string);
        return view;

    }

}
