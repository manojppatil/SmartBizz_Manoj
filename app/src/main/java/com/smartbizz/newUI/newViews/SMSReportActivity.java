package com.smartbizz.newUI.newViews;

import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbizz.R;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.Util.VerticalLineDecorator;

import com.smartbizz.newUI.adapter.SMSReportAdapter;
import com.smartbizz.newUI.network.NetworkManager;
import com.smartbizz.newUI.pojo.mSMS;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.smartbizz.Util.DialogUtil.dismissProgressDialog;

public class SMSReportActivity extends BaseActivity {

    private Toolbar toolbar;

    RecyclerView recyclerView;
    public SMSReportAdapter smsReportAdapter;
    List<mSMS> mSMSList;
    public int beatCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        setContentView(R.layout.activity_sms_report);

        toolbar = findViewById(R.id.toolbar);

        recyclerView = findViewById(R.id.recycler_beat_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SMS Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.white));
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getMessageId();
        getReport();
    }

    private void getMessageId() {
        DialogUtil.showProgressDialog(activity);
        NetworkManager.getInstance(activity).getMessageId(activity, response -> {
            dismissProgressDialog();
            if (response.isSuccess()) {
            }
        });

    }

    private void getReport() {
        DialogUtil.showProgressDialog(activity);
        NetworkManager.getInstance(activity).getReport(activity, response -> {
            dismissProgressDialog();
//            if (response.isSuccess()) {
            mSMSList = null;
            smsReportAdapter = null;

            mSMSList = new ArrayList<>();

            smsReportAdapter = new SMSReportAdapter(activity, mSMSList);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            recyclerView.addItemDecoration(new VerticalLineDecorator(2));
            recyclerView.setAdapter(smsReportAdapter);

            JSONObject jsonObject = response.getResponse();
            if (jsonObject == null)
                return;

//            beatCount = (int) resultObj.opt("total");

            if (jsonObject != null) {
                JSONArray leadsArray = jsonObject.optJSONArray("data");
                if (leadsArray != null && leadsArray.length() > 0) {
                    int size = leadsArray.length();
                    for (int i = 0; i < size; i++) {
                        JSONObject applicationJson = leadsArray.optJSONObject(i);
                        if (applicationJson != null) {
                            mSMS application = new mSMS(leadsArray.optJSONObject(i));
                            mSMSList.add(application);
                        }
                    }

                    smsReportAdapter.notifyDataSetChanged();
                }
            }

//            }
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


}
