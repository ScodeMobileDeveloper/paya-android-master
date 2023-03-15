package com.paya.paragon.adapter;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.paya.paragon.R;
import com.paya.paragon.model.ProjectGalleryListModel;
import com.paya.paragon.model.ProjectGalleryModel;

import java.util.List;

public class ProjectGalleryAdapter extends RecyclerView.Adapter<ProjectGalleryAdapter.MyViewHolder> {

    private List<ProjectGalleryModel> projectGalleryModels;
    private final OnItemClickListener listener;
    private LinearLayoutManager mLayoutManager;
    private Activity activity;
    private String imagePath;
    private boolean featured = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        private RecyclerView recyclerView;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            recyclerView = view.findViewById(R.id.project_gallery_horizontel);


        }


        public void bind(final ProjectGalleryModel typeMain, final OnItemClickListener listener) {
            if (typeMain.getProjectGalleryLists().size() > 0) {
                title.setVisibility(View.VISIBLE);
                title.setText(typeMain.getSpecialListingGroupName());
                mLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
                if (typeMain.getSpecialListingGroupName().equalsIgnoreCase("Featured Projects"))
                    featured = true;
                List<ProjectGalleryListModel> galleryListModels = (List<ProjectGalleryListModel>) typeMain.getProjectGalleryLists();
                ProjectGalleryListAdapter projectGalleryListAdapter = new ProjectGalleryListAdapter(featured, activity, imagePath, galleryListModels, new ProjectGalleryListAdapter.OnItemClickListener() {
                    @Override
                    public void onContactClick(ProjectGalleryListModel item) {
                        listener.onSubContactClick(item);
                    }

                    @Override
                    public void onItemClick(ProjectGalleryListModel item) {
                        listener.onSubItemClick(item);
                    }
                });
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(projectGalleryListAdapter);

            }

        }
    }


    public interface OnItemClickListener {
        void onItemClick(ProjectGalleryModel item);

        void onSubItemClick(ProjectGalleryListModel item);

        void onSubContactClick(ProjectGalleryListModel item);
    }

    public ProjectGalleryAdapter(Activity activity, String imagePath, List<ProjectGalleryModel> typeMainList, OnItemClickListener listener) {
        this.activity = activity;
        this.projectGalleryModels = typeMainList;
        this.imagePath = imagePath;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nod_project_gallery, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(projectGalleryModels.get(position), listener);

        //  holder.category_image.setText(typeMain.getGenre());
    }

    @Override
    public int getItemCount() {
        return projectGalleryModels.size();
    }
}
