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
import com.paya.paragon.api.agentList.AgentList;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterFollowingAgents extends RecyclerView.Adapter<AdapterFollowingAgents.AgentsViewHolder> {

    private ArrayList<AgentList> mFollowingAgents;
    private Context mContext;
    private ItemClickAdapterListener itemClickAdapterListener;

    public AdapterFollowingAgents(Context context, ArrayList<AgentList> followingAgents,
                                  ItemClickAdapterListener itemClickAdapterListener) {
        this.mContext = context;
        this.mFollowingAgents = followingAgents;
        this.itemClickAdapterListener = itemClickAdapterListener;
    }

    @NonNull
    @Override
    public AgentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_agents_listing_model,
                parent, false);
        return new AgentsViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AgentsViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.setIsRecyclable(false);

        AgentList data = mFollowingAgents.get(position);

        if (data.getUserCompanyName() != null && !data.getUserCompanyName().equals("")) {
            holder.companyName.setText(data.getUserCompanyName());
        } else {
            holder.companyName.setText("");
        }

        if (data.getUserFullName() != null && !data.getUserFullName().equals("")) {
            holder.agentName.setText("Agent:  " + data.getUserFullName());
        } else {
            holder.agentName.setText("");
        }

        String location = "";
        if (data.getCityName() != null && !data.getCityName().equals("")) {
            location = data.getCityName();
        }
        if (data.getStateName() != null && !data.getStateName().equals("")) {
            if (location.equals("")) {
                location = data.getStateName();
            } else {
                location = location + ", " + data.getStateName();
            }
        } else {
            holder.companyName.setText("");
        }
        holder.location.setText(location);

        Utils.loadUrlImage(holder.logo, data.getUserCompanyLogo(), R.drawable.no_image, false);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.deleteClick(position);
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.detailsClick(position);
            }
        });

        holder.imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.detailsClick(position);
            }
        });

        holder.contentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.detailsClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFollowingAgents.size();
    }

    static class AgentsViewHolder extends RecyclerView.ViewHolder {

        TextView companyName;
        TextView agentName;
        TextView location;
        ImageView delete;
        ImageView details;
        ImageView logo;
        LinearLayout imageLayout;
        LinearLayout contentLayout;

        AgentsViewHolder(View itemView) {
            super(itemView);
            this.companyName = itemView.findViewById(R.id.agent_company_name_item_agent_listing);
            this.agentName = itemView.findViewById(R.id.agent_user_name_item_agent_listing);
            this.location = itemView.findViewById(R.id.agent_location_item_agent_listing);
            this.delete = itemView.findViewById(R.id.image_agent_delete_item_agent_listing);
            this.details = itemView.findViewById(R.id.image_agent_view_details_item_agent_listing);
            this.logo = itemView.findViewById(R.id.image_agent_profile_item_agent_listing);
            this.imageLayout = itemView.findViewById(R.id.image_layout);
            this.contentLayout = itemView.findViewById(R.id.content_layout);
        }
    }

    public interface ItemClickAdapterListener {

        void deleteClick(int position);

        void detailsClick(int position);
    }
}
