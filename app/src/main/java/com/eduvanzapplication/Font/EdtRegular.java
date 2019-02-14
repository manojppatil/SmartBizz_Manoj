package com.eduvanzapplication.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class EdtRegular extends AppCompatEditText{

        public EdtRegular(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public EdtRegular(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public EdtRegular(Context context) {
            super(context);
            init();
        }

        public void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.ttf");
            setTypeface(tf ,1);

        }
}
