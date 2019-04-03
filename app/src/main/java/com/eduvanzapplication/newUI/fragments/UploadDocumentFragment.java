package com.eduvanzapplication.newUI.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.eduvanzapplication.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class UploadDocumentFragment extends Fragment {

    public static LinearLayout linAllDocBlock,linKYCblock, linKYCblockBottom, linFinancBlockBottom, linEducationBlockBottom, linOtherBottom ,
            linFinancBlock,linEducationBlock,linOther, linKYCDocuments, linFinanceDocuments, linEducationDocuments, linOtherDocuments, linBottomBlocks;

    public static Animation  scaleInlinKYCDocuments, scaleInlinFinanceDocuments,
           scaleInlinEducationDocuments, scaleInlinOtherDocuments;


    public UploadDocumentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_uploaddocument, container, false);
        linAllDocBlock = view.findViewById(R.id.linAllDocBlock);
        linKYCblock = view.findViewById(R.id.linKYCblock);
        linKYCblockBottom = view.findViewById(R.id.linKYCblockBottom);
        linFinancBlock = view.findViewById(R.id.linFinancBlock);
        linFinancBlockBottom = view.findViewById(R.id.linFinancBlockBottom);
        linEducationBlock = view.findViewById(R.id.linEducationBlock);
        linEducationBlockBottom = view.findViewById(R.id.linEducationBlockBottom);
        linOther = view.findViewById(R.id.linOther);
        linOtherBottom = view.findViewById(R.id.linOtherBottom);
        linBottomBlocks = view.findViewById(R.id.linBottomBlocks);

        linKYCDocuments = view.findViewById(R.id.linKYCDocuments);
        linFinanceDocuments  =view.findViewById(R.id.linFinanceDocuments);
        linEducationDocuments = view.findViewById(R.id.linEducationDocuments);
        linOtherDocuments = view.findViewById(R.id.linOtherDocuments);

        linAllDocBlock.setVisibility(VISIBLE);
        linKYCDocuments.setVisibility(GONE);
        linFinanceDocuments.setVisibility(GONE);
        linEducationDocuments.setVisibility(GONE);
        linOtherDocuments.setVisibility(GONE);
        linBottomBlocks.setVisibility(GONE);

        scaleInlinKYCDocuments = AnimationUtils.loadAnimation(getContext(), R.anim.scale_in);
        scaleInlinFinanceDocuments = AnimationUtils.loadAnimation(getContext(), R.anim.scale_in);
        scaleInlinEducationDocuments = AnimationUtils.loadAnimation(getContext(), R.anim.scale_in);
        scaleInlinOtherDocuments = AnimationUtils.loadAnimation(getContext(), R.anim.scale_in);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linKYCblock.setOnClickListener(kycBlockClkListnr);
        linKYCblockBottom.setOnClickListener(kycBlockClkListnr);

        linFinancBlock.setOnClickListener(financeBlockClkListenr);
        linFinancBlockBottom.setOnClickListener(financeBlockClkListenr);

        linEducationBlock.setOnClickListener(educationBlockClkListnr);
        linEducationBlockBottom.setOnClickListener(educationBlockClkListnr);

        linOther.setOnClickListener(otherBlockClkListnr);
        linOtherBottom.setOnClickListener(otherBlockClkListnr);

        scaleInlinKYCDocuments.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linKYCDocuments.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(GONE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scaleInlinFinanceDocuments.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linFinanceDocuments.setVisibility(VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(GONE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scaleInlinEducationDocuments.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linEducationDocuments.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(GONE);
                linOtherBottom.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scaleInlinOtherDocuments.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linOtherDocuments.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    View.OnClickListener kycBlockClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            linAllDocBlock.setVisibility(GONE);
//            linKYCDocuments.setVisibility(VISIBLE);
//            linFinanceDocuments.setVisibility(GONE);
//            linEducationDocuments.setVisibility(GONE);
//            linOtherDocuments.setVisibility(GONE);
//            linBottomBlocks.setVisibility(VISIBLE);
//            linKYCblockBottom.setVisibility(GONE);
//            linFinancBlockBottom.setVisibility(VISIBLE);
//            linEducationBlockBottom.setVisibility(VISIBLE);
//            linOtherBottom.setVisibility(VISIBLE);
            Animation scaleOutlinAllDocBlock = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out);
            if (linAllDocBlock.getVisibility() == VISIBLE){
                linAllDocBlock.startAnimation(scaleOutlinAllDocBlock);
            }else{
                linAllDocBlock.setVisibility(GONE);
                linKYCDocuments.setVisibility(VISIBLE);
                linFinanceDocuments.setVisibility(GONE);
                linEducationDocuments.setVisibility(GONE);
                linOtherDocuments.setVisibility(GONE);
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(GONE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(VISIBLE);

            }
            scaleOutlinAllDocBlock.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linKYCDocuments.startAnimation(scaleInlinKYCDocuments);
                    linAllDocBlock.setVisibility(GONE);
                    linFinanceDocuments.setVisibility(GONE);
                    linEducationDocuments.setVisibility(GONE);
                    linOtherDocuments.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    };



    View.OnClickListener financeBlockClkListenr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            linAllDocBlock.setVisibility(GONE);
//            linKYCDocuments.setVisibility(GONE);
//            linFinanceDocuments.setVisibility(VISIBLE);
//            linEducationDocuments.setVisibility(GONE);
//            linOtherDocuments.setVisibility(GONE);
//            linBottomBlocks.setVisibility(VISIBLE);
//            linKYCblockBottom.setVisibility(VISIBLE);
//            linFinancBlockBottom.setVisibility(GONE);
//            linEducationBlockBottom.setVisibility(VISIBLE);
//            linOtherBottom.setVisibility(VISIBLE);
            Animation scaleOutlinAllDocBlock = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out);
            if (linAllDocBlock.getVisibility() == VISIBLE){
                linAllDocBlock.startAnimation(scaleOutlinAllDocBlock);
            }else{
                linAllDocBlock.setVisibility(GONE);
                linKYCDocuments.setVisibility(GONE);
                linFinanceDocuments.setVisibility(VISIBLE);
                linEducationDocuments.setVisibility(GONE);
                linOtherDocuments.setVisibility(GONE);
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(GONE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(VISIBLE);
            }
            scaleOutlinAllDocBlock.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linFinanceDocuments.startAnimation(scaleInlinFinanceDocuments);
                    linAllDocBlock.setVisibility(GONE);
                    linKYCDocuments.setVisibility(GONE);
                    linEducationDocuments.setVisibility(GONE);
                    linOtherDocuments.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


        }
    };

    View.OnClickListener educationBlockClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            linAllDocBlock.setVisibility(GONE);
//            linKYCDocuments.setVisibility(GONE);
//            linFinanceDocuments.setVisibility(GONE);
//            linEducationDocuments.setVisibility(VISIBLE);
//            linOtherDocuments.setVisibility(GONE);
//            linBottomBlocks.setVisibility(VISIBLE);
//            linKYCblockBottom.setVisibility(VISIBLE);
//            linFinancBlockBottom.setVisibility(VISIBLE);
//            linEducationBlockBottom.setVisibility(GONE);
//            linOtherBottom.setVisibility(VISIBLE);
            Animation scaleOutlinAllDocBlock = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out);
            if (linAllDocBlock.getVisibility() == VISIBLE){
                linAllDocBlock.startAnimation(scaleOutlinAllDocBlock);
            }else{
                linAllDocBlock.setVisibility(GONE);
                linKYCDocuments.setVisibility(GONE);
                linFinanceDocuments.setVisibility(GONE);
                linEducationDocuments.setVisibility(VISIBLE);
                linOtherDocuments.setVisibility(GONE);
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(GONE);
                linOtherBottom.setVisibility(VISIBLE);
            }
            scaleOutlinAllDocBlock.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linEducationDocuments.startAnimation(scaleInlinEducationDocuments);
                    linAllDocBlock.setVisibility(GONE);
                    linKYCDocuments.setVisibility(GONE);
                    linFinanceDocuments.setVisibility(GONE);
                    linOtherDocuments.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    };

    View.OnClickListener otherBlockClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            linAllDocBlock.setVisibility(GONE);
//            linKYCDocuments.setVisibility(GONE);
//            linFinanceDocuments.setVisibility(GONE);
//            linEducationDocuments.setVisibility(GONE);
//            linOtherDocuments.setVisibility(VISIBLE);
//            linBottomBlocks.setVisibility(VISIBLE);
//            linKYCblockBottom.setVisibility(VISIBLE);
//            linFinancBlockBottom.setVisibility(VISIBLE);
//            linEducationBlockBottom.setVisibility(VISIBLE);
//            linOtherBottom.setVisibility(GONE);

            Animation scaleOutlinAllDocBlock = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out);
            if (linAllDocBlock.getVisibility() == VISIBLE){
                linAllDocBlock.startAnimation(scaleOutlinAllDocBlock);
            }else{
                linAllDocBlock.setVisibility(GONE);
                linKYCDocuments.setVisibility(GONE);
                linFinanceDocuments.setVisibility(GONE);
                linEducationDocuments.setVisibility(GONE);
                linOtherDocuments.setVisibility(VISIBLE);
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(GONE);
            }
            scaleOutlinAllDocBlock.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linOtherDocuments.startAnimation(scaleInlinOtherDocuments);
                    linAllDocBlock.setVisibility(GONE);
                    linKYCDocuments.setVisibility(GONE);
                    linFinanceDocuments.setVisibility(GONE);
                    linEducationDocuments.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


        }
    };
}
