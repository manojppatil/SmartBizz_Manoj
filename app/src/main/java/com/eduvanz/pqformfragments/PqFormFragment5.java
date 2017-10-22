package com.eduvanz.pqformfragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eduvanz.MainApplication;
import com.eduvanz.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class PqFormFragment5 extends Fragment {

    Context context;
    Button buttonNext, buttonPrevious;
    CardView cardView1, cardView2, cardView3, cardView4;
    View view1, view2, view1Step3, view2Step3, view1Step4, view2Step4, view1Step5, view2Step5;
    ImageView imageViewStep2, imageViewStep3, imageViewStep4, imageViewStep5;
    ImageView imageViewAdhaar, imageViewPan, imageViewBoth, imageViewNone;
    ImageView imageViewtick1, imageViewtick2, imageViewtick3, imageViewtick4;
    String docCheck = "";
    Boolean tickAdhaar = true, tickPan = true, tickBoth = true, tickNone = true;
    TextView textView1;
    Typeface typefaceFont, typefaceFontBold;

    public PqFormFragment5() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pq_form_fragment5, container, false);
        context = getContext();
        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

        textView1 = (TextView) view.findViewById(R.id.textview_font_fragment5);
        textView1.setTypeface(typefaceFontBold);
        imageViewtick1 = (ImageView) view.findViewById(R.id.imageView_tick);
        imageViewtick2 = (ImageView) view.findViewById(R.id.imageView_tick2);
        imageViewtick3 = (ImageView) view.findViewById(R.id.imageView_tick3);
        imageViewtick4 = (ImageView) view.findViewById(R.id.imageView_tick4);

        imageViewAdhaar = (ImageView) view.findViewById(R.id.imageView_adhaar);
        imageViewPan = (ImageView) view.findViewById(R.id.imageView_pan);
        imageViewBoth = (ImageView) view.findViewById(R.id.imageView_both);
        imageViewNone = (ImageView) view.findViewById(R.id.imageView_none);

//        view1 = view.findViewById(R.id.view1_fragment5_step2);
//        view2 = view.findViewById(R.id.view2_fragment5_step2);
//        imageViewStep2 = (ImageView) view.findViewById(R.id.imageView_fragment5_step2);
//        view1Step3 = view.findViewById(R.id.view1_fragment5_step3);
//        view2Step3 = view.findViewById(R.id.view2_fragment5_step3);
//        imageViewStep3 = (ImageView) view.findViewById(R.id.imageView_fragment5_step3);
//        view1Step4 = view.findViewById(R.id.view1_fragment5_step4);
//        view2Step4 = view.findViewById(R.id.view2_fragment5_step4);
//        imageViewStep4 = (ImageView) view.findViewById(R.id.imageView_fragment5_step4);
//        view1Step5 = view.findViewById(R.id.view1_fragment5_step5);
//        view2Step5 = view.findViewById(R.id.view2_fragment5_step5);
//        imageViewStep5 = (ImageView) view.findViewById(R.id.imageView_fragment5_step5);

//        view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        imageViewStep2.setImageDrawable(getResources().getDrawable(R.drawable.step2_image));
//        view1Step3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        view2Step3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        imageViewStep3.setImageDrawable(getResources().getDrawable(R.drawable.step3_image));
//        view1Step4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        view2Step4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        imageViewStep4.setImageDrawable(getResources().getDrawable(R.drawable.step4_image));
//        view1Step5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        view2Step5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        imageViewStep5.setImageDrawable(getResources().getDrawable(R.drawable.step5_image));


        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        cardView1 = (CardView) view.findViewById(R.id.cardview1);
        cardView2 = (CardView) view.findViewById(R.id.cardview2);
        cardView3 = (CardView) view.findViewById(R.id.cardview3);
        cardView4 = (CardView) view.findViewById(R.id.cardview4);

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_fragment5);
        buttonNext = (Button) view.findViewById(R.id.button_next_fragment5);
        buttonNext.setTypeface(typefaceFontBold);
        buttonPrevious.setTypeface(typefaceFontBold);

        Animation a = AnimationUtils.loadAnimation(context, R.anim.wobble);
        a.reset();

        YoYo.with(Techniques.RollIn)
                .duration(700)
                .repeat(0)
                .playOn(view.findViewById(R.id.cardview1));
        YoYo.with(Techniques.RollIn)
                .duration(700)
                .repeat(0)
                .playOn(view.findViewById(R.id.cardview2));
        YoYo.with(Techniques.RollIn)
                .duration(700)
                .repeat(0)
                .playOn(view.findViewById(R.id.cardview3));
        YoYo.with(Techniques.RollIn)
                .duration(700)
                .repeat(0)
                .playOn(view.findViewById(R.id.cardview4));

        cardView1.clearAnimation();
        cardView2.clearAnimation();
        cardView3.clearAnimation();
        cardView4.clearAnimation();
//        cardView1.startAnimation(a);
//        cardView2.startAnimation(a);
//        cardView3.startAnimation(a);
//        cardView4.startAnimation(a);

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.framelayout_pqform, new PqFormFragment4()).commit();
            }
        });

        if(MainApplication.mainapp_doccheck.equalsIgnoreCase("1")){
            imageViewtick1.setVisibility(View.VISIBLE);
        }else if(MainApplication.mainapp_doccheck.equalsIgnoreCase("2")){
            imageViewtick2.setVisibility(View.VISIBLE);
        }else if(MainApplication.mainapp_doccheck.equalsIgnoreCase("3")){
            imageViewtick3.setVisibility(View.VISIBLE);
        }else if(MainApplication.mainapp_doccheck.equalsIgnoreCase("4")){
            imageViewtick4.setVisibility(View.VISIBLE);
        }

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickAdhaar) {
                    docCheck = "1";
                    imageViewtick1.setVisibility(View.VISIBLE);
                    imageViewtick2.setVisibility(View.GONE);
                    imageViewtick3.setVisibility(View.GONE);
                    imageViewtick4.setVisibility(View.GONE);
                    tickAdhaar = false;
                    tickPan = true;
                    tickBoth = true;
                    tickNone = true;
//                    transaction.replace(R.id.framelayout_pqform, new PqFormFragment6()).commit();
                } else {
                    docCheck = "";
                    imageViewtick1.setVisibility(View.GONE);
                    tickAdhaar = true;
                }
            }
        });


//        imageViewAdhaar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                docCheck = "0";
//                imageViewtick1.setVisibility(View.VISIBLE);
//            }
//        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "onClick: " + tickPan);
                if (tickPan) {
                    Log.e("TAG", "onClick: " + tickPan);
                    docCheck = "2";
                    imageViewtick2.setVisibility(View.VISIBLE);
                    imageViewtick1.setVisibility(View.GONE);
                    imageViewtick3.setVisibility(View.GONE);
                    imageViewtick4.setVisibility(View.GONE);
                    tickPan = false;
                    tickAdhaar = true;
                    tickBoth = true;
                    tickNone = true;
//                transaction.replace(R.id.framelayout_pqform, new PqFormFragment6()).commit();
                } else {
                    docCheck = "";
                    imageViewtick2.setVisibility(View.GONE);
                    tickPan = true;
                }
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickBoth) {
                    docCheck = "3";
                    imageViewtick3.setVisibility(View.VISIBLE);
                    imageViewtick2.setVisibility(View.GONE);
                    imageViewtick1.setVisibility(View.GONE);
                    imageViewtick4.setVisibility(View.GONE);
//                    transaction.replace(R.id.framelayout_pqform, new PqFormFragment6()).commit();
                    tickBoth = false;
                    tickAdhaar = true;
                    tickPan = true;
                    tickNone = true;
                } else {
                    docCheck = "";
                    imageViewtick3.setVisibility(View.GONE);
                    tickBoth = true;
                }
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickNone) {
                    docCheck = "4";
                    imageViewtick4.setVisibility(View.VISIBLE);
                    imageViewtick2.setVisibility(View.GONE);
                    imageViewtick3.setVisibility(View.GONE);
                    imageViewtick1.setVisibility(View.GONE);
//                    transaction.replace(R.id.framelayout_pqform, new PqFormFragment6()).commit();
                    tickNone = false;
                    tickAdhaar = true;
                    tickBoth = true;
                    tickPan = true;
                } else {
                    docCheck = "";
                    imageViewtick4.setVisibility(View.GONE);
                    tickNone = true;
                }
            }
        });



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!docCheck.equalsIgnoreCase("")) {
                    MainApplication.mainapp_doccheck = docCheck;
                }
                if (MainApplication.mainapp_doccheck.equalsIgnoreCase("")) {
                    Toast.makeText(context, "You Need To Select Any One Document", Toast.LENGTH_SHORT).show();
                } else {
                    transaction.replace(R.id.framelayout_pqform, new PqFormFragment6()).commit();
                }
            }
        });

        return view;
    }
}
