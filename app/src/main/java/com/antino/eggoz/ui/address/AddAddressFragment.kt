package com.antino.eggoz.ui.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentAddAddressBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.view.CustomAlertLoading

class AddAddressFragment(
    var token: String, var mcontex: MainActivity,
    var mid: Int,
    var from: String,
    var ids: Int,
    var name: String,
    var img: String,
    var price: String,
    var qnt: Int,
    var des: String,
    var tax: Boolean
) : Fragment() {


    private lateinit var binding: FragmentAddAddressBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddAddressBinding.inflate(inflater, container, false)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            activity?.window?.statusBarColor =
                ContextCompat.getColor(requireContext(), R.color.app_color)
        }


        binding.btnSubmit.setOnClickListener { validate() }

        return binding.root
    }

    private fun validate() {
        if (
            binding.edtBuilding.text?.length == 0 ||
            binding.edtStreet.text?.length == 0 ||
            binding.edtCity.text?.length == 0 ||
            binding.edtPincode.text?.length == 0 ||
            binding.edtPincode.text?.length!! in 1..5
        ) {
            if (binding.edtBuilding.text?.length == 0) binding.edtBuildingLayout.error =
                "Please enter building Name"
            else
                binding.edtBuildingLayout.isErrorEnabled = false
            if (binding.edtStreet.text?.length == 0) binding.edtStreetLayout.error =
                "Please enter Street Name"
            else
                binding.edtStreetLayout.isErrorEnabled = false
          /*  if (binding.edtLandmark.text?.length == 0) binding.edtLandmarkLayout.error =
                "Please enter Landmark"
            else
                binding.edtLandmarkLayout.isErrorEnabled = false*/
            if (binding.edtCity.text?.length == 0) binding.edtCityLayout.error =
                "Please enter city Name"
            else
                binding.edtCityLayout.isErrorEnabled = false
            if (binding.edtPincode.text?.length == 0) binding.edtPincodeLayout.error =
                "Please enter pincode Name"
            else if (binding.edtPincode.text?.length!! in 1..5) binding.edtPincodeLayout.error =
                "Please complete pincode Name"
            else
                binding.edtPincodeLayout.isErrorEnabled = false
        } else {
            binding.edtAddressNameLayout.isErrorEnabled = false
            binding.edtBuildingLayout.isErrorEnabled = false
            binding.edtStreetLayout.isErrorEnabled = false
            binding.edtLandmarkLayout.isErrorEnabled = false
            binding.edtCityLayout.isErrorEnabled = false
            binding.edtPincodeLayout.isErrorEnabled = false
            confirm()

        }
    }

    private fun confirm() {


        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.addAddress(
            token, binding.edtAddressName.text.toString(),
            binding.edtBuilding.text.toString(),
            binding.edtStreet.text.toString(),
            binding.edtLandmark.text.toString(),
            binding.edtCity.text.toString(),
            binding.edtPincode.text.toString(),
        ).observe(viewLifecycleOwner,
            Observer {
                loadingdialog.dismiss()
                if (it.errors == null) {
                    mcontex.loadBuyFragment(
                        from,
                        mid,
                        name,
                        img,
                        price,
                        qnt,
                        des,
                        tax
                    )
                } else
                    Toast.makeText(context, "${it.errors!![0].message}", Toast.LENGTH_SHORT).show()
            }
        )


    }

}