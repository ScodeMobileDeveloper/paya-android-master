package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;

import com.paya.paragon.api.postProperty.PostPropertyLocalityList.state.StateListItem;

import java.util.ArrayList;


public class AdapterPropertyState extends RecyclerView.Adapter<com.paya.paragon.adapter.AdapterDistricts.DistrictViewHolder> {

    private ArrayList<StateListItem> mDistrictList;

    public AdapterPropertyState(ArrayList<StateListItem> districtList) {
        this.mDistrictList = districtList;
    }

    @NonNull
    @Override
    public com.paya.paragon.adapter.AdapterDistricts.DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_listing_model, parent, false);
        return new com.paya.paragon.adapter.AdapterDistricts.DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.paya.paragon.adapter.AdapterDistricts.DistrictViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (mDistrictList.get(position) != null && !mDistrictList.get(position).getStateName().equals("")) {
            holder.districtName.setText(mDistrictList.get(position).getStateName());
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
        void onDistrictSelected(StateListItem district);
    }

    private SelectDistrictInterface mDistrictInterface;

    public void setDistrictInterface(SelectDistrictInterface districtInterface) {
        this.mDistrictInterface = districtInterface;
    }

    private void fireDistrict(StateListItem district) {
        if (mDistrictInterface != null) {
            mDistrictInterface.onDistrictSelected(district);
        }
    }
}






