package com.irfanirawansukirman.core.util.extension

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity

inline fun <reified T : Any> AppCompatActivity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)
    if (value is T) value else default
}

fun AppCompatActivity.navigationToBrowser(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

inline fun AppCompatActivity.navigationForPackage(
    packageName: String,
    isFinishPage: Boolean = false,
    exception: (ClassNotFoundException) -> Unit
) {
    try {
        val intent = Intent(this, Class.forName(packageName))
        startActivity(intent)
        if (isFinishPage) finish()
    } catch (e: ClassNotFoundException) {
        exception(e)
    }
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.navigation(withFinish: Boolean = false) {
    navigation<T>(withFinish = withFinish) {}
}

inline fun <reified T : AppCompatActivity> AppCompatActivity.navigation(
    withFinish: Boolean = false,
    requestCode: Int = 0,
    intentParams: Intent.() -> Unit
) {
    val intent = Intent(this, T::class.java)
    intent.intentParams()
    if (requestCode != 0) startActivityForResult(intent, requestCode) else startActivity(intent)
    if (withFinish) finish()
}