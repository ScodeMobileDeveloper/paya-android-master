package com.paya.paragon.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.tenancyContractList.TenancyContractModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterTenancyContracts extends RecyclerView.Adapter<AdapterTenancyContracts.ContractViewHolder>{

    private ArrayList<TenancyContractModel> mContractModels;
    Context mContext;
    public ItemClickAdapterListener onClickListener;

    public AdapterTenancyContracts(Context context, ArrayList<TenancyContractModel> contractModels, ItemClickAdapterListener onClickListener) {
        this.mContext = context;
        this.mContractModels = contractModels;
        this.onClickListener=onClickListener;
    }
    @Override
    public ContractViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tenancy_contract_model, parent, false);
        return new AdapterTenancyContracts.ContractViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContractViewHolder holder, final int position) {
        holder.setIsRecyclable(false);
        final TenancyContractModel data = mContractModels.get(position);

        if (data.getTenancyAdded() != null && !data.getTenancyAdded().equals("")){
            holder.date.setText(dateFormat(data.getTenancyAdded()));
        } else {
            holder.date.setText("");
        }

        if (data.getTenancyLandlord() != null && !data.getTenancyLandlord().equals("")){
            holder.ownerNAme.setText(data.getTenancyLandlord());
        } else {
            holder.ownerNAme.setText("");
        }

        if (data.getTenancyTenant() != null && !data.getTenancyTenant().equals("")){
            holder.lesserName.setText(data.getTenancyTenant());
        } else {
            holder.lesserName.setText("");
        }

        holder.downloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.downloadClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mContractModels.size();
    }

    class ContractViewHolder extends RecyclerView.ViewHolder {
        TextView downloadPdf;
        TextView date;
        TextView ownerNAme;
        TextView lesserName;

        ContractViewHolder(View itemView) {
            super(itemView);
            this.downloadPdf = itemView.findViewById(R.id.download_pdf_item_tenancy_contract);
            this.date = itemView.findViewById(R.id.date_item_tenancy_contract);
            this.ownerNAme = itemView.findViewById(R.id.owner_name_item_tenancy_contract);
            this.lesserName = itemView.findViewById(R.id.lesser_name_item_tenancy_contract);
        }
    }
    public interface ItemClickAdapterListener {
        void downloadClick(View v, int position);
    }
//2018-05-07 12:42:40
    public String dateFormat(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy hh:mm aaa";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
}

