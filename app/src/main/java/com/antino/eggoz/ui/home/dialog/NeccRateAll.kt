package com.antino.eggoz.ui.home.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.antino.eggoz.R
import com.antino.eggoz.ui.faqs.FaqsAdapter
import com.antino.eggoz.ui.home.adapter.NeccAdapter
import com.antino.eggoz.ui.home.model.NeccRate
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class NeccRateAll(
    private var activity: Fragment,
    var results: List<NeccRate.Result>? = null
): DialogFragment() {
    private lateinit var edt_name: AutoCompleteTextView
    private lateinit var edt_Quantity: TextInputEditText
    private lateinit var edt_Quantity_layout: TextInputLayout
    private lateinit var edt_med_layout: TextInputLayout
    private var qunt: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        val vew: View = inflater.inflate(R.layout.list_necc, container, false)
        val recycle_necc: RecyclerView = vew.findViewById(R.id.recycle_necc)
        val btn_close: ImageView = vew.findViewById(R.id.btn_close)

        btn_close.setOnClickListener { dialog?.cancel() }


        recycle_necc.layoutManager = LinearLayoutManager(context)
        recycle_necc.itemAnimator = DefaultItemAnimator()
        recycle_necc.isNestedScrollingEnabled = false
        recycle_necc.adapter= NeccAdapter(results)



        return vew
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}