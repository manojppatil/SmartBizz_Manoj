package com.eduvanz.fqform.coborrowerdetail;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduvanz.R;
import com.eduvanz.fqform.borrowerdetail.BorrowerEducationFragment;
import com.eduvanz.fqform.borrowerdetail.BorrowerPersonalFragment;
import com.eduvanz.fqform.borrowerdetail.BorrowerProfessionNFinancialFragment;

public class FqFormCoborrower extends AppCompatActivity {

    LinearLayout linearLayoutPersonal, linearLayoutFinancial;
    TextView textViewPersonal, textViewFinancial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coborrower_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("CoBorrower Details");

        linearLayoutPersonal = (LinearLayout) findViewById(R.id.linearlayout_personal_coborrower);
        linearLayoutFinancial = (LinearLayout) findViewById(R.id.linearlayout_financial);

        textViewPersonal = (TextView) findViewById(R.id.textview_personal_coborrower);
        textViewFinancial = (TextView) findViewById(R.id.textview_financial);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textViewPersonal.setBackgroundColor(getColor(R.color.white));
            linearLayoutPersonal.setBackgroundColor(getColor(R.color.white));
            
            textViewFinancial.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
            linearLayoutFinancial.setBackgroundColor(getColor(R.color.lightblue));
        }
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout_fqform_coborrower,new CoBorrowerPersonalFragment()).commit();


        linearLayoutPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textViewPersonal.setBackgroundColor(getColor(R.color.white));
                    linearLayoutPersonal.setBackgroundColor(getColor(R.color.white));
                    
                    textViewFinancial.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
                    linearLayoutFinancial.setBackgroundColor(getColor(R.color.lightblue));
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_fqform_coborrower,new CoBorrowerPersonalFragment()).commit();
            }
        });

        linearLayoutFinancial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    textViewFinancial.setBackgroundColor(getColor(R.color.white));
                    linearLayoutFinancial.setBackgroundColor(getColor(R.color.white));
                    
                    textViewPersonal.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
                    linearLayoutPersonal.setBackgroundColor(getColor(R.color.lightblue));
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_fqform_coborrower,new CoBorrowerFinancialFragment()).commit();
            }
        });
        
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
