package com.paya.paragon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.api.index.LocationInfo;
import com.paya.paragon.databinding.ItemLocationInfoBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LocationListAdapter extends RecyclerView.Adapter<LocationListAdapter.LocationInfoListAdapterViewHolder> {
    private ArrayList<LocationInfo> locationInfoList;
    private LocationListAdapterCallBack callBack;

    public LocationListAdapter(ArrayList<LocationInfo> locationInfoList, LocationListAdapterCallBack callBack) {
        this.locationInfoList = locationInfoList;
        this.callBack = callBack;
    }

    @NonNull
    @NotNull
    @Override
    public LocationInfoListAdapterViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemLocationInfoBinding binding = ItemLocationInfoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new LocationInfoListAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LocationInfoListAdapterViewHolder holder, int position) {
        holder.bindValue();
    }

    @Override
    public int getItemCount() {
        return locationInfoList.size();
    }

    public void updateAdapterList(ArrayList<LocationInfo> locationInfoList, int previousSelectionPosition, int newlySelectedPosition) {
        this.locationInfoList = locationInfoList;
        notifyItemChanged(previousSelectionPosition);
        notifyItemChanged(newlySelectedPosition);
    }

    public class LocationInfoListAdapterViewHolder extends RecyclerView.ViewHolder {
        ItemLocationInfoBinding binding;

        public LocationInfoListAdapterViewHolder(@NonNull @NotNull ItemLocationInfoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindValue() {
            LocationInfo locationInfo = locationInfoList.get(getAdapterPosition());
            binding.tvCityName.setText(locationInfo.getCityName());
            binding.llCityParent.setSelected(locationInfo.isLocationSelected());
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.updateSelection(getAdapterPosition());
                }
            });
        }
    }

    public interface LocationListAdapterCallBack {

        void updateSelection(int newlySelectedPosition);

        void onLocationInfoSelection(LocationInfo selectedLocationInfo);
    }
}
