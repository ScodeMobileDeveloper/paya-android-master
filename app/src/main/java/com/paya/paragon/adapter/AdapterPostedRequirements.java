package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.postedRequirements.PostedRequirementsDataModel;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

@SuppressWarnings({"HardCodedStringLiteral", "FieldCanBeLocal", "unused"})
public class AdapterPostedRequirements extends RecyclerView.Adapter<AdapterPostedRequirements.RequirementsViewHolder> {

    private ArrayList<PostedRequirementsDataModel> mPostedRequirements;
    private Context mContext;
    private ItemClickAdapterListener itemClickAdapterListener;

    public AdapterPostedRequirements(Context context, ArrayList<PostedRequirementsDataModel> postedRequirements,
                                     ItemClickAdapterListener itemClickAdapterListener) {
        this.mPostedRequirements = postedRequirements;
        this.mContext = context;
        this.itemClickAdapterListener = itemClickAdapterListener;
    }

    @NonNull
    @Override
    public RequirementsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_posted_requirements_model,
                parent, false);
        return new AdapterPostedRequirements.RequirementsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequirementsViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final PostedRequirementsDataModel data = mPostedRequirements.get(position);

        //Title
        String title = "";
        if (data.getReqBedrooms() != null && !data.getReqBedrooms().equals("")) {
            title = title + data.getReqBedrooms() + " Beds ";
        }
        if (data.getPropertyTypeName() != null && !data.getPropertyTypeName().equals("")) {
            title = title + data.getPropertyTypeName();
        }
        if (data.getReqPurpose() != null && !data.getReqPurpose().equals("")) {
            if (data.getReqPurpose().equalsIgnoreCase("Sell")) {
                data.setReqPurpose(mContext.getString(R.string.for_buy));
            } else {
                data.setReqPurpose(mContext.getString(R.string.for_rent));
            }
            title = title + " " + data.getReqPurpose();
        }
        holder.title.setText(title);

        //Budget and price info
        String info = "";

        String reqMaxPrice = data.getReqMaxPrice();
        String reqMinPrice = data.getReqMinPrice();
        if (reqMinPrice != null && reqMaxPrice != null && !reqMinPrice.equals("") && !reqMaxPrice.equals("")) {
            if (reqMinPrice.equals("0")
                    && reqMaxPrice.equals("0")) {
                info = mContext.getResources().getString(R.string.no_budget);
            } else if (reqMaxPrice.equalsIgnoreCase(reqMinPrice)) {
                info = reqMaxPrice;
            } else if (!reqMinPrice.equals("0") && reqMaxPrice.equals("0")) {
                info = mContext.getResources().getString(R.string.currency_symbol)
                        + " " + reqMinPrice + " " + mContext.getResources().getString(R.string.onwards);
            } else if (reqMinPrice.equals("0") && !reqMaxPrice.equals("0")) {
                info = mContext.getResources().getString(R.string.up_to) + " " + mContext.getResources().getString(R.string.currency_symbol)
                        + " " + reqMaxPrice;
            } else info = mContext.getResources().getString(R.string.currency_symbol)
                    + " " + reqMinPrice + " - " + mContext.getResources().getString(R.string.currency_symbol)
                    + " " + reqMaxPrice;


            holder.budget.setText(info);
        } else holder.budget.setText(mContext.getResources().getString(R.string.no_budget));

        if (data.getLocDetails() != null && !data.getLocDetails().equals("")) {
            holder.location.setText(data.getLocDetails());
        } else {
            holder.location.setText("");
        }

        //Personal Info
        if (data.getReqName() != null && !data.getReqName().equals("")) {
            holder.name.setText(data.getReqName());
        } else {
            holder.name.setText("");
        }
        if (data.getReqEmail() != null && !data.getReqEmail().equals("")) {
            holder.email.setText(data.getReqEmail());
        } else {
            holder.email.setText("");
        }
        if (data.getReqPhone() != null && !data.getReqPhone().equals("")) {
            holder.phone.setText(data.getReqPhone());
        } else {
            holder.phone.setText("");
        }

        //date
        if (data.getReqPostedDate() != null && !data.getReqPostedDate().equals("")) {
            String date = "| " + mContext.getString(R.string.posted_on_col) + " " + Utils.convertToDateOnlyFormat(data.getReqPostedDate());
            holder.date.setText(date);
        } else {
            holder.date.setText("");
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.editClick(position);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.deleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPostedRequirements.size();
    }

    static class RequirementsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView budget;
        TextView location;
        TextView name;
        TextView email;
        TextView phone;
        TextView date;
        LinearLayout edit;
        LinearLayout delete;

        RequirementsViewHolder(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title_item_posted_requirement);
            this.budget = itemView.findViewById(R.id.budget_item_posted_requirements);
            this.location = itemView.findViewById(R.id.locality_item_posted_requirements);
            this.name = itemView.findViewById(R.id.user_name_item_posted_requirement);
            this.email = itemView.findViewById(R.id.user_email_item_posted_requirement);
            this.phone = itemView.findViewById(R.id.user_mobile_item_posted_requirement);
            this.date = itemView.findViewById(R.id.posted_date_item_posted_requirement);
            this.edit = itemView.findViewById(R.id.edit_item_posted_requirement);
            this.delete = itemView.findViewById(R.id.delete_item_posted_requirement);
        }
    }

    public interface ItemClickAdapterListener {

        void deleteClick(int position);

        void editClick(int position);
    }

}
