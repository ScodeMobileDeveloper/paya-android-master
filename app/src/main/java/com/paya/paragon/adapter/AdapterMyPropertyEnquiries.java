package com.paya.paragon.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.myPropertyEnquiry.PropertyEnquiryInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdapterMyPropertyEnquiries extends RecyclerView.Adapter<AdapterMyPropertyEnquiries.EnquiryViewHolder> {
    private ArrayList<PropertyEnquiryInfo> mPropertyEnquiries;
    private Context mContext;

    public AdapterMyPropertyEnquiries(Context context, ArrayList<PropertyEnquiryInfo> propertyEnquiries) {
        this.mContext = context;
        this.mPropertyEnquiries = propertyEnquiries;
    }

    @NonNull
    @Override
    public EnquiryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enquiry_listing_model1, parent, false);
        return new EnquiryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnquiryViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        PropertyEnquiryInfo data = mPropertyEnquiries.get(position);

        if (data.getEnquiryName() != null && !data.getEnquiryName().equals("")) {
            holder.name.setText(data.getEnquiryName());
        } else {
            holder.name.setText("");
        }

        if (data.getEnquiryEmail() != null && !data.getEnquiryEmail().equals("")) {
            holder.email.setText(data.getEnquiryEmail());
        } else {
            holder.email.setText("");
        }

        if (data.getEnquiryPhone() != null && !data.getEnquiryPhone().equals("")) {
            holder.phone.setText(data.getEnquiryPhone());
        } else {
            holder.phone.setText("");
        }

        if (data.getEnquiryPostedDate() != null && !data.getEnquiryPostedDate().equals("")) {
            holder.date.setText(convertDateFormat(data.getEnquiryPostedDate()));
        } else {
            holder.date.setText("");
        }
        if (data.getEnquiryContent() != null && !data.getEnquiryContent().equals("")) {
            holder.tv_comment.setText(data.getEnquiryContent());
        } else {
            holder.tv_comment.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return mPropertyEnquiries.size();
    }

    static class EnquiryViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView email;
        TextView phone;
        TextView date;
        TextView tv_comment;

        EnquiryViewHolder(View itemView) {
            super(itemView);
            this.name = itemView.findViewById(R.id.enquiry_user_name_item_enquiry_listing);
            this.email = itemView.findViewById(R.id.enquiry_user_email_item_enquiry_listing);
            this.phone = itemView.findViewById(R.id.enquiry_user_phone_item_enquiry_listing);
            this.date = itemView.findViewById(R.id.enquiry_date_item_enquiry_listing);
            this.tv_comment = itemView.findViewById(R.id.tv_comment);
        }
    }

    private String convertDateFormat(String strDate) {
        String formattedDate = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault());
        try {
            Date date = format.parse(strDate);
            formattedDate = formatDate.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}
