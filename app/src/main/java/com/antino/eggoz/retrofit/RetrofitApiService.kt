package com.antino.eggoz.retrofit

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
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

interface RetrofitApiService {
    @Multipart
    @POST("user/login/")
    fun Login(
            @Part("phone_no") mobile_no: RequestBody,
            @Part("password") pass: RequestBody
    ): Call<LoginUser>

    @Multipart
    @POST("login_otp/generate/")
    fun signup1(@Part("phone_no") mobile_no: RequestBody): Call<SignUpUser>

    //    @Multipart
    @FormUrlEncoded
    @POST("login_otp/validate/")
    fun signup2otp(
            /*   @Part("phone_no") mobile_no: RequestBody,
               @Part("otp") otp: RequestBody*/
            @Field("phone_no") mobile_no: String,
            @Field("otp") otp: String
    ): Call<Signup2>

    @FormUrlEncoded
    @POST("user/submit_password/")
    fun signup3pass(
            @Part("phone_no") mobile_no: RequestBody,
            @Part("password") otp: RequestBody
    ): Call<LoginUser>

    @GET("base/city")
    fun cityList(): Call<CityList>


    @POST("farmer/farm/")
    fun createFarm(
            @Body body: HashMap<String, Any>,
            @HeaderMap headers: Map<String, String>
    ): Call<User>

    @GET("farmer/farm/")
    fun getFarm(
            @Query("farmer") farm: Int,
            @HeaderMap headers: Map<String, String>
    ): Call<Farm>

    @GET("farmer/flock_graph/")
    fun getFlockData(
            @Query("flock_id") farm: Int,
            @HeaderMap headers: Map<String, String>
    ): Call<FlockGraph>


    @GET("farmer/flock_breed/")
    fun flockBreed(
            @HeaderMap headers: Map<String, String>
    ): Call<FlockBreed>

    @POST("farmer/shed/")
    fun addShed(
            @Body body: JsonObject,
            @HeaderMap headers: Map<String, String>
    ): Call<User>

    @POST("farmer/order/")
    fun addSchedule(
            @Body body: JsonObject,
            @HeaderMap headers: Map<String, String>
    ): Call<User>

    @POST("farmer/flock/")
    fun addFlock(
            @Body body: JsonObject,
            @HeaderMap headers: Map<String, String>
    ): Call<User>

    @POST("farmer/daily_input/")
    fun addDailyInput(
            @Body body: JsonObject,
            @HeaderMap headers: Map<String, String>
    ): Call<AddDailyInput>

    @GET("farmer/feed_medicine/")
    fun getMedList(
            @HeaderMap headers: Map<String, String>
    ): Call<MedList>

    @GET("farmer/party/")
    fun getParty(
            @HeaderMap headers: Map<String, String>
    ): Call<Party>

    @GET("product/division/")
    fun getdivision(
            @HeaderMap headers: Map<String, String>
    ): Call<Division>

    @GET("product/sub_division/")
    fun getsubdivision(
            @HeaderMap headers: Map<String, String>,
            @Query("productDivision") productDivision: Int
    ): Call<SubDivision>


    @POST("farmer/expenses/")
    fun expenses(
            @Body body: HashMap<String, Any>,
            @HeaderMap headers: Map<String, String>
    ): Call<LoginUser>

    @GET("farmer/expenses/")
    fun getexpenses(
            @HeaderMap headers: Map<String, String>,
            @Query("farmer") farmer: Int
    ): Call<ExpensesList>

    /*@Multipart
    @POST("farmer/")
    fun sigup5( @Part("farmer") id: RequestBody ,
                @Part("farmer_name") farmer_name: RequestBody,
                @Part("pinCode") pinCode: RequestBody,
                @HeaderMap headers: Map<String, String>
               ): Call<LoginUser>*/
    @POST("farmer/")
    fun sigup5(
            @Body body: HashMap<String, Any>,
            @HeaderMap headers: Map<String, String>
    ): Call<LoginUser>

    @POST("farmer/")
    fun editProfile(
            @Body body: HashMap<String, Any>,
            @HeaderMap headers: Map<String, String>
    ): Call<LoginUser>

    @GET("/farmer/{id}/")
    fun farmerProfile(
            @Path("id") id: Int,
            @HeaderMap headers: Map<String, String>
    ): Call<FarmerProfile>

    /*@FormUrlEncoded
    @POST("farmer/registration/")
    fun signupcomplete(
        @Part("email") email: RequestBody, @Part("farmer_name") farmer_name: RequestBody,
        @Part("farm_name") farm_name: RequestBody, @Part("phone_no") phone_no: RequestBody,
        @Part("total_active_bird_capacity") capacity: RequestBody, @Part("farmer_type") farmer_type: RequestBody,
        @Part("is_feed_mixed") is_feed_mixed: RequestBody, @Part("feed_mix_remarks") feed_mix_remarks: RequestBody,
        @HeaderMap headers: Map<String, String>,
        @FieldMap add: MutableMap<String, JSONObject>,
        @QueryMap birds_capacity: MutableMap<String, JSONArray>): Call<SignupComplete>*/

    @FormUrlEncoded
    @POST("farmer/registration/")
    fun signupcomplete(
            @FieldMap params: MutableMap<String, Any>,
            @HeaderMap headers: Map<String, String>,
            @FieldMap add: MutableMap<String, JSONObject>,
            @FieldMap birds_capacity: MutableMap<String, JSONArray>
    ): Call<SignupComplete>

    @Multipart
    @POST("farmer/post/")
    fun createFeed(
            @HeaderMap headers: Map<String, String>,
            @Part("heading") heading: RequestBody,
            @Part("description") description: RequestBody,
            @Part("author") author: RequestBody,
            @Part file: MultipartBody.Part

    ): Call<ExpensesList>

    @POST("farmer/post_like/")
    fun likedislikePost(
            @HeaderMap headers: Map<String, String>,
            @Body body: HashMap<String, Any>
    ): Call<LoginUser>

    @POST("farmer/post_comment_like/")
    fun likedislikecommentPost(
            @HeaderMap headers: Map<String, String>,
            @Body body: HashMap<String, Any>
    ): Call<LoginUser>

    @POST("farmer/post_comment/")
    fun nestedcomment(
            @HeaderMap headers: Map<String, String>,
            @Body body: HashMap<String, Any>
    ): Call<PostComment>


    @GET("farmer/post/")
    fun loadFeed(
            @HeaderMap headers: Map<String, String>
    ): Call<FeedData>

    @GET("feed/feed_product/")
    fun getItemList(
            @HeaderMap headers: Map<String, String>
    ): Call<ItemList>

    @GET("feed/feed_product/{id}/")
    fun getItemDetail(
            @HeaderMap headers: Map<String, String>,
            @Path("id") id: String
    ): Call<ItemDetail>

    @GET("feed/division/")
    fun getCatList(
            @HeaderMap headers: Map<String, String>
    ): Call<CatList>

    @GET("feed/feed_product/")
    fun getItemListByid(
            @HeaderMap headers: Map<String, String>,
            @Query("feedProductDivision") feedProductDivision: Int
    ): Call<ItemList>

    @GET("farmer/post_comment/")
    fun getComment(
            @HeaderMap headers: Map<String, String>,
            @Query("post") post: Int
    ): Call<Comment>

    @Multipart
    @POST("api/feedback/farmer/")
    fun Consulting(
            @HeaderMap headers: Map<String, String>,
            @Part("message") message: RequestBody,
            @Part("title") title: RequestBody,
            @Part("query_type") query_type: RequestBody,
            @Part file: MultipartBody.Part
    ): Call<Comment>

    @Multipart


    @POST("api/feedback/farmer/")
    fun Help(
            @HeaderMap headers: Map<String, String>,
            @Part("message") message: RequestBody,
            @Part("query_type") query_type: RequestBody
    ): Call<Comment>

    @POST("feed/feed_order/")
    fun payBuynow(
            @HeaderMap headers: Map<String, String>,
            @Body body: JsonObject
    ): Call<CartBuy>

    @POST("feed/feed_order/")
    fun cartBuy(
            @HeaderMap headers: Map<String, String>,
            @Body body: JsonObject
    ): Call<CartBuy>

    @POST("users/add_address/")
    fun addAddress(
            @HeaderMap headers: Map<String, String>,
            @Body body: HashMap<String, Any>
    ): Call<Comment>


    @GET("farmer/banner/")
    fun getBanner(
            @HeaderMap headers: Map<String, String>
    ): Call<Banner>

    @GET("feed/feed_product?is_popular=true")
    fun popularProducts(
            @HeaderMap headers: Map<String, String>
    ): Call<PopularProducts>

    @GET("farmer/necc_zone/")
    fun neccZone(
            @HeaderMap headers: Map<String, String>
    ): Call<ZoneList>

    @GET("farmer/necc_city_rate/")
    fun neccRate(
            @HeaderMap headers: Map<String, String>,
            @Query("necc_city") necc_city: Int
    ): Call<NeccRate>

    @GET("farmer/wordpress/")
    fun wordpressFeed(
            @HeaderMap headers: Map<String, String>
    ): Call<WordpressFeed>

    @GET("farmer/weather/")
    fun getTemp(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @HeaderMap headers: Map<String, String>
    ): Call<WeatherData>

    @GET("base/video/")
    fun getVideo(
            @HeaderMap headers: Map<String, String>
    ): Call<VideoList>


    @Multipart
    @PUT("users/update_user/")
    fun profileImage(
            @HeaderMap headers: Map<String, String>,
            @Part file: MultipartBody.Part
    ): Call<ExpensesList>

    @GET("farmer/necc_city_rate/")
    fun neccRateall(
            @HeaderMap headers: Map<String, String>
    ): Call<NeccRate>

    @GET(Constants.IotUrl)
    fun getIot(
            @HeaderMap headers: Map<String, String>,
            @Query("device_id") device_id: String
    ): Call<IotData>

    @POST("farmer/iot_enquiry/")
    fun requestIot(
            @HeaderMap headers: Map<String, String>,
            @Body body: HashMap<String, Any>
    ): Call<LoginUser>
}
