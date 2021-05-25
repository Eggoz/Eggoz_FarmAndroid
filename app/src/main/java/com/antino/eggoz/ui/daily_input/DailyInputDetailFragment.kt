package com.antino.eggoz.ui.daily_input

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentDailyInputDetailBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.daily_input.adapter.FlockmedlistAdapter
import com.antino.eggoz.view.CustomAlertLoading
import com.antino.eggoz.view.MyCustomDialog
import com.antino.eggoz.view.MyCustomDialog2
import java.text.DecimalFormat


class DailyInputDetailFragment(
    val context: MainActivity,val token:String,
    val flockid: Int,
    val flocktotalactivebird: Int,
    val lastupdate:String,
    val from:String
) : Fragment(),DailyInputDetailCallback {

    private lateinit var binding: FragmentDailyInputDetailBinding
    var totalActive = flocktotalactivebird


    var error: Boolean = false
    var totalCulling = 0
    var totolMortality = 0
    var feedconsumed:Double= 0.0
    var totalEgg=0
    private lateinit var decimalformat:DecimalFormat
    var hdep=0.00

    lateinit var medlist:ArrayList<String>
    lateinit var medlistid:ArrayList<Int>

    lateinit var medlistidpost:ArrayList<Int>
    lateinit var medlistqnt:ArrayList<Int>
    lateinit var medlistname:ArrayList<String>

    private var brokenegg="0"
    private var brokeneggInoperation="0"
    private var culling="0"


    private lateinit var adapter:FlockmedlistAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDailyInputDetailBinding.inflate(inflater, container, false)

        decimalformat= DecimalFormat("#.##")


        init()
        getmedlist()

        binding.txtAddMedicine.setOnClickListener {
            MyCustomDialog(this,medlist,medlistid).show(parentFragmentManager, "MyCustomFragment")
        }
        binding.btnSubmit.setOnClickListener {
            if (from=="Layer")
            validation()
            else
                validation2()
        }

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

    override fun mededitCallback(pos: Int,qnt:Int,id:Int) {
        MyCustomDialog2(this,medlist,medlistid,pos,qnt,id).show(parentFragmentManager, "MyCustomFragment2")
        super.mededitCallback(pos,qnt,id)
    }
    override fun medCallback(medid: Int, pos: Int, qunt: String) {
        medlistidpost.add(medlistid[pos])
        medlistqnt.add(qunt.toInt())
        medlistname.add(medlist[pos])

        (binding.recycleMedicen.adapter as FlockmedlistAdapter).notifyDataSetChanged()
        super.medCallback(medid, pos, qunt)
    }
    override fun medupdateCallback(medid: Int, pos: Int, qunt: String) {
        medlistidpost[pos]=medlistid[pos]
        medlistqnt[pos]=(qunt.toInt())
        medlistname[pos]=(medlist[pos])
        (binding.recycleMedicen.adapter as FlockmedlistAdapter).notifyDataSetChanged()
        super.medupdateCallback(medid, pos, qunt)
    }

    override fun meddeleteCallback(pos: Int) {
        medlistidpost.removeAt(pos)
        medlistqnt.removeAt(pos)
        medlistname.removeAt(pos)
        (binding.recycleMedicen.adapter as FlockmedlistAdapter).notifyDataSetChanged()

        super.meddeleteCallback(pos)
    }

    private fun getmedlist(){


        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getMedlist(
            token
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                Log.d("data","${it.toString()}")
                if (it.count>0){
                    for (i in it.results.indices){
                        medlist.add(it.results[i].name)
                        medlistid.add(it.results[i].id)
                    }



                }else{
                    Toast.makeText(context,"val not greater then 0",Toast.LENGTH_SHORT).show()

                }

            }
        )

    }

    private fun init() {

        medlist= ArrayList()
        medlistid= ArrayList()
        medlistidpost= ArrayList()
        medlistqnt= ArrayList()
        medlistname=ArrayList()

        if (from=="Grower"){
            binding.txtCullingBird.visibility=View.GONE
            binding.edtCullingBirdLayout.visibility=View.GONE
        }

        if (from=="Layer"){

            binding.txtEggPrduced.visibility=View.VISIBLE
            binding.txtBrokenEggInOperation.visibility=View.VISIBLE
            binding.txtBrokenEggProduced.visibility=View.VISIBLE

            binding.edtEggProducedLayout.visibility=View.VISIBLE
            binding.edtBrokenEggInOperationLayout.visibility=View.VISIBLE
            binding.edtBrokenEggProducedLayout.visibility=View.VISIBLE

            binding.txtHDEP.visibility=View.VISIBLE
            binding.txtHDEPValue.visibility=View.VISIBLE

            binding.txtWeight.visibility=View.GONE
            binding.edtWeightLayout.visibility=View.GONE

        }else{
            binding.txtWeight.visibility=View.VISIBLE
            binding.edtWeightLayout.visibility=View.VISIBLE




            binding.txtEggPrduced.visibility=View.GONE
            binding.txtBrokenEggInOperation.visibility=View.GONE
            binding.txtBrokenEggProduced.visibility=View.GONE

            binding.edtEggProducedLayout.visibility=View.GONE
            binding.edtBrokenEggInOperationLayout.visibility=View.GONE
            binding.edtBrokenEggProducedLayout.visibility=View.GONE

            binding.txtHDEP.visibility=View.GONE
            binding.txtHDEPValue.visibility=View.GONE
        }



        binding.recycleMedicen.layoutManager = LinearLayoutManager(context)
        binding.recycleMedicen.itemAnimator = DefaultItemAnimator()
        binding.recycleMedicen.isNestedScrollingEnabled = false
        adapter= FlockmedlistAdapter(this,medlistidpost,medlistqnt,medlistname)
        binding.recycleMedicen.adapter= adapter

        binding.edtCullingBird.addTextChangedListener {
            if (it.toString() == "")
                totalCulling = 0
            else
                totalCulling = Integer.parseInt(it.toString())
            binding.txtActiveBirdValue.text =
                "${flocktotalactivebird - totolMortality-totalCulling}"
        }
        binding.edtTotalMortality.addTextChangedListener {
            if (it.toString() == "")
                totolMortality = 0
            else
                totolMortality = Integer.parseInt(it.toString())
            if (flocktotalactivebird - totolMortality-totalCulling>0)
                binding.txtActiveBirdValue.text =
                    "${flocktotalactivebird - totolMortality-totalCulling}"
            else
                binding.txtActiveBirdValue.text ="0"
        }

        binding.edtFeedConsumed.addTextChangedListener {
            if (it.toString() == "")
                feedconsumed = 0.0
            else
                feedconsumed = it.toString().toDouble()
            binding.txtFeedIntakePerBirdValue.text =
                "${String.format("%.2f",(feedconsumed * 1000) / flocktotalactivebird.toDouble()) }gm"

        }
        binding.edtEggProduced.addTextChangedListener {
            totalEgg = if (it.toString() == "")
                0
            else
                Integer.parseInt(it.toString())
            hdep=decimalformat.format(totalEgg.toDouble()/flocktotalactivebird.toDouble()).toDouble()
            binding.txtHDEPValue.text =
                "${hdep*100} %"
        }

    }

    private fun validation() {
        if (binding.edtEggProduced.text!!.isEmpty() || binding.edtTotalMortality.text!!.isEmpty()
            || binding.edtFeedConsumed.text!!.isEmpty()
        ) {
            if (binding.edtEggProduced.text!!.isEmpty()) binding.edtEggProducedLayout.error =
                "Please enter valid Egg Produced"
            else binding.edtEggProducedLayout.isErrorEnabled = false

            if (binding.edtTotalMortality.text!!.isEmpty()) binding.edtTotalMortalityLayout.error =
                "Please enter valid Mortality"
            else binding.edtTotalMortalityLayout.isErrorEnabled = false
/*
            if (binding.edtBrokenEggProduced.text!!.isEmpty()) binding.edtBrokenEggProducedLayout.error =
                "Please enter valid Broken Egg Produced"
            else binding.edtBrokenEggProducedLayout.isErrorEnabled = false

            if (binding.edtBrokenEggInOperation.text!!.isEmpty()) binding.edtBrokenEggInOperationLayout.error =
                "Please enter valid Broken Egg In Operation"
            else binding.edtBrokenEggInOperationLayout.isErrorEnabled = false*/
/*
            if (binding.edtCullingBird.text!!.isEmpty()) binding.edtCullingBirdLayout.error =
                "Please enter valid Culling Bird"
            else binding.edtCullingBirdLayout.isErrorEnabled = false*/

            if (binding.edtFeedConsumed.text!!.isEmpty()) binding.edtFeedConsumedLayout.error =
                "Please enter valid Feed Consumed"
            else binding.edtFeedConsumedLayout.isErrorEnabled = false

        } else {
            binding.edtEggProducedLayout.isErrorEnabled = false
            binding.edtBrokenEggProducedLayout.isErrorEnabled = false
            binding.edtBrokenEggInOperationLayout.isErrorEnabled = false
            binding.edtCullingBirdLayout.isErrorEnabled = false
            binding.edtFeedConsumedLayout.isErrorEnabled = false
            binding.edtRemarkLayout.isErrorEnabled = false
            if (!error)
                submit()
            else Toast.makeText(context, "Error ", Toast.LENGTH_SHORT).show()

        }

    }

    private fun validation2(){

        if ( binding.edtTotalMortality.text!!.isEmpty()  || binding.edtFeedConsumed.text!!.isEmpty()||
                    binding.edtWeight.text!!.isEmpty()
        ) {

            if (binding.edtWeight.text!!.isEmpty()) binding.edtWeightLayout.error =
                "Please enter valid Weight"
            else binding.edtWeightLayout.isErrorEnabled = false

            if (binding.edtTotalMortality.text!!.isEmpty()) binding.edtTotalMortalityLayout.error =
                "Please enter valid Mortality"
            else binding.edtTotalMortalityLayout.isErrorEnabled = false


            if (binding.edtFeedConsumed.text!!.isEmpty()) binding.edtFeedConsumedLayout.error =
                "Please enter valid Feed Consumed"
            else binding.edtFeedConsumedLayout.isErrorEnabled = false

        } else {
            binding.edtCullingBirdLayout.isErrorEnabled = false
            binding.edtFeedConsumedLayout.isErrorEnabled = false
            binding.edtRemarkLayout.isErrorEnabled = false
            binding.edtWeightLayout.isErrorEnabled = false
            submit2()

        }

    }

    private fun submit2(){
        if (!binding.edtCullingBird.text?.isEmpty()!!)
            culling=binding.edtCullingBird.text.toString()

        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.addDailyInputWithWeight(
            token,flockid,lastupdate,Integer.parseInt(binding.edtTotalMortality.text.toString()),
            binding.edtFeedConsumed.text.toString().toInt(),culling.toInt(),binding.edtRemark.text.toString(),medlistidpost,medlistqnt,
            binding.edtWeight.text.toString().toInt()
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                Log.d("data","${it.toString()}")
                if (it.errorType!=null){
                    Toast.makeText(context, it.errors!![0].message,Toast.LENGTH_SHORT).show()
                    Log.d("data","${it.errors!![0].message}")

                }else{
                    Log.d("data","${it.date} ${it.brokenEggInOperation} ${flockid}")
                    Toast.makeText(context, "Saved",Toast.LENGTH_SHORT).show()
                    context.loadDailyInput()

                }

            }
        )


    }

    private fun submit() {
        if (!binding.edtBrokenEggProduced.text?.isEmpty()!!){
            brokenegg=binding.edtBrokenEggProduced.text.toString()
        }
        if (!binding.edtBrokenEggInOperation.text?.isEmpty()!!)
            brokeneggInoperation=binding.edtBrokenEggInOperation.text.toString()
        if (!binding.edtCullingBird.text?.isEmpty()!!)
            culling=binding.edtCullingBird.text.toString()

        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.addDailyInput(
            token,flockid,lastupdate,binding.edtEggProduced.text.toString().toInt(),Integer.parseInt(binding.edtTotalMortality.text.toString()),
            binding.edtFeedConsumed.text.toString().toInt(),culling.toInt(),brokeneggInoperation.toInt(),
            brokenegg.toInt(),binding.edtRemark.text.toString(),medlistidpost,medlistqnt
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                Log.d("data","${it.toString()}")
                if (it.errorType!=null){
                    Toast.makeText(context, it.errors!![0].message,Toast.LENGTH_SHORT).show()
                    Log.d("data","${it.errors!![0].message}")

                }else{
                    Log.d("data","${it.date} ${it.brokenEggInOperation} ${flockid}")
                    Toast.makeText(context, "Saved",Toast.LENGTH_SHORT).show()
                    context.loadDailyInput()

                }

            }
        )


    }

}