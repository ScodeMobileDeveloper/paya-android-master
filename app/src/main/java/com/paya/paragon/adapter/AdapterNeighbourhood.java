package com.paya.paragon.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.listLocProject.RegionDataChild;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdapterNeighbourhood extends RecyclerView.Adapter<AdapterNeighbourhood.NeighbourhoodViewHolder> {
    List<RegionDataChild> mRegionDataChildArrayList = new ArrayList<>();


    public void updateNeighbourhood(List<RegionDataChild> location){
        this.mRegionDataChildArrayList = location;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapterNeighbourhood.NeighbourhoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bed_room_model, parent, false);
        return new AdapterNeighbourhood.NeighbourhoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNeighbourhood.NeighbourhoodViewHolder parent, int position) {

        parent.bedRoom.setText(mRegionDataChildArrayList.get(position).getOriginalText());
        Log.d("ne",Utils.getSelectedNeighbourValue(mRegionDataChildArrayList.get(position).getId())+" ");
        parent.item.setSelected(Utils.getSelectedNeighbourValue(mRegionDataChildArrayList.get(position).getId()));
        parent.item.setOnClickListener(view -> {
            Utils.setNeighbourhoodvalue(mRegionDataChildArrayList.get(position).getId(),mRegionDataChildArrayList.get(position).getOriginalText());

            parent.item.setSelected(true);
        AdapterNeighbourhood.this.notifyDataSetChanged();
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
