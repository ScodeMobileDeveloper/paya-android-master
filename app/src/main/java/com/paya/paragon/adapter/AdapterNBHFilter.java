package com.paya.paragon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.listLocProject.RegionDataChild;
import com.paya.paragon.base.commonClass.GlobalValues;

import java.util.ArrayList;
import java.util.List;

public class AdapterNBHFilter extends RecyclerView.Adapter <AdapterNBHFilter.NeighbourhoodViewHolder> {

    List<RegionDataChild> mRegionDataChildArrayList = new ArrayList<>();

    public AdapterNBHFilter(){
        GlobalValues.selectedNeighbourhoodID = new ArrayList<>();

    }

    public void updateNeighbourhood(List<RegionDataChild> location){
        this.mRegionDataChildArrayList = location;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdapterNBHFilter.NeighbourhoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bed_room_model, parent, false);
        return new AdapterNBHFilter.NeighbourhoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNBHFilter.NeighbourhoodViewHolder holder, int position) {
        holder.bedRoom.setText(mRegionDataChildArrayList.get(position).getOriginalText());

        if (GlobalValues.selectedNeighbourhoodID != null &&
                GlobalValues.selectedNeighbourhoodID.size() > 0) {
                if (GlobalValues.selectedNeighbourhoodID.contains(mRegionDataChildArrayList.get(position).getId())){
                    holder.item.setSelected(true);
                }else {
                    holder.item.setSelected(false);
                }

        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(GlobalValues.selectedNeighbourhoodID!= null && GlobalValues.selectedNeighbourhoodID.contains(mRegionDataChildArrayList.get(position).getId())){
                    holder.item.setSelected(false);
                    GlobalValues.selectedNeighbourhoodID.remove(mRegionDataChildArrayList.get(position).getId());
                }else {
                    holder.item.setSelected(true);
                    GlobalValues.selectedNeighbourhoodID.add(mRegionDataChildArrayList.get(position).getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRegionDataChildArrayList.size();
    }

    public class NeighbourhoodViewHolder extends RecyclerView.ViewHolder{
        TextView bedRoom;
        LinearLayout item;
        public NeighbourhoodViewHolder(@NonNull View itemView) {
            super(itemView);
            this.bedRoom = itemView.findViewById(R.id.bed_room_number_item_bed_room_model);
            this.item = itemView.findViewById(R.id.item_number_item_bed_room_model);

        }
    }
}
