package com.eduvanzapplication.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

public class RbLight extends AppCompatRadioButton{

        public RbLight(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public RbLight(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public RbLight(Context context) {
            super(context);
            init();
        }

        public void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Light.ttf");
            setTypeface(tf ,1);

        }
}
