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

public class KycDetailFragment extends Fragment {

	private VerticalStepperItemView mSteppers[] = new VerticalStepperItemView[3];
	private Button btnNextKycDetail0, btnNextKycDetail1, btnPreviousKycDetail1, btnNextKycDetail2, btnPreviousKycDetail2;

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

		view = inflater.inflate(R.layout.fragment_kycdetail_stepper, parent, false);

        linBorrowerForm = (LinearLayout) view.findViewById(R.id.linBorrowerForm);

        relborrower = (RelativeLayout) view.findViewById(R.id.relborrower);

        txtBorrowerArrowKey = (TextView) view.findViewById(R.id.txtBorrowerArrowKey);

		context = getContext();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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

	    mSteppers[0] = view.findViewById(R.id.stepperKyc0);
		mSteppers[1] = view.findViewById(R.id.stepperKyc1);
		mSteppers[2] = view.findViewById(R.id.stepperKyc2);

		VerticalStepperItemView.bindSteppers(mSteppers);

		btnNextKycDetail0 = view.findViewById(R.id.btnNextKycDetail0);
		btnNextKycDetail0.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mSteppers[0].nextStep();
			}
		});

		mSteppers[0].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mSteppers[0].nextStep();
			}
		});

		mSteppers[1].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mSteppers[0].nextStep();
			}
		});

		mSteppers[2].setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mSteppers[1].nextStep();
			}
		});

		view.findViewById(R.id.button_test_error).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mSteppers[0].getErrorText() != null) {
					mSteppers[0].setErrorText(null);
				} else {
					mSteppers[0].setErrorText("Test error!");
				}
			}
		});

		btnPreviousKycDetail1 = view.findViewById(R.id.btnPreviousKycDetail1);
		btnPreviousKycDetail1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mSteppers[1].prevStep();
			}
		});

		btnNextKycDetail1 = view.findViewById(R.id.btnNextKycDetail1);
		btnNextKycDetail1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mSteppers[1].nextStep();
			}
		});

		btnPreviousKycDetail2 = view.findViewById(R.id.btnPreviousKycDetail2);
		btnPreviousKycDetail2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mSteppers[2].prevStep();
			}
		});

		btnNextKycDetail2 = view.findViewById(R.id.btnNextKycDetail2);
		btnNextKycDetail2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Finish!", Snackbar.LENGTH_LONG).show();
			}
		});


		view.findViewById(R.id.btn_change_point_color).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mActivatedColorRes == R.color.material_blue_500) {
					mActivatedColorRes = R.color.material_deep_purple_500;
				} else {
					mActivatedColorRes = R.color.material_blue_500;
				}
				for (VerticalStepperItemView stepper : mSteppers) {
					stepper.setActivatedColorResource(mActivatedColorRes);
				}
			}
		});
		view.findViewById(R.id.btn_change_done_icon).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mDoneIconRes == R.drawable.ic_done_white_16dp) {
					mDoneIconRes = R.drawable.ic_save_white_16dp;
				} else {
					mDoneIconRes = R.drawable.ic_done_white_16dp;
				}
				for (VerticalStepperItemView stepper : mSteppers) {
					stepper.setDoneIconResource(mDoneIconRes);
				}
			}
		});
	}




}
