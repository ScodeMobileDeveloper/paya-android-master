package com.paya.paragon.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData;

import java.util.ArrayList;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterSelectedRequirementFeatures extends RecyclerView.Adapter<AdapterSelectedRequirementFeatures.SpecificationViewHolder> {

    private ArrayList<AllAttributesData> mAllAttributesData;
    private String selectedTitleName = "";

    public AdapterSelectedRequirementFeatures(ArrayList<AllAttributesData> allAttributesList) {
        this.mAllAttributesData = allAttributesList;
    }


    @NonNull
    @Override
    public SpecificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_specifications_model,
                parent, false);
        return new SpecificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SpecificationViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        AllAttributesData data = mAllAttributesData.get(position);
        if (position == 0) {
            selectedTitleName = data.getAttrGroupName();
            holder.title.setText(selectedTitleName);
        } else {
            if (!selectedTitleName.equals(data.getAttrGroupName())) {
                selectedTitleName = data.getAttrGroupName();
                holder.title.setText(selectedTitleName);
            } else {
                holder.title.setVisibility(View.GONE);
            }
        }

        holder.featureName.setText(data.getAttrName());
        switch (data.getSelection()) {
            case "multy":
                holder.featureValue.setText(data.getAttrOptName());
                break;
            case "single":
                holder.featureValue.setText(data.getPropertyAttrSelectedOptionID());
                break;
            case "edit":
                holder.featureValue.setText(data.getAttrOptName());
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mAllAttributesData.size();
    }

    static class SpecificationViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView featureName;
        TextView featureValue;

        SpecificationViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.selected_feature_property_type);
            this.featureName = itemView.findViewById(R.id.selected_feature_title);
            this.featureValue = itemView.findViewById(R.id.selected_feature_value);
        }
    }
}
