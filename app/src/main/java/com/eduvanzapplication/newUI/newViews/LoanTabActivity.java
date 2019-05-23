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
import android.view.View;
import android.widget.LinearLayout;

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

public class LoanTabActivity extends AppCompatActivity implements KycDetailFragment.OnFragmentInteracting,
        DetailedInfoFragment.onDetailedInfoFragmentInteractionListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private LinearLayout linDashBoard;
    public static ViewPager viewPager;
    public static String lead_id = "", student_id = "";
    Context context;
    AppCompatActivity mActivity;
    SharedPreferences sharedPreferences;
    public static boolean isKycEdit;
    public static boolean isDetailedInfoEdit;

    //kyc values
    public static String firstName = "", lastName = "", middleName = "", gender = "", dob = "", maritalStatus = "2", email = "", mobile = "",
            aadhar = "", pan = "", flatBuildingSociety = "", streetLocalityLandmark = "", pincode = "", countryId = "", stateId = "", cityId = "",
            instituteId = "", courseId = "", instituteLocationId = "", courseFee = "", applicant_id = "",
            application_id = "", requested_loan_amount = "", institute_name = "", location_name = "",
            course_name = "", course_cost = "", fk_institutes_id = "", fk_insitutes_location_id = "", fk_course_id = "", lead_status = "",
            lead_sub_status = "", current_status = "", current_stage = "", has_aadhar_pan = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);

        context = getApplicationContext();
        mActivity = this;

        try {
            Bundle extras = getIntent().getExtras();
            MainActivity.lead_id = lead_id = extras.getString("lead_id", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
