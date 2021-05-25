package com.antino.eggoz.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.AddflockitemDialogBinding
import com.antino.eggoz.modelvew.ModelMain


class AddFlockItemDialog(private val callbacks: AddLayerFragment,private val parentContext: MainActivity,
private var token:String,private var status:Boolean) :
    DialogFragment() {
    private lateinit var edt_name: AutoCompleteTextView
    private lateinit var binding: AddflockitemDialogBinding
    private var EggTypeloc = 0
    private var Breednameloc = 0
    private val EggType: ArrayList<String> = ArrayList()
    private val Breedname: ArrayList<String> = ArrayList()
    private val Breednameid: ArrayList<Int> = ArrayList()

    private var eggtype = ""
    private var breedname = ""
    private var breedid = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        binding = AddflockitemDialogBinding.inflate(inflater, container, false)

        init()
        val set = ConstraintSet()
        set.clone(binding.ConstraintLayoutDialog)
        set.setHorizontalBias(R.id.btn_Submit, 0F)
        set.applyTo(binding.ConstraintLayoutDialog)

        binding.btnDelete.visibility = View.GONE
        dropDownEggType()
        dropDownBreedname()

        keyBoadManage()

        clickevent()



        return binding.root
    }

    private fun init() {
        binding.btnSubmit.setOnClickListener {
            if (status)
            validate()
            else
                validate2()
        }
        if (!status)
        {
            binding.edtEggTypeLayout.visibility=View.GONE
            binding.txtTypeEgg.visibility=View.GONE
        }

    }

    private fun validate() {
        if (binding.edtFlockName.text!!.isEmpty() || binding.edtFlockAge.text!!.isEmpty() || binding.edtFlockSize.text!!.isEmpty() ||
            breedname == "" || eggtype == ""
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
            if (breedname == "") binding.edtBreednameLayout.error = "Select Breed Name"
            else binding.edtBreednameLayout.isErrorEnabled = false
            if (eggtype == "") binding.edtEggTypeLayout.error = "Select Egg type"
            else binding.edtEggTypeLayout.isErrorEnabled = false
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

    private fun validate2() {
        if (binding.edtFlockName.text!!.isEmpty() || binding.edtFlockAge.text!!.isEmpty() || binding.edtFlockSize.text!!.isEmpty() ||
            breedname == ""
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
            if (breedname == "") binding.edtBreednameLayout.error = "Select Breed Name"
            else binding.edtBreednameLayout.isErrorEnabled = false
        }else{
            binding.edtFlockNameLayout.isErrorEnabled = false
            binding.edtFlockAgeLayout.isErrorEnabled = false
            binding.edtFlockSizeLayout.isErrorEnabled = false
            binding.edtBreednameLayout.isErrorEnabled = false

            if (binding.edtFlockSize.text.toString().toInt()>0){
                binding.edtFlockSizeLayout.isErrorEnabled=false
                submit2()
            }
            else
                binding.edtFlockSizeLayout.error="Flock Qunantity must me greater than 0"
        }
    }
    private fun submit(){
        callbacks.addflock(
            binding.edtFlockName.text.toString(),
            Breedname[Breednameloc],
            binding.edtFlockAge.text.toString(),
            binding.edtFlockSize.text.toString(),
            EggType[EggTypeloc],breedid
        )
        dismiss()
    }
    private fun submit2(){
        callbacks.addflock(
            binding.edtFlockName.text.toString(),
            Breedname[Breednameloc],
            binding.edtFlockAge.text.toString(),
            binding.edtFlockSize.text.toString(),
            "",breedid
        )
        dismiss()
    }

    private fun clickevent() {

        binding.edtBreednameLayout.setOnClickListener {
            val imm =
                parentContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        binding.edtBreedname.setOnClickListener {
            val imm =
                parentContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        }

        binding.edtEggType.setOnItemClickListener { parent, view, position, id ->
            EggTypeloc = position
            eggtype = EggType[position]
        }
        binding.edtBreedname.setOnItemClickListener { parent, view, position, id ->
            Breednameloc = position
            breedname = Breedname[position]
            breedid=Breednameid[position]
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }


    }

    private fun keyBoadManage() {
        binding.edtBreednameLayout.setOnClickListener { closeKeyBoard() }
        binding.edtBreednameLayout.setEndIconOnClickListener { closeKeyBoard() }
        binding.edtBreedname.setOnClickListener { closeKeyBoard() }

        binding.edtEggType.setOnClickListener { closeKeyBoard() }
        binding.edtEggTypeLayout.setOnClickListener { closeKeyBoard() }
        binding.edtEggTypeLayout.setEndIconOnClickListener { closeKeyBoard() }

    }

    private fun closeKeyBoard() {
        val imm =
            parentContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
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

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.flockBreed(token

        ).observe(parentContext,
            Observer {
                if (it.breed.isNotEmpty()){
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


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}