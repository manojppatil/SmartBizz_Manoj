package com.eduvanzapplication.newUI.newViews;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.webviews.WebViewPrivacyPolicy;
import com.eduvanzapplication.newUI.webviews.WebViewTermsNCondition;

/**
 * HOW TO DETECT BOTTOM OF SCROLL VIEW
 * https://stackoverflow.com/questions/10316743/detect-end-of-scrollview
 */

public class TermsAndCondition extends AppCompatActivity {

    TextView below23, below3, below5;
    CheckBox below2;
    LinearLayout linContinuePermi;
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.permissionscreen);
//        button = findViewById(R.id.button_agreetnc);
            linContinuePermi = findViewById(R.id.linContinuePermi);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

            below2 = findViewById(R.id.below2);
            below23 = findViewById(R.id.below23);
            below3 = findViewById(R.id.below3);
            below5 = findViewById(R.id.below5);
            scrollView = findViewById(R.id.scrollView_tnc);

            String mystring = getResources().getString(R.string.terms_of_service);
            SpannableString content = new SpannableString(mystring);
            content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
            below3.setText(content +" ");

            String mystring1 = getResources().getString(R.string.privacy_policy);
            SpannableString content1 = new SpannableString(mystring1);
            content1.setSpan(new UnderlineSpan(), 0, mystring1.length(), 0);
            below5.setText(" " + content1);

            below3.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
            below5.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

            linContinuePermi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (below2.isChecked()) {
                        SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userpolicyAgreement", "1");
                        editor.apply();
                        editor.commit();
                        Intent intent = new Intent(TermsAndCondition.this,
                                GetMobileNo.class);
                        startActivity(intent);
                        TermsAndCondition.this.finish();
                    } else {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            below2.setBackgroundTintList(TermsAndCondition.this.getResources().getColorStateList(R.color.colorRed));
                        }

                        if (Build.VERSION.SDK_INT < 21) {
                            CompoundButtonCompat.setButtonTintList(below2, ColorStateList.valueOf(TermsAndCondition.this.getResources().getColor(R.color.colorRed)));//Use android.support.v4.widget.CompoundButtonCompat when necessary else
                        } else {
                            below2.setButtonTintList(ColorStateList.valueOf(TermsAndCondition.this.getResources().getColor(R.color.colorRed)));//setButtonTintList is accessible directly on API>19
                        }

                        Toast.makeText(TermsAndCondition.this, "Please check the Terms & Conditions checkbox", Toast.LENGTH_SHORT).show();

                    }

                }
            });

            below2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

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
                            linContinuePermi.setVisibility(View.VISIBLE);
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
            Globle.ErrorLog(TermsAndCondition.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }
}
