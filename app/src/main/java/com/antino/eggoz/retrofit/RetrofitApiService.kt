package com.antino.eggoz.retrofit

import com.antino.eggoz.ui.Summary.model.FlockGraph
import com.antino.eggoz.ui.activity_log.model.ExpensesList
import com.antino.eggoz.ui.daily_input.model.AddDailyInput
import com.antino.eggoz.ui.daily_input.model.DailInput
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
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiService {
    @Multipart
    @POST(Constants.apiend_Login)
    fun Login(
        @Part("phone_no") mobile_no: RequestBody,
        @Part("password") pass: RequestBody
    ): Call<LoginUser>

    @Multipart
    @POST(Constants.apiend_generate)
    fun signup1(
        @Part("phone_no") mobile_no: RequestBody,
        @Part("hash_code") hashcode: RequestBody
    ): Call<SignUpUser>

    //    @Multipart
    @FormUrlEncoded
    @POST(Constants.apiend_validate)
    fun signup2otp(
        /*   @Part("phone_no") mobile_no: RequestBody,
           @Part("otp") otp: RequestBody*/
        @Field("phone_no") mobile_no: String,
        @Field("otp") otp: String
    ): Call<Signup2>

    @FormUrlEncoded
    @POST(Constants.apiend_submit_password)
    fun signup3pass(
        @Part("phone_no") mobile_no: RequestBody,
        @Part("password") otp: RequestBody
    ): Call<LoginUser>

    @GET(Constants.apiend_city)
    fun cityList(): Call<CityList>


    @POST(Constants.apiend_farm)
    fun createFarm(
        @Body body: HashMap<String, Any>,
        @HeaderMap headers: Map<String, String>
    ): Call<User>

    @PATCH(Constants.apiend_farmbyid)
    fun editFarm(
        @Path("id") id: Int,
        @Body body: HashMap<String, Any>,
        @HeaderMap headers: Map<String, String>
    ): Call<User>

    @GET(Constants.apiend_farm)
    fun getFarm(
        @Query("farmer") farm: Int,
        @HeaderMap headers: Map<String, String>
    ): Call<Farm>

    @GET(Constants.apiend_farmbyid)
    fun getFarmbyid(
        @Path("id") id: Int,
        @Query("farmer") farm: Int,
        @HeaderMap headers: Map<String, String>
    ): Call<Farm.Result>

    @GET(Constants.apiend_shedbyid)
    fun getShedbyid(
        @Path("id") id: Int,
        @HeaderMap headers: Map<String, String>
    ): Call<Farm.Result.Shed>

    @GET(Constants.apiend_flockbyid)
    fun getFlockbyid(
        @Path("id") id: Int,
        @HeaderMap headers: Map<String, String>
    ): Call<Farm.Result.Shed.Flock1>

    @GET(Constants.apiend_flock_graph)
    fun getFlockData(
        @Query("flock_id") farm: Int,
        @HeaderMap headers: Map<String, String>
    ): Call<FlockGraph>


    @GET(Constants.apiend_flock_breed)
    fun flockBreed(
        @HeaderMap headers: Map<String, String>
    ): Call<FlockBreed>

    @POST(Constants.apiend_shed)
    fun addShed(
        @Body body: JsonObject,
        @HeaderMap headers: Map<String, String>
    ): Call<User>

    @PATCH(Constants.apiend_shedbyid)
    fun editShed(
        @Path("id") id: Int,
        @Body body: JsonObject,
        @HeaderMap headers: Map<String, String>
    ): Call<User>

    @POST(Constants.apiend_order)
    fun addSchedule(
        @Body body: JsonObject,
        @HeaderMap headers: Map<String, String>
    ): Call<User>

    @POST(Constants.apiend_flock)
    fun addFlock(
        @Body body: JsonObject,
        @HeaderMap headers: Map<String, String>
    ): Call<User>

    @PATCH(Constants.apiend_flockbyid)
    fun editFlock(
        @Path("id") id: Int,
        @Body body: JsonObject,
        @HeaderMap headers: Map<String, String>
    ): Call<User>

    @POST(Constants.apiend_daily_input)
    fun addDailyInput(
        @Body body: JsonObject,
        @HeaderMap headers: Map<String, String>
    ): Call<AddDailyInput>

    @GET(Constants.apiend_feed_medicine)
    fun getMedList(
        @HeaderMap headers: Map<String, String>
    ): Call<MedList>

    @GET(Constants.apiend_party)
    fun getParty(
        @HeaderMap headers: Map<String, String>
    ): Call<Party>

    @GET(Constants.apiend_division)
    fun getdivision(
        @HeaderMap headers: Map<String, String>
    ): Call<Division>

    @GET(Constants.apiend_sub_division)
    fun getsubdivision(
        @HeaderMap headers: Map<String, String>,
        @Query("productDivision") productDivision: Int
    ): Call<SubDivision>


    @POST(Constants.apiend_expenses)
    fun expenses(
        @Body body: HashMap<String, Any>,
        @HeaderMap headers: Map<String, String>
    ): Call<LoginUser>

    @GET(Constants.apiend_expenses)
    fun getexpenses(
        @HeaderMap headers: Map<String, String>,
        @Query("farmer") farmer: Int
    ): Call<ExpensesList>

    @POST(Constants.apiend_farmer)
    fun sigup5(
        @Body body: HashMap<String, Any>,
        @HeaderMap headers: Map<String, String>
    ): Call<LoginUser>

    @POST(Constants.apiend_farmer)
    fun editProfile(
        @Body body: HashMap<String, Any>,
        @HeaderMap headers: Map<String, String>
    ): Call<LoginUser>

    @GET(Constants.apiend_farmer_id)
    fun farmerProfile(
        @Path("id") id: Int,
        @HeaderMap headers: Map<String, String>
    ): Call<FarmerProfile>

    @FormUrlEncoded
    @POST(Constants.apiend_registration)
    fun signupcomplete(
        @FieldMap params: MutableMap<String, Any>,
        @HeaderMap headers: Map<String, String>,
        @FieldMap add: MutableMap<String, JSONObject>,
        @FieldMap birds_capacity: MutableMap<String, JSONArray>
    ): Call<SignupComplete>

    @Multipart
    @POST(Constants.apiend_post)
    fun createFeed(
        @HeaderMap headers: Map<String, String>,
        @Part("heading") heading: RequestBody,
        @Part("description") description: RequestBody,
        @Part("author") author: RequestBody,
        @Part file: MultipartBody.Part

    ): Call<ExpensesList>

    @POST(Constants.apiend_post_like)
    fun likedislikePost(
        @HeaderMap headers: Map<String, String>,
        @Body body: HashMap<String, Any>
    ): Call<LoginUser>

    @POST(Constants.apiend_post_comment_like)
    fun likedislikecommentPost(
        @HeaderMap headers: Map<String, String>,
        @Body body: HashMap<String, Any>
    ): Call<LoginUser>

    @POST(Constants.apiend_post_comment)
    fun nestedcomment(
        @HeaderMap headers: Map<String, String>,
        @Body body: HashMap<String, Any>
    ): Call<PostComment>


    @GET(Constants.apiend_post)
    fun loadFeed(
        @HeaderMap headers: Map<String, String>
    ): Call<FeedData>

    @GET(Constants.apiend_feed_product)
    fun getItemList(
        @HeaderMap headers: Map<String, String>
    ): Call<ItemList>

    @GET(Constants.apiend_feed_product_id)
    fun getItemDetail(
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: String
    ): Call<ItemDetail>

    @GET(Constants.apiend_feed_division)
    fun getCatList(
        @HeaderMap headers: Map<String, String>
    ): Call<CatList>

    @GET(Constants.apiend_feed_product)
    fun getItemListByid(
        @HeaderMap headers: Map<String, String>,
        @Query("feedProductDivision") feedProductDivision: Int
    ): Call<ItemList>

    @GET(Constants.apiend_post_comment)
    fun getComment(
        @HeaderMap headers: Map<String, String>,
        @Query("post") post: Int
    ): Call<Comment>

    @Multipart
    @POST(Constants.apiend_feedback_farmer)
    fun Consulting(
        @HeaderMap headers: Map<String, String>,
        @Part("message") message: RequestBody,
        @Part("title") title: RequestBody,
        @Part("query_type") query_type: RequestBody,
        @Part file: MultipartBody.Part
    ): Call<Comment>

    @Multipart
    @POST(Constants.apiend_feedback_farmer)
    fun Consulting2(
        @HeaderMap headers: Map<String, String>,
        @Part("message") message: RequestBody,
        @Part("title") title: RequestBody,
        @Part("query_type") query_type: RequestBody
    ): Call<Comment>

    @Multipart


    @POST(Constants.apiend_feedback_farmer)
    fun Help(
        @HeaderMap headers: Map<String, String>,
        @Part("message") message: RequestBody,
        @Part("query_type") query_type: RequestBody
    ): Call<Comment>

    @POST(Constants.apiend_feed_order)
    fun payBuynow(
        @HeaderMap headers: Map<String, String>,
        @Body body: JsonObject
    ): Call<CartBuy>

    @POST(Constants.apiend_feed_order)
    fun cartBuy(
        @HeaderMap headers: Map<String, String>,
        @Body body: JsonObject
    ): Call<CartBuy>

    @POST(Constants.apiend_add_address)
    fun addAddress(
        @HeaderMap headers: Map<String, String>,
        @Body body: HashMap<String, Any>
    ): Call<Comment>


    @GET(Constants.apiend_banner)
    fun getBanner(
        @HeaderMap headers: Map<String, String>
    ): Call<Banner>

    @GET(Constants.apiend_feed_product_ispopular)
    fun popularProducts(
        @HeaderMap headers: Map<String, String>
    ): Call<PopularProducts>

    @GET(Constants.apiend_necc_zone)
    fun neccZone(
        @HeaderMap headers: Map<String, String>
    ): Call<ZoneList>

    @GET(Constants.apiend_necc_city_rate)
    fun neccRate(
        @HeaderMap headers: Map<String, String>,
        @Query("necc_city") necc_city: Int
    ): Call<NeccRate>

    @GET(Constants.apiend_wordpress)
    fun wordpressFeed(
        @Query("language") language: String,
        @HeaderMap headers: Map<String, String>
    ): Call<WordpressFeed>

    @GET(Constants.apiend_weather)
    fun getTemp(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @HeaderMap headers: Map<String, String>
    ): Call<WeatherData>

    @GET(Constants.apiend_weatherbypincode)
    fun getTemppincode(
        @Query("zip") zip: String,
        @Query("appid") appid: String,
        @HeaderMap headers: Map<String, String>
    ): Call<WeatherDataByCode>

    @GET(Constants.apiend_video)
    fun getVideo(
        @HeaderMap headers: Map<String, String>
    ): Call<VideoList>


    @Multipart
    @PUT(Constants.apiend_update_user)
    fun profileImage(
        @HeaderMap headers: Map<String, String>,
        @Part file: MultipartBody.Part
    ): Call<ExpensesList>

    @GET(Constants.apiend_necc_city_rate)
    fun neccRateall(
        @HeaderMap headers: Map<String, String>
    ): Call<NeccRate>

    @GET(Constants.IotUrl)
    fun getIot(
        @HeaderMap headers: Map<String, String>,
        @Query("device_id") device_id: String
    ): Call<IotData>

    @POST(Constants.apiend_iot_enquiry)
    fun requestIot(
        @HeaderMap headers: Map<String, String>,
        @Body body: HashMap<String, Any>
    ): Call<LoginUser>

    @GET(Constants.apiend_daily_input)
    fun getDailyInput(
        @HeaderMap headers: Map<String, String>,
        @Query("flock") flock: Int
    ): Call<DailInput>

    @PATCH(Constants.apiend_daily_input_id)
    fun addDailyInputUpdate(
        @Body body: JsonObject,
        @HeaderMap headers: Map<String, String>,
        @Path("id") id: String
    ): Call<AddDailyInput>
}
