package com.paya.paragon.adapter;

import android.app.Activity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.paya.paragon.R;
import com.paya.paragon.model.ProjectModel;
import com.paya.paragon.utilities.Utils;

import java.util.List;

public class ProjectSimilarAdapter extends RecyclerView.Adapter<ProjectSimilarAdapter.MyViewHolder> {

    private List<ProjectModel> buyProjectsModels;
    private final OnItemClickListener listener;
    private LinearLayoutManager mLayoutManager;
    private Activity activity;
    private String imagePath;

    @SuppressWarnings("RedundantCast")
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView propertyUnitPriceRange,propertyUnitBedRooms
                ,projectName,cityLocName;
        private ImageView projectCoverImage,contactProject;



        public MyViewHolder(View view) {
            super(view);
            propertyUnitPriceRange = (TextView) view.findViewById(R.id.propertyUnitPriceRange);
            propertyUnitBedRooms = (TextView) view.findViewById(R.id.propertyUnitBedRooms);
            projectName = (TextView) view.findViewById(R.id.projectName);
            cityLocName = (TextView) view.findViewById(R.id.cityLocName);
            projectCoverImage = (ImageView) view.findViewById(R.id.projectCoverImage);
            contactProject = (ImageView) view.findViewById(R.id.contact_project);



        }


        public void bind(final ProjectModel buyProjectsModel, final OnItemClickListener listener) {
          /*  String propertyUnitPriceRangeS=buyProjectsModel.getPropertyUnitPriceRange();
            if (propertyUnitPriceRangeS==null || propertyUnitPriceRangeS.equalsIgnoreCase("null")|| propertyUnitPriceRangeS.equalsIgnoreCase(""))
            {
                propertyUnitPriceRangeS="Price on Request";
            }
            else propertyUnitPriceRangeS= activity.getString(R.string.currency_symbol)+" "+propertyUnitPriceRangeS;

                propertyUnitPriceRange.setText(propertyUnitPriceRangeS);*/


            String price = buyProjectsModel.getPropertyUnitPriceRange();
            if (price == null || price.equalsIgnoreCase("null")
                    || price.equalsIgnoreCase("") || price.equalsIgnoreCase("0")) {
                price = "Price on Request";
            }

            propertyUnitPriceRange.setText(price);
            propertyUnitPriceRange.setTextColor(activity.getResources().getColor(R.color.text_color));

            String propertyUnitPricePerSqftS = "";
            propertyUnitPricePerSqftS = buyProjectsModel.getPropertyUnitSqftRange();
            if (propertyUnitPricePerSqftS != null && !propertyUnitPricePerSqftS.equals("") && !propertyUnitPricePerSqftS.equals("null") && !propertyUnitPricePerSqftS.equals("0")) {
                propertyUnitPricePerSqftS = propertyUnitPricePerSqftS + "  "+ activity.getString(R.string.meter_square);
                propertyUnitBedRooms.setVisibility(View.VISIBLE);

            } else propertyUnitBedRooms.setVisibility(View.GONE);


            propertyUnitBedRooms.setText(propertyUnitPricePerSqftS);
            projectName.setText(buyProjectsModel.getProjectName());
            String cityLoc=buyProjectsModel.getCityLocName();

            cityLocName.setText(cityLoc);
            String image = imagePath+ buyProjectsModel.getProjectCoverImage();
            Utils.loadUrlImage(projectCoverImage, image, R.drawable.no_image_placeholder, false);
            contactProject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onContactClick(buyProjectsModel);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(buyProjectsModel);
                }
            });



        }
    }



    public interface OnItemClickListener {
        void onContactClick(ProjectModel item);
        void onItemClick(ProjectModel item);
    }
    public ProjectSimilarAdapter(Activity activity, String imagePath, List<ProjectModel> typeMainList, OnItemClickListener listener) {
        this.activity = activity;
        this.buyProjectsModels = typeMainList;
        this.imagePath=imagePath;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_home_widget, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bind(buyProjectsModels.get(position), listener);

      //  holder.category_image.setText(typeMain.getGenre());
    }

    @Override
    public int getItemCount() {
        return buyProjectsModels.size();
    }
}
