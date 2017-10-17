package com.eduvanz;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    Toolbar mToolbar;
    TextView loanAmtText,courseText,institueText,feesText,loanTitleText,
             personalTitleText,addressText,nameText,emailText,mobileText,progress ;
    ImageView userImage;
    Typeface typefaceFontAwesome,typeFaceReleway;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setTitle("Profile");
        mToolbar.setTitleTextColor(Color.WHITE);

        typefaceFontAwesome = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/droidsans_font.ttf");
        typeFaceReleway = Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/Raleway-Regular.ttf");
        nameText= (TextView) findViewById(R.id.user_name_userProfile);
        userImage= (ImageView) findViewById(R.id.user_image_userProfile);

        loanAmtText= (TextView) findViewById(R.id.amountLoan_textView);
        courseText= (TextView) findViewById(R.id.course_textView);
        institueText= (TextView) findViewById(R.id.institute_textView);
        feesText= (TextView) findViewById(R.id.fee_textView);
        loanTitleText= (TextView) findViewById(R.id.loadTitle);

        personalTitleText= (TextView) findViewById(R.id.personalTitle);
        emailText= (TextView) findViewById(R.id.email_textView);
        mobileText= (TextView) findViewById(R.id.mobile_textView);
        addressText= (TextView) findViewById(R.id.address_textView);
        progress= (TextView) findViewById(R.id.progress_userProfile);

        nameText.setTypeface(typefaceFontAwesome);
        loanAmtText.setTypeface(typefaceFontAwesome);
        courseText.setTypeface(typefaceFontAwesome);
        institueText.setTypeface(typefaceFontAwesome);
        feesText.setTypeface(typefaceFontAwesome);
        loanTitleText.setTypeface(typeFaceReleway);
        personalTitleText.setTypeface(typeFaceReleway);
        progress.setTypeface(typeFaceReleway);
        emailText.setTypeface(typefaceFontAwesome);
        mobileText.setTypeface(typefaceFontAwesome);
        addressText.setTypeface(typefaceFontAwesome);

//        personal
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
