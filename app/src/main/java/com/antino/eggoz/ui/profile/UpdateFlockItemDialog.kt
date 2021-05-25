package com.antino.eggoz.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.VerifiedInputEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.adapters.ViewBindingAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.R
import com.antino.eggoz.databinding.AddflockitemDialogBinding
import com.antino.eggoz.modelvew.ModelMain

class UpdateFlockItemDialog(
    private val callbacks: AddLayerFragment,
    private val position: Int,
    private val flockname: String,
    private val breed: String,
    private val flockage: String,
    private val flockqunt: String,
    private val eggtype: String,
    private val token: String
) : DialogFragment() {
    private lateinit var edt_name: AutoCompleteTextView
    private lateinit var binding: AddflockitemDialogBinding
    private var productloc = 0
    private val name: ArrayList<String> = ArrayList()
    private val Breedname: ArrayList<String> = ArrayList()
    private val Breednameid: ArrayList<Int> = ArrayList()
    private var breedid:Int=0;

    private var typeegg=""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner)
        binding = AddflockitemDialogBinding.inflate(inflater, container, false)
        val set = ConstraintSet()
        set.clone(binding.ConstraintLayoutDialog)
        set.setHorizontalBias(R.id.btn_Submit, 0F)
        set.applyTo(binding.ConstraintLayoutDialog)

        init()


        return binding.root
    }

    private fun init(){

        binding.edtFlockName.setText(flockname)
        binding.edtBreedname.setText(breed)
        binding.edtFlockAge.setText(flockage)
        binding.edtFlockSize.setText(flockqunt)
        binding.edtEggType.setText(eggtype)

        binding.btnDelete.visibility = View.GONE
        dropDownOtherProduct()
        dropDownBreed()

        if (eggtype==""){
            binding.edtEggTypeLayout.visibility=View.GONE
            binding.txtTypeEgg.visibility=View.GONE
        }else{
            binding.edtEggTypeLayout.visibility=View.VISIBLE
            binding.txtTypeEgg.visibility=View.VISIBLE
        }
        click()
    }
    private fun click(){
        binding.edtEggType.setOnItemClickListener { parent, view, position, id ->
            productloc = position
        }
        binding.edtBreedname.setOnItemClickListener { parent, view, position, id ->
            breedid = Breednameid[position]
        }

        binding.edtEggType.setOnItemClickListener { parent, view, position, id ->
            productloc = position
            typeegg = name[position]
        }
        binding.btnDelete.visibility = View.VISIBLE


        binding.btnSubmit.setOnClickListener {
            callbacks.updateflock(
                position, binding.edtFlockName.text.toString(),
                binding.edtBreedname.text.toString(),
                binding.edtFlockAge.text.toString(),
                binding.edtFlockSize.text.toString(),
                typeegg,
                breedid
            )
            dismiss()
        }

        binding.btnDelete.setOnClickListener {
            callbacks.deleteposion(
                position
            )
            dismiss()
        }

        binding.btnClose.setOnClickListener {
            dismiss()
        }
    }
    private fun dropDownOtherProduct() {
        name.add("White")
        name.add("Brown")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, name)
        binding.edtEggType.threshold = 1
        binding.edtEggType.setAdapter(adapter)
        binding.edtEggType.keyListener = null
    }

    private fun dropDownBreed(){
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.flockBreed(token
        ).observe(this,
            Observer {
                if (it.breed.isNotEmpty()){
                    for (i in it.breed.indices){
                        Breedname.add(it.breed[i].breed_name)
                        Breednameid.add(it.breed[i].id)
                    }
                }
            }
        )
    }


    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}