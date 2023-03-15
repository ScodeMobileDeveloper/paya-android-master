package com.paya.paragon.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.imageview.ShapeableImageView;
import com.paya.paragon.R;
import com.paya.paragon.api.buy_properties.BuyPropertiesModel;
import com.paya.paragon.api.buy_properties.ImagesList;
import com.paya.paragon.utilities.Utils;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import timber.log.Timber;


@SuppressWarnings({"HardCodedStringLiteral", "unused", "FieldCanBeLocal"})
public class BuyPropertyListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BuyPropertiesModel> propertyLists;
    private Context con;
    private boolean showProgress;
    public static ItemClickAdapterListener onClickListener;
    public static String imageURLProperties;
    private String imageBaseURLCompany;
    String imageBaseUrl = "https://www.paya-realestate.com/public/uploads/property/images/medium/";

    public BuyPropertyListAdapter(String imageURLProperties, String imageBaseURLCompany, boolean showProgress, List<BuyPropertiesModel> propertyLists,
                                  Context con, ItemClickAdapterListener onClickListener) {
        this.propertyLists = propertyLists;
        this.con = con;
        this.imageBaseURLCompany = imageBaseURLCompany;
        this.showProgress = showProgress;
        this.onClickListener = onClickListener;
        this.imageURLProperties = imageURLProperties;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == 0) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_buy_property_list_model, parent, false);
            viewHolder = new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_item, parent, false);
            viewHolder = new ProgressViewHolder(v);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;

           viewHolder.sliderAdapter = new SliderAdapter(con, propertyLists.get(position).getImages(),propertyLists.get(position),position);
           viewHolder.sliderView.setSliderAdapter(viewHolder.sliderAdapter);
            viewHolder.sliderView.setAutoCycle(false);

           /* viewHolder.viewPagerAdapter = new ImageViewPagerAdapter( con,propertyLists.get(position),propertyLists.get(position).getImages(),imageURLProperties,position);
            viewHolder.imageVp.setAdapter(viewHolder.viewPagerAdapter);
            viewHolder.mImageSize = Integer.parseInt(propertyLists.get(position).getImageCount());
            if(viewHolder.mImageSize>1){
                viewHolder.indicator_lay.setVisibility(View.VISIBLE);
                viewHolder.addBottomDots(0,viewHolder,viewHolder.mImageSize,con);
            }
            else
                viewHolder.indicator_lay.setVisibility(View.GONE);

            viewHolder.imageVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(viewHolder.mImageSize>1)
                        viewHolder.addBottomDots(position,viewHolder,viewHolder.mImageSize,con);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });*/

            if (Objects.equals(propertyLists.get(position).getType(), "Property")) {
                Utils.loadUrlImage(viewHolder.ivCover, imageURLProperties
                        + propertyLists.get(position).getPropertyCoverImage(), R.drawable.no_image_placeholder, false);

                if (Objects.equals(propertyLists.get(position).isNegotiable(), "No")) {
                    viewHolder.tvNegotiable.setVisibility(View.INVISIBLE);
                } else {
                    viewHolder.tvNegotiable.setVisibility(View.VISIBLE);
                }

                //sqft
               /* if (propertyLists.get(position).getPricePerSqft() == null ||
                        propertyLists.get(position).getPricePerSqft().equals("0")) {
                    viewHolder.tvSqFt.setVisibility(View.GONE);
                } else {
                    viewHolder.tvSqFt.setVisibility(View.VISIBLE);
                    String perSq_ft = con.getString(R.string.currency_symbol) + " " +
                            propertyLists.get(position).getPricePerSqft() + "  per " + con.getString(R.string.meter_square);
                    viewHolder.tvSqFt.setText(perSq_ft);
                }*/

                viewHolder.tvHeading.setText(propertyLists.get(position).getPropertyName());

                viewHolder.tvLocation.setVisibility(View.VISIBLE);
                viewHolder.tvLocation.setText(propertyLists.get(position).getCityLocName());

               /* //posted by
                String projectName = propertyLists.get(position).getProjectName();
                String projectUserCompanyName = propertyLists.get(position).getProjectUserCompanyName();
                String projectBuilderName = propertyLists.get(position).getProjectBuilderName();
                if (projectName == null || projectName.equals("")) {
                    viewHolder.tvPostedBy.setVisibility(View.GONE);
                } else {
                    String postedBy;
                    viewHolder.tvPostedBy.setVisibility(View.VISIBLE);
                    if (projectUserCompanyName == null || projectUserCompanyName.equals("")
                            || projectUserCompanyName.equalsIgnoreCase("null")) {
                        if (projectBuilderName == null || projectBuilderName.equals("")) {
                            postedBy = "in " + projectName;
                            viewHolder.tvPostedBy.setText(postedBy);
                        } else {
                            postedBy = "in " + projectName + " by " + projectBuilderName;
                            viewHolder.tvPostedBy.setText(postedBy);
                        }
                    } else {
                        postedBy = "in " + projectName + " by " + projectUserCompanyName;
                        viewHolder.tvPostedBy.setText(postedBy);
                    }
                }*/

                if ((propertyLists.get(position).getPropertyFeatured().equals("ON"))) {
                    viewHolder.tvFeature.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tvFeature.setVisibility(View.GONE);
                }

                Utils.loadUrlImage(viewHolder.ivFav, imageBaseURLCompany
                        + propertyLists.get(position).getProjectUserCompanyLogo(), R.drawable.no_image_placeholder, false);

                String postedDate = con.getString(R.string.posted_on_col) + " " + propertyLists.get(position).getPropertyAddedDate();
                viewHolder.tv_posted_on_date.setText(postedDate);

                if (propertyLists.get(position).getImageCount() != null && !propertyLists.get(position).getImageCount().equals("")) {
                    viewHolder.imageNumber.setText(propertyLists.get(position).getImageCount());
                }
            } else if (propertyLists.get(position).getType().equals("Unit")) {
                viewHolder.tvNegotiable.setVisibility(View.INVISIBLE);
              /*  Glide.with(viewHolder.ivCover.getContext()).load(imageURLProperties + propertyLists.get(position).getProjectCoverImage())
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.no_image_placeholder)
                        .into(viewHolder.ivCover);*/

              /*  if (propertyLists.get(position).getProjectBedRoom() == null) {
                    viewHolder.tvSqFt.setText(propertyLists.get(position).getProjectTypeName());

                } else {
                    viewHolder.bed_ic_lay.setVisibility(View.VISIBLE);
                    String strType = propertyLists.get(position).getProjectBedRoom()
                            + " " + con.getString(R.string.bed) + " " + propertyLists.get(position).getProjectTypeName();
                    viewHolder.tvSqFt.setText(strType);

                }*/

                viewHolder.tvHeading.setText(propertyLists.get(position).getProjectName());

               /* String projectUserCompanyName = propertyLists.get(position).getProjectUserCompanyName();
                String projectBuilderName = propertyLists.get(position).getProjectBuilderName();

                String companyName;
                if (projectUserCompanyName == null) {
                    viewHolder.tvPostedBy.setVisibility(View.VISIBLE);
                    companyName = "by " + projectBuilderName + " " + propertyLists.get(position).getCityLocName();
                    viewHolder.tvPostedBy.setText(companyName);
                } else {
                    viewHolder.tvPostedBy.setVisibility(View.VISIBLE);
                    companyName = "by " + projectUserCompanyName + " " + propertyLists.get(position).getCityLocName();
                    viewHolder.tvPostedBy.setText(companyName);
                }*/

                viewHolder.tvLocation.setVisibility(View.GONE);

                String postedDate = con.getString(R.string.posted_on_col) + " " + dateFormat(propertyLists.get(position).getProjectAddedDate());
                viewHolder.tv_posted_on_date.setText(postedDate);

                if (propertyLists.get(position).getProjectFeatured().equals("ON")) {
                    viewHolder.tvFeature.setVisibility(View.VISIBLE);
                } else {
                    viewHolder.tvFeature.setVisibility(View.GONE);
                }

                viewHolder.ivFav.setVisibility(View.GONE);

                if (propertyLists.get(position).getImageCount() != null && !propertyLists.get(position).getImageCount().equals("")) {
                    viewHolder.imageNumber.setText(propertyLists.get(position).getImageCount());
                } else viewHolder.imageNumber.setVisibility(View.GONE);
            }
            if (propertyLists.get(position).getProjectBedRoom() == null) {
                viewHolder.bed_ic_lay.setVisibility(View.GONE);
            } else {
                viewHolder.bed_ic_lay.setVisibility(View.VISIBLE);

                String propType = propertyLists.get(position).getProjectBedRoom() + " " + con.getString(R.string.bed);
                viewHolder.bed_textView.setText(propType);
            }

            if (propertyLists.get(position).getProjectTypeName() != null && !propertyLists.get(position).getProjectTypeName().equals("")) {
                viewHolder.apartment_textView.setText(propertyLists.get(position).getProjectTypeName());
                viewHolder.apartment_lay.setVisibility(View.VISIBLE);

                if (propertyLists.get(position).getPropertyTypeIcon() != null && !propertyLists.get(position).getPropertyTypeIcon().equals(""))
                    Utils.loadUrlImage(viewHolder.apartment_ic, propertyLists.get(position).getPropertyTypeIcon(), R.drawable.no_image_placeholder, false);
            } else viewHolder.apartment_lay.setVisibility(View.GONE);

            if (propertyLists.get(position).getPropertyBathRoom() != null && !propertyLists.get(position).getPropertyBathRoom().equals("")) {
                viewHolder.bath_textView.setText(propertyLists.get(position).getPropertyBathRoom() + " " + con.getString(R.string.bath));
                viewHolder.bath_ic_lay.setVisibility(View.VISIBLE);
            } else viewHolder.bath_ic_lay.setVisibility(View.GONE);

            if (propertyLists.get(position).getPropertySqrFeet() != null && !propertyLists.get(position).getPropertySqrFeet().equals("")) {
                viewHolder.area_textView.setText(propertyLists.get(position).getPropertySqrFeet() + " " + con.getString(R.string.meter_square));
                viewHolder.area_ic_lay.setVisibility(View.VISIBLE);
            } else if (propertyLists.get(position).getPropertyPlotArea() != null && !propertyLists.get(position).getPropertyPlotArea().equals("")) {
                viewHolder.area_textView.setText(propertyLists.get(position).getPropertyPlotArea());
                viewHolder.area_ic_lay.setVisibility(View.VISIBLE);
            } else viewHolder.area_ic_lay.setVisibility(View.GONE);


            BuyPropertiesModel data = propertyLists.get(position);

            //TODO Area and unit not added.
           /* String perArea = "";
            if (data.getPropertySqrFeet() != null && !data.getPropertySqrFeet().equals("")) {
                if (data.getPricePerUnitValue() != null && !data.getPricePerUnitValue().equals("")) {
                    perArea = data.getPropertySqrFeet() + " " + data.getPricePerUnitValue();
                } else {
                    perArea = "";
                }
            }
            if (perArea.equals("")) {
                viewHolder.tvSqFt.setText("");
                viewHolder.tvSqFt.setVisibility(View.GONE);
            } else {
                Log.e("Label1", perArea + " " + position);
                viewHolder.tvSqFt.setVisibility(View.VISIBLE);
                viewHolder.tvSqFt.setText(perArea);
            }
            String area = "";
            if (data.getPricePerUnit() != null && !data.getPricePerUnit().equals("")) {
                area = data.getPricePerUnit();
            }
            if (!area.equals("")) {
                if (data.getPricePerUnitValue() != null && !data.getPricePerUnitValue().equals("")) {
                    area = con.getString(R.string.currency_symbol) + " " + area + " Per " + data.getPricePerUnitValue();
                } else {
                    area = "";
                }
            }
            if (area.equals("")) {
                viewHolder.tv_area.setText("");
                viewHolder.tv_area.setVisibility(View.GONE);
            } else {
                Log.e("Label2", area + " " + position);
                viewHolder.tv_area.setText(area);
                viewHolder.tv_area.setVisibility(View.VISIBLE);
            }*/

            //Sold out status
            if (data.getPropertySoldOutStatus().equalsIgnoreCase("No")) {
                viewHolder.layout_sold_out.setVisibility(View.GONE);
            } else {
                viewHolder.layout_sold_out.setVisibility(View.VISIBLE);
                String soldOutDate = Utils.convertToDateOnlyFormat(data.getPropertySoldOutDate());
                viewHolder.tv_sold_out_date.setText(soldOutDate);
            }
            String amount = con.getString(R.string.currency_symbol) + " " + propertyLists.get(position).getPropertyPrice();
            Timber.tag("propertyPrice").d(" " + propertyLists.get(position).getPropertyPrice() + " " + propertyLists.get(position).getPropertyID());

            if(propertyLists.get(position).getCurrencyID_5()!=null && !propertyLists.get(position).getCurrencyID_5().equalsIgnoreCase("0.00")){
                amount = con.getString(R.string.currency_symbol) + " " + propertyLists.get(position).getPropertyPrice();
            }else if(propertyLists.get(position).getCurrencyID_1()!=null && !propertyLists.get(position).getCurrencyID_1().equalsIgnoreCase("0.00")){
                amount = con.getString(R.string.iqd_currency_symbol) + " " + propertyLists.get(position).getPropertyPrice();
            }
            viewHolder.tvAmount.setText(amount);


            if (data.isOffer() != null && !Objects.equals(data.isOffer(), "") && Objects.requireNonNull(data.isOffer()).equalsIgnoreCase("Yes")) {
                if (data.getPropertyOfferDiscount() != null && !data.getPropertyOfferDiscount().equals("")) {
                    viewHolder.offerValue.setText(data.getPropertyOfferDiscount() + "% Off");
                    viewHolder.offerValue.setVisibility(View.VISIBLE);
                } else viewHolder.offerValue.setVisibility(View.GONE);

                if (data.getPropertyOfferPrice() != null && !data.getPropertyOfferPrice().equals("")) {

                    String propertyOfferPrice = con.getString(R.string.currency_symbol) + " " + propertyLists.get(position).getPropertyOfferPrice();
                    Spannable spannable = new SpannableString(propertyOfferPrice);
                    spannable.setSpan(new StrikethroughSpan(), 0, amount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(
                            con,
                            R.color.gray
                    )), 0, amount.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else {
                viewHolder.offerValue.setVisibility(View.GONE);
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

            viewHolder.tv_area.setVisibility(View.GONE);
            viewHolder.tvSqFt.setVisibility(View.GONE);
            viewHolder.tvPostedBy.setVisibility(View.GONE);
            viewHolder.llAmount.setVisibility(View.VISIBLE);
        } else if (holder instanceof ProgressViewHolder) {


           /* final ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);*/
        }



    }



    @Override
    public int getItemViewType(int position) {
        int a;
        if (position < propertyLists.size()) {
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
            size = propertyLists.size() + 1;
        } else {
            size = propertyLists.size();
        }
        return size;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView ivCover;
        ImageView  apartment_ic;
        TextView tvAmount, tvNegotiable, tvSqFt;
        TextView tvHeading, offerValue;
        TextView tvLocation;
        LinearLayout llMain;
        LinearLayout bath_ic_lay, bed_ic_lay, area_ic_lay, apartment_lay,indicator_lay;
        ImageView tvFeature;
        ImageView ivFav;
        TextView tvPostedBy;
        TextView tv_posted_on_date;
        TextView tv_area;
        TextView tv_sold_out_date;
        LinearLayout layout_sold_out;
        LinearLayout llAmount;
        TextView imageNumber;

        TextView bath_textView, bed_textView, area_textView, apartment_textView;
        ViewPager imageVp;
        int mImageSize;
        ImageViewPagerAdapter viewPagerAdapter;

        SliderView sliderView;
        SliderAdapter sliderAdapter;


        public ViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_cover);
            apartment_ic = itemView.findViewById(R.id.apartment_image);
            offerValue = itemView.findViewById(R.id.offerValue);
            tvAmount = itemView.findViewById(R.id.tv_amount);
            llAmount = itemView.findViewById(R.id.ll_amount);
            tvNegotiable = itemView.findViewById(R.id.tv_negotiable);
            tvSqFt = itemView.findViewById(R.id.tv_sq_ft);
            tvHeading = itemView.findViewById(R.id.tv_heading);
            tvLocation = itemView.findViewById(R.id.tv_location);
            llMain = itemView.findViewById(R.id.ll_main);
            tvFeature = itemView.findViewById(R.id.tv_feature);
            ivFav = itemView.findViewById(R.id.iv_fav);
            tvPostedBy = itemView.findViewById(R.id.tv_posted_by);
            tv_posted_on_date = itemView.findViewById(R.id.tv_posted_on_date);
            tv_area = itemView.findViewById(R.id.tv_area);
            tv_sold_out_date = itemView.findViewById(R.id.sold_out_date);
            layout_sold_out = itemView.findViewById(R.id.layout_sold_out);
            imageNumber = itemView.findViewById(R.id.image_number);
            /*imageVp = itemView.findViewById(R.id.image_vp);
            indicator_lay = itemView.findViewById(R.id.linear_layout_dots);*/

            sliderView = itemView.findViewById(R.id.imageSlider);


            apartment_lay = itemView.findViewById(R.id.apartment_lay);
            apartment_textView = itemView.findViewById(R.id.apartment_textView);

            area_ic_lay = itemView.findViewById(R.id.area_ic_lay);
            area_textView = itemView.findViewById(R.id.area_textView);

            bed_ic_lay = itemView.findViewById(R.id.bed_ic_lay);
            bed_textView = itemView.findViewById(R.id.bed_textView);

            bath_ic_lay = itemView.findViewById(R.id.bath_ic_lay);
            bath_textView = itemView.findViewById(R.id.bath_textView);
        }

        public void addBottomDots(int currentPage, ViewHolder viewHolder, int size, Context con) {
            TextView[] dots = new TextView[size];

            viewHolder.indicator_lay.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(con);
                dots[i].setText(Html.fromHtml("&#8226;"));
                dots[i].setTextSize(30);
                dots[i].setTextColor(con.getResources().getColor(R.color.gray));
                viewHolder.indicator_lay.addView(dots[i]);
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(con.getResources().getColor(R.color.colorPrimary));
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
        String strDate = null;

        try {
            date = inputFormat.parse(time);
            strDate = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public  class ImageViewPagerAdapter extends PagerAdapter {
        Context context;
        public BuyPropertiesModel propertiesModel;
        LayoutInflater mLayoutInflater;
        ImageView imageView;

        String imageUrl;
        int rcvPos;
        List<ImagesList> imagesLists = new ArrayList<>();
        public ImageViewPagerAdapter(Context con, BuyPropertiesModel buyPropertiesModel, List<ImagesList> images, String imageURLProperties, int currentPosition) {
            this.context = con;

            this.propertiesModel = buyPropertiesModel;
            if(images.size()>2){
                this.imagesLists = images;

            }else {
                this.imagesLists = images;

            }
            this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.imageUrl = imageURLProperties;
            this.rcvPos = currentPosition;
            if(buyPropertiesModel.getImages().isEmpty()){
                imagesLists.clear();
                ImagesList imagesList = new ImagesList();
                imagesList.set0(buyPropertiesModel.getPropertyCoverImage());
                imagesList.setPropertyImageName(buyPropertiesModel.getPropertyCoverImage());
                imagesLists.add(imagesList);
            }
        }



        @Override
        public int getCount() {
            return imagesLists.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.property_image_slider, container, false);
            imageView = itemView.findViewById(R.id.iv_cover);
                Glide.with(imageView.getContext()).load(imageBaseUrl+imagesLists.get(position).getPropertyImageName())
                        .thumbnail(0.5f)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.no_image_placeholder)
                        .into(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.itemClick(view,rcvPos);
                }
            });



            Objects.requireNonNull(container).addView(itemView);
            return itemView;
        }
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((CardView) object);
        }

    }


    public  class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder>{
        Context context;
        List<ImagesList> imagesLists = new ArrayList<>();

        BuyPropertiesModel buyPropertiesModel;
        int rcvPos;

        public SliderAdapter(Context con, List<ImagesList> images,BuyPropertiesModel buyPropertiesModel,int currentPosition) {
            this.context = con;
            this.imagesLists = images;
            /*imagesLists.add("424786304a568d8c45256.jpeg");
            imagesLists.add("589096304a56a0f23456655.jpeg");
            imagesLists.add("336826304a139509a4258.jpeg");*/

            this.buyPropertiesModel = buyPropertiesModel;
            this.rcvPos = currentPosition;
          /*  if(images.isEmpty()){
                imagesLists.clear();
                ImagesList imagesList = new ImagesList();
                imagesList.set0(buyPropertiesModel.getPropertyCoverImage());
                imagesList.setPropertyImageName(buyPropertiesModel.getPropertyCoverImage());
                imagesLists.add(imagesList);
            }*/
           /* if(images.size()>2){
                this.imagesLists = images.subList(0,2);

            }else {
                this.imagesLists = images;
            }*/
        }


        @Override
        public SliderAdapter.SliderViewHolder onCreateViewHolder(ViewGroup parent) {
            View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.property_image_slider,null);
            return new SliderViewHolder(view);        }

        @Override
        public void onBindViewHolder(SliderViewHolder viewHolder, int position) {
            Utils.loadUrlImage(viewHolder.imageView,imageBaseUrl+imagesLists.get(position).getPropertyImageName(),R.drawable.no_image_placeholder,false);
          /* Glide.with(viewHolder.imageView.getContext()).load(imageBaseUrl+"499136304bb768533d1661254515899.jpg")
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.no_image_placeholder)
                    .into(viewHolder.imageView);*/
            viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.itemClick(view,rcvPos);
                }
            });
        }

        @Override
        public int getCount() {
            return imagesLists.size();
        }


        public class SliderViewHolder extends ViewHolder {

            ShapeableImageView imageView;
            public SliderViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.iv_cover);
            }
        }
    }



}
