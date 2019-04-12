package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.fragments.AmortizationFragment;
import com.eduvanzapplication.newUI.fragments.DetailedInfoFragment;
import com.eduvanzapplication.newUI.fragments.KycDetailFragment;
import com.eduvanzapplication.newUI.fragments.PostApprovalDocFragment;
import com.eduvanzapplication.newUI.fragments.UploadDocumentFragment;

import java.util.ArrayList;
import java.util.List;

public class LoanTabActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public static String lead_id="",student_id ="";
    Context context;
    AppCompatActivity mActivity;
    SharedPreferences sharedPreferences;
    //kyc values
    public static String firstName="", lastName="", middleName="", gender="2", dob="", maritalStatus="1", email="", mobile="",
                            aadhar="", pan="", flatBuildingSociety="", streetLocalityLandmark="", pincode="", countryId="", stateId="",cityId="",
                            instituteId="", courseId="", instituteLocationId="", courseFee="", loanAmount="";

    //detailed values

    //Post Doc values
    public static String Postlead_id = "" ,Postapplication_loan_id= "" ,
    Postprincipal_amount= "" ,
            Postdown_payment= "" ,
    Postrate_of_interest= "" ,
            Postemi_type= "" ,
    Postemi_amount= "" ,
            Postrequested_loan_amount= "" ,
    Postrequested_tenure= "" ,
            Postrequested_roi= "" ,
    Postrequested_emi= "" ,
            Postoffered_amount= "" ,
    Postapplicant_id= "" ,
            Postfk_lead_id= "" ,
    Postfirst_name= "" ,
            Postlast_name= "" ,
    Postmobile_number= "" ,
            Postemail_id= "" ,
    Postkyc_address= "" ,
            Postcourse_cost= "" ,
    Postpaid_on= "" ,
            Posttransaction_amount= "" ,
    Postkyc_status= "" ,
            Postdisbursal_status= "" ,
    Postloan_agrement_upload_status= "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Instant Sanction");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.textcolordark));
//        }
//        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        context = getApplicationContext();
        mActivity = this;

        try {
            Bundle extras = getIntent().getExtras();
            MainActivity.lead_id = lead_id = extras.getString("lead_id", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
//        DashboardActivity.student_id;

        try {
            sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            firstName = sharedPreferences.getString("firstName", "");
            MainActivity.auth_token = sharedPreferences.getString("auth_token", "");
            student_id = sharedPreferences.getString("student_id", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void onBackPressed() {
            finish();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new KycDetailFragment(), "KYC Details");
        adapter.addFrag(new DetailedInfoFragment(), "Detailed Info");
        adapter.addFrag(new UploadDocumentFragment(), "Documents Upload");
        adapter.addFrag(new PostApprovalDocFragment(), "Post approval Documentation");
        adapter.addFrag(new AmortizationFragment(), "Amortization");
        viewPager.setAdapter(adapter);
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
