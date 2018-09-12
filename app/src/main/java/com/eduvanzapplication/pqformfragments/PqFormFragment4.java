package com.eduvanzapplication.pqformfragments;


import android.content.Context;
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

import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.jaredrummler.materialspinner.MaterialSpinner;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/AnderWeb/discreteSeekBar
 */
public class PqFormFragment4 extends Fragment {

    MaterialSpinner spinnerLocationOfInstitute;
    Context context;
    Button buttonNext, buttonPrevious;
    Typeface typeface;
    TextView textViewRupeeSymbol;
    DiscreteSeekBar discreteSeekBar1;
    EditText editText;
    String loandAmount = "";
    TextView textView, textView2, textView3;
    View view1, view2, view1Step3, view2Step3, view1Step4, view2Step4;
    ImageView imageViewStep2, imageViewStep3, imageViewStep4;
    Typeface typefaceFont, typefaceFontBold;

    public PqFormFragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pq_form_fragment4, container, false);
        context = getContext();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        typeface = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

        view1 = view.findViewById(R.id.view1_fragment4_step2);
        view2 = view.findViewById(R.id.view2_fragment4_step2);
        imageViewStep2 = (ImageView) view.findViewById(R.id.imageView_fragment4_step2);
        view1Step3 = view.findViewById(R.id.view1_fragment4_step3);
        view2Step3 = view.findViewById(R.id.view2_fragment4_step3);
        imageViewStep3 = (ImageView) view.findViewById(R.id.imageView_fragment4_step3);
        view1Step4 = view.findViewById(R.id.view1_fragment4_step4);
        view2Step4 = view.findViewById(R.id.view2_fragment4_step4);
        imageViewStep4 = (ImageView) view.findViewById(R.id.imageView_fragment4_step4);

        view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewStep2.setImageDrawable(getResources().getDrawable(R.drawable.step2_image));
        view1Step3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view2Step3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewStep3.setImageDrawable(getResources().getDrawable(R.drawable.step3_image));
        view1Step4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        view2Step4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        imageViewStep4.setImageDrawable(getResources().getDrawable(R.drawable.step4_image));

        textViewRupeeSymbol = (TextView) view.findViewById(R.id.textviewRupee4);
        textViewRupeeSymbol.setTypeface(typeface);
        buttonPrevious = (Button) view.findViewById(R.id.button_previous_fragment4);
        buttonNext = (Button) view.findViewById(R.id.button_next_fragment4);
        buttonNext.setTypeface(typefaceFontBold);
        buttonPrevious.setTypeface(typefaceFontBold);
        editText = (EditText) view.findViewById(R.id.input_familymonthlyincome);
        editText.setTypeface(typefaceFont);
        textView = (TextView) view.findViewById(R.id.textview_familymonthlyincome);
        textView.setTypeface(typefaceFontBold);
        textView2 = (TextView) view.findViewById(R.id.textview1_font_fragment4);
        textView2.setText(MainApplication.indianCurrencyFormat("0"));
        textView2.setTypeface(typefaceFont);
        textView3 = (TextView) view.findViewById(R.id.textview2_font_fragment4);
        textView3.setText(MainApplication.indianCurrencyFormat("500000"));
        textView3.setTypeface(typefaceFont);

        Animation a = AnimationUtils.loadAnimation(context, R.anim.slidein_right);
        a.reset();
        textView.clearAnimation();
        textView.setAnimation(a);

        if(!MainApplication.mainapp_fammilyincome.equalsIgnoreCase("")){
            editText.setText(MainApplication.mainapp_fammilyincome);
        }

        discreteSeekBar1 = (DiscreteSeekBar) view.findViewById(R.id.seekbar_familyincomeamount);
        discreteSeekBar1.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                MainApplication.mainapp_fammilyincome = loandAmount = String.valueOf(discreteSeekBar1.getProgress());
                editText.setText(loandAmount);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction.replace(R.id.framelayout_pqform, new PqFormFragment3()).commit();
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().toString().equalsIgnoreCase("") || editText.getText().toString().equalsIgnoreCase("0"))
                {
                    Toast.makeText(context, "Loan Amount Cannot be 0",Toast.LENGTH_SHORT).show();
                    editText.setError("Loan Amount Cannot be 0");
                }else {
                    transaction.replace(R.id.framelayout_pqform, new PqFormFragment5()).commit();
                }
            }
        });
        return view;
    }

}
