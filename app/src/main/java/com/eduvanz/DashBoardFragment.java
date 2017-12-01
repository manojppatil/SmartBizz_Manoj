package com.eduvanz;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eduvanz.fqform.borrowerdetail.FqFormBorrower;
import com.eduvanz.fqform.coborrowerdetail.FqFormCoborrower;
import com.eduvanz.newUI.MainApplication;
import com.eduvanz.uploaddocs.UploadActivityBorrower;
import com.eduvanz.uploaddocs.UploadActivityCoBorrower;
import com.eduvanz.volley.VolleyCall;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

/** SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner */

/** CIRCULAR PROGRESSBAR  https://github.com/lzyzsd/CircleProgress **/

/** Square Progress Bar https://github.com/mrwonderman/android-square-progressbar **/

public class DashBoardFragment extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    public LinearLayout linearLayoutBorrowerDetail, linearLayoutCoborrowerDetail, linearLayoutDocumentUpload, linearLayoutDocumentUploadCoborrower, linearLayoutAccountSettings;
    TextView textView1, textView2, textView3, textView4, textView5, textView6, textView7;
    Typeface typefaceFont, typefaceFontBold;
    public static ImageView imageViewProfilePic, imageViewPQ, imageViewborrower, imageViewCoBorrower, imageViewBorrowerDoc, imageViewCoborrowerDOc;
    String userID="";
    String userpic="";


    public DashBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dash_fragment, container, false);
        context = getContext();
        mFragment = new DashBoardFragment();

        typefaceFont = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_font.ttf" );
        typefaceFontBold = Typeface.createFromAsset(context.getAssets(), "fonts/droidsans_bold.ttf" );

        textView1 = (TextView) view.findViewById(R.id.textview1);
        textView1.setTypeface(typefaceFont);
        textView2 = (TextView) view.findViewById(R.id.textview2);
        textView2.setTypeface(typefaceFont);
        textView3 = (TextView) view.findViewById(R.id.textview3);
        textView3.setTypeface(typefaceFont);
        textView4 = (TextView) view.findViewById(R.id.textview4);
        textView4.setTypeface(typefaceFont);
        textView5 = (TextView) view.findViewById(R.id.textview5);
        textView5.setTypeface(typefaceFont);
        textView6 = (TextView) view.findViewById(R.id.textview6);
        textView6.setTypeface(typefaceFont);
        textView7 = (TextView) view.findViewById(R.id.textview7);
        textView7.setTypeface(typefaceFont);

        imageViewPQ = (ImageView) view.findViewById(R.id.imageView_pq);
        imageViewborrower = (ImageView) view.findViewById(R.id.imageView_borrowerDetail);
        imageViewBorrowerDoc = (ImageView) view.findViewById(R.id.imageView_borrowerdocupload);
        imageViewCoBorrower = (ImageView) view.findViewById(R.id.imageView_coborrowerdetail);
        imageViewCoborrowerDOc = (ImageView) view.findViewById(R.id.imageView_coborrowerdocupload);

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String firstnameshared = sharedPreferences.getString("first_name","null");
        String lastnameshared = sharedPreferences.getString("last_name","null");
        String emailshared = sharedPreferences.getString("user_email","null");
        userpic = sharedPreferences.getString("user_image","null");
        userID = sharedPreferences.getString("logged_id", "null");
        Log.e(MainApplication.TAG, "firstnameshared: "+firstnameshared + "lastnameshared: "+lastnameshared + "emailshared: "+emailshared + "logged_id: "+sharedPreferences.getString("logged_id","null"));
//        Picasso.with(getApplicationContext()).load(userpic).into(imageView);

        linearLayoutBorrowerDetail = (LinearLayout) view.findViewById(R.id.linearlayout_borrowerdetail);
        linearLayoutCoborrowerDetail = (LinearLayout) view.findViewById(R.id.linearlayout_coborrowerdetail);
        linearLayoutDocumentUpload = (LinearLayout) view.findViewById(R.id.linearlayout_documentupload);
        linearLayoutAccountSettings = (LinearLayout) view.findViewById(R.id.linearlayout_accountsetting);
        linearLayoutDocumentUploadCoborrower = (LinearLayout) view.findViewById(R.id.linearlayout_documentuploadCoborrower);

        imageViewProfilePic = (ImageView) view.findViewById(R.id.circularimageview_profilepic);
        Picasso.with(getActivity()).load(userpic).into(imageViewProfilePic);

        linearLayoutBorrowerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FqFormBorrower.class);
                startActivity(intent);

            }
        });

        linearLayoutCoborrowerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FqFormCoborrower.class);
                startActivity(intent);

            }
        });

        linearLayoutDocumentUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UploadActivityBorrower.class);
                startActivity(intent);

            }
        });

        linearLayoutAccountSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MyProfile.class);
                startActivity(intent);

            }
        });

        linearLayoutDocumentUploadCoborrower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UploadActivityCoBorrower.class);
                startActivity(intent);

            }
        });

//        SquareProgressBar squareProgressBar = (SquareProgressBar) view.findViewById(R.id.sprogressbar);
//        squareProgressBar.setRoundedCorners(true);
//        squareProgressBar.showProgress(true);
//        squareProgressBar.setOpacity(true);
//        squareProgressBar.setColor("#006b4d");
//        squareProgressBar.setImageScaleType(ImageView.ScaleType.MATRIX);
//        squareProgressBar.setWidth(5);
//        PercentStyle percentStyle = new PercentStyle(Paint.Align.CENTER, 30,	true);
////        percentStyle.setTextColor(Color.parseColor("#C9C9C9"));
//        squareProgressBar.setPercentStyle(percentStyle);
////        squareProgressBar.setImageGrayscale(true);
//
//
//        try {
//            squareProgressBar.setImageDrawable(drawableFromUrl(userpic));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        squareProgressBar.setProgress(50.0);

        //-----------------------------------------API CALL---------------------------------------//
        try {
            String url = MainApplication.mainUrl + "dashboard/getDashBoardDetails";
            Map<String, String> params = new HashMap<String, String>();
            params.put("studentId", userID);
            VolleyCall volleyCall = new VolleyCall();
            volleyCall.sendRequest(context, url, null, mFragment, "getDashBoard", params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //-------------------------------------END OF API CALL------------------------------------//

        return view;
    }//-----------------------------------END OF ON CREATE----------------------------------------//

    public static Drawable drawableFromUrl(String url) throws IOException {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        Bitmap x;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.connect();
        InputStream input = connection.getInputStream();

        x = BitmapFactory.decodeStream(input);
        return new BitmapDrawable(x);
    }

    /**---------------------------------RESPONSE OF API CALL-------------------------------------**/

    public void getDashBoard(JSONObject jsonData) {
        try {
            Log.e("SERVER CALL", "getDocuments" + jsonData);
            String status = jsonData.optString("status");
            String message = jsonData.optString("message");

            if (status.equalsIgnoreCase("1")) {
                JSONObject jsonObject = jsonData.getJSONObject("result");
                String pqDone = jsonObject.getString("pq_submitted");
                String borrowerPersonalDone = jsonObject.getString("borrower_personal_submitted");
                String borrowerFinanacialDone = jsonObject.getString("borrower_financial_submitted");
                String borrowerEducationDone = jsonObject.getString("borrower_educational_submitted");
                String coborrowerPersonalDone = jsonObject.getString("coborrower_personal_submitted");
                String coborrowerFinanacialDone = jsonObject.getString("coborrower_financial_submitted");
                String borrowerDocDone = jsonObject.getString("borrower_doc_submitted");
                String coBorrowerDocDone = jsonObject.getString("coborrower_doc_submitted");


                if(pqDone.equalsIgnoreCase("1")){
                    imageViewPQ.setVisibility(View.VISIBLE);
                }

                if(borrowerPersonalDone.equalsIgnoreCase("1") && borrowerFinanacialDone.equalsIgnoreCase("1") && borrowerEducationDone.equalsIgnoreCase("1")){
                    imageViewborrower.setVisibility(View.VISIBLE);
                }

                if(coborrowerPersonalDone.equalsIgnoreCase("1") && coborrowerFinanacialDone.equalsIgnoreCase("1")){
                    imageViewCoBorrower.setVisibility(View.VISIBLE);
                }

                if(borrowerDocDone.equalsIgnoreCase("1")){
                    imageViewBorrowerDoc.setVisibility(View.VISIBLE);
                }

                if(coBorrowerDocDone.equalsIgnoreCase("1")){
                    imageViewCoborrowerDOc.setVisibility(View.VISIBLE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
