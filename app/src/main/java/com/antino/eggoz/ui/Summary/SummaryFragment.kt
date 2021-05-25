package com.antino.eggoz.ui.Summary

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.VerifiedInputEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentSummaryBinding
import com.antino.eggoz.modelvew.ModelMain
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


class SummaryFragment(val context: MainActivity, val token: String, val flock_id: Int) :
    Fragment() {
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(inflater, container, false)

        loadData()
        spinner()


        return binding.root
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

                            binding.graphList.visibility=View.VISIBLE
                            binding.consNoGraph.visibility=View.GONE
                            for (i in it.results?.indices!!) {
                                hhdep =
                                    (it.results!![i].eggDailyProduction).toDouble() / (it.results!![i].totalActiveBirds.toDouble())
                                ffcr =
                                    (it.results!![i].feed).toDouble() / (it.results!![i].totalActiveBirds.toDouble())

                                if (feedval > 0) {
                                    feedval = (feedval + ffcr) / 2
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
                                binding.FeedPerBirdValue.text = feedval.toString()
                                binding.EggProductionValue.text = eggval.toString()

                                if (eggval==0) {
                                    binding.txtEggProduction.visibility=View.GONE
                                    binding.EggProductionValue.visibility=View.GONE
                                    binding.cardHdep.visibility=View.GONE
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


                        }else{

                            binding.graphList.visibility=View.GONE
                            binding.consNoGraph.visibility=View.VISIBLE
                        }

                    }else{
                        binding.graphList.visibility=View.GONE
                        binding.consNoGraph.visibility=View.VISIBLE
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


}