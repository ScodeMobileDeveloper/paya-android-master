package com.paya.paragon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.index.LocationInfo;

import java.util.ArrayList;


public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>
{
    ArrayList<LocationInfo> locationInfoArrayList = new ArrayList<>();
    CityOnClickInterface onClickInterface;

    public CityAdapter(ArrayList<LocationInfo> locationInfo, CityOnClickInterface cityOnClickInterface) {
        this.locationInfoArrayList = locationInfo;
        this.onClickInterface = cityOnClickInterface;
    }

    public void updateCity(ArrayList<LocationInfo> locationInfos) {
        this.locationInfoArrayList = locationInfos;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_layout, parent, false);

        return new CityAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
        holder.textView.setText(locationInfoArrayList.get(position).getCityName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickInterface.onItemClick(locationInfoArrayList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationInfoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }

    public interface CityOnClickInterface{
        void onItemClick(LocationInfo locationInfo);
    }

}
