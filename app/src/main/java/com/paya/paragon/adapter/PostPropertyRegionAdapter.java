package com.paya.paragon.adapter;



import android.content.Context;
import android.graphics.Typeface;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.PostPropertyRegion.RegionData;

import java.util.ArrayList;


@SuppressWarnings("HardCodedStringLiteral")
public class PostPropertyRegionAdapter extends RecyclerView.Adapter<PostPropertyRegionAdapter.PropertyViewHolder> {

    private ArrayList<RegionData> regionDataArrayList;
    private Context context;
    private int row_index = -1;

    public PostPropertyRegionAdapter(ArrayList<RegionData> regionDataArrayList, Context context) {
        this.regionDataArrayList = regionDataArrayList;
        this.context = context;

    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_property_type_selector_list_content_2, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PropertyViewHolder holder, final int position) {


        holder.cityName.setText(String.valueOf(regionDataArrayList.get(holder.getAdapterPosition()).getLocationName()));

        holder.cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=position;
                fireRegionData(regionDataArrayList.get(holder.getAdapterPosition()));
                notifyDataSetChanged();
            }
        });

        if (row_index == position) {
            holder.cityName.setTypeface(holder.cityName.getTypeface(), Typeface.BOLD);
            holder.cityName.setBackgroundColor(context.getResources().getColor(R.color.selector_color));
        } else {
            holder.cityName.setTypeface(holder.cityName.getTypeface(), Typeface.NORMAL);
            holder.cityName.setBackgroundColor(context.getResources().getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return regionDataArrayList.size();
    }

    static class PropertyViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        LinearLayout holderLay;

        PropertyViewHolder(View itemView) {
            super(itemView);
            this.cityName = itemView.findViewById(R.id.type_selector_level_two_text);
            this.holderLay = itemView.findViewById(R.id.holder_lay);
        }
    }

    public interface SelectCityInterface {
        void onCitySelected(RegionData price);
    }

    private SelectCityInterface cityInterface;

    public void setSelectCity(SelectCityInterface cityInterface) {
        this.cityInterface = cityInterface;
    }

    private void fireRegionData(RegionData price) {
        if (cityInterface != null) {
            cityInterface.onCitySelected(price);
        }
    }
}
