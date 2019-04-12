package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.eduvanzapplication.R;

import moe.feng.common.stepperview.VerticalStepperItemView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class DetailedInfoFragment extends Fragment {

	private Button btnNextDetailedInfo0, btnNextDetailedInfo1, btnPreviousDetailedInfo1;
	public static TextView txtResidentialToggle, txtProfessionalToggle;
	public static LinearLayout linResidentialBlock, linProfessionalBlock;
	public static Animation collapseanimationResidential, expandAnimationResidential,collapseanimationProfessional, expandAnimationProfessional;
	private Switch switchIsPermanentAddressSame;

    static View view;

	public static Context context;
	public static Fragment mFragment;
    public static RelativeLayout relborrower;
    public static LinearLayout linBorrowerForm,linIfAddressNotSame;
	public static TextView txtBorrowerArrowKey;
	public static int borrowerVisiblity = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_detailedinfo_stepper, parent, false);
		context = getContext();
		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		linIfAddressNotSame = view.findViewById(R.id.linIfAddressNotSame);

		txtResidentialToggle = view.findViewById(R.id.txtResidentialToggle);
		linResidentialBlock = view.findViewById(R.id.linResidentialBlock);
		txtProfessionalToggle = view.findViewById(R.id.txtProfessionalToggle);
		linProfessionalBlock = view.findViewById(R.id.linProfessionalBlock);

		expandAnimationResidential = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
		collapseanimationResidential = AnimationUtils.loadAnimation(context,R.anim.scale_collapse);

		expandAnimationProfessional = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
		collapseanimationProfessional = AnimationUtils.loadAnimation(context,R.anim.scale_collapse);

		switchIsPermanentAddressSame =  view.findViewById(R.id.switchIsPermanentAddressSame);

        return view;
    }

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {


		txtResidentialToggle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (linResidentialBlock.getVisibility() == VISIBLE){
					linResidentialBlock.startAnimation(collapseanimationResidential);
				}else{
					linResidentialBlock.startAnimation(expandAnimationResidential);
				}
			}
		});

		txtProfessionalToggle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (linProfessionalBlock.getVisibility() == VISIBLE){
					linProfessionalBlock.startAnimation(collapseanimationProfessional);
				}else {
					linProfessionalBlock.startAnimation(expandAnimationProfessional);
				}
			}
		});

		collapseanimationResidential.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) { }

			@Override
			public void onAnimationEnd(Animation animation) {
				linResidentialBlock.setVisibility(GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) { }
		});

		expandAnimationResidential.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				linResidentialBlock.setVisibility(VISIBLE);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				linProfessionalBlock.startAnimation(collapseanimationProfessional);
			}

			@Override
			public void onAnimationRepeat(Animation animation) { }
		});


		collapseanimationProfessional.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) { }

			@Override
			public void onAnimationEnd(Animation animation) {
				linProfessionalBlock.setVisibility(GONE);
			}

			@Override
			public void onAnimationRepeat(Animation animation) { }
		});

		expandAnimationProfessional.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				linProfessionalBlock.setVisibility(VISIBLE);
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				linProfessionalBlock.startAnimation(collapseanimationResidential);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {}
		});

		switchIsPermanentAddressSame.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
				{
					linIfAddressNotSame.setVisibility(View.VISIBLE);
				}
				else {
					linIfAddressNotSame.setVisibility(View.GONE);
				}
			}
		});

//		applyFieldsChangeListener();

	}


}
