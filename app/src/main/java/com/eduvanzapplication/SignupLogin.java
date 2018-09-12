package com.eduvanzapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignupLogin extends AppCompatActivity {

    Button buttonSignIn;
    TextView textViewCheckEligibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);

        buttonSignIn = (Button) findViewById(R.id.button_signin);
        textViewCheckEligibility = (TextView) findViewById(R.id.textview_checkforeligibility);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupLogin.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        textViewCheckEligibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2ActivityNavigation.class);
                Bundle bundle = new Bundle();
                bundle.putString("checkfor_eligibility", "1");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }
}
