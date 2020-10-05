package com.smartbizz.newUI.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smartbizz.R;
import com.smartbizz.Util.CommonUtil;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.newUI.network.ApiConstants;
import com.smartbizz.newUI.network.NetworkManager;
import com.smartbizz.newUI.pojo.Category;
import com.smartbizz.newUI.pojo.Requests;
import com.smartbizz.newUI.pojo.SMSTemplates;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SmsTempaltes extends Fragment {
    View view;
    RecyclerView template_recycler;
    List<SMSTemplates> smsTemplates = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sms_tempaltes, container, false);
        template_recycler = view.findViewById(R.id.template_catg_recycler);
        getTemplateCatgList();
        return view;
    }

    public void getTemplateCatgList() {
        DialogUtil.showProgressDialog((Activity) getContext());
//        smsTemplates.clear();
//        smsTemplates.clear();//digiepost.in
        NetworkManager.getInstance(getContext()).getTemplatesCatgList(getContext(), response -> {
            if (response.isSuccess()) {
                JSONObject jsonObject = response.getResponse();
                if (jsonObject != null) {
                    JSONObject resultObj = jsonObject.optJSONObject(ApiConstants.Keys.RESULT);
                    if (resultObj != null) {
                        JSONArray idArray = resultObj.optJSONArray("id");
                        JSONArray categoryArray = resultObj.optJSONArray("category");
                        JSONArray smsArray = resultObj.optJSONArray("sms");

                        if (idArray != null && idArray.length() > 0) {
                            int size = idArray.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject categoryJson = idArray.optJSONObject(i);
                                if (categoryJson != null) {
                                    SMSTemplates templates = new SMSTemplates(idArray.optJSONObject(i));
                                    smsTemplates.add(templates);
                                }
                            }//resultObj.optString("url")

//                            setupViewPager();
                        }

                        if (categoryArray != null && categoryArray.length() > 0) {
                            int size = categoryArray.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject categoryJson = categoryArray.optJSONObject(i);
                                if (categoryJson != null) {
                                    SMSTemplates templates = new SMSTemplates(categoryArray.optJSONObject(i));
                                    smsTemplates.add(templates);
                                }
                            }//resultObj.optString("url")

//                            setupViewPager();
                        }

                        if (smsArray != null && smsArray.length() > 0) {
                            int size = smsArray.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject categoryJson = smsArray.optJSONObject(i);
                                if (categoryJson != null) {
                                    SMSTemplates templates = new SMSTemplates(smsArray.optJSONObject(i));
                                    smsTemplates.add(templates);
                                }
                            }//resultObj.optString("url")

//                            setupViewPager();
                        } else {
                            makeToast("No Templates found.");
                        }
                        setUpRecyclerView();
                    }
                }
            } else {
                makeToast(response.getMessage());
            }
            dismissProgressBar();
        });
    }

    private void setUpRecyclerView() {

    }

    private void dismissProgressBar() {
        new Handler().postDelayed(() -> DialogUtil.dismissProgressDialog(), 1000);
    }

    public void makeToast(String message) {
        CommonUtil.makeToast(getContext(), message);
    }
}