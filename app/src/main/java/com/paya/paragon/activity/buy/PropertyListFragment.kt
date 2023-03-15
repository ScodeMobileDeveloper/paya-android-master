package com.paya.paragon.activity.buy

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.paya.paragon.R
import com.paya.paragon.activity.details.ActivityAgentDetails
import com.paya.paragon.activity.details.ActivityProjectDetails
import com.paya.paragon.activity.details.ActivityPropertyDetails
import com.paya.paragon.activity.login.ActivityLoginEmail
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose
import com.paya.paragon.adapter.PropertyListAdapter
import com.paya.paragon.api.addFavProperty.AddFavPropertyApi
import com.paya.paragon.api.addFavProperty.AddFavPropertyResponse
import com.paya.paragon.api.buy_properties.BuyPropertiesListApi
import com.paya.paragon.api.buy_properties.BuyPropertiesListResponse
import com.paya.paragon.api.buy_properties.BuyPropertiesModel
import com.paya.paragon.base.BaseFragment
import com.paya.paragon.base.commonClass.ApiLinks
import com.paya.paragon.base.commonClass.GlobalValues
import com.paya.paragon.utilities.contactPaya
import com.paya.paragon.utilities.contactPayaWhatsApp
import com.paya.paragon.databinding.FragmentBuyPropertiesBinding
import com.paya.paragon.utilities.*
import com.paya.paragon.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*

class PropertyListFragment : BaseFragment {

    private val homeViewModel by activityViewModel<HomeViewModel>()

    private var imageURLProperties: String? = null
    private var imageBaseURLCompany: String? = null
    private var countProperty: String? = null

    private var loadingMain = false
    private var pageCount = 0
    private var alertDialog: Dialog? = null
    private var mLoadingDialog: DialogProgress? = null
    private var userId = ""
    private var searchPropertyPurpose: String? = null
    private var shortListListener: ShortListListener? = null
    private val mContext: Context by lazy { requireContext() }

    private var iMapMarkerLocation: IMapMarkerLocation? = null
    private var type: PropertyProjectType? = null

    private lateinit var binding: FragmentBuyPropertiesBinding

    constructor() {
        // Required empty public constructor
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        iMapMarkerLocation = context as IMapMarkerLocation
    }

    @SuppressLint("ValidFragment")
    constructor(
        data: MutableList<BuyPropertiesModel>,
        imageURLProperties: String?,
        imageBaseURLCompany: String?,
        countProperty: String?,
        searchPropertyPurpose: String?,
        type: PropertyProjectType?
    ) {
        propertyLists = data
        this.imageURLProperties = imageURLProperties
        this.countProperty = countProperty
        this.imageBaseURLCompany = imageBaseURLCompany
        this.searchPropertyPurpose = searchPropertyPurpose
        this.type = type
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBuyPropertiesBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        Utils.changeLayoutOrientationDynamically(requireContext(), binding.root)
        setData(
            propertyLists,
            imageURLProperties,
            imageBaseURLCompany,
            countProperty,
            searchPropertyPurpose,
            type
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.isPropertyTabClicked.observe(viewLifecycleOwner) {
            if (it?.getContentIfNotHandled() == true) {
                binding.rvList.scrollToPosition(0)
            }
        }

        binding.layoutNoData.llContactUs.setOnClickListener {
            requireContext().contactPayaWhatsApp()
        }
        binding.layoutNoData.llCallUs.setOnClickListener {
            requireContext().contactPaya()
        }
        binding.layoutNoData.llRequestProperty.setOnClickListener {
            startActivity(Intent(requireActivity(), ActivityRequirementPurpose::class.java))
        }
    }

    fun setData(
        data: MutableList<BuyPropertiesModel>,
        imageURLProperties: String?,
        imageBaseURLCompany: String?,
        countProperty: String?,
        searchPropertyPurpose: String?,
        type: PropertyProjectType?
    ) {
        propertyLists = data
        this.imageURLProperties = imageURLProperties
        this.countProperty = countProperty
        this.imageBaseURLCompany = imageBaseURLCompany
        this.searchPropertyPurpose = searchPropertyPurpose
        this.type = type
        userId = if (SessionManager.getLoginStatus(mContext)) {
            SessionManager.getAccessToken(mContext)
        } else {
            ""
        }
        mLoadingDialog = DialogProgress(mContext)

        binding.rvList.apply {
            (itemAnimator as SimpleItemAnimator?)?.supportsChangeAnimations = false
            layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        val layoutManager = binding.rvList.layoutManager as? LinearLayoutManager
                        val visibleItemCount = layoutManager?.childCount ?: 0
                        val totalItemCount = layoutManager?.itemCount ?: 0
                        val pastVisiblesItems = layoutManager?.findFirstVisibleItemPosition() ?: 0
                        Timber.e("pagecount--->$pageCount")
                        if (loadingMain) {
                            if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                                loadingMain = false
                                pageCount++
                                Timber.e("pagecount $pageCount loadingMain + inside ")
                                apiGetProperties()
                            }
                        }
                    }
                }
            })
        }

        Timber.e("propertyLists---->${propertyLists.size}")
//        binding.tvNoItem.visibility = View.GONE

        setList()
    }

    private fun showLoadMoreProgress(show: Boolean) {
        binding.progressBar.isVisible = show
    }

    private fun setList() {
        propertyLists.isEmpty().let {
            binding.layoutContents.isVisible = !it
            binding.layoutNoData.root.isVisible = it

            if (!it) {
                loadingMain = propertyLists.size >= 20
                mPropertyListAdapter =
                    PropertyListAdapter(
                        imageBaseURLCompany.orEmpty(),
                        false,
                        propertyLists,
                        requireActivity(),
                        object : PropertyListAdapter.ItemClickAdapterListener {

                            override fun itemAgentClick(position: Int) {

                                val intent = Intent(
                                    requireActivity(),
                                    ActivityAgentDetails::class.java
                                )
                                intent.putExtra(AppConstant.AGENT_ID, propertyLists[position].userID)
                                intent.putExtra(AppConstant.I_SUB_AGENT_FOR_PARENT, true)
                                startActivity(intent,
                                    ActivityOptions.makeSceneTransitionAnimation(requireActivity())
                                        .toBundle()
                                )
                            }

                            override fun itemClick(v: View?, position: Int) {
                                val item = propertyLists[position]
                                val type = item.type
                                val itemId: String
                                Timber.e(
                                    "id",
                                    item.propertyID + " " + SessionManager.getUserId(
                                        requireContext()
                                    )
                                )
                                if (type == "Property") {
                                    itemId = item.propertyID.orEmpty()
                                    val intent = Intent(activity, ActivityPropertyDetails::class.java)
                                    intent.putExtra("propertyID", itemId)
                                    intent.putExtra("position", position)
                                    intent.putExtra(Utils.TYPE, this@PropertyListFragment.type)
                                    intent.putExtra("purpose", searchPropertyPurpose)
                                    startActivity(intent)
                                } else if (type == "Unit") {
                                    itemId = item.projectID.orEmpty()
                                    val intent = Intent(activity, ActivityProjectDetails::class.java)
                                    intent.putExtra(Utils.TYPE, this@PropertyListFragment.type)
                                    intent.putExtra("projectID", itemId)
                                    intent.putExtra("position", position)
                                    intent.putExtra("purpose", searchPropertyPurpose)
                                    startActivity(intent)
                                }
                            }

                            /*override fun itemCallClick(position: Int) {
                                val item = propertyLists[position]
                                val type = item.type
                                var itemId: String? = null
                                if (type == "Property") {
                                    itemId = item.propertyID
                                } else if (type == "Unit") {
                                    itemId = item.projectID
                                }
                                alertContactOwner(requireActivity(), position, itemId)
                            }*/

                            /*override fun itemShareClick(position: Int) {
                                val shareBody =
                                    PropertyProjectListActivity.siteUrl + "" + propertyLists[position].link
                                val sharingIntent = Intent(Intent.ACTION_SEND)
                                sharingIntent.type = "text/plain"
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                                startActivity(Intent.createChooser(sharingIntent, "share"))
                            }*/

                            override fun itemFavClick(position: Int) {
                                initiateFavourite(position)
                            }
                        })
                binding.rvList.adapter = mPropertyListAdapter
            }
        }
    }

    private fun initiateFavourite(position: Int) {
        if (SessionManager.getLoginStatus(activity)) {
            val propertyID = propertyLists[position].propertyID
            apiFavProperty(propertyID, userId, position)
        } else {
            startActivity(Intent(activity, ActivityLoginEmail::class.java))
        }
    }

    fun setListener(listener: ShortListListener?) {
        shortListListener = listener
    }

    //get properties
    fun apiGetProperties() {
        showLoadMoreProgress(true)
        Timber.e("pageCount", pageCount.toString() + "")
        var locationId: String? = "2"
        if (SessionManager.getLocationId(mContext) != "-1") locationId =
            SessionManager.getLocationId(mContext)
        ApiLinks.client.create(BuyPropertiesListApi::class.java).post(
            userID = userId,
            languageID = "" + SessionManager.getLanguageID(mContext),
            cityID = locationId,
            searchPropertyPurpose = searchPropertyPurpose,
            page = pageCount.toString() + "",
            itemType = "Property",
            sortBy = GlobalValues.selectedSortValue,
            searchMinPrice = GlobalValues.selectedMinPrice,
            searchMaxPrice = GlobalValues.selectedMaxPrice,
            searchPropertyTypeID = GlobalValues.selectedPropertyTypeID,
            searchCountryID = GlobalValues.countryID,
            searchAttributesStr = GlobalValues.selectedBedroomsNumber,
            furnished_status = GlobalValues.selectedFurnishedStaticValue,
            searchRegion = GlobalValues.selectedRegionId,
            possessionStatus = "",
            searchRadius = "",
            Coordinates = "",
            center = "",
            radius = ""
        ).enqueue(object : Callback<BuyPropertiesListResponse> {
            override fun onResponse(
                call: Call<BuyPropertiesListResponse>,
                response: Response<BuyPropertiesListResponse>
            ) {
                showLoadMoreProgress(false)
                if (response.isSuccessful) {

                    val status = response.body()?.response
                    if (status == "Success") {
                        val data = response.body()?.data
                        data?.propertyLists?.filterNotNull()?.let {
                            if (it.isNotEmpty()) loadingMain = true
                            propertyLists.addAll(it)
                            iMapMarkerLocation?.setPropertyListForMap(it)
                        }
                        mPropertyListAdapter?.notifyDataSetChanged()
                        dismissAnimatedProgressBar()
                    }
                } else {
                    Timber.e("Status Failed")
                }
            }

            override fun onFailure(call: Call<BuyPropertiesListResponse>, t: Throwable) {
                showLoadMoreProgress(false)
                Timber.e("Status Failed")
            }
        })
    }

    //Fav Property
    private fun apiFavProperty(id: String?, userID: String?, position: Int) {
        mLoadingDialog?.show()
        ApiLinks.client.create(AddFavPropertyApi::class.java)
            .post(id, userID, SessionManager.getLanguageID(activity))
            .enqueue(object : Callback<AddFavPropertyResponse> {
                override fun onResponse(
                    call: Call<AddFavPropertyResponse>,
                    response: Response<AddFavPropertyResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.response.equals("Success", ignoreCase = true)) {
                            propertyLists[position].let {
                                val propertyFavourite = it.propertyFavourite
                                if (propertyFavourite == "1") {
                                    it.propertyFavourite = "0"
                                    shortListListener?.decreaseClick()
                                } else {
                                    it.propertyFavourite = "1"
                                    shortListListener?.increaseClick()
                                }
                                //                                buyPropertyListAdapter.notifyItemChanged(position);
                                mPropertyListAdapter?.notifyItemChanged(position)
                                mLoadingDialog?.dismiss()
                                Timber.e("Status", "status")
                            }

                        } else {
                            mLoadingDialog?.dismiss()
                            Toast.makeText(
                                context,
                                getString(R.string.failed_to_shortlist),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        Timber.e("Status", "status1")
                    } else {
                        Timber.e("Status", "Failed2")
                        mLoadingDialog?.dismiss()
                        Toast.makeText(
                            context,
                            getString(R.string.failed_to_shortlist),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Timber.e("Status", "status3")
                }

                override fun onFailure(call: Call<AddFavPropertyResponse>, t: Throwable) {
                    Timber.e("Status", t.message!!)
                    mLoadingDialog!!.dismiss()
                    Toast.makeText(
                        context,
                        getString(R.string.failed_to_shortlist),
                        Toast.LENGTH_SHORT
                    ).show()
                    Timber.e("Status", "status4")
                }
            })
    }

    /*private fun postContactOwner(
        activity: FragmentActivity?, alertDialog: Dialog, strName: String, strEmail: String,
        strPhone: String, strCountryCode: String, position: Int, itemId: String?
    ) {
        if (!Utils.NoInternetConnection(activity)) {
            alertDialog.dismiss()
            mLoadingDialog!!.show()
            ApiLinks.getClient().create(
                ContactOwnerPropertyApi::class.java
            )
                .post(
                    SessionManager.getAccessToken(activity),
                    itemId, "Property", "Listing",
                    strEmail, strName, strPhone, "", strCountryCode, isMortgaged
                )
                .enqueue(object : Callback<ContactOwnerResponse> {
                    override fun onResponse(
                        call: Call<ContactOwnerResponse>,
                        response: Response<ContactOwnerResponse>
                    ) {
                        try {
                            if (response.isSuccessful) {
                                val message = response.body()?.response
                                Timber.e("postContactOwner", message)
                                val code = response.body()?.code
                                if (code == 4000) {
                                    //alertDialog.dismiss();
                                    alertSuccess(
                                        requireActivity(),
                                        resources.getString(R.string.thank_you_for_contact),
                                        resources.getString(R.string.contact_owner_success_message)
                                    )
                                } else {
                                    alertDialog.dismiss()
                                    Timber.e("postContactOwner", response.body()?.message)
                                    Toast.makeText(
                                        requireActivity(),
                                        response.body()?.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                mLoadingDialog?.dismiss()
                            } else {
                                alertDialog.dismiss()
                                Toast.makeText(
                                    requireActivity(),
                                    getString(R.string.no_response),
                                    Toast.LENGTH_SHORT
                                ).show()
                                mLoadingDialog?.dismiss()
                            }
                        } catch (e: Exception) {
                            FirebaseCrashlytics.getInstance().recordException(e)
                            e.printStackTrace()
                        }
                    }

                    override fun onFailure(call: Call<ContactOwnerResponse>, t: Throwable) {
                        alertDialog.dismiss()
                        Toast.makeText(getActivity(), t.message, Toast.LENGTH_SHORT).show()
                        mLoadingDialog?.dismiss()
                        alertDialog.dismiss()
                    }
                })
        } else {
            alertDialog.dismiss()
            Utils.showAlertNoInternet(activity)
        }
    }*/

    /*private fun alertContactOwner(activity: FragmentActivity, position: Int, itemId: String?) {
        alertDialog = Dialog(activity)
        alertDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val factory = (activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        @SuppressLint("InflateParams") val layoutAlert =
            factory.inflate(R.layout.alert_contact_agent_popup, null)
        val close = layoutAlert.findViewById<ImageView>(R.id.close_contact_owner)
        val fullName =
            layoutAlert.findViewById<EditText>(R.id.editText_user_full_name_contact_owner)
        val email =
            layoutAlert.findViewById<EditText>(R.id.editText_user_email_address_contact_owner)
        val phone = layoutAlert.findViewById<EditText>(R.id.editText_phone_contact_owner)
        val codePicker =
            layoutAlert.findViewById<CountryCodePicker>(R.id.country_code_contact_owner)
        val message = layoutAlert.findViewById<EditText>(R.id.editText_user_message_contact_owner)
        val submit = layoutAlert.findViewById<TextView>(R.id.button_submit_contact_owner)
        val cbMortgage =
            layoutAlert.findViewById<CheckBox>(R.id.checkBox_info_mortgage_make_an_offer)
        val cbTerms =
            layoutAlert.findViewById<CheckBox>(R.id.checkBox_terms_conditions_make_an_offer)
        message.visibility = View.GONE
        val title = layoutAlert.findViewById<TextView>(R.id.title)
        title.text = "Contact Owner"
        if (SessionManager.getLoginStatus(activity)) {
            fullName.setText(SessionManager.getFullName(activity))
            email.setText(SessionManager.getEmail(activity))
            phone.setText(SessionManager.getPhone(activity))
            codePicker.setCountryForNameCode(SessionManager.getCountryCode(activity))
        }
        close.setOnClickListener { alertDialog?.dismiss() }
        submit.setOnClickListener {
            val strCountryCode = codePicker.selectedCountryNameCode.lowercase(Locale.getDefault())
            val strName = fullName.text.toString()
            val strEmail = email.text.toString()
            val strPhone = phone.text.toString()
            val alertValidation =
                Utils.validateContactPopUp(getActivity(), strName, strEmail, strPhone, cbTerms)
            if (cbMortgage.isChecked) {
                isMortgaged = "Yes"
            }
            if (alertValidation == "ok") {
                if (!Utils.NoInternetConnection(activity)) {
                    postContactOwner(
                        activity,
                        alertDialog!!,
                        strName,
                        strEmail,
                        strPhone,
                        strCountryCode,
                        position,
                        itemId
                    )
                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.no_internet_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                if (!alertValidation.equals("notOk", ignoreCase = true)) {
                    Toast.makeText(getActivity(), alertValidation, Toast.LENGTH_SHORT).show()
                }
            }
        }
        alertDialog?.setContentView(layoutAlert)
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.setCanceledOnTouchOutside(true)
        alertDialog?.show()
    }*/

    private fun alertSuccess(
        activity: FragmentActivity,
        successTitle: String,
        successMessage: String
    ) {
        alertDialog = Dialog(activity)
        alertDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val factory = (activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        @SuppressLint("InflateParams") val layoutAlert =
            factory.inflate(R.layout.alert_success_popup, null)
        val close = layoutAlert.findViewById<ImageView>(R.id.close_success_dialog)
        val title = layoutAlert.findViewById<TextView>(R.id.title_alert_success_popup)
        val message = layoutAlert.findViewById<TextView>(R.id.message_alert_success_popup)
        title.text = successTitle
        message.text = successMessage
        close.setOnClickListener { alertDialog?.dismiss() }
        alertDialog?.setContentView(layoutAlert)
        alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.setCanceledOnTouchOutside(true)
        alertDialog?.show()
    }

    @SuppressLint("StaticFieldLeak")
    companion object {
        @JvmField
        var propertyLists: MutableList<BuyPropertiesModel> = arrayListOf()

        @JvmField
        var mPropertyListAdapter: PropertyListAdapter? = null
    }
}