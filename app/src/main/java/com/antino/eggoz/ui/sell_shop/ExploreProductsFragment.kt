package com.antino.eggoz.ui.sell_shop

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentExploreProductsBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.view.CustomAlertLoading

class ExploreProductsFragment(val context: MainActivity,val token:String,var id:Int?=null) : Fragment() {

    private lateinit var binding: FragmentExploreProductsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreProductsBinding.inflate(inflater, container, false)
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadSellShop()
                true
            } else false
        }

        init()
        if (id==null)
        loaditem()
        else
            loaditembyid()
        return binding.root
    }
    private fun init() {

        val gridLayoutManager = GridLayoutManager(activity, 2)
        binding.recycleviewExploreProduct.layoutManager = gridLayoutManager
        binding.recycleviewExploreProduct.itemAnimator = DefaultItemAnimator()
        binding.recycleviewExploreProduct.isNestedScrollingEnabled = false
    }

    private fun loaditembyid(){
        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getItemListbyid(
            token,id!!
        ).observe(context, androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.countval>0){
                binding.recycleviewExploreProduct.adapter = BuySellAdapter(context,it.results,false,"ExploreProducts")
            }
        }
        )


    }
    private fun loaditem() {


        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getItemList(
            token
        ).observe(context, androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.countval>0){
                binding.recycleviewExploreProduct.adapter = BuySellAdapter(context,it.results,false,"ExploreProducts")
            }
        }
        )


    }

}