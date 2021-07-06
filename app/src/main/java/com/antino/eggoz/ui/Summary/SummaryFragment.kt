package com.antino.eggoz.ui.Summary

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentSummaryBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.daily_input.adapter.DailyInputAdapter
import com.antino.eggoz.ui.daily_input.model.DailInput
import com.antino.eggoz.view.CustomAlertLoading
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SummaryFragment(val context: MainActivity, val token: String, private val flock_id: Int) :
    Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentSummaryBinding

    private var mortality = ArrayList<Int>()
    private var hdep = ArrayList<Double>()
    private var fcr = ArrayList<Double>()

    private var date = ArrayList<String>()

    private var mortalityval = 0
    private var feedval: Double = 0.00
    private var eggval = 0

    private var hhdep = 0.0
    private var ffcr = 0.0
    private lateinit var dpd: DatePickerDialog

    private var filter: ArrayList<DailInput.Result>? = null
    private var result: List<DailInput.Result>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)

        init()

        loadData()
        Log.d("data", "flock id $flock_id")
        spinner()

        return binding.root
    }

    private fun init() {
        result = ArrayList()
        filter = ArrayList()
        binding.txtDate.text=this.resources.getString(com.antino.eggoz.R.string.SelectDate)
        binding.txtClear.visibility = View.GONE

        binding.txtClear.setOnClickListener {
            filter!!.clear()
            binding.txtDate.text=this.resources.getString(com.antino.eggoz.R.string.SelectDate)
            binding.txtClear.visibility = View.GONE
            val adapter = DailyInputAdapter(context, result)
            binding.recycleList.adapter = adapter
            adapter.notifyDataSetChanged()
        }

        val calander = Calendar.getInstance()
        dpd = DatePickerDialog(
            requireContext(),
            this,
            calander.get(Calendar.YEAR),
            calander.get(Calendar.MONTH),
            calander.get(Calendar.DATE)
        )
        binding.crdGraph.setOnClickListener {
            if (binding.constRecycle.isVisible) {
                mortality.clear()
                hdep.clear()
                fcr.clear()
                date.clear()

                loadData()
                binding.consGraph.visibility = View.VISIBLE
                binding.constRecycle.visibility = View.GONE
            }

        }
        binding.crdDate.setOnClickListener {
            dpd.show()
        }
        binding.txtDate.setOnClickListener {
            dpd.show()
        }
        binding.crdList.setOnClickListener {
            if (binding.consGraph.isVisible) {

                binding.txtDate.text=this.resources.getString(com.antino.eggoz.R.string.SelectDate)
                binding.txtClear.visibility = View.GONE

                getlist()
                binding.consGraph.visibility = View.GONE
                binding.constRecycle.visibility = View.VISIBLE
            }
        }




        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadDailyInput()
                true
            } else false
        }

    }


    private fun getlist() {

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        Log.d("data", "comment id $id")
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getDailyInput(
            token, flock_id
        ).observe(requireActivity(),
            Observer {
                loadingdialog.dismiss()
                if (it.errors == null) {

                    Log.d("data", "daily input list ${it.results?.size!!}")
                    binding.recycleList.layoutManager = LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    result = it.results
                    binding.recycleList.setHasFixedSize(true)
                    val adapter = DailyInputAdapter(context, it.results)
                    binding.recycleList.adapter = adapter

                     val sdf = SimpleDateFormat("yyyy-MM-dd")
                     val statedate = sdf.parse(it.results[0].date)
                     val calstart = Calendar.getInstance()
                     calstart.time = statedate

                     val enddate = sdf.parse(it.results[it.results.size - 1].date)
                     val calend = Calendar.getInstance()
                     calend.time = enddate

                     dpd.datePicker.minDate = calstart.timeInMillis
                     dpd.datePicker.maxDate = calend.timeInMillis

                } else {
                    Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                }


            }
        )
    }

    private fun loadData() {

        Log.d("data", "token $token, flock $flock_id, ")


        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getflockData(
            token, flock_id, 0
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                if (it.errors == null) {

                    if (it.results != null) {

                        if (it.results?.isNotEmpty()!!) {

                            binding.graphList.visibility = View.VISIBLE
                            binding.consNoGraph.visibility = View.GONE
                            for (i in it.results?.indices!!) {
                                hhdep =
                                    (it.results!![i].eggDailyProduction).toDouble() / (it.results!![i].totalActiveBirds.toDouble())
                                ffcr =
                                    (it.results!![i].feed).toDouble() / (it.results!![i].totalActiveBirds.toDouble())

                                if (feedval > 0) {
                                    feedval = (feedval + ffcr) / 2
                                } else {
                                    feedval = ffcr
                                }
                                eggval += it.results!![i].eggDailyProduction

                                mortalityval += it.results!![i].mortality
                                mortality.add(it.results!![i].mortality)

                                Log.d("data", "hdep $hhdep fcr $ffcr")
                                hdep.add(hhdep)
                                fcr.add(ffcr)


                                val df = SimpleDateFormat("MMM dd", Locale.getDefault())
                                val ddate: Date =
                                    SimpleDateFormat("yyyy-MM-dd").parse("${it.results!![i].date}")
                                val newDate = Date(ddate.time)
                                val formattedtodayDate = df.format(newDate)


                                date.add(formattedtodayDate)
                            }

                            if (it.results!!.isNotEmpty()) {

                                binding.initialTotalBirdsValue.text =
                                    it.results!![it.results!!.size - 1].totalActiveBirds.toString()
                                binding.MotalityValue.text =
                                    mortalityval.toString()
                                binding.FeedPerBirdValue.text = "${String.format(
                                    "%.2f",
                                    feedval * 1000
                                ).toDouble()}"
                                binding.EggProductionValue.text = eggval.toString()

                                if (eggval == 0) {
                                    binding.txtEggProduction.visibility = View.GONE
                                    binding.EggProductionValue.visibility = View.GONE
                                    binding.cardHdep.visibility = View.GONE
                                }

                            }
                            if (mortality.size > 0)
                                mortalityGraph()
                            else binding.cardMotality.visibility = View.GONE
                            if (hdep.size > 0)
                                hdepGraph()
                            else binding.cardHdep.visibility = View.GONE
                            if (fcr.size > 0)
                                feedGraph()
                            else binding.cardFcr.visibility = View.GONE
                            Log.d(
                                "data",
                                " mortality : ${mortality.size} \n hdep: ${hdep.size} \n fcr :${fcr.size}"
                            )


                        } else {

                            binding.graphList.visibility = View.GONE
                            binding.consNoGraph.visibility = View.VISIBLE
                        }

                    } else {
                        binding.graphList.visibility = View.GONE
                        binding.consNoGraph.visibility = View.VISIBLE
                    }
                } else Toast.makeText(context, "${it.errors!![0].message}", Toast.LENGTH_SHORT)
                    .show()
            }
        )


    }

    @SuppressLint("SetTextI18n")
    private fun mortalityGraph() {


        val dataline1: ArrayList<Entry> = ArrayList()
        for (i in 0 until mortality.size) {
            dataline1.add(Entry(i.toFloat(), mortality[i].toFloat()))
        }


        val linedataset1 = LineDataSet(dataline1, "Mortality")
        linedataset1.setDrawCircles(false)

        linedataset1.color = Color.BLACK


        val linedata = LineData(linedataset1)

        binding.lineChartMotality.data = linedata


        val lineChart2week = date

        binding.lineChartMotality.description.isEnabled = false
        binding.lineChartMotality.setDrawGridBackground(false)

        val xaxis = binding.lineChartMotality.xAxis
        xaxis.setDrawGridLines(false)
        xaxis.position = XAxis.XAxisPosition.BOTTOM
        xaxis.granularity = 1f
        xaxis.setDrawLabels(true)
        xaxis.setDrawAxisLine(false)
        xaxis.valueFormatter = IndexAxisValueFormatter(lineChart2week)

        val yAxisLeft = binding.lineChartMotality.axisLeft
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.isEnabled = false

        binding.lineChartMotality.axisRight.isEnabled = false
        binding.lineChartMotality.setTouchEnabled(true)
        binding.lineChartMotality.setPinchZoom(false)


        val name = arrayListOf<String>("Ammonia", "Tempreture", "Humidity")


        /* val cview = HomeFragment.CustomMarkerView(
             requireContext(),
             R.layout.item_customarkerview,
             dataline1,
             dataline2,
             dataline3, binding.lineChartMotality,
             R.color.Ammonia, R.color.Tempreture,
             R.color.Humidity, name
         )*/

        binding.lineChartMotality.animateXY(2000, 2000)
//        binding.lineChartMotality.marker = cview
        binding.lineChartMotality.setScaleEnabled(false);

        val legend = binding.lineChartMotality.legend
        legend.isEnabled = false


        binding.lineChartMotality.invalidate()
        binding.lineChartMotality.notifyDataSetChanged()


    }

    @SuppressLint("SetTextI18n")
    private fun hdepGraph() {


        val dataline1: ArrayList<Entry> = ArrayList()
        for (i in 0 until hdep.size) {
            dataline1.add(Entry(i.toFloat(), hdep[i].toFloat()))
        }


        val linedataset1 = LineDataSet(dataline1, "HDEP")
        linedataset1.setDrawCircles(false)

        linedataset1.color = Color.BLUE


        val linedata = LineData(linedataset1)

        binding.lineChartHdep.data = linedata


        val lineChart2week = date

        binding.lineChartHdep.description.isEnabled = false
        binding.lineChartHdep.setDrawGridBackground(false)

        val xaxis = binding.lineChartHdep.xAxis
        xaxis.setDrawGridLines(false)
        xaxis.position = XAxis.XAxisPosition.BOTTOM
        xaxis.granularity = 1f
        xaxis.setDrawLabels(true)
        xaxis.setDrawAxisLine(false)
        xaxis.valueFormatter = IndexAxisValueFormatter(lineChart2week)

        val yAxisLeft = binding.lineChartHdep.axisLeft
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.isEnabled = false

        binding.lineChartHdep.axisRight.isEnabled = false
        binding.lineChartHdep.setTouchEnabled(true)
        binding.lineChartHdep.setPinchZoom(false)


        val name = arrayListOf<String>("Ammonia", "Tempreture", "Humidity")


        /* val cview = HomeFragment.CustomMarkerView(
             requireContext(),
             R.layout.item_customarkerview,
             dataline1,
             dataline2,
             dataline3, binding.lineChartMotality,
             R.color.Ammonia, R.color.Tempreture,
             R.color.Humidity, name
         )*/

        binding.lineChartHdep.animateXY(2000, 2000)
//        binding.lineChartMotality.marker = cview
        binding.lineChartHdep.setScaleEnabled(false);

        val legend = binding.lineChartHdep.legend
        legend.isEnabled = false


        binding.lineChartHdep.invalidate()
        binding.lineChartHdep.notifyDataSetChanged()


    }

    @SuppressLint("SetTextI18n")
    private fun feedGraph() {


        val dataline1: ArrayList<Entry> = ArrayList()
        for (i in 0 until fcr.size) {
            dataline1.add(Entry(i.toFloat(), fcr[i].toFloat()))
        }


        val linedataset1 = LineDataSet(dataline1, "FIPB")
        linedataset1.setDrawCircles(false)

        linedataset1.color = Color.GREEN


        val linedata = LineData(linedataset1)

        binding.lineChartFcr.data = linedata


        val lineChart2week = date

        binding.lineChartFcr.description.isEnabled = false
        binding.lineChartFcr.setDrawGridBackground(false)

        val xaxis = binding.lineChartFcr.xAxis
        xaxis.setDrawGridLines(false)
        xaxis.position = XAxis.XAxisPosition.BOTTOM
        xaxis.granularity = 1f
        xaxis.setDrawLabels(true)
        xaxis.setDrawAxisLine(false)
        xaxis.valueFormatter = IndexAxisValueFormatter(lineChart2week)

        val yAxisLeft = binding.lineChartFcr.axisLeft
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.isEnabled = false

        binding.lineChartFcr.axisRight.isEnabled = false
        binding.lineChartFcr.setTouchEnabled(true)
        binding.lineChartFcr.setPinchZoom(false)


        val name = arrayListOf<String>("Ammonia", "Tempreture", "Humidity")


        /* val cview = HomeFragment.CustomMarkerView(
             requireContext(),
             R.layout.item_customarkerview,
             dataline1,
             dataline2,
             dataline3, binding.lineChartMotality,
             R.color.Ammonia, R.color.Tempreture,
             R.color.Humidity, name
         )*/

        binding.lineChartFcr.animateXY(2000, 2000)
//        binding.lineChartMotality.marker = cview
        binding.lineChartFcr.setScaleEnabled(false);

        val legend = binding.lineChartHdep.legend
        legend.isEnabled = false


        binding.lineChartFcr.invalidate()
        binding.lineChartFcr.notifyDataSetChanged()


    }


    private fun spinner() {


        val option = arrayOf("This week ", "year ")
        binding.spinnerSummaryTitle1.adapter = context?.applicationContext?.let {
            ArrayAdapter<String>(
                it, R.layout.spinner_text, option
            )
        }
        binding.spinnerSummaryTitle1.adapter = context?.applicationContext?.let {
            ArrayAdapter<String>(
                it, R.layout.spinner_text, option
            )
        }

        binding.spinnerSummaryTitle1.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener,
                AdapterView.OnItemClickListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d("data", "$position")
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {


                }

                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }


            }

    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        filter?.clear()
        var date = ""
        date = if (p0?.month!!+1 < 10) {
            "${p0.year}-0${p0.month+1}-${p0.dayOfMonth}"

        } else
            "${p0.year}-${p0.month+1}-${p0.dayOfMonth}"
        binding.txtDate.text = date
        binding.txtClear.text=this.resources.getString(com.antino.eggoz.R.string.clear)
        binding.txtClear.visibility = View.VISIBLE
        binding.txtDate.visibility = View.VISIBLE
        for (i in 0 until result?.size!!) {
            Log.d("data", "ser date:${result!![i].date}=${date}")
            if (result!![i].date == date) {
                filter?.add(result!![i])
            }
        }

        val adapter = DailyInputAdapter(context, filter)
        binding.recycleList.adapter = adapter
        adapter.notifyDataSetChanged()
    }


}