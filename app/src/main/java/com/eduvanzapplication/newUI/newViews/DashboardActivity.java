package com.eduvanzapplication.newUI.newViews;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.eduvanzapplication.BuildConfig;
import com.eduvanzapplication.CustomTypefaceSpan;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.fragments.DashboardFragmentNew;
import com.eduvanzapplication.newUI.services.LocationService;
import com.eduvanzapplication.newUI.services.MyServiceAppStats;
import com.eduvanzapplication.newUI.services.MyServiceCallLog;
import com.eduvanzapplication.newUI.services.MyServiceContacts;
import com.eduvanzapplication.newUI.services.MyServiceReadSms;
//import com.eduvanzapplication.newUI.services.MyServiceReadSmsbck;
import com.eduvanzapplication.newUI.webviews.WebViewAboutUs;
import com.eduvanzapplication.newUI.webviews.WebViewBlog;
import com.eduvanzapplication.newUI.webviews.WebViewDisclaimer;
import com.eduvanzapplication.newUI.webviews.WebViewFAQs;
import com.eduvanzapplication.newUI.webviews.WebViewFairPracticsCode;
import com.eduvanzapplication.newUI.webviews.WebViewInterestRatePolicy;
import com.eduvanzapplication.newUI.webviews.WebViewPrivacyPolicy;
import com.eduvanzapplication.newUI.webviews.WebViewTermsNCondition;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.eduvanzapplication.newUI.MainApplication.TAG;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static NavigationView navigationView;
    public static DrawerLayout drawer;
    Context context;
//    com.eduvanzapplication.newUI.newViews.CustomDrawerButton customDrawerButton;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_dashboard);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
//            getSupportActionBar().setTitle(R.string.title_dashboard);
//            toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
//            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
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

//            /** API CALL GET Dates**/
//            try {
//                String url = MainApplication.mainUrl + "mobilescrap/getRecentScrappingDetails";
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("mobileNo", userMobileNo);
//                if (!Globle.isNetworkAvailable(DashboardActivity.this)) {
//
//                } else {
//                    VolleyCallNew volleyCall = new VolleyCallNew();//http://192.168.0.110/eduvanzapi/mobilescrap/getRecentScrappingDetails
//                    volleyCall.sendRequest(context, url, mActivity, null, "getRecentScrapping", params);
//                }
//            } catch (Exception e) {
//                String className = this.getClass().getSimpleName();
//                String name = new Object() {
//                }.getClass().getEnclosingMethod().getName();
//                String errorMsg = e.getMessage();
//                String errorMsgDetails = e.getStackTrace().toString();
//                String errorLine = String.valueOf(e.getStackTrace()[0]);
//                Globle.ErrorLog(DashboardActivity.this,className, name, errorMsg, errorMsgDetails, errorLine);
//            }

//        permissionCallLogs();     //101
//        permissionReadSMS();      //102
//        permissionAppStats();    //103
//        permissionContacts();    //104
//        Otherpermission();

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(DashboardActivity.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }

        showOCRDialog();

    }

    private void showOCRDialog() {

        View view = getLayoutInflater().inflate(R.layout.layout_ocr_options,null);
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .setFooterView(view);
//                .addButton("UPGRADE", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, (dialog, which) -> {
//                    Toast.makeText(DashboardActivity.this, "Upgrade tapped", Toast.LENGTH_SHORT).show();
//                    dialog.dismiss();
//                });

    // Show the alert
        builder.show();

    }

    @Override
    protected void onResume() {
        super.onResume();

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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void permissionAppStats() {
        if (!isAccessGranted()) {
            startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));
        } else {
            setScheduleIntent(MyServiceAppStats.class);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    Intent intent = new Intent(this, MyServiceCallLog.class);
                    startService(intent);
                } else {
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
                            findViewById(R.id.drawer_layout),
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
                break;
            case 102:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    Intent intent = new Intent(this, MyServiceReadSms.class);
                    startService(intent);
                } else {
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
                            findViewById(R.id.drawer_layout),
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
                break;
            case 103:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                } else {
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
                            findViewById(R.id.drawer_layout),
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
                break;
            case 104:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    Intent intent = new Intent(this, MyServiceContacts.class);
                    startService(intent);
                } else {
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
                            findViewById(R.id.drawer_layout),
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
            case 105:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //granted
                    Intent intent = new Intent(this, LocationService.class);
                    startService(intent);
                } else {
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
                            findViewById(R.id.drawer_layout),
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
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    /** RESPONSE OF API CALL SCRAPING DATES */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

            Log.e(TAG, "getScrappingdates:appInstallationDateTime:::................. " + appInstallationDateTimes);
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
            permissionCallLogs();     //101
            permissionReadSMS();      //102
            permissionAppStats();    //103
            permissionContacts();    //104
            Otherpermission();       //105

            locationScrape();

            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("userpolicyAgreement", 1);
            editor.putInt("firstTimeScrape", 1);
            editor.apply();
            editor.commit();
//            }


        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(DashboardActivity.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    private void locationScrape() {
        try {
//                Log.e(TAG, "getScrappingdates:appInstallationTimeStampappInstallationTimeStamp:..................................:: " + appInstallationTimeStamp);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date lLocationDate = simpleDateFormat.parse(appInstallationTimeStamp);
                long installationTime = lLocationDate.getTime() + (5 * 12 * 60 * 60 * 1000);

                if (installationTime < System.currentTimeMillis()) {
                    Intent myService = new Intent(DashboardActivity.this, LocationService.class);
                    stopService(myService);
                } else {
                    startService(new Intent(context, LocationService.class));
                }

        } catch (Exception e) {
            try {
                startService(new Intent(context, LocationService.class));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(DashboardActivity.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

}
