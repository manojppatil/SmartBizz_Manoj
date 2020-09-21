package com.smartbizz.newUI.fragments;


import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.smartbizz.R;
import com.smartbizz.Util.CommonUtil;
import com.smartbizz.Util.Constants;
import com.smartbizz.Util.PreferenceManager;
import com.smartbizz.Util.VerticalLineDecorator;
import com.smartbizz.newUI.adapter.PostCardAdapter;
import com.smartbizz.newUI.network.ApiConstants;
import com.smartbizz.newUI.network.NetworkManager;
import com.smartbizz.newUI.newViews.PostCardActivity;
import com.smartbizz.newUI.newViews.PostCardTabActivity;
import com.smartbizz.newUI.pojo.Category;
import com.smartbizz.newUI.pojo.Requests;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DynamicPostCardFragment extends BaseFragment {
    View view;
    String val;
    PostCardAdapter beatAdapter;
    List<Requests> mBeatsList;
    public static final int REQUEST = 112;
    public int beatCount = 0;
    private Category category;
    private TextView txtNoData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_postcard, container, false);
        txtNoData = view.findViewById(R.id.txtNoData);

        val = getArguments().getString("someInt");
        ((PostCardTabActivity) activity).recyclerView = view.findViewById(R.id.recycler_beat_view);
//        c.setText("Fragment - " + val);
        new DynamicPostCardFragment.AsyncTaskLoadLogo().execute();

        return view;
    }

    public static DynamicPostCardFragment addfrag(int val, Category category) {
        DynamicPostCardFragment fragment = new DynamicPostCardFragment();
        Bundle args = new Bundle();
        args.putString("someInt", category.getId());
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadData();
        BeatListAPI(val);
    }

    public void reloadData() {

        mBeatsList = null;
        beatAdapter = null;

        mBeatsList = new ArrayList<>();

        beatAdapter = new PostCardAdapter(activity, mBeatsList);

//        beatAdapter.setLoadMoreListener(() -> {
//
//            ((PostCardTabActivity) activity).recyclerView.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    BeatListAPI("3");
//                }
//            });
//            //Calling loadMore function in Runnable to fix the
//        });

        ((PostCardTabActivity) activity).recyclerView.setHasFixedSize(true);

        ((PostCardTabActivity) activity).recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        ((PostCardTabActivity) activity).recyclerView.addItemDecoration(new VerticalLineDecorator(2));
        ((PostCardTabActivity) activity).recyclerView.setAdapter(beatAdapter);

    }
    private void BeatListAPI(String id) {

        NetworkManager.getInstance(activity).getBrandPromotion(activity,id, response -> {
            if (response.isSuccess()) {
                JSONObject jsonObject = response.getResponse();
                if (jsonObject != null) {
                    JSONObject resultObj = jsonObject.optJSONObject(ApiConstants.Keys.RESULT);
                    if (resultObj != null) {
                        JSONArray categoryArray = resultObj.optJSONArray("category");
                        JSONArray requestsArray = resultObj.optJSONArray("requests");

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
                            beatAdapter.setMoreDataAvailable(false);
                            txtNoData.setVisibility(View.GONE);
                        }else{
                            txtNoData.setVisibility(View.VISIBLE);
                        }
                    }
                }
            } else {
                makeToast(response.getMessage());
            }
        });

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //do here
                } else {
                    Toast.makeText(activity, "The app was not allowed to read your store.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public class AsyncTaskLoadLogo extends AsyncTask<String, String, Bitmap> {
        private final static String TAG = "AsyncTaskLoadImage";

        public void AsyncTaskLoadImage() {
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmapLogo = null;
            try {
                URL url = new URL(ApiConstants.BASE_URL + "/" + PreferenceManager.getString(activity, Constants.PrefKeys.LOGO));
//                URL url = new URL("https://digiepost.in/assets/uploads/gallery/20/20_1596904590.jpg");
                try {
                    bitmapLogo = BitmapFactory.decodeStream((InputStream) url.getContent()).copy(Bitmap.Config.ARGB_8888, true);
                } catch (NullPointerException e) {
                    bitmapLogo = null;
                    e.printStackTrace();
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
            return bitmapLogo;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            try {

                float aspectRatio = bitmap.getWidth() /
                        (float) bitmap.getHeight();

                int width = 250;
                int height = Math.round(width / aspectRatio);

                bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);

                bitmap = getCircularBitmap(bitmap);

                bitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
                // Add a border around circular bitmap
                bitmap = addBorderToCircularBitmap(bitmap, 5, Color.WHITE);
                // Add a shadow around circular bitmap
//                bitmap = addShadowToCircularBitmap(bitmap, 4, Color.LTGRAY);
                // Set the ImageView image as drawable object
                ((PostCardTabActivity)activity).bitmapLogo = bitmap;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected Bitmap getCircularBitmap(Bitmap srcBitmap) {
        // Calculate the circular bitmap width with border
        int squareBitmapWidth = Math.min(srcBitmap.getWidth(), srcBitmap.getHeight());
        // Initialize a new instance of Bitmap
        Bitmap dstBitmap = Bitmap.createBitmap (
                squareBitmapWidth, // Width
                squareBitmapWidth, // Height
                Bitmap.Config.ARGB_8888 // Config
        );
        Canvas canvas = new Canvas(dstBitmap);
        // Initialize a new Paint instance
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Rect rect = new Rect(0, 0, squareBitmapWidth, squareBitmapWidth);
        RectF rectF = new RectF(rect);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // Calculate the left and top of copied bitmap
        float left = (squareBitmapWidth-srcBitmap.getWidth())/2;
        float top = (squareBitmapWidth-srcBitmap.getHeight())/2;
        canvas.drawBitmap(srcBitmap, left, top, paint);
        // Free the native object associated with this bitmap.
        srcBitmap.recycle();
        // Return the circular bitmap
        return dstBitmap;
    }

    protected Bitmap addBorderToCircularBitmap(Bitmap srcBitmap, int borderWidth, int borderColor) {
        // Calculate the circular bitmap width with border
        int dstBitmapWidth = srcBitmap.getWidth()+borderWidth*2;
        // Initialize a new Bitmap to make it bordered circular bitmap
        Bitmap dstBitmap = Bitmap.createBitmap(dstBitmapWidth,dstBitmapWidth, Bitmap.Config.ARGB_8888);
        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(dstBitmap);
        // Draw source bitmap to canvas
        canvas.drawBitmap(srcBitmap, borderWidth, borderWidth, null);
        // Initialize a new Paint instance to draw border
        Paint paint = new Paint();
        paint.setColor(borderColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(borderWidth);
        paint.setAntiAlias(true);
        canvas.drawCircle(
                canvas.getWidth() / 2, // cx
                canvas.getWidth() / 2, // cy
                canvas.getWidth()/2 - borderWidth / 2, // Radius
                paint // Paint
        );
        // Free the native object associated with this bitmap.
        srcBitmap.recycle();
        // Return the bordered circular bitmap
        return dstBitmap;
    }
    // Custom method to add a shadow around circular bitmap
    protected Bitmap addShadowToCircularBitmap(Bitmap srcBitmap, int shadowWidth, int shadowColor){
        // Calculate the circular bitmap width with shadow
        int dstBitmapWidth = srcBitmap.getWidth()+shadowWidth*2;
        Bitmap dstBitmap = Bitmap.createBitmap(dstBitmapWidth,dstBitmapWidth, Bitmap.Config.ARGB_8888);
        // Initialize a new Canvas instance
        Canvas canvas = new Canvas(dstBitmap);
        canvas.drawBitmap(srcBitmap, shadowWidth, shadowWidth, null);
        // Paint to draw circular bitmap shadow
        Paint paint = new Paint();
        paint.setColor(shadowColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(shadowWidth);
        paint.setAntiAlias(true);
        // Draw the shadow around circular bitmap
        canvas.drawCircle (
                dstBitmapWidth / 2, // cx
                dstBitmapWidth / 2, // cy
                dstBitmapWidth / 2 - shadowWidth / 2, // Radius
                paint // Paint
        );
        srcBitmap.recycle();
        return dstBitmap;
    }

}