package com.paya.paragon.adapter;



import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.activity.localExpert.ActivityLocalExpertListing;
import com.paya.paragon.api.localExpertCategory.LocalExpertCategoryData;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;


@SuppressWarnings("HardCodedStringLiteral")
public class AdapterLocalExpertDashboard extends RecyclerView.Adapter<AdapterLocalExpertDashboard.LocalExpertViewHolder> {

    private ArrayList<LocalExpertCategoryData> mExpertDashboardModels;
    private Context mContext;
    private float mHeight;
    String imageUrl;

    public AdapterLocalExpertDashboard(Context context, ArrayList<LocalExpertCategoryData> expertDashboardModels, float height, String imageUrl) {
        this.mContext = context;
        this.mExpertDashboardModels = expertDashboardModels;
        this.mHeight = height;
        this.imageUrl = imageUrl;
    }

    @Override
    public LocalExpertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_expert_dashboard_model, parent, false);
        return new LocalExpertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LocalExpertViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        holder.layout.getLayoutParams().height = (int) mHeight;
        String myString = mExpertDashboardModels.get(position).getUserCategoryName();
        String upperString = myString.substring(0, 1).toUpperCase() + myString.substring(1);
        holder.category.setText(upperString);
        Utils.loadUrlImage(holder.ivLocalExpert, imageUrl + mExpertDashboardModels.get(position).getUserCategoryIcon().trim(), R.drawable.no_image, false);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityLocalExpertListing.class);
                intent.putExtra("categoryName", mExpertDashboardModels.get(holder.getAdapterPosition()).getUserCategoryName());
                intent.putExtra("categoryId", mExpertDashboardModels.get(holder.getAdapterPosition()).getUserCategoryID());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mExpertDashboardModels.size();
    }


    class LocalExpertViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;
        TextView category;
        ImageView ivLocalExpert;

        LocalExpertViewHolder(View itemView) {
            super(itemView);
            this.category = itemView.findViewById(R.id.text_local_expert_category);
            this.layout = itemView.findViewById(R.id.layout_item_local_expert_category);
            this.ivLocalExpert = itemView.findViewById(R.id.tv_local_expert);
        }
    }
}
