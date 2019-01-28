package com.eduvanzapplication.database;

/**
 * Created by Vijay on 31/Oct/18.
 */

public class Utils {

    ///////////////////////InstituteName///////////////////////////
    public static final String InstituteName_INFO_TABLE = "InstituteName";
    public static final String InstituteName_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS InstituteName(instituteName TEXT,instituteID TEXT,ISInsert TEXT,ISSaved TEXT,ISUploaded TEXT);";

    ///////////////////////CourseName///////////////////////////
    public static final String CourseName_INFO_TABLE = "CourseName";
    public static final String CourseName_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CourseName(courseName TEXT,courseID TEXT,ISInsert TEXT,ISSaved TEXT,ISUploaded TEXT);";

    ///////////////////////InstituteLocation///////////////////////////
    public static final String InstituteLocation_INFO_TABLE = "InstituteLocation";
    public static final String InstituteLocation_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS InstituteLocation(locationName TEXT,locationID TEXT,ISInsert TEXT,ISSaved TEXT,ISUploaded TEXT);";

    ///////////////////////UserEligibility///////////////////////////
    public static final String UserEligibility_INFO_TABLE = "UserEligibility";
    public static final String UserEligibility_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS UserEligibility(institute TEXT,course TEXT, location TEXT," +
            "loanAmount TEXT, cc TEXT,familyIncomeAmount TEXT, studentProfessional TEXT,currentResidence TEXT, borrowerFirstName TEXT, " +
            "borrowerLastName TEXT, mobile TEXT,email TEXT,ISInsert TEXT,ISSaved TEXT,ISUploaded TEXT);";

    ///////////////////////BorrowerLAF///////////////////////////
    public static final String BorrowerLAF_INFO_TABLE = "BorrowerLAF";
    public static final String BorrowerLAF_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS BorrowerLAF(logged_id TEXT,student_address_type TEXT, student_monthly_rent TEXT," +
            "student_current_address TEXT, student_current_city TEXT,student_current_state TEXT, student_current_country TEXT,student_current_pincode TEXT, " +
            "student_permanent_address TEXT, student_permanent_city TEXT, student_permanent_state TEXT,student_permanent_country TEXT, student_gender TEXT, " +
            "any_gaps TEXT, student_permanent_pincode TEXT, " +
            "borrower_previous_emi_amount TEXT, student_first_name TEXT,student_last_name TEXT, student_dob TEXT,student_married TEXT, student_pan_card_no TEXT, " +
            "student_aadhar_card_no TEXT, student_current_address_duration TEXT,borrower_employer_type TEXT," +
            "last_degree_completed TEXT, is_cgpa TEXT,last_degree_percentage TEXT," +
            "last_degree_cgpa TEXT, last_degree_year_completion TEXT,is_student_working TEXT," +
            "student_working_organization TEXT, working_organization_since TEXT,student_income TEXT," +
            "advance_payment TEXT, borrower_has_any_emi TEXT,ip_address TEXT," +
            "ISInsert TEXT,ISSaved TEXT,ISUploaded TEXT);";

    ///////////////////////CoBorrowerLAF///////////////////////////
    public static final String CoBorrowerLAF_INFO_TABLE = "CoBorrowerLAF";
    public static final String CoBorrowerLAF_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS CoBorrowerLAF(logged_id TEXT,coborrower_address_type TEXT, " +
            "coborrower_monthly_rent TEXT,coborrower_current_address TEXT, coborrower_current_city TEXT,coborrower_current_state TEXT, coborrower_current_country TEXT, " +
            "coborrower_current_pincode TEXT, coborrower_permanent_address TEXT,coborrower_permanent_city TEXT, coborrower_permanent_state TEXT, " +
            "coborrower_permanent_country TEXT, coborrower_permanent_pincode TEXT,coborrower_first_name TEXT, coborrower_last_name TEXT, coborrower_dob TEXT, " +
            "coborrower_is_married TEXT,coborrower_pan_no TEXT, coborrower_aadhar_no TEXT,coborrower_email TEXT, coborrower_mobile TEXT, " +
            "coborrower_living_since TEXT, coborrower_relationship TEXT,coborrower_profession TEXT,coborrower_income TEXT, coborrower_organization TEXT," +
            "coborrower_working_organization_since TEXT, coborrower_has_any_emi TEXT, ip_address TEXT,coborrower_previous_emi_amount TEXT, " +
            "coborrower_gender_id TEXT, coborrower_employer_type TEXT, ISInsert TEXT,ISSaved TEXT,ISUploaded TEXT);";


    ///////////////City///////////////////////////
    public static final String City_INFO_TABLE = "city";
    public static final String City_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS citynew (city_id TEXT,state_id TEXT,country_id TEXT,city_name TEXT,category_id TEXT,is_deleted TEXT);";

    ///////////////City///////////////////////////
    public static final String State_INFO_TABLE = "statenew";
    public static final String State_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS statenew (state_id TEXT,country_id TEXT,state_name TEXT,is_deleted TEXT);";


    ///////////////City///////////////////////////
    public static final String DocumentUpload_INFO_TABLE = "DocumentUpload";
    public static final String DocumentUpload_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS DocumentUpload (FileName TEXT,FilePath TEXT,filedir TEXT,Doctype TEXT,DoctypeNo TEXT,selectUrl TEXT,ISSaved TEXT,ISUploaded TEXT);";


    ///////////////ErrorLog///////////////////////////
    public static final String ErrorLog_INFO_TABLE = "ErrorLog";
    public static final String ErrorLog_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS ErrorLog (errorLogID TEXT,appName TEXT," +
            "appVersion TEXT,userID TEXT,errorDate TEXT,moduleName TEXT,methodName TEXT,errorMessage TEXT,errorMessageDtls TEXT," +
            "OSVersion TEXT,IPAddress TEXT,deviceName TEXT,lineNumber TEXT,ISSaved TEXT,ISUploaded TEXT);";

}

    




