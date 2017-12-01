package com.eduvanz.newUI.fragments;


import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

import com.eduvanz.Utils;
import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;

import org.json.JSONObject;

import com.eduvanz.uploaddocs.PathFile;

import org.json.JSONException;

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
/**
 * A simple {@link Fragment} subclass.
 */

/**
 * SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner
 */


public class LoanApplicationFragment_4 extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    Button buttonNext, buttonPrevious, buttonUpload, buttonDownload, buttonDownloadSignedApplication;
    Typeface typefaceFont, typefaceFontBold;
    TextView textView1, textView2, textView3, textView17, textView4,textView5,textView6,textView7,
            textView8,textView9,textView10,textView11,textView12,textView13,textView14, textView19,
            textView15,textView16,textView18;
    RadioButton radioButtonManual, radioButtonEsign;
    LinearLayout linearLayoutManual;
    MainApplication mainApplication;
    String userId;
    String downloadUrl="", downloadSignedUrl="";
    long downloadReference;
    DownloadManager downloadManager;
    public int SELECT_DOC = 2;
    static String uploadFilePath = "", userID="", userFirst="", userLast="", ipaddress="", applicationStatus="";
    StringBuffer sb;
    RadioGroup radioGroupla4;
    public static ProgressBar progressBar;

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

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        downloadUrl = sharedPreferences.getString("laf_download_url", "");
        userID = sharedPreferences.getString("logged_id", "");
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

        radioButtonManual = (RadioButton) view.findViewById(R.id.radioButton_manual);
        mainApplication.applyTypeface(radioButtonManual, context);
        radioButtonManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(radioButtonManual.isChecked()){
                    linearLayoutManual.setVisibility(View.VISIBLE);
                }else if (!radioButtonManual.isChecked()){
                    linearLayoutManual.setVisibility(View.GONE);
                }
            }
        });

        radioButtonEsign = (RadioButton) view.findViewById(R.id.radioButton_esign);
        mainApplication.applyTypeface(radioButtonEsign, context);

        buttonDownload = (Button) view.findViewById(R.id.button_signnsubmit_downloadApplication);
        mainApplication.applyTypeface(buttonDownload, context);

        buttonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(MainApplication.TAG, "downloadUrl+++++: "+downloadUrl);
//               Uri Download_Uri = Uri.parse(downloadUrl);

//                DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
//                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//                request.setAllowedOverRoaming(false);
//                request.setTitle("Downloading LAF Document");
////                request.setDescription("Downloading " + "Sample" + ".png");
//                request.setVisibleInDownloadsUi(true);
//                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Eduvanz/"  + "/" + "LAF" + ".pdf");
                progressBar.setVisibility(View.VISIBLE);
                downLoad(downloadUrl, 1);

            }

        });

        buttonDownloadSignedApplication = (Button) view.findViewById(R.id.button_downloadSignedApplication);
        buttonDownloadSignedApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                downLoad(downloadSignedUrl, 2);
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

        if(applicationStatus.equalsIgnoreCase("1")){
            linearLayoutManual.setVisibility(View.GONE);
            buttonDownloadSignedApplication.setVisibility(View.VISIBLE);
            textView7.setVisibility(View.GONE);
            radioGroupla4.setVisibility(View.GONE);
            textView6.setText("Signed");
            textView6.setTextColor(getResources().getColor(R.color.colorPrimary));
        }

        return view;
    }

    public void downLoad(String uri, int status)
    {
        String fname = "";
        if(status == 1){
            fname = "LAF"+userFirst+userLast+System.currentTimeMillis()+".pdf";
        }else if(status == 2){
            fname = "SIGNED APPLICATION"+userFirst+userLast+System.currentTimeMillis()+".pdf";
        }
        downloadManager = (DownloadManager)context.getSystemService(context.DOWNLOAD_SERVICE);
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

        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, fname);
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
    }


    private BroadcastReceiver downloadReceiver = new BroadcastReceiver()
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadReference == referenceId) {

//                Button cancelDownload = (Button) findViewById(R.id.cancelDownload);
//                cancelDownload.setEnabled(false);

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
                 if(!uploadFilePath.equalsIgnoreCase("")){
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
        String urlup=MainApplication.mainUrl + "laf/lafDocumentUpload";


        Log.e(MainApplication.TAG, "urlup++++++: "+selectedFilePath + ", ipaddress : "+ipaddress  );

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
                                textView6.setTextColor(getResources().getColor(R.color.colorPrimary));
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


}
