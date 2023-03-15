package com.paya.paragon.adapter

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.paya.paragon.R
import com.paya.paragon.api.buy_properties.BuyPropertiesModel
import com.paya.paragon.api.buy_properties.ImagesList
import com.paya.paragon.utilities.Utils
import com.smarteist.autoimageslider.SliderView
import com.smarteist.autoimageslider.SliderViewAdapter
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PropertyListAdapter(
    private val mImageBaseURLCompany: String,
    private var mShowProgress: Boolean,
    private var mPropertyLists: List<BuyPropertiesModel>,
    private var mContext: Context,
    private val mOnClickListener: ItemClickAdapterListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var mImageBaseUrl = "https://www.paya-realestate.com/public/uploads/property/images/medium/"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PropertyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_buy_property_list_model, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PropertyViewHolder) {
            mPropertyLists.getOrNull(position)?.let { item ->
                //Image Sliders
                holder.sliderAdapter.add(mContext, item.images, position)


                //Type Based
                if (item.type == "Property") {

                    //Negotiable
                    /*if (item.isNegotiable == "No") {
                        holder.tvNegotiable.visibility = View.INVISIBLE
                    } else {
                        holder.tvNegotiable.visibility = View.VISIBLE
                    }*/
                    holder.tvNegotiable.isInvisible = item.isNegotiable.equals("no", true)

                    //Property Name
                    holder.tvHeading.text = item.propertyName

                    //Location
                    holder.tvLocation.visibility = View.VISIBLE
                    holder.tvLocation.text = item.cityLocName

                    //Feature
                    /*if (((item.propertyFeatured == "ON"))) {
                        holder.tvFeature.visibility = View.VISIBLE
                    } else {
                        holder.tvFeature.visibility = View.GONE
                    }*/
                    holder.tvFeature.isVisible = item.propertyFeatured == "ON"

                    //Fav(Agent Logo)
                    Utils.loadUrlImage(
                        holder.ivFav,
                        (mImageBaseURLCompany + item.projectUserCompanyLogo),
                        R.drawable.no_image_placeholder,
                        false
                    )

                    //Posted On
                    val postedDate =
                        mContext.getString(R.string.posted_on_col) + " " + item.propertyAddedDate
                    holder.tv_posted_on_date.text = postedDate

                    //ImageCount
                    if (item.imageCount != null && item.imageCount != "") {
                        holder.imageNumber.text = item.imageCount
                    }
                } else if ((item.type == "Unit")) {

                    //Negotiable
                    holder.tvNegotiable.visibility = View.INVISIBLE

                    //PropertyName
                    holder.tvHeading.text = item.projectName

                    //Location
                    holder.tvLocation.visibility = View.GONE

                    //Feature
                    if ((item.projectFeatured == "ON")) {
                        holder.tvFeature.visibility = View.VISIBLE
                    } else {
                        holder.tvFeature.visibility = View.GONE
                    }


                    //Posted on
                    val postedDate =
                        mContext.getString(R.string.posted_on_col) + " " + item.projectAddedDate?.let {
                            dateFormat(it)
                        }
                    holder.tv_posted_on_date.text = postedDate

                    //Fav(Agent Logo)
                    holder.ivFav.visibility = View.GONE

                    //ImageCount
                    if (item.imageCount != null && item.imageCount != "") {
                        holder.imageNumber.text = item.imageCount
                    } else holder.imageNumber.visibility = View.GONE
                }

                //Bedroom
                if (item.projectBedRoom == null) {
                    holder.bed_ic_lay.visibility = View.GONE
                } else {
                    holder.bed_ic_lay.visibility = View.VISIBLE
                    val propType =
                        item.projectBedRoom + " " + mContext.getString(R.string.bed)
                    holder.bed_textView.text = propType
                }

                //Property type
                if (item.projectTypeName != null && item.projectTypeName != "") {
                    holder.apartment_textView.text = item.projectTypeName
                    holder.apartment_lay.visibility = View.VISIBLE
                    if (item.propertyTypeIcon != null && item.propertyTypeIcon != "") Utils.loadUrlImage(
                        holder.apartment_ic,
                        item.propertyTypeIcon,
                        R.drawable.no_image_placeholder,
                        false
                    )
                } else holder.apartment_lay.visibility = View.GONE

                //Bathroom
                if (item.propertyBathRoom != null && item.propertyBathRoom != "") {
                    holder.bath_textView.text = String.format(
                        "%s %s",
                        item.propertyBathRoom,
                        mContext.getString(R.string.bath)
                    )
                    holder.bath_ic_lay.visibility = View.VISIBLE
                } else holder.bath_ic_lay.visibility = View.GONE

                //Area
                if (item.propertySqrFeet != null && item.propertySqrFeet != "") {
                    holder.area_textView.text = String.format(
                        "%s %s",
                        item.propertySqrFeet,
                        mContext.getString(R.string.meter_square)
                    )
                    holder.area_ic_lay.visibility = View.VISIBLE
                } else if (item.propertyPlotArea != null && item.propertyPlotArea != ""
                ) {
                    holder.area_textView.text = item.propertyPlotArea
                    holder.area_ic_lay.visibility = View.VISIBLE
                } else holder.area_ic_lay.visibility = View.GONE


                //Sold out status
                if (item.propertySoldOutStatus.equals("No", ignoreCase = true)) {
                    holder.layout_sold_out.visibility = View.GONE
                } else {
                    holder.layout_sold_out.visibility = View.VISIBLE
                    val soldOutDate = Utils.convertToDateOnlyFormat(item.propertySoldOutDate)
                    holder.tv_sold_out_date.text = soldOutDate
                }

                //Amount
                var amount =
                    mContext.getString(R.string.currency_symbol) + " " + item.propertyPrice
                if (item.currencyID_5 != null && !item.currencyID_5.equals(
                        "0.00",
                        ignoreCase = true
                    )
                ) {
                    amount = mContext.getString(R.string.currency_symbol) + " " + item.propertyPrice
                } else if (item.currencyID_1 != null && !item.currencyID_1.equals(
                        "0.00",
                        ignoreCase = true
                    )
                ) {
                    amount =
                        mContext.getString(R.string.iqd_currency_symbol) + " " + item.propertyPrice
                }
                holder.tvAmount.text = amount


                //Offer
                if ((item.isOffer != null) && item.isOffer != "" && item.isOffer.equals(
                        "Yes",
                        ignoreCase = true
                    )
                ) {
                    if (item.propertyOfferDiscount != null && item.propertyOfferDiscount != "") {
                        holder.offerValue.text = String.format(item.propertyOfferDiscount + "% Off")
                        holder.offerValue.visibility = View.VISIBLE
                    } else holder.offerValue.visibility = View.GONE
                    if (item.propertyOfferPrice != null && item.propertyOfferPrice != "") {
                        val propertyOfferPrice =
                            mContext.getString(R.string.currency_symbol) + " " + item.propertyOfferPrice
                        val spannable: Spannable = SpannableString(propertyOfferPrice)
                        spannable.setSpan(
                            StrikethroughSpan(),
                            0,
                            amount.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                        spannable.setSpan(
                            ForegroundColorSpan(
                                ContextCompat.getColor(
                                    mContext,
                                    R.color.gray
                                )
                            ), 0, amount.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                } else {
                    holder.offerValue.visibility = View.GONE
                }
                holder.itemView.setOnClickListener { view ->
                    mOnClickListener.itemClick(
                        view,
                        position
                    )
                }
                holder.ivFav.setOnClickListener {
                    mOnClickListener.itemAgentClick(position)
                }
                holder.llAmount.visibility = View.VISIBLE


                //shortlisted
                Timber.e("id", item.propertyID.orEmpty())
                if ((item.propertyFavourite == "1")) {
                    holder.ivShortList.setImageResource(R.drawable.menu_icon_like_on)
                } else {
                    holder.ivShortList.setImageResource(R.drawable.menu_icon_like_off)
                }
                holder.ivShortList.setOnClickListener {
                    mOnClickListener.itemFavClick(position)
                }
            }
        }
        /*else if (holder instanceof ProgressViewHolder) {
            final ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.progressBar.setIndeterminate(true);

        }*/
    }

    override fun getItemCount(): Int {
        val size: Int = if (mShowProgress) {
            mPropertyLists.size + 1
        } else {
            mPropertyLists.size
        }
        return size
    }

    override fun getItemViewType(position: Int): Int {
        val a: Int
        if (position < mPropertyLists.size) {
            a = 0
        } else {
            a = 1
        }
        return a
    }

    inner class PropertyViewHolder(v: View?) : RecyclerView.ViewHolder((v)!!) {
        var sliderAdapter: SliderAdapter
        var sliderView: SliderView = itemView.findViewById(R.id.imageSlider)
        var apartment_ic: ImageView = itemView.findViewById(R.id.apartment_image)
        var tvAmount: TextView = itemView.findViewById(R.id.tv_amount)
        var tvNegotiable: TextView = itemView.findViewById(R.id.tv_negotiable)
        var tvHeading: TextView = itemView.findViewById(R.id.tv_heading)
        var offerValue: TextView = itemView.findViewById(R.id.offerValue)
        var tvLocation: TextView = itemView.findViewById(R.id.tv_location)
        var bath_ic_lay: LinearLayout = itemView.findViewById(R.id.bath_ic_lay)
        var bed_ic_lay: LinearLayout = itemView.findViewById(R.id.bed_ic_lay)
        var area_ic_lay: LinearLayout = itemView.findViewById(R.id.area_ic_lay)
        var apartment_lay: LinearLayout = itemView.findViewById(R.id.apartment_lay)
        var tvFeature: ImageView = itemView.findViewById(R.id.tv_feature)
        var ivFav: ImageView = itemView.findViewById(R.id.iv_fav)
        var tv_posted_on_date: TextView = itemView.findViewById(R.id.tv_posted_on_date)
        var tv_sold_out_date: TextView = itemView.findViewById(R.id.sold_out_date)
        var layout_sold_out: LinearLayout = itemView.findViewById(R.id.layout_sold_out)
        var llAmount: LinearLayout = itemView.findViewById(R.id.ll_amount)
        var imageNumber: TextView = itemView.findViewById(R.id.image_number)
        var ivShortList: ImageView = itemView.findViewById(R.id.iv_shortList)
        var bath_textView: TextView = itemView.findViewById(R.id.bath_textView)
        var bed_textView: TextView = itemView.findViewById(R.id.bed_textView)
        var area_textView: TextView = itemView.findViewById(R.id.area_textView)
        var apartment_textView: TextView = itemView.findViewById(R.id.apartment_textView)

        init {
            sliderView.isNestedScrollingEnabled = false
            sliderAdapter = SliderAdapter()
            sliderView.setSliderAdapter(sliderAdapter)
            sliderView.isAutoCycle = false
        }
    }

    inner class SliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        var imageView: ShapeableImageView

        init {
            imageView = itemView.findViewById(R.id.iv_cover)
        }
    }

    inner class SliderAdapter : SliderViewAdapter<SliderViewHolder>() {
        private var context: Context? = null
        private var rcvPos = 0
        private var imagesLists: List<ImagesList>? = null
        fun add(context: Context?, images: List<ImagesList>?, position: Int) {
            this.context = context
            imagesLists = (images)
            rcvPos = position
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.property_image_slider, null)
            return SliderViewHolder(view)
        }

        override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
            Utils.loadUrlImage(
                viewHolder.imageView,
                mImageBaseUrl + imagesLists?.get(position)?.propertyImageName.orEmpty(),
                R.drawable.no_image_placeholder,
                false
            )
            viewHolder.imageView.setOnClickListener { view: View? ->
                mOnClickListener.itemClick(view, rcvPos)
            }
        }

        override fun getCount(): Int {
            return imagesLists?.size ?: 0
        }
    }

    private fun dateFormat(time: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "MMM dd, yyyy"
        val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
        val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())
        val date: Date?
        var strDate: String? = null
        try {
            date = inputFormat.parse(time)
            strDate = date?.let { outputFormat.format(it) }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return strDate
    }

    interface ItemClickAdapterListener {
        fun itemClick(v: View?, position: Int)

        //        void itemCallClick(int position);
        //        void itemShareClick(int position);
        fun itemFavClick(position: Int)
        fun itemAgentClick(position: Int)
    }

    /* companion object {
         var mOnClickListener: ItemClickAdapterListener
     }*/
}