package com.antino.eggoz.view

import android.app.AlertDialog
import androidx.fragment.app.Fragment
import com.antino.eggoz.R

class CustomAlertLoading(
    private var activity: Fragment
) {

    lateinit var dialog: AlertDialog


    fun stateLoading() {
        if (activity.context != null) {
            val builder = AlertDialog.Builder(activity.context)
            val inflater = activity.layoutInflater
            builder.setView(inflater.inflate(R.layout.customdialogloading, null))
            builder.setCancelable(false)

            dialog = builder.create()
            dialog.show()
        }


    }

    fun dismiss() {
        dialog.dismiss()
    }

}