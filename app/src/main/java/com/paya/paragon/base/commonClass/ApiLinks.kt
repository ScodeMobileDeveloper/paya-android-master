package com.paya.paragon.base.commonClass

import org.koin.java.KoinJavaComponent.inject
import retrofit2.Retrofit

object ApiLinks {

    private val retrofit: Retrofit by inject(Retrofit::class.java)

    // Live
    const val CALCULATOR_URL = "https://www.paya-realestate.com/mobile/"

    const val BASE_URL_WEB_VIEW = "https://www.paya-realestate.com/en/calculator/appView/"

    const val BASE_URL_TERMS_AND_CONDITIONS = "https://www.paya-realestate.com/term-and-condition"

    const val BASE_URL_PRIVACY_POLICY = "https://www.paya-realestate.com/privacy-policy"

    const val BASE_URL_CONTACT_US = "https://www.paya-realestate.com/contact-us"

    @JvmStatic
    val client: Retrofit
        get() = retrofit
}