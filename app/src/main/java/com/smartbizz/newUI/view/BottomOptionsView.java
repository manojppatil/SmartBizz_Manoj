package com.smartbizz.newUI.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.smartbizz.R;


public class BottomOptionsView extends LinearLayout {
    private Button btnLeft, btnRight;

    public BottomOptionsView(Context context) {
        super(context);
        init(context, null);
    }

    public BottomOptionsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_bottom_option, this);
        btnLeft = view.findViewById(R.id.btnLeft);
        btnRight = view.findViewById(R.id.btnRight);

        if (attrs != null) {
            String leftButtonText = "", rightButtonText = "";
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BottomOptionsView, 0, 0);

            if (a.hasValue(R.styleable.BottomOptionsView_leftButtonText)) {
                leftButtonText = a.getString(R.styleable.BottomOptionsView_leftButtonText);
            }
            if (a.hasValue(R.styleable.BottomOptionsView_rightButtonText)) {
                rightButtonText = a.getString(R.styleable.BottomOptionsView_rightButtonText);
            }
            a.recycle();
            btnLeft.setText(leftButtonText);
            btnRight.setText(rightButtonText);
        }
    }

    public void registerListeners(OnClickListener listener) {
        btnLeft.setOnClickListener(listener);
        btnRight.setOnClickListener(listener);
    }

    public void setLeftButtonText(String text) {
        btnLeft.setText(text);
    }

    public void setRightButtonText(String text) {
        btnRight.setText(text);
    }

    public String getRightButtonText() {
        return btnRight.getText().toString();
    }

    public void enableLeftButton(boolean enable){
        btnLeft.setEnabled(enable);
    }

    public void setVisibleLeftButton(boolean visible){
        btnLeft.setVisibility(visible?VISIBLE:GONE);
    }
}
