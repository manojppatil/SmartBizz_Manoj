package com.smartbizz.newUI.newViews;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.smartbizz.R;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.newUI.adapter.TabAdapter;
import com.smartbizz.newUI.network.ApiConstants;
import com.smartbizz.newUI.network.NetworkManager;
import com.smartbizz.newUI.pojo.Category;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.smartbizz.Util.DialogUtil.dismissProgressDialog;
import static com.smartbizz.newUI.newViews.EditImageActivity.FILE_PROVIDER_AUTHORITY;

public class PostCardTabActivity extends BaseActivity {

    private Toolbar toolbar;
    private TabAdapter adapter;
    private TabLayout tab;
    private ViewPager viewPager;
    public RecyclerView recyclerView;
    public static Bitmap bitmapLogo = null;
    private List<Category> disbursedList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView(R.layout.activity_postcard_tab);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Brand Promotion");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.white));
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        viewPager = findViewById(R.id.viewPager);
        tab = findViewById(R.id.tabLayout);
        showTabs();
    }

    private void setupViewPager() {

        for (int k = 0; k < disbursedList.size(); k++) {
            tab.addTab(tab.newTab().setText("" + disbursedList.get(k).getName().toString()));
        }

        adapter = new TabAdapter
                (getSupportFragmentManager(), tab.getTabCount(),disbursedList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tabs) {
                //do stuff here
                viewPager.setCurrentItem(tabs.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        adapter.notifyDataSetChanged();
    }

    private void showTabs(){
        DialogUtil.showProgressDialog(activity);
        disbursedList.clear();
        NetworkManager.getInstance(activity).getBrandPromotion(activity,"", response -> {
            dismissProgressDialog();
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

                            Category application1 = new Category("","","","All","All","","");

                            disbursedList.add(application1);

                            for (int i = 0; i < size; i++) {
                                JSONObject categoryJson = categoryArray.optJSONObject(i);
                                if(categoryJson != null) {
                                    Category application = new Category(categoryArray.optJSONObject(i));
                                    disbursedList.add(application);
                                }
                            }

                        }

                        setupViewPager();
                    }
                }
            }else{
                makeToast(response.getMessage());
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    public Uri buildFileProviderUri(@NonNull Uri uri) {
        return FileProvider.getUriForFile(PostCardTabActivity.this,
                FILE_PROVIDER_AUTHORITY,
                new File(uri.getPath()));
    }

}
