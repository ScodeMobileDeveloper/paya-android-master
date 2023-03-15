package com.paya.paragon.adapter;


import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.viewingRequests.ViewingRequestData;
import com.paya.paragon.utilities.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterViewRequest extends RecyclerView.Adapter<AdapterViewRequest.RequestViewHolder> {

    private ArrayList<ViewingRequestData> mViewRequestData;
    Context mContext;

    public AdapterViewRequest(Context context, ArrayList<ViewingRequestData> viewRequestData) {
        this.mViewRequestData = viewRequestData;
        this.mContext = context;
    }

    @Override
    public RequestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_request_model, parent, false);
        return new AdapterViewRequest.RequestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RequestViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Utils.collapse(holder.layoutMessage, 10);

        ViewingRequestData data = mViewRequestData.get(position);

        if (data.getPropertyName() != null && !data.getPropertyName().equals("")) {
            holder.title.setText(data.getPropertyName());
        } else {
            holder.title.setText("");
        }

        if (data.getScheduleName() != null && !data.getScheduleName().equals("")) {
            holder.name.setText(data.getScheduleName());
        } else {
            holder.name.setText("");
        }

        if (data.getScheduleEmail() != null && !data.getScheduleEmail().equals("")) {
            holder.email.setText(data.getScheduleEmail());
        } else {
            holder.email.setText("");
        }

        if (data.getSchedulePhone() != null && !data.getSchedulePhone().equals("")) {
            holder.phone.setText(data.getSchedulePhone());
        } else {
            holder.phone.setText("");
        }

        if (data.getScheduleMessage() != null && !data.getScheduleMessage().equals("")) {
            holder.message.setText(data.getScheduleMessage());
        } else {
            holder.message.setText("");
        }

        if (data.getScheduleDate() != null && !data.getScheduleDate().equals("")) {
            String date=dateFormat(data.getScheduleDate());
            if(date.equals("")){
                date=data.getScheduleDate();
            }
            holder.postedDate.setText(date);
        } else {
            holder.postedDate.setText("");
        }

        String selectedSlot = "";
        if (data.getSlotStart() != null && !data.getSlotStart().equals("")) {
            selectedSlot = data.getSlotStart();
        }
        if (data.getSlotEnd() != null && !data.getSlotEnd().equals("")) {
            selectedSlot = selectedSlot + " - " + data.getSlotEnd();
        }
        if (selectedSlot.equals("")) {
            holder.layoutSelectedSlot.setVisibility(View.GONE);
        } else {
            holder.selectedSlot.setText(selectedSlot);
        }

        holder.iconMessageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.isExpanded) {
                    Utils.collapse(holder.layoutMessage, 300);
                    holder.iconMessageView.setImageResource(R.drawable.icon_plus);
                    holder.isExpanded = false;
                } else {
                    Utils.expand(holder.layoutMessage, 300);
                    holder.iconMessageView.setImageResource(R.drawable.icon_minus);
                    holder.isExpanded = true;
                }
            }
        });
    }
    public String dateFormat(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "EEE, MMM d, yyyy h:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);
        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (Exception e) {
            str="";
        }
        return str;
    }

    @Override
    public int getItemCount() {
        return mViewRequestData.size();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder {
        ImageView iconMessageView;
        LinearLayout layoutMessage;
        LinearLayout layoutSelectedSlot;
        TextView title;
        TextView name;
        TextView email;
        TextView phone;
        TextView message;
        TextView postedDate;
        TextView selectedSlot;
        boolean isExpanded;

        RequestViewHolder(View itemView) {
            super(itemView);
            this.iconMessageView = itemView.findViewById(R.id.open_message_item_view_request);
            this.layoutMessage = itemView.findViewById(R.id.layout_show_message_item_view_request);
            this.layoutSelectedSlot = itemView.findViewById(R.id.layout_selected_slot_item_view_request);
            this.title = itemView.findViewById(R.id.title_item_view_request);
            this.name = itemView.findViewById(R.id.name_item_view_request);
            this.email = itemView.findViewById(R.id.email_item_view_request);
            this.phone = itemView.findViewById(R.id.phone_item_view_request);
            this.message = itemView.findViewById(R.id.message_item_view_request);
            this.postedDate = itemView.findViewById(R.id.posted_date_item_view_request);
            this.selectedSlot = itemView.findViewById(R.id.selected_slot_item_view_request);
            this.isExpanded = false;
        }
    }
}
