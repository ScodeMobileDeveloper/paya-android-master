package com.paya.paragon

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.paya.paragon.activity.LanguageActivity
import com.paya.paragon.activity.agent.AgentListFragment
import com.paya.paragon.activity.buy.IMapMarkerLocation
import com.paya.paragon.activity.buy.LocationsActivity
import com.paya.paragon.activity.buy.ProjectListFragment
import com.paya.paragon.activity.buy.PropertyListFragment
import com.paya.paragon.activity.dashboard.*
import com.paya.paragon.activity.localExpert.ActivityLocalExpertDashboard
import com.paya.paragon.activity.login.ActivityLoginEmail
import com.paya.paragon.activity.login.ActivityOTP
import com.paya.paragon.activity.postProperty.PostPropertyPage01Activity
import com.paya.paragon.activity.postRequirements.ActivityRequirementPurpose
import com.paya.paragon.activity.search.ActivityFilter
import com.paya.paragon.api.BuyProjects.BuyProjectsListApi
import com.paya.paragon.api.BuyProjects.BuyProjectsListResponse
import com.paya.paragon.api.BuyProjects.BuyProjectsModel
import com.paya.paragon.api.DeleteUserApi
import com.paya.paragon.api.PayaAPICall
import com.paya.paragon.api.buy_properties.BuyPropertiesListApi
import com.paya.paragon.api.buy_properties.BuyPropertiesListResponse
import com.paya.paragon.api.buy_properties.BuyPropertiesModel
import com.paya.paragon.api.postProperty.attributeListing.AllAttributesData
import com.paya.paragon.base.commonClass.ApiLinks
import com.paya.paragon.base.commonClass.GlobalValues
import com.paya.paragon.databinding.PropertyProjectListActivityBinding
import com.paya.paragon.model.GenericResponse
import com.paya.paragon.utilities.*
import com.paya.paragon.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*

class PropertyProjectListActivity : AppCompatActivity(), View.OnClickListener, ShortListListener,
    IMapMarkerLocation, TextWatcher {

    private val homeViewModel by viewModel<HomeViewModel>()

    private var tvLanguage: TextView? = null
    private var imgFlag: ImageView? = null
    private var tvProfileName: TextView? = null
    private var llProfileView: LinearLayout? = null
    private var imageURLProperties: String? = null
    private var imageBaseURLCompany: String? = null
    private var imageURLProjects: String? = null
    private var strProjectPropertyCount: String? = null
    private var allAttributesList: ArrayList<AllAttributesData>? = ArrayList()
    private var searchPropertyPurpose: String? = null
    private var userId: String? = null
    private val locationId: String
        get() = SessionManager.getLocationId(this)
    private var sortText: List<String>? = null
    private var sortValue: List<String>? = null
    private var mLoadingDialog: DialogProgress? = null

    private var shortListedCount: TextView? = null
    private var shortListedTotal = 0
    private var navigationView: NavigationView? = null
    private var navLogin: LinearLayout? = null
    private var navLanguage: LinearLayout? = null
    private var navLogout: LinearLayout? = null
    private var navCalculator: LinearLayout? = null
    private var navDashboard: LinearLayout? = null
    private var navSellPostAdd: LinearLayout? = null
    private var navPostRequirement: LinearLayout? = null
    private var navPostedRequirement: LinearLayout? = null
    private var navSettings: LinearLayout? = null
    private var navBuy: LinearLayout? = null
    private var navRent: LinearLayout? = null
    private var navSavedSearch: LinearLayout? = null
    private var navExpertCommunity: LinearLayout? = null
    private var navMyProperty: LinearLayout? = null
    private var navShortlisted: LinearLayout? = null
    private var navDeleteAccount: LinearLayout? = null
    private var isNewProject = false
    private var type: PropertyProjectType? = null

    private var llContactPaya: LinearLayout? = null
    private var imgProperty: ImageView? = null
    private var imgProject: ImageView? = null
    private var imgAgent: ImageView? = null
    private var imgHamburgerMenu: ImageView? = null
    private var tvNavProperty: TextView? = null
    private var tvNavProject: TextView? = null
    private var tvNavAgents: TextView? = null
    private var tvNavMenu: TextView? = null
    private var tvFilterSearch: TextView? = null
    private var imgSort: ImageView? = null
    private var imgMap: ImageView? = null
    private var isExit = false
    private var isPropertyTabSelected = false
    private var isProjectTabSelected = false
    private lateinit var binding: PropertyProjectListActivityBinding
    private var projectListActivityCallBack: PropertyProjectListActivityCallBack? = null

    fun setAgentFragmentCallBack(projectListActivityCallBack: PropertyProjectListActivityCallBack?) {
        this.projectListActivityCallBack = projectListActivityCallBack
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.property_project_list_activity)
        SessionManager.setLocationId(this, SessionManager.getSelectedLocationId(this))
        GlobalValues.clearBuyGlobalValues()
        initiateToolbar()
        initiateViews()
        initiateData()
        appVersion()
        Timber.e("token---->${SessionManager.getDeviceTokenForFCM(this)}")
    }

    private fun initiateToolbar() {
        setSupportActionBar(binding.llMainLayout.toolbar)
        supportActionBar?.elevation = 0f
        binding.llMainLayout.toolbarTitle.textSize = 18f
    }

    private fun appVersion() {
        val appVersion = findViewById<TextView>(R.id.tv_app_version)
        appVersion.text = Utils.getPackageVersionName(this)
    }

    private fun initiateViews() {
        tvFilterSearch = findViewById(R.id.text_filter_search)
        imgSort = findViewById(R.id.img_sort)
        imgMap = findViewById(R.id.img_map)
        tvLanguage = findViewById(R.id.tv_language)
        imgFlag = findViewById(R.id.img_flag)
        llProfileView = findViewById(R.id.ll_profile_view)
        tvProfileName = findViewById(R.id.tv_profile_name)
        navigationView = findViewById<View>(R.id.nav_view_home) as NavigationView
        navLogin = navigationView?.findViewById(R.id.nav_item_login)
        navLanguage = navigationView?.findViewById(R.id.nav_item_language)
        navLogout = navigationView?.findViewById(R.id.nav_item_logout)
        navCalculator = navigationView?.findViewById(R.id.nav_item_savings_calculator)
        navDashboard = navigationView?.findViewById(R.id.nav_item_dashboard)
        navSellPostAdd = navigationView?.findViewById(R.id.nav_item_list_property)
        navPostRequirement = navigationView?.findViewById(R.id.nav_item_post_requirements)
        navPostedRequirement = navigationView?.findViewById(R.id.nav_item_posted_requirements)
        navSettings = navigationView?.findViewById(R.id.nav_item_settings)
        navBuy = navigationView?.findViewById(R.id.nav_item_buy)
        navRent = navigationView?.findViewById(R.id.nav_item_rent)
        navSavedSearch = navigationView?.findViewById(R.id.nav_item_saved_searches)
        navExpertCommunity = navigationView?.findViewById(R.id.nav_item_expert_community)
        navMyProperty = navigationView?.findViewById(R.id.nav_item_my_properties)
//        add_property = findViewById(R.id.add_property)

        shortListedCount = findViewById(R.id.shortListedCount)
        //initiateBottomViews
        imgProperty = findViewById(R.id.img_property)
        imgProject = findViewById(R.id.img_project)
        imgAgent = findViewById(R.id.img_nav_agent)
        imgHamburgerMenu = findViewById(R.id.img_hamberger_menu)

        tvNavProperty = findViewById(R.id.tv_nav_property)
        tvNavProject = findViewById(R.id.tv_nav_project)
        tvNavAgents = findViewById(R.id.tv_nav_agent)
        llContactPaya = findViewById(R.id.ll_contact_paya)
        tvNavMenu = findViewById(R.id.tv_nav_menu)
        navDeleteAccount = findViewById(R.id.nav_item_delete_user)
        navShortlisted = findViewById(R.id.nav_item_favourites)
        navDeleteAccount?.setOnClickListener(this)
        navCalculator?.setOnClickListener(this)
        navDashboard?.setOnClickListener(this)
        navPostRequirement?.setOnClickListener(this)
        navPostedRequirement?.setOnClickListener(this)
        navSettings?.setOnClickListener(this)
        navSellPostAdd?.setOnClickListener(this)
        navSavedSearch?.setOnClickListener(this)
        navMyProperty?.setOnClickListener(this)
        navExpertCommunity?.setOnClickListener(this)
        navBuy?.setOnClickListener(this)
        navRent?.setOnClickListener(this)
        navLogin?.setOnClickListener(this)
        navLanguage?.setOnClickListener(this)
        llProfileView?.setOnClickListener(this)
        llContactPaya?.setOnClickListener(this)
        navLogout?.setOnClickListener(this)
//        add_property?.setOnClickListener(this)
        imgProperty?.setOnClickListener(this)
        imgProject?.setOnClickListener(this)
        imgHamburgerMenu?.setOnClickListener(this)
        imgAgent?.setOnClickListener(this)
        navShortlisted?.setOnClickListener(this)
        tvFilterSearch?.setOnClickListener(this)
        imgMap?.setOnClickListener(this)
        imgSort?.setOnClickListener(this)
        binding.llMainLayout.bottomBar.bottomHeart.setOnClickListener(this)
        binding.llMainLayout.bottomBar.bottomHome.setOnClickListener(this)
        binding.llMainLayout.bottomBar.llProperty.setOnClickListener(this)
        binding.llMainLayout.bottomBar.llAddProperty.setOnClickListener(this)
        binding.llMainLayout.bottomBar.homeAddPropertyImb.setOnClickListener(this)
        binding.llMainLayout.bottomBar.ivAddProperty.setOnClickListener(this)
        binding.llMainLayout.bottomBar.llProject.setOnClickListener(this)
        binding.llMainLayout.bottomBar.llAgents.setOnClickListener(this)
        binding.llMainLayout.bottomBar.llMenu.setOnClickListener(this)
        binding.llMainLayout.imgAgentSort.setOnClickListener(this)
        binding.llMainLayout.imgAgentSearchClear.setOnClickListener(this)
        binding.llMainLayout.imgAgentSearch.setOnClickListener(this)
    }

    private fun initiateData() {
        mLoadingDialog = DialogProgress(this)
        mLoadingDialog?.show()
        searchPropertyPurpose = intent.getStringExtra("searchPropertyPurpose")
        isNewProject = intent.getBooleanExtra("newProject", false)
        type = if (isNewProject) PropertyProjectType.NEW_PROJECT else PropertyProjectType.BUY
        GlobalValues.searchPropertyPurpose = searchPropertyPurpose
        if (isNewProject) {
            GlobalValues.selectedSortValue = "date/orderby/desc"
            GlobalValues.selectedSortText = getString(R.string.new_listing)
        }
        type = if (searchPropertyPurpose.equals("Sell", ignoreCase = true)) {
            if (isNewProject) PropertyProjectType.NEW_PROJECT else PropertyProjectType.BUY
        } else {
            if (isNewProject) PropertyProjectType.NEW_PROJECT else PropertyProjectType.RENT
        }
        setToolbarTitle(getString(R.string.properties))
        userId = if (SessionManager.getLoginStatus(this)) {
            SessionManager.getAccessToken(this)
        } else {
            ""
        }
        setDefaultSortValuesForProjectAndProperty()
        tvLanguage?.text = SessionManager.getLanguageName(this)
        imgFlag?.setImageDrawable(Utils.getLanguageFlag(SessionManager.getLanguageName(this)))
        onPropertyTabSelection()
    }

    private fun setDefaultSortValuesForProjectAndProperty() {
        sortText = listOf(*resources.getStringArray(R.array.sortList))
        sortValue = listOf(*resources.getStringArray(R.array.sortValue))
        if (GlobalValues.selectedSortText == null) {
            GlobalValues.selectedSortText = sortText?.get(3)
            GlobalValues.selectedSortValue = sortValue?.get(3)
        }
    }

    private fun setToolbarTitle(type: String): String {
        val title: String
        val id = Utils.getCitNameBasedOnLanguage(SessionManager.getLocationId(this))
        val propertyType = getString(
            if (searchPropertyPurpose.equals(
                    "Sell",
                    ignoreCase = true
                )
            ) R.string.sale else R.string.rent
        )
        title = String.format(
            "%s %s %s %s",
            getString(
                if (type.equals(
                        getString(R.string.properties),
                        ignoreCase = true
                    )
                ) R.string.properties_for else R.string.projects_for
            ),
            if (type.equals(
                    getString(R.string.properties),
                    ignoreCase = true
                )
            ) propertyType else "",
            getString(R.string.`in`),
            if (id != 0) getString(id) else ""
        )
        binding.llMainLayout.toolbarTitle.visibility = View.VISIBLE
        binding.llMainLayout.toolbarTitle.text = title
        return title
    }


    @SuppressLint("InflateParams")
    private fun alertLogout() {
        val alertDialog = Dialog(this)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val factory = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        val layoutAlert = factory.inflate(R.layout.alert_exit_popup, null)
        layoutAlert.findViewById<TextView>(R.id.alert_cancel_button).setOnClickListener {
            alertDialog.dismiss()
        }
        layoutAlert.findViewById<TextView>(R.id.alert_ok_button).setOnClickListener {
            alertDialog.dismiss()
            SessionManager.logout(this@PropertyProjectListActivity)
            navLogin?.visibility = View.VISIBLE
            navLogout?.visibility = View.GONE
            navLogin?.requestFocus()
            llProfileView?.visibility = View.GONE
            checkLogin()
        }
        alertDialog.setContentView(layoutAlert)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }

    private fun checkLogin() {
        val isLoggedInUser = SessionManager.getLoginStatus(this@PropertyProjectListActivity)
        navLogin?.visibility =
            if (isLoggedInUser) View.GONE else View.VISIBLE
        navLogout?.visibility =
            if (isLoggedInUser) View.VISIBLE else View.GONE
        llProfileView?.visibility = if (isLoggedInUser) View.VISIBLE else View.GONE
        navSettings?.visibility = if (isLoggedInUser) View.VISIBLE else View.GONE
        navPostedRequirement?.visibility = if (isLoggedInUser) View.VISIBLE else View.GONE
        navShortlisted?.visibility =
            if (isLoggedInUser) View.VISIBLE else View.GONE
        navDeleteAccount?.visibility =
            if (isLoggedInUser) View.VISIBLE else View.GONE
    }

    private fun resultBasedOnPreviousTabSelection() {
        if (isProjectTabSelected) {
            onProjectTabSelection()
        } else if (isPropertyTabSelected) {
            onPropertyTabSelection()
        }
    }

    override fun increaseClick() {
        shortListedTotal += 1
        shortListedCount?.text = shortListedTotal.toString()
        if (shortListedTotal > 0) shortListedCount?.background =
            getDrawableRes(R.drawable.cart_round) else shortListedCount?.background =
            getDrawableRes(R.drawable.cart_round_disable)
    }

    override fun decreaseClick() {
        shortListedTotal -= 1
        shortListedCount?.text = shortListedTotal.toString()
        if (shortListedTotal > 0) shortListedCount?.background =
            getDrawableRes(R.drawable.cart_round) else shortListedCount?.background =
            getDrawableRes(R.drawable.cart_round_disable)
    }

    //get properties
    private fun apiGetProperties() {
        mLoadingDialog?.show()
        ApiLinks.client.create(BuyPropertiesListApi::class.java).post(
            userId,
            SessionManager.getLanguageID(this),
            locationId,
            searchPropertyPurpose,
            "0",
            "Property",
            "" + GlobalValues.selectedSortValue,
            if (GlobalValues.selectedMinPrice == null) "0" else GlobalValues.selectedMinPrice,
            if (GlobalValues.selectedMaxPrice == null) "0" else GlobalValues.selectedMaxPrice,
            if (GlobalValues.selectedPropertyTypeID == null) "" else GlobalValues.selectedPropertyTypeID,
            GlobalValues.countryID,
            GlobalValues.selectedBedroomsNumber,
            GlobalValues.selectedFurnishedStaticValue,
            GlobalValues.selectedRegionId,
            GlobalValues.possessionStatus,
            "",
            "",
            "",
            ""
        )
            .enqueue(object : Callback<BuyPropertiesListResponse> {
                override fun onResponse(
                    call: Call<BuyPropertiesListResponse>,
                    response: Response<BuyPropertiesListResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.response.equals("Success", ignoreCase = true)) {
                            response.body()?.data?.let { data ->
                                imageURLProperties = data.imageURL
                                imageBaseURLCompany = data.companyProfileLogoUrl
                                siteUrl = data.siteUrl.orEmpty()
                                strProjectPropertyCount = data.searchPropertyCount
                                propertyLists =
                                    data.propertyLists?.filterNotNull() as MutableList<BuyPropertiesModel>
                                setPropertyListForMap(propertyLists)
                            }

                        } else {
                            dismissAnimationLoading()
                            strProjectPropertyCount = "0"
                            propertyLists = arrayListOf()
                        }
                        initiatePropertyListData()
                    } else {
                        dismissAnimationLoading()
                        Timber.e("Status", "Failed")
                    }
                }

                override fun onFailure(call: Call<BuyPropertiesListResponse>, t: Throwable) {
                    Timber.e("apiGetProperties", t.message.orEmpty())
                    dismissAnimationLoading()
                }
            })
    }

    //get projects
    private fun apiGetProjects() {
        mLoadingDialog?.show()
        ApiLinks.client.create(BuyProjectsListApi::class.java).post(
            userId,
            SessionManager.getLanguageID(this),
            locationId,
            searchPropertyPurpose,
            "0",
            "Project",
            GlobalValues.selectedSortValue,
            if (GlobalValues.selectedMinPrice == null) "0" else GlobalValues.selectedMinPrice,
            if (GlobalValues.selectedMaxPrice == null) "0" else GlobalValues.selectedMaxPrice,
            if (GlobalValues.selectedPropertyTypeID == null) "" else GlobalValues.selectedPropertyTypeID,
            GlobalValues.countryID,
            GlobalValues.selectedBedroomsNumber,
            GlobalValues.selectedFurnishedStaticValue,
            GlobalValues.selectedRegionId,
            GlobalValues.possessionStatus,
            "",
            "",
            "",
            ""
        )
            .enqueue(object : Callback<BuyProjectsListResponse> {
                override fun onResponse(
                    call: Call<BuyProjectsListResponse>,
                    response: Response<BuyProjectsListResponse>
                ) {
                    if (response.isSuccessful) {
                        if (response.body()?.response == "Success") {
                            response.body()?.data?.let { data ->
                                imageURLProjects = data.imageURL
                                siteUrl = data.siteUrl
                                strProjectPropertyCount = data.searchProjectCount
                                projectLists = data.projectLists
                            }

                        } else {
                            dismissAnimationLoading()
                            strProjectPropertyCount = "0"
                            projectLists = arrayListOf()
                        }
                        initiateProjectListData()
                        //                            setTabs(selectedTabPosition);
                    } else {
                        Timber.e("Status", "Failed")
                        dismissAnimationLoading()
                    }
                }

                override fun onFailure(call: Call<BuyProjectsListResponse>, t: Throwable) {
                    dismissAnimationLoading()
                }
            })
    }

    //get Agents
    /*fun apiGetAgents() {
        ApiLinks.getClient().create(BuyAgentsListApi::class.java).post(
            "" + userId,
            "" + SessionManager.getLanguageID(this),
            "" + location_id,
            "" + searchPropertyPurpose,
            "0",
            "Agent",
            "" + GlobalValues.selectedSortValue,
            "" + GlobalValues.selectedMinPrice,
            "" + GlobalValues.selectedMaxPrice,
            "" + GlobalValues.selectedPropertyTypeID,
            GlobalValues.countryID,
            "" + GlobalValues.selectedBedroomsNumber,
            "" + GlobalValues.selectedRegionId,
            "" + GlobalValues.possessionStatus,
            "",
            "",
            "",
            ""
        ).enqueue(object : Callback<BuyAgentsListResponse> {
            override fun onResponse(
                call: Call<BuyAgentsListResponse>,
                response: Response<BuyAgentsListResponse>
            ) {
                if (response.isSuccessful) {
                    //count++;
                    if (response.body()?.response == "Success") {
                        response.body()?.data?.let { data ->
                            imageURLAgents = data.imageURL
                            strCountAgent = data.searchAgentCount
                            agentLists = data.agentLists
                        }

                    } else {
                        strCountAgent = "0"
                        agentLists = null
                    }
                    *//*if (count == 2) {
                        setTabs(selectedTabPosition);
                    }*//*
                } else {
                    Timber.e("Status", "Failed")
                }
            }

            override fun onFailure(call: Call<BuyAgentsListResponse>, t: Throwable) {
                dismissAnimationLoading()
            }
        })
    }*/

    //TODO Main functions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        Utils.changeLayoutOrientationDynamically(this, findViewById(R.id.drawer_layout_home))
        setProfileDetails()
        checkLogin()
    }

    //TODO Click Listener
    override fun onClick(view: View) {
        when (view) {
            navLogin -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                if (SessionManager.getLoginStatus(this@PropertyProjectListActivity)) {
                    val intentLogin =
                        Intent(this@PropertyProjectListActivity, ActivityMyProperties::class.java)
                    startActivity(intentLogin)
                } else {
                    if (SessionManager.getPhone(this).isNotEmpty()) {
                        if (!SessionManager.isOTPVerified(this)) {
                            startActivity(
                                Intent(
                                    this@PropertyProjectListActivity,
                                    ActivityOTP::class.java
                                ).putExtra("comingFrom", "signin")
                            )
                        } else {
                            startActivity(
                                Intent(
                                    this@PropertyProjectListActivity,
                                    ActivityLoginEmail::class.java
                                ).putExtra("comingFrom", "menu")
                            )
                        }
                    } else {
                        startActivity(
                            Intent(
                                this@PropertyProjectListActivity,
                                ActivityLoginEmail::class.java
                            ).putExtra("comingFrom", "menu")
                        )
                    }
                }
            }
            navLanguage -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                val intent = Intent(this@PropertyProjectListActivity, LanguageActivity::class.java)
                startActivity(intent)
            }
            llContactPaya -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                contactPaya()
            }
            llProfileView -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                val intent = Intent(this@PropertyProjectListActivity, ProfileActivity::class.java)
                startActivity(intent)
            }
            navMyProperty -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                if (SessionManager.getLoginStatus(this)) {
                    startActivity(Intent(this, ActivityMyProperties::class.java))
                } else {
                    startActivity(
                        Intent(
                            this@PropertyProjectListActivity,
                            ActivityLoginEmail::class.java
                        ).putExtra("comingFrom", AppConstant.FROM_MY_PROPERTY)
                    )
                }
            }
            navSellPostAdd, binding.llMainLayout.bottomBar.llAddProperty,
            binding.llMainLayout.bottomBar.homeAddPropertyImb, binding.llMainLayout.bottomBar.ivAddProperty -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                if (SessionManager.getLoginStatus(this@PropertyProjectListActivity)) {
                    startActivity(
                        Intent(
                            this@PropertyProjectListActivity,
                            PostPropertyPage01Activity::class.java
                        )
                    )
                } else {
                    startActivity(
                        Intent(
                            this@PropertyProjectListActivity,
                            ActivityLoginEmail::class.java
                        ).putExtra("comingFrom", AppConstant.FROM_POST_PROPERTY)
                    )
                }
            }
            navPostRequirement -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                val intentPostReq =
                    Intent(this@PropertyProjectListActivity, ActivityRequirementPurpose::class.java)
                startActivity(intentPostReq)
            }
            navPostedRequirement -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                val intent =
                    Intent(this@PropertyProjectListActivity, ActivityPostedRequirements::class.java)
                startActivity(intent)
            }
            navExpertCommunity -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                startActivity(
                    Intent(
                        this@PropertyProjectListActivity,
                        ActivityLocalExpertDashboard::class.java
                    )
                )
            }
            navSettings -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                val intent = Intent(this@PropertyProjectListActivity, ActivitySettings::class.java)
                startActivity(intent)
            }
            navLogout -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                alertLogout()
            }
            navDeleteAccount -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                doDeleteAccount()
            }
            navShortlisted -> {
                val intent =
                    Intent(this@PropertyProjectListActivity, ActivityShortlisted::class.java)
                startActivity(intent)
            }
            binding.llMainLayout.bottomBar.llMenu, imgHamburgerMenu -> {
                if (binding.drawerLayoutHome.isDrawerOpen(GravityCompat.END)) {
                    binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                } else {
                    binding.drawerLayoutHome.openDrawer(GravityCompat.END)
                }
            }
            binding.llMainLayout.bottomBar.llProperty, imgProperty -> {
                val selectedLocId = SessionManager.getSelectedLocationId(this)
                val locId = SessionManager.getLocationId(this)
                // TODO need to write a method to check whether filter is applied or not
                val isFilterApplied = !GlobalValues.selectedRegions.isNullOrEmpty()
                if (!isSimilarFragment(PropertyListFragment())) {
                    SessionManager.setLocationId(this, selectedLocId)
                    GlobalValues.clearBuyGlobalValues()
                    onPropertyTabSelection()
                } else {
                    if (selectedLocId == locId && !isFilterApplied) {
                        resetAppBarBehaviour()
                        homeViewModel.updatePropertyTabClickState()
                    } else {
                        SessionManager.setLocationId(this, selectedLocId)
                        GlobalValues.clearBuyGlobalValues()
                        onPropertyTabSelection()
                    }
                }
            }
            binding.llMainLayout.bottomBar.llProject, imgProject -> {
                if (!isSimilarFragment(ProjectListFragment())) {
                    onProjectTabSelection()
                }
            }
            binding.llMainLayout.bottomBar.llAgents, imgAgent -> {
                if (!isSimilarFragment(AgentListFragment())) {
                    onAgentTabSelection()
                }
            }
            imgMap -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                openPropertyProjectMap()
            }
            imgSort -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                openPropertyProjectSortOptionDialog()
            }
            tvFilterSearch -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                SessionManager.setLocationId(this, SessionManager.getSelectedLocationId(this))
                openFilterActivity()
            }
            binding.llMainLayout.imgAgentSearch -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                if (projectListActivityCallBack != null) projectListActivityCallBack?.agentSearchUsingEnterText()
            }
            binding.llMainLayout.imgAgentSort -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                GlobalValues.selectedAgentSortByCount = if (AppConstant.SORT_BY_DESC.equals(
                        GlobalValues.selectedAgentSortByCount,
                        ignoreCase = true
                    )
                ) AppConstant.SORT_BY_ASC else AppConstant.SORT_BY_DESC
                binding.llMainLayout.imgAgentSort.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        if (AppConstant.SORT_BY_DESC.equals(
                                GlobalValues.selectedAgentSortByCount,
                                ignoreCase = true
                            )
                        ) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
                    )
                )
                if (projectListActivityCallBack != null) projectListActivityCallBack?.agentSortAgentList()
            }
            binding.llMainLayout.imgAgentSearchClear -> {
                binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
                if (projectListActivityCallBack != null) projectListActivityCallBack?.agentClearSearchText()
            }
        }
    }

    private fun setProfileDetails() {
        tvProfileName?.text = SessionManager.getFullName(this)
    }

    private fun onPropertyTabSelection() {
        checkLocationUpdateForNotification()
        isPropertyTabSelected = true
        isProjectTabSelected = false
        isExit = false
        resetAppBarBehaviour()
        setOptionColorChange(
            isPropertySelected = true,
            isProjectSelected = false,
            isAgentSelected = false,
            isMenuSelected = false
        )
        binding.llMainLayout.trFilterSortMap.visibility = View.VISIBLE
        binding.llMainLayout.llAgentHeader.visibility = View.GONE
        apiGetProperties()
    }

    private fun onProjectTabSelection() {
        checkLocationUpdateForNotification()
        isPropertyTabSelected = false
        isProjectTabSelected = true
        isExit = false
        resetAppBarBehaviour()
        setOptionColorChange(
            isPropertySelected = false,
            isProjectSelected = true,
            isAgentSelected = false,
            isMenuSelected = false
        )
        binding.llMainLayout.trFilterSortMap.visibility = View.VISIBLE
        binding.llMainLayout.llAgentHeader.visibility = View.GONE
        apiGetProjects()
    }

    private fun onAgentTabSelection() {
        isPropertyTabSelected = false
        isProjectTabSelected = false
        isExit = false
        setOptionColorChange(
            isPropertySelected = false,
            isProjectSelected = false,
            isAgentSelected = true,
            isMenuSelected = false
        )
        binding.llMainLayout.toolbarTitle.text = getString(R.string.agents)
        resetAppBarBehaviour()
        binding.llMainLayout.trFilterSortMap.visibility = View.GONE
        binding.llMainLayout.llAgentHeader.visibility = View.VISIBLE
        binding.llMainLayout.edtFilterSearch.addTextChangedListener(this)
        loadFragment(AgentListFragment())
    }

    private fun setOptionColorChange(
        isPropertySelected: Boolean,
        isProjectSelected: Boolean,
        isAgentSelected: Boolean,
        isMenuSelected: Boolean = false
    ) {
        tvNavProperty?.setTextColor(
            ContextCompat.getColor(
                this,
                if (isPropertySelected) R.color.black else R.color.quantum_grey
            )
        )
        imgProperty?.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (isPropertySelected) R.drawable.ic_nav_property else R.drawable.ic_nav_deselect_properties
            )
        )
        tvNavProperty?.setTypeface(
            null,
            if (isPropertySelected) Typeface.BOLD else Typeface.NORMAL
        )
        tvNavProject?.setTextColor(
            ContextCompat.getColor(
                this,
                if (isProjectSelected) R.color.black else R.color.quantum_grey
            )
        )
        tvNavProject?.setTypeface(null, if (isProjectSelected) Typeface.BOLD else Typeface.NORMAL)
        imgProject?.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (isProjectSelected) R.drawable.ic_nav_projects else R.drawable.ic_nav_deselect_project
            )
        )
        tvNavAgents?.setTextColor(
            ContextCompat.getColor(
                this,
                if (isAgentSelected) R.color.black else R.color.quantum_grey
            )
        )
        tvNavAgents?.setTypeface(null, if (isAgentSelected) Typeface.BOLD else Typeface.NORMAL)
        imgAgent?.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (isAgentSelected) R.drawable.ic_nav_agents else R.drawable.ic_nav_deselect_agent
            )
        )
        tvNavMenu?.setTextColor(
            ContextCompat.getColor(
                this,
                if (isMenuSelected) R.color.black else R.color.quantum_grey
            )
        )
        tvNavMenu?.setTypeface(null, if (isMenuSelected) Typeface.BOLD else Typeface.NORMAL)
        imgHamburgerMenu?.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                if (isMenuSelected) R.drawable.ic_nav_menu else R.drawable.ic_nav_deselect_menu
            )
        )
    }

    private fun initiatePropertyListData() {
        val fragment = PropertyListFragment(
            propertyLists,
            imageURLProperties,
            imageBaseURLCompany,
            strProjectPropertyCount,
            searchPropertyPurpose,
            type
        )
        if (propertyLists.isNotEmpty()) {
            binding.llMainLayout.toolbarTitle.text = String.format(
                "(%s) %s",
                strProjectPropertyCount,
                setToolbarTitle(getString(R.string.properties))
            )
        } else {
            setToolbarTitle(getString(R.string.properties))
        }
        binding.llMainLayout.toolbar.isVisible = propertyLists.isNotEmpty()
        fragment.setListener(this)
        loadFragment(fragment)
        imgMap?.isVisible = propertyLists.isNotEmpty()
        dismissAnimationLoading()
    }

    private fun initiateProjectListData() {
        val fragment = ProjectListFragment(
            projectLists,
            imageURLProjects,
            strProjectPropertyCount,
            searchPropertyPurpose,
            type
        )
        if (projectLists.isNotEmpty()) {
            binding.llMainLayout.toolbarTitle.text = String.format(
                "(%s) %s",
                strProjectPropertyCount,
                setToolbarTitle(getString(R.string.projects))
            )
        } else {
            setToolbarTitle(getString(R.string.projects))
        }
        fragment.setListener(this)
        loadFragment(fragment)
        imgMap?.isVisible = projectLists.isNotEmpty()
        dismissAnimationLoading()
    }

    private fun resetAppBarBehaviour() {
        binding.llMainLayout.appBarLayout.setExpanded(true, false)
    }

    /**
     * call this method to update data for city based notification is not updated when city selection is happened
     * in CityLocationSelection activity and filter activity.
     */
    private fun checkLocationUpdateForNotification() {
        if (!SessionManager.isCityUpdateNotificationUpdated(this)) {
            PayaAPICall().initiateUpdateCityForUserNotification(this)
        }
    }

    private fun dismissAnimationLoading() {
        mLoadingDialog?.let {
            if (it.isShowing) {
                mLoadingDialog?.dismiss()
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        Handler(Looper.getMainLooper()).post {
            surroundTryCatch({
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fl_container, fragment, fragment.javaClass.name)
                fragmentTransaction.addToBackStack(fragment.javaClass.name)
                fragmentTransaction.commitAllowingStateLoss()
            }, {
                FirebaseCrashlytics.getInstance().recordException(it)
                finish()
            })
        }
    }

    private fun isSimilarFragment(fragment: Fragment?): Boolean {
        return fragment != null && activeFragment != null && fragment.javaClass.name == activeFragment?.javaClass?.name
    }

    private val activeFragment: Fragment?
        get() = if (supportFragmentManager.backStackEntryCount == 0) {
            null
        } else supportFragmentManager.findFragmentByTag(
            supportFragmentManager.getBackStackEntryAt(
                supportFragmentManager.backStackEntryCount - 1
            ).name
        )

    override fun onPropertyTabSelected() {

    }

    override fun setPropertyListForMap(propertyLists: List<BuyPropertiesModel>) {
        propertyLists_new = ArrayList()
        if (propertyLists_new.size == 0) {
            propertyLists_new.addAll(propertyLists)
        } else {
            propertyLists_new.addAll(propertyLists_new.size - 1, propertyLists)
        }
    }

    override fun setProjectListForMap(projectLists: List<BuyProjectsModel>) {}
    private fun openPropertyProjectSortOptionDialog() {
        selectedPosition = 100
        val dialogBox = ListDialogBox(
            this@PropertyProjectListActivity,
            sortText,
            getString(R.string.sort_by),
            "sort"
        )
        dialogBox.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogBox.show()
        dialogBox.setOnDismissListener {
            if (selectedPosition != 100) {
                GlobalValues.selectedSortValue = sortValue?.get(selectedPosition)
                GlobalValues.selectedSortText = sortText?.get(selectedPosition)
                resultBasedOnPreviousTabSelection()
            }
        }
    }

    private fun openFilterActivity() {
        val intentBuy = Intent(this, ActivityFilter::class.java).apply {
            putExtra("attributes", allAttributesList)
            putExtra("Properties_Tab_Selected", isPropertyTabSelected)
        }
        filterResult.launch(intentBuy)
    }

    private val filterResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK)
                searchPropertyPurpose = activityResult.data?.getStringExtra("selectedType")
            GlobalValues.searchPropertyPurpose = searchPropertyPurpose
            type = activityResult.data?.getSerializableExtra(Utils.TYPE) as PropertyProjectType?
            allAttributesList =
                activityResult.data?.getSerializableExtra("attributes") as ArrayList<AllAttributesData>?
            if (searchPropertyPurpose == "Sell") {
                binding.llMainLayout.toolbarTitle.setText(R.string.buy)
            } else {
                binding.llMainLayout.toolbarTitle.text = searchPropertyPurpose
            }
            //count = 0;
            /*if (SessionManager.getLocationId(this) != "-1"){
                locationId = SessionManager.getLocationId(this)
            }*/
            resultBasedOnPreviousTabSelection()
        }

    private fun openPropertyProjectMap() {
        val intent = Intent(this, LocationsActivity::class.java).apply {
            if (isPropertyTabSelected) {
                putExtra("from", "Properties")
            } else if (isProjectTabSelected) {
                putExtra("from", "Projects")
            }
            putExtra(AppConstant.I_SEARCH_PURPOSE, searchPropertyPurpose)
            putExtra(AppConstant.I_USER_ID, userId)
            putExtra(Utils.TYPE, type)

        }
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (binding.drawerLayoutHome.isDrawerOpen(GravityCompat.END)) {
            binding.drawerLayoutHome.closeDrawer(GravityCompat.END)
        } else if (activeFragment != null) {
            if (PropertyListFragment::class.java.name == activeFragment?.javaClass?.name) {
                if (!isExit) {
                    isExit = true
                    Toast.makeText(this, "Press once again to exit", Toast.LENGTH_SHORT).show()
                } else {
                    finish()
                }
            } else {
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                onPropertyTabSelection()
            }
        } else {
            finish()
        }
    }

    override fun onDestroy() {
        dismissAnimationLoading()
        super.onDestroy()
    }

    fun updateTotalAgentCount(totalAgentDataCount: Int) {
        binding.llMainLayout.imgAgentSort.visibility =
            if (totalAgentDataCount > 0) View.VISIBLE else View.GONE
        if (totalAgentDataCount > 0) {
            binding.llMainLayout.toolbarTitle.text =
                String.format("%s (%s)", getString(R.string.agents), totalAgentDataCount)
        } else {
            binding.llMainLayout.toolbarTitle.text = getString(R.string.agents)
        }
    }

    fun updateSearchIcons(isSearchTextPresent: Boolean) {
        with(binding.llMainLayout) {
            if (!isSearchTextPresent) {
                edtFilterSearch.removeTextChangedListener(this@PropertyProjectListActivity)
                edtFilterSearch.setText("")
                edtFilterSearch.addTextChangedListener(this@PropertyProjectListActivity)
            }
            imgAgentSearchClear.visibility = if (isSearchTextPresent) View.VISIBLE else View.GONE
        }

    }

    @SuppressLint("InflateParams")
    private fun doDeleteAccount() {
        val alertDialog = Dialog(this)
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val factory = (getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        val layoutAlert = factory.inflate(R.layout.alert_delete_account_popup, null)
        layoutAlert.findViewById<TextView>(R.id.alert_cancel_button).setOnClickListener {
            alertDialog.dismiss()
        }
        layoutAlert.findViewById<TextView>(R.id.alert_ok_button).setOnClickListener {
            alertDialog.dismiss()
            mLoadingDialog?.show()
            ApiLinks.client.create(DeleteUserApi::class.java)
                .deleteUser(SessionManager.getUserId(this))
                .enqueue(object : Callback<GenericResponse> {
                    override fun onResponse(
                        call: Call<GenericResponse>,
                        response: Response<GenericResponse>
                    ) {
                        if (response.isSuccessful) {
                            SessionManager.logout(this@PropertyProjectListActivity)
                            navLogin?.visibility = View.VISIBLE
                            navLogout?.visibility = View.GONE
                            navLogin?.requestFocus()
                            llProfileView?.visibility = View.GONE
                            checkLogin()
                        }
                        dismissAnimationLoading()
                    }

                    override fun onFailure(call: Call<GenericResponse>, t: Throwable) {
                        dismissAnimationLoading()
                    }
                })
        }
        alertDialog.setContentView(layoutAlert)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.setCanceledOnTouchOutside(true)
        alertDialog.show()
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
        if (projectListActivityCallBack != null) projectListActivityCallBack?.agentSearchTechChange(
            charSequence
        )
    }

    override fun afterTextChanged(editable: Editable) {}
    fun hideKeyBoardAutomatically() {
        Utils.hideKeyboard(this, binding.llMainLayout.edtFilterSearch)
    }

    interface PropertyProjectListActivityCallBack {
        fun agentSearchTechChange(value: CharSequence?)
        fun agentClearSearchText()
        fun agentSearchUsingEnterText()
        fun agentSortAgentList()
    }

    override fun onStart() {
        super.onStart()
        Utils.setLanguage(this)
    }

    companion object {
        var propertyLists: MutableList<BuyPropertiesModel> = arrayListOf()

        @JvmField
        var propertyLists_new: MutableList<BuyPropertiesModel> = ArrayList()

        @JvmField
        var projectLists: MutableList<BuyProjectsModel> = ArrayList()

        @JvmField
        var selectedPosition = 100

        @JvmField
        var siteUrl = ""
    }
}