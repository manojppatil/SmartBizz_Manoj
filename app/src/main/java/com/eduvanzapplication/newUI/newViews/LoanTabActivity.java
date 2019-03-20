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
import com.eduvanzapplication.newUI.fragments.AgreementSigningFragment;
import com.eduvanzapplication.newUI.fragments.DetailedInfoFragment;
import com.eduvanzapplication.newUI.fragments.KycDetailFragment;
import com.eduvanzapplication.newUI.fragments.UploadDocumentFragment;

import java.util.ArrayList;
import java.util.List;

public class LoanTabActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
        adapter.addFrag(new DetailedInfoFragment(), "Detailed Ino");
        adapter.addFrag(new UploadDocumentFragment(), "Doc Upload");
        adapter.addFrag(new AgreementSigningFragment(), "Agreement SignIn");
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
