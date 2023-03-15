package com.paya.paragon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.model.ProjectGalleryListModel;
import com.paya.paragon.utilities.Utils;

import java.util.List;

public class ProjectGalleryListAdapter extends RecyclerView.Adapter<ProjectGalleryListAdapter.MyViewHolder> {

    private List<ProjectGalleryListModel> projectGalleryModels;
    private final OnItemClickListener listener;
    private LinearLayoutManager mLayoutManager;
    private Activity activity;
    private String imagePath;
    private boolean featured = false;

    @SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView propertyUnitPriceRange,
                propertyUnitBedRooms, projectName, cityLocName;
        private ImageView projectCoverImage, contactProject;

        public MyViewHolder(View view) {
            super(view);
            propertyUnitPriceRange = (TextView) view.findViewById(R.id.propertyUnitPriceRange);
            propertyUnitBedRooms = (TextView) view.findViewById(R.id.propertyUnitBedRooms);
            projectName = (TextView) view.findViewById(R.id.projectName);
            cityLocName = (TextView) view.findViewById(R.id.cityLocName);
            projectCoverImage = (ImageView) view.findViewById(R.id.projectCoverImage);
            contactProject = (ImageView) view.findViewById(R.id.contact_project);
        }


        public void bind(final ProjectGalleryListModel galleryListModel, final OnItemClickListener listener) {
            String propertyUnitPriceRangeS = galleryListModel.getPropertyUnitPriceRange();
            if (propertyUnitPriceRangeS == null || propertyUnitPriceRangeS.equalsIgnoreCase("null") || propertyUnitPriceRangeS.equalsIgnoreCase("")) {
                propertyUnitPriceRangeS = "Price on Request";
            } else
                propertyUnitPriceRangeS = activity.getString(R.string.currency_symbol) + " " + propertyUnitPriceRangeS;

            propertyUnitPriceRange.setText(propertyUnitPriceRangeS);
            String bedRooms = "";
            if (galleryListModel.getPropertyUnitBedRooms() == null || galleryListModel.getPropertyUnitBedRooms().equalsIgnoreCase("null") || galleryListModel.getPropertyUnitBedRooms().equals("")) {
                bedRooms = galleryListModel.getPropertyUnitTypeNames();

            } else
                bedRooms = galleryListModel.getPropertyUnitBedRooms() + " " + activity.getString(R.string.bed) + " " + galleryListModel.getPropertyUnitTypeNames();

          /* if (featured)
               contactProject.setVisibility(View.INVISIBLE);
           else contactProject.setVisibility(View.VISIBLE);*/

            propertyUnitBedRooms.setText(bedRooms);
            projectName.setText(galleryListModel.getProjectName());
            String cityLoc = galleryListModel.getCityLocName() + ", " + galleryListModel.getCityName();

            cityLocName.setText(cityLoc);
            String image = imagePath + galleryListModel.getProjectCoverImage();
            Utils.loadUrlImage(projectCoverImage, image, R.drawable.no_image_placeholder, false);
            contactProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onContactClick(galleryListModel);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(galleryListModel);
                }
            });


        }
    }


    public interface OnItemClickListener {

        void onContactClick(ProjectGalleryListModel item);

        void onItemClick(ProjectGalleryListModel item);
    }

    public ProjectGalleryListAdapter(boolean featured, Activity activity, String imagePath, List<ProjectGalleryListModel> typeMainList, OnItemClickListener listener) {
        this.activity = activity;
        this.projectGalleryModels = typeMainList;
        this.imagePath = imagePath;
        this.listener = listener;
        this.featured = featured;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_home_widget, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(projectGalleryModels.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return projectGalleryModels.size();
    }
}
