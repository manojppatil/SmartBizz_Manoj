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
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.CameraUtils;
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
import com.google.gson.JsonObject;
import com.idfy.rft.RFTSdk;
import com.idfy.rft.RftSdkCallbackInterface;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private static ProgressBar progressbar;
    private static final String idfy_account_id = "99cde5a9e632/744939bd-4fe2-42e8-94d2-971a79928ee4";
    private static final String idfy_token = "2075c38b-31c3-4fc8-a642-ba7c02697c42";
    RFTSdk rftsdk;
    Bitmap bitmapFront = null, bitmapBack = null;
    String doctype = "";
    public static final String GALLERY_DIRECTORY_NAME = "Hello Camera";
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String IMAGE_EXTENSION = "jpg";

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
            rftsdk = RFTSdk.init(DashboardActivity.this, idfy_account_id, idfy_token);
            progressbar = (ProgressBar) findViewById(R.id.progressbar);

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

//        showOCRDialog();

    }

    private void showOCRDialog() {

        View view = getLayoutInflater().inflate(R.layout.layout_ocr_options,null);
        LinearLayout linPan = view.findViewById(R.id.linPan);
        LinearLayout linAadhar = view.findViewById(R.id.linAadhar);
        LinearLayout linClose =view.findViewById(R.id.linClose);
        LinearLayout linFooter1 = view.findViewById(R.id.linFooter1);
        LinearLayout linTakePicture = view.findViewById(R.id.linTakePicture);
        LinearLayout linQR = view.findViewById(R.id.linQR);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .setFooterView(view);

        CFAlertDialog cfAlertDialog = builder.show();
        cfAlertDialog.setCancelable(false);
        cfAlertDialog.setCanceledOnTouchOutside(false);
        linFooter1.setVisibility(View.VISIBLE);
        linTakePicture.setVisibility(View.GONE);
        linQR.setVisibility(View.GONE);


        linClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cfAlertDialog.dismiss();
            }
        });

        linPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linTakePicture.setVisibility(View.VISIBLE);
                linQR.setVisibility(View.GONE);
                linFooter1.setVisibility(View.GONE);




            }
        });

        linAadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linTakePicture.setVisibility(View.VISIBLE);
                linQR.setVisibility(View.VISIBLE);
                linFooter1.setVisibility(View.GONE);
            }
        });

        linTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linQR.getVisibility() != View.VISIBLE){  //pan is selected
                    if (CameraUtils.checkPermissions(getApplicationContext())) {
                        doctype = "ind_pan";
                        rftsdk.CaptureDocImage(DashboardActivity.this, "ind_pan", rftSdkCallbackInterface);
                    } else {
//                        requestCameraPermission(MEDIA_TYPE_IMAGE);
                    }

                }else{
                    if (CameraUtils.checkPermissions(getApplicationContext())) {
                        Toast.makeText(context, "Capture front-side image of Aadhaar", Toast.LENGTH_LONG).show();

                        doctype = "ind_aadhaar";
//                    doctype = "aadhaar_ocr";
                        rftsdk.CaptureDocImage(DashboardActivity.this, "ind_aadhaar", rftSdkCallbackInterface);
                    } else {
//                        requestCameraPermission(MEDIA_TYPE_IMAGE);
                    }

                }
            }
        });

    }

    private RftSdkCallbackInterface rftSdkCallbackInterface = new RftSdkCallbackInterface() {
        @Override
        public void onImageCaptureSuccess(final Bitmap image) {
            try {
                progressbar.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (doctype.equals("ind_pan")) {
                AsyncReq bv = new AsyncReq(rftsdk, image);
                bv.execute();

//                runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
//                        final Handler handler = new Handler();
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                AsyncReq bv = new AsyncReq(rftsdk, image);
//                                bv.execute();
//                            }
//                        }, 1000);
//
//                    }
//                });

            } else {
                if (bitmapFront == null) {
                    bitmapFront = image;
                    Toast.makeText(context, "Capture backside image of Aadhaar", Toast.LENGTH_LONG).show();
                    rftsdk.CaptureDocImage(DashboardActivity.this, "ind_aadhaar", rftSdkCallbackInterface);
                } else {
                    bitmapBack = image;
                    AsyncReqAaDhaar av = new AsyncReqAaDhaar(rftsdk, bitmapFront, bitmapBack);
                    av.execute();

//                    runOnUiThread(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            final Handler handler = new Handler();
//                            handler.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//
//                                    AsyncReqAaDhaar av = new AsyncReqAaDhaar(rftsdk, bitmapFront, bitmapBack);
//                                    av.execute();
//                                }
//                            }, 1000);
//
//                        }
//                    });

                }

            }
        }

        @Override
        public void onImageCaptureFaliure(JSONObject response) {
            JsonObject jsonObject = new JsonObject();
            Toast.makeText(context, "Upload Failure, Response-> " + jsonObject.toString(), Toast.LENGTH_LONG).show();
        }

    };

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


    private class AsyncReq extends AsyncTask<Void, Void, JSONObject> {

        RFTSdk rftsdk;
        Bitmap bitmap;

        public AsyncReq(RFTSdk instance, Bitmap bitmap) {
            this.rftsdk = instance;
            this.bitmap = bitmap;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {

            JSONObject result = null;
            try {
                String group = "g-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());

//                result = rftsdk.UploadImage("fa5942a5-73d7-4fd7-8158-f87f50c73c82",
//                        "0e48c481-fdc1-4c18-8f86-88aebc5b9a8a",
//                        group,
//                        doctype, bitmap);

//                "token": "2075c38b-31c3-4fc8-a642-ba7c02697c42",
//                "account_id": "99cde5a9e632/744939bd-4fe2-42e8-94d2-971a79928ee4"

                result = rftsdk.UploadImage(idfy_account_id, idfy_token, group, doctype, bitmap);
                // {"url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/17d8011c-9896-4c91-bcc1-1f233fd32505"}
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            JSONObject tasks = new JSONObject();
            try {
                String group = "g-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());
                String task = "t-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());
                tasks.put("type", "pan_ocr");
                tasks.put("group_id", group);
                tasks.put("task_id", task);
                tasks.put("data", new JSONObject().put("doc_url", (String) jsonObject.get("url")));

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray();

            jsonArray.put(tasks);

            JSONObject postobject = new JSONObject();
            try {
                postobject.put("tasks", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Test 3
//postobject = {"tasks":[{"type":"pan_ocr","group_id":"g-6","task_id":"t-13","data":{"doc_url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/51184dec-f70b-476a-9a47-269ec9faace8"}}]}
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "https://api.idfy.com/v2/tasks", postobject,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(final JSONObject response) {
//                            Log.d(TAG, response.toString());
                            try {
                                if (response.getString("request_id") != null) {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        getPANData((String) response.getString("request_id"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, 6000);

                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage()); //com.android.volley.ClientError
                    try {
                        progressbar.setVisibility(View.VISIBLE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }) {

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("apikey", account_id);
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    return super.parseNetworkResponse(response);
                }
            };

            // Adding request to request queue
            queue.add(jsonObjReq);

            //Test 2

//            RequestQueue queue = Volley.newRequestQueue(context);
//            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
//                    Request.Method.POST,"https://api.idfy.com/v2/tasks", postobject,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                            BreakIterator msgResponse = null;
//                            msgResponse.setText(response.toString());
//                        }
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.d(TAG, "Error: " + error.getMessage());
//                }
//            }) {
//
//                /**
//                 * Passing some request headers
//                 */
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("Content-Type", "application/x-www-form-urlencoded");
//                    return headers;
//                }
//
//                @Override
//                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                    return super.parseNetworkResponse(response);
//                }
//            };
//            queue.add(jsonObjReq);

            //Test 1
////postobject = {"tasks":[{"type":"pan_ocr","group_id":"g-6","task_id":"t-13","data":{"doc_url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/3778f140-a80f-49cc-964d-f6a9532c1a87"}}]}
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//
//            final String mRequestBody = postobject.toString();
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.idfy.com/v2/tasks", new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.i("LOG_RESPONSE", response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("LOG_RESPONSE", error.toString());
//                }
//            }) {
//                // Passing some request headers
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("Content-Type", "application/x-www-form-urlencoded");
//                    return headers;
//                }
//
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//                    try {
//                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                        return null;
//                    }
//                }
//
//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//                        responseString = String.valueOf(response.statusCode);
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
//            };
//
//            requestQueue.add(stringRequest);

//            Use response as required
//            It will contain a selflink for uploaded image

            Toast.makeText(context, "Upload succes, Response-> " + jsonObject.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private class AsyncReqAaDhaar extends AsyncTask<Void, Void, JSONArray> {

        RFTSdk rftsdk;
        Bitmap bitmap1, bitmap2;

        public AsyncReqAaDhaar(RFTSdk instance, Bitmap bitmapfrt, Bitmap bitmapbck) {
            this.rftsdk = instance;
            this.bitmap1 = bitmapfrt;
            this.bitmap2 = bitmapbck;
        }

        @Override
        protected JSONArray doInBackground(Void... voids) {

            JSONObject result1 = null;
            JSONObject result2 = null;
            try {
                String group = "g-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());

//                result1 = rftsdk.UploadImage("fa5942a5-73d7-4fd7-8158-f87f50c73c82",
//                        "0e48c481-fdc1-4c18-8f86-88aebc5b9a8a",
//                        group,
//                        doctype, bitmap1);
//
//                result2 = rftsdk.UploadImage("fa5942a5-73d7-4fd7-8158-f87f50c73c82",
//                        "0e48c481-fdc1-4c18-8f86-88aebc5b9a8a",
//                        group,
//                        doctype, bitmap2);

//                "token": "2075c38b-31c3-4fc8-a642-ba7c02697c42",
//                "account_id": "99cde5a9e632/744939bd-4fe2-42e8-94d2-971a79928ee4"
                result1 = rftsdk.UploadImage(idfy_account_id, idfy_token, group, doctype, bitmap1);//Unable to resolve host "signed-urls.idfy.com": No address associated with hostname

                result2 = rftsdk.UploadImage(idfy_account_id, idfy_token, group, doctype, bitmap2);//Unable to resolve host "signed-urls.idfy.com": No address associated with hostname
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(result1);
            jsonArray.put(result2);

            bitmapFront = null;
            bitmapBack = null;

            return jsonArray;
        }

        //{"url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/a5799223-7648-454f-9462-c8d5e9f17eff"}
        //{"url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/8dad48ce-fe07-453a-bcd1-552a43b115d6"}

        @Override
        protected void onPostExecute(JSONArray jsonArrayImg) {
            super.onPostExecute(jsonArrayImg);
            JSONObject tasks = new JSONObject();

            JSONObject data = new JSONObject();
            JSONArray docurl = new JSONArray();
            try {
                docurl.put(jsonArrayImg.getJSONObject(0).get("url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                docurl.put(jsonArrayImg.getJSONObject(1).get("url"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                data.put("doc_url", docurl);
                data.put("aadhaar_consent", "I, the holder of Aadhaar number, hereby give my consent to Baldor Technologies Private Limited, " +
                        "to obtain my Aadhaar number, name, date of birth, address and demographic data for authentication with UIDAI. " +
                        "Baldor Technologies Private Limited has informed me that my identity information would only be used " +
                        "for a background check or a verification of my identity and has also informed me that " +
                        "my biometrics will not be stored/ shared and will be submitted to CIDR only for the purpose of authentication. " +
                        "I have no objection if reports generated from such background check are shared with relevant third parties.");
            } catch (JSONException e) {
                e.printStackTrace();
            }//{"url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/4ecaa516-35a3-482d-97b0-d531cd417476"}
//            "data": {
//                "doc_url": "https://xyz.com/aadhar-123.jpg" ,
//                        "aadhaar_consent": "I, the holder of Aadhaar number, hereby give my consent to Baldor Technologies Private Limited, to obtain my Aadhaar number, name, date of birth, address and demographic data for authentication with UIDAI. Baldor Technologies Private Limited has informed me that my identity information would only be used for a background check or a verification of my identity and has also informed me that my biometrics will not be stored/ shared and will be submitted to CIDR only for the purpose of authentication. I have no objection if reports generated from such background check are shared with relevant third parties."
//            }
            try {
                String group = "g-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());
                String task = "t-" + new SimpleDateFormat("MM dd HH:mm").format(new Date());
                tasks.put("type", "aadhaar_ocr");
                tasks.put("group_id", group);
                tasks.put("task_id", task);
                tasks.put("data", data);

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JSONArray jsonArray = new JSONArray();

            jsonArray.put(tasks);

            JSONObject postobject = new JSONObject();
            try {
                postobject.put("tasks", jsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            Test 3
//postobject = {"tasks":[{"type":"pan_ocr","group_id":"g-6","task_id":"t-13","data":{"doc_url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/51184dec-f70b-476a-9a47-269ec9faace8"}}]}
            RequestQueue queue = Volley.newRequestQueue(context);

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "https://api.idfy.com/v2/tasks", postobject,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(final JSONObject response) {
                            Log.d(TAG, response.toString());
                            try {
                                if (response.getString("request_id") != null) {
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            final Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        getAadhaarData((String) response.getString("request_id"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, 6000);

                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage()); //com.android.volley.ClientError
                    try {
                        progressbar.setVisibility(View.GONE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }) {

                /**
                 * Passing some request headers
                 * */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {

                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", account_id);
                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    return super.parseNetworkResponse(response);
                }
            };

            jsonObjReq.setTag(TAG);
            // Adding request to request queue
            queue.add(jsonObjReq);

            //InCorrect Get Request
//            RequestQueue queue = Volley.newRequestQueue(context);
//
//            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET,"https://api.idfy.com/v2/tasks?request_id=5128d990-6287-4b3a-a9b8-ea12edc3459e",
//                    null, new Response.Listener<JSONObject>() {
//
//                @Override
//                public void onResponse(JSONObject response) {
//                    Log.d(TAG, response.toString());
//
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e(TAG, "Site Info Error: " + error.getMessage());
//                }
//            }) {
//
//                /**
//                 * Passing some request headers
//                 */
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("Content-Type", "application/json; charset=utf-8");
//                    return headers;
//                }
//            };
//            queue.add(req);

            //Test 2

//            RequestQueue queue = Volley.newRequestQueue(context);
//            JsonObjectRequest jsonObjReq = new JsonObjectRequest(
//                    Request.Method.POST,"https://api.idfy.com/v2/tasks", postobject,
//                    new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//
//                            BreakIterator msgResponse = null;
//                            msgResponse.setText(response.toString());
//                        }
//                    }, new Response.ErrorListener() {
//
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    VolleyLog.d(TAG, "Error: " + error.getMessage());
//                }
//            }) {
//
//                /**
//                 * Passing some request headers
//                 */
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("Content-Type", "application/x-www-form-urlencoded");
//                    return headers;
//                }
//
//                @Override
//                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
//                    return super.parseNetworkResponse(response);
//                }
//            };
//            queue.add(jsonObjReq);

            //Test 1
////postobject = {"tasks":[{"type":"pan_ocr","group_id":"g-6","task_id":"t-13","data":{"doc_url":"https:\/\/www.googleapis.com\/storage\/v1\/b\/storage.idfy.com\/o\/3778f140-a80f-49cc-964d-f6a9532c1a87"}}]}
//            RequestQueue requestQueue = Volley.newRequestQueue(context);
//
//            final String mRequestBody = postobject.toString();
//
//            StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.idfy.com/v2/tasks", new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.i("LOG_RESPONSE", response);
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.e("LOG_RESPONSE", error.toString());
//                }
//            }) {
//                // Passing some request headers
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                    headers.put("Content-Type", "application/x-www-form-urlencoded");
//                    return headers;
//                }
//
//                @Override
//                public String getBodyContentType() {
//                    return "application/json; charset=utf-8";
//                }
//
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//                    try {
//                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                        return null;
//                    }
//                }
//
//                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//                        responseString = String.valueOf(response.statusCode);
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
//            };
//
//            requestQueue.add(stringRequest);

//            Use response as required
//            It will contain a selflink for uploaded image

        }
    }


    private void getPANData(String Requestid) {

        RequestQueue queue12 = Volley.newRequestQueue(context);
        StringRequest getRequest = new StringRequest(Request.Method.GET, "https://api.idfy.com/v2/tasks?request_id=" + Requestid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            progressbar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Boolean success = false;
                        try {
                            if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("completed")) {
                                success = true;
                            } else if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("in_progress")) {
                                success = false;

                                try {
                                    progressbar.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    getPANData((String) new JSONArray(response.toString()).getJSONObject(0).getString("request_id"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, 3000);

                                    }
                                });

                            } else {
                                Toast.makeText(context, "OCR Response-> " + response.toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (success) {
                            String panno = "";
                            try {
                                panno = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("pan_number");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String pantype = "";
                            try {
                                pantype = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("pan_type");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String name = "";
                            try {
                                name = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("name_on_card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String dob = "";
                            try {
                                dob = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("date_on_card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String doi = "";
                            try {
                                doi = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("date_of_issue");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String age = "";
                            try {
                                age = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("age");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String fathersname = "";
                            try {
                                fathersname = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("fathers_name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String isminor = "";
                            try {
                                isminor = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("minor");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String isscanned = "";
                            try {
                                isscanned = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("is_scanned");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
//                        else {
//                            Toast.makeText(context, "Upload Failed, Response-> " + response.toString(), Toast.LENGTH_LONG).show();
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                        try {
                            progressbar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, "Upload Failed, Response-> " + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue12.add(getRequest);
    }

    private void getAadhaarData(String Requestid) {
        RequestQueue queue12 = Volley.newRequestQueue(context);//https://api.idfy.com/v2/tasks?request_id=d740cbd1-6af1-45f6-a609-8c2170dc3418
        StringRequest getRequest = new StringRequest(Request.Method.GET, "https://api.idfy.com/v2/tasks?request_id=" + Requestid,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        // response
                        Log.d("Response", response);
                        try {
                            progressbar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Boolean success = false;
                        try {
                            if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("completed")) {
                                success = true;
                            } else if (new JSONArray(response.toString()).getJSONObject(0).getString("status").toLowerCase().equals("in_progress")) {
                                success = false;

                                try {
                                    progressbar.setVisibility(View.VISIBLE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {

                                    @Override
                                    public void run() {
                                        final Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    getAadhaarData((String) new JSONArray(response.toString()).getJSONObject(0).getString("request_id"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }, 3000);

                                    }
                                });

                            } else {
                                Toast.makeText(context, "OCR Response-> " + response.toString(), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (success) {
                            String aadhaarno = "";
                            try {
                                aadhaarno = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("aadhaar_number");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String name = "";
                            try {
                                name = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("name_on_card");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String dob = "";
                            try {
                                dob = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("date_of_birth");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String yob = "";
                            try {
                                yob = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("year_of_birth");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String gender = "";
                            try {
                                gender = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("gender");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String address = "";
                            try {
                                address = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("address");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String street_address = "";
                            try {
                                street_address = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("street_address");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String district = "";
                            try {
                                district = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("gender");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String pincode = "";
                            try {
                                pincode = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("pincode");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String state = "";
                            try {
                                state = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("district");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            String isscanned = "";
                            try {
                                isscanned = new JSONObject(new JSONArray(response.toString()).getJSONObject(0).getString("ocr_output")).getString("state");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Log.v(TAG, response);

//                            Toast.makeText(context, "Upload succes " + aadhaarno + "\n" + name + "\n" + dob + "\n" + yob + "\n" + gender, Toast.LENGTH_LONG).show();
                        }
//                        else {
//                            Toast.makeText(context, "Upload Failed, Response-> " + response.toString(), Toast.LENGTH_LONG).show();
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR", "error => " + error.toString());
                        try {
                            progressbar.setVisibility(View.GONE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(context, "Upload Failure, Response-> " + error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("apikey", "fa5942a5-73d7-4fd7-8158-f87f50c73c82");
//                headers.put("apikey", account_id);
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };
        queue12.add(getRequest);
    }

}
