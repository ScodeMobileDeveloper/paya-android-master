package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.openHouseSlots.OpenHouseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterOpenHouseList extends RecyclerView.Adapter<AdapterOpenHouseList.OpenHouseViewHolder> {

    private ArrayList<OpenHouseModel> mOpenHouseModels;
    private Context mContext;
    private ItemClickAdapterListener itemClickAdapterListener;
    public AdapterOpenHouseList(Context context, ArrayList<OpenHouseModel> openHouseModels, ItemClickAdapterListener itemClickAdapterListener) {
        this.mOpenHouseModels = openHouseModels;
        this.mContext = context;
        this.itemClickAdapterListener = itemClickAdapterListener;
    }

    @Override
    public OpenHouseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_open_house_model, parent, false);
        return new AdapterOpenHouseList.OpenHouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OpenHouseViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.setIsRecyclable(false);

        final OpenHouseModel model = mOpenHouseModels.get(position);

        holder.startDate.setText(convertToDateOnlyFormat(model.getSlotStart()));
        holder.endDate.setText(convertToDateOnlyFormat(model.getSlotEnd()));

        holder.deleteSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.deleteClick( model.getPropertyID(), model.getSlotID(), position);
              //  ActivityOpenHouse.deleteSlot(mContext, model.getPropertyID(), model.getSlotID(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mOpenHouseModels.size();
    }

    class OpenHouseViewHolder extends RecyclerView.ViewHolder {
        TextView startDate;
        TextView endDate;
        ImageView deleteSlot;

        OpenHouseViewHolder(View itemView) {
            super(itemView);
            startDate = itemView.findViewById(R.id.start_date_item_open_house);
            endDate = itemView.findViewById(R.id.end_date_item_open_house);
            deleteSlot = itemView.findViewById(R.id.deleted_slot_item_open_house);
        }
    }

    private String convertToDateOnlyFormat(String strDate) {
        String formattedDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = format.parse(strDate);
            formattedDate = formatDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
    public interface ItemClickAdapterListener {

        void deleteClick(String propertyID, String slotID, final int position);

    }
}
