package com.example.automigration_roomdb.utils

import android.content.Context
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

object Utils {


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

        dialog.show()
    }
}