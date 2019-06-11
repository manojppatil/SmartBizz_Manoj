package com.eduvanzapplication.newUI.webviews;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;

public class WebViewOTPDiag extends AppCompatActivity {

    public static LinearLayout linClose, linDownload, linUpload;
    //OTP Diag
    public static LinearLayout linCloseOTP, layoutOtp, linSubmitOtp;
    public static EditText edtOtp;
    public static WebView webView;
    final int OTPlength = 6;
    Toolbar toolbar;
    public static ProgressBar progressBarDiag;
    public String weburl = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.signin_options_otp);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            }

            Toolbar toolbar =  findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.title_about_us);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
            toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

            linCloseOTP = findViewById(R.id.linCloseOTP);
            layoutOtp = findViewById(R.id.layoutOtp);
            linSubmitOtp = findViewById(R.id.linSubmitOtp);
            webView = findViewById(R.id.webView);
            edtOtp = findViewById(R.id.edtOtp);
            progressBarDiag = findViewById(R.id.progressBar);
            linSubmitOtp.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
            linSubmitOtp.setClickable(false);
            // webView.getSettings().setJavaScriptEnabled(true);

            //Vijay To load url in app's webview instead of browser

            InputFilter[] filterArray = new InputFilter[1];
            filterArray[0] = new InputFilter.LengthFilter(OTPlength);
            edtOtp.setFilters(filterArray);

            try {
                weburl = getIntent().getStringExtra("downloadUrl");
            } catch (Exception e) {
                e.printStackTrace();
            }

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            webView.setWebViewClient(new WebViewClient() {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            webView.getSettings().setPluginState(WebSettings.PluginState.ON);
            webView.getSettings().setAllowFileAccess(true);

            webView.loadUrl(weburl);

            this.setFinishOnTouchOutside(false);

            edtOtp.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    if (edtOtp.getText().toString().trim().length() == OTPlength) {

                        linSubmitOtp.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
                        linSubmitOtp.setClickable(true);
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(edtOtp.getWindowToken(), 0);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            linCloseOTP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    cfAlertDialog.dismiss();
                }
            });

            linSubmitOtp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
//                        galleryDocIntent();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(WebViewOTPDiag.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

//        pDialog = new ProgressDialog(MainActivity.this);
//        pDialog.setTitle("PDF");
//        pDialog.setMessage("Loading...");
//        pDialog.setIndeterminate(false);
//        pDialog.setCancelable(false);

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
}
