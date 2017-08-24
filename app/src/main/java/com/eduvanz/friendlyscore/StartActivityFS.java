package com.eduvanz.friendlyscore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.eduvanz.R;

public class StartActivityFS extends AppCompatActivity implements View.OnClickListener {

    private Button newUser;
    private Button existingUser;
    private String appid = "1565";//Your Friendly Score App Id. Its a number stored as string.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        newUser = (Button)findViewById(R.id.newUser);
        existingUser = (Button)findViewById(R.id.currentUser);

        newUser.setOnClickListener(this);
        existingUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent clientIntent = null;
        if(view.getId() == R.id.newUser){
                clientIntent = new Intent(this,ClientActivity.class);
            //If client has one user per device
            clientIntent.putExtra("currentUser",false);
        }
        else if(view.getId() == R.id.currentUser){
             clientIntent = new Intent(this,ClientActivity.class);
            clientIntent.putExtra("currentUser",true);
            //If client has multiple users per device
            //clientIntent.putExtra("friendlyScoreUser",userObject);
            //The last state of User Object received in ClientActivity after user has completed sign in
        }

        //Passing the app id to ClientActvity
        clientIntent.putExtra("app_id",appid);

        startActivity(clientIntent);
    }
}
