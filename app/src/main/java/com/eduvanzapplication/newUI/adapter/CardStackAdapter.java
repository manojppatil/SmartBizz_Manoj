package com.eduvanzapplication.newUI.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eduvanzapplication.R;
import com.eduvanzapplication.newUI.pojo.MLeads;

import java.util.ArrayList;
import java.util.List;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ViewHolder>{
    List<MLeads> list = new ArrayList();
    Context context;
    FragmentActivity activity;

    public List<MLeads> getSpots() {
        return list;
    }

    public void setSpots(List<MLeads> list) {
        this.list = list;
    }

    public CardStackAdapter(List<MLeads> list, Context context, FragmentActivity activity ){
        this.list = list;
        this.context = context;
        this.activity = activity;
    }
    @NonNull
    @Override
    public CardStackAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.applications_card_view,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
            final MLeads mLeads = list.get(i);

            viewHolder.InsttName.setText("Institute "+i);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), mLeads.first_name, Toast.LENGTH_SHORT).show();
                }
            });
    }






    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView InsttName;
//        TextView city;
//        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            InsttName = itemView.findViewById(R.id.txtInstitute);
//            city = itemView.findViewById(R.id.item_city);
////            image  =itemView.findViewById(R.id.item_image);
//            container = itemView.findViewById(R.id.containerr);


        }
    }
}

