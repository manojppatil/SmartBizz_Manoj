package com.smartbizz.newUI.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.smartbizz.R;
import com.smartbizz.Util.CommonUtil;
import com.smartbizz.Util.VerticalLineDecorator;
import com.smartbizz.newUI.MainApplication;
import com.smartbizz.newUI.adapter.BrandDesignAdapter;
import com.smartbizz.newUI.network.ApiConstants;
import com.smartbizz.newUI.network.NetworkManager;
import com.smartbizz.newUI.newViews.BrandDesigningActivity;
import com.smartbizz.newUI.pojo.Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BrandDesignFragment extends BaseFragment implements View.OnClickListener {

    public static Context context;
    public static Fragment mFragment;
    MainApplication mainApplication;
    public static FragmentTransaction transaction;

    public static View view;

    //    public RecyclerView recyclerBrandDesign;
    public BrandDesignAdapter beatAdapter;
    List<Requests> mBeatsList;
    public int beatCount = 0;

    public BrandDesignFragment() {
        // Required empty public constructor
    }

    public static BrandDesignFragment newInstance() {
        BrandDesignFragment fragment = new BrandDesignFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_brand_design, container, false);

        context = getContext();
        mFragment = new BrandDesignFragment();
        transaction = getFragmentManager().beginTransaction();
        mainApplication = new MainApplication();

        ((BrandDesigningActivity) context).recyclerBrandDesign = view.findViewById(R.id.recyclerBrandDesign);

        reloadData();

        BeatListAPI();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void reloadData() {

        mBeatsList = null;
        beatAdapter = null;

        mBeatsList = new ArrayList<>();

        beatAdapter = new BrandDesignAdapter(activity, mBeatsList);

        beatAdapter.setLoadMoreListener(() -> {

            ((BrandDesigningActivity) context).recyclerBrandDesign.post(new Runnable() {
                @Override
                public void run() {

                    BeatListAPI("3");
                }
            });
            //Calling loadMore function in Runnable to fix the
        });

        ((BrandDesigningActivity) context).recyclerBrandDesign.setHasFixedSize(true);

        //LinearLayoutManager layoutManager = new LinearLayoutManager(context) {
        //            @Override
        //            public boolean canScrollVertically() {
        //                return false;
        //            }
        //        };

        ((BrandDesigningActivity) context).recyclerBrandDesign.setLayoutManager(new LinearLayoutManager(context));
        ((BrandDesigningActivity) context).recyclerBrandDesign.addItemDecoration(new VerticalLineDecorator(2));
        ((BrandDesigningActivity) context).recyclerBrandDesign.setAdapter(beatAdapter);

    }

    private void BeatListAPI() {

        NetworkManager.getInstance(activity).getBrandPromotion(activity,"", response -> {
            if (response.isSuccess()) {
                JSONObject jsonObject = response.getResponse();
                if (jsonObject != null) {
                    JSONObject resultObj = jsonObject.optJSONObject(ApiConstants.Keys.RESULT);
                    if (resultObj != null) {
                        JSONArray categoryArray = resultObj.optJSONArray("category");
                        JSONArray requestsArray = resultObj.optJSONArray("requests");
//                            if (categoryArray != null && categoryArray.length() > 0) {
//                                int size = categoryArray.length();
//                                for (int i = 0; i < size; i++) {
//                                    JSONObject categoryJson = categoryArray.optJSONObject(i);
//                                    if(categoryJson != null) {
//                                        Category application = new Category(categoryArray.optJSONObject(i));
//                                        disbursedList.add(application);
//                                    }
//                                }//resultObj.optString("url")
//                            }

                        if (requestsArray != null && requestsArray.length() > 0) {
//                            mBeatsList.remove(mBeatsList.size() - 1);
                            beatCount = mBeatsList.size();

                            int size = requestsArray.length();
                            for (int i = 0; i < size; i++) {
                                JSONObject applicationJson = requestsArray.optJSONObject(i);
                                if (applicationJson != null) {
                                    Requests myrequest = new Requests(requestsArray.optJSONObject(i));
                                    mBeatsList.add(myrequest);
                                }
                            }
                            if (beatCount == mBeatsList.size()) {
                                beatAdapter.setMoreDataAvailable(false);
                                CommonUtil.makeToast(activity, "No More Data Available");
                            }
                            beatAdapter.notifyDataChanged();
                        }
                    }
                }
            } else {
                makeToast(response.getMessage());
            }
        });

    }

    private void BeatListAPI(String offset) {

        mBeatsList.add(new Requests("load"));
        beatAdapter.notifyItemInserted(mBeatsList.size() - 1);

        if (!NetworkManager.isNetworkAvailable(activity)) {
            CommonUtil.makeToast(activity, activity.getResources().getString(R.string.please_check_your_network_connection));

        } else {

            NetworkManager.getInstance(activity).getBrandPromotion(activity, "", response -> {
                if (response.isSuccess()) {
                    JSONObject jsonObject = response.getResponse();
                    if (jsonObject != null) {
                        JSONObject resultObj = jsonObject.optJSONObject(ApiConstants.Keys.RESULT);
                        if (resultObj != null) {
                            JSONArray categoryArray = resultObj.optJSONArray("category");
                            JSONArray requestsArray = resultObj.optJSONArray("requests");
//                            if (categoryArray != null && categoryArray.length() > 0) {
//                                int size = categoryArray.length();
//                                for (int i = 0; i < size; i++) {
//                                    JSONObject categoryJson = categoryArray.optJSONObject(i);
//                                    if(categoryJson != null) {
//                                        Category application = new Category(categoryArray.optJSONObject(i));
//                                        disbursedList.add(application);
//                                    }
//                                }//resultObj.optString("url")
//                            }
                            beatCount = mBeatsList.size();

                            if (requestsArray != null && requestsArray.length() > 0) {
                                mBeatsList.remove(mBeatsList.size() - 1);

                                int size = requestsArray.length();
                                for (int i = 0; i < size; i++) {
                                    JSONObject applicationJson = requestsArray.optJSONObject(i);
                                    if (applicationJson != null) {
                                        Requests myrequest = new Requests(requestsArray.optJSONObject(i));
                                        mBeatsList.add(myrequest);
                                    }
                                }
                                if (beatCount == mBeatsList.size()) {
                                    beatAdapter.setMoreDataAvailable(false);
                                    CommonUtil.makeToast(activity, "No More Data Available");
                                }
                                beatAdapter.setMoreDataAvailable(false);

                                beatAdapter.notifyDataChanged();
                            }
                        }
                    }
                } else {
                    makeToast(response.getMessage());
                }
            });

//            DialogUtil.showProgressDialog(activity);
//            NetworkManager.getInstance(activity).getNotification(activity, offset, response -> {
//                DialogUtil.dismissProgressDialog();
//                {
//                    if (response.isSuccess()) {
//
//                        JSONObject jobject = response.getResponse().optJSONObject(ApiConstants.Keys.RESULT);
//                        JSONArray result = jobject.optJSONArray("notifications");
//                        try {
//                            beatCount = Integer.parseInt(jobject.optString("total"));
//                        } catch (NumberFormatException e) {
//                            e.printStackTrace();
//                        }
//
//                        if (result != null) {
//                            mBeatsList.remove(mBeatsList.size() - 1);
//
//                            if (result != null) {
//                                if (result != null && result.length() > 0) {
//                                    int size = result.length();
//                                    for (int i = 0; i < size; i++) {
//                                        JSONObject applicationJson = result.optJSONObject(i);
//                                        if (applicationJson != null) {
//                                            mNotification application = new mNotification(result.optJSONObject(i));
//                                            mBeatsList.add(application);
//                                        }
//                                    }
//                                    if (beatCount == mBeatsList.size()) {
//                                        beatAdapter.setMoreDataAvailable(false);
//                                        CommonUtil.makeToast(activity, "No More Data Available");
//                                    }
//                                    beatAdapter.notifyDataChanged();
//                                }
//                            }
//                        }
//                    } else {
////                        makeToast(response.getMessage());
//                    }
//                }
//
//            });
        }
    }


//    private void BeatListAPI() {
//
//        JSONObject headers = new JSONObject();
//        try {
//            headers.put("user_id",PreferenceManager.getString(activity, Constants.PrefKeys.LOGGED_ID));
//            headers.put("limit", "10");
//            headers.put("offset", "0");
//            headers.put("source", "mobile");
//
//            JSONObject request = new JSONObject();
//            try {
//                request.put("start_date", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
//                request.put("end_date", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
//
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            headers.put("filter", request);
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        if (!Globle.isNetworkAvailable(activity)) {
//            CommonUtil.makeToast(activity, getResources().getString(R.string.please_check_your_network_connection));
//        }else {
//            DialogUtil.showProgressDialog(activity);
//            NetworkManager.getInstance(activity).getBeatlist(activity, headers, response -> {
//                DialogUtil.dismissProgressDialog();
//                try {
//                    if (response.getResponse().getBoolean("success") == ApiConstants.StatusCode.CODE_1) {
//                        JSONObject jsonObject = response.getResponse();
//                        if (jsonObject == null)
//                            return;
//
//                        JSONObject resultObj = jsonObject.optJSONObject("data");
//
//                        beatCount = (int) resultObj.opt("total");
//
//                        if (resultObj != null) {
//                            JSONArray leadsArray = resultObj.optJSONArray("beat_plan");
//                            if (leadsArray != null && leadsArray.length() > 0) {
//                                int size = leadsArray.length();
//                                for (int i = 0; i < size; i++) {
//                                    JSONObject applicationJson = leadsArray.optJSONObject(i);
//                                    if (applicationJson != null) {
//                                        MBeats application = new MBeats(leadsArray.optJSONObject(i));
//                                        mBeatsList.add(application);
//                                    }
//                                }
//                                if (beatCount == mBeatsList.size()) {
//                                    beatAdapter.setMoreDataAvailable(false);
//                                }
//                                beatAdapter.notifyDataChanged();
//                            }
//                        }
//
//                    } else {
//
//                        String error_type = response.getResponse().getString("response_type").toLowerCase();
//
//                        switch (error_type) {
//                            case "access_error":
//
//                                CommonUtil.makeToast(activity, response.getResponse().getString("info"));
//                                PreferenceManager.clearAll(activity);
////                                Intent intent = new Intent(activity, SignIn.class);
////                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////                                startActivity(intent);
////                                activity.finish();
//
//                                break;
//
//                            case "db_error":
//
//                                CommonUtil.makeToast(activity, response.getResponse().optString("info"));
//
//                                break;
//
//                            case "invalid_credentails":
//
//                                CommonUtil.makeToast(activity, response.getResponse().optString("info"));
//
//                                break;
//
//                            case "validation_error":
//
//                                JSONObject ErrorField = response.getResponse().optJSONObject("data").optJSONObject("error");
//
//
//                                break;
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//    }

//    public void showBottomSheetDialogFragment(MBeats mBeats) {
//        SaveBeatBottomSheet saveBeatBottomSheet = new SaveBeatBottomSheet(mBeats);
//        saveBeatBottomSheet.show(getActivity().getSupportFragmentManager(), saveBeatBottomSheet.getTag());
//    }

//    private void BeatListAPI(String offset) {
//
//        mBeatsList.add(new MBeats("load"));
//        beatAdapter.notifyItemInserted(mBeatsList.size()-1);
//
//        JSONObject headers = new JSONObject();
//        try {
//            headers.put("user_id",PreferenceManager.getString(activity, Constants.PrefKeys.LOGGED_ID));
//            headers.put("limit", "10");
//            headers.put("offset", offset);
//            headers.put("source", "mobile");
//
//            JSONObject request = new JSONObject();
//            try {
//                request.put("start_date", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
//                request.put("end_date", new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
//
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            headers.put("filter", request);
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        if (!Globle.isNetworkAvailable(activity)) {
//            CommonUtil.makeToast(activity, getResources().getString(R.string.please_check_your_network_connection));
//        }else {
//            DialogUtil.showProgressDialog(activity);
//            NetworkManager.getInstance(activity).getBeatlist(activity, headers, response -> {
//                DialogUtil.dismissProgressDialog();
//                try {
//                    if (response.getResponse().getBoolean("success") == ApiConstants.StatusCode.CODE_1) {
//                        JSONObject jsonObject = response.getResponse();
//                        if (jsonObject == null)
//                            return;
//
//                        mBeatsList.remove(mBeatsList.size() - 1);
//
//                        JSONObject resultObj = jsonObject.optJSONObject("data");
//                        beatCount = (int) resultObj.opt("total");
//
//                        if (resultObj != null) {
////                        JSONArray leadsArray = resultObj.optJSONArray("data");
//                            JSONArray leadsArray = resultObj.optJSONArray("beat_plan");
//                            if (leadsArray != null && leadsArray.length() > 0) {
//                                int size = leadsArray.length();
//                                for (int i = 0; i < size; i++) {
//                                    JSONObject applicationJson = leadsArray.optJSONObject(i);
//                                    if (applicationJson != null) {
//                                        MBeats application = new MBeats(leadsArray.optJSONObject(i));
//                                        mBeatsList.add(application);
//                                    }
//                                }
//                                if (beatCount == mBeatsList.size()) {
//                                    beatAdapter.setMoreDataAvailable(false);
//                                    CommonUtil.makeToast(context, "No More Data Available");
////                                Toast.makeText(context,"No More Data Available",Toast.LENGTH_LONG).show();
//                                }
//                                beatAdapter.notifyDataChanged();
//                            }
//                        }
//
//                    } else {
//
//                        String error_type = response.getResponse().getString("response_type").toLowerCase();
//
//                        switch (error_type) {
//                            case "access_error":
//
//                                CommonUtil.makeToast(activity, response.getResponse().getString("info"));
//                                PreferenceManager.clearAll(activity);
////                                Intent intent = new Intent(activity, SignIn.class);
////                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////                                startActivity(intent);
////                                activity.finish();
//
//                                break;
//
//                            case "db_error":
//
//                                CommonUtil.makeToast(activity, response.getResponse().optString("info"));
//
//                                break;
//
//                            case "invalid_credentails":
//
//                                CommonUtil.makeToast(activity, response.getResponse().optString("info"));
//
//                                break;
//
//                            case "validation_error":
//
//                                JSONObject ErrorField = response.getResponse().optJSONObject("data").optJSONObject("error");
//                                CommonUtil.makeToast(activity, response.getResponse().getString("info"));
//
//                                break;
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
        }
    }

}
