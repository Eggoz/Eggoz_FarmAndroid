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
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentDaiyInputUpdateBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.daily_input.model.DailInput
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.PrefrenceUtils
import com.antino.eggoz.view.CustomAlertLoading
import java.text.DecimalFormat

class DaiyInputUpdateFragment(val mcontext: MainActivity, val data: DailInput.Result) : Fragment() {

    private lateinit var binding: FragmentDaiyInputUpdateBinding

    var totalCulling = 0
    var totolMortality = 0
    var feedconsumed: Double = 0.0
    var totalEgg = 0


    private lateinit var decimalformat: DecimalFormat
    var hdep = 0.00


    private var brokenegg = "0"
    private var brokeneggInoperation = "0"
    private var culling = "0"

    private lateinit var token: String

    lateinit var medlistidpost: ArrayList<Int>
    lateinit var medlistqnt: ArrayList<Int>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaiyInputUpdateBinding.inflate(inflater, container, false)
        initView()
        dataSet()

        token = PrefrenceUtils.retriveData(requireContext(), Constants.ACCESS_TOKEN_PREFERENCE)!!
        return binding.root
    }

    private fun initView() {
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                mcontext.loadDailyInput()
                true
            } else false
        }

        decimalformat = DecimalFormat("#.##")

        totolMortality = data.mortality
        totalCulling = data.culls
        feedconsumed = data.feed.toDouble()
        totalEgg = data.eggDailyProduction - data.brokenEggInProduction

        binding.edtCullingBird.setText(totalCulling.toString())
        binding.edtTotalMortality.setText(totolMortality.toString())
        binding.edtFeedConsumed.setText(feedconsumed.toString())
        binding.edtEggProduced.setText(totalEgg.toString())
        binding.edtWeight.setText(data.weight)
        binding.edtBrokenEggProduced.setText(data.brokenEggInProduction.toString())
        binding.edtBrokenEggInOperation.setText(data.brokenEggInOperation.toString())

        binding.txtActiveBirdValue.text =
            "${data.totalActiveBirds - totolMortality - totalCulling}"
        binding.txtActiveBirdValue.text =
            "${data.totalActiveBirds - totolMortality - totalCulling}"
        binding.txtFeedIntakePerBirdValue.text =
            "${String.format("%.2f", (feedconsumed * 1000) / data.totalActiveBirds.toDouble())}gm"
        hdep =
            decimalformat.format(totalEgg.toDouble() / data.totalActiveBirds.toDouble()).toDouble()
        binding.txtHDEPValue.text =
            "${hdep * 100} %"
        binding.edtRemark.setText(data.remarks)
        if (data.weight.toDouble() == 0.0){
            binding.edtWeightLayout.visibility=View.GONE
            binding.txtWeight.visibility=View.GONE

            binding.edtEggProducedLayout.visibility=View.VISIBLE
            binding.txtEggPrduced.visibility=View.VISIBLE
            binding.edtBrokenEggInOperationLayout.visibility=View.VISIBLE
            binding.txtBrokenEggInOperation.visibility=View.VISIBLE
            binding.edtBrokenEggProducedLayout.visibility=View.VISIBLE
            binding.txtBrokenEggProduced.visibility=View.VISIBLE
            binding.txtHDEP.visibility=View.VISIBLE
            binding.txtHDEPValue.visibility=View.VISIBLE
        }
        else {

            binding.edtWeightLayout.visibility=View.VISIBLE
            binding.txtWeight.visibility=View.VISIBLE

            binding.edtEggProducedLayout.visibility=View.GONE
            binding.txtEggPrduced.visibility=View.GONE
            binding.edtBrokenEggInOperationLayout.visibility=View.GONE
            binding.txtBrokenEggInOperation.visibility=View.GONE
            binding.edtBrokenEggProducedLayout.visibility=View.GONE
            binding.txtBrokenEggProduced.visibility=View.GONE
            binding.txtHDEP.visibility=View.GONE
            binding.txtHDEPValue.visibility=View.GONE
        }


        binding.btnSubmit.setOnClickListener {
            if (data.weight.toDouble() == 0.0)
                validation()
            else
                validation2()
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
            submit2()

        }
    }

    private fun validation2() {

        if (binding.edtTotalMortality.text!!.isEmpty() || binding.edtFeedConsumed.text!!.isEmpty() ||
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

    private fun submit2() {
        if (!binding.edtCullingBird.text?.isEmpty()!!)
            culling = binding.edtCullingBird.text.toString()
        if (!binding.edtBrokenEggProduced.text?.isEmpty()!!)
            brokenegg = binding.edtBrokenEggProduced.text.toString()
        if (!binding.edtBrokenEggInOperation.text?.isEmpty()!!)
            brokeneggInoperation = binding.edtBrokenEggInOperation.text.toString()

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.addDailyInputWithWeightUpdate(
            token,
            data.date,
            Integer.parseInt(binding.edtTotalMortality.text.toString()),
            binding.edtFeedConsumed.text.toString().toDouble(),
            culling.toInt(),
            binding.edtRemark.text.toString(),
            binding.edtWeight.text.toString().toDouble(),
            data.id,
            totalEgg,
            brokenegg,
            brokeneggInoperation
        ).observe(requireActivity(),
            Observer {
                loadingdialog.dismiss()
                Log.d("data", "${it.toString()}")
                if (it.errorType != null) {
                    Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                    Log.d("data", "${it.errors!![0].message}")

                } else {
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show()
                    mcontext.loadDailyInput()

                }

            }
        )


    }

  /*  private fun submit() {
        if (!binding.edtBrokenEggProduced.text?.isEmpty()!!) {
            brokenegg = binding.edtBrokenEggProduced.text.toString()
        }
        if (!binding.edtBrokenEggInOperation.text?.isEmpty()!!)
            brokeneggInoperation = binding.edtBrokenEggInOperation.text.toString()
        if (!binding.edtCullingBird.text?.isEmpty()!!)
            culling = binding.edtCullingBird.text.toString()

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.addDailyInput(
            token,
            data.id,
            data.date,
            binding.edtEggProduced.text.toString().toInt(),
            Integer.parseInt(binding.edtTotalMortality.text.toString()),
            binding.edtFeedConsumed.text.toString().toInt(),
            culling.toInt(),
            brokeneggInoperation.toInt(),
            brokenegg.toInt(),
            binding.edtRemark.text.toString(),
            medlistidpost,
            medlistqnt
        ).observe(requireActivity(),
            Observer {
                loadingdialog.dismiss()
                Log.d("data", "${it.toString()}")
                if (it.errorType != null) {
                    Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                    Log.d("data", "${it.errors!![0].message}")

                } else {
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                    mcontext.loadDailyInput()

                }

            }
        )


    }
*/
    private fun dataSet() {

        binding.edtCullingBird.addTextChangedListener {
            totalCulling = if (it.toString() == "")
                0
            else
                Integer.parseInt(it.toString())
            binding.txtActiveBirdValue.text =
                "${data.totalActiveBirds - totolMortality - totalCulling}"
        }
        binding.edtTotalMortality.addTextChangedListener {
            if (it.toString() == "")
                totolMortality = 0
            else
                totolMortality = Integer.parseInt(it.toString())
            if (data.totalActiveBirds - totolMortality - totalCulling > 0)
                binding.txtActiveBirdValue.text =
                    "${data.totalActiveBirds - totolMortality - totalCulling}"
            else
                binding.txtActiveBirdValue.text = "0"
        }

        binding.edtFeedConsumed.addTextChangedListener {
            if (it.toString() == "")
                feedconsumed = 0.0
            else
                feedconsumed = it.toString().toDouble()
            binding.txtFeedIntakePerBirdValue.text =
                "${String.format(
                    "%.2f",
                    (feedconsumed * 1000) / data.totalActiveBirds.toDouble()
                )}gm"

        }
        binding.edtEggProduced.addTextChangedListener {
            totalEgg = if (it.toString() == "")
                0
            else
                Integer.parseInt(it.toString())
            hdep = decimalformat.format(totalEgg.toDouble() / data.totalActiveBirds.toDouble())
                .toDouble()
            binding.txtHDEPValue.text =
                "${hdep * 100} %"
        }

    }
}