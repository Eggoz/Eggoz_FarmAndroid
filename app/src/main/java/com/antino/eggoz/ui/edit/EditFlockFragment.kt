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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentEditFlockBinding
import com.antino.eggoz.databinding.FragmentEditShedBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.edit.adapter.FlockitemlistEditAdapter
import com.antino.eggoz.view.CustomAlertLoading

class EditFlockFragment(
    private val token: String,
    private val flockid: Int,
    private val contextMain: MainActivity
) : Fragment() {

    private lateinit var binding: FragmentEditFlockBinding

    private var Breednameloc = 0
    private var breedname = ""
    private var breedid = 0
    private val Breedname: ArrayList<String> = ArrayList()
    private val Breednameid: ArrayList<Int> = ArrayList()
    private val EggType: ArrayList<String> = ArrayList()
    private var EggTypeloc = 0
    private var eggtype = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditFlockBinding.inflate(inflater, container, false)

        init()
        dropDownBreedname()
        getFlock()
        return binding.root
    }
    private fun init(){
        dropDownEggType()
        binding.apply {
            root.isFocusableInTouchMode = true
            root.requestFocus()
            root.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                    contextMain.loadDailyInput()
                    true
                } else false
            }

            edtBreedname.setOnItemClickListener { parent, view, position, id ->
                Breednameloc = position
                breedname = Breedname[position]
                breedid=Breednameid[position]
            }

            binding.edtEggType.setOnItemClickListener { parent, view, position, id ->
                EggTypeloc = position
                eggtype = EggType[position]
            }


            btnSubmit.setOnClickListener { validate() }
        }

    }

    private fun dropDownEggType() {
        EggType.add("White")
        EggType.add("Brown")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, EggType)
        binding.edtEggType.threshold = 1
        binding.edtEggType.setAdapter(adapter)
        binding.edtEggType.keyListener = null
    }
    private fun dropDownBreedname() {

        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.flockBreed(token

        ).observe(viewLifecycleOwner,
            Observer {
                if (it.breed.isNotEmpty()){
                    loadingdialog.dismiss()
                    for (i in it.breed.indices){
                        Breedname.add(it.breed[i].breed_name)
                        Breednameid.add(it.breed[i].id)
                    }
                }
            }
        )
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.select_dialog_item,
            Breedname
        )
        binding.edtBreedname.threshold = 1
        binding.edtBreedname.setAdapter(adapter)
        binding.edtBreedname.keyListener = null
    }
    private fun validate() {
        if (binding.edtFlockName.text!!.isEmpty() || binding.edtFlockAge.text!!.isEmpty() || binding.edtFlockSize.text!!.isEmpty()
        ) {
            if (binding.edtFlockName.text!!.isEmpty()) binding.edtFlockNameLayout.error =
                "Enter Flock Name"
            else binding.edtFlockNameLayout.isErrorEnabled = false
            if (binding.edtFlockAge.text!!.isEmpty()) binding.edtFlockAgeLayout.error =
                "Enter Flockage"
            else binding.edtFlockAgeLayout.isErrorEnabled = false
            if (binding.edtFlockSize.text!!.isEmpty()) binding.edtFlockSizeLayout.error =
                "Enter Flock size"
            else binding.edtFlockSizeLayout.isErrorEnabled = false
        }else{
            binding.edtFlockNameLayout.isErrorEnabled = false
            binding.edtFlockAgeLayout.isErrorEnabled = false
            binding.edtFlockSizeLayout.isErrorEnabled = false
            binding.edtBreednameLayout.isErrorEnabled = false
            binding.edtEggTypeLayout.isErrorEnabled = false

            if (binding.edtFlockSize.text.toString().toInt()>0){
                binding.edtFlockSizeLayout.isErrorEnabled=false
                submit()
            }
            else
                binding.edtFlockSizeLayout.error="Flock Qunantity must me greater than 0"
        }
    }
    private fun submit(){

        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.editFlock(token,flockid,binding.edtFlockName.text.toString(),Breednameid[Breednameloc],
            binding.edtFlockAge.text.toString(),binding.edtFlockSize.text.toString(),
            eggtype, binding.edtFlockSize.text.toString()

        ).observe(viewLifecycleOwner,
            Observer {
                loadingdialog.dismiss()
                if(it.errors!=null){
                    Log.d("data","${it.errors!![0].message} \n ${it.errorType} \n ${it.errors!![0].field}")
                }else{
                    Toast.makeText(context,"Flock Added", Toast.LENGTH_SHORT).show()
                    Log.d("data","success")
                    contextMain.loadDailyInput()
                }
            }
        )


    }
    private fun getFlock(){

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getFlockbyid(
            token, flockid
        ).observe(requireActivity(),
            Observer {
                loadingdialog.dismiss()
                if (it.errorType == null) {
                    eggtype=it.eggType
                    binding.apply {
                        edtFlockName.setText(it.flockName)
                        edtFlockAge.setText(it.age.toString())
                        edtFlockSize.setText(it.initialCapacity.toString())
                        edtBreedname.setText(it.breed.breedName)

                        if (it.eggType == "") {
                            edtEggTypeLayout.visibility = View.GONE
                            txtTypeEgg.visibility=View.GONE
                        }else {
                            edtEggType.setText(it.eggType)
                        }


                    }
                }

            }
        )


    }
}