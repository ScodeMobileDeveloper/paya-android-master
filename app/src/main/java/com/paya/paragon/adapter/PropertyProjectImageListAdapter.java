package com.paya.paragon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.databinding.AdapterImageListItemBinding;
import com.paya.paragon.utilities.Utils;

import java.util.List;

public class PropertyProjectImageListAdapter extends RecyclerView.Adapter<PropertyProjectImageListAdapter.ViewHolder> {

    private PropertyProjectImageListAdapterCallback callback;
    private List<String> imageList;

    public PropertyProjectImageListAdapter(PropertyProjectImageListAdapterCallback callback, List<String> imageList) {
        this.callback = callback;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdapterImageListItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_image_list_item, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindValues();
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private AdapterImageListItemBinding binding;

        public ViewHolder(@NonNull AdapterImageListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.propertyProjectImageView.setOnClickListener(imagePreviewClick());
        }

        private View.OnClickListener imagePreviewClick() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.onImagePreviewClick(imageList.get(getAdapterPosition()), getAdapterPosition());
                    }
                }
            };
        }

        public void onBindValues() {
            Utils.loadUrlImage(binding.propertyProjectImageView, imageList.get(getAdapterPosition()), R.drawable.no_image_placeholder, false);
        }

    }

    public interface PropertyProjectImageListAdapterCallback {
        void onImagePreviewClick(String imageUrl, int position);
    }
}
