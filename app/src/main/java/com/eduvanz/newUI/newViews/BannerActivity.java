package com.eduvanz.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.newUI.adapter.ViewPagerAdapterBanner;
import com.eduvanz.newUI.pojo.ViewPagerDashboardPOJO;
import com.eduvanz.volley.VolleyCallNew;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BannerActivity extends AppCompatActivity {

    TextView textViewBannerDetail, textViewBannerTitle;
    ImageView imageViewBanner;
    Button buttonCheckEligiblity;
    MainApplication mainApplication;
    static Context context;
    String image="", id="", title="", fromDeal="";
    AppCompatActivity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        context = this;
        mActivity = this;
        mainApplication = new MainApplication();

        try {
            Bundle bundle = getIntent().getExtras();
            image = bundle.getString("bannerImage");
            id = bundle.getString("bannerID");
            title = bundle.getString("bannerTitle");
            fromDeal = bundle.getString("from_deal","0");
        }catch (Exception e){
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Special Offers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        textViewBannerDetail = (TextView) findViewById(R.id.textView_bannerDetail);
        mainApplication.applyTypeface(textViewBannerDetail, context);

        textViewBannerTitle = (TextView) findViewById(R.id.textView_bannerTitle);
        mainApplication.applyTypeface(textViewBannerTitle, context);

        buttonCheckEligiblity = (Button) findViewById(R.id.button_bannerdetail_checkeligiblity);
        mainApplication.applyTypeface(buttonCheckEligiblity, context);

        buttonCheckEligiblity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EligibilityCheck.class);
                startActivity(intent);
            }
        });

        imageViewBanner = (ImageView) findViewById(R.id.imageView_bannerimage_1);

        /** API CALL**/
        if(fromDeal.equalsIgnoreCase("1")){
            try {
                String url = MainApplication.mainUrl + "mobileadverstisement/getSpecificDealDetails";
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "dealDetail", params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            try {
                String url = MainApplication.mainUrl + "mobileadverstisement/getSpecificBannerDetails";
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                VolleyCallNew volleyCall = new VolleyCallNew();
                volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "bannerDetail", params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    public void setBannerDetail(JSONObject jsonData) {
        try {
            Log.e(MainApplication.TAG, "setBannerDetail: "+ jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONObject jsonObject1 = jsonObject.getJSONObject("banner");

                textViewBannerTitle.setText(jsonObject1.getString("title"));

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    textViewBannerDetail.setText(Html.fromHtml(jsonObject1.getString("description"),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    textViewBannerDetail.setText(Html.fromHtml(jsonObject1.getString("description")));
                }

                String image = jsonObject1.getString("image");

                Picasso.with(context).load(image).placeholder(context.getResources().getDrawable(R.drawable.bannersplaceholder)).into(imageViewBanner);

            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDealDetail(JSONObject jsonData) {
        try {
            Log.e(MainApplication.TAG, "setBannerDetail: "+ jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONObject jsonObject1 = jsonObject.getJSONObject("deal");

                textViewBannerTitle.setText(jsonObject1.getString("title"));

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    textViewBannerDetail.setText(Html.fromHtml(jsonObject1.getString("description"),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    textViewBannerDetail.setText(Html.fromHtml(jsonObject1.getString("description")));
                }

                String image = jsonObject1.getString("image");

                Picasso.with(context).load(image).placeholder(context.getResources().getDrawable(R.drawable.bannersplaceholder)).into(imageViewBanner);

            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
