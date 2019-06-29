package com.eduvanzapplication.newUI.newViews;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
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
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.uploaddocs.PathFile;
import com.eduvanzapplication.uploaddocs.Utility;
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
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


import de.hdodenhof.circleimageview.CircleImageView;

import static com.eduvanzapplication.newUI.newViews.DashboardActivity.textViewName;
import static com.eduvanzapplication.newUI.newViews.DashboardActivity.textView_mobileNo;
import static com.eduvanzapplication.newUI.newViews.DashboardActivity.textViewEmail;
import static com.eduvanzapplication.newUI.newViews.DashboardActivity.ivUserPic;
import static com.eduvanzapplication.newUI.newViews.DashboardActivity.userPic;
import static com.eduvanzapplication.newUI.newViews.DashboardActivity.userFirst;
import static com.eduvanzapplication.newUI.newViews.DashboardActivity.userEmail;
import static com.eduvanzapplication.newUI.newViews.DashboardActivity.userMobileNo;

public class EditProfile extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    public static CircleImageView profileImage;
    public EditText firstName, middleName, lastName, email;
    private TextView mobile_number;
    private LinearLayout submit_button;
    public int REQUEST_CAMERA = 0, SELECT_FILE = 1, SELECT_DOC = 2;
    public String userChoosenTask;
    String uploadFilePath = "";
    ProgressBar progressBar;
    StringBuffer sb;
    static String first_name, last_name, img_profile, email_id;
    public static AppCompatActivity mActivity;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = EditProfile.this;
        mActivity = this;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//---HIDE STATUS BAR

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_arrow_white);
//        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        profileImage = findViewById(R.id.profileImage);
        firstName = findViewById(R.id.edtFnameProfile);
        middleName = findViewById(R.id.edtMnameProfile);
        lastName = findViewById(R.id.edtLnameProfile);
        email = findViewById(R.id.edtProfileEmail);
        mobile_number = findViewById(R.id.edtProfileMobileNumber);
        submit_button = findViewById(R.id.linSubmit);
        progressBar = findViewById(R.id.progressBar_editProfile);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEditProfileData();
                finish();
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();

            }
        });

        setProfileApiCall();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(context);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Gallery")) {
                    userChoosenTask = "Choose from Gallery";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
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

        if (uploadFilePath != null) {
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //creating new thread to handle Http Operations
                    Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                    uploadFile(uploadFilePath);
                }
            }).start();
        } else {
            Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
        }
//        if(imageViewProfilePicSelect == imageViewKycProfilePhoto){
//            imageViewProfilePicSelect.setImageBitmap(thumbnail);
//            buttonKycProfilePhoto.setVisibility(View.VISIBLE);
//        }else if(imageViewProfilePicSelect == imageViewKycProfilePhoto_co){
//            imageViewProfilePicSelect.setImageBitmap(thumbnail);
//            buttonKycProfilePhoto_co.setVisibility(View.VISIBLE);
//        }

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

        if (uploadFilePath != null) {
            progressBar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //creating new thread to handle Http Operations
                    Log.e("TAG", "File:Path absolute : new" + uploadFilePath);
                    uploadFile(uploadFilePath);
                }
            }).start();
        } else {
            Toast.makeText(context, "Please choose a File First", Toast.LENGTH_SHORT).show();
        }
//            profileImage.setImageBitmap(bm);
    }


    public int uploadFile(final String selectedFilePath) {
        String urlup = MainActivity.mainUrl + "authorization/updateProfilePicture";
        Map<String, String> params = new HashMap<>();
        params.put("studentId", DashboardActivity.student_id);

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
        String[] fileExtn = fileName.split(".");


        if (!selectedFile.isFile()) {
            //dialog.dismiss();
            try {
                progressBar.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            EditProfile.this.runOnUiThread(new Runnable() {
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
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"studentId\";studentId=" + DashboardActivity.student_id + "" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(DashboardActivity.student_id);
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
                    final String document_path = mJson.getString("document_path");
                    final String baseUrl = new JSONObject(mJson.getJSONObject("result").toString()).getString("baseUrl").toString();

                    Log.e("TAG", " 2252: " + new Date().toLocaleString());//1538546658896.jpg/
                    if (mData.equalsIgnoreCase("1")) {
                        EditProfile.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                                {"document_path":"student\/3423\/user_profile_1557394413.jpg","result":{"baseUrl":"http:\/\/159.89.204.41\/eduvanzbeta\/"},"status":1,"message":"Profile Picture Updated Successfully"}

                                try {
                                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("user_img", baseUrl + document_path);
//                                    editor.putString("email", mJson.getString("email"));
                                    editor.apply();
                                    editor.commit();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                try {
                                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
//                                    email.setText(sharedPreferences.getString("email", ""));
//                                    firstName.setText(sharedPreferences.getString("first_name", ""));
//                                    lastName.setText(sharedPreferences.getString("last_name", ""));
                                    Picasso.with(context)
                                            .load(sharedPreferences.getString("user_img", ""))
                                            .into(profileImage);

                                    userPic = sharedPreferences.getString("user_img", "");
                                    if (!userPic.equalsIgnoreCase("")) {
                                        Picasso.with(context).load(userPic).placeholder(getResources().getDrawable(R.drawable.profilepic_placeholder)).into(ivUserPic);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


//                                try {
//                                    SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileData", Context.MODE_PRIVATE);
//                                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                                    editor.putString("image_profile", baseUrl + document_path);
//                                    editor.putString("email_id", mJson.getString("email"));
//                                    editor.apply();
//                                    editor.commit();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//                                try {
//                                    SharedPreferences sharedPreferences = context.getSharedPreferences("ProfileData", Context.MODE_PRIVATE);
//                                    email.setText(sharedPreferences.getString("email_id", ""));
//                                    firstName.setText(sharedPreferences.getString("first_name", ""));
//                                    lastName.setText(sharedPreferences.getString("last_name", ""));
//                                    Picasso.with(context)
//                                            .load(sharedPreferences.getString("image_profile", ""))
//                                            .into(profileImage);
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }

                                progressBar.setVisibility(View.GONE);
//                                Log.e("TAG", "uploadFile: code 1 " + mData);
                                Toast.makeText(context, mData1, Toast.LENGTH_SHORT).show();

                            }
                        });

                    } else {
                        EditProfile.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
//                                Log.e("TAG", " 2285: " + new Date().toLocaleString());//1538546658896.jpg/
                                Toast.makeText(context, mData1 + " " + mData, Toast.LENGTH_SHORT).show();

                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (serverResponseCode == 200) {
                    EditProfile.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            Log.e("TAG", " 2303: " + new Date().toLocaleString());//1538546658896.jpg/
                        }
                    });
                }

                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                EditProfile.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("TAG", " 2318: " + new Date().toLocaleString());//1538546658896.jpg/
                        progressBar.setVisibility(View.GONE);
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                EditProfile.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
//                        Log.e("TAG", " 2335: " + new Date().toLocaleString());//1538546658896.jpg/
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            return serverResponseCode;
        }

    }

    public void saveEditProfileData() {
        try {
            progressBar.setVisibility(View.VISIBLE);
            String url = MainActivity.mainUrl + "authorization/updateProfile";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId", DashboardActivity.student_id);
            params.put("email", email.getText().toString());
            params.put("fname", firstName.getText().toString());
            params.put("mname", middleName.getText().toString());
            params.put("lname", lastName.getText().toString());
            params.put("mobile", mobile_number.getText().toString());

            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();

                volleyCall.sendRequest(context, url, mActivity, null, "editProfileDetails", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(mActivity, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void saveResponse(JSONObject jsonData) {
        try {
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (jsonData.getInt("status") == 1) {

            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } else {
                setProfileApiCall();
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setProfileApiCall() {
        String url = MainActivity.mainUrl + "authorization/profile";
        Map<String, String> params = new HashMap<String, String>();
        params.put("studentId", DashboardActivity.student_id);
        if (!Globle.isNetworkAvailable(context)) {
            Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
        } else {
            VolleyCall volleyCall = new VolleyCall();//http://192.168.0.110/eduvanzapi/dashboard/getStudentDashbBoardStatus
            volleyCall.sendRequest(context, url, mActivity, null, "setProfileDetails", params, MainActivity.auth_token);
        }
    }

    public void setProfileDetails(JSONObject jsonObject) {
        String message = jsonObject.optString("message");
        try {
            if (jsonObject.getInt("status") == 1) {
                JSONObject jsonObj = jsonObject.getJSONObject("result");
                if (jsonObj.getString("first_name") != null) {
                    firstName.setText(jsonObj.getString("first_name"));
                }
                if (jsonObj.getString("last_name") != null) {
                    lastName.setText(jsonObj.getString("last_name"));
                }
                if (jsonObj.getString("email") != null) {
                    email.setText(jsonObj.getString("email"));
                }
                if (jsonObj.getString("mobile_no") != null) {
                    mobile_number.setText(jsonObj.getString("mobile_no"));
                }
                if (jsonObj.getString("img_profile") != null) {
                    Picasso.with(context)
                            .load(jsonObj.getString("img_profile").toString())
                            .into(profileImage);
                }

                try {

                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("first_name", jsonObj.getString("name"));
                    editor.putString("first_name", jsonObj.getString("first_name"));
                    editor.putString("last_name", jsonObj.getString("last_name"));
                    editor.putString("user_img", jsonObj.getString("img_profile"));
                    editor.putString("email", jsonObj.getString("email"));
                    editor.apply();
                    editor.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                    userFirst = sharedPreferences.getString("first_name", "");
                    userEmail = sharedPreferences.getString("email", "");
                    userMobileNo = sharedPreferences.getString("mobile_no", "");
                    userPic = sharedPreferences.getString("user_img", "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    textViewName.setText(userFirst);
                    textView_mobileNo.setText(userMobileNo);
                    textViewEmail.setText(userEmail);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
//            {"document_path":"student\/3354\/user_profile_1556790017.jpg","status":0,"message":"Profile Picture Updated Successfully"}
//            http:\/\/159.89.204.41\/eduvanzbeta\/uploads\/student\/3354\/user_profile_1556789668.jpg;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
