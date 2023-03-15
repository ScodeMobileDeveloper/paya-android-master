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
import com.paya.paragon.api.AgentSubLocation.AgentSubLocation;

import java.util.ArrayList;


@SuppressWarnings("HardCodedStringLiteral")
public class AdapterAgentSubLocListing extends RecyclerView.Adapter<AdapterAgentSubLocListing.PropertyViewHolder> {

    private ArrayList<AgentSubLocation> agentSubLocations;
    private Context context;
    private int row_index = -1;

    public AdapterAgentSubLocListing(ArrayList<AgentSubLocation> agentSubLocations, Context context) {
        this.agentSubLocations = agentSubLocations;
        this.context = context;
       
    }

    @Override
    public PropertyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_listing_model, parent, false);
        return new PropertyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PropertyViewHolder holder, final int position) {


        holder.cityName.setText(String.valueOf(agentSubLocations.get(holder.getAdapterPosition()).getCityLocName()));

        holder.cityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index=position;
                fireAgentSubLocation(agentSubLocations.get(holder.getAdapterPosition()));
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
        return agentSubLocations.size();
    }

    static class PropertyViewHolder extends RecyclerView.ViewHolder {
        TextView cityName;
        LinearLayout holderLay;

        PropertyViewHolder(View itemView) {
            super(itemView);
            this.cityName = itemView.findViewById(R.id.text_price_list_item);
            this.holderLay = itemView.findViewById(R.id.holder_lay);
        }
    }

    public interface SelectCityInterface {
        void onLocationSelected(AgentSubLocation price);
    }

    private SelectCityInterface cityInterface;

    public void setSubLocInterface(SelectCityInterface cityInterface) {
        this.cityInterface = cityInterface;
    }

    private void fireAgentSubLocation(AgentSubLocation price) {
        if (cityInterface != null) {
            cityInterface.onLocationSelected(price);
        }
    }
}
