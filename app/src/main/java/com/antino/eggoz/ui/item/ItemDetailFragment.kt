package com.antino.eggoz.ui.item

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentItemDetailBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.room.CartDao
import com.antino.eggoz.room.MyDatabase
import com.antino.eggoz.room.RoomCart
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.PrefrenceUtils
import com.antino.eggoz.view.CustomAlertLoading
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemDetailFragment(var id: String, var mcontext: MainActivity, var from: String) :
    Fragment() {

    private lateinit var binding: FragmentItemDetailBinding
    var token: String = ""


    private var des: String = ""

    private lateinit var database: MyDatabase
    private lateinit var itemDetail: com.antino.eggoz.ui.sell_shop.model.ItemDetail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemDetailBinding.inflate(inflater, container, false)

        token = PrefrenceUtils.retriveData(
            activity?.applicationContext!!,
            Constants.ACCESS_TOKEN_PREFERENCE
        )!!


        if (token != "")
            loadItemDetail()
        else
            binding.conMain.visibility = View.VISIBLE



        clickevent()


        return binding.root
    }

    private fun clickevent() {
        database = Room.databaseBuilder(
            activity?.applicationContext!!,
            MyDatabase::class.java,
            Constants.DB_NAME
        ).allowMainThreadQueries().build()
        binding.imgBack.setOnClickListener {
            if (from == "home")
                mcontext.loadHome()
            else
                mcontext.loadDetailFragback(from)

        }
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                if (from == "home")
                    mcontext.loadHome()
                else
                    mcontext.loadSellShop()
                true
            } else false
        }

        binding.btnPaynow.setOnClickListener {

            mcontext.loadBuyFragment(
                "Buy Now", itemDetail.id,
                itemDetail.name, itemDetail.feedProductImage, itemDetail.currentPriceval,
                1, des, itemDetail.chargeTaxes
            )

        }

        binding.imgCart.setOnClickListener {
            mcontext.loadCart()
        }

        binding.btnAddToCart.setOnClickListener {

            if (itemDetail != null) {
                val CartDao: CartDao = database.deatailcart()
                val cart = RoomCart(
                    itemDetail.id,
                    itemDetail.name,
                    itemDetail.feedProductImage,
                    itemDetail.currentPriceval,
                    1,
                    des,
                    itemDetail.chargeTaxes
                )
                //save
                lifecycleScope.launch(Dispatchers.IO) {
                    CartDao.insertAll(cart)
                }
                Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun loadItemDetail() {

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getItemDetail(
            token, id
        ).observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            loadingdialog.dismiss()

            if (it.errors == null) {
                itemDetail = it
                if (it.feedProductDivision.isVisible) {
                    binding.txtItemDetailTitle.text = it.name
                    binding.txtItemPrice.text = "Rs. ${it.currentPriceval}"
                    if (it.feedProductSpecifications != null)
                        for (i in 0 until it.feedProductSpecifications?.size!!)
                            des = "$des. ${it.feedProductSpecifications!![i].specification}"
                    binding.txtItemDetailDes.text = des
                    Glide.with(this)
                        .asBitmap()
                        .load(it.feedProductImage)
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.logo1)
                        .into(binding.imgItemDetail)


                    if (!it.isAvailable) {
                        binding.btnPaynow.isEnabled = false
                        binding.btnAddToCart.isEnabled = false
                        binding.btnPaynow.isClickable = false
                        binding.btnAddToCart.isClickable = false
                    }
                } else
                    binding.conMain.visibility = View.GONE

            } else {
                Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                binding.conMain.visibility = View.GONE
            }
        }
        )
    }
}