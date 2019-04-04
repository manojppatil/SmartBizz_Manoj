package com.eduvanzapplication.newUI.newViews;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.eduvanzapplication.R;
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
    public static String lead_id="";
    //kyc values
    public static String firstName="", lastName="", middleName="", gender="2", dob="", maritalStatus="1", email="", mobile="",
                            aadhar="", pan="", flatBuildingSociety="", streetLocalityLandmark="", pincode="", countryId="", stateId="",cityId="",
                            instituteId="", courseId="", instituteLocationId="", courseFee="", loanAmount="";

    //detailed values

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
