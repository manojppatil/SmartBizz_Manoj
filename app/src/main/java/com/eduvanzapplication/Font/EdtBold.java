package com.eduvanzapplication.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class EdtBold extends AppCompatEditText {

        public EdtBold(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public EdtBold(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public EdtBold(Context context) {
            super(context);
            init();
        }

        public void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Semibold.ttf");
            setTypeface(tf ,1);

        }
}
