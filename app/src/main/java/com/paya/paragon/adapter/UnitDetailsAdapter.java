package com.paya.paragon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.projectDetails.AttributesModel;
import com.paya.paragon.api.projectDetails.Units;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

public class UnitDetailsAdapter extends RecyclerView.Adapter<UnitDetailsAdapter.MyViewHolder> {

    private ArrayList<Units> units;
    private Context context;
    private OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView propertyPrice, contact_text, view_plan_text, bedroom, balcony; // bathroom, propertySqrFeet, propertyPlotArea, contact_text, view_plan_text;
        LinearLayout bath_ic_lay, bed_ic_lay, area_ic_lay, apartment_lay;
        ImageView apartment_ic;
        TextView bath_textView, bed_textView, area_textView, apartment_textView;

        public MyViewHolder(View view) {
            super(view);
            propertyPrice = (TextView) view.findViewById(R.id.propertyPrice);
            bedroom = (TextView) view.findViewById(R.id.bed_textView);
            contact_text = (TextView) view.findViewById(R.id.contact_text);
            view_plan_text = (TextView) view.findViewById(R.id.view_plan_text);
            apartment_ic = itemView.findViewById(R.id.apartment_image);
            apartment_lay = itemView.findViewById(R.id.apartment_lay);
            apartment_textView = itemView.findViewById(R.id.apartment_textView);

            area_ic_lay = itemView.findViewById(R.id.area_ic_lay);
            area_textView = itemView.findViewById(R.id.area_textView);

            bed_ic_lay = itemView.findViewById(R.id.bed_ic_lay);
            bed_textView = itemView.findViewById(R.id.bed_textView);

            bath_ic_lay = itemView.findViewById(R.id.bath_ic_lay);
            bath_textView = itemView.findViewById(R.id.bath_textView);
        }
    }

    public interface OnItemClickListener {
        void onContactClick();

        void onFlorClick(Units units);

    }

    public UnitDetailsAdapter(ArrayList<Units> units, Context context, OnItemClickListener listener) {
        this.units = units;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_unit_details, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Units units = this.units.get(position);
        holder.propertyPrice.setText(context.getString(R.string.currency_symbol) + " " + units.getPropertyPrice());
        holder.bedroom.setText(units.getPropertyBedRoom());
        if (units.getPropertyTypeName() != null && !units.getPropertyTypeName().equals("")) {
            holder.apartment_textView.setText(units.getPropertyTypeName());

            if (units.getPropertyTypeIcon() != null && !units.getPropertyTypeIcon().equals(""))
                Utils.loadUrlImage(holder.apartment_ic, units.getPropertyTypeIcon(), R.drawable.no_image, false);
        } else holder.apartment_lay.setVisibility(View.GONE);

        if (units.getPropertySqrFeet() != null && !units.getPropertySqrFeet().equals("")) {
            holder.area_textView.setText(units.getPropertySqrFeet() + " " + context.getString(R.string.meter_square));
            holder.area_ic_lay.setVisibility(View.VISIBLE);
        } else holder.area_ic_lay.setVisibility(View.GONE);

        holder.contact_text.setVisibility(View.GONE);
        ArrayList<AttributesModel> attributes = units.getAttributes();
        String bathroomText = "";
        for (AttributesModel attributesModel : attributes) {
            if (attributesModel.getAttributeKey().equalsIgnoreCase("bath-rooms"))
                bathroomText = attributesModel.getAttrDetValue();
        }
        if (bathroomText.equals(""))
            holder.bath_ic_lay.setVisibility(View.GONE);
        else {
            holder.bath_ic_lay.setVisibility(View.VISIBLE);
            holder.bath_textView.setText(bathroomText + " " + context.getString(R.string.bath));
        }

        holder.contact_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onContactClick();
            }
        });
        holder.view_plan_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFlorClick(units);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (units != null && !units.isEmpty()) {
            return units.size();
        }
        return 0;
    }
}
