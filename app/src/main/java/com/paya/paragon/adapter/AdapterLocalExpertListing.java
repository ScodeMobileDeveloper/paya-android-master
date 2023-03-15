package com.paya.paragon.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.activity.localExpert.ActivityLocalExpertDetails;
import com.paya.paragon.api.localExpertList.LocalExpertListData;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;


@SuppressWarnings("HardCodedStringLiteral")
public class AdapterLocalExpertListing extends RecyclerView.Adapter<AdapterLocalExpertListing.LocalExpertViewHolder> {

    private ArrayList<LocalExpertListData> mLocalExperts;
    private Context mContext;
    String imgUrl;
    String categoryId;

    public AdapterLocalExpertListing(Context context, ArrayList<LocalExpertListData> localExperts, String imgUrl, String categoryId) {
        this.mContext = context;
        this.mLocalExperts = localExperts;
        this.imgUrl = imgUrl;
        this.categoryId = categoryId;
    }

    @Override
    public LocalExpertViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_expert_listing_model, parent, false);
        return new LocalExpertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalExpertViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        String logoUrl = "";
        final LocalExpertListData data = mLocalExperts.get(position);

       /* holder.tv_email.setText(data.getBusinessEmail());
        holder.tv_phone_number.setText(data.getBusinessMobile());*/
        holder.name.setText(data.getBusinessName());
        holder.city_name.setText(data.getCityName());
        holder.phone_num.setText(data.getBusinessMobile());
        holder.category.setText(data.getDealCategoryName());
        String strAddress = data.getAddress();
        holder.address.setText(strAddress);
        if (data.getVoucherCount() == 0) {
            holder.voucher.setVisibility(View.GONE);
        } else {
            holder.voucher.setVisibility(View.VISIBLE);

        }
        Utils.loadUrlImage(holder.logoImage, imgUrl + data.getBusinessLogo(), R.drawable.no_image, false);

        holder.expertItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityLocalExpertDetails.class);
                intent.putExtra("localExpertId", data.getBusinessID());
                intent.putExtra("localExpertName", data.getBusinessName());
                intent.putExtra("userUrlKey", data.getUserUrlKey());
                intent.putExtra("categoryId", categoryId);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLocalExperts.size();
    }

    class LocalExpertViewHolder extends RecyclerView.ViewHolder {

        LinearLayout expertItem;
        ImageView logoImage;
        TextView name;
        TextView city_name;
        TextView phone_num;
        TextView category;
        TextView address;
        TextView voucher;
      //  TextView tv_phone_number;
       // TextView tv_email;

        LocalExpertViewHolder(View itemView) {
            super(itemView);
            this.expertItem = itemView.findViewById(R.id.item_view_local_expert_listing);
            this.logoImage = itemView.findViewById(R.id.image_item_property_listing);
            this.city_name = itemView.findViewById(R.id.city_name);
            this.phone_num = itemView.findViewById(R.id.phone_num);
            this.name = itemView.findViewById(R.id.name_item_local_expert_listing);
            this.category = itemView.findViewById(R.id.category_item_local_expert_listing);
            this.address = itemView.findViewById(R.id.address_item_local_expert_listing);
            this.voucher = itemView.findViewById(R.id.tv_offer_available);
          //  this.tv_phone_number = itemView.findViewById(R.id.tv_phone_number);
            //this.tv_email = itemView.findViewById(R.id.tv_email);
        }
    }
}
