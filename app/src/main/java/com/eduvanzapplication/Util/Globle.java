package com.eduvanzapplication.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Paytm;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.MainApplication;
import com.eduvanzapplication.newUI.VolleyCallEligibility;
import com.eduvanzapplication.newUI.VolleyCallNew;
import com.eduvanzapplication.uploaddocs.PathFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;
import static com.eduvanzapplication.database.DBAdapter.ExecuteSql;
import static com.eduvanzapplication.database.DBAdapter.getLocalData;

public class Globle {
    public static void setInstance(Globle instance) {
        Globle.instance = instance;
    }

    private static Globle instance;

    public Paytm paytm;

    public static final String status = "1"; //For Success
    public static final String payment_platform = "2"; //(1-web, 2-app)
    public static final String payment_partner = "1"; //(1-paytm, 2-atom)

    //payment_platform = 2 (1-web, 2-app)
    //payment_partner = 1 (1-paytm, 2-atom)

    public static synchronized Globle getInstance() {
        if (instance == null) {
            instance = new Globle();
        }
        return instance;
    }

    public static String formatDate(long milliseconds) /* This is your topStory.getTime()*1000 */ {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        TimeZone tz = TimeZone.getDefault();
        sdf.setTimeZone(tz);
        return sdf.format(calendar.getTime());
    }

    public static String panPattern = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPattern1 = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean validateAadharNumber(String aadharNumber){
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if(isValidAadhar){
            isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;
    }

    public static void ErrorLog(Context context, String className, String name, String errorMsg, String errorMsgDetails, String errorLine) {
        try {
            String userID = "", versionName = "", osVersion = "", device = "", ipaddress = "";
            String currentDateTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            ipaddress = Utils.getIPAddress(true);

            try {
                SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
                userID = sharedPreferences.getString("logged_id", "null");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                PackageManager manager = context.getPackageManager();
                PackageInfo info = manager.getPackageInfo(
                        context.getPackageName(), 0);
                versionName = info.versionName;
                osVersion = String.valueOf(Build.VERSION.SDK_INT);
                device = String.valueOf(Build.MODEL);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            if (!Globle.isNetworkAvailable(context)) {

                String LocalsSql = "INSERT INTO ErrorLog (errorLogID, appName, appVersion, userID, errorDate, moduleName, " +
                        "methodName, errorMessage, errorMessageDtls, OSVersion, IPAddress, deviceName,lineNumber, ISSaved, ISUploaded) VALUES" +
                        " ('" + UUID.randomUUID().toString() + "'," +
                        " '" + "Eduvanz Student App" + "'," +
                        " '" + versionName + "'," +
                        " '" + userID + "'," +
                        " '" + currentDateTimeString + "'," +
                        " '" + className + "'," +
                        " '" + name + "'," +
                        " '" + errorMsg + "'," +
                        " '" + errorMsgDetails + "'," +
                        " '" + osVersion + "'," +
                        " '" + ipaddress + "'," +
                        " '" + device + "'," +
                        " '" + errorLine + "'," +
                        " '" + true + "'," +
                        "'" + "false" + "')";

                ExecuteSql(LocalsSql);

            } else {

//                String url = MainApplication.mainUrl + "applog/apiErrorLog";
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("errorLogID", UUID.randomUUID().toString());
//                params.put("appName", "Eduvanz Student App");
//                params.put("appVersion", versionName);
//                params.put("userID", userID);
//                params.put("errorDate", currentDateTimeString);
//                params.put("moduleName", className);
//                params.put("methodName", name);
//                params.put("errorMessage", errorMsg);
//                params.put("errorMessageDtls", errorMsgDetails);
//                params.put("OSVersion", osVersion);
//                params.put("IPAddress", ipaddress);
//                params.put("deviceName", device);
//                params.put("lineNumber", errorLine);
//
//                VolleyCallEligibility volleyCall = new VolleyCallEligibility();
//                volleyCall.sendRequest(context, url, null, mFragment, "checkEligiblity", params);

                String LocalsSql = "INSERT INTO ErrorLog (errorLogID, appName, appVersion, userID, errorDate, moduleName, " +
                        "methodName, errorMessage, errorMessageDtls, OSVersion, IPAddress, deviceName,lineNumber, ISSaved, ISUploaded) VALUES" +
                        " ('" + UUID.randomUUID().toString() + "'," +
                        " '" + "Eduvanz Student App" + "'," +
                        " '" + versionName + "'," +
                        " '" + userID + "'," +
                        " '" + currentDateTimeString + "'," +
                        " '" + className + "'," +
                        " '" + name + "'," +
                        " '" + errorMsg + "'," +
                        " '" + errorMsgDetails + "'," +
                        " '" + osVersion + "'," +
                        " '" + ipaddress + "'," +
                        " '" + device + "'," +
                        " '" + errorLine + "'," +
                        " '" + true + "'," +
                        "'" + "false" + "')";

                ExecuteSql(LocalsSql);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;

        StringBuilder phrase = new StringBuilder();
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase.append(Character.toUpperCase(c));
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase.append(c);
        }

        return phrase.toString();
    }

    public static void appendLog(String text) {
        File logFile = new File("sdcard/" +
                "EduPayTest.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String loadJSONFromAsset(Context context, String filepath) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("saveContacts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected() && activeNetworkInfo.isAvailable();
    }

    public static String sSqlStateInsert = "INSERT INTO statenew (state_id, country_id, state_name, is_deleted) VALUES" +
            "(1, 1, 'Delhi', 0)," +
            "(2, 1, 'Maharashtra', 0)," +
            "(3, 1, 'Karnataka', 0)," +
            "(4, 1, 'Tamil Nadu', 0)," +
            "(5, 1, 'Telangana', 0)," +
            "(6, 1, 'Haryana', 0)," +
            "(7, 1, 'Rajasthan', 0)," +
            "(8, 1, 'Punjab', 0)," +
            "(9, 1, 'West Bengal', 0)," +
            "(10, 1, 'Odisha', 0)," +
            "(11, 1, 'Uttar Pradesh', 0)," +
            "(12, 1, 'Bihar', 0)," +
            "(13, 1, 'Assam', 0)," +
            "(14, 1, 'Andhra Pradesh', 0)," +
            "(15, 1, 'Chhattisgarh', 0)," +
            "(16, 1, 'Gujarat', 0)," +
            "(17, 1, 'Himachal Pradesh', 0)," +
            "(18, 1, 'Jammu and Kashmir', 0)," +
            "(19, 1, 'Jharkhand', 0)," +
            "(20, 1, 'Kerala', 0)," +
            "(21, 1, 'Madhya Pradesh', 0)," +
            "(22, 1, 'Meghalaya', 0)," +
            "(23, 1, 'Uttarakhand', 0)," +
            "(24, 1, 'Puducherry\\r\\n', 0)," +
            "(25, 1, 'Tripura', 0)," +
            "(26, 1, 'Mizoram', 0)," +
            "(27, 1, 'Manipur', 0)," +
            "(28, 1, 'Nagaland', 0)," +
            "(29, 1, 'Goa', 0)," +
            "(31, 1, 'Andaman and Nicobar Islands', 0)," +
            "(32, 1, 'Arunachal Pradesh', 0)," +
            "(33, 1, 'Dadra and Nagar Haveli', 0)," +
            "(34, 2, 'California', 1)," +
            "(35, 1, 'wayanad', 1);";

    public static String sSqlCityInsert1 = "INSERT INTO citynew (city_id, state_id, country_id, city_name, category_id, is_deleted) VALUES" +
            "(1, 1, 1, 'Delhi', 0, 0)," +
            "(2, 2, 1, 'Mumbai', 0, 0)," +
            "(3, 3, 1, 'Bangalore', 0, 0)," +
            "(4, 4, 1, 'Chennai', 0, 0)," +
            "(5, 5, 1, 'Hyderabad', 0, 0)," +
            "(6, 6, 1, 'Gurgaon', 0, 0)," +
            "(7, 2, 1, 'Pune', 0, 0)," +
            "(8, 7, 1, 'Jaipur', 0, 0)," +
            "(9, 8, 1, 'Chandigarh', 0, 0)," +
            "(10, 9, 1, 'Kolkata', 0, 0)," +
            "(11, 10, 1, 'Bhubaneshwar', 0, 0)," +
            "(12, 3, 1, 'Mangalore', 0, 0)," +
            "(13, 2, 1, 'Nagpur', 0, 0)," +
            "(14, 11, 1, 'Lucknow', 0, 0)," +
            "(15, 11, 1, 'Noida', 0, 0)," +
            "(16, 2, 1, 'Patna', 0, 0)," +
            "(17, 2, 1, 'Nasik', 0, 1)," +
            "(18, 7, 1, 'Jodhpur', 0, 0)," +
            "(19, 13, 1, 'Gauhati', 0, 0)," +
            "(20, 14, 1, 'East Godavari', 0, 0)," +
            "(21, 14, 1, 'Nellore', 0, 0)," +
            "(22, 14, 1, 'Prakasam', 0, 0)," +
            "(23, 14, 1, 'Visakhapatnam', 0, 0)," +
            "(24, 13, 1, 'Kamrup', 0, 0)," +
            "(25, 13, 1, 'Tezpur', 0, 0)," +
            "(26, 12, 1, 'East Champaran', 0, 0)," +
            "(27, 12, 1, 'Muzaffapur', 0, 0)," +
            "(28, 12, 1, 'Saran', 0, 0)," +
            "(29, 12, 1, 'Siwan', 0, 0)," +
            "(30, 15, 1, 'Dantewada', 0, 0)," +
            "(31, 15, 1, 'Korba', 0, 0)," +
            "(32, 1, 1, 'New Delhi', 0, 0)," +
            "(33, 1, 1, 'North Delhi', 0, 0)," +
            "(34, 1, 1, 'North West Delhi', 0, 0)," +
            "(35, 1, 1, 'West Delhi', 0, 0)," +
            "(36, 1, 1, 'South West Delhi', 0, 0)," +
            "(37, 1, 1, 'South Delhi', 0, 0)," +
            "(38, 1, 1, 'South East Delhi', 0, 0)," +
            "(39, 1, 1, 'Central Delhi', 0, 0)," +
            "(40, 1, 1, 'North East Delhi', 0, 0)," +
            "(41, 1, 1, 'Shahdara', 0, 0)," +
            "(42, 1, 1, 'East Delhi', 0, 0)," +
            "(43, 16, 1, 'Ahemdabad', 0, 0)," +
            "(44, 16, 1, 'Bharuch', 0, 0)," +
            "(45, 16, 1, 'Kherva', 0, 0)," +
            "(46, 16, 1, 'Surat', 0, 0)," +
            "(47, 16, 1, 'Vadodara', 0, 0)," +
            "(48, 6, 1, 'Gurugram', 0, 0)," +
            "(49, 6, 1, 'Faridabad', 0, 0)," +
            "(50, 17, 1, 'Sirmour', 0, 0)," +
            "(51, 18, 1, 'Anantnag', 0, 0)," +
            "(52, 18, 1, 'Budgam', 0, 0)," +
            "(53, 18, 1, 'Jammu', 0, 0)," +
            "(54, 18, 1, 'Kathua', 0, 0)," +
            "(55, 18, 1, 'Kupwara', 0, 0)," +
            "(56, 19, 1, 'Lohardaga', 0, 0)," +
            "(57, 19, 1, 'Ranchi', 0, 0)," +
            "(58, 19, 1, 'West Singhbhum', 0, 0)," +
            "(59, 3, 1, 'Bengaluru', 0, 0)," +
            "(60, 3, 1, 'Bengaluru Urban', 0, 0)," +
            "(61, 3, 1, 'Ramanagara', 0, 0)," +
            "(62, 20, 1, 'Thiruvananthapuram', 0, 0)," +
            "(63, 21, 1, 'Bhopal', 0, 0)," +
            "(64, 21, 1, 'Chhatarpur', 0, 0)," +
            "(65, 21, 1, 'Chhindwara', 0, 0)," +
            "(66, 21, 1, 'Guna', 0, 0)," +
            "(67, 21, 1, 'Indore', 0, 0)," +
            "(68, 21, 1, 'Sidhi', 0, 0)," +
            "(69, 2, 1, 'Raigad', 0, 0)," +
            "(70, 22, 1, 'East Khasi Hills', 0, 0)," +
            "(71, 22, 1, 'North Garo Hills', 0, 0)," +
            "(72, 10, 1, 'Baleswar', 0, 0)," +
            "(73, 10, 1, 'Ganjam', 0, 0)," +
            "(74, 10, 1, 'baramunda', 0, 0)," +
            "(75, 10, 1, 'Rayagada', 0, 0)," +
            "(76, 10, 1, 'Sundargarh', 0, 0)," +
            "(77, 8, 1, 'Jalandhar', 0, 0)," +
            "(78, 8, 1, 'Ludhiana', 0, 0)," +
            "(79, 8, 1, 'Mohali', 0, 0)," +
            "(80, 7, 1, 'Ajmer', 0, 0)," +
            "(81, 7, 1, 'Alwar', 0, 0)," +
            "(82, 7, 1, 'Barmer', 0, 0)," +
            "(83, 7, 1, 'Bhilwara', 0, 0)," +
            "(84, 7, 1, 'Jalore', 0, 0)," +
            "(85, 7, 1, 'Sirohi', 0, 0)," +
            "(86, 23, 1, 'Dehradun', 0, 0)," +
            "(87, 16, 1, 'Ahmedabad', 0, 0)," +
            "(89, 11, 1, 'Kanpur', 0, 0)," +
            "(90, 2, 1, 'Thane', 0, 0)," +
            "(91, 11, 1, 'Firozabad', 0, 0)," +
            "(92, 16, 1, 'Rajkot', 0, 0)," +
            "(93, 11, 1, 'Agra', 0, 0)," +
            "(94, 9, 1, 'Siliguri', 0, 0)," +
            "(95, 2, 1, 'Nashik', 0, 0)," +
            "(96, 8, 1, 'Patiala', 0, 0)," +
            "(97, 11, 1, 'Meerut', 0, 0)," +
            "(98, 2, 1, 'Kalyan-Dombivali', 0, 0)," +
            "(99, 2, 1, 'Vasai-Virar', 0, 0)," +
            "(100, 11, 1, 'Varanasi', 0, 0)," +
            "(101, 18, 1, 'Srinagar', 0, 0)," +
            "(102, 19, 1, 'Dhanbad', 0, 0)," +
            "(103, 8, 1, 'Amritsar', 0, 0)," +
            "(104, 15, 1, 'Raipur', 0, 0)," +
            "(105, 11, 1, 'Allahabad', 0, 0)," +
            "(106, 4, 1, 'Coimbatore', 0, 0)," +
            "(107, 21, 1, 'Jabalpur', 0, 0)," +
            "(108, 21, 1, 'Gwalior', 0, 0)," +
            "(109, 14, 1, 'Vijayawada', 0, 0)," +
            "(110, 4, 1, 'Madurai', 0, 0)," +
            "(111, 13, 1, 'Guwahati', 0, 0)," +
            "(112, 3, 1, 'Hubli-Dharwad', 0, 0)," +
            "(113, 11, 1, 'Amroha', 0, 0)," +
            "(114, 11, 1, 'Moradabad', 0, 0)," +
            "(115, 11, 1, 'Aligarh', 0, 0)," +
            "(116, 2, 1, 'Solapur', 0, 0)," +
            "(117, 4, 1, 'Tiruchirappalli', 0, 0)," +
            "(118, 10, 1, 'Bhubaneswar', 0, 0)," +
            "(119, 4, 1, 'Salem', 0, 0)," +
            "(120, 5, 1, 'Warangal', 0, 0)," +
            "(121, 2, 1, 'Mira-Bhayandar', 0, 0)," +
            "(122, 2, 1, 'Bhiwandi', 0, 0)," +
            "(123, 11, 1, 'Saharanpur', 0, 0)," +
            "(124, 14, 1, 'Guntur', 0, 0)," +
            "(125, 2, 1, 'Amravati', 0, 0)," +
            "(126, 7, 1, 'Bikaner', 0, 0)," +
            "(127, 19, 1, 'Jamshedpur', 0, 0)," +
            "(128, 15, 1, 'Bhilai Nagar', 0, 0)," +
            "(129, 10, 1, 'Cuttack', 0, 0)," +
            "(130, 20, 1, 'Kochi', 0, 0)," +
            "(131, 7, 1, 'Udaipur', 0, 0)," +
            "(132, 16, 1, 'Bhavnagar', 0, 0)," +
            "(133, 9, 1, 'Asansol', 0, 0)," +
            "(134, 2, 1, 'Nanded-Waghala', 0, 0)," +
            "(135, 16, 1, 'Jamnagar', 0, 0)," +
            "(136, 21, 1, 'Ujjain', 0, 0)," +
            "(137, 2, 1, 'Sangli', 0, 0)," +
            "(138, 11, 1, 'Loni', 0, 0)," +
            "(139, 11, 1, 'Jhansi', 0, 0)," +
            "(140, 24, 1, 'Pondicherry', 0, 0)," +
            "(141, 3, 1, 'Belagavi', 0, 0)," +
            "(142, 10, 1, 'Raurkela', 0, 0)," +
            "(143, 3, 1, 'Mangaluru', 0, 0)," +
            "(144, 4, 1, 'Tirunelveli', 0, 0)," +
            "(145, 2, 1, 'Malegaon', 0, 0)," +
            "(146, 12, 1, 'Gaya', 0, 0)," +
            "(147, 4, 1, 'Tiruppur', 0, 0)," +
            "(148, 3, 1, 'Davanagere', 0, 0)," +
            "(149, 20, 1, 'Kozhikode', 0, 0)," +
            "(150, 2, 1, 'Akola', 0, 0)," +
            "(151, 14, 1, 'Kurnool', 0, 0)," +
            "(152, 19, 1, 'Bokaro Steel City', 0, 0)," +
            "(153, 14, 1, 'Rajahmundry', 0, 0)," +
            "(154, 3, 1, 'Ballari', 0, 0)," +
            "(155, 25, 1, 'Agartala', 0, 0)," +
            "(156, 12, 1, 'Bhagalpur', 0, 0)," +
            "(157, 2, 1, 'Latur', 0, 0)," +
            "(158, 2, 1, 'Dhule', 0, 0)," +
            "(159, 10, 1, 'Brahmapur', 0, 0)," +
            "(160, 3, 1, 'Mysore', 0, 0)," +
            "(161, 12, 1, 'Muzaffarpur', 0, 0)," +
            "(162, 2, 1, 'Ahmednagar', 0, 0)," +
            "(163, 20, 1, 'Kollam', 0, 0)," +
            "(164, 9, 1, 'Raghunathganj', 0, 0)," +
            "(165, 15, 1, 'Bilaspur', 0, 0)," +
            "(166, 11, 1, 'Shahjahanpur', 0, 0)," +
            "(167, 20, 1, 'Thrissur', 0, 0)," +
            "(168, 14, 1, 'Kakinada', 0, 0)," +
            "(169, 5, 1, 'Nizamabad', 0, 0)," +
            "(170, 21, 1, 'Sagar', 0, 0)," +
            "(171, 3, 1, 'Tumkur', 0, 0)," +
            "(172, 6, 1, 'Hisar', 0, 0)," +
            "(173, 6, 1, 'Rohtak', 0, 0)," +
            "(174, 6, 1, 'Panipat', 0, 0)," +
            "(175, 12, 1, 'Darbhanga', 0, 0)," +
            "(176, 9, 1, 'Kharagpur', 0, 0)," +
            "(177, 26, 1, 'Aizawl', 0, 0)," +
            "(178, 2, 1, 'Ichalkaranji', 0, 0)," +
            "(179, 14, 1, 'Tirupati', 0, 0)," +
            "(180, 6, 1, 'Karnal', 0, 0)," +
            "(181, 8, 1, 'Bathinda', 0, 0)," +
            "(182, 11, 1, 'Rampur', 0, 0)," +
            "(183, 3, 1, 'Shivamogga', 0, 0)," +
            "(184, 21, 1, 'Ratlam', 0, 0)," +
            "(185, 11, 1, 'Modinagar', 0, 0)," +
            "(186, 15, 1, 'Durg', 0, 0)," +
            "(187, 22, 1, 'Shillong', 0, 0)," +
            "(188, 27, 1, 'Imphal', 0, 0)," +
            "(189, 11, 1, 'Hapur', 0, 0)," +
            "(190, 4, 1, 'Ranipet', 0, 0)," +
            "(191, 14, 1, 'Anantapur', 0, 0)," +
            "(192, 12, 1, 'Arrah', 0, 0)," +
            "(193, 5, 1, 'Karimnagar', 0, 0)," +
            "(194, 2, 1, 'Parbhani', 0, 0)," +
            "(195, 11, 1, 'Etawah', 0, 0)," +
            "(196, 7, 1, 'Bharatpur', 0, 0)," +
            "(197, 12, 1, 'Begusarai', 0, 0)," +
            "(198, 12, 1, 'Chhapra', 0, 0)," +
            "(199, 14, 1, 'Kadapa', 0, 0)," +
            "(200, 5, 1, 'Ramagundam', 0, 0)," +
            "(201, 7, 1, 'Pali', 0, 0)," +
            "(202, 21, 1, 'Satna', 0, 0)," +
            "(203, 14, 1, 'Vizianagaram', 0, 0)," +
            "(204, 12, 1, 'Katihar', 0, 0)," +
            "(205, 23, 1, 'Hardwar', 0, 0)," +
            "(206, 6, 1, 'Sonipat', 0, 0)," +
            "(207, 4, 1, 'Nagercoil', 0, 0)," +
            "(208, 4, 1, 'Thanjavur', 0, 0)," +
            "(209, 21, 1, 'Murwara (Katni)', 0, 0)," +
            "(210, 9, 1, 'Naihati', 0, 0)," +
            "(211, 11, 1, 'Sambhal', 0, 0)," +
            "(212, 16, 1, 'Nadiad', 0, 0)," +
            "(213, 6, 1, 'Yamunanagar', 0, 0)," +
            "(214, 9, 1, 'English Bazar', 0, 0)," +
            "(215, 14, 1, 'Eluru', 0, 0)," +
            "(216, 12, 1, 'Munger', 0, 0)," +
            "(217, 6, 1, 'Panchkula', 0, 0)," +
            "(218, 3, 1, 'Raayachuru', 0, 0)," +
            "(219, 2, 1, 'Panvel', 0, 0)," +
            "(220, 19, 1, 'Deoghar', 0, 0)," +
            "(221, 14, 1, 'Ongole', 0, 0)," +
            "(222, 14, 1, 'Nandyal', 0, 0)," +
            "(223, 21, 1, 'Morena', 0, 0)," +
            "(224, 6, 1, 'Bhiwani', 0, 0)," +
            "(225, 16, 1, 'Porbandar', 0, 0)," +
            "(226, 20, 1, 'Palakkad', 0, 0)," +
            "(227, 16, 1, 'Anand', 0, 0)," +
            "(228, 12, 1, 'Purnia', 0, 0)," +
            "(229, 9, 1, 'Baharampur', 0, 0)," +
            "(230, 16, 1, 'Morvi', 0, 0)," +
            "(231, 11, 1, 'Orai', 0, 0)," +
            "(232, 11, 1, 'Bahraich', 0, 0)," +
            "(233, 7, 1, 'Sikar', 0, 0)," +
            "(234, 4, 1, 'Vellore', 0, 0)," +
            "(235, 21, 1, 'Singrauli', 0, 0)," +
            "(236, 5, 1, 'Khammam', 0, 0)," +
            "(237, 16, 1, 'Mahesana', 0, 0)," +
            "(238, 13, 1, 'Silchar', 0, 0)," +
            "(239, 10, 1, 'Sambalpur', 0, 0)," +
            "(240, 21, 1, 'Rewa', 0, 0)," +
            "(241, 11, 1, 'Unnao', 0, 0)," +
            "(242, 9, 1, 'Hugli-Chinsurah', 0, 0)," +
            "(243, 9, 1, 'Raiganj', 0, 0)," +
            "(244, 19, 1, 'Phusro', 0, 0)," +
            "(245, 19, 1, 'Adityapur', 0, 0)," +
            "(246, 20, 1, 'Alappuzha', 0, 0)," +
            "(247, 6, 1, 'Bahadurgarh', 0, 0)," +
            "(248, 14, 1, 'Machilipatnam', 0, 0)," +
            "(249, 11, 1, 'Rae Bareli', 0, 0)," +
            "(250, 9, 1, 'Jalpaiguri', 0, 0)," +
            "(251, 8, 1, 'Pathankot', 0, 0)," +
            "(252, 8, 1, 'Hoshiarpur', 0, 0)," +
            "(253, 18, 1, 'Baramula', 0, 0)," +
            "(254, 14, 1, 'Adoni', 0, 0)," +
            "(255, 6, 1, 'Jind', 0, 0)," +
            "(256, 7, 1, 'Tonk', 0, 0)," +
            "(257, 14, 1, 'Tenali', 0, 0)," +
            "(258, 4, 1, 'Kancheepuram', 0, 0)," +
            "(259, 16, 1, 'Vapi', 0, 0)," +
            "(260, 6, 1, 'Sirsa', 0, 0)," +
            "(261, 16, 1, 'Navsari', 0, 0)," +
            "(262, 5, 1, 'Mahbubnagar', 0, 0)," +
            "(263, 10, 1, 'Puri', 0, 0)," +
            "(264, 3, 1, 'Robertson Pet', 0, 0)," +
            "(265, 4, 1, 'Erode', 0, 0)," +
            "(266, 8, 1, 'Batala', 0, 0)," +
            "(267, 23, 1, 'Haldwani-cum-Kathgodam', 0, 0)," +
            "(268, 21, 1, 'Vidisha', 0, 0)," +
            "(269, 12, 1, 'Saharsa', 0, 0)," +
            "(270, 6, 1, 'Thanesar', 0, 0)," +
            "(271, 14, 1, 'Chittoor', 0, 0)," +
            "(272, 16, 1, 'Veraval', 0, 0)," +
            "(273, 11, 1, 'Lakhimpur', 0, 0)," +
            "(274, 11, 1, 'Sitapur', 0, 0)," +
            "(275, 14, 1, 'Hindupur', 0, 0)," +
            "(276, 9, 1, 'Santipur', 0, 0)," +
            "(277, 9, 1, 'Balurghat', 0, 0)," +
            "(278, 21, 1, 'Ganjbasoda', 0, 0)," +
            "(279, 8, 1, 'Moga', 0, 0)," +
            "(280, 14, 1, 'Proddatur', 0, 0)," +
            "(281, 23, 1, 'Srinagar', 0, 0)," +
            "(282, 9, 1, 'Medinipur', 0, 0)," +
            "(283, 9, 1, 'Habra', 0, 0)," +
            "(284, 12, 1, 'Sasaram', 0, 0)," +
            "(285, 12, 1, 'Hajipur', 0, 0)," +
            "(286, 16, 1, 'Bhuj', 0, 0)," +
            "(287, 21, 1, 'Shivpuri', 0, 0)," +
            "(288, 9, 1, 'Ranaghat', 0, 0)," +
            "(289, 17, 1, 'Shimla', 0, 0)," +
            "(290, 4, 1, 'Tiruvannamalai', 0, 0)," +
            "(291, 6, 1, 'Kaithal', 0, 0)," +
            "(292, 15, 1, 'Rajnandgaon', 0, 0)," +
            "(293, 16, 1, 'Godhra', 0, 0)," +
            "(294, 19, 1, 'Hazaribag', 0, 0)," +
            "(295, 14, 1, 'Bhimavaram', 0, 0)," +
            "(296, 21, 1, 'Mandsaur', 0, 0)," +
            "(297, 13, 1, 'Dibrugarh', 0, 0)," +
            "(298, 3, 1, 'Kolar', 0, 0)," +
            "(299, 9, 1, 'Bankura', 0, 0)," +
            "(300, 3, 1, 'Mandya', 0, 0);";

    public static String sSqlCityInsert2 = "INSERT INTO citynew (city_id, state_id, country_id, city_name, category_id, is_deleted) VALUES" +
            "(301, 12, 1, 'Dehri-on-Sone', 0, 0)," +
            "(302, 14, 1, 'Madanapalle', 0, 0)," +
            "(303, 8, 1, 'Malerkotla', 0, 0)," +
            "(304, 11, 1, 'Lalitpur', 0, 0)," +
            "(305, 12, 1, 'Bettiah', 0, 0)," +
            "(306, 4, 1, 'Pollachi', 0, 0)," +
            "(307, 8, 1, 'Khanna', 0, 0)," +
            "(308, 21, 1, 'Neemuch', 0, 0)," +
            "(309, 6, 1, 'Palwal', 0, 0)," +
            "(310, 16, 1, 'Palanpur', 0, 0)," +
            "(311, 14, 1, 'Guntakal', 0, 0)," +
            "(312, 9, 1, 'Nabadwip', 0, 0)," +
            "(313, 3, 1, 'Udupi', 0, 0)," +
            "(314, 15, 1, 'Jagdalpur', 0, 0)," +
            "(315, 12, 1, 'Motihari', 0, 0)," +
            "(316, 11, 1, 'Pilibhit', 0, 0)," +
            "(317, 28, 1, 'Dimapur', 0, 0)," +
            "(318, 7, 1, 'Sadulpur', 0, 0)," +
            "(319, 4, 1, 'Rajapalayam', 0, 0)," +
            "(320, 14, 1, 'Dharmavaram', 0, 0)," +
            "(321, 23, 1, 'Kashipur', 0, 0)," +
            "(322, 4, 1, 'Sivakasi', 0, 0)," +
            "(323, 9, 1, 'Darjiling', 0, 0)," +
            "(324, 3, 1, 'Chikkamagaluru', 0, 0)," +
            "(325, 14, 1, 'Gudivada', 0, 0)," +
            "(326, 10, 1, 'Baleshwar Town', 0, 0)," +
            "(327, 5, 1, 'Mancherial', 0, 0)," +
            "(328, 14, 1, 'Srikakulam', 0, 0)," +
            "(329, 5, 1, 'Adilabad', 0, 0)," +
            "(330, 2, 1, 'Yavatmal', 0, 0)," +
            "(331, 8, 1, 'Barnala', 0, 0)," +
            "(332, 13, 1, 'Nagaon', 0, 0)," +
            "(333, 14, 1, 'Narasaraopet', 0, 0)," +
            "(334, 15, 1, 'Raigarh', 0, 0)," +
            "(335, 23, 1, 'Roorkee', 0, 0)," +
            "(336, 16, 1, 'Valsad', 0, 0)," +
            "(337, 15, 1, 'Ambikapur', 0, 0)," +
            "(338, 19, 1, 'Giridih', 0, 0)," +
            "(339, 11, 1, 'Chandausi', 0, 0)," +
            "(340, 9, 1, 'Purulia', 0, 0)," +
            "(341, 16, 1, 'Patan', 0, 1)," +
            "(342, 12, 1, 'Bagaha', 0, 0)," +
            "(343, 11, 1, 'Hardoi', 0, 0)," +
            "(344, 2, 1, 'Achalpur', 0, 0)," +
            "(345, 2, 1, 'Osmanabad', 0, 0)," +
            "(346, 16, 1, 'Deesa', 0, 0)," +
            "(347, 2, 1, 'Nandurbar', 0, 0)," +
            "(348, 11, 1, 'Azamgarh', 0, 0)," +
            "(349, 19, 1, 'Ramgarh', 0, 0)," +
            "(350, 8, 1, 'Firozpur', 0, 0)," +
            "(351, 10, 1, 'Baripada Town', 0, 0)," +
            "(352, 3, 1, 'Karwar', 0, 0)," +
            "(353, 14, 1, 'Rajampet', 0, 0)," +
            "(354, 4, 1, 'Pudukkottai', 0, 0)," +
            "(355, 14, 1, 'Tadpatri', 0, 0)," +
            "(356, 2, 1, 'Satara', 0, 0)," +
            "(357, 10, 1, 'Bhadrak', 0, 0)," +
            "(358, 12, 1, 'Kishanganj', 0, 0)," +
            "(359, 5, 1, 'Suryapet', 0, 0)," +
            "(360, 2, 1, 'Wardha', 0, 0)," +
            "(361, 3, 1, 'Ranebennuru', 0, 0)," +
            "(362, 16, 1, 'Amreli', 0, 0)," +
            "(363, 4, 1, 'Neyveli (TS)', 0, 0)," +
            "(364, 12, 1, 'Jamalpur', 0, 0)," +
            "(365, 29, 1, 'Marmagao', 0, 0)," +
            "(366, 2, 1, 'Udgir', 0, 0)," +
            "(367, 14, 1, 'Tadepalligudem', 0, 0)," +
            "(368, 4, 1, 'Nagapattinam', 0, 0)," +
            "(369, 12, 1, 'Buxar', 0, 0)," +
            "(370, 2, 1, 'Aurangabad', 0, 0)," +
            "(371, 12, 1, 'Jehanabad', 0, 0)," +
            "(372, 8, 1, 'Phagwara', 0, 0)," +
            "(373, 11, 1, 'Khair', 0, 0)," +
            "(374, 7, 1, 'Sawai Madhopur', 0, 0)," +
            "(375, 8, 1, 'Kapurthala', 0, 0)," +
            "(376, 14, 1, 'Chilakaluripet', 0, 0)," +
            "(377, 12, 1, 'Aurangabad', 0, 0)," +
            "(378, 20, 1, 'Malappuram', 0, 0)," +
            "(379, 6, 1, 'Rewari', 0, 0)," +
            "(380, 7, 1, 'Nagaur', 0, 0)," +
            "(381, 11, 1, 'Sultanpur', 0, 0)," +
            "(382, 21, 1, 'Nagda', 0, 0)," +
            "(383, 31, 1, 'Port Blair', 0, 0)," +
            "(384, 12, 1, 'Lakhisarai', 0, 0)," +
            "(385, 29, 1, 'Panaji', 0, 0)," +
            "(386, 13, 1, 'Tinsukia', 0, 0)," +
            "(387, 21, 1, 'Itarsi', 0, 0)," +
            "(388, 28, 1, 'Kohima', 0, 0)," +
            "(389, 10, 1, 'Balangir', 0, 0)," +
            "(390, 12, 1, 'Nawada', 0, 0)," +
            "(391, 10, 1, 'Jharsuguda', 0, 0)," +
            "(392, 5, 1, 'Jagtial', 0, 0)," +
            "(393, 4, 1, 'Viluppuram', 0, 0)," +
            "(394, 2, 1, 'Amalner', 0, 0)," +
            "(395, 8, 1, 'Zirakpur', 0, 0)," +
            "(396, 11, 1, 'Tanda', 0, 0)," +
            "(397, 4, 1, 'Tiruchengode', 0, 0)," +
            "(398, 11, 1, 'Nagina', 0, 0)," +
            "(399, 14, 1, 'Yemmiganur', 0, 0)," +
            "(400, 4, 1, 'Vaniyambadi', 0, 0)," +
            "(401, 21, 1, 'Sarni', 0, 0)," +
            "(402, 4, 1, 'Theni Allinagaram', 0, 0)," +
            "(403, 29, 1, 'Margao', 0, 0)," +
            "(404, 2, 1, 'Akot', 0, 0)," +
            "(405, 21, 1, 'Sehore', 0, 0)," +
            "(406, 21, 1, 'Mhow Cantonment', 0, 0)," +
            "(407, 8, 1, 'Kot Kapura', 0, 0)," +
            "(408, 7, 1, 'Makrana', 0, 0)," +
            "(409, 2, 1, 'Pandharpur', 0, 0)," +
            "(410, 5, 1, 'Miryalaguda', 0, 0)," +
            "(411, 11, 1, 'Shamli', 0, 0)," +
            "(412, 21, 1, 'Seoni', 0, 0)," +
            "(413, 3, 1, 'Ranibennur', 0, 0)," +
            "(414, 14, 1, 'Kadiri', 0, 0)," +
            "(415, 2, 1, 'Shrirampur', 0, 0)," +
            "(416, 23, 1, 'Rudrapur', 0, 0)," +
            "(417, 2, 1, 'Parli', 0, 0)," +
            "(418, 11, 1, 'Najibabad', 0, 0)," +
            "(419, 5, 1, 'Nirmal', 0, 0)," +
            "(420, 4, 1, 'Udhagamandalam', 0, 0)," +
            "(421, 11, 1, 'Shikohabad', 0, 0)," +
            "(422, 19, 1, 'Jhumri Tilaiya', 0, 0)," +
            "(423, 4, 1, 'Aruppukkottai', 0, 0)," +
            "(424, 20, 1, 'Ponnani', 0, 0)," +
            "(425, 12, 1, 'Jamui', 0, 0)," +
            "(426, 12, 1, 'Sitamarhi', 0, 0)," +
            "(427, 14, 1, 'Chirala', 0, 0)," +
            "(428, 16, 1, 'Anjar', 0, 0)," +
            "(429, 24, 1, 'Karaikal', 0, 0)," +
            "(430, 6, 1, 'Hansi', 0, 0)," +
            "(431, 14, 1, 'Anakapalle', 0, 0)," +
            "(432, 15, 1, 'Mahasamund', 0, 0)," +
            "(433, 8, 1, 'Faridkot', 0, 0)," +
            "(434, 19, 1, 'Saunda', 0, 0)," +
            "(435, 16, 1, 'Dhoraji', 0, 0)," +
            "(436, 4, 1, 'Paramakudi', 0, 0)," +
            "(437, 21, 1, 'Balaghat', 0, 0)," +
            "(438, 7, 1, 'Sujangarh', 0, 0)," +
            "(439, 16, 1, 'Khambhat', 0, 0)," +
            "(440, 8, 1, 'Muktsar', 0, 0)," +
            "(441, 8, 1, 'Rajpura', 0, 0)," +
            "(442, 14, 1, 'Kavali', 0, 0)," +
            "(443, 15, 1, 'Dhamtari', 0, 0)," +
            "(444, 21, 1, 'Ashok Nagar', 0, 0)," +
            "(445, 7, 1, 'Sardarshahar', 0, 0)," +
            "(446, 16, 1, 'Mahuva', 0, 0)," +
            "(447, 10, 1, 'Bargarh', 0, 0)," +
            "(448, 5, 1, 'Kamareddy', 0, 0)," +
            "(449, 19, 1, 'Sahibganj', 0, 0)," +
            "(450, 5, 1, 'Kothagudem', 0, 0)," +
            "(451, 3, 1, 'Ramanagaram', 0, 0)," +
            "(452, 3, 1, 'Gokak', 0, 0)," +
            "(453, 21, 1, 'Tikamgarh', 0, 0)," +
            "(454, 12, 1, 'Araria', 0, 0)," +
            "(455, 23, 1, 'Rishikesh', 0, 0)," +
            "(456, 21, 1, 'Shahdol', 0, 0)," +
            "(457, 19, 1, 'Medininagar (Daltonganj)', 0, 0)," +
            "(458, 4, 1, 'Arakkonam', 0, 0)," +
            "(459, 2, 1, 'Washim', 0, 0)," +
            "(460, 8, 1, 'Sangrur', 0, 0)," +
            "(461, 5, 1, 'Bodhan', 0, 0)," +
            "(462, 8, 1, 'Fazilka', 0, 0)," +
            "(463, 14, 1, 'Palacole', 0, 0)," +
            "(464, 16, 1, 'Keshod', 0, 0)," +
            "(465, 14, 1, 'Sullurpeta', 0, 0)," +
            "(466, 16, 1, 'Wadhwan', 0, 0)," +
            "(467, 8, 1, 'Gurdaspur', 0, 0)," +
            "(468, 20, 1, 'Vatakara', 0, 0)," +
            "(469, 22, 1, 'Tura', 0, 0)," +
            "(470, 6, 1, 'Narnaul', 0, 0)," +
            "(471, 8, 1, 'Kharar', 0, 0)," +
            "(472, 3, 1, 'Yadgir', 0, 0)," +
            "(473, 2, 1, 'Ambejogai', 0, 0)," +
            "(474, 16, 1, 'Ankleshwar', 0, 0)," +
            "(475, 16, 1, 'Savarkundla', 0, 0)," +
            "(476, 10, 1, 'Paradip', 0, 0)," +
            "(477, 4, 1, 'Virudhachalam', 0, 0)," +
            "(478, 20, 1, 'Kanhangad', 0, 0)," +
            "(479, 16, 1, 'Kadi', 0, 0)," +
            "(480, 4, 1, 'Srivilliputhur', 0, 0)," +
            "(481, 8, 1, 'Gobindgarh', 0, 0)," +
            "(482, 4, 1, 'Tindivanam', 0, 0)," +
            "(483, 8, 1, 'Mansa', 0, 0)," +
            "(484, 20, 1, 'Taliparamba', 0, 0)," +
            "(485, 2, 1, 'Manmad', 0, 0)," +
            "(486, 14, 1, 'Tanuku', 0, 0)," +
            "(487, 14, 1, 'Rayachoti', 0, 0)," +
            "(488, 4, 1, 'Virudhunagar', 0, 0)," +
            "(489, 20, 1, 'Koyilandy', 0, 0)," +
            "(490, 13, 1, 'Jorhat', 0, 0)," +
            "(491, 4, 1, 'Karur', 0, 0)," +
            "(492, 4, 1, 'Valparai', 0, 0)," +
            "(493, 14, 1, 'Srikalahasti', 0, 0)," +
            "(494, 20, 1, 'Neyyattinkara', 0, 0)," +
            "(495, 14, 1, 'Bapatla', 0, 0)," +
            "(496, 6, 1, 'Fatehabad', 0, 0)," +
            "(497, 8, 1, 'Malout', 0, 0)," +
            "(498, 4, 1, 'Sankarankovil', 0, 0)," +
            "(499, 4, 1, 'Tenkasi', 0, 0)," +
            "(500, 2, 1, 'Ratnagiri', 0, 0)," +
            "(501, 3, 1, 'Rabkavi Banhatti', 0, 0)," +
            "(502, 11, 1, 'Sikandrabad', 0, 0)," +
            "(503, 19, 1, 'Chaibasa', 0, 0)," +
            "(504, 15, 1, 'Chirmiri', 0, 0)," +
            "(505, 5, 1, 'Palwancha', 0, 0)," +
            "(506, 10, 1, 'Bhawanipatna', 0, 0)," +
            "(507, 20, 1, 'Kayamkulam', 0, 0)," +
            "(508, 21, 1, 'Pithampur', 0, 0)," +
            "(509, 8, 1, 'Nabha', 0, 0)," +
            "(510, 11, 1, 'Shahabad, Hardoi', 0, 0)," +
            "(511, 10, 1, 'Dhenkanal', 0, 0)," +
            "(512, 2, 1, 'Uran Islampur', 0, 0)," +
            "(513, 12, 1, 'Gopalganj', 0, 0)," +
            "(514, 13, 1, 'Bongaigaon City', 0, 0)," +
            "(515, 4, 1, 'Palani', 0, 0)," +
            "(516, 2, 1, 'Pusad', 0, 0)," +
            "(517, 18, 1, 'Sopore', 0, 0)," +
            "(518, 11, 1, 'Pilkhuwa', 0, 0)," +
            "(519, 8, 1, 'Tarn Taran', 0, 0)," +
            "(520, 11, 1, 'Renukoot', 0, 0)," +
            "(521, 5, 1, 'Mandamarri', 0, 0)," +
            "(522, 3, 1, 'Shahabad', 0, 0)," +
            "(523, 10, 1, 'Barbil', 0, 0)," +
            "(524, 5, 1, 'Koratla', 0, 0)," +
            "(525, 12, 1, 'Madhubani', 0, 0)," +
            "(526, 9, 1, 'Arambagh', 0, 0)," +
            "(527, 6, 1, 'Gohana', 0, 0)," +
            "(528, 7, 1, 'Ladnu', 0, 0)," +
            "(529, 4, 1, 'Pattukkottai', 0, 0)," +
            "(530, 3, 1, 'Sirsi', 0, 0)," +
            "(531, 5, 1, 'Sircilla', 0, 0)," +
            "(532, 9, 1, 'Tamluk', 0, 0)," +
            "(533, 8, 1, 'Jagraon', 0, 0)," +
            "(534, 9, 1, 'AlipurdUrban Agglomerationr', 0, 0)," +
            "(535, 21, 1, 'Alirajpur', 0, 0)," +
            "(536, 5, 1, 'Tandur', 0, 0)," +
            "(537, 14, 1, 'Naidupet', 0, 0)," +
            "(538, 4, 1, 'Tirupathur', 0, 0)," +
            "(539, 6, 1, 'Tohana', 0, 0)," +
            "(540, 7, 1, 'Ratangarh', 0, 0)," +
            "(541, 13, 1, 'Dhubri', 0, 0)," +
            "(542, 12, 1, 'Masaurhi', 0, 0)," +
            "(543, 16, 1, 'Visnagar', 0, 0)," +
            "(544, 11, 1, 'Vrindavan', 0, 0)," +
            "(545, 7, 1, 'Nokha', 0, 0)," +
            "(546, 14, 1, 'Nagari', 0, 0)," +
            "(547, 6, 1, 'Narwana', 0, 0)," +
            "(548, 4, 1, 'Ramanathapuram', 0, 0)," +
            "(549, 11, 1, 'Ujhani', 0, 0)," +
            "(550, 12, 1, 'Samastipur', 0, 0)," +
            "(551, 11, 1, 'Laharpur', 0, 0)," +
            "(552, 2, 1, 'Sangamner', 0, 0)," +
            "(553, 7, 1, 'Nimbahera', 0, 0)," +
            "(554, 5, 1, 'Siddipet', 0, 0)," +
            "(555, 9, 1, 'Suri', 0, 0)," +
            "(556, 13, 1, 'Diphu', 0, 0)," +
            "(557, 9, 1, 'Jhargram', 0, 0)," +
            "(558, 2, 1, 'Shirpur-Warwade', 0, 0)," +
            "(559, 11, 1, 'Tilhar', 0, 0)," +
            "(560, 3, 1, 'Sindhnur', 0, 0)," +
            "(561, 4, 1, 'Udumalaipettai', 0, 0)," +
            "(562, 2, 1, 'Malkapur', 0, 0)," +
            "(563, 5, 1, 'Wanaparthy', 0, 0)," +
            "(564, 14, 1, 'Gudur', 0, 0)," +
            "(565, 10, 1, 'Kendujhar', 0, 0)," +
            "(566, 21, 1, 'Mandla', 0, 0)," +
            "(567, 17, 1, 'Mandi', 0, 0)," +
            "(568, 20, 1, 'Nedumangad', 0, 0)," +
            "(569, 13, 1, 'North Lakhimpur', 0, 0)," +
            "(570, 14, 1, 'Vinukonda', 0, 0)," +
            "(571, 3, 1, 'Tiptur', 0, 0)," +
            "(572, 4, 1, 'Gobichettipalayam', 0, 0)," +
            "(573, 10, 1, 'Sunabeda', 0, 0)," +
            "(574, 2, 1, 'Wani', 0, 0)," +
            "(575, 16, 1, 'Upleta', 0, 0)," +
            "(576, 14, 1, 'Narasapuram', 0, 0)," +
            "(577, 14, 1, 'Nuzvid', 0, 0)," +
            "(578, 16, 1, 'Una', 0, 0)," +
            "(579, 14, 1, 'Markapur', 0, 0)," +
            "(580, 21, 1, 'Sheopur', 0, 0)," +
            "(581, 4, 1, 'Thiruvarur', 0, 0)," +
            "(582, 16, 1, 'Sidhpur', 0, 0)," +
            "(583, 11, 1, 'Sahaswan', 0, 0)," +
            "(584, 7, 1, 'Suratgarh', 0, 0)," +
            "(585, 21, 1, 'Shajapur', 0, 0)," +
            "(586, 2, 1, 'Lonavla', 0, 0)," +
            "(587, 14, 1, 'Ponnur', 0, 0)," +
            "(588, 5, 1, 'Kagaznagar', 0, 0)," +
            "(589, 5, 1, 'Gadwal', 0, 0)," +
            "(590, 15, 1, 'Bhatapara', 0, 0)," +
            "(591, 14, 1, 'Kandukur', 0, 0)," +
            "(592, 5, 1, 'Sangareddy', 0, 0)," +
            "(593, 16, 1, 'Unjha', 0, 0)," +
            "(594, 26, 1, 'Lunglei', 0, 0)," +
            "(595, 13, 1, 'Karimganj', 0, 0)," +
            "(596, 20, 1, 'Kannur', 0, 0)," +
            "(597, 14, 1, 'Bobbili', 0, 0)," +
            "(598, 12, 1, 'Mokameh', 0, 0)," +
            "(599, 2, 1, 'Talegaon Dabhade', 0, 0)," +
            "(600, 2, 1, 'Anjangaon', 0, 0);";

    public static String sSqlCityInsert3 = "INSERT INTO citynew (city_id, state_id, country_id, city_name, category_id, is_deleted) VALUES" +
            "(601, 16, 1, 'Mangrol', 0, 0)," +
            "(602, 8, 1, 'Sunam', 0, 0)," +
            "(603, 9, 1, 'Gangarampur', 0, 0)," +
            "(604, 4, 1, 'Thiruvallur', 0, 0)," +
            "(605, 20, 1, 'Tirur', 0, 0)," +
            "(606, 11, 1, 'Rath', 0, 0)," +
            "(607, 10, 1, 'Jatani', 0, 0)," +
            "(608, 16, 1, 'Viramgam', 0, 0)," +
            "(609, 7, 1, 'Rajsamand', 0, 0)," +
            "(610, 24, 1, 'Yanam', 0, 0)," +
            "(611, 20, 1, 'Kottayam', 0, 0)," +
            "(612, 4, 1, 'Panruti', 0, 0)," +
            "(613, 8, 1, 'Dhuri', 0, 0)," +
            "(614, 4, 1, 'Namakkal', 0, 0)," +
            "(615, 20, 1, 'Kasaragod', 0, 0)," +
            "(616, 16, 1, 'Modasa', 0, 0)," +
            "(617, 14, 1, 'Rayadurg', 0, 0)," +
            "(618, 12, 1, 'Supaul', 0, 0)," +
            "(619, 20, 1, 'Kunnamkulam', 0, 0)," +
            "(620, 2, 1, 'Umred', 0, 0)," +
            "(621, 5, 1, 'Bellampalle', 0, 0)," +
            "(622, 13, 1, 'Sibsagar', 0, 0)," +
            "(623, 6, 1, 'Mandi Dabwali', 0, 0)," +
            "(624, 20, 1, 'Ottappalam', 0, 0)," +
            "(625, 12, 1, 'Dumraon', 0, 0)," +
            "(626, 14, 1, 'Samalkot', 0, 0)," +
            "(627, 14, 1, 'Jaggaiahpet', 0, 0)," +
            "(628, 13, 1, '29lpara', 0, 0)," +
            "(629, 14, 1, 'Tuni', 0, 0)," +
            "(630, 7, 1, 'Lachhmangarh', 0, 0)," +
            "(631, 5, 1, 'Bhongir', 0, 0)," +
            "(632, 14, 1, 'Amalapuram', 0, 0)," +
            "(633, 8, 1, 'Firozpur Cantt.', 0, 0)," +
            "(634, 5, 1, 'Vikarabad', 0, 0)," +
            "(635, 20, 1, 'Thiruvalla', 0, 0)," +
            "(636, 11, 1, 'Sherkot', 0, 0)," +
            "(637, 2, 1, 'Palghar', 0, 0)," +
            "(638, 2, 1, 'Shegaon', 0, 0)," +
            "(639, 5, 1, 'Jangaon', 0, 0)," +
            "(640, 14, 1, 'Bheemunipatnam', 0, 0)," +
            "(641, 21, 1, 'Panna', 0, 0)," +
            "(642, 20, 1, 'Thodupuzha', 0, 0)," +
            "(643, 18, 1, 'KathUrban Agglomeration', 0, 0)," +
            "(644, 16, 1, 'Palitana', 0, 0)," +
            "(645, 12, 1, 'Arwal', 0, 0)," +
            "(646, 14, 1, 'Venkatagiri', 0, 0)," +
            "(647, 11, 1, 'Kalpi', 0, 0)," +
            "(648, 7, 1, 'Rajgarh (Churu)', 0, 0)," +
            "(649, 14, 1, 'Sattenapalle', 0, 0)," +
            "(650, 3, 1, 'Arsikere', 0, 0)," +
            "(651, 2, 1, 'Ozar', 0, 0)," +
            "(652, 4, 1, 'Thirumangalam', 0, 0)," +
            "(653, 16, 1, 'Petlad', 0, 0)," +
            "(654, 7, 1, 'Nasirabad', 0, 0)," +
            "(655, 2, 1, 'Phaltan', 0, 0)," +
            "(656, 9, 1, 'Rampurhat', 0, 0)," +
            "(657, 3, 1, 'Nanjangud', 0, 0)," +
            "(658, 12, 1, 'Forbesganj', 0, 0)," +
            "(659, 11, 1, 'Tundla', 0, 0)," +
            "(660, 12, 1, 'BhabUrban Agglomeration', 0, 0)," +
            "(661, 3, 1, 'Sagara', 0, 0)," +
            "(662, 14, 1, 'Pithapuram', 0, 0)," +
            "(663, 3, 1, 'Sira', 0, 0)," +
            "(664, 5, 1, 'Bhadrachalam', 0, 0)," +
            "(665, 6, 1, 'Charkhi Dadri', 0, 0)," +
            "(666, 19, 1, 'Chatra', 0, 0)," +
            "(667, 14, 1, 'Palasa Kasibugga', 0, 0)," +
            "(668, 7, 1, 'Nohar', 0, 0)," +
            "(669, 2, 1, 'Yevla', 0, 0)," +
            "(670, 8, 1, 'Sirhind Fatehgarh Sahib', 0, 0)," +
            "(671, 5, 1, 'Bhainsa', 0, 0)," +
            "(672, 14, 1, 'Parvathipuram', 0, 0)," +
            "(673, 2, 1, 'Shahade', 0, 0)," +
            "(674, 20, 1, 'Chalakudy', 0, 0)," +
            "(675, 12, 1, 'Narkatiaganj', 0, 0)," +
            "(676, 16, 1, 'Kapadvanj', 0, 0)," +
            "(677, 14, 1, 'Macherla', 0, 0)," +
            "(678, 21, 1, 'Raghogarh-Vijaypur', 0, 0)," +
            "(679, 8, 1, 'Rupnagar', 0, 0)," +
            "(680, 12, 1, 'Naugachhia', 0, 0)," +
            "(681, 21, 1, 'Sendhwa', 0, 0)," +
            "(682, 10, 1, 'Byasanagar', 0, 0)," +
            "(683, 11, 1, 'Sandila', 0, 0)," +
            "(684, 14, 1, 'Gooty', 0, 0)," +
            "(685, 14, 1, 'Salur', 0, 0)," +
            "(686, 11, 1, 'Nanpara', 0, 0)," +
            "(687, 11, 1, 'Sardhana', 0, 0)," +
            "(688, 2, 1, 'Vita', 0, 0)," +
            "(689, 19, 1, 'Gumia', 0, 0)," +
            "(690, 3, 1, 'Puttur', 0, 0)," +
            "(691, 8, 1, 'Jalandhar Cantt.', 0, 0)," +
            "(692, 11, 1, 'Nehtaur', 0, 0)," +
            "(693, 20, 1, 'Changanassery', 0, 0)," +
            "(694, 14, 1, 'Mandapeta', 0, 0)," +
            "(695, 19, 1, 'Dumka', 0, 0)," +
            "(696, 11, 1, 'Seohara', 0, 0)," +
            "(697, 2, 1, 'Umarkhed', 0, 0)," +
            "(698, 19, 1, 'Madhupur', 0, 0)," +
            "(699, 4, 1, 'Vikramasingapuram', 0, 0)," +
            "(700, 20, 1, 'Punalur', 0, 0)," +
            "(701, 10, 1, 'Kendrapara', 0, 0)," +
            "(702, 16, 1, 'Sihor', 0, 0)," +
            "(703, 4, 1, 'Nellikuppam', 0, 0)," +
            "(704, 8, 1, 'Samana', 0, 0)," +
            "(705, 2, 1, 'Warora', 0, 0)," +
            "(706, 20, 1, 'Nilambur', 0, 0)," +
            "(707, 4, 1, 'Rasipuram', 0, 0)," +
            "(708, 23, 1, 'Ramnagar', 0, 0)," +
            "(709, 14, 1, 'Jammalamadugu', 0, 0)," +
            "(710, 8, 1, 'Nawanshahr', 0, 0)," +
            "(711, 27, 1, 'Thoubal', 0, 0)," +
            "(712, 3, 1, 'Athni', 0, 0)," +
            "(713, 20, 1, 'Cherthala', 0, 0)," +
            "(714, 5, 1, 'Farooqnagar', 0, 0)," +
            "(715, 14, 1, 'Peddapuram', 0, 0)," +
            "(716, 19, 1, 'Chirkunda', 0, 0)," +
            "(717, 2, 1, 'Pachora', 0, 0)," +
            "(718, 12, 1, 'Madhepura', 0, 0)," +
            "(719, 23, 1, 'Pithoragarh', 0, 0)," +
            "(720, 2, 1, 'Tumsar', 0, 0)," +
            "(721, 7, 1, 'Phalodi', 0, 0)," +
            "(722, 4, 1, 'Tiruttani', 0, 0)," +
            "(723, 8, 1, 'Rampura Phul', 0, 0)," +
            "(724, 20, 1, 'Perinthalmanna', 0, 0)," +
            "(725, 11, 1, 'Padrauna', 0, 0)," +
            "(726, 21, 1, 'Pipariya', 0, 0)," +
            "(727, 15, 1, 'Dalli-Rajhara', 0, 0)," +
            "(728, 14, 1, 'Punganur', 0, 0)," +
            "(729, 20, 1, 'Mattannur', 0, 0)," +
            "(730, 11, 1, 'Mathura', 0, 0)," +
            "(731, 11, 1, 'Thakurdwara', 0, 0)," +
            "(732, 4, 1, 'Nandivaram-Guduvancheri', 0, 0)," +
            "(733, 3, 1, 'Mulbagal', 0, 0)," +
            "(734, 2, 1, 'Manjlegaon', 0, 0)," +
            "(735, 16, 1, 'Wankaner', 0, 0)," +
            "(736, 2, 1, 'Sillod', 0, 0)," +
            "(737, 14, 1, 'Nidadavole', 0, 0)," +
            "(738, 3, 1, 'Surapura', 0, 0)," +
            "(739, 10, 1, 'Rajagangapur', 0, 0)," +
            "(740, 12, 1, 'Sheikhpura', 0, 0)," +
            "(741, 10, 1, 'Parlakhemundi', 0, 0)," +
            "(742, 9, 1, 'Kalimpong', 0, 0)," +
            "(743, 3, 1, 'Siruguppa', 0, 0)," +
            "(744, 2, 1, 'Arvi', 0, 0)," +
            "(745, 16, 1, 'Limbdi', 0, 0)," +
            "(746, 13, 1, 'Barpeta', 0, 0)," +
            "(747, 23, 1, 'Manglaur', 0, 0)," +
            "(748, 14, 1, 'Repalle', 0, 0)," +
            "(749, 3, 1, 'Mudhol', 0, 0)," +
            "(750, 21, 1, 'Shujalpur', 0, 0)," +
            "(751, 16, 1, 'Mandvi', 0, 0)," +
            "(752, 16, 1, 'Thangadh', 0, 0)," +
            "(753, 21, 1, 'Sironj', 0, 0)," +
            "(754, 2, 1, 'Nandura', 0, 0)," +
            "(755, 20, 1, 'Shoranur', 0, 0)," +
            "(756, 7, 1, 'Nathdwara', 0, 0)," +
            "(757, 4, 1, 'Periyakulam', 0, 0)," +
            "(758, 12, 1, 'Sultanganj', 0, 0)," +
            "(759, 5, 1, 'Medak', 0, 0)," +
            "(760, 5, 1, 'Narayanpet', 0, 0)," +
            "(761, 12, 1, 'Raxaul Bazar', 0, 0)," +
            "(762, 18, 1, 'Rajauri', 0, 0)," +
            "(763, 4, 1, 'Pernampattu', 0, 0)," +
            "(764, 23, 1, 'Nainital', 0, 0)," +
            "(765, 14, 1, 'Ramachandrapuram', 0, 0)," +
            "(766, 2, 1, 'Vaijapur', 0, 0)," +
            "(767, 8, 1, 'Nangal', 0, 0)," +
            "(768, 3, 1, 'Sidlaghatta', 0, 0)," +
            "(769, 18, 1, 'Punch', 0, 0)," +
            "(770, 21, 1, 'Pandhurna', 0, 0)," +
            "(771, 2, 1, 'Wadgaon Road', 0, 0)," +
            "(772, 10, 1, 'Talcher', 0, 0)," +
            "(773, 20, 1, 'Varkala', 0, 0)," +
            "(774, 7, 1, 'Pilani', 0, 0)," +
            "(775, 21, 1, 'Nowgong', 0, 0)," +
            "(776, 15, 1, 'Naila Janjgir', 0, 0)," +
            "(777, 29, 1, 'Mapusa', 0, 0)," +
            "(778, 4, 1, 'Vellakoil', 0, 0)," +
            "(779, 7, 1, 'Merta City', 0, 0)," +
            "(780, 4, 1, 'Sivaganga', 0, 0)," +
            "(781, 21, 1, 'Mandideep', 0, 0)," +
            "(782, 2, 1, 'Sailu', 0, 0)," +
            "(783, 16, 1, 'Vyara', 0, 0)," +
            "(784, 14, 1, 'Kovvur', 0, 0)," +
            "(785, 4, 1, 'Vadalur', 0, 0)," +
            "(786, 11, 1, 'Nawabganj', 0, 0)," +
            "(787, 16, 1, 'Padra', 0, 0)," +
            "(788, 9, 1, 'Sainthia', 0, 0)," +
            "(789, 11, 1, 'Siana', 0, 0)," +
            "(790, 3, 1, 'Shahpur', 0, 0)," +
            "(791, 7, 1, 'Sojat', 0, 0)," +
            "(792, 11, 1, 'Noorpur', 0, 0)," +
            "(793, 20, 1, 'Paravoor', 0, 0)," +
            "(794, 2, 1, 'Murtijapur', 0, 0)," +
            "(795, 12, 1, 'Ramnagar', 0, 0)," +
            "(796, 9, 1, 'Taki', 0, 0)," +
            "(797, 3, 1, 'Saundatti-Yellamma', 0, 0)," +
            "(798, 20, 1, 'Pathanamthitta', 0, 0)," +
            "(799, 3, 1, 'Wadi', 0, 0)," +
            "(800, 4, 1, 'Rameshwaram', 0, 0)," +
            "(801, 2, 1, 'Tasgaon', 0, 0)," +
            "(802, 11, 1, 'Sikandra Rao', 0, 0)," +
            "(803, 21, 1, 'Sihora', 0, 0)," +
            "(804, 4, 1, 'Tiruvethipuram', 0, 0)," +
            "(805, 14, 1, 'Tiruvuru', 0, 0)," +
            "(806, 2, 1, 'Mehkar', 0, 0)," +
            "(807, 20, 1, 'Peringathur', 0, 0)," +
            "(808, 4, 1, 'Perambalur', 0, 0)," +
            "(809, 3, 1, 'Manvi', 0, 0)," +
            "(810, 28, 1, 'Zunheboto', 0, 0)," +
            "(811, 12, 1, 'Mahnar Bazar', 0, 0)," +
            "(812, 20, 1, 'Attingal', 0, 0)," +
            "(813, 6, 1, 'Shahbad', 0, 0)," +
            "(814, 11, 1, 'Puranpur', 0, 0)," +
            "(815, 3, 1, 'Nelamangala', 0, 0)," +
            "(816, 8, 1, 'Nakodar', 0, 0)," +
            "(817, 16, 1, 'Lunawada', 0, 0)," +
            "(818, 9, 1, 'Murshidabad', 0, 0)," +
            "(819, 24, 1, 'Mahe', 0, 0)," +
            "(820, 13, 1, 'Lanka', 0, 0)," +
            "(821, 11, 1, 'Rudauli', 0, 0)," +
            "(822, 28, 1, 'Tuensang', 0, 0)," +
            "(823, 3, 1, 'Lakshmeshwar', 0, 0)," +
            "(824, 8, 1, 'Zira', 0, 0)," +
            "(825, 2, 1, 'Yawal', 0, 0)," +
            "(826, 11, 1, 'Thana Bhawan', 0, 0)," +
            "(827, 3, 1, 'Ramdurg', 0, 0)," +
            "(828, 2, 1, 'Pulgaon', 0, 0)," +
            "(829, 5, 1, 'Sadasivpet', 0, 0)," +
            "(830, 3, 1, 'Nargund', 0, 0)," +
            "(831, 7, 1, 'Neem-Ka-Thana', 0, 0)," +
            "(832, 9, 1, 'Memari', 0, 0)," +
            "(833, 2, 1, 'Nilanga', 0, 0)," +
            "(834, 32, 1, 'Naharlagun', 0, 0)," +
            "(835, 19, 1, 'Pakaur', 0, 0)," +
            "(836, 2, 1, 'Wai', 0, 0)," +
            "(837, 3, 1, 'Tarikere', 0, 0)," +
            "(838, 3, 1, 'Malavalli', 0, 0)," +
            "(839, 21, 1, 'Raisen', 0, 0)," +
            "(840, 21, 1, 'Lahar', 0, 0)," +
            "(841, 14, 1, 'Uravakonda', 0, 0)," +
            "(842, 3, 1, 'Savanur', 0, 0)," +
            "(843, 18, 1, 'Udhampur', 0, 0)," +
            "(844, 2, 1, 'Umarga', 0, 0)," +
            "(845, 7, 1, 'Pratapgarh', 0, 0)," +
            "(846, 3, 1, 'Lingsugur', 0, 0)," +
            "(847, 4, 1, 'Usilampatti', 0, 0)," +
            "(848, 11, 1, 'Palia Kalan', 0, 0)," +
            "(849, 28, 1, 'Wokha', 0, 0)," +
            "(850, 16, 1, 'Rajpipla', 0, 0)," +
            "(851, 3, 1, 'Vijayapura', 0, 0)," +
            "(852, 7, 1, 'Rawatbhata', 0, 0)," +
            "(853, 7, 1, 'Sangaria', 0, 0)," +
            "(854, 2, 1, 'Paithan', 0, 0)," +
            "(855, 2, 1, 'Rahuri', 0, 0)," +
            "(856, 8, 1, 'Patti', 0, 0)," +
            "(857, 11, 1, 'Zaidpur', 0, 0)," +
            "(858, 7, 1, 'Lalsot', 0, 0)," +
            "(859, 21, 1, 'Maihar', 0, 0)," +
            "(860, 4, 1, 'Vedaranyam', 0, 0)," +
            "(861, 2, 1, 'Nawapur', 0, 0)," +
            "(862, 17, 1, 'Solan', 0, 0)," +
            "(864, 21, 1, 'Sanawad', 0, 0)," +
            "(865, 12, 1, 'Warisaliganj', 0, 0)," +
            "(866, 12, 1, 'Revelganj', 0, 0)," +
            "(867, 21, 1, 'Sabalgarh', 0, 0)," +
            "(868, 2, 1, 'Tuljapur', 0, 0)," +
            "(869, 19, 1, 'Simdega', 0, 0)," +
            "(870, 19, 1, 'Musabani', 0, 0)," +
            "(871, 20, 1, 'Kodungallur', 0, 0)," +
            "(872, 10, 1, 'Phulabani', 0, 0)," +
            "(873, 16, 1, 'Umreth', 0, 0)," +
            "(874, 14, 1, 'Narsipatnam', 0, 0)," +
            "(875, 11, 1, 'Nautanwa', 0, 0)," +
            "(876, 12, 1, 'Rajgir', 0, 0)," +
            "(877, 5, 1, 'Yellandu', 0, 0)," +
            "(878, 4, 1, 'Sathyamangalam', 0, 0)," +
            "(879, 7, 1, 'Pilibanga', 0, 0)," +
            "(880, 2, 1, 'Morshi', 0, 0)," +
            "(881, 6, 1, 'Pehowa', 0, 0)," +
            "(882, 12, 1, 'Sonepur', 0, 0)," +
            "(883, 20, 1, 'Pappinisseri', 0, 0)," +
            "(884, 11, 1, 'Zamania', 0, 0)," +
            "(885, 19, 1, 'Mihijam', 0, 0)," +
            "(886, 2, 1, 'Purna', 0, 0)," +
            "(887, 4, 1, 'Puliyankudi', 0, 0)," +
            "(888, 11, 1, 'Shikarpur, Bulandshahr', 0, 0)," +
            "(889, 21, 1, 'Umaria', 0, 0)," +
            "(890, 21, 1, 'Porsa', 0, 0)," +
            "(891, 11, 1, 'Naugawan Sadat', 0, 0)," +
            "(892, 11, 1, 'Fatehpur Sikri', 0, 0)," +
            "(893, 5, 1, 'Manuguru', 0, 0)," +
            "(894, 25, 1, 'Udaipur', 0, 0)," +
            "(895, 7, 1, 'Pipar City', 0, 0)," +
            "(896, 10, 1, 'Pattamundai', 0, 0)," +
            "(897, 4, 1, 'Nanjikottai', 0, 0)," +
            "(898, 7, 1, 'Taranagar', 0, 0)," +
            "(899, 14, 1, 'Yerraguntla', 0, 0)," +
            "(900, 2, 1, 'Satana', 0, 0);";

    public static String sSqlCityInsert4 = "INSERT INTO citynew (city_id, state_id, country_id, city_name, category_id, is_deleted) VALUES" +
            "(901, 12, 1, 'Sherghati', 0, 0)," +
            "(902, 3, 1, 'Sankeshwara', 0, 0)," +
            "(903, 3, 1, 'Madikeri', 0, 0)," +
            "(904, 4, 1, 'Thuraiyur', 0, 0)," +
            "(905, 16, 1, 'Sanand', 0, 0)," +
            "(906, 16, 1, 'Rajula', 0, 0)," +
            "(907, 5, 1, 'Kyathampalle', 0, 0)," +
            "(908, 11, 1, 'Shahabad, Rampur', 0, 0)," +
            "(909, 15, 1, 'Tilda Newra', 0, 0)," +
            "(910, 21, 1, 'Narsinghgarh', 0, 0)," +
            "(911, 20, 1, 'Chittur-Thathamangalam', 0, 0)," +
            "(912, 21, 1, 'Malaj Khand', 0, 0)," +
            "(913, 21, 1, 'Sarangpur', 0, 0)," +
            "(914, 11, 1, 'Robertsganj', 0, 0)," +
            "(915, 4, 1, 'Sirkali', 0, 0)," +
            "(916, 16, 1, 'Radhanpur', 0, 0)," +
            "(917, 4, 1, 'Tiruchendur', 0, 0)," +
            "(918, 11, 1, 'Utraula', 0, 0)," +
            "(919, 19, 1, 'Patratu', 0, 0)," +
            "(920, 7, 1, 'Vijainagar, Ajmer', 0, 0)," +
            "(921, 4, 1, 'Periyasemur', 0, 0)," +
            "(922, 2, 1, 'Pathri', 0, 0)," +
            "(923, 11, 1, 'Sadabad', 0, 0)," +
            "(924, 3, 1, 'Talikota', 0, 0)," +
            "(925, 2, 1, 'Sinnar', 0, 0)," +
            "(926, 15, 1, 'Mungeli', 0, 0)," +
            "(927, 3, 1, 'Sedam', 0, 0)," +
            "(928, 3, 1, 'Shikaripur', 0, 0)," +
            "(929, 7, 1, 'Sumerpur', 0, 0)," +
            "(930, 4, 1, 'Sattur', 0, 0)," +
            "(931, 12, 1, 'Sugauli', 0, 0)," +
            "(932, 13, 1, 'Lumding', 0, 0)," +
            "(933, 4, 1, 'Vandavasi', 0, 0)," +
            "(934, 10, 1, 'Titlagarh', 0, 0)," +
            "(935, 2, 1, 'Uchgaon', 0, 0)," +
            "(936, 28, 1, 'Mokokchung', 0, 0)," +
            "(937, 9, 1, 'Paschim Punropara', 0, 0)," +
            "(938, 7, 1, 'Sagwara', 0, 0)," +
            "(939, 7, 1, 'Ramganj Mandi', 0, 0)," +
            "(940, 9, 1, 'Tarakeswar', 0, 0)," +
            "(941, 3, 1, 'Mahalingapura', 0, 0)," +
            "(942, 25, 1, 'Dharmanagar', 0, 0)," +
            "(943, 16, 1, 'Mahemdabad', 0, 0)," +
            "(944, 15, 1, 'Manendragarh', 0, 0)," +
            "(945, 2, 1, 'Uran', 0, 0)," +
            "(946, 4, 1, 'Tharamangalam', 0, 0)," +
            "(947, 4, 1, 'Tirukkoyilur', 0, 0)," +
            "(948, 2, 1, 'Pen', 0, 0)," +
            "(949, 12, 1, 'Makhdumpur', 0, 0)," +
            "(950, 12, 1, 'Maner', 0, 0)," +
            "(951, 4, 1, 'Oddanchatram', 0, 0)," +
            "(952, 4, 1, 'Palladam', 0, 0)," +
            "(953, 21, 1, 'Mundi', 0, 0)," +
            "(954, 10, 1, 'Nabarangapur', 0, 0)," +
            "(955, 3, 1, 'Mudalagi', 0, 0)," +
            "(956, 6, 1, 'Samalkha', 0, 0)," +
            "(957, 21, 1, 'Nepanagar', 0, 0)," +
            "(958, 2, 1, 'Karjat', 0, 0)," +
            "(959, 16, 1, 'Ranavav', 0, 0)," +
            "(960, 14, 1, 'Pedana', 0, 0)," +
            "(961, 6, 1, 'Pinjore', 0, 0)," +
            "(962, 7, 1, 'Lakheri', 0, 0)," +
            "(963, 21, 1, 'Pasan', 0, 0)," +
            "(964, 14, 1, 'Puttur', 0, 0)," +
            "(965, 4, 1, 'Vadakkuvalliyur', 0, 0)," +
            "(966, 4, 1, 'Tirukalukundram', 0, 0)," +
            "(967, 21, 1, 'Mahidpur', 0, 0)," +
            "(968, 23, 1, 'Mussoorie', 0, 0)," +
            "(969, 20, 1, 'Muvattupuzha', 0, 0)," +
            "(970, 11, 1, 'Rasra', 0, 0)," +
            "(971, 7, 1, 'Udaipurwati', 0, 0)," +
            "(972, 2, 1, 'Manwath', 0, 0)," +
            "(973, 20, 1, 'Adoor', 0, 0)," +
            "(974, 4, 1, 'Uthamapalayam', 0, 0)," +
            "(975, 2, 1, 'Partur', 0, 0)," +
            "(976, 17, 1, 'Nahan', 0, 0)," +
            "(977, 6, 1, 'Ladwa', 0, 0)," +
            "(978, 13, 1, 'Mankachar', 0, 0)," +
            "(979, 22, 1, 'Nongstoin', 0, 0)," +
            "(980, 7, 1, 'Losal', 0, 0)," +
            "(981, 7, 1, 'Sri Madhopur', 0, 0)," +
            "(982, 7, 1, 'Ramngarh', 0, 0)," +
            "(983, 20, 1, 'Mavelikkara', 0, 0)," +
            "(984, 7, 1, 'Rawatsar', 0, 0)," +
            "(985, 7, 1, 'Rajakhera', 0, 0)," +
            "(986, 11, 1, 'Lar', 0, 0)," +
            "(987, 11, 1, 'Lal Gopalganj Nindaura', 0, 0)," +
            "(988, 3, 1, 'Muddebihal', 0, 0)," +
            "(989, 11, 1, 'Sirsaganj', 0, 0)," +
            "(990, 7, 1, 'Shahpura', 0, 0)," +
            "(991, 4, 1, 'Surandai', 0, 0)," +
            "(992, 2, 1, 'Sangole', 0, 0)," +
            "(993, 3, 1, 'Pavagada', 0, 0)," +
            "(994, 16, 1, 'Tharad', 0, 0)," +
            "(995, 16, 1, 'Mansa', 0, 0)," +
            "(996, 16, 1, 'Umbergaon', 0, 0)," +
            "(997, 20, 1, 'Mavoor', 0, 0)," +
            "(998, 13, 1, 'Nalbari', 0, 0)," +
            "(999, 16, 1, 'Talaja', 0, 0)," +
            "(1000, 3, 1, 'Malur', 0, 0)," +
            "(1001, 2, 1, 'Mangrulpir', 0, 0)," +
            "(1002, 10, 1, 'Soro', 0, 0)," +
            "(1004, 16, 1, 'Vadnagar', 0, 0)," +
            "(1005, 7, 1, 'Raisinghnagar', 0, 0)," +
            "(1006, 3, 1, 'Sindhagi', 0, 0)," +
            "(1007, 3, 1, 'Sanduru', 0, 0)," +
            "(1008, 6, 1, 'Sohna', 0, 0)," +
            "(1009, 16, 1, 'Manavadar', 0, 0)," +
            "(1010, 11, 1, 'Pihani', 0, 0)," +
            "(1011, 6, 1, 'Safidon', 0, 0)," +
            "(1012, 2, 1, 'Risod', 0, 0)," +
            "(1013, 12, 1, 'Rosera', 0, 0)," +
            "(1014, 4, 1, 'Sankari', 0, 0)," +
            "(1015, 7, 1, 'Malpura', 0, 0)," +
            "(1016, 9, 1, 'Sonamukhi', 0, 0)," +
            "(1017, 11, 1, 'Shamsabad, Agra', 0, 0)," +
            "(1018, 12, 1, 'Nokha', 0, 0)," +
            "(1019, 9, 1, 'PandUrban Agglomeration', 0, 0)," +
            "(1020, 9, 1, 'Mainaguri', 0, 0)," +
            "(1021, 3, 1, 'Afzalpur', 0, 0)," +
            "(1022, 2, 1, 'Shirur', 0, 0)," +
            "(1023, 16, 1, 'Salaya', 0, 0)," +
            "(1024, 4, 1, 'Shenkottai', 0, 0)," +
            "(1025, 25, 1, 'Pratapgarh', 0, 0)," +
            "(1026, 4, 1, 'Vadipatti', 0, 0)," +
            "(1027, 5, 1, 'Nagarkurnool', 0, 0)," +
            "(1028, 2, 1, 'Savner', 0, 0)," +
            "(1029, 2, 1, 'Sasvad', 0, 0)," +
            "(1030, 11, 1, 'Rudrapur', 0, 0)," +
            "(1031, 11, 1, 'Soron', 0, 0)," +
            "(1032, 4, 1, 'Sholingur', 0, 0)," +
            "(1033, 2, 1, 'Pandharkaoda', 0, 0)," +
            "(1034, 20, 1, 'Perumbavoor', 0, 0)," +
            "(1035, 3, 1, 'Maddur', 0, 0)," +
            "(1036, 7, 1, 'Nadbai', 0, 0)," +
            "(1037, 2, 1, 'Talode', 0, 0)," +
            "(1038, 2, 1, 'Shrigonda', 0, 0)," +
            "(1039, 3, 1, 'Madhugiri', 0, 0)," +
            "(1040, 3, 1, 'Tekkalakote', 0, 0)," +
            "(1041, 21, 1, 'Seoni-Malwa', 0, 0)," +
            "(1042, 2, 1, 'Shirdi', 0, 0)," +
            "(1043, 11, 1, 'SUrban Agglomerationr', 0, 0)," +
            "(1044, 3, 1, 'Terdal', 0, 0)," +
            "(1045, 2, 1, 'Raver', 0, 0)," +
            "(1047, 6, 1, 'Taraori', 0, 0)," +
            "(1048, 2, 1, 'Mukhed', 0, 0)," +
            "(1049, 4, 1, 'Manachanallur', 0, 0)," +
            "(1050, 21, 1, 'Rehli', 0, 0)," +
            "(1051, 7, 1, 'Sanchore', 0, 0)," +
            "(1052, 2, 1, 'Rajura', 0, 0)," +
            "(1053, 12, 1, 'Piro', 0, 0)," +
            "(1054, 3, 1, 'Mudabidri', 0, 0)," +
            "(1055, 2, 1, 'Vadgaon Kasba', 0, 0)," +
            "(1056, 7, 1, 'Nagar', 0, 0)," +
            "(1057, 16, 1, 'Vijapur', 0, 0)," +
            "(1058, 4, 1, 'Viswanatham', 0, 0)," +
            "(1059, 4, 1, 'Polur', 0, 0)," +
            "(1060, 4, 1, 'Panagudi', 0, 0)," +
            "(1061, 21, 1, 'Manawar', 0, 0)," +
            "(1062, 23, 1, 'Tehri', 0, 0)," +
            "(1063, 11, 1, 'Samdhan', 0, 0)," +
            "(1064, 16, 1, 'Pardi', 0, 0)," +
            "(1065, 21, 1, 'Rahatgarh', 0, 0)," +
            "(1066, 21, 1, 'Panagar', 0, 0)," +
            "(1067, 4, 1, 'Uthiramerur', 0, 0)," +
            "(1068, 2, 1, 'Tirora', 0, 0)," +
            "(1069, 13, 1, 'Rangia', 0, 0)," +
            "(1070, 11, 1, 'Sahjanwa', 0, 0)," +
            "(1071, 21, 1, 'Wara Seoni', 0, 0)," +
            "(1072, 3, 1, 'Magadi', 0, 0)," +
            "(1073, 7, 1, 'Rajgarh (Alwar)', 0, 0)," +
            "(1074, 12, 1, 'Rafiganj', 0, 0)," +
            "(1075, 21, 1, 'Tarana', 0, 0)," +
            "(1076, 11, 1, 'Rampur Maniharan', 0, 0)," +
            "(1077, 7, 1, 'Sheoganj', 0, 0)," +
            "(1078, 8, 1, 'Raikot', 0, 0)," +
            "(1079, 23, 1, 'Pauri', 0, 0)," +
            "(1080, 11, 1, 'Sumerpur', 0, 0)," +
            "(1081, 3, 1, 'Navalgund', 0, 0)," +
            "(1082, 11, 1, 'Shahganj', 0, 0)," +
            "(1083, 12, 1, 'Marhaura', 0, 0)," +
            "(1084, 11, 1, 'Tulsipur', 0, 0)," +
            "(1085, 7, 1, 'Sadri', 0, 0)," +
            "(1086, 4, 1, 'Thiruthuraipoondi', 0, 0)," +
            "(1087, 3, 1, 'Shiggaon', 0, 0)," +
            "(1088, 4, 1, 'Pallapatti', 0, 0)," +
            "(1089, 6, 1, 'Mahendragarh', 0, 0)," +
            "(1090, 21, 1, 'Sausar', 0, 0)," +
            "(1091, 4, 1, 'Ponneri', 0, 0)," +
            "(1092, 2, 1, 'Mahad', 0, 0)," +
            "(1093, 11, 1, 'Tirwaganj', 0, 0)," +
            "(1094, 13, 1, 'Margherita', 0, 0)," +
            "(1095, 17, 1, 'Sundarnagar', 0, 0)," +
            "(1096, 21, 1, 'Rajgarh', 0, 0)," +
            "(1097, 13, 1, 'Mangaldoi', 0, 0)," +
            "(1098, 14, 1, 'Renigunta', 0, 0)," +
            "(1099, 8, 1, 'Longowal', 0, 0)," +
            "(1100, 6, 1, 'Ratia', 0, 0)," +
            "(1101, 4, 1, 'Lalgudi', 0, 0)," +
            "(1102, 3, 1, 'Shrirangapattana', 0, 0)," +
            "(1103, 21, 1, 'Niwari', 0, 0)," +
            "(1104, 4, 1, 'Natham', 0, 0)," +
            "(1105, 4, 1, 'Unnamalaikadai', 0, 0)," +
            "(1106, 11, 1, 'PurqUrban Agglomerationzi', 0, 0)," +
            "(1107, 11, 1, 'Shamsabad, Farrukhabad', 0, 0)," +
            "(1108, 12, 1, 'Mirganj', 0, 0)," +
            "(1109, 7, 1, 'Todaraisingh', 0, 0)," +
            "(1110, 11, 1, 'Warhapur', 0, 0)," +
            "(1111, 14, 1, 'Rajam', 0, 0)," +
            "(1112, 8, 1, 'Urmar Tanda', 0, 0)," +
            "(1113, 2, 1, 'Lonar', 0, 0)," +
            "(1114, 11, 1, 'Powayan', 0, 0)," +
            "(1115, 4, 1, 'P.N.Patti', 0, 0)," +
            "(1116, 17, 1, 'Palampur', 0, 0)," +
            "(1117, 14, 1, 'Srisailam Project (Right Flank Colony) Township', 0, 0)," +
            "(1118, 3, 1, 'Sindagi', 0, 0)," +
            "(1119, 11, 1, 'Sandi', 0, 0)," +
            "(1120, 20, 1, 'Vaikom', 0, 0)," +
            "(1121, 9, 1, 'Malda', 0, 0)," +
            "(1122, 4, 1, 'Tharangambadi', 0, 0)," +
            "(1123, 3, 1, 'Sakaleshapura', 0, 0)," +
            "(1124, 12, 1, 'Lalganj', 0, 0)," +
            "(1125, 10, 1, 'Malkangiri', 0, 0)," +
            "(1126, 16, 1, 'Rapar', 0, 0)," +
            "(1127, 21, 1, 'Mauganj', 0, 0)," +
            "(1128, 7, 1, 'Todabhim', 0, 0)," +
            "(1129, 3, 1, 'Srinivaspur', 0, 0)," +
            "(1130, 12, 1, 'Murliganj', 0, 0)," +
            "(1131, 7, 1, 'Reengus', 0, 0)," +
            "(1132, 2, 1, 'Sawantwadi', 0, 0)," +
            "(1133, 4, 1, 'Tittakudi', 0, 0)," +
            "(1134, 27, 1, 'Lilong', 0, 0)," +
            "(1135, 7, 1, 'Rajaldesar', 0, 0)," +
            "(1136, 2, 1, 'Pathardi', 0, 0)," +
            "(1137, 11, 1, 'Achhnera', 0, 0)," +
            "(1138, 4, 1, 'Pacode', 0, 0)," +
            "(1139, 11, 1, 'Naraura', 0, 0)," +
            "(1140, 11, 1, 'Nakur', 0, 0)," +
            "(1141, 20, 1, 'Palai', 0, 0)," +
            "(1142, 8, 1, 'Morinda, India', 0, 0)," +
            "(1143, 21, 1, 'Manasa', 0, 0)," +
            "(1144, 21, 1, 'Nainpur', 0, 0)," +
            "(1145, 11, 1, 'Sahaspur', 0, 0)," +
            "(1146, 2, 1, 'Pauni', 0, 0)," +
            "(1147, 21, 1, 'Prithvipur', 0, 0)," +
            "(1148, 2, 1, 'Ramtek', 0, 0)," +
            "(1149, 13, 1, 'Silapathar', 0, 0)," +
            "(1150, 16, 1, 'Songadh', 0, 0)," +
            "(1151, 11, 1, 'Safipur', 0, 0)," +
            "(1152, 21, 1, 'Sohagpur', 0, 0)," +
            "(1153, 2, 1, 'Mul', 0, 0)," +
            "(1154, 7, 1, 'Sadulshahar', 0, 0)," +
            "(1155, 8, 1, 'Phillaur', 0, 0)," +
            "(1156, 7, 1, 'Sambhar', 0, 0)," +
            "(1157, 7, 1, 'Prantij', 0, 0)," +
            "(1158, 23, 1, 'Nagla', 0, 0)," +
            "(1159, 8, 1, 'Pattran', 0, 0)," +
            "(1160, 7, 1, 'Mount Abu', 0, 0)," +
            "(1161, 11, 1, 'Reoti', 0, 0)," +
            "(1162, 19, 1, 'Tenu dam-cum-Kathhara', 0, 0)," +
            "(1163, 9, 1, 'Panchla', 0, 0)," +
            "(1164, 23, 1, 'Sitarganj', 0, 0)," +
            "(1165, 32, 1, 'Pasighat', 0, 0)," +
            "(1166, 12, 1, 'Motipur', 0, 0)," +
            "(1167, 4, 1, 'O`Valley', 0, 0)," +
            "(1168, 9, 1, 'Raghunathpur', 0, 0)," +
            "(1169, 4, 1, 'Suriyampalayam', 0, 0)," +
            "(1170, 8, 1, 'Qadian', 0, 0)," +
            "(1171, 10, 1, 'Rairangpur', 0, 0)," +
            "(1172, 33, 1, 'Silvassa', 0, 0)," +
            "(1173, 21, 1, 'Nowrozabad (Khodargama)', 0, 0)," +
            "(1174, 7, 1, 'Mangrol', 0, 0)," +
            "(1175, 2, 1, 'Soyagaon', 0, 0)," +
            "(1176, 8, 1, 'Sujanpur', 0, 0)," +
            "(1177, 12, 1, 'Manihari', 0, 0)," +
            "(1178, 11, 1, 'Sikanderpur', 0, 0)," +
            "(1179, 2, 1, 'Mangalvedhe', 0, 0)," +
            "(1180, 7, 1, 'Phulera', 0, 0)," +
            "(1181, 3, 1, 'Ron', 0, 0)," +
            "(1182, 4, 1, 'Sholavandan', 0, 0)," +
            "(1183, 11, 1, 'Saidpur', 0, 0)," +
            "(1184, 21, 1, 'Shamgarh', 0, 0)," +
            "(1185, 4, 1, 'Thammampatti', 0, 0)," +
            "(1186, 21, 1, 'Maharajpur', 0, 0)," +
            "(1187, 21, 1, 'Multai', 0, 0)," +
            "(1188, 8, 1, 'Mukerian', 0, 0)," +
            "(1189, 11, 1, 'Sirsi', 0, 0)," +
            "(1190, 11, 1, 'Purwa', 0, 0)," +
            "(1191, 12, 1, 'Sheohar', 0, 0)," +
            "(1192, 4, 1, 'Namagiripettai', 0, 0)," +
            "(1193, 11, 1, 'Parasi', 0, 0)," +
            "(1194, 16, 1, 'Lathi', 0, 0)," +
            "(1195, 11, 1, 'Lalganj', 0, 0)," +
            "(1196, 2, 1, 'Narkhed', 0, 0)," +
            "(1197, 9, 1, 'Mathabhanga', 0, 0)," +
            "(1198, 2, 1, 'Shendurjana', 0, 0)," +
            "(1199, 4, 1, 'Peravurani', 0, 0)," +
            "(1200, 13, 1, 'Mariani', 0, 0)," +
            "(1201, 11, 1, 'Phulpur', 0, 0)," +
            "(1202, 6, 1, 'Rania', 0, 0)," +
            "(1203, 21, 1, 'Pali', 0, 0)," +
            "(1204, 21, 1, 'Pachore', 0, 0)," +
            "(1205, 4, 1, 'Parangipettai', 0, 0)," +
            "(1206, 4, 1, 'Pudupattinam', 0, 0)," +
            "(1207, 20, 1, 'Panniyannur', 0, 0)," +
            "(1208, 12, 1, 'Maharajganj', 0, 0)," +
            "(1209, 21, 1, 'Rau', 0, 0)," +
            "(1210, 9, 1, 'Monoharpur', 0, 0)," +
            "(1211, 7, 1, 'Mandawa', 0, 0)," +
            "(1212, 13, 1, 'Marigaon', 0, 0)," +
            "(1213, 4, 1, 'Pallikonda', 0, 0)," +
            "(1214, 7, 1, 'Pindwara', 0, 0)," +
            "(1215, 11, 1, 'Shishgarh', 0, 0)," +
            "(1216, 2, 1, 'Patur', 0, 0)," +
            "(1217, 27, 1, 'Mayang Imphal', 0, 0)," +
            "(1218, 21, 1, 'Mhowgaon', 0, 0)," +
            "(1219, 20, 1, 'Guruvayoor', 0, 0)," +
            "(1220, 2, 1, 'Mhaswad', 0, 0)," +
            "(1221, 11, 1, 'Sahawar', 0, 0)," +
            "(1222, 4, 1, 'Sivagiri', 0, 0)," +
            "(1223, 3, 1, 'Mundargi', 0, 0)," +
            "(1224, 4, 1, 'Punjaipugalur', 0, 0)," +
            "(1225, 25, 1, 'Kailasahar', 0, 0)," +
            "(1226, 11, 1, 'Samthar', 0, 0)," +
            "(1227, 15, 1, 'Sakti', 0, 0)," +
            "(1228, 3, 1, 'Sadalagi', 0, 0)," +
            "(1229, 12, 1, 'Silao', 0, 0)," +
            "(1230, 7, 1, 'Mandalgarh', 0, 0)," +
            "(1231, 2, 1, 'Loha', 0, 0)," +
            "(1232, 11, 1, 'Pukhrayan', 0, 0)," +
            "(1233, 4, 1, 'Padmanabhapuram', 0, 0)," +
            "(1234, 25, 1, 'Belonia', 0, 0)," +
            "(1235, 26, 1, 'Saiha', 0, 0)," +
            "(1236, 9, 1, 'Srirampore', 0, 0)," +
            "(1237, 8, 1, 'Talwara', 0, 0)," +
            "(1238, 20, 1, 'Puthuppally', 0, 0)," +
            "(1239, 25, 1, 'Khowai', 0, 0)," +
            "(1240, 21, 1, 'Vijaypur', 0, 0)," +
            "(1241, 7, 1, 'Takhatgarh', 0, 0)," +
            "(1242, 4, 1, 'Thirupuvanam', 0, 0)," +
            "(1243, 9, 1, 'Adra', 0, 0)," +
            "(1244, 3, 1, 'Piriyapatna', 0, 0)," +
            "(1245, 11, 1, 'Obra', 0, 0)," +
            "(1246, 16, 1, 'Adalaj', 0, 0)," +
            "(1247, 2, 1, 'Nandgaon', 0, 0)," +
            "(1248, 12, 1, 'Barh', 0, 0)," +
            "(1249, 16, 1, 'Chhapra', 0, 0)," +
            "(1250, 20, 1, 'Panamattom', 0, 0)," +
            "(1251, 11, 1, 'Niwai', 0, 0)," +
            "(1252, 23, 1, 'Bageshwar', 0, 0)," +
            "(1253, 10, 1, 'Tarbha', 0, 0)," +
            "(1254, 3, 1, 'Adyar', 0, 0)," +
            "(1256, 2, 1, 'Warud', 0, 0)," +
            "(1257, 12, 1, 'Asarganj', 0, 0)," +
            "(1258, 6, 1, 'Sarsod', 0, 0)," +
            "(1259, 34, 2, 'New Jersey\\r\\n', 0, 1)," +
            "(1260, 9, 1, 'Howrah', 0, 0)," +
            "(1261, 3, 1, 'Gulbarga', 0, 0)," +
            "(1262, 12, 1, 'Patan', 0, 0)," +
            "(1263, 11, 1, 'Bijnor', 0, 0)," +
            "(1264, 11, 1, 'Ghaziabad', 0, 0)," +
            "(1265, 7, 1, 'sri ganganagar', 0, 0)," +
            "(1266, 20, 1, 'Ernakulam', 0, 0)," +
            "(1267, 10, 1, 'jajpur', 0, 0)," +
            "(1268, 21, 1, 'khargone', 0, 0)," +
            "(1269, 4, 1, 'dindigul', 0, 0)," +
            "(1270, 21, 1, 'Betul', 0, 0)," +
            "(1271, 9, 1, 'Birbhum', 0, 0)," +
            "(1272, 12, 1, 'bELONDI', 0, 0)," +
            "(1273, 5, 1, 'Nandipet', 0, 0)," +
            "(1274, 21, 1, 'khandwa', 0, 0)," +
            "(1275, 9, 1, 'Barddhaman', 0, 0)," +
            "(1276, 21, 1, 'Burhanpur', 0, 0)," +
            "(1277, 20, 1, 'wayanad', 0, 0)," +
            "(1278, 2, 1, 'Jalgaon', 0, 0)," +
            "(1279, 11, 1, 'Siddharth nagar', 0, 0)," +
            "(1280, 9, 1, 'Dumdum', 0, 0)," +
            "(1281, 2, 1, 'Kolhapur', 0, 0)," +
            "(1282, 7, 1, 'Jhunjhunu', 0, 0)," +
            "(1283, 2, 1, 'Gandhidham', 0, 0)," +
            "(1284, 12, 1, 'Mahnar', 0, 0)," +
            "(1285, 7, 1, 'Dooni', 0, 0)," +
            "(1286, 1, 1, 'New Layal Pur', 0, 0)," +
            "(1287, 6, 1, 'Ambala', 0, 0);";

}
