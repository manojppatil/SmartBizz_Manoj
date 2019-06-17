package com.eduvanzapplication.newUI.newViews;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduvanzapplication.BuildConfig;
import com.eduvanzapplication.R;


public class LeadOwnerType extends AppCompatActivity {

    private final String TAG = LeadOwnerType.class.getSimpleName();
    LinearLayout linContinue;
    public static LinearLayout linStudentBtn, linSalariedBtn, linSelfEmployedBtn,lin3;
    public static ImageView ivStud,ivSal,ivSelfEmp;
           public static View viewdashStud,viewdashSal, viewdashSelfEmp;
   public static TextView txtStudent,txtSalaried,txtSelfEmp ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leadowner);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        setViews();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    private void setViews() {

        lin3 = findViewById(R.id.lin3);
        linContinue = findViewById(R.id.linContinue);

        linStudentBtn = findViewById(R.id.linStudentBtn);
        linSalariedBtn = findViewById(R.id.linSalariedBtn);
        linSelfEmployedBtn = findViewById(R.id.linSelfEmployedBtn);

        ivStud = findViewById(R.id.ivStud);
        ivSal = findViewById(R.id.ivSal);
        ivSelfEmp = findViewById(R.id.ivSelfEmp);

        viewdashStud = findViewById(R.id.viewdashStud);
        viewdashSal = findViewById(R.id.viewdashSal);
        viewdashSelfEmp = findViewById(R.id.viewdashSelfEmp);

        txtStudent = findViewById(R.id.txtStudent);
        txtSalaried = findViewById(R.id.txtSalaried);
        txtSelfEmp = findViewById(R.id.txtSelfEmp);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow);
//        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        Drawable bg;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_student, null);
            ivStud.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
        } else {
            bg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_student);
            DrawableCompat.setTint(bg, getResources().getColor(R.color.darkblue));
        }
        ivStud.setImageDrawable(bg);

        Drawable bg1;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_salaried, null);
            ivSal.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
        } else {
            bg1 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_salaried);
            DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
        }
        ivSal.setImageDrawable(bg1);

        Drawable bg2;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_self_employed, null);
            ivSelfEmp.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
        } else {
            bg2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_self_employed);
            DrawableCompat.setTint(bg2, getResources().getColor(R.color.darkblue));
        }
        ivSelfEmp.setImageDrawable(bg2);

        linStudentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.profession = "2";
                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linContinue.setClickable(true);
                lin3.setVisibility(View.VISIBLE);

                txtStudent.setTextColor(getResources().getColor(R.color.white));
                txtSalaried.setTextColor(getResources().getColor(R.color.textcolordark));
                txtSelfEmp.setTextColor(getResources().getColor(R.color.textcolordark));

                viewdashStud.setBackgroundColor(getResources().getColor(R.color.white));
                viewdashSal.setBackgroundColor(getResources().getColor(R.color.blue1));
                viewdashSelfEmp.setBackgroundColor(getResources().getColor(R.color.blue1));

                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_student, null);
                    ivStud.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_student);
                    DrawableCompat.setTint(bg, getResources().getColor(R.color.white));
                }
                ivStud.setImageDrawable(bg);

                Drawable bg1;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_salaried, null);
                    ivSal.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg1 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_salaried);
                    DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
                }
                ivSal.setImageDrawable(bg1);

                Drawable bg2;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_self_employed, null);
                    ivSelfEmp.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_self_employed);
                    DrawableCompat.setTint(bg2, getResources().getColor(R.color.darkblue));
                }
                ivSelfEmp.setImageDrawable(bg2);

                linContinue.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));

            }
        });

        linSalariedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.profession = "1";
                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linContinue.setClickable(true);
                lin3.setVisibility(View.INVISIBLE);

                txtStudent.setTextColor(getResources().getColor(R.color.textcolordark));
                txtSalaried.setTextColor(getResources().getColor(R.color.white));
                txtSelfEmp.setTextColor(getResources().getColor(R.color.textcolordark));

                viewdashStud.setBackgroundColor(getResources().getColor(R.color.blue1));
                viewdashSal.setBackgroundColor(getResources().getColor(R.color.white));
                viewdashSelfEmp.setBackgroundColor(getResources().getColor(R.color.blue1));

                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_salaried, null);
                    ivSal.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_salaried);
                    DrawableCompat.setTint(bg, getResources().getColor(R.color.white));
                }
                ivSal.setImageDrawable(bg);

                Drawable bg1;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_student, null);
                    ivStud.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg1 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_student);
                    DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
                }
                ivStud.setImageDrawable(bg1);

                Drawable bg2;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_self_employed, null);
                    ivSelfEmp.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_self_employed);
                    DrawableCompat.setTint(bg2, getResources().getColor(R.color.darkblue));
                }
                ivSelfEmp.setImageDrawable(bg2);


                linContinue.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
            }
        });

        linSelfEmployedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewLeadActivity.profession = "3";
                linStudentBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSalariedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linSelfEmployedBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linContinue.setClickable(true);
                lin3.setVisibility(View.INVISIBLE);

                txtStudent.setTextColor(getResources().getColor(R.color.textcolordark));
                txtSalaried.setTextColor(getResources().getColor(R.color.textcolordark));
                txtSelfEmp.setTextColor(getResources().getColor(R.color.white));

                viewdashStud.setBackgroundColor(getResources().getColor(R.color.blue1));
                viewdashSal.setBackgroundColor(getResources().getColor(R.color.blue1));
                viewdashSelfEmp.setBackgroundColor(getResources().getColor(R.color.white));

                Drawable bg;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_self_employed, null);
                    ivSelfEmp.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_self_employed);
                    DrawableCompat.setTint(bg, getResources().getColor(R.color.white));
                }
                ivSelfEmp.setImageDrawable(bg);

                Drawable bg2;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg2 = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_student, null);
                    ivStud.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_student);
                    DrawableCompat.setTint(bg2, getResources().getColor(R.color.darkblue));
                }
                ivStud.setImageDrawable(bg2);

                Drawable bg1;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    bg1 = VectorDrawableCompat.create(getResources(), R.drawable.ic_profession_salaried, null);
                    ivSal.setColorFilter(getResources().getColor(R.color.darkblue), PorterDuff.Mode.MULTIPLY);
                } else {
                    bg1 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_profession_salaried);
                    DrawableCompat.setTint(bg1, getResources().getColor(R.color.darkblue));
                }
                ivSal.setImageDrawable(bg1);

                linContinue.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
            }
        });

        linContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeadOwnerType.this, NewLeadActivity.class));
                LeadOwnerType.this.finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1:
                if (grantResults.length <= 0) {
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED && grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[4] == PackageManager.PERMISSION_GRANTED && grantResults[5] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[6] == PackageManager.PERMISSION_GRANTED && grantResults[7] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[8] == PackageManager.PERMISSION_GRANTED) {
                    //granted
//                    apiCall();
                } else {
                    //not granted
//                    Log.e(MainApplication.TAG, "not granted: Dashboard " + grantResults[0]);
                    {
                        // Permission denied.
                        // Notify the user via a SnackBar that they have rejected a core permission for the
                        // app, which makes the Activity useless. In a real app, core permissions would
                        // typically be best requested during a welcome-screen flow.
                        // Additionally, it is important to remember that a permission might have been
                        // rejected without asking the user for permission (device policy or "Never ask
                        // again" prompts). Therefore, a user interface affordance is typically implemented
                        // when permissions are denied. Otherwise, your app could appear unresponsive to
                        // touches or interactions which have required permissions.
                        //                    Toast.makeText(this, R.string.permission_denied_explanation, Toast.LENGTH_LONG).show();
                        //                    finish();

                        try {
//                            button.setEnabled(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Snackbar.make(
                                findViewById(R.id.rootViews),
                                R.string.permission_denied_explanation,
                                Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.settings, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Build intent that displays the App settings screen.
                                        Intent intent = new Intent();
                                        intent.setAction(
                                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        Uri uri = Uri.fromParts("package",
                                                BuildConfig.APPLICATION_ID, null);
                                        intent.setData(uri);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }
                }
                break;
        }

    }
}
