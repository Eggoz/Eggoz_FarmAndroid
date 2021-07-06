package com.antino.eggoz.ui.edit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentEditProfileBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.profile.callback.EditProfileCallback
import com.antino.eggoz.ui.profile.dialog.ErrorMessage
import com.antino.eggoz.view.CustomAlertLoading

class EditProfileFragment(
    private var mcontext:MainActivity,
    private var token: String,
    private var mid: Int) : Fragment(),EditProfileCallback {


    private lateinit var binding: FragmentEditProfileBinding
    private var fid: Int?=null

    private lateinit var email:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        loadProfile()



        binding.btnEditSubmit.setOnClickListener { vilidateEdit() }

        return binding.root
    }

    private fun vilidateEdit(){
//        ||binding.edtMobileNo.text!!.isEmpty()

        if (binding.edtFarmerName.text!!.isEmpty() ||binding.edtMobileNo.text!!.length<10||
            binding.edtEmailId.text!!.isEmpty()
        ) {

            if (binding.edtFarmerName.text!!.isEmpty()) binding.edtFarmerNameLayout.error =
                "Enter valid Farmer Name"
            else binding.edtFarmerNameLayout.isErrorEnabled = false

            if (binding.edtPincode.text!!.isEmpty()) binding.edtPincodeLayout.error =
                "Enter valid Pincode"
            else if (binding.edtPincode.text!!.length < 6) binding.edtPincodeLayout.error =
                "Enter valid Pincode"
            else binding.edtPincodeLayout.isErrorEnabled = false
            if ( binding.edtEmailId.text!!.isEmpty()) binding.edtEmailIdLayout.error=
                "Enter Email id"
            else binding.edtEmailIdLayout.isErrorEnabled=false
            if (binding.edtMobileNo.text!!.length<10) binding.edtMobileNoLayout.error=
                "Enter Mobile Number"
            else binding.edtMobileNoLayout.isErrorEnabled=false

        } else {

            binding.edtFarmerNameLayout.isErrorEnabled = false
            binding.edtEmailIdLayout.isErrorEnabled=false
            binding.edtMobileNoLayout.isErrorEnabled=false
            if (email==binding.edtEmailId.text.toString())
            editProfile()
            else
                ErrorMessage("Relogin mandatory after email change", this,false).show(
                    mcontext.supportFragmentManager,
                    "MyCustomFragment2"
                )

        }

    }

    private fun editProfile() {

        if (fid!=null) {
            val loadingdialog = CustomAlertLoading(this)
            loadingdialog.stateLoading()

            val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
            viewModel.editProfile(
                token, fid!!, binding.edtFarmerName.text.toString(),
                "+91${binding.edtMobileNo.text.toString()}",
                binding.edtEmailId.text.toString(),
                binding.edtPincode.text.toString()
            ).observe(mcontext,
                Observer {
                    loadingdialog.dismiss()
                    if (it?.errors == null) {

                        mcontext.loadProfile()
                        mcontext.fetchProfile()

//                        startActivity(Intent(activity,Signup1::class.java))
                    } else {
                        Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                        Log.d("data", "${it.errors!![0].message}")
                        /*val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("id",id)
                    intent.putExtra("token", "Bearer $token")
                    startActivity(intent)*/
                    }
                }
            )
        }else{
            Toast.makeText(context,"Error Loading farmer id",Toast.LENGTH_SHORT).show()
        }

    }



    private fun loadProfile(){


        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.farmerProfile(
            token, mid
        ).observe(mcontext,
            Observer {
                loadingdialog.dismiss()
                if (it?.id!=null){

                    fid=it.farmer.id
                    binding.edtFarmerName.setText(it.farmer.name)
                    binding.edtMobileNo.setText(it.farmer.phoneNo?.substring(3,it.farmer.phoneNo!!.length))
                    binding.edtEmailId.setText(it.farmer.email)
                    binding.edtPincode.setText("${it.farmer.defaultAddress?.pinCode ?:""}")

                    email=it.farmer.email

                }else{
                    Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                    Log.d("data","${it.errors!![0].message}")
                }
            }
        )


    }

    override fun save() {
        editProfile()
    }

}