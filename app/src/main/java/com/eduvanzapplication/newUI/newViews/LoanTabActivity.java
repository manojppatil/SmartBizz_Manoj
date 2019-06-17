package com.eduvanzapplication.newUI.newViews;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

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


public class LoanTabActivity extends AppCompatActivity implements KycDetailFragment.OnFragmentInteracting,
        DetailedInfoFragment.onDetailedInfoFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private LinearLayout linDashBoard;
    public static ViewPager viewPager;
    public static String lead_id = "", student_id = "";
    Context context;
    public AppCompatActivity mActivity;
    SharedPreferences sharedPreferences;
    public static boolean isKycEdit;
    public static boolean isDetailedInfoEdit;
    public int GET_MY_PERMISSION = 1, permission;

    LocationManager locationManager;
    String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION};

    //    //kyc values
    public static String firstName = "", applicant_id = "";
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
                startService(new Intent(context, LocationService.class));
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
                                Globle.dialog(LoanTabActivity.this,"Please turn on your cellular data or wifi for exact location.", "No internet access").show();
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

    public void onBackPressed() {
        finish();
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
                }
            }
        });
    }

    @Override
    public void onDetailedInfoFragment(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }

    @Override
    public void onFragmentInteraction(boolean valid, int next) {
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
}
