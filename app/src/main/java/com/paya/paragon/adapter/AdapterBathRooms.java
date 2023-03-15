package com.paya.paragon.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.model.BathRoomInfo;

import java.util.ArrayList;

public class AdapterBathRooms extends RecyclerView.Adapter<AdapterBathRooms.BedRoomViewHolder> {

    private ArrayList<BathRoomInfo> mBathRoomList;
    private String selectedList;

    public AdapterBathRooms(ArrayList<BathRoomInfo> bathRoomInfo, String selectedList) {
        this.mBathRoomList = bathRoomInfo;
        GlobalValues.selectedBathRoomID = "";
        this.selectedList = selectedList;
    }

    @NonNull
    @Override

    public BedRoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bed_room_model, parent, false);
        return new BedRoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BedRoomViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        /*if (position == mBathRoomList.size() - 1) {
            holder.padding.setVisibility(View.GONE);
        } else {
            holder.padding.setVisibility(View.VISIBLE);
        }*/
        final BathRoomInfo data = mBathRoomList.get(position);
        final String id = data.getAttributeID().substring(data.getAttributeID().lastIndexOf("_") + 1,
                data.getAttributeID().length());
        holder.bedRoom.setText(data.getAttrOptName());

        if (GlobalValues.selectedBathRoomID != null && !GlobalValues.selectedBathRoomID.equals("")) {
            if (GlobalValues.selectedBathRoomID.equals(id)) {
                holder.item.setSelected(true);
            } else {
                holder.item.setSelected(false);
            }
        }

        if (!selectedList.equals("") && selectedList.contains(id)) {
            holder.item.setSelected(true);
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GlobalValues.selectedBathRoomID != null && !GlobalValues.selectedBathRoomID.equals("")) {
                    if (!GlobalValues.selectedBathRoomID.equals(id)) {
                        holder.item.setSelected(true);
                        GlobalValues.selectedBathRoomID = id;
                        notifyDataSetChanged();
                    }
                } else {
                    GlobalValues.selectedBathRoomID = "";
                    holder.item.setSelected(true);
                    GlobalValues.selectedBathRoomID = id;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mBathRoomList.size();
    }

    static class BedRoomViewHolder extends RecyclerView.ViewHolder {
        TextView bedRoom;
        LinearLayout item;
       // View padding;

        BedRoomViewHolder(View itemView) {
            super(itemView);
            this.bedRoom = itemView.findViewById(R.id.bed_room_number_item_bed_room_model);
            this.item = itemView.findViewById(R.id.item_number_item_bed_room_model);
           // this.padding = itemView.findViewById(R.id.padding_view);
        }
    }
}
