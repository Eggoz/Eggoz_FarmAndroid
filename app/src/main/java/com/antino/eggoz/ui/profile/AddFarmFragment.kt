package com.antino.eggoz.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentAddFarmBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.home.HomeFragment
import com.antino.eggoz.ui.profile.callback.locationCallback
import com.antino.eggoz.view.CustomAlertLoading
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.io.IOException
import java.text.DecimalFormat
import java.util.*

class AddFarmFragment(val context: MainActivity) : Fragment(), locationCallback {
    private lateinit var binding: FragmentAddFarmBinding
    private var no_of_sheds = 0
    private var no_of_grower = 0

    //location
    private lateinit var locationRequest: LocationRequest
    private var requestchecksetting = 1001
    private var requestcheckper = 1000
    private var listner: MyLocationListener? = null

    private lateinit var flat_building: String
    private lateinit var building_name: String
    private lateinit var landmark: String
    private lateinit var city: String
    private lateinit var state: String
    private lateinit var pincode: String

    private var gpslocation = false

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private var farm_layer_type=""
    private val colarlocation=205
    private val gpslocationrequest=206

    private var gpsListener: LocationListener? = null
    private var networkListener: LocationListener? = null
    private var isdetach = false




    private lateinit var loadingdialog :CustomAlertLoading

    val name: ArrayList<String> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddFarmBinding.inflate(inflater, container, false)

        init()
//        turnGPSOn()

        checkpermisssion()
        binding.btnSubmit.setOnClickListener {
            validate()

        }
        return binding.root
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
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val statusOfGPS =
            manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        Log.d("data", "gps status$statusOfGPS")
        if (!statusOfGPS) {
            loc()
        } else {

            getlocation()
        }
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


    private fun init(){
        binding.edtFarmLayerType.keyListener=null

        loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()
        binding.edtFarmLayerType.setOnItemClickListener { parent, view, position, mid ->
            farm_layer_type=name[position]

            if (position==0){
                binding.txtNoOfBroiler.visibility=View.VISIBLE
                binding.txtNoOfSheds.visibility=View.GONE
                binding.txtNumberOfGrowerShed.visibility=View.GONE

                binding.edtNoOfBroilerLayout.visibility=View.VISIBLE
                binding.edtNoOfShedsLayout.visibility=View.GONE
                binding.edtNumberOfGrowerShedLayout.visibility=View.GONE
            }else{
                binding.txtNoOfBroiler.visibility=View.GONE
                binding.txtNoOfSheds.visibility=View.VISIBLE
                binding.txtNumberOfGrowerShed.visibility=View.VISIBLE

                binding.edtNoOfBroilerLayout.visibility=View.GONE
                binding.edtNoOfShedsLayout.visibility=View.VISIBLE
                binding.edtNumberOfGrowerShedLayout.visibility=View.VISIBLE

            }
        }

        name.add("Broiler")
        name.add("Layer")


        binding.edtFarmLayerType.threshold = 1
        binding.edtFarmLayerType.setTextColor(ContextCompat.getColor(activity?.applicationContext!!, R.color.app_color))
        val adapter = ArrayAdapter(context, android.R.layout.select_dialog_item, name)
        binding.edtFarmLayerType.setAdapter(adapter)


        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadFeed()
                true
            } else false
        }

    }

    private fun validate() {
        if (binding.edtFarmerName.text!!.isEmpty() ||
            binding.edtBuilding.text!!.isEmpty() || binding.edtLandmark.text!!.isEmpty() || binding.edtCity.text!!.isEmpty() ||
            binding.edtState.text!!.isEmpty() || binding.edtPincode.text!!.isEmpty() || farm_layer_type==""
        ) {
            if (farm_layer_type=="") binding.edtFarmLayerTypeLayout.error="Select type"
            else binding.edtFarmLayerTypeLayout.isErrorEnabled=false

            if (binding.edtFarmerName.text!!.isEmpty()) binding.edtFarmerNameLayout.error =
                "Enter Farmer name"
            else binding.edtFarmerNameLayout.isErrorEnabled = false

            if (binding.edtBuilding.text!!.isEmpty()) binding.edtFlatnoLayout.error =
                "Enter Building/Flat number"
            else binding.edtFlatnoLayout.isErrorEnabled = false
            if (binding.edtLandmark.text!!.isEmpty()) binding.edtLandmarkLayout.error =
                "Enter Landmark"
            else binding.edtLandmarkLayout.isErrorEnabled = false
            if (binding.edtCity.text!!.isEmpty()) binding.edtCityLayout.error = "Enter city name"
            else binding.edtCityLayout.isErrorEnabled = false
            if (binding.edtState.text!!.isEmpty()) binding.edtStateLayout.error = "Enter sate Name"
            else binding.edtStateLayout.isErrorEnabled = false
            if (binding.edtPincode.text!!.isEmpty()) binding.edtPincodeLayout.error =
                "Enter pincode"
            else binding.edtPincodeLayout.isErrorEnabled = false
        }else{
            if (binding.edtPincode.text.toString().length!=6) binding.edtPincodeLayout.error="Enter valid pincode"
            else{
                binding.edtFarmerNameLayout.isErrorEnabled = false
                binding.edtFlatnoLayout.isErrorEnabled = false
                binding.edtLandmarkLayout.isErrorEnabled = false
                binding.edtCityLayout.isErrorEnabled = false
                binding.edtStateLayout.isErrorEnabled = false
                binding.edtPincodeLayout.isErrorEnabled = false
                binding.edtFarmLayerTypeLayout.isErrorEnabled=false

                if (binding.edtNoOfBroiler.text!!.isEmpty()&& (binding.edtNoOfSheds.text!!.isEmpty()||binding.edtNumberOfGrowerShed.text!!.isEmpty())){
                    Toast.makeText(context,"Input feild missing",Toast.LENGTH_LONG).show()
                }else
                submit()
            }
        }
    }

    private fun submit(){
        var NoOfBroiler=0
        var number_of_layer_shed=0
        var number_of_grower_shed=0
        if (binding.edtNoOfBroiler.text!!.isNotEmpty()){
            NoOfBroiler=binding.edtNoOfBroiler.text.toString().toInt()
            context.Farmdata(
                binding.edtFarmerName.text.toString(),
                binding.edtBuilding.text.toString(),
                binding.edtLandmark.text.toString(),
                binding.edtCity.text.toString(),
                binding.edtState.text.toString(),
                binding.edtPincode.text.toString(),
                NoOfBroiler,number_of_layer_shed,number_of_grower_shed,farm_layer_type
            )
        }
        if (binding.edtNoOfSheds.text!!.isNotEmpty()&&binding.edtNumberOfGrowerShed.text!!.isNotEmpty()){
            number_of_layer_shed=binding.edtNoOfSheds.text.toString().toInt()
            number_of_grower_shed=binding.edtNumberOfGrowerShed.text.toString().toInt()
            context.Farmdata(
                binding.edtFarmerName.text.toString(),
                binding.edtBuilding.text.toString(),
                binding.edtLandmark.text.toString(),
                binding.edtCity.text.toString(),
                binding.edtState.text.toString(),
                binding.edtPincode.text.toString(),
                NoOfBroiler,number_of_layer_shed,number_of_grower_shed,farm_layer_type
            )
        }


    }
    private fun MgetLocation() {

        Log.d("data", "MgetLocation")

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("data", "MgetLocation  no per enible")

            enableUserlocation()
            return
        } else {


            if (!gpslocation) {
                turnGPSOn()
                getlocation()
            }

            Log.d("data", "MgetLocation per")
        }


    }

    private fun turnGPSOn() {

        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 2000

        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        builder.setAlwaysShow(true)

        val result: Task<LocationSettingsResponse> = LocationServices.getSettingsClient(context)
            .checkLocationSettings(builder.build())

        result.addOnCompleteListener {
            try {

                val response: LocationSettingsResponse = it.getResult(ApiException::class.java)
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

    private fun enableUserlocation() =
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            Log.d("data", "enableUserlocation")
            getlocation()
        } else {

            Log.d("data", "no enableUserlocation")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    (context as Activity?)!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

                Log.d("data", "per req enableUserlocation")
                ActivityCompat.requestPermissions(
                    (context as Activity?)!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    requestcheckper
                )
            } else {

                Log.d("data", "no per req enableUserlocation")
                ActivityCompat.requestPermissions(
                    (context as Activity?)!!,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    requestcheckper
                )
            }
        }

    @SuppressLint("MissingPermission")
    private fun getlocation() {


        if (gpsListener == null && networkListener == null && !gpslocation) {


            val manager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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
        /*
        Log.d("data", "getlocation fx 1496")

        if (gpsListener == null && networkListener == null && !gpslocation) {


            val manager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
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

        }*/

    }

    override fun location(
        building_name: String,
        landmark: String,
        city: String,
        state: String,
        pincode: String
    ) {
        loadingdialog.dismiss()
        this.building_name = building_name
        this.landmark = landmark
        this.city = city
        this.state = state
        this.pincode = pincode

//        if (!gpslocation) {

//        }




        Log.d("data", "getTem api 1700 start ")
        if (gpsListener != null && networkListener != null && !isdetach && !gpslocation) {
            locationManager.removeUpdates(gpsListener!!)
            locationManager.removeUpdates(networkListener!!)
            listner = null
            gpsListener = null
            networkListener = null
            binding.edtBuilding.setText(this.building_name)
            binding.edtLandmark.setText(this.landmark)
            binding.edtCity.setText(this.city)
            binding.edtState.setText(this.state)
            binding.edtPincode.setText(this.pincode)

            if (city != null || city != "")
                gpslocation = true

        } else {
            Log.d("data", "getTem api  null")
        }


        super.location(building_name, landmark, city, state, pincode)
    }


    class MyLocationListener(val context: Context, private val call: AddFarmFragment) :
        LocationListener {
        override fun onLocationChanged(loc: Location) {
            val longitude = "Longitude: " + loc.longitude
            val latitude = "Latitude: " + loc.latitude

            //add
            /*------- To get city name from coordinates -------- */
            /*------- To get city name from coordinates -------- */
            var cityName: String? = null
            var address: String? = null
            var subLocality: String? = null
            var state: String? = null
            var country: String? = null
            var postalCode: String? = null
            var knownName: String? = null
            val gcd = Geocoder(context, Locale.getDefault())
            val addresses: List<Address>
            try {
                addresses = gcd.getFromLocation(
                    loc.latitude,
                    loc.longitude, 1
                )
                if (addresses.size > 0) {
                    println(addresses[0].getLocality())

                    call.location(
                        addresses[0].featureName,
                        addresses[0].subLocality,
                        addresses[0].locality,
                        addresses[0].adminArea,
                        addresses[0].postalCode
                    )

                    cityName = addresses[0].locality
                    address = addresses?.get(0)
                        ?.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    subLocality = addresses[0].subLocality
                    state = addresses[0].adminArea
                    country = addresses[0].countryName
                    postalCode = addresses[0].postalCode
                    knownName = addresses[0].featureName
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("data", e.message.toString())

            }
            val s = """
                $longitude
                $latitude
                
                My Current City is: $cityName \n
                addre: $address \n
                sub loc: $subLocality \n
                state: $state \n
                country: $country \n
                postalcode: $postalCode \n
                knownName: $knownName
                """.trimIndent()
            Log.d("data", s)


        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("data", "onActivityResult ${requestCode}")
       /* if (requestCode == requestchecksetting) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "Gps is turned on", Toast.LENGTH_SHORT).show()
                MgetLocation()

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(context, "Gps is cancel", Toast.LENGTH_SHORT).show()

            }
        }
        if (requestCode == requestcheckper) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(context, "Gps is turned on", Toast.LENGTH_SHORT).show()
               getlocation()

            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(context, "Gps is cancel", Toast.LENGTH_SHORT).show()

            }
        }*/
        if (requestCode == gpslocationrequest) {
            if (resultCode == Activity.RESULT_OK) {
                getlocation()
                Log.d("data","add farm gps enable")
                Toast.makeText(context, "Gps is turned on", Toast.LENGTH_SHORT).show()

            } else {
                loc()
                Log.d("data","add farm gps not enable")
                Toast.makeText(context, "Gps is cancel", Toast.LENGTH_SHORT).show()

            }



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
                    if (!gpslocation)
                        turnGPSOn()
                    Log.d("data", "Gps is turned on")

                } else if (grantResults[i] == Activity.RESULT_CANCELED) {
                    Log.d("data", "Gps is cancel")

                }
            }


        }

        Log.d("data", "onRequestPermissionsResult add farm frag ${requestCode} ${grantResults.toString()}")

    }

    override fun onDetach() {
        isdetach = true
        super.onDetach()
    }


}