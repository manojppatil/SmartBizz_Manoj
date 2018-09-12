package com.eduvanzapplication.newUI.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.uploaddocs.PathFile;
import com.eduvanzapplication.uploaddocs.Utility;
import com.eduvanzapplication.newUI.VolleyCallNew;
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
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */


public class LoanApplicationFragment_3 extends Fragment {

    static Context context;
    public static Fragment mFragment;
    static Typeface typefaceFont, typefaceFontBold, typeface;
    static TextView textView1, textView2, textView3, textViewArrowDown, textViewArrowDownCo;
    static View view;
    static ImageView imageViewUploadTick_1, imageViewUploadTick_2, imageViewUploadTick_3,
            imageViewUploadTick_4, imageViewUploadTick_5, imageViewUploadTick_6, imageViewUploadTick_7,
            imageViewUploadTick_8, imageViewUploadTick_9;
    static Button buttonKycProfilePhoto, buttonKycPhotoId, buttonKycAddressProof, buttonKycSignatureProof,
            buttonFinanceIncomeProof, buttonFinanceBankStatement, buttonEducationDegreeMarksheets, buttonEducationDegreeCertificate,
            buttonOtherDocument;
    static ImageView imageViewKycProfilePhoto, imageViewKycPhotoId, imageViewKycAddressProof, imageViewKycSignatureProof,
            imageViewFinanceIncomeProof, imageViewFinanceBankStatement, imageViewEducationDegreeMarksheets, imageViewEducationDegreeCertificate,
            imageViewOtherDocument;
    static ImageView imageViewUploadTick_9_co, imageViewUploadTick_1_co, imageViewUploadTick_2_co, imageViewUploadTick_3_co,
            imageViewUploadTick_4_co, imageViewUploadTick_5_co, imageViewUploadTick_8_co;
    static Button buttonKycProfilePhoto_co, buttonKycPhotoId_co, buttonKycAddressProof_co, buttonKycSignatureProof_co,
            buttonFinanceIncomeProof_co, buttonFinanceBankStatement_co, buttonOtherDocument_co;
    static ProgressBar progressBar;
    public static String coBorrowerID = "", baseUrl="";
    public Boolean kyc = true, financial = true, education = true, other = true;
    public String documentType = "", documentTypeNo = "", userID = "";
    static int doc_finish, signedAppStatus;
    static String lafDownloadPath = "";
    public int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_DOC = 2;
    public String userChoosenTask;
    Button buttonNext, buttonPrevious;
    MainApplication mainApplication;
    LinearLayout linearLayoutBorrower, linearLayoutCoBorrower;
    RelativeLayout relativeLayoutBorrower, relativeLayoutCoBorrower;
    int borrowerVisiblity = 1, coborrowerVisiblity = 1;
    static FragmentTransaction transaction;
    static ImageView imageViewKycProfilePhoto_co, imageViewKycPhotoId_co, imageViewKycAddressProof_co, imageViewKycSignatureProof_co,
            imageViewFinanceIncomeProof_co, imageViewFinanceBankStatement_co, imageViewOtherDocument_co;
    String uploadFilePath = "";

    static ImageView imageViewProfilePicSelect;
    StringBuffer sb;

    public LoanApplicationFragment_3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loanapplication_3, container, false);
        context = getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mainApplication = new MainApplication();

        mFragment = new LoanApplicationFragment_3();

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id", "null");

        MainApplication.currrentFrag = 3;

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");
        typeface = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

        transaction = getFragmentManager().beginTransaction();

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_docupload);

        textView1 = (TextView) view.findViewById(R.id.textView1_l3);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) view.findViewById(R.id.textView2_l3);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) view.findViewById(R.id.textView3_l3);
        mainApplication.applyTypefaceBold(textView3, context);

        textViewArrowDown = (TextView) view.findViewById(R.id.texView_borrower_arrowdown);
        textViewArrowDown.setTypeface(typeface);

        textViewArrowDownCo = (TextView) view.findViewById(R.id.texView_coborrower_arrowdown);
        textViewArrowDownCo.setTypeface(typeface);

        buttonNext = (Button) view.findViewById(R.id.button_next_loanappfragment3);
        buttonNext.setTypeface(typefaceFontBold);

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_loanappfragment3);
        buttonPrevious.setTypeface(typefaceFontBold);

        linearLayoutBorrower = (LinearLayout) view.findViewById(R.id.linearLayout_docupload_borrower);
        relativeLayoutBorrower = (RelativeLayout) view.findViewById(R.id.relativeLayout_borrower);

        linearLayoutCoBorrower = (LinearLayout) view.findViewById(R.id.linearLayout_docupload_coborrower);
        relativeLayoutCoBorrower = (RelativeLayout) view.findViewById(R.id.relativeLayout_coborrower);

        imageViewUploadTick_9 = (ImageView) view.findViewById(R.id.imageView_uploadtick9);
        imageViewUploadTick_1 = (ImageView) view.findViewById(R.id.imageView_uploadtick1);
        imageViewUploadTick_2 = (ImageView) view.findViewById(R.id.imageView_uploadtick2);
        imageViewUploadTick_3 = (ImageView) view.findViewById(R.id.imageView_uploadtick3);
        imageViewUploadTick_4 = (ImageView) view.findViewById(R.id.imageView_uploadtick4);
        imageViewUploadTick_5 = (ImageView) view.findViewById(R.id.imageView_uploadtick5);
        imageViewUploadTick_6 = (ImageView) view.findViewById(R.id.imageView_uploadtick6);
        imageViewUploadTick_7 = (ImageView) view.findViewById(R.id.imageView_uploadtick7);
        imageViewUploadTick_8 = (ImageView) view.findViewById(R.id.imageView_uploadtick8);

        imageViewUploadTick_9_co = (ImageView) view.findViewById(R.id.imageView_uploadtick9_co);
        imageViewUploadTick_1_co = (ImageView) view.findViewById(R.id.imageView_uploadtick1_co);
        imageViewUploadTick_2_co = (ImageView) view.findViewById(R.id.imageView_uploadtick2_co);
        imageViewUploadTick_3_co = (ImageView) view.findViewById(R.id.imageView_uploadtick3_co);
        imageViewUploadTick_4_co = (ImageView) view.findViewById(R.id.imageView_uploadtick4_co);
        imageViewUploadTick_5_co = (ImageView) view.findViewById(R.id.imageView_uploadtick5_co);
        imageViewUploadTick_8_co = (ImageView) view.findViewById(R.id.imageView_uploadtick8_co);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /** API CALL **/
                try {
                    String url = MainApplication.mainUrl + "laf/getStudentLaf";
                    Map<String, String> params = new HashMap<String, String>();
                    if(!Globle.isNetworkAvailable(context))
                    {
                        Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();
                        params.put("studentId", userID);
                        volleyCall.sendRequest(context, url, null, mFragment, "getStudentLaf", params);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /**API CALL*/

            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanApplicationFragment_2 loanApplicationFragment_2 = new LoanApplicationFragment_2();
                transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_2).commit();
            }
        });

        relativeLayoutBorrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (borrowerVisiblity == 0) {
                    linearLayoutBorrower.setVisibility(View.VISIBLE);
                    borrowerVisiblity = 1;
                    textViewArrowDown.setText(getResources().getString(R.string.up));
                    textViewArrowDown.setTypeface(typeface);
                } else if (borrowerVisiblity == 1) {
                    linearLayoutBorrower.setVisibility(View.GONE);
                    borrowerVisiblity = 0;
                    textViewArrowDown.setText(getResources().getString(R.string.down));
                    textViewArrowDown.setTypeface(typeface);
                }

            }
        });

        relativeLayoutCoBorrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (coborrowerVisiblity == 0) {
                    linearLayoutCoBorrower.setVisibility(View.VISIBLE);
                    coborrowerVisiblity = 1;
                    textViewArrowDownCo.setText(getResources().getString(R.string.up));
                    textViewArrowDownCo.setTypeface(typeface);
                } else if (coborrowerVisiblity == 1) {
                    linearLayoutCoBorrower.setVisibility(View.GONE);
                    coborrowerVisiblity = 0;
                    textViewArrowDownCo.setText(getResources().getString(R.string.down));
                    textViewArrowDownCo.setTypeface(typeface);
                }

            }
        });

        /**--------------------------------KYC - PROFILE PHOTO-----------------------------------**/
        imageViewKycProfilePhoto = (ImageView) view.findViewById(R.id.imageView_kyc_profilephoto);
        imageViewKycProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewProfilePicSelect = imageViewKycProfilePhoto;
                documentType = "9_SD_PhotoDoc";
                documentTypeNo = "9";
                imageViewUploadTick_9.setVisibility(View.GONE);
                buttonKycProfilePhoto.setText("Upload");
                selectImage();
            }
        });
        buttonKycProfilePhoto = (Button) view.findViewById(R.id.button_kyc_profilephoto);
        buttonKycProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "9_SD_PhotoDoc", "9", 0);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**----------------------------END OF KYC - PROFILE PHOTO--------------------------------**/


        /**--------------------------------KYC - PHOTO ID----------------------------------------**/
        imageViewKycPhotoId = (ImageView) view.findViewById(R.id.imageView_kyc_photoId);

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
        buttonKycPhotoId = (Button) view.findViewById(R.id.button_kyc_photoID);
        buttonKycPhotoId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
//                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "1_SD_PhotoDoc", "1", 0);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-------------------------------END OF KYC - PHOTO ID----------------------------------**/

        /**--------------------------------KYC - ADDRESS PROOF-----------------------------------**/
        imageViewKycAddressProof = (ImageView) view.findViewById(R.id.imageview_kyc_addressproof);
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
        buttonKycAddressProof = (Button) view.findViewById(R.id.button_kyc_addressproof);
        buttonKycAddressProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "2_SD_PhotoDoc", "2", 0);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-------------------------------END OF KYC - ADDRESS PROOF-----------------------------**/

        /**--------------------------------KYC - SIGNATURE PROOF---------------------------------**/
        imageViewKycSignatureProof = (ImageView) view.findViewById(R.id.imageview_kyc_signatureproof);
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
        buttonKycSignatureProof = (Button) view.findViewById(R.id.button_kyc_signatureproof);
        buttonKycSignatureProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "3_SD_PhotoDoc", "3", 0);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-------------------------------END OF KYC - SIGNATURE PROOF---------------------------**/

        /**-------------------------------FINANCE - INCOME PROOF---------------------------------**/
        imageViewFinanceIncomeProof = (ImageView) view.findViewById(R.id.imageview_finance_incomeproof);
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
        buttonFinanceIncomeProof = (Button) view.findViewById(R.id.button_finance_incomeproof);
        buttonFinanceIncomeProof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "4_SD_PhotoDoc", "4", 0);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**------------------------------END OF FINANCE - INCOME PROOF---------------------------**/

        /**-------------------------------FINANCE - BANK STATEMENT-------------------------------**/
        imageViewFinanceBankStatement = (ImageView) view.findViewById(R.id.imageview_finance_bankstatement);
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
        buttonFinanceBankStatement = (Button) view.findViewById(R.id.button_finance_bankstatement);
        buttonFinanceBankStatement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "5_SD_PhotoDoc", "5", 0);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-----------------------------END OF FINANCE - BANK STATEMENT--------------------------**/

        /**-----------------------------EDUCATION - DEGREE MARKSHEETS----------------------------**/
        imageViewEducationDegreeMarksheets = (ImageView) view.findViewById(R.id.imageview_education_degreemarksheet);
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
        buttonEducationDegreeMarksheets = (Button) view.findViewById(R.id.button_education_degreemarksheet);
        buttonEducationDegreeMarksheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "6_SD_PhotoDoc", "6", 0);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**---------------------------END OF EDUCATION - DEGREE MARKSHEETS-----------------------**/

        /**-----------------------------EDUCATION - DEGREE CERTIFICATE---------------------------**/
        imageViewEducationDegreeCertificate = (ImageView) view.findViewById(R.id.imageview_education_degreecertificate);
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
        buttonEducationDegreeCertificate = (Button) view.findViewById(R.id.button_education_degreecertificate);
        buttonEducationDegreeCertificate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "7_SD_PhotoDoc", "7", 0);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**---------------------------END OF EDUCATION - DEGREE CERTIFICATE----------------------**/

        /**--------------------------------------OTHER DOCUMENT----------------------------------**/
        imageViewOtherDocument = (ImageView) view.findViewById(R.id.imageview_otherdocument);
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
        buttonOtherDocument = (Button) view.findViewById(R.id.button_otherdocument);
        buttonOtherDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "8_SD_PhotoDoc", "8", 0);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**----------------------------------END OF OTHER DOCUMENT-------------------------------**/

                            /** CO BORROWER DOC UPLOAD */

        /**--------------------------------KYC - PROFILE PHOTO-----------------------------------**/
        imageViewKycProfilePhoto_co = (ImageView) view.findViewById(R.id.imageView_kyc_profilephoto_co);
        imageViewKycProfilePhoto_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewProfilePicSelect = imageViewKycProfilePhoto_co;
                documentType = "9_SD_PhotoDoc";
                documentTypeNo = "9_co";
                imageViewUploadTick_9_co.setVisibility(View.GONE);
                buttonKycProfilePhoto_co.setText("Upload");
                selectImage();
            }
        });
        buttonKycProfilePhoto_co = (Button) view.findViewById(R.id.button_kyc_profilephoto_co);
        buttonKycProfilePhoto_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "9_SD_PhotoDoc", "9_co", 1);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**----------------------------END OF KYC - PROFILE PHOTO--------------------------------**/


        /**--------------------------------KYC - PHOTO ID----------------------------------------**/
        imageViewKycPhotoId_co = (ImageView) view.findViewById(R.id.imageView_kyc_photoId_co);

        imageViewKycPhotoId_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_1_co.setVisibility(View.GONE);
                documentType = "1_SD_PhotoDoc";
                documentTypeNo = "1_co";
                galleryDocIntent();
                buttonKycPhotoId_co.setText("Upload");
            }
        });
        buttonKycPhotoId_co = (Button) view.findViewById(R.id.button_kyc_photoID_co);
        buttonKycPhotoId_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            try {
                                uploadFile(uploadFilePath, "1_SD_PhotoDoc", "1_co", 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-------------------------------END OF KYC - PHOTO ID----------------------------------**/

        /**--------------------------------KYC - ADDRESS PROOF-----------------------------------**/
        imageViewKycAddressProof_co = (ImageView) view.findViewById(R.id.imageview_kyc_addressproof_co);
        imageViewKycAddressProof_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_2_co.setVisibility(View.GONE);
                documentType = "2_SD_PhotoDoc";
                documentTypeNo = "2_co";
                buttonKycAddressProof_co.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonKycAddressProof_co = (Button) view.findViewById(R.id.button_kyc_addressproof_co);
        buttonKycAddressProof_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "2_SD_PhotoDoc", "2_co", 1);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-------------------------------END OF KYC - ADDRESS PROOF-----------------------------**/

        /**--------------------------------KYC - SIGNATURE PROOF---------------------------------**/
        imageViewKycSignatureProof_co = (ImageView) view.findViewById(R.id.imageview_kyc_signatureproof_co);
        imageViewKycSignatureProof_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_3_co.setVisibility(View.GONE);
                documentType = "3_SD_PhotoDoc";
                documentTypeNo = "3_co";
                buttonKycSignatureProof_co.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonKycSignatureProof_co = (Button) view.findViewById(R.id.button_kyc_signatureproof_co);
        buttonKycSignatureProof_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "3_SD_PhotoDoc", "3_co", 1);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-------------------------------END OF KYC - SIGNATURE PROOF---------------------------**/

        /**-------------------------------FINANCE - INCOME PROOF---------------------------------**/
        imageViewFinanceIncomeProof_co = (ImageView) view.findViewById(R.id.imageview_finance_incomeproof_co);
        imageViewFinanceIncomeProof_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_4_co.setVisibility(View.GONE);
                documentType = "4_SD_PhotoDoc";
                documentTypeNo = "4_co";
                buttonFinanceIncomeProof_co.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonFinanceIncomeProof_co = (Button) view.findViewById(R.id.button_finance_incomeproof_co);
        buttonFinanceIncomeProof_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
//                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            try {
                                uploadFile(uploadFilePath, "4_SD_PhotoDoc", "4_co", 1);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**------------------------------END OF FINANCE - INCOME PROOF---------------------------**/

        /**-------------------------------FINANCE - BANK STATEMENT-------------------------------**/
        imageViewFinanceBankStatement_co = (ImageView) view.findViewById(R.id.imageview_finance_bankstatement_co);
        imageViewFinanceBankStatement_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_5_co.setVisibility(View.GONE);
                documentType = "5_SD_PhotoDoc";
                documentTypeNo = "5_co";
                buttonFinanceBankStatement_co.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonFinanceBankStatement_co = (Button) view.findViewById(R.id.button_finance_bankstatement_co);
        buttonFinanceBankStatement_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "5_SD_PhotoDoc", "5_co", 1);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**-----------------------------END OF FINANCE - BANK STATEMENT--------------------------**/

        /**--------------------------------------OTHER DOCUMENT----------------------------------**/
        imageViewOtherDocument_co = (ImageView) view.findViewById(R.id.imageview_otherdocument_co);
        imageViewOtherDocument_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewUploadTick_8_co.setVisibility(View.GONE);
                documentType = "8_SD_PhotoDoc";
                documentTypeNo = "8_co";
                buttonOtherDocument_co.setText("Upload");
                galleryDocIntent();
            }
        });
        buttonOtherDocument_co = (Button) view.findViewById(R.id.button_otherdocument_co);
        buttonOtherDocument_co.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uploadFilePath != null) {
                    // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                            uploadFile(uploadFilePath, "8_SD_PhotoDoc", "8_co", 1);
                        }
                    }).start();
                } else {
                    Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /**----------------------------------END OF OTHER DOCUMENT-------------------------------**/

        /** API CALL **/
        try {
            String url = MainApplication.mainUrl + "document/getapplicantDocumentDetails";
            Map<String, String> params = new HashMap<String, String>();
            if(!Globle.isNetworkAvailable(context))
            {
                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                params.put("studentId", userID);
                volleyCall.sendRequest(context, url, null, mFragment, "getDocumentsBorrower", params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**API CALL*/
        try {
            String url = MainApplication.mainUrl + "document/getCoApplicantDocumentDetails";
            Map<String, String> params = new HashMap<String, String>();
            if(!Globle.isNetworkAvailable(context))
            {
                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                params.put("studentId", userID);
                volleyCall.sendRequest(context, url, null, mFragment, "getCoBorrowerDocuments", params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    private void galleryDocIntent() {
        Intent intent = new Intent();
        intent.setType("*/*");  // for all types of file
//        intent.setType("application/pdf"); // for pdf
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_DOC);
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

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(context);

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

    private Bitmap decodeUri(Uri selectedImage,Context context) throws FileNotFoundException {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(selectedImage), null, o);

        final int REQUIRED_SIZE = 100;

        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) {
                break;
            }
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        return BitmapFactory.decodeStream(
                context.getContentResolver().openInputStream(selectedImage), null, o2);
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
                String FileExtn = null;
                Long FileSize = null;
                try {//mDensity = 440 mHeight = 375 mWidth = 500
                    bm = decodeUri(data.getData(),context);//5383513
//                    bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), data.getData());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri selectedImage = data.getData();
                uploadFilePath = PathFile.getPath(context, selectedImage);

                try {
                    Cursor returnCursor =
                            context.getContentResolver().query(selectedImage, null, null, null, null);
                    int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
                    returnCursor.moveToFirst();

                    FileSize = returnCursor.getLong(sizeIndex);//5383513 //26435143

//                    Long fsize = returnCursor.getLong(sizeIndex);//5383513 //26435143
//                    final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
//                    int digitGroups = (int) (Math.log10(fsize) / Math.log10(1024));
//                    FileSize = new DecimalFormat("#,##0.##").format(fsize / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
                } catch (Exception e) {
                    e.printStackTrace();
                }

                FileExtn = uploadFilePath.substring(uploadFilePath.lastIndexOf(".")+1);// Without dot jpg, png

//                FileSize.substring(0,FileSize.length()-3); //25.21
//                FileSize.substring(FileSize.length()-2,FileSize.length()); //MB

//                if(FileExtn.equals("jpg") || FileExtn.equals("jpeg") || FileExtn.equals("png") || FileExtn.equals("pdf") || FileExtn.equals("bmp") ||
//                        FileExtn.equals("webp") || FileExtn.equals("zip") || FileExtn.equals("rar"))

                if(FileExtn.equals("jpg") || FileExtn.equals("jpeg") || FileExtn.equals("png") || FileExtn.equals("pdf")||
                        FileExtn.equals("bmp") || FileExtn.equals("webp") || FileExtn.equals("zip") || FileExtn.equals("rar")) {

                    if (FileSize < 30000000) {
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
                        } else if (documentTypeNo.equalsIgnoreCase("1_co")) {
                            buttonKycPhotoId_co.setVisibility(View.VISIBLE);
                            imageViewKycPhotoId_co.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("2_co")) {
                            buttonKycAddressProof_co.setVisibility(View.VISIBLE);
                            imageViewKycAddressProof_co.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("3_co")) {
                            buttonKycSignatureProof_co.setVisibility(View.VISIBLE);
                            imageViewKycSignatureProof_co.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("4_co")) {
                            buttonFinanceIncomeProof_co.setVisibility(View.VISIBLE);
                            imageViewFinanceIncomeProof_co.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("5_co")) {
                            buttonFinanceBankStatement_co.setVisibility(View.VISIBLE);
                            imageViewFinanceBankStatement_co.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("8_co")) {
                            buttonOtherDocument_co.setVisibility(View.VISIBLE);
                            imageViewOtherDocument_co.setImageBitmap(bm);
                        }
                    }
                    else{
                        Toast.makeText(context, "File size exceeds limit of 30 MB", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(context, "file is not in supported format", Toast.LENGTH_LONG).show();
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

        if(imageViewProfilePicSelect == imageViewKycProfilePhoto){
            imageViewProfilePicSelect.setImageBitmap(thumbnail);
            buttonKycProfilePhoto.setVisibility(View.VISIBLE);
        }else if(imageViewProfilePicSelect == imageViewKycProfilePhoto_co){
            imageViewProfilePicSelect.setImageBitmap(thumbnail);
            buttonKycProfilePhoto_co.setVisibility(View.VISIBLE);
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
                uploadFilePath = PathFile.getPath(context, selectedFileUri);
                Log.e("TAG", "onSelectFromGalleryResult: " + uploadFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(imageViewProfilePicSelect == imageViewKycProfilePhoto){
            imageViewProfilePicSelect.setImageBitmap(bm);
            buttonKycProfilePhoto.setVisibility(View.VISIBLE);
        }else if(imageViewProfilePicSelect == imageViewKycProfilePhoto_co){
            imageViewProfilePicSelect.setImageBitmap(bm);
            buttonKycProfilePhoto_co.setVisibility(View.VISIBLE);
        }
    }

    public int uploadFile(final String selectedFilePath, String doctype, String doctypeno, int selectUrl) {
        String urlup="";

        Log.e(MainApplication.TAG, "uploadFile++++++: selectUrl"+selectUrl + "doctype  "+ doctype + "  doctypeno "+ doctypeno + " selectedFilePath "+selectedFilePath + " coBorrowerID   " + coBorrowerID);
        if(selectUrl == 0){
             urlup = MainApplication.mainUrl + "document/applicantDocumentUpload";
        }else if(selectUrl == 1){
             urlup = MainApplication.mainUrl + "document/coapplicantDocumentUpload";
        }

        Log.e(MainApplication.TAG, "urlup++++++: "+urlup  );

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
        String[] fileExtn = fileName.split(".");


        if (!selectedFile.isFile()) {
            //dialog.dismiss();
            try {
                progressBar.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            getActivity().runOnUiThread(new Runnable() {
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
                connection.setChunkedStreamingMode(1024);
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


                if(selectUrl == 1){
                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"id\";id=" + coBorrowerID + "" + lineEnd);
                    dataOutputStream.writeBytes(lineEnd);
                    dataOutputStream.writeBytes(coBorrowerID);
                    dataOutputStream.writeBytes(lineEnd);

                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"studentId\";studentId=" + userID + "" + lineEnd);
                    dataOutputStream.writeBytes(lineEnd);
                    dataOutputStream.writeBytes(userID);
                    dataOutputStream.writeBytes(lineEnd);
                }else if(selectUrl == 0){
                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"id\";id=" + userID + "" + lineEnd);
                    dataOutputStream.writeBytes(lineEnd);
                    dataOutputStream.writeBytes(userID);
                    dataOutputStream.writeBytes(lineEnd);
                }


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

                try {
                    JSONObject mJson = new JSONObject(sb.toString());
                    final String mData = mJson.getString("status");
                    final String mData1 = mJson.getString("message");

                    Log.e("TAG", "uploadFile: code " + mData);
                    if (mData.equalsIgnoreCase("1")) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


                                progressBar.setVisibility(View.GONE);
                                Log.e("TAG", "uploadFile: code 1 " + mData);
                                Toast.makeText(context, mData1, Toast.LENGTH_SHORT).show();
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

                                else if (documentTypeNo.equalsIgnoreCase("9_co")) {
                                    imageViewUploadTick_9_co.setVisibility(View.VISIBLE);
                                    buttonKycProfilePhoto_co.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("1_co")) {
                                    imageViewUploadTick_1_co.setVisibility(View.VISIBLE);
                                    buttonKycPhotoId_co.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("2_co")) {
                                    imageViewUploadTick_2_co.setVisibility(View.VISIBLE);
                                    buttonKycAddressProof_co.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("3_co")) {
                                    imageViewUploadTick_3_co.setVisibility(View.VISIBLE);
                                    buttonKycSignatureProof_co.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("4_co")) {
                                    imageViewUploadTick_4_co.setVisibility(View.VISIBLE);
                                    buttonFinanceIncomeProof_co.setText("Uploaded");
                                } else if (documentTypeNo.equalsIgnoreCase("5_co")) {
                                    imageViewUploadTick_5_co.setVisibility(View.VISIBLE);
                                    buttonFinanceBankStatement_co.setText("Uploaded");
                                }  else if (documentTypeNo.equalsIgnoreCase("8_co")) {
                                    imageViewUploadTick_8_co.setVisibility(View.VISIBLE);
                                    buttonOtherDocument_co.setText("Uploaded");
                                }
                                uploadFilePath = "";
                            }
                        });

                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                Log.e("TAG", "uploadFile: code 2 " + mData);
                                Toast.makeText(context, mData1, Toast.LENGTH_SHORT).show();
                            }
                        });
//                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                    getActivity().runOnUiThread(new Runnable() {
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
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(context, "File Not Found", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            dialog.dismiss();
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(context, "File Not Found", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
            return serverResponseCode;
        }

    }


    /**
     * ---------------------------------RESPONSE OF API CALL-------------------------------------
     **/

    public void getBorrowerDocuments(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "getDocuments" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                String baseUrl = jsonObject.getString("baseUrl");

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
                        Picasso.with(context).load(baseUrl + image).into(imageViewKycPhotoId);
                    } else if (s.equalsIgnoreCase("2")) {
                        buttonKycAddressProof.setVisibility(View.VISIBLE);
                        buttonKycAddressProof.setText("Uploaded");
                        imageViewUploadTick_2.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewKycAddressProof);
                    } else if (s.equalsIgnoreCase("3")) {
                        buttonKycSignatureProof.setVisibility(View.VISIBLE);
                        buttonKycSignatureProof.setText("Uploaded");
                        imageViewUploadTick_3.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewKycSignatureProof);
                    } else if (s.equalsIgnoreCase("9")) {
                        buttonKycProfilePhoto.setVisibility(View.VISIBLE);
                        buttonKycProfilePhoto.setText("Uploaded");
                        imageViewUploadTick_9.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewKycProfilePhoto);
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
                        Picasso.with(context).load(baseUrl + image).into(imageViewFinanceIncomeProof);
                    } else if (s.equalsIgnoreCase("5")) {
                        buttonFinanceBankStatement.setVisibility(View.VISIBLE);
                        buttonFinanceBankStatement.setText("Uploaded");
                        imageViewUploadTick_5.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewFinanceBankStatement);
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
                        Picasso.with(context).load(baseUrl + image).into(imageViewEducationDegreeMarksheets);
                    } else if (s.equalsIgnoreCase("7")) {
                        buttonEducationDegreeCertificate.setVisibility(View.VISIBLE);
                        buttonEducationDegreeCertificate.setText("Uploaded");
                        imageViewUploadTick_7.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewEducationDegreeCertificate);
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
                        Picasso.with(context).load(baseUrl + image).into(imageViewOtherDocument);
                    }
                }

            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCoBorrowerDocuments(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "getCoBorrowerDocuments" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");
                coBorrowerID = jsonObject.getString("coBorrowerId");
                String baseUrl = jsonObject.getString("baseUrl");
                Log.e(MainApplication.TAG, "coBorrowerID: " + coBorrowerID);

                JSONArray jsonArray = jsonObject.getJSONArray("KycDocument");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");
                    Log.e(MainApplication.TAG, "TYPENO: " + s);
                    Log.e(MainApplication.TAG, "image: " + image);
                    if (s.equalsIgnoreCase("1")) {
                        buttonKycPhotoId_co.setVisibility(View.VISIBLE);
                        buttonKycPhotoId_co.setText("Uploaded");
                        imageViewUploadTick_1_co.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewKycPhotoId_co);
                    } else if (s.equalsIgnoreCase("2")) {
                        buttonKycAddressProof_co.setVisibility(View.VISIBLE);
                        buttonKycAddressProof_co.setText("Uploaded");
                        imageViewUploadTick_2_co.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewKycAddressProof_co);
                    } else if (s.equalsIgnoreCase("3")) {
                        buttonKycSignatureProof_co.setVisibility(View.VISIBLE);
                        buttonKycSignatureProof_co.setText("Uploaded");
                        imageViewUploadTick_3_co.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewKycSignatureProof_co);
                    } else if (s.equalsIgnoreCase("9")) {
                        buttonKycProfilePhoto_co.setVisibility(View.VISIBLE);
                        buttonKycProfilePhoto_co.setText("Uploaded");
                        imageViewUploadTick_9_co.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewKycProfilePhoto_co);
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
                        buttonFinanceIncomeProof_co.setVisibility(View.VISIBLE);
                        buttonFinanceIncomeProof_co.setText("Uploaded");
                        imageViewUploadTick_4_co.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewFinanceIncomeProof_co);
                    } else if (s.equalsIgnoreCase("5")) {
                        buttonFinanceBankStatement_co.setVisibility(View.VISIBLE);
                        buttonFinanceBankStatement_co.setText("Uploaded");
                        imageViewUploadTick_5_co.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewFinanceBankStatement_co);
                    }
                }


                JSONArray jsonArray3 = jsonObject.getJSONArray("Other");
                for (int i = 0; i < jsonArray3.length(); i++) {
                    JSONObject jsonObject1 = jsonArray3.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");

                    if (s.equalsIgnoreCase("8")) {
                        buttonOtherDocument_co.setVisibility(View.VISIBLE);
                        buttonOtherDocument_co.setText("Uploaded");
                        imageViewUploadTick_8_co.setVisibility(View.VISIBLE);
                        Picasso.with(context).load(baseUrl + image).into(imageViewOtherDocument_co);
                    }
                }

            } else {
//                JSONObject jsonObject = jsonData.getJSONObject("result");
//                coBorrowerID = jsonObject.getString("coBorrowerId");
//                Log.e(MainApplication.TAG, "coBorrowerID: "+coBorrowerID );
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStudentLaf(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "getStudentLaf" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                doc_finish = jsonObject.getInt("docFinish");
                lafDownloadPath = jsonObject.getString("lafDownloadPath");
                signedAppStatus = jsonObject.getInt("signedApplicationStatus");

                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("laf_download_url", lafDownloadPath);
                if(!jsonObject.getString("docPath").equalsIgnoreCase("")){
                    editor.putString("signed_application_url", jsonObject.getString("docPath"));
                }
                editor.putString("signed_appstatus", String.valueOf(signedAppStatus));
                editor.apply();
                editor.commit();

                if(doc_finish == 1){
                    LoanApplicationFragment_4 loanApplicationFragment_4 = new LoanApplicationFragment_4();
                    transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_4).commit();
                }else {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
