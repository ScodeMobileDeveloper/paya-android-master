package com.paya.paragon

import android.annotation.SuppressLint
import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.iid.FirebaseInstanceId
import com.paya.paragon.di.networkModule
import com.paya.paragon.di.viewModelModule
import com.paya.paragon.utilities.SessionManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class PayaAppClass : Application() {
    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        payaAppInstance = this
        try {
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { instanceIdResult ->
                val token = instanceIdResult.token
                SessionManager.setDeviceTokenForFCM(payaAppInstance, token)
            }
        } catch (e: Exception) {
            FirebaseCrashlytics.getInstance().recordException(e)
        }

        startKoin {
            androidContext(this@PayaAppClass)
            androidLogger(Level.DEBUG)
            modules(
                listOf(
                    networkModule,
                    viewModelModule,
                )
            )
        }
    }

    companion object {
        var payaAppInstance: PayaAppClass? = null
        @JvmStatic
        fun getAppInstance(): PayaAppClass? {
            if (payaAppInstance == null) {
                payaAppInstance = PayaAppClass()
            }
            return payaAppInstance
        }
    }
}