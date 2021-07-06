package com.antino.eggoz.ui.sell_shop

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentSellShopBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.sell_shop.callback.Catcallback
import com.antino.eggoz.ui.sell_shop.callback.SellShopCallback
import com.antino.eggoz.view.CustomAlertLoading
import java.util.*

class SellShopFragment(private val callbacks: MainActivity, private val token: String) : Fragment(),
    SellShopCallback, Catcallback {

    private lateinit var sellShopViewModel: SellShopViewModel
    private lateinit var binding: FragmentSellShopBinding
    private lateinit var swipeTimer: Timer
    private lateinit var mcontext: Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sellShopViewModel =
            ViewModelProvider(this).get(SellShopViewModel::class.java)
        setHasOptionsMenu(true)
        binding = FragmentSellShopBinding.inflate(inflater, container, false)

        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                callbacks.loadHome()
                true
            } else false
        }


//        binding.shimmerViewContainer.stopShimmerAnimation()
//        binding.shimmerViewContainer.visibility = View.GONE
        binding.recycleSellShop.visibility = View.VISIBLE


        sliderBanner()
        categories()
        binding.txtExploreProd.setOnClickListener { callbacks.onclick(null) }
        buysellrecycle()

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_search).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun sliderBanner() {

        swipeTimer = Timer()
        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getBanner(
            token
        ).observe(callbacks, androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.errors == null) {
                if (it.count > 0) {

                    if (it.results != null && it.results!!.size != 0) {

                        val adapter = SliderAdapter(this, it.results)
                        binding.paggerSlider.adapter = adapter
                        binding.paggerSlider.offscreenPageLimit = 2

                        var pos = 0

                        swipeTimer.schedule(object : TimerTask() {
                            override fun run() {
                                Handler(Looper.getMainLooper()).postDelayed({
                                    if (pos >= adapter.size())
                                        pos = 0
                                    pos++
                                    Log.d("data", pos.toString() + " size" + adapter.size())
                                    binding.paggerSlider.setCurrentItem(pos, true)
                                }, 0)
                            }
                        }, 3000, 3000)

                    }else binding.paggerSlider.visibility=View.GONE
                }

            } else {
                Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
            }


        }
        )

    }

    private fun categories() {
        binding.recycleCategories.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.recycleCategories.itemAnimator = DefaultItemAnimator()
        binding.recycleCategories.isNestedScrollingEnabled = false

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getCatList(
            token
        ).observe(callbacks, androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.errors == null) {
                if (it.count > 0) {

                    val adapter = CategoriesAdapter(this, it.results)
                    binding.recycleCategories.adapter = adapter
                }

            } else {
                Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
            }


        }
        )
    }


    override fun onDestroyView() {

        swipeTimer.cancel()
        super.onDestroyView()
    }

    private fun buysellrecycle() {

        val gridLayoutManager = GridLayoutManager(activity, 2)
        binding.recycleSellShop.layoutManager = gridLayoutManager
        binding.recycleSellShop.itemAnimator = DefaultItemAnimator()
        binding.recycleSellShop.isNestedScrollingEnabled = false

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getItemList(
            token
        ).observe(callbacks, androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.errors == null) {
                if (it.countval > 0) {
                    binding.recycleSellShop.adapter =
                        BuySellAdapter(callbacks, it.results, true, "sellshop")
                }
            } else {
                Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
            }
        }
        )


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mcontext = context
    }

    override fun callback(id: Int) {
        callbacks.onclick(id)
    }
}