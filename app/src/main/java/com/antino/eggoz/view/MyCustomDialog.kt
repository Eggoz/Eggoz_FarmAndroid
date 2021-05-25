package com.antino.eggoz.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import com.antino.eggoz.R
import com.antino.eggoz.ui.daily_input.DailyInputDetailFragment
import com.antino.eggoz.ui.daily_input.DailyInputFragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MyCustomDialog(val mcontext:DailyInputDetailFragment,val medlist:ArrayList<String>,val medlistid: ArrayList<Int>) : DialogFragment() {
    private lateinit var edt_name:AutoCompleteTextView
    private lateinit var edt_Quantity: TextInputEditText
    private lateinit var edt_Quantity_layout: TextInputLayout
    private lateinit var edt_med_layout: TextInputLayout
    private var medid: Int?=null
    private var pos: Int?=null
    private var qunt: String?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        val vew:View= inflater.inflate(R.layout.dailyinput_dialog, container, false)
        val btn: CardView = vew.findViewById(R.id.btn_submit)
        val btn_close:ImageView=vew.findViewById(R.id.btn_close)
        edt_Quantity=vew.findViewById(R.id.edt_Quantity)
        edt_Quantity_layout=vew.findViewById(R.id.edt_Quantity_layout)
        val btn_delete: CardView = vew.findViewById(R.id.btn_delete)
        val btn_update: CardView = vew.findViewById(R.id.btn_update)

        btn.visibility=View.VISIBLE
        btn_delete.visibility=View.GONE
        btn_update.visibility=View.GONE


        btn_close.setOnClickListener { dismiss() }
        btn.setOnClickListener {
            mcontext.context.currentFocus?.let { view ->
                val imm =
                    mcontext.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            validate()
             }
        edt_name=vew.findViewById(R.id.edt_item)
        edt_med_layout=vew.findViewById(R.id.edt_item_title)


        edt_name.setOnItemClickListener { parent, view, position, id ->
            medid=medlistid[position]
            pos=position
        }
        dropdownsetup()

        return vew
    }
    private fun validate(){
        if (edt_Quantity.text!!.isEmpty()||medid==null) {
            if (edt_Quantity.text!!.isEmpty())
                edt_Quantity_layout.error = "Quantity can't be empty."
            else edt_Quantity_layout.isErrorEnabled = false
            if (medid == null)
                edt_med_layout.error = "Select some med frist"
            else edt_med_layout.isErrorEnabled = false
        }

        else{
            edt_Quantity_layout.isErrorEnabled=false
            edt_med_layout.isErrorEnabled = false
            dismiss()
            submit()
        }

    }
    private fun submit(){
        mcontext.medCallback(medid!!, pos!!, edt_Quantity.text.toString())

    }
    private fun dropdownsetup(){
        val adapter =  ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, medlist)
        edt_name.threshold=1
        edt_name.setAdapter(adapter)
        edt_name.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_color))
    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}