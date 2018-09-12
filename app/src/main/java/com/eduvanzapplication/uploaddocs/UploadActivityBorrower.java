package com.eduvanzapplication.uploaddocs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.volley.VolleyCall;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class UploadActivityBorrower extends AppCompatActivity {

    public Boolean kyc = true, financial = true, education = true, other = true;
    public String documentType = "";
    public String documentTypeNo = "";
    public int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_DOC = 2;
    public String userChoosenTask;
    AppCompatActivity mActivity;
    LinearLayout linearLayoutKyc, linearLayoutKycHide, linearLayoutFinancial,
            linearLayoutFinancialHide, linearLayoutEducation, linearLayoutEducationHide,
            linearLayoutOther, linearLayoutOtherHide;
    ImageView imageViewECKyc, imageViewECFinancial, imageViewECEducation, imageViewECOther,
            imageViewUploadTick_9, imageViewUploadTick_1, imageViewUploadTick_2, imageViewUploadTick_3, imageViewUploadTick_4, imageViewUploadTick_5, imageViewUploadTick_6, imageViewUploadTick_7,
            imageViewUploadTick_8;
    Animation a, b;
    Button buttonKycProfilePhoto, buttonKycPhotoId, buttonKycAddressProof, buttonKycSignatureProof,
            buttonFinanceIncomeProof, buttonFinanceBankStatement, buttonEducationDegreeMarksheets, buttonEducationDegreeCertificate,
            buttonOtherDocument;
    ImageView imageViewKycProfilePhoto, imageViewKycPhotoId, imageViewKycAddressProof, imageViewKycSignatureProof,
            imageViewFinanceIncomeProof, imageViewFinanceBankStatement, imageViewEducationDegreeMarksheets, imageViewEducationDegreeCertificate,
            imageViewOtherDocument;
    TextView textViewKycProfilePhoto, textViewKycPhotoId, textViewKycAddressProof, textViewKycSignatureProof;
    String uploadFilePath = "";
    String urlup = MainApplication.mainUrl + "document/applicantDocumentUpload";
    StringBuffer sb;
    ProgressDialog mDialog;
    TextView textView1, textView2, textView3;
    Typeface typefaceFont, typefaceFontBold;
    String userID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_borrower);

        mActivity = this;

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id", "null");

        typefaceFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_bold.ttf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Upload Documents");

        imageViewUploadTick_9 = (ImageView) findViewById(R.id.imageView_uploadtick9);
        imageViewUploadTick_1 = (ImageView) findViewById(R.id.imageView_uploadtick1);
        imageViewUploadTick_2 = (ImageView) findViewById(R.id.imageView_uploadtick2);
        imageViewUploadTick_3 = (ImageView) findViewById(R.id.imageView_uploadtick3);
        imageViewUploadTick_4 = (ImageView) findViewById(R.id.imageView_uploadtick4);
        imageViewUploadTick_5 = (ImageView) findViewById(R.id.imageView_uploadtick5);
        imageViewUploadTick_6 = (ImageView) findViewById(R.id.imageView_uploadtick6);
        imageViewUploadTick_7 = (ImageView) findViewById(R.id.imageView_uploadtick7);
        imageViewUploadTick_8 = (ImageView) findViewById(R.id.imageView_uploadtick8);

        textView1 = (TextView) findViewById(R.id.textview1_font_pu);
        textView1.setTypeface(typefaceFontBold);
        textView2 = (TextView) findViewById(R.id.textview2_font_pu);
        textView2.setTypeface(typefaceFont);
        textViewKycProfilePhoto = (TextView) findViewById(R.id.textview_kyc_profilephoto);

        /**--------------------------------KYC - PROFILE PHOTO-----------------------------------**/
        imageViewKycProfilePhoto = (ImageView) findViewById(R.id.imageView_kyc_profilephoto);
        imageViewKycProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                documentType = "9_SD_PhotoDoc";
                documentTypeNo = "9";
                imageViewUploadTick_9.setVisibility(View.GONE);
                buttonKycProfilePhoto.setText("Upload");
                selectImage();
            }
        });
        buttonKycProfilePhoto = (Button) findViewById(R.id.button_kyc_profilephoto);
        buttonKycProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    mDialog = new ProgressDialog(UploadActivityBorrower.this);
                    mDialog.setMessage("UPLOADING FILE");
                    mDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "9_SD_PhotoDoc", "9");
                        }
                    }).start();
                } else {
                    Toast.makeText(UploadActivityBorrower.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**----------------------------END OF KYC - PROFILE PHOTO--------------------------------**/


        /**--------------------------------KYC - PHOTO ID----------------------------------------**/
        textViewKycPhotoId = (TextView) findViewById(R.id.textview_kyc_photoId);
        imageViewKycPhotoId = (ImageView) findViewById(R.id.imageView_kyc_photoId);

        imageViewKycPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_1.setVisibility(View.GONE);
                documentType = "1_SD_PhotoDoc";
                documentTypeNo = "1";
                galleryDocIntent();
                buttonKycPhotoId.setText("Upload");
            }
        });
        buttonKycPhotoId = (Button) findViewById(R.id.button_kyc_photoID);
        buttonKycPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    mDialog = new ProgressDialog(UploadActivityBorrower.this);
                    mDialog.setMessage("UPLOADING FILE");
                    mDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "1_SD_PhotoDoc", "1");
                        }
                    }).start();
                } else {
                    Toast.makeText(UploadActivityBorrower.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-------------------------------END OF KYC - PHOTO ID----------------------------------**/

        /**--------------------------------KYC - ADDRESS PROOF-----------------------------------**/
        imageViewKycAddressProof = (ImageView) findViewById(R.id.imageview_kyc_addressproof);
        imageViewKycAddressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_2.setVisibility(View.GONE);
                documentType = "2_SD_PhotoDoc";
                documentTypeNo = "2";
                buttonKycAddressProof.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonKycAddressProof = (Button) findViewById(R.id.button_kyc_addressproof);
        buttonKycAddressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    mDialog = new ProgressDialog(UploadActivityBorrower.this);
                    mDialog.setMessage("UPLOADING FILE");
                    mDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "2_SD_PhotoDoc", "2");
                        }
                    }).start();
                } else {
                    Toast.makeText(UploadActivityBorrower.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-------------------------------END OF KYC - ADDRESS PROOF-----------------------------**/

        /**--------------------------------KYC - SIGNATURE PROOF---------------------------------**/
        imageViewKycSignatureProof = (ImageView) findViewById(R.id.imageview_kyc_signatureproof);
        imageViewKycSignatureProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_3.setVisibility(View.GONE);
                documentType = "3_SD_PhotoDoc";
                documentTypeNo = "3";
                buttonKycSignatureProof.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonKycSignatureProof = (Button) findViewById(R.id.button_kyc_signatureproof);
        buttonKycSignatureProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    mDialog = new ProgressDialog(UploadActivityBorrower.this);
                    mDialog.setMessage("UPLOADING FILE");
                    mDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "3_SD_PhotoDoc", "3");
                        }
                    }).start();
                } else {
                    Toast.makeText(UploadActivityBorrower.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-------------------------------END OF KYC - SIGNATURE PROOF---------------------------**/

        /**-------------------------------FINANCE - INCOME PROOF---------------------------------**/
        imageViewFinanceIncomeProof = (ImageView) findViewById(R.id.imageview_finance_incomeproof);
        imageViewFinanceIncomeProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_4.setVisibility(View.GONE);
                documentType = "4_SD_PhotoDoc";
                documentTypeNo = "4";
                buttonFinanceIncomeProof.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonFinanceIncomeProof = (Button) findViewById(R.id.button_finance_incomeproof);
        buttonFinanceIncomeProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    mDialog = new ProgressDialog(UploadActivityBorrower.this);
                    mDialog.setMessage("UPLOADING FILE");
                    mDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "4_SD_PhotoDoc", "4");
                        }
                    }).start();
                } else {
                    Toast.makeText(UploadActivityBorrower.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**------------------------------END OF FINANCE - INCOME PROOF---------------------------**/

        /**-------------------------------FINANCE - BANK STATEMENT-------------------------------**/
        imageViewFinanceBankStatement = (ImageView) findViewById(R.id.imageview_finance_bankstatement);
        imageViewFinanceBankStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_5.setVisibility(View.GONE);
                documentType = "5_SD_PhotoDoc";
                documentTypeNo = "5";
                buttonFinanceBankStatement.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonFinanceBankStatement = (Button) findViewById(R.id.button_finance_bankstatement);
        buttonFinanceBankStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    mDialog = new ProgressDialog(UploadActivityBorrower.this);
                    mDialog.setMessage("UPLOADING FILE");
                    mDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "5_SD_PhotoDoc", "5");
                        }
                    }).start();
                } else {
                    Toast.makeText(UploadActivityBorrower.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-----------------------------END OF FINANCE - BANK STATEMENT--------------------------**/

        /**-----------------------------EDUCATION - DEGREE MARKSHEETS----------------------------**/
        imageViewEducationDegreeMarksheets = (ImageView) findViewById(R.id.imageview_education_degreemarksheet);
        imageViewEducationDegreeMarksheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_6.setVisibility(View.GONE);
                documentType = "6_SD_PhotoDoc";
                documentTypeNo = "6";
                buttonEducationDegreeMarksheets.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonEducationDegreeMarksheets = (Button) findViewById(R.id.button_education_degreemarksheet);
        buttonEducationDegreeMarksheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    mDialog = new ProgressDialog(UploadActivityBorrower.this);
                    mDialog.setMessage("UPLOADING FILE");
                    mDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "6_SD_PhotoDoc", "6");
                        }
                    }).start();
                } else {
                    Toast.makeText(UploadActivityBorrower.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**---------------------------END OF EDUCATION - DEGREE MARKSHEETS-----------------------**/

        /**-----------------------------EDUCATION - DEGREE CERTIFICATE---------------------------**/
        imageViewEducationDegreeCertificate = (ImageView) findViewById(R.id.imageview_education_degreecertificate);
        imageViewEducationDegreeCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_7.setVisibility(View.GONE);
                documentType = "7_SD_PhotoDoc";
                documentTypeNo = "7";
                galleryDocIntent();
                buttonEducationDegreeCertificate.setText("Upload");
            }
        });
        buttonEducationDegreeCertificate = (Button) findViewById(R.id.button_education_degreecertificate);
        buttonEducationDegreeCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    mDialog = new ProgressDialog(UploadActivityBorrower.this);
                    mDialog.setMessage("UPLOADING FILE");
                    mDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "7_SD_PhotoDoc", "7");
                        }
                    }).start();
                } else {
                    Toast.makeText(UploadActivityBorrower.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**---------------------------END OF EDUCATION - DEGREE CERTIFICATE----------------------**/

        /**--------------------------------------OTHER DOCUMENT----------------------------------**/
        imageViewOtherDocument = (ImageView) findViewById(R.id.imageview_otherdocument);
        imageViewOtherDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_8.setVisibility(View.GONE);
                documentType = "8_SD_PhotoDoc";
                documentTypeNo = "8";
                buttonOtherDocument.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonOtherDocument = (Button) findViewById(R.id.button_otherdocument);
        buttonOtherDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    mDialog = new ProgressDialog(UploadActivityBorrower.this);
                    mDialog.setMessage("UPLOADING FILE");
                    mDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "8_SD_PhotoDoc", "8");
                        }
                    }).start();
                } else {
                    Toast.makeText(UploadActivityBorrower.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**----------------------------------END OF OTHER DOCUMENT-------------------------------**/

        imageViewECKyc = (ImageView) findViewById(R.id.icon_expand_collapse_kyc);
        linearLayoutKyc = (LinearLayout) findViewById(R.id.linearlayout_kycdocument);
        linearLayoutKycHide = (LinearLayout) findViewById(R.id.linearlayout_kycdocumenthide);

        imageViewECFinancial = (ImageView) findViewById(R.id.icon_expand_collapse_financial);
        linearLayoutFinancial = (LinearLayout) findViewById(R.id.linearlayout_financialdocument);
        linearLayoutFinancialHide = (LinearLayout) findViewById(R.id.linearlayout_financialhide);

        imageViewECEducation = (ImageView) findViewById(R.id.icon_expand_collapse_education);
        linearLayoutEducation = (LinearLayout) findViewById(R.id.linearlayout_educationdocument);
        linearLayoutEducationHide = (LinearLayout) findViewById(R.id.linearlayout_educationhide);

        imageViewECOther = (ImageView) findViewById(R.id.icon_expand_collapse_other);
        linearLayoutOther = (LinearLayout) findViewById(R.id.linearlayout_otherdocument);
        linearLayoutOtherHide = (LinearLayout) findViewById(R.id.linearlayout_othehide);

        linearLayoutKyc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kyc) {
                    a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidedown_top);
                    a.reset();
                    linearLayoutKycHide.setVisibility(View.VISIBLE);
                    imageViewECKyc.setImageDrawable(getResources().getDrawable(R.drawable.icon_collapse));
                    linearLayoutKycHide.clearAnimation();
                    linearLayoutKycHide.setAnimation(a);
                    kyc = false;
                } else {
                    b = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidein_top);
                    b.reset();
                    imageViewECKyc.setImageDrawable(getResources().getDrawable(R.drawable.icon_expand));
                    linearLayoutKycHide.clearAnimation();
                    linearLayoutKycHide.setAnimation(b);
                    linearLayoutKycHide.setVisibility(View.GONE);
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            // Do something after 5s = 5000ms
//                            linearLayoutKycHide.setVisibility(View.GONE);
//                        }
//                    }, 500);

                    kyc = true;
                }
            }
        });

        linearLayoutFinancial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (financial) {
                    a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidedown_top);
                    a.reset();
                    linearLayoutFinancialHide.setVisibility(View.VISIBLE);
                    imageViewECFinancial.setImageDrawable(getResources().getDrawable(R.drawable.icon_collapse));
                    linearLayoutFinancialHide.clearAnimation();
                    linearLayoutFinancialHide.setAnimation(a);
                    financial = false;
                } else {
                    b = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidein_top);
                    b.reset();
                    imageViewECFinancial.setImageDrawable(getResources().getDrawable(R.drawable.icon_expand));
                    linearLayoutFinancialHide.clearAnimation();
                    linearLayoutFinancialHide.setAnimation(b);
                    linearLayoutFinancialHide.setVisibility(View.GONE);
                    financial = true;
                }
            }
        });

        linearLayoutEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (education) {
                    a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidedown_top);
                    a.reset();
                    linearLayoutEducationHide.setVisibility(View.VISIBLE);
                    imageViewECEducation.setImageDrawable(getResources().getDrawable(R.drawable.icon_collapse));
                    linearLayoutEducationHide.clearAnimation();
                    linearLayoutEducationHide.setAnimation(a);
                    education = false;
                } else {
                    b = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidein_top);
                    b.reset();
                    imageViewECEducation.setImageDrawable(getResources().getDrawable(R.drawable.icon_expand));
                    linearLayoutEducationHide.clearAnimation();
                    linearLayoutEducationHide.setAnimation(b);
                    linearLayoutEducationHide.setVisibility(View.GONE);
                    education = true;
                }
            }
        });

        linearLayoutOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (other) {
                    a = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidedown_top);
                    a.reset();
                    linearLayoutOtherHide.setVisibility(View.VISIBLE);
                    imageViewECOther.setImageDrawable(getResources().getDrawable(R.drawable.icon_collapse));
                    linearLayoutOtherHide.clearAnimation();
                    linearLayoutOtherHide.setAnimation(a);
                    other = false;
                } else {
                    b = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slidein_top);
                    b.reset();
                    imageViewECOther.setImageDrawable(getResources().getDrawable(R.drawable.icon_expand));
                    linearLayoutOtherHide.clearAnimation();
                    linearLayoutOtherHide.setAnimation(b);
                    linearLayoutOtherHide.setVisibility(View.GONE);
                    other = true;
                }
            }
        });

        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "document/getapplicantDocumentDetails";
            Map<String, String> params = new HashMap<String, String>();
            VolleyCall volleyCall = new VolleyCall();
            params.put("studentId", userID);
            volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "getDocuments", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//

    }//----------------------------------------END OF ONCREATE------------------------------------//

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
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadActivityBorrower.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(UploadActivityBorrower.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
            else if (requestCode == SELECT_DOC) {
                Bitmap bm = null;
                try {
                    bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri selectedImage = data.getData();
                uploadFilePath = PathFile.getPath(this, selectedImage);
                Log.e("TAG", "onActivityResult: DOC PATH " + uploadFilePath);
//                imageViewKycPhotoId.setImageDrawable(getResources().getDrawable(R.drawable.pdf_image));
                if (documentTypeNo.equalsIgnoreCase("1")) {
                    buttonKycPhotoId.setVisibility(View.VISIBLE);
                    imageViewKycPhotoId.setImageBitmap(bm);
                } else if (documentTypeNo.equalsIgnoreCase("2")) {
                    buttonKycAddressProof.setVisibility(View.VISIBLE);
                    imageViewKycAddressProof.setImageBitmap(bm);
                } else if (documentTypeNo.equalsIgnoreCase("3")) {
                    buttonKycSignatureProof.setVisibility(View.VISIBLE);
                    imageViewKycSignatureProof.setImageBitmap(bm);
                } else if (documentTypeNo.equalsIgnoreCase("4")) {
                    buttonFinanceIncomeProof.setVisibility(View.VISIBLE);
                    imageViewFinanceIncomeProof.setImageBitmap(bm);
                } else if (documentTypeNo.equalsIgnoreCase("5")) {
                    buttonFinanceBankStatement.setVisibility(View.VISIBLE);
                    imageViewFinanceBankStatement.setImageBitmap(bm);
                } else if (documentTypeNo.equalsIgnoreCase("6")) {
                    buttonEducationDegreeMarksheets.setVisibility(View.VISIBLE);
                    imageViewEducationDegreeMarksheets.setImageBitmap(bm);
                } else if (documentTypeNo.equalsIgnoreCase("7")) {
                    buttonEducationDegreeCertificate.setVisibility(View.VISIBLE);
                    imageViewEducationDegreeCertificate.setImageBitmap(bm);
                } else if (documentTypeNo.equalsIgnoreCase("8")) {
                    buttonOtherDocument.setVisibility(View.VISIBLE);
                    imageViewOtherDocument.setImageBitmap(bm);
                }
            }
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

        textViewKycProfilePhoto.setVisibility(View.GONE);
        imageViewKycProfilePhoto.setImageBitmap(thumbnail);
        buttonKycProfilePhoto.setVisibility(View.VISIBLE);
//        imageViewUploadTick.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                Uri selectedFileUri = data.getData();
                uploadFilePath = PathFile.getPath(this, selectedFileUri);
                Log.e("TAG", "onSelectFromGalleryResult: " + uploadFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        textViewKycProfilePhoto.setVisibility(View.GONE);
        imageViewKycProfilePhoto.setImageBitmap(bm);
        buttonKycProfilePhoto.setVisibility(View.VISIBLE);
    }

    private void galleryDocIntent() {
        Intent intent = new Intent();
        intent.setType("*/*");  // for all types of file
//        intent.setType("application/pdf"); // for pdf
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_DOC);
    }

    //android upload file to server
    public int uploadFile(final String selectedFilePath, String doctype, String doctypeno) {

        int serverResponseCode = 0;
        documentType = doctype;
        documentTypeNo = doctypeno;
        Log.e(MainApplication.TAG, "documentType: " + documentType + "documentTypeNo: " + documentTypeNo);
        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {
            //dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("TAG", "run: " + "Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(urlup);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("document", selectedFilePath);
//                connection.setRequestProperty("user_id", user_id);
                Log.e("TAG", "Server property" + connection.getRequestMethod() + ":property " + connection.getRequestProperties());


                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"myfile\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0) {
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    Log.e("TAG", " here: \n\n" + buffer + "\n" + bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"id\";id=" + userID + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(userID);
                dataOutputStream.writeBytes(lineEnd);


                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"documentType\";documentType=" + documentType + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(documentType);
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"documentTypeNo\";documentTypeNo=" + documentTypeNo + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(documentTypeNo);
                dataOutputStream.writeBytes(lineEnd);


                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                Log.e("TAG", " here:server response serverResponseCode\n\n" + serverResponseCode);
                String serverResponseMessage = connection.getResponseMessage();
                Log.e("TAG", " here: server message serverResponseMessage \n\n" + serverResponseMessage.toString() + "\n" + bufferSize);
                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                String output = "";
                sb = new StringBuffer();

                while ((output = br.readLine()) != null) {
                    sb.append(output);
                    Log.e("TAG", "uploadFile: " + br);
                    Log.e("TAG", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);
                }
                Log.e("TAG", "uploadFile: " + sb.toString());
//                [{"code":1,"file_name":"284f0b39af461ad8ae3ee17ac20ee5f4.pdf","message":"Document uploaded successfully"}]

//                try {
//                    JSONArray mJson= new JSONArray(sb.toString());
//                    final JSONObject mData= mJson.getJSONObject(0);
//                    final int code=mData.optInt("code");
//                    Log.e("TAG", "uploadFile: code "+code);
//                    if(code == 1)
//                    {
//                        runOnUiThread(new Runnable()
//                        {
//                            @Override
//                            public void run() {
//                                mDialog.dismiss();
//                                Log.e("TAG", "uploadFile: code 1 "+code);
////                                    Toast.makeText(UploadActivityBorrower.this, mData.getString("message").toString(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(UploadActivityBorrower.this,"File Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                                imageViewUploadTick.setVisibility(View.VISIBLE);
//                            }
//                        });
//
////                        finish();
//
//                    }else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    mDialog.dismiss();
//                                    Log.e("TAG", "uploadFile: code 2 "+code);
//                                    Toast.makeText(UploadActivityBorrower.this, mData.getString("message").toString(), Toast.LENGTH_SHORT).show();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
////                        finish();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                try {
                    JSONObject mJson = new JSONObject(sb.toString());
                    final String mData = mJson.getString("status");
                    final String mData1 = mJson.getString("message");

                    Log.e("TAG", "uploadFile: code " + mData);
                    if (mData.equalsIgnoreCase("1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                Log.e("TAG", "uploadFile: code 1 " + mData);
                                Toast.makeText(UploadActivityBorrower.this, mData1, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(UploadActivityBorrower.this,"File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                if (documentTypeNo.equalsIgnoreCase("9")) {
                                    imageViewUploadTick_9.setVisibility(View.VISIBLE);
                                    buttonKycProfilePhoto.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("1")) {
                                    imageViewUploadTick_1.setVisibility(View.VISIBLE);
                                    buttonKycPhotoId.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("2")) {
                                    imageViewUploadTick_2.setVisibility(View.VISIBLE);
                                    buttonKycAddressProof.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("3")) {
                                    imageViewUploadTick_3.setVisibility(View.VISIBLE);
                                    buttonKycSignatureProof.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("4")) {
                                    imageViewUploadTick_4.setVisibility(View.VISIBLE);
                                    buttonFinanceIncomeProof.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("5")) {
                                    imageViewUploadTick_5.setVisibility(View.VISIBLE);
                                    buttonFinanceBankStatement.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("6")) {
                                    imageViewUploadTick_6.setVisibility(View.VISIBLE);
                                    buttonEducationDegreeMarksheets.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("7")) {
                                    imageViewUploadTick_7.setVisibility(View.VISIBLE);
                                    buttonEducationDegreeCertificate.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("8")) {
                                    imageViewUploadTick_8.setVisibility(View.VISIBLE);
                                    buttonOtherDocument.setText("Uploaded");
                                }
                                uploadFilePath = "";
                            }
                        });

//                        finish();

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                Log.e("TAG", "uploadFile: code 2 " + mData);
                                Toast.makeText(UploadActivityBorrower.this, mData1, Toast.LENGTH_SHORT).show();
                            }
                        });
//                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("TAG", " here: \n\n" + fileName);
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UploadActivityBorrower.this, "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(UploadActivityBorrower.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(UploadActivityBorrower.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
//            dialog.dismiss();
            return serverResponseCode;
        }

    }//---------------------------------------END OF UPLOAD FILE----------------------------------//

    /**---------------------------------RESPONSE OF API CALL-------------------------------------**/

    public void getDocuments(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "getDocuments" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(UploadActivityBorrower.this, message, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = jsonData.getJSONObject("result");

                JSONArray jsonArray = jsonObject.getJSONArray("KycDocument");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");
                    Log.e(MainApplication.TAG, "TYPENO: " + s);
                    Log.e(MainApplication.TAG, "image: " + image);
                    if (s.equalsIgnoreCase("1")) {
                        buttonKycPhotoId.setVisibility(View.VISIBLE);
                        buttonKycPhotoId.setText("Uploaded");
                        imageViewUploadTick_1.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://139.59.32.234/eduvanz/" + image).into(imageViewKycPhotoId);
                    } else if (s.equalsIgnoreCase("2")) {
                        buttonKycAddressProof.setVisibility(View.VISIBLE);
                        buttonKycAddressProof.setText("Uploaded");
                        imageViewUploadTick_2.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://139.59.32.234/eduvanz/" + image).into(imageViewKycAddressProof);
                    } else if (s.equalsIgnoreCase("3")) {
                        buttonKycSignatureProof.setVisibility(View.VISIBLE);
                        buttonKycSignatureProof.setText("Uploaded");
                        imageViewUploadTick_3.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://139.59.32.234/eduvanz/" + image).into(imageViewKycSignatureProof);
                    } else if (s.equalsIgnoreCase("9")) {
                        buttonKycProfilePhoto.setVisibility(View.VISIBLE);
                        buttonKycProfilePhoto.setText("Uploaded");
                        imageViewUploadTick_9.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://139.59.32.234/eduvanz/" + image).into(imageViewKycProfilePhoto);
                    }
                }

                JSONArray jsonArray1 = jsonObject.getJSONArray("Financial");
                Log.e(MainApplication.TAG, "getDocuments: " + jsonArray1);
                for (int i = 0; i < jsonArray1.length(); i++) {
                    Log.e(MainApplication.TAG, "FOR LOOP: " + i);
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");

                    if (s.equalsIgnoreCase("4")) {
                        Log.e(MainApplication.TAG, "IF LOOP : 4");
                        buttonFinanceIncomeProof.setVisibility(View.VISIBLE);
                        buttonFinanceIncomeProof.setText("Uploaded");
                        imageViewUploadTick_4.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://139.59.32.234/eduvanz/" + image).into(imageViewFinanceIncomeProof);
                    } else if (s.equalsIgnoreCase("5")) {
                        buttonFinanceBankStatement.setVisibility(View.VISIBLE);
                        buttonFinanceBankStatement.setText("Uploaded");
                        imageViewUploadTick_5.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://139.59.32.234/eduvanz/" + image).into(imageViewFinanceBankStatement);
                    }
                }

                JSONArray jsonArray2 = jsonObject.getJSONArray("Education");
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject jsonObject1 = jsonArray2.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");

                    if (s.equalsIgnoreCase("6")) {
                        buttonEducationDegreeMarksheets.setVisibility(View.VISIBLE);
                        buttonEducationDegreeMarksheets.setText("Uploaded");
                        imageViewUploadTick_6.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://139.59.32.234/eduvanz/" + image).into(imageViewEducationDegreeMarksheets);
                    } else if (s.equalsIgnoreCase("7")) {
                        buttonEducationDegreeCertificate.setVisibility(View.VISIBLE);
                        buttonEducationDegreeCertificate.setText("Uploaded");
                        imageViewUploadTick_7.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://139.59.32.234/eduvanz/" + image).into(imageViewEducationDegreeCertificate);
                    }
                }

                JSONArray jsonArray3 = jsonObject.getJSONArray("Other");
                for (int i = 0; i < jsonArray3.length(); i++) {
                    JSONObject jsonObject1 = jsonArray3.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");

                    if (s.equalsIgnoreCase("8")) {
                        buttonOtherDocument.setVisibility(View.VISIBLE);
                        buttonOtherDocument.setText("Uploaded");
                        imageViewUploadTick_8.setVisibility(View.VISIBLE);
                        Picasso.with(getApplicationContext()).load("http://139.59.32.234/eduvanz/" + image).into(imageViewOtherDocument);
                    }
                }

            } else {
                Toast.makeText(UploadActivityBorrower.this, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
