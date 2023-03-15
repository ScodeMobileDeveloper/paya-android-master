package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.districtList.DistrictListingData;

import java.util.ArrayList;

public class AdapterDistricts extends RecyclerView.Adapter<AdapterDistricts.DistrictViewHolder> {

    private ArrayList<DistrictListingData> mDistrictList;

    public AdapterDistricts(ArrayList<DistrictListingData> districtList) {
        this.mDistrictList = districtList;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_listing_model, parent, false);
        return new AdapterDistricts.DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (mDistrictList.get(position) != null && !mDistrictList.get(position).getCityName().equals("")) {
            holder.districtName.setText(mDistrictList.get(position).getCityName());
        } else {
            holder.districtName.setText("");
        }

        holder.districtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireDistrict(mDistrictList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDistrictList.size();
    }

    static class DistrictViewHolder extends RecyclerView.ViewHolder {
        TextView districtName;

        DistrictViewHolder(View itemView) {
            super(itemView);
            this.districtName = itemView.findViewById(R.id.text_price_list_item);
        }
    }


    public interface SelectDistrictInterface {
        void onDistrictSelected(DistrictListingData district);
    }

    private AdapterDistricts.SelectDistrictInterface mDistrictInterface;

    public void setDistrictInterface(AdapterDistricts.SelectDistrictInterface districtInterface) {
        this.mDistrictInterface = districtInterface;
    }

    private void fireDistrict(DistrictListingData district) {
        if (mDistrictInterface != null) {
            mDistrictInterface.onDistrictSelected(district);
        }
    }
}
