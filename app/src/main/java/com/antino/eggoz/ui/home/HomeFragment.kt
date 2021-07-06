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
import kotlinx.coroutines.Job
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
    private var pincode:Int?=0



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

    private var tempState=false

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

        binding.tempCity

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

        binding.btnFarmDetail
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
//                                binding.textView29.visibility=View.VISIBLE
                                binding.crdDailyInputMissed.visibility=View.VISIBLE
                            }

                            if (df.format(c).toString()!=it.results!![i].sheds!![j].flocks!![k].lastDailyInputDate){
//                                binding.textView29.visibility=View.VISIBLE
                                binding.crdDailyInputMissed.visibility=View.VISIBLE
                            }

                        }
                    }
                }

                loadingcheck()
                if (it.count!=null){
                    if (it.count!! ==0){
                        binding.txtAlerts.visibility=View.VISIBLE
                        binding.alertAddFarm2.visibility=View.VISIBLE

                    }
                }else{
                    binding.txtAlerts.visibility=View.VISIBLE
                    binding.alertAddFarm2.visibility=View.VISIBLE
                }

                Log.d("data","farm count ${it.count}")

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

                    if (it.farmer.defaultAddress!=null)
                    pincode=it.farmer.defaultAddress?.pinCode ?:-1

                    Log.d("data","pincode $pincode")


                    if (pincode!=null&&pincode!=0&&pincode!=-1) {
                        tempbypincode(pincode = pincode!!)
                    }else
                        tempState=false


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
                        binding.txtLiveIOTReport.visibility=View.VISIBLE
                        iot = true

                        iotAlert()
                    }
                } else {
                    Toast.makeText(context, "${it.errors!![0].message}", Toast.LENGTH_SHORT).show()
                }

            }
        )


    }

    private fun tempbypincode(pincode:Int){
        if (pincode!=0) {
            val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
            viewModel.getTemppincode(
                pincode, token
            ).observe(viewLifecycleOwner,
                Observer {
                    loadingcheck()
                    gpslocation = true

                    if (it.cod == 404)
                        tempState = false

                    Log.d("data", "getTem pincod ${it.cod} $pincode")
                    if (it.cod == 200) {
                        if (!tempState) {
                            tempState=true
                            binding.cardTemperature.visibility = View.VISIBLE
                            val df = DecimalFormat("####0.00")
                            binding.tempText.text =
                                "${it.weather!![0].main}"
                            binding.tempCity.text = "(${it.name})"
                            binding.temp.text = "${it.main?.temp?.toInt() ?:0- 273}"

//                        binding.temMax.text = "${it.results.main.tempMax.toInt() - 273}"
//                        binding.temMin.text = "${it.results.main.tempMin.toInt() - 273}"

                            binding.txtHumidity.text = "  ${it.main?.humidity!!} %"
                            binding.txtWindSpeed.text = "  ${it.wind?.speed!!} Km/hr"
                            binding.txtVisibilty.text = "  ${it.visibility!! / 1000} Km"

                            binding.cardTemperature.visibility = View.VISIBLE
                        }else
                            tempState=false

                    }else
                        tempState=false

                }
            )
        }else
            temp=false
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
//                    binding.crdAmmonia.visibility = View.VISIBLE

                    binding.txtTemVal.text = "${it.temperatureval} °C"
                    binding.txtHumidityVal.text = "${it.humidityval} %"
                    binding.txtAmmoniaVal.text = "${it.nh3val} ppm"
                    binding.txtLiveIOTReport.visibility=View.VISIBLE

                } else {
                    Toast.makeText(context, "${it.message}", Toast.LENGTH_SHORT).show()
                }

            })

    }


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


    //comment

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
        var language="Hindi"
        val lan=PrefrenceUtils.retriveData(requireContext(), Constants.LANG)!!
        if (lan=="en")
            language="English"


        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.wordpressFeed(
            token,language
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

        override fun onProviderEnabled(provider: String) {
        }

        override fun onProviderDisabled(provider: String) {
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
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
                        Toast.makeText(context, "Setting Change Unavailable", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }

        }

    }

    //get Temp call
    override fun getTem(lat: Double, log: Double) {

        if (gpsListener != null && networkListener != null && !isdetach && !gpslocation) {
            locationManager.removeUpdates(gpsListener!!)
            locationManager.removeUpdates(networkListener!!)
            listner = null
            gpsListener = null
            networkListener = null
            val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
            var temp=viewModel.getTemp(
                lat, log, token
            ).observe(viewLifecycleOwner,
                Observer {
                    temp = true
                    loadingcheck()
                    gpslocation = true

                    Log.d("data", "getTem api end ")
                    if (it.errors == null) {
                        if (!tempState) {

                            binding.cardTemperature.visibility = View.VISIBLE
                            val df = DecimalFormat("####0.00")
                            binding.tempText.text =
                                "${it.results.weather!![0].main}"
                            binding.tempCity.text = "(${it.results.name})"
                            binding.temp.text = "${it.results.main.temp.toInt() - 273}"

//                        binding.temMax.text = "${it.results.main.tempMax.toInt() - 273}"
//                        binding.temMin.text = "${it.results.main.tempMin.toInt() - 273}"

                            binding.txtHumidity.text = "  ${it.results.main.humidity!!} %"
                            binding.txtWindSpeed.text = "  ${it.results.wind.speed!!} Km/hr"
                            binding.txtVisibilty.text = "  ${it.results.visibility!! / 1000} Km"

                            binding.cardTemperature.visibility = View.VISIBLE
                        }

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
                    Toast.makeText(context, "Colar Location Permission Enable", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Colar Location Permission Cancel", Toast.LENGTH_SHORT).show()
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
                        Toast.makeText(context, "Gps On", Toast.LENGTH_SHORT).show()
                    }
                    Activity.RESULT_CANCELED -> {
                        loc()
                        Toast.makeText(context, "Gps Cancel", Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }
        Log.d("data", "onRequestPermissionsResult home frag ${requestCode} ${grantResults.toString()}")
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == gpslocationrequest) {
                if (resultCode == Activity.RESULT_OK) {
                    getlocation()
                    Log.d("data","home gps enable")
//                    Toast.makeText(context, "Gps Is Turned On", Toast.LENGTH_SHORT).show()

                } else {
                    loc()
                    Log.d("data","home gps not enable")
                    Toast.makeText(context, "Gps Is Cancel", Toast.LENGTH_SHORT).show()

                }



        }
    }

    override fun onDetach() {
        isdetach = true
        super.onDetach()
    }
}