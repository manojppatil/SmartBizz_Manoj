package com.smartbizz.newUI.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class EduvanzButton extends AppCompatButton {
    public EduvanzButton(Context context) {
        super(context);
    }

    public EduvanzButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setDisable(){
        setAlpha(0.5f);
        setEnabled(false);
    }

    public void setEnable(){
        setAlpha(1f);
        setEnabled(true);
    }
}
