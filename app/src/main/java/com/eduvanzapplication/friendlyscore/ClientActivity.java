package com.eduvanzapplication.friendlyscore;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.FriendlyScoreUI.LaunchUI;
import com.eduvanzapplication.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import FriendlyScore.AppErrorForClient;
import FriendlyScore.Constants;
import FriendlyScore.Credentials;
import FriendlyScore.ErrorForUser;
import FriendlyScore.ReceivedTokenScore;
import FriendlyScore.ScoreCompleted;
import FriendlyScore.SendToFriendlyScore;


public class ClientActivity extends LaunchUI implements
        GoogleApiClient.OnConnectionFailedListener {
//A4:EB:50:BB:83:56:4C:6A:8C:9E:79:2F:08:AF:52:24:AC:77:E8:C7

    private static final String TAG = "ClientActivity";
    private static final int GOOGLE_SIGN_IN_CODE = 9001;
    /*
   Facebook
    */
    private static final int REQUEST_FACEBOOK_CODE = 1000;
    /*
       Linkedin Auth Variables
    */
    private static String linkedinTopCardUrl = Constants.LINKEDIN_API_URL;
    private final String SESSION_KEY_PREF = "SESSION_KEY_PREF";
    private final String USER_KEY_PREF = "USER_KEY_PREF";
    Context mContext;
    /*
        Google Auth Variables
     */
    private GoogleApiClient mGoogleApiClient;
    private String idToken = null;
    private String SESSION_KEY_HEADER = "session-key";


    /*

    Paypal
     */

    //
//    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
//
//    private static String FSCORE_PROD_CONFIG_CLIENT_ID = "251611";
//    private static final int REQUEST_CODE_PROFILE_SHARING = 3;
//    private static PayPalConfiguration config = new PayPalConfiguration()
//
//            .environment(CONFIG_ENVIRONMENT)
//            .clientId(FSCORE_PROD_CONFIG_CLIENT_ID)
//            .merchantName("Example Merchant")
//            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
//            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
    private String USER_KEY_HEADER = "current-user-state";
    private CallbackManager fbCallbackManager;

    /*
        Twitter;

     */
    private TwitterLoginButton twitterLoginbutton;

    //Add to Client Instructions
    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.RW_COMPANY_ADMIN, Scope.R_EMAILADDRESS, Scope.W_SHARE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        checkGooglePlayServicesVersion();

        //Toolbar toolbar = new Toolbar(this);

        //setSupportActionBar(toolbar);

        Intent clientIntent = getIntent();
        boolean currentUser = clientIntent.getBooleanExtra("currentUser", false);


        setUpGoogle();

//        paypalSetUp();

        setUpFacebook();

        setUpTwitter();

        Log.d(TAG, "Session Key: " + getSessionKey());

        String appid = clientIntent.getStringExtra("app_id");
//        Log.e("FRIENDLY SCORE", appid);
        Credentials credentials = null;
        String session_key = null;
        mContext = this;
        if (currentUser) {


            if (clientIntent.hasExtra("friendlyScoreUser")) {
                //Multiple Users per device, so client has to store User Object
                //That has session key in a sqlite database and pass it on.
                session_key = new Gson().fromJson(clientIntent.getStringExtra("friendlyScoreUser"), ReceivedTokenScore.class).session_key;

            } else {
                //One user per device. So Session Key for that user is unique
                session_key = getSessionKey();
            }


        }
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);

        }

        //Log.d(TAG,session_key);
        credentials = new Credentials(appid, session_key);

        credentials.getInstance();

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.client_menu, menu);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    // [START on_stop_remove_listener]
    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        Log.e(TAG, "onActivityResult: " + data);
        Log.e(TAG, "onActivityResult: " + resultCode);
        Log.e(TAG, "onActivityResult: " + requestCode);
        if (requestCode == GOOGLE_SIGN_IN_CODE) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.e(TAG, "onActivityResult: " + result.isSuccess());
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                handleGoogleAuthorization(account);
            } else {

                userAuthError(Constants.GOOGLE_ID);
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
                //updateUI(null);
                // [END_EXCLUDE]
            }
        }
//        else if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
//            if (resultCode == Activity.RESULT_OK) {
//                PayPalAuthorization auth =
//                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
//                if (auth != null) {
//                    try {
//
//                        Log.d(TAG, auth.toJSONObject().toString());
//                        sendPayPalAuthorizationToServer(auth.getAuthorizationCode());
//
//                    } catch (Exception e) {
//                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
//                        userAuthError(Constants.PAYPAL_ID);
//                    }
//                }
//            } else if (resultCode == Activity.RESULT_CANCELED) {
//                userAuthError(Constants.PAYPAL_ID);
//
//                Log.i("ProfileSharingExample", "The user canceled.");
//            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
//                userAuthError(Constants.PAYPAL_ID);
//
//            }
//        }
        else if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            fbCallbackManager.onActivityResult(requestCode, resultCode, data);

        }
//        else if (requestCode == Activity.Result_CANCELLED.) {
//            fbCallbackManager.onActivityResult(requestCode, resultCode, data);
//        }
        else if (requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) {
            twitterLoginbutton.onActivityResult(requestCode, resultCode, data);
        } else {
            //Linkedin Response
            LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        }
    }

    //Call loginonClick on button Click. You will receive response in onActivityResult method
    public void loginonClick() {

        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {

            @Override
            public void onAuthSuccess() {
                Log.e(TAG, "onAuthSuccess: ");
                callLinkedInApi();
                //Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(this).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAuthError(LIAuthError error) {
                //setUpdateState();
                Log.e(TAG, "onAuthSuccess:Linked IN  " + error);
                userAuthError(Constants.LINKEDIN_ID);


            }
        }, true);
    }

    private void callLinkedInApi() {

        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
        apiHelper.getRequest(this, linkedinTopCardUrl, new ApiListener() {
            @Override
            public void onApiSuccess(ApiResponse s) {
                Log.e(TAG, "onApiSuccess");
                handleAuthorization(s);//access toke is not set
            }

            @Override
            public void onApiError(LIApiError error) {
                //((TextView) findViewById(R.id.response)).setText(error.toString());
                Log.e(TAG, "onApiSuccess" + error);
                userAuthError(Constants.LINKEDIN_ID);

            }
        });
    }

    private void handleAuthorization(ApiResponse linkedAuthResponse) {

        Log.d(TAG, linkedAuthResponse.getResponseDataAsString());
        new SendToFriendlyScore(this).execute(linkedAuthResponse.getResponseDataAsString().toString(), "" + Constants.LINKEDIN_ID);


    }

    //Abstract Method from Superclass
    @Override
    public void setClicked(int networkType, boolean connected) {

        //Log.d(TAG,event.networkType);

        switch (networkType) {
            case Constants.GOOGLE_ID:
                signInWithGoogle();
                break;
            case Constants.TWITTER_ID:
                executeTwitterLogin();
                break;
            case Constants.LINKEDIN_ID:
                loginonClick();
                break;
            case Constants.PAYPAL_ID:
//                onProfileSharing();
                break;
            case Constants.FACEBOOKL_ID:
                fbLogin();
                break;
            case Constants.INSTAGRAM_ID:
//                loginWithInstagram();
                break;
        }

        /* Do something */
    }

    //    private void loginWithInstagram() {
//        String scope = "basic+public_content+follower_list+comments+relationships+likes";
////scope is for the permissions
//        instagramHelper = new InstagramHelper.Builder()
//                .withClientId(INSTAGRAM_CLIENT_ID)
//                .withRedirectUrl(INSTAGRAM_CALLBACK_URL)
//
//                .build();
//
//        instagramHelper.loginFromActivity(this);
//
//    }


    private void signInWithGoogle() {
        mGoogleApiClient.clearDefaultAccountAndReconnect();
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_CODE);
    }

    private void setUpGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id1))
                .requestServerAuthCode(getString(R.string.default_web_client_id1))
                .requestScopes(
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[0]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[1]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[2]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[3]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[4]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[5]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[6]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[7]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[8]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[9]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[10]),
                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[11])
                )

                .requestEmail()
                .build();


        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }


    //Send Data to FriendlyScore
    private void handleGoogleAuthorization(GoogleSignInAccount acct) {

        Log.e(TAG, "display name: " + acct.getDisplayName());
        new GoogleServerAuthCodeTask(this).execute(acct.getEmail(), "com.google", acct.getIdToken());

        idToken = acct.getIdToken();
        //getAccessToken(acct.getEmail());

        Log.e(TAG + "token id", acct.getIdToken());

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.e(TAG, "onConnectionFailed:" + connectionResult);
        //Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }


    /*
        Paypal Process
     */
//
//    private void paypalSetUp(){
//        Intent intent = new Intent(this, PayPalService.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        startService(intent);
//    }
//
//    private void onProfileSharing() {
//        Intent intent = new Intent(ClientActivity.this, PayPalProfileSharingActivity.class);
//
//        // send the same configuration for restart resiliency
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//        intent.putExtra(PayPalProfileSharingActivity.EXTRA_REQUESTED_SCOPES, getOauthScopes());
//
//        startActivityForResult(intent, REQUEST_CODE_PROFILE_SHARING);
//    }
//
//    private PayPalOAuthScopes getOauthScopes() {
//        /* create the set of required scopes
//         * Note: see https://developer.paypal.com/docs/integration/direct/identity/attributes/ for mapping between the
//         * attributes you select for this app in the PayPal developer portal and the scopes required here.
//         */
//        //Log.d("ProfileSharingExample",Constants.PAYPAL_SCOPES);
//        Set<String> scopes = new HashSet<String>(Constants.PAYPAL_SCOPES);
//        return new PayPalOAuthScopes(scopes);
//    }
//
//    private void sendPayPalAuthorizationToServer(String authorization_code) {
//
//        new SendToFriendlyScore(this).execute(authorization_code,""+Constants.PAYPAL_ID);
//
//    }


    /*
        Facebook Process

     */

    private void setUpFacebook() {
        FacebookSdk.sdkInitialize(getApplicationContext());

        FacebookSdk.setIsDebugEnabled(true);
        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
    }

    private void fbLogin() {
        fbCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(Constants.FACEBOOK_PERMISSIONS));

        LoginManager.getInstance().registerCallback(fbCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");

                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(ClientActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                        //If there was error with Facebook Process, due to user canceling
                        userAuthError(Constants.FACEBOOKL_ID);

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(ClientActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        //If there was error with Facebook Process, due to other reasons

                        userAuthError(Constants.FACEBOOKL_ID);

                    }
                });


    }


    // [START auth_with_facebook]
    private void handleFacebookAccessToken(final AccessToken token) {

        handleFBAuthorization(token);

    }


    //Send FB Token to FriendlyScore
    private void handleFBAuthorization(final AccessToken token) {
        new SendToFriendlyScore(this).execute(token.getToken(), "" + Constants.FACEBOOKL_ID);
    }


    /*

        Twitter
     */

    private void setUpTwitter() {
        // Configure Twitter SDK
//        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
//                getString(R.string.twitter_consumer_key),
//                getString(R.string.twitter_consumer_secret));
//        Fabric.with(this, new Twitter(authConfig));

        Log.e(TAG, "setUpTwitter: twitter_consumer_key  " + getString(R.string.twitter_consumer_key));
        Log.e(TAG, "setUpTwitter: twitter_consumer_secret   " + getString(R.string.twitter_consumer_secret));
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                        getString(R.string.twitter_consumer_secret)))
                .debug(true)
                .build();
        Twitter.initialize(config);

    }

    private void executeTwitterLogin() {
        Log.e(TAG, "executeTwitterLogin: ");
        twitterLoginbutton = new TwitterLoginButton(this);

        twitterLoginbutton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                handleTwitterSession(result.data);
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                userAuthError(Constants.TWITTER_ID);

            }
        });

        //Artificially Generate Click, this is so we can use customized ui buttons else we would be forced to use twitter ui.
        twitterLoginbutton.performClick();
    }

    //     [START auth_with_twitter]
    private void handleTwitterSession(final TwitterSession session) {

        handleTwitterAuthorization(session);

    }
    // [END auth_with_twitter] by sending token to Friendly Score

    private void handleTwitterAuthorization(final TwitterSession session) {

        //https://friendlyscore.com/api/sdk/1565/1/token.json
//        https://friendlyscore.com/sdk-api/v2/send-token/twitter/android.json

        new SendToFriendlyScore(this).execute(String.valueOf(session.getUserId()), "" + Constants.TWITTER_ID);
    }

    //The current user state is stored. The token received when any authentication is completed.
    //The user state can be used to update UI based on your choice.
    private void storeCurrentUserState(ReceivedTokenScore receivedTokenScore) {

        //It is store using key-pairs. Private Mode ensures it is available only to your app.
        SharedPreferences sessionPreferences = this.getSharedPreferences(USER_KEY_PREF, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sessionPreferences.edit();
        editor.putString(USER_KEY_HEADER, new Gson().toJson(receivedTokenScore));
        editor.commit();
    }

    //The session is used by to connect your user to Friendly Score. So for a repeat user, the previous authentication
    //and scores are available.
    private String getSessionKey() {
        SharedPreferences sessionPreferences = this.getSharedPreferences(SESSION_KEY_PREF, Context.MODE_PRIVATE);

        String session_key = sessionPreferences.getString(SESSION_KEY_HEADER, null);

        Log.d(TAG, "Sesssion Key:" + session_key);
        return session_key;
    }

    //Store the session key returned by SDK so you can identify the user to Friendly Score again.
    private void storeSessionKey(String session_key) {

        SharedPreferences sessionPreferences = this.getSharedPreferences(SESSION_KEY_PREF, Context.MODE_PRIVATE);

        Log.d(TAG, "Sesssion Key:" + session_key);

        SharedPreferences.Editor editor = sessionPreferences.edit();
        editor.putString(SESSION_KEY_HEADER, session_key);
        editor.commit();
    }


    //Abstract Method from Superclass


    //The function is triggered when the process of "See Your Score" is finished and the SDK passes
    //for the user to your app. "See Your Score" at the bottom of the screen is visible
    //only when the user has authenticated with the required minimum number of data points(Social Networks).

    /** sample output of scoreCompleted object
     * {
     "data": {
     "twitter": {
     "id": 1,
     "name": "twitter",
     "score_percent": 60
     }
     },
     "max_score": 1000,
     "photo_url": "https://media.licdn.com/mpr/mprx/0_Pk1lNNAAt9gjNr7gt6OxAzTPPLhjP-_gtqSptvxgOTTYN1A1rNtgrx7jR8F",
     "score_percent": 0.0,
     "total_score": 735,
     "user_id": "41508",
     "user_session_key": "59ccd6f04b512"
     }**/
    @Override
    public void getCompletedScore(ScoreCompleted scoreCompleted) {
        Log.d("ClientActivity", "getCompletedScore" + scoreCompleted.userObject);
        try {
            JSONObject jsonObject = new JSONObject(scoreCompleted.userObject);

            String maxScore = jsonObject.getString("total_score");
            Log.e(TAG, "getCompletedScore: "+maxScore );

            // Create custom dialog object
            final Dialog dialog = new Dialog(ClientActivity.this);
            // Include dialog.xml file
            dialog.setContentView(R.layout.dialog);
            // Set dialog title
            dialog.setTitle("Your Score");

            // set values for custom dialog components - text, image and button
            TextView text = (TextView) dialog.findViewById(R.id.textDialog);
//            text.setText("Custom dialog Android example.");
            ImageView image = (ImageView) dialog.findViewById(R.id.imageDialog);
//            image.setImageResource(R.drawable.image0);

            dialog.show();

            Button declineButton = (Button) dialog.findViewById(R.id.declineButton);
            // if decline button is clicked, close the custom dialog
            declineButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Close dialog
                    dialog.dismiss();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    //The method is called when the process of authetication for any of the data networks is completed, i.e.
    //each time when Facebook authentication is completed, Google authentication is completed etc.
    @Override
    public void handleReceivedTokenScore(ReceivedTokenScore receivedTokenScore) {
        storeCurrentUserState(receivedTokenScore);

        storeSessionKey(receivedTokenScore.session_key);
        Log.d(TAG, new Gson().toJson(receivedTokenScore));
        // DO NOT CLOSE OR CHANGE UI
    }

    //The method is called when the SDK identifies errors that are primarily associated
    //with your implementation of the app or the SDK.
    @Override
    public void appErrorMessage(AppErrorForClient appErrorForClient) {
        Log.d(TAG, String.valueOf(appErrorForClient.getErrorNo()));
        String message = null;
        String title = null;
        switch (appErrorForClient.getErrorNo()) {
            case AppErrorForClient.app_id_not_found:
                title = "Check your App Id on Friendly Score";
                message = "Contact App Developer";
                showErrorDialog(message, title);
                break;
            case AppErrorForClient.session_key_not_found:
                title = "User not found";
                message = "Check user data";
                showErrorDialog(message, title);
                break;
            case AppErrorForClient.internalServerError:
                title = "Problem with our servers";
                message = "There was problem with our servers, try again soon";
                showErrorDialog(message, title);
                break;
        }

    }

    //The method is called when the SDK identifies errors that are primarily associated
    //with problems with user interaction with the SDK, such as no internet, no data for
    //computing score, slow internet connection.
    @Override
    public void userErrorMessage(ErrorForUser errorForUser) {
        Log.d(TAG, errorForUser.getErrorMessage());
        String message = null;
        String title = null;
        switch (errorForUser.getErrorNo()) {
            case ErrorForUser.no_internet:
                title = "no internet";
                message = "Check Your Internet Connection";
                showErrorDialog(message, title);

                break;
            case ErrorForUser.scoreCalculationError:
                title = "Problem Calculating Score";
                message = "There was problem calculating your score, try again in a few moments";
                showErrorDialog(message, title);
                break;
            case ErrorForUser.noDataToScore:
                title = "Problem Calculating Score";
                message = "Your Profile does not have enough data to calculate the score";
                showErrorDialog(message, title);

                break;
            case ErrorForUser.poorInternetConnectionError:
                title = "Problem Calculating Score";
                message = getResources().getString(R.string.poor_internet_connection);
                showErrorDialog(message, title);
                break;
        }
    }

    @Override
    public boolean checkGooglePlayServicesVersion() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        switch (status) {
            case ConnectionResult.SUCCESS:
                return true;
            case ConnectionResult.SERVICE_MISSING:
                showGooglePlayServicesErrorAlert(
                        getResources().getString(R.string.no_google_play_services),
                        getResources().getString(R.string.no_google_play_services_title));

            case ConnectionResult.SERVICE_UPDATING:
                showGooglePlayServicesErrorAlert(
                        getResources().getString(R.string.google_play_services_updating),
                        getResources().getString(R.string.google_play_services_updating_title));

            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                showGooglePlayServicesErrorAlert(
                        getResources().getString(R.string.google_play_services_update),
                        getResources().getString(R.string.google_play_services_title_update));

            case ConnectionResult.SERVICE_DISABLED:
                showGooglePlayServicesErrorAlert(
                        getResources().getString(R.string.google_play_services_disabled),
                        getResources().getString(R.string.google_play_services_disabled_title));

            case ConnectionResult.SERVICE_INVALID:
                showGooglePlayServicesErrorAlert(
                        getResources().getString(R.string.google_play_services_invalid),
                        getResources().getString(R.string.google_play_services_invalid_title));

        }
        //finish();

        return false;
    }

    //The function displays the dialog when any of the error methods
    //described earlier are triggered.
    private void showErrorDialog(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message)
                .setTitle(title);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                //activity.finish();
                finish();
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //activity.finish();
                //finish();
            }
        });
        dialog.show();
    }

    //The function is needed to ensure that Google Play Services, required for Google Authentication
    //are upto date.
    private void showGooglePlayServicesErrorAlert(String message, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(message)
                .setTitle(title);
        // Add the buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                //activity.finish();
                finish();
            }
        });

        // Create the AlertDialog
        AlertDialog dialog = builder.create();

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //activity.finish();
                finish();
            }
        });
        dialog.show();
    }

}


//package com.eduvanz.friendlyscore;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.v7.app.AlertDialog;
//import android.util.Log;
//import android.view.Menu;
//import android.widget.Toast;
//
//import com.FriendlyScoreUI.LaunchUI;
//import com.eduvanz.R;
//import com.facebook.AccessToken;
//import com.facebook.CallbackManager;
//import com.facebook.FacebookCallback;
//import com.facebook.FacebookException;
//import com.facebook.FacebookSdk;
//import com.facebook.LoggingBehavior;
//import com.facebook.internal.CallbackManagerImpl;
//import com.facebook.login.LoginManager;
//import com.facebook.login.LoginResult;
//import com.google.android.gms.auth.api.Auth;
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
//import com.google.android.gms.auth.api.signin.GoogleSignInResult;
//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesUtil;
//import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.gson.Gson;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//import FriendlyScore.AppErrorForClient;
//import FriendlyScore.Constants;
//import FriendlyScore.Credentials;
//import FriendlyScore.ErrorForUser;
//import FriendlyScore.ReceivedTokenScore;
//import FriendlyScore.ScoreCompleted;
//import FriendlyScore.SendToFriendlyScore;
//
//public class ClientActivity extends LaunchUI implements
//        GoogleApiClient.OnConnectionFailedListener {
//
//
//    private static final String TAG = "ClientActivity" ;
//    /*
//        Google Auth Variables
//
//
//     */
//    private GoogleApiClient mGoogleApiClient;
//
//    private static final int GOOGLE_SIGN_IN_CODE = 9001;
//
//    private String idToken = null;
//
//
//    private final String SESSION_KEY_PREF = "SESSION_KEY_PREF";
//
//    private  String SESSION_KEY_HEADER = "session-key";
//
//
//    private final String USER_KEY_PREF = "USER_KEY_PREF";
//
//    private  String USER_KEY_HEADER = "current-user-state";
//
//
//
//    /*
//       Linkedin Auth Variables
//    */
//    private static String linkedinTopCardUrl = Constants.LINKEDIN_API_URL;
//
//
//    /*
//
//    Paypal
//     */
//
////
////    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;
////
////    private static String FSCORE_PROD_CONFIG_CLIENT_ID = "251611";
////    private static final int REQUEST_CODE_PROFILE_SHARING = 3;
////    private static PayPalConfiguration config = new PayPalConfiguration()
////
////            .environment(CONFIG_ENVIRONMENT)
////            .clientId(FSCORE_PROD_CONFIG_CLIENT_ID)
////            .merchantName("Example Merchant")
////            .merchantPrivacyPolicyUri(Uri.parse("https://www.example.com/privacy"))
////            .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));
//
//
//    /*
//   Facebook
//    */
//    private static final int REQUEST_FACEBOOK_CODE = 1000;
//    private CallbackManager fbCallbackManager;
//
//    /*
//        Twitter;
//
//     */
////    private TwitterLoginButton twitterLoginbutton;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//
//        checkGooglePlayServicesVersion();
//
//        //Toolbar toolbar = new Toolbar(this);
//
//        //setSupportActionBar(toolbar);
//
//        Intent clientIntent = getIntent();
//        boolean currentUser  = clientIntent.getBooleanExtra("currentUser", false);
//
//
//
//        setUpGoogle();
//
////        paypalSetUp();
//
//        setUpFacebook();
//
////        setUpTwitter();
//
//        Log.d(TAG,"Session Key: "+getSessionKey());
//
//        String appid = clientIntent.getStringExtra("app_id");
//        Credentials credentials = null;
//        String session_key = null;
//        if(currentUser){
//
//
//            if(clientIntent.hasExtra("friendlyScoreUser")){
//                //Multiple Users per device, so client has to store User Object
//                //That has session key in a sqlite database and pass it on.
//                session_key = new Gson().fromJson(clientIntent.getStringExtra("friendlyScoreUser"), ReceivedTokenScore.class).session_key;
//
//            }else{
//                //One user per device. So Session Key for that user is unique
//                session_key = getSessionKey();
//            }
//
//
//        }
//        //Log.d(TAG,session_key);
//        credentials = new Credentials(appid,session_key);
//
//        credentials.getInstance();
//
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.client_menu, menu);
//        return true;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//
//    }
//
//    // [START on_stop_remove_listener]
//    @Override
//    public void onStop() {
//        super.onStop();
//
//    }
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == GOOGLE_SIGN_IN_CODE) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
//            if (result.isSuccess()) {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = result.getSignInAccount();
//
//
//                handleGoogleAuthorization(account);
//            } else {
//
//                userAuthError(Constants.GOOGLE_ID);
//                // Google Sign In failed, update UI appropriately
//                // [START_EXCLUDE]
//                //updateUI(null);
//                // [END_EXCLUDE]
//            }
//        }
////        else if (requestCode == REQUEST_CODE_PROFILE_SHARING) {
////            if (resultCode == Activity.RESULT_OK) {
////                PayPalAuthorization auth =
////                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
////                if (auth != null) {
////                    try {
////
////                        Log.d(TAG, auth.toJSONObject().toString());
////                        sendPayPalAuthorizationToServer(auth.getAuthorizationCode());
////
////                    } catch (Exception e) {
////                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
////                        userAuthError(Constants.PAYPAL_ID);
////                    }
////                }
////            } else if (resultCode == Activity.RESULT_CANCELED) {
////                userAuthError(Constants.PAYPAL_ID);
////
////                Log.i("ProfileSharingExample", "The user canceled.");
////            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
////                userAuthError(Constants.PAYPAL_ID);
////
////            }
////        }
//        else if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
//            fbCallbackManager.onActivityResult(requestCode, resultCode, data);
//        }
////        else if(requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE){
////            twitterLoginbutton.onActivityResult(requestCode, resultCode, data);
////        }
////        else {
////
////            LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
////        }
//    }
////    public  void loginonClick(){
////
////        LISessionManager.getInstance(getApplicationContext()).init(this, buildScope(), new AuthListener() {
////
////            @Override
////            public void onAuthSuccess() {
////
////                callLinkedInApi();
////                //Toast.makeText(getApplicationContext(), "success" + LISessionManager.getInstance(this).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
////            }
////            @Override
////            public void onAuthError(LIAuthError error) {
////                //setUpdateState();
////                userAuthError(Constants.LINKEDIN_ID);
////
////
////            }
////        }, true);
////    }
//
////    private void callLinkedInApi(){
////
////        APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
////        apiHelper.getRequest(this, linkedinTopCardUrl, new ApiListener() {
////            @Override
////            public void onApiSuccess(ApiResponse s) {
////                Log.d(TAG,"onApiSuccess");
////
////                handleAuthorization(s);
////            }
////
////            @Override
////            public void onApiError(LIApiError error) {
////                //((TextView) findViewById(R.id.response)).setText(error.toString());
////                userAuthError(Constants.LINKEDIN_ID);
////
////            }
////        });
////    }
//
////    private void handleAuthorization(ApiResponse linkedAuthResponse){
////
////        Log.d(TAG,linkedAuthResponse.getResponseDataAsString());
////        new SendToFriendlyScore(this).execute(linkedAuthResponse.getResponseDataAsString().toString(), ""+ Constants.LINKEDIN_ID);
////
////
////    }
////
////    //Add to Client Instructions
////    private static Scope buildScope() {
////        return Scope.build(Scope.R_BASICPROFILE, Scope.RW_COMPANY_ADMIN, Scope.R_EMAILADDRESS, Scope.W_SHARE);
////    }
//
//    //Abstract Method from Superclass
//    @Override
//    public void setClicked(int networkType, boolean connected) {
//
//        //Log.d(TAG,event.networkType);
//
//        switch (networkType){
//            case Constants.GOOGLE_ID:
//                signInWithGoogle();
//                break;
//            case Constants.TWITTER_ID:
////                executeTwitterLogin();
//                break;
//            case Constants.LINKEDIN_ID:
////                loginonClick();
//                break;
//            case Constants.PAYPAL_ID:
////                onProfileSharing();
//                break;
//            case Constants.FACEBOOKL_ID:
//                fbLogin();
//                break;
//        }
//
//        /* Do something */}
//
//
//
//    ;
//
//
//    private void signInWithGoogle(){
//        mGoogleApiClient.clearDefaultAccountAndReconnect();
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
//        startActivityForResult(signInIntent, GOOGLE_SIGN_IN_CODE);
//    }
//    private void setUpGoogle(){
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestServerAuthCode(getString(R.string.default_web_client_id))
//                .requestScopes(
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[0]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[1]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[2]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[3]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[4]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[5]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[6]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[7]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[8]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[9]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[10]),
//                        new com.google.android.gms.common.api.Scope(Constants.GOOGLE_SCOPES[11])
//                )
//
//                .requestEmail()
//                .build();
//
//
//
//        // [END config_signin]
//
//
//
//        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
//                .build();
//
//
//    }
//
//
//    //Send Data to FriendlyScore
//    private void handleGoogleAuthorization(GoogleSignInAccount acct){
//
//
//        new GoogleServerAuthCodeTask(this).execute(acct.getEmail(),"com.google",acct.getIdToken());
//
//
//
//        idToken = acct.getIdToken();
//        //getAccessToken(acct.getEmail());
//
//        Log.d(TAG,acct.getIdToken());
//
//    }
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
//        // be available.
//        Log.d(TAG, "onConnectionFailed:" + connectionResult);
//        //Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
//    }
//
//
//
//    /*
//        Paypal Process
//     */
//
////    private void paypalSetUp(){
////        Intent intent = new Intent(this, PayPalService.class);
////        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
////        startService(intent);
////    }
////
////    private void onProfileSharing() {
////        Intent intent = new Intent(ClientActivity.this, PayPalProfileSharingActivity.class);
////
////        // send the same configuration for restart resiliency
////        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
////
////        intent.putExtra(PayPalProfileSharingActivity.EXTRA_REQUESTED_SCOPES, getOauthScopes());
////
////        startActivityForResult(intent, REQUEST_CODE_PROFILE_SHARING);
////    }
////
////    private PayPalOAuthScopes getOauthScopes() {
////        /* create the set of required scopes
////         * Note: see https://developer.paypal.com/docs/integration/direct/identity/attributes/ for mapping between the
////         * attributes you select for this app in the PayPal developer portal and the scopes required here.
////         */
////        //Log.d("ProfileSharingExample",Constants.PAYPAL_SCOPES);
////        Set<String> scopes = new HashSet<String>(Constants.PAYPAL_SCOPES);
////        return new PayPalOAuthScopes(scopes);
////    }
////
////    private void sendPayPalAuthorizationToServer(String authorization_code) {
////
////        new SendToFriendlyScore(this).execute(authorization_code,""+ Constants.PAYPAL_ID);
////
////    }
//
//
//    /*
//        Facebook Process
//
//     */
//
//    private void setUpFacebook(){
//        FacebookSdk.sdkInitialize(getApplicationContext());
//
//        FacebookSdk.setIsDebugEnabled(true);
//        FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
//    }
//
//    private void fbLogin(){
//        fbCallbackManager = CallbackManager.Factory.create();
//
//
//        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(Constants.FACEBOOK_PERMISSIONS));
//
//        LoginManager.getInstance().registerCallback(fbCallbackManager,
//                new FacebookCallback<LoginResult>() {
//                    @Override
//                    public void onSuccess(LoginResult loginResult) {
//                        Log.d("Success", "Login");
//
//                        handleFacebookAccessToken(loginResult.getAccessToken());
//                    }
//
//                    @Override
//                    public void onCancel() {
//                        Toast.makeText(ClientActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
//                        //If there was error with Facebook Process, due to user canceling
//                        userAuthError(Constants.FACEBOOKL_ID);
//
//                    }
//
//                    @Override
//                    public void onError(FacebookException exception) {
//                        Toast.makeText(ClientActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
//                        //If there was error with Facebook Process, due to other reasons
//
//                        userAuthError(Constants.FACEBOOKL_ID);
//
//                    }
//                });
//
//
//
//    }
//
//
//    // [START auth_with_facebook]
//    private void handleFacebookAccessToken(final AccessToken token) {
//
//
//        handleFBAuthorization(token);
//
//    }
//
//
//    //Send FB Token to FriendlyScore
//    private void handleFBAuthorization(final AccessToken token){
//        new SendToFriendlyScore(this).execute(token.getToken(),""+ Constants.FACEBOOKL_ID);
//    }
//
//
//    /*
//
//        Twitter
//     */
//
////    private void setUpTwitter(){
////        // Configure Twitter SDK
////        TwitterAuthConfig authConfig =  new TwitterAuthConfig(
////                getString(R.string.twitter_consumer_key),
////                getString(R.string.twitter_consumer_secret));
////        Fabric.with(this, new Twitter(authConfig));
//
////    }
////
////    private void executeTwitterLogin(){
////        twitterLoginbutton = new TwitterLoginButton(this);
////
////        twitterLoginbutton.setCallback(new Callback<TwitterSession>() {
////            @Override
////            public void success(Result<TwitterSession> result) {
////                handleTwitterSession(result.data);
////            }
////
////            @Override
////            public void failure(TwitterException exception) {
////                // Do something on failure
////                userAuthError(Constants.TWITTER_ID);
////
////            }
////        });
//
////        //Artificially Generate Click, this is so we can use customized ui buttons else we would be forced to use twitter ui.
////        twitterLoginbutton.performClick();
////    }
//
////    // [START auth_with_twitter]
////    private void handleTwitterSession(final TwitterSession session) {
////
////        handleTwitterAuthorization(session);
////
////    }
//    // [END auth_with_twitter] by sending token to Friendly Score
//
////    private void handleTwitterAuthorization(final TwitterSession session){
////
////        new SendToFriendlyScore(this).execute(String.valueOf(session.getUserId()), ""+ Constants.TWITTER_ID);
////    }
//
//    //The current user state is stored. The token received when any authentication is completed.
//    //The user state can be used to update UI based on your choice.
//    private void storeCurrentUserState(ReceivedTokenScore receivedTokenScore){
//
//        //It is store using key-pairs. Private Mode ensures it is available only to your app.
//        SharedPreferences sessionPreferences = this.getSharedPreferences(USER_KEY_PREF,Context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sessionPreferences.edit();
//        editor.putString(USER_KEY_HEADER,new Gson().toJson(receivedTokenScore));
//        editor.commit();
//    }
//
//    //The session is used by to connect your user to Friendly Score. So for a repeat user, the previous authentication
//    //and scores are available.
//    private String getSessionKey(){
//        SharedPreferences sessionPreferences = this.getSharedPreferences(SESSION_KEY_PREF,Context.MODE_PRIVATE);
//
//        String session_key = sessionPreferences.getString(SESSION_KEY_HEADER,null);
//
//        Log.d(TAG, "Sesssion Key:"+session_key);
//        return session_key;
//    }
//
//    //Store the session key returned by SDK so you can identify the user to Friendly Score again.
//    private void storeSessionKey(String session_key){
//
//        SharedPreferences sessionPreferences = this.getSharedPreferences(SESSION_KEY_PREF,Context.MODE_PRIVATE);
//
//        Log.d(TAG, "Sesssion Key:"+session_key);
//
//        SharedPreferences.Editor editor = sessionPreferences.edit();
//        editor.putString(SESSION_KEY_HEADER,session_key);
//        editor.commit();
//    }
//
//
//    //Abstract Method from Superclass
//
//
//    //The function is triggered when the process of "See Your Score" is finished and the SDK passes
//    //for the user to your app. "See Your Score" at the bottom of the screen is visible
//    //only when the user has authenticated with the required minimum number of data points(Social Networks).
//    @Override
//    public void getCompletedScore(ScoreCompleted scoreCompleted) {
//        Log.d("ClientActivity",""+scoreCompleted.userObject);
//
//        //
//    }
//
//    //The method is called when the process of authetication for any of the data networks is completed, i.e.
//    //each time when Facebook authentication is completed, Google authentication is completed etc.
//    @Override
//    public void handleReceivedTokenScore(ReceivedTokenScore receivedTokenScore){
//        storeCurrentUserState(receivedTokenScore);
//
//        storeSessionKey(receivedTokenScore.session_key);
//        Log.d(TAG,new Gson().toJson(receivedTokenScore));
//        // DO NOT CLOSE OR CHANGE UI
//    }
//
//    //The method is called when the SDK identifies errors that are primarily associated
//    //with your implementation of the app or the SDK.
//    @Override
//    public  void appErrorMessage(AppErrorForClient appErrorForClient){
//        Log.d(TAG,String.valueOf(appErrorForClient.getErrorNo()));
//        String message = null;
//        String title= null;
//        switch (appErrorForClient.getErrorNo()){
//            case AppErrorForClient.app_id_not_found:
//                title = "Check your App Id on Friendly Score";
//                message = "Contact App Developer";
//                showErrorDialog(message,title);
//                break;
//            case AppErrorForClient.session_key_not_found:
//                title = "User not found";
//                message = "Check user data";
//                showErrorDialog(message,title);
//                break;
//            case AppErrorForClient.internalServerError:
//                title = "Problem with our servers";
//                message = "There was problem with our servers, try again soon";
//                showErrorDialog(message,title);
//                break;
//        }
//
//    }
//
//    //The method is called when the SDK identifies errors that are primarily associated
//    //with problems with user interaction with the SDK, such as no internet, no data for
//    //computing score, slow internet connection.
//    @Override
//    public void userErrorMessage(ErrorForUser errorForUser){
//        Log.d(TAG,errorForUser.getErrorMessage());
//        String message = null;
//        String title= null;
//        switch (errorForUser.getErrorNo()){
//            case ErrorForUser.no_internet:
//                title = "no internet";
//                message = "Check Your Internet Connection";
//                showErrorDialog(message,title);
//
//                break;
//            case ErrorForUser.scoreCalculationError:
//                title = "Problem Calculating Score";
//                message = "There was problem calculating your score, try again in a few moments";
//                showErrorDialog(message,title);
//                break;
//            case ErrorForUser.noDataToScore:
//                title = "Problem Calculating Score";
//                message = "Your Profile does not have enough data to calculate the score";
//                showErrorDialog(message,title);
//
//                break;
//            case ErrorForUser.poorInternetConnectionError:
//                title = "Problem Calculating Score";
//                message = getResources().getString(R.string.poor_internet_connection);
//                showErrorDialog(message,title);
//                break;
//        }
//    }
//
//    @Override
//    public  boolean checkGooglePlayServicesVersion(){
//        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
//
//        switch (status){
//            case ConnectionResult.SUCCESS:
//                return true;
//            case ConnectionResult.SERVICE_MISSING:
//                showGooglePlayServicesErrorAlert(
//                        getResources().getString(R.string.no_google_play_services),
//                        getResources().getString(R.string.no_google_play_services_title));
//
//            case ConnectionResult.SERVICE_UPDATING:
//                showGooglePlayServicesErrorAlert(
//                        getResources().getString(R.string.google_play_services_updating),
//                        getResources().getString(R.string.google_play_services_updating_title));
//
//            case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
//                showGooglePlayServicesErrorAlert(
//                        getResources().getString(R.string.google_play_services_update),
//                        getResources().getString(R.string.google_play_services_title_update));
//
//            case ConnectionResult.SERVICE_DISABLED:
//                showGooglePlayServicesErrorAlert(
//                        getResources().getString( R.string.google_play_services_disabled),
//                        getResources().getString(R.string.google_play_services_disabled_title));
//
//            case ConnectionResult.SERVICE_INVALID:
//                showGooglePlayServicesErrorAlert(
//                        getResources().getString(R.string.google_play_services_invalid),
//                        getResources().getString(R.string.google_play_services_invalid_title));
//
//        }
//        //finish();
//
//        return false;
//    }
//
//    //The function displays the dialog when any of the error methods
//    //described earlier are triggered.
//    private void showErrorDialog(String message, String title ){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setMessage(message)
//                .setTitle(title);
//        // Add the buttons
//        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User clicked OK button
//                //activity.finish();
//                finish();
//            }
//        });
//
//        // Create the AlertDialog
//        AlertDialog dialog = builder.create();
//
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                //activity.finish();
//                //finish();
//            }
//        });
//        dialog.show();
//    }
//
//    //The function is needed to ensure that Google Play Services, required for Google Authentication
//    //are upto date.
//    private void showGooglePlayServicesErrorAlert(String message, String title){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        builder.setMessage(message)
//                .setTitle(title);
//        // Add the buttons
//        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                // User clicked OK button
//                //activity.finish();
//                finish();
//            }
//        });
//
//        // Create the AlertDialog
//        AlertDialog dialog = builder.create();
//
//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//            @Override
//            public void onDismiss(DialogInterface dialogInterface) {
//                //activity.finish();
//                finish();
//            }
//        });
//        dialog.show();
//    }
//
//}
