package com.paya.paragon.adapter;



import android.annotation.SuppressLint;
import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.upgradePlanListing.UpgradePlanListData;

import java.util.ArrayList;


@SuppressWarnings("HardCodedStringLiteral")
public class AdapterPlanInfo extends RecyclerView.Adapter<AdapterPlanInfo.PlanViewHolder> {

    private ArrayList<UpgradePlanListData> mPlanInfoList;
    private Context mContext;
    private String selectedID;
    ItemClickAdapterListener itemClickAdapterListener;
    float CardElevation,MaxCardElevation;
    public AdapterPlanInfo(String selectedId,Context context, ArrayList<UpgradePlanListData> planInfo,ItemClickAdapterListener itemClickAdapterListener) {
        this.mPlanInfoList = planInfo;
        this.mContext = context;
        this.itemClickAdapterListener=itemClickAdapterListener;
        this.selectedID=selectedId;
         CardElevation = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 1.0f,
                mContext.getResources().getDisplayMetrics() );
        MaxCardElevation = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 1,
                mContext.getResources().getDisplayMetrics() );
    }

    @Override
    public PlanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan_info_model, parent, false);
        return new AdapterPlanInfo.PlanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PlanViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        holder.setIsRecyclable(false);
        UpgradePlanListData data = mPlanInfoList.get(position);

        if (data.getPlanTitle() != null && !data.getPlanTitle().equals("")) {
            holder.planName.setText(data.getPlanTitle());
            if (data.getPlanTitle().equalsIgnoreCase("Assisted service")) {
                holder.planType.setVisibility(View.GONE);
            }else  holder.planType.setVisibility(View.VISIBLE);
        } else {
            holder.planName.setText("");
        }
       /* if (data.getUserTypeName() != null && !data.getUserTypeName().equals("")) {
            holder.planType.setText("(" + data.getUserTypeName() + ")");
        } else {
            holder.planType.setText("");
        }*/
        if (data.getPlanPrice() != null && !data.getPlanPrice().equals("")) {
            holder.planAmount.setText(mContext.getString(R.string.currency_symbol)+" " + data.getPlanPrice());
        } else {
            holder.planAmount.setText("");
        }

        if (mPlanInfoList.get(position).getPlanID().equals(selectedID)) {
            holder.planInfoItem.setSelected(true);
            holder.planAmount.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.planName.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.planType.setTextColor(mContext.getResources().getColor(R.color.white));
            holder.down_arrow.setVisibility(View.VISIBLE);
            holder.down_arrow.bringToFront();
            holder.cardView.setCardElevation(0);

        } else {
            holder.planInfoItem.setSelected(false);
            holder.planAmount.setTextColor(mContext.getResources().getColor(R.color.text_color));
            holder.planName.setTextColor(mContext.getResources().getColor(R.color.text_color_search));
            holder.planType.setTextColor(mContext.getResources().getColor(R.color.sub_text));
            holder.down_arrow.setVisibility(View.GONE);
            holder.cardView.setCardElevation(CardElevation);
            holder.cardView.setMaxCardElevation(MaxCardElevation);
        }

        holder.planInfoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedID = mPlanInfoList.get(holder.getAdapterPosition()).getPlanID();
                holder.planInfoItem.setSelected(true);
                holder.planAmount.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.planName.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.planType.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.down_arrow.setVisibility(View.VISIBLE);
                holder.cardView.setCardElevation(0);
                notifyDataSetChanged();
                itemClickAdapterListener.menuClick(position,selectedID);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlanInfoList.size();
    }

    class PlanViewHolder extends RecyclerView.ViewHolder {
        LinearLayout planInfoItem;
        TextView planName;
        TextView planType;
        TextView planAmount;
        ImageView down_arrow;
        CardView cardView;

        PlanViewHolder(View itemView) {
            super(itemView);
            this.planInfoItem = itemView.findViewById(R.id.layout_item_plan_info_model);
            this.planName = itemView.findViewById(R.id.plan_name_item_plan_info);
            this.planType = itemView.findViewById(R.id.plan_type_item_plan_info);
            this.planAmount = itemView.findViewById(R.id.plan_amount_item_plan_info);
            this.down_arrow = itemView.findViewById(R.id.down_arrow);
            this.cardView = itemView.findViewById(R.id.cardView);
        }
    }
    public interface ItemClickAdapterListener {
        void menuClick(int position,String selectedPlanID);
    }
}
