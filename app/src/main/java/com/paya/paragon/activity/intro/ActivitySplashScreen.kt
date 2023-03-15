package com.paya.paragon.activity.intro

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Base64
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.paya.paragon.PropertyProjectListActivity
import com.paya.paragon.R
import com.paya.paragon.activity.CitySelectionActivity
import com.paya.paragon.activity.LanguageActivity
import com.paya.paragon.activity.details.ActivityAgentDetails
import com.paya.paragon.activity.details.ActivityProjectDetails
import com.paya.paragon.activity.details.ActivityPropertyDetails
import com.paya.paragon.activity.localExpert.ActivityLocalExpertDetails
import com.paya.paragon.base.commonClass.GlobalValues
import com.paya.paragon.utilities.surroundTryCatch
import com.paya.paragon.utilities.AppConstant
import com.paya.paragon.utilities.SessionManager
import com.paya.paragon.utilities.Utils
import pl.droidsonroids.gif.GifImageView
import timber.log.Timber
import java.security.MessageDigest
import java.util.*

@SuppressLint("CustomSplashScreen")
class ActivitySplashScreen : AppCompatActivity() {
    private var mUrl = ""
    private var type: String? = ""
    private var typeId: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )
        Timber.tag("id").e(SessionManager.getAccessToken(this))
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        SessionManager.getLanguageName(this)?.let { toggleLanguage(it) }
        setContentView(R.layout.layout_splash_screen)
        printHashKey()
        initializeGifImages()
        checkAppUpdate()
    }

    private fun checkAppUpdate() {
        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance().apply {
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build()
            setConfigSettingsAsync(configSettings)
            setDefaultsAsync(R.xml.firebase_remote_config_default)
            fetchAndActivate()
        }

        val currentAppVersion = appVersion ?: "0"
        val softUpdateVersion = firebaseRemoteConfig.getString("soft_update_version_no")
        val forceUpdateVersion = firebaseRemoteConfig.getString("force_update_version_no")
        if (currentAppVersion.isNotEmpty() && forceUpdateVersion.isNotEmpty() && softUpdateVersion.isNotEmpty()) {
            val currentAppVer = currentAppVersion.replace(".", "").toInt()
            if (currentAppVer < forceUpdateVersion.replace(".", "").toInt()) {
                forceUpdatePopUp()
                return
            } else if (currentAppVer < softUpdateVersion.replace(".", "").toInt()) {
                softUpdateUpdatePopUp()
                return
            } else {
                startActivityAfterDelay()
            }
        } else {
            startActivityAfterDelay()
        }
    }

    private fun forceUpdatePopUp() {
        val updateApp: AlertDialog
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(getString(R.string.text_app_update_title))
            .setMessage(getString(R.string.text_force_update_message))
            .setCancelable(false)
        alertDialog.setPositiveButton(getString(R.string.text_update)) { dialog, _ ->
            SessionManager.logout(this@ActivitySplashScreen)
            openStoreAction()
            dialog.dismiss()
        }
        updateApp = alertDialog.create()
        updateApp.setOnShowListener {
            updateApp.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(this@ActivitySplashScreen, R.color.yellow)
            )
        }
        updateApp.show()
    }

    private fun softUpdateUpdatePopUp() {
        val updateApp: AlertDialog
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(getString(R.string.text_app_update_title))
            .setMessage(getString(R.string.text_soft_update_message))
            .setCancelable(false)
        alertDialog.setPositiveButton(getString(R.string.text_update)) { dialog, _ ->
            SessionManager.logout(this)
            openStoreAction()
            dialog.dismiss()
        }
        alertDialog.setNegativeButton(getString(R.string.text_skip)) { dialog, _ ->
            startActivityAfterDelay()
            dialog.dismiss()
        }
        updateApp = alertDialog.create()
        updateApp.setOnShowListener {
            updateApp.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.yellow))
            updateApp.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.yellow))
        }
        updateApp.show()
    }

    private fun openStoreAction() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(applicationLink())))
    }

    private fun applicationLink(): String {
        return String.format(AppConstant.PLAY_STORE_LINK, packageName)
    }

    private val appVersion: String?
        get() {
            return surroundTryCatch {
                packageManager.getPackageInfo(packageName, 0).versionName
            }
        }

    private fun startActivityAfterDelay() {
        Handler(Looper.getMainLooper()).postDelayed({ intentValues }, 2000L)
    }

    private val intentValues: Unit
        get() {
            if (intent != null) {
                type = intent.getStringExtra("type")
                typeId = intent.getStringExtra("type_id")
            }
            checkIntent()
        }

    private fun checkIntent() {
        try {
            val action =
                if (intent != null && !TextUtils.isEmpty(intent.action)) intent.action else null
            if (action != null && action == "android.intent.action.MAIN") {
                if (type?.isNotEmpty() == true) {
                    Timber.tag("push").d("from main $type -> $typeId")
                    openRespectiveActivity(type, typeId)
                } else {
                    startHomeActivity()
                }
            } else {
                Timber.tag("push").e("from data $mUrl")
                mUrl = intent.data.toString()
                openRespectiveActivity(type, typeId)
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
            startHomeActivity()
        }
    }

    private fun openRespectiveActivity(type: String?, typeId: String?) {
        val startIntent: Intent
        if (type != null && type?.isNotEmpty() == true) {
            if (type.equals("property", ignoreCase = true)) {
                startIntent = Intent(this, ActivityPropertyDetails::class.java)
                startIntent.putExtra("propertyID", typeId)
                startIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(startIntent)
                finish()
            } else if (type.equals("project", ignoreCase = true)) {
                startIntent = Intent(this, ActivityProjectDetails::class.java)
                startIntent.putExtra("projectID", typeId)
                startIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(startIntent)
                finish()
            } else if (type.equals("agency", ignoreCase = true)) {
                startIntent = Intent(this, ActivityAgentDetails::class.java)
                startIntent.putExtra(AppConstant.AGENT_ID, typeId)
                startIntent.putExtra(AppConstant.I_SUB_AGENT_FOR_PARENT, true)
                startIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(startIntent)
                finish()
            } else if (type.equals("service", ignoreCase = true)) {
                startIntent = Intent(this, ActivityLocalExpertDetails::class.java)
                startIntent.putExtra("localExpertId", typeId)
                startIntent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(startIntent)
            } else {
                startHomeActivity()
            }
        } else {
            val splitUrl = mUrl.split("/".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (splitUrl.isNotEmpty()) {
                if (splitUrl[splitUrl.size - 1].equals(
                        AppConstant.PREFIX_PROPERTY,
                        ignoreCase = true
                    )
                ) {
                    startIntent = Intent(this, ActivityPropertyDetails::class.java)
                    startIntent.putExtra("propertyID", splitUrl[splitUrl.size - 2])
                    startIntent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(startIntent)
                    finish()
                } else if (splitUrl[splitUrl.size - 1].equals(
                        AppConstant.PREFIX_PROJECT,
                        ignoreCase = true
                    )
                ) {
                    startIntent = Intent(this, ActivityProjectDetails::class.java)
                    startIntent.putExtra("projectID", splitUrl[splitUrl.size - 2])
                    startIntent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    startActivity(startIntent)
                    finish()
                } else {
                    startHomeActivity()
                }
            } else {
                startHomeActivity()
            }
        }
    }

    private fun initializeGifImages() {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        GlobalValues.screen_height = displayMetrics.heightPixels
        GlobalValues.screen_width = displayMetrics.widthPixels
        val gifImageView = findViewById<GifImageView>(R.id.gif_img)
        val appCompatImageView = findViewById<AppCompatImageView>(R.id.img_logo)
        gifImageView.visibility = View.GONE
        appCompatImageView.visibility = View.VISIBLE
    }

    private fun printHashKey() {
        surroundTryCatch {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Timber.e(String(Base64.encode(md.digest(), 0)))
            }
        }
    }

    private fun toggleLanguage(langName: String) {
        val locale = Locale(
            if (langName.contains(Utils.LAG_ENGLISH)) "en" else if (langName.contains(
                    Utils.LAG_ARABIC
                )
            ) "ar" else "ku"
        )
        Locale.setDefault(locale)
        val config = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun startHomeActivity() {
        val intent: Intent
        if (SessionManager.isFirstTimeLaunch(this)) {
            intent = Intent(this, LanguageActivity::class.java)
        } else if (SessionManager.getLocationId(this) != null && SessionManager.getLocationId(this)
                .isNotEmpty()
        ) {
            GlobalValues.clearBuyGlobalValues()
            intent = Intent(this, PropertyProjectListActivity::class.java)
            intent.putExtra("searchPropertyPurpose", "Sell")
        } else {
            intent = Intent(this, CitySelectionActivity::class.java)
        }
        startActivity(intent)
    }
}