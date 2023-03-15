package com.paya.paragon.adapter;


import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class AdapterAmenitiesSelector extends RecyclerView.Adapter<AdapterAmenitiesSelector.AmenitiesViewHolder> {

    private ArrayList<AmenitiesModel> mAmenitiesModels;
    private String mSelectedList = "";

    public AdapterAmenitiesSelector(ArrayList<AmenitiesModel> amenities) {
        this.mAmenitiesModels = amenities;
        GlobalValues.postPropertyAmenities = new ArrayList<>();
    }

    public AdapterAmenitiesSelector(ArrayList<AmenitiesModel> amenities, String selectedList) {
        this.mAmenitiesModels = amenities;
        this.mSelectedList = selectedList;
        GlobalValues.postPropertyAmenities = new ArrayList<>();
    }

    @NonNull
    @Override
    public AmenitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amenities_selector_model, parent, false);
        return new AmenitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AmenitiesViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.onBind();
    }

    @Override
    public int getItemCount() {
        return mAmenitiesModels.size();
    }

    class AmenitiesViewHolder extends RecyclerView.ViewHolder {

        TextView tvAmenities;
        LinearLayout llAmenities;
        ImageView imgAmenities;

        AmenitiesViewHolder(View itemView) {
            super(itemView);
            this.tvAmenities = itemView.findViewById(R.id.tv_amenities);
            this.llAmenities = itemView.findViewById(R.id.ll_amenities);
            this.imgAmenities = itemView.findViewById(R.id.img_amenities);
        }

        public void onBind() {
            AmenitiesModel amenity = mAmenitiesModels.get(getAdapterPosition());
            String amenityTitle = amenity.getAttrOptName();
            tvAmenities.setText(amenityTitle);
            Utils.ATTR_AMENITIES_ID = amenity.getAttributeID();
            Utils.loadDrawableImage(imgAmenities, Utils.getAmenityImagesFromDrawable(amenity.getAttrOptionID()));
            changeAmenitiesSelection(Utils.getPreviousEditedValue(amenity.getAttributeID()).contains(amenity.getAttrOptionID()));
            llAmenities.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeAmenitiesSelection(!llAmenities.isSelected());
                    Utils.setPreviousEditedValue(amenity.getAttributeID(), amenity.getAttrOptionID(), llAmenities.isSelected(), true);
                }
            });

        }

        private void changeAmenitiesSelection(boolean isAmenitiesSelected) {
            llAmenities.setSelected(isAmenitiesSelected);
            tvAmenities.setTypeface(null, isAmenitiesSelected ? Typeface.BOLD : Typeface.NORMAL);
        }
    }
}
