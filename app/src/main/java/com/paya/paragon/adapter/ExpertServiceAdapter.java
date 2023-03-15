package com.paya.paragon.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.localExpertDetials.LocalExpertServiceModel;
import com.paya.paragon.utilities.Utils;

import java.util.List;

public class ExpertServiceAdapter extends RecyclerView.Adapter<ExpertServiceAdapter.MyViewHolder> {

    private List<LocalExpertServiceModel> expertServiceModels;
    private Activity activity;
    private String imagePath;

    @SuppressWarnings({"RedundantCast", "HardCodedStringLiteral"})
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView servicesName,
                servicesDescription;
        private ImageView servicesImage;

        public MyViewHolder(View view) {
            super(view);
            servicesName = (TextView) view.findViewById(R.id.servicesName);
            servicesDescription = (TextView) view.findViewById(R.id.servicesDescription);
            servicesImage = (ImageView) view.findViewById(R.id.servicesImage);
        }


        public void bind(final LocalExpertServiceModel expertServiceModel) {
            servicesName.setText(expertServiceModel.getServicesName());
            servicesDescription.setText(expertServiceModel.getServicesDescription());

            String image = imagePath + expertServiceModel.getServicesImage();
            Utils.loadUrlImage(servicesImage, image, R.drawable.no_image, false);
        }
    }


    public ExpertServiceAdapter(Activity activity, String imagePath, List<LocalExpertServiceModel> expertServiceModels) {
        this.activity = activity;
        this.expertServiceModels = expertServiceModels;
        this.imagePath = imagePath;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_expert_service, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(expertServiceModels.get(position));
    }

    @Override
    public int getItemCount() {
        return expertServiceModels.size();
    }
}
