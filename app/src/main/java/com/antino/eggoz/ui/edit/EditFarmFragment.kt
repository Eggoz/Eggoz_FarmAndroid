package com.antino.eggoz.ui.edit

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentEditFarmBinding
import com.antino.eggoz.databinding.FragmentEditProfileBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.profile.adapter.FarmAdapter
import com.antino.eggoz.view.CustomAlertLoading
import java.util.ArrayList


class EditFarmFragment(
    private val token: String,
    private val mid: Int,
    private val farmid: Int,
    private val contextMain: MainActivity
) : Fragment() {

    private lateinit var binding: FragmentEditFarmBinding
    val name: ArrayList<String> = ArrayList()
    private var farm_layer_type = ""

    var NoOfBroiler=0
    var number_of_layer_shed=0
    var number_of_grower_shed=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditFarmBinding.inflate(inflater, container, false)
        init()
        getFarm()
        return binding.root
    }

    private fun init() {



        name.add("Broiler")
        name.add("Layer")


        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                contextMain.loadDailyInput()
                true
            } else false
        }


        binding.edtFarmLayerType.keyListener=null
        binding.edtFarmLayerType.threshold = 1
        binding.edtFarmLayerType.setTextColor(
            ContextCompat.getColor(
                activity?.applicationContext!!,
                R.color.app_color
            )
        )
        val adapter = ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, name)
        binding.edtFarmLayerType.setAdapter(adapter)
        binding.edtFarmLayerType.setOnItemClickListener { parent, view, position, mid ->
            farm_layer_type = name[position]

            if (position == 0) {
                binding.txtNoOfBroiler.visibility = View.VISIBLE
                binding.txtNoOfSheds.visibility = View.GONE
                binding.txtNumberOfGrowerShed.visibility = View.GONE

                binding.edtNoOfBroilerLayout.visibility = View.VISIBLE
                binding.edtNoOfShedsLayout.visibility = View.GONE
                binding.edtNumberOfGrowerShedLayout.visibility = View.GONE
            } else {
                binding.txtNoOfBroiler.visibility = View.GONE
                binding.txtNoOfSheds.visibility = View.VISIBLE
                binding.txtNumberOfGrowerShed.visibility = View.VISIBLE

                binding.edtNoOfBroilerLayout.visibility = View.GONE
                binding.edtNoOfShedsLayout.visibility = View.VISIBLE
                binding.edtNumberOfGrowerShedLayout.visibility = View.VISIBLE

            }
        }

        binding.btnSubmit.setOnClickListener {
            validate()
        }
    }


    private fun validate() {
        if (binding.edtFarmerName.text!!.isEmpty() ||
            binding.edtBuilding.text!!.isEmpty() || binding.edtCity.text!!.isEmpty() ||
            binding.edtState.text!!.isEmpty() || binding.edtPincode.text!!.isEmpty() || farm_layer_type==""
        ) {
            if (farm_layer_type=="") binding.edtFarmLayerTypeLayout.error="Select type"
            else binding.edtFarmLayerTypeLayout.isErrorEnabled=false

            if (binding.edtFarmerName.text!!.isEmpty()) binding.edtFarmerNameLayout.error =
                "Enter Farmer name"
            else binding.edtFarmerNameLayout.isErrorEnabled = false

            if (binding.edtBuilding.text!!.isEmpty()) binding.edtFlatnoLayout.error =
                "Enter Building/Flat number"
            else binding.edtFlatnoLayout.isErrorEnabled = false
            if (binding.edtCity.text!!.isEmpty()) binding.edtCityLayout.error = "Enter city name"
            else binding.edtCityLayout.isErrorEnabled = false
            if (binding.edtState.text!!.isEmpty()) binding.edtStateLayout.error = "Enter sate Name"
            else binding.edtStateLayout.isErrorEnabled = false
            if (binding.edtPincode.text!!.isEmpty()) binding.edtPincodeLayout.error =
                "Enter pincode"
            else binding.edtPincodeLayout.isErrorEnabled = false
        }else{
            if (binding.edtPincode.text.toString().length!=6) binding.edtPincodeLayout.error="Enter valid pincode"
            else{
                binding.edtFarmerNameLayout.isErrorEnabled = false
                binding.edtFlatnoLayout.isErrorEnabled = false
                binding.edtLandmarkLayout.isErrorEnabled = false
                binding.edtCityLayout.isErrorEnabled = false
                binding.edtStateLayout.isErrorEnabled = false
                binding.edtPincodeLayout.isErrorEnabled = false
                binding.edtFarmLayerTypeLayout.isErrorEnabled=false

                if (binding.edtNoOfBroiler.text!!.isEmpty()&& (binding.edtNoOfSheds.text!!.isEmpty()||binding.edtNumberOfGrowerShed.text!!.isEmpty())){
                    Toast.makeText(context,"Input Field Missing", Toast.LENGTH_LONG).show()
                }else{
                    if (binding.edtNoOfBroiler.text!!.isNotEmpty()) {
                        NoOfBroiler = binding.edtNoOfBroiler.text.toString().toInt()
                    }

                    if (binding.edtNoOfSheds.text!!.isNotEmpty()&&binding.edtNumberOfGrowerShed.text!!.isNotEmpty()) {
                        number_of_layer_shed = binding.edtNoOfSheds.text.toString().toInt()
                        number_of_grower_shed =
                            binding.edtNumberOfGrowerShed.text.toString().toInt()
                    }
                    submit()
                }
            }
        }
    }
    private fun submit(){

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
            viewModel.editFarm(
                token,
                farmid,
                binding.edtFarmerName.text.toString(),
                binding.edtBuilding.text.toString(),
                binding.edtLandmark.text.toString(),
                binding.edtCity.text.toString(),
                binding.edtState.text.toString(),
                binding.edtPincode.text.toString(),
                NoOfBroiler,
                number_of_layer_shed,
                number_of_grower_shed,
                farm_layer_type,
                mid

            ).observe(viewLifecycleOwner,
                Observer {
                    loadingdialog.dismiss()
                    if (it.errors== null) {
                        contextMain.loadDailyInput()

                    } else {
                        Log.d("data", "${it.errors!![0].message}")
                    }
                }
            )


    }

    private fun getFarm() {

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        Log.d("data", "token:$token mid:$mid farmid$farmid")
        viewModel.getFarmbyid(
            token, mid, farmid
        ).observe(requireActivity(),
            Observer {
                loadingdialog.dismiss()
                if (it.errors == null) {
                    farm_layer_type=it.farmLayerType
                    binding.edtFarmerName.setText(it.farmName ?: "")
                    if (it.number_of_broiler_shed ?: 0 > 0) {
                        binding.edtNoOfBroilerLayout.visibility = View.VISIBLE
                        binding.txtNoOfBroiler.visibility = View.VISIBLE
                        binding.edtFarmLayerType.setText("Broiler")
                        binding.edtNoOfBroiler.setText(it.number_of_broiler_shed.toString())
                    }
                    if (it.numberOfLayerShed ?: 0 > 0) {
                        binding.edtNoOfShedsLayout.visibility = View.VISIBLE
                        binding.txtNoOfSheds.visibility = View.VISIBLE
                        binding.edtFarmLayerType.setText("Layer")
                        binding.edtNoOfSheds.setText(it.numberOfLayerShed.toString())
                    }
                    if (it.numberOfGrowerShed ?: 0 > 0) {
                        binding.edtNumberOfGrowerShedLayout.visibility = View.VISIBLE
                        binding.txtNumberOfGrowerShed.visibility = View.VISIBLE
                        binding.edtFarmLayerType.setText("Layer")
                        binding.edtNumberOfGrowerShed.setText(it.numberOfGrowerShed.toString())
                    }
                    binding.edtBuilding.setText(it.billingAddress.buildingAddress)
                    binding.edtLandmark.setText(it.billingAddress.landmark)
                    binding.edtCity.setText(it.billingAddress.billingCity)
                    binding.edtState.setText(it.billingAddress.streetAddress)
                    binding.edtPincode.setText(it.billingAddress.pinCode.toString())

                    Log.d("data","${it.billingAddress.pinCode}")

                }else

                    Log.d("data","type ${it.errorType} field ${it.errors!![0].field}  message ${it.errors!![0].message}")

            }
        )

    }

}