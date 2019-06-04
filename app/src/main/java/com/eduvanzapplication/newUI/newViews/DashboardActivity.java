package com.eduvanzapplication.newUI.newViews;

import android.Manifest;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduvanzapplication.BuildConfig;
import com.eduvanzapplication.CustomTypefaceSpan;
import com.eduvanzapplication.DataSyncReceiver;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCallAlgo360;
import com.eduvanzapplication.newUI.fragments.DashboardFragmentNew;

import com.eduvanzapplication.newUI.webviews.WebViewAboutUs;
import com.eduvanzapplication.newUI.webviews.WebViewDisclaimer;
import com.eduvanzapplication.newUI.webviews.WebViewFAQs;
import com.eduvanzapplication.newUI.webviews.WebViewFairPracticsCode;
import com.eduvanzapplication.newUI.webviews.WebViewInterestRatePolicy;
import com.eduvanzapplication.newUI.webviews.WebViewPrivacyPolicy;
import com.eduvanzapplication.newUI.webviews.WebViewTermsNCondition;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.thinkanalytics.algo360SDK.Algo360_SDK_Init;
import in.thinkanalytics.algo360SDK.ExtraHelperFunctions;


public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static NavigationView navigationView;
    public static DrawerLayout drawer;
    public static Context context;
    //    com.eduvanzapplication.newUI.newViews.CustomDrawerButton customDrawerButton;
    TextView textViewName, textView_mobileNo, textViewEmail;
    FrameLayout frameLayoutDashboard;
    SharedPref sharedPref;
    LinearLayout linearLayoutSignup, linearLayoutUserDetail, editProfile;
    public DataSyncReceiver dataSyncReceiver;
    static String student_id = "", appInstallationTimeStamp = "", userFirst = "", userLast = "", userEmail = "", userPic = "", userMobileNo = "", isDataSync = "false";
    public static AppCompatActivity mActivity;
    SharedPreferences sharedPreferences;
    public int GET_MY_PERMISSION = 1, permission;
    ImageView ivUserPic;

    static int firstTimeScrape = 0;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_dashboard);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("");
            context = getApplicationContext();
            sharedPref = new SharedPref();
            mActivity = this;

            try {
                sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                userFirst = sharedPreferences.getString("name", "");
                userEmail = sharedPreferences.getString("email", "");
                userMobileNo = sharedPreferences.getString("mobile_no", "");
                MainActivity.auth_token = sharedPreferences.getString("auth_token", "");
                student_id = sharedPreferences.getString("student_id", "");
                userPic = sharedPreferences.getString("user_img", "");
                firstTimeScrape = sharedPreferences.getInt("firstTimeScrape", 0);
                appInstallationTimeStamp = sharedPreferences.getString("appInstallationTimeStamp", "null");
            } catch (Exception e) {
                e.printStackTrace();
                firstTimeScrape = 0;
            }

            try {
                sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                isDataSync = sharedPreferences.getString("Data synced", "false");
            } catch (Exception e) {
                e.printStackTrace();
                firstTimeScrape = 0;
            }
            drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();
            //**To change the hamburger color on dashboard **/
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

            navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View header = navigationView.getHeaderView(0);

            editProfile = header.findViewById(R.id.linearLayout_userdetail_dashboard);
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), EditProfile.class));
                }
            });
            hideMenuOptions();

            textViewName = header.findViewById(R.id.textView_name);
            textViewName.setText(userFirst);
            textView_mobileNo = header.findViewById(R.id.textView_mobileNo);
            textView_mobileNo.setText(userMobileNo);
            textViewEmail = header.findViewById(R.id.textView_emailID);
            textViewEmail.setText(userEmail);

            ivUserPic = header.findViewById(R.id.ivUserPic);
            if (!userPic.equalsIgnoreCase("")) {
                Picasso.with(context).load(userPic).placeholder(getResources().getDrawable(R.drawable.profilepic_placeholder)).into(ivUserPic);
            }

//            try {
//                sharedPreferences = context.getSharedPreferences("ProfileData", Context.MODE_PRIVATE);
//                textViewEmail.setText(sharedPreferences.getString("email_id", ""));
//                textViewName.setText(sharedPreferences.getString("first_name", ""));
//                Picasso.with(context)
//                        .load(sharedPreferences.getString("image_profile", ""))
//                        .into(ivUserPic);
//            } catch (Exception e) {
//                e.printStackTrace();
//                firstTimeScrape = 0;
//            }

            linearLayoutUserDetail = header.findViewById(R.id.linearLayout_userdetail_dashboard);

            frameLayoutDashboard = findViewById(R.id.framelayout_dashboard);
            Menu m = navigationView.getMenu();
            for (int i = 0; i < m.size(); i++) {
                MenuItem mi = m.getItem(i);

                //for applying a font to subMenu ...
                SubMenu subMenu = mi.getSubMenu();
                if (subMenu != null && subMenu.size() > 0) {
                    for (int j = 0; j < subMenu.size(); j++) {
                        MenuItem subMenuItem = subMenu.getItem(j);
                        applyFontToMenuItem(subMenuItem);
                    }
                }

                //the method we have create in activity
                applyFontToMenuItem(mi);
            }

            if (sharedPref.getLoginDone(context)) {
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_eligibility).setVisible(false);
            } else {
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            }

            getSupportFragmentManager().beginTransaction().add(R.id.framelayout_dashboard, new DashboardFragmentNew()).commit();

            dataSyncReceiver = new DataSyncReceiver();
            IntentFilter filter = new IntentFilter("in.thinkanalytics.app.app_init.DATASYNC_BROADCAST_ACTION");
            context.registerReceiver(dataSyncReceiver, filter);

            if (Build.VERSION.SDK_INT >= 23) {
                permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_SMS);

                if (permission != PackageManager.PERMISSION_GRANTED) {//Direct Permission without disclaimer dialog
//                    ActivityCompat.requestPermissions(DashboardActivity.this,
//                            new String[]{
//                                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.CAMERA},
//                            GET_MY_PERMISSION);
                    ActivityCompat.requestPermissions(DashboardActivity.this,
                            new String[]{
                                    Manifest.permission.READ_CONTACTS,
                                    Manifest.permission.READ_SMS,
                                    Manifest.permission.RECEIVE_SMS,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION},
                            GET_MY_PERMISSION);

                } else {
                    ExtraHelperFunctions.putRefUserId(context, userMobileNo);
                    Algo360_SDK_Init.startAlgo360(getApplicationContext(), Algo360_SDK_Init.TESTING_ENV, Algo360_SDK_Init.ENABLE_PRINT);
                }
            }

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(DashboardActivity.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            Boolean dataSynced = intent.getBooleanExtra("DataSynced", false);
//            Log.e("Receiver", "Data synced: " + dataSynced);
//            Log.e("Receiver", "Data Action: " + action);

//            if(dataSynced) {
            saveAlgo360();
//            }
        }
    };

    public static void saveAlgo360() {
        /** API CALL **/
        try {//auth_token
//            String url = "http://192.168.1.63/eduvanzapi/dashboard/saveAlgo360response";
            if (!isDataSync.equals("true")) {
                String url = MainActivity.mainUrl + "dashboard/saveAlgo360response";
                Map<String, String> params = new HashMap<String, String>();

                params.put("student_id", student_id);
                params.put("mobile_no", userMobileNo);
                params.put("email_id", userEmail);
                params.put("algo360_datasync", String.valueOf(true));
                VolleyCallAlgo360 volleyCall = new VolleyCallAlgo360();
//            volleyCall.sendRequest(context, url, mActivity, null, "addAlgo360", params, "90ad441a12b48c6d7c5524b8b2a334c3");
                volleyCall.sendRequest(context, url, mActivity, null, "addAlgo360", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void saveAlgo360(Context context, String studid, String mobno, String email,String datasync) {
//        /** API CALL **/
//        try {//auth_token
//            String url = "192.168.1.63/eduvanzapi/dashboard/saveAlgo360response";
////            String url = MainActivity.mainUrl + "dashboard/saveAlgo360response";
//            Map<String, String> params = new HashMap<String, String>();
//
//            params.put("student_id", studid);
//            params.put("mobile_no", mobno);
//            params.put("email_id", email);
//            params.put("algo360_datasync", datasync);
//            VolleyCall volleyCall = new VolleyCall();
//            volleyCall.sendRequest(context, url, mActivity, null, "addAlgo360", params, MainActivity.auth_token);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    protected void onPause() {

        unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {

        IntentFilter intentFilter = new IntentFilter("in.thinkanalytics.app.app_init.DATASYNC_BROADCAST_ACTION");
        registerReceiver(broadcastReceiver, intentFilter);
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_eligibility) {
//            Intent intent = new Intent(DashboardActivity.this, NewLeadActivity.class);
            Intent intent = new Intent(DashboardActivity.this, LeadOwnerType.class);
            startActivity(intent);
        } else if (id == R.id.nav_howitworks) {
            Intent intent = new Intent(context, HowItWorks.class);
            startActivity(intent);
        }
//        else if (id == R.id.nav_language) {
//            Intent intent = new Intent(DashboardActivity.this, Language.class);
//            startActivity(intent);
//        }
        else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(DashboardActivity.this, WebViewAboutUs.class);
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            Intent intent = new Intent(DashboardActivity.this, WebViewFAQs.class);
            startActivity(intent);
        }
//        else if (id == R.id.nav_contactus) {
//            Intent intent = new Intent(DashboardActivity.this, ContactUs.class);
//            startActivity(intent);
//        }
        else if (id == R.id.nav_more) {
            showMenuOptions();
        }
//        else if (id == R.id.nav_blog) {
//            Intent intent = new Intent(DashboardActivity.this, WebViewBlog.class);
//            startActivity(intent);
//        }
        else if (id == R.id.nav_disclaimer) {
            Intent intent = new Intent(DashboardActivity.this, WebViewDisclaimer.class);
            startActivity(intent);
        } else if (id == R.id.nav_termsandconditions) {
            Intent intent = new Intent(DashboardActivity.this, WebViewTermsNCondition.class);
            startActivity(intent);
        } else if (id == R.id.nav_privacypolicy) {
            Intent intent = new Intent(DashboardActivity.this, WebViewPrivacyPolicy.class);
            startActivity(intent);
        } else if (id == R.id.nav_interestratepolicy) {
            Intent intent = new Intent(DashboardActivity.this, WebViewInterestRatePolicy.class);
            startActivity(intent);
        } else if (id == R.id.nav_fairpracticscode) {
            Intent intent = new Intent(DashboardActivity.this, WebViewFairPracticsCode.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
            builder.setTitle(R.string.app_name);
            builder.setIcon(R.drawable.eduvanz_logo_new);
            builder.setMessage(R.string.are_you_sure_you_waqnt_to_exit)
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            SharedPref sharedPref = new SharedPref();
                            sharedPref.clearSharedPreference(DashboardActivity.this);
//            /** STORING THE COLOR and BOOLEAN VALUE FOR FIRST TIME DEFAULT COLOR STORE INTO SHARED PREFERENCE **/
//            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putString("primary_color", "#4FC0E8");
//            editor.putString("primary_color_dark", "#197b9d");
//            editor.putString("fcm_id", fcmID);
//            editor.apply();
//            editor.commit();
                            Intent intent = new Intent(DashboardActivity.this, GetMobileNo.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (id == R.id.nav_more) {
            drawer.openDrawer(GravityCompat.START);
        } else {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/sourcesanspro_italic.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void hideMenuOptions() {
        Menu nav_Menu = navigationView.getMenu();
//        nav_Menu.findItem(R.id.nav_blog).setVisible(false);
        nav_Menu.findItem(R.id.nav_disclaimer).setVisible(false);
        nav_Menu.findItem(R.id.nav_termsandconditions).setVisible(false);
        nav_Menu.findItem(R.id.nav_privacypolicy).setVisible(false);
        nav_Menu.findItem(R.id.nav_interestratepolicy).setVisible(false);
        nav_Menu.findItem(R.id.nav_fairpracticscode).setVisible(false);
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);
    }

    private void showMenuOptions() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_more).setVisible(false);
//        nav_Menu.findItem(R.id.nav_blog).setVisible(true);
        nav_Menu.findItem(R.id.nav_disclaimer).setVisible(true);
        nav_Menu.findItem(R.id.nav_termsandconditions).setVisible(true);
        nav_Menu.findItem(R.id.nav_privacypolicy).setVisible(true);
        nav_Menu.findItem(R.id.nav_interestratepolicy).setVisible(true);
        nav_Menu.findItem(R.id.nav_fairpracticscode).setVisible(true);
        if (sharedPref.getLoginDone(context)) {
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1:
                if (grantResults.length <= 0) {
                }
//                else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
                else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[4] == PackageManager.PERMISSION_GRANTED && grantResults[5] == PackageManager.PERMISSION_GRANTED &&
//                        grantResults[6] == PackageManager.PERMISSION_GRANTED && grantResults[7] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[8] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    ExtraHelperFunctions.putRefUserId(context, userMobileNo);
                    Algo360_SDK_Init.startAlgo360(getApplicationContext(), Algo360_SDK_Init.TESTING_ENV, Algo360_SDK_Init.ENABLE_PRINT);
                } else {
                    //not granted
//                    Log.e(MainApplication.TAG, "not granted: Dashboard " + grantResults[0]);
                    {
                        // Permission denied.
                        // Notify the user via a SnackBar that they have rejected a core permission for the
                        // app, which makes the Activity useless. In a real app, core permissions would
                        // typically be best requested during a welcome-screen flow.
                        // Additionally, it is important to remember that a permission might have been
                        // rejected without asking the user for permission (device policy or "Never ask
                        // again" prompts). Therefore, a user interface affordance is typically implemented
                        // when permissions are denied. Otherwise, your app could appear unresponsive to
                        // touches or interactions which have required permissions.
                        //                    Toast.makeText(this, R.string.permission_denied_explanation, Toast.LENGTH_LONG).show();
                        //                    finish();

                        Snackbar.make(
                                findViewById(R.id.framelayout_dashboard),
                                R.string.permission_denied_explanation,
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.settings, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Build intent that displays the App settings screen.
                                        Intent intent = new Intent();
                                        intent.setAction(
                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package",
                                                BuildConfig.APPLICATION_ID, null);
                                        intent.setData(uri);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                }
                break;
        }

    }

    public void updateAlgo360Res(JSONObject jsonDataO) {

        String status = jsonDataO.optString("status");
        if (status.equals("1")) {
            sharedPreferences = context.getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Data synced", "true");
            editor.commit();
        }
    }
}