package com.eduvanzapplication.newUI.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Utils;
import com.eduvanzapplication.newUI.newViews.VideoKYC;
import com.eduvanzapplication.newUI.pojo.MKycLeads;
import com.khoslalabs.facesdk.FaceSdkModuleFactory;
import com.khoslalabs.ocrsdk.OcrSdkModuleFactory;
import com.khoslalabs.videoidkyc.ui.init.VideoIdKycInitActivity;
import com.khoslalabs.videoidkyc.ui.init.VideoIdKycInitRequest;
import com.khoslalabs.vikycapi.TimeUtil;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.eduvanzapplication.MainActivity.TAG;
import static com.eduvanzapplication.newUI.newViews.DashboardActivity.userMobileNo;
import static com.eduvanzapplication.newUI.newViews.VideoKYC.requestId;
import static com.eduvanzapplication.newUI.newViews.VideoKYC.ipadd;
import static com.eduvanzapplication.newUI.newViews.VideoKYC.transmission_datetime;
import static com.eduvanzapplication.newUI.newViews.VideoKYC.lead_id;
import static com.eduvanzapplication.newUI.newViews.VideoKYC.applicant_id;
import static com.eduvanzapplication.newUI.newViews.VideoKYC.user_idKyc;
import static com.google.gson.internal.bind.TypeAdapters.UUID;

public class LeadAdaptereKyc extends RecyclerView.Adapter<LeadAdaptereKyc.ViewHolder> {

    private static final int VI_KYC_SDK_REQ_CODE = 7879;
    List<MKycLeads> list = new ArrayList();

    public List<MKycLeads> getSpots() {
        return list;
    }

    FragmentActivity activity;

    public void setSpots(List<MKycLeads> list) {
        this.list = list;
    }

    public LeadAdaptereKyc(List<MKycLeads> list, VideoKYC videoKYC) {
        this.list = list;
        this.activity = videoKYC;
    }

    @NonNull
    @Override
    public LeadAdaptereKyc.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.ekyclist_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final MKycLeads mLeads = list.get(i);

        viewHolder.txtLeadfName.setText(mLeads.first_name + " " + mLeads.middle_name + " " + mLeads.last_name);
        viewHolder.txtApplicationId.setText(mLeads.application_id);

        if (mLeads.fk_applicant_type_id.equals("1")) {
            viewHolder.txtApplicantType.setText("Borrower");
            if (mLeads.bo_instantKyc.equals("1")) {
                viewHolder.txtStatus1.setText("Done");
            } else {
                viewHolder.txtStatus1.setText("Pending");
            }
        } else {
            viewHolder.txtApplicantType.setText("Co-Borrower");
            if (mLeads.co_instantKyc.equals("1")) {
                viewHolder.txtStatus1.setText("Done");
            } else {
                viewHolder.txtStatus1.setText("Pending");
            }
        }


        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtLeadfName, txtLeadlName, txtApplicationId, txtApplicantType, txtStatus1;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            txtLeadfName = itemView.findViewById(R.id.txtLeadfName);
            txtLeadlName = itemView.findViewById(R.id.txtLeadlName);
            txtApplicationId = itemView.findViewById(R.id.txtApplicationId);
            txtApplicantType = itemView.findViewById(R.id.txtApplicantType);
            txtStatus1 = itemView.findViewById(R.id.txtStatus1);
            //(((list.get(Integer.parseInt(itemView.getTag().toString())).bo_instantKyc).equals("0")) && ((list.get(Integer.parseInt(itemView.getTag().toString())).co_instantKyc) == null))
//(((list.get(Integer.parseInt(itemView.getTag().toString())).bo_instantKyc) == null) && ((list.get(Integer.parseInt(itemView.getTag().toString())).co_instantKyc).equals("0")))
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((list.get(Integer.parseInt(itemView.getTag().toString())).bo_instantKyc) != null) {
//applicant_id = "19712"
//application_id = "A190531051"
//bo_instantKyc = "1"
//co_instantKyc = "0"
//first_name = "Eduvanz"
//fk_applicant_type_id = "1"
//has_coborrower = "0"
//last_name = "Edu"
//lead_id = "14217"
//middle_name = ""
//student_id = "12769"

                        if ((list.get(Integer.parseInt(itemView.getTag().toString())).bo_instantKyc).equals("0")) {

                            try {
                                lead_id = list.get(Integer.parseInt(itemView.getTag().toString())).lead_id;
                                applicant_id = list.get(Integer.parseInt(itemView.getTag().toString())).applicant_id;

                                String clientCode = Globle.clientCode;  //EDUV8729 Client Code shared during Onboarding
                                String apiKey = Globle.apiKey;    //gq991xYd Api Key shared during Onboarding
                                String salt = Globle.salt; //hj17fhd8 Salt shared during Onboarding
                                String otpType = "MOB_NO"; // OTP Type option opted by client it can be null, "MOB_NO" or "EMAIL"
                                String email = null;   //emailId for getting OTP if otpType is EMAIL
                                String mobNo = userMobileNo;   //Moble No for getting OTP if otpType is MOB_NO
                                String screenTitle = Globle.screenTitle;
                                boolean ocrSdk = true;
                                boolean faceSdk = true;
                                boolean selfieSdk = true;
                                boolean docSelectMin = true;

                                requestId = clientCode + "-" + TimeUtil.getCurTimeMilisec() + "";
                                transmission_datetime = TimeUtil.getCurTimeMilisec() + "";

                                // For request integrity check  Hash Digest is also passed. Refer below how to do the same.
                                String hash = calculateHash(clientCode, requestId, apiKey, salt);

                                ipadd = Utils.getIPAddress(true);

                                if (hash == null) {
                                    throw new Exception("Hash cannot be generated.");
                                }

                                Intent myIntent = new Intent(activity, VideoIdKycInitActivity.class);

                                // Forming VideoIdKyc Request for invoking SDK.
                                VideoIdKycInitRequest.Builder videoIdKycInitRequestBuilder =
                                        new VideoIdKycInitRequest
                                                .Builder(
                                                clientCode, //EDDU2319
                                                apiKey, //762753152658161
                                                "Testing Demo Application",
                                                requestId,
                                                hash
                                        )
                                                .otpType(otpType)//mob_No
                                                .email(email)
                                                .mobile(mobNo)
                                                .screenTitle(Globle.screenTitle)//screenTitle
                                                .salt(salt); //Jge5fSh37nfKLsy88L.

                                videoIdKycInitRequestBuilder.moduleFactory(OcrSdkModuleFactory.newInstance());
                                videoIdKycInitRequestBuilder.moduleFactory(FaceSdkModuleFactory.newInstance());

                                myIntent.putExtra("init_request", videoIdKycInitRequestBuilder.build());

                                activity.startActivityForResult(myIntent, VI_KYC_SDK_REQ_CODE);

                            } catch (Exception e) {
                                Log.e(TAG, "startSdk: ", e);
                            }
                        } else {
                            Toast.makeText(activity, "Your eKyc is done already", Toast.LENGTH_SHORT).show();
                        }
                    }
                    if ((list.get(Integer.parseInt(itemView.getTag().toString())).co_instantKyc) != null) {

                        if ((list.get(Integer.parseInt(itemView.getTag().toString())).co_instantKyc).equals("0")) {

                            try {
                                lead_id = list.get(Integer.parseInt(itemView.getTag().toString())).lead_id;
                                applicant_id = list.get(Integer.parseInt(itemView.getTag().toString())).applicant_id;

                                String clientCode = Globle.clientCode;  //EDUV8729 Client Code shared during Onboarding
                                String apiKey = Globle.apiKey;    //gq991xYd Api Key shared during Onboarding
                                String salt = Globle.salt; //hj17fhd8 Salt shared during Onboarding
                                String otpType = "MOB_NO"; // OTP Type option opted by client it can be null, "MOB_NO" or "EMAIL"
                                String email = null;   //emailId for getting OTP if otpType is EMAIL
                                String mobNo = userMobileNo;   //Moble No for getting OTP if otpType is MOB_NO
                                String screenTitle = Globle.screenTitle;
                                boolean ocrSdk = true;
                                boolean faceSdk = true;
                                boolean selfieSdk = true;
                                boolean docSelectMin = true;

                                requestId = clientCode + "-" + TimeUtil.getCurTimeMilisec() + "";
                                transmission_datetime = TimeUtil.getCurTimeMilisec() + "";

                                // For request integrity check  Hash Digest is also passed. Refer below how to do the same.
                                String hash = calculateHash(clientCode, requestId, apiKey, salt);

                                ipadd = Utils.getIPAddress(true);

                                if (hash == null) {
                                    throw new Exception("Hash cannot be generated.");
                                }

                                Intent myIntent = new Intent(activity, VideoIdKycInitActivity.class);

                                // Forming VideoIdKyc Request for invoking SDK.
                                VideoIdKycInitRequest.Builder videoIdKycInitRequestBuilder =
                                        new VideoIdKycInitRequest
                                                .Builder(
                                                clientCode, //EDDU2319
                                                apiKey, //762753152658161
                                                "Testing Demo Application",
                                                requestId,
                                                hash
                                        )
                                                .otpType(otpType)//mob_No
                                                .email(email)
                                                .mobile(mobNo)
                                                .screenTitle(Globle.screenTitle)//screenTitle
                                                .salt(salt); //Jge5fSh37nfKLsy88L.

                                videoIdKycInitRequestBuilder.moduleFactory(OcrSdkModuleFactory.newInstance());
                                videoIdKycInitRequestBuilder.moduleFactory(FaceSdkModuleFactory.newInstance());

                                myIntent.putExtra("init_request", videoIdKycInitRequestBuilder.build());

                                activity.startActivityForResult(myIntent, VI_KYC_SDK_REQ_CODE);

                            } catch (Exception e) {
                                Log.e(TAG, "startSdk: ", e);
                            }
                        } else {
                            Toast.makeText(activity, "Your eKyc is done already", Toast.LENGTH_SHORT).show();
                        }
                    }

                }
            });

        }
    }

    private String calculateHash(String clientCode, String requestId, String apiKey, String salt)
            throws NoSuchAlgorithmException {

        MessageDigest digest;

        digest = MessageDigest.getInstance("SHA-256");
        if (digest != null) {

            byte[] hash =
                    digest.digest((Globle.clientCode + "|" + requestId + "|" + Globle.apiKey + "|" + Globle.salt).getBytes());

            return bytesToHex(hash);
        }

        return null;
    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

