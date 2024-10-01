package com.larren.abertsonsexam.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.larren.abertsonsexam.databinding.DialogProgressLoadingBinding

object ProgressLoadingDialog {

    private var dialog: Dialog? = null

    fun show(context: Context) {
        if (dialog == null) {
            init(context = context)
            dialog?.window?.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        dialog?.setCancelable(false)
        dialog?.show()
    }

    fun hide() {
        dialog?.dismiss()
        dialog = null
    }

    private fun init(context: Context) {
        val binding = DialogProgressLoadingBinding.inflate(LayoutInflater.from(context))
        dialog = Dialog(context).apply {
            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                requestWindowFeature(Window.FEATURE_NO_TITLE)
            }
            with(binding) {
                setContentView(root)
            }
        }
    }
}