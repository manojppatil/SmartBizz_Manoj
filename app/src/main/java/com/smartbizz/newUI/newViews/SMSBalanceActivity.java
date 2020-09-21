package com.smartbizz.newUI.newViews;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.smartbizz.R;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.newUI.network.NetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import static com.smartbizz.Util.DialogUtil.dismissProgressDialog;

public class SMSBalanceActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvSMSBalance;
    private Button btnRefresh;
    private Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();
        setContentView(R.layout.activity_sms_balance);

        toolbar = findViewById(R.id.toolbar);

        tvSMSBalance = findViewById(R.id.tvSMSBalance);
        btnRefresh = findViewById(R.id.btnRefresh);

        btnRefresh.setOnClickListener(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SMS Report");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            toolbar.getNavigationIcon().setTint(getResources().getColor(R.color.white));
        }
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        getSMSBalance();
    }

    private void getSMSBalance() {
        DialogUtil.showProgressDialog(activity);
        NetworkManager.getInstance(activity).getBalanceSMS(activity, response -> {
            dismissProgressDialog();
            String msgcnt = "0";
            try {
                if(response.getResponse() != null) {
                    msgcnt = String.valueOf(response.getResponse().getJSONObject("data").get("balance"));
                }else{
                    makeToast(response.getMessage());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tvSMSBalance.setText("Your current SMS Balance is " + msgcnt);

        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnRefresh:
                getSMSBalance();
                break;
        }
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
