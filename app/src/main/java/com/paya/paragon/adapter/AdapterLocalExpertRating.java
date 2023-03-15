package com.paya.paragon.adapter;



import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.localExpertDetials.LocalExpertRatingModel;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;


public class AdapterLocalExpertRating extends RecyclerView.Adapter<AdapterLocalExpertRating.LocalExpertViewHolder> {

    private ArrayList<LocalExpertRatingModel> mExpertRatingModels;
    private Context mContext;
    String imageURL;
    public AdapterLocalExpertRating(Context context, ArrayList<LocalExpertRatingModel> localExpertsRatings,String imageURL) {
        this.mContext = context;
        this.mExpertRatingModels = localExpertsRatings;
        this.imageURL = imageURL;
    }

    @Override
    public LocalExpertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_expert_review_model, parent, false);
        return new LocalExpertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalExpertViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        LocalExpertRatingModel model = mExpertRatingModels.get(position);
        String name=model.getUserFirstName()+" "+model.getUserLastName();
        holder.reviewerName.setText(name);
        holder.reviewTime.setText(model.getUserReviewedDate());
        if (model.getReviewTitle() != null && !model.getReviewTitle().equals("")) {
            holder.reviewTitle.setText(model.getReviewTitle());
        } else {
            holder.reviewTitle.setVisibility(View.GONE);
        }
        if (model.getReviewText() != null && !model.getReviewText().equals("")) {
            holder.reviewContent.setText(model.getReviewText());
        } else {
            holder.reviewContent.setVisibility(View.GONE);
        }
        holder.ratingBar.setRating(Float.valueOf(model.getUserRating()));

        Utils.loadUrlImage(holder.userProfilePicture, imageURL+model.getUserProfilePicture(), R.drawable.no_image_user, false);
    }

    @Override
    public int getItemCount() {
        return mExpertRatingModels.size();
    }

    class LocalExpertViewHolder extends RecyclerView.ViewHolder {

        TextView reviewerName;
        TextView reviewTime;
        TextView reviewTitle;
        TextView reviewContent;
        RatingBar ratingBar;
        ImageView userProfilePicture;

        LocalExpertViewHolder(View itemView) {
            super(itemView);
            this.reviewerName = itemView.findViewById(R.id.reviewer_name_item_expert_review);
            this.reviewTime = itemView.findViewById(R.id.review_time_item_expert_review);
            this.reviewTitle = itemView.findViewById(R.id.review_title_item_expert_review);
            this.reviewContent = itemView.findViewById(R.id.review_content_item_expert_review);
            this.ratingBar = itemView.findViewById(R.id.rating_bar_item_local_expert);
            this.userProfilePicture = itemView.findViewById(R.id.userProfilePicture);

        }
    }
}
