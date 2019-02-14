package com.eduvanzapplication.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TxtLight extends AppCompatTextView{

        public TxtLight(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public TxtLight(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public TxtLight(Context context) {
            super(context);
            init();
        }

        public void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Light.ttf");
            setTypeface(tf ,1);

        }
}
