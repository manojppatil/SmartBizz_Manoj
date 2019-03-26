package com.eduvanzapplication.newUI.newViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.adapter.TenureOfferedAdapter;
import com.eduvanzapplication.newUI.pojo.Mforoferedloan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TenureSelectionActivity extends AppCompatActivity {

    List<Mforoferedloan> horizontalList = new ArrayList<>();
    RecyclerView rvOffered;
    LinearLayout linProceedTenure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenure_selection);
        setViews();
        setOfferedRecycler();
    }

    private void setOfferedRecycler() {
        Mforoferedloan mforoferedloan = new Mforoferedloan();
        for (int i=0; i<10; i++){
            mforoferedloan = new Mforoferedloan();
            mforoferedloan.emi_amount = "565588888888.3358";
            mforoferedloan.loan_amount = "888888888";
            mforoferedloan.tenure ="24";
            horizontalList.add(mforoferedloan);
        }

        rvOffered.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        TenureOfferedAdapter offeredAdapter = new TenureOfferedAdapter(horizontalList, TenureSelectionActivity.this);
        rvOffered.setAdapter(offeredAdapter);
        rvOffered.setHasFixedSize(true);
    }

    private void setViews() {
        rvOffered = findViewById(R.id.rvOffered);
        linProceedTenure = findViewById(R.id.linProceedTenure);

        linProceedTenure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TenureSelectionActivity.this, DashboardActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }
}
