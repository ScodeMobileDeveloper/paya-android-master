package com.paya.paragon.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.paya.paragon.R;
import com.paya.paragon.api.budgetList.BudgetPriceData;

import java.util.ArrayList;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterBudgetListing extends RecyclerView.Adapter<AdapterBudgetListing.BudgetViewHolder> {

    private ArrayList<BudgetPriceData> mPriceList;
    private int minPrice = 0;
private Context context;
    public AdapterBudgetListing(ArrayList<BudgetPriceData> priceList,Context context) {
        this.mPriceList = priceList;
        this.context = context;
    }

    public AdapterBudgetListing(ArrayList<BudgetPriceData> priceList, int minPrice,Context context) {
        this.mPriceList = priceList;
        this.minPrice = minPrice;
        this.context = context;
    }

    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_listing_model, parent,
                false);
        return new BudgetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final BudgetViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.setIsRecyclable(false);
        final BudgetPriceData data = mPriceList.get(position);
        holder.priceItem.setText(context.getString(R.string.currency_symbol)+" "+String.valueOf(mPriceList.get(holder.getAdapterPosition()).getPriceValue()));
       // holder.priceItem.setText(String.valueOf(mPriceList.get(holder.getAdapterPosition()).getPriceValue()));

        if (!data.getPrice().equals("+")) {
            if (Integer.valueOf(data.getPrice()) < minPrice) {
                if (position < mPriceList.size()) {
                    holder.priceItem.setTextColor(Color.parseColor("#818286"));
                }
            }
        }

        holder.priceItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!data.getPrice().equals("+")) {
                    if (Integer.valueOf(data.getPrice()) >= minPrice) {
                        fireMinimumPrice(mPriceList.get(position), position);
                    }
                } else {
                    fireLastPrice(mPriceList.get(position), position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPriceList.size();
    }

    static class BudgetViewHolder extends RecyclerView.ViewHolder {
        TextView priceItem;

        BudgetViewHolder(View itemView) {
            super(itemView);
            this.priceItem = itemView.findViewById(R.id.text_price_list_item);
        }
    }

    public interface SelectPriceInterface {
        void onPriceSelected(BudgetPriceData price, int position);
    }

    private SelectPriceInterface mPriceInterface;

    public void setMinPriceInterface(SelectPriceInterface priceInterface) {
        this.mPriceInterface = priceInterface;
    }

    private void fireMinimumPrice(BudgetPriceData price, int position) {
        if (mPriceInterface != null) {
            mPriceInterface.onPriceSelected(price, position);
        }
    }

    public interface SelectLastPriceInterface {
        void onLastPriceSelected(BudgetPriceData price, int position);
    }

    private SelectLastPriceInterface mLastPriceInterface;

    public void setLastPriceInterface(SelectLastPriceInterface lastPriceInterface) {
        this.mLastPriceInterface = lastPriceInterface;
    }

    private void fireLastPrice(BudgetPriceData price, int position) {
        if (mLastPriceInterface != null) {
            mLastPriceInterface.onLastPriceSelected(price, position);
        }
    }
}