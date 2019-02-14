package com.eduvanzapplication.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class BtnBold extends AppCompatButton {

        public BtnBold(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public BtnBold(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public BtnBold(Context context) {
            super(context);
            init();
        }

        public void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Semibold.ttf");
            setTypeface(tf ,1);

        }
}
