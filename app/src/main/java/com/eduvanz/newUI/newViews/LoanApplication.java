package com.eduvanz.newUI.newViews;

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

import com.eduvanz.R;
import com.eduvanz.Utils;
import com.eduvanz.newUI.MainApplication;
import com.eduvanz.newUI.VolleyCallNew;
import com.eduvanz.newUI.fragments.LoanApplicationFragment_1;
import com.eduvanz.newUI.fragments.LoanApplicationFragment_2;
import com.eduvanz.newUI.fragments.LoanApplicationFragment_3;
import com.eduvanz.newUI.fragments.LoanApplicationFragment_4;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoanApplication extends AppCompatActivity {

    static AppCompatActivity mActivity;
    static Context context;
    static String userID = "";
    static String setFragmentCOBorrower = "", setFragmentDocUpload = "", setFragmentSignSubnit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_application);

        mActivity = this;
        context = this;

        try {
            Bundle extras = getIntent().getExtras();
            setFragmentCOBorrower = extras.getString("toCoBorrower", "0");
            setFragmentDocUpload = extras.getString("toDocUpload", "0");
            setFragmentSignSubnit = extras.getString("toSignSubmit", "0");

            Log.e(MainApplication.TAG, "+++++++:" + "setFragmentCOBorrower:" + setFragmentCOBorrower + "setFragmentDocUpload" + setFragmentDocUpload + "setFragmentSignSubnit" + setFragmentSignSubnit);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Loan Application");
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
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id", "null");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.e(MainApplication.TAG, "onBackPressed: " + MainApplication.currrentFrag);
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
                        if (MainApplication.currrentFrag == 1) {
                            MainApplication.apiCallBorrower(context, mActivity, userID);
                        } else if (MainApplication.currrentFrag == 2) {
                            MainApplication.apiCallCoBorrower(context, mActivity, userID);
                        }


//                        }else {
//                            alertDialog.dismiss();
//                            Toast.makeText(getApplicationContext(), "Please provide Firstname, Lastname, Dob and Aadhaar No.", Toast.LENGTH_LONG).show();
//                        }
                    }
                });
            } else {
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {

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

                    if (!MainApplication.borrowerValue13.equals("") &&
                            !MainApplication.borrowerValue14.equals("") &&
                            !MainApplication.borrowerValue15.equals("") &&
                            !MainApplication.borrowerValue18.equals("")
                            ||
                            !MainApplication.coborrowerValue14.equals("") &&
                                    !MainApplication.coborrowerValue15.equals("") &&
                                    !MainApplication.coborrowerValue18.equals("") &&
                                    !MainApplication.coborrowerValue13.equals("")) {
                        if (MainApplication.currrentFrag == 1) {
                            MainApplication.apiCallBorrower(context, mActivity, userID);
                        } else if (MainApplication.currrentFrag == 2) {
                            MainApplication.apiCallCoBorrower(context, mActivity, userID);
                        }


                    } else {
                        alertDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Please provide Firstname, Lastname, Dob and Aadhaar No.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            finish();
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

        }
    }


    // Callback listener functions for Digio
    public void onSigningSuccess(String documentId) {
        Log.e(MainApplication.TAG, "onSigningSuccess: ");
        Toast.makeText(context, documentId + " signed successfully", Toast.LENGTH_SHORT).show();
        LoanApplicationFragment_4 loanApplicationFragment_4 = new LoanApplicationFragment_4();
        loanApplicationFragment_4.digioSuccess(documentId);
    }

    public void onSigningFailure(String documentId, int code, String response) {
        Log.e(MainApplication.TAG, "onSigningFailure: ");
        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
        LoanApplicationFragment_4 loanApplicationFragment_4 = new LoanApplicationFragment_4();
        loanApplicationFragment_4.digioFailure(documentId, code, response);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
                    Log.e(MainApplication.TAG, "onActivityResult: "+"Transaction Failed!" );
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
                        volleyCall.sendRequest(context, url, mActivity, null, "kycPaymentCaptureWizard", params);
                        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(MainApplication.TAG, "onActivityResult: "+"Transaction Successful!" );
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
                        volleyCall.sendRequest(context, url, mActivity, null, "kycPaymentCaptureWizard", params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    LoanApplicationFragment_4 loanApplicationFragment_4 = new LoanApplicationFragment_4();
//                    loanApplicationFragment_4.atomPaymentSuccessful();
                    getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
                }
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(context, "Payment not successful ", Toast.LENGTH_LONG).show();
                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_loanapplication, new LoanApplicationFragment_4()).commit();
            }

        }
    }
}
