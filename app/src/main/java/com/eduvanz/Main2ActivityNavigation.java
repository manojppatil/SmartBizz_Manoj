package com.eduvanz;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
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
import com.eduvanz.pqformfragments.PqFormFragment1;
import com.eduvanz.pqformfragments.SuccessAfterPQForm;
import com.eduvanz.uploaddocs.UploadActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
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

import static com.eduvanz.MainApplication.TAG;

public class Main2ActivityNavigation extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView textViewName, textViewEmail;
    Typeface typefaceFont, typefaceFontBold;
    ImageView imageView;
    SharedPref sharedPref;
    String checkForEligibility="";
    StringBuffer sb;
    long total = 0;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MainApplication.mainapp_courseID ="";
        MainApplication.mainapp_instituteID="";
        MainApplication.mainapp_locationID="";
        MainApplication.mainapp_loanamount="";
        MainApplication.mainapp_fammilyincome="";
        MainApplication.mainapp_doccheck="";
        MainApplication.mainapp_professioncheck="";
        MainApplication.mainapp_currentCity="";

        try {
            Bundle bundle = getIntent().getExtras();
            checkForEligibility = bundle.getString("checkfor_eligibility");
            Log.e(MainApplication.TAG, "checkForEligibility: "+checkForEligibility );
        }catch (Exception e){
            checkForEligibility = "0";
        }

        if(!checkForEligibility.equalsIgnoreCase("1")) {
            long time= System.currentTimeMillis();
            Log.e(TAG, "CURRENT TIME: " + time);
            long databasetime;
            DBHandler dbHandler = new DBHandler(getApplicationContext());

            if(dbHandler.getDate("1").equalsIgnoreCase("")){
                databasetime=0;
            }else {
                databasetime = Long.parseLong(dbHandler.getDate("1"));
            }
            Log.e(TAG, "DATABASE TIME: " + databasetime);
            if(time-databasetime>86400000){
                long a = time-databasetime;
                Log.e(TAG, "TIME: " + a);
                MainApplication.readSms(getApplicationContext());
                mReadJsonData();
            }
        }


        toolbar.setTitleTextColor(Color.WHITE);

        if(!checkForEligibility.equalsIgnoreCase("1")) {
            getSupportActionBar().setTitle("DashBoard");
        }else {
            getSupportActionBar().setTitle("Pq Form");
        }

        typefaceFont = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_font.ttf");
        typefaceFontBold = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_bold.ttf");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        SharedPreferences sharedPreferences = getSharedPreferences("UserData", getApplicationContext().MODE_PRIVATE);
        String firstnameshared = sharedPreferences.getString("first_name","null");
        String lastnameshared = sharedPreferences.getString("last_name","null");
        String emailshared = sharedPreferences.getString("user_email","null");
        String userpic = sharedPreferences.getString("user_image","null");

        Log.e(MainApplication.TAG, "firstnameshared: "+firstnameshared + "lastnameshared: "+lastnameshared + "emailshared: "+emailshared + "logged_id: "+sharedPreferences.getString("logged_id","null"));

        if(!userpic.equalsIgnoreCase("null") || !userpic.equalsIgnoreCase("")) {
            Picasso.with(getApplicationContext()).load(userpic).into(imageView);
        }

        textViewName.setText(firstnameshared);
        textViewEmail.setText(emailshared);

        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);

            //for applying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    applyFontToMenuItem(subMenuItem);
                }
            }

            //the method we have create in activity
            applyFontToMenuItem(mi);
        }

        if(!checkForEligibility.equalsIgnoreCase("1")) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            getSupportFragmentManager().beginTransaction().add(R.id.framelayout_pqform, new DashBoardFragment()).commit();
        }else {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            getSupportFragmentManager().beginTransaction().add(R.id.framelayout_pqform, new PqFormFragment1()).commit();
        }
    }

    private void applyFontToMenuItem(MenuItem mi) {
//        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf");
//        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");
        Typeface font = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/droidsans_font.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
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
            Intent intent = new Intent(Main2ActivityNavigation.this, UploadActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_coborrowerdetail) {
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
        }
        else if (id == R.id.nav_faq) {
            Intent intent = new Intent(Main2ActivityNavigation.this, FAQ.class);
            startActivity(intent);

        } else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(Main2ActivityNavigation.this, WebViewAboutUs.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_termsncondition) {
            Intent intent = new Intent(Main2ActivityNavigation.this, WebViewTermsNCondition.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_account_settings) {
            Intent intent = new Intent(Main2ActivityNavigation.this, MyProfile.class);
            startActivity(intent);
        }else if (id == R.id.nav_logout) {

//                String checkForImageSlider = "1";
            sharedPref = new SharedPref();
            sharedPref.clearSharedPreference(Main2ActivityNavigation.this);
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
                finish();
        }else if (id == R.id.nav_friendlyscore) {
            Intent clientIntent = null;
            clientIntent = new Intent(this,ClientActivity.class);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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


    /** upload file to server **/
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
