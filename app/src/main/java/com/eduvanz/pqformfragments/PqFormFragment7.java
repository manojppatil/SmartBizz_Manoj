package com.eduvanz.pqformfragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

/**
 * A simple {@link Fragment} subclass.
 */
public class PqFormFragment7 extends Fragment {

    Context context;
    Button buttonNext, buttonPrevious;
    TextView textView;
    View view1, view2, view1Step3, view2Step3, view1Step4, view2Step4, view1Step5, view2Step5, view1Step6, view2Step6, view1Step7;
    ImageView imageViewStep2, imageViewStep3, imageViewStep4, imageViewStep5, imageViewStep6, imageViewStep7;
    Typeface typefaceFont, typefaceFontBold;
    EditText editTextCurrentCity;

    public PqFormFragment7() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pq_form_fragment7, container, false);
        context = getContext();

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

        view1 = view.findViewById(R.id.view1_fragment7_step2);
        view2 = view.findViewById(R.id.view2_fragment7_step2);
        imageViewStep2 = (ImageView) view.findViewById(R.id.imageView_fragment7_step2);
        view1Step3 = view.findViewById(R.id.view1_fragment7_step3);
        view2Step3 = view.findViewById(R.id.view2_fragment7_step3);
        imageViewStep3 = (ImageView) view.findViewById(R.id.imageView_fragment7_step3);
        view1Step4 = view.findViewById(R.id.view1_fragment7_step4);
        view2Step4 = view.findViewById(R.id.view2_fragment7_step4);
        imageViewStep4 = (ImageView) view.findViewById(R.id.imageView_fragment7_step4);
        view1Step5 = view.findViewById(R.id.view1_fragment7_step5);
        view2Step5 = view.findViewById(R.id.view2_fragment7_step5);
        imageViewStep5 = (ImageView) view.findViewById(R.id.imageView_fragment7_step5);
        view1Step6 = view.findViewById(R.id.view1_fragment7_step6);
        view2Step6 = view.findViewById(R.id.view2_fragment7_step6);
        imageViewStep6 = (ImageView) view.findViewById(R.id.imageView_fragment7_step6);
        view1Step7 = view.findViewById(R.id.view1_fragment7_step7);
        imageViewStep7 = (ImageView) view.findViewById(R.id.imageView_fragment7_step7);
        editTextCurrentCity = (EditText) view.findViewById(R.id.input_city_fragment7);
        editTextCurrentCity.setTypeface(typefaceFont);

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
        view1Step7.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewStep7.setImageDrawable(getResources().getDrawable(R.drawable.step7_image));

        if(!MainApplication.mainapp_currentCity.equalsIgnoreCase("")){
            editTextCurrentCity.setText(MainApplication.mainapp_currentCity);
        }

        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_fragment7);
        buttonNext = (Button) view.findViewById(R.id.button_next_fragment7);
        buttonNext.setTypeface(typefaceFontBold);
        buttonPrevious.setTypeface(typefaceFontBold);

        textView = (TextView) view.findViewById(R.id.textview_currentcity);
        textView.setTypeface(typefaceFontBold);

        Animation a = AnimationUtils.loadAnimation(context, R.anim.slidein_right);
        a.reset();
        textView.clearAnimation();
        textView.setAnimation(a);

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.framelayout_pqform, new PqFormFragment5()).commit();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextCurrentCity.getText().toString().equalsIgnoreCase(""))
                {
                    Toast.makeText(context, "You Need To Enter Your City", Toast.LENGTH_SHORT).show();
                    editTextCurrentCity.setError("You Need To Enter Your City");
                }else {
                    MainApplication.mainapp_currentCity = editTextCurrentCity.getText().toString();
                    Intent intent = new Intent(context, SuccessAfterPQForm.class);
                    startActivity(intent);
                }
            }
        });
        return view;
    }

}
