package com.eduvanz.newUI.newViews;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.eduvanz.R;

public class Notification extends AppCompatActivity {

    Typeface typeface;
    TextView textViewArrowDown;
    TextView aaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        typeface = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        textViewArrowDown = (TextView) findViewById(R.id.texView_borrower_arrowdown1);
        textViewArrowDown.setTypeface(typeface);

        aaa = (TextView) findViewById(R.id.aaa);

        textViewArrowDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aaa.setMaxLines(10);
            }
        });
    }
}
