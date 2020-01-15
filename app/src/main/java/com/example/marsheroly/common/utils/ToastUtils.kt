package com.example.marsheroly.common.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import com.example.marsheroly.R
import kotlinx.android.synthetic.main.toast_error.view.*
import kotlinx.android.synthetic.main.toast_success.view.*

class ToastUtils {
    companion object {

        fun showToastError(message: String, context: Context) {
            val toastView = LayoutInflater.from(context).inflate(R.layout.toast_error, null)
            val toast = Toast(context)
            toastView.toast_error_text.text = message
            toastView.toast_error_close.setOnClickListener {
                toast.cancel()
            }
            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 25)
            toast.duration = Toast.LENGTH_LONG
            toast.view = toastView
            toast.show()
        }

        fun showToastSuccess(message: String, context: Context) {
            val toastView = LayoutInflater.from(context).inflate(R.layout.toast_success, null)
            val toast = Toast(context)
            toastView.toast_success_text.text = message
            toastView.toast_success_close.setOnClickListener {
                toast.cancel()
            }
            toast.setGravity(Gravity.TOP or Gravity.FILL_HORIZONTAL, 0, 25)
            toast.duration = Toast.LENGTH_SHORT
            toast.view = toastView
            toast.show()
        }

        fun showNoConnectionMessage(context: Context): Dialog {
            val messageDialog = Dialog(context)
            messageDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
            messageDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            messageDialog.setContentView(R.layout.message_no_connection)
            messageDialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            messageDialog.window?.setGravity(Gravity.TOP)
            messageDialog.setCancelable(false)
            messageDialog.show()
            return messageDialog
        }
    }
}