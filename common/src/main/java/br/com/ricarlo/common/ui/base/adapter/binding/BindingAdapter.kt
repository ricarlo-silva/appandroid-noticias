package br.com.ricarlo.common.ui.base.adapter.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import br.com.ricarlo.common.R
import br.com.ricarlo.common.util.ApsNoticiasUtils
import jp.wasabeef.glide.transformations.CropCircleTransformation

@BindingAdapter("imageUrl")
fun ImageView.imageUrl(url: String?) {
    if (url.isNullOrEmpty()) return

    Glide.with(context).load(url)
//            .placeholder(R.drawable.bg_photo)
//            .error(R.drawable.bg_photo)
            .crossFade()
            .bitmapTransform(CropCircleTransformation(context))
            .into(this)
}

@BindingAdapter("date")
fun TextView.date(value: String) {
    visibility = try {
        text = ApsNoticiasUtils.converteDatePost(value)
        View.VISIBLE
    } catch (e: Exception) {
        View.INVISIBLE
    }
}

@BindingAdapter("isGone")
fun View.bindIsGone(isGone: Boolean) {
    visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}