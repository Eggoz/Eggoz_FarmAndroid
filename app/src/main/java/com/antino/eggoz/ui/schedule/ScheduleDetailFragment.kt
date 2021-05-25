package com.antino.eggoz.ui.schedule

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentScheduleDetailBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.view.CustomAlertLoading
import com.google.android.material.textfield.TextInputLayout
import java.util.*
import kotlin.collections.ArrayList

class ScheduleDetailFragment(
    private val context: MainActivity,
    private val token: String,
    private val mid: Int
) :
    Fragment(), AdditemDialog {
    private lateinit var binding: FragmentScheduleDetailBinding

    private val other_product: ArrayList<String> = ArrayList()
    private val quantity: ArrayList<String> = ArrayList()
    private val totalRate: ArrayList<String> = ArrayList()
    private val totalamt: ArrayList<String> = ArrayList()
    private val remark: ArrayList<String> = ArrayList()
    private lateinit var adapter: ScheduleitemlistAdapter

    private var date: String = ""
    private var whiteEgg: Int = 0
    private var brownEgg: Int = 0
    private var KadaknathEgg = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        binding = FragmentScheduleDetailBinding.inflate(inflater, container, false)



        init()
        initaddOther()

        return binding.root
    }

    private fun init() {

        binding.btnSubmit.setOnClickListener {
            validate()
        }

        binding.edtSelectDays.setOnClickListener {
            Log.d("data", "click date")
            closeKeyboard()
            datePicker()
        }

        binding.edtSelectDaysLayout.endIconMode = TextInputLayout.END_ICON_CUSTOM
        binding.edtSelectDays.keyListener = null
        binding.edtSelectDaysLayout.setEndIconDrawable(R.drawable.ic_baseline_calendar_today_24)
        binding.edtSelectDaysLayout.setEndIconOnClickListener {
            closeKeyboard()
            datePicker()
        }

    }

    private fun closeKeyboard() {
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun initaddOther() {
/* binding.btnAddOtherProduct.setOnClickListener {
            AddOtherItemDialog(this).show(parentFragmentManager, "MyCustomFragment")
        }*/
        /*

        binding.recyclerViewOterProduct.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewOterProduct.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewOterProduct.isNestedScrollingEnabled = false
        adapter=ScheduleitemlistAdapter(this,other_product, quantity, totalRate, totalamt, remark)
        binding.recyclerViewOterProduct.adapter= adapter
*/
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_search).isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("SetTextI18n")
    private fun datePicker() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            requireContext(),
            R.style.DialogTheme,
            { view, year, monthOfYear, dayOfMonth ->
                binding.edtSelectDays.setText("$year-$monthOfYear-$dayOfMonth")
            },
            year,
            month,
            day
        )
        dpd.show()
        dpd.getButton(DatePickerDialog.BUTTON_NEGATIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.app_color))
        dpd.getButton(DatePickerDialog.BUTTON_POSITIVE)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.app_color))
    }

    private fun validate() {
        if (binding.edtSelectDays.text!!.isEmpty() || binding.edtBrownEgg.text!!.isEmpty() ||
            binding.edtWhiteEggs.text!!.isEmpty() || binding.edtKadaknathEggs.text!!.isEmpty()
        ) {
            if (binding.edtSelectDays.text!!.isEmpty()) binding.edtSelectDaysLayout.error =
                "Please enter valid Selection Days"
            else binding.edtSelectDaysLayout.isErrorEnabled = false

            if (binding.edtBrownEgg.text!!.isEmpty()) binding.edtBrownEggLayout.error =
                "Please enter valid Brown Egg"
            else binding.edtBrownEggLayout.isErrorEnabled = false

            if (binding.edtWhiteEggs.text!!.isEmpty()) binding.edtWhiteEggsLayout.error =
                "Please enter valid White Eggs"
            else binding.edtWhiteEggsLayout.isErrorEnabled = false

            if (binding.edtKadaknathEggs.text!!.isEmpty()) binding.edtKadaknathEggsLayout.error =
                "Please enter valid Kadaknath Eggs"
            else binding.edtKadaknathEggsLayout.isErrorEnabled = false


        } else {
            binding.edtSelectDaysLayout.isErrorEnabled = false
            binding.edtBrownEggLayout.isErrorEnabled = false
            binding.edtKadaknathEggsLayout.isErrorEnabled = false
            binding.edtWhiteEggsLayout.isErrorEnabled = false
            submit()


        }
    }

/*    override fun onclick(pos: String, qnt: String, rate: String, totalamt: String, remark: String) {
        other_product.add(pos)
        quantity.add(qnt)
        totalRate.add(rate)
        this.totalamt.add(totalamt)
        this.remark.add(remark)
        (binding.recyclerViewOterProduct.adapter as ScheduleitemlistAdapter).notifyDataSetChanged()
    }*/

    override fun custom_dialog(pos: Int) {
        UpdateOtherItemDialog(
            pos, this, other_product[pos],
            quantity[pos],
            totalRate[pos],
            this.totalamt[pos],
            this.remark[pos]
        ).show(parentFragmentManager, "MyCustomFragment")
    }

    /* override fun updateclick(
         pos: String,
         position: Int,
         qnt: String,
         rate: String,
         totalamt: String,
         remark: String
     ) {
         other_product[position] = pos
         quantity[position] = qnt
         totalRate[position] = rate
         this.totalamt[position] = totalamt
         this.remark[position] = remark
         (binding.recyclerViewOterProduct.adapter as ScheduleitemlistAdapter).notifyDataSetChanged()
     }

     override fun delete(pos: Int) {
         other_product.removeAt(pos)
         quantity.removeAt(pos)
         totalRate.removeAt(pos)
         this.totalamt.removeAt(pos)
         this.remark.removeAt(pos)
         (binding.recyclerViewOterProduct.adapter as ScheduleitemlistAdapter).notifyDataSetChanged()
     }*/

    private fun submit() {
        date = binding.edtSelectDays.text.toString()
        whiteEgg = binding.edtWhiteEggs.text.toString().toInt()
        brownEgg = binding.edtBrownEgg.text.toString().toInt()
        KadaknathEgg = binding.edtKadaknathEggs.text.toString().toInt()

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()
        ViewModelProvider(this).get(ModelMain::class.java).apply {
            addSchedule(
                token, mid, date, whiteEgg, brownEgg, KadaknathEgg
            ).observe(context,
                Observer {
                    loadingdialog.dismiss()
                    if (it.success != null) {
                        context.loadSchedule()
                    } else {
                        Log.d("data", "Error ${it}")
                        Log.d("data", "Error2 ${it.errors!![0].message} ${it.errors!![0].field}")
                        Toast.makeText(context, "${it.errorType}", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }

    }
}