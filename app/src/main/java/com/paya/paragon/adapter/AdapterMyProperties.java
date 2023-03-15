package com.paya.paragon.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.myProperties.MyPropertiesItem;
import com.paya.paragon.base.commonClass.GlobalValues;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterMyProperties extends RecyclerView.Adapter<AdapterMyProperties.PropertiesViewHolder> {

    private ArrayList<MyPropertiesItem> mPropertyModels;
    private Context mContext;
    private ItemClickAdapterListener itemClickAdapterListener;

    public AdapterMyProperties(Context context, ArrayList<MyPropertiesItem> propertyModels,
                               ItemClickAdapterListener itemClickAdapterListener) {
        this.mPropertyModels = propertyModels;
        this.mContext = context;
        this.itemClickAdapterListener = itemClickAdapterListener;
    }

    @NonNull
    @Override
    public PropertiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_property_model,
                parent, false);
        return new AdapterMyProperties.PropertiesViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PropertiesViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final MyPropertiesItem data = mPropertyModels.get(position);

        if (position == mPropertyModels.size() - 1) {
            holder.padding.setVisibility(View.VISIBLE);
        } else {
            holder.padding.setVisibility(View.GONE);
        }

        //Name
        if (data.getPropertyName() != null && !data.getPropertyName().equals("")) {
            holder.name.setText(data.getPropertyName());
        } else {
            holder.name.setText("");
        }

        //Price
       /* if (data.getCurrencySymbolLeft() != null && !data.getCurrencySymbolLeft().equals("")) {
            price = price + mContext.getResources().getString(R.string.currency_symbol);
        }
        if (data.getPropertyPrice() != null && !data.getPropertyPrice().equals("")) {
            price = price + " " + data.getPropertyPrice() + " ";
        }
        if (data.getCurrencySymbolRight() != null && !data.getCurrencySymbolRight().equals("")) {
            price = price + data.getCurrencySymbolRight();
        }*/
        String price = mContext.getString(R.string.currency_symbol) + " " + data.getPropertyPrice();;
            if(data.getCurrencyID_5()!=null&&!data.getCurrencyID_5().equalsIgnoreCase("0")){
                price = mContext.getString(R.string.currency_symbol)+" "+data.getPropertyPrice();
            }
            else if(data.getCurrencyID_1()!=null&&!data.getCurrencyID_1().equalsIgnoreCase("0")){
                price = mContext.getString(R.string.iqd_currency_symbol) + " " + data.getPropertyPrice();
            }


        holder.price.setText(price);

        if(data.getPropertyPricePerM2() != null && !data.getPropertyPricePerM2().isEmpty()&&!data.getPropertyPricePerM2().equals("0")){
            holder.priceperm2.setText(mContext.getString(R.string.priceinM));

        }
        else{
            holder.priceperm2.setText(mContext.getString(R.string.total_price));

        }
        //Address
        String address = "";
        if (data.getPropertyLocality() != null && !data.getPropertyLocality().equals("")) {
            address = address + data.getPropertyLocality();
        }
        /*if (data.getAddress2() != null && !data.getAddress2().equals("")) {
            if (address.equals("")) {
                address = address + data.getAddress2();
            } else {
                address = address + ", " + data.getAddress2();
            }
        }
        if (data.getCityName() != null && !data.getCityName().equals("")) {
            if (address.equals("")) {
                address = address + data.getCityName();
            } else {
                address = address + ", " + data.getCityName();
            }
        }*/
        if (data.getStateName() != null && !data.getStateName().equals("")) {
            if (address.equals("")) {
                address = address + data.getStateName();
            } else {
                address = address + ", " + data.getStateName();
            }
        }
        holder.address.setText(address);


        //Area
        if (data.getPropertySqrFeet() != null && !data.getPropertySqrFeet().equals("")) {
            holder.area.setText(data.getPropertySqrFeet() +  mContext.getString(R.string.meter_square));
        } else {
            holder.area.setText("");
        }

        if (data.getPropEnqCount() != null && !data.getPropEnqCount().equals("")) {
            holder.enquiryNumber.setText(mContext.getString(R.string.no_enquiry) + " " + data.getPropEnqCount());
        } else {
            holder.enquiryNumber.setText(mContext.getString(R.string.no_enquiry) + " 0");
        }

        if (data.getRestotalViews() != null && !data.getRestotalViews().equals("")) {
            holder.viewNumber.setText(mContext.getString(R.string.no_of_view) + " " + data.getRestotalViews());
        } else {
            holder.viewNumber.setText(mContext.getString(R.string.no_of_view) + " 0");
        }

        String imageUrl = "";
        if (GlobalValues.myPropertiesImageUrl != null && !GlobalValues.myPropertiesImageUrl.equals("")) {
            imageUrl = GlobalValues.myPropertiesImageUrl;
        }
        if (data.getPropertyCoverImage() != null && !data.getPropertyCoverImage().equals("")) {
            imageUrl += data.getPropertyCoverImage();
        }
        // Plan alert visibility
        if(data.getPropertyStatus()!=null && !data.getPropertyStatus().equals("")){
            if(data.getPropertyStatus().equals("Active")){
                holder.noPlanAlert.setText(R.string.your_property_published_successfully);
                holder.noPlanAlert.setTextColor(mContext.getResources().getColor(R.color.quantum_googgreen));

            }else {
                holder.noPlanAlert.setText(R.string.thanks_will_publish_soon);
                holder.noPlanAlert.setTextColor(mContext.getResources().getColor(R.color.red));
            }

        }else {
            holder.noPlanAlert.setText(R.string.thanks_will_publish_soon);
            holder.noPlanAlert.setTextColor(mContext.getResources().getColor(R.color.red));
        }
        //plan details

        if (data.getPlanID() != null && !data.getPlanID().equals("")) {
            if (data.getPlanID().equals("0")) {
                holder.noPlanAlert.setVisibility(View.VISIBLE);
                holder.planInfoLayout.setVisibility(View.GONE);
            } else {
                holder.noPlanAlert.setVisibility(View.GONE);
                holder.planInfoLayout.setVisibility(View.VISIBLE);
            }
        } else {
            holder.noPlanAlert.setVisibility(View.VISIBLE);
            holder.planInfoLayout.setVisibility(View.GONE);
        }

        if (data.getPlanTitle() != null && !data.getPlanTitle().equals("")) {
            holder.planName.setText(data.getPlanTitle());
        }
        if (data.getPremiumEndDate() != null && !data.getPremiumEndDate().equals("")) {
            holder.planExpiry.setText(Utils.convertToDateOnlyFormat(data.getPremiumEndDate()));
        }
        if (data.getChangePlan() != null && !data.getChangePlan().equals("")) {
            if (data.getChangePlan().equals("false")) {
                holder.changePlan.setVisibility(View.GONE);
            }
        }

        Utils.loadUrlImage(holder.coverImage, imageUrl, R.drawable.no_image_placeholder, false);
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

        holder.enquiryNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.enquiryClick(position);
            }
        });

       /* holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickAdapterListener.itemClick(position);
            }
        });*/

        holder.changePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.changePlanClick(position);
            }
        });

        holder.upgradePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.upgradePlanClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mPropertyModels.size();
    }

    static class PropertiesViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView price;
        TextView priceperm2;
        TextView address;
        TextView enquiryNumber;
        TextView viewNumber;
        TextView soldOutStatus;
        TextView area;
        TextView noPlanAlert;
        LinearLayout edit;
        LinearLayout delete;
        LinearLayout item;
        LinearLayout planInfoLayout;
        ImageView coverImage;
        View padding;
        TextView planName;
        TextView planExpiry;
        TextView upgradePlan;
        TextView changePlan;

        PropertiesViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.property_name_item_my_properties);
            this.price = itemView.findViewById(R.id.property_price_item_my_properties);
            this.priceperm2 = itemView.findViewById(R.id.tv_ppm2);
            this.address = itemView.findViewById(R.id.property_address_item_my_properties);
            this.enquiryNumber = itemView.findViewById(R.id.property_enquiry_number_item_my_properties);
            this.viewNumber = itemView.findViewById(R.id.property_view_number_item_my_properties);
            this.soldOutStatus = itemView.findViewById(R.id.property_sold_out_status_item_my_properties);
            this.edit = itemView.findViewById(R.id.property_edit_item_my_properties);
            this.delete = itemView.findViewById(R.id.property_delete_item_my_properties);
            this.coverImage = itemView.findViewById(R.id.property_image_item_my_properties);
            this.area = itemView.findViewById(R.id.property_area_item_my_properties);
            this.padding = itemView.findViewById(R.id.padding_view);
            this.planName = itemView.findViewById(R.id.plan_name_item_my_property);
            this.planExpiry = itemView.findViewById(R.id.plan_expiry_date_item_my_property);
            this.upgradePlan = itemView.findViewById(R.id.upgrade_plan_item_my_properties);
            this.changePlan = itemView.findViewById(R.id.change_plan_item_my_properties);
            this.item = itemView.findViewById(R.id.item_view_property_listing);
            this.planInfoLayout = itemView.findViewById(R.id.layout_plan_info_item_my_property);
            this.noPlanAlert = itemView.findViewById(R.id.text_alert_no_plan_selected_item_my_properties);
        }
    }

    public interface ItemClickAdapterListener {

        void deleteClick(int position);

        void editClick(int position);

        void enquiryClick(int position);

        void itemClick(int position);

        void changePlanClick(int position);

        void upgradePlanClick(int position);
    }
}
