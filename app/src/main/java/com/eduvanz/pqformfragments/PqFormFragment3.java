package com.eduvanz.pqformfragments;


import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
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

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;
import com.eduvanz.volley.VolleyCall;
import com.xw.repo.BubbleSeekBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.eduvanz.newUI.MainApplication.TAG;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/AnderWeb/discreteSeekBar
 */
public class PqFormFragment3 extends Fragment {

    public static Context context;
    public static TextView textViewRupeeSymbol, textviewMaxAmount, textViewRupeeSymbol2, textViewRupeeSymbol3;
    public static BubbleSeekBar bubbleSeekBar1;
    public static BubbleSeekBar bubbleSeekBar2;
    Button buttonNext, buttonPrevious;
    Typeface typeface;
    String loandAmount = "";
    TextView textView, textView2, textView3;
    View view1, view2, view1Step3, view2Step3;
    ImageView imageViewStep2, imageViewStep3;
    EditText editTextLoanAmount, editText, editTextCourseFee;
    Typeface typefaceFont, typefaceFontBold;
    String instituteID = "", courseID = "", locationID = "";
    Fragment mFragment;
    String resultAmount = "";

    public PqFormFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pq_form_fragment3, container, false);
        context = getContext();
        mFragment = new PqFormFragment3();
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        typeface = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

//        instituteID = getArguments().getString("institute_id");
//        courseID = getArguments().getString("course_id");
//        locationID = getArguments().getString("location_id");
        Log.e(TAG, "instituteID: " + instituteID + "  courseID " + courseID + "  locationID " + locationID);

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");

//        view1 = view.findViewById(R.id.view1_fragment3_step2);
//        view2 = view.findViewById(R.id.view2_fragment3_step2);
//        imageViewStep2 = (ImageView) view.findViewById(R.id.imageView_fragment3_step2);
//        view1Step3 = view.findViewById(R.id.view1_fragment3_step3);
//        view2Step3 = view.findViewById(R.id.view2_fragment3_step3);
//        imageViewStep3 = (ImageView) view.findViewById(R.id.imageView_fragment3_step3);
//        textviewMaxAmount = (TextView) view.findViewById(R.id.textview3_maxamount);
//        textviewMaxAmount.setTypeface(typefaceFont);
        textViewRupeeSymbol2 = (TextView) view.findViewById(R.id.textviewRupee4);
        textViewRupeeSymbol2.setTypeface(typeface);
        textViewRupeeSymbol3 = (TextView) view.findViewById(R.id.textviewRupee1);
        textViewRupeeSymbol3.setTypeface(typeface);
        editText = (EditText) view.findViewById(R.id.input_familymonthlyincome);
        editText.setTypeface(typefaceFont);

        editTextCourseFee = (EditText) view.findViewById(R.id.input_courseamount);
        editTextCourseFee.setTypeface(typefaceFont);

//        view1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        view2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        imageViewStep2.setImageDrawable(getResources().getDrawable(R.drawable.step2_image));
//        view1Step3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        view2Step3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        imageViewStep3.setImageDrawable(getResources().getDrawable(R.drawable.step3_image));

//        textView2 = (TextView) view.findViewById(R.id.textview2_font_fragment3);
//        textView2.setTypeface(typefaceFont);
//        textView2.setText(MainApplication.indianCurrencyFormat("0"));
        textViewRupeeSymbol = (TextView) view.findViewById(R.id.textviewRupee);
        textViewRupeeSymbol.setTypeface(typeface);
        buttonPrevious = (Button) view.findViewById(R.id.button_previous_fragment3);
        buttonNext = (Button) view.findViewById(R.id.button_next_fragment3);
        buttonNext.setTypeface(typefaceFontBold);
        buttonPrevious.setTypeface(typefaceFontBold);

        editTextLoanAmount = (EditText) view.findViewById(R.id.input_loanamount);
        editTextLoanAmount.setTypeface(typefaceFont);
        editTextLoanAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String a = editText.getText().toString();

//                Log.e(TAG, "onTextChanged: "+Float.parseFloat(a) );
//                if(!a.equalsIgnoreCase("") || a != null){
//                    bubbleSeekBar1.setProgress(20);
//                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textView = (TextView) view.findViewById(R.id.textview_loanamountneeded);
        textView.setTypeface(typefaceFontBold);

        Animation a = AnimationUtils.loadAnimation(context, R.anim.slidein_left);
        a.reset();
        textView.clearAnimation();
        textView.setAnimation(a);

        if (!MainApplication.mainapp_loanamount.equalsIgnoreCase("")) {
            editTextLoanAmount.setText(MainApplication.mainapp_loanamount);
        }

        bubbleSeekBar1 = (BubbleSeekBar) view.findViewById(R.id.seekbar_loanamount);
        bubbleSeekBar1.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                MainApplication.mainapp_loanamount = loandAmount = String.valueOf(bubbleSeekBar1.getProgress());
                editTextLoanAmount.setText(loandAmount);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
        });

        bubbleSeekBar2 = (BubbleSeekBar) view.findViewById(R.id.seekbar_familyincomeamount);
        bubbleSeekBar2.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                MainApplication.mainapp_fammilyincome = loandAmount = String.valueOf(bubbleSeekBar2.getProgress());
                editText.setText(loandAmount);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PqFormFragment1 pqf2 = new PqFormFragment1();
//                MainApplication.previous_pq2_courseID = courseID;
//                MainApplication.previous_pq2_instituteID = instituteID;
//                MainApplication.previous_pq3_locationID = locationID;
                MainApplication.previousfragment3 = 1;
                transaction.replace(R.id.framelayout_pqform, pqf2).commit();

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextCourseFee.getText().toString().equalsIgnoreCase("")) {
                    if (!editTextLoanAmount.getText().toString().equalsIgnoreCase("") || editTextLoanAmount.getText().toString().equalsIgnoreCase("0")) {
                        if (!editText.getText().toString().equalsIgnoreCase("") || editText.getText().toString().equalsIgnoreCase("0")) {

                            PqFormFragment5 pqf5 = new PqFormFragment5();
//                    MainApplication.previous_pq2_courseID = courseID;
//                    MainApplication.previous_pq2_instituteID = instituteID;
//                    MainApplication.previous_pq3_locationID = locationID;
                            transaction.replace(R.id.framelayout_pqform, pqf5).commit();

                        } else {
                            Toast.makeText(context, "Family Monthly Income Cannot be 0", Toast.LENGTH_SHORT).show();
                            editText.setError("Family Monthly Income Cannot be 0");
                        }
                    } else {
                        Toast.makeText(context, "Loan Amount Cannot be 0", Toast.LENGTH_SHORT).show();
                        editTextLoanAmount.setError("Loan Amount Cannot be 0");
                    }

                } else {
                    Toast.makeText(context, "Course Fee Cannot be 0", Toast.LENGTH_SHORT).show();
                    editTextCourseFee.setError("Loan Amount Cannot be 0");
                }

            }
        });

        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "pqform/pqform/apiPrefillSliderAmount";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute_id", MainApplication.mainapp_instituteID);
            params.put("course_id", MainApplication.mainapp_courseID);
            params.put("location_id", MainApplication.mainapp_locationID);
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null, mFragment, "prefillLoanAmountFragment3", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//

        return view;
    }

    //---------------------------------RESPONSE OF API CALL---------------------------------------//
    public void prefillLoanAmountFragment3(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "PrefillInstitutesFragment1" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                resultAmount = jsonData.getString("result");
//                bubbleSeekBar1.(Integer.parseInt(resultAmount));
//                textviewMaxAmount.setText(MainApplication.indianCurrencyFormat(resultAmount));
                Log.e(TAG, "prefillLoanAmountFragment3: " + resultAmount);


            } else {
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
