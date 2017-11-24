package com.eduvanz.pqformfragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.eduvanz.newUI.newViews.OtpValidation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by nikhil on 30/9/16.
 */

public class SmsReceiver extends BroadcastReceiver {
    public Pattern p = Pattern.compile("(|^)\\d{6}");
//    SharedPrefEmployer mShared;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        SuccessAfterPQForm otpPage;
        String str = "";
        String smsSource = "EDUVNZ", smsRecived = "";
//        String smsSource = "+917977534698", smsRecived = "";

        if (bundle != null) {
            //---retrieve the SMS message received---
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                str += "SMS from " + msgs[i].getOriginatingAddress();

                str += " :";
                str += msgs[i].getMessageBody().toString();
                Log.e("TAG", "onReceive: " + str);
                str += "n";
//                if(smsSource.equalsIgnoreCase(msgs[i].getOriginatingAddress())){
//                    otpPage=new OtpPage();
//                    Log.e("TAG", "onReceive: equal" );
//                    otpPage.setOtp(msgs[i].getMessageBody().toString());
//                }
                try {
                    String smsName[] = msgs[i].getOriginatingAddress().trim().split("-");
                    Log.e("TAG", "onReceive: " + msgs[i].getMessageBody()+""+ msgs[i].getMessageBody());
                    Log.e("TAG", "onReceive: split " + "\n" + smsName[0] + smsName[1]);
                    smsRecived = smsName[1];
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (smsSource.equalsIgnoreCase(smsRecived)) {

                    Log.e("TAG", "onReceive: equal" );
                    Log.e("TAG", "onReceive: message "+msgs[i].getMessageBody().toString());
                    Matcher m = p.matcher(msgs[i].getMessageBody().toString());
                    if(m.find()) {


                            OtpValidation otpPageE = new OtpValidation();
                            Log.e("TAG", "onReceive:m.group(0) Employee " + m.group(0));
                            otpPageE.setOtp(m.group(0));

//                        otp.setText(m.group(0));
                    }
                    else
                    {
                        //something went wrong
                    }
                }
                //---display the new SMS message---
                //Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
