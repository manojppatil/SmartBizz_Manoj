package com.smartbizz.newUI.fragments;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.smartbizz.R;
import com.smartbizz.Util.CommonUtil;
import com.smartbizz.Util.MyLogger;
import com.smartbizz.newUI.interfaces.ActivityCallback;

public class BaseFragment extends Fragment {

    protected Activity activity;
    protected ActivityCallback activityCallback;
    protected boolean focusEnabled;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    protected void makeToast(String message){
        CommonUtil.makeToast(activity, message);
    }

    protected void disableRadioGroup(RadioGroup radioGroup) {
        //set default to false
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            radioGroup.getChildAt(i).setEnabled(false);
        }
    }

    protected String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    protected String getText(EditText editText) {
        return editText.getText().toString().trim();
    }


    protected long downloadReference;
    protected DownloadManager downloadManager;
    private boolean isRegistered;


    protected void registerOnTouchListener(View view) {
        view.setOnTouchListener((v, event) -> {
            CommonUtil.hideSoftKeyboard(activity);
            return true;
        });
    }

    protected void addDropdown(AutoCompleteTextView autoCompleteTextView) {
        autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(activity, R.drawable.ic_dropdown), null);
    }

    protected void removeDropdown(AutoCompleteTextView autoCompleteTextView) {
        autoCompleteTextView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
    }


    private void validateFocus(View view){
        if (focusEnabled && view != null) {
            try {
                NestedScrollView nestedScrollView = activity.findViewById(R.id.scrollView);
                if(nestedScrollView != null){
                    scrollToView(nestedScrollView,view);
                }
                focusEnabled = false;
            } catch (Exception e) {
                MyLogger.error(e);
            }
        }
    }

    private void scrollToView(final View scrollView, final View view) {
        view.requestFocus();
        final Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);
        if (!view.getLocalVisibleRect(scrollBounds)) {
            new Handler().post(() -> {
                int toScroll = getRelativeTop(view) - getRelativeTop(scrollView);
                if(scrollView instanceof NestedScrollView) {
                    ((NestedScrollView) scrollView).smoothScrollTo(0, toScroll - 120);
                }else  if(scrollView instanceof ScrollView) {
                    ((ScrollView) scrollView).smoothScrollTo(0, toScroll - 120);
                }
            });
        }
    }

    private int getRelativeTop(View myView) {
        if (myView.getParent() == myView.getRootView()) return myView.getTop();
        else return myView.getTop() + getRelativeTop((View) myView.getParent());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
