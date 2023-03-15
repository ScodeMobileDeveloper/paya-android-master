package com.paya.paragon.adapter;



import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.index.LocationInfo;

import java.util.ArrayList;


@SuppressWarnings("HardCodedStringLiteral")
public class AdapterCityListing extends RecyclerView.Adapter<AdapterCityListing.PropertyViewHolder> {

    private ArrayList<LocationInfo> locationInfos;
    private Context context;
    private int row_index = -1;
    private String selected = "";

    public AdapterCityListing(ArrayList<LocationInfo> locationInfos, Context context, String selected) {
        this.locationInfos = locationInfos;
        this.context = context;
        this.selected = selected;
    }
    public void searchedLocationList(ArrayList<LocationInfo> searchedLocatinInfo) {
        locationInfos = searchedLocatinInfo;
        notifyDataSetChanged();

    }
    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_listing_model, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PropertyViewHolder holder,  int position) {

        if (selected.equalsIgnoreCase(locationInfos.get(holder.getAdapterPosition()).getCityID())) {
            row_index = position;
        }
        holder.cityName.setText(String.valueOf(locationInfos.get(holder.getAdapterPosition()).getCityName()));

        holder.cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                selected = "";
                fireLocationInfo(locationInfos.get(holder.getAdapterPosition()));
                notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            holder.cityName.setTypeface(holder.cityName.getTypeface(), Typeface.BOLD);
            holder.cityName.setBackgroundColor(context.getResources().getColor(R.color.selector_color));
        } else {
            holder.cityName.setTypeface(holder.cityName.getTypeface(), Typeface.NORMAL);
            holder.cityName.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return locationInfos.size();
    }



    static class PropertyViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        LinearLayout holderLay;

        PropertyViewHolder(View itemView) {
            super(itemView);
            this.cityName = itemView.findViewById(R.id.text_price_list_item);
            this.holderLay = itemView.findViewById(R.id.holder_lay);
        }
    }

    public interface SelectCityInterface {
        void onPriceSelected(LocationInfo price);
    }

    private SelectCityInterface cityInterface;

    public void setMinPriceInterface(SelectCityInterface cityInterface) {
        this.cityInterface = cityInterface;
    }

    private void fireLocationInfo(LocationInfo price) {
        if (cityInterface != null) {
            cityInterface.onPriceSelected(price);
        }
    }
}
