package com.eduvanzapplication.newUI.newViews;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.eduvanzapplication.BuildConfig;
import com.eduvanzapplication.CustomTypefaceSpan;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.CameraUtils;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.fragments.DashboardFragmentNew;

import com.eduvanzapplication.newUI.webviews.WebViewAboutUs;
import com.eduvanzapplication.newUI.webviews.WebViewBlog;
import com.eduvanzapplication.newUI.webviews.WebViewDisclaimer;
import com.eduvanzapplication.newUI.webviews.WebViewFAQs;
import com.eduvanzapplication.newUI.webviews.WebViewFairPracticsCode;
import com.eduvanzapplication.newUI.webviews.WebViewInterestRatePolicy;
import com.eduvanzapplication.newUI.webviews.WebViewPrivacyPolicy;
import com.eduvanzapplication.newUI.webviews.WebViewTermsNCondition;
import com.google.gson.JsonObject;
import com.idfy.rft.RFTSdk;
import com.idfy.rft.RftSdkCallbackInterface;
import com.squareup.picasso.Picasso;

import static com.eduvanzapplication.newUI.MainApplication.TAG;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static NavigationView navigationView;
    public static DrawerLayout drawer;
    Context context;
//    com.eduvanzapplication.newUI.newViews.CustomDrawerButton customDrawerButton;
    TextView textViewName, textViewEmail;
    MainApplication mainApplication;
    FrameLayout frameLayoutDashboard;
    SharedPref sharedPref;
    LinearLayout linearLayoutSignup, linearLayoutUserDetail,editProfile;

    static String userMobileNo = "", student_id = "", appInstallationTimeStamp = "";
    AppCompatActivity mActivity;
    SharedPreferences sharedPreferences;

    String userFirst = "", userLast = "", userEmail = "", userPic = "";
    ImageView imageViewProfilePic;

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
//            toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
            context = getApplicationContext();
            mainApplication = new MainApplication();
            sharedPref = new SharedPref();
            mActivity = this;

            try {
                sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                userFirst = sharedPreferences.getString("name", "");
                userEmail = sharedPreferences.getString("email", "");
                userMobileNo = sharedPreferences.getString("mobile_no", "");
                MainActivity.auth_token = sharedPreferences.getString("auth_token", "");
                student_id = sharedPreferences.getString("student_id", "");
                userPic = sharedPreferences.getString("user_image", "");
                firstTimeScrape = sharedPreferences.getInt("firstTimeScrape", 0);
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
            toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

            navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            View header = navigationView.getHeaderView(0);
            editProfile=(LinearLayout)header.findViewById(R.id.linearLayout_userdetail_dashboard);
            editProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),EditProfile.class));
                }
            });
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


            linearLayoutUserDetail = (LinearLayout) header.findViewById(R.id.linearLayout_userdetail_dashboard);

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
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_loanApplication).setVisible(true);
                nav_Menu.findItem(R.id.nav_eligibility).setVisible(false);
            }else {
                Menu nav_Menu = navigationView.getMenu();
                nav_Menu.findItem(R.id.nav_logout).setVisible(false);
            }

            getSupportFragmentManager().beginTransaction().add(R.id.framelayout_dashboard, new DashboardFragmentNew()).commit();


        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(DashboardActivity.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }

//        showOCRDialog();

    }



    @Override
    protected void onResume() {
        super.onResume();

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

            Intent intent = new Intent(DashboardActivity.this, LoanTabActivity.class);
            startActivity(intent);
//            if (sharedPref.getLoginDone(context)) {
//                Intent intent = new Intent(context, MyProfileNew.class);
//                startActivity(intent);
//            } else {
//                Intent intent = new Intent(context, SignUp.class);
//                startActivity(intent);
//            }
        } else if (id == R.id.nav_eligibility) {
//            Intent intent = new Intent(DashboardActivity.this, EligibilityCheck.class);
//            startActivity(intent);
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
        } else if (id == R.id.nav_contactus) {
            Intent intent = new Intent(DashboardActivity.this, ContactUs.class);
            startActivity(intent);
        } else if (id == R.id.nav_more) {
            showMenuOptions();
        } else if (id == R.id.nav_blog) {
            Intent intent = new Intent(DashboardActivity.this, WebViewBlog.class);
            startActivity(intent);
        } else if (id == R.id.nav_disclaimer) {
            Intent intent = new Intent(DashboardActivity.this, WebViewDisclaimer.class);
            startActivity(intent);
        } else if (id == R.id.nav_termsandconditions) {
            Intent intent = new Intent(DashboardActivity.this, WebViewTermsNCondition.class);
            startActivity(intent);
        }else if (id == R.id.nav_privacypolicy) {
            Intent intent = new Intent(DashboardActivity.this, WebViewPrivacyPolicy.class);
            startActivity(intent);
        } else if (id == R.id.nav_interestratepolicy) {
            Intent intent = new Intent(DashboardActivity.this, WebViewInterestRatePolicy.class);
            startActivity(intent);
        }else if (id == R.id.nav_fairpracticscode) {
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
//                            Intent intent = new Intent(DashboardActivity.this, SingInWithTruecaller.class);
//                            Intent intent = new Intent(DashboardActivity.this, NewTruecallerSignIn.class);
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


        } else if (id == R.id.nav_loanApplication) {
//            Intent intent = new Intent(context, LoanApplication.class);
//            startActivity(intent);
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
        nav_Menu.findItem(R.id.nav_blog).setVisible(false);
        nav_Menu.findItem(R.id.nav_disclaimer).setVisible(false);
        nav_Menu.findItem(R.id.nav_termsandconditions).setVisible(false);
        nav_Menu.findItem(R.id.nav_privacypolicy).setVisible(false);
        nav_Menu.findItem(R.id.nav_interestratepolicy).setVisible(false);
        nav_Menu.findItem(R.id.nav_fairpracticscode).setVisible(false);
        nav_Menu.findItem(R.id.nav_logout).setVisible(false);
        nav_Menu.findItem(R.id.nav_loanApplication).setVisible(false);
    }

    private void showMenuOptions() {
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.nav_more).setVisible(false);
        nav_Menu.findItem(R.id.nav_blog).setVisible(true);
        nav_Menu.findItem(R.id.nav_disclaimer).setVisible(true);
        nav_Menu.findItem(R.id.nav_termsandconditions).setVisible(true);
        nav_Menu.findItem(R.id.nav_privacypolicy).setVisible(true);
        nav_Menu.findItem(R.id.nav_interestratepolicy).setVisible(true);
        nav_Menu.findItem(R.id.nav_fairpracticscode).setVisible(true);
        if (sharedPref.getLoginDone(context)) {
            nav_Menu.findItem(R.id.nav_logout).setVisible(true);
        }
    }

}