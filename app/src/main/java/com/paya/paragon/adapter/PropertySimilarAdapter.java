package com.paya.paragon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.buy_properties.BuyPropertiesModel;
import com.paya.paragon.utilities.Utils;

import java.util.List;

public class PropertySimilarAdapter extends RecyclerView.Adapter<PropertySimilarAdapter.MyViewHolder> {

    private List<BuyPropertiesModel> buyProjectsModels;
    private final OnItemClickListener listener;
    private LinearLayoutManager mLayoutManager;
    private Activity activity;
    private String imagePath;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView propertyUnitPriceRange, propertyUnitBedRooms, projectName, cityLocName;
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


        public void bind(final BuyPropertiesModel buyProjectsModel, final OnItemClickListener listener) {
          /*  String propertyUnitPriceRangeS=buyProjectsModel.getPropertyUnitPriceRange();
            if (propertyUnitPriceRangeS==null || propertyUnitPriceRangeS.equalsIgnoreCase("null")|| propertyUnitPriceRangeS.equalsIgnoreCase(""))
            {
                propertyUnitPriceRangeS="Price on Request";
            }
            else propertyUnitPriceRangeS= activity.getString(R.string.currency_symbol)+" "+propertyUnitPriceRangeS;

                propertyUnitPriceRange.setText(propertyUnitPriceRangeS);*/


            String price = buyProjectsModel.getPropertyPrice();
           /* if (price == null || price.equalsIgnoreCase("null")
                    || price.equalsIgnoreCase("") || price.equalsIgnoreCase("0")) {
                price = "Price on Request";
            } else*/
            price = activity.getString(R.string.currency_symbol) + " " + price;

            propertyUnitPriceRange.setText(price);
            propertyUnitPriceRange.setTextColor(activity.getResources().getColor(R.color.text_color));

            String propertyUnitPricePerSqftS = "";
            propertyUnitPricePerSqftS = buyProjectsModel.getPricePerSqft();
            if (propertyUnitPricePerSqftS != null && !propertyUnitPricePerSqftS.equals("") && !propertyUnitPricePerSqftS.equals("null") && !propertyUnitPricePerSqftS.equals("0")) {
                propertyUnitPricePerSqftS = propertyUnitPricePerSqftS + " per " + activity.getString(R.string.meter_square);
                propertyUnitBedRooms.setVisibility(View.VISIBLE);

            } else propertyUnitBedRooms.setVisibility(View.GONE);


            propertyUnitBedRooms.setText(propertyUnitPricePerSqftS);


            String projectUserCompanyName = "";
            projectUserCompanyName = buyProjectsModel.getProjectUserCompanyName();
            String projectBuilderName = "";
            projectBuilderName = buyProjectsModel.getProjectBuilderName();
            if (projectUserCompanyName != null && !projectUserCompanyName.equals("") && !projectUserCompanyName.equals("null") && !projectUserCompanyName.equals("0")) {
                projectUserCompanyName = projectUserCompanyName;
                // propertyUnitBedRooms.setVisibility(View.VISIBLE);

            } else if (projectBuilderName != null && !projectBuilderName.equals("") && !projectBuilderName.equals("null") && !projectBuilderName.equals("0")) {
                projectUserCompanyName = projectBuilderName;
            }


            projectName.setText(projectUserCompanyName);

            String bedRooms = "";
            if (buyProjectsModel.getProjectBedRoom() == null || buyProjectsModel.getProjectBedRoom().equalsIgnoreCase("null") || buyProjectsModel.getProjectBedRoom().equals("")) {


            } else
                bedRooms = buyProjectsModel.getProjectBedRoom() + " " + activity.getString(R.string.bed) + " ";
            String propertySqrFeet = "";

            propertySqrFeet = buyProjectsModel.getPropertySqrFeet();
            if (propertySqrFeet != null && !propertySqrFeet.equals("") && !propertySqrFeet.equals("null") && !propertySqrFeet.equals("0")) {

                propertySqrFeet = " | " + propertySqrFeet + "  " + activity.getString(R.string.meter_square);
            } else propertySqrFeet = "";
        /*   if (featured)
               contactProject.setVisibility(View.INVISIBLE);
           else contactProject.setVisibility(View.VISIBLE);*/

            // propertyUnitBedRooms.setText(bedRooms);
            // projectName.setText(buyProjectsModel.getProjectName());
            // String cityLoc=buyProjectsModel.getCityLocName();

            cityLocName.setText(bedRooms + propertySqrFeet);
            String image = imagePath + buyProjectsModel.getPropertyCoverImage();
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
        void onContactClick(BuyPropertiesModel item);

        void onItemClick(BuyPropertiesModel item);
    }

    public PropertySimilarAdapter(Activity activity, String imagePath, List<BuyPropertiesModel> typeMainList, OnItemClickListener listener) {
        this.activity = activity;
        this.buyProjectsModels = typeMainList;
        this.imagePath = imagePath;
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
