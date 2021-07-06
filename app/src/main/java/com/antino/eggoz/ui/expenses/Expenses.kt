package com.antino.eggoz.ui.expenses

import android.R
import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentExpensesBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.view.CustomAlertLoading
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import kotlin.collections.ArrayList


class Expenses(val mcontext:MainActivity,val token:String,val mid:Int) : Fragment() {
    private lateinit var binding: FragmentExpensesBinding
    val partynamelist: ArrayList<String> = ArrayList()
    val partynamelistId: ArrayList<Int> = ArrayList()
    val Itemdivisionlist: ArrayList<String> = ArrayList()
    val Itemdivisionlistid: ArrayList<Int> = ArrayList()
    val Itemsubdivisionlist: ArrayList<String> = ArrayList()
    val Itemsubdivisionlistid: ArrayList<Int> = ArrayList()

    var partynameid = 0
    var divisionid = 0
    var sub_divisionid = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExpensesBinding.inflate(inflater, container, false)

        loadPart()
        loaddivision()
        init()
        dropdownpartyname()
        dropdownItemname()
        init()
        binding.btnSubmit.setOnClickListener {
            validate()
        }
        return binding.root
    }
    private fun loaddivision(){
        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getdivision(
            token
        ).observe(mcontext,
            Observer {
                loadingdialog.dismiss()
                Log.d("data","${it.toString()}")
                if (it.count>0){
                    for (i in it.results.indices){
                        Itemdivisionlist.add(it.results[i].name)
                        Itemdivisionlistid.add(it.results[i].id)
                    }

                    dropdownItemTypename()


                }else{

                }

            }
        )

    }
    private  fun loadsubdivision(){
        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getsubdivision(
            token,divisionid
        ).observe(mcontext,
            Observer {
                loadingdialog.dismiss()
                Log.d("data","${it.toString()}")
                if (it.count>0){
                    for (i in it.results.indices){
                        Itemsubdivisionlist.add(it.results[i].name)
                        Itemsubdivisionlistid.add(it.results[i].id)
                    }
                }else{

                }

            }
        )

    }
    private fun loadPart(){
        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getParty(
            token
        ).observe(mcontext,
            Observer {
                loadingdialog.dismiss()
                Log.d("data","${it.toString()}")
                if (it.count>0){
                    for (i in it.results.indices){
                        partynamelist.add(it.results[i].name)
                        partynamelistId.add(it.results[i].id)
                    }



                }else{

                }

            }
        )
    }

    private fun init() {
        binding.edtParty.setOnItemClickListener { parent, view, position, id ->
            partynameid = partynamelistId[position]
        }
        binding.edtItemType.setOnItemClickListener { parent, view, position, id ->
            divisionid = Itemdivisionlistid[position]
            loadsubdivision()
        }
        binding.edtItem.setOnItemClickListener { parent, view, position, id ->
            sub_divisionid = Itemsubdivisionlistid[position]
        }

        binding.edtSelectDaysLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
        binding.edtSelectDay.keyListener = null
        binding.edtSelectDaysLayout.setEndIconDrawable(com.antino.eggoz.R.drawable.ic_baseline_calendar_today_24)
        binding.edtSelectDaysLayout.setEndIconOnClickListener {
            closeKeyboard()
            datePicker()
        }
        binding.edtSelectDay.setOnClickListener {
            closeKeyboard()
            datePicker()
        }
        binding.edtSelectDaysLayout.setOnClickListener {
            closeKeyboard()
            datePicker()
        }

    }
    private fun closeKeyboard() {
        val imm =
            mcontext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    @SuppressLint("SetTextI18n")
    private fun datePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            requireContext(),
            com.antino.eggoz.R.style.DialogTheme,
            { view, year, monthOfYear, dayOfMonth ->
                binding.edtSelectDay.setText("$year-$monthOfYear-$dayOfMonth")
            },
            year,
            month,
            day
        )
        dpd.show()
        dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), com.antino.eggoz.R.color.app_color))
        dpd.getButton(DatePickerDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), com.antino.eggoz.R.color.app_color))
    }

    private fun validate() {
        if (binding.edtSelectDay.text!!.isEmpty() || binding.edtQuantity.text!!.isEmpty() || binding.edtTotalAmount.text!!.isEmpty() ||
            binding.edtRemark.text!!.isEmpty() || partynameid == 0 || divisionid == 0 || sub_divisionid == 0
        ) {
            if (partynameid == 0) binding.edtPartyLayout.error = "Select a party "
            else binding.edtPartyLayout.isErrorEnabled = false
            if (divisionid == 0) binding.edtItemTypeLayout.error = "Select ItemType"
            else binding.edtItemLayout.isErrorEnabled = false
            if (sub_divisionid == 0) binding.edtItemLayout.error = "Select Item"
            else binding.edtItemLayout.isErrorEnabled = false
            if (binding.edtSelectDay.text!!.isEmpty()) binding.edtSelectDaysLayout.error =
                "Select a date to proceed"
            else binding.edtSelectDaysLayout.isErrorEnabled = false
            if (binding.edtQuantity.text!!.isEmpty()) binding.edtQuantityLayout.error =
                "Enter some Quantity"
            else binding.edtQuantityLayout.isErrorEnabled = false
            if (binding.edtTotalAmount.text!!.isEmpty()) binding.edtTotalAmountLayout.error =
                "Enter Some Amount"
            else binding.edtTotalAmountLayout.isErrorEnabled = false
            if (binding.edtRemark.text!!.isEmpty()) binding.edtRemarkLayout.error =
                "Enter some Remark"
            else binding.edtRemarkLayout.isErrorEnabled = false
        } else {
            binding.edtSelectDaysLayout.isErrorEnabled = false
            binding.edtQuantityLayout.isErrorEnabled = false
            binding.edtTotalAmountLayout.isErrorEnabled = false
            binding.edtRemarkLayout.isErrorEnabled = false
            submit()
        }
    }

    private fun submit() {

        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.expenses(
            token,mid,binding.edtSelectDay.text.toString(),partynameid,sub_divisionid,binding.edtQuantity.text.toString(),
            binding.edtTotalAmount.text.toString(),binding.edtRemark.text.toString()
        ).observe(mcontext,
            Observer {
                loadingdialog.dismiss()

                Log.d("data","$it")

                if (it?.errors==null){
                    partynameid=0
                    sub_divisionid=0
                    divisionid=0
                    binding.edtQuantity.setText("")
                    binding.edtTotalAmount.setText("")
                    binding.edtRemark.setText("")
                    binding.edtSelectDay.setText("")

                    binding.edtParty.setText("")
                    binding.edtItemType.setText("")
                    binding.edtItem.setText("")


                    Toast.makeText(mcontext, "Expenses updated",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(mcontext, it.errors!![0].message,Toast.LENGTH_SHORT).show()
                    Log.d("data","${it.errors!![0].message}")
                }

            }
        )

    }

    private fun dropdownpartyname() {
        val adapter = ArrayAdapter(requireContext(), R.layout.select_dialog_item, partynamelist)
        binding.edtParty.threshold = 1
        binding.edtParty.setAdapter(adapter)
        binding.edtParty.keyListener = null
        binding.edtParty.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                com.antino.eggoz.R.color.app_color
            )
        )
    }

    private fun dropdownItemTypename() {
        val adapter = ArrayAdapter(requireContext(), R.layout.select_dialog_item, Itemdivisionlist)
        binding.edtItemType.threshold = 1
        binding.edtItemType.setAdapter(adapter)
        binding.edtItemType.keyListener = null
        binding.edtItemType.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                com.antino.eggoz.R.color.app_color
            )
        )
    }

    private fun dropdownItemname() {
        val adapter = ArrayAdapter(requireContext(), R.layout.select_dialog_item, Itemsubdivisionlist)
        binding.edtItem.threshold = 1
        binding.edtItem.setAdapter(adapter)
        binding.edtItem.keyListener = null
        binding.edtItem.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                com.antino.eggoz.R.color.app_color
            )
        )
    }


}