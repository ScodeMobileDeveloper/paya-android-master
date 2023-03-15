package com.paya.paragon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.bedRoomList.BedRoomInfo;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

public class AdapterBedRooms extends RecyclerView.Adapter<AdapterBedRooms.BedRoomViewHolder> {

    private ArrayList<BedRoomInfo> mBedRoomList;
    private String selectedList;

    public AdapterBedRooms(ArrayList<BedRoomInfo> bedRoomInfo, String selectedList) {
        this.mBedRoomList = bedRoomInfo;
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
        holder.onBindData(mBedRoomList);
    }

    @Override
    public int getItemCount() {
        return mBedRoomList.size();
    }

    class BedRoomViewHolder extends RecyclerView.ViewHolder {
        TextView bedRoom;
        LinearLayout item;

        BedRoomViewHolder(View itemView) {
            super(itemView);
            this.bedRoom = itemView.findViewById(R.id.bed_room_number_item_bed_room_model);
            this.item = itemView.findViewById(R.id.item_number_item_bed_room_model);
        }

        public void onBindData(ArrayList<BedRoomInfo> mBedRoomList) {
            final BedRoomInfo data = mBedRoomList.get(getAdapterPosition());
//          final String id = data.getOptionNewName();
            final String id = data.getAttributeID().substring(data.getAttributeID().lastIndexOf("_") + 1, data.getAttributeID().length());
            bedRoom.setText(data.getAttrOptName());
            item.setSelected(Utils.getPreviousEditedValue(Utils.ATTR_BED_ROOM_ID).equals(id));
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.setPreviousEditedValue(Utils.ATTR_BED_ROOM_ID, id, false, false);
                    item.setSelected(true);
                    AdapterBedRooms.this.notifyDataSetChanged();
                }
            });
        }
    }
}
