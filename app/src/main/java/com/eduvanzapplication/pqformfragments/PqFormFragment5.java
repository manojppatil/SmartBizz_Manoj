package com.eduvanzapplication.pqformfragments;


import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.SuccessSplash;

/**
 * A simple {@link Fragment} subclass.
 */
public class PqFormFragment5 extends Fragment {

    Context context;
    Button buttonNext, buttonPrevious;
    CardView cardView1, cardView2, cardView3, cardView4;
    CardView cardView_1, cardView_2, cardView_3;

    View view1, view2, view1Step3, view2Step3, view1Step4, view2Step4, view1Step5, view2Step5;
    ImageView imageViewStep2, imageViewStep3, imageViewStep4, imageViewStep5;
    ImageView imageViewAdhaar, imageViewPan, imageViewBoth, imageViewNone;
    ImageView imageViewtick1, imageViewtick2, imageViewtick3, imageViewtick4;
    ImageView imageViewtick_1, imageViewtick_2, imageViewtick_3;
    Boolean tickIamEmployeed = true, tickIamSelfEmployeed = true, tickIamStudent = true;
    String docCheck = "";
    String doc_Check = "";
    Boolean tickAdhaar = true, tickPan = true, tickBoth = true, tickNone = true;
    TextView textView1;
    Typeface typefaceFont, typefaceFontBold;
    EditText editTextCurrentCity;

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

        imageViewtick_1 = (ImageView) view.findViewById(R.id.imageView_tick_fragment6);
        imageViewtick_2 = (ImageView) view.findViewById(R.id.imageView_tick2_fragment6);
        imageViewtick_3 = (ImageView) view.findViewById(R.id.imageView_tick3_fragment6);

        imageViewAdhaar = (ImageView) view.findViewById(R.id.imageView_adhaar);
        imageViewPan = (ImageView) view.findViewById(R.id.imageView_pan);
        imageViewBoth = (ImageView) view.findViewById(R.id.imageView_both);
        imageViewNone = (ImageView) view.findViewById(R.id.imageView_none);

        editTextCurrentCity = (EditText) view.findViewById(R.id.input_city_fragment7);
        editTextCurrentCity.setTypeface(typefaceFont);

        if(!MainApplication.mainapp_currentCity.equalsIgnoreCase("")){
            editTextCurrentCity.setText(MainApplication.mainapp_currentCity);
        }

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

        cardView_1 = (CardView) view.findViewById(R.id.cardview1_fragment6);
        cardView_2 = (CardView) view.findViewById(R.id.cardview2_fragment6);
        cardView_3 = (CardView) view.findViewById(R.id.cardview3_fragment6);

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
                transaction.replace(R.id.framelayout_pqform, new PqFormFragment3()).commit();
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


        final Animation e = AnimationUtils.loadAnimation(context, R.anim.slidein_left);
        Animation f = AnimationUtils.loadAnimation(context, R.anim.slidein_right);
        Animation g = AnimationUtils.loadAnimation(context, R.anim.slidein_bottom);
        e.reset();
        f.reset();
        g.reset();

        cardView_1.clearAnimation();
        cardView_2.clearAnimation();
        cardView_3.clearAnimation();
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
            imageViewtick_1.setVisibility(View.VISIBLE);
        }else if(MainApplication.mainapp_professioncheck.equalsIgnoreCase("selfemployed")){
            imageViewtick_2.setVisibility(View.VISIBLE);
        }else if(MainApplication.mainapp_professioncheck.equalsIgnoreCase("student")){
            imageViewtick_3.setVisibility(View.VISIBLE);
        }

        cardView_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickIamEmployeed) {
                    doc_Check = "employed";
                    imageViewtick_1.setVisibility(View.VISIBLE);
                    imageViewtick_2.setVisibility(View.GONE);
                    imageViewtick_3.setVisibility(View.GONE);
                    tickIamEmployeed = false;
                    tickIamSelfEmployeed = true;
                    tickIamStudent = true;
                } else {
                    doc_Check = "";
                    imageViewtick_1.setVisibility(View.GONE);
                    tickIamEmployeed = true;
                }

            }
        });
        cardView_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickIamSelfEmployeed) {
                    doc_Check = "selfemployed";
                    imageViewtick_2.setVisibility(View.VISIBLE);
                    imageViewtick_1.setVisibility(View.GONE);
                    imageViewtick_3.setVisibility(View.GONE);
                    tickIamEmployeed = true;
                    tickIamSelfEmployeed = false;
                    tickIamStudent = true;
//                    transaction.replace(R.id.framelayout_pqform, new PqFormFragment7()).commit();
                } else {
                    doc_Check = "";
                    imageViewtick_2.setVisibility(View.GONE);
                    tickIamSelfEmployeed = true;
                }
            }
        });
        cardView_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tickIamStudent) {
                    doc_Check = "student";
                    imageViewtick_2.setVisibility(View.GONE);
                    imageViewtick_1.setVisibility(View.GONE);
                    imageViewtick_3.setVisibility(View.VISIBLE);
                    tickIamEmployeed = true;
                    tickIamSelfEmployeed = true;
                    tickIamStudent = false;
//                transaction.replace(R.id.framelayout_pqform,new PqFormFragment7()).commit();
                } else {
                    doc_Check = "";
                    imageViewtick_3.setVisibility(View.GONE);
                    tickIamStudent = true;
                }
            }
        });



        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(MainApplication.TAG, "onClick: buttonNext"+docCheck );
                Log.e(MainApplication.TAG, "onClick: buttonNext"+doc_Check );
                if(!docCheck.equalsIgnoreCase("")) {
                    MainApplication.mainapp_doccheck = docCheck;
                }
                if(!doc_Check.equalsIgnoreCase("")) {
                    MainApplication.mainapp_professioncheck = doc_Check;
                }

                Log.e(MainApplication.TAG, "onClick: docCheck"+MainApplication.mainapp_doccheck );
                Log.e(MainApplication.TAG, "onClick: doc_Check"+MainApplication.mainapp_professioncheck );

                if (!MainApplication.mainapp_doccheck.equalsIgnoreCase("") )
                {

                    if(!MainApplication.mainapp_professioncheck.equalsIgnoreCase(""))
                    {
                        if(!editTextCurrentCity.getText().toString().equalsIgnoreCase("")){
                            Intent intent = new Intent(context, SuccessSplash.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(context, "You Need To Enter Your City", Toast.LENGTH_SHORT).show();
                            editTextCurrentCity.setError("You Need To Enter Your City");
                        }

                    }else {
                        Toast.makeText(context, "You Need To Select Your Profession", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "You Need To Select Any One Document", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}
