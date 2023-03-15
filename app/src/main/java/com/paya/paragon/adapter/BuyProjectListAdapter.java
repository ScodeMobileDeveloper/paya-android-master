package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.paya.paragon.R;
import com.paya.paragon.api.BuyProjects.BuyProjectsModel;
import com.paya.paragon.utilities.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


@SuppressWarnings("HardCodedStringLiteral")
public class BuyProjectListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<BuyProjectsModel> projectLists;
    private Context con;
    private boolean showProgress;
    private ItemClickAdapterListener onClickListener;
    private String imageURLProjects;

    public BuyProjectListAdapter(String imageURLProjects, boolean showProgrss, List<BuyProjectsModel> projectLists,
                                 Context con, ItemClickAdapterListener onClickListener) {
        this.projectLists = projectLists;
        this.con = con;
        this.showProgress = showProgrss;
        this.onClickListener = onClickListener;
        this.imageURLProjects = imageURLProjects;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_project_list_model, parent, false);
            viewHolder = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progress_item, parent, false);
            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {

        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            Utils.loadUrlImage(viewHolder.ivCover,imageURLProjects
                    + projectLists.get(position).getProjectCoverImage(), R.drawable.no_image_placeholder,false);
            String propertyUnitPriceRangeS = projectLists.get(position).getProjectUnitPriceRange();
            if (propertyUnitPriceRangeS == null || propertyUnitPriceRangeS.equalsIgnoreCase("null")
                    || propertyUnitPriceRangeS.equalsIgnoreCase("")
                    || propertyUnitPriceRangeS.equalsIgnoreCase("0")) {
                propertyUnitPriceRangeS = "Price on Request";
            } else
                propertyUnitPriceRangeS = con.getString(R.string.currency_symbol) + " " + propertyUnitPriceRangeS;

            viewHolder.tvAmount.setText(propertyUnitPriceRangeS);
            if (projectLists.get(position).getProjectBedRoom() == null) {
                viewHolder.tvSub.setText(projectLists.get(position).getProjectTypeName());
            } else {
                String subject = projectLists.get(position).getProjectBedRoom() + " " + con.getString(R.string.bed) + " "
                        + projectLists.get(position).getProjectTypeName();
                viewHolder.tvSub.setText(subject);
            }
            viewHolder.tvHeading.setText(projectLists.get(position).getProjectName());
            String builderName = "";
            if (projectLists.get(position).getProjectBuilderName() != null
                    && !projectLists.get(position).getProjectBuilderName().equals("")) {
                builderName = projectLists.get(position).getProjectBuilderName();
            } else {
                if (projectLists.get(position).getProjectUserCompanyName() != null
                        && !projectLists.get(position).getProjectUserCompanyName().equals("")) {
                    builderName = projectLists.get(position).getProjectUserCompanyName();
                }
            }
            if (builderName.equals("")) {
                viewHolder.tvLocation.setText(projectLists.get(position).getCityLocName());
            } else {
                String location = "by " + builderName;
                viewHolder.tvLocation.setText(location);
                viewHolder.tv_cityName.setText(projectLists.get(position).getCityLocName());
            }
            if (projectLists.get(position).getImageCount() != null && !projectLists.get(position).getImageCount().equals("")) {
                viewHolder.imageNumber.setText(projectLists.get(position).getImageCount());
            } else viewHolder.imageNumber.setVisibility(View.GONE);
            viewHolder.tvTime.setVisibility(View.GONE);
            String posted = con.getString(R.string.posted_on_col) + " " + projectLists.get(position).getProjectAddedDate();
            viewHolder.tv_posted_on_date.setText(posted);
            if (projectLists.get(position).getProjectFeatured().equals("ON")) {
                viewHolder.tvFeature.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tvFeature.setVisibility(View.INVISIBLE);
            }
            // viewHolder.ivFav.setVisibility(View.GONE);
            //fav
            if (projectLists.get(position).getProjectFavourite().equals("1")) {
                viewHolder.ivFav.setImageResource(R.drawable.menu_icon_like_on);
            } else {
                viewHolder.ivFav.setImageResource(R.drawable.menu_icon_like_off);
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.itemClick(view, position);
                }
            });

            viewHolder.ivFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.itemFavClick(position);
                }
            });

            viewHolder.tvSub.setVisibility(View.GONE);
            viewHolder.tvLocation.setVisibility(View.GONE);
            viewHolder.tvTime.setVisibility(View.GONE);
        } else if (holder instanceof ProgressViewHolder) {
            final ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        int a;
        if (position < projectLists.size()) {
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
            size = projectLists.size() + 1;
        } else {
            size = projectLists.size();
        }
        return size;
    }



    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCover;
        TextView tvAmount, tvSub;
        TextView tvHeading;
        TextView tvLocation, tv_cityName;
        TextView tvTime;
        LinearLayout llMain;
        ImageView tvFeature;
        ImageView ivFav;
        TextView tv_posted_on_date;
        TextView imageNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            tvSub = itemView.findViewById(R.id.tv_sub);
            tvHeading = itemView.findViewById(R.id.tv_heading);
            tvLocation = itemView.findViewById(R.id.tv_location);
            tv_cityName = itemView.findViewById(R.id.tv_cityName);
            tvTime = itemView.findViewById(R.id.tv_time);
            llMain = itemView.findViewById(R.id.ll_main);
            tvFeature = itemView.findViewById(R.id.tv_feature);
            ivFav = itemView.findViewById(R.id.iv_fav);
            tv_posted_on_date = itemView.findViewById(R.id.tv_posted_on_date);
            imageNumber = itemView.findViewById(R.id.image_number);
        }
    }

    @SuppressWarnings("RedundantCast")
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }


    public interface ItemClickAdapterListener {
        void itemClick(View v, int position);

        void itemCallClick(int position);

        void itemShareClick(int position);

        void itemFavClick(int position);
    }

    private String dateFormat(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "MMM dd, yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());
        Date date;
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
