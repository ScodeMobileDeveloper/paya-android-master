package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.paya.paragon.R;
import com.paya.paragon.api.BuyAgents.BuyAgentsModel;
import com.paya.paragon.utilities.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BuyAgentListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BuyAgentsModel> agentLists;
    private Context con;
    private boolean showProgress;
    private ItemClickAdapterListener onClickListener;
    private String imageURLAgents;

    public BuyAgentListAdapter(String imageURLAgents, boolean showProgrss, List<BuyAgentsModel> agentLists, Context con, ItemClickAdapterListener onClickListener) {
        this.agentLists = agentLists;
        this.con = con;
        this.showProgress = showProgrss;
        this.onClickListener = onClickListener;
        this.imageURLAgents = imageURLAgents;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_agent_list_model, parent, false);
            viewHolder = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);
            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            Utils.loadUrlImage(viewHolder.ivCover, imageURLAgents + agentLists.get(position).getUserCompanyLogo(), R.drawable.no_logo, false);
            viewHolder.tvAmount.setText(agentLists.get(position).getUserCompanyName());
            viewHolder.tvSub.setText("Agent: " + agentLists.get(position).getUserFirstName() + " " + agentLists.get(position).getUserLastName());
            viewHolder.tvDeal.setText(agentLists.get(position).getDealsin());
            viewHolder.tvPosted.setText(agentLists.get(position).getProjectCount() + " Projects | " +
                    agentLists.get(position).getSellCount() + " Properties for sale | " + agentLists.get(position).getRentCount() +
                    " Properties for Rent "
            );
            viewHolder.llMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.itemClick(view, position);
                }
            });

        } else if (holder instanceof ProgressViewHolder) {
            final ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int a = 0;
        if (position < agentLists.size()) {
            a = 0;
        } else {
            a = 1;
        }
        return a;
    }

    @Override
    public int getItemCount() {
        int size;
        if (showProgress) {
            size = agentLists.size() + 1;
        } else {
            size = agentLists.size();
        }
        return size;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvAmount, tvSub;
        TextView tvDeal, tvPosted;
        LinearLayout llMain;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvSub = itemView.findViewById(R.id.tv_sub);
            tvDeal = itemView.findViewById(R.id.tv_deal);
            tvPosted = itemView.findViewById(R.id.tv_posted);
            llMain = itemView.findViewById(R.id.ll_main);


        }
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }


    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);
    }

    public String dateFormat(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "MMM dd, yyyy";
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
