package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badoualy.stepperindicator.StepperIndicator;
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.CameraUtils;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.SharedPref;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.fragments.CurrentAddressFragment;
import com.eduvanzapplication.newUI.fragments.DocumentAvailabilityFragment;
import com.eduvanzapplication.newUI.fragments.EmploymentDetailsFragment;
import com.eduvanzapplication.newUI.fragments.PersonalDetailsFragment;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewLeadActivity extends AppCompatActivity implements PersonalDetailsFragment.OnFragmentInteractionListener, CurrentAddressFragment.OnCurrentAddrFragmentInteractionListener, DocumentAvailabilityFragment.OnDocumentFragmentInteractionListener, EmploymentDetailsFragment.OnEmploymentFragmentInteractionListener {

    public static ViewPager viewPager;
    private ImageView ivNextBtn, ivPrevBtn;
    private LinearLayout linSubmit;
    private TextView txtStepTracker;
    private StepperIndicator stepperIndicator;
    public static ImageView ivOCRBtn;

    public static Boolean isProfileEnabled = false, isDocAvailabilityEnabled = false, isCurrentAddEnabled = false, isEmploymentDtlEnabled = false;

    public static Boolean scrolledRight = true;
    public static int lastPage = 0;
    public static String profession = "";
    public static String firstName = "", lastName = "", middleName = "", gender = "", maritalStatus = "2", dob = "";
    public static String documents = "1", aadharNumber = "", panNUmber = "";
    public static String flatBuildingSoc = "", streetLocalityLandMark = "", pinCode = "", countryId = "", stateId = "", cityId = "";
    public static String companyName = "", annualIncome = "";
    public static String instituteId = "", instituteLocationId = "", courseId = "", courseFee = "", loanAmount = "";
    public static String leadId = "", applicantId = "";
    public static String lead_id = "", student_id = "";

    public static String Aaadhaarno = "", Aname = "", Adob = "", Ayob = "", Agender = "", Aaddress = "", Astreet_address = "", Adistrict = "", Apincode = "", Astate = "", Aisscanned = "";

    public static String Ppanno = "", Ppantype = "", Pname = "", Pdob = "", Pdoi = "", Page = "", Pfathersname = "", Pisminor = "", Pisscanned = "";

    public static Context context;
    AppCompatActivity mActivity;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int height = Resources.getSystem().getDisplayMetrics().heightPixels;
        if (height < 1800) {
            setContentView(R.layout.activity_new_lead_small);
        } else {
            setContentView(R.layout.activity_new_lead);
        }
        context = this;
        mActivity = NewLeadActivity.this;
        setViews();

        isProfileEnabled = false;
        isDocAvailabilityEnabled = false;
        isCurrentAddEnabled = false;
        isEmploymentDtlEnabled = false;


        setOcrSharedPref();

        setupViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0) {
                    ivPrevBtn.setVisibility(View.GONE);
                    ivOCRBtn.setVisibility(View.VISIBLE);
                } else {
                    ivPrevBtn.setVisibility(View.VISIBLE);
                    ivOCRBtn.setVisibility(View.GONE);
                }
                txtStepTracker.setText("Step ".concat(String.valueOf(position + 1)).concat(" of ").concat("4"));

                if (lastPage > position) {
                    scrolledRight = false;
                } else if (lastPage < position) {
                    scrolledRight = true;
                } else {

                }

//                switch (position) {
//                    case 0:
//                        lastPage = position;
//                        break;
//                    case 1:
//                        PersonalDetailsFragment.validate();
//                        lastPage = position;
//                        break;
//                    case 2:
//                        DocumentAvailabilityFragment.validate();
//                        lastPage = position;
//                        break;
//                    case 3:
//                        CurrentAddressFragment.validate();
//                        lastPage = position;
//                        break;
//                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    switch (viewPager.getCurrentItem()) {
                        case 0:
                            if (scrolledRight) {
                                lastPage = viewPager.getCurrentItem();
                            } else {
                                PersonalDetailsFragment.checkAllFields();
                                lastPage = viewPager.getCurrentItem();
                            }
                            break;
                        case 1:
                            if (scrolledRight) {
                                PersonalDetailsFragment.checkAllFields();
                                if (ivNextBtn.isEnabled()) {
                                    PersonalDetailsFragment.validate();
                                    lastPage = viewPager.getCurrentItem();
                                } else {
                                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                                    lastPage = viewPager.getCurrentItem() - 1;
                                }
                            } else {
                                PersonalDetailsFragment.checkAllFields();
                                lastPage = viewPager.getCurrentItem();
                            }
                            break;

                        case 2:
                            if (scrolledRight) {
                                DocumentAvailabilityFragment.checkAllFields();
                                if (ivNextBtn.isEnabled()) {
                                    DocumentAvailabilityFragment.validate();
                                    lastPage = viewPager.getCurrentItem();
                                } else {
                                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                                    lastPage = viewPager.getCurrentItem() - 1;
                                }
                            } else {
                                try {
                                    linSubmit.setVisibility(View.GONE);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                try {
                                    ivNextBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
                                    ivNextBtn.setOnClickListener(null);
                                    ivNextBtn.setEnabled(false);
                                    ivNextBtn.setVisibility(View.VISIBLE);
                                } catch (Resources.NotFoundException e) {
                                    e.printStackTrace();
                                }
                                DocumentAvailabilityFragment.checkAllFields();
                                lastPage = viewPager.getCurrentItem();
                            }
                            break;

                        case 3:
                            if (scrolledRight) {
                                CurrentAddressFragment.checkAllFields();
                                if (ivNextBtn.isEnabled()) {
                                    ivNextBtn.setVisibility(View.GONE);
                                    linSubmit.setVisibility(View.VISIBLE);
                                    linSubmit.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
                                    linSubmit.setEnabled(false);
                                    CurrentAddressFragment.validate();
                                    EmploymentDetailsFragment.checkAllFields();
                                    lastPage = viewPager.getCurrentItem();
                                } else {
                                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                                    lastPage = viewPager.getCurrentItem() - 1;
                                }
                            } else {
                                CurrentAddressFragment.checkAllFields();
                                lastPage = viewPager.getCurrentItem();
                            }
                            break;
                    }
                }
//                if (state == ViewPager.SCROLL_STATE_IDLE) {
//                    if (viewPager.getCurrentItem() == 1) {
//                        PersonalDetailsFragment.validate();
//                    }
//                    if (viewPager.getCurrentItem() == 2) {
//                        DocumentAvailabilityFragment.validate();
//                    }
//                    if (viewPager.getCurrentItem() == 3) {
//                        CurrentAddressFragment.validate();
//                    }
//                }
            }
        });
        if (!CameraUtils.isDeviceSupportCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Sorry! Your device doesn't support camera", Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }
    }

    private void setOcrSharedPref() {

        try {

            profession = "";
            firstName = "";
            lastName = "";
            middleName = "";
            gender = "";
            maritalStatus = "2";
            dob = "";
            documents = "1";
            aadharNumber = "";
            panNUmber = "";
            flatBuildingSoc = "";
            streetLocalityLandMark = "";
            pinCode = "";
            countryId = "";
            stateId = "";
            cityId = "";
            companyName = "";
            annualIncome = "";
            instituteId = "";
            instituteLocationId = "";
            courseId = "";
            courseFee = "";
            loanAmount = "";
            leadId = "";
            applicantId = "";
            lead_id = "";
            student_id = "";

            Aaadhaarno = "";
            Aname = "";
            Adob = "";
            Ayob = "";
            Agender = "";
            Aaddress = "";
            Astreet_address = "";
            Adistrict = "";
            Apincode = "";
            Astate = "";
            Aisscanned = "";

            Ppanno = "";
            Ppantype = "";
            Pname = "";
            Pdob = "";
            Pdoi = "";
            Page = "";
            Pfathersname = "";
            Pisminor = "";
            Pisscanned = "";

            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Aaadhaarno", "");
            editor.putString("Aname", "");
            editor.putString("Adob", "");
            editor.putString("Ayob", "");
            editor.putString("Agender", "");
            editor.putString("Aaddress", "");
            editor.putString("Astreet_address", "");
            editor.putString("Adistrict", "");
            editor.putString("Apincode", "");
            editor.putString("Astate", "");
            editor.putString("Aisscanned", "");
            editor.putString("Ppanno", "");
            editor.putString("Ppantype", "");
            editor.putString("Pname", "");
            editor.putString("Pdob", "");
            editor.putString("Pdoi", "");
            editor.putString("Page", "");
            editor.putString("Pfathersname", "");
            editor.putString("Pisminor", "");
            editor.putString("Pisscanned", "");
            editor.apply();
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            sharedPreferences = this.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            firstName = sharedPreferences.getString("firstName", "");
            MainActivity.auth_token = sharedPreferences.getString("auth_token", "");
            student_id = sharedPreferences.getString("student_id", "");

            Aaadhaarno = sharedPreferences.getString("Aaadhaarno", "");
            Aname = sharedPreferences.getString("Aname", "");
            Adob = sharedPreferences.getString("Adob", "");
            Ayob = sharedPreferences.getString("Ayob", "");
            Agender = sharedPreferences.getString("Agender", "");
            Aaddress = sharedPreferences.getString("Aaddress", "");
            Astreet_address = sharedPreferences.getString("Astreet_address", "");
            Adistrict = sharedPreferences.getString("Adistrict", "");
            Apincode = sharedPreferences.getString("Apincode", "");
            Astate = sharedPreferences.getString("Astate", "");
            Aisscanned = sharedPreferences.getString("Aisscanned", "");

            Ppanno = sharedPreferences.getString("Ppanno", "");
            Ppantype = sharedPreferences.getString("Ppantype", "");
            Pname = sharedPreferences.getString("Pname", "");
            Pdob = sharedPreferences.getString("Pdob", "");
            Pdoi = sharedPreferences.getString("Pdoi", "");
            Page = sharedPreferences.getString("Page", "");
            Pfathersname = sharedPreferences.getString("Pfathersname", "");
            Pisminor = sharedPreferences.getString("Pisminor", "");
            Pisscanned = sharedPreferences.getString("Pisscanned", "");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void setOcrData() {

        if (Aaadhaarno.length() > 3) {

            if (Pname.length() > 3) {
                if (!Pname.contains(Aname.substring(0, 4))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.drawable.eduvanz_logo_new);
                    builder.setMessage("Your name on Pan card does not match with aadhaar name.")
                            .setCancelable(false)
                            .setPositiveButton("Continue with Aadhaar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Ppanno", "");
                                    editor.putString("Ppantype", "");
                                    editor.putString("Pname", "");
                                    editor.putString("Pdob", "");
                                    editor.putString("Pdoi", "");
                                    editor.putString("Page", "");
                                    editor.putString("Pfathersname", "");
                                    editor.putString("Pisminor", "");
                                    editor.putString("Pisscanned", "");
                                    editor.apply();
                                    editor.commit();

                                    Ppanno = "";
                                    Ppantype = "";
                                    Pname = "";
                                    Pdob = "";
                                    Pdoi = "";
                                    Page = "";
                                    Pfathersname = "";
                                    Pisminor = "";
                                    Pisscanned = "";
                                    setOcrData();
                                    dialog.cancel();

                                }
                            })
                            .setNegativeButton("Continue with PAN", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("Aaadhaarno", "");
                                    editor.putString("Aname", "");
                                    editor.putString("Adob", "");
                                    editor.putString("Ayob", "");
                                    editor.putString("Agender", "");
                                    editor.putString("Aaddress", "");
                                    editor.putString("Astreet_address", "");
                                    editor.putString("Adistrict", "");
                                    editor.putString("Apincode", "");
                                    editor.putString("Astate", "");
                                    editor.putString("Aisscanned", "");
                                    editor.apply();
                                    editor.commit();

                                    Aaadhaarno = "";
                                    Aname = "";
                                    Adob = "";
                                    Ayob = "";
                                    Agender = "";
                                    Aaddress = "";
                                    Astreet_address = "";
                                    Adistrict = "";
                                    Apincode = "";
                                    Astate = "";
                                    Aisscanned = "";
                                    setOcrData();
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
            firstName = Aname;
            if (Agender.toLowerCase().equals("male")) {
                gender = "1";
            }
            if (Agender.toLowerCase().equals("female")) {
                gender = "2";
            }

            if (Adob.length() > Pdob.length()) {
                dob = Adob;
            } else {
                dob = Pdob;
            }
            if (Ppanno.length() > 3) {
                documents = "3";
            } else {
                documents = "1";
            }

            aadharNumber = Aaadhaarno;
            panNUmber = Ppanno;
            flatBuildingSoc = Aaddress;
            streetLocalityLandMark = Astreet_address;
            pinCode = Apincode;

        } else if (Ppanno.length() > 3) {

            if (Aname.length() > 3) {
                if (!Aname.contains(Pname.substring(0, 4))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(R.string.app_name);
                    builder.setIcon(R.drawable.eduvanz_logo_new);
                    builder.setMessage("Your name on Pan card does not match with aadhaar name.")
                            .setCancelable(false)
                            .setPositiveButton("Continue with Aadhaar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("Continue with PAN", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
            firstName = Pname;
            if (Adob.length() > Pdob.length()) {
                dob = Adob;
            } else {
                dob = Pdob;
            }
            if (aadharNumber.length() > 3) {
                documents = "3";
            } else {
                documents = "2";
            }
            aadharNumber = Aaadhaarno;
            panNUmber = Ppanno;
        }

        DocumentAvailabilityFragment.setDcoAvailabilityData();
        CurrentAddressFragment.setCurrentAddressData();
        EmploymentDetailsFragment.setEmploymentData();
    }

    private void setViews() {
        viewPager = findViewById(R.id.viewpager);
        ivNextBtn = findViewById(R.id.ivNextBtn);
        ivPrevBtn = findViewById(R.id.ivPrevBtn);
        linSubmit = findViewById(R.id.linSubmit);
        txtStepTracker = findViewById(R.id.txtStepTracker);
        stepperIndicator = findViewById(R.id.stepperIndicator);
        ivOCRBtn = findViewById(R.id.ivOCRBtn);
        ivOCRBtn.setVisibility(View.VISIBLE);

        ivNextBtn.setOnClickListener(nextClickListener);
        ivPrevBtn.setOnClickListener(prevClickListener);
        linSubmit.setOnClickListener(nextClickListener1);
//        linSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (viewPager.getCurrentItem() == (viewPager.getAdapter().getCount() - 1)) {
//                    onOffButtonEmployent(false,true);
//                    if (!NewLeadActivity.companyName.equals("") && !NewLeadActivity.annualIncome.equals("")) {
//                        saveBorrowerData();
//                    } else {
//                        Snackbar.make(ivNextBtn, "Please fill all the fields", Snackbar.LENGTH_SHORT).show();
//                    }
//                }
//                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
//            }
//        });

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new PersonalDetailsFragment(), getResources().getString(R.string.personal_details));
        adapter.addFrag(new DocumentAvailabilityFragment(), "Document Availability");
        adapter.addFrag(new CurrentAddressFragment(), "Current Address");
        adapter.addFrag(new EmploymentDetailsFragment(), "Employment Details");
        viewPager.setAdapter(adapter);
        stepperIndicator.setViewPager(viewPager);

//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 0) {
//                    ivPrevBtn.setVisibility(View.GONE);
//                    ivOCRBtn.setVisibility(View.VISIBLE);
//                } else {
//                    ivPrevBtn.setVisibility(View.VISIBLE);
//                    ivOCRBtn.setVisibility(View.GONE);
//                }
//                txtStepTracker.setText("Step ".concat(String.valueOf(position + 1)).concat(" of ").concat(String.valueOf(adapter.getCount())));
//
//                switch (position) {
//                    case 0:
//                        break;
//                    case 1:
//                        break;
//                    case 2:
//                        break;
//                    case 3:
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                if (state == ViewPager.SCROLL_STATE_IDLE) {
//                    if (viewPager.getCurrentItem() == 1) {
//                        PersonalDetailsFragment.validate();
//                    }
//                    if (viewPager.getCurrentItem() == 2) {
//                        DocumentAvailabilityFragment.validate();
//                    }
//                    if (viewPager.getCurrentItem() == 3) {
//                        CurrentAddressFragment.validate();
//                    }
//                }
//            }
//        });

    }

    View.OnClickListener nextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (viewPager.getCurrentItem() == (viewPager.getAdapter().getCount() - 1)) {
                onOffButtonEmployent(false, true);
                if (!NewLeadActivity.companyName.equals("") && !NewLeadActivity.annualIncome.equals("")) {
//                    startActivity(new Intent(NewLeadActivity.this, CourseDetailsActivity.class)
//                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    saveBorrowerData();
                } else {
                    Snackbar.make(ivNextBtn, "Please fill all the fields", Snackbar.LENGTH_SHORT).show();
                }
            }
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    };

    View.OnClickListener nextClickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (viewPager.getCurrentItem() == (viewPager.getAdapter().getCount() - 1)) {
                onOffButtonEmployentSubmit(false, true);
                if (!NewLeadActivity.companyName.equals("") && !NewLeadActivity.annualIncome.equals("")) {
//                    startActivity(new Intent(NewLeadActivity.this, CourseDetailsActivity.class)
//                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    saveBorrowerData();
                } else {
                    Snackbar.make(ivNextBtn, "Please fill all the fields", Snackbar.LENGTH_SHORT).show();
                }
            }
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        }
    };

    View.OnClickListener prevClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (viewPager.getCurrentItem() != 0)
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    };

    @Override
    public void onOffButtons(boolean next, boolean prev) {
        enableDisableButtons(next, prev);
    }

    @Override
    public void onOffButtonsDocuments(boolean next, boolean prev) {
        enableDisableButtons(next, prev);
    }

    @Override
    public boolean onOffButtonsDocuments(boolean b) {
        return false;
    }

    @Override
    public void onOffButtonsCurrentAddress(boolean next, boolean prev) {
        enableDisableButtons(next, prev);
    }

    @Override
    public void onOffButtonEmployent(boolean next, boolean prev) {
        enableDisableButtons(next, prev);
    }

    @Override
    public void onOffButtonEmployentSubmit(boolean submit, boolean prev) {

        if (submit) {
            linSubmit.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
            linSubmit.setOnClickListener(nextClickListener);
            linSubmit.setEnabled(true);
        } else {
            linSubmit.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
            linSubmit.setOnClickListener(null);
            linSubmit.setEnabled(false);
        }
        if (prev) {
            ivPrevBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
            ivPrevBtn.setOnClickListener(prevClickListener);
        } else ivPrevBtn.setOnClickListener(prevClickListener);
    }


    private void enableDisableButtons(boolean next, boolean prev) {
        if (next) {
            ivNextBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_red_filled));
            ivNextBtn.setOnClickListener(nextClickListener);
            ivNextBtn.setEnabled(true);
        } else {
            ivNextBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_grey_filled));
            ivNextBtn.setOnClickListener(null);
            ivNextBtn.setEnabled(false);
        }
        if (prev) {
            ivPrevBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
            ivPrevBtn.setOnClickListener(prevClickListener);
        } else ivPrevBtn.setOnClickListener(prevClickListener);
    }

    @Override
    public void onFragmentInteraction(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }

    @Override
    public void onDocumentFragmentInteraction(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }

    @Override
    public void onCurrentAddrFragmentInteraction(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }

    @Override
    public void onEmploymentFragmentInteraction(boolean valid, int next) {
        viewPager.setCurrentItem(next);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private void saveBorrowerData() {

        try {//auth_token
            SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
            String student_id = "";
            student_id = sharedPreferences.getString("student_id", "");
            String auth_token = sharedPreferences.getString("auth_token", "");
            String mobileNo = sharedPreferences.getString("mobile_no", "");

            String url = MainActivity.mainUrl + "dashboard/addborrower";
            Map<String, String> params = new HashMap<String, String>();

            params.put("student_id", student_id);
            params.put("sourceId", "2");
            params.put("first_name", NewLeadActivity.firstName);
            params.put("middle_name", NewLeadActivity.middleName);
            params.put("last_name", NewLeadActivity.lastName);
            params.put("pincode", NewLeadActivity.pinCode);
            params.put("dob", NewLeadActivity.dob);
            params.put("current_address", NewLeadActivity.flatBuildingSoc);
            params.put("current_address_city", NewLeadActivity.cityId);
            params.put("current_address_state", NewLeadActivity.stateId);
            params.put("pan_number", NewLeadActivity.panNUmber);
            params.put("aadhar_number", NewLeadActivity.aadharNumber);
            params.put("mobile_number", mobileNo);
            params.put("loan_amount", "");
            params.put("current_landmark", NewLeadActivity.streetLocalityLandMark);
            params.put("current_address_country", NewLeadActivity.countryId);
            params.put("gender_id", NewLeadActivity.gender);
            params.put("marital_status", NewLeadActivity.maritalStatus);
//            params.put("has_aadhar_pan", NewLeadActivity.documents);
            params.put("profession", "1");
            params.put("employer_name", NewLeadActivity.companyName);
            params.put("annual_income", NewLeadActivity.annualIncome);
            params.put("has_aadhar_pan", documents);

//            if(MainApplication.lead_id == null) {
//                params.put("lead_id", "");
//            }
//            else {
//                params.put("lead_id", MainApplication.lead_id);
//            }
//            if(MainApplication.application_id == null) {
//
//                params.put("application_id", "");
//            }else{
//                params.put("application_id", MainApplication.application_id);
//
//            }
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(getApplicationContext(), url, NewLeadActivity.this, null, "addborrower", params, auth_token);
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(NewLeadActivity.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void addBorrowerResponse(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            MainActivity.lead_id = jsonData.optString("lead_id");
            MainActivity.applicant_id = jsonData.optString("applicant_id");
            NewLeadActivity.leadId = jsonData.optString("lead_id");
            NewLeadActivity.applicantId = jsonData.optString("applicant_id");


            if (jsonData.getInt("status") == 1) {
                startActivity(new Intent(NewLeadActivity.this, CourseDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(NewLeadActivity.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }


}
