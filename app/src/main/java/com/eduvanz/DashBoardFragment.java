package com.eduvanz;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
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
import com.eduvanz.uploaddocs.UploadActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ch.halcyon.squareprogressbar.SquareProgressBar;
import ch.halcyon.squareprogressbar.utils.PercentStyle;

/**
 * A simple {@link Fragment} subclass.
 */

/** SEEK BAR LINK - https://github.com/jaredrummler/MaterialSpinner */


/** Square Progress Bar https://github.com/mrwonderman/android-square-progressbar **/
public class DashBoardFragment extends Fragment {

    public static Context context;
    public static Fragment mFragment;
    public LinearLayout linearLayoutBorrowerDetail, linearLayoutCoborrowerDetail, linearLayoutDocumentUpload, linearLayoutAccountSettings;
    TextView textView1, textView2, textView3, textView4, textView5, textView6;
    Typeface typefaceFont, typefaceFontBold;

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

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String firstnameshared = sharedPreferences.getString("first_name","null");
        String lastnameshared = sharedPreferences.getString("last_name","null");
        String emailshared = sharedPreferences.getString("user_email","null");
        String userpic = sharedPreferences.getString("user_image","null");
        Log.e(MainApplication.TAG, "firstnameshared: "+firstnameshared + "lastnameshared: "+lastnameshared + "emailshared: "+emailshared + "logged_id: "+sharedPreferences.getString("logged_id","null"));
//        Picasso.with(getApplicationContext()).load(userpic).into(imageView);

        linearLayoutBorrowerDetail = (LinearLayout) view.findViewById(R.id.linearlayout_borrowerdetail);
        linearLayoutCoborrowerDetail = (LinearLayout) view.findViewById(R.id.linearlayout_coborrowerdetail);
        linearLayoutDocumentUpload = (LinearLayout) view.findViewById(R.id.linearlayout_documentupload);
        linearLayoutAccountSettings = (LinearLayout) view.findViewById(R.id.linearlayout_accountsetting);

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
                Intent intent = new Intent(context, UploadActivity.class);
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

        SquareProgressBar squareProgressBar = (SquareProgressBar) view.findViewById(R.id.sprogressbar);
        squareProgressBar.setRoundedCorners(true);
        squareProgressBar.showProgress(true);
        squareProgressBar.setOpacity(true);
        squareProgressBar.setColor("#006b4d");
        squareProgressBar.setImageScaleType(ImageView.ScaleType.MATRIX);
        squareProgressBar.setWidth(7);
        PercentStyle percentStyle = new PercentStyle(Paint.Align.CENTER, 30,	true);
//        percentStyle.setTextColor(Color.parseColor("#C9C9C9"));
        squareProgressBar.setPercentStyle(percentStyle);
//        squareProgressBar.setImageGrayscale(true);


        try {
            squareProgressBar.setImageDrawable(drawableFromUrl(userpic));
        } catch (IOException e) {
            e.printStackTrace();
        }
        squareProgressBar.setProgress(50.0);

        return view;
    }

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

}
