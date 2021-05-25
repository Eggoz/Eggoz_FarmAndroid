package com.antino.eggoz.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.antino.eggoz.R
import com.antino.eggoz.databinding.AddotheritemDialogBinding

class UpdateOtherItemDialog(private val pos :Int, private val callbacks: ScheduleDetailFragment,private val product:String,
                            private val quantity:String,private val rates:String,
                            private val totalamt:String,private val remark:String) : DialogFragment(),AdditemDialog {

    private lateinit var edt_name: AutoCompleteTextView
    private lateinit var binding: AddotheritemDialogBinding
    private val name:ArrayList<String> = ArrayList()
    private var productloc=0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        binding = AddotheritemDialogBinding.inflate(inflater, container, false)

        binding.txtTitle.text ="Update"
        binding.btnDelete.visibility =View.VISIBLE

        binding.edtOtherProduct.setText(product)
        binding.edtQuantity.setText(quantity)
        binding.edtRate.setText(rates)
        binding.edtTotalAmount.setText(totalamt)
        binding.edtRemark.setText(remark)

        dropdownsetup()
        binding.edtOtherProduct.setOnItemClickListener { parent, view, position, id ->
            productloc=position
        }

        binding.btnSubmit.setOnClickListener {
            callbacks.updateclick(name[productloc], pos,
                binding.edtQuantity.text.toString(), binding.edtRate.text.toString(),
                binding.edtTotalAmount.text.toString(), binding.edtRemark.text.toString()
            )
            dismiss()
        }
        binding.btnDelete.setOnClickListener {
            callbacks.delete(pos)
            dismiss()
        }

        binding.btnClose.setOnClickListener{
            dismiss()
        }

        return binding.root
    }
    private fun dropdownsetup(){
        name.add("eggoz")
        name.add("white egg")
        name.add("brown egg")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, name)
        binding.edtOtherProduct.threshold=1
        binding.edtOtherProduct.setAdapter(adapter)
        binding.edtOtherProduct.keyListener=null
        binding.edtOtherProduct.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.app_color
            )
        )
    }

    private fun validate(){
        if (binding.edtOtherProduct.text!!.isEmpty()||
            binding.edtQuantity.text!!.isEmpty()||binding.edtRate.text!!.isEmpty()||
            binding.edtTotalAmount.text!!.isEmpty()||binding.edtRemark.text!!.isEmpty()){
            if (binding.edtOtherProduct.text!!.isEmpty()) binding.edtOtherProductLayout.error="Please enter valid Other Product"
            else binding.edtOtherProductLayout.isErrorEnabled=false

            if (binding.edtQuantity.text!!.isEmpty()) binding.edtQuantityLayout.error="Please enter valid Quantity"
            else binding.edtQuantityLayout.isErrorEnabled=false

            if (binding.edtRate.text!!.isEmpty()) binding.edtRateLayout.error="Please enter valid Rate"
            else binding.edtRateLayout.isErrorEnabled=false


            if (binding.edtTotalAmount.text!!.isEmpty()) binding.edtTotalAmountLayout.error="Please enter valid Total Amount"
            else binding.edtTotalAmountLayout.isErrorEnabled=false

            if (binding.edtRemark.text!!.isEmpty()) binding.edtRemarkLayout.error="Please enter Remark"
            else binding.edtRemarkLayout.isErrorEnabled=false
        }else{

            binding.edtOtherProductLayout.isErrorEnabled=false
            binding.edtQuantityLayout.isErrorEnabled=false
            binding.edtRateLayout.isErrorEnabled=false
            binding.edtTotalAmountLayout.isErrorEnabled=false
            binding.edtRemarkLayout.isErrorEnabled=false
            submit()
        }
    }


    private fun submit() {
        TODO("Not yet implemented")
    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}