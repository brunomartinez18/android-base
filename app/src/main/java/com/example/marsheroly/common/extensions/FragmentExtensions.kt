package com.example.marsheroly.common.extensions

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.marsheroly.R
import com.example.marsheroly.common.utils.ToastUtils

fun Fragment.comingSoon() {
    ToastUtils.showToastError("COMING SOON", this.context!!)
}

/**
 * COLORS:
 * 0 -> gery_primary
 * 1 -> white
 * default -> gery_primary
 */
fun Fragment.showProgressBar(color: Int = 0): Dialog {
    val resource = when (color) {
        0 -> R.layout.progress_bar_primary
        1 -> R.layout.progress_bar_white
        else -> R.layout.progress_bar_primary
    }
    val progressBar = layoutInflater.inflate(resource, null)
    val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
    val loadingDialog = Dialog(context!!)
    loadingDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
    loadingDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    loadingDialog.addContentView(progressBar, params)
    loadingDialog.setCancelable(false)
    if (isAdded) {
        loadingDialog.show()
    }
    return loadingDialog
}