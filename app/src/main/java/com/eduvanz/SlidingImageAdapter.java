package com.eduvanz;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eduvanz.newUI.MainApplication;

/**
 * Created by nikhil on 23/9/16.
 */
public class SlidingImageAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    String dataofText[];
    int image[];
    public SlidingImageAdapter(Context sliderPage, String data[], int[] mResources) {
        this.context = sliderPage;
        inflater = LayoutInflater.from(context);
        dataofText=data;
        image = mResources;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }


    @Override
    public int getCount() {
        return image.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=null;
//
//        switch (position)
//        {
//            case 0:
        TextView textForslider;
        ImageView imageView;
//         Typeface breeR = Typeface.createFromAsset(context.getAssets(), "BreeSerif-Regular.ttf");
        view = inflater.inflate(R.layout.slidersplash, container, false);
         textForslider= (TextView) view.findViewById(R.id.textForSlider);
        imageView = (ImageView) view.findViewById(R.id.imageviewslide);
//        imageView.setAlpha(160);
        imageView.setImageResource(image[position]);
        textForslider.setText(dataofText[position]);


        if(position == 1){
            Log.e(MainApplication.TAG, "instantiateItem: "+position );
            YoYo.with(Techniques.RollIn)
                    .duration(800)
                    .repeat(0)
                    .playOn(view.findViewById(R.id.imageviewslide));
        }
        if(position == 2){
            Log.e(MainApplication.TAG, "instantiateItem: "+position );
            YoYo.with(Techniques.RollIn)
                    .duration(800)
                    .repeat(0)
                    .playOn(view.findViewById(R.id.imageviewslide));
        }


//         textForslider.setText(dataofText[position]);
//         textForslider.setTypeface(breeR);
//                break;
//            case 1:
//                view = inflater.inflate(R.layout.slidet, container, false);
//                break;
//            case 2:
//                view = inflater.inflate(R.layout.slideo, container, false);
//                break;


//        }
        container.addView(view);
        return view;
    }
}
