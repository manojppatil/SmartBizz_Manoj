package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.fragments.KycDetailFragment;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {
    private CircleImageView profileImage;
    private EditText firstName,middleName,lastName,email;
    private TextView mobile_number;
    private LinearLayout submit_button;
    ProgressBar progressBar;
    static String first_name,last_name,img_profile,email_id;
    public static AppCompatActivity mActivity;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = getApplicationContext();
        mActivity = this;


        profileImage = findViewById(R.id.profileImage);
        firstName = findViewById(R.id.edtFnameProfile);
        middleName = findViewById(R.id.edtMnameProfile);
        lastName = findViewById(R.id.edtLnameProfile);
        email = findViewById(R.id.edtProfileEmail);
        mobile_number = findViewById(R.id.edtProfileMobileNumber);
        submit_button = findViewById(R.id.linSubmit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_editProfile);

        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            first_name = sharedPreferences.getString("first_name", "");
            email_id = sharedPreferences.getString("email", "");
            last_name = sharedPreferences.getString("last_name", "");
            img_profile = sharedPreferences.getString("profile_image", "");

        } catch (Exception e) {
            e.printStackTrace();
        }

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditProfileData();
                finish();
            }
        });

        setProfileApiCall();

    }

    public void saveEditProfileData() {
        try{
            progressBar.setVisibility(View.VISIBLE);
            String url = MainActivity.mainUrl + "authorization/updateProfile";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId",DashboardActivity.student_id);
            params.put("email",email.getText().toString());
            params.put("fname",firstName.getText().toString());
            params.put("mname",middleName.getText().toString());
            params.put("lname",lastName.getText().toString());

            if (!Globle.isNetworkAvailable(context)){
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            }else {
                VolleyCall volleyCall = new VolleyCall();

                volleyCall.sendRequest(context,url,mActivity,null,"editProfileDetails",params,MainActivity.auth_token);
            }
        }catch (Exception e){
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void saveResponse(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

//            if (jsonData.getInt("status") == 1) {

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProfileApiCall(){
        String url = MainActivity.mainUrl + "authorization/profile";
        Map<String, String> params = new HashMap<String, String>();
        params.put("studentId",DashboardActivity.student_id);
        if (!Globle.isNetworkAvailable(context)) {
            Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
        } else {
            VolleyCall volleyCall = new VolleyCall();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
            volleyCall.sendRequest(context, url, mActivity, null, "setProfileDetails", params, MainActivity.auth_token);
        }
    }

    public void setProfileDetails(JSONObject jsonObject){
        String message = jsonObject.optString("message");
        try {
            if (jsonObject.getInt("status")==1){
                    JSONObject jsonObj =jsonObject.getJSONObject("result");
                    if (jsonObj.getString("first_name")!=null){
                        firstName.setText(jsonObj.getString("first_name"));
                    }
                    if (jsonObj.getString("last_name")!=null){
                        lastName.setText(jsonObj.getString("last_name"));
                    }
                    if (jsonObj.getString("email")!=null){
                        email.setText(jsonObj.getString("email"));
                    }
                    if (jsonObj.getString("mobile_no")!=null){
                        mobile_number.setText(jsonObj.getString("mobile_no"));
                    }
                    if (jsonObj.getString("img_profile")!=null){

                    }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
