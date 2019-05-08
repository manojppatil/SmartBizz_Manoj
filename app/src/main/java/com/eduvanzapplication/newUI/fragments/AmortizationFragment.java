package com.eduvanzapplication.newUI.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.MainActivity;
import com.eduvanzapplication.R;
import com.eduvanzapplication.Util.Globle;
import com.eduvanzapplication.newUI.VolleyCall;
import com.eduvanzapplication.newUI.adapter.AmortAdapter;
import com.eduvanzapplication.newUI.newViews.LoanTabActivity;
import com.eduvanzapplication.newUI.pojo.MLoanEmis;
import android.app.AlertDialog;

import com.squareup.picasso.Picasso;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eduvanzapplication.newUI.MainApplication.TAG;
import static com.eduvanzapplication.newUI.newViews.LoanTabActivity.lead_id;


public class AmortizationFragment extends Fragment implements View.OnClickListener {

    static View view, thumbView1;
    public static Context context;
    public static Fragment mFragment;
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
            txtEmiNo64, txtEmiNo65, txtEmiNo66, txtEmiNo67, txtEmiNo68, txtEmiNo69, txtEmiNo70;
    public static LinearLayout linAmortBtn1, linAmortBtn2, linAmortBtn3, linAmortBtn4, linAmortBtn5, linAmortBtn6,
            linAmortBtn7, linAmortBtn8, linAmortBtn9, linAmortBtn10, linAmortBtn11, linAmortBtn12, linAmortBtn13, linAmortBtn14,linAmortTile;
    public static CardView card1, card2, card3, card4, card5, card6, card7, card8, card9, card10,
            card11, card12, card13, card14, card15, card16, card17, card18, card19, card20,
            card21, card22, card23, card24, card25, card26, card27, card28, card29, card30,
            card31, card32, card33, card34, card35, card36, card37, card38, card39, card40,
            card41, card42, card43, card44, card45, card46, card47, card48, card49, card50,
            card51, card52, card53, card54, card55, card56, card57, card58, card59, card60,
            card61, card62, card63, card64, card65, card66, card67, card68, card69, card70;

    public static TextView txtEmiNo,txtEmiAmount,txtDueBy, txtPaymentDate, txtPaymentStatus,txtBtnText;
    public static LinearLayout linPayBtn;

    public static RecyclerView rvAmort;
    public static AmortAdapter adapter;
    public static SeekBar seek_bar;
    public static CardView emi_card_1;
    public static String emi_num, str_emi_due, str_emi_pay_date;

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

//        emi_card_1 = view.findViewById(R.id.card1);
//        emi_number =view.findViewById(R.id.txtEmiNo);
//        emi_due_date = view.findViewById(R.id.txtDueBy);
//        emi_Payment_date = view.findViewById(R.id.txtPaymentDate);
//        emi_card_1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, emi_num+" "+str_emi_pay_date+" "+str_emi_due, Toast.LENGTH_SHORT).show();
//            }
//        });

        txtEmiNo = view.findViewById(R.id.txtEmiNo);
        txtEmiAmount = view.findViewById(R.id.txtEmiAmount);
        txtDueBy= view.findViewById(R.id.txtDueBy);
        txtPaymentDate = view.findViewById(R.id.txtPaymentDate);
        txtPaymentStatus = view.findViewById(R.id.txtPaymentStatus);
        txtBtnText = view.findViewById(R.id.txtBtnText);
        linPayBtn = view.findViewById(R.id.linPayBtn);
        linAmortTile = view.findViewById(R.id.linAmortTile);

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

        //linear layout
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


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvAmort.setLayoutManager(linearLayoutManager);
        adapter = new AmortAdapter(mLoanEmisArrayList, context, getActivity());
        rvAmort.setAdapter(adapter);
        rvAmort.setNestedScrollingEnabled(false);

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


        try {
            String url = MainActivity.mainUrl + "dashboard/ammortisation";
            Map<String, String> params = new HashMap<String, String>();
            params.put("lead_id", lead_id);
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

        return view;

    }

    public void setAmortDetails(JSONObject jsonDataO) {
        Log.e(TAG, "setProfileDashbBoardStatus: " + jsonDataO);
        try {
//            progressDialog.dismiss();
            mLoanEmisArrayList = new ArrayList<>();
            if (jsonDataO.getInt("status") == 1) {
                String message = jsonDataO.getString("message");
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

                for (int j = 0; j < amortlistsize; j++) {
                    switch (j) {

                        case 0: linAmortBtn1.setVisibility(View.VISIBLE);txtEmiNo1.setVisibility(View.VISIBLE);card1.setVisibility(View.VISIBLE);txtEmiNo1.setText("0" + mLoanEmisArrayList.get(j).emi_no);card1.setTag(j);break;
                        case 1: linAmortBtn1.setVisibility(View.VISIBLE);txtEmiNo2.setVisibility(View.VISIBLE);card2.setVisibility(View.VISIBLE);txtEmiNo2.setText("0" + mLoanEmisArrayList.get(j).emi_no);card2.setTag(j);break;
                        case 2: linAmortBtn1.setVisibility(View.VISIBLE);txtEmiNo3.setVisibility(View.VISIBLE);card3.setVisibility(View.VISIBLE);txtEmiNo3.setText("0" + mLoanEmisArrayList.get(j).emi_no);card3.setTag(j);break;
                        case 3: linAmortBtn1.setVisibility(View.VISIBLE);txtEmiNo4.setVisibility(View.VISIBLE);card4.setVisibility(View.VISIBLE);txtEmiNo4.setText("0" + mLoanEmisArrayList.get(j).emi_no);card4.setTag(j);break;
                        case 4: linAmortBtn1.setVisibility(View.VISIBLE);txtEmiNo5.setVisibility(View.VISIBLE);card5.setVisibility(View.VISIBLE);txtEmiNo5.setText("0" + mLoanEmisArrayList.get(j).emi_no);card5.setTag(j);break;

                        case 5: linAmortBtn2.setVisibility(View.VISIBLE);txtEmiNo6.setVisibility(View.VISIBLE);card6.setVisibility(View.VISIBLE);txtEmiNo6.setText("0" + mLoanEmisArrayList.get(j).emi_no);card6.setTag(j);break;
                        case 6: linAmortBtn2.setVisibility(View.VISIBLE);txtEmiNo7.setVisibility(View.VISIBLE);card7.setVisibility(View.VISIBLE);txtEmiNo7.setText("0" + mLoanEmisArrayList.get(j).emi_no);card7.setTag(j);break;
                        case 7: linAmortBtn2.setVisibility(View.VISIBLE);txtEmiNo8.setVisibility(View.VISIBLE);card8.setVisibility(View.VISIBLE);txtEmiNo8.setText("0" + mLoanEmisArrayList.get(j).emi_no);card8.setTag(j);break;
                        case 8: linAmortBtn2.setVisibility(View.VISIBLE);txtEmiNo9.setVisibility(View.VISIBLE);card9.setVisibility(View.VISIBLE);txtEmiNo9.setText("0" + mLoanEmisArrayList.get(j).emi_no);card9.setTag(j);break;
                        case 9: linAmortBtn2.setVisibility(View.VISIBLE);txtEmiNo10.setVisibility(View.VISIBLE);card10.setVisibility(View.VISIBLE);txtEmiNo10.setText("0" + mLoanEmisArrayList.get(j).emi_no);card10.setTag(j);break;

                        case 10: linAmortBtn3.setVisibility(View.VISIBLE);txtEmiNo11.setVisibility(View.VISIBLE);card11.setVisibility(View.VISIBLE);txtEmiNo11.setText("0" + mLoanEmisArrayList.get(j).emi_no);card11.setTag(j);break;
                        case 11: linAmortBtn3.setVisibility(View.VISIBLE);txtEmiNo12.setVisibility(View.VISIBLE);card12.setVisibility(View.VISIBLE);txtEmiNo12.setText("0" + mLoanEmisArrayList.get(j).emi_no);card12.setTag(j);break;
                        case 12: linAmortBtn3.setVisibility(View.VISIBLE);txtEmiNo13.setVisibility(View.VISIBLE);card13.setVisibility(View.VISIBLE);txtEmiNo13.setText("0" + mLoanEmisArrayList.get(j).emi_no);card13.setTag(j);break;
                        case 13: linAmortBtn3.setVisibility(View.VISIBLE);txtEmiNo14.setVisibility(View.VISIBLE);card14.setVisibility(View.VISIBLE);txtEmiNo14.setText("0" + mLoanEmisArrayList.get(j).emi_no);card14.setTag(j);break;
                        case 14: linAmortBtn1.setVisibility(View.VISIBLE);txtEmiNo15.setVisibility(View.VISIBLE);card15.setVisibility(View.VISIBLE);txtEmiNo15.setText("0" + mLoanEmisArrayList.get(j).emi_no);card15.setTag(j);break;

                        case 15: linAmortBtn4.setVisibility(View.VISIBLE);txtEmiNo16.setVisibility(View.VISIBLE);card16.setVisibility(View.VISIBLE);txtEmiNo16.setText("0" + mLoanEmisArrayList.get(j).emi_no);card16.setTag(j);break;
                        case 16: linAmortBtn4.setVisibility(View.VISIBLE);txtEmiNo17.setVisibility(View.VISIBLE);card17.setVisibility(View.VISIBLE);txtEmiNo17.setText("0" + mLoanEmisArrayList.get(j).emi_no);card17.setTag(j);break;
                        case 17: linAmortBtn4.setVisibility(View.VISIBLE);txtEmiNo18.setVisibility(View.VISIBLE);card18.setVisibility(View.VISIBLE);txtEmiNo18.setText("0" + mLoanEmisArrayList.get(j).emi_no);card18.setTag(j);break;
                        case 18: linAmortBtn4.setVisibility(View.VISIBLE);txtEmiNo19.setVisibility(View.VISIBLE);card19.setVisibility(View.VISIBLE);txtEmiNo19.setText("0" + mLoanEmisArrayList.get(j).emi_no);card19.setTag(j);break;
                        case 19: linAmortBtn4.setVisibility(View.VISIBLE);txtEmiNo20.setVisibility(View.VISIBLE);card20.setVisibility(View.VISIBLE);txtEmiNo20.setText("0" + mLoanEmisArrayList.get(j).emi_no);card20.setTag(j);break;

                case 20: linAmortBtn5.setVisibility(View.VISIBLE);txtEmiNo21.setVisibility(View.VISIBLE);card21.setVisibility(View.VISIBLE);txtEmiNo21.setText("0" + mLoanEmisArrayList.get(j).emi_no);card21.setTag(j);break;
                case 21: linAmortBtn5.setVisibility(View.VISIBLE);txtEmiNo22.setVisibility(View.VISIBLE);card22.setVisibility(View.VISIBLE);txtEmiNo22.setText("0" + mLoanEmisArrayList.get(j).emi_no);card22.setTag(j);break;
                case 22: linAmortBtn5.setVisibility(View.VISIBLE);txtEmiNo23.setVisibility(View.VISIBLE);card23.setVisibility(View.VISIBLE);txtEmiNo23.setText("0" + mLoanEmisArrayList.get(j).emi_no);card23.setTag(j);break;
                case 23: linAmortBtn5.setVisibility(View.VISIBLE);txtEmiNo24.setVisibility(View.VISIBLE);card24.setVisibility(View.VISIBLE);txtEmiNo24.setText("0" + mLoanEmisArrayList.get(j).emi_no);card24.setTag(j);break;
                case 24: linAmortBtn5.setVisibility(View.VISIBLE);txtEmiNo25.setVisibility(View.VISIBLE);card25.setVisibility(View.VISIBLE);txtEmiNo25.setText("0" + mLoanEmisArrayList.get(j).emi_no);card25.setTag(j);break;

                case 25: linAmortBtn6.setVisibility(View.VISIBLE);txtEmiNo26.setVisibility(View.VISIBLE);card26.setVisibility(View.VISIBLE);txtEmiNo26.setText("0" + mLoanEmisArrayList.get(j).emi_no);card26.setTag(j);break;
                case 26: linAmortBtn6.setVisibility(View.VISIBLE);txtEmiNo27.setVisibility(View.VISIBLE);card27.setVisibility(View.VISIBLE);txtEmiNo27.setText("0" + mLoanEmisArrayList.get(j).emi_no);card27.setTag(j);break;
                case 27: linAmortBtn6.setVisibility(View.VISIBLE);txtEmiNo28.setVisibility(View.VISIBLE);card28.setVisibility(View.VISIBLE);txtEmiNo28.setText("0" + mLoanEmisArrayList.get(j).emi_no);card28.setTag(j);break;
                case 28: linAmortBtn6.setVisibility(View.VISIBLE);txtEmiNo29.setVisibility(View.VISIBLE);card29.setVisibility(View.VISIBLE);txtEmiNo29.setText("0" + mLoanEmisArrayList.get(j).emi_no);card29.setTag(j);break;
                case 29: linAmortBtn6.setVisibility(View.VISIBLE);txtEmiNo30.setVisibility(View.VISIBLE);card30.setVisibility(View.VISIBLE);txtEmiNo30.setText("0" + mLoanEmisArrayList.get(j).emi_no);card30.setTag(j);break;

                case 30: linAmortBtn7.setVisibility(View.VISIBLE);txtEmiNo31.setVisibility(View.VISIBLE);card31.setVisibility(View.VISIBLE);txtEmiNo31.setText("0" + mLoanEmisArrayList.get(j).emi_no);card31.setTag(j);break;
                case 31: linAmortBtn7.setVisibility(View.VISIBLE);txtEmiNo32.setVisibility(View.VISIBLE);card32.setVisibility(View.VISIBLE);txtEmiNo32.setText("0" + mLoanEmisArrayList.get(j).emi_no);card32.setTag(j);break;
                case 32: linAmortBtn7.setVisibility(View.VISIBLE);txtEmiNo33.setVisibility(View.VISIBLE);card33.setVisibility(View.VISIBLE);txtEmiNo33.setText("0" + mLoanEmisArrayList.get(j).emi_no);card33.setTag(j);break;
                case 33: linAmortBtn7.setVisibility(View.VISIBLE);txtEmiNo34.setVisibility(View.VISIBLE);card34.setVisibility(View.VISIBLE);txtEmiNo34.setText("0" + mLoanEmisArrayList.get(j).emi_no);card34.setTag(j);break;
                case 34: linAmortBtn7.setVisibility(View.VISIBLE);txtEmiNo35.setVisibility(View.VISIBLE);card35.setVisibility(View.VISIBLE);txtEmiNo35.setText("0" + mLoanEmisArrayList.get(j).emi_no);card35.setTag(j);break;

                case 35: linAmortBtn8.setVisibility(View.VISIBLE);txtEmiNo36.setVisibility(View.VISIBLE);card36.setVisibility(View.VISIBLE);txtEmiNo36.setText("0" + mLoanEmisArrayList.get(j).emi_no);card36.setTag(j);break;
                case 36: linAmortBtn8.setVisibility(View.VISIBLE);txtEmiNo37.setVisibility(View.VISIBLE);card37.setVisibility(View.VISIBLE);txtEmiNo37.setText("0" + mLoanEmisArrayList.get(j).emi_no);card37.setTag(j);break;
                case 37: linAmortBtn8.setVisibility(View.VISIBLE);txtEmiNo38.setVisibility(View.VISIBLE);card38.setVisibility(View.VISIBLE);txtEmiNo38.setText("0" + mLoanEmisArrayList.get(j).emi_no);card38.setTag(j);break;
                case 38: linAmortBtn8.setVisibility(View.VISIBLE);txtEmiNo39.setVisibility(View.VISIBLE);card39.setVisibility(View.VISIBLE);txtEmiNo39.setText("0" + mLoanEmisArrayList.get(j).emi_no);card39.setTag(j);break;
                case 39: linAmortBtn8.setVisibility(View.VISIBLE);txtEmiNo40.setVisibility(View.VISIBLE);card40.setVisibility(View.VISIBLE);txtEmiNo40.setText("0" + mLoanEmisArrayList.get(j).emi_no);card40.setTag(j);break;

                case 40: linAmortBtn9.setVisibility(View.VISIBLE);txtEmiNo41.setVisibility(View.VISIBLE);card41.setVisibility(View.VISIBLE);txtEmiNo41.setText("0" + mLoanEmisArrayList.get(j).emi_no);card41.setTag(j);break;
                case 41: linAmortBtn9.setVisibility(View.VISIBLE);txtEmiNo42.setVisibility(View.VISIBLE);card42.setVisibility(View.VISIBLE);txtEmiNo42.setText("0" + mLoanEmisArrayList.get(j).emi_no);card42.setTag(j);break;
                case 42: linAmortBtn9.setVisibility(View.VISIBLE);txtEmiNo43.setVisibility(View.VISIBLE);card43.setVisibility(View.VISIBLE);txtEmiNo43.setText("0" + mLoanEmisArrayList.get(j).emi_no);card43.setTag(j);break;
                case 43: linAmortBtn9.setVisibility(View.VISIBLE);txtEmiNo44.setVisibility(View.VISIBLE);card44.setVisibility(View.VISIBLE);txtEmiNo44.setText("0" + mLoanEmisArrayList.get(j).emi_no);card44.setTag(j);break;
                case 44: linAmortBtn9.setVisibility(View.VISIBLE);txtEmiNo45.setVisibility(View.VISIBLE);card45.setVisibility(View.VISIBLE);txtEmiNo45.setText("0" + mLoanEmisArrayList.get(j).emi_no);card45.setTag(j);break;

                case 45: linAmortBtn10.setVisibility(View.VISIBLE);txtEmiNo46.setVisibility(View.VISIBLE);card46.setVisibility(View.VISIBLE);txtEmiNo46.setText("0" + mLoanEmisArrayList.get(j).emi_no);card46.setTag(j);break;
                case 46: linAmortBtn10.setVisibility(View.VISIBLE);txtEmiNo47.setVisibility(View.VISIBLE);card47.setVisibility(View.VISIBLE);txtEmiNo47.setText("0" + mLoanEmisArrayList.get(j).emi_no);card47.setTag(j);break;
                case 47: linAmortBtn10.setVisibility(View.VISIBLE);txtEmiNo48.setVisibility(View.VISIBLE);card48.setVisibility(View.VISIBLE);txtEmiNo48.setText("0" + mLoanEmisArrayList.get(j).emi_no);card48.setTag(j);break;
                case 48: linAmortBtn10.setVisibility(View.VISIBLE);txtEmiNo49.setVisibility(View.VISIBLE);card49.setVisibility(View.VISIBLE);txtEmiNo49.setText("0" + mLoanEmisArrayList.get(j).emi_no);card49.setTag(j);break;
                case 49: linAmortBtn10.setVisibility(View.VISIBLE);txtEmiNo50.setVisibility(View.VISIBLE);card50.setVisibility(View.VISIBLE);txtEmiNo50.setText("0" + mLoanEmisArrayList.get(j).emi_no);card50.setTag(j);break;

                case 50: linAmortBtn11.setVisibility(View.VISIBLE);txtEmiNo51.setVisibility(View.VISIBLE);card51.setVisibility(View.VISIBLE);txtEmiNo51.setText("0" + mLoanEmisArrayList.get(j).emi_no);card51.setTag(j);break;
                case 51: linAmortBtn11.setVisibility(View.VISIBLE);txtEmiNo52.setVisibility(View.VISIBLE);card52.setVisibility(View.VISIBLE);txtEmiNo52.setText("0" + mLoanEmisArrayList.get(j).emi_no);card52.setTag(j);break;
                case 52: linAmortBtn11.setVisibility(View.VISIBLE);txtEmiNo53.setVisibility(View.VISIBLE);card53.setVisibility(View.VISIBLE);txtEmiNo53.setText("0" + mLoanEmisArrayList.get(j).emi_no);card53.setTag(j);break;
                case 53: linAmortBtn11.setVisibility(View.VISIBLE);txtEmiNo54.setVisibility(View.VISIBLE);card54.setVisibility(View.VISIBLE);txtEmiNo54.setText("0" + mLoanEmisArrayList.get(j).emi_no);card54.setTag(j);break;
                case 54: linAmortBtn11.setVisibility(View.VISIBLE);txtEmiNo55.setVisibility(View.VISIBLE);card55.setVisibility(View.VISIBLE);txtEmiNo55.setText("0" + mLoanEmisArrayList.get(j).emi_no);card55.setTag(j);break;

                case 55: linAmortBtn12.setVisibility(View.VISIBLE);txtEmiNo56.setVisibility(View.VISIBLE);card56.setVisibility(View.VISIBLE);txtEmiNo56.setText("0" + mLoanEmisArrayList.get(j).emi_no);card56.setTag(j);break;
                case 56: linAmortBtn12.setVisibility(View.VISIBLE);txtEmiNo57.setVisibility(View.VISIBLE);card57.setVisibility(View.VISIBLE);txtEmiNo57.setText("0" + mLoanEmisArrayList.get(j).emi_no);card57.setTag(j);break;
                case 57: linAmortBtn12.setVisibility(View.VISIBLE);txtEmiNo58.setVisibility(View.VISIBLE);card58.setVisibility(View.VISIBLE);txtEmiNo58.setText("0" + mLoanEmisArrayList.get(j).emi_no);card58.setTag(j);break;
                case 58: linAmortBtn12.setVisibility(View.VISIBLE);txtEmiNo59.setVisibility(View.VISIBLE);card59.setVisibility(View.VISIBLE);txtEmiNo59.setText("0" + mLoanEmisArrayList.get(j).emi_no);card59.setTag(j);break;
                case 59: linAmortBtn12.setVisibility(View.VISIBLE);txtEmiNo60.setVisibility(View.VISIBLE);card60.setVisibility(View.VISIBLE);txtEmiNo60.setText("0" + mLoanEmisArrayList.get(j).emi_no);card60.setTag(j);break;

                case 60: linAmortBtn13.setVisibility(View.VISIBLE);txtEmiNo61.setVisibility(View.VISIBLE);card61.setVisibility(View.VISIBLE);txtEmiNo61.setText("0" + mLoanEmisArrayList.get(j).emi_no);card61.setTag(j);break;
                case 61: linAmortBtn13.setVisibility(View.VISIBLE);txtEmiNo62.setVisibility(View.VISIBLE);card62.setVisibility(View.VISIBLE);txtEmiNo62.setText("0" + mLoanEmisArrayList.get(j).emi_no);card62.setTag(j);break;
                case 62: linAmortBtn13.setVisibility(View.VISIBLE);txtEmiNo63.setVisibility(View.VISIBLE);card63.setVisibility(View.VISIBLE);txtEmiNo63.setText("0" + mLoanEmisArrayList.get(j).emi_no);card63.setTag(j);break;
                case 63: linAmortBtn13.setVisibility(View.VISIBLE);txtEmiNo64.setVisibility(View.VISIBLE);card64.setVisibility(View.VISIBLE);txtEmiNo64.setText("0" + mLoanEmisArrayList.get(j).emi_no);card64.setTag(j);break;
                case 64: linAmortBtn13.setVisibility(View.VISIBLE);txtEmiNo65.setVisibility(View.VISIBLE);card65.setVisibility(View.VISIBLE);txtEmiNo65.setText("0" + mLoanEmisArrayList.get(j).emi_no);card65.setTag(j);break;

                case 65: linAmortBtn14.setVisibility(View.VISIBLE);txtEmiNo66.setVisibility(View.VISIBLE);card66.setVisibility(View.VISIBLE);txtEmiNo66.setText("0" + mLoanEmisArrayList.get(j).emi_no);card66.setTag(j);break;
                case 66: linAmortBtn14.setVisibility(View.VISIBLE);txtEmiNo67.setVisibility(View.VISIBLE);card67.setVisibility(View.VISIBLE);txtEmiNo67.setText("0" + mLoanEmisArrayList.get(j).emi_no);card67.setTag(j);break;
                case 67: linAmortBtn14.setVisibility(View.VISIBLE);txtEmiNo68.setVisibility(View.VISIBLE);card68.setVisibility(View.VISIBLE);txtEmiNo68.setText("0" + mLoanEmisArrayList.get(j).emi_no);card68.setTag(j);break;
                case 68: linAmortBtn14.setVisibility(View.VISIBLE);txtEmiNo69.setVisibility(View.VISIBLE);card69.setVisibility(View.VISIBLE);txtEmiNo69.setText("0" + mLoanEmisArrayList.get(j).emi_no);card69.setTag(j);break;
                case 69: linAmortBtn14.setVisibility(View.VISIBLE);txtEmiNo70.setVisibility(View.VISIBLE);card70.setVisibility(View.VISIBLE);txtEmiNo70.setText("0" + mLoanEmisArrayList.get(j).emi_no);card70.setTag(j);break;

                    }
                }


                selectedAmort(amortlistsize-1);
//                adapter = new AmortAdapter(mLoanEmisArrayList, context, getActivity());
//                rvAmort.setAdapter(adapter);

            } else {
                String message = jsonDataO.getString("message");
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            String className = this.getClass().getSimpleName();
            String name = new Object() {
            }.getClass().getEnclosingMethod().getName();
            String errorMsg = e.getMessage();
            String errorMsgDetails = e.getStackTrace().toString();
            String errorLine = String.valueOf(e.getStackTrace()[0]);
            Globle.ErrorLog(getActivity(), className, name, errorMsg, errorMsgDetails, errorLine);
        }

    }

    @Override
    public void onClick(View v) {

        try {
            switch (v.getId()) {

                case R.id.linPayBtn:
                    if(txtBtnText.getText().toString().contains("EMI History")){

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
                    }else {
                        try {
                            String url = MainActivity.mainUrl + "dashboard/ammortisation";
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("lead_id", lead_id);
                            if (!Globle.isNetworkAvailable(context)) {
                                Toast.makeText(context, R.string.please_check_your_network_connection, Toast.LENGTH_SHORT).show();
                            } else {
                                VolleyCall volleyCall = new VolleyCall();
                                volleyCall.sendRequest(context, url, null, mFragment, "getAmortDetails", params, MainActivity.auth_token);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//                    selectedAmort((Integer) card1.getTag());
                    break;

                case R.id.card1: selectedAmort((Integer) card1.getTag());break;
                case R.id.card2: selectedAmort((Integer) card2.getTag());break;
                case R.id.card3: selectedAmort((Integer) card3.getTag());break;
                case R.id.card4: selectedAmort((Integer) card4.getTag());break;
                case R.id.card5: selectedAmort((Integer) card5.getTag());break;
                case R.id.card6: selectedAmort((Integer) card6.getTag());break;
                case R.id.card7: selectedAmort((Integer) card7.getTag());break;
                case R.id.card8: selectedAmort((Integer) card8.getTag());break;
                case R.id.card9: selectedAmort((Integer) card9.getTag());break;
                case R.id.card10: selectedAmort((Integer) card10.getTag());break;
                case R.id.card11: selectedAmort((Integer) card11.getTag());break;
                case R.id.card12: selectedAmort((Integer) card12.getTag());break;
                case R.id.card13: selectedAmort((Integer) card13.getTag());break;
                case R.id.card14: selectedAmort((Integer) card14.getTag());break;
                case R.id.card15: selectedAmort((Integer) card15.getTag());break;
                case R.id.card16: selectedAmort((Integer) card16.getTag());break;
                case R.id.card17: selectedAmort((Integer) card17.getTag());break;
                case R.id.card18: selectedAmort((Integer) card18.getTag());break;
                case R.id.card19: selectedAmort((Integer) card19.getTag());break;
                case R.id.card20: selectedAmort((Integer) card20.getTag());break;
                case R.id.card21: selectedAmort((Integer) card21.getTag());break;
                case R.id.card22: selectedAmort((Integer) card22.getTag());break;
                case R.id.card23: selectedAmort((Integer) card23.getTag());break;
                case R.id.card24: selectedAmort((Integer) card24.getTag());break;
                case R.id.card25: selectedAmort((Integer) card25.getTag());break;
                case R.id.card26: selectedAmort((Integer) card26.getTag());break;
                case R.id.card27: selectedAmort((Integer) card27.getTag());break;
                case R.id.card28: selectedAmort((Integer) card28.getTag());break;
                case R.id.card29: selectedAmort((Integer) card29.getTag());break;
                case R.id.card30: selectedAmort((Integer) card30.getTag());break;
                case R.id.card31: selectedAmort((Integer) card31.getTag());break;
                case R.id.card32: selectedAmort((Integer) card32.getTag());break;
                case R.id.card33: selectedAmort((Integer) card33.getTag());break;
                case R.id.card34: selectedAmort((Integer) card34.getTag());break;
                case R.id.card35: selectedAmort((Integer) card35.getTag());break;
                case R.id.card36: selectedAmort((Integer) card36.getTag());break;
                case R.id.card37: selectedAmort((Integer) card37.getTag());break;
                case R.id.card38: selectedAmort((Integer) card38.getTag());break;
                case R.id.card39: selectedAmort((Integer) card39.getTag());break;
                case R.id.card40: selectedAmort((Integer) card40.getTag());break;
                case R.id.card41: selectedAmort((Integer) card41.getTag());break;
                case R.id.card42: selectedAmort((Integer) card42.getTag());break;
                case R.id.card43: selectedAmort((Integer) card43.getTag());break;
                case R.id.card44: selectedAmort((Integer) card44.getTag());break;
                case R.id.card45: selectedAmort((Integer) card45.getTag());break;
                case R.id.card46: selectedAmort((Integer) card46.getTag());break;
                case R.id.card47: selectedAmort((Integer) card47.getTag());break;
                case R.id.card48: selectedAmort((Integer) card48.getTag());break;
                case R.id.card49: selectedAmort((Integer) card49.getTag());break;
                case R.id.card50: selectedAmort((Integer) card50.getTag());break;
                case R.id.card51: selectedAmort((Integer) card51.getTag());break;
                case R.id.card52: selectedAmort((Integer) card52.getTag());break;
                case R.id.card53: selectedAmort((Integer) card53.getTag());break;
                case R.id.card54: selectedAmort((Integer) card54.getTag());break;
                case R.id.card55: selectedAmort((Integer) card55.getTag());break;
                case R.id.card56: selectedAmort((Integer) card56.getTag());break;
                case R.id.card57: selectedAmort((Integer) card57.getTag());break;
                case R.id.card58: selectedAmort((Integer) card58.getTag());break;
                case R.id.card59: selectedAmort((Integer) card59.getTag());break;
                case R.id.card60: selectedAmort((Integer) card60.getTag());break;
                case R.id.card61: selectedAmort((Integer) card61.getTag());break;
                case R.id.card62: selectedAmort((Integer) card62.getTag());break;
                case R.id.card63: selectedAmort((Integer) card63.getTag());break;
                case R.id.card64: selectedAmort((Integer) card64.getTag());break;
                case R.id.card65: selectedAmort((Integer) card65.getTag());break;
                case R.id.card66: selectedAmort((Integer) card66.getTag());break;
                case R.id.card67: selectedAmort((Integer) card67.getTag());break;
                case R.id.card68: selectedAmort((Integer) card68.getTag());break;
                case R.id.card69: selectedAmort((Integer) card69.getTag());break;
                case R.id.card70: selectedAmort((Integer) card70.getTag());break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void emiHistoryDialog(JSONObject mPath) {

        try {

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
            TextView txtPaymentMode =  dialogLayout.findViewById(R.id.txtPaymentMode);
            TextView txtTransactionStatus =  dialogLayout.findViewById(R.id.txtTransactionStatus);
            TextView txtPaymentAmount =  dialogLayout.findViewById(R.id.txtPaymentAmount);
            TextView txtPaymentDate =  dialogLayout.findViewById(R.id.txtPaymentDate);

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void selectedAmort(int pos) {

        if(linAmortTile.getVisibility()!= View.VISIBLE)
        {
            linAmortTile.setVisibility(View.VISIBLE);
        }
        txtEmiNo.setText("0"+mLoanEmisArrayList.get(pos).emi_no);
        txtEmiAmount.setText(mLoanEmisArrayList.get(pos).emi_amount);
        txtDueBy.setText(mLoanEmisArrayList.get(pos).proposed_payment_date);
        txtPaymentDate.setText(mLoanEmisArrayList.get(pos).actual_payment_date);
        txtPaymentStatus.setText(" "+ mLoanEmisArrayList.get(pos).statusMessage);
        if(mLoanEmisArrayList.get(pos).status.equals("0")) {
            txtBtnText.setText(" " + "Pre Pay");
        }
        else {
            txtBtnText.setText(" " + "EMI History");
        }
        linPayBtn.setTag(mLoanEmisArrayList.get(pos).loan_emi_id);
    }
}
