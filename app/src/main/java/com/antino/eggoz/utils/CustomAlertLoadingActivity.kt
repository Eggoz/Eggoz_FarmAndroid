package com.antino.eggoz.utils

import android.app.Activity
import android.app.AlertDialog
import com.antino.eggoz.R

class CustomAlertLoadingActivity(
    private var activity: Activity
) {

    lateinit var dialog: AlertDialog


    fun stateLoading(){
        val builder= AlertDialog.Builder(activity)
        val inflater=activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.customdialogloading,null))
        builder.setCancelable(false)

        dialog=builder.create()
        dialog.show()

    }
    fun dismiss(){
        dialog.dismiss()
    }

}