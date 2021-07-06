package com.antino.eggoz.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentAddLayerBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.profile.adapter.FlockitemlistAdapter
import com.antino.eggoz.ui.profile.callback.AddLayerCallback
import com.antino.eggoz.view.CustomAlertLoading


class AddLayerFragment(var context: MainActivity, var mid:Int,var token:String,var from:String) : Fragment(),
    AddLayerCallback {

    private lateinit var binding: FragmentAddLayerBinding
    private val Former_Type:ArrayList<String> = ArrayList()
    private val Former_Type_id:ArrayList<Int> = ArrayList()
    private val FolkType:ArrayList<String> = ArrayList()
    private var Former_Type_pos=0
    private var FolkType_pos=0

    private lateinit var adapter:FlockitemlistAdapter
    private val flockName:ArrayList<String> = ArrayList()
    private val flockbreed:ArrayList<String> = ArrayList()
    private val flockage:ArrayList<String> = ArrayList()
    private val flockquantity:ArrayList<String> = ArrayList()
    private val eggtype:ArrayList<String> = ArrayList()
    private val breedId:ArrayList<Int> = ArrayList()

    private var type=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddLayerBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener { validate() }

        binding.edtLayerType.setOnItemClickListener { parent, view, position, id ->
            Former_Type_pos=position
            type=Former_Type[position]
        }

        init()

        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadDailyInput()
                true
            } else false
        }


        return binding.root
    }
    private fun init(){

        if (from=="layer"){
            dropdownedtLayer_Type()
        }else if (from=="broiler"){
            dropdownedtLayer_Type2()
        }


        binding.btnAddFlocks.setOnClickListener {
            Log.d("data","clicked $from")
            if (from=="layer"){
                AddFlockItemDialog(this,context,token,true).show(parentFragmentManager, "MyCustomFragment")
            }else if (from=="broiler"){
                AddFlockItemDialog(this,context,token,false).show(parentFragmentManager, "MyCustomFragment")
            }
        }

        binding.recyclerViewFlocks.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewFlocks.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewFlocks.isNestedScrollingEnabled = false
        adapter= FlockitemlistAdapter(this,flockName, flockbreed, flockage, flockquantity, eggtype,breedId)
        binding.recyclerViewFlocks.adapter= adapter



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
            else Toast.makeText(context,"Add Some Flock ",Toast.LENGTH_SHORT).show()
        }
    }
    private fun submit(){
        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.addShed(token,binding.edtLayerType.text.toString(),binding.edtLayerName.text.toString(),mid,
            binding.edtCapacity.text.toString(),flockName,
            breedId, flockage, flockquantity, eggtype

        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                if (it.success!=null){
                    Toast.makeText(context,"Shade Add",Toast.LENGTH_SHORT).show()
                    Log.d("data","success")
                    context.loadDailyInput()
                }else{
                    Log.d("data","${it.errors!![0].message} \n ${it.errorType} \n ${it.errors!![0].field}")
                }
            }
        )

    }

    override fun back() {
        super.back()
        context.onBackPressed()

    }

    override fun updateposion(position: Int) {
        super.updateposion(position)
        UpdateFlockItemDialog(this,position,flockName[position],
            flockbreed[position], flockage[position], flockquantity[position], eggtype[position],token).show(parentFragmentManager, "MyCustomFragment")
    }

    override fun addflock(
        flockname: String,
        breed: String,
        flockage: String,
        flockqunt: String,
        eggtype: String,
        flockid:Int
    ) {
        super.addflock(flockname, breed, flockage, flockqunt, eggtype,flockid)
        flockName.add(flockname)
        flockbreed.add(breed)
        this.flockage.add(flockage)
        flockquantity.add(flockqunt)
        this.eggtype.add(eggtype)
        this.breedId.add(flockid)
        (binding.recyclerViewFlocks.adapter as FlockitemlistAdapter).notifyDataSetChanged()
    }

    override fun deleteposion(position: Int) {
        super.deleteposion(position)
        flockName.removeAt(position)
        flockbreed.removeAt(position)
        this.flockage.removeAt(position)
        flockquantity.removeAt(position)
        this.eggtype.removeAt(position)
        this.breedId.removeAt(position)
        (binding.recyclerViewFlocks.adapter as FlockitemlistAdapter).notifyDataSetChanged()
    }

    override fun updateflock(
        position: Int,
        flockname: String,
        breed: String,
        flockage: String,
        flockqunt: String,
        eggtype: String,flockid:Int
    ) {
        super.updateflock(position, flockname, breed, flockage, flockqunt, eggtype,flockid)
        flockName[position] = flockname
        flockbreed[position] = breed
        this.flockage[position] = flockage
        flockquantity[position] = flockqunt
        this.eggtype[position] = eggtype
        this.breedId[position]=flockid
        (binding.recyclerViewFlocks.adapter as FlockitemlistAdapter).notifyDataSetChanged()
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