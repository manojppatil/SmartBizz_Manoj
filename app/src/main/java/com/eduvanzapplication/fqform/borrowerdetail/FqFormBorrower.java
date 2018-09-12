package com.eduvanzapplication.fqform.borrowerdetail;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.eduvanzapplication.R;

public class FqFormBorrower extends AppCompatActivity {

    TextView textViewPersonal, textViewEducation, textViewProfessionalandfinancial;
    TextView textViewPersonal1, textViewEducation2, textViewProfessionalNfinancial3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Borrower Details");

        textViewPersonal = (TextView) findViewById(R.id.textview_personal);
        textViewEducation = (TextView) findViewById(R.id.textview_education);
        textViewProfessionalandfinancial = (TextView) findViewById(R.id.textview_Professional);

//        textViewPersonal1 = (TextView) findViewById(R.id.textview_personal);
//        textViewEducation2 = (TextView) findViewById(R.id.textview_education);
//        textViewProfessionalNfinancial3 = (TextView) findViewById(R.id.textview_professionNfinancial);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textViewPersonal.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//            linearLayoutPersonal.setBackgroundColor(getColor(R.color.white));

//            textViewProfessionalNfinancial.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
//            textViewEducation.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
//            linearLayoutEducation.setBackgroundColor(getColor(R.color.lightblue));
//            linearLayoutProfessionalandfinancial.setBackgroundColor(getColor(R.color.lightblue));

            textViewEducation.setTextColor(getResources().getColor(R.color.white));
            textViewProfessionalandfinancial.setTextColor(getResources().getColor(R.color.white));
        }
        getSupportFragmentManager().beginTransaction().add(R.id.framelayout_fqform,new BorrowerPersonalFragment()).commit();


        textViewPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    textViewPersonal.setBackgroundColor(getColor(R.color.white));
//                    linearLayoutPersonal.setBackgroundColor(getColor(R.color.white));
//
//                    textViewProfessionalNfinancial.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
//                    textViewEducation.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
//                    linearLayoutEducation.setBackgroundColor(getColor(R.color.lightblue));
//                    linearLayoutProfessionalandfinancial.setBackgroundColor(getColor(R.color.lightblue));

                    textViewPersonal.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    textViewEducation.setTextColor(getResources().getColor(R.color.white));
                    textViewProfessionalandfinancial.setTextColor(getResources().getColor(R.color.white));
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_fqform,new BorrowerPersonalFragment()).commit();
            }
        });

        textViewEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    textViewEducation.setBackgroundColor(getColor(R.color.white));
//                    linearLayoutEducation.setBackgroundColor(getColor(R.color.white));
//
//                    textViewProfessionalNfinancial.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
//                    textViewPersonal.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
//                    linearLayoutProfessionalandfinancial.setBackgroundColor(getColor(R.color.lightblue));
//                    linearLayoutPersonal.setBackgroundColor(getColor(R.color.lightblue));

                    textViewPersonal.setTextColor(getResources().getColor(R.color.white));
                    textViewEducation.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    textViewProfessionalandfinancial.setTextColor(getResources().getColor(R.color.white));
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_fqform,new BorrowerEducationFragment()).commit();
            }
        });

        textViewProfessionalandfinancial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    textViewProfessionalNfinancial.setBackgroundColor(getColor(R.color.white));
//                    linearLayoutProfessionalandfinancial.setBackgroundColor(getColor(R.color.white));
//
//                    textViewPersonal.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
//                    textViewEducation.setBackground(getResources().getDrawable(R.drawable.rectanglebackground));
//                    linearLayoutEducation.setBackgroundColor(getColor(R.color.lightblue));
//                    linearLayoutPersonal.setBackgroundColor(getColor(R.color.lightblue));

                    textViewPersonal.setTextColor(getResources().getColor(R.color.white));
                    textViewEducation.setTextColor(getResources().getColor(R.color.white));
                    textViewProfessionalandfinancial.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout_fqform,new BorrowerProfessionNFinancialFragment()).commit();
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
