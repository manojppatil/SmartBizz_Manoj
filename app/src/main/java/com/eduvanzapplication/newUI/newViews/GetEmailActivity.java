package com.eduvanzapplication.newUI.newViews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eduvanzapplication.R;

public class GetEmailActivity extends AppCompatActivity {

   EditText edtEmail;
   LinearLayout linFacebook, linLinkedIn, linGoogle, linSubmitEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_email);
        setViews();
    }

    private void setViews() {
        edtEmail = findViewById(R.id.edtEmail);
        linFacebook = findViewById(R.id.linFacebook);
        linLinkedIn = findViewById(R.id.linLinkedIn);
        linGoogle = findViewById(R.id.linGoogle);
        linSubmitEmail = findViewById(R.id.linSubmitEmail);
    }
}
