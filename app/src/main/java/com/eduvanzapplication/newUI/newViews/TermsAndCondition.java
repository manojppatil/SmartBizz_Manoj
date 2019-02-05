package com.eduvanzapplication.newUI.newViews;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.webviews.WebViewPrivacyPolicy;
import com.eduvanzapplication.newUI.webviews.WebViewTermsNCondition;

/**HOW TO DETECT BOTTOM OF SCROLL VIEW
 *  https://stackoverflow.com/questions/10316743/detect-end-of-scrollview */

public class TermsAndCondition extends AppCompatActivity {

    TextView below3, below5;
    Button button;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.permissionscreen);
//        button = findViewById(R.id.button_agreetnc);
            button = findViewById(R.id.button_permission);
            below3 = findViewById(R.id.below3);
            below5 = findViewById(R.id.below5);
            scrollView = findViewById(R.id.scrollView_tnc);

            String mystring = " " +getResources().getString(R.string.terms_of_service) + " ";
//        String mystring=new String(" terms of service");
            SpannableString content = new SpannableString(mystring);
            content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
            below3.setText(content);

            String mystring1 = " " +getResources().getString(R.string.privacy_policy);
//        String mystring1=new String(" privacy policy.");
            SpannableString content1 = new SpannableString(mystring1);
            content1.setSpan(new UnderlineSpan(), 0, mystring1.length(), 0);
            below5.setText(content1);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("userpolicyAgreement", 1);
                        editor.apply();
                        editor.commit();

                    Intent intent = new Intent(TermsAndCondition.this,
                            GetMobileNo.class);

//                    Intent intent = new Intent(TermsAndCondition.this,
//                            SingInWithTruecaller.class);
//                    Intent intent = new Intent(TermsAndCondition.this,
//                            NewTruecallerSignIn.class);
                    startActivity(intent);

                }
            });

            below3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(TermsAndCondition.this,
                            WebViewTermsNCondition.class);
                    startActivity(intent);

                }
            });

            below5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(TermsAndCondition.this,
                            WebViewPrivacyPolicy.class);
                    startActivity(intent);
                }
            });

            scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                @Override
                public void onScrollChanged() {
                    if (scrollView != null) {
                        if (scrollView.getChildAt(0).getBottom() <= (scrollView.getHeight() + scrollView.getScrollY())) {
                            //scroll view is at bottom
                            button.setVisibility(View.VISIBLE);
                        } else {
                            //scroll view is not at bottom
                        }
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
            Globle.ErrorLog(TermsAndCondition.this,className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }
}
