package com.eduvanzapplication.newUI;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Vijay on 3/8/17.
 */

public class MainApplication {
    //6FoiXr+hADbzGL05ywYcs3BIXaFmmTad9kydjVsUDe64c6NiaIGTK7xgjWuthdiD1oep9okGUlzmVkdcpXRhuw== password
    public static String TAG = "EDUVANZ LOG";
//    public static String mainUrl = "http://139.59.32.234/eduvanzApi/"; //PRODUCTION
//    public static String mainUrl = " http://139.59.61.225/eduvanzApi/"; //TESTING

    public static String mainUrl = "http://159.89.204.41/eduvanzApi/"; //BETA

//    public static String mainUrl = "http://192.168.0.115/eduvanzapi/"; //Sachin
//    public static String mainUrl = "http://192.168.1.78/eduvanzapi/"; //Sachin
//    public static String mainUrl = "http://192.168.0.108/eduvanzapi/"; //Samir
//    public static String mainUrl = "http://192.168.0.101/eduvanzapi/index.php/"; //Vijay

//    public static String mainUrl = "https://api.eduvanz.com/"; //PRODUCTION
//SELECT `id`, `application_id`, `student_id`,`created_datetime`, `status`,`algo_score`, `calulated_algo_score`, `calculated_algo_mapped_id`, `friendly_score`, `app_stats_score`, `has_friendly_score_opted`, `is_email_verified`, `is_mobile_verified`, `pq_score`, `cibil_status`, `cibil_application_id`, `cibil_score`, `cibil_response`, `ntc_score`, `co_cibil_status`, `co_cibil_application_id`, `co_cibil_score`, `co_ntc_score`, `co_cibil_response`,`modified_datetime`, `modified_by_id`, `modified_by_ip`, `modified_by_type`, `created_by_type`, `deleted_by_type`, `coborrower_created_by_ip`, `coborrower_created_datetime`, `lead_id`, `is_updated`, `lead_source`, `instant_sanction_status` FROM `pq_form` WHERE `lead_source` = 2 ORDER BY `created_datetime` DESC
    public static String auth_token ="",lead_id ="", application_id = "" ;
    public static int previous, previousfragment3;
    public static String mainapp_courseID = "", mainapp_instituteID = "";
    public static String mainapp_locationID = "";
    public static String mainapp_coursefee = "";
    public static String mainapp_loanamount = "";

    public static String mainapp_fammilyincome = "";
    public static String mainapp_doccheck = "";
    public static String mainapp_professioncheck = "";
    public static String mainapp_currentCity = "";
    public static String mainapp_userdocument = "", mainapp_userprofession = "";
    public static String mainapp_firstname = "", mainapp_lastname = "", mainapp_emailid = "", staticdownloadurl = "";
    public static Typeface typefaceFont, typefaceFontBold;

    public static int currrentFrag = 0;

    public static String borrowerValue1 = "", borrowerValue2 = "", borrowerValue3 = "", borrowerValue4 = "", borrowerValue5 = "", borrowerValue6 = "",
            borrowerValue7 = "", borrowerValue8 = "", borrowerValue9 = "", borrowerValue10 = "", borrowerValue11 = "", borrowerValue12 = "", borrowerValue13 = "", borrowerValue14 = "",
            borrowerValue15 = "", borrowerValue16 = "", borrowerValue17 = "", borrowerValue18 = "", borrowerValue19 = "", borrowerValue20 = "", borrowerValue21 = "", borrowerValue22 = "",
            borrowerValue23 = "", borrowerValue24 = "", borrowerValue25 = "", borrowerValue26 = "", borrowerValue27 = "", borrowerValue28 = "", borrowerValue29 = "", borrowerValue30 = "",
            borrowerValue31 = "", borrowerValue32 = "", borrowerValue33 = "", borrowerValue34 = "";

    public static String coborrowerValue1 = "", coborrowerValue2 = "", coborrowerValue3 = "", coborrowerValue4 = "", coborrowerValue5 = "", coborrowerValue6 = "",
            coborrowerValue7 = "", coborrowerValue8 = "", coborrowerValue9 = "", coborrowerValue10 = "", coborrowerValue11 = "", coborrowerValue12 = "", coborrowerValue13 = "", coborrowerValue14 = "",
            coborrowerValue15 = "", coborrowerValue16 = "", coborrowerValue17 = "", coborrowerValue18 = "", coborrowerValue19 = "", coborrowerValue20 = "", coborrowerValue21 = "", coborrowerValue22 = "",
            coborrowerValue23 = "", coborrowerValue24 = "", coborrowerValue25 = "", coborrowerValue26 = "", coborrowerValue27 = "", coborrowerValue28 = "", coborrowerValue29 = "", coborrowerValue30 = "";

//   public static String lead_id = "",fk_institutes_id = "", fk_insitutes_location_id = "", fk_course_id = "", requested_loan_amount = "",
//           applicant_id = "", profession = "", first_name = "", middle_name = "", last_name = "", dob = "", gender_id = "",
//           mobile_number = "", email_id = "", pan_number = "", aadhar_number = "", employer_name = "", annual_income = "",
//           kyc_address = "", kyc_landmark = "", kyc_address_pin = "", kyc_address_country = "", kyc_address_state = "",
//           kyc_address_city = "",
//
//           coapplicant_id = "", cofirst_name = "", comiddle_name = "", colast_name = "", codob = "", cogender_id = "",
//                   comobile_number = "", coemail_id = "", copan_number = "", coaadhar_number = "", coemployer_name = "",
//                   coannual_income = "", cokyc_address = "", cokyc_landmark = "", cokyc_address_pin = "", cokyc_address_country = "",
//                   cokyc_address_state = "", cokyc_address_city = "", has_coborrower;
//
   public static String student_id= "",sourceId= "",first_name= "",middle_name= "",last_name= "",pincode= "",dob= "",kyc_address= ""
        ,kyc_address_city= "", kyc_address_state= "",pan_number= "", aadhar_number= "",mobile_number= "",loan_amount= "",
        kyc_landmark= "",kyc_address_country= "",gender_id= "", has_aadhar_pan= "",profession= "",employer_name= "",annual_income = "",
        relationship_with_applicant = "", brmobile_number ="";
//lead_id
//applicant_id


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

//    public static void apiCallBorrower(Context context, AppCompatActivity mActivity, String userID) {
//
//        /**API CALL**/
//        try {
//
//            String url = MainApplication.mainUrl + "algo/setBorrowerLoanDetails";
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("logged_id", userID);
//            params.put("student_address_type", borrowerValue1);
//            params.put("student_monthly_rent", borrowerValue2);
//            params.put("student_current_address", borrowerValue3);
//            params.put("student_current_city", borrowerValue4);
//            params.put("student_current_state", borrowerValue5);
//            params.put("student_current_country", borrowerValue6);
//            params.put("student_current_pincode", borrowerValue7);
//            params.put("student_permanent_address", borrowerValue8);
//            params.put("student_permanent_city", borrowerValue9);
//            params.put("student_permanent_state", borrowerValue10);
//            params.put("student_permanent_country", borrowerValue11);
//            params.put("student_permanent_pincode", borrowerValue12);
//            params.put("student_first_name", borrowerValue13);
//            params.put("student_last_name", borrowerValue14);
//            params.put("student_dob", borrowerValue15);
//            params.put("student_married", borrowerValue16);
//            params.put("student_pan_card_no", borrowerValue17);
//            params.put("student_aadhar_card_no", borrowerValue18);
//
//            params.put("last_degree_completed", borrowerValue19);
//            params.put("is_cgpa", borrowerValue20);
//            params.put("last_degree_percentage", borrowerValue21);
//            params.put("last_degree_cgpa", borrowerValue22);
//            params.put("last_degree_year_completion", borrowerValue23);
//
//            params.put("is_student_working", borrowerValue24);
//            params.put("student_working_organization", borrowerValue25);
//            params.put("working_organization_since", borrowerValue26);
//            params.put("student_income", borrowerValue27);
//            params.put("advance_payment", borrowerValue28);
//
//            VolleyCallNew volleyCall = new VolleyCallNew();
//            volleyCall.sendRequest(context, url, mActivity, null, "fromMain", params, MainApplication.auth_token);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    public static void apiCallCoBorrower(Context context, AppCompatActivity mActivity, String userID) {
//        try {
//            String url = MainApplication.mainUrl + "algo/setCoBorrowerLoanDetails";
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("logged_id", userID);
//            params.put("coborrower_address_type", coborrowerValue1);
//            params.put("coborrower_monthly_rent", coborrowerValue2);
//            params.put("coborrower_current_address", coborrowerValue3);
//            params.put("coborrower_current_city", coborrowerValue4);
//            params.put("coborrower_current_state", coborrowerValue5);
//            params.put("coborrower_current_country", coborrowerValue6);
//            params.put("coborrower_current_pincode", coborrowerValue7);
//            params.put("coborrower_permanent_address", coborrowerValue8);
//            params.put("coborrower_permanent_city", coborrowerValue9);
//            params.put("coborrower_permanent_state", coborrowerValue10);
//            params.put("coborrower_permanent_country", coborrowerValue11);
//            params.put("coborrower_permanent_pincode", coborrowerValue12);
//            params.put("coborrower_first_name", coborrowerValue13);
//            params.put("coborrower_last_name", coborrowerValue14);
//            params.put("coborrower_dob", coborrowerValue15);
//            params.put("coborrower_is_married", coborrowerValue16);
//            params.put("coborrower_pan_no", coborrowerValue17);
//            params.put("coborrower_aadhar_no", coborrowerValue18);
//
//            params.put("coborrower_email", coborrowerValue19);
//            params.put("coborrower_mobile", coborrowerValue20);
//            params.put("coborrower_living_since", coborrowerValue21);
//            params.put("coborrower_relationship", coborrowerValue22);
//
//            params.put("coborrower_profession", coborrowerValue23);
//            params.put("coborrower_income", coborrowerValue24);
//            params.put("coborrower_organization", coborrowerValue25);
//            params.put("coborrower_working_organization_since", coborrowerValue26);
//
//            VolleyCallNew volleyCall = new VolleyCallNew();
//            volleyCall.sendRequest(context, url, mActivity, null, "fromMain", params, MainApplication.auth_token);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
