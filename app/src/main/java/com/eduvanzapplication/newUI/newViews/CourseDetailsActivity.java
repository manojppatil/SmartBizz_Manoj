package com.eduvanzapplication.newUI.newViews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.eduvanzapplication.R;

public class CourseDetailsActivity extends AppCompatActivity {

    private ImageView ivNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        setViews();
    }

    private void setViews() {
        ivNextBtn = findViewById(R.id.ivNextBtn);


        ivNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CourseDetailsActivity.this, TenureSelectionActivity.class));
            }
        });
    }
}
