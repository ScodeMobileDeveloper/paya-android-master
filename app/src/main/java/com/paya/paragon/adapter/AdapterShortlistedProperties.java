package com.paya.paragon.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.shoertlistedProperties.ShortlistedPropertyModel;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterShortlistedProperties extends RecyclerView.Adapter<AdapterShortlistedProperties.PropertyViewHolder> {

    private ArrayList<ShortlistedPropertyModel> mPropertyList;
    private Context mContext;
    private ItemClickAdapterListener itemClickAdapterListener;

    public AdapterShortlistedProperties(Context context, ArrayList<ShortlistedPropertyModel> propertyListData,
                                        ItemClickAdapterListener itemClickAdapterListener) {
        this.mContext = context;
        this.mPropertyList = propertyListData;
        this.itemClickAdapterListener = itemClickAdapterListener;
    }

    @NonNull
    @Override
    public PropertyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_property_shortlisted_model,
                parent, false);
        return new PropertyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final PropertyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.setIsRecyclable(false);

        final ShortlistedPropertyModel data = mPropertyList.get(position);


        //Name
        if (data.getPropertyName() != null && !data.getPropertyName().equals("")) {
            holder.name.setText(data.getPropertyName());
        } else {
            holder.name.setText("");
        }

        //Posted by
        if (data.getProjectBuilderName() != null && !data.getProjectBuilderName().equals("")) {
            holder.builderName.setText("By " + data.getProjectBuilderName());
        } else {
            holder.builderName.setText("");
            holder.builderName.setVisibility(View.GONE);
        }

        //Location
        String location = "";
        if (data.getCityLocName() != null && !data.getCityLocName().equals("")) {
            location = location + data.getCityLocName();
        }
        if (data.getStateName() != null && !data.getStateName().equals("")) {
            location = location + ", " + data.getStateName();
        }
        if (!location.equals("")) {
            holder.location.setText(location);
        } else {
            holder.location.setText("");
            holder.location.setVisibility(View.GONE);
        }

        //Key
        if (data.getPropertyKey() != null && !data.getPropertyKey().equals("")) {
            holder.propertyKey.setText("ID: " + data.getPropertyKey());
        } else {
            holder.propertyKey.setText("");
            holder.propertyKey.setVisibility(View.GONE);
        }

        //Features
       /* String feature = "";
        if (data.getPropertyBedRoom() != null && !data.getPropertyBedRoom().equals("")) {
            feature = data.getPropertyBedRoom() + " BHK Apartment";
        }
        if (data.getPropertyTypeName() != null && !data.getPropertyTypeName().equals("")) {
            if (feature.equals("")) {
                feature = data.getPropertyTypeName();
            } else {
                feature = feature + " | " + data.getPropertyTypeName();
            }
        }
        if (!feature.equals("")) {
            holder.features.setVisibility(View.VISIBLE);
            holder.features.setText(feature);
        } else {
            holder.features.setText("");
            holder.features.setVisibility(View.GONE);
        }

        //Possession
        if (data.getPossessionDate() != null && !data.getPossessionDate().equals("")) {
            holder.possession.setText("Possession: " + data.getPossessionDate());
        } else {
            holder.possession.setText("");
            holder.possession.setVisibility(View.GONE);
        }*/
        holder.features.setVisibility(View.GONE);
        holder.possession.setVisibility(View.GONE);
        //Property Price
        String price = "";
        if (data.getCurrencySymbolLeft() != null && !data.getCurrencySymbolLeft().equals("")) {
            price = price + mContext.getResources().getString(R.string.currency_symbol);
        }
        if (data.getPropertyPrice() != null && !data.getPropertyPrice().equals("")) {
            price = price + " " + data.getPropertyPrice();
        }
        if (data.getCurrencySymbolRight() != null && !data.getCurrencySymbolRight().equals("")) {
            price = price + " " + data.getCurrencySymbolRight();
        }
        holder.price.setText(price);

        //Sq. ft
        if (data.getPropertySqrFeet() != null && !data.getPropertySqrFeet().equals("")) {
            holder.area.setText(data.getPropertySqrFeet() + "  " + mContext.getString(R.string.meter_square));
        } else {
            holder.area.setText("");
            holder.area.setVisibility(View.GONE);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.deleteClick(position);
            }
        });

        holder.layoutTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.viewDetailsClick(position);
            }
        });

        holder.layoutBottom1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.viewDetailsClick(position);
            }
        });

        holder.layoutBottom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.viewDetailsClick(position);
            }
        });

        holder.contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.contactUser(position);
            }
        });

        String url = GlobalValues.shortlistedPropertiesImageUrl + data.getPropCoverImage();
        Utils.loadUrlImage(holder.coverImage, url, R.drawable.no_image_placeholder, false);

    }

    @Override
    public int getItemCount() {
        return mPropertyList.size();
    }

    public void clear() {
        mPropertyList.clear();
        notifyDataSetChanged();
    }

    class PropertyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layoutTop, layoutBottom1, layoutBottom2;
        ImageView coverImage, delete;
        ImageView contact;
        TextView name;
        TextView builderName;
        TextView location;
        TextView propertyKey;
        TextView features;
        TextView possession;
        TextView price;
        TextView area;

        PropertyViewHolder(View itemView) {
            super(itemView);
            this.layoutTop = itemView.findViewById(R.id.layout_top_item_property_shortlisted);
            this.layoutBottom1 = itemView.findViewById(R.id.layout_bottom1_item_property_shortlisted);
            this.layoutBottom2 = itemView.findViewById(R.id.layout_bottom2_item_property_shortlisted);
            this.delete = itemView.findViewById(R.id.delete_item_property_shortlisted);
            this.contact = itemView.findViewById(R.id.icon_call_item_property_shortlisted);

            this.coverImage = itemView.findViewById(R.id.image_item_property_listing);
            this.name = itemView.findViewById(R.id.name_item_property_shortlisted);
            this.builderName = itemView.findViewById(R.id.builder_name_item_property_shortlisted);
            this.location = itemView.findViewById(R.id.property_area_item_property_shortlisted);
            this.propertyKey = itemView.findViewById(R.id.property_key_item_property_shortlisted);
            this.features = itemView.findViewById(R.id.property_details_item_property_shortlisted);
            this.possession = itemView.findViewById(R.id.property_possession_item_property_shortlisted);
            this.price = itemView.findViewById(R.id.property_price_item_property_shortlisted);
            this.area = itemView.findViewById(R.id.property_price_average_item_property_shortlisted);
        }
    }

    public interface ItemClickAdapterListener {

        void deleteClick(int position);

        void viewDetailsClick(int position);

        void contactUser(int position);

    }
}
