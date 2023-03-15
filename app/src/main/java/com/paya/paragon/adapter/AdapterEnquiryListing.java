package com.paya.paragon.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.enquiryList.EnquiryListItemData;

import java.util.ArrayList;

public class AdapterEnquiryListing extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<EnquiryListItemData> mEnquiryListItems;
    private Context mContext;
    boolean loadMore;


    public AdapterEnquiryListing(boolean loadMore,Context context, ArrayList<EnquiryListItemData> enquiryListItems) {
        this.mEnquiryListItems = enquiryListItems;
        this.mContext = context;
        this.loadMore=loadMore;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder=null;
        if(viewType==0){
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enquiry_listing_model, parent, false);
            viewHolder= new ViewHolderMain(v);
        }else{
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);
            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderMain) {
            final ViewHolderMain viewHolder = (ViewHolderMain) holder;
            viewHolder.offerPrice.setVisibility(View.GONE);
            collapse(viewHolder.layoutMessage, 10);
            viewHolder.isExpanded = false;

            EnquiryListItemData data = mEnquiryListItems.get(position);

            viewHolder.expand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (viewHolder.isExpanded){
                        collapse(viewHolder.layoutMessage, 200);
                        viewHolder.isExpanded = false;
                        viewHolder.expand.setImageResource(R.drawable.icon_plus);
                    } else {
                        expand(viewHolder.layoutMessage, 200);
                        viewHolder.isExpanded = true;
                        viewHolder.expand.setImageResource(R.drawable.icon_minus);
                    }
                }
            });

            if (data.getPropertyName() != null && !data.getPropertyName().equals("")){
                viewHolder.propertyName.setText(data.getPropertyName());
            } else {
                viewHolder.propertyName.setText("");
            }
            if (data.getEnquiryName() != null && !data.getEnquiryName().equals("")){
                viewHolder.userName.setText(data.getEnquiryName());
            } else {
                viewHolder.userName.setText("");
            }
            if (data.getEnquiryEmail() != null && !data.getEnquiryEmail().equals("")){
                viewHolder.userEmail.setText(data.getEnquiryEmail());
            } else {
                viewHolder.userEmail.setText("");
            }
            if (data.getEnquiryPhone() != null && !data.getEnquiryPhone().equals("")){
                viewHolder.userPhone.setText(data.getEnquiryPhone());
            } else {
                viewHolder.userPhone.setText("");
            }
            if (data.getEnquiryContent() != null && !data.getEnquiryContent().equals("")){
                viewHolder.userMessage.setText(data.getEnquiryContent());
            } else {
                viewHolder.userMessage.setText("");
            }

        } else if (holder instanceof ProgressViewHolder) {
            final ProgressViewHolder progressViewHolder= (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        }

    }

    @Override
    public int getItemViewType(int position) {
        int a = 0;
        if (position < mEnquiryListItems.size()) {
            a = 0;
        } else {
            a = 1;
        }
        return a;
    }
    @Override
    public int getItemCount() {
        int size;
        if (loadMore) {
            size = mEnquiryListItems.size() + 1;
        } else {
            size = mEnquiryListItems.size();
        }
        return size;
    }


    static class ViewHolderMain extends RecyclerView.ViewHolder {
        TextView propertyName;
        TextView offerPrice;
        TextView userName;
        TextView userPhone;
        TextView userEmail;
        TextView userMessage;
        ImageView expand;
        LinearLayout layoutMessage;
        boolean isExpanded;

        ViewHolderMain(View itemView) {
            super(itemView);
            this.propertyName = itemView.findViewById(R.id.property_name_item_enquiry_listing);
            this.offerPrice = itemView.findViewById(R.id.offer_price_item_enquiry_listing);
            this.userName = itemView.findViewById(R.id.enquiry_user_name_item_enquiry_listing);
            this.userPhone = itemView.findViewById(R.id.enquiry_user_phone_item_enquiry_listing);
            this.userEmail = itemView.findViewById(R.id.enquiry_user_email_item_enquiry_listing);
            this.userMessage = itemView.findViewById(R.id.enquiry_message_item_enquiry_listing);
            this.expand = itemView.findViewById(R.id.expand_item_enquiry_listing);
            this.layoutMessage = itemView.findViewById(R.id.layout_expanded_message_item_enquiry_listing);
            this.isExpanded = false;
        }
    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;
        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    private void expand(final View view, int duration) {
        view.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = view.getMeasuredHeight();
        view.getLayoutParams().height = 1;
        view.setVisibility(View.VISIBLE);
        Animation anim = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                view.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                view.requestLayout();
            }

            public boolean willChangeBounds() {
                return true;
            }
        };
        anim.setDuration(duration);
        view.startAnimation(anim);
    }

    private void collapse(final View view, int duration) {
        final int initialHeight = view.getMeasuredHeight();
        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    view.setVisibility(View.GONE);
                } else {
                    view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        anim.setDuration(duration);
        view.startAnimation(anim);
    }
}
