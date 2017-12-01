package com.eduvanz.newUI.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;
import com.eduvanz.newUI.newViews.BannerActivity;
import com.eduvanz.newUI.pojo.ViewPagerDashboardPOJO;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by projetctheena on 14/11/17.
 */

public class ViewPagerAdapterDashboard extends PagerAdapter {
    Context mContext;
    LayoutInflater mInfaler;
    ArrayList<ViewPagerDashboardPOJO> mData;
    ImageView mImage;

    public ViewPagerAdapterDashboard(Context context, ArrayList<ViewPagerDashboardPOJO> data) {
            this.mContext = context;
            this.mData=data;
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
        public Object instantiateItem(ViewGroup view, final int position) {
            View myImageLayout = mInfaler.inflate(R.layout.custom_viewpager, view, false);
            ImageView myImage = (ImageView) myImageLayout
                    .findViewById(R.id.imageView_bannerImage);
//            myImage.setImageResource(images.get(position));
            view.addView(myImageLayout, 0);

            Log.e(MainApplication.TAG, "instantiateItem: "+mData.size() );

            Picasso.with(mContext).load(mData.get(position).image).placeholder(mContext.getResources().getDrawable(R.drawable.bannersplaceholder)).into(myImage);

            myImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BannerActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("bannerImage", mData.get(position).image);
                    bundle.putString("bannerID", mData.get(position).id);
                    bundle.putString("bannerTitle", mData.get(position).title);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                }
            });

            return myImageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }
}

