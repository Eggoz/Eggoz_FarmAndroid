package com.antino.eggoz.ui.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentAddFlockBinding
import com.antino.eggoz.databinding.FragmentProfileBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.view.CustomAlertLoading

class AddFlockFragment(val parentContext:MainActivity,val token:String,val mid:Int,val type:String) : Fragment() {
    private lateinit var binding: FragmentAddFlockBinding
    private val EggType: ArrayList<String> = ArrayList()
    private val Breedname: ArrayList<String> = ArrayList()
    private val Breednameid: ArrayList<Int> = ArrayList()

    private var eggtype = ""
    private var breedname = ""
    private var breedid = 0

    private var EggTypeloc = 0
    private var Breednameloc = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddFlockBinding.inflate(inflater, container, false)
        init()

        binding.btnAddFarm.setOnClickListener {
            validate()
        }

        binding.edtEggType.setOnItemClickListener { parent, view, position, id ->
            EggTypeloc = position
            eggtype = EggType[position]
        }

        return binding.root
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
        viewModel.addFlock(token,mid,binding.edtFlockName.text.toString(),Breednameid[Breednameloc],
            binding.edtFlockAge.text.toString(),binding.edtFlockSize.text.toString(),
            eggtype, binding.edtFlockSize.text.toString()

        ).observe(parentContext,
            Observer {
                loadingdialog.dismiss()
                if(it.errors!=null){
                    Log.d("data","${it.errors!![0].message} \n ${it.errorType} \n ${it.errors!![0].field}")
                }else{
                Toast.makeText(context,"Flock add", Toast.LENGTH_SHORT).show()
                Log.d("data","success")
                parentContext.loadProfile()
            }
            }
        )


    }
    private fun init(){
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                parentContext.loadDailyInput()
                true
            } else false
        }
        dropDownEggType()
        dropDownBreedname()

        keyBoadManage()

        clickevent()

        if (type=="Broiler"){
            binding.txtTypeEgg.visibility=View.GONE
            binding.edtEggTypeLayout.visibility=View.GONE
        }else{
            binding.txtTypeEgg.visibility=View.VISIBLE
            binding.edtEggTypeLayout.visibility=View.VISIBLE

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

        ).observe(parentContext,
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
    private fun keyBoadManage() {
        binding.edtBreednameLayout.setOnClickListener { closeKeyBoard() }
        binding.edtBreednameLayout.setEndIconOnClickListener { closeKeyBoard() }
        binding.edtBreedname.setOnClickListener { closeKeyBoard() }

        binding.edtEggType.setOnClickListener { closeKeyBoard() }
        binding.edtEggTypeLayout.setOnClickListener { closeKeyBoard() }
        binding.edtEggTypeLayout.setEndIconOnClickListener { closeKeyBoard() }

    }
    private fun clickevent() {

        binding.edtBreednameLayout.setOnClickListener {
            val imm =
                parentContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        binding.edtBreednameLayout.setEndIconOnClickListener {
            val imm =
                parentContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
        }
        binding.edtBreedname.setOnClickListener {
            val imm =
                parentContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view?.windowToken, 0)
            Log.d("data2", "keyboard bak outside")
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







    }

    private fun closeKeyBoard() {
        val imm =
            parentContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}