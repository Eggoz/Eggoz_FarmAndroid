package com.antino.eggoz.ui.activity_log

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentActivityLogBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.view.CustomAlertLoading


class ActivityLog(val token:String,val mid:Int) : Fragment() {
    private lateinit var binding: FragmentActivityLogBinding
    val id:ArrayList<Int> = ArrayList()
    val date:ArrayList<String> = ArrayList()
    val quantity:ArrayList<Int> = ArrayList()
    val amount:ArrayList<Double> = ArrayList()
    val remark:ArrayList<String> = ArrayList()
    val farmerid:ArrayList<Int> = ArrayList()
    val partyid:ArrayList<Int> = ArrayList()
    val productSubDivision:ArrayList<Int> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentActivityLogBinding.inflate(inflater, container, false)

        val option = arrayOf("Sale", "Expense","Daily")
        binding.spinnerActivityLog.adapter = context?.applicationContext?.let {
            ArrayAdapter<String>(
                it, R.layout.spinner_text, option
            )
        }
        binding.spinnerActivityLog.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener,
                AdapterView.OnItemClickListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d("data", "$position")
                    if (position==1){
                        getExpense()
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {


                }

                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }


            }




        return binding.root
    }

    private fun loaddata(){
        binding.recycleViewActivityLog.layoutManager = LinearLayoutManager(context)
        binding.recycleViewActivityLog.itemAnimator = DefaultItemAnimator()
        binding.recycleViewActivityLog.isNestedScrollingEnabled = false
        binding.recycleViewActivityLog.adapter= ActivityLogAdapter(  id,
                date,
                quantity,
                amount,
                remark,
                farmerid,
                partyid,
                productSubDivision)

    }

    private fun getExpense(){

        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getExpense(
            token,mid
        ).observe(this,
            Observer {
                loadingdialog.dismiss()
                Log.d("data","${it.toString()}")
                if (it.count>0){
                    for (i in it.results.indices){
                        id.add(it.results[i].id)
                        date.add(it.results[i].date)
                        quantity.add(it.results[i].quantity)
                        amount.add(it.results[i].amount)
                        remark.add(it.results[i].remark)
                        farmerid.add(it.results[i].farmer)
                        partyid.add(it.results[i].party)
                        productSubDivision.add(it.results[i].productSubDivision)
                    }
                    loaddata()



                }else{
                    Toast.makeText(context,"val not greater then 0", Toast.LENGTH_SHORT).show()

                }

            }
        )
    }

}