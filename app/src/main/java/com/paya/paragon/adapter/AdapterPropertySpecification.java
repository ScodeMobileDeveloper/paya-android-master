package com.paya.paragon.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.propertyDetails.SpecificationModel;

import java.util.ArrayList;

public class AdapterPropertySpecification extends RecyclerView.Adapter<AdapterPropertySpecification.SpecificationViewHolder>{
    private ArrayList<SpecificationModel> mDetailsSpecifications;

    public AdapterPropertySpecification(ArrayList<SpecificationModel> specifications){
        this.mDetailsSpecifications = specifications;
    }

    @NonNull
    @Override
    public SpecificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specidication_listing_model, parent, false);
        return new AdapterPropertySpecification.SpecificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecificationViewHolder holder, int position) {
        holder.specification.setText(mDetailsSpecifications.get(position).getAttrName());
        holder.attrDetValue.setText(mDetailsSpecifications.get(position).getAttrDetValue());
    }

    @Override
    public int getItemCount() {
        return mDetailsSpecifications.size();
    }

    @SuppressWarnings("RedundantCast")
    static class SpecificationViewHolder extends RecyclerView.ViewHolder {
        TextView specification;
        TextView attrDetValue;

        SpecificationViewHolder(View itemView) {
            super(itemView);
            this.specification = (TextView) itemView.findViewById(R.id.text_specification_item);
            this.attrDetValue = (TextView) itemView.findViewById(R.id.attrDetValue);
        }
    }
}
