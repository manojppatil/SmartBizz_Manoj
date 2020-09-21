package com.smartbizz.newUI.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.smartbizz.R;
import com.smartbizz.Util.CommonUtil;

public class EduvanzDocumentView extends LinearLayout implements View.OnClickListener {
    private String url;
    private LinearLayout layoutDelete, layoutView;
    private ImageView ivDocument;

    public EduvanzDocumentView(Context context) {
        super(context);
        init(context, null);
    }

    public EduvanzDocumentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_document, this);
        layoutDelete = view.findViewById(R.id.layoutDelete);
        layoutView = view.findViewById(R.id.layoutView);
        ivDocument = view.findViewById(R.id.ivDocument);
        layoutDelete.setOnClickListener(this);
        layoutView.setOnClickListener(this);
        if (attrs != null) {
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layoutDelete:
                CommonUtil.makeToast(getContext(), "Delete Under development");
                break;
            case R.id.layoutView:
                CommonUtil.makeToast(getContext(), "View Under development");
                break;
        }
    }
}
