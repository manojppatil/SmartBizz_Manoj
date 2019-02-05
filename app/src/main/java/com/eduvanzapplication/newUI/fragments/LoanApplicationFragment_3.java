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
import static com.eduvanzapplication.newUI.MainApplication.student_id;
import static com.facebook.FacebookSdk.getApplicationContext;
import static vijay.createpdf.util.StringUtils.showSnackbar;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */

//"baseUrl": "http://159.89.204.41/eduvanzbeta/" 277 1
//{
//            "fk_document_type_id": "3",
//            "doc_path": "uploads/document/277/Aadhar+Card_217_1549202914.pdf",
//            "verification_status": "0",
//            "document_name": "Aadhar Card"
//        },
//        {
//            "fk_document_type_id": "1",
//            "doc_path": "uploads/document/277/Photo_217_1549103164.png",
//            "verification_status": "0",
//            "document_name": "Photo"
//        }

//"baseUrl": "http://159.89.204.41/eduvanzbeta/"  278 2
// {
//            "fk_document_type_id": "19",
//            "doc_path": "uploads/document/278/Bank+Statement+-+3+Months_217_1549216296.pdf",
//            "verification_status": "0",
//            "document_name": "Bank Statement - 3 Months"
//        },
//        {
//            "fk_document_type_id": "31",
//            "doc_path": "uploads/document/278/Others_217_1549216140.pdf",
//            "verification_status": "0",
//            "document_name": "Others"
//        },
//        {
//            "fk_document_type_id": "18",
//            "doc_path": "uploads/document/278/Salary+Slip+-+3+Months_217_1549216036.pdf",
//            "verification_status": "0",
//            "document_name": "Salary Slip - 3 Months"
//        },
//        {
//            "fk_document_type_id": "23",
//            "doc_path": "uploads/document/278/Last+completed+degree+Mark+sheet_217_1549215894.pdf",
//            "verification_status": "0",
//            "document_name": "Last completed degree Mark sheet"
//        },
//        {
//            "fk_document_type_id": "3",
//            "doc_path": "uploads/document/278/Aadhar+Card_217_1549215755.pdf",
//            "verification_status": "0",
//            "document_name": "Aadhar Card"
//        },
//        {
//            "fk_document_type_id": "2",
//            "doc_path": "uploads/document/278/PAN+Card_217_1549215599.pdf",
//            "verification_status": "0",
//            "document_name": "PAN Card"
//        },
//        {
//            "fk_document_type_id": "30",
//            "doc_path": "uploads/document/278/Address+Proof_217_1549215552.pdf",
//            "verification_status": "0",
//            "document_name": "Address Proof"
//        },
//        {
//            "fk_document_type_id": "1",
//            "doc_path": "uploads/document/278/Photo_217_1549214353.jpg",
//            "verification_status": "0",
//            "document_name": "Photo"
//        },
//        {
//            "fk_document_type_id": "3",
//            "doc_path": "uploads/document/278/A190126008_Aadhar_Card_1549111411.png",
//            "verification_status": "0",
//            "document_name": "Aadhar Card"
//        }


public class LoanApplicationFragment_3 extends Fragment {

    private static final int PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE_RESULT = 1;
    public static Fragment mFragment;
    public static String coBorrowerID = "", baseUrl = "";
    static Context context;
    static Typeface typefaceFont, typefaceFontBold, typeface;
    static TextView textView1, textView2, textView3, textViewArrowDown, textViewArrowDownCo;
    static TextView txtPhoto1, txtAadhaar3, txtPan2, txtAddress38, txtSalarySlip18, txtBankStmt19, txtDegreeMarkSheet23,
            txtDegreeCerti24, txtOtherDoc31,
            txtPhoto1CoBr, txtAadhaar3CoBr, txtPan2CoBr, txtAddress30CoBr, txtSalarySlip18CoBr, txtBankStmt19CoBr, txtOtherDoc31CoBr;
    static View view;
    static ImageView imgAadhaarUploadTick3, imgPanUploadTick2, imgAddressUploadTick38,
            imgSalarySlipUploadTick18, imgBankStmtUploadTick19, imgDegreeMarkSheetUploadTick23, imgDegreeCertiUploadTick24,
            imgOtherDocUploadTick31, imgPhotoUploadTick;
    static Button btnPhoto1, btnAadhaar3, btnPan2, btnAddress38,
            btnSalarySlip18, btnBankStmt19, btnDegreeMarkSheet23, btnDegreeCerti24,
            buttonOtherDocument;
    static ImageView imgPhoto1, imgAadhaar3, imgPan2, imgAddress38,
            imgSalarySlip18, imgBankStmt19, imgDegreeMarkSheet23, imgDegreeCerti24,
            imgOtherDoc31;
    static ImageView imgPhotoUploadTickCoBr, imageViewUploadTick_1_co, imgPanUploadTick2CoBr, imgAddressUploadTick30CoBr,
            imgSalarySlipUploadTick18CoBr, imgBankStmtUploadTick19CoBr, imgOtherDocUploadTick31CoBr;
    static Button btnPhoto1CoBr, btnAadhaar3CoBr, btnPan2CoBr, btnAddress30CoBr,
            btnSalarySlip18CoBr, btnBankStmt19CoBr, btnOtherDoc31CoBr;

    static ProgressBar progressBar;
    static int doc_finish, signedAppStatus;
    static String lafDownloadPath = "";
    static FragmentTransaction transaction;
    static ImageView imgPhoto1CoBr, imgAadhaar3CoBr, imgPan2CoBr, imgAddress30CoBr,
            imgSalarySlip18CoBr, imgBankStmt19CoBr, imgOtherDoc31CoBr;
    static ImageView imageViewProfilePicSelect;
    public Boolean kyc = true, financial = true, education = true, other = true;
    public String applicantType = "", documentTypeNo = "", userID = "";
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
                    Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
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

            txtPhoto1 = (TextView) view.findViewById(R.id.txtPhoto1);
            txtAadhaar3 = (TextView) view.findViewById(R.id.txtAadhaar3);
            txtPan2 = (TextView) view.findViewById(R.id.txtPan2);
            txtAddress38 = (TextView) view.findViewById(R.id.txtAddress38);
            txtSalarySlip18 = (TextView) view.findViewById(R.id.txtSalarySlip18);
            txtBankStmt19 = (TextView) view.findViewById(R.id.txtBankStmt19);
            txtDegreeMarkSheet23 = (TextView) view.findViewById(R.id.txtDegreeMarkSheet23);
            txtDegreeCerti24 = (TextView) view.findViewById(R.id.txtDegreeCerti24);
            txtOtherDoc31 = (TextView) view.findViewById(R.id.txtOtherDoc31);

            txtPhoto1CoBr = (TextView) view.findViewById(R.id.txtPhoto1CoBr);
            txtAadhaar3CoBr = (TextView) view.findViewById(R.id.txtAadhaar3CoBr);
            txtPan2CoBr = (TextView) view.findViewById(R.id.txtPan2CoBr);
            txtAddress30CoBr = (TextView) view.findViewById(R.id.txtAddress30CoBr);
            txtSalarySlip18CoBr = (TextView) view.findViewById(R.id.txtSalarySlip18CoBr);
            txtBankStmt19CoBr = (TextView) view.findViewById(R.id.txtBankStmt19CoBr);
            txtOtherDoc31CoBr = (TextView) view.findViewById(R.id.txtOtherDoc31CoBr);

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

            imgPhotoUploadTick = (ImageView) view.findViewById(R.id.imgPhotoUploadTick);
            imgAadhaarUploadTick3 = (ImageView) view.findViewById(R.id.imgAadhaarUploadTick3);
            imgPanUploadTick2 = (ImageView) view.findViewById(R.id.imgPanUploadTick2);
            imgAddressUploadTick38 = (ImageView) view.findViewById(R.id.imgAddressUploadTick38);
            imgSalarySlipUploadTick18 = (ImageView) view.findViewById(R.id.imgSalarySlipUploadTick18);
            imgBankStmtUploadTick19 = (ImageView) view.findViewById(R.id.imgBankStmtUploadTick19);
            imgDegreeMarkSheetUploadTick23 = (ImageView) view.findViewById(R.id.imgDegreeMarkSheetUploadTick23);
            imgDegreeCertiUploadTick24 = (ImageView) view.findViewById(R.id.imgDegreeCertiUploadTick24);
            imgOtherDocUploadTick31 = (ImageView) view.findViewById(R.id.imgOtherDocUploadTick31);

            imgPhotoUploadTickCoBr = (ImageView) view.findViewById(R.id.imgPhotoUploadTickCoBr);
            imageViewUploadTick_1_co = (ImageView) view.findViewById(R.id.imgAadhaarUploadTick3CoBr);
            imgPanUploadTick2CoBr = (ImageView) view.findViewById(R.id.imgPanUploadTick2CoBr);
            imgAddressUploadTick30CoBr = (ImageView) view.findViewById(R.id.imgAddressUploadTick30CoBr);
            imgSalarySlipUploadTick18CoBr = (ImageView) view.findViewById(R.id.imgSalarySlipUploadTick18CoBr);
            imgBankStmtUploadTick19CoBr = (ImageView) view.findViewById(R.id.imgBankStmtUploadTick19CoBr);
            imgOtherDocUploadTick31CoBr = (ImageView) view.findViewById(R.id.imgOtherDocUploadTick31CoBr);

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
                        Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
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
            imgPhoto1 = (ImageView) view.findViewById(R.id.imgPhoto1);
            imgPhoto1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgPhotoUploadTick.setVisibility(View.GONE);
                    applicantType = "1";
                    documentTypeNo = "1";
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_profile_picture), getString(R.string.applicant_single_picture_required_to_be_uploaded), MainApplication.Brapplicant_idkyc, "1");
                    btnPhoto1.setText(R.string.upload);
                }
            });
            btnPhoto1 = (Button) view.findViewById(R.id.btnPhoto1);
            btnPhoto1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnPhoto1.getTag().toString().substring(btnPhoto1.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(String.valueOf(btnPhoto1.getTag()));

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnPhoto1.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnPhoto1.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
            });
            /**----------------------------END OF KYC - PROFILE PHOTO--------------------------------**/


            /**--------------------------------KYC - PHOTO ID----------------------------------------**/
            imgAadhaar3 = (ImageView) view.findViewById(R.id.imgAadhaar3);

            imgAadhaar3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgAadhaarUploadTick3.setVisibility(View.GONE);
                    applicantType = "1";
                    documentTypeNo = "3";
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_adhaar_card), getString(R.string.applicant_adhaar_card_front_and_backside), MainApplication.Brapplicant_idkyc, "1");
                    btnAadhaar3.setText(R.string.upload);
                }
            });
            btnAadhaar3 = (Button) view.findViewById(R.id.btnAadhaar3);
            btnAadhaar3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnAadhaar3.getTag().toString().substring(btnAadhaar3.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    File filepath1 = new File("sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        try {
                            openPdf(btnAadhaar3.getTag().toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnAadhaar3.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnAadhaar3.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**-------------------------------END OF KYC - PHOTO ID----------------------------------**/

            /**--------------------------------KYC - ADDRESS PROOF-----------------------------------**/
            imgPan2 = (ImageView) view.findViewById(R.id.imgPan2);
            imgPan2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgPanUploadTick2.setVisibility(View.GONE);
                    //Earlier used
                    applicantType = "1";
                    documentTypeNo = "2";
                    btnPan2.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_pan_card), getString(R.string.applicant_pan_card), MainApplication.Brapplicant_idkyc, "1");

                }
            });
            btnPan2 = (Button) view.findViewById(R.id.btnPan2);
            btnPan2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnPan2.getTag().toString().substring(btnPan2.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnPan2.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnPan2.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnPan2.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**-------------------------------END OF KYC - ADDRESS PROOF-----------------------------**/

            /**--------------------------------KYC - SIGNATURE PROOF---------------------------------**/
            imgAddress38 = (ImageView) view.findViewById(R.id.imgAddress38);
            imgAddress38.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgAddressUploadTick38.setVisibility(View.GONE);
                    //Earlier used
                    applicantType = "1";
                    documentTypeNo = "30";
                    btnAddress38.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_address_proof), getString(R.string.applicant_address_proof), MainApplication.Brapplicant_idkyc, "1");
                }
            });
            btnAddress38 = (Button) view.findViewById(R.id.btnAddress38);
            btnAddress38.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnAddress38.getTag().toString().substring(btnAddress38.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnAddress38.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnAddress38.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnAddress38.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**-------------------------------END OF KYC - SIGNATURE PROOF---------------------------**/

            /**-------------------------------FINANCE - INCOME PROOF---------------------------------**/
            imgSalarySlip18 = (ImageView) view.findViewById(R.id.imgSalarySlip18);
            imgSalarySlip18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgSalarySlipUploadTick18.setVisibility(View.GONE);
                    applicantType = "1";
                    documentTypeNo = "18";
                    btnSalarySlip18.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_income_proof), getString(R.string.salary_slip_of_applicant_latest_3_months_if_not_available_salary_certificate_stating_the_same_details_would_be_accepted), MainApplication.Brapplicant_idkyc, "1");

                }
            });
            btnSalarySlip18 = (Button) view.findViewById(R.id.btnSalarySlip18);
            btnSalarySlip18.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnSalarySlip18.getTag().toString().substring(btnSalarySlip18.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnSalarySlip18.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnSalarySlip18.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnSalarySlip18.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**------------------------------END OF FINANCE - INCOME PROOF---------------------------**/

            /**-------------------------------FINANCE - BANK STATEMENT-------------------------------**/
            imgBankStmt19 = (ImageView) view.findViewById(R.id.imgBankStmt19);
            imgBankStmt19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgBankStmtUploadTick19.setVisibility(View.GONE);
                    applicantType = "1";
                    documentTypeNo = "19";
                    btnBankStmt19.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_bank_statement), getString(R.string.current_3_months_bank_statement_of_applicant_reflecting_salary_along_with_the_front_page), MainApplication.Brapplicant_idkyc, "1");
                }
            });
            btnBankStmt19 = (Button) view.findViewById(R.id.btnBankStmt19);
            btnBankStmt19.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnBankStmt19.getTag().toString().substring(btnBankStmt19.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnBankStmt19.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnBankStmt19.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnBankStmt19.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                }
            });
            /**-----------------------------END OF FINANCE - BANK STATEMENT--------------------------**/

            /**-----------------------------EDUCATION - DEGREE MARKSHEETS----------------------------**/
            imgDegreeMarkSheet23 = (ImageView) view.findViewById(R.id.imgDegreeMarkSheet23);
            imgDegreeMarkSheet23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgDegreeMarkSheetUploadTick23.setVisibility(View.GONE);
                    applicantType = "1";
                    documentTypeNo = "23";
                    btnDegreeMarkSheet23.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_latest_marksheet), getString(R.string.latest_marksheet_of_the_applicant), MainApplication.Brapplicant_idkyc, "1");
                }
            });
            btnDegreeMarkSheet23 = (Button) view.findViewById(R.id.btnDegreeMarkSheet23);
            btnDegreeMarkSheet23.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnDegreeMarkSheet23.getTag().toString().substring(btnDegreeMarkSheet23.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnDegreeMarkSheet23.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnDegreeMarkSheet23.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnDegreeMarkSheet23.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**---------------------------END OF EDUCATION - DEGREE MARKSHEETS-----------------------**/

            /**-----------------------------EDUCATION - DEGREE CERTIFICATE---------------------------**/
            imgDegreeCerti24 = (ImageView) view.findViewById(R.id.imgDegreeCerti24);
            imgDegreeCerti24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgDegreeCertiUploadTick24.setVisibility(View.GONE);
                    applicantType = "1";
                    documentTypeNo = "24";
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_latest_certificate), getString(R.string.latest_certificate_of_the_applicant), MainApplication.Brapplicant_idkyc, "1");

                    btnDegreeCerti24.setText(R.string.upload);
                }
            });
            btnDegreeCerti24 = (Button) view.findViewById(R.id.btnDegreeCerti24);
            btnDegreeCerti24.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnDegreeCerti24.getTag().toString().substring(btnDegreeCerti24.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnDegreeCerti24.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnDegreeCerti24.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnDegreeCerti24.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**---------------------------END OF EDUCATION - DEGREE CERTIFICATE----------------------**/

            /**--------------------------------------OTHER DOCUMENT----------------------------------**/
            imgOtherDoc31 = (ImageView) view.findViewById(R.id.imgOtherDoc31);
            imgOtherDoc31.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgOtherDocUploadTick31.setVisibility(View.GONE);
                    applicantType = "1";
                    documentTypeNo = "31";
                    buttonOtherDocument.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_other_document), getString(R.string.recent_utility_bill_if_the_house_is_owned_or_rent_agreement_if_the_house_is_rented_rent_agreement_should_be_noterised), MainApplication.Brapplicant_idkyc, "1");

                }
            });
            buttonOtherDocument = (Button) view.findViewById(R.id.btnOtherDoc31);
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
            imgPhoto1CoBr = (ImageView) view.findViewById(R.id.imgPhoto1CoBr);
            imgPhoto1CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgPhotoUploadTickCoBr.setVisibility(View.GONE);
                    applicantType = "2";
                    documentTypeNo = "1";
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_profile_picture), getString(R.string.co_applicant_single_picture_required_to_be_uploaded), MainApplication.CoBrapplicant_idkyc, "2");
                    btnPhoto1CoBr.setText(R.string.upload);
                }
            });
            btnPhoto1CoBr = (Button) view.findViewById(R.id.btnPhoto1CoBr);
            btnPhoto1CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnPhoto1CoBr.getTag().toString().substring(btnPhoto1CoBr.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(String.valueOf(btnPhoto1CoBr.getTag()));

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnPhoto1CoBr.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnPhoto1CoBr.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**----------------------------END OF KYC - PROFILE PHOTO--------------------------------**/


            /**--------------------------------KYC - PHOTO ID----------------------------------------**/
            imgAadhaar3CoBr = (ImageView) view.findViewById(R.id.imgAadhaar3CoBr);
            imgAadhaar3CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageViewUploadTick_1_co.setVisibility(View.GONE);
                    applicantType = "2";
                    documentTypeNo = "3";
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_adhaar_card), getString(R.string.co_applicant_adhaar_card_front_and_backside), MainApplication.CoBrapplicant_idkyc, "2");
                    btnAadhaar3CoBr.setText(R.string.upload);
                }
            });
            btnAadhaar3CoBr = (Button) view.findViewById(R.id.btnAadhaar3CoBr);
            btnAadhaar3CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnAadhaar3CoBr.getTag().toString().substring(btnAadhaar3CoBr.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnAadhaar3CoBr.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnAadhaar3.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnAadhaar3.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**-------------------------------END OF KYC - PHOTO ID----------------------------------**/

            /**--------------------------------KYC - ADDRESS PROOF-----------------------------------**/
            imgPan2CoBr = (ImageView) view.findViewById(R.id.imgPan2CoBr);
            imgPan2CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgPanUploadTick2CoBr.setVisibility(View.GONE);
                    //User Earlier
                    applicantType = "2";
                    documentTypeNo = "2";
                    btnPan2CoBr.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_pan_card), getString(R.string.co_applicant_pan_card), MainApplication.CoBrapplicant_idkyc, "2");

                }
            });
            btnPan2CoBr = (Button) view.findViewById(R.id.btnPan2CoBr);
            btnPan2CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnPan2CoBr.getTag().toString().substring(btnPan2CoBr.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnPan2CoBr.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnPan2CoBr.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnPan2CoBr.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**-------------------------------END OF KYC - ADDRESS PROOF-----------------------------**/

            /**--------------------------------KYC - SIGNATURE PROOF---------------------------------**/
            imgAddress30CoBr = (ImageView) view.findViewById(R.id.imgAddress30CoBr);
            imgAddress30CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgAddressUploadTick30CoBr.setVisibility(View.GONE);
                    //Used Earlier
                    applicantType = "2";
                    documentTypeNo = "30";
                    btnAddress30CoBr.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_address_proof), getString(R.string.co_applicant_address_proof), MainApplication.CoBrapplicant_idkyc, "2");

                }
            });
            btnAddress30CoBr = (Button) view.findViewById(R.id.btnAddress30CoBr);
            btnAddress30CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnAddress30CoBr.getTag().toString().substring(btnAddress30CoBr.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnAddress30CoBr.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnAddress30CoBr.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnAddress30CoBr.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**-------------------------------END OF KYC - SIGNATURE PROOF---------------------------**/

            /**-------------------------------FINANCE - INCOME PROOF---------------------------------**/
            imgSalarySlip18CoBr = (ImageView) view.findViewById(R.id.imgSalarySlip18CoBr);
            imgSalarySlip18CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgSalarySlipUploadTick18CoBr.setVisibility(View.GONE);
                    applicantType = "2";
                    documentTypeNo = "18";
                    btnSalarySlip18CoBr.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_income_proof), getString(R.string.salary_slip_of_co_applicant_latest_3_months_if_not_available_salary_certificate_stating_the_same_details_would_be_accepted), MainApplication.CoBrapplicant_idkyc, "2");
                }
            });
            btnSalarySlip18CoBr = (Button) view.findViewById(R.id.btnSalarySlip18CoBr);
            btnSalarySlip18CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnSalarySlip18CoBr.getTag().toString().substring(btnSalarySlip18CoBr.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);


                    if (FileExtn.equals("pdf")) {
                        openPdf(btnSalarySlip18CoBr.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnSalarySlip18CoBr.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnSalarySlip18CoBr.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**------------------------------END OF FINANCE - INCOME PROOF---------------------------**/

            /**-------------------------------FINANCE - BANK STATEMENT-------------------------------**/
            imgBankStmt19CoBr = (ImageView) view.findViewById(R.id.imgBankStmt19CoBr);
            imgBankStmt19CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgBankStmtUploadTick19CoBr.setVisibility(View.GONE);
                    applicantType = "2";
                    documentTypeNo = "19";
                    btnBankStmt19CoBr.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_bank_statement), getString(R.string.current_3_months_bank_statement_of_co_applicant_reflecting_salary_along_with_the_front_page), MainApplication.CoBrapplicant_idkyc, "2");
                }
            });
            btnBankStmt19CoBr = (Button) view.findViewById(R.id.btnBankStmt19CoBr);
            btnBankStmt19CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnBankStmt19CoBr.getTag().toString().substring(btnBankStmt19CoBr.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnBankStmt19CoBr.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnBankStmt19CoBr.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnBankStmt19CoBr.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**-----------------------------END OF FINANCE - BANK STATEMENT--------------------------**/

            /**--------------------------------------OTHER DOCUMENT----------------------------------**/
            imgOtherDoc31CoBr = (ImageView) view.findViewById(R.id.imgOtherDoc31CoBr);
            imgOtherDoc31CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgOtherDocUploadTick31CoBr.setVisibility(View.GONE);
                    applicantType = "2";
                    documentTypeNo = "31";
                    btnOtherDoc31CoBr.setText(R.string.upload);
                    //                galleryDocIntent();
                    imageToPdf(documentTypeNo, getString(R.string.upload_other_document), getString(R.string.recent_utility_bill_if_the_house_is_owned_or_rent_agreement_if_the_house_is_rented_rent_agreement_should_be_noterised), MainApplication.CoBrapplicant_idkyc, "2");
                }
            });
            btnOtherDoc31CoBr = (Button) view.findViewById(R.id.btnOtherDoc31CoBr);
            btnOtherDoc31CoBr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/A181017024_Aadhar_Document_1539955596.zip

                    String strFileName = btnOtherDoc31CoBr.getTag().toString().substring(btnOtherDoc31CoBr.getTag().toString().lastIndexOf("/") + 1);

                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                    if (FileExtn.equals("pdf")) {
                        openPdf(btnOtherDoc31CoBr.getTag().toString());

                    } else if (FileExtn.equals("zip") || FileExtn.equals("rar")) {

                        if (filepath.exists()) {

                            Toast.makeText(getActivity(), "File is already downloaded : " + "sdcard/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName, Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                downLoadClick(String.valueOf(btnOtherDoc31CoBr.getTag()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    } else {
                        try {
                            openImage(String.valueOf(btnOtherDoc31CoBr.getTag()));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            });
            /**----------------------------------END OF OTHER DOCUMENT-------------------------------**/

            getUploadDocumentsApiCall();

        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
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
        if (cursor != null) {
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
                                uploadFile(FilePathArraylist.get(0), DoctypeNoArraylist.get(0), "", "");
                            }
                        }
                    }
                }).start();
            } else {
                getUploadDocumentsApiCall();
            }

        } else {
            getUploadDocumentsApiCall();
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
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
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

    private void imageToPdf(String documentTypeNo, String toolbarTitle, String note, String strapplicantId, String strapplicantType) {

        Intent intent = new Intent(getActivity(), ImgToPdfActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("strapplicantId", strapplicantId);
        bundle.putString("strapplicantType", strapplicantType);
        bundle.putString("documentTypeNo", documentTypeNo);
        bundle.putString("toolbarTitle", toolbarTitle);
        bundle.putString("note", note);
        intent.putExtras(bundle);
        startActivityForResult(intent, 2);// Activity is started with requestCode 2
    }

    private void getUploadDocumentsApiCall() {
        /** API CALL **/
        try {
            String url = MainApplication.mainUrl + "document/getDocumentsDetails";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getapplicantDocumentDetails
                params.put("lead_id", MainApplication.lead_id);//"studentId" -> "2953"
//                params.put("fk_applicant_id", MainApplication.Brapplicant_iddtl);//"studentId" -> "2953"
                params.put("fk_applicant_id", MainApplication.Brapplicant_idkyc);//"studentId" -> "2953"
                params.put("applicant_type", "1");//"studentId" -> "2953"
                volleyCall.sendRequest(context, url, null, mFragment, "getDocumentsBorrower", params, MainApplication.auth_token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**API CALL*/
        try {
            String url = MainApplication.mainUrl + "document/getDocumentsDetails";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getCoApplicantDocumentDetails
                params.put("lead_id", MainApplication.lead_id);//"studentId" -> "2953"
//                params.put("fk_applicant_id", MainApplication.CoBrapplicant_iddtl);//"studentId" -> "2953"
                params.put("fk_applicant_id", MainApplication.CoBrapplicant_idkyc);//"studentId" -> "2953"
                params.put("applicant_type", "2");//"studentId" -> "2953"
                volleyCall.sendRequest(context, url, null, mFragment, "getCoBorrowerDocuments", params, MainApplication.auth_token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

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
                String strapplicantType = data.getStringExtra("strapplicantType");
                String strapplicantId = data.getStringExtra("strapplicantId");

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
                                            //uploadFileOffline(uploadFilePath, doctype, doctypeno, selectUrl);

                                        } else {
                                            uploadFile(uploadFilePath, doctypeno, strapplicantType, strapplicantId);
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
                getUploadDocumentsApiCall();
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
//                imgAadhaar3.setImageDrawable(getResources().getDrawable(R.drawable.pdf_image));
                        if (documentTypeNo.equalsIgnoreCase("1")) {
                            btnAadhaar3.setVisibility(View.VISIBLE);
                            imgAadhaar3.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("2")) {
                            btnPan2.setVisibility(View.VISIBLE);
                            imgPan2.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("3")) {
                            btnAddress38.setVisibility(View.VISIBLE);
                            imgAddress38.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("4")) {
                            btnSalarySlip18.setVisibility(View.VISIBLE);
                            imgSalarySlip18.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("5")) {
                            btnBankStmt19.setVisibility(View.VISIBLE);
                            imgBankStmt19.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("6")) {
                            btnDegreeMarkSheet23.setVisibility(View.VISIBLE);
                            imgDegreeMarkSheet23.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("7")) {
                            btnDegreeCerti24.setVisibility(View.VISIBLE);
                            imgDegreeCerti24.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("8")) {
                            buttonOtherDocument.setVisibility(View.VISIBLE);
                            imgOtherDoc31.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("1_co")) {
                            btnAadhaar3.setVisibility(View.VISIBLE);
                            imgAadhaar3CoBr.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("2_co")) {
                            btnPan2CoBr.setVisibility(View.VISIBLE);
                            imgPan2CoBr.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("3_co")) {
                            btnAddress30CoBr.setVisibility(View.VISIBLE);
                            imgAddress30CoBr.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("4_co")) {
                            btnSalarySlip18CoBr.setVisibility(View.VISIBLE);
                            imgSalarySlip18CoBr.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("5_co")) {
                            btnBankStmt19CoBr.setVisibility(View.VISIBLE);
                            imgBankStmt19CoBr.setImageBitmap(bm);
                        } else if (documentTypeNo.equalsIgnoreCase("8_co")) {
                            btnOtherDoc31CoBr.setVisibility(View.VISIBLE);
                            imgOtherDoc31CoBr.setImageBitmap(bm);
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

        if (imageViewProfilePicSelect == imgPhoto1) {
            imageViewProfilePicSelect.setImageBitmap(thumbnail);
            btnPhoto1.setVisibility(View.VISIBLE);
        } else if (imageViewProfilePicSelect == imgPhoto1CoBr) {
            imageViewProfilePicSelect.setImageBitmap(thumbnail);
            btnPhoto1CoBr.setVisibility(View.VISIBLE);
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
        if (imageViewProfilePicSelect == imgPhoto1) {
            imageViewProfilePicSelect.setImageBitmap(bm);
            btnPhoto1.setVisibility(View.VISIBLE);
        } else if (imageViewProfilePicSelect == imgPhoto1CoBr) {
            imageViewProfilePicSelect.setImageBitmap(bm);
            btnPhoto1CoBr.setVisibility(View.VISIBLE);
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

    public int uploadFile(final String selectedFilePath, String doctypeno, String strapplicantType, String strapplicantId) {
        String urlup = MainApplication.mainUrl + "document/documentUpload";

        Log.e(TAG, "urlup++++++: " + urlup);

        int serverResponseCode = 0;
        documentTypeNo = doctypeno;
        Log.e(TAG, "applicantType: " + strapplicantType + "documentTypeNo: " + doctypeno);
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
//                connection.setHeader("Authorization", "Bearer" + MainApplication.auth_token);
                connection.setRequestProperty("Authorization", "Bearer " + MainApplication.auth_token);
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

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);

//                params.put("fk_lead_id", MainApplication.lead_id);
//                params.put("page_id", "1");
//                params.put("fk_applicant_id", MainApplication.application_id);
//                params.put("fk_document_type_id", doctypeno);
//                params.put("myfile", selectedFilePath);
//                params.put("doucment_status", "add");

//                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"fk_lead_id\";fk_lead_id=" + "23" + "" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"fk_lead_id\";fk_lead_id=" + MainApplication.lead_id + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(MainApplication.lead_id);
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"page_id\";page_id=" + "1" + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes("1");
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"fk_applicant_id\";fk_applicant_id=" + strapplicantId + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(strapplicantId);
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"fk_document_type_id\";fk_document_type_id=" + documentTypeNo + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(documentTypeNo);
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);


                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"doucment_status\";doucment_status=" + "add" + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes("add");
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

                                getUploadDocumentsApiCall();

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
            String baseUrl = String.valueOf(jsonData.getJSONObject("result").get("baseUrl"));
            if (status.equalsIgnoreCase("1")) {
                String strFileName, FileExtn;

                Boolean bPhoto = true, bAadhaar = true, bPan = true, bAddress = true, bSalSlip = true,
                        bBankStmt = true, bDregreeMarkSheet = true, bDegreeCerti = true, bOtherDoc = true;

                JSONArray jsonArray = jsonData.getJSONArray("uploaded_files");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    String s = jsonObject1.getString("fk_document_type_id");
                    String image = jsonObject1.getString("doc_path");
                    String verification_status = jsonObject1.getString("verification_status");
                    String document_name = jsonObject1.getString("document_name");


                    Log.e(TAG, "TYPENO: " + s);
                    Log.e(TAG, "image: " + image);

                    switch (s) {

                        case "1":
                            if (bPhoto) {
                                btnPhoto1.setVisibility(View.VISIBLE);
                                btnPhoto1.setText(R.string.preview);
                                btnPhoto1.setTag(baseUrl + image);
                                imgPhotoUploadTick.setVisibility(View.VISIBLE);
                                txtPhoto1.setVisibility(View.GONE);
//                              imgPhoto1.setBackgroundResource(R.drawable.pdf_image);
                                Picasso.with(context).load(baseUrl + image).into(imgPhoto1);
                                bPhoto = false;
                            }
                            break;

                        case "3":
                            if (bAadhaar) {
                                btnAadhaar3.setVisibility(View.VISIBLE);
                                btnAadhaar3.setTag(baseUrl + image);
                                imgAadhaarUploadTick3.setVisibility(View.VISIBLE);
                                txtAadhaar3.setVisibility(View.GONE);

                                strFileName = btnAadhaar3.getTag().toString().substring(btnAadhaar3.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnAadhaar3.setText(R.string.preview);
                                    imgAadhaar3.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnAadhaar3.setText(R.string.download);
                                    imgAadhaar3.setBackgroundResource(R.drawable.zip_image);
                                }
                                bAadhaar = false;
                            }
                            break;

                        case "2":
                            if (bPan) {
                                btnPan2.setVisibility(View.VISIBLE);
                                btnPan2.setTag(baseUrl + image);
                                imgPanUploadTick2.setVisibility(View.VISIBLE);
                                txtPan2.setVisibility(View.GONE);
                                strFileName = btnPan2.getTag().toString().substring(btnPan2.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnPan2.setText(R.string.preview);
                                    imgPan2.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnPan2.setText(R.string.download);
                                    imgPan2.setBackgroundResource(R.drawable.zip_image);

                                }
//                        Picasso.with(context).load(String.valueOf(bm)).into(imgPan2);
                                bPan = false;
                            }
                            break;

                        case "30":
                            if (bAddress) {
                                btnAddress38.setVisibility(View.VISIBLE);
                                btnAddress38.setTag(baseUrl + image);
                                imgAddressUploadTick38.setVisibility(View.VISIBLE);
                                txtAddress38.setVisibility(View.GONE);
                                strFileName = btnAddress38.getTag().toString().substring(btnAddress38.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnAddress38.setText(R.string.preview);
                                    imgAddress38.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnAddress38.setText(R.string.download);
                                    imgAddress38.setBackgroundResource(R.drawable.zip_image);
                                }

//                        Picasso.with(context).load(String.valueOf(bm)).into(imgAddress38);
                                bAddress = false;
                            }
                            break;

                        case "18":

                            if (bSalSlip) {
                                btnSalarySlip18.setVisibility(View.VISIBLE);
                                btnSalarySlip18.setTag(baseUrl + image);
                                imgSalarySlipUploadTick18.setVisibility(View.VISIBLE);
                                txtSalarySlip18.setVisibility(View.GONE);
                                strFileName = btnSalarySlip18.getTag().toString().substring(btnSalarySlip18.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnSalarySlip18.setText(R.string.preview);
                                    imgSalarySlip18.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnSalarySlip18.setText(R.string.download);
                                    imgSalarySlip18.setBackgroundResource(R.drawable.zip_image);
                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgSalarySlip18);
                                bSalSlip = false;
                            }
                            break;

                        case "19":

                            if (bBankStmt) {
                                btnBankStmt19.setVisibility(View.VISIBLE);
                                btnBankStmt19.setTag(baseUrl + image);
                                imgBankStmtUploadTick19.setVisibility(View.VISIBLE);
                                txtBankStmt19.setVisibility(View.GONE);
                                strFileName = btnBankStmt19.getTag().toString().substring(btnBankStmt19.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnBankStmt19.setText(R.string.preview);
                                    imgBankStmt19.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnBankStmt19.setText(R.string.download);
                                    imgBankStmt19.setBackgroundResource(R.drawable.zip_image);

                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgBankStmt19);
                                bBankStmt = false;
                            }

                            break;

                        case "23":

                            if (bDregreeMarkSheet) {
                                btnDegreeMarkSheet23.setVisibility(View.VISIBLE);
                                btnDegreeMarkSheet23.setTag(baseUrl + image);
                                imgDegreeMarkSheetUploadTick23.setVisibility(View.VISIBLE);
                                txtDegreeMarkSheet23.setVisibility(View.GONE);
                                strFileName = btnDegreeMarkSheet23.getTag().toString().substring(btnDegreeMarkSheet23.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnDegreeMarkSheet23.setText(R.string.preview);
                                    imgDegreeMarkSheet23.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnDegreeMarkSheet23.setText(R.string.download);
                                    imgDegreeMarkSheet23.setBackgroundResource(R.drawable.zip_image);

                                }
                                //                        Picasso.with(context).load(baseUrl + image).into(imgDegreeMarkSheet23);
                                bDregreeMarkSheet = false;
                            }
                            break;

                        case "24":

                            if (bDegreeCerti) {
                                btnDegreeCerti24.setVisibility(View.VISIBLE);
                                btnDegreeCerti24.setTag(baseUrl + image);
                                imgDegreeCertiUploadTick24.setVisibility(View.VISIBLE);
                                txtDegreeCerti24.setVisibility(View.GONE);
                                strFileName = btnDegreeCerti24.getTag().toString().substring(btnDegreeCerti24.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnDegreeCerti24.setText(R.string.preview);
                                    imgDegreeCerti24.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnDegreeCerti24.setText(R.string.download);
                                    imgDegreeCerti24.setBackgroundResource(R.drawable.zip_image);
                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgDegreeCerti24);
                                bDegreeCerti = false;
                            }

                            break;

                        case "31":

                            if (bOtherDoc) {
                                buttonOtherDocument.setVisibility(View.VISIBLE);
                                buttonOtherDocument.setTag(baseUrl + image);
                                imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);
                                strFileName = buttonOtherDocument.getTag().toString().substring(buttonOtherDocument.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    buttonOtherDocument.setText(R.string.preview);
                                    imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    buttonOtherDocument.setText(R.string.download);
                                    imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bOtherDoc = false;
                            }
                            break;

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
            String baseUrl = String.valueOf(jsonData.getJSONObject("result").get("baseUrl"));

            if (status.equalsIgnoreCase("1")) {
                String strFileName, FileExtn;
                Boolean bPhoto = true, bAadhaar = true, bPan = true, bAddress = true, bSalSlip = true,
                        bBankStmt = true, bDregreeMarkSheet = true, bDegreeCerti = true, bOtherDoc = true;

                JSONArray jsonArray = jsonData.getJSONArray("uploaded_files");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    String s = jsonObject1.getString("fk_document_type_id");
                    String image = jsonObject1.getString("doc_path");
                    String verification_status = jsonObject1.getString("verification_status");
                    String document_name = jsonObject1.getString("document_name");
                    Log.e(TAG, "TYPENO: " + s);
                    Log.e(TAG, "image: " + image);

                    switch (s) {

                        case "1":
                            if (bPhoto) {
                                btnPhoto1CoBr.setVisibility(View.VISIBLE);
                                btnPhoto1CoBr.setText(R.string.preview);
                                btnPhoto1CoBr.setTag(baseUrl + image);
                                imgPhotoUploadTickCoBr.setVisibility(View.VISIBLE);
                                txtPhoto1CoBr.setVisibility(View.GONE);
//                        imgPhoto1CoBr.setBackgroundResource(R.drawable.pdf_image);
                                Picasso.with(context).load(baseUrl + image).into(imgPhoto1CoBr);
                                bPhoto = false;
                            }
                            break;


                        case "3":
                            if (bAadhaar) {
                                btnAadhaar3.setVisibility(View.VISIBLE);
                                btnAadhaar3.setTag(baseUrl + image);
                                imageViewUploadTick_1_co.setVisibility(View.VISIBLE);
                                txtAadhaar3CoBr.setVisibility(View.GONE);
                                strFileName = btnAadhaar3.getTag().toString().substring(btnAadhaar3.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnAadhaar3.setText(R.string.preview);
                                    imgAadhaar3CoBr.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnAadhaar3.setText(R.string.download);
                                    imgAadhaar3CoBr.setBackgroundResource(R.drawable.zip_image);
                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgAadhaar3CoBr);
                                bAadhaar = false;
                            }

                            break;

                        case "30":

                            if (bAddress) {
                                btnAddress30CoBr.setVisibility(View.VISIBLE);
                                btnAddress30CoBr.setTag(baseUrl + image);
                                imgAddressUploadTick30CoBr.setVisibility(View.VISIBLE);
                                txtAddress30CoBr.setVisibility(View.GONE);
                                strFileName = btnAddress30CoBr.getTag().toString().substring(btnAddress30CoBr.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnAddress30CoBr.setText(R.string.preview);
                                    imgAddress30CoBr.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnAddress30CoBr.setText(R.string.download);
                                    imgAddress30CoBr.setBackgroundResource(R.drawable.zip_image);
                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgAddress30CoBr);
                                bAddress = false;
                            }

                            break;

                        case "2":

                            if (bPan) {
                                btnPan2CoBr.setVisibility(View.VISIBLE);
                                btnPan2CoBr.setTag(baseUrl + image);
                                imgPanUploadTick2CoBr.setVisibility(View.VISIBLE);
                                txtAadhaar3CoBr.setVisibility(View.GONE);
                                strFileName = btnPan2CoBr.getTag().toString().substring(btnPan2CoBr.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnPan2CoBr.setText(R.string.preview);
                                    imgPan2CoBr.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnPan2CoBr.setText(R.string.download);
                                    imgPan2CoBr.setBackgroundResource(R.drawable.zip_image);
                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgPan2CoBr);
                                bPan = false;
                            }
                            break;

                        case "18":

                            if (bSalSlip) {
                                Log.e(TAG, "IF LOOP : 4");
                                btnSalarySlip18CoBr.setVisibility(View.VISIBLE);
                                btnSalarySlip18CoBr.setTag(baseUrl + image);
                                imgSalarySlipUploadTick18CoBr.setVisibility(View.VISIBLE);
                                txtSalarySlip18CoBr.setVisibility(View.GONE);
                                strFileName = btnSalarySlip18CoBr.getTag().toString().substring(btnSalarySlip18CoBr.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnSalarySlip18CoBr.setText(R.string.preview);
                                    imgSalarySlip18CoBr.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnSalarySlip18CoBr.setText(R.string.download);
                                    imgSalarySlip18CoBr.setBackgroundResource(R.drawable.zip_image);
                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgSalarySlip18CoBr);
                                bSalSlip = false;
                            }

                            break;

                        case "19":

                            if (bBankStmt) {
                                btnBankStmt19CoBr.setVisibility(View.VISIBLE);
                                btnBankStmt19CoBr.setTag(baseUrl + image);
                                imgBankStmtUploadTick19CoBr.setVisibility(View.VISIBLE);
                                txtBankStmt19CoBr.setVisibility(View.GONE);
                                strFileName = btnBankStmt19CoBr.getTag().toString().substring(btnBankStmt19CoBr.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnBankStmt19CoBr.setText(R.string.preview);
                                    imgBankStmt19CoBr.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnBankStmt19CoBr.setText(R.string.download);
                                    imgBankStmt19CoBr.setBackgroundResource(R.drawable.zip_image);
                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgBankStmt19CoBr);
                                bBankStmt = false;
                            }

                            break;

                        case "31":

                            if (bOtherDoc) {
                                btnOtherDoc31CoBr.setVisibility(View.VISIBLE);
                                btnOtherDoc31CoBr.setText(R.string.preview);
                                btnOtherDoc31CoBr.setTag(baseUrl + image);
                                imgOtherDocUploadTick31CoBr.setVisibility(View.VISIBLE);
                                txtOtherDoc31CoBr.setVisibility(View.GONE);
                                strFileName = btnOtherDoc31CoBr.getTag().toString().substring(btnOtherDoc31CoBr.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    btnOtherDoc31CoBr.setText(R.string.preview);
                                    imgOtherDoc31CoBr.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    btnOtherDoc31CoBr.setText(R.string.download);
                                    imgOtherDoc31CoBr.setBackgroundResource(R.drawable.zip_image);
                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31CoBr);
                                bOtherDoc = false;
                            }

                            break;

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
