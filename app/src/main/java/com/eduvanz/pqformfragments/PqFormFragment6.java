package com.eduvanz.pqformfragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
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
import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PqFormFragment6 extends Fragment {

    Context context;
    Button buttonNext, buttonPrevious;
    CardView cardView1, cardView2, cardView3;
    View view1, view2, view1Step3, view2Step3, view1Step4, view2Step4, view1Step5, view2Step5, view1Step6, view2Step6;
    ImageView imageViewStep2, imageViewStep3, imageViewStep4, imageViewStep5, imageViewStep6;
    String docCheck = "";
    ImageView imageViewtick1, imageViewtick2, imageViewtick3;
    Boolean tickIamEmployeed = true, tickIamSelfEmployeed = true, tickIamStudent = true;
    TextView textView;
    Typeface typefaceFont, typefaceFontBold;

    public PqFormFragment6() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pq_form_fragment6, container, false);
        context = getContext();
        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

        textView = (TextView) view.findViewById(R.id.textview_font_fragment6);
        textView.setTypeface(typefaceFontBold);
        imageViewtick1 = (ImageView) view.findViewById(R.id.imageView_tick_fragment6);
        imageViewtick2 = (ImageView) view.findViewById(R.id.imageView_tick2_fragment6);
        imageViewtick3 = (ImageView) view.findViewById(R.id.imageView_tick3_fragment6);

        view1 = view.findViewById(R.id.view1_fragment6_step2);
        view2 = view.findViewById(R.id.view2_fragment6_step2);
        imageViewStep2 = (ImageView) view.findViewById(R.id.imageView_fragment6_step2);
        view1Step3 = view.findViewById(R.id.view1_fragment6_step3);
        view2Step3 = view.findViewById(R.id.view2_fragment6_step3);
        imageViewStep3 = (ImageView) view.findViewById(R.id.imageView_fragment6_step3);
        view1Step4 = view.findViewById(R.id.view1_fragment6_step4);
        view2Step4 = view.findViewById(R.id.view2_fragment6_step4);
        imageViewStep4 = (ImageView) view.findViewById(R.id.imageView_fragment6_step4);
        view1Step5 = view.findViewById(R.id.view1_fragment6_step5);
        view2Step5 = view.findViewById(R.id.view2_fragment6_step5);
        imageViewStep5 = (ImageView) view.findViewById(R.id.imageView_fragment6_step5);
        view1Step6 = view.findViewById(R.id.view1_fragment6_step6);
        view2Step6 = view.findViewById(R.id.view2_fragment6_step6);
        imageViewStep6 = (ImageView) view.findViewById(R.id.imageView_fragment6_step6);

        view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewStep2.setImageDrawable(getResources().getDrawable(R.drawable.step2_image));
        view1Step3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view2Step3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewStep3.setImageDrawable(getResources().getDrawable(R.drawable.step3_image));
        view1Step4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view2Step4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewStep4.setImageDrawable(getResources().getDrawable(R.drawable.step4_image));
        view1Step5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view2Step5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewStep5.setImageDrawable(getResources().getDrawable(R.drawable.step5_image));
        view1Step6.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view2Step6.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewStep6.setImageDrawable(getResources().getDrawable(R.drawable.step6_image));


        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_fragment6);
        buttonNext = (Button) view.findViewById(R.id.button_next_fragment6);
        buttonNext.setTypeface(typefaceFontBold);
        buttonPrevious.setTypeface(typefaceFontBold);

        cardView1 = (CardView) view.findViewById(R.id.cardview1_fragment6);
        cardView2 = (CardView) view.findViewById(R.id.cardview2_fragment6);
        cardView3 = (CardView) view.findViewById(R.id.cardview3_fragment6);

        Animation a = AnimationUtils.loadAnimation(context, R.anim.slidein_left);
        Animation b = AnimationUtils.loadAnimation(context, R.anim.slidein_right);
        Animation c = AnimationUtils.loadAnimation(context, R.anim.slidein_bottom);
        a.reset();
        b.reset();
        c.reset();

        cardView1.clearAnimation();
        cardView2.clearAnimation();
        cardView3.clearAnimation();
//        cardView1.startAnimation(a);
//        cardView2.startAnimation(b);
//        cardView3.startAnimation(c);

        YoYo.with(Techniques.BounceIn)
                .duration(700)
                .repeat(0)
                .playOn(view.findViewById(R.id.cardview1_fragment6));
        YoYo.with(Techniques.BounceIn)
                .duration(700)
                .repeat(0)
                .playOn(view.findViewById(R.id.cardview2_fragment6));
        YoYo.with(Techniques.BounceIn)
                .duration(700)
                .repeat(0)
                .playOn(view.findViewById(R.id.cardview3_fragment6));

        if(MainApplication.mainapp_professioncheck.equalsIgnoreCase("employed")){
            imageViewtick1.setVisibility(View.VISIBLE);
        }else if(MainApplication.mainapp_professioncheck.equalsIgnoreCase("selfemployed")){
            imageViewtick2.setVisibility(View.VISIBLE);
        }else if(MainApplication.mainapp_professioncheck.equalsIgnoreCase("student")){
            imageViewtick3.setVisibility(View.VISIBLE);
        }

        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickIamEmployeed) {
                    docCheck = "employed";
                    imageViewtick1.setVisibility(View.VISIBLE);
                    imageViewtick2.setVisibility(View.GONE);
                    imageViewtick3.setVisibility(View.GONE);
                    tickIamEmployeed = false;
                    tickIamSelfEmployeed = true;
                    tickIamStudent = true;
                } else {
                    docCheck = "";
                    imageViewtick1.setVisibility(View.GONE);
                    tickIamEmployeed = true;
                }

            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickIamSelfEmployeed) {
                    docCheck = "selfemployed";
                    imageViewtick2.setVisibility(View.VISIBLE);
                    imageViewtick1.setVisibility(View.GONE);
                    imageViewtick3.setVisibility(View.GONE);
                    tickIamEmployeed = true;
                    tickIamSelfEmployeed = false;
                    tickIamStudent = true;
//                    transaction.replace(R.id.framelayout_pqform, new PqFormFragment7()).commit();
                } else {
                    docCheck = "";
                    imageViewtick2.setVisibility(View.GONE);
                    tickIamSelfEmployeed = true;
                }
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickIamStudent) {
                    docCheck = "student";
                    imageViewtick2.setVisibility(View.GONE);
                    imageViewtick1.setVisibility(View.GONE);
                    imageViewtick3.setVisibility(View.VISIBLE);
                    tickIamEmployeed = true;
                    tickIamSelfEmployeed = true;
                    tickIamStudent = false;
//                transaction.replace(R.id.framelayout_pqform,new PqFormFragment7()).commit();
                } else {
                    docCheck = "";
                    imageViewtick3.setVisibility(View.GONE);
                    tickIamStudent = true;
                }
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.framelayout_pqform, new PqFormFragment5()).commit();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!docCheck.equalsIgnoreCase("")) {
                    MainApplication.mainapp_professioncheck = docCheck;
                }
                if (MainApplication.mainapp_professioncheck.equalsIgnoreCase("")) {
                    Toast.makeText(context, "You Need To Select Any One Document", Toast.LENGTH_SHORT).show();
                } else {
                    transaction.replace(R.id.framelayout_pqform, new PqFormFragment7()).commit();
                }

            }
        });
        return view;
    }

}
