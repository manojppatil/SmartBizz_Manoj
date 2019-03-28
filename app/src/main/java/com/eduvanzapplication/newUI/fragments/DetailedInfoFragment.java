package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eduvanzapplication.R;

import moe.feng.common.stepperview.VerticalStepperItemView;

public class DetailedInfoFragment extends Fragment {

	private VerticalStepperItemView mSteppersDetailed[] = new VerticalStepperItemView[2];
	private Button mNextBtn0, mNextBtn1, mPrevBtn1;

    private int mActivatedColorRes = R.color.material_blue_500;
	private int mDoneIconRes = R.drawable.ic_done_white_16dp;

    static View view;

	public static Context context;
	public static Fragment mFragment;
    public static RelativeLayout relborrower;
    public static LinearLayout linBorrowerForm;
	public static TextView txtBorrowerArrowKey;
	public static int borrowerVisiblity = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_detailedinfo_stepper, parent, false);

        linBorrowerForm = (LinearLayout) view.findViewById(R.id.linBorrowerForm);

        relborrower = (RelativeLayout) view.findViewById(R.id.relborrower);

        txtBorrowerArrowKey = (TextView) view.findViewById(R.id.txtBorrowerArrowKey);

		context = getContext();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
//		if (borrowerVisiblity == 0) {
//			linBorrowerForm.setVisibility(View.VISIBLE);
//			borrowerVisiblity = 1;
//			txtBorrowerArrowKey.setText(getResources().getString(R.string.up));
//		} else if (borrowerVisiblity == 1) {
//			linBorrowerForm.setVisibility(View.GONE);
//			borrowerVisiblity = 0;
//			txtBorrowerArrowKey.setText(getResources().getString(R.string.down));
//		}
//

//		relborrower.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (borrowerVisiblity == 0) {
//					linBorrowerForm.setVisibility(View.VISIBLE);
//					borrowerVisiblity = 1;
//					txtBorrowerArrowKey.setText(getResources().getString(R.string.up));
//				} else if (borrowerVisiblity == 1) {
//					linBorrowerForm.setVisibility(View.GONE);
//					borrowerVisiblity = 0;
//					txtBorrowerArrowKey.setText(getResources().getString(R.string.down));
//				}
//
//			}
//		});

        return view;
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

//		mSteppersDetailed[0] = view.findViewById(R.id.stepperDetailed0);
//		mSteppersDetailed[1] = view.findViewById(R.id.stepperDetailed1);
//
//		VerticalStepperItemView.bindSteppers(mSteppersDetailed);
//
//		mNextBtn0 = view.findViewById(R.id.button_next_0);
//		mNextBtn0.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppersDetailed[0].nextStep();
//			}
//		});
//
//		mSteppersDetailed[0].setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppersDetailed[0].nextStep();
//			}
//		});
//
//		mSteppersDetailed[1].setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppersDetailed[0].nextStep();
//			}
//		});
//
//		view.findViewById(R.id.button_test_error).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (mSteppersDetailed[0].getErrorText() != null) {
//					mSteppersDetailed[0].setErrorText(null);
//				} else {
//					mSteppersDetailed[0].setErrorText("Test error!");
//				}
//			}
//		});
//
//		mPrevBtn1 = view.findViewById(R.id.button_prev_1);
//		mPrevBtn1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppersDetailed[1].prevStep();
//			}
//		});
//
//		mNextBtn1 = view.findViewById(R.id.button_next_1);
//		mNextBtn1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//
//				Snackbar.make(view, "Finish!", Snackbar.LENGTH_LONG).show();
//			}
//		});
//
//		view.findViewById(R.id.btn_change_point_color).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (mActivatedColorRes == R.color.material_blue_500) {
//					mActivatedColorRes = R.color.material_deep_purple_500;
//				} else {
//					mActivatedColorRes = R.color.material_blue_500;
//				}
//				for (VerticalStepperItemView stepper : mSteppersDetailed) {
//					stepper.setActivatedColorResource(mActivatedColorRes);
//				}
//			}
//		});
//		view.findViewById(R.id.btn_change_done_icon).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (mDoneIconRes == R.drawable.ic_done_white_16dp) {
//					mDoneIconRes = R.drawable.ic_save_white_16dp;
//				} else {
//					mDoneIconRes = R.drawable.ic_done_white_16dp;
//				}
//				for (VerticalStepperItemView stepper : mSteppersDetailed) {
//					stepper.setDoneIconResource(mDoneIconRes);
//				}
//			}
//		});
	}


}
