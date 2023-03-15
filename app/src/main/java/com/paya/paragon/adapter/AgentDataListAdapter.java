package com.paya.paragon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.agentList.AgentDataListDATAItemObject;
import com.paya.paragon.databinding.ItemAgentDataListBinding;
import com.paya.paragon.utilities.AppConstant;
import com.paya.paragon.utilities.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AgentDataListAdapter extends RecyclerView.Adapter<AgentDataListAdapter.ViewHolder> {

    private Context context;
    private List<AgentDataListDATAItemObject> dataList;
    private AgentDataListAdapterCallBack callBack;
    private String listFrom;


    public AgentDataListAdapter(Context context, List<AgentDataListDATAItemObject> dataList, String agentDetailsFrom) {
        this.context = context;
        this.dataList = dataList;
        this.listFrom = agentDetailsFrom;
    }



    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemAgentDataListBinding binding = ItemAgentDataListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bindData();
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void updateList(List<AgentDataListDATAItemObject> updatedDataList) {
        int insertPosition = dataList.size();
        dataList.addAll(updatedDataList);
        notifyItemRangeInserted(insertPosition, updatedDataList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ItemAgentDataListBinding binding;

        public ViewHolder(ItemAgentDataListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            ViewGroup.LayoutParams layoutParams = binding.cvParentAgentItem.getLayoutParams();
            layoutParams.width = AppConstant.AGENT_DETAILS.equalsIgnoreCase(listFrom) ? Integer.parseInt(String.valueOf(Math.round(Utils.dipToPixels(binding.cvParentAgentItem.getContext(), 300f)))) : ViewGroup.LayoutParams.MATCH_PARENT;
            binding.cvParentAgentItem.setLayoutParams(layoutParams);
        }

        public void bindData() {
            AgentDataListDATAItemObject agentData = dataList.get(getAdapterPosition());
            if (agentData != null) {
                Utils.loadUrlImage(binding.imgAgentLogo, agentData.getImageBaseUrl() + agentData.getLogo(), R.drawable.no_image_user, false);
                binding.tvAgentName.setText(agentData.getName());
                binding.tvAgentPropCount.setText(String.format(context.getString(R.string.text_agent_properties_count), agentData.getCountProperties()));
            }
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callBack.onItemClick(getAdapterPosition(), agentData, binding.imgAgentLogo);
                }
            });
        }
    }

    public void setCallBack(AgentDataListAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public interface AgentDataListAdapterCallBack {
        void onItemClick(int adapterPosition, AgentDataListDATAItemObject agentData, ImageView imgAgentLogo);
    }
}
