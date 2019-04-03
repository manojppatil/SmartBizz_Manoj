package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.eduvanzapplication.R;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class KycDetailFragment extends Fragment {
    static View view;
    public static Context context;
    public static TextView txtPersonalToggle, txtIdentityToggle, txtCourseToggle;
    public static LinearLayout linPersonalBlock,relIdentityBlock, relCourseBlock;
    public static Animation expandAnimationPersonal, collapseanimationPersonal;
    public static Animation expandAnimationIdentity, collapseAnimationIdentity;
    public static Animation expanAnimationCourse, collapseAnimationCourse;
    public static FloatingActionButton fabEdit;
    public static boolean isEdit = false;

    public static TextInputLayout tilFirstName, tilMiddleName, tilLastName, tilEmail, tilMobile, tilFlat, tilStreet, tilPincode;
    public static LinearLayout linMale,linFemale,linOther, linDob, linMaritalStatus;
    public static EditText edtAadhaar, edtPAN, edtLoanAmt;
    public static Spinner spCountry, spState, spCity, spInsttLocation, spCourse;
    public static AutoCompleteTextView acInstituteName;
    public static TextView txtCourseFee;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_kycdetail_stepper, container, false);
        context = getContext();
        expandAnimationPersonal = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        expandAnimationIdentity= AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        expanAnimationCourse = AnimationUtils.loadAnimation(context, R.anim.scale_expand);
        collapseanimationPersonal = AnimationUtils.loadAnimation(context,R.anim.scale_collapse);
        collapseAnimationIdentity = AnimationUtils.loadAnimation(context,R.anim.scale_collapse);
        collapseAnimationCourse = AnimationUtils.loadAnimation(context,R.anim.scale_collapse);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        txtPersonalToggle = view.findViewById(R.id.txtPersonalToggle);
        linPersonalBlock = view.findViewById(R.id.linPersonalBlock);
        txtIdentityToggle = view.findViewById(R.id.txtIdentityToggle);
        relIdentityBlock = view.findViewById(R.id.relIdentityBlock);
        txtCourseToggle = view.findViewById(R.id.txtCourseToggle);
        relCourseBlock = view.findViewById(R.id.relCourseBlock);
        fabEdit = view.findViewById(R.id.fabEdit);
        tilFirstName = view.findViewById(R.id.tilFirstName);
        tilMiddleName = view.findViewById(R.id.tilMiddleName);
        tilLastName = view.findViewById(R.id.tilLastName);

        tilEmail = view.findViewById(R.id.tilEmail);
        tilMobile = view.findViewById(R.id.tilMobile);
        tilFlat = view.findViewById(R.id.tilFlat);
        tilStreet = view.findViewById(R.id.tilStreet);
        tilPincode = view.findViewById(R.id.tilPincode);
        linMale = view.findViewById(R.id.linMale);
        linFemale = view.findViewById(R.id.linFemale);
        linOther = view.findViewById(R.id.linOther);
        linDob = view.findViewById(R.id.linDob);
        linMaritalStatus = view.findViewById(R.id.linMaritalStatus);
        edtAadhaar = view.findViewById(R.id.edtAadhaar);
        edtPAN = view.findViewById(R.id.edtPAN);
        edtLoanAmt = view.findViewById(R.id.edtLoanAmt);
        spCountry = view.findViewById(R.id.spCountry);
        spState = view.findViewById(R.id.spState);
        spCity = view.findViewById(R.id.spCity);
        spInsttLocation = view.findViewById(R.id.spInsttLocation);
        spCourse = view.findViewById(R.id.spCourse);
        acInstituteName = view.findViewById(R.id.acInstituteName);
        txtCourseFee = view.findViewById(R.id.txtCourseFee);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linPersonalBlock.startAnimation(expandAnimationPersonal);
        relIdentityBlock.startAnimation(collapseAnimationIdentity);
        relCourseBlock.startAnimation(collapseAnimationCourse);
        setViewsEnabled(false);

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isEdit){
                    isEdit = true;
                    setViewsEnabled(true);
                    fabEdit.setImageResource(R.drawable.ic_save_white_16dp);
                    fabEdit.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                }
                else {
                }
            }
        });

        txtIdentityToggle.setCompoundDrawablesRelativeWithIntrinsicBounds(context.getResources().getDrawable(R.drawable.ic_id_card),null, context.getResources().getDrawable(R.drawable.ic_exclamation_circle_yellow),null);
        txtIdentityToggle.getCompoundDrawablesRelative()[0].setTint(Color.RED);
        txtPersonalToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (linPersonalBlock.getVisibility() == VISIBLE){
                    linPersonalBlock.startAnimation(collapseanimationPersonal);
                }else{
                    linPersonalBlock.startAnimation(expandAnimationPersonal);
                }
            }
        });

        txtIdentityToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relIdentityBlock.getVisibility() == VISIBLE){
                    relIdentityBlock.startAnimation(collapseAnimationIdentity);
                }else {
                    relIdentityBlock.startAnimation(expandAnimationIdentity);
                }
            }
        });

        txtCourseToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (relCourseBlock.getVisibility() == VISIBLE){
                    relCourseBlock.startAnimation(collapseAnimationCourse);
                }else {
                    relCourseBlock.startAnimation(expanAnimationCourse);
                }
            }
        });


        collapseanimationPersonal.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linPersonalBlock.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        expandAnimationPersonal.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linPersonalBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                relIdentityBlock.startAnimation(collapseAnimationIdentity);
                relCourseBlock.startAnimation(collapseAnimationCourse);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        collapseAnimationIdentity.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                relIdentityBlock.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        expandAnimationIdentity.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                relIdentityBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linPersonalBlock.startAnimation(collapseanimationPersonal);
                relCourseBlock.startAnimation(collapseAnimationCourse);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        collapseAnimationCourse.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                relCourseBlock.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        expanAnimationCourse.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                relCourseBlock.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linPersonalBlock.startAnimation(collapseanimationPersonal);
                relIdentityBlock.startAnimation(collapseAnimationIdentity);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void setViewsEnabled(boolean f){
        tilFirstName.setEnabled(f);
        tilMiddleName.setEnabled(f);
        tilLastName.setEnabled(f);
        linMale.setEnabled(f);
        linFemale.setEnabled(f);
        linOther.setEnabled(f);
        linDob.setEnabled(f);
        linMaritalStatus.setEnabled(f);
        tilEmail.setEnabled(f);
        tilMobile.setEnabled(f);
        edtAadhaar.setEnabled(f);
        edtPAN.setEnabled(f);
        tilFlat.setEnabled(f);
        tilStreet.setEnabled(f);
        tilPincode.setEnabled(f);
        spCountry.setEnabled(f);
        spState.setEnabled(f);
        spCity.setEnabled(f);
        acInstituteName.setEnabled(f);
        spInsttLocation.setEnabled(f);
        spCourse.setEnabled(f);
        txtCourseFee.setEnabled(f);
        edtLoanAmt.setEnabled(f);

    }



    //	private VerticalStepperItemView mSteppers[] = new VerticalStepperItemView[3];
//	private Button btnNextKycDetail0, btnNextKycDetail1, btnPreviousKycDetail1, btnNextKycDetail2, btnPreviousKycDetail2;
//
//    private int mActivatedColorRes = R.color.material_blue_500;
//	private int mDoneIconRes = R.drawable.ic_done_white_16dp;
//
//    static View view;
//
//	public static Context context;
//	public static Fragment mFragment;
//    public static RelativeLayout relborrower;
//    public static LinearLayout linBorrowerForm;
//	public static TextView txtBorrowerArrowKey;
//	public static int borrowerVisiblity = 0;
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
//
//		view = inflater.inflate(R.layout.fragment_kycdetail_stepper, parent, false);
//
//        linBorrowerForm = (LinearLayout) view.findViewById(R.id.linBorrowerForm);
//
//        relborrower = (RelativeLayout) view.findViewById(R.id.relborrower);
//
//        txtBorrowerArrowKey = (TextView) view.findViewById(R.id.txtBorrowerArrowKey);
//
//		context = getContext();
//		getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
////		if (borrowerVisiblity == 0) {
////			linBorrowerForm.setVisibility(View.VISIBLE);
////			borrowerVisiblity = 1;
////			txtBorrowerArrowKey.setText(getResources().getString(R.string.up));
////		} else if (borrowerVisiblity == 1) {
////			linBorrowerForm.setVisibility(View.GONE);
////			borrowerVisiblity = 0;
////			txtBorrowerArrowKey.setText(getResources().getString(R.string.down));
////		}
////
////
////		relborrower.setOnClickListener(new View.OnClickListener() {
////			@Override
////			public void onClick(View v) {
////				if (borrowerVisiblity == 0) {
////					linBorrowerForm.setVisibility(View.VISIBLE);
////					borrowerVisiblity = 1;
////					txtBorrowerArrowKey.setText(getResources().getString(R.string.up));
////				} else if (borrowerVisiblity == 1) {
////					linBorrowerForm.setVisibility(View.GONE);
////					borrowerVisiblity = 0;
////					txtBorrowerArrowKey.setText(getResources().getString(R.string.down));
////				}
////
////			}
////		});
//
//        return view;
//    }
//
//	@Override
//	public void onViewCreated(View view, Bundle savedInstanceState) {
//
//	    mSteppers[0] = view.findViewById(R.id.stepperKyc0);
//		mSteppers[1] = view.findViewById(R.id.stepperKyc1);
//		mSteppers[2] = view.findViewById(R.id.stepperKyc2);
//
//		VerticalStepperItemView.bindSteppers(mSteppers);
//
//		btnNextKycDetail0 = view.findViewById(R.id.btnNextKycDetail0);
//		btnNextKycDetail0.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[0].nextStep();
//			}
//		});
//
//		mSteppers[0].setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[0].nextStep();
//			}
//		});
//
//		mSteppers[1].setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[0].nextStep();
//			}
//		});
//
//		mSteppers[2].setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[1].nextStep();
//			}
//		});
//
//		view.findViewById(R.id.button_test_error).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (mSteppers[0].getErrorText() != null) {
//					mSteppers[0].setErrorText(null);
//				} else {
//					mSteppers[0].setErrorText("Test error!");
//				}
//			}
//		});
//
//		btnPreviousKycDetail1 = view.findViewById(R.id.btnPreviousKycDetail1);
//		btnPreviousKycDetail1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[1].prevStep();
//			}
//		});
//
//		btnNextKycDetail1 = view.findViewById(R.id.btnNextKycDetail1);
//		btnNextKycDetail1.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[1].nextStep();
//			}
//		});
//
//		btnPreviousKycDetail2 = view.findViewById(R.id.btnPreviousKycDetail2);
//		btnPreviousKycDetail2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				mSteppers[2].prevStep();
//			}
//		});
//
//		btnNextKycDetail2 = view.findViewById(R.id.btnNextKycDetail2);
//		btnNextKycDetail2.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				Snackbar.make(view, "Finish!", Snackbar.LENGTH_LONG).show();
//			}
//		});
//
//
//		view.findViewById(R.id.btn_change_point_color).setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View view) {
//				if (mActivatedColorRes == R.color.material_blue_500) {
//					mActivatedColorRes = R.color.material_deep_purple_500;
//				} else {
//					mActivatedColorRes = R.color.material_blue_500;
//				}
//				for (VerticalStepperItemView stepper : mSteppers) {
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
//				for (VerticalStepperItemView stepper : mSteppers) {
//					stepper.setDoneIconResource(mDoneIconRes);
//				}
//			}
//		});
//	}


}
