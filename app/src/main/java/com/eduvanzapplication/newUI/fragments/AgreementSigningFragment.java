package com.eduvanzapplication.newUI.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.eduvanzapplication.R;


public class AgreementSigningFragment extends Fragment {

    static View view;
    RadioGroup rgSignIn;
    RadioButton rbEsign,rbManual;
    LinearLayout linManual;


    public AgreementSigningFragment() {
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
        view = inflater.inflate(R.layout.fragment_agreementsigning, container, false);

        rbEsign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        rbManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rbManual.isChecked()) {
                    linManual.setVisibility(View.VISIBLE);
                } else if (!rbManual.isChecked()) {
                    linManual.setVisibility(View.GONE);
                }
            }
        });

        return view;

    }

}
