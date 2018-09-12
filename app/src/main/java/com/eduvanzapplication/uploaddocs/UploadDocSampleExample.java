//package com.eduvanz;
//
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.content.Intent;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Color;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.provider.MediaStore;
//import android.support.annotation.Nullable;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//
//import com.butler.butlerpartner.MainApplication;
//import com.butler.butlerpartner.R;
//import com.butler.butlerpartner.SharedP;
//import com.butler.butlerpartner.utility.PathFile;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * Created by nikhil on 6/10/16.
// */
//
//public class UploadDoc extends AppCompatActivity {
//    Button mButton, pdf,uploadDoc;
//    String selectedFile,user_id;
//    int Select_Image = 10, SelectFile = 20;
//    int serverResponseCode = 0;
//    String urlup = MainApplication.MainUrl + "/profile/update_service_provider_profile";
//    String uploadFileName, uploadFilePath;
//    StringBuffer sb;
//    SharedP mShared;
//    ImageView uploadDone;
//    Toolbar mToolbar;
//    TextView selectionText;
//    ProgressDialog mDialog;
//    Context mContext;
//    TextView toolbarT;
//    ImageView imageOfUpload;
//    AppCompatActivity mActivity;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.upload_document);
//        mButton = (Button) findViewById(R.id.btnUpload1);
//        mShared = new SharedP();
//        mActivity= this;
//        user_id=mShared.getPref(this).getString("user_id",null);
//        Log.e(MainApplication.TAG, "onCreate: "+user_id );
//        uploadDone= (ImageView) findViewById(R.id.uploadDone);
//        selectionText= (TextView) findViewById(R.id.successfullyUploadMsg);
//        mToolbar= (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Upload Doc");
//        mToolbar.setTitleTextColor(Color.WHITE);
//        mContext=this;
//        toolbarT= (TextView) findViewById(R.id.nameToolbar);
//        toolbarT.setVisibility(View.GONE);
//        imageOfUpload= (ImageView) findViewById(R.id.uploadStatus);
//
//        try {
//            Bundle mBundle= getIntent().getExtras();
//            boolean isUploded=mBundle.getBoolean("isUploaded",false);
////            userId= mShared.getPref(mActivity).getString("user_id", null);
//            if(isUploded){
//                imageOfUpload.setImageResource(R.drawable.filled);
//            }else {
//                imageOfUpload.setImageResource(R.drawable.notfilled);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        mButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//
////        intent.setType("image/* video/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);//
//                startActivityForResult(Intent.createChooser(intent, "Select File"), Select_Image);
//            }
//        });
//
//        pdf = (Button) findViewById(R.id.btnUpload);
//        pdf.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                intent.setType("application/pdf");
////                application/msword,application/pdf
////        intent.setType("image/* video/*");
//                //
//                startActivityForResult(Intent.createChooser(intent, "Select File"), SelectFile);
//            }
//        });
//        uploadDoc = (Button) findViewById(R.id.btnuploadDoc);
//        uploadDoc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                if(MainApplication.isConnectingToInternet(mContext))
//                {
//                    if (uploadFilePath != null) {
//                        // dialog = ProgressDialog.show(MainActivity.this,"","Uploading File...",true);
//                        mDialog = new ProgressDialog(UploadDoc.this);
//                        mDialog.setMessage("UPLOADING FILE");
//                        mDialog.show();
//                        new Thread(new Runnable() {
//                            @Override
//                            public void run() {
//                                //creating new thread to handle Http Operations
////                            uploadFile(uploadFilePath);
//                                Log.e(MainApplication.TAG, "File:Path absolute : new" + uploadFilePath);
//                                uploadFile(uploadFilePath);
//                            }
//                        }).start();
//                    } else {
//                        Toast.makeText(UploadDoc.this, "Please choose a File First", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == android.R.id.home){
//            finish();
//        }
//        return (super.onOptionsItemSelected(item));
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        try {
//            // When an Image is picked
//            if (resultCode == RESULT_OK
//                    && null != data) {
//                if (requestCode == Select_Image) {
//                    Uri selectedImage = data.getData();
//                    getImagePath(selectedImage);// for getting extension
//                    InputStream imageStream = getContentResolver().openInputStream(selectedImage);
//                    Bitmap yourImage = BitmapFactory.decodeStream(imageStream);
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    yourImage.compress(Bitmap.CompressFormat.JPEG, 70, stream);
//                    Uri selectedFileUri = data.getData();
//                    uploadFilePath = PathFile.getPath(this,selectedFileUri);
//                    uploadDone.setVisibility(View.VISIBLE);
//                    selectionText.setVisibility(View.VISIBLE);
//
//                } else if (requestCode == SelectFile) {
//                    Uri selectedImage = data.getData();
//                    getFilePath(selectedImage);
//                    uploadFilePath = PathFile.getPath(this,selectedImage);
//                    uploadDone.setVisibility(View.VISIBLE);
//                    selectionText.setVisibility(View.VISIBLE);
//                }
//
//            } else {
//
//                Toast.makeText(this, "You haven't picked Image",
//                        Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
//                    .show();
//        }
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void getImagePath(Uri selectedImage) {
//      //  uploadFilePath = PathFile.getPath(UploadDoc.this, selectedImage);
////        Log.e(MainApplicationGlobalVariables.TAG, "File:Path absolute : " + uploadFilePath);
//        String uriString = selectedImage.toString();
//        Log.e(MainApplication.TAG, "File:uri : " + uriString);
//        File myFile = new File(uriString);
////        uploadFilePath = myFile.getAbsolutePath();
//
//        String[] filePathColumn = {MediaStore.Images.Media.DATA, MediaStore.Images.Media.DISPLAY_NAME};
//        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//        if (cursor.moveToFirst()) {
//            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//            String filePath = cursor.getString(columnIndex);
//            Log.e(MainApplication.TAG, "filePath: " + filePath);
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//            Log.e(MainApplication.TAG, "bitmap : " + bitmap);
//            int fileNameIndex = cursor.getColumnIndex(filePathColumn[1]);
//            uploadFileName = cursor.getString(fileNameIndex);
//            Log.e(MainApplication.TAG, "fileName : " + uploadFileName);
////            IMG-20161006-WA0001.jpg
//            // Here we get the extension you want
//            String extension = uploadFileName.replaceAll("^.*\\.", "");
//            Log.e(MainApplication.TAG, "extension : " + extension);
////            if (MainApplication.extensions.contains(extension)) {
////                //uploadFile(uriString);
////                Toast.makeText(this, "Start Upload ", Toast.LENGTH_SHORT).show();
////            } else {
////                Toast.makeText(this, "Invalid Format ", Toast.LENGTH_SHORT).show();
////            }
//        }
//        cursor.close();
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public String getFilePath(Uri uri) {
//
//
//        String uriString = uri.toString();
//        Log.e(MainApplication.TAG, "File:uri : " + uriString);
//
////        File:uri : file:///storage/sdcard0/bootstrap_tutorial.pdf
//
//        File myFile = new File(uri.getPath().toString());
//        //  uploadFilePath = myFile.getAbsolutePath();
//        Log.e(MainApplication.TAG, "File:Path : " + uploadFilePath);
//
////        File:Path : /storage/sdcard0/bootstrap_tutorial.pdf
//
//        uploadFileName = myFile.getName().toString();
//        Log.e(MainApplication.TAG, "File:File NAme: " + uploadFileName);
//
////        File:File NAme: bootstrap_tutorial.pdf
////        int index=uploadFilePath.lastIndexOf('/');
////        System.out.println(uploadFilePath.substring(0,index));
//
//        String extension = uploadFileName.replaceAll("^.*\\.", "");
//        Log.e(MainApplication.TAG, "extension : " + extension);
////        if (MainApplication.extensions.contains(extension)) {
////           // uploadFile(uriString);
////            Toast.makeText(this, "Start Upload ", Toast.LENGTH_SHORT).show();
////        } else {
//////            Toast.makeText(this, "Invalid Format ", Toast.LENGTH_SHORT).show();
////        }
//        return null;
//    }
////
////    public int uploadFile(String sourceFileUri) {
////
////
////        String fileName = sourceFileUri;
////
////        HttpURLConnection conn = null;
////        DataOutputStream dos = null;
////        String lineEnd = "\r\n";
////        String twoHyphens = "--";
////        String boundary = "*****";
////        int bytesRead, bytesAvailable, bufferSize;
////        byte[] buffer;
////        int maxBufferSize = 1 * 1024 * 1024;
////        File sourceFile = new File(sourceFileUri);
////        Log.e(MainApplicationGlobalVariables.TAG, "File:File maxBufferSize: "+String.valueOf(maxBufferSize));
////        if (sourceFile.isFile()) {
////
//////            dialog.dismiss();
////
////            Log.e("uploadFile", "Source File not exist :"
////                    +uploadFilePath + "" + uploadFileName);
////
////            runOnUiThread(new Runnable() {
////                public void run() {
//////                    messageText.setText("Source File not exist :"
//////                            +uploadFilePath + "" + uploadFileName);
////                }
////            });
////
////            return 0;
////
////        }
////        else
////        {
////            try {
////
////                // open a URL connection to the Servlet
////                FileInputStream fileInputStream = new FileInputStream(sourceFile);
////
////                URL url = new URL(upLoadServerUri);
////                Log.e(MainApplicationGlobalVariables.TAG, "File:File url: "+url);
////                // Open a HTTP  connection to  the URL
////                conn = (HttpURLConnection) url.openConnection();
////                conn.setDoInput(true); // Allow Inputs
////                conn.setDoOutput(true); // Allow Outputs
////                conn.setUseCaches(false); // Don't use a Cached Copy
////                conn.setRequestMethod("POST");
////                conn.setRequestProperty("Connection", "Keep-Alive");
////                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
////                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
////                conn.setRequestProperty("uploaded_file", fileName);
////                dos = new DataOutputStream(conn.getOutputStream());
////                Log.e(MainApplicationGlobalVariables.TAG, "File:File data output stream: "+dos);
////                dos.writeBytes(twoHyphens + boundary + lineEnd);
////                dos.writeBytes("Content-Disposition: form-data; name="+fileName+";filename="
////                                + fileName + "" + lineEnd);
////                        dos.writeBytes(lineEnd);
////
////                // create a buffer of  maximum size
////                bytesAvailable = fileInputStream.available();
////
////                bufferSize = Math.min(bytesAvailable, maxBufferSize);
////                buffer = new byte[bufferSize];
////
////                // read file and write it into form...
////                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
////
////                while (bytesRead > 0) {
////                    Log.e(MainApplicationGlobalVariables.TAG, "File:File bytesRead: "+bytesRead);
////                    dos.write(buffer, 0, bufferSize);
////                    bytesAvailable = fileInputStream.available();
////                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
////                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
////
////                }
////
////                // send multipart form data necesssary after file data...
////                dos.writeBytes(lineEnd);
////                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
////
////                // Responses from the server (code and message)
////                serverResponseCode = conn.getResponseCode();
////                String serverResponseMessage = conn.getResponseMessage();
////
////                Log.i("uploadFile", "HTTP Response is : "
////                        + serverResponseMessage + ": " + serverResponseCode);
////
////                if(serverResponseCode == 200){
////
////                    runOnUiThread(new Runnable() {
////                        public void run() {
////
////                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
////                                    +" http://www.androidexample.com/media/uploads/"
////                                    +uploadFileName;
////
////                            Toast.makeText(UploadDoc.this, "File Upload Complete.",
////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    });
////                }
////
////                //close the streams //
////                fileInputStream.close();
////                dos.flush();
////                dos.close();
////
////            } catch (MalformedURLException ex) {
////
//////                dialog.dismiss();
////                ex.printStackTrace();
////
////                runOnUiThread(new Runnable() {
////                    public void run() {
//////                        messageText.setText("MalformedURLException Exception : check script url.");
////                        Toast.makeText(UploadDoc.this, "MalformedURLException",
////                                Toast.LENGTH_SHORT).show();
////                    }
////                });
////
////                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
////            } catch (Exception e) {
////
//////                dialog.dismiss();
////                e.printStackTrace();
////
////                runOnUiThread(new Runnable() {
////                    public void run() {
//////                        messageText.setText("Got Exception : see logcat ");
////                        Toast.makeText(UploadDoc.this, "Got Exception : see logcat ",
////                                Toast.LENGTH_SHORT).show();
////                    }
////                });
////                Log.e("Upload file", "Exception : "+ e.getMessage(), e);
////            }
//////            dialog.dismiss();
////            return serverResponseCode;
////
////        } // End else block
////    }
//
//    //android upload file to server
//    public int uploadFile(final String selectedFilePath) {
//
//        int serverResponseCode = 0;
//
//        HttpURLConnection connection;
//        DataOutputStream dataOutputStream;
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//
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
//                    Log.e(MainApplication.TAG, "run: " + "Source File Doesn't Exist: " + selectedFilePath);
//                }
//            });
//            return 0;
//        } else {
//            try {
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
////                connection.setRequestProperty("user_id", user_id);
//                Log.e(MainApplication.TAG, "Server property" + connection.getRequestMethod() + ":property " + connection.getRequestProperties());
//
//
//                //creating new dataoutputstream
//                dataOutputStream = new DataOutputStream(connection.getOutputStream());
//
//                //writing bytes to data outputstream
//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"trading_licence\";filename=\""
//                        + selectedFilePath + "\"" + lineEnd);
//                dataOutputStream.writeBytes(lineEnd);
//
//                //returns no. of bytes present in fileInputStream
//                bytesAvailable = fileInputStream.available();
//                //selecting the buffer size as minimum of available bytes or 1 MB
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                //setting the buffer as byte array of size of bufferSize
//                buffer = new byte[bufferSize];
//
//                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
//                while (bytesRead > 0) {
//                    //write the bytes read from inputstream
//                    dataOutputStream.write(buffer, 0, bufferSize);
//                    Log.e(MainApplication.TAG," here: \n\n" + buffer +"\n"+bufferSize);
//                    bytesAvailable = fileInputStream.available();
//                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//                }
//
//                dataOutputStream.writeBytes(lineEnd);
//
//                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
////                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
////                        + selectedFilePath + "\"" + lineEnd);
//                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"service_provider_id\";service_provider_id="+user_id+"" + lineEnd);
//                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(user_id);
//                dataOutputStream.writeBytes(lineEnd);
//
//                // sending type
////                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
////                taOutputStream.writeBytes("Content-Disposition: form-data; name=\"document\";filename=\""
////                        + selectedFilePath + "\"" + lineEnd);
//                ////new
////                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"type\";type="+"1"+"" + lineEnd);
////                dataOutputStream.writeBytes(lineEnd);
////                dataOutputStream.writeBytes("1");
////                dataOutputStream.writeBytes(lineEnd);
//                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//                ////new
//
//                //dataOutputStream.writeBytes(URLEncoder.encode("user_id", "UTF-8")
//                //        + "=" + URLEncoder.encode("1", "UTF-8"));
//
//                serverResponseCode = connection.getResponseCode();
//                Log.e(MainApplication.TAG," here:server response serverResponseCode\n\n" + serverResponseCode );
//                String serverResponseMessage = connection.getResponseMessage();
//                Log.e(MainApplication.TAG," here: server message serverResponseMessage \n\n" + serverResponseMessage.toString() +"\n"+bufferSize);
//                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
//                String output="";
//                sb= new StringBuffer();
//
//                    while ((output = br.readLine()) != null) {
//                        sb.append(output);
//                        Log.e(MainApplication.TAG, "uploadFile: " + br);
//                        Log.e(MainApplication.TAG, "Server Response is: " + serverResponseMessage + ": " + serverResponseCode);
//                    }
//                Log.e(MainApplication.TAG, "uploadFile: "+sb.toString() );
////                [{"code":1,"file_name":"284f0b39af461ad8ae3ee17ac20ee5f4.pdf","message":"Document uploaded successfully"}]
//
//                try {
//                    JSONArray mJson= new JSONArray(sb.toString());
//                    final JSONObject mData= mJson.getJSONObject(0);
//                    final int code=mData.optInt("code");
//                    Log.e(MainApplication.TAG, "uploadFile: code "+code);
//                    if(code == 1)
//                    {
//                        MainApplication.newDocUploaded=true;
//                        runOnUiThread(new Runnable()
//                        {
//                            @Override
//                            public void run() {
//                                try {
//                                    mDialog.dismiss();
//                                    Log.e(MainApplication.TAG, "uploadFile: code 1 "+code);
//                                    Toast.makeText(UploadDoc.this, mData.getString("message").toString(), Toast.LENGTH_SHORT).show();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//
//                        finish();
//
//                    }else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    mDialog.dismiss();
//                                    Log.e(MainApplication.TAG, "uploadFile: code 2 "+code);
//                                    Toast.makeText(UploadDoc.this, mData.getString("message").toString(), Toast.LENGTH_SHORT).show();
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                        finish();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                //response code of 200 indicates the server status OK
//                if (serverResponseCode == 200) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Log.e(MainApplication.TAG," here: \n\n" + fileName);
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
//                        Toast.makeText(UploadDoc.this, "File Not Found", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//                Toast.makeText(UploadDoc.this, "URL error!", Toast.LENGTH_SHORT).show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                Toast.makeText(UploadDoc.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
//            }
////            dialog.dismiss();
//            return serverResponseCode;
//        }
//
//    }
//
//
//
//}
