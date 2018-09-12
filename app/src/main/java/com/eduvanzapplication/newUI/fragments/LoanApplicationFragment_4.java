package com.eduvanzapplication.newUI.fragments;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.atom.mobilepaymentsdk.PayActivity;
import com.digio.in.esignsdk.Digio;
import com.digio.in.esignsdk.DigioConfig;
import com.digio.in.esignsdk.DigioEnvironment;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.newViews.LoanApplication;
import com.eduvanzapplication.Util.Paytm;
import com.eduvanzapplication.uploaddocs.PathFile;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static com.eduvanzapplication.newUI.MainApplication.staticdownloadurl;
/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */


public class LoanApplicationFragment_4 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    public static ProgressBar progressBar;
    public static String uploadFilePath = "", userID = "", userFirst = "", userLast = "", ipaddress = "",
            applicationStatus = "", userEmailid="", amount="", transactionId="";
    public int SELECT_DOC = 2;
    public static Button buttonNext, buttonPrevious, buttonUpload, buttonDownload,
            buttonDownloadSignedApplication, buttonPay;
    Typeface typefaceFont, typefaceFontBold;
    public static TextView textView1, textView2, textView3, textView17, textView4, textView5, textView6, textView7,
            textView8, textView9, textView10, textView11, textView12, textView13, textView14, textView19,
            textView15, textView16, textView18;
    public static RadioButton radioButtonManual, radioButtonEsign,rdpaytm,rdatom;
    public static LinearLayout linearLayoutManual, linearLayout_paymentDetails,linpaymentoptions,linnote;
    MainApplication mainApplication;
    String userId;
    String downloadUrl = "", downloadSignedUrl = "";
    long downloadReference;
    SharedPreferences sharedPreferences;
    DownloadManager downloadManager;
    StringBuffer sb;
    public static RadioGroup radioGroupla4,radioGroupPayment;

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadReference == referenceId) {

//                Button cancelDownload = (Button) findViewById(R.id.cancelDownload);
//                cancelDownload.setEnabled(false);
//file:///storage/emulated/0/Android/data/com.eduvanzapplication/files/Download/SIGNED APPLICATIONEdutesterEduvanz1530095441962.pdf
                int ch;
                ParcelFileDescriptor file;
                StringBuffer strContent = new StringBuffer("");
                StringBuffer countryData = new StringBuffer("");

                //parse the JSON data and display on the screen
                try {
                    file = downloadManager.openDownloadedFile(downloadReference);
                    FileInputStream fileInputStream
                            = new ParcelFileDescriptor.AutoCloseInputStream(file);

                    while ((ch = fileInputStream.read()) != -1)
                        strContent.append((char) ch);

//                    JSONObject responseObj = new JSONObject(strContent.toString());
//                    JSONArray countriesObj = responseObj.getJSONArray("countries");
//
//                    for (int i = 0; i < countriesObj.length(); i++) {
//                        Gson gson = new Gson();
//                        String countryInfo = countriesObj.getJSONObject(i).toString();
//                        Country country = gson.fromJson(countryInfo, Country.class);
//                        countryData.append(country.getCode() + ": " + country.getName() + "\n");
//                    }
//
//                    TextView showCountries = (TextView) findViewById(R.id.countryData);
//                    showCountries.setText(countryData.toString());

                    progressBar.setVisibility(View.GONE);
                    Toast toast = Toast.makeText(context,
                            "Downloading of File just finished", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    public LoanApplicationFragment_4() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loanapplication_4, container, false);
        context = getContext();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mainApplication = new MainApplication();
        mFragment = new LoanApplicationFragment_4();

        MainApplication.currrentFrag = 4;

        sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        downloadUrl = sharedPreferences.getString("laf_download_url", "");
        userID = sharedPreferences.getString("logged_id", "");
        userEmailid = sharedPreferences.getString("user_email", "");
        userFirst = sharedPreferences.getString("first_name", "");
        userLast = sharedPreferences.getString("last_name", "");
        downloadSignedUrl = sharedPreferences.getString("signed_application_url", "");
        applicationStatus = sharedPreferences.getString("signed_appstatus", "0");

        ipaddress = Utils.getIPAddress(true);

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");
//        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
//        userId= sharedPreferences.getString("logged_id", "");
        final FragmentTransaction transaction = getFragmentManager().beginTransaction();

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar_signsubmit);

        radioGroupla4 = (RadioGroup) view.findViewById(R.id.radioGroup_la4);
        radioGroupPayment = (RadioGroup) view.findViewById(R.id.radioGroupPayment);
        textView1 = (TextView) view.findViewById(R.id.textView1_l4);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) view.findViewById(R.id.textView2_l4);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) view.findViewById(R.id.textView3_l4);
        mainApplication.applyTypefaceBold(textView3, context);

        textView4 = (TextView) view.findViewById(R.id.textView_signsubmit_1);
        mainApplication.applyTypeface(textView4, context);
        textView5 = (TextView) view.findViewById(R.id.textView_signsubmit_2);
        mainApplication.applyTypeface(textView5, context);

        textView6 = (TextView) view.findViewById(R.id.textView_signsubmit_3);
        mainApplication.applyTypeface(textView6, context);
        textView7 = (TextView) view.findViewById(R.id.textView_signsubmit_4);
        mainApplication.applyTypeface(textView7, context);
        textView8 = (TextView) view.findViewById(R.id.textView_signsubmit_5);
        mainApplication.applyTypeface(textView8, context);
        textView9 = (TextView) view.findViewById(R.id.textView_signsubmit_6);
        mainApplication.applyTypeface(textView9, context);
        textView10 = (TextView) view.findViewById(R.id.textView_signsubmit_7);
        mainApplication.applyTypeface(textView10, context);
        textView11 = (TextView) view.findViewById(R.id.textView_signsubmit_8);
        mainApplication.applyTypeface(textView11, context);
        textView12 = (TextView) view.findViewById(R.id.textView_signsubmit_9);
        mainApplication.applyTypeface(textView12, context);
        textView13 = (TextView) view.findViewById(R.id.textView_signsubmit_10);
        mainApplication.applyTypeface(textView13, context);
        textView14 = (TextView) view.findViewById(R.id.textView_signsubmit_11);
        mainApplication.applyTypeface(textView14, context);
        textView15 = (TextView) view.findViewById(R.id.textView_signsubmit_12);
        mainApplication.applyTypeface(textView15, context);
        textView16 = (TextView) view.findViewById(R.id.textView_signsubmit_13);
        mainApplication.applyTypeface(textView16, context);
        textView17 = (TextView) view.findViewById(R.id.textView_signsubmit_14);
        mainApplication.applyTypeface(textView17, context);
        textView18 = (TextView) view.findViewById(R.id.textView_signsubmit_15);
        mainApplication.applyTypeface(textView18, context);
        textView19 = (TextView) view.findViewById(R.id.textView_signsubmit_0);
        mainApplication.applyTypeface(textView19, context);

        buttonNext = (Button) view.findViewById(R.id.button_submit_loanappfragment4);
        buttonNext.setTypeface(typefaceFontBold);

        buttonPrevious = (Button) view.findViewById(R.id.button_previous_loanappfragment4);
        buttonPrevious.setTypeface(typefaceFontBold);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EligibilityCheckFragment_4 eligibilityCheckFragment_4 = new EligibilityCheckFragment_4();
//                transaction.replace(R.id.frameLayout_loanapplication, eligibilityCheckFragment_4).commit();
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoanApplicationFragment_3 loanApplicationFragment_3 = new LoanApplicationFragment_3();
                transaction.replace(R.id.frameLayout_loanapplication, loanApplicationFragment_3).commit();
            }
        });

        linearLayoutManual = (LinearLayout) view.findViewById(R.id.linearLayout_manual);

        linearLayout_paymentDetails = (LinearLayout) view.findViewById(R.id.linearLayout_paymentdetails);
        linpaymentoptions = (LinearLayout) view.findViewById(R.id.linpaymentoptions);
        linnote = (LinearLayout) view.findViewById(R.id.linnote);

        radioButtonManual = (RadioButton) view.findViewById(R.id.radioButton_manual);
        rdpaytm = (RadioButton) view.findViewById(R.id.rdpaytm);
        rdatom = (RadioButton) view.findViewById(R.id.rdatom);

        mainApplication.applyTypeface(radioButtonManual, context);
        radioButtonManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (radioButtonManual.isChecked()) {
                    linearLayoutManual.setVisibility(View.VISIBLE);
                } else if (!radioButtonManual.isChecked()) {
                    linearLayoutManual.setVisibility(View.GONE);
                }
            }
        });

        radioButtonEsign = (RadioButton) view.findViewById(R.id.radioButton_esign);
        mainApplication.applyTypeface(radioButtonEsign, context);

        radioButtonEsign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (radioButtonEsign.isChecked()) {
                    /** API CALL **/
                    try {
                        String ipaddress = Utils.getIPAddress(true);
                        String url = MainApplication.mainUrl + "laf/getDigioDocumentIdForStudent";
                        Map<String, String> params = new HashMap<String, String>();
                        if(!Globle.isNetworkAvailable(context))
                        {
                            Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                        } else {
                            VolleyCallNew volleyCall = new VolleyCallNew();
                            params.put("logged_id", userID);
                            params.put("created_by_ip", ipaddress);
                            if(!Globle.isNetworkAvailable(context))
                            {
                                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                            } else {
                                volleyCall.sendRequest(context, url, null, mFragment, "getDigioDocumentIdForStudent", params);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        buttonDownload = (Button) view.findViewById(R.id.button_signnsubmit_downloadApplication);
        mainApplication.applyTypeface(buttonDownload, context);

        buttonPay = (Button) view.findViewById(R.id.button_signnsubmit_payment);
        buttonPay.setBackgroundColor(((Activity)context).getResources().getColor(R.color.grey));
//        buttonPay.setEnabled(false);

        buttonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textView6.getText().toString().trim().equals("Signed")) {
                 /** API CALL **/
                try {
                String url = MainApplication.mainUrl + "epayment/getKycPaymentRelatedInfo";
                Map<String, String> params = new HashMap<String, String>();
                    if(!Globle.isNetworkAvailable(context))
                    {
                        Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();
                        params.put("studentId", userID);
                        if(!Globle.isNetworkAvailable(context))
                        {
                            Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                        } else {
                            volleyCall.sendRequest(context, url, null, mFragment, "getKycPaymentRelatedInfo", params);
                        }
                    }
                } catch (Exception e) {
                     e.printStackTrace(); }
            }
            else {
                    Toast.makeText(context, "Please Sign the documents first", Toast.LENGTH_SHORT).show();

                }

            }
        });

        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Log.e(MainApplication.TAG, "downloadUrl+++++: " + downloadUrl);
                    Uri Download_Uri = Uri.parse(downloadUrl);

                    DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
                    request.setAllowedOverRoaming(false);
                    request.setTitle("Downloading LAF Document");
//                request.setDescription("Downloading " + "Sample" + ".png");
                    request.setVisibleInDownloadsUi(true);
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Eduvanz/"  + "/" + "LAF" + ".pdf");
                    progressBar.setVisibility(View.VISIBLE);
                    downLoad(downloadUrl, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        });

        buttonDownloadSignedApplication = (Button) view.findViewById(R.id.button_downloadSignedApplication);
        buttonDownloadSignedApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                if(!downloadSignedUrl.equalsIgnoreCase("")){
                    if(downloadSignedUrl.length()<5)
                    {
                        downloadSignedUrl = staticdownloadurl;
                    }
                    downLoad(downloadSignedUrl, 2);
                }else {
                    Toast.makeText(context, "Something went wrong, please try again later.", Toast.LENGTH_SHORT).show();

                }

            }
        });

        buttonUpload = (Button) view.findViewById(R.id.button_signnsubmit_uploadApplication);
        mainApplication.applyTypeface(buttonUpload, context);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galleryDocIntent();

            }
        });

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        context.registerReceiver(downloadReceiver, filter);

        if (applicationStatus.equalsIgnoreCase("1")) {
            linearLayoutManual.setVisibility(View.GONE);
            buttonDownloadSignedApplication.setVisibility(View.VISIBLE);
            textView7.setVisibility(View.GONE);
            radioGroupla4.setVisibility(View.GONE);
            textView6.setText("Signed");
            textView6.setTextColor(((Activity)context).getResources().getColor(R.color.colorPrimary));
//            buttonPay.setEnabled(true);
            buttonPay.setBackgroundColor(((Activity)context).getResources().getColor(R.color.colorRed));

        }


        /** API CALL Status of payment and esign**/
        try {
            String url = MainApplication.mainUrl + "epayment/getSignAndSubmitDetails";
            Map<String, String> params = new HashMap<String, String>();
            if(!Globle.isNetworkAvailable(context))
            {
                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                VolleyCallNew volleyCall = new VolleyCallNew();
                params.put("loggedId", userID);
                if(!Globle.isNetworkAvailable(context))
                {
                    Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                } else {
                    volleyCall.sendRequest(context, url, null, mFragment, "getSignAndSubmitDetails", params);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        return view;
    }


    // Callback listener functions for Digio
    public void digioSuccess(String documentId){

        /** API CALL **/
        try {
            String ipaddress = Utils.getIPAddress(true);
            String url = MainApplication.mainUrl + "laf/onSuccessfulRegisterStudentESignCase";
            Map<String, String> params = new HashMap<String, String>();
            VolleyCallNew volleyCall = new VolleyCallNew();
            if(!Globle.isNetworkAvailable(context))
            {
                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                params.put("logged_id", userID);                //610
                params.put("created_by_ip", ipaddress);         //192.168.1.16
                volleyCall.sendRequest(context, url, null, mFragment, "onSuccessfulRegisterStudentESignCase", params);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        linearLayoutManual.setVisibility(View.GONE);
        buttonDownloadSignedApplication.setVisibility(View.VISIBLE);
        textView7.setVisibility(View.GONE);
        radioGroupla4.setVisibility(View.GONE);
        textView6.setText("Signed");
        textView6.setTextColor(((Activity)context).getResources().getColor(R.color.colorPrimary));
    }

    public void digioFailure(String documentId, int code, String response){
        radioButtonEsign.setChecked(false);
    }

    public void atomPaymentSuccessful(){
        linearLayout_paymentDetails.setVisibility(View.VISIBLE);
        textView11.setText("Paid");
        textView11.setTextColor(((Activity)context).getResources().getColor(R.color.colorPrimary));
    }


    public void downLoad(String uri, int status) {
        try {
            String fname = "";
            if (status == 1) {
                fname = "LAF" + userFirst + userLast + System.currentTimeMillis() + ".pdf";
            } else if (status == 2) {
                fname = "SIGNED APPLICATION" + userFirst + userLast + System.currentTimeMillis() + ".pdf";
            }
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
            //Set the local destination for the downloaded file to a path within the application's external files directory
//        if(isImage) {

            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fname);//
//        }else {
//            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_PICTURES, "DATA"+System.currentTimeMillis()+".pdf");
//        }
            //Enqueue a new download and same the referenceId
            downloadReference = downloadManager.enqueue(request);

//        TextView showCountries = (TextView) findViewById(R.id.countryData);
//        showCountries.setText("Getting data from Server, Please WAIT...");

//        Button checkStatus = (Button) findViewById(R.id.checkStatus);
//        checkStatus.setEnabled(true);
//        Button cancelDownload = (Button) findViewById(R.id.cancelDownload);
//        cancelDownload.setEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void galleryDocIntent() {
        Intent intent = new Intent();
        intent.setType("application/pdf");  // for all types of file
//        intent.setType("application/pdf"); // for pdf
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_DOC);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_DOC) {

                Uri selectedImage = data.getData();
                uploadFilePath = PathFile.getPath(context, selectedImage);
                Log.e("TAG", "onActivityResult: DOC PATH " + uploadFilePath);
                if (!uploadFilePath.equalsIgnoreCase("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //creating new thread to handle Http Operations
                            uploadFile(uploadFilePath);
                        }
                    }).start();
                }

            }
        }

    }

    public int uploadFile(final String selectedFilePath) {
        String urlup = MainApplication.mainUrl + "laf/lafDocumentUpload";


        Log.e(MainApplication.TAG, "urlup++++++: " + selectedFilePath + ", ipaddress : " + ipaddress);

        int serverResponseCode = 0;
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
            progressBar.setVisibility(View.GONE);
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
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"logged_id\";logged_id=" + userID + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(userID);
                dataOutputStream.writeBytes(lineEnd);

                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"created_by_ip\";created_by_ip=" + ipaddress + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(ipaddress);
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
                    final JSONObject mData2 = mJson.getJSONObject("result");

                    Log.e("TAG", "uploadFile: code " + mData);
                    if (mData.equalsIgnoreCase("1")) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mData1, Toast.LENGTH_SHORT).show();

                                linearLayoutManual.setVisibility(View.GONE);
                                buttonDownloadSignedApplication.setVisibility(View.VISIBLE);
                                textView7.setVisibility(View.GONE);
                                radioGroupla4.setVisibility(View.GONE);
                                textView6.setText("Signed");
                                textView6.setTextColor(((Activity)context).getResources().getColor(R.color.colorPrimary));
                                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                try {
                                    editor.putString("signed_application_url", mData2.getString("docPath"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                editor.apply();
                                editor.commit();
                                try {
                                    downloadSignedUrl = mData2.getString("docPath");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });

                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mData1, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
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
                        Toast.makeText(context, "File Not Found", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(context, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(context, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
//            dialog.dismiss();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            });
            return serverResponseCode;
        }

    }


    /**
     * RESPONSE OF API CALL
     **/

    public void getDigioDocumentIdForStudent(JSONObject jsonData) {//{"result":{"documentId":"DID180627125727122W11P5DAJQX5NZ2","email":"vijay.shukla@eduvanz.in"},"status":1,"message":"Please follow the instructions to esign the form."}
        try {
            Log.e("SERVER CALL", "getDocuments" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                String documentID = jsonObject.getString("documentId");//DID180627125727122W11P5DAJQX5NZ2
                String email = jsonObject.getString("email");//vijay.shukla@eduvanz.in

                // Invoke Esign


                    final Digio digio = new Digio();
                    DigioConfig digioConfig = new DigioConfig();
                    digioConfig.setLogo("https://lh3.googleusercontent.com/v6lR_JSsjovEzLBkHPYPbVuw1161rkBjahSxW0d38RT4f2YoOYeN2rQSrcW58MAfuA=w300"); //Your company logo
                    digioConfig.setEnvironment(DigioEnvironment.PRODUCTION);   //Stage is sandbox

                    try {
                        digio.init((Activity)context, digioConfig);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        digio.esign(documentID, email);
                        Log.e(MainApplication.TAG, "downloadUrldownloadUrl: "+downloadUrl + " userEmailiduserEmailid"+ userEmailid );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }



            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSuccessfulRegisterStudentESignCase(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "onSuccessfulRegisterStudentESignCase" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                downloadSignedUrl = jsonObject.getString("docPath");


            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getKycPaymentRelatedInfo(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "getKycPaymentRelatedInfo" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                amount = jsonObject.getString("amount");
                transactionId = jsonObject.getString("transactionId");

                if(rdpaytm.isChecked())
                {
                    try {
                        try {
                        Globle.getInstance().paytm = new Paytm(
                            "Eduvan80947867008828",
                            "WAP",
                            amount,
                            "APPPROD",
                            "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp",
                            "Retail109"
                            );
                        } catch (Exception e) {
                          e.printStackTrace();
                       }

                        String url = "https://eduvanz.com/Paytm/generateChecksum.php/";
                        Map<String, String> params = new HashMap<String, String>();
                        VolleyCallNew volleyCall = new VolleyCallNew();
//                        ((LoanApplication)mFragment).mActivity1;
//                      params.put("MID", ((LoanApplication)getActivity()).paytm.getmId());
                        params.put("MID", Globle.getInstance().paytm.getmId());
                        params.put("ORDER_ID", Globle.getInstance().paytm.getOrderId());
                        params.put("CUST_ID", Globle.getInstance().paytm.getCustId());
                        params.put("CHANNEL_ID", Globle.getInstance().paytm.getChannelId());
                        params.put("TXN_AMOUNT", Globle.getInstance().paytm.getTxnAmount());
                        params.put("WEBSITE", Globle.getInstance().paytm.getWebsite());
                        params.put("CALLBACK_URL", Globle.getInstance().paytm.getCallBackUrl());
                        params.put("INDUSTRY_TYPE_ID", Globle.getInstance().paytm.getIndustryTypeId());
                        if(!Globle.isNetworkAvailable(context))
                        {
                            Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                        } else {
                            volleyCall.sendRequest(context, url, null, mFragment, "initializePaytmPayment", params);
                        }
//                        volleyCall.sendRequest(context, url,((LoanApplication) context).mActivity1 , null, "initializePaytmPayment", params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                 if(rdatom.isChecked())
                {
                    Intent newPayIntent = new Intent(context, PayActivity.class);
                    newPayIntent.putExtra("merchantId", "197");
                    newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be 0
                    newPayIntent.putExtra("loginid", "41554");
                    newPayIntent.putExtra("password", "EDUVANZ@123");
                    newPayIntent.putExtra("prodid", "EDUVANZ");
                    newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be ?INR?
                    newPayIntent.putExtra("clientcode", "001");
                    newPayIntent.putExtra("custacc", "100000036600");
                    newPayIntent.putExtra("amt", amount);//Should be 3 decimal number i.e 51.000
                    newPayIntent.putExtra("txnid", transactionId);
                    newPayIntent.putExtra("date", "25/08/2015 18:31:00");//Should be in same format
                    newPayIntent.putExtra("bankid", ""); //Should be valid bank id // Optional
                    newPayIntent.putExtra("discriminator", "All"); // NB or IMPS or All ONLY (value should be same as commented)
                    newPayIntent.putExtra("signature_request", "32caffa3053906fef7");
                    newPayIntent.putExtra("signature_response", "e65db3704c7b4a7b3b");
//                newPayIntent.putExtra("ru", "https://payment.atomtech.in/paynetz/epi/fts"); // FOR PRODUCTION
                    newPayIntent.putExtra("ru", "https://payment.atomtech.in/mobilesdk/param"); // FOR PRODUCTION

//                newPayIntent.putExtra("merchantId", "197");
//                newPayIntent.putExtra("txnscamt", "0"); //Fixed. Must be 0
//                newPayIntent.putExtra("loginid", "41554");
//                newPayIntent.putExtra("password", "EDUVANZ@123");
//                newPayIntent.putExtra("prodid", "EDUVANZ");
//                newPayIntent.putExtra("txncurr", "INR"); //Fixed. Must be INR
//                newPayIntent.putExtra("clientcode", "007");
//                newPayIntent.putExtra("custacc", "100000036600");
//                newPayIntent.putExtra("channelid", "INT");
//                newPayIntent.putExtra("amt", myamount);//Should be 3 decimal number i.e 100.000
//                newPayIntent.putExtra("txnid", "2365F315");
//                newPayIntent.putExtra("date", "30/12/2015 18:31:00");//Should be in same format
//                newPayIntent.putExtra("cardtype", "CC");// CC or DC ONLY (value should be same as commented)
//                newPayIntent.putExtra("cardAssociate", "VISA");// for VISA and MASTER.  MAESTRO ONLY (value should be same as commented)
//                newPayIntent.putExtra("surcharge", "NO");// Should be passed YES if surcharge is applicable else pass NO
//                newPayIntent.putExtra("signature_request", "32caffa3053906fef7");
//                newPayIntent.putExtra("signature_response", "e65db3704c7b4a7b3b");
//                newPayIntent.putExtra("ru", "https://payment.atomtech.in/paynetz/epi/fts"); // FOR UAT (Testing)

                    //Optinal Parameters
                    newPayIntent.putExtra("customerName", jsonObject.getString("fullName")); //Only for Name
                    newPayIntent.putExtra("customerEmailID", jsonObject.getString("email"));//Only for Email ID
                    newPayIntent.putExtra("customerMobileNo", jsonObject.getString("mobile"));//Only for Mobile Number
                    newPayIntent.putExtra("billingAddress", jsonObject.getString("mobile"));//Only for Address
                    newPayIntent.putExtra("optionalUdf9", "OPTIONAL DATA 1");// Can pass any data
//                newPayIntent.putExtra("mprod", mprod); // Pass data in XML format, only for Multi product
                    ((Activity)context).startActivityForResult(newPayIntent, 1);
                }


            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializePaytmPayment(JSONObject jsonData) {

        try {
            //getting paytm service
            PaytmPGService Service = PaytmPGService.getProductionService();
            String checksumHash = jsonData.optString("CHECKSUMHASH");

            //creating a hashmap and adding all the values required
            Map<String, String> paramMap = new HashMap<String, String>();

            paramMap.put("MID", Globle.getInstance().paytm.getmId());
            paramMap.put("ORDER_ID", Globle.getInstance().paytm.getOrderId());
            paramMap.put("CUST_ID", Globle.getInstance().paytm.getCustId());
            paramMap.put("CHANNEL_ID", Globle.getInstance().paytm.getChannelId());
            paramMap.put("TXN_AMOUNT", Globle.getInstance().paytm.getTxnAmount());
            paramMap.put("WEBSITE", Globle.getInstance().paytm.getWebsite());
//        paramMap.put("CALLBACK_URL",paytm.getCallBackUrl().concat("?").concat("ORDER_ID=").concat(paytm.orderId));
            paramMap.put("CALLBACK_URL", Globle.getInstance().paytm.getCallBackUrl());
            paramMap.put("CHECKSUMHASH", checksumHash);
            paramMap.put("INDUSTRY_TYPE_ID", Globle.getInstance().paytm.getIndustryTypeId());

//            0 = {HashMap$HashMapEntry@5648} "MID" -> "Eduvan80947867008828"
//            1 = {HashMap$HashMapEntry@5649} "CALLBACK_URL" -> "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp"
//            2 = {HashMap$HashMapEntry@5650} "TXN_AMOUNT" -> "10.00"
//            3 = {HashMap$HashMapEntry@5651} "ORDER_ID" -> "ORDER200006679"
//            4 = {HashMap$HashMapEntry@5652} "WEBSITE" -> "APPPROD"
//            5 = {HashMap$HashMapEntry@5653} "INDUSTRY_TYPE_ID" -> "Retail109"
//            6 = {HashMap$HashMapEntry@5654} "CHECKSUMHASH" -> "ptMv3qYM2en9Y1TmVwRoax0yr8VgFW77RxYTZOewMFlfemLJ98lk6mVr8BmvFhX5myDKneXZ8scqta/h5FuKTdZihj580RPFiXUZoOgIsbA="
//            7 = {HashMap$HashMapEntry@5655} "CHANNEL_ID" -> "WAP"
//            8 = {HashMap$HashMapEntry@5656} "CUST_ID" -> "CUSTOMER200005319"

//            0 = {HashMap$HashMapEntry@6751} "MID" -> "Eduvan80947867008828"
//            1 = {HashMap$HashMapEntry@6752} "CALLBACK_URL" -> "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp"
//            2 = {HashMap$HashMapEntry@6753} "TXN_AMOUNT" -> "10.00"
//            3 = {HashMap$HashMapEntry@6754} "ORDER_ID" -> "ORDER100002176"
//            4 = {HashMap$HashMapEntry@6755} "WEBSITE" -> "APPPROD"
//            5 = {HashMap$HashMapEntry@6756} "INDUSTRY_TYPE_ID" -> "Retail109"
//            6 = {HashMap$HashMapEntry@6757} "CHECKSUMHASH" -> "Zo0R0dEqby7rSNFfX5wX6SBCAAsxTCZ1xwuzsC4t9Nfjm75puU6mJaJEgmbfggQWjZu3H+Hdn7owdzHw7JYtSsIR/viV415AoWlztCzvc0Q="
//            7 = {HashMap$HashMapEntry@6758} "CHANNEL_ID" -> "WAP"
//            8 = {HashMap$HashMapEntry@6759} "CUST_ID" -> "CUSTOMER100004464"

            PaytmOrder order = new PaytmOrder((HashMap<String, String>) paramMap);

            //intializing the paytm service
            Service.initialize(order, null);

            //finally starting the payment transaction
            Service.startPaymentTransaction(context, true, true, new PaytmPaymentTransactionCallback() {
                @Override
                public void someUIErrorOccurred(String inErrorMessage) {
                    // Some UI Error Occurred in Payment Gateway Activity.
                    // // This may be due to initialization of views in
                    // Payment Gateway Activity or may be due to //
                    // initialization of webview. // Error Message details
                    // the error occurred.
                    Toast.makeText(context, "Payment Transaction response " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
                    s.append("inErrorMessage-");
                    s.append(inErrorMessage);
//                    Globle.appendLog(String.valueOf(s));

                }


                @Override
                public void onTransactionResponse(Bundle inResponse) {
                    Log.d("LOG", "Payment Transaction is successful " + inResponse);
                    Toast.makeText(context, "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
                    s.append("inResponse-");
                    s.append(inResponse);
//                    Globle.appendLog(String.valueOf(s));
//"Bundle[{STATUS=TXN_SUCCESS,
// CHECKSUMHASH=ENXZLPAIk3AlC/rdD7EfpMnG8Okxe0819nIQvFBjJL+aGnTrGIQfHHtGLFoiI+sWxVEFmOer+UCZiNaRNaRyOGbE4NMF66qRldhhHLJFaUs=,
// BANKNAME=Union Bank of India, ORDERID=ORDER100008205, TXNAMOUNT=10.00,
// TXNDATE=2018-07-10 14:23:01.0, MID=Eduvan80947867008828, TXNID=20180710111212800110168845030370887,
// RESPCODE=01, PAYMENTMODE=DC, BANKTXNID=201819106425560, CURRENCY=INR, GATEWAYNAME=SBIFSS,
// RESPMSG=Txn Success}]"
                    if(inResponse.get("STATUS").equals("TXN_SUCCESS"))
//                    if(inResponse.get("STATUS").equals("TXN_FAILURE"))
                    {
                        if (inResponse != null) {
                            String message = String.valueOf(inResponse.get("RESPMSG"));
                            String CHECKSUMHASH = String.valueOf(inResponse.get("CHECKSUMHASH"));
                            String BANKNAME = String.valueOf(inResponse.get("BANKNAME"));
                            String ORDERID = String.valueOf(inResponse.get("ORDERID"));
                            String TXNAMOUNT = String.valueOf(inResponse.get("TXNAMOUNT"));
                            String TXNDATE = String.valueOf(inResponse.get("TXNDATE"));
                            String MID = String.valueOf(inResponse.get("MID"));
                            String TXNID = String.valueOf(inResponse.get("TXNID"));
                            String RESPCODE = String.valueOf(inResponse.get("RESPCODE"));
                            String BANKTXNID = String.valueOf(inResponse.get("BANKTXNID"));
                            String PAYMENTMODE = String.valueOf(inResponse.get("PAYMENTMODE"));

//                            String[] resKey = data.getStringArrayExtra("responseKeyArray");
//                            String[] resValue = data.getStringArrayExtra("responseValueArray");
//                            String merTxn = "", bnkTxn = "", amt = "";

//                            if (resKey != null && resValue != null) {
//                                for (int i = 0; i < resKey.length; i++) {
//                                    System.out.println("  " + i + " resKey : " + resKey[i] + " resValue : " + resValue[i]);
//
//                                    if (resKey[i].equalsIgnoreCase("mer_txn")) {
//                                        merTxn = resValue[i];
//                                    } else if (resKey[i].equalsIgnoreCase("bank_txn")) {
//                                        bnkTxn = resValue[i];
//                                    } else if (resKey[i].equalsIgnoreCase("amt")) {
//                                        amt = resValue[i];
//                                    }
//                                }
//                                System.out.println(" status " + message);
//                            }
                               if(message.equalsIgnoreCase("TXN_SUCCESS")) {
                                Log.e(MainApplication.TAG, "onActivityResult: " + "Transaction Successful!");
                                /** API CALL **/
                                try {
                                    String ipaddress = Utils.getIPAddress(true);
                                    String url = MainApplication.mainUrl + "epayment/kycPaymentCaptureWizard";
                                    Map<String, String> params = new HashMap<String, String>();
                                    VolleyCallNew volleyCall = new VolleyCallNew();
                                    params.put("studentId", userID);
                                    params.put("amount",TXNAMOUNT );
                                    params.put("txnId", TXNID); // merchant ID
                                    params.put("bankTxnId", BANKTXNID); // Bank ID
                                    params.put("status", "1");
                                    params.put("created_by_ip", ipaddress);
                                    params.put("gateway_type", "Paytm");
                                    if(!Globle.isNetworkAvailable(context))
                                    {
                                        Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                                    } else {
                                        volleyCall.sendRequest(context, url, null, mFragment, "kycPaymentCaptureWizard", params);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
                            }

                            else {
                                Log.e(MainApplication.TAG, "onActivityResult: " + "Transaction Successful!");
                                /** API CALL **/
                                try {
                                    String ipaddress = Utils.getIPAddress(true);
                                    String url = MainApplication.mainUrl + "epayment/kycPaymentCaptureWizard";
                                    Map<String, String> params = new HashMap<String, String>();
                                    VolleyCallNew volleyCall = new VolleyCallNew();
                                    params.put("studentId", userID);
                                    params.put("amount",TXNAMOUNT );
                                    params.put("txnId", TXNID); // merchant ID
                                    params.put("bankTxnId", BANKTXNID); // Bank ID
                                    params.put("status", "1");
                                    params.put("created_by_ip", ipaddress);
                                    params.put("gateway_type", "Paytm");
                                    if(!Globle.isNetworkAvailable(context))
                                    {
                                        Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                                    } else {
                                        volleyCall.sendRequest(context, url, null, mFragment, "kycPaymentCaptureWizard", params);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                //getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
                            }
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(context, "Payment not successful ", Toast.LENGTH_LONG).show();
//                            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
                        }

                    }
                }

                @Override
                public void networkNotAvailable() { // If network is not
                    // available, then this
                    // method gets called.
                }

                @Override
                public void clientAuthenticationFailed(String inErrorMessage) {
                    // This method gets called if client authentication
                    // failed. // Failure may be due to following reasons //
                    // 1. Server error or downtime. // 2. Server unable to
                    // generate checksum or checksum response is not in
                    // proper format. // 3. Server failed to authenticate
                    // that client. That is value of payt_STATUS is 2. //
                    // Error Message describes the reason for failure.
                    Toast.makeText(context,"Back pressed. Transaction cancelled",Toast.LENGTH_LONG).show();
//                    Globle.appendLog(inErrorMessage);
                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
                    s.append("inErrorMessage-");
                    s.append(inErrorMessage);
//                    Globle.appendLog(String.valueOf(s));

                }

                @Override
                public void onErrorLoadingWebPage(int iniErrorCode,
                                                  String inErrorMessage, String inFailingUrl) {
                    Toast.makeText(context,"Back pressed. Transaction cancelled",Toast.LENGTH_LONG).show();

                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
                    s.append("inErrorMessage-");
                    s.append(inErrorMessage);
                    s.append(" inFailingUrl-");
                    s.append(inFailingUrl);
//                    Globle.appendLog(String.valueOf(s));

                }

                // had to be added: NOTE
                @Override
                public void onBackPressedCancelTransaction() {
                    Toast.makeText(context,"Back pressed. Transaction cancelled",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                    Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                    Toast.makeText(context, "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
                    s.append("inErrorMessage-");
                    s.append(inErrorMessage);
//                    Globle.appendLog(String.valueOf(s));

                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void getSignAndSubmitDetails(JSONObject jsonData) {//{"result":{"docFinish":1,"lafDownloadPath":"http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/lafdocumentstore\/610\/A180626002_Loan_Application_1530083542.pdf","signedApplicationStatus":1,"docPath":"http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/lafmanualupload\/610\/loanapplication_1530085197.pdf","paymentStatus":"0","transactionId":"","transactionAmount":800,"transactionDate":""},"status":1,"message":"success"}
        try {
            Log.e("SERVER CALL", "getSignAndSubmitDetails" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                JSONObject jsonObject = jsonData.getJSONObject("result");

                downloadSignedUrl = jsonObject.getString("docPath");
                String paymentStatus = jsonObject.getString("paymentStatus");
                staticdownloadurl = downloadSignedUrl;
//                edi.putString("checkForImageSlider",checkForImageSlider);
//                editor.commit();

                if(jsonObject.getString("signedApplicationStatus").equalsIgnoreCase("1")){
                    try {
                        linearLayoutManual.setVisibility(View.GONE);
                        buttonDownloadSignedApplication.setVisibility(View.VISIBLE);
                        textView7.setVisibility(View.GONE);
                        radioGroupla4.setVisibility(View.GONE);
                        textView6.setText("Signed");
                        textView6.setTextColor(((Activity)context).getResources().getColor(R.color.colorPrimary));
                    } catch (Resources.NotFoundException e) {
                        e.printStackTrace();
                    }
                }

                if(paymentStatus.equalsIgnoreCase("1")){
                    linearLayout_paymentDetails.setVisibility(View.VISIBLE);
                    linpaymentoptions.setVisibility(View.GONE);
                    linnote.setVisibility(View.GONE);
                    textView11.setText("Paid");
                    textView11.setTextColor(((Activity)context).getResources().getColor(R.color.colorPrimary));
                    textView14.setText(jsonObject.getString("transactionAmount"));
                    textView16.setText(jsonObject.getString("transactionId"));
                    textView18.setText(jsonObject.getString("transactionDate"));
                    buttonPay.setVisibility(View.GONE);
                }

            } else {
//                buttonPay.setEnabled(false);
//                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
