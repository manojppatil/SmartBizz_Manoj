package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduvanzapplication.R;

public class LoanDetailsFragment extends Fragment {

    RecyclerView tranche_recycler_view, amort_recycler_view;

    static View view;
    ImageButton btnDisrbExpand,btnAmortExpand;
    TextView txtDisbussch, txtAmortSchedule;
    LinearLayout linDisbursSchExp,linAmortSchExp,lindisbursSchedule, linamortSchedule;


    public LoanDetailsFragment() {
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
        view = inflater.inflate(R.layout.fragment_loandetails, container, false);
        txtDisbussch = (TextView) view.findViewById(R.id.txtDisbussch);
        txtAmortSchedule = (TextView) view.findViewById(R.id.txtAmortSchedule);

        btnDisrbExpand = (ImageButton) view.findViewById(R.id.btnDisrbExpand);
        btnAmortExpand = (ImageButton) view.findViewById(R.id.btnAmortExpand);

        linDisbursSchExp = (LinearLayout) view.findViewById(R.id.linDisbursSchExp);
        linAmortSchExp = (LinearLayout) view.findViewById(R.id.linAmortSchExp);
        lindisbursSchedule = (LinearLayout) view.findViewById(R.id.lindisbursSchedule);
        linamortSchedule = (LinearLayout) view.findViewById(R.id.linamortSchedule);

        tranche_recycler_view = (RecyclerView) view.findViewById(R.id.recyclertranche);

        amort_recycler_view = (RecyclerView) view.findViewById(R.id.recycleramort);


        return view;
    }


}
