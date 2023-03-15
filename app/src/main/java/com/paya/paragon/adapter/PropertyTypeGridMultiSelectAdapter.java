package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.requirementPropertyType.SubPropertyTypeData;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("RedundantCast")
public class PropertyTypeGridMultiSelectAdapter extends RecyclerView.Adapter<PropertyTypeGridMultiSelectAdapter.MultiSelectViewHolder> {

    private List<SubPropertyTypeData> mPropertyTypeList;
    private ItemClickAdapterListener mItemClickAdapterListener;
    private String imagePath;
    private Context mContext;
    private ArrayList<String> mSelectedTypeIds;

    public PropertyTypeGridMultiSelectAdapter(Context context, String imagePath, List<SubPropertyTypeData> typeMainList,
                                              ArrayList<String> propertyTypeIds, ItemClickAdapterListener itemClickAdapterListener) {
        this.mContext = context;
        this.mPropertyTypeList = typeMainList;
        this.mItemClickAdapterListener = itemClickAdapterListener;
        this.imagePath = imagePath;
        this.mSelectedTypeIds = propertyTypeIds;
    }


    @NonNull
    @Override
    public MultiSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_property_type,
                parent, false);
        return new MultiSelectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MultiSelectViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        holder.setIsRecyclable(false);

        SubPropertyTypeData propertyType = mPropertyTypeList.get(position);
        final String propertyId = propertyType.getPropertyTypeID();
        final String propertyName = propertyType.getPropertyTypeName();

        holder.title.setText(propertyType.getPropertyTypeName());

        if (mSelectedTypeIds != null && mSelectedTypeIds.size() > 0) {
            for (String id : mSelectedTypeIds) {
                if (id.equals(propertyId)) {
                    holder.cardView.setSelected(true);
                }
            }
        }

        String image = imagePath + propertyType.getPropertyTypeIcon();
        Utils.loadUrlImage(holder.category_image, image, R.drawable.no_image, false);

        if (position == mPropertyTypeList.size() - 1) {
            for (SubPropertyTypeData data : mPropertyTypeList) {
                if (mSelectedTypeIds.contains(data.getPropertyTypeID())) {
                    mItemClickAdapterListener.itemClick(data.getPropertyTypeID(), data.getPropertyTypeName(),
                            false);
                }
            }
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = false;
                if (mSelectedTypeIds.size() != 0) {
                    for (String selected : mSelectedTypeIds) {
                        if (propertyId.equals(selected)) {
                            isSelected = true;
                        }
                    }
                }
                holder.cardView.setSelected(!isSelected);
                mItemClickAdapterListener.itemClick(propertyId, propertyName, isSelected);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPropertyTypeList.size();
    }

    static class MultiSelectViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView category_image;
        public LinearLayout cardView;

        MultiSelectViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.category_image = (ImageView) itemView.findViewById(R.id.category_image);
            this.cardView = (LinearLayout) itemView.findViewById(R.id.cardView);
        }
    }

    public interface ItemClickAdapterListener {
        void itemClick(String propertyTypeId, String propertyTypeName, boolean isSelected);
    }
}
