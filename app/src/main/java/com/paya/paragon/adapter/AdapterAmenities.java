package com.paya.paragon.adapter;



import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;


@SuppressWarnings({"SuspiciousNameCombination", "FieldCanBeLocal", "HardCodedStringLiteral"})
public class AdapterAmenities extends RecyclerView.Adapter<AdapterAmenities.AmenitiesViewHolder> {

    private ArrayList<AmenitiesModel> mAmenitiesModels;
    private Context mContext;
    private String imagePath = "";
    public AdapterAmenities(Context context, ArrayList<AmenitiesModel> amenities,String imagePath) {
        this.mAmenitiesModels = amenities;
        this.mContext = context;
        this.imagePath = imagePath;
    }

    @Override
    public AmenitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amenities_model, parent, false);
        return new AdapterAmenities.AmenitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AmenitiesViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        AmenitiesModel model = mAmenitiesModels.get(position);
        String amenity = model.getAttrOptName();
        holder.amenity.setText(amenity);
        String image = imagePath + model.getAttrOptionImage();
        Utils.loadUrlImage(holder.amenitiesIcon, image,R.drawable.no_image, false);
    }

    @Override
    public int getItemCount() {
        return mAmenitiesModels.size();
    }

    static class AmenitiesViewHolder extends RecyclerView.ViewHolder {
        TextView amenity;
        ImageView amenitiesIcon;

        AmenitiesViewHolder(View itemView) {
            super(itemView);
            this.amenity = itemView.findViewById(R.id.amenity_item_amenities_prop_details);
            this.amenitiesIcon = itemView.findViewById(R.id.amenity_item_image_amenities_prop_details);
        }
    }
}
