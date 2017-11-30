package com.eduvanz;

import android.annotation.TargetApi;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.eduvanz.pqformfragments.SuccessAfterPQForm;
import com.eduvanz.volley.VolleyCallNew;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by projetctheena on 3/8/17.
 */

public class MainApplication {

    public static String TAG = "EDUVANZ LOG";
    public static String mainUrl = "http://139.59.32.234/eduvanzApi/";
    public static int previous, previousfragment3;
    public static String mainapp_courseID = "", mainapp_instituteID = "";
    public static String mainapp_locationID = "";
    public static String mainapp_coursefee = "";
    public static String mainapp_loanamount = "";
    public static String mainapp_fammilyincome = "";
    public static String mainapp_doccheck = "";
    public static String mainapp_professioncheck = "";
    public static String mainapp_currentCity = "";
    public static String mainapp_userdocument="", mainapp_userprofession="";
    public static String mainapp_firstname="", mainapp_lastname="", mainapp_emailid="";
    public static Typeface typefaceFont, typefaceFontBold;

    public static int currrentFrag=0;

    public static String borrowerValue1="", borrowerValue2="", borrowerValue3="", borrowerValue4="", borrowerValue5="", borrowerValue6="",
            borrowerValue7="", borrowerValue8="", borrowerValue9="", borrowerValue10="", borrowerValue11="", borrowerValue12="", borrowerValue13="", borrowerValue14="",
            borrowerValue15="", borrowerValue16="", borrowerValue17="", borrowerValue18="", borrowerValue19="", borrowerValue20="", borrowerValue21="", borrowerValue22="",
            borrowerValue23="", borrowerValue24="", borrowerValue25="", borrowerValue26="", borrowerValue27="", borrowerValue28="";

    public static String coborrowerValue1="", coborrowerValue2="", coborrowerValue3="", coborrowerValue4="", coborrowerValue5="", coborrowerValue6="",
            coborrowerValue7="", coborrowerValue8="", coborrowerValue9="", coborrowerValue10="", coborrowerValue11="", coborrowerValue12="", coborrowerValue13="", coborrowerValue14="",
            coborrowerValue15="", coborrowerValue16="", coborrowerValue17="", coborrowerValue18="", coborrowerValue19="", coborrowerValue20="", coborrowerValue21="", coborrowerValue22="",
            coborrowerValue23="", coborrowerValue24="", coborrowerValue25="", coborrowerValue26="";
    public static double latitude;
    public static double longitde;

    public void applyTypeface(TextView view, Context context) {
        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/Raleway-Regular.ttf");
        view.setTypeface(typefaceFont);
    }

    public void applyTypefaceBold(TextView view, Context context) {
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf");
        view.setTypeface(typefaceFontBold);
    }

    public static String indianCurrencyFormat(String rupees) {
        double a = Double.parseDouble(rupees);

//        DecimalFormat format = new DecimalFormat("0.#");
//        System.out.println(format.format(a));

        Locale indian = new Locale("hi", "IN");
        NumberFormat indianFormat = NumberFormat.getCurrencyInstance(indian);
        String finalAmount = String.valueOf(indianFormat.format(a));
        return finalAmount;
    }


    public static void apiCallBorrower(Context context, AppCompatActivity mActivity, String userID) {

        /**API CALL**/
        try {

            String url = MainApplication.mainUrl + "algo/setBorrowerLoanDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("logged_id", userID);
            params.put("student_address_type", borrowerValue1);
            params.put("student_monthly_rent", borrowerValue2);
            params.put("student_current_address", borrowerValue3);
            params.put("student_current_city", borrowerValue4);
            params.put("student_current_state", borrowerValue5);
            params.put("student_current_country", borrowerValue6);
            params.put("student_current_pincode", borrowerValue7);
            params.put("student_permanent_address", borrowerValue8);
            params.put("student_permanent_city", borrowerValue9);
            params.put("student_permanent_state", borrowerValue10);
            params.put("student_permanent_country", borrowerValue11);
            params.put("student_permanent_pincode", borrowerValue12);
            params.put("student_first_name", borrowerValue13);
            params.put("student_last_name", borrowerValue14);
            params.put("student_dob", borrowerValue15);
            params.put("student_married", borrowerValue16);
            params.put("student_pan_card_no", borrowerValue17);
            params.put("student_aadhar_card_no", borrowerValue18);

            params.put("last_degree_completed", borrowerValue19);
            params.put("is_cgpa", borrowerValue20);
            params.put("last_degree_percentage", borrowerValue21);
            params.put("last_degree_cgpa", borrowerValue22);
            params.put("last_degree_year_completion", borrowerValue23);

            params.put("is_student_working", borrowerValue24);
            params.put("student_working_organization", borrowerValue25);
            params.put("working_organization_since", borrowerValue26);
            params.put("student_income", borrowerValue27);
            params.put("advance_payment", borrowerValue28);

            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, mActivity, null, "fromMain", params);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void apiCallCoBorrower(Context context, AppCompatActivity mActivity, String userID) {
        try {
            String url = MainApplication.mainUrl + "algo/setCoBorrowerLoanDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("logged_id",userID);
            params.put("coborrower_address_type",coborrowerValue1);
            params.put("coborrower_monthly_rent",coborrowerValue2);
            params.put("coborrower_current_address",coborrowerValue3);
            params.put("coborrower_current_city",coborrowerValue4);
            params.put("coborrower_current_state",coborrowerValue5);
            params.put("coborrower_current_country",coborrowerValue6);
            params.put("coborrower_current_pincode",coborrowerValue7);
            params.put("coborrower_permanent_address",coborrowerValue8);
            params.put("coborrower_permanent_city",coborrowerValue9);
            params.put("coborrower_permanent_state",coborrowerValue10);
            params.put("coborrower_permanent_country",coborrowerValue11);
            params.put("coborrower_permanent_pincode",coborrowerValue12);
            params.put("coborrower_first_name",coborrowerValue13);
            params.put("coborrower_last_name",coborrowerValue14);
            params.put("coborrower_dob",coborrowerValue15);
            params.put("coborrower_is_married",coborrowerValue16);
            params.put("coborrower_pan_no",coborrowerValue17);
            params.put("coborrower_aadhar_no",coborrowerValue18);

            params.put("coborrower_email",coborrowerValue19);
            params.put("coborrower_mobile",coborrowerValue20);
            params.put("coborrower_living_since",coborrowerValue21);
            params.put("coborrower_relationship", coborrowerValue22);

            params.put("coborrower_profession",coborrowerValue23);
            params.put("coborrower_income", coborrowerValue24);
            params.put("coborrower_organization", coborrowerValue25);
            params.put("coborrower_working_organization_since",coborrowerValue26);

            VolleyCallNew volleyCall = new VolleyCallNew();
            volleyCall.sendRequest(context, url, mActivity, null, "fromMain", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    }
