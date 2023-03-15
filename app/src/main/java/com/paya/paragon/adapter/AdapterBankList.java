package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.bankListPropertyPost.BankListData;

import java.util.ArrayList;

public class AdapterBankList extends RecyclerView.Adapter<AdapterBankList.DistrictViewHolder> {

    private ArrayList<BankListData> bankListData;

    public AdapterBankList(ArrayList<BankListData> districtList) {
        this.bankListData = districtList;
    }

    @NonNull
    @Override
    public DistrictViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_price_listing_model, parent, false);
        return new AdapterBankList.DistrictViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (bankListData.get(position) != null && !bankListData.get(position).getBankTitle().equals("")) {
            holder.districtName.setText(bankListData.get(position).getBankTitle());
        } else {
            holder.districtName.setText("");
        }

        holder.districtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fireDistrict(bankListData.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankListData.size();
    }

    static class DistrictViewHolder extends RecyclerView.ViewHolder {
        TextView districtName;

        DistrictViewHolder(View itemView) {
            super(itemView);
            this.districtName = itemView.findViewById(R.id.text_price_list_item);
        }
    }


    public interface SelectDistrictInterface {
        void onDistrictSelected(BankListData district);
    }

    private AdapterBankList.SelectDistrictInterface mDistrictInterface;

    public void setDistrictInterface(AdapterBankList.SelectDistrictInterface districtInterface) {
        this.mDistrictInterface = districtInterface;
    }

    private void fireDistrict(BankListData district) {
        if (mDistrictInterface != null) {
            mDistrictInterface.onDistrictSelected(district);
        }
    }
}
