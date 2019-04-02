package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.newViews.NewLeadActivity;

import static android.view.View.GONE;
import static com.eduvanzapplication.newUI.newViews.NewLeadActivity.viewPager;

public class DocumentAvailabilityFragment extends Fragment {

    private static OnDocumentFragmentInteractionListener mDocListener;
    private LinearLayout linAadharBtn, linPanBtn, linBothBtn, linNoneBtn;
    private TextInputLayout tilAadhar,tilPan;


    public DocumentAvailabilityFragment() {
        // Required empty public constructor
    }

    public static DocumentAvailabilityFragment newInstance(String param1, String param2) {
        DocumentAvailabilityFragment fragment = new DocumentAvailabilityFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_document_availability, container, false);
        linAadharBtn = view.findViewById(R.id.linAadharBtn);
        linPanBtn = view.findViewById(R.id.linPanBtn);
        linBothBtn =view.findViewById(R.id.linBothBtn);
        linNoneBtn = view.findViewById(R.id.linNoneBtn);

        tilAadhar = view.findViewById(R.id.tilAadhar);
        tilPan = view.findViewById(R.id.tilPan);

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tilAadhar.setVisibility(GONE);
        tilPan.setVisibility(GONE);
        linAadharBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.documents  ="1";
                tilAadhar.setVisibility(View.VISIBLE);
                tilPan.setVisibility(GONE);
                linAadharBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linPanBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linBothBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linNoneBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linPanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.documents  ="2";
                tilAadhar.setVisibility(View.GONE);
                tilPan.setVisibility(View.VISIBLE);
                linAadharBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linPanBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linBothBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linNoneBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linBothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.documents  ="3";
                tilAadhar.setVisibility(View.VISIBLE);
                tilPan.setVisibility(View.VISIBLE);
                linAadharBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linPanBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linBothBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linNoneBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));

            }
        });

        linNoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.documents  ="4";
                tilAadhar.setVisibility(View.GONE);
                tilPan.setVisibility(View.GONE);
                linAadharBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linPanBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linBothBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linNoneBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));

            }
        });
        linAadharBtn.performClick();

        tilAadhar.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.aadharNumber = tilAadhar.getEditText().getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tilPan.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                NewLeadActivity.panNUmber = tilPan.getEditText().getText().toString();
                checkAllFields();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if (viewPager.getCurrentItem() == 1){
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (state == ViewPager.SCROLL_STATE_IDLE){

                    }
                }
            });
        }


    }

    private void checkAllFields() {

        switch (NewLeadActivity.documents){
            case "1":
                if(!Globle.validateAadharNumber(NewLeadActivity.aadharNumber)){
                    mDocListener.onOffButtonsDocuments(false, true);
                }
                else
                    mDocListener.onOffButtonsDocuments(true, true);
                break;

            case "2":
                if (NewLeadActivity.panNUmber.equals(""))
                    mDocListener.onOffButtonsDocuments(false, true);
                else
                    mDocListener.onOffButtonsDocuments(true, true);
                break;

            case "3":
                if (NewLeadActivity.aadharNumber.equals("") && NewLeadActivity.panNUmber.equals(""))
                    mDocListener.onOffButtonsDocuments(false, true);
                else
                    mDocListener.onOffButtonsDocuments(true, true);
                break;

            case "4":
                mDocListener.onOffButtonsDocuments(true, true);
                break;
        }

    }

    public static void validate() {
        switch (NewLeadActivity.documents){
            case "1":
                if (!Globle.validateAadharNumber(NewLeadActivity.aadharNumber))
                    mDocListener.onDocumentFragmentInteraction(false, 1);
                else
                    mDocListener.onDocumentFragmentInteraction(true, 2);
                break;

            case "2":
                if (NewLeadActivity.panNUmber.equals(""))
                    mDocListener.onDocumentFragmentInteraction(false, 1);
                else
                    mDocListener.onDocumentFragmentInteraction(true, 2);
                break;

            case "3":
                if (NewLeadActivity.aadharNumber.equals("") && NewLeadActivity.panNUmber.equals(""))
                    mDocListener.onDocumentFragmentInteraction(false, 1);
                else
                    mDocListener.onDocumentFragmentInteraction(true, 2);
                break;

            case "4":
                mDocListener.onDocumentFragmentInteraction(true, 2);
                break;
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mDocListener != null) {
            mDocListener.onDocumentFragmentInteraction(true,2);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDocumentFragmentInteractionListener) {
            mDocListener = (OnDocumentFragmentInteractionListener) context;
            mDocListener.onOffButtonsDocuments(false, true);

        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (NewLeadActivity.documents.equals("1")){
            linAadharBtn.performClick();
            tilAadhar.getEditText().setText(NewLeadActivity.aadharNumber);
        }
        else if (NewLeadActivity.documents.equals("2")){
            linPanBtn.performClick();
            tilPan.getEditText().setText(NewLeadActivity.panNUmber);
        }
        else if (NewLeadActivity.documents.equals("3")){
            linBothBtn.performClick();
            tilAadhar.getEditText().setText(NewLeadActivity.aadharNumber);
            tilPan.getEditText().setText(NewLeadActivity.panNUmber);
        }
        else {
            linNoneBtn.performClick();
        }



    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDocListener = null;
    }

    public interface OnDocumentFragmentInteractionListener {
        // TODO: Update argument type and name
        void onDocumentFragmentInteraction(boolean valid, int next);
        void onOffButtonsDocuments(boolean next, boolean prev);

    }
}
