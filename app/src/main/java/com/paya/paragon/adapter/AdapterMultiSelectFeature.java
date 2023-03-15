package com.paya.paragon.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.paya.paragon.R;
import com.paya.paragon.classes.MultiSelectData;

import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("WeakerAccess")
public class AdapterMultiSelectFeature extends RecyclerView.Adapter<AdapterMultiSelectFeature.MultiSelectViewHolder> {

    private ArrayList<MultiSelectData> mSelectData;
    private ArrayList<String> selectedIds;
    private ItemClickAdapterListener itemClickAdapterListener;

    public AdapterMultiSelectFeature(ArrayList<MultiSelectData> selectData, String selectedIds,
                                     ItemClickAdapterListener itemClickAdapterListener) {
        this.mSelectData = selectData;
        this.itemClickAdapterListener = itemClickAdapterListener;
        if (selectedIds == null || selectedIds.equals("")) {
            this.selectedIds = new ArrayList<>();
        } else {
            this.selectedIds = new ArrayList<>(Arrays.asList(selectedIds.split(",")));
            changeSelectedValues(this.selectedIds);
        }
    }

    @NonNull
    @Override
    public MultiSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_multi_select_model, parent,
                false);
        return new MultiSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MultiSelectViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        final MultiSelectData data = mSelectData.get(position);
        final String id = data.getAttributeID().substring(data.getAttributeID().lastIndexOf("_") + 1,
                data.getAttributeID().length());
        data.setPropertyAttrSelectedOptionID(id);
        holder.bedRoom.setText(data.getAttrOptName());

        if (selectedIds != null && selectedIds.size() != 0) {
            for (String str : selectedIds) {
                if (str.equals(data.getPropertyAttrSelectedOptionID())) {
                    holder.item.setSelected(true);
                }
            }
            changeSelectedValues(selectedIds);
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = false;
                for (String item : selectedIds) {
                    if (item.equals(data.getPropertyAttrSelectedOptionID())) {
                        isSelected = true;
                    }
                }
                if (!isSelected) {
                    selectedIds.add(data.getPropertyAttrSelectedOptionID());
                    changeSelectedValues(selectedIds);
                    holder.item.setSelected(true);
                } else {
                    selectedIds.remove(data.getPropertyAttrSelectedOptionID());
                    changeSelectedValues(selectedIds);
                    holder.item.setSelected(false);
                }
            }
        });
    }

    private void changeSelectedValues(ArrayList<String> selectedIds) {
        if (selectedIds.size() == 0) {
            itemClickAdapterListener.itemClick("");
        }
        if (selectedIds.size() == 1) {
            itemClickAdapterListener.itemClick(selectedIds.get(0));
        }
        if (selectedIds.size() > 1) {
            StringBuilder ids = new StringBuilder();
            for (String item : selectedIds) {
                if (ids.toString().equals("")) {
                    ids = new StringBuilder(item);
                } else {
                    ids.append(",").append(item);

                }
            }
            itemClickAdapterListener.itemClick(ids.toString());
        }
    }

    @Override
    public int getItemCount() {
        return mSelectData.size();
    }

    static class MultiSelectViewHolder extends RecyclerView.ViewHolder {
        TextView bedRoom;
        LinearLayout item;

        MultiSelectViewHolder(View itemView) {
            super(itemView);
            this.bedRoom = itemView.findViewById(R.id.bed_room_number_item_bed_room_model);
            this.item = itemView.findViewById(R.id.item_number_item_bed_room_model);
        }
    }

    public interface ItemClickAdapterListener {

        void itemClick(String selectionValues);
    }
}
