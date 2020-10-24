package br.com.ricarlo.common.util.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.openBrowser(url: String?, flags: Int? = null) {
    if (url.isNullOrEmpty()) return
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        flags?.let { addFlags(it) }
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Context.openPlayStore() {
    // TODO review
//    val uri = Uri.parse("market://details?id=${packageName.removeApplicationSuffix()}")
//    val goToMarket = Intent(Intent.ACTION_VIEW, uri).addFlags(
//            Intent.FLAG_ACTIVITY_NO_HISTORY or
//                    Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or
//                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
//    )
//    try {
//        startActivity(goToMarket)
//    } catch (e: ActivityNotFoundException) {
//        openBrowser("https://play.google.com/store/apps/details?id=$appPackageName")
//    }
}