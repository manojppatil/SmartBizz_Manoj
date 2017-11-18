package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.eduvanz.MainApplication;
import com.eduvanz.R;

public class ContactUs extends AppCompatActivity {

    TextView  textView1, textView2, textView3, textView4, textView5, textView6,
            textView7, textView8;
    MainApplication mainApplication;
    Context context;
    ImageView imageViewOfficeLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        context = this;
        mainApplication = new MainApplication();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Us");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        textView1 = (TextView) findViewById(R.id.textview1_contactus);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) findViewById(R.id.textview2_contactus);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) findViewById(R.id.textview3_contactus);
        mainApplication.applyTypeface(textView3, context);
        textView4 = (TextView) findViewById(R.id.textview4_contactus);
        mainApplication.applyTypeface(textView4, context);
        textView5 = (TextView) findViewById(R.id.textview5_contactus);
        mainApplication.applyTypeface(textView5, context);
        textView6 = (TextView) findViewById(R.id.textview6_contactus);
        mainApplication.applyTypeface(textView6, context);
        textView7 = (TextView) findViewById(R.id.textview7_contactus);
        mainApplication.applyTypeface(textView7, context);
        textView8 = (TextView) findViewById(R.id.textview8_contactus);
        mainApplication.applyTypeface(textView8, context);

        imageViewOfficeLocation = (ImageView) findViewById(R.id.imageView_officelocation);

        imageViewOfficeLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String geoUri = "http://maps.google.com/maps?q=loc:" + 19.1066177 + "," + 72.8526924 + " (" + "Eduvanz" + ")";
//                geo:0,0?q=my+street+address
                String map = "http://maps.google.co.in/maps?q=" + "Eduvanz";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(map));
                context.startActivity(intent);

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
