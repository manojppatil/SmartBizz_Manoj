package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;
import com.eduvanz.newUI.SharedPref;
import com.eduvanz.newUI.VolleyCallNew;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignIn extends AppCompatActivity {

    TextView textViewUserName, textView1, textView2, textView3;
    Button buttonSignIn;
    EditText editTextEmailId, editTextPassword;
    MainApplication mainApplication;
    Context context;
    AppCompatActivity mActivity;
    public static ProgressBar progressBar;
    String user_email = "", user_passWord = "", user_id="", user_name="", user_firstname="",
            user_lastname="", user_type="", user_profilepic="";
    SharedPref shareP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mainApplication = new MainApplication();
        context = this;
        mActivity = this;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Sign In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        progressBar = (ProgressBar) findViewById(R.id.progressBar_emailsignin);
        textViewUserName = (TextView) findViewById(R.id.textView_signin_username);
        mainApplication.applyTypeface(textViewUserName, context);
        textView1 = (TextView) findViewById(R.id.textView_signin_1);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) findViewById(R.id.textView_signin_2);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) findViewById(R.id.textView_signin_3);
        mainApplication.applyTypeface(textView3, context);
        buttonSignIn = (Button) findViewById(R.id.button_signin_signin);
        mainApplication.applyTypeface(buttonSignIn, context);

        editTextEmailId = (EditText) findViewById(R.id.editText_emalID);
        editTextPassword = (EditText) findViewById(R.id.editText_password);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** API CALL **/
                try {
                    progressBar.setVisibility(View.VISIBLE);
                    String url = MainApplication.mainUrl + "authorization/email_login";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username",editTextEmailId.getText().toString());
                    params.put("password",editTextPassword.getText().toString());
                    VolleyCallNew volleyCall = new VolleyCallNew();
                    volleyCall.sendRequest(context, url, mActivity, null, "emailSignIn", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /** END OF API CALL **/            }
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


    /**RESPONSE OF API CALL **/
    public void emailSignin(JSONObject jsonData) {
        try {

            Log.e("SERVER", "logIn" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");
                progressBar.setVisibility(View.GONE);
                user_email = jsonObject.optString("email");
                user_id = jsonObject.optString("logged_id");
                user_firstname = jsonObject.optString("first_name");
                user_lastname = jsonObject.optString("last_name");
                user_type= jsonObject.optString("user_type");
                user_profilepic = jsonObject.optString("img_profile");

                shareP =new SharedPref();
                shareP.setLoginDone(this,true);

                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("logged_id",user_id);
                editor.putString("first_name",user_firstname);
                editor.putString("last_name",user_lastname);
                editor.putString("user_type",user_type);
                editor.putString("user_image",user_profilepic);
                editor.putString("user_email",user_email);
                editor.commit();
                Log.e(MainApplication.TAG, "signIn: "+user_id+"    " + user_firstname+ "       "+
                        user_lastname+"           "+user_email+"        "+ user_id+"         ");

                Intent intent = new Intent(SignIn.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }else {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
