package com.eduvanz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.eduvanz.pqformfragments.PqFormFragment1;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportFragmentManager().beginTransaction().add(R.id.framelayout_pqform,new PqFormFragment1()).commit();
    }
}
