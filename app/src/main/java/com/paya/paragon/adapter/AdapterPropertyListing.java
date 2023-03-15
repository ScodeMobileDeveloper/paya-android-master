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

import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.paya.paragon.R;
import com.paya.paragon.activity.details.ActivityPropertyDetails;
import com.paya.paragon.api.propertyDetails.SuggestedPropertyDetails;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;


@SuppressWarnings("HardCodedStringLiteral")
public class AdapterPropertyListing extends RecyclerView.Adapter<AdapterPropertyListing.PropertyViewHolder> {

    private ArrayList<SuggestedPropertyDetails> mPropertyList;
    private Context mContext;

    public AdapterPropertyListing(Context context, ArrayList<SuggestedPropertyDetails> propertyList) {
        this.mContext = context;
        this.mPropertyList = propertyList;
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property_listing_model1, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PropertyViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final SuggestedPropertyDetails propertyLists = mPropertyList.get(holder.getAdapterPosition());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityPropertyDetails.class);
                intent.putExtra("propertyID", mPropertyList.get(holder.getAdapterPosition()).getId());
                intent.putExtra("position", holder.getAdapterPosition());
                mContext.startActivity(intent);
            }
        });

        /*if (model.getPropertyFeatured() != null) {
            if (model.getPropertyFeatured().equals("ON")) {
                holder.featuredText.setVisibility(View.VISIBLE);
            } else {
                holder.featuredText.setVisibility(View.GONE);
            }
        }

        if (model.getPropertyImagecount() != null && !model.getPropertyImagecount().equals("")) {
            holder.imageNumber.setText(model.getPropertyImagecount());
        }

        String price = "";
        if (model.getCurrencySymbolLeft() != null) {
            price = price + model.getCurrencySymbolLeft();
        }
        price = price + " " + model.getPropertyPrice();
        if (model.getCurrencySymbolRight() != null) {
            price = price + model.getCurrencySymbolRight();
        }
        holder.propertyPrice.setText(price);
        holder.propertyName.setText(model.getPropertyName());

        String info1 = "", info2 = "", info;
        if (model.getPropertyBedRoom() != null && !model.getPropertyBedRoom().equals("")) {
            info1 = model.getPropertyBedRoom() + " Bedroom ";
        }
        if (model.getPropertyTypeID() != null && !model.getPropertyTypeID().equals("")) {
            for (PropertyTypeMain main : GlobalValues.propertyTypes) {
                for (PropertyTypeSub sub : main.getSubCategory()) {
                    if (sub.getPropertyTypeID().equals(model.getPropertyTypeID())) {
                        info1 = info1 + sub.getPropertyTypeName();
                    }
                }
            }
        }
        if (model.getPropertyPlotArea() != null && !model.getPropertyPlotArea().equals("")) {
            info2 = info2 + model.getPropertyPlotArea() + "  "+ mContext.getString(R.string.meter_square);
        }
        if (!info1.equals("") && !info2.equals("")) {
            info = info1 + " | " + info2;
        } else if (!info1.equals("")) {
            info = info1;
        } else {
            info = info2;
        }
        holder.propertyInfo.setText(info);

        String loc1 = "", loc2 = "", location;
        if (model.getPropertyAddress1() != null && !model.getPropertyAddress1().equals("")) {
            loc1 = model.getPropertyAddress1();
        } else {
            if (model.getPropertyAddress2() == null || model.getPropertyAddress2().equals("")) {
                holder.propertyLocation.setVisibility(View.GONE);
            }
        }
        if (model.getPropertyAddress2() != null && !model.getPropertyAddress2().equals("")) {
            loc2 = model.getPropertyAddress2();
        }
        if (!loc1.equals("") && !loc2.equals("")) {
            location = loc1 + ", " + loc2;
        } else {
            if (!loc1.equals("")) {
                location = loc1;
            } else {
                location = loc2;
            }
        }
        holder.propertyLocation.setText(location);

        if (GlobalValues.baseUrl != null && !GlobalValues.baseUrl.equals("")) {
            if (model.getPropertyCoverImage() != null && !model.getPropertyCoverImage().equals("")) {
                String imageUrl = GlobalValues.baseUrl + model.getPropertyCoverImage();
                Utils.loadUrlImage(holder.propertyImage,imageUrl, R.drawable.no_image_placeholder, false);
            } else {
                holder.propertyImage.setImageResource(R.drawable.no_image_placeholder);
            }
        } else {
            holder.propertyImage.setImageResource(R.drawable.no_image_placeholder);
        }

        if (model.getFavourite() == 1) {
            holder.propertyFavourite.setImageDrawable(mContext.getDrawable(R.drawable.menu_icon_like_on));
        }*/

        String price = "";
        if(propertyLists.getPropertyPrice().equalsIgnoreCase(propertyLists.getPropertyPrices().get1())){

            price = mContext.getString(R.string.iqd_currency_symbol)+""+propertyLists.getPropertyPrice();
        }else {
            price = mContext.getString(R.string.currency_symbol)+""+propertyLists.getPropertyPrice();
        }
        holder.propertyPrice.setText(price);
        holder.propertyName.setText(propertyLists.getPropertyName());

        holder.propertyInfo.setText(propertyLists.getPropertyAddress());
        holder.imageNumber.setText(propertyLists.getImageCount());
        if (propertyLists.getImageUrl() != null && !propertyLists.getImageUrl().equals("")) {
            if (propertyLists.getPropCoverImage() != null && !propertyLists.getPropCoverImage().equals("")) {
                String imageUrl = propertyLists.getImageUrl() + propertyLists.getPropCoverImage();
                Utils.loadUrlImage(holder.propertyImage,imageUrl, R.drawable.no_image_placeholder, false);
            } else {
                holder.propertyImage.setImageResource(R.drawable.no_image_placeholder);
            }
        } else {
            holder.propertyImage.setImageResource(R.drawable.no_image_placeholder);
        }

        if (propertyLists.getPropertyTypeName() != null && !propertyLists.getPropertyTypeName().equals("")) {
            holder.apartment_textView.setText(propertyLists.getPropertyTypeName());
            holder.apartment_lay.setVisibility(View.VISIBLE);

            if (propertyLists.getPropertyTypeIcon() != null && !propertyLists.getPropertyTypeIcon().equals(""))
                Utils.loadUrlImage(holder.apartment_ic, propertyLists.getPropertyTypeIcon(), R.drawable.no_image_placeholder, false);
        } else holder.apartment_lay.setVisibility(View.GONE);

        if (propertyLists.getPropertyBathRoom() != null && !propertyLists.getPropertyBathRoom().equals("")) {
            holder.bath_textView.setText(propertyLists.getPropertyBathRoom() + " " + mContext.getString(R.string.bath));
            holder.bath_ic_lay.setVisibility(View.VISIBLE);
        } else holder.bath_ic_lay.setVisibility(View.GONE);

        if (propertyLists.getPropertySqrFeet() != null && !propertyLists.getPropertySqrFeet().equals("")) {
            holder.area_textView.setText(propertyLists.getPropertySqrFeet() + " " + mContext.getString(R.string.meter_square));
            holder.area_ic_lay.setVisibility(View.VISIBLE);
        } else if (propertyLists.getPropertySqrFeet() != null && !propertyLists.getPropertySqrFeet().equals("")) {
            holder.area_textView.setText(propertyLists.getPropertySqrFeet());
            holder.area_ic_lay.setVisibility(View.VISIBLE);
        } else holder.area_ic_lay.setVisibility(View.GONE);

        if (propertyLists.getPropertyBedRoom() == null) {
            holder.bed_ic_lay.setVisibility(View.GONE);
        } else {
            holder.bed_ic_lay.setVisibility(View.VISIBLE);

            String propType = propertyLists.getPropertyBedRoom() + " " + mContext.getString(R.string.bed);
            holder.bed_textView.setText(propType);
        }
        holder.propertyShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharePropertyLink(propertyLists.getPropertyName(),propertyLists.getPropertyLink());
            }
        });

    }
    private void sharePropertyLink(String propertyName, String propertyLink) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        try {
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, propertyName);
            sendIntent.putExtra(Intent.EXTRA_TEXT, propertyLink);

        } catch (Exception e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            e.printStackTrace();
        }
        mContext.startActivity(sendIntent);
    }
    @Override
    public int getItemCount() {
        return mPropertyList.size();
    }

    static class PropertyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout item;
        TextView featuredText;
        TextView imageNumber;
        ImageView propertyImage;
        TextView propertyPrice;
        TextView propertyName;
        TextView propertyInfo;
        TextView propertyLocation;
        ImageView propertyShare;
        LinearLayout bath_ic_lay, bed_ic_lay, area_ic_lay, apartment_lay;
        TextView bath_textView, bed_textView, area_textView, apartment_textView;
        ImageView apartment_ic;

        PropertyViewHolder(View itemView) {
            super(itemView);
            this.item = itemView.findViewById(R.id.item_view_property_listing);
            this.featuredText = itemView.findViewById(R.id.featured_text_item_property_listing);
            this.imageNumber = itemView.findViewById(R.id.image_number_item_property_listing);
            this.propertyImage = itemView.findViewById(R.id.image_item_property_listing);
            this.propertyPrice = itemView.findViewById(R.id.price_item_property_listing);
            this.propertyName = itemView.findViewById(R.id.property_name_item_property_listing);
            this.propertyInfo = itemView.findViewById(R.id.property_details_item_property_listing);
            this.propertyLocation = itemView.findViewById(R.id.property_location_item_property_listing);
            this.propertyShare = itemView.findViewById(R.id.share_item_property_listing);

            apartment_ic = itemView.findViewById(R.id.apartment_image);

            apartment_lay = itemView.findViewById(R.id.apartment_lay);
            apartment_textView = itemView.findViewById(R.id.apartment_textView);

            area_ic_lay = itemView.findViewById(R.id.area_ic_lay);
            area_textView = itemView.findViewById(R.id.area_textView);

            bed_ic_lay = itemView.findViewById(R.id.bed_ic_lay);
            bed_textView = itemView.findViewById(R.id.bed_textView);

            bath_ic_lay = itemView.findViewById(R.id.bath_ic_lay);
            bath_textView = itemView.findViewById(R.id.bath_textView);
        }
    }
}
