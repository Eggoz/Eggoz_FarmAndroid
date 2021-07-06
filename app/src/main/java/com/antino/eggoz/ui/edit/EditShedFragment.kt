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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentEditFarmBinding
import com.antino.eggoz.databinding.FragmentEditShedBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.edit.adapter.FlockitemlistEditAdapter
import com.antino.eggoz.ui.edit.dailog.UpdateFlockItemEditDialog
import com.antino.eggoz.ui.profile.UpdateFlockItemDialog
import com.antino.eggoz.ui.profile.adapter.FlockitemlistAdapter
import com.antino.eggoz.ui.profile.callback.AddLayerCallback
import com.antino.eggoz.view.CustomAlertLoading


class EditShedFragment(
    private val token: String,
    private val shadeid: Int,
    private val contextMain: MainActivity
) : Fragment(), AddLayerCallback {

    private lateinit var binding: FragmentEditShedBinding

    private val flockName:ArrayList<String> = ArrayList()
    private val flockbreed:ArrayList<String> = ArrayList()
    private val flockage:ArrayList<String> = ArrayList()
    private val flockquantity:ArrayList<String> = ArrayList()
    private val eggtype:ArrayList<String> = ArrayList()
    private val breedId:ArrayList<Int> = ArrayList()
    private val Former_Type:ArrayList<String> = ArrayList()

    private var Former_Type_pos=0
    private var type=""

//    private lateinit var adapter:FlockitemlistEditAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditShedBinding.inflate(inflater, container, false)

        init()
        loadShed()

        return binding.root
    }
    private fun init(){

        binding.apply {
            root.isFocusableInTouchMode = true
            root.requestFocus()
            root.setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                    contextMain.loadDailyInput()
                    true
                } else false
            }



            recyclerViewFlocks.layoutManager = LinearLayoutManager(context)
            recyclerViewFlocks.itemAnimator = DefaultItemAnimator()
            recyclerViewFlocks.isNestedScrollingEnabled = false

            edtLayerType.setOnItemClickListener { parent, view, position, id ->
                Former_Type_pos=position
                type=Former_Type[position]
            }
            btnSubmit.setOnClickListener { validate() }
        }


    }


    private fun validate(){
        if (binding.edtLayerName.text!!.isEmpty()||binding.edtCapacity.text!!.isEmpty()||type==""){
            if (binding.edtLayerName.text!!.isEmpty()) binding.edtLayerNameLayout.error="Enter Layer Name"
            else binding.edtLayerNameLayout.isErrorEnabled=false
            if (binding.edtCapacity.text!!.isEmpty()) binding.edtCapacityLayout.error="Enter Capacity"
            else binding.edtCapacityLayout.isErrorEnabled=false
            if (type=="") binding.edtLayerTypeLayout.error="Select Type"
            else binding.edtLayerTypeLayout.isErrorEnabled=false

        }else{
            binding.edtLayerNameLayout.isErrorEnabled=false
            binding.edtCapacityLayout.isErrorEnabled=false
            binding.edtLayerTypeLayout.isErrorEnabled=false
            if (flockName.size>0)
                submit()
            else Toast.makeText(context,"Add Some Flock ", Toast.LENGTH_SHORT).show()
        }
    }
    private fun submit(){
        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.editShed(token,binding.edtLayerType.text.toString(),binding.edtLayerName.text.toString(),shadeid,
            binding.edtCapacity.text.toString(),flockName,
            breedId, flockage, flockquantity, eggtype

        ).observe(viewLifecycleOwner,
            Observer {
                loadingdialog.dismiss()
                if (it.errors==null){
                    Toast.makeText(context,"Shade Add", Toast.LENGTH_SHORT).show()
                    Log.d("data","success")
                    contextMain.loadDailyInput()
                }else{
                }
            }
        )

    }

    private fun loadShed(){

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getShedbyid(
            token, shadeid
        ).observe(requireActivity(),
            Observer {
                loadingdialog.dismiss()
                if (it.errorType == null) {

                    if (it.shedType=="Broiler")
                        dropdownedtLayer_Type2()
                    else
                        dropdownedtLayer_Type()

                    type=it.shedType

                    binding.apply {
                        edtLayerType.setText(it.shedType)
                        edtLayerName.setText(it.shedName)
                        edtCapacity.setText(it.totalActiveBirdCapacity.toString())
                    }
                    if (it.flocks != null)
                        for (i in it.flocks?.indices!!) {

                            flockName.add(it.flocks!![i].flockName)
                            flockbreed.add(it.flocks!![i].breed.breedName)
                            flockage.add(it.flocks!![i].age.toString())
                            flockquantity.add(it.flocks!![i].initialCapacity.toString())
                            eggtype.add(it.flocks!![i].eggType)
                            breedId.add(it.flocks!![i].breed.id)
                        }

             /*       adapter = FlockitemlistEditAdapter(
                        this,
                        flockName,
                        flockbreed,
                        flockage,
                        flockquantity,
                        eggtype,
                        breedId
                    )
                    binding.recyclerViewFlocks.adapter = adapter
                    (binding.recyclerViewFlocks as FlockitemlistEditAdapter).notifyDataSetChanged()*/

                }

            }
        )
    }

    override fun updateposion(position: Int) {
        super.updateposion(position)
        UpdateFlockItemEditDialog(this,position,flockName[position],
            flockbreed[position], flockage[position], flockquantity[position], eggtype[position],token).show(parentFragmentManager, "MyCustomFragment")
    }

    override fun updateflock(
        position: Int,
        flockname: String,
        breed: String,
        flockage: String,
        flockqunt: String,
        eggtype: String,
        flockid: Int
    ) {
        flockName[position] = flockname
        flockbreed[position] = breed
        this.flockage[position] = flockage
        flockquantity[position] = flockqunt
        this.eggtype[position] = eggtype
        this.breedId[position]=flockid
        (binding.recyclerViewFlocks.adapter as FlockitemlistEditAdapter).notifyDataSetChanged()
    }

    override fun deleteposion(position: Int) {
        super.deleteposion(position)
        flockName.removeAt(position)
        flockbreed.removeAt(position)
        this.flockage.removeAt(position)
        flockquantity.removeAt(position)
        this.eggtype.removeAt(position)
        this.breedId.removeAt(position)
        (binding.recyclerViewFlocks.adapter as FlockitemlistEditAdapter).notifyDataSetChanged()
    }


    private fun dropdownedtLayer_Type() {

        Former_Type.add("Layer")
        Former_Type.add("Grower")
        val adapter =  ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, Former_Type)
        binding.edtLayerType.threshold=1
        binding.edtLayerType.setAdapter(adapter)
        binding.edtLayerType.keyListener=null
        binding.edtLayerType.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_color))
    }

    private fun dropdownedtLayer_Type2() {

        Former_Type.add("Broiler")
        val adapter =  ArrayAdapter(requireContext(), android.R.layout.select_dialog_item, Former_Type)
        binding.edtLayerType.threshold=1
        binding.edtLayerType.setAdapter(adapter)
        binding.edtLayerType.keyListener=null
        binding.edtLayerType.setTextColor(ContextCompat.getColor(requireActivity(),R.color.app_color))
    }

}