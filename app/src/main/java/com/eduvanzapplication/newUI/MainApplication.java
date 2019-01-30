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


   public static String student_id= "",sourceId= "",first_name= "",middle_name= "",last_name= "",pincode= "",dob= "",kyc_address= ""
        ,kyc_address_city= "", kyc_address_state= "",pan_number= "", aadhar_number= "",mobile_number= "",loan_amount= "",
        kyc_landmark= "",kyc_address_country= "",gender_id= "", has_aadhar_pan= "",profession= "",employer_name= "",annual_income = "",
        relationship_with_applicant = "", brmobile_number ="";


   //Kyc Details
   public static String lead_idkyc = "", has_coborrowerkyc = "", application_idkyc = "", requested_loan_amountkyc = "", institute_namekyc = "",
           location_namekyc = "", course_namekyc = "", course_costkyc = "", fk_institutes_idkyc = "", fk_insitutes_location_idkyc = "",
           fk_course_idkyc = "";

    public static String Brapplicant_idkyc = "", Brfk_lead_idkyc = "", Brfk_applicant_type_idkyc = "", Brfirst_namekyc = "",
            Brmiddle_namekyc = "", Brlast_namekyc = "", Brhas_aadhar_pankyc = "", Brdobkyc = "", Brpan_numberkyc = "",
            Braadhar_numberkyc = "", Brmarital_statuskyc = "", Brgender_idkyc = "", Brmobile_numberkyc = "", Bremail_idkyc = "",
            Brrelationship_with_applicantkyc = "", Brprofessionkyc = "", Bremployer_typekyc = "", Bremployer_namekyc = "",
            Brannual_incomekyc = "", Brcurrent_employment_durationkyc = "", Brtotal_employement_durationkyc = "",
            Bremployer_mobile_numberkyc = "", Bremployer_landline_numberkyc = "", Broffice_landmarkkyc = "", Broffice_addresskyc = "",
            Broffice_address_citykyc = "", Broffice_address_statekyc = "", Broffice_address_countrykyc = "", Broffice_address_pinkyc = "",
            Brhas_active_loankyc = "", BrEMI_Amountkyc = "", Brkyc_landmarkkyc = "", Brkyc_addresskyc = "", Brkyc_address_citykyc = "",
            Brkyc_address_statekyc = "", Brkyc_address_countrykyc = "", Brkyc_address_pinkyc = "",
            Bris_borrower_current_address_same_askyc = "", Bris_coborrower_current_address_same_askyc = "", Brcurrent_residence_typekyc = "",
            Brcurrent_landmarkkyc = "", Brcurrent_addresskyc = "", Brcurrent_address_citykyc = "", Brcurrent_address_statekyc = "",
            Brcurrent_address_countrykyc = "", Brcurrent_address_pinkyc = "", Brcurrent_address_rentkyc = "",
            Brcurrent_address_stay_durationkyc = "", Bris_borrower_permanent_address_same_askyc = "",
            Bris_coborrower_permanent_address_same_askyc = "", Brpermanent_residence_typekyc = "", Brpermanent_landmarkkyc = "",
            Brpermanent_addresskyc = "", Brpermanent_address_citykyc = "", Brpermanent_address_statekyc = "",
            Brpermanent_address_countrykyc = "", Brpermanent_address_pinkyc = "", Brpermanent_address_rentkyc = "",
            Brpermanent_address_stay_durationkyc = "", Brlast_completed_degreekyc = "", Brscore_unitkyc = "", Brcgpakyc = "",
            Brpercentagekyc = "", Brpassing_yearkyc = "", Brgap_in_educationkyc = "", Brfull_name_pan_responsekyc = "", Brcreated_by_idkyc = "",
            Brcreated_date_timekyc = "", Brcreated_ip_addresskyc = "", Brmodified_bykyc = "", Brmodified_date_timekyc = "",
            Brmodified_ip_addresskyc = "", Bris_deletedkyc = "";

    public static String CoBrapplicant_idkyc = "", CoBrfk_lead_idkyc = "", CoBrfk_applicant_type_idkyc = "", CoBrfirst_namekyc = "",
            CoBrmiddle_namekyc = "", CoBrlast_namekyc = "", CoBrhas_aadhar_pankyc = "", CoBrdobkyc = "", CoBrpan_numberkyc = "",
            CoBraadhar_numberkyc = "", CoBrmarital_statuskyc = "", CoBrgender_idkyc = "", CoBrmobile_numberkyc = "",
            CoBremail_idkyc = "", CoBrrelationship_with_applicantkyc = "", CoBrprofessionkyc = "", CoBremployer_typekyc = "",
            CoBremployer_namekyc = "", CoBrannual_incomekyc = "", CoBrcurrent_employment_durationkyc = "",
            CoBrtotal_employement_durationkyc = "", CoBremployer_mobile_numberkyc = "", CoBremployer_landline_numberkyc = "",
            CoBroffice_landmarkkyc = "", CoBroffice_addresskyc = "", CoBroffice_address_citykyc = "", CoBroffice_address_statekyc = "",
            CoBroffice_address_countrykyc = "", CoBroffice_address_pinkyc = "", CoBrhas_active_loankyc = "", CoBrEMI_Amountkyc = "",
            CoBrkyc_landmarkkyc = "", CoBrkyc_addresskyc = "", CoBrkyc_address_citykyc = "", CoBrkyc_address_statekyc = "",
            CoBrkyc_address_countrykyc = "", CoBrkyc_address_pinkyc = "", CoBris_borrower_current_address_same_askyc = "",
            CoBris_coborrower_current_address_same_askyc = "", CoBrcurrent_residence_typekyc = "", CoBrcurrent_landmarkkyc = "",
            CoBrcurrent_addresskyc = "", CoBrcurrent_address_citykyc = "", CoBrcurrent_address_statekyc = "",
            CoBrcurrent_address_countrykyc = "", CoBrcurrent_address_pinkyc = "", CoBrcurrent_address_rentkyc = "",
            CoBrcurrent_address_stay_durationkyc = "", CoBris_borrower_permanent_address_same_askyc = "",
            CoBris_coborrower_permanent_address_same_askyc = "", CoBrpermanent_residence_typekyc = "", CoBrpermanent_landmarkkyc = "",
            CoBrpermanent_addresskyc = "", CoBrpermanent_address_citykyc = "", CoBrpermanent_address_statekyc = "",
            CoBrpermanent_address_countrykyc = "", CoBrpermanent_address_pinkyc = "", CoBrpermanent_address_rentkyc = "",
            CoBrpermanent_address_stay_durationkyc = "", CoBrlast_completed_degreekyc = "", CoBrscore_unitkyc = "", CoBrcgpakyc = "",
            CoBrpercentagekyc = "", CoBrpassing_yearkyc = "", CoBrgap_in_educationkyc = "", CoBrfull_name_pan_responsekyc = "",
            CoBrcreated_by_idkyc = "", CoBrcreated_date_timekyc = "", CoBrcreated_ip_addresskyc = "", CoBrmodified_bykyc = "",
            CoBrmodified_date_timekyc = "", CoBrmodified_ip_addresskyc = "", CoBris_deletedkyc = "";

    public static String lead_status_idkyc = "", fk_lead_idkyc = "", lead_statuskyc = "", lead_sub_statuskyc = "", current_stagekyc = "",
            current_statuskyc = "", lead_drop_statuskyc = "", lead_reject_statuskyc = "", lead_initiated_datetimekyc = "",
            is_lead_owner_addedkyc = "", lead_owner_added_datetimekyc = "", lead_owner_added_bykyc = "", is_lead_counsellor_addedkyc = "",
            lead_counsellor_added_datetimekyc = "", lead_counsellor_added_bykyc = "", is_kyc_details_filledkyc = "",
            kyc_details_filled_datetimekyc = "", kyc_details_filled_bykyc = "", coborrower_added_datetimekyc = "",
            coborrower_added_by_idkyc = "", is_detailed_info_filledkyc = "", detailed_info_filled_datetimekyc = "",
            detailed_info_filled_by_idkyc = "", approval_request_sales_statuskyc = "", approval_request_sales_status_datetimekyc = "",
            approval_request_sales_status_by_idkyc = "", list_of_LAF_info_pendingkyc = "", list_of_LAF_info_filledkyc = "",
            IPA_statuskyc = "", IPA_datetimekyc = "", IPA_by_idkyc = "", docs_upload_statuskyc = "", docs_upload_datetimekyc = "",
            list_of_uplaoded_docskyc = "", list_of_pendingdocskyc = "", docs_verification_statuskyc = "",
            docs_verification_datetimekyc = "", credit_approval_request_statuskyc = "", credit_approval_request_status_datetimekyc = "",
            credit_approval_request_status_by_idkyc = "", applicant_ekyc_statuskyc = "", applicant_ekyc_datetimekyc = "",
            co_applicant_ekyc_statuskyc = "", co_applicant_ekyc_datetimekyc = "", credit_assessment_statuskyc = "",
            credit_assessment_by_idkyc = "", credit_assessment_datetimekyc = "", loan_product_selection_statuskyc = "",
            loan_product_by_idkyc = "", loan_product_datetimekyc = "", underwriting_statuskyc = "", underwriting_by_idkyc = "",
            underwriting_datetimekyc = "", is_processing_fees_setkyc = "", processing_fees_set_datetimekyc = "",
            processing_fees_set_by_idkyc = "", processing_fees_paidkyc = "", processing_fees_paid_datetimekyc = "",
            processing_fees_paid_bykyc = "", lender_creation_statuskyc = "", lender_creation_modified_datetimekyc = "",
            lender_creation_modified_bykyc = "", amort_creation_statuskyc = "", amort_creation_modified_datetimekyc = "",
            amort_creation_modified_bykyc = "", borrower_pan_ekyc_responsekyc = "", borrower_aadhar_ekyc_responsekyc = "",
            borrower_pan_ekyc_statuskyc = "", borrower_aadhar_ekyc_statuskyc = "", coborrower_pan_ekyc_responsekyc = "",
            coborrower_aadhar_ekyc_responsekyc = "", coborrower_aadhar_ekyc_statuskyc = "", coborrower_pan_ekyc_statuskyc = "",
            is_cam_uploadedkyc = "", is_finbit_uploadedkyc = "", is_exception_uploadedkyc = "", is_loan_agreement_uploadedkyc = "",
            loan_agreement_uploaded_bykyc = "", applicant_pan_verified_bykyc = "", applicant_pan_verified_onkyc = "",
            created_date_timekyc = "", created_ip_addresskyc = "", modified_bykyc = "", modified_date_timekyc = "",
            modified_ip_addresskyc = "", is_deletedkyc = "", borrower_required_docskyc = "",
            co_borrower_required_docskyc = "", co_borrower_pending_docskyc = "", borrower_extra_required_docskyc = "",
            co_borrower_extra_required_docskyc = "", idkyc = "", status_namekyc = "", stage_idkyc = "";


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
