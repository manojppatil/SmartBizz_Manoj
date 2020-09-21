package com.smartbizz.newUI.newViews;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.smartbizz.R;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.newUI.adapter.PagerAdapter;
import com.smartbizz.newUI.fragments.DisbursedApplicationsFragment;
import com.smartbizz.newUI.fragments.PendingApplicationsFragment;
import com.smartbizz.newUI.network.ApiConstants;
import com.smartbizz.newUI.network.NetworkManager;
import com.smartbizz.newUI.pojo.Category;
import com.smartbizz.newUI.pojo.Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
////1.Design getcategories
////selct and comment.
//
////categories id,name    Requested. image,comment,category,action
////Request

public class DesignActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private TextView tvDisbursal, tvPending;
    private View viewDisbursal, viewPending;
    private ViewPager viewPager;
    private List<Requests> pendingsList = new ArrayList<>();
            private List<Category> disbursedList = new ArrayList<>();
    private int selectedTabIndex = 0;
    private String baseUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        initViews();
        registerListeners();
        getApplicationList();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        getApplicationList();
//
//    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getApplicationList();
    }

    private void initViews() {
        tvDisbursal = findViewById(R.id.tvDisbursal);
        viewDisbursal = findViewById(R.id.viewDisbursal);
        tvPending = findViewById(R.id.tvPending);
        viewPending = findViewById(R.id.viewPending);
        viewPager = findViewById(R.id.viewPager);
    }

    private void registerListeners() {
        tvDisbursal.setOnClickListener(this);
        viewDisbursal.setOnClickListener(this);
        tvPending.setOnClickListener(this);
        viewPending.setOnClickListener(this);
        viewPager.addOnPageChangeListener(this);
    }

    private void setupViewPager() {
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
//        if(disbursedList.size()>0) {
//            viewDisbursal.setVisibility(View.VISIBLE);
//            tvDisbursal.setVisibility(View.VISIBLE);
//            adapter.addFragment(DisbursedApplicationsFragment.newInstance(disbursedList));
//        }else{
//            selectPendingApplication();
//            viewDisbursal.setVisibility(View.GONE);
//            tvDisbursal.setVisibility(View.GONE);
//        }

        if(pendingsList.size()>0) {
            viewPending.setVisibility(View.VISIBLE);
            tvPending.setVisibility(View.VISIBLE);
            adapter.addFragment(PendingApplicationsFragment.newInstance(pendingsList));
        }else{
            selectPendingApplication();
            viewPending.setVisibility(View.GONE);
            tvPending.setVisibility(View.GONE);
        }
        adapter.addFragment(DisbursedApplicationsFragment.newInstance(disbursedList));

        viewPager.setOffscreenPageLimit(adapter.getCount());
        viewPager.setAdapter(adapter);
        viewPager.getAdapter().notifyDataSetChanged();
        if(selectedTabIndex<=adapter.getCount()){
            viewPager.setCurrentItem(selectedTabIndex);
        }
    }

    public void getApplicationList() {
        DialogUtil.showProgressDialog(activity);
        disbursedList.clear();
        pendingsList.clear();//digiepost.in
        NetworkManager.getInstance(activity).getApplicationsList(activity, response -> {
            if (response.isSuccess()) {
                JSONObject jsonObject = response.getResponse();
                if (jsonObject != null) {
                    JSONObject resultObj = jsonObject.optJSONObject(ApiConstants.Keys.RESULT);
                    if (resultObj != null) {
                        JSONArray leadsArray = resultObj.optJSONArray("leadsData");
                        JSONArray categoryArray = resultObj.optJSONArray("category");
                        JSONArray requestsArray = resultObj.optJSONArray("requests");
                        if (categoryArray != null && categoryArray.length() > 0) {
                            int size = categoryArray.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject categoryJson = categoryArray.optJSONObject(i);
                                if(categoryJson != null) {
                                    Category application = new Category(categoryArray.optJSONObject(i));
                                    disbursedList.add(application);
                                }
                            }//resultObj.optString("url")

//                            setupViewPager();
                        }

                        if (requestsArray != null && requestsArray.length() > 0) {
                            int size = requestsArray.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject categoryJson = requestsArray.optJSONObject(i);
                                if(categoryJson != null) {
                                    Requests application = new Requests(requestsArray.optJSONObject(i));
                                    pendingsList.add(application);
                                }
                            }//resultObj.optString("url")

//                            setupViewPager();
                        }else {
                            makeToast("No design found. Create a new desing request");
                        }
                        setupViewPager();
                    }
                }
            }else{
                makeToast(response.getMessage());
            }
            dismissProgressBar();
        });
    }

    private void dismissProgressBar(){
        new Handler().postDelayed(() -> DialogUtil.dismissProgressDialog(),1000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvDisbursal:
            case R.id.viewDisbursal:
                selectDisbursedApplication();
                if (viewPager.getChildCount() > 0) {
                    viewPager.setCurrentItem(0);
                }
                break;
            case R.id.tvPending:
            case R.id.viewPending:
                selectPendingApplication();
                if (viewPager.getChildCount() > 1) {
                    viewPager.setCurrentItem(1);
                }
                break;
        }
    }

    private void selectDisbursedApplication() {
        tvPending.setTextColor(ContextCompat.getColor(activity, R.color.colorSubTitle));
        viewPending.setVisibility(View.INVISIBLE);
        tvDisbursal.setTextColor(ContextCompat.getColor(activity, android.R.color.white));
        viewDisbursal.setVisibility(View.VISIBLE);
    }

    private void selectPendingApplication() {
        tvDisbursal.setTextColor(ContextCompat.getColor(activity, R.color.colorSubTitle));
        viewDisbursal.setVisibility(View.INVISIBLE);
        tvPending.setTextColor(ContextCompat.getColor(activity, android.R.color.white));
        viewPending.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        selectedTabIndex = position;
        switch (position) {
            case 0:
                selectDisbursedApplication();
                break;
            case 1:
                selectPendingApplication();
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(activity, DashboardActivity.class));
        finish();
    }
}
