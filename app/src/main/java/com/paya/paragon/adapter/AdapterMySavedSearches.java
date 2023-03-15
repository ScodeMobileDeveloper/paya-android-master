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
import com.paya.paragon.api.mySavedSearches.SavedSearchInfo;
import com.paya.paragon.utilities.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("HardCodedStringLiteral")
public class AdapterMySavedSearches extends RecyclerView.Adapter<AdapterMySavedSearches.SavedSearchViewHolder> {

    private ArrayList<SavedSearchInfo> mMySavedSearchesData;
    private ItemClickAdapterListener itemClickAdapterListener;
    private int mTotalItemCount;
    private Context mContext;

    public AdapterMySavedSearches(Context context, ArrayList<SavedSearchInfo> mySavedSearchesData,
                                  ItemClickAdapterListener itemClickAdapterListener, int totalItemCount) {
        this.mMySavedSearchesData = mySavedSearchesData;
        this.itemClickAdapterListener = itemClickAdapterListener;
        this.mTotalItemCount = totalItemCount;
        this.mContext = context;
    }

    @NonNull
    @Override
    public SavedSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_saved_searches_model, parent,
                false);
        return new SavedSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SavedSearchViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {
        final SavedSearchInfo data = mMySavedSearchesData.get(position);

        if (position == mMySavedSearchesData.size() - 1) {
            holder.finalPadding.setVisibility(View.VISIBLE);
        } else {
            holder.finalPadding.setVisibility(View.GONE);
        }

        if (mMySavedSearchesData.size() == mTotalItemCount) {
            holder.finalLoading.setVisibility(View.GONE);
        }

        if (data.getSearchDate() != null && !data.getSearchDate().equals("")) {
            holder.postedDate.setText(Utils.convertToDateOnlyFormat(data.getSearchDate()));
        } else {
            holder.postedDate.setText("");
        }

        if (data.getSearchTitle() != null && !data.getSearchTitle().equals("")) {
            holder.postedTitle.setText(data.getSearchTitle());
        } else {
            holder.postedTitle.setText("");
        }

        StringBuilder info = new StringBuilder("");
        if (data.getSaveSearchParameter().getPropertyTypeName() != null
                && !data.getSaveSearchParameter().getPropertyTypeName().equals("")) {
            info = new StringBuilder(data.getSaveSearchParameter().getPropertyTypeName());
        }
        if (data.getSaveSearchParameter().getSearchPropertyPurpose() != null
                && !data.getSaveSearchParameter().getSearchPropertyPurpose().equals("")) {
            String purpose;
            if (data.getSaveSearchParameter().getSearchPropertyPurpose().equalsIgnoreCase("Sell")) {
                purpose = "Buy";
            } else {
                purpose = "Rent";
            }
            info.append(" For ").append(purpose);
        }

        if (data.getSaveSearchParameter().getAttrDetails() != null &&
                data.getSaveSearchParameter().getAttrDetails().size() > 0) {
            HashMap<String, String> attributes = data.getSaveSearchParameter().getAttrDetails();
            for (Map.Entry<String, String> entry : attributes.entrySet()) {
                info.append("  |  ").append(entry.getKey()).append(" : ").append(entry.getValue());
            }
        }
        String rupeeSymbol = mContext.getResources().getString(R.string.currency_symbol);
        if (!data.getSearchMinPriceValue().equalsIgnoreCase("0")) {
            if (!data.getSearchMaxPriceValue().equalsIgnoreCase("0")) {
                info.append("  |  ").append(rupeeSymbol). append(" ")
                        .append(data.getSearchMinPriceValue()).append(" - ")
                        .append(rupeeSymbol). append(" ")
                        .append(data.getSearchMaxPriceValue());
            } else {
                info.append("  |  ").append(rupeeSymbol). append(" ")
                        .append(data.getSearchMinPriceValue()).append(" - ").append("Above");
            }
        } else {
            if (!data.getSearchMaxPriceValue().equalsIgnoreCase("0")) {
                info.append("  |  ").append(rupeeSymbol). append(" ")
                        .append(data.getSearchMaxPriceValue()).append(" - ").append("Below");
            }
        }

        StringBuilder location = new StringBuilder("");
        if (data.getSaveSearchParameter().getLocationDetails() != null
                && data.getSaveSearchParameter().getStateDetails().size() > 0) {
            if (!data.getSaveSearchParameter().getLocationDetails().get(0).getCityLocName().equals("")) {
                location.append(data.getSaveSearchParameter().getLocationDetails().get(0).getCityLocName());
            }
        }
        if (data.getSaveSearchParameter().getCityDetails() != null
                && data.getSaveSearchParameter().getCityDetails().size() > 0) {
            if (!data.getSaveSearchParameter().getCityDetails().get(0).getCityName().equals("")) {
                if (location.toString().equals("")){
                    location.append(data.getSaveSearchParameter().getCityDetails().get(0).getCityName());
                } else {
                    location.append(", ").append(data.getSaveSearchParameter().getCityDetails().get(0).getCityName());
                }
            }
        }
        if (data.getSaveSearchParameter().getStateDetails() != null
                && data.getSaveSearchParameter().getStateDetails().size() > 0) {
            if (!data.getSaveSearchParameter().getStateDetails().get(0).getStateName().equals("")) {
                if (location.toString().equals("")){
                    location.append(data.getSaveSearchParameter().getStateDetails().get(0).getStateName());
                } else {
                    location.append(", ").append(data.getSaveSearchParameter().getStateDetails().get(0).getStateName());
                }
            }
        }

        if (!location.toString().equals("")) {
            info.append("  |  ").append(location);
        }

        holder.postedInfo.setText(info.toString());


        if (data.getSearchStatus() != null && !data.getSearchStatus().equals("")) {
            if (data.getSearchStatus().equals("Active")) {
                holder.pausePlay.setText(R.string.pause);
                holder.pausePlay.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_pause_button,
                        0, 0, 0);
            } else {
                holder.pausePlay.setText(R.string.resume);
                holder.pausePlay.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_play_button,
                        0, 0, 0);
            }
        }

        holder.pausePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.getSearchStatus().equals("Active")) {
                    itemClickAdapterListener.playPauseClick(position, "Inactive");
                } else {
                    itemClickAdapterListener.playPauseClick(position, "Active");
                }
            }
        });

        holder.postedInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.itemClick(position, data.getSaveSearchParameter().getSearchPropertyPurpose());
            }
        });

        holder.postedTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.itemClick(position, data.getSaveSearchParameter().getSearchPropertyPurpose());
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickAdapterListener.deleteClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMySavedSearchesData.size();
    }

    static class SavedSearchViewHolder extends RecyclerView.ViewHolder {
        TextView postedDate;
        TextView postedTitle;
        TextView postedInfo;
        TextView propertyCount;
        TextView pausePlay;
        ImageView delete;
        LinearLayout layoutItem;
        LinearLayout finalPadding;
        ProgressBar finalLoading;

        SavedSearchViewHolder(View itemView) {
            super(itemView);
            this.postedDate = itemView.findViewById(R.id.saved_date_item_my_saved_searches);
            this.postedTitle = itemView.findViewById(R.id.saved_title_item_my_saved_searches);
            this.postedInfo = itemView.findViewById(R.id.saved_info_item_my_saved_searches);
            this.propertyCount = itemView.findViewById(R.id.saved_property_count_item_my_saved_searches);
            this.pausePlay = itemView.findViewById(R.id.saved_pause_play_item_my_saved_searches);
            this.delete = itemView.findViewById(R.id.delete_item_my_saved_searches);
            this.layoutItem = itemView.findViewById(R.id.layout_item_my_saved_searches);
            this.finalPadding = itemView.findViewById(R.id.layout_padding_view);
            this.finalLoading = itemView.findViewById(R.id.final_padding_progress);
        }
    }

    public interface ItemClickAdapterListener {

        void deleteClick(int position);

        void playPauseClick(int position, String status);

        void itemClick(int position, String purpose);
    }
}
