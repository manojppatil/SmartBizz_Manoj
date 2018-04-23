package com.eduvanz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.newUI.SharedPref;
import com.eduvanz.volley.VolleyCall;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button buttonLogin;
    AppCompatActivity mActivity;
    ProgressDialog mDialogBar;
    String user_email = "", user_passWord = "", user_id="", user_name="", user_firstname="",
                        user_lastname="", user_type="", user_profilepic="";
    SharedPref shareP;
    TextView textViewCheckForEligibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR
        mActivity = this;

        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        buttonLogin = (Button) findViewById(R.id.btn_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_email = email.getText().toString();
                user_passWord = password.getText().toString();

                    String url = MainApplication.mainUrl + "authorization/email_login";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("username", user_email);
                    params.put("password", user_passWord);
                    VolleyCall volleyCall = new VolleyCall();
                    mDialogBar = new ProgressDialog(Login.this, R.style.AppTheme_Dark_Dialog);
                    mDialogBar.setMessage("Authenticating... ");
                    mDialogBar.setCancelable(false);
                    mDialogBar.show();
                    volleyCall.sendRequest(Login.this, url, mActivity, null, "logInPage", params);
            }
        });

        textViewCheckForEligibility = (TextView) findViewById(R.id.textview_checkforeligibility);
        textViewCheckForEligibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Main2ActivityNavigation.class);
                Bundle bundle = new Bundle();
                bundle.putString("checkfor_eligibility", "1");
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
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

    //------------------------------------RESPONSE OF API CALL------------------------------------//
    public void logIn(JSONObject jsonData) {
        try {

            Log.e("SERVER", "logIn" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");
                mDialogBar.dismiss();
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
                editor.apply();
                editor.commit();

                Log.e(MainApplication.TAG, "signIn: "+user_id+"    " + user_firstname+ "       "+
                        user_lastname+"           "+user_email+"        "+ user_id+"         ");

                Intent intent = new Intent(Login.this, Main2ActivityNavigation.class);
                startActivity(intent);
                finish();
            }else {
                mDialogBar.dismiss();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
