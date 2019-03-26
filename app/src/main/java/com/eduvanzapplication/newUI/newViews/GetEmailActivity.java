package com.eduvanzapplication.newUI.newViews;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.eduvanzapplication.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.Arrays;

public class GetEmailActivity extends AppCompatActivity {

    private static final String TAG = GetEmailActivity.class.getSimpleName();
    EditText edtEmail,edtFirstName;
   LinearLayout linFacebook, linLinkedIn, linGoogle, linSubmitEmail;

   String userEmail = "";

   CallbackManager callbackManagerFb = CallbackManager.Factory.create();
    private static final String EMAIL = "email";
    LoginButton fbLoginButton;

    final private int RC_SIGN_IN = 112;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_email);
        setViews();
        setFacebookLogin();
    }

    private void setFacebookLogin() {
        fbLoginButton.setReadPermissions(Arrays.asList(EMAIL));
        fbLoginButton.registerCallback(callbackManagerFb, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Profile profile = Profile.getCurrentProfile();
                profile.getFirstName();
                Log.d("FBSUCESS","RESULT - "+profile.getFirstName() +""+profile.getId());
                edtFirstName.setText(profile.getFirstName());
                if (profile.getId().contains("@")){
                    edtEmail.setText(profile.getId());
                }else
                    Snackbar.make(linSubmitEmail, "Unable to get Email ID, Please enter manually",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("FBONERROR","ERROR - "+ error.toString());
            }
        });

    }

    private void setViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtFirstName=  findViewById(R.id.edtFirstName);
        linFacebook = findViewById(R.id.linFacebook);
        linLinkedIn = findViewById(R.id.linLinkedIn);
        linGoogle = findViewById(R.id.linGoogle);
        linSubmitEmail = findViewById(R.id.linSubmitEmail);
        fbLoginButton = findViewById(R.id.fb_login_button);

        linFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLoginButton.performClick();
            }
        });

        linGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGoogleLogin();
            }
        });

        linSubmitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GetEmailActivity.this, DashboardActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
    }

    private void setGoogleLogin() {
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManagerFb.onActivityResult(requestCode, resultCode, data); //facebook
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            userEmail =  account.getEmail();
            edtEmail.setText(userEmail);
            edtFirstName.setText(account.getDisplayName());
            Log.d(TAG,"Google Mail - "+userEmail);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }
}
