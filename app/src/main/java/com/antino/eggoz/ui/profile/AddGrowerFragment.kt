package com.antino.eggoz.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentAddGrowerBinding
import com.antino.eggoz.ui.profile.Model.Grower


class AddGrowerFragment(var context: MainActivity, var grower:ArrayList<Grower>, private var noOfShads:String,
                        private var noOfGrower: String) : Fragment() {

    private lateinit var binding: FragmentAddGrowerBinding
    private val Vaccination:ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddGrowerBinding.inflate(inflater, container, false)
        dropdownvac()

        binding.btnSubmit.setOnClickListener { context.addGrower(binding.edtGrowerName.text.toString(),
            binding.edtTotalBirdCapacity.text.toString(),binding.edtAge.text.toString(),
            binding.edtVaccination.text.toString(),noOfShads,noOfGrower
        ) }
        return binding.root
    }

    private fun dropdownvac(){
        Vaccination.add("Done")
        Vaccination.add("Pending")
        Vaccination.add("On Progress")
        val adapter =  ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, Vaccination)
        binding.edtVaccination.threshold=1
        binding.edtVaccination.setAdapter(adapter)
        binding.edtVaccination.keyListener=null
        binding.edtVaccination.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_color))
    }

}