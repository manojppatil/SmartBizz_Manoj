package com.eduvanzapplication.newUI.newViews;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.digio.in.esign2sdk.Digio;
import com.digio.in.esign2sdk.DigioConfig;
import com.digio.in.esign2sdk.DigioEnvironment;
import com.eduvanzapplication.BuildConfig;
import com.eduvanzapplication.CustomTypefaceSpan;
import com.eduvanzapplication.LocationService;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.fragments.AmortizationFragment;
import com.eduvanzapplication.newUI.fragments.DetailedInfoFragment;
import com.eduvanzapplication.newUI.fragments.KycDetailFragment;
import com.eduvanzapplication.newUI.fragments.PostApprovalDocFragment;
import com.eduvanzapplication.newUI.fragments.UploadDocumentFragment;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;

public class LoanTabActivity extends AppCompatActivity implements KycDetailFragment.OnFragmentInteracting,
        DetailedInfoFragment.onDetailedInfoFragmentInteractionListener, UploadDocumentFragment.onUploadFragmentInteractionListener, PostApprovalDocFragment.onPostFragmentInteractionListener {

    private TabLayout tabLayout;
    private LinearLayout linDashBoard;
    public static ViewPager viewPager;
    public static String lead_id = "", student_id = "";
    Context context;
    public static AppCompatActivity mActivity;
    private final int REQUEST_LOCATION_PERMISSION = 1;
    SharedPreferences sharedPreferences;
    public static boolean isKycEdit;
    public static boolean isDetailedInfoEdit;
    public int GET_MY_PERMISSION = 1, permission;
    public LocationManager locationManager;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};

    //    //kyc values
    public static String firstName = "", applicant_id = "", tenureselectedflag = "";
    private AlertDialog mGPSDialog;
    // lastName = "", middleName = "", gender = "", dob = "", maritalStatus = "2", email = "", mobile = "",
//            aadhar = "", pan = "", flatBuildingSociety = "", streetLocalityLandmark = "", pincode = "", countryId = "", stateId = "", cityId = "",
//            instituteId = "", courseId = "", instituteLocationId = "", courseFee = "", applicant_id = "",
//            application_id = "", requested_loan_amount = "", institute_name = "", location_name = "",
//            course_name = "", course_cost = "", fk_institutes_id = "", fk_insitutes_location_id = "", fk_course_id = "", lead_status = "",
//            lead_sub_status = "", current_status = "", current_stage = "", has_aadhar_pan = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        context = getApplicationContext();
        mActivity = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        try {
            Bundle extras = getIntent().getExtras();
            MainActivity.lead_id = lead_id = extras.getString("lead_id", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }

        applicant_id = "";
        isKycEdit = false;
        isDetailedInfoEdit = false;
        try {
            sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            firstName = sharedPreferences.getString("firstName", "");
            MainActivity.auth_token = sharedPreferences.getString("auth_token", "");
            student_id = sharedPreferences.getString("student_id", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewPager = findViewById(R.id.viewpager1);
        setupViewPager(viewPager);

        linDashBoard = findViewById(R.id.linDashBoard);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        linDashBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION);

            if (permission != PackageManager.PERMISSION_GRANTED) {//Direct Permission without disclaimer dialog
                ActivityCompat.requestPermissions(LoanTabActivity.this,
                        new String[]{
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        GET_MY_PERMISSION);
            } else {

                locationManager = (LocationManager) getSystemService(context.LOCATION_SERVICE);

                //statusofGPS is use for location is on/off

                boolean statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                //this below code for set location mode to highaccuracy

                try {
                    //locationMode is use for which mode type is selected currently
                    //locationMode=0 is high acc is selected
                    //locationMode=1 is Device only is selected
                    //locationMode=2 is Battery saving is selected

                    int locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

                    //location on and high acc mode or device only selected

                    if (locationMode == LOCATION_MODE_HIGH_ACCURACY && statusOfGPS == true || locationMode == 1 && statusOfGPS == true) {

                        //request location updates
                        startService(new Intent(context, LocationService.class));

                    }
                    //gps off

                    else if (statusOfGPS == false) {

                        if (locationMode == 0) {

                            //gps off and high acc selected

                            showGPSDisabledAlertToUser();

                        } else if (!(locationMode == LOCATION_MODE_HIGH_ACCURACY) && statusOfGPS == false) {

                            //gps on and high acc.mode isn't selected

                            showGPSModeAlertToUser();

                        }


                    }

                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                }

             /*   if ( locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {

                   // Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
                startService(new Intent(context, LocationService.class));

                }else{
                  //  buildAlertMessageNoGps();
                    showGPSDisabledAlertToUser();

                }
*/
                // showGPSDisabledAlertToUser();

                //  startService(new Intent(context, LocationService.class));
//                if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//                    if (Globle.isNetworkAvailable(LoanTabActivity.this)) {
//                        alertDialog.show();
//                    }
//                }
            }
        }

//        try {
//            //  if (Build.VERSION.SDK_INT >= 23) {
//            if (!hasPermissions(this, PERMISSIONS)) {
//                ActivityCompat.requestPermissions(LoanTabActivity.this, PERMISSIONS, PERMISSION_ALL);
//            } else {
//                startService(new Intent(context, LocationService.class));
//                if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
//                    if (Globle.isNetworkAvailable(LoanTabActivity.this)) {
////                        alertDialog.show();
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//
//        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {

            switch (requestCode) {
                case 1:
                    if (grantResults.length <= 0) {
                        // If user interaction was interrupted, the permission request is cancelled and you
                        // receive empty arrays.
                    } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                            if (Globle.isNetworkAvailable(LoanTabActivity.this)) {
                                startService(new Intent(context, LocationService.class));
//                                alertDialog.show();
                            } else {
//                                alertDialog.dismiss();
//                                alertDialog.cancel();
                                Globle.dialog(LoanTabActivity.this, "Please turn on your cellular data or wifi for exact location.", "No internet access").show();
                            }

                        } else {
//                            showGPSDisabledAlertToUser();
                        }
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.

                    } else {
                        // Permission denied.
//                        alertDialog.dismiss();
//                        alertDialog.cancel();
                        Snackbar.make(
                                findViewById(R.id.Tabs),
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

                    }
                    break;
            }
//            switch (requestCode) {
//                case 1:
//                    if (grantResults.length > 0
//                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        // CameraOn();
//                        // permission was granted, yay! Do the
//                        // contacts-related task you need to do.
//                    }
//                    break;
//                case 2:
//                    try {
//                        if (grantResults.length > 0
//                                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                            // permission was granted, yay! Do the
//                            // contacts-related task you need to do.
//                            startLocation();
//                            // showLast();
//                        }
//                    } catch (Exception e) {
//                        if (NetworkUtils.isNetworkAvailable(AddVisitsEntry.this)) {
//                            MainActivity.ErrorLog("AddVisitsEntry", "onRequestPermissionsResultCase2", e.getMessage().toString().replace("'"," "), String.valueOf(e.getStackTrace()[0]), AddVisitsEntry.this);
//                        }
//                    }
//                    break;
//
//            }
        } catch (Exception e) {

        }
    }


    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled,Please enable it !")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });

//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        dialog.cancel();
//                    }
//                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

   /* public void showGPSDiabledDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS Disabled");
        builder.setMessage("Gps is disabled, in order to use the application properly you need to enable GPS of your device");
        builder.setPositiveButton("Enable GPS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),GPS_ENABLE_REQUEST);
            }
        }).setNegativeButton("No, Just Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        mGPSDialog = builder.create();
        mGPSDialog.show();
    }*/


    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device.\nGoto Settings Page To Enable GPS and set Mode to High accuracy")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
//        alertDialogBuilder.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void showGPSModeAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please select GPS mode to High Accuracy.\nGoto Settings Page To Enable GPS Mode")
                .setCancelable(false)
                .setPositiveButton("Settings",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                            }
                        });
//        alertDialogBuilder.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public void onBackPressed() {
        try {
            if (LoanTabActivity.isKycEdit == true || LoanTabActivity.isDetailedInfoEdit == true) {
                Log.e(MainActivity.TAG, "onBackPressed: " + MainActivity.currrentFrag);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.savedialog, null);
                dialogBuilder.setView(dialogView);
                LinearLayout buttonNo = dialogView.findViewById(R.id.button_dialog_no);
                LinearLayout buttonSave = dialogView.findViewById(R.id.button_dialog_save);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        LoanTabActivity.super.onBackPressed();
                        finish();//coment this line
                        //                    if (!MainActivity.borrowerValue13.equals("") &&
                        //                            !MainActivity.borrowerValue14.equals("") &&
                        //                            !MainActivity.borrowerValue15.equals("") &&
                        //                            !MainActivity.borrowerValue18.equals("")
                        //                            ||
                        //                            !MainActivity.coborrowerValue14.equals("") &&
                        //                                    !MainActivity.coborrowerValue15.equals("") &&
                        //                                    !MainActivity.coborrowerValue18.equals("") &&
                        //                                    !MainActivity.coborrowerValue13.equals("")) {
//                        if (MainActivity.currrentFrag == 1) {
//                            MainActivity.apiCallBorrower(context, mActivity, userID);
//                        } else if (MainActivity.currrentFrag == 2) {
//                            MainActivity.apiCallCoBorrower(context, mActivity, userID);
//                        }

                        //                    } else {
                        //                        alertDialog.dismiss();
                        //                        Toast.makeText(getApplicationContext(), "Please provide Firstname, Lastname, Dob and Aadhaar No.", Toast.LENGTH_LONG).show();
                        //                    }
                    }
                });
            } else {
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupViewPager(final ViewPager viewPager) {

        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/sourcesanspro_italic.ttf");

        SpannableString mKycDetail = new SpannableString("KYC Details");
        mKycDetail.setSpan(new CustomTypefaceSpan("", font), 0, mKycDetail.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString mDetailedInfo = new SpannableString("Detailed Info");
        mDetailedInfo.setSpan(new CustomTypefaceSpan("", font), 0, mDetailedInfo.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString mDocUplaod = new SpannableString("Documents Upload");
        mDocUplaod.setSpan(new CustomTypefaceSpan("", font), 0, mDocUplaod.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString mPostAppr = new SpannableString("Post approval Documentation");
        mPostAppr.setSpan(new CustomTypefaceSpan("", font), 0, mPostAppr.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString mAmort = new SpannableString("Amortization");
        mAmort.setSpan(new CustomTypefaceSpan("", font), 0, mAmort.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFrag(new KycDetailFragment(), String.valueOf(mKycDetail));
        adapter.addFrag(new DetailedInfoFragment(), String.valueOf(mDetailedInfo));
        adapter.addFrag(new UploadDocumentFragment(), String.valueOf(mDocUplaod));
        adapter.addFrag(new PostApprovalDocFragment(), String.valueOf(mPostAppr));
        adapter.addFrag(new AmortizationFragment(), String.valueOf(mAmort));

        tenureselectedflag = getIntent().getExtras().getString("tenureselectedflag");

//        adapter.addFrag(new KycDetailFragment(), "KYC Details");
//        adapter.addFrag(new DetailedInfoFragment(), "Detailed Info");
//        adapter.addFrag(new UploadDocumentFragment(), "Documents Upload");
//        adapter.addFrag(new PostApprovalDocFragment(), "Post approval Documentation");
//        adapter.addFrag(new AmortizationFragment(), "Amortization");
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

               /* if ( adapter.mFragmentList.toString().contains("DetailedInfoFragment")){
                    adapter.addFrag(new DetailedInfoFragment(), String.valueOf(mDetailedInfo));
                }*/
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (viewPager.getCurrentItem() == 1) {
                        if (isKycEdit == true)
                            KycDetailFragment.validate();
                    }

                    if (viewPager.getCurrentItem() == 2) {
                        if (isDetailedInfoEdit == true)
                            DetailedInfoFragment.validate();
                    }

                    if (viewPager.getCurrentItem() == 3) {
                        if (PostApprovalDocFragment.linNoLoan.getVisibility() == View.VISIBLE)
                            PostApprovalDocFragment.showDiaThanksDiag();
                    }

                    if (viewPager.getCurrentItem() == 4) {
                        if (AmortizationFragment.linNoAmort.getVisibility() == View.VISIBLE)
                            AmortizationFragment.showDiaThanksDiag();
                    }

                }
            }
        });
    }

    @Override
    public void onDetailedInfoFragment(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }

    @Override
    public void onUploadInfoFragment(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }

    @Override
    public void onFragmentInteraction(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }

    @Override
    public void onPostApprovalFragment(boolean valid, int next) {
        viewPager.setCurrentItem(next);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static void initDigio(String documentID, String email) {

        final Digio digio = new Digio();
        DigioConfig digioConfig = new DigioConfig();
        digioConfig.setLogo("https://lh3.googleusercontent.com/v6lR_JSsjovEzLBkHPYPbVuw1161rkBjahSxW0d38RT4f2YoOYeN2rQSrcW58MAfuA=w300"); //Your company logo
        digioConfig.setEnvironment(DigioEnvironment.PRODUCTION);   //Stage is sandbox

        try {
            digio.init(mActivity, digioConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            digio.esign(documentID, email);
        } catch (Exception e) {
            String errorLine = String.valueOf(e.getStackTrace()[0]);
        }
    }

    public void onSigningSuccess(String documentId, String message) {
        Log.e(MainActivity.TAG, "onSigningSuccess2: ");
//        Toast.makeText(context, documentId +" " + message, Toast.LENGTH_SHORT).show();
        PostApprovalDocFragment.digioSuccess(documentId);//DID180802180658447Q6OOLIITSFR2DJ
    }

    public void onSigningFailure(String documentId, int code, String response) {
        Log.e(MainActivity.TAG, "onSigningFailure: ");
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        PostApprovalDocFragment.digioFailure(documentId, code, response);
    }
}
