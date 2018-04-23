package com.eduvanz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.newUI.MainApplication;
import com.eduvanz.uploaddocs.PathFile;
import com.eduvanz.uploaddocs.Utility;
import com.eduvanz.volley.VolleyCall;
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

public class MyProfile extends AppCompatActivity {

    public String userChoosenTask;
    public int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    TextView textViewEditFontAwesome;
    Typeface typefaceFontAwesome;
    LinearLayout linearLayoutChangeImage;
    ImageView imageView;
    ProgressDialog mDialog;
    String uploadFilePath = "";
    String urlup = MainApplication.mainUrl + "dashboard/changeImage";
    String userID = "";
    StringBuffer sb;
    String user_image = "", mobileno = "";
    Context context;
    AppCompatActivity mActivity;
    Button buttonConfirm;
    EditText editTextPhoneNo, editTextPassword, editTextConfrimPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        context = this;
        mActivity = this;
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);// hide the keyboard everytime the activty starts.

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        userID = sharedPreferences.getString("logged_id", "null");
        user_image = sharedPreferences.getString("user_image", "null");
        mobileno = sharedPreferences.getString("mobile_no", "null");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setTitle("Account Settings");

        editTextPhoneNo = (EditText) findViewById(R.id.editText_phoneno_setting);
        editTextPhoneNo.setText(mobileno);
        editTextPassword = (EditText) findViewById(R.id.editText_password_setting);
        editTextConfrimPassword = (EditText) findViewById(R.id.editText_confrimpassword_setting);

        buttonConfirm = (Button) findViewById(R.id.button_confirm_changesetting);
        imageView = (ImageView) findViewById(R.id.profilepicofUSER);
        linearLayoutChangeImage = (LinearLayout) findViewById(R.id.linearlayout_changeprofilepic);
        linearLayoutChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        typefaceFontAwesome = Typeface.createFromAsset(getApplicationContext().getAssets(), "fontawesome-webfont.ttf");
        textViewEditFontAwesome = (TextView) findViewById(R.id.myprofileEdit);
        textViewEditFontAwesome.setTypeface(typefaceFontAwesome);

        Picasso.with(this).load(user_image).into(imageView);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editTextPassword.getText().toString().equalsIgnoreCase(editTextConfrimPassword.getText().toString())) {
                    /** API CALL */
                    try {
                        String url = MainApplication.mainUrl + "dashboard/changePasswordAndNotificationsSettings";
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("mobile", editTextPhoneNo.getText().toString());
                        params.put("currentPassword", editTextPassword.getText().toString());
                        params.put("studentId", userID);
                        VolleyCall volleyCall = new VolleyCall();
                        volleyCall.sendRequest(context, url, mActivity, null, "changeSettings", params);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /** END OF API CALL */
                } else {
                    editTextPassword.setError("Password Does not match");
                }

            }
        });

    }

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
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(MyProfile.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(MyProfile.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
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

        imageView.setImageBitmap(thumbnail);
        if (uploadFilePath != null) {
            // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
            mDialog = new ProgressDialog(MyProfile.this);
            mDialog.setMessage("UPLOADING FILE");
            mDialog.show();
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
            Toast.makeText(MyProfile.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                Uri selectedFileUri = data.getData();
                uploadFilePath = PathFile.getPath(this, selectedFileUri);
                Log.e("TAG", "onSelectFromGalleryResult: " + uploadFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        imageView.setImageBitmap(bm);

        if (uploadFilePath != null) {
            // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
            mDialog = new ProgressDialog(MyProfile.this);
            mDialog.setMessage("UPLOADING FILE");
            mDialog.show();
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
            Toast.makeText(MyProfile.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * android upload file to server
     **/
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
                                mDialog.dismiss();
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = mJson.getJSONObject("result");
                                    String image = jsonObject.getString("img_profile");

                                    SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("user_image", image);
                                    editor.commit();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                Log.e("TAG", "uploadFile: code 1 " + mData);
                                Toast.makeText(MyProfile.this, mData1, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(UploadActivityBorrower.this,"File Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

//                        finish();

                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                Log.e("TAG", "uploadFile: code 2 " + mData);
                                Toast.makeText(MyProfile.this, mData1, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MyProfile.this, "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(MyProfile.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MyProfile.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
//            dialog.dismiss();
            return serverResponseCode;
        }

    }/** END OF UPLOAD FILE */


    /**
     * RESPONSE OF API CALL
     **/

    public void changeSettings(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "getDocuments" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");
                String phoneno = jsonObject.getString("mobile");

                SharedPreferences sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("mobile_no", phoneno);
                editor.apply();
                editor.commit();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
