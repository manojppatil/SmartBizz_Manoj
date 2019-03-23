package com.eduvanzapplication.newUI.newViews;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.fragments.CurrentAddressFragment;
import com.eduvanzapplication.newUI.fragments.DocumentAvailabilityFragment;
import com.eduvanzapplication.newUI.fragments.PersonalDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class NewLeadActivity extends AppCompatActivity implements PersonalDetailsFragment.OnFragmentInteractionListener,
                                                            CurrentAddressFragment.OnFragmentInteractionListener,
                                                            DocumentAvailabilityFragment.OnFragmentInteractionListener {

    public static ViewPager viewPager;
    private ImageView ivNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lead);

        setViews();
        setupViewPager(viewPager);
    }

    private void setViews() {

        viewPager = findViewById(R.id.viewpager);
        ivNextBtn = findViewById(R.id.ivNextBtn);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE){
                    if (viewPager.getCurrentItem() == 1){

                    }
                }
            }
        });

        ivNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PersonalDetailsFragment(), getResources().getString(R.string.personal_details));
        adapter.addFrag(new DocumentAvailabilityFragment(), "Document Availability");
        adapter.addFrag(new CurrentAddressFragment(), "Current Address");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(boolean valid, int next) {
        viewPager.setCurrentItem(next);
        Toast.makeText(NewLeadActivity.this, ""+next, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOffButtons(boolean next, boolean prev) {
        if (next)
            ivNextBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
        else
            ivNextBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

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
