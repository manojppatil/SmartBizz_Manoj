package com.eduvanzapplication;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eduvanzapplication.newUI.MainApplication;

/**
 * Created by nikhil on 23/9/16.
 */
public class SlidingImageAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;
    String dataofText[];
    String dataofTitle[];
    int image[];
    int imagebg[];
    public SlidingImageAdapter(Context sliderPage, String[] data, String[] dataTitle, int[] mResources, int[] mResourcesbg) {
        this.context = sliderPage;
        inflater = LayoutInflater.from(context);
        dataofText=data;
        dataofTitle=dataTitle;
        image = mResources;
        imagebg = mResourcesbg;
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
        TextView textForSliderTitle,textForslider;
        ImageView imageView;
        LinearLayout linSliderBackground;
        view = inflater.inflate(R.layout.slidersplash, container, false);
        textForSliderTitle= view.findViewById(R.id.textForSliderTitle);
        textForslider= view.findViewById(R.id.textForSlider);
        imageView = view.findViewById(R.id.imageviewslide);
        linSliderBackground = view.findViewById(R.id.linSliderBackground);
//      imageView.setAlpha(160);
        linSliderBackground.setBackground(context.getResources().getDrawable(imagebg[position]));
        imageView.setImageResource(image[position]);
        textForSliderTitle.setText(dataofTitle[position]);
        textForslider.setText(dataofText[position]);

        if(position == 1){
//            Log.e(MainApplication.TAG, "instantiateItem: "+position );
            YoYo.with(Techniques.RollIn)
                    .duration(800)
                    .repeat(0)
                    .playOn(view.findViewById(R.id.imageviewslide));
        }
        if(position == 2){
//            Log.e(MainApplication.TAG, "instantiateItem: "+position );
//            YoYo.with(Techniques.RollIn)
//                    .duration(800)
//                    .repeat(0)
//                    .playOn(view.findViewById(R.id.imageviewslide));
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
