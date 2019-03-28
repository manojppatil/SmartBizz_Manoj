package com.eduvanzapplication.newUI.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.eduvanzapplication.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class UploadDocumentFragment extends Fragment {

    public static LinearLayout linAllDocBlock,linKYCblock, linKYCblockBottom, linFinancBlockBottom, linEducationBlockBottom, linOtherBottom ,
            linFinancBlock,linEducationBlock,linOther, linKYCDocuments, linFinanceDocuments, linEducationDocuments, linOtherDocuments, linBottomBlocks;

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
    }

    View.OnClickListener kycBlockClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
    };

    View.OnClickListener financeBlockClkListenr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
    };

    View.OnClickListener educationBlockClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
    };

    View.OnClickListener otherBlockClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
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
    };
}
