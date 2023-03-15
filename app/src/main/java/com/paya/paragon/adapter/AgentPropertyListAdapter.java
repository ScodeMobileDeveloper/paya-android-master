package com.paya.paragon.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.activity.details.ActivityPropertyDetails;
import com.paya.paragon.api.agentProperty.AgentPropertyModel;
import com.paya.paragon.utilities.Utils;

import java.util.List;

public class AgentPropertyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<AgentPropertyModel> agentPropertyModels;
    private int row_index = -1;
    private String imagePath = "";
    String totalCount;
    private Context context;
    boolean showProgrss = true;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView propertyUnitPriceRange, propertyUnitPricePerSqft,
                projectBuilderName, cityLocName;
        public ImageView projectCoverImage;


        public MyViewHolder(View view) {
            super(view);
            propertyUnitPriceRange = (TextView) view.findViewById(R.id.propertyUnitPriceRange);
            propertyUnitPricePerSqft = (TextView) view.findViewById(R.id.propertyUnitPricePerSqft);
            projectBuilderName = (TextView) view.findViewById(R.id.projectBuilderName);
            cityLocName = (TextView) view.findViewById(R.id.cityLocName);

            projectCoverImage = (ImageView) view.findViewById(R.id.projectCoverImage);


        }


        public void bind(final AgentPropertyModel agentProjectModel, final int position) {
            propertyUnitPriceRange.setText(context.getString(R.string.currency_symbol) + " " + agentProjectModel.getPropertyPrice());

            String propertyUnitPricePerSqftS = agentProjectModel.getPropertyUnitPricePerSqft();
            if (propertyUnitPricePerSqftS != null && !propertyUnitPricePerSqftS.equals("") && !propertyUnitPricePerSqftS.equals("0")) {
                propertyUnitPricePerSqftS = context.getString(R.string.currency_symbol) + " " + agentProjectModel.getPropertyUnitPricePerSqft() + " per "+ context.getString(R.string.meter_square);
                propertyUnitPricePerSqft.setText(propertyUnitPricePerSqftS);
                propertyUnitPricePerSqft.setVisibility(View.VISIBLE);

            }else propertyUnitPricePerSqft.setVisibility(View.GONE);
            propertyUnitPricePerSqftS = "";


     /*       String cityLocNameString = agentProjectModel.getCityLocName() + " , " + agentProjectModel.getCityName();

            cityLocName.setText(cityLocNameString);*/

            projectBuilderName.setText(agentProjectModel.getPropertyName());
            String bedRooms = "";
            if (agentProjectModel.getPropertyBedRoom() == null || agentProjectModel.getPropertyBedRoom().equalsIgnoreCase("null") || agentProjectModel.getPropertyBedRoom().equals("")) {
                bedRooms = agentProjectModel.getPropertyTypeName();

            } else
                bedRooms = agentProjectModel.getPropertyBedRoom() + " "+context.getString(R.string.bed)+" " + agentProjectModel.getPropertyTypeName();
            cityLocName.setText(bedRooms);

            String image = imagePath + agentProjectModel.getPropertyCoverImage();
            Utils.loadUrlImage(projectCoverImage, image, R.drawable.no_image_placeholder, false);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,
                            ActivityPropertyDetails.class);
                    intent.putExtra("propertyID", agentProjectModel.getPropertyID());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                   /* Intent intent = new Intent(context, AgentDetailsActivity.class);
                    intent.putExtra("agentDetail", agentProjectModel);
                    context.startActivity(intent);*/
                }
            });


        }
    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }

    public AgentPropertyListAdapter(Context context, String imagePath, List<AgentPropertyModel> agentPropertyModels, boolean showProgrss, String totalCount) {
        this.agentPropertyModels = agentPropertyModels;
        this.imagePath = imagePath;
        this.totalCount = totalCount;
        this.context = context;
        this.showProgrss = showProgrss;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.nod_agent_project, parent, false);
            viewHolder = new MyViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);
            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }

  
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            final MyViewHolder viewHolder = (MyViewHolder) holder;
            AgentPropertyModel agentProjectModel=agentPropertyModels.get(position);
            viewHolder.bind(agentProjectModel,position);


        } else if (holder instanceof ProgressViewHolder) {
            if (agentPropertyModels.size() != Integer.parseInt(totalCount)) {
                final ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
                progressViewHolder.progressBar.setVisibility(View.VISIBLE);
                progressViewHolder.progressBar.setIndeterminate(true);
            } else {
                final ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
                progressViewHolder.progressBar.setVisibility(View.GONE);
            }

        }
    }
    @Override
    public int getItemViewType(int position) {
        int a = 0;
        if (position < agentPropertyModels.size()) {
            a = 0;
        } else {
            a = 1;
        }
        return a;
    }

    @Override
    public int getItemCount() {
        int size;
        if (showProgrss) {
            size = agentPropertyModels.size() + 1;
        } else {
            size = agentPropertyModels.size();
        }
        return size;
    }
}
