package com.eduvanz;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class Dashboard extends Fragment {

    LinearLayout linearAboutUs,linearContactUs,linearFaq,linearHowItWorks;
    Button checkEligibility;
    TextView dealAlertMessage,eligibilityText;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Typeface typefaceFontAwesome,typeFaceReleway;
    Context mContext;
    TextView howItworkText,aboutText,contactText,faqText;


    public Dashboard() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView= inflater.inflate(R.layout.fragment_dashboard, container, false);
        mContext=getContext();
        typefaceFontAwesome = Typeface.createFromAsset(mContext.getAssets(),"fonts/droidsans_font.ttf");
        typeFaceReleway = Typeface.createFromAsset(mContext.getAssets(),"fonts/Raleway-Regular.ttf");
        linearAboutUs= (LinearLayout) mView.findViewById(R.id.about_us_ll);
        linearContactUs= (LinearLayout) mView.findViewById(R.id.contact_us_ll);
        linearFaq= (LinearLayout) mView.findViewById(R.id.freq_asked_ll);
        linearHowItWorks= (LinearLayout) mView.findViewById(R.id.how_it_work_ll);
        checkEligibility= (Button) mView.findViewById(R.id.check_eligibility_btn);
        dealAlertMessage= (TextView) mView.findViewById(R.id.dealAlertMessage);
        eligibilityText= (TextView) mView.findViewById(R.id.eligibilityText);
        faqText= (TextView) mView.findViewById(R.id.faq_text);
        aboutText= (TextView) mView.findViewById(R.id.about_us_text);
        contactText= (TextView) mView.findViewById(R.id.contact_us_text);
        howItworkText= (TextView) mView.findViewById(R.id.how_it_work_text);
        dealAlertMessage.setTypeface(typeFaceReleway);
        eligibilityText.setTypeface(typeFaceReleway);
        faqText.setTypeface(typeFaceReleway);
        aboutText.setTypeface(typeFaceReleway);
        contactText.setTypeface(typeFaceReleway);
        howItworkText.setTypeface(typeFaceReleway);

        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }



}
