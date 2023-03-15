package com.paya.paragon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.databinding.ItemGalleryImageBinding;
import com.paya.paragon.model.ImageDataClass;
import com.paya.paragon.utilities.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.GalleryImageAdapterViewHolder> {
    private Context context;
    private List<ImageDataClass> imageList;
    private boolean isMultipleSelection;
    private float dpWidth;

    public GalleryImageAdapter(Context context, List<ImageDataClass> imageList, boolean isMultipleSelection, float dpWidth) {
        this.context = context;
        this.dpWidth = dpWidth;
        this.imageList = imageList;
        this.isMultipleSelection = isMultipleSelection;
    }

    @NonNull
    @NotNull
    @Override
    public GalleryImageAdapterViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemGalleryImageBinding binding = ItemGalleryImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new GalleryImageAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GalleryImageAdapterViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class GalleryImageAdapterViewHolder extends RecyclerView.ViewHolder {
        private ItemGalleryImageBinding binding;

        public GalleryImageAdapterViewHolder(@NonNull @NotNull ItemGalleryImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.setAdapter(this);
            this.binding.setIsMultipleSelection(isMultipleSelection);
        }

        public void bindData() {
            ImageDataClass dataClass = imageList.get(getAdapterPosition());
            binding.imgGalleryImage.getLayoutParams().height = (int) dpWidth;
            binding.chkImage.setChecked(dataClass.isImageSelected());
            Utils.loadUrlImage(binding.imgGalleryImage, dataClass.getImageUrl(), R.drawable.no_image, false);
        }

        public void onImageClick() {
            int position = getAdapterPosition();
            ImageDataClass dataClass = imageList.get(position);
            String path = dataClass.getImageUrl();
            binding.chkImage.setChecked(!binding.chkImage.isChecked());
            imageList.get(position).setImageSelected(!imageList.get(position).isImageSelected());
            notifyItemChanged(getAdapterPosition());
            if (payaGalleryImageAdapterListener != null) {
                payaGalleryImageAdapterListener.onImageClickListener(position, path, isMultipleSelection);
            }

        }
    }

    public interface GalleryImageAdapterCallBackListener {
        void onImageClickListener(int position, String imageUrl, boolean isMultipleSelection);
    }

    private GalleryImageAdapterCallBackListener payaGalleryImageAdapterListener;

    public void setPayaGalleryImageAdapterListener(GalleryImageAdapterCallBackListener payaGalleryImageAdapterListener) {
        this.payaGalleryImageAdapterListener = payaGalleryImageAdapterListener;
    }

}
