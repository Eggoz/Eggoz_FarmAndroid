package com.antino.eggoz.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.ActivitySignup4DeatailsBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.CustomAlertLoadingActivity
import com.antino.eggoz.utils.PrefrenceUtils
import kotlin.properties.Delegates


class Signup5deatails : AppCompatActivity() {
    private lateinit var binding: ActivitySignup4DeatailsBinding
    private lateinit var token: String
    private lateinit var id: String
    private var pos by Delegates.notNull<Int>()
    private var zone_id:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignup4DeatailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

/*
        token = intent.getStringExtra("token").toString()
        id = intent.getStringExtra("id").toString()*/
        token=PrefrenceUtils.retriveData(this@Signup5deatails, Constants.ACCESS_TOKEN_PREFERENCE)!!
        id= PrefrenceUtils.retriveData(this@Signup5deatails, Constants.TEMPID)!!

        Log.d("data", "enc ${token} \n id $id ")

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.app_color)
        }
        binding.edtCity.keyListener = null
//        dropdownsetup()
        dropdownZone()
        binding.btnSubmit.setOnClickListener {
            validate()
        }

    }

    private fun dropdownZone(){
        val loadingdialog = CustomAlertLoadingActivity(this)
        loadingdialog.stateLoading()

        val name: ArrayList<String> = ArrayList()
        val id: ArrayList<Int> = ArrayList()

        binding.edtZone.keyListener=null

        binding.edtZone.setOnItemClickListener { parent, view, position, mid ->
            zone_id=id[position]
        }

        binding.edtZone.threshold = 1
        binding.edtZone.setTextColor(ContextCompat.getColor(this, R.color.app_color))

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.neccZone(token).observe(this,
            Observer {
                loadingdialog.dismiss()
                if (it.errors == null) {

                    if (it.results!=null) {
                        for (i in it.results?.indices!!) {
                            name.add(it.results!![i].name)
                            id.add(it.results!![i].id)
                        }
                        val adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, name)
                        binding.edtZone.setAdapter(adapter)

                    }
                } else {
                    Toast.makeText(this,"${it.errors!![0].message}",Toast.LENGTH_SHORT).show()
                }
            })


    }

    private fun dropdownsetup() {

        pos = 0
        val name: ArrayList<String> = ArrayList()
        val id: ArrayList<Int> = ArrayList()
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.cityList(
        ).observe(this,
            Observer {
                name.clear()
                if (it.count == "0") {
                    Toast.makeText(this, "Service not available", Toast.LENGTH_SHORT).show()
                } else {
                    for (i in it.results.indices) {
                        name.add(it.results[i].city_name + "")
                        id.add(it.results[i].id)
                    }
                }
            })
        val adapter = ArrayAdapter(this, android.R.layout.select_dialog_item, name)
        binding.edtZone.threshold = 1
        binding.edtZone.setAdapter(adapter)
        binding.edtZone.setTextColor(ContextCompat.getColor(this, R.color.app_color))

        binding.edtZone.onItemClickListener =
            OnItemClickListener { arg0, view, position, longArgs ->
                pos = id[position]
            }
    }

    override fun onBackPressed() {
        return
    }

    private fun validate() {
        if (binding.edtFarmerName.text!!.isEmpty() ||
            binding.edtPincode.text!!.isEmpty() || binding.edtPincode.text!!.length != 6
        ) {

            if (binding.edtFarmerName.text!!.isEmpty()) binding.edtFarmerNameLayout.error =
                "Enter valid Farmer Name"
            else binding.edtFarmerNameLayout.isErrorEnabled = false

            if (binding.edtPincode.text!!.isEmpty()) binding.edtPincodeLayout.error =
                "Enter valid Pincode"
            else if (binding.edtPincode.text!!.length < 6) binding.edtPincodeLayout.error =
                "Enter valid Pincode"
            else binding.edtPincodeLayout.isErrorEnabled = false
        } else {
            binding.edtFarmNameLayout.isErrorEnabled = false
            binding.edtFarmerNameLayout.isErrorEnabled = false
            binding.edtCityLayout.isErrorEnabled = false
            binding.edtAddressLayout.isErrorEnabled = false
            binding.edtPincodeLayout.isErrorEnabled = false

            if (zone_id!=-1)
                submit()
            else Toast.makeText(this,"zone Select Error",Toast.LENGTH_SHORT).show()


        }
    }

    private fun submit() {

        val newid:Int=Integer.parseInt(id)
        val pincode:Int=Integer.parseInt(binding.edtPincode.text.toString())

        val loadingdialog = CustomAlertLoadingActivity(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.saveName(
            token, newid, binding.edtFarmerName.text.toString(), pincode,zone_id
        ).observe(this,
            Observer {
                loadingdialog.dismiss()
                if (it?.errors == null) {
                      val sharedPreferences = getSharedPreferences("eggoz", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("id", it.id)
                    editor.putBoolean("profile", true)
                    editor.apply()
                        if (it.id!=null) {
                            PrefrenceUtils.insertData(
                                this@Signup5deatails,
                                Constants.ID,
                                "${it.id}"
                            )

                            val intent = Intent(this, MainActivity::class.java)
                            intent.putExtra("token", token)
                            intent.putExtra("id", it.id)
                            startActivity(intent)
                        }

                } else {
                    Toast.makeText(this, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                    Log.d("data", "${it.errors!![0].message}")
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id",id)
                    intent.putExtra("token", "Bearer $token")
                    startActivity(intent)
                }

            })




    }
}