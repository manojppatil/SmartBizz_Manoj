package com.eduvanzapplication.newUI.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eduvanzapplication.R;

import java.util.ArrayList;

/**
 * Created by Vijay on 14/11/17.
 */

public class ViewPagerAdapterBanner extends PagerAdapter {
    Context mContext;
    LayoutInflater mInfaler;
    ArrayList<Integer> mData;
    ImageView mImage;

    public ViewPagerAdapterBanner(Context context, ArrayList<Integer> images) {
            this.mContext = context;
            this.mData=images;
            mInfaler = LayoutInflater.from(context);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View myImageLayout = mInfaler.inflate(R.layout.custom_viewpager, view, false);
            ImageView myImage = (ImageView) myImageLayout
                    .findViewById(R.id.imageView_bannerImage);
//            myImage.setImageResource(images.get(position));
            view.addView(myImageLayout, 0);
            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
}

