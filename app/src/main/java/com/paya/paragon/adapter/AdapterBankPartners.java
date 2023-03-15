package com.paya.paragon.adapter;



import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.paya.paragon.R;
import com.paya.paragon.api.propertyDetails.AmenitiesModel;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;


@SuppressWarnings({"SuspiciousNameCombination", "FieldCanBeLocal", "HardCodedStringLiteral"})
public class AdapterBankPartners extends RecyclerView.Adapter<AdapterBankPartners.AmenitiesViewHolder> {

    private ArrayList<AmenitiesModel> mAmenitiesModels;
    private Context mContext;
    private String imagePath = "";
    public AdapterBankPartners(Context context, ArrayList<AmenitiesModel> amenities, String imagePath) {
        this.mAmenitiesModels = amenities;
        this.mContext = context;
        this.imagePath = imagePath;
    }

    @Override
    public AmenitiesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bank_model, parent, false);
        return new AdapterBankPartners.AmenitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AmenitiesViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        AmenitiesModel model = mAmenitiesModels.get(position);
        String image = imagePath + model.getAttrOptionImage();
        Utils.loadUrlImage(holder.amenitiesIcon, image,R.drawable.no_image, false);
    }

    @Override
    public int getItemCount() {
        return mAmenitiesModels.size();
    }

    static class AmenitiesViewHolder extends RecyclerView.ViewHolder {

        ImageView amenitiesIcon;

        AmenitiesViewHolder(View itemView) {
            super(itemView);
            this.amenitiesIcon = itemView.findViewById(R.id.amenity_item_image_amenities_prop_details);
        }
    }
}
