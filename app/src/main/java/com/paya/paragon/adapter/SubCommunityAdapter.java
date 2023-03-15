package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.propertyLocation.SubCommunity;

import java.util.ArrayList;

public class SubCommunityAdapter extends RecyclerView.Adapter<SubCommunityAdapter.DistrictViewHolder> {

    private ArrayList<SubCommunity> subCommunities;

    public SubCommunityAdapter(ArrayList<SubCommunity> districtList) {
        this.subCommunities = districtList;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_listing_model, parent, false);
        return new SubCommunityAdapter.DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (subCommunities.get(position) != null && !subCommunities.get(position).getCommunityName().equals("")) {
            holder.districtName.setText(subCommunities.get(position).getCommunityName());
        } else {
            holder.districtName.setText("");
        }

        holder.districtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireDistrict(subCommunities.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return subCommunities.size();
    }

    static class DistrictViewHolder extends RecyclerView.ViewHolder {
        TextView districtName;

        DistrictViewHolder(View itemView) {
            super(itemView);
            this.districtName = itemView.findViewById(R.id.text_price_list_item);
        }
    }


    public interface SelectDistrictInterface {
        void onDistrictSelected(SubCommunity district);
    }

    private SubCommunityAdapter.SelectDistrictInterface mDistrictInterface;

    public void setDistrictInterface(SubCommunityAdapter.SelectDistrictInterface districtInterface) {
        this.mDistrictInterface = districtInterface;
    }

    private void fireDistrict(SubCommunity district) {
        if (mDistrictInterface != null) {
            mDistrictInterface.onDistrictSelected(district);
        }
    }
}
