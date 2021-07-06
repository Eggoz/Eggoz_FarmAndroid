package com.antino.eggoz.ui.daily_input

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentDailyInputBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.profile.adapter.FarmAdapter
import com.antino.eggoz.view.CustomAlertLoading


class DailyInputFragment(
    private val context: MainActivity,
    private val token: String,
    private val mid: Int
) : Fragment() {
    private lateinit var dailyInputViewModel: DailyInputViewModel
    private lateinit var binding: FragmentDailyInputBinding
    private val farmname: ArrayList<String> = ArrayList()
    private val shedname: ArrayList<String> = ArrayList()
    private val flock: ArrayList<String> = ArrayList()
    private val lastupdate: ArrayList<String> = ArrayList()
    private val breedName: ArrayList<String> = ArrayList()
    private val flockid: ArrayList<Int> = ArrayList()
    private val flockactivebird: ArrayList<Int> = ArrayList()
    private var farmloc = 0
    private var shedloc = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dailyInputViewModel =
            ViewModelProvider(requireActivity()).get(DailyInputViewModel::class.java)
        setHasOptionsMenu(true)

        binding = FragmentDailyInputBinding.inflate(inflater, container, false)

        Log.d("data","DailyInputFragment")

        init()
        farmData()
        return binding.root
    }

    private fun init() {
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadHome()
                true
            } else false
        }


        binding.btnAddFlocks.setOnClickListener {
//            context.loadProfile()
            context.addFarm()
        }

        binding.txtAddMore.setOnClickListener { context.addFarm() }
    }
    private fun farmData(){
        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        Log.d("data","token:$token mid:$mid")
        viewModel.getFarm(
            token, mid
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                if (it.count == null) {
                    binding.conAddflock.visibility = View.VISIBLE
                    binding.conloadData.visibility = View.GONE


                    if (it.errors?.get(0)?.message != null || it.errors?.get(0)?.message != "") {

                        Log.d("data", "get farm error ${it.errors!![0].message}")
                    } else
                        Log.d("data", "farm data ${it.results?.size} count ${it.count}")

                } else {

                    Log.d("data", "count:- ${it.count}")

                    if (it?.count!! > 0) {


                        binding.conAddflock.visibility = View.GONE
                        binding.conloadData.visibility = View.VISIBLE



                        if (it.results?.size!!>0) {
                            binding.conloadData.visibility = View.VISIBLE

                            binding.recycleviewFarmDetail.layoutManager = LinearLayoutManager(
                                activity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.recycleviewFarmDetail.setHasFixedSize(true)
                            val adapter = FarmAdapter(context, it.results)
                            binding.recycleviewFarmDetail.adapter = adapter
                        } else {
                            binding.txtFarmDetails.visibility = View.GONE
                            binding.txtAddMore.visibility = View.GONE
                        }

                    }
                    else{
                        binding.conAddflock.visibility = View.VISIBLE
                        binding.conloadData.visibility = View.GONE
                    }
                }

            }
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(com.antino.eggoz.R.id.action_search).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

}