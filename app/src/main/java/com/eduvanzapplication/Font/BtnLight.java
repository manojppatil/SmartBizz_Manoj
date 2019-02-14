package com.eduvanzapplication.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class BtnLight extends AppCompatButton{

        public BtnLight(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public BtnLight(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public BtnLight(Context context) {
            super(context);
            init();
        }

        public void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Light.ttf");
            setTypeface(tf ,1);

        }
}
