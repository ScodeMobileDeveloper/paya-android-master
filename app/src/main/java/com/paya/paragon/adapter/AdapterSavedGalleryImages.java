package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.postProperty.loadGalleryImage.SavedImageInfo;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

@SuppressLint("RecyclerView")
public class AdapterSavedGalleryImages extends RecyclerView.Adapter<AdapterSavedGalleryImages.GalleryImageViewHolder> {

    private final ArrayList<SavedImageInfo> imageList;
    private final String imagePath;

    public AdapterSavedGalleryImages(ArrayList<SavedImageInfo> imageList, String imagePath) {
        this.imageList = imageList;
        this.imagePath = imagePath;
    }

    @NonNull
    @Override
    public GalleryImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_saved_gallery_image_model,
                parent, false);
        return new GalleryImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryImageViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final SavedImageInfo data = imageList.get(position);
        Utils.loadUrlImage(holder.galleryImage, Uri.parse(imagePath != null ? imagePath + data.getPropertyImageName() : data.getPropertyImageName()).toString(), R.drawable.no_image_placeholder, false);
        holder.close.setOnClickListener(v -> fireDeleteImage(imageList.get(position).getPropertyImageID(), holder.getAdapterPosition()));
        holder.galleryImage.setOnClickListener(v -> {
            if (mDeleteImageInterface != null) {
                mDeleteImageInterface.onImageClicked(imageList.get(position), position, imagePath);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    static class GalleryImageViewHolder extends RecyclerView.ViewHolder {
        ImageView galleryImage;
        ImageView close;

        GalleryImageViewHolder(View itemView) {
            super(itemView);
            this.galleryImage = itemView.findViewById(R.id.image_view_item_gallery_image_model);
            this.close = itemView.findViewById(R.id.close_item_gallery_image_model);
        }
    }


    public interface DeleteImageInterface {
        void onImageDeleted(String imageID, int position);

        void onImageClicked(SavedImageInfo savedImageInfo, int position, String imagePath);
    }

    private DeleteImageInterface mDeleteImageInterface;

    public void setDeleteImageInterface(DeleteImageInterface deleteImageInterface) {
        this.mDeleteImageInterface = deleteImageInterface;
    }

    private void fireDeleteImage(String imageID, int position) {
        if (mDeleteImageInterface != null) {
            mDeleteImageInterface.onImageDeleted(imageID, position);
        }
    }
}
