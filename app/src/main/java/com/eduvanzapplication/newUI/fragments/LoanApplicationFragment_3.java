package com.eduvanzapplication.newUI.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Util.JavaGetFileSize;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.R;
import com.eduvanzapplication.uploaddocs.PathFile;
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
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vijay.createpdf.activity.ImgToPdfActivity;
import vijay.createpdf.util.FileUtils;

import static com.eduvanzapplication.database.DBAdapter.ExecuteSql;
import static com.eduvanzapplication.database.DBAdapter.getLocalData;
import static com.eduvanzapplication.newUI.MainApplication.TAG;
import static com.facebook.FacebookSdk.getApplicationContext;
import static vijay.createpdf.util.StringUtils.showSnackbar;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */


public class LoanApplicationFragment_3 extends Fragment {

    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT = 1;
    public static Fragment mFragment;
    public static String coBorrowerID = "", baseUrl = "";
    static Context context;
    static Typeface typefaceFont, typefaceFontBold, typeface;
    static TextView textView1, textView2, textView3, textViewArrowDown, textViewArrowDownCo;
    static TextView txt_kyc_profilephoto, txt_kyc_photoId, txt_address, txt_signature, txt_income, txt_bank, txt_degreemarksheets,
            txt_degreecerti, txt_otherdocument,
            txt_kyc_profilephoto_co, txt_kyc_photoId_co, txt_address_co, txt_signature_co, txt_income_co, txt_bank_co, txt_otherdocument_co;
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
    static int doc_finish, signedAppStatus;
    static String lafDownloadPath = "";
    static FragmentTransaction transaction;
    static ImageView imageViewKycProfilePhoto_co, imageViewKycPhotoId_co, imageViewKycAddressProof_co, imageViewKycSignatureProof_co,
            imageViewFinanceIncomeProof_co, imageViewFinanceBankStatement_co, imageViewOtherDocument_co;
    static ImageView imageViewProfilePicSelect;
    public Boolean kyc = true, financial = true, education = true, other = true;
    public String documentType = "", documentTypeNo = "", userID = "";
    public int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_DOC = 2, IMGTOPDF = 9;
    public String userChoosenTask;
    Button buttonNext, buttonPrevious;
    MainApplication mainApplication;
    LinearLayout linearLayoutBorrower, linearLayoutCoBorrower;
    RelativeLayout relativeLayoutBorrower, relativeLayoutCoBorrower;
    int borrowerVisiblity = 1, coborrowerVisiblity = 1;
    String uploadFilePath = "";
    StringBuffer sb;
    private FileUtils mFileUtils;
    DownloadManager downloadManager;
    long downloadReference;

    ArrayList<String> FileNameArraylist;
    ArrayList<String> FilePathArraylist;
    ArrayList<String> filedirArraylist;
    ArrayList<String> DoctypeArraylist;
    ArrayList<String> DoctypeNoArraylist;
    ArrayList<String> selectUrlArraylist;

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadReference == referenceId) {

//file:///storage/emulated/0/Android/data/com.eduvanzapplication/files/Download/SIGNED APPLICATIONEdutesterEduvanz1530095441962.pdf
                int ch;
                ParcelFileDescriptor file;
                StringBuffer strContent = new StringBuffer("");

                //parse the JSON data and display on the screen
                try {
                    file = downloadManager.openDownloadedFile(downloadReference);
                    FileInputStream fileInputStream
                            = new ParcelFileDescriptor.AutoCloseInputStream(file);

                    while ((ch = fileInputStream.read()) != -1)
                        strContent.append((char) ch);

                    progressBar.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(context, R.string.downloading_of_file_just_finished, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    String className = this.getClass().getSimpleName();
                    String name = new Object() {
                    }.getClass().getEnclosingMethod().getName();
                    String errorMsg = e.getMessage();
                    String errorMsgDetails = e.getStackTrace().toString();
                    String errorLine = String.valueOf(e.getStackTrace()[0]);
                    Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
                }
            }
        }
    };

    public LoanApplicationFragment_3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_loanapplication_3, container, false);
        try {
            context = getContext();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            mainApplication = new MainApplication();
            mFileUtils = new FileUtils(getActivity());

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

            txt_kyc_profilephoto = (TextView) view.findViewById(R.id.txt_kyc_profilephoto);
            txt_kyc_photoId = (TextView) view.findViewById(R.id.txt_kyc_photoId);
            txt_address = (TextView) view.findViewById(R.id.txt_address);
            txt_signature = (TextView) view.findViewById(R.id.txt_signature);
            txt_income = (TextView) view.findViewById(R.id.txt_income);
            txt_bank = (TextView) view.findViewById(R.id.txt_bank);
            txt_degreemarksheets = (TextView) view.findViewById(R.id.txt_degreemarksheets);
            txt_degreecerti = (TextView) view.findViewById(R.id.txt_degreecerti);
            txt_otherdocument = (TextView) view.findViewById(R.id.txt_otherdocument);
            txt_kyc_profilephoto_co = (TextView) view.findViewById(R.id.txt_kyc_profilephoto_co);
            txt_kyc_photoId_co = (TextView) view.findViewById(R.id.txt_kyc_photoId_co);
            txt_address_co = (TextView) view.findViewById(R.id.txt_address_co);
            txt_signature_co = (TextView) view.findViewById(R.id.txt_signature_co);
            txt_income_co = (TextView) view.findViewById(R.id.txt_income_co);
            txt_bank_co = (TextView) view.findViewById(R.id.txt_bank_co);
            txt_otherdocument_co = (TextView) view.findViewById(R.id.txt_otherdocument_co);

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
                        String url = MainApplication.mainUrl + "laf/getStudentLaf"; //https://api.eduvanz.com/laf/getStudentLaf
                        Map<String, String> params = new HashMap<String, String>();
                        if (!Globle.isNetworkAvailable(context)) {
                            Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                        } else {
                            VolleyCallNew volleyCall = new VolleyCallNew();
                            params.put("studentId", userID); //2953
                            volleyCall.sendRequest(context, url, null, mFragment, "getStudentLaf", params, MainApplication.auth_token);
                        }
                    } catch (Exception e) {
                        String className = this.getClass().getSimpleName();
                        String name = new Object() {
                        }.getClass().getEnclosingMethod().getName();
                        String errorMsg = e.getMessage();
                        String errorMsgDetails = e.getStackTrace().toString();
                        String errorLine = String.valueOf(e.getStackTrace()[0]);
                        Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
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

            IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            context.registerReceiver(downloadReceiver, filter);

            /**--------------------------------KYC - PROFILE PHOTO-----------------------------------**/
            imageViewKycProfilePhoto = (ImageView) view.findViewById(R.id.imageView_kyc_profilephoto);
            imageViewKycProfilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageViewUploadTick_9.setVisibility(View.GONE);
                    documentType = "9_SD_PhotoDoc";
                    documentTypeNo = "9";
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_profile_picture), getString(R.string.applicant_single_picture_required_to_be_uploaded));
                    buttonKycProfilePhoto.setText(R.string.upload);
                }
            });
            buttonKycProfilePhoto = (Button) view.findViewById(R.id.button_kyc_profilephoto);
            buttonKycProfilePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonKycProfilePhoto.getTag().toString().substring(buttonKycProfilePhoto.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(String.valueOf(buttonKycProfilePhoto.getTag()));

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonKycProfilePhoto.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonKycProfilePhoto.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_adhaar_card), getString(R.string.applicant_adhaar_card_front_and_backside));
                    buttonKycPhotoId.setText(R.string.upload);
                }
            });
            buttonKycPhotoId = (Button) view.findViewById(R.id.button_kyc_photoID);
            buttonKycPhotoId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonKycPhotoId.getTag().toString().substring(buttonKycPhotoId.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    File filepath1 = new File("sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        try {
                            openPdf(buttonKycPhotoId.getTag().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonKycPhotoId.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonKycPhotoId.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    //Earlier used
//                    documentType = "2_SD_PhotoDoc";
//                    documentTypeNo = "2";
                    documentType = "3_SD_PhotoDoc";
                    documentTypeNo = "3";
                    buttonKycAddressProof.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_pan_card), getString(R.string.applicant_pan_card));

                }
            });
            buttonKycAddressProof = (Button) view.findViewById(R.id.button_kyc_addressproof);
            buttonKycAddressProof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonKycAddressProof.getTag().toString().substring(buttonKycAddressProof.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonKycAddressProof.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonKycAddressProof.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonKycAddressProof.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    //Earlier used
//                    documentType = "3_SD_PhotoDoc";
//                    documentTypeNo = "3";
                    documentType = "2_SD_PhotoDoc";
                    documentTypeNo = "2";
                    buttonKycSignatureProof.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_address_proof), getString(R.string.applicant_address_proof));
                }
            });
            buttonKycSignatureProof = (Button) view.findViewById(R.id.button_kyc_signatureproof);
            buttonKycSignatureProof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonKycSignatureProof.getTag().toString().substring(buttonKycSignatureProof.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonKycSignatureProof.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonKycSignatureProof.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonKycSignatureProof.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    buttonFinanceIncomeProof.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_income_proof), getString(R.string.salary_slip_of_applicant_latest_3_months_if_not_available_salary_certificate_stating_the_same_details_would_be_accepted));

                }
            });
            buttonFinanceIncomeProof = (Button) view.findViewById(R.id.button_finance_incomeproof);
            buttonFinanceIncomeProof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonFinanceIncomeProof.getTag().toString().substring(buttonFinanceIncomeProof.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonFinanceIncomeProof.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonFinanceIncomeProof.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonFinanceIncomeProof.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    buttonFinanceBankStatement.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_bank_statement), getString(R.string.current_3_months_bank_statement_of_applicant_reflecting_salary_along_with_the_front_page));
                }
            });
            buttonFinanceBankStatement = (Button) view.findViewById(R.id.button_finance_bankstatement);
            buttonFinanceBankStatement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonFinanceBankStatement.getTag().toString().substring(buttonFinanceBankStatement.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonFinanceBankStatement.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonFinanceBankStatement.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonFinanceBankStatement.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    buttonEducationDegreeMarksheets.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_latest_marksheet), getString(R.string.latest_marksheet_of_the_applicant));
                }
            });
            buttonEducationDegreeMarksheets = (Button) view.findViewById(R.id.button_education_degreemarksheet);
            buttonEducationDegreeMarksheets.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonEducationDegreeMarksheets.getTag().toString().substring(buttonEducationDegreeMarksheets.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonEducationDegreeMarksheets.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonEducationDegreeMarksheets.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonEducationDegreeMarksheets.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_latest_certificate), getString(R.string.latest_certificate_of_the_applicant));

                    buttonEducationDegreeCertificate.setText(R.string.upload);
                }
            });
            buttonEducationDegreeCertificate = (Button) view.findViewById(R.id.button_education_degreecertificate);
            buttonEducationDegreeCertificate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonEducationDegreeCertificate.getTag().toString().substring(buttonEducationDegreeCertificate.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonEducationDegreeCertificate.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonEducationDegreeCertificate.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonEducationDegreeCertificate.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    buttonOtherDocument.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_other_document), getString(R.string.recent_utility_bill_if_the_house_is_owned_or_rent_agreement_if_the_house_is_rented_rent_agreement_should_be_noterised));

                }
            });
            buttonOtherDocument = (Button) view.findViewById(R.id.button_otherdocument);
            buttonOtherDocument.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonOtherDocument.getTag().toString().substring(buttonOtherDocument.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonOtherDocument.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonOtherDocument.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonOtherDocument.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    imageViewUploadTick_9_co.setVisibility(View.GONE);
                    documentType = "9_SD_PhotoDoc";
                    documentTypeNo = "9_co";
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_profile_picture), getString(R.string.co_applicant_single_picture_required_to_be_uploaded));
                    buttonKycProfilePhoto_co.setText(R.string.upload);
                }
            });
            buttonKycProfilePhoto_co = (Button) view.findViewById(R.id.button_kyc_profilephoto_co);
            buttonKycProfilePhoto_co.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonKycProfilePhoto_co.getTag().toString().substring(buttonKycProfilePhoto_co.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(String.valueOf(buttonKycProfilePhoto_co.getTag()));

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonKycProfilePhoto_co.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonKycProfilePhoto_co.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_adhaar_card), getString(R.string.co_applicant_adhaar_card_front_and_backside));
                    buttonKycPhotoId_co.setText(R.string.upload);
                }
            });
            buttonKycPhotoId_co = (Button) view.findViewById(R.id.button_kyc_photoID_co);
            buttonKycPhotoId_co.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonKycPhotoId_co.getTag().toString().substring(buttonKycPhotoId_co.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonKycPhotoId_co.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonKycPhotoId_co.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonKycPhotoId_co.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    //User Earlier
//                    documentType = "2_SD_PhotoDoc";
//                    documentTypeNo = "2_co";
                    documentType = "3_SD_PhotoDoc";
                    documentTypeNo = "3_co";
                    buttonKycAddressProof_co.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_pan_card), getString(R.string.co_applicant_pan_card));

                }
            });
            buttonKycAddressProof_co = (Button) view.findViewById(R.id.button_kyc_addressproof_co);
            buttonKycAddressProof_co.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonKycAddressProof_co.getTag().toString().substring(buttonKycAddressProof_co.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonKycAddressProof_co.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonKycAddressProof_co.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonKycAddressProof_co.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    //Used Earlier
//                    documentType = "3_SD_PhotoDoc";
//                    documentTypeNo = "3_co";

                    documentType = "2_SD_PhotoDoc";
                    documentTypeNo = "2_co";
                    buttonKycSignatureProof_co.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_address_proof), getString(R.string.co_applicant_address_proof));

                }
            });
            buttonKycSignatureProof_co = (Button) view.findViewById(R.id.button_kyc_signatureproof_co);
            buttonKycSignatureProof_co.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonKycSignatureProof_co.getTag().toString().substring(buttonKycSignatureProof_co.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonKycSignatureProof_co.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonKycSignatureProof_co.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonKycSignatureProof_co.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    buttonFinanceIncomeProof_co.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_income_proof), getString(R.string.salary_slip_of_co_applicant_latest_3_months_if_not_available_salary_certificate_stating_the_same_details_would_be_accepted));
                }
            });
            buttonFinanceIncomeProof_co = (Button) view.findViewById(R.id.button_finance_incomeproof_co);
            buttonFinanceIncomeProof_co.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonFinanceIncomeProof_co.getTag().toString().substring(buttonFinanceIncomeProof_co.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);


                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonFinanceIncomeProof_co.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonFinanceIncomeProof_co.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonFinanceIncomeProof_co.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    buttonFinanceBankStatement_co.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_bank_statement), getString(R.string.current_3_months_bank_statement_of_co_applicant_reflecting_salary_along_with_the_front_page));
                }
            });
            buttonFinanceBankStatement_co = (Button) view.findViewById(R.id.button_finance_bankstatement_co);
            buttonFinanceBankStatement_co.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonFinanceBankStatement_co.getTag().toString().substring(buttonFinanceBankStatement_co.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonFinanceBankStatement_co.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonFinanceBankStatement_co.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonFinanceBankStatement_co.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
                    buttonOtherDocument_co.setText(R.string.upload);
    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_other_document), getString(R.string.recent_utility_bill_if_the_house_is_owned_or_rent_agreement_if_the_house_is_rented_rent_agreement_should_be_noterised));
                }
            });
            buttonOtherDocument_co = (Button) view.findViewById(R.id.button_otherdocument_co);
            buttonOtherDocument_co.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = buttonOtherDocument_co.getTag().toString().substring(buttonOtherDocument_co.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(buttonOtherDocument_co.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(buttonOtherDocument_co.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(buttonOtherDocument_co.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**----------------------------------END OF OTHER DOCUMENT-------------------------------**/

            /** API CALL **/
            try {
                String url = MainApplication.mainUrl + "document/getapplicantDocumentDetails";
                Map<String, String> params = new HashMap<String, String>();
                if (!Globle.isNetworkAvailable(context)) {
                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                } else {
                    VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getapplicantDocumentDetails
                    params.put("studentId", userID);//"studentId" -> "2953"
                    volleyCall.sendRequest(context, url, null, mFragment, "getDocumentsBorrower", params, MainApplication.auth_token);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /**API CALL*/
            try {
                String url = MainApplication.mainUrl + "document/getCoApplicantDocumentDetails";
                Map<String, String> params = new HashMap<String, String>();
                if (!Globle.isNetworkAvailable(context)) {
                    Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                } else {
                    VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getCoApplicantDocumentDetails
                    params.put("studentId", userID);//"studentId" -> "2942"
                    volleyCall.sendRequest(context, url, null, mFragment, "getCoBorrowerDocuments", params, MainApplication.auth_token);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//            FileUploadToWeb();

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
        }
        return view;
    }

    private void FileUploadToWeb() {

        String sSQL = "SELECT FileName,FilePath,filedir,Doctype,DoctypeNo,selectUrl FROM DocumentUpload WHERE ISUploaded = 'false'";

        FileNameArraylist = new ArrayList<>();
        FilePathArraylist = new ArrayList<>();
        filedirArraylist = new ArrayList<>();
        DoctypeArraylist = new ArrayList<>();
        DoctypeNoArraylist = new ArrayList<>();
        selectUrlArraylist = new ArrayList<>();

        Cursor cursor = null;
        try {
            cursor = getLocalData(context, sSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(cursor != null) {
            if (cursor.getCount() >= 1) {

                if (cursor.moveToFirst()) {
                    do {
                        FileNameArraylist.add(cursor.getString(0));
                        FilePathArraylist.add(cursor.getString(1));
                        filedirArraylist.add(cursor.getString(2));
                        DoctypeArraylist.add(cursor.getString(3));
                        DoctypeNoArraylist.add(cursor.getString(4));
                        selectUrlArraylist.add(cursor.getString(5));
                    }
                    while (cursor.moveToNext());
                    cursor.close();
                }
                if (Globle.isNetworkAvailable(context)) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (!Globle.isNetworkAvailable(context)) {
                        } else {
                            if (FileNameArraylist.size() > 0) {
                                uploadFile(FilePathArraylist.get(0), DoctypeArraylist.get(0), DoctypeNoArraylist.get(0), Integer.parseInt(selectUrlArraylist.get(0)));
                            }
                        }
                    }
                }).start();
            }
            else{
                /** API CALL **/
                try {
                    String url = MainApplication.mainUrl + "document/getapplicantDocumentDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    if (!Globle.isNetworkAvailable(context)) {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getapplicantDocumentDetails
                        params.put("studentId", userID);//"studentId" -> "2953"
                        volleyCall.sendRequest(context, url, null, mFragment, "getDocumentsBorrower", params, MainApplication.auth_token);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String url = MainApplication.mainUrl + "document/getCoApplicantDocumentDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    if (!Globle.isNetworkAvailable(context)) {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getCoApplicantDocumentDetails
                        params.put("studentId", userID);//"studentId" -> "2942"
                        volleyCall.sendRequest(context, url, null, mFragment, "getCoBorrowerDocuments", params, MainApplication.auth_token);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        else
        {
                /** API CALL **/
                try {
                    String url = MainApplication.mainUrl + "document/getapplicantDocumentDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    if (!Globle.isNetworkAvailable(context)) {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getapplicantDocumentDetails
                        params.put("studentId", userID);//"studentId" -> "2953"
                        volleyCall.sendRequest(context, url, null, mFragment, "getDocumentsBorrower", params, MainApplication.auth_token);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    String url = MainApplication.mainUrl + "document/getCoApplicantDocumentDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    if (!Globle.isNetworkAvailable(context)) {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getCoApplicantDocumentDetails
                        params.put("studentId", userID);//"studentId" -> "2942"
                        volleyCall.sendRequest(context, url, null, mFragment, "getCoBorrowerDocuments", params, MainApplication.auth_token);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }

    }

    private void openImage(String mPath) {

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            final AlertDialog dialog = builder.create();
            LayoutInflater inflater = getLayoutInflater();
            View dialogLayout = inflater.inflate(R.layout.imagedialog, null);
            dialog.setView(dialogLayout);
            ImageView image = (ImageView) dialogLayout.findViewById(R.id.imgDialogImage);
            Picasso.with(context).load(mPath).into(image);

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialog.show();

            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface d) {
    //                ImageView image = (ImageView) dialog.findViewById(R.id.imgDialogImage);
    //                Picasso.with(context).load(mPath).into(image);
    //                Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
    //                        R.drawable.whygoprodialogimage);
    //                float imageWidthInPX = (float) image.getWidth();
    //
    //                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Math.round(imageWidthInPX),
    //                        Math.round(imageWidthInPX * (float) icon.getHeight() / (float) icon.getWidth()));
    //                image.setLayoutParams(layoutParams);

                }
            });
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(),className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

//    private void openImage(String mPath) {
//
//        Uri path = Uri.parse(mPath);
//        Intent intent = new Intent(Intent.ACTION_VIEW);
////        intent.setDataAndType(path, "application/*");
//        intent.setDataAndType(path, "image/*");
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        try {
//            startActivityForResult(intent, 1);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(getActivity(), R.string.no_application_available_ro_view_pdf, Toast.LENGTH_SHORT).show();
//        }
//    }

    private void openPdf(String mPath) {

        Uri path = Uri.parse(mPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(path, "application/*");
        intent.setDataAndType(path, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), R.string.no_application_available_ro_view_pdf, Toast.LENGTH_SHORT).show();
        }
    }

    private void imageToPdf(String documentTypeNo, String toolbarTitle, String note) {

        Intent intent = new Intent(getActivity(), ImgToPdfActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userID", userID);
        bundle.putString("documentTypeNo", documentTypeNo);
        bundle.putString("toolbarTitle", toolbarTitle);
        bundle.putString("note", note);
        intent.putExtras(bundle);
        startActivityForResult(intent, 2);// Activity is started with requestCode 2
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

    private Bitmap decodeUri(Uri selectedImage, Context context) throws FileNotFoundException {
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

        if (requestCode == 2) {
            if (resultCode == 2) {
                String message = data.getStringExtra("PATH");
                String doctypeno = data.getStringExtra("documentTypeNo");
                String FileExtn = null;
                Double FileSize = null;

                uploadFilePath = message;

                String filesz = JavaGetFileSize.getFileSizeMegaBytes(new File(message)).substring(0, JavaGetFileSize.getFileSizeMegaBytes(new File(message)).length() - 3);
                FileSize = Double.valueOf(filesz);

                FileExtn = uploadFilePath.substring(uploadFilePath.lastIndexOf(".") + 1);// Without dot jpg, png

                if (FileExtn.equals("jpg") || FileExtn.equals("jpeg") || FileExtn.equals("png") || FileExtn.equals("pdf") ||
                        FileExtn.equals("bmp") || FileExtn.equals("webp") || FileExtn.equals("zip") || FileExtn.equals("rar")) {

                    if (FileSize < 30) {
                        Log.e("TAG", "onActivityResult: DOC PATH " + uploadFilePath);

                        if (uploadFilePath != null) {
                            // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
                            progressBar.setVisibility(View.VISIBLE);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        String doctype = "";
                                        int selectUrl = 0;
                                        if (doctypeno.length() > 2) {
                                            doctype = doctypeno.substring(0, 1) + "_SD_PhotoDoc";
                                            selectUrl = 1;
                                        } else {
                                            doctype = doctypeno + "_SD_PhotoDoc";
                                            selectUrl = 0;
                                        }
                                        //creating new thread to handle Http Operations
//                            Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                                        if (!Globle.isNetworkAvailable(context)) {
    //                                        uploadFileOffline(uploadFilePath, doctype, doctypeno, selectUrl);

                                        } else {
                                            uploadFile(uploadFilePath, doctype, doctypeno, selectUrl);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                        } else {
                            Toast.makeText(context, R.string.please_choose_a_file_first, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, R.string.file_size_exceeds_limits_of_30_mb, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, R.string.file_is_not_in_supported_format, Toast.LENGTH_LONG).show();
                }

            }
            if (resultCode == 1) {
                String message = data.getStringExtra("BACK");
                textView1.setText(message);
                /** API CALL **/
                try {
                    String url = MainApplication.mainUrl + "document/getapplicantDocumentDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    if (!Globle.isNetworkAvailable(context)) {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getapplicantDocumentDetails
                        params.put("studentId", userID);//"studentId" -> "2953"
                        volleyCall.sendRequest(context, url, null, mFragment, "getDocumentsBorrower", params, MainApplication.auth_token);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /**API CALL*/
                try {
                    String url = MainApplication.mainUrl + "document/getCoApplicantDocumentDetails";
                    Map<String, String> params = new HashMap<String, String>();
                    if (!Globle.isNetworkAvailable(context)) {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getCoApplicantDocumentDetails
                        params.put("studentId", userID);//"studentId" -> "2942"
                        volleyCall.sendRequest(context, url, null, mFragment, "getCoBorrowerDocuments", params, MainApplication.auth_token);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        if (requestCode == 1) {
            if (resultCode == 1) {
                String message = data.getStringExtra("BACK");
                textView1.setText(message);
            }
        }
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
                    bm = decodeUri(data.getData(), context);//5383513
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

                FileExtn = uploadFilePath.substring(uploadFilePath.lastIndexOf(".") + 1);// Without dot jpg, png

                if (FileExtn.equals("jpg") || FileExtn.equals("jpeg") || FileExtn.equals("png") || FileExtn.equals("pdf") ||
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
                    } else {
                        Toast.makeText(context, R.string.file_size_exceeds_limit_of_30_mb, Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, R.string.file_is_not_in_supported_format, Toast.LENGTH_LONG).show();
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

        if (imageViewProfilePicSelect == imageViewKycProfilePhoto) {
            imageViewProfilePicSelect.setImageBitmap(thumbnail);
            buttonKycProfilePhoto.setVisibility(View.VISIBLE);
        } else if (imageViewProfilePicSelect == imageViewKycProfilePhoto_co) {
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
        if (imageViewProfilePicSelect == imageViewKycProfilePhoto) {
            imageViewProfilePicSelect.setImageBitmap(bm);
            buttonKycProfilePhoto.setVisibility(View.VISIBLE);
        } else if (imageViewProfilePicSelect == imageViewKycProfilePhoto_co) {
            imageViewProfilePicSelect.setImageBitmap(bm);
            buttonKycProfilePhoto_co.setVisibility(View.VISIBLE);
        }
    }

    public String copyFileOrDirectory(String srcDir, String dstDir) {

        String filepath = dstDir;

        try {
            File src = new File(srcDir);
            File dst = new File(dstDir, src.getName());
            if (src.isDirectory()) {

                String files[] = src.list();
                int filesLength = files.length;
                for (int i = 0; i < filesLength; i++) {
                    String src1 = (new File(src, files[i]).getPath());
                    String dst1 = dst.getPath();
                    copyFileOrDirectory(src1, dst1);
                }
            } else {
                filepath = copyFile(src, dst);
            }
        } catch (Exception e) {
            e.printStackTrace();
            filepath = "Error Copying file";
        }
        return filepath;
    }

    public String copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.getParentFile().exists())
            destFile.getParentFile().mkdirs();
//        File dst = new File(destFile, sourceFile.getName());
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
        return String.valueOf(destFile);
    }

    public void uploadFileOffline(final String selectedFilePath, String doctype, String doctypeno, int selectUrl) {

        String strFileName = selectedFilePath.toString().substring(selectedFilePath.toString().lastIndexOf("/") + 1);

        File filepath = null;
        File myDir = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.eduvanzapplication/applicantDocumentUpload/");
        if (!myDir.exists()) {
            myDir.mkdir();
        }

        File myDir1 = new File(Environment.getExternalStorageDirectory() + "/Android/data/com.eduvanzapplication/coapplicantDocumentUpload/");
        if (!myDir.exists()) {
            myDir1.mkdir();
        }

        Log.e(TAG, "uploadFile++++++: selectUrl" + selectUrl + "doctype  " + doctype + "  doctypeno " + doctypeno + " selectedFilePath " + selectedFilePath + " coBorrowerID   " + coBorrowerID);
        if (selectUrl == 0) {

            try {
                filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/applicantDocumentUpload/");
                File desfilepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/applicantDocumentUpload/" + strFileName);

                Log.e(TAG, "urlup++++++: " + filepath);

//                        String CreatedFilePath = copyFileOrDirectory(new File(selectedFilePath).getPath(), filepath.getPath());
                String CreatedFilePath = copyFile(new File(selectedFilePath), desfilepath);
//storage/emulated/0/document/applicantDocumentUpload/Photo_20181103_174926.jpg
                if (new File(CreatedFilePath).exists()) {
                    File Savedfile = new File(CreatedFilePath);
                    String filename = new File(CreatedFilePath).getName();
                    String filedir = new File(CreatedFilePath).getParent();

                    Log.e(TAG, "Create File++++++: " + filename);

                    String MySql = "select count(*) from DocumentUpload where DoctypeNo ='" + doctypeno + "'";

                    int count = 0;
                    try {
                        count = Integer.parseInt(getLocalData(context, MySql).getString(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (count < 1) {
                        try {
                            String sSql = "INSERT INTO DocumentUpload (FileName ,FilePath , filedir ,Doctype ,DoctypeNo,selectUrl ,ISSaved ,ISUploaded ) VALUES" +
                                    " ('" + filename + "'," +
                                    " '" + CreatedFilePath + "'," +
                                    " '" + filedir + "'," +
                                    " '" + doctype + "'," +
                                    " '" + doctypeno + "'," +
                                    " '" + selectUrl + "'," +
                                    " '" + true + "'," +
                                    " '" + false + "')";
                            ExecuteSql(sSql);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        try {
                            String DeleteRecord = "Delete from DocumentUpload where DoctypeNo = '" + doctypeno + "'";
                            ExecuteSql(DeleteRecord);

                            String sSql = "INSERT INTO DocumentUpload (FileName ,FilePath , filedir ,Doctype ,DoctypeNo,selectUrl ,ISSaved ,ISUploaded ) VALUES" +
                                    " ('" + filename + "'," +
                                    " '" + CreatedFilePath + "'," +
                                    " '" + filedir + "'," +
                                    " '" + doctype + "'," +
                                    " '" + doctypeno + "'," +
                                    " '" + selectUrl + "'," +
                                    " '" + true + "'," +
                                    " '" + false + "')";
                            ExecuteSql(sSql);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            String sSQL = "Select FileName from DocumentUpload where selectUrl ='" + selectUrl + "'";
                            ArrayList<String> dbfilesArraylist;
                            dbfilesArraylist = new ArrayList<>();

                            Cursor cursor = null;
                            try {
                                cursor = getLocalData(context, sSQL);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (cursor.getCount() >= 1) {

                                if (cursor.moveToFirst()) {
                                    do {
                                        dbfilesArraylist.add(cursor.getString(0));
                                    }
                                    while (cursor.moveToNext());
                                    cursor.close();
                                }
                            }
                            String files[] = (filepath).list();
                            int filesLength = files.length;
                            Boolean deleteFile = false;

                            for (int i = 0; i < filesLength; i++) {

                                for (int j = 0; j < dbfilesArraylist.size(); j++) {

                                    if (files[i].toString().toString().equals(dbfilesArraylist.get(j))) {
                                        deleteFile = true;
                                    }

                                }

                                if (!deleteFile) {
                                    new File(filepath + "/" + files[i]).delete();
                                    if (new File(filepath + "/" + files[i]).exists()) {
                                        new File(filepath + "/" + files[i]).getCanonicalFile().delete();
                                        Log.e(TAG, "Deleted File++++++: " + files[i]);

                                        if (new File(filedir + files[i]).exists()) {
                                            getApplicationContext().deleteFile(new File(filedir + "/" + files[i]).getName());
                                        }
                                    }
                                }
                                deleteFile = false;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (selectUrl == 1) {

            try {
                filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/coapplicantDocumentUpload/");
                File desfilepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/coapplicantDocumentUpload/" + strFileName);

                Log.e(TAG, "urlup++++++: " + filepath);

//                        String CreatedFilePath = copyFileOrDirectory(new File(selectedFilePath).getPath(), filepath.getPath());
                String CreatedFilePath = copyFile(new File(selectedFilePath), desfilepath);

                if (new File(CreatedFilePath).exists()) {
                    String filename = new File(CreatedFilePath).getName();
                    String filedir = new File(CreatedFilePath).getParent();

                    String MySql = "select count(*) from DocumentUpload where DoctypeNo ='" + doctypeno + "'";

                    int count = 0;
                    try {
                        count = Integer.parseInt(getLocalData(context, MySql).getString(0));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (count < 1) {
                        try {
                            String sSql = "INSERT INTO DocumentUpload (FileName ,FilePath , filedir ,Doctype ,DoctypeNo,selectUrl ,ISSaved ,ISUploaded ) VALUES" +
                                    " ('" + filename + "'," +
                                    " '" + CreatedFilePath + "'," +
                                    " '" + filedir + "'," +
                                    " '" + doctype + "'," +
                                    " '" + doctypeno + "'," +
                                    " '" + selectUrl + "'," +
                                    " '" + true + "'," +
                                    " '" + false + "')";
                            ExecuteSql(sSql);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        try {
                            String DeleteRecord = "Delete from DocumentUpload where DoctypeNo = '" + doctypeno + "'";
                            ExecuteSql(DeleteRecord);

                            String sSql = "INSERT INTO DocumentUpload (FileName ,FilePath , filedir ,Doctype ,DoctypeNo,selectUrl ,ISSaved ,ISUploaded ) VALUES" +
                                    " ('" + filename + "'," +
                                    " '" + CreatedFilePath + "'," +
                                    " '" + filedir + "'," +
                                    " '" + doctype + "'," +
                                    " '" + doctypeno + "'," +
                                    " '" + selectUrl + "'," +
                                    " '" + true + "'," +
                                    " '" + false + "')";
                            ExecuteSql(sSql);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        try {
                            String sSQL = "Select FileName from DocumentUpload where selectUrl ='" + selectUrl + "'";
                            String dbfiles[];
                            ArrayList<String> dbfilesArraylist;
                            dbfilesArraylist = new ArrayList<>();

                            Cursor cursor = null;
                            try {
                                cursor = getLocalData(context, sSQL);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (cursor.getCount() >= 1) {

                                if (cursor.moveToFirst()) {
                                    do {
                                        dbfilesArraylist.add(cursor.getString(0));
                                    }
                                    while (cursor.moveToNext());
                                    cursor.close();
                                }
                            }
                            String files[] = (filepath).list();
                            int filesLength = files.length;
                            Boolean deleteFile = false;

                            for (int i = 0; i < filesLength; i++) {

                                for (int j = 0; j < dbfilesArraylist.size(); j++) {

                                    if (files[i].toString().toString().equals(dbfilesArraylist.get(j))) {
                                        deleteFile = true;
                                    }
                                }

                                if (!deleteFile) {
                                    new File(filepath + "/" + files[i]).delete();
                                    if (new File(filepath + "/" + files[i]).exists()) {
                                        new File(filepath + "/" + files[i]).getCanonicalFile().delete();
                                        Log.e(TAG, "Deleted File++++++: " + files[i]);

                                        if (new File(filedir + files[i]).exists()) {
                                            getApplicationContext().deleteFile(new File(filedir + "/" + files[i]).getName());
                                        }
                                    }
                                }
                                deleteFile = false;
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int uploadFile(final String selectedFilePath, String doctype, String doctypeno, int selectUrl) {
        String urlup = "";
        Log.e(TAG, "uploadFile++++++: selectUrl" + selectUrl + "doctype  " + doctype + "  doctypeno " + doctypeno + " selectedFilePath " + selectedFilePath + " coBorrowerID   " + coBorrowerID);
        if (selectUrl == 0) {
            urlup = MainApplication.mainUrl + "document/applicantDocumentUpload";
        } else if (selectUrl == 1) {
            urlup = MainApplication.mainUrl + "document/coapplicantDocumentUpload";
        }

        Log.e(TAG, "urlup++++++: " + urlup);

        int serverResponseCode = 0;
        documentType = doctype;
        documentTypeNo = doctypeno;
        Log.e(TAG, "documentType: " + documentType + "documentTypeNo: " + documentTypeNo);
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
//              connection.setRequestProperty("user_id", user_id);
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

                if (selectUrl == 1) {
                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                    if(coBorrowerID.equals("")) {
                        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                        coBorrowerID = sharedPreferences.getString("logged_id", "null");
                    }
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
                } else if (selectUrl == 0) {
                    dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"id\";id=" + userID + "" + lineEnd);//2953
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
//{"result":"\/var\/www\/html\/eduvanzbeta\/uploads\/document\/student\/A181003003_User_Photo_1538546888.jpg","status":1,"message":"success"}
                try {
                    JSONObject mJson = new JSONObject(sb.toString());
                    final String mData = mJson.getString("status");
                    final String mData1 = mJson.getString("message");

                    Log.e("TAG", " 2252: " + new Date().toLocaleString());//1538546658896.jpg/
                    if (mData.equalsIgnoreCase("1")) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                uploadFilePath = "";

                                /** API CALL **/
                                try {
                                    String url = MainApplication.mainUrl + "document/getapplicantDocumentDetails";
                                    Map<String, String> params = new HashMap<String, String>();
                                    if (!Globle.isNetworkAvailable(context)) {
                                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                                    } else {
                                        VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getapplicantDocumentDetails
                                        params.put("studentId", userID);//"studentId" -> "2953"
                                        volleyCall.sendRequest(context, url, null, mFragment, "getDocumentsBorrower", params, MainApplication.auth_token);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                /**API CALL*/
                                try {
                                    String url = MainApplication.mainUrl + "document/getCoApplicantDocumentDetails";
                                    Map<String, String> params = new HashMap<String, String>();
                                    if (!Globle.isNetworkAvailable(context)) {
                                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                                    } else {
                                        VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getCoApplicantDocumentDetails
                                        params.put("studentId", userID);//"studentId" -> "2942"
                                        volleyCall.sendRequest(context, url, null, mFragment, "getCoBorrowerDocuments", params, MainApplication.auth_token);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                progressBar.setVisibility(View.GONE);
                                Log.e("TAG", "uploadFile: code 1 " + mData);
                                Toast.makeText(context, mData1, Toast.LENGTH_SHORT).show();

                                //UnComment this for offline
//                                        try {
//                                            String sSql = "Update DocumentUpload set ISUploaded = '" + true + "' WHERE FilePath = '"+selectedFilePath+"'" ;
//                                            ExecuteSql(sSql);
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
//
//                                    uploadFilePath = "";
//                                    progressBar.setVisibility(View.GONE);
//                                Log.e(TAG, "uploadFile 2267: " + selectUrl + "doctype  " + doctype + "  doctypeno " + doctypeno + " selectedFilePath " + selectedFilePath + " coBorrowerID   " + coBorrowerID);
//                                Toast.makeText(context, mData1, Toast.LENGTH_SHORT).show();

                            }
                        });

                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                try {
//                                    String sSql = "Update DocumentUpload set ISUploaded = '" + false + "' WHERE FilePath = '"+selectedFilePath+"'" ;
//                                    ExecuteSql(sSql);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
                                progressBar.setVisibility(View.GONE);
                                Log.e("TAG", " 2285: " + new Date().toLocaleString());//1538546658896.jpg/
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
                            Log.e("TAG", " 2303: " + new Date().toLocaleString());//1538546658896.jpg/
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
                        Log.e("TAG", " 2318: " + new Date().toLocaleString());//1538546658896.jpg/
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
////                        FileUploadToWeb();
                        Log.e("TAG", " 2335: " + new Date().toLocaleString());//1538546658896.jpg/
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.e("TAG", " 2342: " + new Date().toLocaleString());//1538546658896.jpg/

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
                    Log.e(TAG, "TYPENO: " + s);
                    Log.e(TAG, "image: " + image);
                    if (s.equalsIgnoreCase("1")) {
                        buttonKycPhotoId.setVisibility(View.VISIBLE);
                        buttonKycPhotoId.setTag(baseUrl + image);
                        imageViewUploadTick_1.setVisibility(View.VISIBLE);
                        txt_kyc_photoId.setVisibility(View.GONE);

                        String strFileName = buttonKycPhotoId.getTag().toString().substring(buttonKycPhotoId.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonKycPhotoId.setText(R.string.preview);
                            imageViewKycPhotoId.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonKycPhotoId.setText(R.string.download);
                            imageViewKycPhotoId.setBackgroundResource(R.drawable.zip_image);
                        }
//                        Picasso.with(context).load(String.valueOf(bm)).into(imageViewKycPhotoId);
                    } else if (s.equalsIgnoreCase("3")) {
                        buttonKycAddressProof.setVisibility(View.VISIBLE);
                        buttonKycAddressProof.setTag(baseUrl + image);
                        imageViewUploadTick_2.setVisibility(View.VISIBLE);
                        txt_address.setVisibility(View.GONE);
                        String strFileName = buttonKycAddressProof.getTag().toString().substring(buttonKycAddressProof.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonKycAddressProof.setText(R.string.preview);
                            imageViewKycAddressProof.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonKycAddressProof.setText(R.string.download);
                            imageViewKycAddressProof.setBackgroundResource(R.drawable.zip_image);

                        }
//                        Picasso.with(context).load(String.valueOf(bm)).into(imageViewKycAddressProof);
                    } else if (s.equalsIgnoreCase("2")) {
                        buttonKycSignatureProof.setVisibility(View.VISIBLE);
                        buttonKycSignatureProof.setTag(baseUrl + image);
                        imageViewUploadTick_3.setVisibility(View.VISIBLE);
                        txt_signature.setVisibility(View.GONE);
                        String strFileName = buttonKycSignatureProof.getTag().toString().substring(buttonKycSignatureProof.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonKycSignatureProof.setText(R.string.preview);
                            imageViewKycSignatureProof.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonKycSignatureProof.setText(R.string.download);
                            imageViewKycSignatureProof.setBackgroundResource(R.drawable.zip_image);

                        }

//                        Picasso.with(context).load(String.valueOf(bm)).into(imageViewKycSignatureProof);
                    } else if (s.equalsIgnoreCase("9")) {
                        buttonKycProfilePhoto.setVisibility(View.VISIBLE);
                        buttonKycProfilePhoto.setText(R.string.preview);
                        buttonKycProfilePhoto.setTag(baseUrl + image);
                        imageViewUploadTick_9.setVisibility(View.VISIBLE);
                        txt_kyc_profilephoto.setVisibility(View.GONE);
//                        imageViewKycProfilePhoto.setBackgroundResource(R.drawable.pdf_image);
                        Picasso.with(context).load(baseUrl + image).into(imageViewKycProfilePhoto);
                    }
                }

                JSONArray jsonArray1 = jsonObject.getJSONArray("Financial");
                Log.e(TAG, "getDocuments: " + jsonArray1);
                for (int i = 0; i < jsonArray1.length(); i++) {
                    Log.e(TAG, "FOR LOOP: " + i);
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");

                    if (s.equalsIgnoreCase("4")) {
                        Log.e(TAG, "IF LOOP : 4");
                        buttonFinanceIncomeProof.setVisibility(View.VISIBLE);
                        buttonFinanceIncomeProof.setTag(baseUrl + image);
                        imageViewUploadTick_4.setVisibility(View.VISIBLE);
                        txt_income.setVisibility(View.GONE);
                        String strFileName = buttonFinanceIncomeProof.getTag().toString().substring(buttonFinanceIncomeProof.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonFinanceIncomeProof.setText(R.string.preview);
                            imageViewFinanceIncomeProof.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonFinanceIncomeProof.setText(R.string.download);
                            imageViewFinanceIncomeProof.setBackgroundResource(R.drawable.zip_image);
                        }
//                        Picasso.with(context).load(baseUrl + image).into(imageViewFinanceIncomeProof);
                    } else if (s.equalsIgnoreCase("5")) {
                        buttonFinanceBankStatement.setVisibility(View.VISIBLE);
                        buttonFinanceBankStatement.setTag(baseUrl + image);
                        imageViewUploadTick_5.setVisibility(View.VISIBLE);
                        txt_bank.setVisibility(View.GONE);
                        String strFileName = buttonFinanceBankStatement.getTag().toString().substring(buttonFinanceBankStatement.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonFinanceBankStatement.setText(R.string.preview);
                            imageViewFinanceBankStatement.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonFinanceBankStatement.setText(R.string.download);
                            imageViewFinanceBankStatement.setBackgroundResource(R.drawable.zip_image);

                        }
//                        Picasso.with(context).load(baseUrl + image).into(imageViewFinanceBankStatement);
                    }
                }

                JSONArray jsonArray2 = jsonObject.getJSONArray("Education");
                for (int i = 0; i < jsonArray2.length(); i++) {
                    JSONObject jsonObject1 = jsonArray2.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");

                    if (s.equalsIgnoreCase("6")) {
                        buttonEducationDegreeMarksheets.setVisibility(View.VISIBLE);
                        buttonEducationDegreeMarksheets.setTag(baseUrl + image);
                        imageViewUploadTick_6.setVisibility(View.VISIBLE);
                        txt_degreemarksheets.setVisibility(View.GONE);
                        String strFileName = buttonEducationDegreeMarksheets.getTag().toString().substring(buttonEducationDegreeMarksheets.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonEducationDegreeMarksheets.setText(R.string.preview);
                            imageViewEducationDegreeMarksheets.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonEducationDegreeMarksheets.setText(R.string.download);
                            imageViewEducationDegreeMarksheets.setBackgroundResource(R.drawable.zip_image);

                        }

//                        Picasso.with(context).load(baseUrl + image).into(imageViewEducationDegreeMarksheets);
                    } else if (s.equalsIgnoreCase("7")) {
                        buttonEducationDegreeCertificate.setVisibility(View.VISIBLE);
                        buttonEducationDegreeCertificate.setTag(baseUrl + image);
                        imageViewUploadTick_7.setVisibility(View.VISIBLE);
                        txt_degreecerti.setVisibility(View.GONE);
                        String strFileName = buttonEducationDegreeCertificate.getTag().toString().substring(buttonEducationDegreeCertificate.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonEducationDegreeCertificate.setText(R.string.preview);
                            imageViewEducationDegreeCertificate.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonEducationDegreeCertificate.setText(R.string.download);
                            imageViewEducationDegreeCertificate.setBackgroundResource(R.drawable.zip_image);
                        }
//                        Picasso.with(context).load(baseUrl + image).into(imageViewEducationDegreeCertificate);
                    }
                }

                JSONArray jsonArray3 = jsonObject.getJSONArray("Other");
                for (int i = 0; i < jsonArray3.length(); i++) {
                    JSONObject jsonObject1 = jsonArray3.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");

                    if (s.equalsIgnoreCase("8")) {
                        buttonOtherDocument.setVisibility(View.VISIBLE);
                        buttonOtherDocument.setTag(baseUrl + image);
                        imageViewUploadTick_8.setVisibility(View.VISIBLE);
                        txt_otherdocument.setVisibility(View.GONE);
                        String strFileName = buttonOtherDocument.getTag().toString().substring(buttonOtherDocument.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonOtherDocument.setText(R.string.preview);
                            imageViewOtherDocument.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonOtherDocument.setText(R.string.download);
                            imageViewOtherDocument.setBackgroundResource(R.drawable.zip_image);
                        }
//                      Picasso.with(context).load(baseUrl + image).into(imageViewOtherDocument);
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
                Log.e(TAG, "coBorrowerID: " + coBorrowerID);

                JSONArray jsonArray = jsonObject.getJSONArray("KycDocument");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");
                    Log.e(TAG, "TYPENO: " + s);
                    Log.e(TAG, "image: " + image);
                    if (s.equalsIgnoreCase("1")) {
                        buttonKycPhotoId_co.setVisibility(View.VISIBLE);
                        buttonKycPhotoId_co.setTag(baseUrl + image);
                        imageViewUploadTick_1_co.setVisibility(View.VISIBLE);
                        txt_kyc_photoId_co.setVisibility(View.GONE);
                        String strFileName = buttonKycPhotoId_co.getTag().toString().substring(buttonKycPhotoId_co.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonKycPhotoId_co.setText(R.string.preview);
                            imageViewKycPhotoId_co.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonKycPhotoId_co.setText(R.string.download);
                            imageViewKycPhotoId_co.setBackgroundResource(R.drawable.zip_image);
                        }
//                        Picasso.with(context).load(baseUrl + image).into(imageViewKycPhotoId_co);
                    } else if (s.equalsIgnoreCase("3")) {
                        buttonKycAddressProof_co.setVisibility(View.VISIBLE);
                        buttonKycAddressProof_co.setTag(baseUrl + image);
                        imageViewUploadTick_2_co.setVisibility(View.VISIBLE);
                        txt_address_co.setVisibility(View.GONE);
                        String strFileName = buttonKycAddressProof_co.getTag().toString().substring(buttonKycAddressProof_co.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonKycAddressProof_co.setText(R.string.preview);
                            imageViewKycAddressProof_co.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonKycAddressProof_co.setText(R.string.download);
                            imageViewKycAddressProof_co.setBackgroundResource(R.drawable.zip_image);
                        }
//                        Picasso.with(context).load(baseUrl + image).into(imageViewKycAddressProof_co);
                    } else if (s.equalsIgnoreCase("2")) {
                        buttonKycSignatureProof_co.setVisibility(View.VISIBLE);
                        buttonKycSignatureProof_co.setTag(baseUrl + image);
                        imageViewUploadTick_3_co.setVisibility(View.VISIBLE);
                        txt_signature_co.setVisibility(View.GONE);
                        String strFileName = buttonKycSignatureProof_co.getTag().toString().substring(buttonKycSignatureProof_co.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonKycSignatureProof_co.setText(R.string.preview);
                            imageViewKycSignatureProof_co.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonKycSignatureProof_co.setText(R.string.download);
                            imageViewKycSignatureProof_co.setBackgroundResource(R.drawable.zip_image);
                        }
//                        Picasso.with(context).load(baseUrl + image).into(imageViewKycSignatureProof_co);
                    } else if (s.equalsIgnoreCase("9")) {
                        buttonKycProfilePhoto_co.setVisibility(View.VISIBLE);
                        buttonKycProfilePhoto_co.setText(R.string.preview);
                        buttonKycProfilePhoto_co.setTag(baseUrl + image);
                        imageViewUploadTick_9_co.setVisibility(View.VISIBLE);
                        txt_kyc_profilephoto_co.setVisibility(View.GONE);
//                        imageViewKycProfilePhoto_co.setBackgroundResource(R.drawable.pdf_image);
                        Picasso.with(context).load(baseUrl + image).into(imageViewKycProfilePhoto_co);
                    }
                }

                JSONArray jsonArray1 = jsonObject.getJSONArray("Financial");
                Log.e(TAG, "getDocuments: " + jsonArray1);
                for (int i = 0; i < jsonArray1.length(); i++) {
                    Log.e(TAG, "FOR LOOP: " + i);
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");

                    if (s.equalsIgnoreCase("4")) {
                        Log.e(TAG, "IF LOOP : 4");
                        buttonFinanceIncomeProof_co.setVisibility(View.VISIBLE);
                        buttonFinanceIncomeProof_co.setTag(baseUrl + image);
                        imageViewUploadTick_4_co.setVisibility(View.VISIBLE);
                        txt_income_co.setVisibility(View.GONE);
                        String strFileName = buttonFinanceIncomeProof_co.getTag().toString().substring(buttonFinanceIncomeProof_co.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonFinanceIncomeProof_co.setText(R.string.preview);
                            imageViewFinanceIncomeProof_co.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonFinanceIncomeProof_co.setText(R.string.download);
                            imageViewFinanceIncomeProof_co.setBackgroundResource(R.drawable.zip_image);
                        }
//                        Picasso.with(context).load(baseUrl + image).into(imageViewFinanceIncomeProof_co);
                    } else if (s.equalsIgnoreCase("5")) {
                        buttonFinanceBankStatement_co.setVisibility(View.VISIBLE);
                        buttonFinanceBankStatement_co.setTag(baseUrl + image);
                        imageViewUploadTick_5_co.setVisibility(View.VISIBLE);
                        txt_bank_co.setVisibility(View.GONE);
                        String strFileName = buttonFinanceBankStatement_co.getTag().toString().substring(buttonFinanceBankStatement_co.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonFinanceBankStatement_co.setText(R.string.preview);
                            imageViewFinanceBankStatement_co.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonFinanceBankStatement_co.setText(R.string.download);
                            imageViewFinanceBankStatement_co.setBackgroundResource(R.drawable.zip_image);
                        }
//                        Picasso.with(context).load(baseUrl + image).into(imageViewFinanceBankStatement_co);
                    }
                }


                JSONArray jsonArray3 = jsonObject.getJSONArray("Other");
                for (int i = 0; i < jsonArray3.length(); i++) {
                    JSONObject jsonObject1 = jsonArray3.getJSONObject(i);
                    String s = jsonObject1.getString("document_type_id");
                    String image = jsonObject1.getString("document_path");

                    if (s.equalsIgnoreCase("8")) {
                        buttonOtherDocument_co.setVisibility(View.VISIBLE);
                        buttonOtherDocument_co.setText(R.string.preview);
                        buttonOtherDocument_co.setTag(baseUrl + image);
                        imageViewUploadTick_8_co.setVisibility(View.VISIBLE);
                        txt_otherdocument_co.setVisibility(View.GONE);
                        String strFileName = buttonOtherDocument_co.getTag().toString().substring(buttonOtherDocument_co.getTag().toString().lastIndexOf("/") + 1);

                        String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                        if (FileExtn.equals("pdf")) {
                            buttonOtherDocument_co.setText(R.string.preview);
                            imageViewOtherDocument_co.setBackgroundResource(R.drawable.pdf_image);
                        } else {
                            buttonOtherDocument_co.setText(R.string.download);
                            imageViewOtherDocument_co.setBackgroundResource(R.drawable.zip_image);
                        }
//                        Picasso.with(context).load(baseUrl + image).into(imageViewOtherDocument_co);
                    }
                }

            } else {
//                JSONObject jsonObject = jsonData.getJSONObject("result");
//                coBorrowerID = jsonObject.getString("coBorrowerId");
//                Log.e(MainApplication.TAG, "coBorrowerID: "+coBorrowerID );
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("coBorrowerID", coBorrowerID);
            editor.apply();
            editor.commit();

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
                if (!jsonObject.getString("docPath").equalsIgnoreCase("")) {
                    editor.putString("signed_application_url", jsonObject.getString("docPath"));
                }
                editor.putString("signed_appstatus", String.valueOf(signedAppStatus));
                editor.apply();
                editor.commit();

                if (doc_finish == 1) {
                    LoanApplicationFragment_4 loanApplicationFragment_4 = new LoanApplicationFragment_4();
                    transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_4).commit();
                } else {
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                }


            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void downLoadClick(String uri) {
        try {
            Handler handler = new Handler();  //Optional. Define as a variable in your activity.

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    // your code here
                    handler.post(new Runnable()  //If you want to update the UI, queue the code on the UI thread
                    {
                        public void run() {
                            Log.e(TAG, "downloadUrl+++++: " + uri);
                            Uri Download_Uri = Uri.parse(String.valueOf(uri));

//                            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
//                            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                            request.setAllowedOverRoaming(false);
//                            request.setTitle("Downloading Document");
//                            request.setVisibleInDownloadsUi(true);
//                            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "Eduvanz" + "/" + "UploadedDoc" + "/");
////            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Eduvanz" + "/" + "UploadedDoc" + "/");
//                            progressBar.setVisibility(View.VISIBLE);
////                            downLoad(String.valueOf(uri));
                            try {
                                String fname = "";
                                fname = uri.toString().substring(uri.toString().lastIndexOf("/") + 1);

                                downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);

                                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

                                //Restrict the types of networks over which this download may proceed.
                                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                                //Set whether this download may proceed over a roaming connection.
                                request.setAllowedOverRoaming(false);
                                //Set the title of this download, to be displayed in notifications (if enabled).
                                request.setTitle("Your Document is Downloading");
                                //Set a description of this download, to be displayed in notifications (if enabled)
                                request.setDescription("Android Data download using DownloadManager.");

                                request.setVisibleInDownloadsUi(true);

                                request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "Eduvanz" + "/" + "UploadedDoc" + "/" + fname);//

                                progressBar.setVisibility(View.VISIBLE);

                                //Enqueue a new download and same the referenceId
                                downloadReference = downloadManager.enqueue(request);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            };

            Thread t = new Thread(r);
            t.start();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void downLoad(String uri) {
        try {
            String fname = "";
            fname = uri.toString().substring(uri.toString().lastIndexOf("/") + 1);

            downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);
            Uri Download_Uri = Uri.parse(uri);
            DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

            //Restrict the types of networks over which this download may proceed.
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
            //Set whether this download may proceed over a roaming connection.
            request.setAllowedOverRoaming(false);
            //Set the title of this download, to be displayed in notifications (if enabled).
            request.setTitle("Your Document is Downloading");
            //Set a description of this download, to be displayed in notifications (if enabled)
            request.setDescription("Android Data download using DownloadManager.");

            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "Eduvanz" + "/" + "UploadedDoc" + "/" + fname);//

            //Enqueue a new download and same the referenceId
            downloadReference = downloadManager.enqueue(request);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
