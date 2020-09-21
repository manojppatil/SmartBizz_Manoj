package com.smartbizz.newUI.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.recyclerview.widget.RecyclerView;

import com.smartbizz.R;
import com.smartbizz.Util.DialogUtil;
import com.smartbizz.newUI.network.ApiConstants;
import com.smartbizz.newUI.network.NetworkManager;
import com.smartbizz.newUI.newViews.BaseActivity;
import com.smartbizz.newUI.newViews.EditImageActivity;
import com.smartbizz.newUI.newViews.BrandDesigningActivity;
import com.smartbizz.newUI.pojo.Requests;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.PhotoEditor;
import ja.burhanrashid52.photoeditor.PhotoEditorView;
import ja.burhanrashid52.photoeditor.SaveSettings;
import ja.burhanrashid52.photoeditor.TextStyleBuilder;
import ja.burhanrashid52.photoeditor.ViewType;

import static com.smartbizz.Util.TextEditorDialogFragment.TAG;

public class BrandDesignAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_MOVIE = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<Requests> mBeatsList;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

//    @Nullable
//    @VisibleForTesting
//    Uri mSaveImageUri;

    /*
     * isLoading - to set the remote loading and complete status to fix back to back load more call
     * isMoreDataAvailable - to set whether more data from server available or not.
     * It will prevent useless load more request even after all the server data loaded
     * */

    public BrandDesignAdapter(Context context, List<Requests> mBeatsList) {
        this.context = context;
        this.mBeatsList = mBeatsList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == TYPE_MOVIE) {
            return new MovieHolder(inflater.inflate(R.layout.view_brands_adapter, parent, false));
        } else {
            return new LoadHolder(inflater.inflate(R.layout.row_load, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position >= getItemCount() - 1 && isMoreDataAvailable && !isLoading && loadMoreListener != null) {
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if (getItemViewType(position) == TYPE_MOVIE) {
            ((MovieHolder) holder).bindData(mBeatsList.get(position), position);
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
//        if( mBeatsList.get(position).type.equals("movie")){
//            return TYPE_MOVIE;
//        }else{
//            return TYPE_LOAD;
//        }

        if (mBeatsList.get(position).name1.equals("load")) {
            return TYPE_LOAD;
        } else {
            return TYPE_MOVIE;
        }
    }

    @Override
    public int getItemCount() {
        return mBeatsList.size();
    }

    static class MovieHolder extends RecyclerView.ViewHolder implements OnPhotoEditorListener, View.OnClickListener {

        PhotoEditorView photoView;

        PhotoEditor mPhotoEditor;

        Button btnEdit, btnContinue;


        @Nullable
        @VisibleForTesting
        Uri mSaveImageUri;


        public MovieHolder(View itemView) {
            super(itemView);


            photoView = itemView.findViewById(R.id.photoView);

            mPhotoEditor = new PhotoEditor.Builder(context, photoView)
                    .setPinchTextScalable(true) // set flag to make text scalable when pinch
                    //.setDefaultTextTypeface(mTextRobotoTf)
                    //.setDefaultEmojiTypeface(mEmojiTypeFace)
                    .build(); // build photo editor sdk

            mPhotoEditor.setOnPhotoEditorListener(this);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnContinue = itemView.findViewById(R.id.btnContinue);
        }

        void bindData(Requests mBeats, int position) {

            mPhotoEditor.clearAllViews();
            Picasso.with(context).load(ApiConstants.BASE_URL+"/"+mBeats.getImgPath()).placeholder(context.getResources().getDrawable(R.drawable.ic_placeholder_banner)).into(photoView.getSource());
            photoView.setTag(photoView.getSource());

            try {
                final TextStyleBuilder styleBuilder = new TextStyleBuilder();
                styleBuilder.withTextColor(-16669848);
//                styleBuilder.withTextColor(16581375);
                styleBuilder.withTextSize(25f);

                mPhotoEditor.addText("Vijay", styleBuilder);
            } catch (Exception e) {
                e.printStackTrace();
            }


            btnEdit.setOnClickListener(v -> {

//            Intent intent = new Intent(context, EditImageActivity.class);
//            intent.putExtra("imgUri", photoView.getTag().toString());
//            ((BrandDesigningActivity)context).startActivity(intent);

                DialogUtil.showProgressDialog(((BrandDesigningActivity) context));
                NetworkManager.getInstance(context).putUserAction(context,mBeats.getId(),"0", response -> {
                    if (response.isSuccess()) {
                        DialogUtil.dismissProgressDialog();
                        ((BrandDesigningActivity) context).makeToast(response.getResponse().optString("result"));
                        ((BrandDesigningActivity)context).onStart();
                    }else{
                        ((BrandDesigningActivity) context).makeToast(response.getResponse().optString("result"));
                    }
                });

            });

            btnContinue.setOnClickListener(v -> {

//            Intent intent = new Intent(context, EditImageActivity.class);
//            intent.putExtra("imgUri", photoView.getTag().toString());
//            ((BrandDesigningActivity)context).startActivity(intent);

                DialogUtil.showProgressDialog(((BrandDesigningActivity) context));
                NetworkManager.getInstance(context).putUserAction(context,mBeats.getId(),"1", response -> {
                    DialogUtil.dismissProgressDialog();
                    if (response.isSuccess()) {
                        ((BrandDesigningActivity) context).makeToast(response.getResponse().optString("result"));
                        ((BrandDesigningActivity)context).onStart();
                    }else{
                        ((BrandDesigningActivity) context).makeToast(response.getResponse().optString("result"));
                    }
                });

            });
        }

        @Override
        public void onEditTextChangeListener(View rootView, String text, int colorCode) {

        }

        @Override
        public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
            Log.d(TAG, "onAddViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
        }

        @Override
        public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {
            Log.d(TAG, "onRemoveViewListener() called with: viewType = [" + viewType + "], numberOfAddedViews = [" + numberOfAddedViews + "]");
        }

        @Override
        public void onStartViewChangeListener(ViewType viewType) {
            Log.d(TAG, "onStartViewChangeListener() called with: viewType = [" + viewType + "]");
//            mOwnerRecyclerView.setLayoutFrozen(true);
//            ((BrandDesigningActivity)context).recyclerBrandDesign.setLayoutFrozen(true);
//            ((PostCardFragment)context).recyclerBrandDesign.setLayoutFrozen(true);
//            ((PostCardFragment) context).getFragmentManager().findFragmentById(R.id.recycler_beat_view)
//            (RecyclerView) (((PostCardFragment) context).getFragmentManager().findFragmentById(R.id.recycler_beat_view))
//
//            ((PostCardFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMapAsync(this);
            ((BrandDesigningActivity)context).recyclerBrandDesign.setLayoutFrozen(true);

        }

        @Override
        public void onStopViewChangeListener(ViewType viewType) {
            Log.d(TAG, "onStopViewChangeListener() called with: viewType = [" + viewType + "]");
//            mOwnerRecyclerView.setLayoutFrozen(false);
            ((BrandDesigningActivity)context).recyclerBrandDesign.setLayoutFrozen(false);
        }

        @Override
        public void onClick(View view) {

        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder {
        public LoadHolder(View itemView) {
            super(itemView);

        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    public void notifyDataChanged() {
        notifyDataSetChanged();
        isLoading = false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public interface ActivityAdapterInterface {
        public void prepareSelection(View v, int position);
    }

}
