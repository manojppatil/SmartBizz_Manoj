package com.eduvanz;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanz.faqexpandablelistview.FAQ;
import com.eduvanz.fqform.borrowerdetail.FqFormBorrower;
import com.eduvanz.fqform.coborrowerdetail.FqFormCoborrower;
import com.eduvanz.friendlyscore.StartActivityFS;
import com.eduvanz.pqformfragments.PqFormFragment1;
import com.eduvanz.pqformfragments.SuccessAfterPQForm;
import com.eduvanz.uploaddocs.UploadActivityBorrower;
import com.eduvanz.uploaddocs.UploadActivityCoBorrower;
import com.squareup.picasso.Picasso;

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
import java.util.Calendar;

import static com.eduvanz.MainApplication.TAG;

/** GET ALL CONTACTS https://stackoverflow.com/questions/12562151/android-get-all-contacts **/
public class Main2ActivityNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public int GET_MY_PERMISSION = 1;
    TextView textViewName, textViewEmail;
    Typeface typefaceFont, typefaceFontBold;
    ImageView imageView;
    SharedPref sharedPref;
    String checkForEligibility = "";
    StringBuffer sb;
    long total = 0;
    ProgressDialog mDialog;
    String userpic = "";
    DrawerLayout drawer;
    int permission, permission2, permission3;
    ProgressDialog mDialogBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        Intent aaa = new Intent(this, MainActivity.class);
//        startActivity(aaa);

//        Intent intentp = new Intent(Main2ActivityNavigation.this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(Main2ActivityNavigation.this, 0, intentp, 0);
//        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR, 3);
//        calendar.set(Calendar.MINUTE, 8);
//        calendar.set(Calendar.AM_PM, Calendar.PM);
//
////                        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), interval, pendingIntent);
//        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                AlarmManager.INTERVAL_DAY, pendingIntent);
//        Log.e(MainApplication.TAG, "onTimeSet: milisec : "+  calendar.getTimeInMillis());

        /** getting data from shared preference **/
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
        String firstnameshared = sharedPreferences.getString("first_name", "null");
        String lastnameshared = sharedPreferences.getString("last_name", "null");
        String emailshared = sharedPreferences.getString("user_email", "null");
        String userID = sharedPreferences.getString("logged_id", "null");
        String userNo = sharedPreferences.getString("mobile_no", "null");
        userpic = sharedPreferences.getString("user_image", "null");

        Log.e(MainApplication.TAG, "firstnameshared: " + firstnameshared + "lastnameshared: " + lastnameshared + "emailshared: " + emailshared + "logged_id: " + sharedPreferences.getString("logged_id", "null"));


        startActivity(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS));

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
                /** SERVICE CALL **/
                Log.e(TAG, "onCreate: CALLed Once");
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
            }
        }
        else
        {
            /** SERVICE CALL **/
            Log.e(TAG, "onCreate: CALLed Once");
            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        }

        MainApplication.mainapp_courseID = "";
        MainApplication.mainapp_instituteID = "";
        MainApplication.mainapp_locationID = "";
        MainApplication.mainapp_loanamount = "";
        MainApplication.mainapp_fammilyincome = "";
        MainApplication.mainapp_doccheck = "";
        MainApplication.mainapp_professioncheck = "";
        MainApplication.mainapp_currentCity = "";

        try {
            Bundle bundle = getIntent().getExtras();
            checkForEligibility = bundle.getString("checkfor_eligibility");
            Log.e(MainApplication.TAG, "checkForEligibility: " + checkForEligibility);
        } catch (Exception e) {
            checkForEligibility = "0";
        }

//        if (!checkForEligibility.equalsIgnoreCase("1")) {
//            long time = System.currentTimeMillis();
//            Log.e(TAG, "CURRENT TIME: " + time);
//            long databasetime;
//            DBHandler dbHandler = new DBHandler(getApplicationContext());
//
//            if (dbHandler.getDate("1").equalsIgnoreCase("")) {
//                databasetime = 0;
//            } else {
//                databasetime = Long.parseLong(dbHandler.getDate("1"));
//            }
//            Log.e(TAG, "DATABASE TIME: " + databasetime);
//            if (time - databasetime > 86400000) {
//                long a = time - databasetime;
//                Log.e(TAG, "TIME: " + a);


//                if (Build.VERSION.SDK_INT >= 23)
//                {
//                    permission = ContextCompat.checkSelfPermission(getApplicationContext(),
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
//                    permission2 = ContextCompat.checkSelfPermission(getApplicationContext(),
//                            Manifest.permission.WRITE_CONTACTS);
//                    permission3 = ContextCompat.checkSelfPermission(getApplicationContext(),
//                            Manifest.permission.READ_CONTACTS);
//                    Log.e(TAG, "permission: "+permission );
//                    if (permission != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED || permission3 != PackageManager.PERMISSION_GRANTED ) {
//                        Log.i("TAG", "Permission to record denied");
//                        makeRequest();
//                        if(permission == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED && permission3== PackageManager.PERMISSION_GRANTED) {
//                            MainApplication.readSms(getApplicationContext(), userID, userNo);
//                            Log.e(TAG, "onCreate: " + "AYYYYYAAAA");
//                            //                                MainApplication.contactsRead(getApplicationContext());
//                            mReadJsonData();
//                        }
//                    } else if(permission == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED && permission3== PackageManager.PERMISSION_GRANTED){
//
//                        MainApplication.readSms(getApplicationContext(), userNo, userID);
//                        Log.e(TAG, "onCreate: "+"AYYYYYAAAA" );
//                        mDialogBar = new ProgressDialog(Main2ActivityNavigation.this, R.style.AppTheme_Dark_Dialog);
//                        mDialogBar.setMessage("Authenticating... ");
//                        mDialogBar.setCancelable(false);
//                        mDialogBar.show();
////                            MainApplication.contactsRead(getApplicationContext());
//                        mDialogBar.hide();
//                        mReadJsonData();
//                    }
//                }else {
//                    MainApplication.readSms(getApplicationContext(), userNo, userID);
//                    Log.e(TAG, "onCreate: "+"AYYYYYAAAA" );
//                    //                        MainApplication.contactsRead(getApplicationContext());
//                    mReadJsonData();
//                }
//
//            }
//        }


        toolbar.setTitleTextColor(Color.WHITE);

        try {
            if (!checkForEligibility.equalsIgnoreCase("1")) {
                getSupportActionBar().setTitle("DashBoard");
            } else {
                getSupportActionBar().setTitle("Pq Form");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        typefaceFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_bold.ttf");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        textViewName = (TextView) header.findViewById(R.id.textView_username);
        textViewEmail = (TextView) header.findViewById(R.id.textView_useremail);
        imageView = (ImageView) header.findViewById(R.id.imageView_userpic);
        textViewName.setTypeface(typefaceFontBold);
        textViewEmail.setTypeface(typefaceFontBold);


        if (!userpic.equalsIgnoreCase("null") || !userpic.equalsIgnoreCase("")) {
            Picasso.with(getApplicationContext()).load(userpic).into(imageView);
        }

        textViewName.setText(firstnameshared);
        textViewEmail.setText(emailshared);

        Menu m = navigationView.getMenu();
        for (int i = 0; i < m.size(); i++) {
            MenuItem mi = m.getItem(i);

            //for applying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu != null && subMenu.size() > 0) {
                for (int j = 0; j < subMenu.size(); j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        try {
            if (!checkForEligibility.equalsIgnoreCase("1")) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                getSupportFragmentManager().beginTransaction().add(R.id.framelayout_pqform, new DashBoardFragment()).commit();
            } else {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                getSupportFragmentManager().beginTransaction().add(R.id.framelayout_pqform, new PqFormFragment1()).commit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
        Log.e(MainApplication.TAG, "ON RESUME");
        SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
        userpic = sharedPreferences.getString("user_image", "null");
        Log.e(MainApplication.TAG, "ON RESUME" + userpic);
        Picasso.with(Main2ActivityNavigation.this).load(userpic).into(imageView);

        if (!checkForEligibility.equalsIgnoreCase("1")) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getSupportFragmentManager().beginTransaction().add(R.id.framelayout_pqform, new DashBoardFragment()).commit();
        } else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportFragmentManager().beginTransaction().add(R.id.framelayout_pqform, new PqFormFragment1()).commit();
        }

    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(Main2ActivityNavigation.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS},
                GET_MY_PERMISSION);
    }

    private void applyFontToMenuItem(MenuItem mi) {
//        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
//        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_font.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2_activity_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), MyProfile.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_upload_docs_view) {
            Intent intent = new Intent(Main2ActivityNavigation.this, UploadActivityBorrower.class);
            startActivity(intent);
        } else if (id == R.id.nav_upload_docs_coborrower) {
            // Handle the camera action
            Intent intent = new Intent(Main2ActivityNavigation.this, UploadActivityCoBorrower.class);
            startActivity(intent);
        } else if (id == R.id.nav_coborrowerdetail) {
            Intent intent = new Intent(Main2ActivityNavigation.this, FqFormCoborrower.class);
            startActivity(intent);
        }
//        else if (id == R.id.nav_upload_docs) {
//            Intent intent = new Intent(Main2ActivityNavigation.this, UploadDoc.class);
//            startActivity(intent);
//        }
        else if (id == R.id.nav_borrowerdetails) {
            // Handle the camera action
            Intent intent = new Intent(Main2ActivityNavigation.this, FqFormBorrower.class);
            startActivity(intent);
        } else if (id == R.id.nav_faq) {
            Intent intent = new Intent(Main2ActivityNavigation.this, FAQ.class);
            startActivity(intent);

        } else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(Main2ActivityNavigation.this, WebViewAboutUs.class);
            startActivity(intent);
        } else if (id == R.id.nav_termsncondition) {
            Intent intent = new Intent(Main2ActivityNavigation.this, WebViewTermsNCondition.class);
            startActivity(intent);
        } else if (id == R.id.nav_account_settings) {
            Intent intent = new Intent(Main2ActivityNavigation.this, MyProfile.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {

//                String checkForImageSlider = "1";
            sharedPref = new SharedPref();
            sharedPref.clearSharedPreference(Main2ActivityNavigation.this);
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_friendlyscore) {
            Intent intent = new Intent(Main2ActivityNavigation.this, StartActivityFS.class);
            startActivity(intent);
//            Intent clientIntent = null;
//            clientIntent = new Intent(this,ClientActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void mReadJsonData() {
        final File dir = new File(Environment.getExternalStorageDirectory() + "/");
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

    public void mReadJsonDataContacts() {
        final File dir = new File(Environment.getExternalStorageDirectory() + "/");
        if (dir.exists() == false) {
            dir.mkdirs();
        }
        final File f = new File(dir, "contacts.json");
        new Thread(new Runnable() {
            @Override
            public void run() {
                uploadFile(f.getAbsolutePath());
            }
        }).start();
    }


    /**
     * upload file to server
     **/
    public int uploadFile(final String selectedFilePath) {
        String urlup = "http://139.59.32.234/sms/Api/send_message";
        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        final int count, fileLength;

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
        } else {

            try {

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        showProressBar("Please wait verifying user credentials");
//                    }
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
                        + selectedFilePath + "\"" + lineEnd);
                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];
                fileLength = bufferSize;
                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                Log.e(TAG, "uploadFile: TOTAL bytes to read " + bytesRead + "total" + bufferSize);
                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                total = 0;

                while (bytesRead > 0) {
                    total += bytesRead;
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer, 0, bufferSize);
                    Log.e("ReadSms", " here: \n\n" + buffer + "\n" + bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    Log.e(TAG, "uploadFile: " + bytesRead + total);
                    Log.e(TAG, "uploadFile: percentage " + ((int) Math.round(total * 100 / fileLength)));
                    // Publish the progress
                    final int finalBytesRead = bytesRead;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.e(TAG, "uploadFile: percentage " + ((int) Math.round(total * 100 / fileLength)));
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
//                            Toast.makeText(SuccessAfterPQForm.this, sb.toString(), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(Main2ActivityNavigation.this, "File Not Found", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(Main2ActivityNavigation.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(Main2ActivityNavigation.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
//            dialog.dismiss();
            return serverResponseCode;
        }
    }

    private void showProressBar(String s) {
        mDialog = new ProgressDialog(Main2ActivityNavigation.this);
        mDialog.setMessage(s);
//        mDialog.setIndeterminate(false);
//        mDialog.setMax(100);
//        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(true);
        mDialog.show();
//        return pDialog;
    }
}
