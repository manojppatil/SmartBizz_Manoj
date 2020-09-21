package com.smartbizz.newUI.newViews;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.smartbizz.R;

public class GetEmailActivity extends AppCompatActivity {

    private static final String TAG = GetEmailActivity.class.getSimpleName();
    EditText edtEmail,edtFirstName;
   LinearLayout linSubmitEmail;

   String userEmail = "";

    private static final String EMAIL = "email";

    final private int RC_SIGN_IN = 112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_email);
        setViews();
    }

    private void setViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtFirstName=  findViewById(R.id.edtFirstName);
        linSubmitEmail = findViewById(R.id.linSubmitEmail);

        linSubmitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetEmailActivity.this, DashboardActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

}
