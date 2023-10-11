package com.example.automigration_roomdb.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

object Utils {

    fun hideKeyboard(activity: Activity) {
        try {
            val inputManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            activity.currentFocus?.let {
                inputManager.hideSoftInputFromWindow(
                    activity.currentFocus!!.windowToken,
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        } catch (e: Exception) {
        }
    }

    fun showKeyboard(view: View, context: Context) {
        view.requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    fun showEditTextDialog(
        context: Context,
        title: String,
        positiveButtonText: String,
        negativeButtonText: String,
        userName: String,
        onPositiveButtonClick: (String) -> Unit,
    ) {
        val editText = EditText(context)
        editText.setText(userName)

        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setView(editText)
            .setPositiveButton(positiveButtonText) { dialog, which ->
                val enteredText = editText.text.toString()
                onPositiveButtonClick(enteredText)
                dialog.dismiss()
            }
            .setNegativeButton(negativeButtonText) { dialog, which ->
                dialog.cancel()
            }
            .create()
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        dialog.show()
    }
}