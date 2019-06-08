package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.Util.Paytm;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.pojo.MLoanEmis;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import android.app.AlertDialog;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.VISIBLE;
import static com.eduvanzapplication.Util.Globle.payment_partner;
import static com.eduvanzapplication.Util.Globle.payment_platform;
import static com.eduvanzapplication.R.color.colorGreen;
import static com.eduvanzapplication.R.color.colorRed;
import static com.eduvanzapplication.newUI.MainApplication.TAG;

//9773494235 Test
public class AmortizationFragment extends Fragment implements View.OnClickListener {

    static View view;
    public static Context context;
    public static Fragment mFragment;
    public static ProgressBar progressBarAmort;
    public static ImageView imgpaymentStatus;

    public static List<MLoanEmis> mLoanEmisArrayList;
    public static TextView thumbView;
    public static TextView txtEmiNo1, txtEmiNo2, txtEmiNo3, txtEmiNo4, txtEmiNo5, txtEmiNo6, txtEmiNo7,
            txtEmiNo8, txtEmiNo9, txtEmiNo10, txtEmiNo11, txtEmiNo12, txtEmiNo13, txtEmiNo14, txtEmiNo15,
            txtEmiNo16, txtEmiNo17, txtEmiNo18, txtEmiNo19, txtEmiNo20, txtEmiNo21, txtEmiNo22, txtEmiNo23,
            txtEmiNo24, txtEmiNo25, txtEmiNo26, txtEmiNo27, txtEmiNo28, txtEmiNo29, txtEmiNo30, txtEmiNo31,
            txtEmiNo32, txtEmiNo33, txtEmiNo34, txtEmiNo35, txtEmiNo36, txtEmiNo37, txtEmiNo38, txtEmiNo39,
            txtEmiNo40, txtEmiNo41, txtEmiNo42, txtEmiNo43, txtEmiNo44, txtEmiNo45, txtEmiNo46, txtEmiNo47,
            txtEmiNo48, txtEmiNo49, txtEmiNo50, txtEmiNo51, txtEmiNo52, txtEmiNo53, txtEmiNo54, txtEmiNo55,
            txtEmiNo56, txtEmiNo57, txtEmiNo58, txtEmiNo59, txtEmiNo60, txtEmiNo61, txtEmiNo62, txtEmiNo63,
            txtEmiNo64, txtEmiNo65, txtEmiNo66, txtEmiNo67, txtEmiNo68, txtEmiNo69, txtEmiNo70,
            txtEmiNo71, txtEmiNo72, txtEmiNo73, txtEmiNo74, txtEmiNo75, txtEmiNo76, txtEmiNo77,
            txtEmiNo78, txtEmiNo79, txtEmiNo80, txtEmiNo81, txtEmiNo82, txtEmiNo83, txtEmiNo84,
            txtEmiNo85, txtEmiNo86, txtEmiNo87, txtEmiNo88, txtEmiNo89, txtEmiNo90, txtEmiNo91,
            txtEmiNo92, txtEmiNo93, txtEmiNo94, txtEmiNo95, txtEmiNo96, txtEmiNo97, txtEmiNo98,
            txtEmiNo99, txtEmiNo100;

    public static LinearLayout linAmortBtn1, linAmortBtn2, linAmortBtn3, linAmortBtn4, linAmortBtn5, linAmortBtn6,
            linAmortBtn7, linAmortBtn8, linAmortBtn9, linAmortBtn10, linAmortBtn11, linAmortBtn12, linAmortBtn13, linAmortBtn14,
            linAmortBtn15,linAmortBtn16,linAmortBtn17,linAmortBtn18,linAmortBtn19,linAmortBtn20,linAmortTile;
    public static CardView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10,
            card11, card12, card13, card14, card15, card16, card17, card18, card19, card20,
            card21, card22, card23, card24, card25, card26, card27, card28, card29, card30,
            card31, card32, card33, card34, card35, card36, card37, card38, card39, card40,
            card41, card42, card43, card44, card45, card46, card47, card48, card49, card50,
            card51, card52, card53, card54, card55, card56, card57, card58, card59, card60,
            card61, card62, card63, card64, card65, card66, card67, card68, card69, card70,
            card71, card72, card73, card74, card75, card76, card77, card78, card79, card80,
            card81, card82, card83, card84, card85, card86, card87, card88, card89, card90,
            card91, card92, card93, card94, card95, card96, card97, card98, card99, card100;


    public static TextView txtEmiNo, txtEmiAmount, txtDueBy, txtPaymentDate, txtPaymentStatus, txtBtnText, txtPaidDue, txtOutstandingDue,
            txtNextEmiAmount, txtNextEmiDue, txtNextEmiEndDate, txtOutstandingAmt;
    public static LinearLayout linPayBtn, linAmortFrag, linHasAmort, linNoAmort;

    public static TextView txtAmortNotCreated;
    public static RecyclerView rvAmort;
    public static SeekBar seek_bar;
    public static String emi_num, str_emi_due, str_emi_pay_date, emi_amount1 = "",
            next_emi_date1 = "", emi_end_date1 = "", outstanding_amount1 = "";

    public AmortizationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Drawable getThumb(int progress) {
        thumbView.setText(progress + "");

        thumbView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        Bitmap bitmap = Bitmap.createBitmap(thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        thumbView.layout(0, 0, thumbView.getMeasuredWidth(), thumbView.getMeasuredHeight());
        thumbView.draw(canvas);

        return new BitmapDrawable(getResources(), bitmap);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_amortization, container, false);

        context = getContext();
        mFragment = new AmortizationFragment();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        seek_bar = view.findViewById(R.id.seek_bar);
        rvAmort = view.findViewById(R.id.rvAmort);

        progressBarAmort = view.findViewById(R.id.progressBar_signsubmit);

        txtAmortNotCreated = view.findViewById(R.id.txtAmortNotCreated);

        txtEmiNo = view.findViewById(R.id.txtEmiNo);
        txtEmiAmount = view.findViewById(R.id.txtEmiAmount);
        txtDueBy = view.findViewById(R.id.txtDueBy);
        txtPaymentDate = view.findViewById(R.id.txtPaymentDate);
        txtPaymentStatus = view.findViewById(R.id.txtPaymentStatus);
        imgpaymentStatus = view.findViewById(R.id.imgpaymentStatus);
        txtBtnText = view.findViewById(R.id.txtBtnText);
        linPayBtn = view.findViewById(R.id.linPayBtn);
        linAmortTile = view.findViewById(R.id.linAmortTile);

        txtPaidDue = view.findViewById(R.id.txtPaidDue);
        txtOutstandingDue = view.findViewById(R.id.txtOutstandingDue);

        txtNextEmiAmount = view.findViewById(R.id.txtNextEmiAmount);
        txtNextEmiDue = view.findViewById(R.id.txtNextEmiDue);
        txtNextEmiEndDate = view.findViewById(R.id.txtNextEmiEndDate);
        txtOutstandingAmt = view.findViewById(R.id.txtOutstandingAmt);

        //textview
        txtEmiNo1 = view.findViewById(R.id.txtEmiNo1);
        txtEmiNo2 = view.findViewById(R.id.txtEmiNo2);
        txtEmiNo3 = view.findViewById(R.id.txtEmiNo3);
        txtEmiNo4 = view.findViewById(R.id.txtEmiNo4);
        txtEmiNo5 = view.findViewById(R.id.txtEmiNo5);
        txtEmiNo6 = view.findViewById(R.id.txtEmiNo6);
        txtEmiNo7 = view.findViewById(R.id.txtEmiNo7);
        txtEmiNo8 = view.findViewById(R.id.txtEmiNo8);
        txtEmiNo9 = view.findViewById(R.id.txtEmiNo9);
        txtEmiNo10 = view.findViewById(R.id.txtEmiNo10);
        txtEmiNo11 = view.findViewById(R.id.txtEmiNo11);
        txtEmiNo12 = view.findViewById(R.id.txtEmiNo12);
        txtEmiNo13 = view.findViewById(R.id.txtEmiNo13);
        txtEmiNo14 = view.findViewById(R.id.txtEmiNo14);
        txtEmiNo15 = view.findViewById(R.id.txtEmiNo15);
        txtEmiNo16 = view.findViewById(R.id.txtEmiNo16);
        txtEmiNo17 = view.findViewById(R.id.txtEmiNo17);
        txtEmiNo18 = view.findViewById(R.id.txtEmiNo18);
        txtEmiNo19 = view.findViewById(R.id.txtEmiNo19);
        txtEmiNo20 = view.findViewById(R.id.txtEmiNo20);
        txtEmiNo21 = view.findViewById(R.id.txtEmiNo21);
        txtEmiNo22 = view.findViewById(R.id.txtEmiNo22);
        txtEmiNo23 = view.findViewById(R.id.txtEmiNo23);
        txtEmiNo24 = view.findViewById(R.id.txtEmiNo24);
        txtEmiNo25 = view.findViewById(R.id.txtEmiNo25);
        txtEmiNo26 = view.findViewById(R.id.txtEmiNo26);
        txtEmiNo27 = view.findViewById(R.id.txtEmiNo27);
        txtEmiNo28 = view.findViewById(R.id.txtEmiNo28);
        txtEmiNo29 = view.findViewById(R.id.txtEmiNo29);
        txtEmiNo30 = view.findViewById(R.id.txtEmiNo30);
        txtEmiNo31 = view.findViewById(R.id.txtEmiNo31);
        txtEmiNo32 = view.findViewById(R.id.txtEmiNo32);
        txtEmiNo33 = view.findViewById(R.id.txtEmiNo33);
        txtEmiNo34 = view.findViewById(R.id.txtEmiNo34);
        txtEmiNo35 = view.findViewById(R.id.txtEmiNo35);
        txtEmiNo36 = view.findViewById(R.id.txtEmiNo36);
        txtEmiNo37 = view.findViewById(R.id.txtEmiNo37);
        txtEmiNo38 = view.findViewById(R.id.txtEmiNo38);
        txtEmiNo39 = view.findViewById(R.id.txtEmiNo39);
        txtEmiNo40 = view.findViewById(R.id.txtEmiNo40);
        txtEmiNo41 = view.findViewById(R.id.txtEmiNo41);
        txtEmiNo42 = view.findViewById(R.id.txtEmiNo42);
        txtEmiNo43 = view.findViewById(R.id.txtEmiNo43);
        txtEmiNo44 = view.findViewById(R.id.txtEmiNo44);
        txtEmiNo45 = view.findViewById(R.id.txtEmiNo45);
        txtEmiNo46 = view.findViewById(R.id.txtEmiNo46);
        txtEmiNo47 = view.findViewById(R.id.txtEmiNo47);
        txtEmiNo48 = view.findViewById(R.id.txtEmiNo48);
        txtEmiNo49 = view.findViewById(R.id.txtEmiNo49);
        txtEmiNo50 = view.findViewById(R.id.txtEmiNo50);
        txtEmiNo51 = view.findViewById(R.id.txtEmiNo51);
        txtEmiNo52 = view.findViewById(R.id.txtEmiNo52);
        txtEmiNo53 = view.findViewById(R.id.txtEmiNo53);
        txtEmiNo54 = view.findViewById(R.id.txtEmiNo54);
        txtEmiNo55 = view.findViewById(R.id.txtEmiNo55);
        txtEmiNo56 = view.findViewById(R.id.txtEmiNo56);
        txtEmiNo57 = view.findViewById(R.id.txtEmiNo57);
        txtEmiNo58 = view.findViewById(R.id.txtEmiNo58);
        txtEmiNo59 = view.findViewById(R.id.txtEmiNo59);
        txtEmiNo60 = view.findViewById(R.id.txtEmiNo60);
        txtEmiNo61 = view.findViewById(R.id.txtEmiNo61);
        txtEmiNo62 = view.findViewById(R.id.txtEmiNo62);
        txtEmiNo63 = view.findViewById(R.id.txtEmiNo63);
        txtEmiNo64 = view.findViewById(R.id.txtEmiNo64);
        txtEmiNo65 = view.findViewById(R.id.txtEmiNo65);
        txtEmiNo66 = view.findViewById(R.id.txtEmiNo66);
        txtEmiNo67 = view.findViewById(R.id.txtEmiNo67);
        txtEmiNo68 = view.findViewById(R.id.txtEmiNo68);
        txtEmiNo69 = view.findViewById(R.id.txtEmiNo69);
        txtEmiNo70 = view.findViewById(R.id.txtEmiNo70);

        txtEmiNo71 = view.findViewById(R.id.txtEmiNo71);
        txtEmiNo72 = view.findViewById(R.id.txtEmiNo72);
        txtEmiNo73 = view.findViewById(R.id.txtEmiNo73);
        txtEmiNo74 = view.findViewById(R.id.txtEmiNo74);
        txtEmiNo75 = view.findViewById(R.id.txtEmiNo75);
        txtEmiNo76 = view.findViewById(R.id.txtEmiNo76);
        txtEmiNo77 = view.findViewById(R.id.txtEmiNo77);
        txtEmiNo78 = view.findViewById(R.id.txtEmiNo78);
        txtEmiNo79 = view.findViewById(R.id.txtEmiNo79);
        txtEmiNo80 = view.findViewById(R.id.txtEmiNo80);


        txtEmiNo81 = view.findViewById(R.id.txtEmiNo81);
        txtEmiNo82 = view.findViewById(R.id.txtEmiNo82);
        txtEmiNo83 = view.findViewById(R.id.txtEmiNo83);
        txtEmiNo84 = view.findViewById(R.id.txtEmiNo84);
        txtEmiNo85 = view.findViewById(R.id.txtEmiNo85);
        txtEmiNo86 = view.findViewById(R.id.txtEmiNo86);
        txtEmiNo87 = view.findViewById(R.id.txtEmiNo87);
        txtEmiNo88 = view.findViewById(R.id.txtEmiNo88);
        txtEmiNo89 = view.findViewById(R.id.txtEmiNo89);
        txtEmiNo90 = view.findViewById(R.id.txtEmiNo90);


        txtEmiNo91 = view.findViewById(R.id.txtEmiNo91);
        txtEmiNo92 = view.findViewById(R.id.txtEmiNo92);
        txtEmiNo93 = view.findViewById(R.id.txtEmiNo93);
        txtEmiNo94 = view.findViewById(R.id.txtEmiNo94);
        txtEmiNo95 = view.findViewById(R.id.txtEmiNo95);
        txtEmiNo96 = view.findViewById(R.id.txtEmiNo96);
        txtEmiNo97 = view.findViewById(R.id.txtEmiNo97);
        txtEmiNo98 = view.findViewById(R.id.txtEmiNo98);
        txtEmiNo99 = view.findViewById(R.id.txtEmiNo99);
        txtEmiNo100 = view.findViewById(R.id.txtEmiNo100);

        //card view

        card1 = view.findViewById(R.id.card1);
        card2 = view.findViewById(R.id.card2);
        card3 = view.findViewById(R.id.card3);
        card4 = view.findViewById(R.id.card4);
        card5 = view.findViewById(R.id.card5);
        card6 = view.findViewById(R.id.card6);
        card7 = view.findViewById(R.id.card7);
        card8 = view.findViewById(R.id.card8);
        card9 = view.findViewById(R.id.card9);
        card10 = view.findViewById(R.id.card10);
        card11 = view.findViewById(R.id.card11);
        card12 = view.findViewById(R.id.card12);
        card13 = view.findViewById(R.id.card13);
        card14 = view.findViewById(R.id.card14);
        card15 = view.findViewById(R.id.card15);
        card16 = view.findViewById(R.id.card16);
        card17 = view.findViewById(R.id.card17);
        card18 = view.findViewById(R.id.card18);
        card19 = view.findViewById(R.id.card19);
        card20 = view.findViewById(R.id.card20);
        card21 = view.findViewById(R.id.card21);
        card22 = view.findViewById(R.id.card22);
        card23 = view.findViewById(R.id.card23);
        card24 = view.findViewById(R.id.card24);
        card25 = view.findViewById(R.id.card25);
        card26 = view.findViewById(R.id.card26);
        card27 = view.findViewById(R.id.card27);
        card28 = view.findViewById(R.id.card28);
        card29 = view.findViewById(R.id.card29);
        card30 = view.findViewById(R.id.card30);
        card31 = view.findViewById(R.id.card31);
        card32 = view.findViewById(R.id.card32);
        card33 = view.findViewById(R.id.card33);
        card34 = view.findViewById(R.id.card34);
        card35 = view.findViewById(R.id.card35);
        card36 = view.findViewById(R.id.card36);
        card37 = view.findViewById(R.id.card37);
        card38 = view.findViewById(R.id.card38);
        card39 = view.findViewById(R.id.card39);
        card40 = view.findViewById(R.id.card40);
        card41 = view.findViewById(R.id.card41);
        card42 = view.findViewById(R.id.card42);
        card43 = view.findViewById(R.id.card43);
        card44 = view.findViewById(R.id.card44);
        card45 = view.findViewById(R.id.card45);
        card46 = view.findViewById(R.id.card46);
        card47 = view.findViewById(R.id.card47);
        card48 = view.findViewById(R.id.card48);
        card49 = view.findViewById(R.id.card49);
        card50 = view.findViewById(R.id.card50);
        card51 = view.findViewById(R.id.card51);
        card52 = view.findViewById(R.id.card52);
        card53 = view.findViewById(R.id.card53);
        card54 = view.findViewById(R.id.card54);
        card55 = view.findViewById(R.id.card55);
        card56 = view.findViewById(R.id.card56);
        card57 = view.findViewById(R.id.card57);
        card58 = view.findViewById(R.id.card58);
        card59 = view.findViewById(R.id.card59);
        card60 = view.findViewById(R.id.card60);
        card61 = view.findViewById(R.id.card61);
        card62 = view.findViewById(R.id.card62);
        card63 = view.findViewById(R.id.card63);
        card64 = view.findViewById(R.id.card64);
        card65 = view.findViewById(R.id.card65);
        card66 = view.findViewById(R.id.card66);
        card67 = view.findViewById(R.id.card67);
        card68 = view.findViewById(R.id.card68);
        card69 = view.findViewById(R.id.card69);
        card70 = view.findViewById(R.id.card70);

        card71 = view.findViewById(R.id.card71);
        card72 = view.findViewById(R.id.card72);
        card73 = view.findViewById(R.id.card73);
        card74 = view.findViewById(R.id.card74);
        card75 = view.findViewById(R.id.card75);
        card76 = view.findViewById(R.id.card76);
        card77 = view.findViewById(R.id.card77);
        card78 = view.findViewById(R.id.card78);
        card79 = view.findViewById(R.id.card79);
        card80 = view.findViewById(R.id.card80);

        card81 = view.findViewById(R.id.card81);
        card82 = view.findViewById(R.id.card82);
        card83 = view.findViewById(R.id.card83);
        card84 = view.findViewById(R.id.card84);
        card85 = view.findViewById(R.id.card85);
        card86 = view.findViewById(R.id.card86);
        card87 = view.findViewById(R.id.card87);
        card88 = view.findViewById(R.id.card88);
        card89 = view.findViewById(R.id.card89);
        card90 = view.findViewById(R.id.card90);


        card91 = view.findViewById(R.id.card91);
        card92 = view.findViewById(R.id.card92);
        card93 = view.findViewById(R.id.card93);
        card94 = view.findViewById(R.id.card94);
        card95 = view.findViewById(R.id.card95);
        card96 = view.findViewById(R.id.card96);
        card97 = view.findViewById(R.id.card97);
        card98 = view.findViewById(R.id.card98);
        card99 = view.findViewById(R.id.card99);
        card100 = view.findViewById(R.id.card100);

        //linear layout
        linHasAmort = view.findViewById(R.id.linHasAmort);
        linNoAmort = view.findViewById(R.id.linNoAmort);

        linAmortFrag = view.findViewById(R.id.linAmortFrag);

        linAmortBtn1 = view.findViewById(R.id.linAmortBtn1);
        linAmortBtn2 = view.findViewById(R.id.linAmortBtn2);
        linAmortBtn3 = view.findViewById(R.id.linAmortBtn3);
        linAmortBtn4 = view.findViewById(R.id.linAmortBtn4);
        linAmortBtn5 = view.findViewById(R.id.linAmortBtn5);
        linAmortBtn6 = view.findViewById(R.id.linAmortBtn6);
        linAmortBtn7 = view.findViewById(R.id.linAmortBtn7);
        linAmortBtn8 = view.findViewById(R.id.linAmortBtn8);
        linAmortBtn9 = view.findViewById(R.id.linAmortBtn9);
        linAmortBtn10 = view.findViewById(R.id.linAmortBtn10);
        linAmortBtn11 = view.findViewById(R.id.linAmortBtn11);
        linAmortBtn12 = view.findViewById(R.id.linAmortBtn12);
        linAmortBtn13 = view.findViewById(R.id.linAmortBtn13);
        linAmortBtn14 = view.findViewById(R.id.linAmortBtn14);

        linAmortBtn15 = view.findViewById(R.id.linAmortBtn15);
        linAmortBtn16 = view.findViewById(R.id.linAmortBtn16);
        linAmortBtn17 = view.findViewById(R.id.linAmortBtn17);
        linAmortBtn18 = view.findViewById(R.id.linAmortBtn18);
        linAmortBtn19 = view.findViewById(R.id.linAmortBtn19);
        linAmortBtn20 = view.findViewById(R.id.linAmortBtn20);

        linPayBtn.setOnClickListener(this);
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        card6.setOnClickListener(this);
        card7.setOnClickListener(this);
        card8.setOnClickListener(this);
        card9.setOnClickListener(this);
        card10.setOnClickListener(this);
        card11.setOnClickListener(this);
        card12.setOnClickListener(this);
        card13.setOnClickListener(this);
        card14.setOnClickListener(this);
        card15.setOnClickListener(this);
        card16.setOnClickListener(this);
        card17.setOnClickListener(this);
        card18.setOnClickListener(this);
        card19.setOnClickListener(this);
        card20.setOnClickListener(this);
        card21.setOnClickListener(this);
        card22.setOnClickListener(this);
        card23.setOnClickListener(this);
        card24.setOnClickListener(this);
        card25.setOnClickListener(this);
        card26.setOnClickListener(this);
        card27.setOnClickListener(this);
        card28.setOnClickListener(this);
        card29.setOnClickListener(this);
        card30.setOnClickListener(this);
        card31.setOnClickListener(this);
        card32.setOnClickListener(this);
        card33.setOnClickListener(this);
        card34.setOnClickListener(this);
        card35.setOnClickListener(this);
        card36.setOnClickListener(this);
        card37.setOnClickListener(this);
        card38.setOnClickListener(this);
        card39.setOnClickListener(this);
        card40.setOnClickListener(this);
        card41.setOnClickListener(this);
        card42.setOnClickListener(this);
        card43.setOnClickListener(this);
        card44.setOnClickListener(this);
        card45.setOnClickListener(this);
        card46.setOnClickListener(this);
        card47.setOnClickListener(this);
        card48.setOnClickListener(this);
        card49.setOnClickListener(this);
        card50.setOnClickListener(this);
        card51.setOnClickListener(this);
        card52.setOnClickListener(this);
        card53.setOnClickListener(this);
        card54.setOnClickListener(this);
        card55.setOnClickListener(this);
        card56.setOnClickListener(this);
        card57.setOnClickListener(this);
        card58.setOnClickListener(this);
        card59.setOnClickListener(this);
        card60.setOnClickListener(this);
        card61.setOnClickListener(this);
        card62.setOnClickListener(this);
        card63.setOnClickListener(this);
        card64.setOnClickListener(this);
        card65.setOnClickListener(this);
        card66.setOnClickListener(this);
        card67.setOnClickListener(this);
        card68.setOnClickListener(this);
        card69.setOnClickListener(this);
        card70.setOnClickListener(this);

        card71.setOnClickListener(this);
        card72.setOnClickListener(this);
        card73.setOnClickListener(this);
        card74.setOnClickListener(this);
        card75.setOnClickListener(this);
        card76.setOnClickListener(this);
        card77.setOnClickListener(this);
        card78.setOnClickListener(this);
        card79.setOnClickListener(this);
        card80.setOnClickListener(this);


        card81.setOnClickListener(this);
        card82.setOnClickListener(this);
        card83.setOnClickListener(this);
        card84.setOnClickListener(this);
        card85.setOnClickListener(this);
        card86.setOnClickListener(this);
        card87.setOnClickListener(this);
        card88.setOnClickListener(this);
        card89.setOnClickListener(this);
        card90.setOnClickListener(this);


        card91.setOnClickListener(this);
        card92.setOnClickListener(this);
        card93.setOnClickListener(this);
        card94.setOnClickListener(this);
        card95.setOnClickListener(this);
        card96.setOnClickListener(this);
        card97.setOnClickListener(this);
        card98.setOnClickListener(this);
        card99.setOnClickListener(this);
        card100.setOnClickListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAmort.setLayoutManager(linearLayoutManager);

        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        getAmortDetails();
        return view;

    }

    public void getAmortDetails() {
        try {
            String url = MainActivity.mainUrl + "dashboard/ammortisation";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", MainActivity.lead_id);
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
            } else {
                VolleyCall volleyCall = new VolleyCall();
                volleyCall.sendRequest(context, url, null, mFragment, "getAmortDetails", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(context, className, name, errorMsg, errorMsgDetails, errorLine);
        }
    }

    public void setAmortDetails(JSONObject jsonDataO) {
        Log.e(TAG, "setProfileDashbBoardStatus: " + jsonDataO);
        try {
//            progressDialog.dismiss();
            mLoanEmisArrayList = new ArrayList<>();
            if (jsonDataO.getInt("status") == 1) {
                try {
                    linHasAmort.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    linNoAmort.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    progressBarAmort.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String message = jsonDataO.getString("message");

                if (!jsonDataO.get("loanData").equals(null)) {

                    JSONObject jsonloanDataDetails = jsonDataO.getJSONObject("loanData");

                    if (jsonloanDataDetails.has("application_loan_id")) {
                        if (!jsonloanDataDetails.getString("emi_amount").toString().equals("null"))
                            emi_amount1 = jsonloanDataDetails.getString("emi_amount");
                        if (!jsonloanDataDetails.getString("next_emi_date").toString().equals("null"))
                            next_emi_date1 = jsonloanDataDetails.getString("next_emi_date");
                        if (!jsonloanDataDetails.getString("emi_end_date").toString().equals("null"))
                            emi_end_date1 = jsonloanDataDetails.getString("emi_end_date");
                        if (!jsonloanDataDetails.getString("outstanding_amount").toString().equals("null"))
                            outstanding_amount1 = jsonloanDataDetails.getString("outstanding_amount");
                    }
                }
                JSONArray jsonArray1 = jsonDataO.getJSONArray("loanEmiDetails");

                for (int i = 0; i < jsonArray1.length(); i++) {
                    MLoanEmis mLoanEmis = new MLoanEmis();
                    JSONObject jsonEmiDetails = jsonArray1.getJSONObject(i);
                    try {
                        if (!jsonEmiDetails.getString("loan_emi_id").toString().equals("null"))
                            mLoanEmis.loan_emi_id = jsonEmiDetails.getString("loan_emi_id");
                        if (!jsonEmiDetails.getString("loan_id").toString().equals("null"))
                            mLoanEmis.loan_id = jsonEmiDetails.getString("loan_id");
                        if (!jsonEmiDetails.getString("emi_no").toString().equals("null"))
                            mLoanEmis.emi_no = emi_num = jsonEmiDetails.getString("emi_no");
                        if (!jsonEmiDetails.getString("dpd").toString().equals("null"))
                            mLoanEmis.dpd = jsonEmiDetails.getString("dpd");
                        if (!jsonEmiDetails.getString("emi_amount").toString().equals("null"))
                            mLoanEmis.emi_amount = jsonEmiDetails.getString("emi_amount");
                        if (!jsonEmiDetails.getString("principle_amount").toString().equals("null"))
                            mLoanEmis.principle_amount = jsonEmiDetails.getString("principle_amount");
                        if (!jsonEmiDetails.getString("interest_amount").toString().equals("null"))
                            mLoanEmis.interest_amount = jsonEmiDetails.getString("interest_amount");
                        if (!jsonEmiDetails.getString("balance").toString().equals("null"))
                            mLoanEmis.balance = jsonEmiDetails.getString("balance");
                        if (!jsonEmiDetails.getString("proposed_payment_date").toString().equals("null"))
                            mLoanEmis.proposed_payment_date = str_emi_due = jsonEmiDetails.getString("proposed_payment_date");
                        if (!jsonEmiDetails.getString("actual_payment_date").toString().equals("null"))
                            mLoanEmis.actual_payment_date = jsonEmiDetails.getString("actual_payment_date");
                        if (!jsonEmiDetails.getString("final_payment_date").toString().equals("null"))
                            mLoanEmis.final_payment_date = str_emi_pay_date = jsonEmiDetails.getString("final_payment_date");
                        if (!jsonEmiDetails.getString("total_amount_paid").toString().equals("null"))
                            mLoanEmis.total_amount_paid = jsonEmiDetails.getString("total_amount_paid");
                        if (!jsonEmiDetails.getString("status").toString().equals("null"))
                            mLoanEmis.status = jsonEmiDetails.getString("status");
                        if (!jsonEmiDetails.getString("comments").toString().equals("null"))
                            mLoanEmis.statusMessage = jsonEmiDetails.getString("comments");
                        if (!jsonEmiDetails.getString("statusMessage").toString().equals("null"))
                            mLoanEmis.statusMessage = jsonEmiDetails.getString("statusMessage");

                    } catch (JSONException e) {
                    }
                    mLoanEmisArrayList.add(mLoanEmis);
                }

                int amortlistsize = mLoanEmisArrayList.size();
                float OutstandingDue = 0, TotalDue = 0, PaidDue = 0;
                int NextEmiPos = 0;
                for (int k = 0; k < amortlistsize; k++) {

                    if (mLoanEmisArrayList.get(k).status.toString().equals("0")) {
                        OutstandingDue = OutstandingDue + Float.parseFloat(mLoanEmisArrayList.get(k).emi_amount);
                        if (NextEmiPos == 0) {
                            NextEmiPos = k;
                        }
                    } else {
                        PaidDue = PaidDue + Float.parseFloat(mLoanEmisArrayList.get(k).emi_amount);
                    }
                    TotalDue = TotalDue + Float.parseFloat(mLoanEmisArrayList.get(k).emi_amount);
                }

                seek_bar.setProgress((int) (PaidDue / TotalDue * 100));

                txtPaidDue.setText(" " + String.valueOf(PaidDue));
//                txtOutstandingDue.setText(" " + String.valueOf(OutstandingDue));
                txtOutstandingDue.setText(" " + outstanding_amount1);

                txtNextEmiAmount.setText(" "+emi_amount1);
                if(next_emi_date1.length() > 5) {

                     String[] split = next_emi_date1.split(" ");
                     String day = split[0].toString();
                     String month = split[1].toString().substring(0,3);
                     String year = split[2];
                    txtNextEmiDue.setText(day+" "+month+" "+year);

                }else{
                    txtNextEmiDue.setText(next_emi_date1);
                }

                if(emi_end_date1.length() > 5) {

                    String[] split = emi_end_date1.split(" ");
                    String day = split[0].toString();
                    String month = split[1].toString().substring(0,3);
                    String year = split[2];
                    txtNextEmiEndDate.setText(day+" "+month+" "+year);

                }else{
                    txtNextEmiEndDate.setText(emi_end_date1);
                }

                txtOutstandingAmt.setText(" " + outstanding_amount1);

                for (int j = 0; j < amortlistsize; j++) {
                    switch (j) {

                        case 0:
                            linAmortBtn1.setVisibility(View.VISIBLE);
                            txtEmiNo1.setVisibility(View.VISIBLE);
                            card1.setVisibility(View.VISIBLE);
                            txtEmiNo1.setText("0" + mLoanEmisArrayList.get(j).emi_no);
                            card1.setTag(j);
                            break;
                        case 1:
                            linAmortBtn1.setVisibility(View.VISIBLE);
                            txtEmiNo2.setVisibility(View.VISIBLE);
                            card2.setVisibility(View.VISIBLE);
                            txtEmiNo2.setText("0" + mLoanEmisArrayList.get(j).emi_no);
                            card2.setTag(j);
                            break;
                        case 2:
                            linAmortBtn1.setVisibility(View.VISIBLE);
                            txtEmiNo3.setVisibility(View.VISIBLE);
                            card3.setVisibility(View.VISIBLE);
                            txtEmiNo3.setText("0" + mLoanEmisArrayList.get(j).emi_no);
                            card3.setTag(j);
                            break;
                        case 3:
                            linAmortBtn1.setVisibility(View.VISIBLE);
                            txtEmiNo4.setVisibility(View.VISIBLE);
                            card4.setVisibility(View.VISIBLE);
                            txtEmiNo4.setText("0" + mLoanEmisArrayList.get(j).emi_no);
                            card4.setTag(j);
                            break;
                        case 4:
                            linAmortBtn1.setVisibility(View.VISIBLE);
                            txtEmiNo5.setVisibility(View.VISIBLE);
                            card5.setVisibility(View.VISIBLE);
                            txtEmiNo5.setText("0" + mLoanEmisArrayList.get(j).emi_no);
                            card5.setTag(j);
                            break;

                        case 5:
                            linAmortBtn2.setVisibility(View.VISIBLE);
                            txtEmiNo6.setVisibility(View.VISIBLE);
                            card6.setVisibility(View.VISIBLE);
                            txtEmiNo6.setText("0" + mLoanEmisArrayList.get(j).emi_no);
                            card6.setTag(j);
                            break;
                        case 6:
                            linAmortBtn2.setVisibility(View.VISIBLE);
                            txtEmiNo7.setVisibility(View.VISIBLE);
                            card7.setVisibility(View.VISIBLE);
                            txtEmiNo7.setText("0" + mLoanEmisArrayList.get(j).emi_no);
                            card7.setTag(j);
                            break;
                        case 7:
                            linAmortBtn2.setVisibility(View.VISIBLE);
                            txtEmiNo8.setVisibility(View.VISIBLE);
                            card8.setVisibility(View.VISIBLE);
                            txtEmiNo8.setText("0" + mLoanEmisArrayList.get(j).emi_no);
                            card8.setTag(j);
                            break;
                        case 8:
                            linAmortBtn2.setVisibility(View.VISIBLE);
                            txtEmiNo9.setVisibility(View.VISIBLE);
                            card9.setVisibility(View.VISIBLE);
                            txtEmiNo9.setText("0" + mLoanEmisArrayList.get(j).emi_no);
                            card9.setTag(j);
                            break;
                        case 9:
                            linAmortBtn2.setVisibility(View.VISIBLE);
                            txtEmiNo10.setVisibility(View.VISIBLE);
                            card10.setVisibility(View.VISIBLE);
                            txtEmiNo10.setText("0" + mLoanEmisArrayList.get(j).emi_no);
                            card10.setTag(j);
                            break;

                        case 10:
                            linAmortBtn3.setVisibility(View.VISIBLE);
                            txtEmiNo11.setVisibility(View.VISIBLE);
                            card11.setVisibility(View.VISIBLE);
                            txtEmiNo11.setText(mLoanEmisArrayList.get(j).emi_no);
                            card11.setTag(j);
                            break;
                        case 11:
                            linAmortBtn3.setVisibility(View.VISIBLE);
                            txtEmiNo12.setVisibility(View.VISIBLE);
                            card12.setVisibility(View.VISIBLE);
                            txtEmiNo12.setText(mLoanEmisArrayList.get(j).emi_no);
                            card12.setTag(j);
                            break;
                        case 12:
                            linAmortBtn3.setVisibility(View.VISIBLE);
                            txtEmiNo13.setVisibility(View.VISIBLE);
                            card13.setVisibility(View.VISIBLE);
                            txtEmiNo13.setText(mLoanEmisArrayList.get(j).emi_no);
                            card13.setTag(j);
                            break;
                        case 13:
                            linAmortBtn3.setVisibility(View.VISIBLE);
                            txtEmiNo14.setVisibility(View.VISIBLE);
                            card14.setVisibility(View.VISIBLE);
                            txtEmiNo14.setText(mLoanEmisArrayList.get(j).emi_no);
                            card14.setTag(j);
                            break;
                        case 14:
                            linAmortBtn1.setVisibility(View.VISIBLE);
                            txtEmiNo15.setVisibility(View.VISIBLE);
                            card15.setVisibility(View.VISIBLE);
                            txtEmiNo15.setText(mLoanEmisArrayList.get(j).emi_no);
                            card15.setTag(j);
                            break;

                        case 15:
                            linAmortBtn4.setVisibility(View.VISIBLE);
                            txtEmiNo16.setVisibility(View.VISIBLE);
                            card16.setVisibility(View.VISIBLE);
                            txtEmiNo16.setText(mLoanEmisArrayList.get(j).emi_no);
                            card16.setTag(j);
                            break;
                        case 16:
                            linAmortBtn4.setVisibility(View.VISIBLE);
                            txtEmiNo17.setVisibility(View.VISIBLE);
                            card17.setVisibility(View.VISIBLE);
                            txtEmiNo17.setText(mLoanEmisArrayList.get(j).emi_no);
                            card17.setTag(j);
                            break;
                        case 17:
                            linAmortBtn4.setVisibility(View.VISIBLE);
                            txtEmiNo18.setVisibility(View.VISIBLE);
                            card18.setVisibility(View.VISIBLE);
                            txtEmiNo18.setText(mLoanEmisArrayList.get(j).emi_no);
                            card18.setTag(j);
                            break;
                        case 18:
                            linAmortBtn4.setVisibility(View.VISIBLE);
                            txtEmiNo19.setVisibility(View.VISIBLE);
                            card19.setVisibility(View.VISIBLE);
                            txtEmiNo19.setText(mLoanEmisArrayList.get(j).emi_no);
                            card19.setTag(j);
                            break;
                        case 19:
                            linAmortBtn4.setVisibility(View.VISIBLE);
                            txtEmiNo20.setVisibility(View.VISIBLE);
                            card20.setVisibility(View.VISIBLE);
                            txtEmiNo20.setText(mLoanEmisArrayList.get(j).emi_no);
                            card20.setTag(j);
                            break;

                        case 20:
                            linAmortBtn5.setVisibility(View.VISIBLE);
                            txtEmiNo21.setVisibility(View.VISIBLE);
                            card21.setVisibility(View.VISIBLE);
                            txtEmiNo21.setText(mLoanEmisArrayList.get(j).emi_no);
                            card21.setTag(j);
                            break;
                        case 21:
                            linAmortBtn5.setVisibility(View.VISIBLE);
                            txtEmiNo22.setVisibility(View.VISIBLE);
                            card22.setVisibility(View.VISIBLE);
                            txtEmiNo22.setText(mLoanEmisArrayList.get(j).emi_no);
                            card22.setTag(j);
                            break;
                        case 22:
                            linAmortBtn5.setVisibility(View.VISIBLE);
                            txtEmiNo23.setVisibility(View.VISIBLE);
                            card23.setVisibility(View.VISIBLE);
                            txtEmiNo23.setText(mLoanEmisArrayList.get(j).emi_no);
                            card23.setTag(j);
                            break;
                        case 23:
                            linAmortBtn5.setVisibility(View.VISIBLE);
                            txtEmiNo24.setVisibility(View.VISIBLE);
                            card24.setVisibility(View.VISIBLE);
                            txtEmiNo24.setText(mLoanEmisArrayList.get(j).emi_no);
                            card24.setTag(j);
                            break;
                        case 24:
                            linAmortBtn5.setVisibility(View.VISIBLE);
                            txtEmiNo25.setVisibility(View.VISIBLE);
                            card25.setVisibility(View.VISIBLE);
                            txtEmiNo25.setText(mLoanEmisArrayList.get(j).emi_no);
                            card25.setTag(j);
                            break;

                        case 25:
                            linAmortBtn6.setVisibility(View.VISIBLE);
                            txtEmiNo26.setVisibility(View.VISIBLE);
                            card26.setVisibility(View.VISIBLE);
                            txtEmiNo26.setText(mLoanEmisArrayList.get(j).emi_no);
                            card26.setTag(j);
                            break;
                        case 26:
                            linAmortBtn6.setVisibility(View.VISIBLE);
                            txtEmiNo27.setVisibility(View.VISIBLE);
                            card27.setVisibility(View.VISIBLE);
                            txtEmiNo27.setText(mLoanEmisArrayList.get(j).emi_no);
                            card27.setTag(j);
                            break;
                        case 27:
                            linAmortBtn6.setVisibility(View.VISIBLE);
                            txtEmiNo28.setVisibility(View.VISIBLE);
                            card28.setVisibility(View.VISIBLE);
                            txtEmiNo28.setText(mLoanEmisArrayList.get(j).emi_no);
                            card28.setTag(j);
                            break;
                        case 28:
                            linAmortBtn6.setVisibility(View.VISIBLE);
                            txtEmiNo29.setVisibility(View.VISIBLE);
                            card29.setVisibility(View.VISIBLE);
                            txtEmiNo29.setText(mLoanEmisArrayList.get(j).emi_no);
                            card29.setTag(j);
                            break;
                        case 29:
                            linAmortBtn6.setVisibility(View.VISIBLE);
                            txtEmiNo30.setVisibility(View.VISIBLE);
                            card30.setVisibility(View.VISIBLE);
                            txtEmiNo30.setText(mLoanEmisArrayList.get(j).emi_no);
                            card30.setTag(j);
                            break;

                        case 30:
                            linAmortBtn7.setVisibility(View.VISIBLE);
                            txtEmiNo31.setVisibility(View.VISIBLE);
                            card31.setVisibility(View.VISIBLE);
                            txtEmiNo31.setText(mLoanEmisArrayList.get(j).emi_no);
                            card31.setTag(j);
                            break;
                        case 31:
                            linAmortBtn7.setVisibility(View.VISIBLE);
                            txtEmiNo32.setVisibility(View.VISIBLE);
                            card32.setVisibility(View.VISIBLE);
                            txtEmiNo32.setText(mLoanEmisArrayList.get(j).emi_no);
                            card32.setTag(j);
                            break;
                        case 32:
                            linAmortBtn7.setVisibility(View.VISIBLE);
                            txtEmiNo33.setVisibility(View.VISIBLE);
                            card33.setVisibility(View.VISIBLE);
                            txtEmiNo33.setText(mLoanEmisArrayList.get(j).emi_no);
                            card33.setTag(j);
                            break;
                        case 33:
                            linAmortBtn7.setVisibility(View.VISIBLE);
                            txtEmiNo34.setVisibility(View.VISIBLE);
                            card34.setVisibility(View.VISIBLE);
                            txtEmiNo34.setText(mLoanEmisArrayList.get(j).emi_no);
                            card34.setTag(j);
                            break;
                        case 34:
                            linAmortBtn7.setVisibility(View.VISIBLE);
                            txtEmiNo35.setVisibility(View.VISIBLE);
                            card35.setVisibility(View.VISIBLE);
                            txtEmiNo35.setText(mLoanEmisArrayList.get(j).emi_no);
                            card35.setTag(j);
                            break;

                        case 35:
                            linAmortBtn8.setVisibility(View.VISIBLE);
                            txtEmiNo36.setVisibility(View.VISIBLE);
                            card36.setVisibility(View.VISIBLE);
                            txtEmiNo36.setText(mLoanEmisArrayList.get(j).emi_no);
                            card36.setTag(j);
                            break;
                        case 36:
                            linAmortBtn8.setVisibility(View.VISIBLE);
                            txtEmiNo37.setVisibility(View.VISIBLE);
                            card37.setVisibility(View.VISIBLE);
                            txtEmiNo37.setText(mLoanEmisArrayList.get(j).emi_no);
                            card37.setTag(j);
                            break;
                        case 37:
                            linAmortBtn8.setVisibility(View.VISIBLE);
                            txtEmiNo38.setVisibility(View.VISIBLE);
                            card38.setVisibility(View.VISIBLE);
                            txtEmiNo38.setText(mLoanEmisArrayList.get(j).emi_no);
                            card38.setTag(j);
                            break;
                        case 38:
                            linAmortBtn8.setVisibility(View.VISIBLE);
                            txtEmiNo39.setVisibility(View.VISIBLE);
                            card39.setVisibility(View.VISIBLE);
                            txtEmiNo39.setText(mLoanEmisArrayList.get(j).emi_no);
                            card39.setTag(j);
                            break;
                        case 39:
                            linAmortBtn8.setVisibility(View.VISIBLE);
                            txtEmiNo40.setVisibility(View.VISIBLE);
                            card40.setVisibility(View.VISIBLE);
                            txtEmiNo40.setText(mLoanEmisArrayList.get(j).emi_no);
                            card40.setTag(j);
                            break;

                        case 40:
                            linAmortBtn9.setVisibility(View.VISIBLE);
                            txtEmiNo41.setVisibility(View.VISIBLE);
                            card41.setVisibility(View.VISIBLE);
                            txtEmiNo41.setText(mLoanEmisArrayList.get(j).emi_no);
                            card41.setTag(j);
                            break;
                        case 41:
                            linAmortBtn9.setVisibility(View.VISIBLE);
                            txtEmiNo42.setVisibility(View.VISIBLE);
                            card42.setVisibility(View.VISIBLE);
                            txtEmiNo42.setText(mLoanEmisArrayList.get(j).emi_no);
                            card42.setTag(j);
                            break;
                        case 42:
                            linAmortBtn9.setVisibility(View.VISIBLE);
                            txtEmiNo43.setVisibility(View.VISIBLE);
                            card43.setVisibility(View.VISIBLE);
                            txtEmiNo43.setText(mLoanEmisArrayList.get(j).emi_no);
                            card43.setTag(j);
                            break;
                        case 43:
                            linAmortBtn9.setVisibility(View.VISIBLE);
                            txtEmiNo44.setVisibility(View.VISIBLE);
                            card44.setVisibility(View.VISIBLE);
                            txtEmiNo44.setText(mLoanEmisArrayList.get(j).emi_no);
                            card44.setTag(j);
                            break;
                        case 44:
                            linAmortBtn9.setVisibility(View.VISIBLE);
                            txtEmiNo45.setVisibility(View.VISIBLE);
                            card45.setVisibility(View.VISIBLE);
                            txtEmiNo45.setText(mLoanEmisArrayList.get(j).emi_no);
                            card45.setTag(j);
                            break;

                        case 45:
                            linAmortBtn10.setVisibility(View.VISIBLE);
                            txtEmiNo46.setVisibility(View.VISIBLE);
                            card46.setVisibility(View.VISIBLE);
                            txtEmiNo46.setText(mLoanEmisArrayList.get(j).emi_no);
                            card46.setTag(j);
                            break;
                        case 46:
                            linAmortBtn10.setVisibility(View.VISIBLE);
                            txtEmiNo47.setVisibility(View.VISIBLE);
                            card47.setVisibility(View.VISIBLE);
                            txtEmiNo47.setText(mLoanEmisArrayList.get(j).emi_no);
                            card47.setTag(j);
                            break;
                        case 47:
                            linAmortBtn10.setVisibility(View.VISIBLE);
                            txtEmiNo48.setVisibility(View.VISIBLE);
                            card48.setVisibility(View.VISIBLE);
                            txtEmiNo48.setText(mLoanEmisArrayList.get(j).emi_no);
                            card48.setTag(j);
                            break;
                        case 48:
                            linAmortBtn10.setVisibility(View.VISIBLE);
                            txtEmiNo49.setVisibility(View.VISIBLE);
                            card49.setVisibility(View.VISIBLE);
                            txtEmiNo49.setText(mLoanEmisArrayList.get(j).emi_no);
                            card49.setTag(j);
                            break;
                        case 49:
                            linAmortBtn10.setVisibility(View.VISIBLE);
                            txtEmiNo50.setVisibility(View.VISIBLE);
                            card50.setVisibility(View.VISIBLE);
                            txtEmiNo50.setText(mLoanEmisArrayList.get(j).emi_no);
                            card50.setTag(j);
                            break;

                        case 50:
                            linAmortBtn11.setVisibility(View.VISIBLE);
                            txtEmiNo51.setVisibility(View.VISIBLE);
                            card51.setVisibility(View.VISIBLE);
                            txtEmiNo51.setText(mLoanEmisArrayList.get(j).emi_no);
                            card51.setTag(j);
                            break;
                        case 51:
                            linAmortBtn11.setVisibility(View.VISIBLE);
                            txtEmiNo52.setVisibility(View.VISIBLE);
                            card52.setVisibility(View.VISIBLE);
                            txtEmiNo52.setText(mLoanEmisArrayList.get(j).emi_no);
                            card52.setTag(j);
                            break;
                        case 52:
                            linAmortBtn11.setVisibility(View.VISIBLE);
                            txtEmiNo53.setVisibility(View.VISIBLE);
                            card53.setVisibility(View.VISIBLE);
                            txtEmiNo53.setText(mLoanEmisArrayList.get(j).emi_no);
                            card53.setTag(j);
                            break;
                        case 53:
                            linAmortBtn11.setVisibility(View.VISIBLE);
                            txtEmiNo54.setVisibility(View.VISIBLE);
                            card54.setVisibility(View.VISIBLE);
                            txtEmiNo54.setText(mLoanEmisArrayList.get(j).emi_no);
                            card54.setTag(j);
                            break;
                        case 54:
                            linAmortBtn11.setVisibility(View.VISIBLE);
                            txtEmiNo55.setVisibility(View.VISIBLE);
                            card55.setVisibility(View.VISIBLE);
                            txtEmiNo55.setText(mLoanEmisArrayList.get(j).emi_no);
                            card55.setTag(j);
                            break;

                        case 55:
                            linAmortBtn12.setVisibility(View.VISIBLE);
                            txtEmiNo56.setVisibility(View.VISIBLE);
                            card56.setVisibility(View.VISIBLE);
                            txtEmiNo56.setText(mLoanEmisArrayList.get(j).emi_no);
                            card56.setTag(j);
                            break;
                        case 56:
                            linAmortBtn12.setVisibility(View.VISIBLE);
                            txtEmiNo57.setVisibility(View.VISIBLE);
                            card57.setVisibility(View.VISIBLE);
                            txtEmiNo57.setText(mLoanEmisArrayList.get(j).emi_no);
                            card57.setTag(j);
                            break;
                        case 57:
                            linAmortBtn12.setVisibility(View.VISIBLE);
                            txtEmiNo58.setVisibility(View.VISIBLE);
                            card58.setVisibility(View.VISIBLE);
                            txtEmiNo58.setText(mLoanEmisArrayList.get(j).emi_no);
                            card58.setTag(j);
                            break;
                        case 58:
                            linAmortBtn12.setVisibility(View.VISIBLE);
                            txtEmiNo59.setVisibility(View.VISIBLE);
                            card59.setVisibility(View.VISIBLE);
                            txtEmiNo59.setText(mLoanEmisArrayList.get(j).emi_no);
                            card59.setTag(j);
                            break;
                        case 59:
                            linAmortBtn12.setVisibility(View.VISIBLE);
                            txtEmiNo60.setVisibility(View.VISIBLE);
                            card60.setVisibility(View.VISIBLE);
                            txtEmiNo60.setText(mLoanEmisArrayList.get(j).emi_no);
                            card60.setTag(j);
                            break;

                        case 60:
                            linAmortBtn13.setVisibility(View.VISIBLE);
                            txtEmiNo61.setVisibility(View.VISIBLE);
                            card61.setVisibility(View.VISIBLE);
                            txtEmiNo61.setText(mLoanEmisArrayList.get(j).emi_no);
                            card61.setTag(j);
                            break;
                        case 61:
                            linAmortBtn13.setVisibility(View.VISIBLE);
                            txtEmiNo62.setVisibility(View.VISIBLE);
                            card62.setVisibility(View.VISIBLE);
                            txtEmiNo62.setText(mLoanEmisArrayList.get(j).emi_no);
                            card62.setTag(j);
                            break;
                        case 62:
                            linAmortBtn13.setVisibility(View.VISIBLE);
                            txtEmiNo63.setVisibility(View.VISIBLE);
                            card63.setVisibility(View.VISIBLE);
                            txtEmiNo63.setText(mLoanEmisArrayList.get(j).emi_no);
                            card63.setTag(j);
                            break;
                        case 63:
                            linAmortBtn13.setVisibility(View.VISIBLE);
                            txtEmiNo64.setVisibility(View.VISIBLE);
                            card64.setVisibility(View.VISIBLE);
                            txtEmiNo64.setText(mLoanEmisArrayList.get(j).emi_no);
                            card64.setTag(j);
                            break;
                        case 64:
                            linAmortBtn13.setVisibility(View.VISIBLE);
                            txtEmiNo65.setVisibility(View.VISIBLE);
                            card65.setVisibility(View.VISIBLE);
                            txtEmiNo65.setText(mLoanEmisArrayList.get(j).emi_no);
                            card65.setTag(j);
                            break;

                        case 65:
                            linAmortBtn14.setVisibility(View.VISIBLE);
                            txtEmiNo66.setVisibility(View.VISIBLE);
                            card66.setVisibility(View.VISIBLE);
                            txtEmiNo66.setText(mLoanEmisArrayList.get(j).emi_no);
                            card66.setTag(j);
                            break;
                        case 66:
                            linAmortBtn14.setVisibility(View.VISIBLE);
                            txtEmiNo67.setVisibility(View.VISIBLE);
                            card67.setVisibility(View.VISIBLE);
                            txtEmiNo67.setText(mLoanEmisArrayList.get(j).emi_no);
                            card67.setTag(j);
                            break;
                        case 67:
                            linAmortBtn14.setVisibility(View.VISIBLE);
                            txtEmiNo68.setVisibility(View.VISIBLE);
                            card68.setVisibility(View.VISIBLE);
                            txtEmiNo68.setText(mLoanEmisArrayList.get(j).emi_no);
                            card68.setTag(j);
                            break;
                        case 68:
                            linAmortBtn14.setVisibility(View.VISIBLE);
                            txtEmiNo69.setVisibility(View.VISIBLE);
                            card69.setVisibility(View.VISIBLE);
                            txtEmiNo69.setText(mLoanEmisArrayList.get(j).emi_no);
                            card69.setTag(j);
                            break;
                        case 69:
                            linAmortBtn14.setVisibility(View.VISIBLE);
                            txtEmiNo70.setVisibility(View.VISIBLE);
                            card70.setVisibility(View.VISIBLE);
                            txtEmiNo70.setText(mLoanEmisArrayList.get(j).emi_no);
                            card70.setTag(j);
                            break;

                        case 70:
                            linAmortBtn15.setVisibility(View.VISIBLE);
                            txtEmiNo71.setVisibility(View.VISIBLE);
                            card71.setVisibility(View.VISIBLE);
                            txtEmiNo71.setText(mLoanEmisArrayList.get(j).emi_no);
                            card71.setTag(j);
                            break;
                        case 71:
                            linAmortBtn15.setVisibility(View.VISIBLE);
                            txtEmiNo72.setVisibility(View.VISIBLE);
                            card72.setVisibility(View.VISIBLE);
                            txtEmiNo72.setText(mLoanEmisArrayList.get(j).emi_no);
                            card72.setTag(j);
                            break;

                        case 72:
                            linAmortBtn15.setVisibility(View.VISIBLE);
                            txtEmiNo73.setVisibility(View.VISIBLE);
                            card73.setVisibility(View.VISIBLE);
                            txtEmiNo73.setText(mLoanEmisArrayList.get(j).emi_no);
                            card73.setTag(j);
                            break;

                        case 73:
                            linAmortBtn15.setVisibility(View.VISIBLE);
                            txtEmiNo74.setVisibility(View.VISIBLE);
                            card74.setVisibility(View.VISIBLE);
                            txtEmiNo74.setText(mLoanEmisArrayList.get(j).emi_no);
                            card74.setTag(j);
                            break;

                        case 74:
                            linAmortBtn15.setVisibility(View.VISIBLE);
                            txtEmiNo75.setVisibility(View.VISIBLE);
                            card75.setVisibility(View.VISIBLE);
                            txtEmiNo75.setText(mLoanEmisArrayList.get(j).emi_no);
                            card75.setTag(j);
                            break;

                        case 75:
                            linAmortBtn16.setVisibility(View.VISIBLE);
                            txtEmiNo76.setVisibility(View.VISIBLE);
                            card76.setVisibility(View.VISIBLE);
                            txtEmiNo76.setText(mLoanEmisArrayList.get(j).emi_no);
                            card76.setTag(j);
                            break;

                        case 76:
                            linAmortBtn16.setVisibility(View.VISIBLE);
                            txtEmiNo77.setVisibility(View.VISIBLE);
                            card77.setVisibility(View.VISIBLE);
                            txtEmiNo77.setText(mLoanEmisArrayList.get(j).emi_no);
                            card77.setTag(j);
                            break;

                        case 77:
                            linAmortBtn16.setVisibility(View.VISIBLE);
                            txtEmiNo78.setVisibility(View.VISIBLE);
                            card78.setVisibility(View.VISIBLE);
                            txtEmiNo78.setText(mLoanEmisArrayList.get(j).emi_no);
                            card78.setTag(j);
                            break;

                        case 78:
                            linAmortBtn16.setVisibility(View.VISIBLE);
                            txtEmiNo79.setVisibility(View.VISIBLE);
                            card79.setVisibility(View.VISIBLE);
                            txtEmiNo79.setText(mLoanEmisArrayList.get(j).emi_no);
                            card79.setTag(j);
                            break;

                        case 79:
                            linAmortBtn16.setVisibility(View.VISIBLE);
                            txtEmiNo80.setVisibility(View.VISIBLE);
                            card80.setVisibility(View.VISIBLE);
                            txtEmiNo80.setText(mLoanEmisArrayList.get(j).emi_no);
                            card80.setTag(j);
                            break;

                        case 80:
                            linAmortBtn17.setVisibility(View.VISIBLE);
                            txtEmiNo81.setVisibility(View.VISIBLE);
                            card81.setVisibility(View.VISIBLE);
                            txtEmiNo81.setText(mLoanEmisArrayList.get(j).emi_no);
                            card81.setTag(j);
                            break;

                        case 81:
                            linAmortBtn17.setVisibility(View.VISIBLE);
                            txtEmiNo82.setVisibility(View.VISIBLE);
                            card82.setVisibility(View.VISIBLE);
                            txtEmiNo82.setText(mLoanEmisArrayList.get(j).emi_no);
                            card82.setTag(j);
                            break;

                        case 82:
                            linAmortBtn17.setVisibility(View.VISIBLE);
                            txtEmiNo83.setVisibility(View.VISIBLE);
                            card83.setVisibility(View.VISIBLE);
                            txtEmiNo83.setText(mLoanEmisArrayList.get(j).emi_no);
                            card83.setTag(j);
                            break;

                        case 83:
                            linAmortBtn17.setVisibility(View.VISIBLE);
                            txtEmiNo84.setVisibility(View.VISIBLE);
                            card84.setVisibility(View.VISIBLE);
                            txtEmiNo84.setText(mLoanEmisArrayList.get(j).emi_no);
                            card84.setTag(j);
                            break;

                        case 84:
                            linAmortBtn17.setVisibility(View.VISIBLE);
                            txtEmiNo85.setVisibility(View.VISIBLE);
                            card85.setVisibility(View.VISIBLE);
                            txtEmiNo85.setText(mLoanEmisArrayList.get(j).emi_no);
                            card85.setTag(j);
                            break;

                        case 85:
                            linAmortBtn18.setVisibility(View.VISIBLE);
                            txtEmiNo86.setVisibility(View.VISIBLE);
                            card86.setVisibility(View.VISIBLE);
                            txtEmiNo86.setText(mLoanEmisArrayList.get(j).emi_no);
                            card86.setTag(j);
                            break;

                        case 86:
                            linAmortBtn18.setVisibility(View.VISIBLE);
                            txtEmiNo87.setVisibility(View.VISIBLE);
                            card87.setVisibility(View.VISIBLE);
                            txtEmiNo87.setText(mLoanEmisArrayList.get(j).emi_no);
                            card87.setTag(j);
                            break;

                        case 87:
                            linAmortBtn18.setVisibility(View.VISIBLE);
                            txtEmiNo88.setVisibility(View.VISIBLE);
                            card88.setVisibility(View.VISIBLE);
                            txtEmiNo88.setText(mLoanEmisArrayList.get(j).emi_no);
                            card88.setTag(j);
                            break;

                        case 88:
                            linAmortBtn18.setVisibility(View.VISIBLE);
                            txtEmiNo89.setVisibility(View.VISIBLE);
                            card89.setVisibility(View.VISIBLE);
                            txtEmiNo89.setText(mLoanEmisArrayList.get(j).emi_no);
                            card89.setTag(j);
                            break;

                        case 89:
                            linAmortBtn18.setVisibility(View.VISIBLE);
                            txtEmiNo90.setVisibility(View.VISIBLE);
                            card90.setVisibility(View.VISIBLE);
                            txtEmiNo90.setText(mLoanEmisArrayList.get(j).emi_no);
                            card90.setTag(j);
                            break;

                        case 90:
                            linAmortBtn19.setVisibility(View.VISIBLE);
                            txtEmiNo91.setVisibility(View.VISIBLE);
                            card91.setVisibility(View.VISIBLE);
                            txtEmiNo91.setText(mLoanEmisArrayList.get(j).emi_no);
                            card91.setTag(j);
                            break;

                        case 91:
                            linAmortBtn19.setVisibility(View.VISIBLE);
                            txtEmiNo92.setVisibility(View.VISIBLE);
                            card92.setVisibility(View.VISIBLE);
                            txtEmiNo92.setText(mLoanEmisArrayList.get(j).emi_no);
                            card92.setTag(j);
                            break;

                        case 92:
                            linAmortBtn19.setVisibility(View.VISIBLE);
                            txtEmiNo93.setVisibility(View.VISIBLE);
                            card93.setVisibility(View.VISIBLE);
                            txtEmiNo93.setText(mLoanEmisArrayList.get(j).emi_no);
                            card93.setTag(j);
                            break;

                        case 93:
                            linAmortBtn19.setVisibility(View.VISIBLE);
                            txtEmiNo94.setVisibility(View.VISIBLE);
                            card94.setVisibility(View.VISIBLE);
                            txtEmiNo94.setText(mLoanEmisArrayList.get(j).emi_no);
                            card94.setTag(j);
                            break;

                        case 94:
                            linAmortBtn19.setVisibility(View.VISIBLE);
                            txtEmiNo95.setVisibility(View.VISIBLE);
                            card95.setVisibility(View.VISIBLE);
                            txtEmiNo95.setText(mLoanEmisArrayList.get(j).emi_no);
                            card95.setTag(j);
                            break;

                        case 95:
                            linAmortBtn20.setVisibility(View.VISIBLE);
                            txtEmiNo96.setVisibility(View.VISIBLE);
                            card96.setVisibility(View.VISIBLE);
                            txtEmiNo96.setText(mLoanEmisArrayList.get(j).emi_no);
                            card96.setTag(j);
                            break;

                        case 96:
                            linAmortBtn20.setVisibility(View.VISIBLE);
                            txtEmiNo97.setVisibility(View.VISIBLE);
                            card97.setVisibility(View.VISIBLE);
                            txtEmiNo97.setText(mLoanEmisArrayList.get(j).emi_no);
                            card97.setTag(j);
                            break;

                        case 97:
                            linAmortBtn20.setVisibility(View.VISIBLE);
                            txtEmiNo98.setVisibility(View.VISIBLE);
                            card98.setVisibility(View.VISIBLE);
                            txtEmiNo98.setText(mLoanEmisArrayList.get(j).emi_no);
                            card98.setTag(j);
                            break;

                        case 98:
                            linAmortBtn20.setVisibility(View.VISIBLE);
                            txtEmiNo99.setVisibility(View.VISIBLE);
                            card99.setVisibility(View.VISIBLE);
                            txtEmiNo99.setText(mLoanEmisArrayList.get(j).emi_no);
                            card99.setTag(j);
                            break;

                        case 99:
                            linAmortBtn20.setVisibility(View.VISIBLE);
                            txtEmiNo100.setVisibility(View.VISIBLE);
                            card100.setVisibility(View.VISIBLE);
                            txtEmiNo100.setText(mLoanEmisArrayList.get(j).emi_no);
                            card100.setTag(j);
                            break;


                    }
                }

                for (int k = 0; k < amortlistsize; k++) {
                    if (mLoanEmisArrayList.get(k).status.equals("0")) {
//                        selectedAmort(k);
                        if (linAmortTile.getVisibility() != View.VISIBLE) {
                            linAmortTile.setVisibility(View.VISIBLE);
                        }

                        txtEmiNo.setText("0" + mLoanEmisArrayList.get(k).emi_no);
                        txtEmiAmount.setText(mLoanEmisArrayList.get(k).emi_amount);
                        txtDueBy.setText(mLoanEmisArrayList.get(k).proposed_payment_date);
                        txtPaymentDate.setText(mLoanEmisArrayList.get(k).actual_payment_date);
                        txtPaymentStatus.setText(" " + mLoanEmisArrayList.get(k).statusMessage);

                        if (mLoanEmisArrayList.get(k).status.equals("0")) {
                            txtBtnText.setText(" " + "Pre Pay");
                            txtPaymentStatus.setTextColor(Color.parseColor("#1ac31a"));
                            imgpaymentStatus.setImageResource(R.drawable.ic_exclamation_circle);
                            imgpaymentStatus.setColorFilter(ContextCompat.getColor(context, colorGreen), android.graphics.PorterDuff.Mode.SRC_IN);

                        } else {
                            txtBtnText.setText(" " + "EMI History");
                            txtPaymentStatus.setTextColor(Color.parseColor("#1ac31a"));
                            imgpaymentStatus.setImageResource(R.drawable.ic_check_circle_green);
                            imgpaymentStatus.setColorFilter(ContextCompat.getColor(context, colorGreen), android.graphics.PorterDuff.Mode.SRC_IN);
                        }

                        linPayBtn.setTag(mLoanEmisArrayList.get(k).loan_emi_id);
                        break;
                    }
                }

            } else {
                try {
                    linHasAmort.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    linNoAmort.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String message = jsonDataO.getString("message");
                txtAmortNotCreated.setText(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                progressBarAmort.setVisibility(View.GONE);
            } catch (Exception e1) {
                e.printStackTrace();
            }
        }
    }

    public void makePayment(String emi_id, String Amount) {
        try {
            try {
                Globle.getInstance().paytm = new Paytm(
                        "Eduvan80947867008828",
                        "WAP",
                        Amount,
                        "APPPROD",
                        "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp",
//                                "https://securegw.paytm.in/theia/paytmCallback",
                        "Retail109"
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

            String url = "https://eduvanz.com/Paytm/generateChecksum.php/";
            Map<String, String> params = new HashMap<String, String>();
            VolleyCall volleyCall = new VolleyCall();
            params.put("MID", Globle.getInstance().paytm.getmId());
            params.put("ORDER_ID", Globle.getInstance().paytm.getOrderId());
            params.put("CUST_ID", Globle.getInstance().paytm.getCustId());
            params.put("CHANNEL_ID", Globle.getInstance().paytm.getChannelId());
            params.put("TXN_AMOUNT", Globle.getInstance().paytm.getTxnAmount());
            params.put("WEBSITE", Globle.getInstance().paytm.getWebsite());
            params.put("CALLBACK_URL", Globle.getInstance().paytm.getCallBackUrl());
            params.put("INDUSTRY_TYPE_ID", Globle.getInstance().paytm.getIndustryTypeId());
            if (!Globle.isNetworkAvailable(context)) {
                Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

            } else {
                volleyCall.sendRequest(context, url, null, mFragment, "initializePaytmPaymentAmort", params, MainActivity.auth_token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializePaytmPaymentAmort(JSONObject jsonData) {

        try {
            //getting paytm service
            PaytmPGService Service = PaytmPGService.getProductionService();
            String checksumHash = jsonData.optString("CHECKSUMHASH");

            //creating a hashmap and adding all the values required
            Map<String, String> paramMap = new HashMap<String, String>();

            paramMap.put("MID", Globle.getInstance().paytm.getmId());
            paramMap.put("ORDER_ID", Globle.getInstance().paytm.getOrderId());
            paramMap.put("CUST_ID", Globle.getInstance().paytm.getCustId());
//            paramMap.put( "MOBILE_NO" , "7777777777");
//            paramMap.put( "EMAIL" , "shuklavijay249@gmail.com");
            paramMap.put("CHANNEL_ID", Globle.getInstance().paytm.getChannelId());
            paramMap.put("TXN_AMOUNT", Globle.getInstance().paytm.getTxnAmount());
            paramMap.put("WEBSITE", Globle.getInstance().paytm.getWebsite());
//        paramMap.put("CALLBACK_URL",Globle.getInstance().paytm.getCallBackUrl().concat("?").concat("ORDER_ID=").concat(Globle.getInstance().paytm.getOrderId()));
            paramMap.put("CALLBACK_URL", Globle.getInstance().paytm.getCallBackUrl());
            paramMap.put("CHECKSUMHASH", checksumHash);
            paramMap.put("INDUSTRY_TYPE_ID", Globle.getInstance().paytm.getIndustryTypeId());

            PaytmOrder order = new PaytmOrder((HashMap<String, String>) paramMap);

            //intializing the paytm service
            Service.initialize(order, null);

            //finally starting the payment transaction
            Service.startPaymentTransaction(context, true, true, new PaytmPaymentTransactionCallback() {
                @Override
                public void someUIErrorOccurred(String inErrorMessage) {
                    // Some UI Error Occurred in Payment Gateway Activity.
                    // // This may be due to initialization of views in
                    // Payment Gateway Activity or may be due to //
                    // initialization of webview. // Error Message details
                    // the error occurred.
//                    Toast.makeText(context, "Payment Transaction response " + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
                    s.append("inErrorMessage-");
                    s.append(inErrorMessage);
                    Globle.appendLog(String.valueOf(s));

                }

                @Override
                public void onTransactionResponse(Bundle inResponse) {
                    Log.d("LOG", "Payment Transaction is successful " + inResponse);
//                    Toast.makeText(context, "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
                    s.append("inResponse-");
                    s.append(inResponse);
                    Globle.appendLog(String.valueOf(s));
                    //"Bundle[{STATUS=TXN_SUCCESS,
                    // CHECKSUMHASH=ENXZLPAIk3AlC/rdD7EfpMnG8Okxe0819nIQvFBjJL+aGnTrGIQfHHtGLFoiI+sWxVEFmOer+UCZiNaRNaRyOGbE4NMF66qRldhhHLJFaUs=,
                    // BANKNAME=Union Bank of India, ORDERID=ORDER100008205, TXNAMOUNT=10.00,
                    // TXNDATE=2018-07-10 14:23:01.0, MID=Eduvan80947867008828, TXNID=20180710111212800110168845030370887,
                    // RESPCODE=01, PAYMENTMODE=DC, BANKTXNID=201819106425560, CURRENCY=INR, GATEWAYNAME=SBIFSS,
                    // RESPMSG=Txn Success}]"
                    if (inResponse.get("STATUS").equals("TXN_SUCCESS"))
                    //                    if(inResponse.get("STATUS").equals("TXN_FAILURE"))
                    {
                        if (inResponse != null) {
                            String message = String.valueOf(inResponse.get("RESPMSG"));
                            String CHECKSUMHASH = String.valueOf(inResponse.get("CHECKSUMHASH"));
                            String BANKNAME = String.valueOf(inResponse.get("BANKNAME"));
                            String ORDERID = String.valueOf(inResponse.get("ORDERID"));
                            String TXNAMOUNT = String.valueOf(inResponse.get("TXNAMOUNT"));
                            String TXNDATE = String.valueOf(inResponse.get("TXNDATE"));
                            String MID = String.valueOf(inResponse.get("MID"));
                            String TXNID = String.valueOf(inResponse.get("TXNID"));
                            String RESPCODE = String.valueOf(inResponse.get("RESPCODE"));
                            String BANKTXNID = String.valueOf(inResponse.get("BANKTXNID"));
                            String PAYMENTMODE = String.valueOf(inResponse.get("PAYMENTMODE"));

                            if (message.equalsIgnoreCase("TXN_SUCCESS")) {
                                Log.e(MainActivity.TAG, "onActivityResult: " + "Transaction Successful!");
                                /** API CALL **/
                                try {

                                    //payment_platform = 2 (1-web, 2-app)
                                    //payment_partner = 1 (1-paytm, 2-atom)

                                    String url = MainActivity.mainUrl + "epayment/studentEmiReceive";
                                    Map<String, String> params = new HashMap<String, String>();
                                    VolleyCall volleyCall = new VolleyCall();
                                    params.put("loan_emi_id", linPayBtn.getTag().toString());
                                    params.put("TXNAMOUNT", TXNAMOUNT);
                                    params.put("TXNID", TXNID); // merchant ID
//                                  params.put("bankTxnId", BANKTXNID); // Bank ID
                                    params.put("STATUS", "TXN_SUCCESS");
                                    params.put("paymentOption", "1");
                                    params.put("payment_partner", payment_partner);
                                    params.put("payment_platform", payment_platform);
                                    if (!Globle.isNetworkAvailable(context)) {
                                        Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();

                                    } else {
                                        volleyCall.sendRequest(context, url, null, mFragment, "kycPaymentCaptureWizard", params, MainActivity.auth_token);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Log.e(MainActivity.TAG, "onActivityResult: " + "Transaction Successful!");
                                /** API CALL **/
                                try {
//                                        String ipaddress = Utils.getIPAddress(true);
                                    String url = MainActivity.mainUrl + "epayment/studentEmiReceive";
                                    Map<String, String> params = new HashMap<String, String>();
                                    VolleyCall volleyCall = new VolleyCall();
                                    params.put("loan_emi_id", linPayBtn.getTag().toString());
                                    params.put("TXNAMOUNT", TXNAMOUNT);
                                    params.put("TXNID", TXNID); // merchant ID
//                                  params.put("bankTxnId", BANKTXNID); // Bank ID
                                    params.put("STATUS", "TXN_SUCCESS");
                                    params.put("paymentOption", "1");
                                    params.put("payment_partner", payment_partner);
                                    params.put("payment_platform", payment_platform);
                                    if (!Globle.isNetworkAvailable(context)) {
                                        Toast.makeText(context, "Please check your network connection", Toast.LENGTH_SHORT).show();
                                    } else {
                                        volleyCall.sendRequest(context, url, null, mFragment, "kycPaymentCaptureWizard", params, MainActivity.auth_token);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                            try {
                                progressBarAmort.setVisibility(VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            getAmortDetails();
                        } else {
                            Toast.makeText(context, "Payment not successful ", Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void networkNotAvailable() { // If network is not
                    // available, then this
                    // method gets called.
                }

                @Override
                public void clientAuthenticationFailed(String inErrorMessage) {
                    // This method gets called if client authentication
                    // failed. // Failure may be due to following reasons //
                    // 1. Server error or downtime. // 2. Server unable to
                    // generate checksum or checksum response is not in
                    // proper format. // 3. Server failed to authenticate
                    // that client. That is value of payt_STATUS is 2. //
                    // Error Message describes the reason for failure.
                    Toast.makeText(context, "Back pressed. Transaction cancelled", Toast.LENGTH_LONG).show();
                    //                    Globle.appendLog(inErrorMessage);
                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
                    s.append("inErrorMessage-");
                    s.append(inErrorMessage);
                    Globle.appendLog(String.valueOf(s));

                }

                @Override
                public void onErrorLoadingWebPage(int iniErrorCode,
                                                  String inErrorMessage, String inFailingUrl) {
                    Toast.makeText(context, "Back pressed. Transaction cancelled", Toast.LENGTH_LONG).show();

                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
                    s.append("inErrorMessage-");
                    s.append(inErrorMessage);
                    s.append(" inFailingUrl-");
                    s.append(inFailingUrl);
                    Globle.appendLog(String.valueOf(s));

                }

                // had to be added: NOTE
                @Override
                public void onBackPressedCancelTransaction() {
                    Toast.makeText(context, "Back pressed. Transaction cancelled", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                    Log.d("LOG", "Payment Transaction Failed " + inErrorMessage);
                    Toast.makeText(context, "Payment Transaction Failed ", Toast.LENGTH_LONG).show();
                    StringBuilder s = new StringBuilder();//cb 207np 63w 54more text
                    s.append("inErrorMessage-");
                    s.append(inErrorMessage);
                    Globle.appendLog(String.valueOf(s));

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {

                case R.id.linPayBtn:
                    if (txtBtnText.getText().toString().contains("EMI History")) {

                        try {
                            String url = MainActivity.mainUrl + "dashboard/getEmiTransactionDetails";
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("emi_id", linPayBtn.getTag().toString());
                            if (!Globle.isNetworkAvailable(context)) {
                                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                            } else {
                                VolleyCall volleyCall = new VolleyCall();
                                volleyCall.sendRequest(context, url, null, mFragment, "getEmiTransactionDetails", params, MainActivity.auth_token);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        makePayment(linPayBtn.getTag().toString(), txtEmiAmount.getText().toString());

                    }
                    break;

                case R.id.card1:
                    selectedAmort((Integer) card1.getTag(),card1);

                    break;
                case R.id.card2:
                    selectedAmort((Integer) card2.getTag(), card2);
                    break;
                case R.id.card3:
                    selectedAmort((Integer) card3.getTag(), card3);
                    break;
                case R.id.card4:
                    selectedAmort((Integer) card4.getTag(), card4);
                    break;
                case R.id.card5:
                    selectedAmort((Integer) card5.getTag(), card5);
                    break;
                case R.id.card6:
                    selectedAmort((Integer) card6.getTag(), card6);
                    break;
                case R.id.card7:
                    selectedAmort((Integer) card7.getTag(), card7);
                    break;
                case R.id.card8:
                    selectedAmort((Integer) card8.getTag(), card8);
                    break;
                case R.id.card9:
                    selectedAmort((Integer) card9.getTag(), card9);
                    break;
                case R.id.card10:
                    selectedAmort((Integer) card10.getTag(), card10);
                    break;
                case R.id.card11:
                    selectedAmort((Integer) card11.getTag(), card11);
                    break;
                case R.id.card12:
                    selectedAmort((Integer) card12.getTag(), card12);
                    break;
                case R.id.card13:
                    selectedAmort((Integer) card13.getTag(), card13);
                    break;
                case R.id.card14:
                    selectedAmort((Integer) card14.getTag(), card14);
                    break;
                case R.id.card15:
                    selectedAmort((Integer) card15.getTag(), card15);
                    break;
                case R.id.card16:
                    selectedAmort((Integer) card16.getTag(), card16);
                    break;
                case R.id.card17:
                    selectedAmort((Integer) card17.getTag(), card17);
                    break;
                case R.id.card18:
                    selectedAmort((Integer) card18.getTag(), card18);
                    break;
                case R.id.card19:
                    selectedAmort((Integer) card19.getTag(), card19);
                    break;
                case R.id.card20:
                    selectedAmort((Integer) card20.getTag(), card20);
                    break;
                case R.id.card21:
                    selectedAmort((Integer) card21.getTag(), card21);
                    break;
                case R.id.card22:
                    selectedAmort((Integer) card22.getTag(), card22);
                    break;
                case R.id.card23:
                    selectedAmort((Integer) card23.getTag(), card23);
                    break;
                case R.id.card24:
                    selectedAmort((Integer) card24.getTag(), card24);
                    break;
                case R.id.card25:
                    selectedAmort((Integer) card25.getTag(), card25);
                    break;
                case R.id.card26:
                    selectedAmort((Integer) card26.getTag(), card26);
                    break;
                case R.id.card27:
                    selectedAmort((Integer) card27.getTag(), card27);
                    break;
                case R.id.card28:
                    selectedAmort((Integer) card28.getTag(), card28);
                    break;
                case R.id.card29:
                    selectedAmort((Integer) card29.getTag(), card29);
                    break;
                case R.id.card30:
                    selectedAmort((Integer) card30.getTag(), card30);
                    break;
                case R.id.card31:
                    selectedAmort((Integer) card31.getTag(), card31);
                    break;
                case R.id.card32:
                    selectedAmort((Integer) card32.getTag(), card32);
                    break;
                case R.id.card33:
                    selectedAmort((Integer) card33.getTag(), card33);
                    break;
                case R.id.card34:
                    selectedAmort((Integer) card34.getTag(), card34);
                    break;
                case R.id.card35:
                    selectedAmort((Integer) card35.getTag(), card35);
                    break;
                case R.id.card36:
                    selectedAmort((Integer) card36.getTag(), card36);
                    break;
                case R.id.card37:
                    selectedAmort((Integer) card37.getTag(), card37);
                    break;
                case R.id.card38:
                    selectedAmort((Integer) card38.getTag(), card38);
                    break;
                case R.id.card39:
                    selectedAmort((Integer) card39.getTag(), card39);
                    break;
                case R.id.card40:
                    selectedAmort((Integer) card40.getTag(), card40);
                    break;
                case R.id.card41:
                    selectedAmort((Integer) card41.getTag(), card41);
                    break;
                case R.id.card42:
                    selectedAmort((Integer) card42.getTag(), card42);
                    break;
                case R.id.card43:
                    selectedAmort((Integer) card43.getTag(), card43);
                    break;
                case R.id.card44:
                    selectedAmort((Integer) card44.getTag(), card44);
                    break;
                case R.id.card45:
                    selectedAmort((Integer) card45.getTag(), card45);
                    break;
                case R.id.card46:
                    selectedAmort((Integer) card46.getTag(), card46);
                    break;
                case R.id.card47:
                    selectedAmort((Integer) card47.getTag(), card47);
                    break;
                case R.id.card48:
                    selectedAmort((Integer) card48.getTag(), card48);
                    break;
                case R.id.card49:
                    selectedAmort((Integer) card49.getTag(), card49);
                    break;
                case R.id.card50:
                    selectedAmort((Integer) card50.getTag(), card50);
                    break;
                case R.id.card51:
                    selectedAmort((Integer) card51.getTag(), card51);
                    break;
                case R.id.card52:
                    selectedAmort((Integer) card52.getTag(), card52);
                    break;
                case R.id.card53:
                    selectedAmort((Integer) card53.getTag(), card53);
                    break;
                case R.id.card54:
                    selectedAmort((Integer) card54.getTag(), card54);
                    break;
                case R.id.card55:
                    selectedAmort((Integer) card55.getTag(), card55);
                    break;
                case R.id.card56:
                    selectedAmort((Integer) card56.getTag(), card56);
                    break;
                case R.id.card57:
                    selectedAmort((Integer) card57.getTag(), card57);
                    break;
                case R.id.card58:
                    selectedAmort((Integer) card58.getTag(), card58);
                    break;
                case R.id.card59:
                    selectedAmort((Integer) card59.getTag(), card59);
                    break;
                case R.id.card60:
                    selectedAmort((Integer) card60.getTag(), card60);
                    break;
                case R.id.card61:
                    selectedAmort((Integer) card61.getTag(), card61);
                    break;
                case R.id.card62:
                    selectedAmort((Integer) card62.getTag(), card62);
                    break;
                case R.id.card63:
                    selectedAmort((Integer) card63.getTag(), card63);
                    break;
                case R.id.card64:
                    selectedAmort((Integer) card64.getTag(), card64);
                    break;
                case R.id.card65:
                    selectedAmort((Integer) card65.getTag(), card65);
                    break;
                case R.id.card66:
                    selectedAmort((Integer) card66.getTag(), card66);
                    break;
                case R.id.card67:
                    selectedAmort((Integer) card67.getTag(), card67);
                    break;
                case R.id.card68:
                    selectedAmort((Integer) card68.getTag(), card68);
                    break;
                case R.id.card69:
                    selectedAmort((Integer) card69.getTag(), card69);
                    break;
                case R.id.card70:
                    selectedAmort((Integer) card70.getTag(), card70);
                    break;

                case R.id.card71:
                    selectedAmort((Integer) card71.getTag(), card71);
                    break;
                case R.id.card72:
                    selectedAmort((Integer) card72.getTag(), card72);
                    break;
                case R.id.card73:
                    selectedAmort((Integer) card73.getTag(), card73);
                    break;
                case R.id.card74:
                    selectedAmort((Integer) card74.getTag(), card74);
                    break;
                case R.id.card75:
                    selectedAmort((Integer) card75.getTag(), card75);
                    break;
                case R.id.card76:
                    selectedAmort((Integer) card76.getTag(), card76);
                    break;
                case R.id.card77:
                    selectedAmort((Integer) card77.getTag(), card77);
                    break;
                case R.id.card78:
                    selectedAmort((Integer) card78.getTag(), card78);
                    break;
                case R.id.card79:
                    selectedAmort((Integer) card79.getTag(), card79);
                    break;
                case R.id.card80:
                    selectedAmort((Integer) card80.getTag(), card80);
                    break;

                case R.id.card81:
                    selectedAmort((Integer) card81.getTag(), card81);
                    break;
                case R.id.card82:
                    selectedAmort((Integer) card82.getTag(), card82);
                    break;
                case R.id.card83:
                    selectedAmort((Integer) card83.getTag(), card83);
                    break;
                case R.id.card84:
                    selectedAmort((Integer) card84.getTag(), card84);
                    break;
                case R.id.card85:
                    selectedAmort((Integer) card85.getTag(), card85);
                    break;
                case R.id.card86:
                    selectedAmort((Integer) card86.getTag(), card86);
                    break;
                case R.id.card87:
                    selectedAmort((Integer) card87.getTag(), card87);
                    break;
                case R.id.card88:
                    selectedAmort((Integer) card88.getTag(), card88);
                    break;
                case R.id.card89:
                    selectedAmort((Integer) card89.getTag(), card89);
                    break;
                case R.id.card90:
                    selectedAmort((Integer) card90.getTag(), card90);
                    break;

                case R.id.card91:
                    selectedAmort((Integer) card91.getTag(), card91);
                    break;
                case R.id.card92:
                    selectedAmort((Integer) card92.getTag(), card92);
                    break;
                case R.id.card93:
                    selectedAmort((Integer) card93.getTag(), card93);
                    break;
                case R.id.card94:
                    selectedAmort((Integer) card94.getTag(), card94);
                    break;
                case R.id.card95:
                    selectedAmort((Integer) card95.getTag(), card95);
                    break;
                case R.id.card96:
                    selectedAmort((Integer) card96.getTag(), card96);
                    break;
                case R.id.card97:
                    selectedAmort((Integer) card97.getTag(), card97);
                    break;
                case R.id.card98:
                    selectedAmort((Integer) card98.getTag(), card98);
                    break;
                case R.id.card99:
                    selectedAmort((Integer) card99.getTag(), card99);
                    break;
                case R.id.card100:
                    selectedAmort((Integer) card100.getTag(), card100);
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void emiHistoryDialog(JSONObject jsonObject) {

        try {

            if (jsonObject.getInt("status") == 1) {

                jsonObject.getString("status");
                jsonObject.getString("message");
                JSONObject jsonObject1 = jsonObject.getJSONObject("emiCompleteInstrumentDetails");


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//            builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                }
//            });
                final AlertDialog dialog = builder.create();
                LayoutInflater inflater = dialog.getLayoutInflater();
                View dialogLayout = inflater.inflate(R.layout.emihistorydialog, null);
                dialog.setView(dialogLayout);
                ImageButton closeButton = dialogLayout.findViewById(R.id.btnClose);
                TextView txtPaymentMode = dialogLayout.findViewById(R.id.txtPaymentMode);
                TextView txtTransactionStatus = dialogLayout.findViewById(R.id.txtTransactionStatus);
                TextView txtPaymentAmount = dialogLayout.findViewById(R.id.txtPaymentAmount);
                TextView txtPaymentDate = dialogLayout.findViewById(R.id.txtPaymentDate);

                txtPaymentMode.setText(jsonObject1.getString("instrument_type").toString());
                txtTransactionStatus.setText(jsonObject1.getString("transaction_type_value").toString());
                txtPaymentAmount.setText(jsonObject1.getString("transaction_amount").toString());
                txtPaymentDate.setText(jsonObject1.getString("epayment_transaction_date").toString());

                closeButton.setOnClickListener((View.OnClickListener) mFragment);

                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                    try {
//
//                        InputMethodManager imm = (InputMethodManager) (context.getSystemService(Context.INPUT_METHOD_SERVICE));
//                        if (imm != null) {
//                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                        dialog.dismiss();
                    }
                });

                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.show();

                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface d) {

                    }
                });

//            final AlertDialog emihistorydialog = new AlertDialog.Builder(context).create();
//            LayoutInflater factory = LayoutInflater.from(context);
//            final View dia = factory.inflate(R.layout.emihistorydialog, null);
//
//            ImageButton closeButton = dia.findViewById(R.id.btnClose);
//            TextView txtPaymentMode =  dia.findViewById(R.id.txtPaymentMode);
//            TextView txtTransactionStatus =  dia.findViewById(R.id.txtTransactionStatus);
//            TextView txtPaymentAmount =  dia.findViewById(R.id.txtPaymentAmount);
//            TextView txtPaymentDate =  dia.findViewById(R.id.txtPaymentDate);
//
//            closeButton.setOnClickListener((View.OnClickListener) mFragment);
//
//            emihistorydialog.show();
//            closeButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    try {
////
////                        InputMethodManager imm = (InputMethodManager) (context.getSystemService(Context.INPUT_METHOD_SERVICE));
////                        if (imm != null) {
////                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
////                        }
////                    } catch (Exception e) {
////                        e.printStackTrace();
////                    }
//                    emihistorydialog.dismiss();
//                }
//
//            });
            } else {

                Toast.makeText(context, jsonObject.getString("message").toString(), Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void selectedAmort(int pos, CardView CheckedCard) {

        if (linAmortTile.getVisibility() != View.VISIBLE) {
            linAmortTile.setVisibility(View.VISIBLE);
        }

        if(mLoanEmisArrayList.get(pos).emi_no.length()>1){
            txtEmiNo.setText(mLoanEmisArrayList.get(pos).emi_no);
        } else {
        txtEmiNo.setText("0" + mLoanEmisArrayList.get(pos).emi_no);
        }
        txtEmiAmount.setText(mLoanEmisArrayList.get(pos).emi_amount);
        txtDueBy.setText(mLoanEmisArrayList.get(pos).proposed_payment_date);
        txtPaymentDate.setText(mLoanEmisArrayList.get(pos).actual_payment_date);
        txtPaymentStatus.setText(" " + mLoanEmisArrayList.get(pos).statusMessage);
        String msg = (String) txtPaymentStatus.getText();

        if (mLoanEmisArrayList.get(pos).status.equals("0")) {

            txtBtnText.setText(" " + "Pre Pay");
            txtPaymentStatus.setTextColor(Color.parseColor("#ee415e"));
            imgpaymentStatus.setImageResource(R.drawable.ic_exclamation_circle);
            imgpaymentStatus.setColorFilter(ContextCompat.getColor(context, colorRed), android.graphics.PorterDuff.Mode.SRC_IN);

        } else {

            txtBtnText.setText(" " + "EMI History");
            txtPaymentStatus.setTextColor(Color.parseColor("#1ac31a"));
            imgpaymentStatus.setImageResource(R.drawable.ic_check_circle_green);
            imgpaymentStatus.setColorFilter(ContextCompat.getColor(context, colorGreen), android.graphics.PorterDuff.Mode.SRC_IN);

        }
        linPayBtn.setTag(mLoanEmisArrayList.get(pos).loan_emi_id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                card1.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card2.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card3.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card4.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card5.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card6.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card7.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card8.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card9.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card10.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card11.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card12.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card13.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card14.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card15.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card16.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card17.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card18.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card19.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card20.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card21.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card22.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card23.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card24.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card25.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card26.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card27.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card28.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card29.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card30.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card31.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card32.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card33.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card34.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card35.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card36.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card37.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card38.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card39.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card40.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card41.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card42.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card43.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card44.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card45.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card46.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card47.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card48.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card49.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card50.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card51.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card52.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card53.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card54.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card55.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card56.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card57.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card58.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card59.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card60.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card61.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card62.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card63.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card64.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card65.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card66.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card67.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card68.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card69.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card70.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));

                card71.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card72.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card73.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card74.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card75.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card76.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card77.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card78.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card79.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card80.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));

                card81.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card82.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card83.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card84.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card85.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card86.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card87.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card88.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card89.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card90.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));


                card91.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card92.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card93.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card94.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card95.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card96.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card97.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card98.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card99.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
                card100.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.white)));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CheckedCard.setBackgroundTintList(ColorStateList.valueOf(context.getResources().getColor(R.color.lightGrey)));
        }

    }
}
