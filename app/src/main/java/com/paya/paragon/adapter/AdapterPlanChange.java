package com.paya.paragon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.paya.paragon.R;
import com.paya.paragon.api.myProperties.ActivePlanList;

import java.util.ArrayList;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterPlanChange extends RecyclerView.Adapter<AdapterPlanChange.PlanViewHolder> {

    private ArrayList<ActivePlanList> mPlanList;
    private Context mContext;
    private int selectedPos = 0;

    public AdapterPlanChange(Context context, ArrayList<ActivePlanList> planLists) {
        this.mPlanList = planLists;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_change_plan,
                parent, false);
        return new PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlanViewHolder holder, final int position) {
        holder.setIsRecyclable(false);

        ActivePlanList plan = mPlanList.get(position);

        if (selectedPos == position){
            holder.checked.setSelected(true);
        }

        if (plan.getPlanTitle() != null && !plan.getPlanTitle().equals("")) {
            holder.planName.setText(plan.getPlanTitle());
        }

        if (plan.getPlanDisplayTime() != null && !plan.getPlanDisplayTime().equals("")) {
            String value = mContext.getResources().getString(R.string.duration)
                    + " " + plan.getPlanDisplayTime() + " Days";
            holder.duration.setText(value);
        }

        if (plan.getPlanphotouploadCount() != null && !plan.getPlanphotouploadCount().equals("")) {
            String value = mContext.getResources().getString(R.string.photo_uploading)
                    + " " + plan.getPlanphotouploadCount() + " Nos";
            holder.photo.setText(value);
        }

        if (plan.getPlanVideoUploadingCount() != null && !plan.getPlanVideoUploadingCount().equals("")) {
            String value = mContext.getResources().getString(R.string.video_uploading)
                    + " " + plan.getPlanVideoUploadingCount() + " Nos";
            holder.video.setText(value);
        }

        if (plan.getPlanPropertyDetails() != null && !plan.getPlanPropertyDetails().equals("")) {
            String value = mContext.getResources().getString(R.string.property_description_plan)
                    + " " + plan.getPlanPropertyDetails();
            holder.description.setText(value);
        }

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.checked.setSelected(true);
                selectedPos = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPlanList.size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {

        LinearLayout item;
        ImageView checked;
        TextView duration;
        TextView planName;
        TextView photo;
        TextView video;
        TextView description;

        PlanViewHolder(View itemView) {
            super(itemView);
            this.item = itemView.findViewById(R.id.item_layout_change_plan);
            this.checked = itemView.findViewById(R.id.image_plan_change_check);
            this.planName = itemView.findViewById(R.id.plan_name_item_plan_change);
            this.duration = itemView.findViewById(R.id.duration_item_plan_change);
            this.photo = itemView.findViewById(R.id.photo_item_plan_change);
            this.video = itemView.findViewById(R.id.video_item_plan_change);
            this.description = itemView.findViewById(R.id.description_item_plan_change);
        }
    }
}
