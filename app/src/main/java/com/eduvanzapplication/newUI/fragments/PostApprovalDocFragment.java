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
import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.newViews.DashboardActivity;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static com.airbnb.lottie.model.layer.Layer.LayerType.Text;
import static com.eduvanzapplication.MainActivity.TAG;
import static com.eduvanzapplication.R.*;
import static com.eduvanzapplication.R.color.red;


public class PostApprovalDocFragment extends Fragment {

    static View view;
    private Context context;
    private Fragment mFragment;
    private int SELECT_DOC = 2;
    private static ProgressBar progressBar;
    SharedPreferences sharedPreferences;
    DownloadManager downloadManager;
    String userId;
    String downloadUrl = "", downloadSignedUrl = "";
    long downloadReference;

    private LinearLayout linManualBtn, lineSignBtn, linOTPBtn, linPayBtn, linData,linDownloadAgreement,linExpandCollapse;
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
//                        Country countryId = gson.fromJson(countryInfo, Country.class);
//                        countryData.append(countryId.getCode() + ": " + countryId.getName() + "\n");
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
        linDownloadAgreement = view.findViewById(R.id.linDownloadAgreement);
        btnExpandCollapse = view.findViewById(R.id.btnExpandCollapse);

        txtProcessingFee = view.findViewById(id.txtProcessingFee);

        context = getContext();
        mFragment = new PostApprovalDocFragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

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
                try {
                    String ipaddress = Utils.getIPAddress(true);
                    String url = MainActivity.mainUrl + "laf/getDigioDocumentIdForStudent";
                    Map<String, String> params = new HashMap<String, String>();
                    if (!Globle.isNetworkAvailable(context)) {
                        Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                    } else {
                        VolleyCallNew volleyCall = new VolleyCallNew();
//                        params.put("logged_id", userID);
                        params.put("created_by_ip", ipaddress);
                        if (!Globle.isNetworkAvailable(context)) {
                            Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();

                        } else {
                            volleyCall.sendRequest(context, url, null, mFragment, "getDigioDocumentIdForStudent", params, MainActivity.auth_token);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
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

        try {
            String url = MainActivity.mainUrl + "laf/getLoanDetails";
            Map<String, String> params = new HashMap<String, String>();
//                params.put("studentId","" );
            params.put("lead_id", MainActivity.lead_id);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
//                volleyCall.sendRequest(context, url, null, mFragment, "getLoanDetails", params,"");
                volleyCall.sendRequest(context, url, null, mFragment, "getLoanDetails", params, MainActivity.auth_token);
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

    }

    public void setLoanDetails(JSONObject jsonDataO) {
        Log.e(TAG, "setLoanDetails: " + jsonDataO);
        try {//
//            progressDialog.dismiss();
//            if (jsonDataO.getInt("status") == 1) {
//
//                JSONObject mObject = jsonDataO.optJSONObject("result");
//                String message = jsonDataO.getString("message");
//
//                JSONArray jsonArray1 = mObject.getJSONArray("leadsData");
//                mLeadsArrayList = new ArrayList<>();
//                if (jsonArray1.length() == 0){
//                    linLeadsLayout.setVisibility(View.GONE);
//                    relStartNewLayout.setVisibility(View.VISIBLE);
//                }
//                else {
//                    relStartNewLayout.setVisibility(View.GONE);
//                    linLeadsLayout.setVisibility(View.VISIBLE);
//                }
//                for (int i = 0; i < jsonArray1.length(); i++) {
//                    MLeads mLeads = new MLeads();
//                    JSONObject jsonleadStatus = jsonArray1.getJSONObject(i);
//
//                    try {
//
//                        if (!jsonleadStatus.getString("requested_loan_amount").toString().equals("null"))
//                            mLeads.requested_loan_amount  = jsonleadStatus.getString("requested_loan_amount");
//
//                        if (!jsonleadStatus.getString("lead_id").toString().equals("null"))
//                            mLeads.lead_id = jsonleadStatus.getString("lead_id");
//
//                        if (!jsonleadStatus.getString("application_id").toString().equals("null"))
//                            mLeads.application_id = jsonleadStatus.getString("application_id");
//
//                        if (!jsonleadStatus.getString("first_name").toString().equals("null"))
//                            mLeads.first_name = jsonleadStatus.getString("first_name");
//
//                        if (!jsonleadStatus.getString("middle_name").toString().equals("null"))
//                            mLeads.middle_name = jsonleadStatus.getString("middle_name");
//
//                        if (!jsonleadStatus.getString("last_name").toString().equals("null"))
//                            mLeads.last_name = jsonleadStatus.getString("last_name");
//
//                        if (!jsonleadStatus.getString("created_date_time").toString().equals("null"))
//                            mLeads.created_date_time = jsonleadStatus.getString("created_date_time");
//
//                        if (!jsonleadStatus.getString("profession").toString().equals("null"))
//                            mLeads.profession = jsonleadStatus.getString("profession");
//
//                        if (!jsonleadStatus.getString("fk_applicant_type_id").toString().equals("null"))
//                            mLeads.fk_applicant_type_id = jsonleadStatus.getString("fk_applicant_type_id");
//
//                        if (!jsonleadStatus.getString("has_coborrower").toString().equals("null"))
//                            mLeads.has_coborrower = jsonleadStatus.getString("has_coborrower");
//
//                        if (!jsonleadStatus.getString("course_name").toString().equals("null"))
//                            mLeads.course_name = jsonleadStatus.getString("course_name");
//
//                        if (!jsonleadStatus.getString("course_cost").toString().equals("null"))
//                            mLeads.course_cost = jsonleadStatus.getString("course_cost");
//
//                        if (!jsonleadStatus.getString("status_name").toString().equals("null"))
//                            mLeads.status_name = jsonleadStatus.getString("status_name");
//
//                        if (!jsonleadStatus.getString("location_name").toString().equals("null"))
//                            mLeads.location_name = jsonleadStatus.getString("location_name");
//
//                        if (!jsonleadStatus.getString("institute_name").toString().equals("null"))
//                            mLeads.institute_name = jsonleadStatus.getString("institute_name");
//
//                        if (!jsonleadStatus.getString("student_id").toString().equals("null"))
//                            mLeads.student_id = jsonleadStatus.getString("student_id");
//
//
//                    } catch (JSONException e) {
//                        String className = this.getClass().getSimpleName();
//                        String name = new Object() {
//                        }.getClass().getEnclosingMethod().getName();
//                        String errorMsg = e.getMessage();
//                        String errorMsgDetails = e.getStackTrace().toString();
//                        String errorLine = String.valueOf(e.getStackTrace()[0]);
//                        Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
//                    }
//                    mLeadsArrayList.add(mLeads);
//
//                }
//                adapter = new CardStackAdapter(mLeadsArrayList, context, getActivity());
//                rvLeads.setAdapter(adapter);

//            }
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
