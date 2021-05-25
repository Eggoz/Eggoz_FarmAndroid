package com.antino.eggoz.ui.Buy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentBuyBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.room.CartDao
import com.antino.eggoz.room.MyDatabase
import com.antino.eggoz.room.RoomCart
import com.antino.eggoz.ui.Buy.callback.BuyAddUpdate
import com.antino.eggoz.ui.Buy.dialog.Messagedialog
import com.antino.eggoz.ui.item.CartLargelistAdapter
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.PrefrenceUtils
import com.antino.eggoz.view.CustomAlertLoading
import com.antino.eggoz.view.adapter.address_list_adapter
import com.antino.eggoz.view.adapter.item_list_adapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BuyFragment(
    var mcontext: MainActivity, var token: String,
    var mid: Int,
    var from: String,
    var mmid: Int,
    var name: String,
    var img: String,
    var price: String,
    var qnt: Int,
    var des: String,
    var tax: Boolean,
    var farmerid: Int

) : Fragment(), BuyAddUpdate {


    private lateinit var database: MyDatabase
    private lateinit var cartdao: CartDao
    private lateinit var cart: List<RoomCart>
    private var add_id: Int = -1


    private lateinit var binding: FragmentBuyBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentBuyBinding.inflate(inflater, container, false)

        init()

        addresslist()

        return binding.root
    }

    private fun init() {

        farmerid =
            PrefrenceUtils.retriveData(activity?.baseContext!!, Constants.ID).toString().toInt()


        if (from == "Buy Now")
            itemlist()
        else
            cartlist()

        binding.txtAddressAdd.setOnClickListener {
            Log.d("data", "txtAddressAdd")
            mcontext.loadAddAddress(
                from,
                mid,
                name,
                img,
                price,
                qnt,
                des,
                tax
            )
        }

        binding.llAddQuan.setOnClickListener { activity?.onBackPressed() }
    }

    override fun updateAdd(id: Int) {
        add_id = id
        Log.d("data", "add id $add_id")
    }


    private fun payBuynow() {


        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.payBuynow(
            token,
            Integer.parseInt(
                PrefrenceUtils.retriveData(
                    activity?.applicationContext!!,
                    Constants.ID
                )!!
            ),
            mid,
            qnt,
            price,
            add_id
        ).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.errors == null) {
                /*Toast.makeText(context, it.success, Toast.LENGTH_SHORT).show()
                mcontext.loadSellShop()*/
                Messagedialog("Thank You " +
                        "Your enquiry has reached us our team will get back to soon", mcontext).show(
                    mcontext.supportFragmentManager,
                    "MyCustomFragment2"
                )
            } else
                Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT)
                    .show()
        }
        )
    }

    private fun cartBuy() {

        cartlist()
        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.cartBuy(
            token,
            Integer.parseInt(
                PrefrenceUtils.retriveData(
                    activity?.applicationContext!!,
                    Constants.ID
                )!!
            ),
            cart,
            add_id
        ).observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.errors == null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    cartdao.deletecart()
                }
//                mcontext.loadSellShop()
                Messagedialog("Thank You " +
                        "Your enquiry has reached us our team will get back to soon", mcontext).show(
                    mcontext.supportFragmentManager,
                    "MyCustomFragment2"
                )
            } else
                Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT)
                    .show()
        }
        )

    }

    private fun itemlist() {

        binding.recycleViewItemPaymentItemList.layoutManager = LinearLayoutManager(context)
        binding.recycleViewItemPaymentItemList.itemAnimator = DefaultItemAnimator()
        binding.recycleViewItemPaymentItemList.isNestedScrollingEnabled = false
        binding.recycleViewItemPaymentItemList.adapter = item_list_adapter(
            mmid,
            name, img, price, qnt, des, tax
        )
        binding.btnPaynow.setOnClickListener {
            if (add_id != -1)
                payBuynow()
            else
                Toast.makeText(context, "No Address Found", Toast.LENGTH_SHORT).show()
        }

    }

    private fun cartlist() {

        database = Room.databaseBuilder(mcontext, MyDatabase::class.java, Constants.DB_NAME)
            .allowMainThreadQueries().build()
        cartdao = database.deatailcart()
        cart = cartdao.getAll()

        if (cart.isEmpty()) {
            binding.dataLayoutData.visibility = View.GONE
            binding.conEmpty.visibility = View.VISIBLE
        }

        binding.recycleViewItemPaymentItemList.layoutManager = LinearLayoutManager(context)
        binding.recycleViewItemPaymentItemList.itemAnimator = DefaultItemAnimator()
        binding.recycleViewItemPaymentItemList.isNestedScrollingEnabled = false
        binding.recycleViewItemPaymentItemList.adapter = CartLargelistAdapter(cart, cartdao)

        binding.btnPaynow.setOnClickListener {
            if (add_id != -1)
                cartBuy()
            else
                Toast.makeText(context, "No Address Found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addresslist() {

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.farmerProfile(
            token, farmerid
        ).observe(viewLifecycleOwner,
            Observer {
                loadingdialog.dismiss()
                if (it.errors == null) {
                    binding.recycleViewItemPaymentAddressList.removeAllViews()
                    binding.recycleViewItemPaymentAddressList.layoutManager =
                        LinearLayoutManager(context)
                    binding.recycleViewItemPaymentAddressList.itemAnimator = DefaultItemAnimator()
                    binding.recycleViewItemPaymentAddressList.isNestedScrollingEnabled = false

                    var adapter = address_list_adapter(this, it.farmer.addresses!!)

                    binding.recycleViewItemPaymentAddressList.adapter = adapter

                    if (it.farmer.addresses!!.isNotEmpty()) {
                        add_id = it.farmer.addresses!![0].id
                        Log.d("data", "add id $add_id")
                    }

                } else {
                    Toast.makeText(context, "${it.errors!![0].message}", Toast.LENGTH_SHORT).show()
                }
            }

        )


    }

}