package com.antino.eggoz.retrofit

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.antino.eggoz.room.RoomCart
import com.antino.eggoz.ui.Summary.model.FlockGraph
import com.antino.eggoz.ui.activity_log.model.ExpensesList
import com.antino.eggoz.ui.daily_input.model.AddDailyInput
import com.antino.eggoz.ui.daily_input.model.MedList
import com.antino.eggoz.ui.expenses.Model.Division
import com.antino.eggoz.ui.expenses.Model.Party
import com.antino.eggoz.ui.expenses.Model.SubDivision
import com.antino.eggoz.ui.feed.model.Comment
import com.antino.eggoz.ui.feed.model.FeedData
import com.antino.eggoz.ui.feed.model.PostComment
import com.antino.eggoz.ui.home.model.*
import com.antino.eggoz.ui.profile.Model.Farm
import com.antino.eggoz.ui.profile.Model.FarmerProfile
import com.antino.eggoz.ui.profile.Model.FlockBreed
import com.antino.eggoz.ui.sell_shop.model.*
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.view.data.*
import com.antino.eggoz.view.model.ZoneList
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.collections.HashMap


class Retrofithit {

    val mutableLiveSignupUser = MutableLiveData<SignUpUser>()
    val mutableLiveSignup2User = MutableLiveData<Signup2>()
    val mutableLiveSignup3User = MutableLiveData<LoginUser>()
    val mutableLiveSignupComplete = MutableLiveData<SignupComplete>()
    val mutableCityList = MutableLiveData<CityList>()
    val mutableLiveSigninUser = MutableLiveData<LoginUser>()
    val mutableLiveUser = MutableLiveData<LoginUser>()
    val mutableLiveCreateFarm = MutableLiveData<User>()
    val mutableLivegetFarm = MutableLiveData<Farm>()
    val flockBreedlist = MutableLiveData<FlockBreed>()
    val addDailyInput = MutableLiveData<AddDailyInput>()
    val mutableLiveFlockGraph = MutableLiveData<FlockGraph>()
    val mutableLiveAddFlock = MutableLiveData<User>()
    val mutableLiveEditProfile = MutableLiveData<LoginUser>()
    val mutableLiveFarmerProfile = MutableLiveData<FarmerProfile>()
    val mutableMedList = MutableLiveData<MedList>()
    val mutableLiveSchedule = MutableLiveData<User>()
    val mutableLivePart = MutableLiveData<Party>()
    val mutableLiveDivision = MutableLiveData<Division>()
    val mutableLiveSubDivision = MutableLiveData<SubDivision>()
    val mutableLiveExpense = MutableLiveData<LoginUser>()
    val mutableLivegetExpense = MutableLiveData<ExpensesList>()
    val mutableLiveLikedislikecoment = MutableLiveData<LoginUser>()
    val addShed = MutableLiveData<User>()
    val mutableLiveFeedData = MutableLiveData<FeedData>()
    val mutableLiveItemList = MutableLiveData<ItemList>()
    val mutableLiveItemDetail = MutableLiveData<ItemDetail>()
    val mutableLiveCatList = MutableLiveData<CatList>()
    val mutableLiveComment = MutableLiveData<Comment>()
    val mutableLivePostComment = MutableLiveData<PostComment>()
    val mutableLiveCartBuy = MutableLiveData<CartBuy>()
    val mutableLiveBanner = MutableLiveData<Banner>()
    val mutableLivePopularProducts = MutableLiveData<PopularProducts>()
    val mutableLiveZoneList = MutableLiveData<ZoneList>()
    val mutableLiveNeccRate = MutableLiveData<NeccRate>()
    val mutableLiveWordpressFeed = MutableLiveData<WordpressFeed>()
    val mutableLiveWeatherData = MutableLiveData<WeatherData>()
    val mutableLiveVideoList = MutableLiveData<VideoList>()
    val mutableLiveIotData = MutableLiveData<IotData>()
    val mutableLiveIotRequest = MutableLiveData<LoginUser>()




    fun signup1(mobile_no: String): MutableLiveData<SignUpUser> {
        val mm = RequestBody.create(MediaType.parse("multipart/form-data"), mobile_no)
        val call: Call<SignUpUser> = RetrofitClient().getApi().signup1(
            mm
        )
        call.enqueue(object : Callback<SignUpUser> {
            override fun onResponse(
                call: Call<SignUpUser>,
                response: Response<SignUpUser>
            ) {
                Log.d("data","${response.body()}")
                try {
                    if (response.isSuccessful) {
                        mutableLiveSignupUser.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<SignUpUser>() {}.type
                        val errorResponse: SignUpUser? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveSignupUser.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<SignUpUser>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveSignupUser

    }

    fun createFarm(
        token: String,
        id: Int,
        farmName: String,
        buildingno: String,
        landmark: String,
        city: String,
        state: String,
        pincode: String,
        NoOfBroiler:Int,number_of_layer_shed:Int,number_of_grower_shed:Int,farm_layer_type:String
    ): MutableLiveData<User> {
        val body = HashMap<String, Any>()
        body["farm_name"] = farmName
        body["farmer"] = id
        body["number_of_layer_shed"] = number_of_layer_shed
        body["number_of_grower_shed"] = number_of_grower_shed

        body["number_of_broiler_shed"] = NoOfBroiler
        body["farm_layer_type"] = farm_layer_type

        val address = HashMap<String, Any>()
        address["building_address"] = buildingno
        address["street_address"] = state
        address["landmark"] = landmark
        address["billing_city"] = city
        address["pinCode"] = Integer.parseInt(pincode)

        body["farm_address"] = address


        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<User> = RetrofitClient().getApi().createFarm(
            body, headerMap
        )
        call.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                Log.d("data","${response.body()} body ${body.toString()}")
                try {
                    if (response.isSuccessful) {
                        mutableLiveCreateFarm.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<User>() {}.type
                        val errorResponse: User? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveCreateFarm.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveCreateFarm

    }

    fun flockBreed(token: String): MutableLiveData<FlockBreed> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<FlockBreed> = RetrofitClient().getApi().flockBreed(
            headerMap
        )
        call.enqueue(object : Callback<FlockBreed> {
            override fun onResponse(
                call: Call<FlockBreed>,
                response: Response<FlockBreed>
            ) {
                try {
                    if (response.isSuccessful) {
                        flockBreedlist.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<FlockBreed>() {}.type
                        val errorResponse: FlockBreed? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        flockBreedlist.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<FlockBreed>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return flockBreedlist

    }

    fun addShed(
        token: String,
        shedType: String,
        shedName: String,
        farmid: Int,
        totalActiveBirdCapacity: String,
        flockName: ArrayList<String>,
        flockbreedid: ArrayList<Int>,
        flockage: ArrayList<String>,
        flockquantity: ArrayList<String>,
        eggtype: ArrayList<String>

    ): MutableLiveData<User> {

        val body = JsonObject()

        body.addProperty("shed_type", shedType)
        body.addProperty("shed_name", shedName)
        body.addProperty("farm", farmid)
        body.addProperty("total_active_bird_capacity", Integer.parseInt(totalActiveBirdCapacity))

        val flockarray = JsonArray()

        for (i in 0 until flockName.size) {
            val flockdata = JsonObject()
            flockdata.addProperty("flock_name", flockName[i])
            flockdata.addProperty("breed", flockbreedid[i])
            flockdata.addProperty("age", Integer.parseInt(flockage[i]))

            flockdata.addProperty("initial_capacity", Integer.parseInt(flockquantity[i]))
            flockdata.addProperty("egg_type", eggtype[i])
            flockdata.addProperty("initial_production", 0)

            flockarray.add(flockdata)

        }

        body.add("flocks", flockarray)

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<User> = RetrofitClient().getApi().addShed(
            body,
            headerMap

        )
        call.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                try {
                    Log.d("data", "${body} \n ${headerMap}")
                    if (response.isSuccessful) {
                        addShed.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<User>() {}.type
                        val errorResponse: User? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        addShed.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return addShed

    }

    fun addFlock(
        token: String,
        shed: Int,
        flock_name: String,
        breed: Int,
        age: String,
        initial_capacity: String,
        egg_type: String,
        initial_production: String

    ): MutableLiveData<User> {

        val body = JsonObject()

        body.addProperty("shed", shed)
        body.addProperty("flock_name", flock_name)
        body.addProperty("breed", breed)
        body.addProperty("age", Integer.parseInt(age))
        body.addProperty("initial_capacity", Integer.parseInt(initial_capacity))
        body.addProperty("egg_type", egg_type)
        body.addProperty("initial_production", Integer.parseInt(initial_production))


        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<User> = RetrofitClient().getApi().addFlock(
            body,
            headerMap

        )
        call.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                try {
                    Log.d("data", "${body} \n ${headerMap}")
                    if (response.isSuccessful) {
                        mutableLiveAddFlock.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<User>() {}.type
                        val errorResponse: User? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveAddFlock.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveAddFlock

    }


    fun addDailyInput(
        token: String,
        id: Int,
        date: String,
        egg_production: Int,
        mortality: Int,
        feed: Int,
        culling: Int,
        bokenEggInproduction: Int,
        brokenEggInOperation: Int,
        remark: String,
        medlistidpost: ArrayList<Int>,
        medlistqnt: ArrayList<Int>
    ):
            MutableLiveData<AddDailyInput> {

        val body = JsonObject()

        body.addProperty("flock", id)
        body.addProperty("date", date)
        body.addProperty("egg_daily_production", egg_production)
        body.addProperty("mortality", mortality)
        body.addProperty("feed", feed)
        body.addProperty("culls", culling)
        body.addProperty("broken_egg_in_production", bokenEggInproduction)
        body.addProperty("broken_egg_in_operation", brokenEggInOperation)
        body.addProperty("remarks", remark)

        val flockmedarray = JsonArray()

        for (i in 0 until medlistidpost.size) {
            val flockmed = JsonObject()
            flockmed.addProperty("quantity", medlistqnt[i])
            flockmed.addProperty("feedMedicine", medlistidpost[i])

            flockmedarray.add(flockmed)

        }

        body.add("medicine_inputs", flockmedarray)

        /* val body = HashMap<String, Any>()
         body["flock"] = id
         body["date"] = date
         body["egg_daily_production"] = egg_production
         body["mortality"] = mortality
         body["feed"] = feed
         body["culls"] = culling
         body["broken_egg_in_production"] = bokenEggInproduction
         body["broken_egg_in_operation"] = brokenEggInOperation
         body["remarks"] = remark
 */

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<AddDailyInput> = RetrofitClient().getApi().addDailyInput(
            body, headerMap
        )
        call.enqueue(object : Callback<AddDailyInput> {
            override fun onResponse(
                call: Call<AddDailyInput>,
                response: Response<AddDailyInput>
            ) {
                Log.d("data", "boady ${body.toString()}")
                try {
                    if (response.isSuccessful) {
                        addDailyInput.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<AddDailyInput>() {}.type
                        val errorResponse: AddDailyInput? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        addDailyInput.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<AddDailyInput>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return addDailyInput

    }

    fun getMedlist(token: String): MutableLiveData<MedList> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<MedList> = RetrofitClient().getApi().getMedList(
            headerMap
        )
        call.enqueue(object : Callback<MedList> {
            override fun onResponse(
                call: Call<MedList>,
                response: Response<MedList>
            ) {
                try {
                    if (response.isSuccessful) {
                        mutableMedList.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<MedList>() {}.type
                        val errorResponse: MedList? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableMedList.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<MedList>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableMedList

    }

    fun getParty(token: String): MutableLiveData<Party> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<Party> = RetrofitClient().getApi().getParty(
            headerMap
        )
        call.enqueue(object : Callback<Party> {
            override fun onResponse(
                call: Call<Party>,
                response: Response<Party>
            ) {
                try {
                    if (response.isSuccessful) {
                        mutableLivePart.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<Party>() {}.type
                        val errorResponse: Party? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLivePart.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<Party>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLivePart

    }

    fun getdivision(token: String): MutableLiveData<Division> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<Division> = RetrofitClient().getApi().getdivision(
            headerMap
        )
        call.enqueue(object : Callback<Division> {
            override fun onResponse(
                call: Call<Division>,
                response: Response<Division>
            ) {
                try {
                    if (response.isSuccessful) {
                        mutableLiveDivision.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<Division>() {}.type
                        val errorResponse: Division? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveDivision.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<Division>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveDivision

    }

    fun getsubdivision(token: String, id: Int): MutableLiveData<SubDivision> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<SubDivision> = RetrofitClient().getApi().getsubdivision(
            headerMap, id
        )
        call.enqueue(object : Callback<SubDivision> {
            override fun onResponse(
                call: Call<SubDivision>,
                response: Response<SubDivision>
            ) {
                Log.d("data", "url ==${response.raw().request().url()}")
                try {
                    if (response.isSuccessful) {
                        mutableLiveSubDivision.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<SubDivision>() {}.type
                        val errorResponse: SubDivision? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveSubDivision.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<SubDivision>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveSubDivision

    }

    fun expenses(
        token: String,
        farmerid: Int,
        date: String,
        party: Int,
        productSubDivision: Int,
        quantity: String,
        amount: String,
        remark: String
    ):
            MutableLiveData<LoginUser> {
        val body = HashMap<String, Any>()
        body["farmer"] = farmerid
        body["date"] = date
        body["party"] = party
        body["productSubDivision"] = productSubDivision
        body["quantity"] = amount.toDouble()
        body["remark"] = remark
        body["amount"] = quantity.toDouble()

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<LoginUser> = RetrofitClient().getApi().expenses(
            body, headerMap
        )
        call.enqueue(object : Callback<LoginUser> {
            override fun onResponse(
                call: Call<LoginUser>,
                response: Response<LoginUser>
            ) {
                Log.d("data", "url ==${response.raw().request().url()}")
                try {
                    if (response.isSuccessful) {
                        mutableLiveExpense.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<LoginUser>() {}.type
                        val errorResponse: LoginUser? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveExpense.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveExpense

    }

    fun getexpenses(token: String, farmerid: Int):
            MutableLiveData<ExpensesList> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<ExpensesList> = RetrofitClient().getApi().getexpenses(
            headerMap, farmerid
        )
        call.enqueue(object : Callback<ExpensesList> {
            override fun onResponse(
                call: Call<ExpensesList>,
                response: Response<ExpensesList>
            ) {
                Log.d("data", "url ==${response.raw().request().url()}")
                try {
                    if (response.isSuccessful) {
                        mutableLivegetExpense.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ExpensesList>() {}.type
                        val errorResponse: ExpensesList? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLivegetExpense.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<ExpensesList>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLivegetExpense

    }


    fun getFlockData(token: String, id: Int, days: Int): MutableLiveData<FlockGraph> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<FlockGraph> = RetrofitClient().getApi().getFlockData(
            id, headerMap
        )
        call.enqueue(object : Callback<FlockGraph> {
            override fun onResponse(
                call: Call<FlockGraph>,
                response: Response<FlockGraph>
            ) {
                Log.d("data","${response.body()}")
                try {
                    if (response.isSuccessful) {
                        mutableLiveFlockGraph.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<FlockGraph>() {}.type
                        val errorResponse: FlockGraph? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveFlockGraph.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<FlockGraph>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveFlockGraph

    }

    fun addSchedule(
        token: String,
        id: Int,
        date: String,
        whiteEgg: Int,
        brownEgg: Int,
        KadaknathEgg: Int
    ): MutableLiveData<User> {

        val body = JsonObject()

        body.addProperty("farm", id)
        body.addProperty("date", date)

        val flockmedarray = JsonArray()

        var orderInlinesobj: JsonObject? = null
        orderInlinesobj = JsonObject()
        orderInlinesobj.addProperty("egg_type", "White")
        orderInlinesobj.addProperty("quantity", whiteEgg)

        flockmedarray.add(orderInlinesobj)
        orderInlinesobj = null

        orderInlinesobj = JsonObject()

        orderInlinesobj.addProperty("egg_type", "Brown")
        orderInlinesobj.addProperty("quantity", brownEgg)

        flockmedarray.add(orderInlinesobj)
        orderInlinesobj = null

        orderInlinesobj = JsonObject()

        orderInlinesobj.addProperty("egg_type", "Kadaknath")
        orderInlinesobj.addProperty("quantity", KadaknathEgg)

        flockmedarray.add(orderInlinesobj)
        orderInlinesobj = null

        body.add("orderInlines", flockmedarray)

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<User> = RetrofitClient().getApi().addSchedule(
            body, headerMap
        )
        call.enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                try {
                    if (response.isSuccessful) {
                        mutableLiveSchedule.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<User>() {}.type
                        val errorResponse: User? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveSchedule.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveSchedule

    }


    fun getFarm(token: String, id: Int): MutableLiveData<Farm> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<Farm> = RetrofitClient().getApi().getFarm(
            id, headerMap
        )
        call.enqueue(object : Callback<Farm> {
            override fun onResponse(
                call: Call<Farm>,
                response: Response<Farm>
            ) {
                Log.d("data", "${response.body()}")
                try {
                    if (response.isSuccessful) {
                        mutableLivegetFarm.value = response.body()
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<Farm>() {}.type
                        val errorResponse: Farm? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLivegetFarm.value = errorResponse
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<Farm>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLivegetFarm

    }


    fun signup2(mobile_no: String, otp: String): MutableLiveData<Signup2> {
//        val mm = RequestBody.create(MediaType.parse("multipart/form-data"), mobile_no)
//        val ot = RequestBody.create(MediaType.parse("multipart/form-data"), otp)
        val call: Call<Signup2> = RetrofitClient().getApi().signup2otp(
            mobile_no, otp
        )
        call.enqueue(object : Callback<Signup2> {
            override fun onResponse(
                call: Call<Signup2>,
                response: Response<Signup2>
            ) {
                try {
                    if (response.isSuccessful) {
                        mutableLiveSignup2User.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<Signup2>() {}.type
                        val errorResponse: Signup2? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveSignup2User.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<Signup2>, t: Throwable) {
                Log.d("data", "onFailure ${t.message.toString()} \n ${mobile_no} $otp")
            }
        })
        return mutableLiveSignup2User

    }

    fun signup3(mobile_no: String, password: String): MutableLiveData<LoginUser> {
        val mm = RequestBody.create(MediaType.parse("multipart/form-data"), mobile_no)
        val pass = RequestBody.create(MediaType.parse("multipart/form-data"), password)
        val call: Call<LoginUser> = RetrofitClient().getApi().signup3pass(
            mm, pass
        )
        call.enqueue(object : Callback<LoginUser> {
            override fun onResponse(
                call: Call<LoginUser>,
                response: Response<LoginUser>
            ) {
                try {
                    if (response.isSuccessful) {
                        mutableLiveSignup3User.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<LoginUser>() {}.type
                        val errorResponse: LoginUser? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveSignup3User.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveSignup3User

    }

    fun signup5(
        token: String,
        farmer_id: Int,
        farmer_name: String,
        farmer_pinCode: Int,
        zoneid:Int
    ): MutableLiveData<LoginUser> {
        val body = HashMap<String, Any>()
        body["farmer"] = farmer_id
        body["farmer_name"] = farmer_name
        body["pinCode"] = farmer_pinCode
        body["necc_zone"] = zoneid

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<LoginUser> = RetrofitClient().getApi().sigup5(
            body, headerMap
        )
        call.enqueue(object : Callback<LoginUser> {
            override fun onResponse(
                call: Call<LoginUser>,
                response: Response<LoginUser>
            ) {
                Log.d("data","body ${response.body()} \n ${body}")
                try {
                    if (response.isSuccessful) {
                        val user = LoginUser(
                            "", null, null, "", "", "", "", "", "", "", ""
                        )
                        mutableLiveUser.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<LoginUser>() {}.type
                        val errorResponse: LoginUser? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveUser.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<LoginUser?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveUser
    }

    fun editProfile(
        token: String,
        farmer_id: Int,
        farmer_name: String,
        phone: String,
        email: String
    ): MutableLiveData<LoginUser> {
        val body = HashMap<String, Any>()
        body["farmer"] = farmer_id
        body["farmer_name"] = farmer_name
        body["phone_no"] = phone
        body["email"] = email

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<LoginUser> = RetrofitClient().getApi().editProfile(
            body, headerMap
        )
        call.enqueue(object : Callback<LoginUser> {
            override fun onResponse(
                call: Call<LoginUser>,
                response: Response<LoginUser>
            ) {
                try {
                    if (response.isSuccessful) {
                        val user = LoginUser(
                            "", null, null, "", "", "", "", "", "", "", ""
                        )
                        mutableLiveEditProfile.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<LoginUser>() {}.type
                        val errorResponse: LoginUser? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveEditProfile.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<LoginUser?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveEditProfile
    }

    fun farmerProfile(
        token: String,
        farmer_id: Int
    ): MutableLiveData<FarmerProfile> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<FarmerProfile> = RetrofitClient().getApi().farmerProfile(
            farmer_id, headerMap
        )
        call.enqueue(object : Callback<FarmerProfile> {
            override fun onResponse(
                call: Call<FarmerProfile>,
                response: Response<FarmerProfile>
            ) {

                Log.d("data", "profile ${farmer_id.toString()}")
                try {
                    if (response.isSuccessful) {
                        mutableLiveFarmerProfile.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<FarmerProfile>() {}.type
                        val errorResponse: FarmerProfile? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveFarmerProfile.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<FarmerProfile?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveFarmerProfile
    }


    /*   fun signup5(token:String,farmer_id: String, farmer_name: String,farmer_pinCode:String): MutableLiveData<LoginUser> {
           val id = RequestBody.create(MediaType.parse("multipart/form-data"), farmer_id)
           val name = RequestBody.create(MediaType.parse("multipart/form-data"), farmer_name)
           val pincode = RequestBody.create(MediaType.parse("multipart/form-data"), farmer_pinCode)

          *//* var map: MutableMap<String, Any> =HashMap()
        map["farmer"] = farmer_id
        map["farmer_name"] = farmer_name
        map["pinCode"] = farmer_pinCode*//*

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer $token"

        val call: Call<LoginUser> = RetrofitClient().getApi().sigup5(
            id,name,pincode,headerMap
        )
        call.enqueue(object : Callback<LoginUser> {
            override fun onResponse(
                call: Call<LoginUser>,
                response: Response<LoginUser>
            ) {
                try {
                    if (response.isSuccessful) {
                        Log.d("data","isSuccessful")
                        val user=LoginUser("",null,null,"","","","",""
                        ,"","")
                        mutableLiveUser.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<LoginUser>() {}.type
                        val errorResponse: LoginUser? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveUser.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<LoginUser?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveUser

    }
*/

    fun signupcomplete(
        farmername: String, farmname: String, email: String,
        city: Int, address: String, pincode: Int,
        birdNumber: String, whiteDailyProduction: String, whiteBirdCapacity: String,
        BrownDailyProduction: String, BrownBirdCapacity: String,
        FormerType: String, FeedMixing: Boolean, FeedMixingRemark: String, mobile_no: String,
        token: String


    ): MutableLiveData<SignupComplete> {
        val map: MutableMap<String, Any> = HashMap()
        map["email"] = email
        map["farmer_name"] = farmername
        map["farm_name"] = farmname
        map["phone_no"] = mobile_no
        map["total_active_bird_capacity"] = Integer.parseInt(birdNumber)
        map["farmer_type"] = FormerType
        map["is_feed_mixed"] = FeedMixing
        map["feed_mix_remarks"] = FeedMixingRemark

/*


        val ufarmer_name = RequestBody.create(MediaType.parse("multipart/form-data"), farmername)
        val ufarm_name = RequestBody.create(MediaType.parse("multipart/form-data"), farmname)
        val uemail = RequestBody.create(MediaType.parse("multipart/form-data"), email)
        val uphone_no = RequestBody.create(MediaType.parse("multipart/form-data"), mobile_no)
        val totalcapacity = RequestBody.create(MediaType.parse("multipart/form-data"), birdNumber)
        val farmer_type = RequestBody.create(MediaType.parse("multipart/form-data"), FormerType)
        val is_feed_mixed =
            RequestBody.create(MediaType.parse("multipart/form-data"), FeedMixing.toString())
        val feed_mix_remarks =
            RequestBody.create(MediaType.parse("multipart/form-data"), FeedMixingRemark)

*/

        val jsonobjMap: MutableMap<String, JSONObject> = HashMap()
        val jsonObjectadd = JSONObject()

        jsonObjectadd.put("city", city.toString())
        jsonObjectadd.put("address_name", address)
        jsonObjectadd.put("pinCode", pincode.toString())
        jsonobjMap["farm_address"] = jsonObjectadd


        val jsonArrayMapbird: MutableMap<String, JSONArray> = HashMap()
        val jsonbirdArray = JSONArray()

        val jsonObjectbrownegg = JSONObject()
        jsonObjectbrownegg.put("egg_type", "Brown Eggs")
        jsonObjectbrownegg.put("egg_present_daily_production", BrownDailyProduction)
        jsonObjectbrownegg.put("bird_Capacity", BrownBirdCapacity)

        val jsonObjectWhiteegg = JSONObject()
        jsonObjectWhiteegg.put("egg_type", "White Eggs Eggs")
        jsonObjectWhiteegg.put("egg_present_daily_production", whiteDailyProduction)
        jsonObjectWhiteegg.put("bird_Capacity", whiteBirdCapacity)

        jsonbirdArray.put(jsonObjectbrownegg)
        jsonbirdArray.put(jsonObjectWhiteegg)

        jsonArrayMapbird["birds_capacity"] = jsonbirdArray

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<SignupComplete> = RetrofitClient().getApi().signupcomplete(
            map, headerMap,
            jsonobjMap, jsonArrayMapbird
        )
        call.enqueue(object : Callback<SignupComplete> {
            override fun onResponse(
                call: Call<SignupComplete>,
                response: Response<SignupComplete>
            ) {
                try {
                    Log.d("data", "responce code" + response.code().toString())
                    if (response.code() == 201) {
                        mutableLiveSignupComplete.postValue(
                            SignupComplete(
                                Collections.emptyList(),
                                "",
                                true
                            )
                        )
                    } else {
                        if (response.isSuccessful) {
                            mutableLiveSignupComplete.postValue(response.body())
                        } else {
                            val gson = Gson()
                            val type = object : TypeToken<SignupComplete>() {}.type
                            val errorResponse: SignupComplete? = gson.fromJson(
                                response.errorBody()!!.charStream(), type
                            )
                            mutableLiveSignupComplete.postValue(errorResponse)
                        }
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error ${ex.toString()}")
                }
            }

            override fun onFailure(call: Call<SignupComplete>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveSignupComplete

    }

    fun cityList(): MutableLiveData<CityList> {
        val call: Call<CityList> = RetrofitClient().getApi().cityList()
        call.enqueue(object : Callback<CityList> {
            override fun onResponse(
                call: Call<CityList>,
                response: Response<CityList>
            ) {
                try {
                    if (response.isSuccessful) {
                        mutableCityList.postValue(response.body())
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error ${ex.toString()}")
                }
            }

            override fun onFailure(call: Call<CityList>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableCityList

    }


    fun signin(mobile_no: String, password: String): MutableLiveData<LoginUser> {
        val mm = RequestBody.create(MediaType.parse("multipart/form-data"), mobile_no)
        val pp = RequestBody.create(MediaType.parse("multipart/form-data"), password)
        val call: Call<LoginUser> = RetrofitClient().getApi().Login(
            mm, pp
        )
        call.enqueue(object : Callback<LoginUser> {
            override fun onResponse(
                call: Call<LoginUser>,
                response: Response<LoginUser>
            ) {
                try {
                    mutableLiveSigninUser.postValue(null)
                    if (response.isSuccessful) {
                        mutableLiveSigninUser.postValue(response.body())
                    } else {

                        val gson = Gson()
                        val type = object : TypeToken<LoginUser>() {}.type
                        val errorResponse: LoginUser? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveSigninUser.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error ${ex.toString()}")
                }
            }

            override fun onFailure(call: Call<LoginUser>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveSigninUser

    }

    fun createFeed(
        token: String,
        heading: String,
        des: String,
        file: File,
        requestFile: RequestBody
    ):
            MutableLiveData<ExpensesList> {


        val body = MultipartBody.Part.createFormData("images", file.getName(), requestFile)

        /*     // add another part within the multipart request

             // add another part within the multipart request
             val descriptionString = "hello, this is description speaking"
             val description = RequestBody.create(
                 MultipartBody.FORM, descriptionString
             )
     */

        val heading = RequestBody.create(MediaType.parse("multipart/form-data"), heading)
        val des = RequestBody.create(MediaType.parse("multipart/form-data"), des)
        val author = RequestBody.create(MediaType.parse("multipart/form-data"), "1")

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token


        val call: Call<ExpensesList> = RetrofitClient().getApi().createFeed(
            headerMap, heading, des, author, body
        )
        call.enqueue(object : Callback<ExpensesList> {
            override fun onResponse(
                call: Call<ExpensesList>,
                response: Response<ExpensesList>
            ) {
                try {
                    if (response.code() == 201) {
                        mutableLivegetExpense.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ExpensesList>() {}.type
                        val errorResponse: ExpensesList? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLivegetExpense.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<ExpensesList>, t: Throwable) {
                Log.d("data", "fail" + t.message.toString())
            }
        })
        return mutableLivegetExpense

    }

    fun likedislikePost(
        token: String,
        post_comment: Int
    ): MutableLiveData<LoginUser> {


        val body = HashMap<String, Any>()
        body["post"] = post_comment


        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<LoginUser> = RetrofitClient().getApi().likedislikePost(
            headerMap, body
        )
        call.enqueue(object : Callback<LoginUser> {
            override fun onResponse(
                call: Call<LoginUser>,
                response: Response<LoginUser>
            ) {
                try {
                    if (response.code() == 201) {
                        mutableLiveLikedislikecoment.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<LoginUser>() {}.type
                        val errorResponse: LoginUser? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveLikedislikecoment.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<LoginUser?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveLikedislikecoment
    }


    fun likedislikeCommentPost(
        token: String,
        post_comment: Int
    ): MutableLiveData<LoginUser> {


        val body = HashMap<String, Any>()
        body["post_comment"] = post_comment

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<LoginUser> = RetrofitClient().getApi().likedislikecommentPost(
            headerMap, body
        )
        call.enqueue(object : Callback<LoginUser> {
            override fun onResponse(
                call: Call<LoginUser>,
                response: Response<LoginUser>
            ) {
                Log.d("data","${response.body()}")
                try {
                    if (response.code() == 201) {
                        mutableLiveLikedislikecoment.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<LoginUser>() {}.type
                        val errorResponse: LoginUser? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveLikedislikecoment.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<LoginUser?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveLikedislikecoment
    }

    fun nestedcomment(
        token: String, nested_comment: Int,
        comment_text: String
    ): MutableLiveData<PostComment> {

        val body = HashMap<String, Any>()
        body["post"] = nested_comment
        body["comment_text"] = comment_text

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<PostComment> = RetrofitClient().getApi().nestedcomment(
            headerMap, body
        )
        call.enqueue(object : Callback<PostComment> {
            override fun onResponse(
                call: Call<PostComment>,
                response: Response<PostComment>
            ) {
                Log.d("data", "${response.body().toString()}")
                try {
                    if (response.code() == 200) {
                        mutableLivePostComment.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<PostComment>() {}.type
                        val errorResponse: PostComment? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLivePostComment.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<PostComment?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLivePostComment
    }

    fun loadFeed(
        token: String
    ): MutableLiveData<FeedData> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<FeedData> = RetrofitClient().getApi().loadFeed(
            headerMap
        )
        call.enqueue(object : Callback<FeedData> {
            override fun onResponse(
                call: Call<FeedData>,
                response: Response<FeedData>
            ) {
                try {
                    if (response.code() == 200) {
                        mutableLiveFeedData.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<FeedData>() {}.type
                        val errorResponse: FeedData? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveFeedData.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<FeedData?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveFeedData
    }

    fun getItemList(
        token: String
    ): MutableLiveData<ItemList> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<ItemList> = RetrofitClient().getApi().getItemList(
            headerMap
        )
        call.enqueue(object : Callback<ItemList> {
            override fun onResponse(
                call: Call<ItemList>,
                response: Response<ItemList>
            ) {
                try {
                    if (response.code() == 200) {
                        mutableLiveItemList.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ItemList>() {}.type
                        val errorResponse: ItemList? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveItemList.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<ItemList?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveItemList
    }

    fun getItemDetail(
        token: String, id: String
    ): MutableLiveData<ItemDetail> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<ItemDetail> = RetrofitClient().getApi().getItemDetail(
            headerMap, id
        )
        call.enqueue(object : Callback<ItemDetail> {
            override fun onResponse(
                call: Call<ItemDetail>,
                response: Response<ItemDetail>
            ) {
                Log.d("data", response.toString())
                try {
                    if (response.code() == 200) {
                        mutableLiveItemDetail.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ItemDetail>() {}.type
                        val errorResponse: ItemDetail? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveItemDetail.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<ItemDetail?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveItemDetail
    }

    fun getCatList(
        token: String
    ): MutableLiveData<CatList> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<CatList> = RetrofitClient().getApi().getCatList(
            headerMap
        )
        call.enqueue(object : Callback<CatList> {
            override fun onResponse(
                call: Call<CatList>,
                response: Response<CatList>
            ) {
                Log.d("data", response.toString())
                try {
                    if (response.code() == 200) {
                        mutableLiveCatList.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<CatList>() {}.type
                        val errorResponse: CatList? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveCatList.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<CatList?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveCatList
    }


    fun getItemListbyid(
        token: String, id: Int
    ): MutableLiveData<ItemList> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<ItemList> = RetrofitClient().getApi().getItemListByid(
            headerMap, id
        )
        call.enqueue(object : Callback<ItemList> {
            override fun onResponse(
                call: Call<ItemList>,
                response: Response<ItemList>
            ) {
                try {
                    if (response.code() == 200) {
                        mutableLiveItemList.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ItemList>() {}.type
                        val errorResponse: ItemList? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveItemList.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<ItemList?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveItemList
    }

    fun getComment(
        token: String, id: Int
    ): MutableLiveData<Comment> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<Comment> = RetrofitClient().getApi().getComment(
            headerMap, id
        )
        call.enqueue(object : Callback<Comment> {
            override fun onResponse(
                call: Call<Comment>,
                response: Response<Comment>
            ) {
                Log.d("data", response.body().toString())
                try {
                    if (response.code() == 200) {
                        mutableLiveComment.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<Comment>() {}.type
                        val errorResponse: Comment? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveComment.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<Comment?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveComment
    }


    fun Consulting(token:String,message: String,title: String,file: File,requestFile: RequestBody,querytype:String
    ): MutableLiveData<Comment> {

        val message = RequestBody.create(MediaType.parse("multipart/form-data"), message)
        val title = RequestBody.create(MediaType.parse("multipart/form-data"), title)
        val querytype = RequestBody.create(MediaType.parse("multipart/form-data"), querytype)

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val body = MultipartBody.Part.createFormData("file", file.getName(), requestFile)


        val call: Call<Comment> = RetrofitClient().getApi().Consulting(
            headerMap, message,title,querytype,body
        )
        call.enqueue(object : Callback<Comment> {
            override fun onResponse(
                call: Call<Comment>,
                response: Response<Comment>
            ) {
                Log.d("data", response.body().toString())
                try {
                    if (response.code() == 201) {
                        mutableLiveComment.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<Comment>() {}.type
                        val errorResponse: Comment? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveComment.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<Comment?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveComment
    }

    fun help(
        token: String, message: String
    ): MutableLiveData<Comment> {

        val message = RequestBody.create(MediaType.parse("multipart/form-data"), message)
        val query_type = RequestBody.create(MediaType.parse("multipart/form-data"), "Help Query")

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token


        val call: Call<Comment> = RetrofitClient().getApi().Help(
                headerMap, message,query_type
        )
        call.enqueue(object : Callback<Comment> {
            override fun onResponse(
                call: Call<Comment>,
                response: Response<Comment>
            ) {
                Log.d("data", response.body().toString())
                try {
                    if (response.code() == 201) {
                        mutableLiveComment.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<Comment>() {}.type
                        val errorResponse: Comment? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveComment.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<Comment?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveComment
    }


    fun payBuynow(
        token: String, mid: Int, id: Int, qnt: Int, price: String,shipping_address:Int
    ): MutableLiveData<CartBuy> {

        val body = JsonObject()

        body.addProperty("farm", mid)
        body.addProperty("order_price_amount", price)
        body.addProperty("shipping_address",shipping_address)

        val cart = JsonArray()

        var cartobj: JsonObject? = null
        cartobj = JsonObject()
        cartobj.addProperty("feed_product", id)
        cartobj.addProperty("quantity", qnt)

        cart.add(cartobj)
        cartobj = null

        body.add("feed_order_lines", cart)

//        body.add("orderInlines", cart)


        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<CartBuy> = RetrofitClient().getApi().payBuynow(
            headerMap, body
        )
        call.enqueue(object : Callback<CartBuy> {
            override fun onResponse(
                call: Call<CartBuy>,
                response: Response<CartBuy>
            ) {
                Log.d("data", "${response.body().toString()} \n ${body.toString()}")
                try {
                    if (response.code() == 201) {
                        mutableLiveCartBuy.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<CartBuy>() {}.type
                        val errorResponse: CartBuy? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveCartBuy.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<CartBuy?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveCartBuy
    }

    fun cartBuy(
        token: String, mid: Int, roomcart: List<RoomCart>,shipping_address:Int
    ): MutableLiveData<CartBuy> {

        var price: Double = 0.0
        val body = JsonObject()

        body.addProperty("farm", mid)

        val cart = JsonArray()

        roomcart.indices.forEach { i ->
            var cartobj: JsonObject? = null
            cartobj = JsonObject()
            cartobj.addProperty("feed_product", roomcart[i].id)
            cartobj.addProperty("quantity", roomcart[i].quantaty)

            price += price + roomcart[i].price?.toDouble()!!

            cart.add(cartobj)
            cartobj = null

            body.add("feed_order_lines", cart)
        }

        body.addProperty("order_price_amount", price)
        body.addProperty("shipping_address", shipping_address)

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<CartBuy> = RetrofitClient().getApi().cartBuy(
            headerMap, body
        )
        call.enqueue(object : Callback<CartBuy> {
            override fun onResponse(
                call: Call<CartBuy>,
                response: Response<CartBuy>
            ) {
                Log.d("data", "buy ${response.body().toString()} \n ${body.toString()}")
                try {
                    if (response.code() == 201) {
                        mutableLiveCartBuy.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<CartBuy>() {}.type
                        val errorResponse: CartBuy? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveCartBuy.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<CartBuy?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveCartBuy
    }

    fun addAddress(token:String, AddressName:String, Building:String, Street:String,Landmark:String,City:String,Pincode:String): MutableLiveData<Comment> {

        val body = HashMap<String, Any>()

        val address = HashMap<String, Any>()
        address["building_address"] = Building
        address["street_address"] = Street
        address["landmark"] = Landmark
        address["billing_city"] = City
        address["pinCode"] = Integer.parseInt(Pincode)

        body["address"] = address

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<Comment> = RetrofitClient().getApi().addAddress(
            headerMap, body
        )
        call.enqueue(object : Callback<Comment> {
            override fun onResponse(
                call: Call<Comment>,
                response: Response<Comment>
            ) {
                Log.d("data", response.body().toString())
                try {
                    if (response.code() == 201) {
                        mutableLiveComment.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<Comment>() {}.type
                        val errorResponse: Comment? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveComment.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<Comment?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveComment
    }

    fun getBanner(
        token: String
    ): MutableLiveData<Banner> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<Banner> = RetrofitClient().getApi().getBanner(
            headerMap
        )
        call.enqueue(object : Callback<Banner> {
            override fun onResponse(
                call: Call<Banner>,
                response: Response<Banner>
            ) {
                Log.d("data", response.toString())
                try {
                    if (response.code() == 200) {
                        mutableLiveBanner.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<Banner>() {}.type
                        val errorResponse: Banner? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveBanner.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<Banner?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveBanner
    }

    fun popularProducts(token: String): MutableLiveData<PopularProducts> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<PopularProducts> = RetrofitClient().getApi().popularProducts(
            headerMap
        )
        call.enqueue(object : Callback<PopularProducts> {
            override fun onResponse(
                call: Call<PopularProducts>,
                response: Response<PopularProducts>
            ) {
                try {
                    if (response.isSuccessful) {
                        mutableLivePopularProducts.value = response.body()
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<PopularProducts>() {}.type
                        val errorResponse: PopularProducts? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLivePopularProducts.value = errorResponse
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<PopularProducts>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLivePopularProducts

    }



    fun addDailyInputWithWeight(
        token: String,
        id: Int,
        date: String,
        mortality: Int,
        feed: Int,
        culling: Int,
        remark: String,
        medlistidpost: ArrayList<Int>,
        medlistqnt: ArrayList<Int>,
        weight:Int
    ):
            MutableLiveData<AddDailyInput> {

        val body = JsonObject()

        body.addProperty("flock", id)
        body.addProperty("date", date)
        body.addProperty("mortality", mortality)
        body.addProperty("feed", feed)
        body.addProperty("culls", culling)
        body.addProperty("remarks", remark)
        body.addProperty("weight",weight)

        val flockmedarray = JsonArray()

        for (i in 0 until medlistidpost.size) {
            val flockmed = JsonObject()
            flockmed.addProperty("quantity", medlistqnt[i])
            flockmed.addProperty("feedMedicine", medlistidpost[i])

            flockmedarray.add(flockmed)

        }

        body.add("medicine_inputs", flockmedarray)

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<AddDailyInput> = RetrofitClient().getApi().addDailyInput(
            body, headerMap
        )
        call.enqueue(object : Callback<AddDailyInput> {
            override fun onResponse(
                call: Call<AddDailyInput>,
                response: Response<AddDailyInput>
            ) {
                Log.d("data", "boady ${body.toString()}")
                try {
                    if (response.isSuccessful) {
                        addDailyInput.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<AddDailyInput>() {}.type
                        val errorResponse: AddDailyInput? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        addDailyInput.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<AddDailyInput>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return addDailyInput

    }

    fun neccZone(
        token: String): MutableLiveData<ZoneList> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<ZoneList> = RetrofitClient().getApi().neccZone(
            headerMap
        )
        call.enqueue(object : Callback<ZoneList> {
            override fun onResponse(
                call: Call<ZoneList>,
                response: Response<ZoneList>
            ) {
                try {
                    if (response.isSuccessful) {
                        mutableLiveZoneList.postValue(response.body())
                    }else {
                        val gson = Gson()
                        val type = object : TypeToken<ZoneList>() {}.type
                        val errorResponse: ZoneList? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveZoneList.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error ${ex.toString()}")
                }
            }

            override fun onFailure(call: Call<ZoneList>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveZoneList

    }

    fun neccRate(token: String,mid:Int): MutableLiveData<NeccRate> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<NeccRate> = RetrofitClient().getApi().neccRate(
            headerMap,mid
        )
        call.enqueue(object : Callback<NeccRate> {
            override fun onResponse(
                call: Call<NeccRate>,
                response: Response<NeccRate>
            ) {
                Log.d("data","necc ${response.body()}")
                try {
                    if (response.isSuccessful) {
                        mutableLiveNeccRate.postValue(response.body())
                    }else {
                        val gson = Gson()
                        val type = object : TypeToken<NeccRate>() {}.type
                        val errorResponse: NeccRate? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveNeccRate.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error ${ex.toString()}")
                }
            }

            override fun onFailure(call: Call<NeccRate>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveNeccRate

    }

    fun wordpressFeed(
        token: String
    ): MutableLiveData<WordpressFeed> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<WordpressFeed> = RetrofitClient().getApi().wordpressFeed( headerMap
        )
        call.enqueue(object : Callback<WordpressFeed> {
            override fun onResponse(
                call: Call<WordpressFeed>,
                response: Response<WordpressFeed>
            ) {

                Log.d("suggestion","suggestion ${response.body()}")
                try {
                    if (response.isSuccessful) {
                        mutableLiveWordpressFeed.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<WordpressFeed>() {}.type
                        val errorResponse: WordpressFeed? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveWordpressFeed.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("suggestion", "error $ex")
                }
            }

            override fun onFailure(call: Call<WordpressFeed?>, t: Throwable) {
                Log.d("suggestion", t.message.toString())
            }
        })
        return mutableLiveWordpressFeed
    }

    fun getTemp(lat: Double, log: Double,token: String): MutableLiveData<WeatherData> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<WeatherData> = RetrofitClient().getApi().getTemp(
            lat,log,headerMap
        )
        call.enqueue(object : Callback<WeatherData> {
            override fun onResponse(
                call: Call<WeatherData>,
                response: Response<WeatherData>
            ) {

                try {
                    if (response.code()==200) {
                        mutableLiveWeatherData.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<WeatherData>() {}.type
                        val errorResponse: WeatherData? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveWeatherData.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<WeatherData?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveWeatherData
    }

    fun getvideo(
        token: String
    ): MutableLiveData<VideoList> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<VideoList> = RetrofitClient().getApi().getVideo( headerMap
        )
        call.enqueue(object : Callback<VideoList> {
            override fun onResponse(
                call: Call<VideoList>,
                response: Response<VideoList>
            ) {

                try {
                    if (response.isSuccessful) {
                        mutableLiveVideoList.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<VideoList>() {}.type
                        val errorResponse: VideoList? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveVideoList.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<VideoList?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveVideoList
    }


    fun profileImage(
        token: String,
        file: File,
        requestFile: RequestBody
    ):
            MutableLiveData<ExpensesList> {


        val body = MultipartBody.Part.createFormData("image", file.getName(), requestFile)


        val author = RequestBody.create(MediaType.parse("multipart/form-data"), "1")

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token


        val call: Call<ExpensesList> = RetrofitClient().getApi().profileImage(
            headerMap, body
        )
        call.enqueue(object : Callback<ExpensesList> {
            override fun onResponse(
                call: Call<ExpensesList>,
                response: Response<ExpensesList>
            ) {
                try {
                    if (response.code() == 201) {
                        mutableLivegetExpense.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<ExpensesList>() {}.type
                        val errorResponse: ExpensesList? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLivegetExpense.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<ExpensesList>, t: Throwable) {
                Log.d("data", "fail" + t.message.toString())
            }
        })
        return mutableLivegetExpense

    }

    fun neccRateall(token: String): MutableLiveData<NeccRate> {

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<NeccRate> = RetrofitClient().getApi().neccRateall(
            headerMap
        )
        call.enqueue(object : Callback<NeccRate> {
            override fun onResponse(
                call: Call<NeccRate>,
                response: Response<NeccRate>
            ) {
                Log.d("data","necc ${response.body()}")
                try {
                    if (response.isSuccessful) {
                        mutableLiveNeccRate.postValue(response.body())
                    }else {
                        val gson = Gson()
                        val type = object : TypeToken<NeccRate>() {}.type
                        val errorResponse: NeccRate? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveNeccRate.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error ${ex.toString()}")
                }
            }

            override fun onFailure(call: Call<NeccRate>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveNeccRate

    }

    fun getIot(
        id: String
    ): MutableLiveData<IotData> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = Constants.IotHeader

        val call: Call<IotData> = RetrofitClient().getApi().getIot(
            headerMap,
            id
        )
        call.enqueue(object : Callback<IotData> {
            override fun onResponse(
                call: Call<IotData>,
                response: Response<IotData>
            ) {
                Log.d("iot","${response.body()} ${call.request().url()}")
                try {
                    if (response.code()==200) {
                        mutableLiveIotData.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<IotData>() {}.type
                        val errorResponse: IotData? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveIotData.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("iot", "error $ex")
                }
            }

            override fun onFailure(call: Call<IotData?>, t: Throwable) {
                Log.d("iot", t.message.toString())
            }
        })
        return mutableLiveIotData
    }

    fun requestiot(
        token: String
    ): MutableLiveData<LoginUser> {

        val body = HashMap<String, Any>()
        body["title"] = "Iot requred"
        body["message"] = "iot message"

        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = token

        val call: Call<LoginUser> = RetrofitClient().getApi().requestIot(
            headerMap, body
        )
        call.enqueue(object : Callback<LoginUser> {
            override fun onResponse(
                call: Call<LoginUser>,
                response: Response<LoginUser>
            ) {
                Log.d("data", response.body().toString())
                try {
                    if (response.isSuccessful) {
                        mutableLiveIotRequest.postValue(response.body())
                    } else {
                        val gson = Gson()
                        val type = object : TypeToken<LoginUser>() {}.type
                        val errorResponse: LoginUser? = gson.fromJson(
                            response.errorBody()!!.charStream(), type
                        )
                        mutableLiveIotRequest.postValue(errorResponse)
                    }
                } catch (ex: Exception) {
                    Log.d("data", "error $ex")
                }
            }

            override fun onFailure(call: Call<LoginUser?>, t: Throwable) {
                Log.d("data", t.message.toString())
            }
        })
        return mutableLiveIotRequest
    }

}