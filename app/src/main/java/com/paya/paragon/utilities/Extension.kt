package com.paya.paragon.utilities

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.paya.paragon.api.listLocProject.RegionDataChild
import timber.log.Timber


fun String?.formatToDouble() = this?.toDoubleOrNull() ?: 0.0

fun String?.formatToInt() = this?.toIntOrNull() ?: -1

fun Context.getDrawableRes(@DrawableRes resId: Int): Drawable? {
    return AppCompatResources.getDrawable(this, resId)
}

inline fun <T> surroundTryCatch(block: () -> T?): T? {
    return try {
        block()
    } catch (e: Exception) {
        Timber.e(e)
        FirebaseCrashlytics.getInstance().recordException(e)
        null
    }
}

inline fun <T> surroundTryCatch(block: () -> T?, exception: (e: Exception) -> Unit): T? {
    return try {
        block()
    } catch (e: Exception) {
        Timber.e(e)
        FirebaseCrashlytics.getInstance().recordException(e)
        exception(e)
        null
    }
}

fun Context.contactPaya() {
    Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse("tel:$PAYA_CONTACT_NO")
    }.let {
        startActivity(it)
    }
}

const val PAYA_CONTACT_NO = "+9647501297777"
fun Context.contactPayaWhatsApp() {
    val url = "https://api.whatsapp.com/send?phone=$PAYA_CONTACT_NO"
    surroundTryCatch({
        val i = Intent(Intent.ACTION_VIEW).apply {
            setPackage("com.whatsapp")
            data = Uri.parse(url)
        }
        if (i.resolveActivity(packageManager) != null) {
            startActivity(i)
        } else {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }, {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    })
}

fun String?.checkEquals(input: String?, ignoreCase: Boolean = true) =
    this?.trimIndent()?.equals(input, ignoreCase)


fun List<RegionDataChild>.containsOther() = this.any { it.originalText.contains("other", true) }

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}