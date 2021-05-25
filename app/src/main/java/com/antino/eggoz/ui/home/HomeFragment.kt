package com.antino.eggoz.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentHomeBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.home.adapter.AlertAdapter
import com.antino.eggoz.ui.home.adapter.AlertFarmAdapter
import com.antino.eggoz.ui.home.adapter.ProductAdapter
import com.antino.eggoz.ui.home.adapter.SuggestionVedioAdapter
import com.antino.eggoz.ui.home.dialog.NeccRateAll
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.PrefrenceUtils
import com.antino.eggoz.view.CustomAlertLoading
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment(val context: MainActivity) : Fragment(), HomeFragmentCallback {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    private lateinit var token: String
    private lateinit var mid: String

    private var necczone: Int = -1

    private lateinit var locationManager: LocationManager
    private var gpsListener: LocationListener? = null
    private var networkListener: LocationListener? = null
    private var requestcheckper = 1000

    private var isdetach = false


    private var gpslocation = false


    //location
    private lateinit var locationRequest: LocationRequest
    private var requestchecksetting = 1001
    private var listner: MyLocationListener? = null


    //loading
    private var loadProfile: Boolean = false
    private var neccrate: Boolean = false
    private var videolist: Boolean = false
    private var productlist: Boolean = false
    private var suggestion: Boolean = false
    private var temp: Boolean = false
    private var iot: Boolean = false
    private var farmdata: Boolean = false

    private lateinit var loadingdialog: CustomAlertLoading
    private val colarlocation=205
    private val gpslocationrequest=206

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        init()

        token = PrefrenceUtils.retriveData(context, Constants.ACCESS_TOKEN_PREFERENCE)!!
        mid = PrefrenceUtils.retriveData(context, Constants.ID)!!

        Apicall()



        binding.recycleHomeSlider1

        return view
    }

    private fun Apicall() {

        checkpermisssion()
        loadProfile()
        farmData()

        loadingcheck()

        product()
        Alert()
        suggestion()
        suggestionVedio()

    }

    private fun IotApi(){
        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        Log.d("data","comment id $id")
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.iotRequest(
            token
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                if (it.errors==null){
                   Toast.makeText(context,"${it.success}",Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(context,it.errors!![0].message,Toast.LENGTH_SHORT).show()
                }


            }
        )

    }


    private fun checkpermisssion(){
        if (ActivityCompat.checkSelfPermission(
                activity?.applicationContext!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity?.applicationContext!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            checkgps()
            Log.d("data","permission avv")
        }else{
            ActivityCompat.requestPermissions(
                (context as Activity?)!!,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                colarlocation
            )
        }


    }

    private fun checkgps(){


            val manager =
                context.getSystemService(LOCATION_SERVICE) as LocationManager
            val statusOfGPS =
                manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        Log.d("data", "gps status$statusOfGPS")
            if (!statusOfGPS) {
                loc()
            } else {

                getlocation()
            }
    }


    private fun loadingcheck() {
        if (!loadProfile && !neccrate && !videolist && !productlist && !suggestion && !temp && !iot && !farmdata) {
            loadingdialog.stateLoading()
        } else {
            loadingdialog.dismiss()
        }
    }

    private fun init() {

        binding.floatingConsult.setOnClickListener { context.loadConsulting() }

        binding.floatingConsult.extend()
        binding.floatingConsult.icon.colorFilter =
            PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP)


        loadingdialog = CustomAlertLoading(this)
        binding.nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (scrollY > 100) {
                binding.floatingConsult.shrink()
            } else {
                binding.floatingConsult.extend()
            }

            binding.floatingConsult.isGone = scrollY >= (binding.completeLayout.height - 2000)
        }


        binding.crdDailyInputMissed.setOnClickListener {
            context.HomeCallbackdailyInputMissed()
        }
    }

    //iot alert
    private fun iotAlert(){

        binding.btnAddIot.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Iot Service")
            builder.setMessage("Are you sure want to request iot service")
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            builder.setPositiveButton("Yes"){dialogInterface, which ->
                dialogInterface.dismiss()
                IotApi()
            }

            builder.setNegativeButton("No"){dialogInterface, which ->
                dialogInterface.dismiss()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()

        }

    }

    //farm data
    private fun farmData() {

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getFarm(
            token, mid.toInt()
        ).observe(context,
            Observer {
                farmdata = true

                for (i in it.results?.indices!!){
                    for (j in it.results!![i].sheds!!.indices){
                        for (k in it.results!![i].sheds!![j].flocks!!.indices){
                            val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                            val c = Calendar.getInstance().time
                            if (it.results!![i].sheds!![j].flocks!![k].lastDailyInputDate==null){
                                binding.textView29.visibility=View.VISIBLE
                                binding.crdDailyInputMissed.visibility=View.VISIBLE
                            }

                            if (df.format(c).toString()!=it.results!![i].sheds!![j].flocks!![k].lastDailyInputDate){
                                binding.textView29.visibility=View.VISIBLE
                                binding.crdDailyInputMissed.visibility=View.VISIBLE
                            }

                        }
                    }
                }

                loadingcheck()
                if (it.count == null) {
                    binding.btnFarmDetail.text = context.resources.getString(com.antino.eggoz.R.string.add_farm)
                } else {
                    binding.ttaddfarmdetail.text= context.resources.getString(com.antino.eggoz.R.string.expand_farm)
                    binding.btnFarmDetail.text = context.resources.getString(com.antino.eggoz.R.string.add_more_farm)
                }

            }
        )


    }

    //call get profile
    private fun loadProfile() {


        Log.d("data", "loadProfile api start 265")

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.farmerProfile(
            token, mid.toInt()
        ).observe(viewLifecycleOwner,
            Observer {
                loadProfile = true
                loadingcheck()
                Log.d("data", "loadProfile api end")

                if (it.errors == null) {

                    if (it.neccZone==null){
                        necczone=-1
                    }else necczone = it.neccZone!!
                    slider1()
                    if (it.farmer_iot_id != "non-iot") {
                        loadIot(it.farmer_iot_id)
                        binding.alertAddIot.visibility=View.GONE
                    } else {
                        binding.crdTempreture.visibility = View.GONE
                        binding.crdHumidity.visibility = View.GONE
                        binding.crdAmmonia.visibility = View.GONE
                        binding.alertAddIot.visibility=View.VISIBLE
                        iot = true

                        iotAlert()
                    }
                } else {
                    Toast.makeText(context, "${it.errors!![0].message}", Toast.LENGTH_SHORT).show()
                }

            }
        )


    }

    private fun loadIot(id: String) {

        Log.d("data", "loadIot api start 301")

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getIot(
            id
        ).observe(viewLifecycleOwner,
            Observer {
                loadProfile = true
                loadingcheck()
                if (it.message == null) {
                    iot = true
                    loadingcheck()

                    binding.crdTempreture.visibility = View.VISIBLE
                    binding.crdHumidity.visibility = View.VISIBLE
                    binding.crdAmmonia.visibility = View.VISIBLE

                    binding.txtTemVal.text = "${it.temperatureval} °C"
                    binding.txtHumidityVal.text = "${it.humidityval} %"
                    binding.txtAmmoniaVal.text = "${it.nh3val} ppm"

                } else {
                    Toast.makeText(context, "loadIot ${it.message}", Toast.LENGTH_SHORT).show()
                }

            })

    }


  /*  private fun buttonClick() {

        binding.floatingConsult.setOnClickListener { context.loadConsulting() }

        *//*
        binding.crdTempreture.setOnClickListener {
            if (binding.cardDailyReport.visibility == View.VISIBLE) {
                binding.cardDailyReport.visibility = View.GONE
            } else binding.cardDailyReport.visibility = View.VISIBLE
            val anim = ScaleAnimation(1f, 1f, 0.5f, 1f)
            anim.duration = 1500
            binding.cardDailyReport.startAnimation(anim)
        }
        binding.crdHumidity.setOnClickListener {
            if (binding.cardDailyReport.visibility == View.VISIBLE) {
                binding.cardDailyReport.visibility = View.GONE
            } else binding.cardDailyReport.visibility = View.VISIBLE
            val anim = ScaleAnimation(1f, 1f, 0.5f, 1f)
            anim.duration = 1500
            binding.cardDailyReport.startAnimation(anim)
        }
        binding.crdAmmonia.setOnClickListener {
            if (binding.cardDailyReport.visibility == View.VISIBLE) {
                binding.cardDailyReport.visibility = View.GONE
            } else binding.cardDailyReport.visibility = View.VISIBLE
            val anim = ScaleAnimation(1f, 1f, 0.5f, 1f)
            anim.duration = 1500
            binding.cardDailyReport.startAnimation(anim)
        }*//*
    }*/

    override fun loadall() {
        loadAllnecdata()
    }

    private fun slider1() {


        binding.recycleHomeSlider1.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.recycleHomeSlider1.setHasFixedSize(true)

        if (necczone != -1 && necczone != null) {

            Log.d("data", "slider1 api start 380")
            val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
            viewModel.neccRate(
                token, necczone
            ).observe(viewLifecycleOwner,
                Observer {
                    neccrate = true
                    loadingcheck()
                    Log.d("data", "slider1 api end")
                    if (it.errors == null) {
                        if (it.results != null && it.results!!.isNotEmpty())
                            binding.recycleHomeSlider1.adapter = HomeSliderAdapter(this, it.results)
                        else
                            binding.recycleHomeSlider1.visibility = View.GONE
                    } else {
                        Toast.makeText(context, "${it.errors!![0].message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            )

        } else {
            binding.recycleHomeSlider1.visibility = View.GONE
        }


    }

    private fun loadAllnecdata() {

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.neccRate(
            token
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                if (it?.errors == null) {
                    NeccRateAll(this, it.results).show(
                        context.supportFragmentManager,
                        "MyCustomFragment2"
                    )

                } else {
                    Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                }
            }
        )

    }


    //call get populal product
    private fun product() {

        Log.d("data", "product api start 263")
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.popularProducts(
            token
        ).observe(context,
            Observer {
                productlist = true
                loadingcheck()
                Log.d("data", "product api end")
                if (it.errors == null) {
                    if (it?.count != null && it.count > 0) {
                        val LayoutManager = LinearLayoutManager(
                            activity,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                        binding.recyceleviewProductspopulars.layoutManager = LayoutManager
                        binding.recyceleviewProductspopulars.itemAnimator = DefaultItemAnimator()
                        binding.recyceleviewProductspopulars.isNestedScrollingEnabled = false
                        if (it.results != null)
                            binding.recyceleviewProductspopulars.adapter =
                                ProductAdapter(context, it.results!!)
                    }
                } else Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
            }
        )


    }

    private fun Alert() {

        val img: ArrayList<String> = ArrayList()
        val title: ArrayList<String> = ArrayList()
        val whiteegg: ArrayList<String> = ArrayList()
        val brownegg: ArrayList<String> = ArrayList()
        val timestamp: ArrayList<String> = ArrayList()

//        img.add("https://images.media-allrecipes.com/userphotos/4507923.jpg")
//        img.add("https://hips.hearstapps.com/del.h-cdn.co/assets/17/19/1494614947-delish-philly-cheesesteak-omelette-1.jpg")
//        img.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWZ6OorE_8p7WGQ1HXG9iNvzLbf_XNuyTrUA&usqp=CAU")

        img.add("https://eggoz-android.s3.ap-south-1.amazonaws.com/AndroidDummy/MicrosoftTeams-image+(1).png")
        img.add("https://eggoz-android.s3.ap-south-1.amazonaws.com/AndroidDummy/MicrosoftTeams-image+(1).png")
        img.add("https://eggoz-android.s3.ap-south-1.amazonaws.com/AndroidDummy/MicrosoftTeams-image+(1).png")
        title.add("Egg Sechdule")
        title.add("Egg Sechdule")
        title.add("Egg Sechdule")
        whiteegg.add("White Eggs\t 2332")
        whiteegg.add("White Eggs\t 2332")
        whiteegg.add("White Eggs\t 2332")
        brownegg.add("Brown Eggs\t 7777")
        brownegg.add("Brown Eggs\t 2222")
        brownegg.add("Brown Eggs\t 1111")
        timestamp.add("Yesterday")
        timestamp.add("Yesterday")
        timestamp.add("Yesterday")
        val layout = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
//        layout.reverseLayout = true
        binding.recyceleviewAlerts.layoutManager = layout
        binding.recyceleviewAlerts.setHasFixedSize(true)
        binding.recyceleviewAlerts.adapter = AlertAdapter(img, title, whiteegg, brownegg, timestamp)
        binding.recyceleviewAlerts.scrollToPosition(img.size)

        binding.btnAddSechdule.setOnClickListener { context.loadSchedule() }

        binding.btnAddFarm.setOnClickListener { context.addFarm() }


        val img2: ArrayList<String> = ArrayList()
        val title2: ArrayList<String> = ArrayList()

        img2.add("https://images.media-allrecipes.com/userphotos/4507923.jpg")
        img2.add("https://hips.hearstapps.com/del.h-cdn.co/assets/17/19/1494614947-delish-philly-cheesesteak-omelette-1.jpg")
        img2.add("https://hips.hearstapps.com/del.h-cdn.co/assets/17/19/1494614947-delish-philly-cheesesteak-omelette-1.jpg")
//        img2.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSWZ6OorE_8p7WGQ1HXG9iNvzLbf_XNuyTrUA&usqp=CAU")

        title2.add("Turn of Fan")
        title2.add("Turn on Fan")
        title2.add("Open the lid")

        binding.recyceleviewAlertsFarm.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.recyceleviewAlertsFarm.setHasFixedSize(true)
        binding.recyceleviewAlertsFarm.adapter = AlertFarmAdapter(img2, title2)


    }

/*    private fun LiveIOTUpdate() {

        val section1 = DonutSection(
            name = "section_1",
            color = Color.parseColor("#FF6F9B"),
            amount = 70f
        )

        val section2 = DonutSection(
            name = "section_2",
            color = Color.parseColor("#FF6F9B"),
            amount = 30f
        )

        val sectionback = DonutSection(
            name = "section_2",
            color = Color.parseColor("#F6D8E2"),
            amount = 100f
        )

        binding.donutViewTemp.cap = 100f
        binding.donutViewTemp.gapWidthDegrees = 180f
        binding.donutViewTemp.submitData(listOf(section2))


        binding.donutViewTemp1.cap = 100f
        binding.donutViewTemp1.submitData(listOf(sectionback))


    }*/

    //comment
 /*   private fun spinner() {

        val option = arrayOf("This week ", "year ")
        binding.spinnerHomeTitle2.adapter = context.applicationContext?.let {
            ArrayAdapter<String>(
                it, R.layout.spinner_text, option
            )
        }
        binding.spinnerHomeTitle1.adapter = context.applicationContext?.let {
            ArrayAdapter<String>(
                it, R.layout.spinner_text, option
            )
        }

        binding.spinnerHomeTitle2.onItemSelectedListener =
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

    private fun spinnerIotReport() {

        val option = arrayOf("All ", "Ammonia ", "Tempreture ", "Humidity ")
        binding.spinnerDailyreport.adapter = context.applicationContext?.let {
            ArrayAdapter<String>(
                it, R.layout.spinner_text, option
            )
        }
        binding.spinnerDailyreport.adapter = context.applicationContext?.let {
            ArrayAdapter<String>(
                it, R.layout.spinner_text, option
            )
        }

        binding.spinnerDailyreport.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener,
                AdapterView.OnItemClickListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            Log.d("data", "dailyreport 0")
                            binding.lineChart2.fitScreen()
                            binding.lineChart2.data?.clearValues()
                            binding.lineChart2.xAxis.valueFormatter = null
                            binding.lineChart2.notifyDataSetChanged()
                            binding.lineChart2.clear()
                            binding.lineChart2.invalidate()
                            iotReport()
                        }
                        1 -> {
                            Log.d("data", "dailyreport 1")
                            binding.lineChart2.fitScreen()
                            binding.lineChart2.data?.clearValues()
                            binding.lineChart2.xAxis.valueFormatter = null
                            binding.lineChart2.notifyDataSetChanged()
                            binding.lineChart2.clear()
                            binding.lineChart2.invalidate()
                            iotreportSingle(1)
                        }
                        2 -> {
                            Log.d("data", "dailyreport 2")
                            binding.lineChart2.fitScreen()
                            binding.lineChart2.data?.clearValues()
                            binding.lineChart2.xAxis.valueFormatter = null
                            binding.lineChart2.notifyDataSetChanged()
                            binding.lineChart2.clear()
                            binding.lineChart2.invalidate()
                            iotreportSingle(2)

                        }
                        3 -> {
                            Log.d("data", "dailyreport 3")
                            binding.lineChart2.fitScreen()
                            binding.lineChart2.data?.clearValues()
                            binding.lineChart2.xAxis.valueFormatter = null
                            binding.lineChart2.notifyDataSetChanged()
                            binding.lineChart2.clear()
                            binding.lineChart2.invalidate()
                            iotreportSingle(3)

                        }
                    }
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

    @SuppressLint("SetTextI18n")
    private fun iotReport() {

        binding.lineChart2.animateX(1000)

        val dataline1: ArrayList<Entry> = ArrayList()
        dataline1.add(Entry(1f, 24f))
        dataline1.add(Entry(2f, 40f))
        dataline1.add(Entry(3f, 35f))
        dataline1.add(Entry(4f, 32.5f))
        dataline1.add(Entry(5f, 10f))
        dataline1.add(Entry(6f, 50f))

        val dataline2: ArrayList<Entry> = ArrayList()
        dataline2.add(Entry(1f, 2f))
        dataline2.add(Entry(2f, 8f))
        dataline2.add(Entry(3f, 42f))
        dataline2.add(Entry(4f, 32f))
        dataline2.add(Entry(5f, 28f))
        dataline2.add(Entry(6f, 48f))

        val dataline3: ArrayList<Entry> = ArrayList()
        dataline3.add(Entry(1f, 50f))
        dataline3.add(Entry(2f, 65f))
        dataline3.add(Entry(3f, 71f))
        dataline3.add(Entry(4f, 55f))
        dataline3.add(Entry(5f, 65f))
        dataline3.add(Entry(6f, 81f))

        val linedataset1 = LineDataSet(dataline1, "Ammonia")
        linedataset1.setDrawCircles(false)
        val linedataset2 = LineDataSet(dataline2, "Tempreture")
        linedataset2.setDrawCircles(false)
        val linedataset3 = LineDataSet(dataline3, "Humidity")
        linedataset3.setDrawCircles(false)

        binding.txtLineChart2Color1Title.text = "Ammonia"
        binding.txtLineChart2Color2Title.text = "Tempreture"
        binding.txtLineChart2Color3Title.text = "Humidity"
        binding.txtLineChart2Color1Title.visibility = View.VISIBLE
        binding.txtLineChart2Color2Title.visibility = View.VISIBLE
        binding.txtLineChart2Color3Title.visibility = View.VISIBLE
        setColor(binding.txtLineChart2Color1Title, R.color.Ammonia)
        setColor(binding.txtLineChart2Color2Title, R.color.Tempreture)
        setColor(binding.txtLineChart2Color3Title, R.color.Humidity)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            linedataset1.color = resources.getColor(R.color.Ammonia, null)
            linedataset2.color = resources.getColor(R.color.Tempreture, null)
            linedataset3.color = resources.getColor(R.color.Humidity, null)
        } else {
            linedataset1.color = Color.BLUE
            linedataset2.color = Color.YELLOW
            linedataset3.color = Color.DKGRAY
        }

        val linedata = LineData(linedataset1, linedataset2, linedataset3)

        binding.lineChart2.data = linedata


        val lineChart2week = arrayOf("sun", "sun", "mon", "tue", "wed", "thr", "fri")

        binding.lineChart2.description.isEnabled = false
        binding.lineChart2.setDrawGridBackground(false)

        val xaxis = binding.lineChart2.xAxis
        xaxis.setDrawGridLines(false)
        xaxis.position = XAxis.XAxisPosition.BOTTOM
        xaxis.granularity = 1f
        xaxis.setDrawLabels(true)
        xaxis.setDrawAxisLine(false)
        xaxis.valueFormatter = IndexAxisValueFormatter(lineChart2week)

        val yAxisLeft = binding.lineChart2.axisLeft
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.isEnabled = false

        binding.lineChart2.axisRight.isEnabled = false
        binding.lineChart2.setTouchEnabled(true)
        binding.lineChart2.setPinchZoom(false)


        val name = arrayListOf<String>("Ammonia", "Tempreture", "Humidity")


        val cview = CustomMarkerView(
            requireContext(),
            R.layout.item_customarkerview,
            dataline1,
            dataline2,
            dataline3, binding.lineChart2,
            R.color.Ammonia, R.color.Tempreture,
            R.color.Humidity, name
        )

        binding.lineChart2.animateXY(2000, 2000)
        binding.lineChart2.marker = cview
        binding.lineChart2.setScaleEnabled(false);

        val legend = binding.lineChart2.legend
        legend.isEnabled = false


        binding.lineChart2.invalidate()
        binding.lineChart2.notifyDataSetChanged()


    }

    private fun iotreportSingle(loc: Int) {

        binding.lineChart2.animateX(1000)

        val dataline1: ArrayList<Entry> = ArrayList()
        dataline1.add(Entry(1f, 24f))
        dataline1.add(Entry(2f, 40f))
        dataline1.add(Entry(3f, 35f))
        dataline1.add(Entry(4f, 32.5f))
        dataline1.add(Entry(5f, 10f))
        dataline1.add(Entry(6f, 50f))

        var linedataset: LineDataSet
        if (loc == 1) {
            linedataset = LineDataSet(dataline1, "Ammonia")
            binding.txtLineChart2Color1Title.text = "Ammonia"
            binding.txtLineChart2Color1Title.visibility = View.VISIBLE
            binding.txtLineChart2Color2Title.visibility = View.GONE
            binding.txtLineChart2Color3Title.visibility = View.GONE
        } else if (loc == 2) {
            linedataset = LineDataSet(dataline1, "Tempreture")
            binding.txtLineChart2Color2Title.text = "Tempreture"
            binding.txtLineChart2Color1Title.visibility = View.GONE
            binding.txtLineChart2Color2Title.visibility = View.VISIBLE
            binding.txtLineChart2Color3Title.visibility = View.GONE
        } else {
            linedataset = LineDataSet(dataline1, "Humidity")
            binding.txtLineChart2Color3Title.text = "Humidity"
            binding.txtLineChart2Color1Title.visibility = View.GONE
            binding.txtLineChart2Color2Title.visibility = View.GONE
            binding.txtLineChart2Color3Title.visibility = View.VISIBLE
        }
        linedataset.setDrawCircles(false)

        setColor(binding.txtLineChart2Color1Title, R.color.Ammonia)
        setColor(binding.txtLineChart2Color2Title, R.color.Tempreture)
        setColor(binding.txtLineChart2Color3Title, R.color.Humidity)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            when (loc) {
                1 -> linedataset.color = resources.getColor(R.color.Ammonia, null)
                2 -> linedataset.color = resources.getColor(R.color.Tempreture, null)
                else -> linedataset.color = resources.getColor(R.color.Humidity, null)
            }
        } else {
            when (loc) {
                1 -> linedataset.color = Color.BLUE
                2 -> linedataset.color = Color.YELLOW
                else -> linedataset.color = Color.DKGRAY
            }
        }

        val linedata = LineData(linedataset)

        binding.lineChart2.data = linedata


        val lineChart2week = arrayOf("sun", "sun", "mon", "tue", "wed", "thr", "fri")

        binding.lineChart2.description.isEnabled = false
        binding.lineChart2.setDrawGridBackground(false)

        val xaxis = binding.lineChart2.xAxis
        xaxis.setDrawGridLines(false)
        xaxis.position = XAxis.XAxisPosition.BOTTOM
        xaxis.granularity = 1f
        xaxis.setDrawLabels(true)
        xaxis.setDrawAxisLine(false)
        xaxis.valueFormatter = IndexAxisValueFormatter(lineChart2week)

        val yAxisLeft = binding.lineChart2.axisLeft
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.isEnabled = false

        binding.lineChart2.axisRight.isEnabled = false
        binding.lineChart2.setTouchEnabled(true)
        binding.lineChart2.setPinchZoom(false)


        binding.lineChart2.setScaleEnabled(false);

        val legend = binding.lineChart2.legend
        legend.isEnabled = false
        binding.lineChart2.marker = null


        binding.lineChart2.invalidate()
        binding.lineChart2.notifyDataSetChanged()


    }

    private fun spinnerDailyReport() {


        val option = arrayOf("All ", "Bird Productivity ", "Bird Mortality ", "FCR per dozen ")
        binding.spinnerIotreport.adapter = context?.applicationContext?.let {
            ArrayAdapter<String>(
                it, R.layout.spinner_text, option
            )
        }
        binding.spinnerIotreport.adapter = context?.applicationContext?.let {
            ArrayAdapter<String>(
                it, R.layout.spinner_text, option
            )
        }

        binding.spinnerIotreport.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener,
                AdapterView.OnItemClickListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            binding.lineChart3.fitScreen()
                            binding.lineChart3.data?.clearValues()
                            binding.lineChart3.xAxis.valueFormatter = null
                            binding.lineChart3.notifyDataSetChanged()
                            binding.lineChart3.clear()
                            binding.lineChart3.invalidate()
                            Log.d("data", "iot 0")
                            DailyReport()
                        }
                        1 -> {
                            binding.lineChart3.fitScreen()
                            binding.lineChart3.data?.clearValues()
                            binding.lineChart3.xAxis.valueFormatter = null
                            binding.lineChart3.notifyDataSetChanged()
                            binding.lineChart3.clear()
                            binding.lineChart3.invalidate()
                            Log.d("data", "iot 1")
                            DailyReportSingle(1)
                        }
                        2 -> {
                            binding.lineChart3.fitScreen()
                            binding.lineChart3.data?.clearValues()
                            binding.lineChart3.xAxis.valueFormatter = null
                            binding.lineChart3.notifyDataSetChanged()
                            binding.lineChart3.clear()
                            binding.lineChart3.invalidate()
                            Log.d("data", "iot 2")
                            DailyReportSingle(2)
                        }
                        else -> {
                            binding.lineChart3.fitScreen()
                            binding.lineChart3.data?.clearValues()
                            binding.lineChart3.xAxis.valueFormatter = null
                            binding.lineChart3.notifyDataSetChanged()
                            binding.lineChart3.clear()
                            binding.lineChart3.invalidate()
                            Log.d("data", "iot 3")
                            DailyReportSingle(3)
                        }
                    }
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

    @SuppressLint("SetTextI18n")
    private fun DailyReport() {

        binding.lineChart3.animateX(1000)

        val dataline1: ArrayList<Entry> = ArrayList()
        dataline1.add(Entry(1f, 100f))
        dataline1.add(Entry(2f, 150f))
        dataline1.add(Entry(3f, 200f))
        dataline1.add(Entry(4f, 80f))
        dataline1.add(Entry(5f, 100f))
        dataline1.add(Entry(6f, 112f))

        val dataline2: ArrayList<Entry> = ArrayList()
        dataline2.add(Entry(1f, 5f))
        dataline2.add(Entry(2f, 10f))
        dataline2.add(Entry(3f, 1f))
        dataline2.add(Entry(4f, 7f))
        dataline2.add(Entry(5f, 32f))
        dataline2.add(Entry(6f, 5f))

        val dataline3: ArrayList<Entry> = ArrayList()
        dataline3.add(Entry(1f, 10f))
        dataline3.add(Entry(2f, 80f))
        dataline3.add(Entry(3f, 90f))
        dataline3.add(Entry(4f, 85f))
        dataline3.add(Entry(5f, 40f))
        dataline3.add(Entry(6f, 90f))

        val linedataset1 = LineDataSet(dataline1, "Bird Productivity")
        linedataset1.setDrawCircles(false)
        linedataset1.mode = LineDataSet.Mode.CUBIC_BEZIER
        val linedataset2 = LineDataSet(dataline2, "Bird Mortality")
        linedataset2.setDrawCircles(false)
        linedataset2.mode = LineDataSet.Mode.CUBIC_BEZIER
        val linedataset3 = LineDataSet(dataline3, "FCR per dozen")
        linedataset3.setDrawCircles(false)
        linedataset3.mode = LineDataSet.Mode.CUBIC_BEZIER

        linedataset1.color = Color.GREEN
        linedataset2.color = Color.BLACK
        linedataset3.color = Color.RED

        binding.txtLineChart3Color1Title.text = "Bird Productivity"
        binding.txtLineChart3Color2Title.text = "Bird Mortality"
        binding.txtLineChart3Color3Title.text = "FCR per dozen"
        setColor(binding.txtLineChart3Color1Title, R.color.green)
        setColor(binding.txtLineChart3Color2Title, R.color.black)
        setColor(binding.txtLineChart3Color3Title, R.color.red)
        binding.txtLineChart3Color1Title.visibility = View.VISIBLE
        binding.txtLineChart3Color2Title.visibility = View.VISIBLE
        binding.txtLineChart3Color3Title.visibility = View.VISIBLE


        val linedata = LineData(linedataset1, linedataset2, linedataset3)

        binding.lineChart3.data = linedata


        val lineChart3week = arrayOf("", "sun", "mon", "tue", "wed", "thr", "fri")

        val name = arrayListOf("Bird Productivity", "Bird Mortality", "FCR per dozen")

        val cview = CustomMarkerView2(
            requireContext(),
            R.layout.item_customarkerview,
            dataline1,
            dataline2,
            dataline3, binding.lineChart3,
            R.color.green, R.color.black,
            R.color.red, name
        )






        binding.lineChart3.animateXY(2000, 2000)

        binding.lineChart3.marker = cview
        binding.lineChart3.setScaleEnabled(false)



        binding.lineChart3.description.isEnabled = false
        binding.lineChart3.setDrawGridBackground(false)

        val xaxis = binding.lineChart3.xAxis
        xaxis.setDrawGridLines(false)
        xaxis.position = XAxis.XAxisPosition.BOTTOM
        xaxis.granularity = 1f
        xaxis.setDrawLabels(true)
        xaxis.setDrawAxisLine(false)
        xaxis.valueFormatter = IndexAxisValueFormatter(lineChart3week)

        val yAxisLeft = binding.lineChart3.axisLeft
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.isEnabled = false

        binding.lineChart3.axisRight.isEnabled = false
        binding.lineChart3.setTouchEnabled(true)
        binding.lineChart3.setPinchZoom(false)


        val legend = binding.lineChart3.legend
        legend.isEnabled = false

        binding.lineChart3.invalidate()
        binding.lineChart3.notifyDataSetChanged()

    }

    private fun DailyReportSingle(loc: Int) {


        val dataline1: ArrayList<Entry> = ArrayList()
        dataline1.add(Entry(1f, 100f))
        dataline1.add(Entry(2f, 150f))
        dataline1.add(Entry(3f, 200f))
        dataline1.add(Entry(4f, 80f))
        dataline1.add(Entry(5f, 100f))
        dataline1.add(Entry(6f, 112f))
        var linedataset: LineDataSet
        when (loc) {
            1 -> {
                linedataset = LineDataSet(dataline1, "Bird Productivity")
                binding.txtLineChart3Color1Title.text = "Bird Productivity"
                binding.txtLineChart3Color1Title.visibility = View.VISIBLE
                binding.txtLineChart3Color2Title.visibility = View.GONE
                binding.txtLineChart3Color3Title.visibility = View.GONE
                linedataset.color = Color.GREEN
                setColor(binding.txtLineChart3Color1Title, R.color.green)
            }
            2 -> {
                linedataset = LineDataSet(dataline1, "Bird Mortality")
                binding.txtLineChart3Color2Title.text = "Bird Mortality"
                binding.txtLineChart3Color1Title.visibility = View.GONE
                binding.txtLineChart3Color2Title.visibility = View.VISIBLE
                binding.txtLineChart3Color3Title.visibility = View.GONE
                setColor(binding.txtLineChart3Color2Title, R.color.black)
                linedataset.color = Color.BLACK
            }
            else -> {
                binding.txtLineChart3Color3Title.text = "FCR per dozen"
                linedataset = LineDataSet(dataline1, "FCR per dozen")
                binding.txtLineChart3Color1Title.visibility = View.GONE
                binding.txtLineChart3Color2Title.visibility = View.GONE
                binding.txtLineChart3Color3Title.visibility = View.VISIBLE
                setColor(binding.txtLineChart3Color3Title, R.color.red)
                linedataset.color = Color.RED
            }
        }
        linedataset.setDrawCircles(false)
        linedataset.mode = LineDataSet.Mode.CUBIC_BEZIER




        binding.lineChart3.animateXY(2000, 2000)

        binding.lineChart3.setScaleEnabled(false)

        val linedata = LineData(linedataset)

        binding.lineChart3.data = linedata


        val lineChart3week = arrayOf("", "sun", "mon", "tue", "wed", "thr", "fri")


        binding.lineChart3.description.isEnabled = false
        binding.lineChart3.setDrawGridBackground(false)

        val xaxis = binding.lineChart3.xAxis
        xaxis.setDrawGridLines(false)
        xaxis.position = XAxis.XAxisPosition.BOTTOM
        xaxis.granularity = 1f
        xaxis.setDrawLabels(true)
        xaxis.setDrawAxisLine(false)
        xaxis.valueFormatter = IndexAxisValueFormatter(lineChart3week)

        val yAxisLeft = binding.lineChart3.axisLeft
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.isEnabled = false

        binding.lineChart3.axisRight.isEnabled = false

        val legend = binding.lineChart3.legend
        legend.isEnabled = false
        binding.lineChart3.marker = null

        binding.lineChart3.invalidate()
        binding.lineChart3.notifyDataSetChanged()
    }*/

    //get Suggestionvedio call
    private fun suggestionVedio() {

        binding.recycleviewVedio.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.recycleviewVedio.setHasFixedSize(true)

        Log.d("data", "Suggestionvedio api start 1200")

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getVideo(
            token
        ).observe(viewLifecycleOwner,
            Observer {
                videolist = true
                loadingcheck()
                Log.d("data", "Suggestionvedio api end")

                if (it.errors == null) {
                    binding.txtSlider2Title.visibility=View.VISIBLE
                    if (it.results != null && it.results!!.isNotEmpty())
                        binding.recycleviewVedio.adapter =
                            SuggestionVedioAdapter(context, it.results)
                } else {
                    binding.txtSlider2Title.visibility=View.GONE
                    Log.e("data", "${it.errors!![0].message}")
                }

            }
        )

    }


    //get Suggestion call
    private fun suggestion() {

        binding.recycleHomeSlider2.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recycleHomeSlider2.setHasFixedSize(true)


        Log.d("suggestion", "suggestion api 1236 start")

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.wordpressFeed(
            token
        ).observe(viewLifecycleOwner,
            Observer {
                suggestion = true
                loadingcheck()
                Log.d("data", "suggestion api end")


                if (it.errors == null) {
                    binding.txtSlider2Title2.visibility=View.VISIBLE
                    if (it.results.results != null && it.results.results!!.isNotEmpty())
                        binding.recycleHomeSlider2.adapter =
                            HomeSuggestionSliderAdapter(context, it.results.results)
                    if (it.results.results?.size==0||it.results.results==null)
                        binding.txtSlider2Title2.visibility=View.GONE
                } else {
                    binding.txtSlider2Title2.visibility=View.GONE
                    Log.e("data", "${it.errors!![0].message}")
                }

            }
        )

    }
/*

    private fun setColor(textview: TextView, color: Int) {
        textview.setCompoundDrawablesWithIntrinsicBounds(
            R.drawable.circle,
            0,
            0,
            0
        )
        for (drawable in textview.compoundDrawables) {
            if (drawable != null) {
                drawable.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(textview.context, color),
                        PorterDuff.Mode.SRC_IN
                    )
            }
        }
    }

    private fun barchart1(): ArrayList<BarEntry> {
        val barChartval: ArrayList<BarEntry> = ArrayList()
        barChartval.add(BarEntry(1f, 200f))
        barChartval.add(BarEntry(2f, 200f))
        barChartval.add(BarEntry(3f, 200f))
        barChartval.add(BarEntry(4f, 200f))
        barChartval.add(BarEntry(5f, 200f))
        barChartval.add(BarEntry(6f, 200f))
        return barChartval
    }

    private fun barchart2(): ArrayList<BarEntry> {
        val barChartval: ArrayList<BarEntry> = ArrayList()
        barChartval.add(BarEntry(1f, 300f))
        barChartval.add(BarEntry(2f, 400f))
        barChartval.add(BarEntry(3f, 600f))
        barChartval.add(BarEntry(4f, 200f))
        barChartval.add(BarEntry(5f, 700f))
        barChartval.add(BarEntry(6f, 100f))
        return barChartval
    }

    class CustomMarkerView(
        context: Context?,
        val layoutResource: Int,
        private val line1: ArrayList<Entry>,
        private val line2: ArrayList<Entry>,
        private val line3: ArrayList<Entry>, val linechart: LineChart,
        color1: Int, color2: Int, color3: Int,
        var name: ArrayList<String>
    ) : MarkerView(context, layoutResource) {

        */
/*private val mOffset2 = MPPointF()

        private var xOffsetMultiplier=0.0f*//*


        private var item1: TextView = findViewById(R.id.txt_item1)
        private var item2: TextView = findViewById(R.id.txt_item2)
        private var item3: TextView = findViewById(R.id.txt_item3)

        init {
            setColor(item1, color1)
            setColor(item2, color2)
            setColor(item3, color3)
        }

        private fun setColor(textview: TextView, color: Int) {
            textview.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.circle,
                0,
                0,
                0
            )
            for (drawable in textview.compoundDrawables) {
                if (drawable != null) {
                    drawable.colorFilter =
                        PorterDuffColorFilter(
                            ContextCompat.getColor(textview.context, color),
                            PorterDuff.Mode.SRC_IN
                        )
                }
            }
        }

        override fun draw(canvas: Canvas, posx: Float, posy: Float) {
            var mposx = posx
            var mposy = posy
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
//            val display = windowManager.defaultDisplay
            val size = Point()
//            display.getSize(size)

//            val width: Int = size.x
            val width: Int = linechart.width
            val w = getWidth()
            if (width - posx - w < w) {
                mposx -= w.toFloat()
            }
            val height: Int = linechart.height
            val h = getHeight()
            if (height - posy - h < h) {
                mposy -= h.toFloat()
            }

            canvas.translate(mposx, mposy)
            draw(canvas)
            canvas.translate(-mposx, -mposy)
        }

        @SuppressLint("SetTextI18n")
        override fun refreshContent(e: Entry?, highlight: Highlight?) {
            val xIndex = e!!.x.toInt()
            item1.text = "  ${name[0]} ${line1[xIndex - 1].y}"
            item2.text = "  ${name[1]} ${line2[xIndex - 1].y}"
            item3.text = "  ${name[2]} ${line3[xIndex - 1].y}"

            */
/* if (e.x.toInt()< 4) {
                 xOffsetMultiplier = 3.2f
             } else if (e.x.toInt() > item2.text.length - 6) {
                 xOffsetMultiplier = 1.12f
             } else {
                 xOffsetMultiplier = 2f
             }

             if (e.x < 4) {
                 xOffsetMultiplier = 3.2f
             } else if (e.x > item2.text.length - 6) {
                 //timestamps is an array containing xValues timestamps
                 xOffsetMultiplier = 1.12f
             } else {
                 xOffsetMultiplier = 2f
             }*//*

            super.refreshContent(e, highlight)
        }
    }

    class CustomMarkerView2(
        context: Context?,
        val layoutResource: Int,
        private val line1: ArrayList<Entry>,
        private val line2: ArrayList<Entry>,
        private val line3: ArrayList<Entry>, val linechart: LineChart,
        color1: Int, color2: Int, color3: Int,
        var name: ArrayList<String>
    ) : MarkerView(context, layoutResource) {

        */
/*private val mOffset2 = MPPointF()

        private var xOffsetMultiplier=0.0f*//*


        private var item1: TextView = findViewById(R.id.txt_item1)
        private var item2: TextView = findViewById(R.id.txt_item2)
        private var item3: TextView = findViewById(R.id.txt_item3)

        init {
            setColor(item1, color1)
            setColor(item2, color2)
            setColor(item3, color3)
        }

        private fun setColor(textview: TextView, color: Int) {
            textview.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.circle,
                0,
                0,
                0
            )
            for (drawable in textview.compoundDrawables) {
                if (drawable != null) {
                    drawable.colorFilter =
                        PorterDuffColorFilter(
                            ContextCompat.getColor(textview.context, color),
                            PorterDuff.Mode.SRC_IN
                        )
                }
            }
        }

        override fun draw(canvas: Canvas, posx: Float, posy: Float) {
            var mposx = posx
            var mposy = posy
            val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)

            val width: Int = size.x
            val width2: Int = linechart.width
            Log.d("data", "size $width2 $posx")
            val w = getWidth()

            if (posx > width2 / 2) {
                mposx -= w.toFloat()
                Log.d("data", "updated $width2 $posx ")
            }
            */
/* if (width - posx - w < w) {
                 Log.d("data","switched call")
                 mposx -= w.toFloat()
             }else{
                 Log.d("data","not switched call")

             }*//*

            val height: Int = linechart.height
            val h = getHeight()
            if (height - posy - h < h) {
                mposy -= h.toFloat()
            }

            canvas.translate(mposx, mposy)
            draw(canvas)
            canvas.translate(-mposx, -mposy)
        }

        @SuppressLint("SetTextI18n")
        override fun refreshContent(e: Entry?, highlight: Highlight?) {
            val xIndex = e!!.x.toInt()
            item1.text = "  ${name[0]} ${line1[xIndex - 1].y}"
            item2.text = "  ${name[1]} ${line2[xIndex - 1].y}"
            item3.text = "  ${name[2]} ${line3[xIndex - 1].y}"

            super.refreshContent(e, highlight)
        }
    }
*/


    @SuppressLint("MissingPermission")
    private fun getlocation() {

        Log.d("data", "getlocation fx 1496")

        if (gpsListener == null && networkListener == null && !gpslocation) {


            val manager =
                context.getSystemService(LOCATION_SERVICE) as LocationManager
            val statusOfGPS =
                manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            Log.d("data", "gps status$statusOfGPS")
            if (statusOfGPS) {
                locationManager =
                    context.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager

                listner = MyLocationListener(context, this)
                gpsListener = listner!!
                networkListener = listner!!

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, 5000, 10f, gpsListener!!
                )
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 5000, 10f, networkListener!!
                )

            }

        }

    }

    class MyLocationListener(val context: Context, private val call: HomeFragment) :
        LocationListener {
        override fun onLocationChanged(loc: Location) {
            if (loc.latitude != null &&
                loc.longitude != null
            )
                call.getTem(loc.latitude, loc.longitude)

            Log.d("data", "MyLocationListener 1542 lat ${loc.latitude} long ${loc.longitude}")
        }


    }

    private fun MgetLocation() {

        Log.d("data", "MgetLocation fx ")

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("data", "MgetLocation  fx no per enible")

            enableUserlocation()
            return
        } else {


            if (!gpslocation) {
                turnGPSOn()

                getlocation()
            }

        }


    }

    private fun enableUserlocation() =
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            Log.d("data", "enableUserlocation fx 1585")

            if (!gpslocation) {
                getlocation()
            } else
                Log.d("data", "enableUserlocation fx $gpslocation")
        } else {

            /*  Log.d("data", "no enableUserlocation")
              if (ActivityCompat.shouldShowRequestPermissionRationale(
                      (context as Activity?)!!,
                      Manifest.permission.ACCESS_FINE_LOCATION
                  )
              ) {

                  Log.d("data", "per req enableUserlocation")
              } else {*/

            ActivityCompat.requestPermissions(
                (context as Activity?)!!,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                202
            )
//            }
        }

    private fun loc() {
        val locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result = LocationServices.getSettingsClient(activity)
            .checkLocationSettings(builder.build())
        result.addOnCompleteListener(
            OnCompleteListener {
                try {


                    val response: LocationSettingsResponse = it.getResult(ApiException::class.java)

                } catch (ex: ApiException) {

                    when (ex.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            val resolvableApiException = ex as ResolvableApiException

                            resolvableApiException.startResolutionForResult(activity, gpslocationrequest)

                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                        }
                    }
                }


            })
    }

    private fun turnGPSOn() {

        Log.d("data", "turnGPSOn fx 1653")
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000

        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(context)
            .checkLocationSettings(builder.build())

        result.addOnCompleteListener {
            try {

                if (!isdetach)
                    MgetLocation()

            } catch (ex: ApiException) {

                when (ex.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {

                            val resolveApi = ex as ResolvableApiException
                            resolveApi.startResolutionForResult(context, requestchecksetting)
                        } catch (e: Exception) {
                            Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        Toast.makeText(context, "SETTINGS_CHANGE_UNAVAILABLE", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }

        }

    }

    //get Temp call
    override fun getTem(lat: Double, log: Double) {

        Log.d("data", "getTem api 1700 start ")
        if (gpsListener != null && networkListener != null && !isdetach && !gpslocation) {
            locationManager.removeUpdates(gpsListener!!)
            locationManager.removeUpdates(networkListener!!)
            listner = null
            gpsListener = null
            networkListener = null
            val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
            viewModel.getTemp(
                lat, log, token
            ).observe(viewLifecycleOwner,
                Observer {
                    temp = true
                    loadingcheck()
                    gpslocation = true

                    Log.d("data", "getTem api end ")
                    if (it.errors == null) {
                        binding.cardTemperature.visibility = View.VISIBLE
                        val df = DecimalFormat("####0.00")
                        binding.tempText.text =
                            "${it.results.weather!![0].main} (${it.results.name})"
                        binding.temp.text = "${it.results.main.temp.toInt() - 273}"

                        binding.temMax.text = "${it.results.main.tempMax.toInt() - 273}"
                        binding.temMin.text = "${it.results.main.tempMin.toInt() - 273}"

                        binding.txtHumidity.text = "  ${it.results.main.humidity!!} %"
                        binding.txtWindSpeed.text = "  ${it.results.wind.speed!!} Km/hr"
                        binding.txtVisibilty.text = "  ${it.results.visibility!! / 1000} Km"

                        binding.cardTemperature.visibility = View.VISIBLE
                    } else {
                        binding.cardTemperature.visibility = View.GONE
                        Toast.makeText(context, "${it.errors!![0].message}", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            )
        } else {
            Log.d("data", "getTem api  null")
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode==colarlocation){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("data","colarlocation permission enable")
                    checkgps()
                    Toast.makeText(context, "colarlocation permission enable", Toast.LENGTH_SHORT).show()

                } else {
                    Log.d("data","colarlocation permission cancel")
                    Toast.makeText(context, "colarlocation permission cancel", Toast.LENGTH_SHORT).show()
                    activity?.finish()
                }
        }
        if (requestCode==gpslocationrequest){

            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("data", "home gps enable")
                } else {
                    Log.d("data", "home gps not enable")
                    loc()
                }
            }

        }
        if (requestCode == requestchecksetting) {
            for (i in grantResults.indices) {
                if (grantResults[i] == Activity.RESULT_OK) {
//                    if (!gpslocation)
//                    loc()
                    Log.d("data", "Gps is turned on")
                    checkgps()
                } else if (grantResults[i] == Activity.RESULT_CANCELED) {
                    Log.d("data", "Gps is cancel")
                    turnGPSOn()

                }
            }


        }

        if (requestCode == 2000) {

            for (i in grantResults.indices) {
                when (grantResults[i]) {
                    Activity.RESULT_OK -> {
                        Apicall()
                        Toast.makeText(context, "Gps on", Toast.LENGTH_SHORT).show()
                    }
                    Activity.RESULT_CANCELED -> {
                        loc()
                        Toast.makeText(context, "Gps cancel", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
        Log.d("data", "onRequestPermissionsResult home frag ${requestCode} ${grantResults.toString()}")
  /*      if (requestCode == requestcheckper) {

            if (!gpslocation)
                getlocation()
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == gpslocationrequest) {
                if (resultCode == Activity.RESULT_OK) {
                    getlocation()
                    Log.d("data","home gps enable")
                    Toast.makeText(context, "Gps is turned on", Toast.LENGTH_SHORT).show()

                } else {
                    loc()
                    Log.d("data","home gps not enable")
                    Toast.makeText(context, "Gps is cancel", Toast.LENGTH_SHORT).show()

                }



        }
    }

    override fun onDetach() {
        isdetach = true
        super.onDetach()
    }
}