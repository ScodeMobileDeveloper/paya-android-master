package com.paya.paragon.adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.paya.paragon.R;
import com.paya.paragon.api.bedRoomList.BedRoomInfo;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class BedRoomAdapter extends RecyclerView.Adapter<BedRoomAdapter.MyViewHolder> {

    private List<BedRoomInfo> bedRoomInfos;

    public BedRoomAdapter(List<BedRoomInfo> bedRoomInfos) {
        this.bedRoomInfos = bedRoomInfos;
        if (GlobalValues.selectedBedrooms == null){
            GlobalValues.selectedBedrooms = new ArrayList<>();
        }
        if (GlobalValues.selectedBedroomID== null){
            GlobalValues.selectedBedroomID   = new ArrayList<>();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nod_bedroom, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final BedRoomInfo bedRoomInfo = bedRoomInfos.get(position);

        holder.title.setText(bedRoomInfo.getAttrOptName());

        if (GlobalValues.selectedBedroomID != null &&
                GlobalValues.selectedBedroomID.size() > 0) {
            for (String room : GlobalValues.selectedBedroomID) {
                if (room.equals(bedRoomInfo.getOptionNewName())){
                    holder.cardView.setSelected(true);
                    bedRoomInfo.setSelected(true);
                    bedRoomInfos.get(position).setSelected(true);
                }
            }
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bedRoomInfo.isSelected()) {
                    GlobalValues.selectedBedroomID.remove(bedRoomInfo.getOptionNewName());
                    GlobalValues.selectedBedrooms.remove(bedRoomInfo.getOptionNewName());

                    bedRoomInfos.get(position).setSelected(false);
                    bedRoomInfo.setSelected(false);
                    holder.cardView.setSelected(false);
                } else {
                    GlobalValues.selectedBedroomID.add(bedRoomInfo.getOptionNewName());
                    GlobalValues.selectedBedrooms.add(bedRoomInfo.getOptionNewName());


                    bedRoomInfos.get(position).setSelected(true);
                    bedRoomInfo.setSelected(true);
                    holder.cardView.setSelected(true);
                }
            }
        });

    }

    public String bedroomOption(String ID){
        String option= "";
        if(ID.equalsIgnoreCase(Utils.ATTR_BED_ROOM_STUDIO_ID)){
            option = "Studio";

        }else if(ID.equalsIgnoreCase(Utils.ATTR_BED_ROOM_1_ID)){
            option = "1";

        }else if(ID.equalsIgnoreCase(Utils.ATTR_BED_ROOM_2_ID)){
            option = "2";

        }else if(ID.equalsIgnoreCase(Utils.ATTR_BED_ROOM_3_ID)){
            option = "3";

        }else if(ID.equalsIgnoreCase(Utils.ATTR_BED_ROOM_4_ID)){
            option = "4";

        }else if(ID.equalsIgnoreCase(Utils.ATTR_BED_ROOM_5_ID)){
            option = "5";

        }else if(ID.equalsIgnoreCase(Utils.ATTR_BED_ROOM_6_ID)){
            option = "6";

        }else if(ID.equalsIgnoreCase(Utils.ATTR_BED_ROOM_7_ID)){
            option = "7";

        }else if(ID.equalsIgnoreCase(Utils.ATTR_BED_ROOM_8_ID)){
            option = "+8";

        }

        return option;
    }

    @Override
    public int getItemCount() {
        return bedRoomInfos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView category_image;
        public LinearLayout cardView;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            category_image = (ImageView) view.findViewById(R.id.category_image);
            cardView = (LinearLayout) view.findViewById(R.id.cardView);

        }
    }
}
