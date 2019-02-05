package com.eduvanzapplication.newUI.newViews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.newUI.fragments.LoanApplicationFragment_1;
import com.eduvanzapplication.newUI.fragments.LoanApplicationFragment_2;
import com.eduvanzapplication.newUI.fragments.LoanApplicationFragment_3;
import com.eduvanzapplication.newUI.fragments.LoanApplicationFragment_4;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoanApplication extends AppCompatActivity {

    public static AppCompatActivity mActivity;
    public AppCompatActivity mActivity1;
    static Context context;
    static String userID = "", leadid;
    public static String userName = "", userId = "", student_id = "", mobile_no = "", auth_token = "";
    static String setFragmentCOBorrower = "", setFragmentDocUpload = "", setFragmentSignSubnit = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_loan_application);

            mActivity = this;
            context = this;
            mActivity1 = this;

            try {
                Bundle bundle = getIntent().getExtras();
                leadid = bundle.getString("lead_id");
            } catch (Exception e) {
                e.printStackTrace();
            }

            MainApplication.lead_id = leadid;
            try {
                Bundle extras = getIntent().getExtras();
                setFragmentCOBorrower = extras.getString("toCoBorrower", "0");
                setFragmentDocUpload = extras.getString("toDocUpload", "0");
                setFragmentSignSubnit = extras.getString("toSignSubmit", "0");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                userName = sharedPreferences.getString("first_name", "User");
                userId = sharedPreferences.getString("logged_id", "");
                mobile_no = sharedPreferences.getString("mobile_no", "");

            } catch (Exception e) {
                e.printStackTrace();
            }

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(R.string.title_loan_application);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
            toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

            if (setFragmentCOBorrower.equalsIgnoreCase("1") && setFragmentDocUpload.equalsIgnoreCase("0")) {
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_2()).commit();
            } else if (setFragmentDocUpload.equalsIgnoreCase("1") && setFragmentCOBorrower.equalsIgnoreCase("0") && setFragmentSignSubnit.equalsIgnoreCase("0")) {
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_3()).commit();
            } else if (setFragmentSignSubnit.equalsIgnoreCase("1") && setFragmentDocUpload.equalsIgnoreCase("0") && setFragmentCOBorrower.equalsIgnoreCase("0")) {
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_1()).commit();
//                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_3()).commit();
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            userID = sharedPreferences.getString("logged_id", "null");
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(LoanApplication.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (MainApplication.currrentFrag == 1 || MainApplication.currrentFrag == 2) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.savedialog, null);
                dialogBuilder.setView(dialogView);
                Button buttonNo = (Button) dialogView.findViewById(R.id.button_dialog_no);
                Button buttonSave = (Button) dialogView.findViewById(R.id.button_dialog_save);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    LoanApplication.super.onBackPressed();

//                        if(!MainApplication.borrowerValue13.equals("") &&
//                                !MainApplication.borrowerValue14.equals("") &&
//                                !MainApplication.borrowerValue15.equals("") &&
//                                !MainApplication.borrowerValue18.equals("")
//                                ||
//                                !MainApplication.coborrowerValue14.equals("") &&
//                                        !MainApplication.coborrowerValue15.equals("") &&
//                                        !MainApplication.coborrowerValue18.equals("") &&
//                                        !MainApplication.coborrowerValue13.equals(""))
//                        {
// //                       if (MainApplication.currrentFrag == 1) {
//   //                         MainApplication.apiCallBorrower(context, mActivity, userID);
//     //                   } else if (MainApplication.currrentFrag == 2) {
//       //                     MainApplication.apiCallCoBorrower(context, mActivity, userID);
//         //               }
//           //             }else {
//             //               alertDialog.dismiss();
//               //             Toast.makeText(getApplicationContext(), "Please provide Firstname, Lastname, Dob and Aadhaar No.", Toast.LENGTH_LONG).show();
//                 //       }

                    }
                });
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {

        try {
            if (MainApplication.currrentFrag == 1 || MainApplication.currrentFrag == 2) {
                Log.e(MainApplication.TAG, "onBackPressed: " + MainApplication.currrentFrag);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                // ...Irrelevant code for customizing the buttons and title
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.savedialog, null);
                dialogBuilder.setView(dialogView);
                Button buttonNo = (Button) dialogView.findViewById(R.id.button_dialog_no);
                Button buttonSave = (Button) dialogView.findViewById(R.id.button_dialog_save);
                final AlertDialog alertDialog = dialogBuilder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                buttonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                buttonSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoanApplication.super.onBackPressed();
                        finish();//coment this line
                        //                    if (!MainApplication.borrowerValue13.equals("") &&
                        //                            !MainApplication.borrowerValue14.equals("") &&
                        //                            !MainApplication.borrowerValue15.equals("") &&
                        //                            !MainApplication.borrowerValue18.equals("")
                        //                            ||
                        //                            !MainApplication.coborrowerValue14.equals("") &&
                        //                                    !MainApplication.coborrowerValue15.equals("") &&
                        //                                    !MainApplication.coborrowerValue18.equals("") &&
                        //                                    !MainApplication.coborrowerValue13.equals("")) {
//                        if (MainApplication.currrentFrag == 1) {
//                            MainApplication.apiCallBorrower(context, mActivity, userID);
//                        } else if (MainApplication.currrentFrag == 2) {
//                            MainApplication.apiCallCoBorrower(context, mActivity, userID);
//                        }

                        //                    } else {
                        //                        alertDialog.dismiss();
                        //                        Toast.makeText(getApplicationContext(), "Please provide Firstname, Lastname, Dob and Aadhaar No.", Toast.LENGTH_LONG).show();
                        //                    }
                    }
                });
            } else {
                finish();
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(LoanApplication.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    public void fromMain(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "fromMain" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {

                if (MainApplication.currrentFrag == 1) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("first_name", MainApplication.borrowerValue13);
                    editor.putString("last_name", MainApplication.borrowerValue14);
                    editor.apply();
                    editor.commit();
                }
                finish();

            } else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(LoanApplication.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    // Callback listener functions for Digio
    public void onSigningSuccess(String documentId) {
        Log.e(MainApplication.TAG, "onSigningSuccess: ");
        Toast.makeText(context, documentId + getString(R.string.signed_successfully), Toast.LENGTH_SHORT).show();
        LoanApplicationFragment_4 loanApplicationFragment_4 = new LoanApplicationFragment_4();
        loanApplicationFragment_4.digioSuccess(documentId);//DID180802180658447Q6OOLIITSFR2DJ
    }

    public void onSigningFailure(String documentId, int code, String response) {
        Log.e(MainApplication.TAG, "onSigningFailure: ");
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        LoanApplicationFragment_4 loanApplicationFragment_4 = new LoanApplicationFragment_4();
        loanApplicationFragment_4.digioFailure(documentId, code, response);
    }

//    public void initializePaytmPayment(JSONObject jsonData) {
//
//        try {
//            //getting paytm service
//            PaytmPGService Service = PaytmPGService.getProductionService();
//            String checksumHash = jsonData.optString("CHECKSUMHASH");
//
//            //creating a hashmap and adding all the values required
//            Map<String, String> paramMap = new HashMap<String, String>();
//
//            paramMap.put("MID", Globle.getInstance().paytm.getmId());
//            paramMap.put("ORDER_ID", Globle.getInstance().paytm.getOrderId());
//            paramMap.put("CUST_ID", Globle.getInstance().paytm.getCustId());
//            paramMap.put("CHANNEL_ID", Globle.getInstance().paytm.getChannelId());
//            paramMap.put("TXN_AMOUNT", Globle.getInstance().paytm.getTxnAmount());
//            paramMap.put("WEBSITE", Globle.getInstance().paytm.getWebsite());
////        paramMap.put("CALLBACK_URL",paytm.getCallBackUrl().concat("?").concat("ORDER_ID=").concat(paytm.orderId));
//            paramMap.put("CALLBACK_URL", Globle.getInstance().paytm.getCallBackUrl());
//            paramMap.put("CHECKSUMHASH", checksumHash);
//            paramMap.put("INDUSTRY_TYPE_ID", Globle.getInstance().paytm.getIndustryTypeId());
//
////            0 = {HashMap$HashMapEntry@5648} "MID" -> "Eduvan80947867008828"
////            1 = {HashMap$HashMapEntry@5649} "CALLBACK_URL" -> "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp"
////            2 = {HashMap$HashMapEntry@5650} "TXN_AMOUNT" -> "10.00"
////            3 = {HashMap$HashMapEntry@5651} "ORDER_ID" -> "ORDER200006679"
////            4 = {HashMap$HashMapEntry@5652} "WEBSITE" -> "APPPROD"
////            5 = {HashMap$HashMapEntry@5653} "INDUSTRY_TYPE_ID" -> "Retail109"
////            6 = {HashMap$HashMapEntry@5654} "CHECKSUMHASH" -> "ptMv3qYM2en9Y1TmVwRoax0yr8VgFW77RxYTZOewMFlfemLJ98lk6mVr8BmvFhX5myDKneXZ8scqta/h5FuKTdZihj580RPFiXUZoOgIsbA="
////            7 = {HashMap$HashMapEntry@5655} "CHANNEL_ID" -> "WAP"
////            8 = {HashMap$HashMapEntry@5656} "CUST_ID" -> "CUSTOMER200005319"
//
////            0 = {HashMap$HashMapEntry@6751} "MID" -> "Eduvan80947867008828"
////            1 = {HashMap$HashMapEntry@6752} "CALLBACK_URL" -> "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp"
////            2 = {HashMap$HashMapEntry@6753} "TXN_AMOUNT" -> "10.00"
////            3 = {HashMap$HashMapEntry@6754} "ORDER_ID" -> "ORDER100002176"
////            4 = {HashMap$HashMapEntry@6755} "WEBSITE" -> "APPPROD"
////            5 = {HashMap$HashMapEntry@6756} "INDUSTRY_TYPE_ID" -> "Retail109"
////            6 = {HashMap$HashMapEntry@6757} "CHECKSUMHASH" -> "Zo0R0dEqby7rSNFfX5wX6SBCAAsxTCZ1xwuzsC4t9Nfjm75puU6mJaJEgmbfggQWjZu3H+Hdn7owdzHw7JYtSsIR/viV415AoWlztCzvc0Q="
////            7 = {HashMap$HashMapEntry@6758} "CHANNEL_ID" -> "WAP"
////            8 = {HashMap$HashMapEntry@6759} "CUST_ID" -> "CUSTOMER100004464"
//
//            PaytmOrder order = new PaytmOrder((HashMap<String, String>) paramMap);
//
//            //intializing the paytm service
//            Service.initialize(order, null);
//
//            //finally starting the payment transaction
//            Service.startPaymentTransaction(context, true, true, new PaytmPaymentTransactionCallback() {
//                @Override
//                public void someUIErrorOccurred(String inErrorMessage) {
//                    // Some UI Error Occurred in Payment Gateway Activity.
//                    // // This may be due to initialization of views in
//                    // Payment Gateway Activity or may be due to //
//                    // initialization of webview. // Error Message details
//                    // the error occurred.
//                    Toast.makeText(context, "Payment Transaction response " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
//                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
//                    s.append("inErrorMessage-");
//                    s.append(inErrorMessage);
//                    Globle.appendLog(String.valueOf(s));
//
//                }
//
//
//                @Override
//                public void onTransactionResponse(Bundle inResponse) {
//                    Log.d("LOG", "Payment Transaction is successful " + inResponse);
//                    Toast.makeText(context, "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
//                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
//                    s.append("inResponse-");
//                    s.append(inResponse);
//                    Globle.appendLog(String.valueOf(s));
////"Bundle[{STATUS=TXN_SUCCESS,
//// CHECKSUMHASH=ENXZLPAIk3AlC/rdD7EfpMnG8Okxe0819nIQvFBjJL+aGnTrGIQfHHtGLFoiI+sWxVEFmOer+UCZiNaRNaRyOGbE4NMF66qRldhhHLJFaUs=,
//// BANKNAME=Union Bank of India, ORDERID=ORDER100008205, TXNAMOUNT=10.00,
//// TXNDATE=2018-07-10 14:23:01.0, MID=Eduvan80947867008828, TXNID=20180710111212800110168845030370887,
//// RESPCODE=01, PAYMENTMODE=DC, BANKTXNID=201819106425560, CURRENCY=INR, GATEWAYNAME=SBIFSS,
//// RESPMSG=Txn Success}]"
//                    if(inResponse.get("STATUS").equals("TXN_SUCCESS"))
////                    if(inResponse.get("STATUS").equals("TXN_FAILURE"))
//                    {
//                        if (inResponse != null) {
//                            String message = null;
//                            try {
//                                message = String.valueOf(inResponse.get("RESPMSG"));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            String TXNAMOUNT = null;
//                            try {
//                                TXNAMOUNT = String.valueOf(inResponse.get("TXNAMOUNT"));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            String TXNID = null;
//                            try {
//                                TXNID = String.valueOf(inResponse.get("TXNID"));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            String BANKTXNID = null;
//                            try {
//                                BANKTXNID = String.valueOf(inResponse.get("BANKTXNID"));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            try {
//                                String CHECKSUMHASH = String.valueOf(inResponse.get("CHECKSUMHASH"));
//                                String BANKNAME = String.valueOf(inResponse.get("BANKNAME"));
//                                String ORDERID = String.valueOf(inResponse.get("ORDERID"));
//                                String TXNDATE = String.valueOf(inResponse.get("TXNDATE"));
//                                String MID = String.valueOf(inResponse.get("MID"));
//                                String RESPCODE = String.valueOf(inResponse.get("RESPCODE"));
//                                String PAYMENTMODE = String.valueOf(inResponse.get("PAYMENTMODE"));
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//
//                            if(message.equalsIgnoreCase("TXN_SUCCESS")) {
//                                Log.e(MainApplication.TAG, "onActivityResult: " + "Transaction Successful!");
//                                /** API CALL **/
//                                try {
//                                    String ipaddress = Utils.getIPAddress(true);
//                                    String url = MainApplication.mainUrl + "epayment/kycPaymentCaptureWizard";
//                                    Map<String, String> params = new HashMap<String, String>();
//                                    VolleyCallNew volleyCall = new VolleyCallNew();
//                                    params.put("studentId", userID);
//                                    params.put("amount",TXNAMOUNT );
//                                    params.put("txnId", TXNID); // merchant ID
//                                    params.put("bankTxnId", BANKTXNID); // Bank ID
//                                    params.put("status", "1");
//                                    params.put("created_by_ip", ipaddress);
//                                    params.put("gateway_type", "Paytm");
//                                    volleyCall.sendRequest(LoanApplication.this, url, LoanApplication.this, null, "kycPaymentCaptureWizard", params);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
//                            }
//
//                            else {
//                                Log.e(MainApplication.TAG, "onActivityResult: " + "Transaction Successful!");
//                                /** API CALL **/
//                                try {
//                                    String ipaddress = Utils.getIPAddress(true);
//                                    String url = MainApplication.mainUrl + "epayment/kycPaymentCaptureWizard";
//                                    Map<String, String> params = new HashMap<String, String>();
//                                    VolleyCallNew volleyCall = new VolleyCallNew();
//                                    params.put("studentId", userID);
//                                    params.put("amount",TXNAMOUNT );
//                                    params.put("txnId", TXNID); // merchant ID
//                                    params.put("bankTxnId", BANKTXNID); // Bank ID
//                                    params.put("status", "0");
//                                    params.put("created_by_ip", ipaddress);
//                                    params.put("gateway_type", "Paytm");
//                                    volleyCall.sendRequest(LoanApplication.this, url, LoanApplication.this, null, "kycPaymentCaptureWizard", params);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//                                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
//                            }
//                            Toast.makeText(LoanApplication.this, message, Toast.LENGTH_LONG).show();
//
//                        } else {
//                            Toast.makeText(LoanApplication.this, "Payment not successful ", Toast.LENGTH_LONG).show();
//                            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
//                        }
//
//                    }
//                }
//
//                @Override
//                public void networkNotAvailable() { // If network is not
//                    // available, then this
//                    // method gets called.
//                }
//
//                @Override
//                public void clientAuthenticationFailed(String inErrorMessage) {
//                    // This method gets called if client authentication
//                    // failed. // Failure may be due to following reasons //
//                    // 1. Server error or downtime. // 2. Server unable to
//                    // generate checksum or checksum response is not in
//                    // proper format. // 3. Server failed to authenticate
//                    // that client. That is value of payt_STATUS is 2. //
//                    // Error Message describes the reason for failure.
//                    Toast.makeText(LoanApplication.this,"Back pressed. Transaction cancelled",Toast.LENGTH_LONG).show();
//                    Globle.appendLog(inErrorMessage);
//                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
//                    s.append("inErrorMessage-");
//                    s.append(inErrorMessage);
//                    Globle.appendLog(String.valueOf(s));
//
//                }
//
//                @Override
//                public void onErrorLoadingWebPage(int iniErrorCode,
//                                                  String inErrorMessage, String inFailingUrl) {
//                    Toast.makeText(LoanApplication.this,"Back pressed. Transaction cancelled",Toast.LENGTH_LONG).show();
//
//                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
//                    s.append("inErrorMessage-");
//                    s.append(inErrorMessage);
//                    s.append(" inFailingUrl-");
//                    s.append(inFailingUrl);
//                    Globle.appendLog(String.valueOf(s));
//
//                }
//
//                // had to be added: NOTE
//                @Override
//                public void onBackPressedCancelTransaction() {
//                    Toast.makeText(LoanApplication.this,"Back pressed. Transaction cancelled",Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
//                    Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
//                    Toast.makeText(LoanApplication.this, "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
//                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
//                    s.append("inErrorMessage-");
//                    s.append(inErrorMessage);
//                    Globle.appendLog(String.valueOf(s));
//
//                }
//
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {

            if (requestCode == 1) {
                if (data != null) {
                    String message = data.getStringExtra("status");
                    String[] resKey = data.getStringArrayExtra("responseKeyArray");
                    String[] resValue = data.getStringArrayExtra("responseValueArray");
                    String merTxn = "", bnkTxn = "", amt = "";

                    if (resKey != null && resValue != null) {
                        for (int i = 0; i < resKey.length; i++) {
                            System.out.println("  " + i + " resKey : " + resKey[i] + " resValue : " + resValue[i]);

                            if (resKey[i].equalsIgnoreCase("mer_txn")) {
                                merTxn = resValue[i];
                            } else if (resKey[i].equalsIgnoreCase("bank_txn")) {
                                bnkTxn = resValue[i];
                            } else if (resKey[i].equalsIgnoreCase("amt")) {
                                amt = resValue[i];
                            }
                        }
                        System.out.println(" status " + message);
                    }

                    if (message.equalsIgnoreCase("Transaction Failed!")) {
                        Log.e(MainApplication.TAG, "onActivityResult: " + "Transaction Failed!");
                        /** API CALL **/
                        try {
                            String ipaddress = Utils.getIPAddress(true);
                            String url = MainApplication.mainUrl + "epayment/kycPaymentCaptureWizard";
                            Map<String, String> params = new HashMap<String, String>();
                            VolleyCallNew volleyCall = new VolleyCallNew();
                            params.put("studentId", userID);
                            params.put("amount", amt);
                            params.put("txnId", merTxn); // merchant ID
                            params.put("bankTxnId", "null"); // Bank ID
                            params.put("status", "0");
                            params.put("created_by_ip", ipaddress);
                            volleyCall.sendRequest(LoanApplication.this, url, mActivity, null, "kycPaymentCaptureWizard", params, MainApplication.auth_token);
                            getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
                        } catch (Exception e) {
                            String className = this.getClass().getSimpleName();
                            String name = new Object() {
                            }.getClass().getEnclosingMethod().getName();
                            String errorMsg = e.getMessage();
                            String errorMsgDetails = e.getStackTrace().toString();
                            String errorLine = String.valueOf(e.getStackTrace()[0]);
                            Globle.ErrorLog(LoanApplication.this, className, name, errorMsg, errorMsgDetails, errorLine);
                        }
                    } else {
                        Log.e(MainApplication.TAG, "onActivityResult: " + "Transaction Successful!");
                        /** API CALL **/
                        try {
                            String ipaddress = Utils.getIPAddress(true);
                            String url = MainApplication.mainUrl + "epayment/kycPaymentCaptureWizard";
                            Map<String, String> params = new HashMap<String, String>();
                            VolleyCallNew volleyCall = new VolleyCallNew();
                            params.put("studentId", userID);
                            params.put("amount", amt);
                            params.put("txnId", merTxn); // merchant ID
                            params.put("bankTxnId", bnkTxn); // Bank ID
                            params.put("status", "1");
                            params.put("created_by_ip", ipaddress);
                            volleyCall.sendRequest(LoanApplication.this, url, mActivity, null, "kycPaymentCaptureWizard", params, MainApplication.auth_token);
                        } catch (Exception e) {
                            String className = this.getClass().getSimpleName();
                            String name = new Object() {
                            }.getClass().getEnclosingMethod().getName();
                            String errorMsg = e.getMessage();
                            String errorMsgDetails = e.getStackTrace().toString();
                            String errorLine = String.valueOf(e.getStackTrace()[0]);
                            Globle.ErrorLog(LoanApplication.this, className, name, errorMsg, errorMsgDetails, errorLine);
                        }
                        //                    LoanApplicationFragment_4 loanApplicationFragment_4 = new LoanApplicationFragment_4();
                        //                    loanApplicationFragment_4.atomPaymentSuccessful();
                        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
                    }
                    Toast.makeText(LoanApplication.this, message, Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(LoanApplication.this, R.string.payment_not_successful, Toast.LENGTH_LONG).show();
                    getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
                }

            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(LoanApplication.this, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }
}