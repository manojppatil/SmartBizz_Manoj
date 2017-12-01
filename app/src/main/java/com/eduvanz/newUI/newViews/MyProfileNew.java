package com.eduvanz.newUI.newViews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.R;
import com.eduvanz.uploaddocs.PathFile;
import com.eduvanz.uploaddocs.Utility;
import com.eduvanz.newUI.VolleyCallNew;
import com.squareup.picasso.Picasso;

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
import java.util.HashMap;
import java.util.Map;

import static com.eduvanz.newUI.MainApplication.TAG;

public class MyProfileNew extends AppCompatActivity {

    static TextView textViewUserName, textViewPercentage, textView1, textView2, textView3, textView4,
            textView5;
    static ImageView imageViewEligiblityCheck, imageViewMobileVerification, imageViewEmailVerification,
            imageViewSocialScore, imageViewProfilePic;
    MainApplication mainApplication;
    static Context context;
    Button buttonCompleteNow;
    AppCompatActivity mActivity;
    String mobile_no="",user_email="";
    public static TextView applicationStatus,applicationStatusName,loanDetailsHeader,personalDetailsHeader,
            institute,instituteName,course,courseName,fees,feesName,loanAmount,loanAmountText,
            mobile,mobileName,email,emailName,address,addressName;

    String userID="", fname="", lname="", profilePic="";
    StringBuffer sb;
    public int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    public String userChoosenTask;
    String uploadFilePath = "";
    String urlup = MainApplication.mainUrl + "dashboard/changeImage";
    public static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_new);
        mainApplication = new MainApplication();
        context = this;
        mActivity = this;

        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
            userID = sharedPreferences.getString("logged_id", "");
            fname = sharedPreferences.getString("first_name", "--");
            lname = sharedPreferences.getString("last_name", "--");

            user_email = sharedPreferences.getString("user_email", "--");
            mobile_no = sharedPreferences.getString("mobile_no", "--");

            profilePic = sharedPreferences.getString("user_image", "");
        }catch (Exception e){
            e.printStackTrace();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));

        progressBar = (ProgressBar) findViewById(R.id.progressBar_myprofile);
        textViewUserName = (TextView) findViewById(R.id.textView_profile_username);
        textViewUserName.setText(fname + " "+ lname);
        mainApplication.applyTypeface(textViewUserName, context);
        textViewPercentage = (TextView) findViewById(R.id.textView_profilecomplete_percentage);
        mainApplication.applyTypeface(textViewPercentage, context);
        textView1 = (TextView) findViewById(R.id.textView_profile1);
        mainApplication.applyTypeface(textView1, context);
        textView2 = (TextView) findViewById(R.id.textView_profile2);
        mainApplication.applyTypeface(textView2, context);
        textView3 = (TextView) findViewById(R.id.textView_profile3);
        mainApplication.applyTypeface(textView3, context);
        textView4 = (TextView) findViewById(R.id.textView_profile4);
        mainApplication.applyTypeface(textView4, context);
        textView5 = (TextView) findViewById(R.id.textView_profile5);
        mainApplication.applyTypeface(textView5, context);

        imageViewEligiblityCheck = (ImageView) findViewById(R.id.imageView_myprofile_eliglibilityCheck);
        imageViewMobileVerification = (ImageView) findViewById(R.id.imageView_myprofile_mobileverification);
        imageViewEmailVerification = (ImageView) findViewById(R.id.imageView_myprofile_emailVerification);
        imageViewSocialScore = (ImageView) findViewById(R.id.imageView_myprofile_socialScore);

        imageViewProfilePic= (ImageView) findViewById(R.id.imageView_newuserpic);
        if(!profilePic.equalsIgnoreCase("")){
            Picasso.with(context).load(profilePic).placeholder(getResources().getDrawable(R.drawable.profilepic_placeholder)).into(imageViewProfilePic);
        }

        buttonCompleteNow = (Button) findViewById(R.id.button_mtprofile_completenow);
        mainApplication.applyTypeface(buttonCompleteNow, context);

        // new View start
        applicationStatus= (TextView) findViewById(R.id.applicationStatus_textview);
        applicationStatusName= (TextView) findViewById(R.id.applicationStatus_name_textview);
        loanDetailsHeader= (TextView) findViewById(R.id.loanDetails_textview);
        personalDetailsHeader= (TextView) findViewById(R.id.personal_details_textview);

        institute= (TextView) findViewById(R.id.institute_textviewN);
        instituteName= (TextView) findViewById(R.id.institute_name_textview);
        course= (TextView) findViewById(R.id.course_textView);
        courseName= (TextView) findViewById(R.id.course_name_textView);
        fees= (TextView) findViewById(R.id.fees_textViewN);
        feesName= (TextView) findViewById(R.id.fees_value_textView);
        loanAmount= (TextView) findViewById(R.id.loan_textview);
        loanAmountText= (TextView) findViewById(R.id.loan_value_textview);

        mobile= (TextView) findViewById(R.id.mobile_textviewN);
        mobileName= (TextView) findViewById(R.id.mobile_name_textview);
        email= (TextView) findViewById(R.id.email_textViewN);
        emailName= (TextView) findViewById(R.id.email_name_textView);
        address= (TextView) findViewById(R.id.address_textViewN);
        addressName= (TextView) findViewById(R.id.address_name_textView);

        applicationStatus.setTypeface(MainApplication.typefaceFont);
        applicationStatusName.setTypeface(MainApplication.typefaceFont);
        loanDetailsHeader.setTypeface(MainApplication.typefaceFont);
        personalDetailsHeader.setTypeface(MainApplication.typefaceFont);
        mainApplication.applyTypeface(institute, context);
        mainApplication.applyTypeface(instituteName, context);
        mainApplication.applyTypeface(course, context);
        mainApplication.applyTypeface(courseName, context);

        mainApplication.applyTypeface(fees, context);
        mainApplication.applyTypeface(feesName, context);
        mainApplication.applyTypeface(loanAmount, context);
        mainApplication.applyTypeface(loanAmountText, context);
        mainApplication.applyTypeface(mobile, context);
        mainApplication.applyTypeface(mobileName, context);
        mainApplication.applyTypeface(email, context);
        mainApplication.applyTypeface(emailName, context);
        mainApplication.applyTypeface(address, context);
        mainApplication.applyTypeface(courseName, context);
        mainApplication.applyTypeface(addressName, context);

        mobileName.setText("+91 "+mobile_no);
        emailName.setText(user_email);

// new View end
        buttonCompleteNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        imageViewProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        /**API CALL**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getProfileDashbBoardStatus";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId", userID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, mActivity, null, "myProfile", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /** API CALL GET Dates**/
        try {
            String url = MainApplication.mainUrl + "dashboard/getProfileDashbBoardStatus";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId",userID);
            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, mActivity, null, "ProfileDashbBoardStatusData", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }


    /** RESPONSE OF API CALL**/
    public void myProfile(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "myProfile" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");

                String ec = jsonObject.getString("eligibility");
                String mv = jsonObject.getString("mobileVerified");
                String ev = jsonObject.getString("emailVerified");
                String ss = jsonObject.getString("friendlyScore");

                String profilePercentage = jsonObject.getString("profileDashboardStats");

                textViewPercentage.setText(profilePercentage+"%");

                if(ec.equalsIgnoreCase("1")){
                    imageViewEligiblityCheck.setImageDrawable(getResources().getDrawable(R.drawable.verified));
                }else {
                    imageViewEligiblityCheck.setImageDrawable(getResources().getDrawable(R.drawable.crossbox));
                }

                if(mv.equalsIgnoreCase("1")){
                    imageViewMobileVerification.setImageDrawable(getResources().getDrawable(R.drawable.verified));
                }else {
                    imageViewMobileVerification.setImageDrawable(getResources().getDrawable(R.drawable.crossbox));
                }

                if(ev.equalsIgnoreCase("1")){
                    imageViewEmailVerification.setImageDrawable(getResources().getDrawable(R.drawable.verified));
                }else {
                    imageViewEmailVerification.setImageDrawable(getResources().getDrawable(R.drawable.crossbox));
                }

                if(ss.equalsIgnoreCase("1")){
                    imageViewSocialScore.setImageDrawable(getResources().getDrawable(R.drawable.verified));
                }else {
                    imageViewSocialScore.setImageDrawable(getResources().getDrawable(R.drawable.crossbox));
                }

            }else {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProfileDashbBoardStatusData(JSONObject jsonDataO) {
        Log.e("", "setProfileDashbBoardStatusData: "+jsonDataO );
        Log.e(TAG, "getScrappingdates: "+jsonDataO );
        try {
            if (jsonDataO.getInt("status") == 1) {

                String courseFees = null, addressT = null, loanAmount = null,
                        courseT = null, instituteT = null, applicationStatus = null;
                JSONObject mObject = jsonDataO.optJSONObject("result");
                applicationStatus= mObject.getString("applicationStatus");
                instituteT= mObject.getString("institute");
                courseT= mObject.getString("course");
                loanAmount= mObject.getString("loanAmount");
                courseFees= mObject.getString("courseFees");
                addressT= mObject.getString("address");

                applicationStatusName.setText(applicationStatus);
                instituteName.setText(instituteT);
                courseName.setText(courseT);
                addressName.setText(addressT);
                feesName.setText(courseFees);
                loanAmountText.setText(loanAmount);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfileNew.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= Utility.checkPermission(MyProfileNew.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }
    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        uploadFilePath = destination.toString();
//        Log.e("TAG", "onCaptureImageResult: "+uploadFilePath );
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

        imageViewProfilePic.setImageBitmap(thumbnail);
        if (uploadFilePath != null) {
            // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                    Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                    uploadFile(uploadFilePath);
                }
            }).start();
        } else {
            Toast.makeText(MyProfileNew.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                Uri selectedFileUri = data.getData();
                uploadFilePath = PathFile.getPath(this,selectedFileUri);
                Log.e("TAG", "onSelectFromGalleryResult: "+uploadFilePath );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageViewProfilePic.setImageBitmap(bm);

        if (uploadFilePath != null) {
            // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //creating new thread to handle Http Operations
//                            uploadFile(uploadFilePath);
                    Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                    uploadFile(uploadFilePath);
                }
            }).start();
        } else {
            Toast.makeText(MyProfileNew.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
        }

    }



    /** android upload file to server **/
    public int uploadFile(final String selectedFilePath) {

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

            runOnUiThread(new Runnable() {
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
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploads\";filename=\""
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
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"studentId\";studentId=" + userID + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(userID);
                dataOutputStream.writeBytes(lineEnd);


//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
////                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
////                        + selectedFilePath + "\"" + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"documentType\";documentType=" + documentType + "" + lineEnd);
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(documentType);
//                dataOutputStream.writeBytes(lineEnd);
//
//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
////                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
////                        + selectedFilePath + "\"" + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"documentTypeNo\";documentTypeNo=" + documentTypeNo + "" + lineEnd);
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(documentTypeNo);
//                dataOutputStream.writeBytes(lineEnd);
//
//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
////                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
////                        + selectedFilePath + "\"" + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"coborrower_id\";coborrower_id=" + coBorrowerID + "" + lineEnd);
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(coBorrowerID);
//                dataOutputStream.writeBytes(lineEnd);


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
//                [{"code":1,"file_name":"284f0b39af461ad8ae3ee17ac20ee5f4.pdf","message":"Document uploaded successfully"}]

//                try {
//                    JSONArray mJson= new JSONArray(sb.toString());
//                    final JSONObject mData= mJson.getJSONObject(0);
//                    final int code=mData.optInt("code");
//                    Log.e("TAG", "uploadFile: code "+code);
//                    if(code == 1)
//                    {
//                        runOnUiThread(new Runnable()
//                        {
//                            @Override
//                            public void run() {
//                                mDialog.dismiss();
//                                Log.e("TAG", "uploadFile: code 1 "+code);
////                                    Toast.makeText(UploadActivityBorrower.this, mData.getString("message").toString(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(UploadActivityBorrower.this,"File Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                                imageViewUploadTick.setVisibility(View.VISIBLE);
//                            }
//                        });
//
////                        finish();
//
//                    }else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    mDialog.dismiss();
//                                    Log.e("TAG", "uploadFile: code 2 "+code);
//                                    Toast.makeText(UploadActivityBorrower.this, mData.getString("message").toString(), Toast.LENGTH_SHORT).show();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
////                        finish();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                try {
                    final JSONObject mJson = new JSONObject(sb.toString());
                    final String mData = mJson.getString("status");
                    final String mData1 = mJson.getString("message");

                    Log.e("TAG", "uploadFile: code " + mData);
                    if (mData.equalsIgnoreCase("1")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = mJson.getJSONObject("result");
                                    String image = jsonObject.getString("img_profile");

                                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("user_image",image);
                                    editor.commit();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                Log.e("TAG", "uploadFile: code 1 " + mData);
                                Toast.makeText(MyProfileNew.this, mData1, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(UploadActivityBorrower.this,"File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

//                        finish();

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                Log.e("TAG", "uploadFile: code 2 " + mData);
                                Toast.makeText(MyProfileNew.this, mData1, Toast.LENGTH_SHORT).show();
                            }
                        });
//                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //response code of 200 indicates the server status OK
                if (serverResponseCode == 200) {
                    runOnUiThread(new Runnable() {
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyProfileNew.this, "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(MyProfileNew.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MyProfileNew.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
//            dialog.dismiss();
            return serverResponseCode;
        }

    }//---------------------------------------END OF UPLOAD FILE----------------------------------//

}
