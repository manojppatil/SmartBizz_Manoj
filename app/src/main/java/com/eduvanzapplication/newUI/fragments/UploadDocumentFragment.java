package com.eduvanzapplication.newUI.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Util.JavaGetFileSize;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.uploaddocs.PathFile;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import vijay.createpdf.activity.ImgToPdfActivity;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.eduvanzapplication.MainActivity.TAG;


public class UploadDocumentFragment extends Fragment {

    public static LinearLayout linAllDocBlock,linKYCblock, linKYCblockBottom, linFinancBlockBottom, linEducationBlockBottom, linOtherBottom ,
            linFinancBlock,linEducationBlock,linOther, linKYCDocuments, linFinanceDocuments, linEducationDocuments, linOtherDocuments, linBottomBlocks;

    public static Animation  scaleInlinKYCDocuments, scaleInlinFinanceDocuments,
           scaleInlinEducationDocuments, scaleInlinOtherDocuments;
    ProgressDialog dialog;

    //code by yash
    /*KYC documents*/
    static LinearLayout profileImage,aadharCard,panCard,passport,voterId,drivingLicense,telephoneBill,electricityBill,rentAgreement,addressProof;
    /*Financial Documents*/
    static LinearLayout salSlipSix,salSlipThree,bankStmntThree,bankStmntSix,kvp,licPolicy,form16,form61,pensionLetter,itr,pnl;
    /*Educational Documents*/
    static LinearLayout tenthMarksheet,twelvethMarksheet,degreeMarkSheet,degreeCertificate;
    /*others documents*/
    static LinearLayout others;

    int tap;

    public int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_DOC = 2;

    public static Fragment mFragment;
    String uploadFilePath = "";
    StringBuffer sb;

    public String applicantType = "", documentTypeNo = "", userID = "";
    DownloadManager downloadManager;
    static Context context;
    static ImageView imageViewProfilePicSelect;
    ProgressBar progressBar;
    long downloadReference;
    public UploadDocumentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        mFragment = new UploadDocumentFragment();
        View view =inflater.inflate(R.layout.fragment_uploaddocument, container, false);
        linAllDocBlock = view.findViewById(R.id.linAllDocBlock);
        linKYCblock = view.findViewById(R.id.linKYCblock);
        linKYCblockBottom = view.findViewById(R.id.linKYCblockBottom);
        linFinancBlock = view.findViewById(R.id.linFinancBlock);
        linFinancBlockBottom = view.findViewById(R.id.linFinancBlockBottom);
        linEducationBlock = view.findViewById(R.id.linEducationBlock);
        linEducationBlockBottom = view.findViewById(R.id.linEducationBlockBottom);
        linOther = view.findViewById(R.id.linOther);
        linOtherBottom = view.findViewById(R.id.linOtherBottom);
        linBottomBlocks = view.findViewById(R.id.linBottomBlocks);

        linKYCDocuments = view.findViewById(R.id.linKYCDocuments);
        linFinanceDocuments  =view.findViewById(R.id.linFinanceDocuments);
        linEducationDocuments = view.findViewById(R.id.linEducationDocuments);
        linOtherDocuments = view.findViewById(R.id.linOtherDocuments);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_docupload);

        linAllDocBlock.setVisibility(VISIBLE);
        linKYCDocuments.setVisibility(GONE);
        linFinanceDocuments.setVisibility(GONE);
        linEducationDocuments.setVisibility(GONE);
        linOtherDocuments.setVisibility(GONE);
        linBottomBlocks.setVisibility(GONE);

        scaleInlinKYCDocuments = AnimationUtils.loadAnimation(getContext(), R.anim.scale_in);
        scaleInlinFinanceDocuments = AnimationUtils.loadAnimation(getContext(), R.anim.scale_in);
        scaleInlinEducationDocuments = AnimationUtils.loadAnimation(getContext(), R.anim.scale_in);
        scaleInlinOtherDocuments = AnimationUtils.loadAnimation(getContext(), R.anim.scale_in);

        try {
            //============================KYC profile image========================
            profileImage = view.findViewById(R.id.linPhoto);
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tap ++;

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "1";
                                imageToPdf(documentTypeNo,getString(R.string.upload_profile_picture),getString(R.string.applicant_single_picture_required_to_be_uploaded), MainApplication.Brapplicant_idkyc,"1");
                                tap = 0;
                            }
                            else if (tap == 2){
                                if (profileImage.getTag()!=null){
                                    String strFileName = profileImage.getTag().toString().substring(profileImage.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(profileImage.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(profileImage.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(profileImage.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }

                                tap=0;
                            }
                            tap=0;
                        }
                    },700);


                }
            });

            /*=================================profile photo finished=============================*/
            /*===================================Pan card=========================================*/
            panCard = view.findViewById(R.id.linPan);
            panCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap == 1){
                                applicantType = "1";
                                documentTypeNo = "2";
                                imageToPdf(documentTypeNo, getString(R.string.upload_pan_card), getString(R.string.applicant_pan_card), MainApplication.Brapplicant_idkyc, "1");

                                tap=0;
                            }else if (tap==2){

                                if (panCard.getTag()!=null){
                                    String strFileName = panCard.getTag().toString().substring(panCard.getTag().toString().lastIndexOf("/")+1);
                                    File filePath = new File(Environment.getExternalStorageDirectory().getPath()+"/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/"+strFileName);
                                    String fileExtension = strFileName.substring(strFileName.lastIndexOf('.')+1);
                                    if (fileExtension.equals("pdf")){
                                        openPdf(String.valueOf(panCard.getTag()));
                                    }else if (fileExtension.equals("zip")||fileExtension.equals("rar")){
                                        if (filePath.exists()){
                                            Toast.makeText(getActivity(), "File has already downloaded", Toast.LENGTH_SHORT).show();
                                        }else {
                                            try {
                                                downLoadClick(String.valueOf(panCard.getTag()));
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(profileImage.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }


                                tap=0;
                            }
                        }
                    },700);

                }
            });


            /*======================pan card done===============*/

            /*==============================================adhar card=====================================*/
            aadharCard = view.findViewById(R.id.linAadhaar);
            aadharCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                         if (tap==1){
                             applicantType = "1";
                             documentTypeNo = "3";
                             imageToPdf(documentTypeNo,getString(R.string.upload_adhaar_card),getString(R.string.applicant_adhaar_card_front_and_backside), MainApplication.Brapplicant_idkyc,"1");
                             tap=0;
                         }else if (tap==2){

                             if (aadharCard.getTag()!=null){
                                 String strFileName = aadharCard.getTag().toString().substring(aadharCard.getTag().toString().lastIndexOf("/")+1);

                                 File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                 String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                 if (FileExtn.equals("pdf")) {
                                     openPdf(String.valueOf(aadharCard.getTag()));

                                 }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                     if (filepath.exists()){
                                         Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                     }
                                     else {
                                         try {
                                             downLoadClick(String.valueOf(aadharCard.getTag()));
                                         }catch (Exception e){
                                             e.printStackTrace();
                                         }
                                     }
                                 }else {
                                     try {
                                         openImage(String.valueOf(aadharCard.getTag()));
                                     }catch (Exception e){
                                         e.printStackTrace();
                                     }
                                 }
                             }else {
                                 Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                             }


                             tap=0;
                         }
                         tap = 0;
                        }
                    },700);

                }
            });
            /*========================end of aadha card==========================*/
            /*================================passport===============================*/
            passport = view.findViewById(R.id.linPassport);
            passport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "4";
                                imageToPdf(documentTypeNo,getString(R.string.upload_passport),getString(R.string.applicant_passport), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (passport.getTag()!=null){
                                    String strFileName = passport.getTag().toString().substring(passport.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(passport.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(passport.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(passport.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }


                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);


                }
            });

            /* ====================================end of passport=========================*/

            /*==============================voter id===================================*/
            voterId = view.findViewById(R.id.linVoterId);
            voterId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "5";
                                imageToPdf(documentTypeNo,getString(R.string.upload_voterID),getString(R.string.applicant_voterID), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (voterId.getTag()!=null){
                                    String strFileName = voterId.getTag().toString().substring(voterId.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(voterId.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(voterId.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(voterId.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }


                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                }
            });
            /*=================================end of voter id==========================*/
            /*==========================================driving License========================*/

            drivingLicense = view.findViewById(R.id.linDrivingLicense);
            drivingLicense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "6";
                                imageToPdf(documentTypeNo,getString(R.string.upload_driving_license),getString(R.string.applicant_driving_license), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (drivingLicense.getTag()!=null){
                                    String strFileName = drivingLicense.getTag().toString().substring(drivingLicense.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(drivingLicense.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(drivingLicense.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(drivingLicense.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }



                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);
                }
            });

            /*============================end of driving license===============================*/
            /*===========================================Telephone Bill==============================*/

            telephoneBill = view.findViewById(R.id.linTelephoneBill);
            telephoneBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "7";
                                imageToPdf(documentTypeNo,getString(R.string.upload_telephone_bill),getString(R.string.applicant_telephone_bill), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (telephoneBill.getTag()!=null){
                                    String strFileName = telephoneBill.getTag().toString().substring(telephoneBill.getTag().toString().lastIndexOf("/")+1);
                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);
                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(telephoneBill.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(telephoneBill.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(telephoneBill.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);


                }
            });


            /*========================end of telephone Bill================================*/
            /*==========================================Electricity Bill============================*/
            electricityBill = view.findViewById(R.id.linElectricityBill);
            electricityBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "8";
                                imageToPdf(documentTypeNo,getString(R.string.upload_electricity_bill),getString(R.string.applicant_electricity_bill), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (electricityBill.getTag()!=null){
                                    String strFileName = electricityBill.getTag().toString().substring(electricityBill.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(electricityBill.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(electricityBill.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(electricityBill.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }


                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);


                }
            });

            /*====================end of electricity Bill===========================*/
            /*=================================Rent Agreement=================================*/
            rentAgreement = view.findViewById(R.id.linRentAgreement);
            rentAgreement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "9";
                                imageToPdf(documentTypeNo,getString(R.string.upload_rent_agreement),getString(R.string.applicant_rent_agreement), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (rentAgreement.getTag()!=null){
                                    String strFileName = rentAgreement.getTag().toString().substring(rentAgreement.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(rentAgreement.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(rentAgreement.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(rentAgreement.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }


                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);


                }
            });


            /*==================================end of rent agreement===========================*/
            /*=====================================Address Proof=======================================*/
            addressProof = view.findViewById(R.id.linAddressProof);
            addressProof.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){

                                applicantType = "1";
                                documentTypeNo = "30";
                                imageToPdf(documentTypeNo,getString(R.string.upload_address_proof),getString(R.string.applicant_address_proof), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (addressProof.getTag()!=null){
                                    String strFileName = addressProof.getTag().toString().substring(addressProof.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(addressProof.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(addressProof.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(addressProof.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }


                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                }
            });

            /*=======================End Address Proof & End Of KYC================================*/
            /*=====================================Financial Documents & salary slip six months===============================*/
            salSlipSix = view.findViewById(R.id.linSalarySlipSix);
            salSlipSix.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "17";
                                imageToPdf(documentTypeNo,getString(R.string.upload_income_proof_6),getString(R.string.salary_slip_of_applicant_latest_6_months_if_not_available_salary_certificate_stating_the_same_details_would_be_accepted), MainApplication.Brapplicant_idkyc,"1");

                                tap=0;
                            }else if (tap==2){




                                if (salSlipSix.getTag()!=null){
                                    String strFileName = salSlipSix.getTag().toString().substring(salSlipSix.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(salSlipSix.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(salSlipSix.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(salSlipSix.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);
                }
            });

/*end sal slip six months*/

/*==========================================sal slip three months=============================*/
            salSlipThree = view.findViewById(R.id.linSalarySlipThree);
            salSlipThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "18";
                                imageToPdf(documentTypeNo,getString(R.string.upload_income_proof),getString(R.string.salary_slip_of_applicant_latest_3_months_if_not_available_salary_certificate_stating_the_same_details_would_be_accepted), MainApplication.Brapplicant_idkyc,"1");

                                tap=0;
                            }else if (tap==2){

                                if (salSlipThree.getTag()!=null){
                                    String strFileName = salSlipThree.getTag().toString().substring(salSlipThree.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(salSlipThree.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(salSlipThree.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(salSlipThree.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }


                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                    }
            });

/*================================end of sal slip three=============================*/
/*===========================================bank statement three months=========================*/

            bankStmntThree = view.findViewById(R.id.linBankStatementThree);
            bankStmntThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "19";
                                imageToPdf(documentTypeNo,getString(R.string.upload_bank_statement),getString(R.string.current_3_months_bank_statement_of_applicant_reflecting_salary_along_with_the_front_page), MainApplication.Brapplicant_idkyc,"1");

                                tap=0;
                            }else if (tap==2){

                                if (bankStmntThree.getTag()!=null){
                                    String strFileName = bankStmntThree.getTag().toString().substring(bankStmntThree.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(bankStmntThree.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(bankStmntThree.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(bankStmntThree.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                     }
            });

            /*end if bank statement three*/

            /*===================================bank statement six=================================*/

            bankStmntSix = view.findViewById(R.id.linBankStatementSix);
            bankStmntSix.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "20";
                                imageToPdf(documentTypeNo,getString(R.string.upload_bank_statement_6),getString(R.string.current_6_months_bank_statement_of_applicant_reflecting_salary_along_with_the_front_page), MainApplication.Brapplicant_idkyc,"1");

                                tap=0;
                            }else if (tap==2){

                                if (bankStmntSix.getTag()!=null){
                                    String strFileName = bankStmntSix.getTag().toString().substring(bankStmntSix.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(bankStmntSix.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(bankStmntSix.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(bankStmntSix.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }


                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                    }
            });

            /*end of bank statement 6*/

            /*=======================================KVP============================*/

            kvp = view.findViewById(R.id.linKVP);
            kvp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){

                                applicantType = "1";
                                documentTypeNo = "10";
                                imageToPdf(documentTypeNo,getString(R.string.upload_KVP),getString(R.string.current_KVP), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (kvp.getTag()!=null){
                                    String strFileName = kvp.getTag().toString().substring(kvp.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(kvp.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(kvp.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(kvp.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                }
            });

            /*=================end of kvp==================*/
            /*========================================lic policy================================================*/

            licPolicy = view.findViewById(R.id.linLICPolicy);
            licPolicy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "11";
                                imageToPdf(documentTypeNo,getString(R.string.upload_lic_policy),getString(R.string.current_applicant_lic_policy), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (licPolicy.getTag()!=null){
                                    String strFileName = licPolicy.getTag().toString().substring(licPolicy.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(licPolicy.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(licPolicy.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(licPolicy.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);


                }
            });

            /*=================================end of lic policy==============================*/
            /*===========================================form 16=========================================*/

            form16 = view.findViewById(R.id.linForm16);
            form16.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){

                                applicantType = "1";
                                documentTypeNo = "12";
                                imageToPdf(documentTypeNo,getString(R.string.upload_form_16),getString(R.string.current_applicant_form_16), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (form16.getTag()!=null){
                                    String strFileName = form16.getTag().toString().substring(form16.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(form16.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(form16.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(form16.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }


                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                }
            });


            /*============================================end of form 16=====================================*/
            /*===================================================form 61=================================================*/

            form61 = view.findViewById(R.id.linForm61);
            form61.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){

                                applicantType = "1";
                                documentTypeNo = "13";
                                imageToPdf(documentTypeNo,getString(R.string.upload_form_61),getString(R.string.current_applicant_form_61), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (form61.getTag()!=null){
                                    String strFileName = form61.getTag().toString().substring(form61.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(form61.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(form61.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(form61.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                }
            });

            /*end of form 61*/
            /*=======================================Pension Letter================================*/
            pensionLetter = view.findViewById(R.id.linPensionLetter);
            pensionLetter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "14";
                                imageToPdf(documentTypeNo,getString(R.string.upload_pension_letter),getString(R.string.current_applicant_pension_letter), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (pensionLetter.getTag()!=null){
                                    String strFileName = pensionLetter.getTag().toString().substring(pensionLetter.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(pensionLetter.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(pensionLetter.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(pensionLetter.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);


                }
            });


            /*================================end of pension letter===========================*/
            /*=========================================ITR=============================================*/

            itr = view.findViewById(R.id.linITR);
            itr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){

                                applicantType = "1";
                                documentTypeNo = "15";
                                imageToPdf(documentTypeNo,getString(R.string.upload_itr),getString(R.string.current_itr), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (itr.getTag()!=null){
                                    String strFileName = itr.getTag().toString().substring(itr.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(itr.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(itr.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(itr.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                }
            });

/*end of itr*/

/*====================================================PNL===================================*/
            pnl = view.findViewById(R.id.linPNL);
            pnl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "16";
                                imageToPdf(documentTypeNo,getString(R.string.upload_pnl),getString(R.string.current_applicant_pnl), MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (pnl.getTag()!=null){
                                    String strFileName = pnl.getTag().toString().substring(pnl.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(pnl.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(pnl.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(pnl.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);



                }
            });

           /* end of pnl & financial documents*/
           /*==========================================Educational Documents================================*/

            tenthMarksheet = view.findViewById(R.id.lintenth_mark_sheet);
            tenthMarksheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "21";
                                imageToPdf(documentTypeNo,getString(R.string.upload_latest_marksheet),getString(R.string.latest_marksheet_of_the_applicant), MainApplication.Brapplicant_idkyc,"1");

                                tap=0;
                            }else if (tap==2){

                                if (tenthMarksheet.getTag()!=null){
                                    String strFileName = tenthMarksheet.getTag().toString().substring(tenthMarksheet.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(tenthMarksheet.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(tenthMarksheet.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(tenthMarksheet.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                    }
            });

/*========================end of tenth marksheet==========================*/
/*========================================twelveth marksheet======================================*/

            twelvethMarksheet = view.findViewById(R.id.lintwelvethMarkSheet);
            twelvethMarksheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "22";
                                imageToPdf(documentTypeNo,getString(R.string.upload_latest_marksheet),getString(R.string.latest_marksheet_of_the_applicant), MainApplication.Brapplicant_idkyc,"1");

                                tap=0;
                            }else if (tap==2){

                                if (twelvethMarksheet.getTag()!=null){
                                    String strFileName = twelvethMarksheet.getTag().toString().substring(twelvethMarksheet.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(twelvethMarksheet.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(twelvethMarksheet.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(twelvethMarksheet.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                    }
            });

            /*end of twelve marksheet*/
            /*========================================degree marksheet============================================*/
            degreeMarkSheet = view.findViewById(R.id.linlastCompletedMarkSheet);
            degreeMarkSheet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "23";
                                imageToPdf(documentTypeNo,getString(R.string.upload_latest_marksheet),getString(R.string.latest_marksheet_of_the_applicant), MainApplication.Brapplicant_idkyc,"1");

                                tap=0;
                            }else if (tap==2){

                                if (degreeMarkSheet.getTag()!=null){
                                    String strFileName = degreeMarkSheet.getTag().toString().substring(degreeMarkSheet.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(degreeMarkSheet.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(degreeMarkSheet.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(degreeMarkSheet.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }


                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                    }
            });

            /*end of degree marksheet*/
            /*==================================degree certificate==========================*/

            degreeCertificate = view.findViewById(R.id.linlastcompletedDegreeCertificate);
            degreeCertificate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "24";
                                imageToPdf(documentTypeNo,getString(R.string.upload_latest_certificate),getString(R.string.latest_certificate_of_the_applicant), MainApplication.Brapplicant_idkyc,"1");

                                tap=0;
                            }else if (tap==2){

                                if (degreeCertificate.getTag()!=null){
                                    String strFileName = degreeCertificate.getTag().toString().substring(degreeCertificate.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(degreeCertificate.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(degreeCertificate.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(degreeCertificate.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                   }
            });
            /*============================================end of educational documents=================================================================*/
            /*===============================================Others====================================================*/

            others = view.findViewById(R.id.others);
            others.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    tap++;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (tap==1){
                                applicantType = "1";
                                documentTypeNo = "31";
                                imageToPdf(documentTypeNo,"others","others", MainApplication.Brapplicant_idkyc,"1");
                                tap=0;
                            }else if (tap==2){

                                if (others.getTag()!=null){
                                    String strFileName = others.getTag().toString().substring(others.getTag().toString().lastIndexOf("/")+1);

                                    File filepath = new File(Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.eduvanzapplication/files/Download/Eduvanz/UploadedDoc/" + strFileName);

                                    String FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                    if (FileExtn.equals("pdf")) {
                                        openPdf(String.valueOf(others.getTag()));

                                    }else if (FileExtn.equals("zip")||FileExtn.equals("rar")){
                                        if (filepath.exists()){
                                            Toast.makeText(getActivity(),"File is already downloaded: ",Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            try {
                                                downLoadClick(String.valueOf(others.getTag()));
                                            }catch (Exception e){
                                                e.printStackTrace();
                                            }
                                        }
                                    }else {
                                        try {
                                            openImage(String.valueOf(others.getTag()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                    }
                                }else {
                                    Toast.makeText(getActivity(), "Please upload document first to preview", Toast.LENGTH_SHORT).show();
                                }
                                tap=0;
                            }
                            tap = 0;
                        }
                    },700);

                }
            });
/*===========================================end of others document==========================================*/
            getUploadDocumentsApiCall();
        }catch (Exception e){
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

    private void getUploadDocumentsApiCall() {
        /** API CALL **/
        try {
            String url = MainActivity.mainUrl + "document/getDocumentsDetails";
            Map<String, String> params = new HashMap<String, String>();
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();//http://159.89.204.41/eduvanzApi/document/getapplicantDocumentDetails
                params.put("lead_id", MainActivity.lead_id);//"studentId" -> "2953"
//                params.put("fk_applicant_id", MainApplication.Brapplicant_iddtl);//"studentId" -> "2953"
                params.put("fk_applicant_id", MainActivity.applicant_id);//"studentId" -> "2953"
                params.put("applicant_type", "1");//"studentId" -> "2953"
                volleyCall.sendRequest(context, url, null, mFragment, "getDocumentsBorrower", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linKYCblock.setOnClickListener(kycBlockClkListnr);
        linKYCblockBottom.setOnClickListener(kycBlockClkListnr);

        linFinancBlock.setOnClickListener(financeBlockClkListenr);
        linFinancBlockBottom.setOnClickListener(financeBlockClkListenr);

        linEducationBlock.setOnClickListener(educationBlockClkListnr);
        linEducationBlockBottom.setOnClickListener(educationBlockClkListnr);

        linOther.setOnClickListener(otherBlockClkListnr);
        linOtherBottom.setOnClickListener(otherBlockClkListnr);

        scaleInlinKYCDocuments.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linKYCDocuments.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(GONE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scaleInlinFinanceDocuments.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linFinanceDocuments.setVisibility(VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(GONE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scaleInlinEducationDocuments.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linEducationDocuments.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(GONE);
                linOtherBottom.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        scaleInlinOtherDocuments.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linOtherDocuments.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    View.OnClickListener kycBlockClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            linAllDocBlock.setVisibility(GONE);
//            linKYCDocuments.setVisibility(VISIBLE);
//            linFinanceDocuments.setVisibility(GONE);
//            linEducationDocuments.setVisibility(GONE);
//            linOtherDocuments.setVisibility(GONE);
//            linBottomBlocks.setVisibility(VISIBLE);
//            linKYCblockBottom.setVisibility(GONE);
//            linFinancBlockBottom.setVisibility(VISIBLE);
//            linEducationBlockBottom.setVisibility(VISIBLE);
//            linOtherBottom.setVisibility(VISIBLE);
            Animation scaleOutlinAllDocBlock = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out);
            if (linAllDocBlock.getVisibility() == VISIBLE){
                linAllDocBlock.startAnimation(scaleOutlinAllDocBlock);
            }else{
                linAllDocBlock.setVisibility(GONE);
                linKYCDocuments.setVisibility(VISIBLE);
                linFinanceDocuments.setVisibility(GONE);
                linEducationDocuments.setVisibility(GONE);
                linOtherDocuments.setVisibility(GONE);
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(GONE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(VISIBLE);

            }
            scaleOutlinAllDocBlock.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linKYCDocuments.startAnimation(scaleInlinKYCDocuments);
                    linAllDocBlock.setVisibility(GONE);
                    linFinanceDocuments.setVisibility(GONE);
                    linEducationDocuments.setVisibility(GONE);
                    linOtherDocuments.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    };



    View.OnClickListener financeBlockClkListenr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            linAllDocBlock.setVisibility(GONE);
//            linKYCDocuments.setVisibility(GONE);
//            linFinanceDocuments.setVisibility(VISIBLE);
//            linEducationDocuments.setVisibility(GONE);
//            linOtherDocuments.setVisibility(GONE);
//            linBottomBlocks.setVisibility(VISIBLE);
//            linKYCblockBottom.setVisibility(VISIBLE);
//            linFinancBlockBottom.setVisibility(GONE);
//            linEducationBlockBottom.setVisibility(VISIBLE);
//            linOtherBottom.setVisibility(VISIBLE);
            Animation scaleOutlinAllDocBlock = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out);
            if (linAllDocBlock.getVisibility() == VISIBLE){
                linAllDocBlock.startAnimation(scaleOutlinAllDocBlock);
            }else{
                linAllDocBlock.setVisibility(GONE);
                linKYCDocuments.setVisibility(GONE);
                linFinanceDocuments.setVisibility(VISIBLE);
                linEducationDocuments.setVisibility(GONE);
                linOtherDocuments.setVisibility(GONE);
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(GONE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(VISIBLE);
            }
            scaleOutlinAllDocBlock.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linFinanceDocuments.startAnimation(scaleInlinFinanceDocuments);
                    linAllDocBlock.setVisibility(GONE);
                    linKYCDocuments.setVisibility(GONE);
                    linEducationDocuments.setVisibility(GONE);
                    linOtherDocuments.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


        }
    };

    View.OnClickListener educationBlockClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            linAllDocBlock.setVisibility(GONE);
//            linKYCDocuments.setVisibility(GONE);
//            linFinanceDocuments.setVisibility(GONE);
//            linEducationDocuments.setVisibility(VISIBLE);
//            linOtherDocuments.setVisibility(GONE);
//            linBottomBlocks.setVisibility(VISIBLE);
//            linKYCblockBottom.setVisibility(VISIBLE);
//            linFinancBlockBottom.setVisibility(VISIBLE);
//            linEducationBlockBottom.setVisibility(GONE);
//            linOtherBottom.setVisibility(VISIBLE);
            Animation scaleOutlinAllDocBlock = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out);
            if (linAllDocBlock.getVisibility() == VISIBLE){
                linAllDocBlock.startAnimation(scaleOutlinAllDocBlock);
            }else{
                linAllDocBlock.setVisibility(GONE);
                linKYCDocuments.setVisibility(GONE);
                linFinanceDocuments.setVisibility(GONE);
                linEducationDocuments.setVisibility(VISIBLE);
                linOtherDocuments.setVisibility(GONE);
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(GONE);
                linOtherBottom.setVisibility(VISIBLE);
            }
            scaleOutlinAllDocBlock.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linEducationDocuments.startAnimation(scaleInlinEducationDocuments);
                    linAllDocBlock.setVisibility(GONE);
                    linKYCDocuments.setVisibility(GONE);
                    linFinanceDocuments.setVisibility(GONE);
                    linOtherDocuments.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }
    };

    View.OnClickListener otherBlockClkListnr = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            linAllDocBlock.setVisibility(GONE);
//            linKYCDocuments.setVisibility(GONE);
//            linFinanceDocuments.setVisibility(GONE);
//            linEducationDocuments.setVisibility(GONE);
//            linOtherDocuments.setVisibility(VISIBLE);
//            linBottomBlocks.setVisibility(VISIBLE);
//            linKYCblockBottom.setVisibility(VISIBLE);
//            linFinancBlockBottom.setVisibility(VISIBLE);
//            linEducationBlockBottom.setVisibility(VISIBLE);
//            linOtherBottom.setVisibility(GONE);

            Animation scaleOutlinAllDocBlock = AnimationUtils.loadAnimation(getContext(), R.anim.scale_out);
            if (linAllDocBlock.getVisibility() == VISIBLE){
                linAllDocBlock.startAnimation(scaleOutlinAllDocBlock);
            }else{
                linAllDocBlock.setVisibility(GONE);
                linKYCDocuments.setVisibility(GONE);
                linFinanceDocuments.setVisibility(GONE);
                linEducationDocuments.setVisibility(GONE);
                linOtherDocuments.setVisibility(VISIBLE);
                linBottomBlocks.setVisibility(VISIBLE);
                linKYCblockBottom.setVisibility(VISIBLE);
                linFinancBlockBottom.setVisibility(VISIBLE);
                linEducationBlockBottom.setVisibility(VISIBLE);
                linOtherBottom.setVisibility(GONE);
            }
            scaleOutlinAllDocBlock.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    linOtherDocuments.startAnimation(scaleInlinOtherDocuments);
                    linAllDocBlock.setVisibility(GONE);
                    linKYCDocuments.setVisibility(GONE);
                    linFinanceDocuments.setVisibility(GONE);
                    linEducationDocuments.setVisibility(GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


        }
    };

    //====================changes by yash=========================
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

    private void openPdf(String mPath) {

        Uri path = Uri.parse(mPath);
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setDataAndType(path, "application/pdf");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), R.string.no_application_available_ro_view_pdf, Toast.LENGTH_SHORT).show();
        }
    }

    public void downLoadClick(String uri) {
        try {
            Handler handler = new Handler();

            Runnable r = new Runnable() {
                @Override
                public void run() {
                    // your code here
                    handler.post(new Runnable()
                    {
                        public void run() {
                            Log.e(TAG, "downloadUrl+++++: " + uri);
                            Uri Download_Uri = Uri.parse(String.valueOf(uri));

                            try {
                                String fname = "";
                                fname = uri.toString().substring(uri.toString().lastIndexOf("/") + 1);

                                downloadManager = (DownloadManager) context.getSystemService(context.DOWNLOAD_SERVICE);

                                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

                                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                               request.setAllowedOverRoaming(false);
                                request.setTitle("Your Document is Downloading");
                                request.setDescription("Android Data download using DownloadManager.");

                                request.setVisibleInDownloadsUi(true);

                                request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "Eduvanz" + "/" + "UploadedDoc" + "/" + fname);//

                                progressBar.setVisibility(View.VISIBLE);

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
//solve this
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
                                            Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show();
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

                getUploadDocumentsApiCall();
            }

        }
        if (requestCode == 1) {
            if (resultCode == 1) {
                String message = data.getStringExtra("BACK");

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
                            aadharCard.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("2")) {
                            panCard.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("3")) {
                            passport.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("4")) {
                            voterId.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("5")) {
                            drivingLicense.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("6")) {
                            telephoneBill.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("7")) {
                            electricityBill.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("8")) {
                            rentAgreement.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("9")) {
                            addressProof.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("10")) {
                            salSlipSix.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("11")) {
                            salSlipThree.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("12")) {
                            bankStmntThree.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("13")) {
                            bankStmntSix.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("14")) {
                            kvp.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("15")) {
                            licPolicy.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("16")) {
                            form16.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("17")) {
                            form61.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("18")) {
                            pensionLetter.setVisibility(View.VISIBLE);
                        }else if (documentTypeNo.equalsIgnoreCase("19")) {
                            itr.setVisibility(View.VISIBLE);
                        }else if (documentTypeNo.equalsIgnoreCase("20")) {
                            pnl.setVisibility(View.VISIBLE);
                        }else if (documentTypeNo.equalsIgnoreCase("21")) {
                            tenthMarksheet.setVisibility(View.VISIBLE);
                        }else if (documentTypeNo.equalsIgnoreCase("22")) {
                            twelvethMarksheet.setVisibility(View.VISIBLE);
                        }else if (documentTypeNo.equalsIgnoreCase("23")) {
                            degreeMarkSheet.setVisibility(View.VISIBLE);
                        }else if (documentTypeNo.equalsIgnoreCase("24")) {
                            degreeCertificate.setVisibility(View.VISIBLE);
                        } else if (documentTypeNo.equalsIgnoreCase("25")) {
                            others.setVisibility(View.VISIBLE);

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


    public int uploadFile(final String selectedFilePath, String doctypeno, String strapplicantType, String strapplicantId) {
        String urlup = MainActivity.mainUrl + "document/documentUpload";

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
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setUseCaches(false);
                connection.setChunkedStreamingMode(1024);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("Authorization", "Bearer " + MainActivity.auth_token);
                connection.setRequestProperty("document", selectedFilePath);
                Log.e("TAG", "Server property" + connection.getRequestMethod() + ":property " + connection.getRequestProperties());
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"myfile\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);

                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dataOutputStream.write(buffer, 0, bufferSize);
                    Log.e("TAG", " here: \n\n" + buffer + "\n" + bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"fk_lead_id\";fk_lead_id=" + MainActivity.lead_id + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(MainActivity.lead_id);
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"page_id\";page_id=" + "1" + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes("1");
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"fk_applicant_id\";fk_applicant_id=" + strapplicantId + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(strapplicantId);
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
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
                               progressBar.setVisibility(View.GONE);
                                Log.e("TAG", " 2285: " + new Date().toLocaleString());//1538546658896.jpg/
                                Toast.makeText(context, mData1+" "+mData, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                 if (serverResponseCode == 200) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("TAG", " 2303: " + new Date().toLocaleString());//1538546658896.jpg/
                        }
                    });
                }

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
            try {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
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

    public void getBorrowerDocuments(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "getDocuments" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");
            String baseUrl = String.valueOf(jsonData.getJSONObject("result").get("baseUrl"));
            if (status.equalsIgnoreCase("1")) {
                String strFileName, FileExtn;

                Boolean bPhoto = true, bAadhaar = true, bPan = true, bAddress = true, bPassport = true,bVoterId = true,bDrivingLicense = true,bTelephoneBill = true,bElectricityBill = true,bRentAgreemnet = true,

                        bSalSlip6 = true , bSalSlip3 = true, bBankStmt3 = true , bBankStmt6 = true ,bKVP = true,bLic = true,bForm16 = true,bForm61 = true,bPension = true,bITR = true,bPNL = true, bDregreeMarkSheet = true, bDegreeCerti = true, bOtherDoc = true,btenthmark = true,btwelvethmark = true;

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

                                profileImage.setTag(baseUrl + image);
                                /*imgPhotoUploadTick.setVisibility(View.VISIBLE);
                                txtPhoto1.setVisibility(View.GONE);*/
//                              imgPhoto1.setBackgroundResource(R.drawable.pdf_image);
                                /*Picasso.with(context).load(baseUrl + image).into(imgPhoto1);
                                */bPhoto = false;
                            }
                            break;

                        case "3":
                            if (bAadhaar) {
                                aadharCard.setTag(baseUrl + image);
                               /* imgAadhaarUploadTick3.setVisibility(View.VISIBLE);
                                txtAadhaar3.setVisibility(View.GONE);
*/
                                strFileName = aadharCard.getTag().toString().substring(aadharCard.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnAadhar.setText(R.string.preview);*/
                                    /*imgAadhaar3.setBackgroundResource(R.drawable.pdf_image);
                               */ } else {
                                    /*btnAadhar.setText(R.string.download);*/
                                      }
                                bAadhaar = false;
                            }
                            break;

                        case "2":
                            if (bPan) {
                                panCard.setTag(baseUrl + image);
                                /*imgPanUploadTick2.setVisibility(View.VISIBLE);
                                txtPan2.setVisibility(View.GONE);*/
                                strFileName = panCard.getTag().toString().substring(panCard.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnPanCard.setText(R.string.preview);*/
                                    /*imgPan2.setBackgroundResource(R.drawable.pdf_image);*/
                                } else {
                                    /*btnPanCard.setText(R.string.download);*/
                                    /* imgPan2.setBackgroundResource(R.drawable.zip_image);*/

                                }
//                        Picasso.with(context).load(String.valueOf(bm)).into(imgPan2);
                                bPan = false;
                            }
                            break;

                        case "4":
                            if (bPassport) {
                                passport.setTag(baseUrl + image);
                                /*imgAddressUploadTick38.setVisibility(View.VISIBLE);
                                txtAddress38.setVisibility(View.GONE);*/
                                strFileName = passport.getTag().toString().substring(passport.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnPassport.setText(R.string.preview);*/
                                    /*imgAddress38.setBackgroundResource(R.drawable.pdf_image);*/
                                } else {
                                    /*btnPassport.setText(R.string.download);*/
                                    /*imgAddress38.setBackgroundResource(R.drawable.zip_image);*/
                                }

//                        Picasso.with(context).load(String.valueOf(bm)).into(imgAddress38);
                                bAddress = false;
                            }
                            break;

                        case "5":

                            if (bVoterId) {
                                voterId.setTag(baseUrl + image);
                                /*imgSalarySlipUploadTick18.setVisibility(View.VISIBLE);
                                txtSalarySlip18.setVisibility(View.GONE);*/
                                strFileName = voterId.getTag().toString().substring(voterId.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                   /* btnVoterId.setText(R.string.preview);*/
                                    /*imgSalarySlip18.setBackgroundResource(R.drawable.pdf_image);*/
                                } else {
                                    /*btnVoterId.setText(R.string.download);*/
                                    /* imgSalarySlip18.setBackgroundResource(R.drawable.zip_image);*/
                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgSalarySlip18);
                                bVoterId = false;
                            }
                            break;

                        case "6":

                            if (bDrivingLicense) {
                                drivingLicense.setTag(baseUrl + image);
                               /* imgBankStmtUploadTick19.setVisibility(View.VISIBLE);
                                txtBankStmt19.setVisibility(View.GONE);*/
                                strFileName = drivingLicense.getTag().toString().substring(drivingLicense.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*drivingLicense.setText(R.string.preview);*/
                                    //imgBankStmt19.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnDrivingLicense.setText(R.string.download);*/
                                    //imgBankStmt19.setBackgroundResource(R.drawable.zip_image);

                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgBankStmt19);
                                bDrivingLicense = false;
                            }

                            break;

                        case "7":

                            if (bTelephoneBill) {
                                telephoneBill.setTag(baseUrl + image);
                                /*imgDegreeMarkSheetUploadTick23.setVisibility(View.VISIBLE);
                                txtDegreeMarkSheet23.setVisibility(View.GONE);*/
                                strFileName = telephoneBill.getTag().toString().substring(telephoneBill.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnTelephoneBill.setText(R.string.preview);*/
                                    //      imgDegreeMarkSheet23.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnTelephoneBill.setText(R.string.download);*/
                                    //  imgDegreeMarkSheet23.setBackgroundResource(R.drawable.zip_image);

                                }
                                //                        Picasso.with(context).load(baseUrl + image).into(imgDegreeMarkSheet23);
                                bTelephoneBill = false;
                            }
                            break;

                        case "8":

                            if (bElectricityBill) {
                                electricityBill.setTag(baseUrl + image);
                               /* imgDegreeMarkSheetUploadTick23.setVisibility(View.VISIBLE);
                                txtDegreeMarkSheet23.setVisibility(View.GONE);*/
                                strFileName = electricityBill.getTag().toString().substring(electricityBill.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*electricityBill.setText(R.string.preview);*/
                                    //imgDegreeMarkSheet23.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnTelephoneBill.setText(R.string.download);*/
                                    //imgDegreeMarkSheet23.setBackgroundResource(R.drawable.zip_image);

                                }
                                //                        Picasso.with(context).load(baseUrl + image).into(imgDegreeMarkSheet23);
                                bElectricityBill = false;
                            }
                            break;

                        case "24":

                            if (bDegreeCerti) {
                                degreeCertificate.setTag(baseUrl + image);
                                /*imgDegreeCertiUploadTick24.setVisibility(View.VISIBLE);
                                txtDegreeCerti24.setVisibility(View.GONE);*/
                                strFileName = degreeCertificate.getTag().toString().substring(degreeCertificate.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnDegreeCertificate.setText(R.string.preview);*/
                                    // imgDegreeCerti24.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnDegreeCertificate.setText(R.string.download);*/
                                    //imgDegreeCerti24.setBackgroundResource(R.drawable.zip_image);
                                }
//                        Picasso.with(context).load(baseUrl + image).into(imgDegreeCerti24);
                                bDegreeCerti = false;
                            }

                            break;

                        case "31":

                            if (bOtherDoc) {
                                others.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = others.getTag().toString().substring(others.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnOthers.setText(R.string.preview);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnOthers.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bOtherDoc = false;
                            }
                            break;

                        case "9":

                            if (bRentAgreemnet) {
                                rentAgreement.setTag(baseUrl + image);
                               /* imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = rentAgreement.getTag().toString().substring(rentAgreement.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnRentAgreement.setText(R.string.preview);*/
                                    // imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnRentAgreement.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bRentAgreemnet = false;
                            }
                            break;

                        case "30":

                            if (bAddress) {
                                addressProof.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = addressProof.getTag().toString().substring(addressProof.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnAddressProof.setText(R.string.preview);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                   /* btnAddressProof.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bAddress = false;
                            }
                            break;

                        case "17":

                            if (bSalSlip6) {
                                salSlipSix.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = salSlipSix.getTag().toString().substring(salSlipSix.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnSalSlipSix.setText(R.string.preview);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnSalSlipSix.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bSalSlip6 = false;
                            }
                            break;

                        case "18":

                            if (bSalSlip3) {
                                salSlipThree.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = salSlipThree.getTag().toString().substring(salSlipThree.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnSalSlipThree.setText(R.string.preview);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnSalSlipThree.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bSalSlip3 = false;
                            }
                            break;

                        case "19":

                            if (bBankStmt3) {
                                bankStmntThree.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = bankStmntThree.getTag().toString().substring(bankStmntThree.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnBankStmntThree.setText(R.string.preview);*/
                                    // imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnBankStmntThree.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bBankStmt3 = false;
                            }
                            break;

                        case "20":

                            if (bBankStmt6) {
                                bankStmntSix.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = bankStmntSix.getTag().toString().substring(bankStmntSix.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*bankStmntSix.setText(R.string.preview);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnBankStmntSix.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bBankStmt6 = false;
                            }
                            break;

                        case "10":

                            if (bKVP) {
                                kvp.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = kvp.getTag().toString().substring(kvp.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnKVP.setText(R.string.preview);*/
                                    // imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnKVP.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bKVP = false;
                            }
                            break;

                        case "11":

                            if (bLic) {
                                licPolicy.setTag(baseUrl + image);
                               /* imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = licPolicy.getTag().toString().substring(licPolicy.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnLICPolicy.setText(R.string.preview);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnLICPolicy.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bLic = false;
                            }
                            break;

                        case "12":

                            if (bForm16) {
                                form16.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = form16.getTag().toString().substring(form16.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                   /* btnForm16.setText(R.string.preview);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnForm16.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bForm16 = false;
                            }
                            break;

                        case "13":

                            if (bForm61) {
                                form61.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = form61.getTag().toString().substring(form61.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnForm61.setText(R.string.preview);*/
                                    // imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnForm61.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bForm61 = false;
                            }
                            break;

                        case "14":

                            if (bPension) {
                                pensionLetter.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = pensionLetter.getTag().toString().substring(pensionLetter.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnPensionLetter.setText(R.string.preview);*/
                                    //   imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnPensionLetter.setText(R.string.download);*/
                                    //  imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bPension = false;
                            }
                            break;

                        case "15":

                            if (bITR) {
                                itr.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = itr.getTag().toString().substring(itr.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnITR.setText(R.string.preview);*/
                                    //   imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnITR.setText(R.string.download);*/
                                    //  imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bITR = false;
                            }
                            break;

                        case "16":

                            if (bPNL) {
                                pnl.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = pnl.getTag().toString().substring(pnl.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnPNL.setText(R.string.preview);*/
                                    // imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnPNL.setText(R.string.download);*/
                                    // imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bPNL = false;
                            }
                            break;

                        case "23":

                            if (bDregreeMarkSheet) {
                                degreeMarkSheet.setTag(baseUrl + image);
                               /* imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = degreeMarkSheet.getTag().toString().substring(degreeMarkSheet.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btnDegreeMarksheet.setText(R.string.preview);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btnDegreeMarksheet.setText(R.string.download);*/
                                    //imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                bDregreeMarkSheet = false;
                            }
                            break;

                        case "21":

                            if (btenthmark) {
                                tenthMarksheet.setTag(baseUrl + image);
                                /*imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = tenthMarksheet.getTag().toString().substring(tenthMarksheet.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                   /* btntenthMarksheet.setText(R.string.preview);*/
                                    // imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btntenthMarksheet.setText(R.string.download);*/
                                    // imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                btenthmark = false;
                            }
                            break;

                        case "22":

                            if (btwelvethmark) {
                                twelvethMarksheet.setTag(baseUrl + image);
                               /* imgOtherDocUploadTick31.setVisibility(View.VISIBLE);
                                txtOtherDoc31.setVisibility(View.GONE);*/
                                strFileName = twelvethMarksheet.getTag().toString().substring(twelvethMarksheet.getTag().toString().lastIndexOf("/") + 1);

                                FileExtn = strFileName.substring(strFileName.lastIndexOf('.') + 1);

                                if (FileExtn.equals("pdf")) {
                                    /*btntwelvethMarksheet.setText(R.string.preview);*/
                                    // imgOtherDoc31.setBackgroundResource(R.drawable.pdf_image);
                                } else {
                                    /*btntwelvethMarksheet.setText(R.string.download);*/
                                    // imgOtherDoc31.setBackgroundResource(R.drawable.zip_image);
                                }
//                      Picasso.with(context).load(baseUrl + image).into(imgOtherDoc31);
                                btwelvethmark = false;
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


    /*===============================TILL HERE========================================*/
}
