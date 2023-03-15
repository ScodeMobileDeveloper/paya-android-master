package com.paya.paragon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.mySubscriptions.MySubscriptionsData;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterMySubscriptions extends RecyclerView.Adapter<AdapterMySubscriptions.SubscriptionViewHolder> {

    private ArrayList<MySubscriptionsData> mSubscriptionsData;
    private Context mContext;

    public AdapterMySubscriptions(Context context, ArrayList<MySubscriptionsData> subscriptionsData) {
        this.mSubscriptionsData = subscriptionsData;
        this.mContext = context;
    }


    @NonNull
    @Override
    public AdapterMySubscriptions.SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_subscriptions_model, parent,
                false);
        return new SubscriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMySubscriptions.SubscriptionViewHolder holder, int position) {
        holder.setIsRecyclable(false);

        if (position == mSubscriptionsData.size() - 1) {
            holder.finalPadding.setVisibility(View.VISIBLE);
        } else {
            holder.finalPadding.setVisibility(View.GONE);
        }

        MySubscriptionsData data = mSubscriptionsData.get(position);

        holder.planName.setText(data.getPlanTitle());

        String amount = mContext.getResources().getString(R.string.currency_symbol)
                + " " + data.getPlanPrice();
        holder.planAmount.setText(amount);
        int total = 0;
        if (data.getPlanPropertyCount() != null && !data.getPlanPropertyCount().equals("") &&
                data.getPlanPropertyCountBalance() != null && !data.getPlanPropertyCountBalance().equals("")) {
            int planPropertyCount = Integer.parseInt(data.getPlanPropertyCount());
            int planPropertyCountBalance = Integer.parseInt(data.getPlanPropertyCountBalance());
            total = planPropertyCount - planPropertyCountBalance;
        }
        holder.propertyPosted.setText(""+total);

        holder.availableCredit.setText(data.getPlanPropertyCountBalance());

        holder.enquiryCount.setText(data.getPlanEnquiryCountBalance());

        String date = mContext.getResources().getString(R.string.subscribed_date)
                + " " + Utils.convertToDateOnlyFormat(data.getPlanPurchaseDate());
        holder.planStartDate.setText(date);

        holder.planStatus.setText(data.getIsexpired());
        if (data.getIsexpired().equalsIgnoreCase("Active")) {
            holder.planStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_plan_status_active,
                    0, 0, 0);
        } else {
            holder.planStatus.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_plan_status_expired,
                    0, 0, 0);
        }
    }

    @Override
    public int getItemCount() {
        return mSubscriptionsData.size();
    }

    class SubscriptionViewHolder extends RecyclerView.ViewHolder {
        TextView planName;
        TextView planStartDate;
        TextView planStatus;
        TextView planAmount;
        TextView propertyPosted;
        TextView availableCredit;
        TextView enquiryCount;
        TextView viewDetails;
        View finalPadding;

        SubscriptionViewHolder(View itemView) {
            super(itemView);
            this.planName = itemView.findViewById(R.id.text_plan_title);
            this.planStartDate = itemView.findViewById(R.id.text_plan_start_date);
            this.planStatus = itemView.findViewById(R.id.text_plan_status);
            this.planAmount = itemView.findViewById(R.id.text_plan_amount);
            this.propertyPosted = itemView.findViewById(R.id.text_plan_property_posted);
            this.availableCredit = itemView.findViewById(R.id.text_plan_remain_credit);
            this.enquiryCount = itemView.findViewById(R.id.text_plan_enquiry_balance_count);
            this.viewDetails = itemView.findViewById(R.id.text_plan_view_payment);
            this.finalPadding = itemView.findViewById(R.id.layout_padding_view);
        }
    }
}
