package com.eduvanzapplication.newUI.fragments;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.newViews.DashboardActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static com.airbnb.lottie.model.layer.Layer.LayerType.Text;
import static com.eduvanzapplication.R.*;
import static com.eduvanzapplication.R.color.red;


public class PostApprovalDocFragment extends Fragment {

    static View view;
    public static Context context;
    public static Fragment mFragment;
    public int SELECT_DOC = 2;
    public static ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    DownloadManager downloadManager;
    String userId;
    String downloadUrl = "", downloadSignedUrl = "";
    long downloadReference;

    private LinearLayout linManualBtn, lineSignBtn, linOTPBtn, linPayBtn, linData,linExpandCollapse;
    private ImageButton btnExpandCollapse;

    TextView txtProcessingFee;

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (downloadReference == referenceId) {

//                Button cancelDownload = (Button) findViewById(R.id.cancelDownload);
//                cancelDownload.setEnabled(false);
//file:///storage/emulated/0/Android/data/com.eduvanzemployees/files/Download/SIGNED APPLICATIONEdutesterEduvanz1530095441962.pdf

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
                    Toast toast = Toast.makeText(context, R.string.downloading_of_file_just_finished, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP, 25, 400);
                    toast.show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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
        }
    };

    public PostApprovalDocFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(layout.fragment_postapprovaldoc, container, false);

        linExpandCollapse = view.findViewById(R.id.linExpandCollapse);
        linManualBtn = view.findViewById(R.id.linManualBtn);
        lineSignBtn = view.findViewById(R.id.lineSignBtn);
        linOTPBtn = view.findViewById(R.id.linOTPBtn);
        linPayBtn = view.findViewById(R.id.linPayBtn);
        linData = view.findViewById(R.id.linData);
        btnExpandCollapse = view.findViewById(R.id.btnExpandCollapse);

        txtProcessingFee = view.findViewById(id.txtProcessingFee);

        context = getContext();
        mFragment = new DashboardFragmentNew();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


//        SpannableString string = new SpannableString("\u25CF Processing Fee");
//        string.setSpan(new BulletSpan(40, 20), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        SpannableString string1 = new SpannableString("Text with\nBullet point");
//        string1.setSpan(new BulletSpan(40, red, 20), 10, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

//        txtProcessingFee.setText(string);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        linManualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NewLeadActivity.gender = "1";
                linManualBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                lineSignBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linOTPBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                manualSignInDialog();
            }
        });

        lineSignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NewLeadActivity.gender = "2";
                linManualBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                lineSignBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
                linOTPBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
            }
        });

        linOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NewLeadActivity.gender = "2";
                linManualBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                lineSignBtn.setBackground(getResources().getDrawable(R.drawable.border_circular));
                linOTPBtn.setBackground(getResources().getDrawable(R.drawable.border_circular_blue_filled));
            }
        });

        linExpandCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (linData.getVisibility() == 0) {
                    linData.setVisibility(View.GONE);
                    btnExpandCollapse.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_chevron_down));
                } else {
                    linData.setVisibility(View.VISIBLE);
                    btnExpandCollapse.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_chevron_up));
                }

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    private void manualSignInDialog() {

        View view = getLayoutInflater().inflate(layout.signin_options, null);
        LinearLayout linClose = view.findViewById(R.id.linClose);
        LinearLayout linDownload = view.findViewById(R.id.linDownload);
        LinearLayout linUpload = view.findViewById(R.id.linUpload);

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(context)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .setFooterView(view);

        CFAlertDialog cfAlertDialog = builder.show();
        cfAlertDialog.setCancelable(false);
        cfAlertDialog.setCanceledOnTouchOutside(false);


        linClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cfAlertDialog.dismiss();
            }
        });


        linDownload.setOnClickListener(new View.OnClickListener() {
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
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/Eduvanz/" + "/" + "LAF" + ".pdf");
                    progressBar.setVisibility(View.VISIBLE);
                    downLoad(downloadUrl, 1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        linUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    galleryDocIntent();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }

    private void galleryDocIntent() {
        Intent intent = new Intent();
        intent.setType("application/pdf");  // for all types of file
//        intent.setType("application/pdf"); // for pdf
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_DOC);
    }

    public void downLoad(String uri, int status) {
        try {
            String fname = "";
            if (status == 1) {
//                fname = "LAF" + userFirst + userLast + System.currentTimeMillis() + ".pdf";
            } else if (status == 2) {
//                fname = "SIGNED APPLICATION" + userFirst + userLast + System.currentTimeMillis() + ".pdf";
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

}
