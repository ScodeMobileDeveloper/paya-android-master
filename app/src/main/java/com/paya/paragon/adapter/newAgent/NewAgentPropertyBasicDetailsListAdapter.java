package com.paya.paragon.adapter.newAgent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.paya.paragon.R;
import com.paya.paragon.api.newAgent.AgentPropertyBasicDetails;
import com.paya.paragon.databinding.ItemNewAgentPropertyBasicDetailsRowBinding;
import com.paya.paragon.utilities.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NewAgentPropertyBasicDetailsListAdapter extends RecyclerView.Adapter<NewAgentPropertyBasicDetailsListAdapter.NewAgentPropertyBasicDetailsListAdapterViewHolder> {

    private List<AgentPropertyBasicDetails> propertyList;
    private NewAgentPropertyBasicDetailsListAdapterCallBack callBack;

    public NewAgentPropertyBasicDetailsListAdapter(List<AgentPropertyBasicDetails> propertyList) {
        this.propertyList = propertyList;
    }

    @NonNull
    @NotNull
    @Override
    public NewAgentPropertyBasicDetailsListAdapterViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemNewAgentPropertyBasicDetailsRowBinding binding = ItemNewAgentPropertyBasicDetailsRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewAgentPropertyBasicDetailsListAdapterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NewAgentPropertyBasicDetailsListAdapterViewHolder holder, int position) {
        holder.onBind();
    }

    @Override
    public int getItemCount() {
        return propertyList.size();
    }

    public void updateList(List<AgentPropertyBasicDetails> updatePropertyList) {
        int insertPosition = this.propertyList.size();
        this.propertyList.addAll(updatePropertyList);
        notifyItemRangeInserted(insertPosition, updatePropertyList.size());
    }

    public class NewAgentPropertyBasicDetailsListAdapterViewHolder extends RecyclerView.ViewHolder {

        private ItemNewAgentPropertyBasicDetailsRowBinding binding;

        public NewAgentPropertyBasicDetailsListAdapterViewHolder(@NonNull @NotNull ItemNewAgentPropertyBasicDetailsRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind() {
            AgentPropertyBasicDetails details = propertyList.get(getAdapterPosition());
            Utils.loadUrlImage(binding.imgPropertyCoverImage, details.getImageUrl() + details.getPropertyImageName(), R.drawable.no_image_placeholder, false);
            binding.tvAPName.setText(details.getPropertyName());
            if (details != null && details.getPropertyAddress() != null && !details.getPropertyAddress().isEmpty()) {
                binding.tvAPAddress.setVisibility(View.VISIBLE);
                binding.tvAPAddress.setText(details.getPropertyAddress());
            } else {
                binding.tvAPAddress.setVisibility(View.GONE);
            }
            binding.tvAPPrice.setText(String.format(binding.tvAPPrice.getContext().getString(R.string.agent_property_price), Utils.currencyFormat(details.getPropertyPrice())));
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        callBack.onPropertyItemClickCallback(details);
                    }
                }
            });
        }
    }

    public void setCallBack(NewAgentPropertyBasicDetailsListAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public interface NewAgentPropertyBasicDetailsListAdapterCallBack {

        void onPropertyItemClickCallback(AgentPropertyBasicDetails details);
    }
}
