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
    public static String TAG = "GALLERY LOG";

    public static Typeface typefaceFont, typefaceFontBold;

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
