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
import com.eduvanzapplication.newUI.fragments.EmploymentDetailsFragment;
import com.eduvanzapplication.newUI.fragments.PersonalDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class NewLeadActivity extends AppCompatActivity implements PersonalDetailsFragment.OnFragmentInteractionListener,
                                                            CurrentAddressFragment.OnCurrentAddrFragmentInteractionListener,
                                                            DocumentAvailabilityFragment.OnDocumentFragmentInteractionListener,
                                                            EmploymentDetailsFragment.OnEmploymentFragmentInteractionListener {

    public static ViewPager viewPager;
    private ImageView ivNextBtn, ivPrevBtn;

    View dot1,dot2, dot3, dot4, dot5, track1, track2, track3, track4, track5;

    public static String firstName="", lastName="", middleName="", gender="2", maritalStatus="0", dob="";
    public static String documents = "1", aadharNumber="", panNUmber="";
    public static String flatBuildingSoc="", streetLocalityLandMark="", pinCode="", country="", state="", city="";
    public static String companyName="", annualIncome="";


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
        ivPrevBtn = findViewById(R.id.ivPrevBtn);
        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);
        dot4 = findViewById(R.id.dot4);
        track1 = findViewById(R.id.track1);
        track2 = findViewById(R.id.track2);
        track3 = findViewById(R.id.track3);
        track4 = findViewById(R.id.track4);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    ivPrevBtn.setVisibility(View.GONE);
                else
                    ivPrevBtn.setVisibility(View.VISIBLE);

                switch (position){
                    case 0:
                        dot1.setVisibility(View.VISIBLE);
                        track1.setVisibility(View.VISIBLE);
                        dot2.setVisibility(View.GONE);
                        track2.setVisibility(View.GONE);
                        dot3.setVisibility(View.GONE);
                        track3.setVisibility(View.GONE);
                        dot4.setVisibility(View.GONE);
                        track4.setVisibility(View.GONE);
                        break;
                    case 1:
                        dot1.setVisibility(View.VISIBLE);
                        track1.setVisibility(View.VISIBLE);
                        dot2.setVisibility(View.VISIBLE);
                        track2.setVisibility(View.VISIBLE);
                        dot3.setVisibility(View.GONE);
                        track3.setVisibility(View.GONE);
                        dot4.setVisibility(View.GONE);
                        track4.setVisibility(View.GONE);
                        break;
                    case 2:
                        dot1.setVisibility(View.VISIBLE);
                        track1.setVisibility(View.VISIBLE);
                        dot2.setVisibility(View.VISIBLE);
                        track2.setVisibility(View.VISIBLE);
                        dot3.setVisibility(View.VISIBLE);
                        track3.setVisibility(View.VISIBLE);
                        dot4.setVisibility(View.GONE);
                        track4.setVisibility(View.GONE);
                        break;
                    case 3:
                        dot1.setVisibility(View.VISIBLE);
                        track1.setVisibility(View.VISIBLE);
                        dot2.setVisibility(View.VISIBLE);
                        track2.setVisibility(View.VISIBLE);
                        dot3.setVisibility(View.VISIBLE);
                        track3.setVisibility(View.VISIBLE);
                        dot4.setVisibility(View.VISIBLE);
                        track4.setVisibility(View.VISIBLE);

                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE){
                    if (viewPager.getCurrentItem() == 1){
                        PersonalDetailsFragment.validate();
                    }
                    if (viewPager.getCurrentItem() == 2){
                        DocumentAvailabilityFragment.validate();
                    }
                    if (viewPager.getCurrentItem() == 3){
                        CurrentAddressFragment.validate();
                    }
                }
            }
        });

        ivNextBtn.setOnClickListener(nextClickListener);
        ivPrevBtn.setOnClickListener(prevClickListener);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PersonalDetailsFragment(), getResources().getString(R.string.personal_details));
        adapter.addFrag(new DocumentAvailabilityFragment(), "Document Availability");
        adapter.addFrag(new CurrentAddressFragment(), "Current Address");
        adapter.addFrag(new EmploymentDetailsFragment(), "Employment Details");
        viewPager.setAdapter(adapter);
    }

    View.OnClickListener nextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
        }
    };
    View.OnClickListener prevClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (viewPager.getCurrentItem() != 0)
                viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
        }
    };

    @Override
    public void onOffButtons(boolean next, boolean prev) {
        enableDisableButtons(next, prev);
    }

    @Override
    public void onOffButtonsDocuments(boolean next, boolean prev) {
        enableDisableButtons(next, prev);
    }
    @Override
    public void onOffButtonsCurrentAddress(boolean next, boolean prev) {
        enableDisableButtons(next, prev);
    }
    @Override
    public void onOffButtonEmployent(boolean next, boolean prev) {
        enableDisableButtons(next, prev);
    }


    private void enableDisableButtons(boolean next, boolean prev) {
        if (next){
            ivNextBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
            ivNextBtn.setOnClickListener(nextClickListener);
        }
        else{
            ivNextBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
            ivNextBtn.setOnClickListener(null);
        }
        if (prev){
            ivPrevBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
            ivPrevBtn.setOnClickListener(prevClickListener);
        }else
            ivPrevBtn.setOnClickListener(prevClickListener);
    }


    @Override
    public void onFragmentInteraction(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }

    @Override
    public void onDocumentFragmentInteraction(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }

    @Override
    public void onCurrentAddrFragmentInteraction(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }

    @Override
    public void onEmploymentFragmentInteraction(boolean valid, int next) {
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
