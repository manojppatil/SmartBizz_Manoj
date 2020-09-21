package com.smartbizz.newUI;

import android.content.Context;
import android.graphics.Typeface;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Vijay on 3/8/17.
 */

public class MainApplication {
    //6FoiXr+hADbzGL05ywYcs3BIXaFmmTad9kydjVsUDe64c6NiaIGTK7xgjWuthdiD1oep9okGUlzmVkdcpXRhuw== password
    public static String TAG = "EDUVANZ LOG";
//    public static String mainUrl = "http://139.59.32.234/eduvanzApi/"; //PRODUCTION
//    public static String mainUrl = " http://139.59.61.225/eduvanzApi/"; //TESTING

//    public static String mainUrl = "http://159.89.204.41/eduvanzApi/"; //BETA

//    public static String mainUrl = "http://192.168.1.26/eduvanzapi/"; //Dharam
//    public static String mainUrl = "http://192.168.0.115/eduvanzapi/"; //Sachin
//    public static String mainUrl = "http://192.168.1.78/eduvanzapi/"; //Sachin
//    public static String mainUrl = "http://192.168.0.108/eduvanzapi/"; //Samir
//    public static String mainUrl = "http://192.168.0.101/eduvanzapi/index.php/"; //Vijay

//    public static String mainUrl = "https://api.eduvanz.com/"; ///////////////////L I V E//////////////

//SELECT `id`, `application_id`, `student_id`,`created_datetime`, `status`,`algo_score`, `calulated_algo_score`, `calculated_algo_mapped_id`, `friendly_score`, `app_stats_score`, `has_friendly_score_opted`, `is_email_verified`, `is_mobile_verified`, `pq_score`, `cibil_status`, `cibil_application_id`, `cibil_score`, `cibil_response`, `ntc_score`, `co_cibil_status`, `co_cibil_application_id`, `co_cibil_score`, `co_ntc_score`, `co_cibil_response`,`modified_datetime`, `modified_by_id`, `modified_by_ip`, `modified_by_type`, `created_by_type`, `deleted_by_type`, `coborrower_created_by_ip`, `coborrower_created_datetime`, `lead_id`, `is_updated`, `lead_source`, `instant_sanction_status` FROM `pq_form` WHERE `lead_source` = 2 ORDER BY `created_datetime` DESC
    public static String  lead_id ="", application_id = "",applicant_id ="" ;
    public static int previous, previousfragment3;
    public static String mainapp_courseID = "", mainapp_instituteID = "";
    public static String mainapp_locationID = "";
    public static String mainapp_coursefee = "";
    public static String mainapp_loanamount = "";
    public static Boolean isBorrower = true;


    public static String mainapp_fammilyincome = "";
    public static String mainapp_doccheck = "";
    public static String mainapp_professioncheck = "";
    public static String mainapp_currentCity = "";
    public static String mainapp_userdocument = "", mainapp_userprofession = "";
    public static String mainapp_firstname = "", mainapp_lastname = "", mainapp_emailid = "", staticdownloadurl = "";
    public static Typeface typefaceFont, typefaceFontBold;

    public static int currrentFrag = 0;

    public static String Brapplicant_idkyc = "";


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

}
