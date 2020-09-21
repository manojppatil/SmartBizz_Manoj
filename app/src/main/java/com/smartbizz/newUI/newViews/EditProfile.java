package com.smartbizz.newUI.newViews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.smartbizz.Util.FileUtil;
import com.smartbizz.R;
import com.smartbizz.Util.Constants;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.Util.NumberUtil;
import com.smartbizz.Util.PreferenceManager;
import com.smartbizz.newUI.adapter.SpinnerAdapter;
import com.smartbizz.newUI.network.ApiConstants;
import com.smartbizz.newUI.network.NetworkManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.smartbizz.newUI.newViews.DashboardActivity.textViewName;
import static com.smartbizz.newUI.newViews.DashboardActivity.textViewEmail;
import static com.smartbizz.newUI.newViews.DashboardActivity.ivUserPic;
import static com.smartbizz.newUI.newViews.DashboardActivity.userPic;

public class EditProfile extends BaseActivity implements AdapterView.OnItemSelectedListener{
    private static final int PICK_IMAGE = 1;
    public static CircleImageView profileImage;
    public EditText firstName, email, BrandName, etAddress;
    private LinearLayout submit_button;
    public int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_DOC = 2;
    public String userChoosenTask;
    private String uploadFilePath = "";
    ProgressBar progressBar;
    public static AppCompatActivity mActivity;
    Context context;
    private String employerType;

    List<String> EMPLOYER_TYPE_LIST = new ArrayList<String>() {{
        add("Select one");
        add("Private Limited");
        add("Partnership");
        add("Proprietorship");
        add("Public Limited");
        add("Government Sector");
    }};

    private AppCompatSpinner spinnerEmployerType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = EditProfile.this;
        mActivity = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_white);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        profileImage = findViewById(R.id.profileImage);
        firstName = findViewById(R.id.etFirstNme);
        email = findViewById(R.id.etEmail);
        BrandName = findViewById(R.id.etBrandName);
        etAddress = findViewById(R.id.etAddress);
        spinnerEmployerType = findViewById(R.id.spinnerEmployerType);
        submit_button = findViewById(R.id.linSubmit);
        progressBar = findViewById(R.id.progressBar_editProfile);

        spinnerEmployerType.setOnItemSelectedListener(this);

        submit_button.setOnClickListener(v -> {
            saveEditProfileData(uploadFilePath);
            finish();
        });
        setEmployerTypeAdapters();
        profileImage.setOnClickListener(v -> selectImage());
        setProfileApiCall();

    }

    private void setEmployerTypeAdapters() {
        //Rented/Owned Adapter
        SpinnerAdapter employerTypeAdapter = new SpinnerAdapter<>(activity, R.layout.spinner_item, EMPLOYER_TYPE_LIST);
        employerTypeAdapter.setDropDownViewResource(R.layout.spinner_drop_down_item);
        spinnerEmployerType.setAdapter(employerTypeAdapter);
        spinnerEmployerType.setSelection(0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void selectImage() {
        final CharSequence[] items = {"Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Logo!");
        builder.setItems(items, (dialog, item) -> {
            boolean result = true;

            if (items[item].equals("Choose from Gallery")) {
                userChoosenTask = "Choose from Gallery";
                if (result)
                    galleryIntent();

            } else if (items[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        uploadFilePath = destination.toString();
        Log.e("TAG", "onCaptureImageResult: " + uploadFilePath);
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
                Uri selectedFileUri = data.getData();
                profileImage.setImageBitmap(bm);
                uploadFilePath = FileUtil.getPath(activity, data.getData());
//                uploadFilePath = PathFile.getPath(context, selectedFileUri);
                Log.e("TAG", "onSelectFromGalleryResult: " + uploadFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void saveEditProfileData(String filePath) {

        if (TextUtils.isEmpty(employerType)) {
            makeToast("Please select CompanyType");
            return;
        }
        if (TextUtils.isEmpty(BrandName.getText().toString().trim())) {
            makeToast("Please enter brandname");
            return;
        } if (TextUtils.isEmpty(firstName.getText().toString().trim())) {
            makeToast("Please select name");
            return;
        }if (TextUtils.isEmpty(email.getText().toString().trim())) {
            makeToast("Please select email");
            return;
        }if (TextUtils.isEmpty(etAddress.getText().toString().trim())) {
            makeToast("Please select address");
            return;
        }

        DialogUtil.showProgressDialog(activity);
        NetworkManager.getInstance(activity).putUploadBankStmt(activity, BrandName.getText().toString().trim(), firstName.getText().toString().trim(), email.getText().toString().trim(), etAddress.getText().toString().trim(),employerType, filePath, response -> {
            DialogUtil.dismissProgressDialog();
            if (response.isSuccess()) {
                setProfileApiCall();
            } else {
                makeToast(response.getMessage());
            }
        });

    }

    public void setProfileApiCall() {

        DialogUtil.showProgressDialog(activity);
        NetworkManager.getInstance(activity).getProfile(activity, response -> {
            DialogUtil.dismissProgressDialog();
            if (response.isSuccess()) {
                JSONObject jsonObject = null;
                try {
                    jsonObject = (JSONObject) response.getResponse().optJSONObject("result").optJSONArray("userData").get(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    PreferenceManager.saveString(activity, Constants.PrefKeys.FIRST_NAME, jsonObject.optString(ApiConstants.Keys.FIRST_NAME));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    PreferenceManager.saveString(activity, Constants.PrefKeys.FIRST_NAME, jsonObject.optString("fullname"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    PreferenceManager.saveString(activity, Constants.PrefKeys.LOGO, jsonObject.optString("image_path"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    PreferenceManager.saveString(activity, Constants.PrefKeys.EMAIL, jsonObject.optString(ApiConstants.Keys.EMAIL));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    PreferenceManager.saveString(activity, Constants.PrefKeys.Address, jsonObject.optString("address"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    PreferenceManager.saveString(activity, Constants.PrefKeys.BRANDNAME, jsonObject.optString("brand_name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(jsonObject.has("company_type")) {
                    for (int i = 0; i < EMPLOYER_TYPE_LIST.size(); i++) {

                        if (EMPLOYER_TYPE_LIST.get(i).equalsIgnoreCase(jsonObject.optString("company_type"))) {
                            spinnerEmployerType.setSelection(i+1);
                            try {
                                PreferenceManager.saveString(activity, Constants.PrefKeys.COMPANYTYPE, jsonObject.optString("company_type"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                firstName.setText(PreferenceManager.getString(context, Constants.PrefKeys.FIRST_NAME));
                email.setText(PreferenceManager.getString(context, Constants.PrefKeys.EMAIL));
                BrandName.setText(PreferenceManager.getString(context, Constants.PrefKeys.BRANDNAME));
                etAddress.setText(PreferenceManager.getString(context, Constants.PrefKeys.Address));

                if (!TextUtils.isEmpty(PreferenceManager.getString(context, Constants.PrefKeys.COMPANYTYPE))) {
                    spinnerEmployerType.setSelection(NumberUtil.getInt(PreferenceManager.getString(context, Constants.PrefKeys.COMPANYTYPE)) + 1);//id start from 0
                }

                Picasso.with(context)
                        .load(ApiConstants.BASE_URL + "/" + PreferenceManager.getString(context, Constants.PrefKeys.LOGO))
                        .into(profileImage);

                Picasso.with(context)
                        .load(ApiConstants.BASE_URL + "/" + PreferenceManager.getString(context, Constants.PrefKeys.LOGO))
                        .placeholder(getResources().getDrawable(R.drawable.ic_building))
                        .into(profileImage);

                textViewName.setText(PreferenceManager.getString(context, Constants.PrefKeys.FIRST_NAME));
                textViewEmail.setText(PreferenceManager.getString(context, Constants.PrefKeys.EMAIL));

                Picasso.with(context)
                        .load(ApiConstants.BASE_URL + "/" + PreferenceManager.getString(context, Constants.PrefKeys.LOGO))
                        .placeholder(getResources().getDrawable(R.drawable.ic_building))
                        .into(ivUserPic);

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            switch (parent.getId()) {
                case R.id.spinnerEmployerType:
//                    employerType = String.valueOf(position-1);
                    employerType = EMPLOYER_TYPE_LIST.get(position-1);
                    break;

            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
