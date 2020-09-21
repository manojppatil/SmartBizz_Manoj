package com.smartbizz.Util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    static String REGEX_PAN = "[a-z]{3}[a|b|c|f|g|h|l|j|p|t|k][a-z]\\d{4}[a-z]";
    static String REG_EX_IFSC = "^[A-Za-z]{4}[0-9]{7}$";
    public static boolean isValidMobileNumber(final String mobileNumber) {
        if (TextUtils.isEmpty(mobileNumber) || !Patterns.PHONE.matcher(mobileNumber).matches() || mobileNumber.length() != 10) {
            return false;
        }
        return true;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    public static boolean isValidAadhar(String aadhar) {
//        if (TextUtils.isEmpty(aadhar) || aadhar.length() != 12 || !VerhoeffAlgorithm.isValidAadhar(aadhar)) {
//            return false;
//        }
//Disabled VerhoeffAlgorithm
        if (TextUtils.isEmpty(aadhar) || aadhar.length() != 12) {
            return false;
        }
        return true;
    }
    public static boolean isValidCKYC(String ckyc) {
        if (TextUtils.isEmpty(ckyc) || ckyc.length() != 14) {
            return false;
        }
        return true;
    }

    public static boolean isValidPan(String pan) {
        if (!TextUtils.isEmpty(pan) && pan.length() == 10 && pan.toLowerCase().matches(REGEX_PAN)) {
            return true;
        }
        return false;
    }

    public static boolean isValidIFSC(String ifsc) {
        if (ifsc.length() == 11 && ifsc.toLowerCase().matches(REG_EX_IFSC)) {
            return true;
        }
        return false;
    }

    public static boolean isValidPincode(String pan) {
        if (TextUtils.isEmpty(pan) || pan.length() != 6) {
            return false;
        }
        return true;
    }
    private static void makeToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


}
