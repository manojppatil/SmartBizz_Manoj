package com.eduvanzapplication.Font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TxtRegular extends AppCompatTextView{

        public TxtRegular(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        public TxtRegular(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public TxtRegular(Context context) {
            super(context);
            init();
        }

        public void init() {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/SourceSansPro-Regular.ttf");
            setTypeface(tf ,1);

        }
}
