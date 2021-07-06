package com.antino.eggoz.ui.daily_input

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentDailyInputListBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.daily_input.adapter.DailyInputAdapter
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.PrefrenceUtils
import com.antino.eggoz.view.CustomAlertLoading

class DailyInputListFragment(val mcontext:MainActivity,val ids:Int) : Fragment() {
    private lateinit var binding: FragmentDailyInputListBinding
    private lateinit var token:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyInputListBinding.inflate(inflater, container, false)

        token = PrefrenceUtils.retriveData(requireContext(), Constants.ACCESS_TOKEN_PREFERENCE)!!
        initView()
        getDailyInput()

        return binding.root
    }

    private fun initView() {
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                mcontext.loadDailyInput()
                true
            } else false
        }
    }

    private fun getDailyInput() {
        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        Log.d("data","comment id $id")
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getDailyInput(
            token,ids
        ).observe(requireActivity(),
            Observer {
                loadingdialog.dismiss()
                if (it.errors==null){

                    Log.d("data","daily input list ${it.results?.size!!}")
                    binding.recycleDailYInput.layoutManager = LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    binding.recycleDailYInput.setHasFixedSize(true)
                    val adapter = DailyInputAdapter(mcontext, it.results)
                    binding.recycleDailYInput.adapter = adapter
                }else{
                    Toast.makeText(context,it.errors!![0].message, Toast.LENGTH_SHORT).show()
                }


            }
        )

    }
}