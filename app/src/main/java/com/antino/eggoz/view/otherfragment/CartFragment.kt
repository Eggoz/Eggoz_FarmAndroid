package com.antino.eggoz.view.otherfragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentCartBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.room.CartDao
import com.antino.eggoz.room.MyDatabase
import com.antino.eggoz.room.RoomCart
import com.antino.eggoz.ui.item.CartLargelistAdapter
import com.antino.eggoz.ui.sell_shop.BuySellAdapter
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.view.CustomAlertLoading

class CartFragment(var token:String,var mcontext:MainActivity) : Fragment() {

    private lateinit var database: MyDatabase
    private lateinit var cartdao:CartDao
    private lateinit var cart:List<RoomCart>


    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentCartBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        init()
        cartlist()
        explorelist()
        return binding.root
    }

    private fun init(){

        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                mcontext.loadHome()
                true
            } else false
        }


        binding.btnProceedBuy.setOnClickListener {
            mcontext.loadBuyFragment("Cart",-1,"","","",-1,"",
            false)
        }
    }

    private fun cartlist(){

        database= Room.databaseBuilder(requireContext(),MyDatabase::class.java, Constants.DB_NAME).allowMainThreadQueries().build()
        cartdao=database.deatailcart()
        cart=cartdao.getAll()

        if (cart.isEmpty())
            binding.btnProceedBuy.visibility=View.GONE

        binding.recycleViewCartItemList.layoutManager = LinearLayoutManager(context)
        binding.recycleViewCartItemList.itemAnimator = DefaultItemAnimator()
        binding.recycleViewCartItemList.isNestedScrollingEnabled = false
        val adapter= CartLargelistAdapter(cart,cartdao,mcontext)
        binding.recycleViewCartItemList.adapter=adapter

    }
    private fun explorelist(){

        val gridLayoutManager = GridLayoutManager(activity, 2)
        binding.recycleviewExplore.layoutManager = gridLayoutManager
        binding.recycleviewExplore.itemAnimator = DefaultItemAnimator()
        binding.recycleviewExplore.isNestedScrollingEnabled = false

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getItemList(
            token
        ).observe(requireActivity(), androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.errors == null) {
                if (it.countval > 0) {
                    binding.recycleviewExplore.adapter = BuySellAdapter(mcontext,it.results,false,"cart")
                }
            } else {
                Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
            }
        }
        )


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.nav_top_My_Cart).isVisible =false
        super.onCreateOptionsMenu(menu, inflater)
    }
}