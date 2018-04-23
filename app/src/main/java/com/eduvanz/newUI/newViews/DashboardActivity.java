package com.eduvanz.newUI.newViews;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

import com.eduvanz.CustomTypefaceSpan;
import com.eduvanz.R;
import com.eduvanz.newUI.MainApplication;
import com.eduvanz.newUI.SharedPref;
import com.eduvanz.newUI.VolleyCallNew;
import com.eduvanz.newUI.fragments.DashboardFragmentNew;
import com.eduvanz.newUI.services.LocationService;
import com.eduvanz.newUI.services.MyServiceAppStats;
import com.eduvanz.newUI.services.MyServiceCallLog;
import com.eduvanz.newUI.services.MyServiceContacts;
import com.eduvanz.newUI.services.MyServiceReadSms;
import com.eduvanz.newUI.webviews.WebViewAboutUs;
import com.eduvanz.newUI.webviews.WebViewDisclaimer;
import com.eduvanz.newUI.webviews.WebViewFAQs;
import com.eduvanz.newUI.webviews.WebViewFairPracticsCode;
import com.eduvanz.newUI.webviews.WebViewPrivacyPolicy;
import com.eduvanz.newUI.webviews.WebViewTermsNCondition;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanz.newUI.MainApplication.TAG;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static NavigationView navigationView;
    public static DrawerLayout drawer;
    Context context;
    TextView textViewName, textViewEmail;
    Button buttonSignup, buttonSignIn;
    MainApplication mainApplication;
    FrameLayout frameLayoutDashboard;
    SharedPref sharedPref;
    LinearLayout linearLayoutSignup, linearLayoutUserDetail;

    static String userMobileNo = "", userId = "", appInstallationTimeStamp = "";
    AppCompatActivity mActivity;
    SharedPreferences sharedPreferences;

    String userFirst = "", userLast = "", userEmail = "", userPic = "";
    ImageView imageViewProfilePic;

    static int firstTimeScrape = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        context = getApplicationContext();
        mainApplication = new MainApplication();
        sharedPref = new SharedPref();
        mActivity = this;

        try {
            sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            userFirst = sharedPreferences.getString("first_name", "");
            userLast = sharedPreferences.getString("last_name", "");
            userEmail = sharedPreferences.getString("user_email", "");
            userMobileNo = sharedPreferences.getString("mobile_no", "");
            userId = sharedPreferences.getString("logged_id", "");
            userPic = sharedPreferences.getString("user_image", "");
            firstTimeScrape= sharedPreferences.getInt("firstTimeScrape", 0);
            appInstallationTimeStamp = sharedPreferences.getString("appInstallationTimeStamp", "null");
        } catch (Exception e) {
            e.printStackTrace();
            firstTimeScrape = 0;
        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        //**To change the hamburger color on dashboard **/
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary));

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);

        hideMenuOptions();

        textViewName = (TextView) header.findViewById(R.id.textView_name);
        mainApplication.applyTypeface(textViewName, context);
        textViewName.setText(userFirst + " " + userLast);
        textViewEmail = (TextView) header.findViewById(R.id.textView_emailID);
        mainApplication.applyTypeface(textViewEmail, context);
        textViewEmail.setText(userEmail);

        imageViewProfilePic = (ImageView) header.findViewById(R.id.imageView_userpic);
        if (!userPic.equalsIgnoreCase("")) {
            Picasso.with(context).load(userPic).placeholder(getResources().getDrawable(R.drawable.profilepic_placeholder)).into(imageViewProfilePic);
        }

        buttonSignup = (Button) header.findViewById(R.id.button_dashboard_signup);
        mainApplication.applyTypeface(buttonSignup, context);
        buttonSignIn = (Button) header.findViewById(R.id.button_dashboard_signin);
        mainApplication.applyTypeface(buttonSignIn, context);

        linearLayoutSignup = (LinearLayout) header.findViewById(R.id.linearLayout_signupdetail_dashboard);
        linearLayoutUserDetail = (LinearLayout) header.findViewById(R.id.linearLayout_userdetail_dashboard);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, SignIn.class);
                startActivity(intent);
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, EligibilityCheck.class);
                startActivity(intent);
            }
        });

        frameLayoutDashboard = (FrameLayout) findViewById(R.id.framelayout_dashboard);
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
            linearLayoutUserDetail.setVisibility(View.VISIBLE);
            linearLayoutSignup.setVisibility(View.GONE);
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_loanApplication).setVisible(true);
            nav_Menu.findItem(R.id.nav_eligibility).setVisible(false);
        }else {
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        }

        getSupportFragmentManager().beginTransaction().add(R.id.framelayout_dashboard, new DashboardFragmentNew()).commit();
        // services for upload scapping data
        /** API CALL GET Dates**/
        try {
            String url = MainApplication.mainUrl + "mobilescrap/getRecentScrappingDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("mobileNo", userMobileNo);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, mActivity, null, "getRecentScrapping", params);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void Otherpermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean permission = checkIfAlreadyhavePermissionLocation();

            if (!permission) {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
                        }, 105);
            } else {
                /** SERVICE CALL **/
                setScheduleIntent(LocationService.class);
            }
        } else {
            /** SERVICE CALL **/
            setScheduleIntent(LocationService.class);
        }
    }

    private boolean checkIfAlreadyhavePermissionLocation() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void permissionContacts() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean permission = checkIfAlreadyhavePermissionContact();

            if (!permission) {
                Log.i("TAG", "Permission to record denied");
                requestPermissions(
                        new String[]{Manifest.permission.READ_CONTACTS,
                        }, 104);
            } else {
                /** SERVICE CALL **/
                setScheduleIntent(MyServiceContacts.class);
            }
        } else {
            /** SERVICE CALL **/
            setScheduleIntent(MyServiceContacts.class);
        }
    }

    private boolean checkIfAlreadyhavePermissionContact() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void permissionAppStats() {
        if (!isAccessGranted()) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        } else {
            setScheduleIntent(MyServiceAppStats.class);
        }
    }

    private boolean isAccessGranted() {
        try {
            PackageManager packageManager = getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.KITKAT) {
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    private boolean checkIfAlreadyhavePermissionAppStats() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void permissionReadSMS() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean permission = checkIfAlreadyhavePermissionSMS();
            if (!permission) {
                Log.i("TAG", "Permission to record denied");
                requestPermissions(
                        new String[]{Manifest.permission.READ_SMS,
                        }, 102);
            } else {
                /** SERVICE CALL **/
                setScheduleIntent(MyServiceReadSms.class);
            }
        } else {
            /** SERVICE CALL **/
            setScheduleIntent(MyServiceReadSms.class);
        }
    }

    private boolean checkIfAlreadyhavePermissionSMS() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void permissionCallLogs() {
        if (Build.VERSION.SDK_INT >= 23) {
            boolean permission = checkIfAlreadyhavePermission();
            if (!permission) {
                requestPermissions(
                        new String[]{Manifest.permission.READ_CALL_LOG, Manifest.permission.READ_PHONE_STATE,
                        }, 101);
            } else {
                /** SERVICE CALL **/
                setScheduleIntent(MyServiceCallLog.class);
            }
        } else {
            /** SERVICE CALL **/
            setScheduleIntent(MyServiceCallLog.class);
        }
    }

    private void setScheduleIntent(Class myServiceCallLogClass) {
        // start location service

        startService(new Intent(context, myServiceCallLogClass));
    }

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_myprofile) {
            if (sharedPref.getLoginDone(context)) {
                Intent intent = new Intent(context, MyProfileNew.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(context, SignUp.class);
                startActivity(intent);
            }
        } else if (id == R.id.nav_eligibility) {
            Intent intent = new Intent(DashboardActivity.this, EligibilityCheck.class);
            startActivity(intent);
        } else if (id == R.id.nav_howitworks) {
            Intent intent = new Intent(context, HowItWorks.class);
            startActivity(intent);
        } else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(DashboardActivity.this, WebViewAboutUs.class);
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            Intent intent = new Intent(DashboardActivity.this, WebViewFAQs.class);
            startActivity(intent);
        } else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(DashboardActivity.this, ContactUs.class);
            startActivity(intent);
        } else if (id == R.id.nav_more) {
            showMenuOptions();
        } else if (id == R.id.nav_privacypolicy) {
            Intent intent = new Intent(DashboardActivity.this, WebViewPrivacyPolicy.class);
            startActivity(intent);
        } else if (id == R.id.nav_termsandconditions) {
            Intent intent = new Intent(DashboardActivity.this, WebViewTermsNCondition.class);
            startActivity(intent);

        } else if (id == R.id.nav_disclaimer) {
            Intent intent = new Intent(DashboardActivity.this, WebViewDisclaimer.class);
            startActivity(intent);
        } else if (id == R.id.nav_fairpracticscode) {
            Intent intent = new Intent(DashboardActivity.this, WebViewFairPracticsCode.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {

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
            Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_loanApplication) {
            Intent intent = new Intent(context, LoanApplication.class);
            startActivity(intent);
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
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    private void hideMenuOptions() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_privacypolicy).setVisible(false);
        nav_Menu.findItem(R.id.nav_termsandconditions).setVisible(false);
        nav_Menu.findItem(R.id.nav_disclaimer).setVisible(false);
        nav_Menu.findItem(R.id.nav_fairpracticscode).setVisible(false);
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        nav_Menu.findItem(R.id.nav_loanApplication).setVisible(false);
    }

    private void showMenuOptions() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_more).setVisible(false);
        nav_Menu.findItem(R.id.nav_privacypolicy).setVisible(true);
        nav_Menu.findItem(R.id.nav_termsandconditions).setVisible(true);
        nav_Menu.findItem(R.id.nav_disclaimer).setVisible(true);
        nav_Menu.findItem(R.id.nav_fairpracticscode).setVisible(true);
        if (sharedPref.getLoginDone(context)) {
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    Intent intent = new Intent(this, MyServiceCallLog.class);
                    startService(intent);
                } else {
                    //not granted
                }
                break;
            case 102:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    Intent intent = new Intent(this, MyServiceReadSms.class);
                    startService(intent);
                }
                break;
            case 103:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                }
                break;
            case 104:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    Intent intent = new Intent(this, MyServiceContacts.class);
                    startService(intent);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /** RESPONSE OF API CALL SCRAPING DATES */
    public void getScrappingdates(JSONObject jsonDataO) {
        Log.e(TAG, "*****************GET SCRAPING DATES************** \n" + jsonDataO);
        try {
            String smsTimeStamp = null, callTimeStamp = null, contactTimeStamp = null,
                    appTimeStamp = null, appInstallationDateTimes = null, locationTimeStamp = null;
            JSONObject mobject = jsonDataO.optJSONObject("result");
            try {
                smsTimeStamp = mobject.getString("smsTimeStamp");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                callTimeStamp = mobject.getString("callTimeStamp");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                contactTimeStamp = mobject.getString("contactTimeStamp");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                appTimeStamp = mobject.getString("appTimeStamp");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                 appInstallationTimeStamp = appInstallationDateTimes = mobject.getString("appInstallationDateTime");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                locationTimeStamp = mobject.getString("locationTimeStamp");
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e(TAG, "getScrappingdates:appInstallationDateTime:::................. "+ appInstallationDateTimes);
            try {
                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("smsTimeStamp", smsTimeStamp);
                editor.putString("callTimeStamp", callTimeStamp);
                editor.putString("contactTimeStamp", contactTimeStamp);
                editor.putString("appTimeStamp", appTimeStamp);
                editor.putString("locationTimeStamp", locationTimeStamp);
                editor.putString("appInstallationTimeStamp", appInstallationDateTimes);
                editor.apply();
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }


//            if(firstTimeScrape == 0){
                permissionCallLogs();       //101
                permissionReadSMS();   // 102
                permissionAppStats();    // 103
                permissionContacts();   // 104
                locationScrape();

                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("firstTimeScrape", 1);
                editor.apply();
                editor.commit();
//            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void locationScrape() {
        try {
            try {
                Log.e(TAG, "getScrappingdates:appInstallationTimeStampappInstallationTimeStamp:..................................:: "+ appInstallationTimeStamp);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date lLocationDate = simpleDateFormat.parse(appInstallationTimeStamp);
                long installationTime = lLocationDate.getTime() + (5 * 12 * 60 * 60 * 1000);

                if (installationTime < System.currentTimeMillis()) {
                    Intent myService = new Intent(DashboardActivity.this, LocationService.class);
                    stopService(myService);
                } else {
                    startService(new Intent(context, LocationService.class));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
