package com.antino.eggoz.modelvew

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.antino.eggoz.retrofit.Retrofithit
import com.antino.eggoz.room.RoomCart
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
import com.antino.eggoz.view.data.*
import com.antino.eggoz.view.model.ZoneList
import okhttp3.RequestBody
import java.io.File

class ModelMain : ViewModel() {

    fun signup1(mobile: String,hash:String): MutableLiveData<SignUpUser> {
        return Retrofithit().signup1(mobile,hash)
    }

    fun signup2otp(mobile: String, otp: String): MutableLiveData<Signup2> {
        return Retrofithit().signup2(mobile, otp)
    }

    fun createFarm(
        token: String,
        id: Int,
        farmName: String,
        buildingno: String,
        landmark: String,
        city: String,
        state: String,
        pincode: String, NoOfBroiler:Int,number_of_layer_shed:Int,number_of_grower_shed:Int
        ,farm_layer_type:String
    ): MutableLiveData<User> {
        return Retrofithit().createFarm(
            token, id, farmName, buildingno, landmark, city, state,
            pincode,  NoOfBroiler,number_of_layer_shed,number_of_grower_shed
            ,farm_layer_type
        )
    }
    fun editFarm(
        token: String,
        farmid: Int,
        farmName: String,
        buildingno: String,
        landmark: String,
        city: String,
        state: String,
        pincode: String, NoOfBroiler:Int,number_of_layer_shed:Int,number_of_grower_shed:Int
        ,farm_layer_type:String,farmerid:Int
    ): MutableLiveData<User> {
        return Retrofithit().editFarm(
            token, farmid, farmName, buildingno, landmark, city, state,
            pincode,  NoOfBroiler,number_of_layer_shed,number_of_grower_shed
            ,farm_layer_type,farmerid
        )
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
        remark: String,medlistidpost:ArrayList<Int>,medlistqnt:ArrayList<Int>
    ): MutableLiveData<AddDailyInput> {
        return Retrofithit().addDailyInput(
            token,
            id,
            date,
            egg_production,
            mortality,
            feed,
            culling,
            bokenEggInproduction,
            brokenEggInOperation,
            remark,medlistidpost,medlistqnt
        )
    }


    fun getMedlist(
        token: String
    ): MutableLiveData<MedList> {
        return Retrofithit().getMedlist(
            token
        )
    }

    fun getParty(
        token: String
    ): MutableLiveData<Party> {
        return Retrofithit().getParty(
            token
        )
    }

    fun getExpense(
        token: String,
        id: Int
    ): MutableLiveData<ExpensesList> {
        return Retrofithit().getexpenses(
            token,id
        )
    }

    fun getdivision(
        token: String
    ): MutableLiveData<Division> {
        return Retrofithit().getdivision(
            token
        )
    }

    fun getsubdivision(
        token: String,id: Int
    ): MutableLiveData<SubDivision> {
        return Retrofithit().getsubdivision(
            token,id
        )
    }

    fun expenses(
        token: String,farmerid: Int,date:String,party:Int,productSubDivision:Int,quantity:String,amount:String,remark:String
    ): MutableLiveData<LoginUser> {
        return Retrofithit().expenses(
            token,farmerid,date,party,productSubDivision,quantity,amount,remark
        )
    }




    fun flockBreed(token: String): MutableLiveData<FlockBreed> {
        return Retrofithit().flockBreed(token)
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
        return Retrofithit().addShed(
            token, shedType, shedName, farmid, totalActiveBirdCapacity, flockName,
            flockbreedid, flockage, flockquantity, eggtype
        )
    }

    fun editShed(
        token: String,
        shedType: String,
        shedName: String,
        shadeid: Int,
        totalActiveBirdCapacity: String,
        flockName: ArrayList<String>,
        flockbreedid: ArrayList<Int>,
        flockage: ArrayList<String>,
        flockquantity: ArrayList<String>,
        eggtype: ArrayList<String>
    ): MutableLiveData<User> {
        return Retrofithit().editShed(
            token, shedType, shedName, shadeid, totalActiveBirdCapacity, flockName,
            flockbreedid, flockage, flockquantity, eggtype
        )
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
        return Retrofithit().addFlock(
            token,
            shed,
            flock_name,
            breed,
            age,
            initial_capacity,
            egg_type,
            initial_production
        )
    }

    fun editFlock(
        token: String,
        flockid: Int,
        flock_name: String,
        breed: Int,
        age: String,
        initial_capacity: String,
        egg_type: String,
        initial_production: String
    ): MutableLiveData<User> {
        return Retrofithit().editFlock(
            token,
            flockid,
            flock_name,
            breed,
            age,
            initial_capacity,
            egg_type,
            initial_production
        )
    }
    fun addSchedule(token: String, id: Int,date:String,whiteEgg:Int,brownEgg:Int,KadaknathEgg:Int): MutableLiveData<User> {
        return Retrofithit().addSchedule(token, id,date,whiteEgg,brownEgg,KadaknathEgg)
    }

    fun getFarm(token: String, id: Int): MutableLiveData<Farm> {
        return Retrofithit().getFarm(token, id)
    }

    fun getFarmbyid(token: String, id: Int, farmid: Int): MutableLiveData<Farm.Result> {
        return Retrofithit().getFarmbyid(token, id,farmid)
    }

    fun getShedbyid(token: String, id: Int): MutableLiveData<Farm.Result.Shed> {
        return Retrofithit().getShedbyid(token, id)
    }

    fun getFlockbyid(token: String, id: Int): MutableLiveData<Farm.Result.Shed.Flock1> {
        return Retrofithit().getFlockbyid(token, id)
    }

    fun getflockData(token: String, id: Int, days: Int): MutableLiveData<FlockGraph> {
        return Retrofithit().getFlockData(token, id, days)
    }

    fun signup3password(mobile: String, password: String): MutableLiveData<LoginUser> {
        return Retrofithit().signup3(mobile, password)
    }

    fun signupcomplete(
        farmername: String,
        farmname: String,
        email: String,
        city: Int,
        address: String,
        pincode: Int,
        birdNumber: String,
        whiteDailyProduction: String,
        whiteBirdCapacity: String,
        BrownDailyProduction: String,
        BrownBirdCapacity: String,
        FormerType: String,
        FeedMixing: String,
        mobile: String,
        token: String
    ): MutableLiveData<SignupComplete> {
        var fmix: Boolean = false
        if (FeedMixing.isEmpty() || FeedMixing == null) {
        } else {
            fmix = true
        }

        return Retrofithit().signupcomplete(
            farmername, farmname, email, city, address, pincode,
            birdNumber, whiteDailyProduction, whiteBirdCapacity,
            BrownDailyProduction, BrownBirdCapacity,
            FormerType, fmix, FeedMixing, mobile, token
        )
    }

    fun cityList(): MutableLiveData<CityList> {
        return Retrofithit().cityList()
    }

    fun saveName(token: String, id: Int, name: String, pincode: Int,zone_id:Int): MutableLiveData<LoginUser> {
        return Retrofithit().signup5(token, id, name, pincode,zone_id)
    }
    fun editProfile(token: String, id: Int, name: String, phone: String,email: String,pincode: String): MutableLiveData<LoginUser> {
        return Retrofithit().editProfile(token, id, name, phone,email,pincode)
    }
    fun farmerProfile(token: String, id: Int): MutableLiveData<FarmerProfile> {
        return Retrofithit().farmerProfile(token, id)
    }

    fun signin(mobile_signin: String, pass_signin: String): MutableLiveData<LoginUser> {
        return Retrofithit().signin(mobile_signin, pass_signin)
    }

    fun createFeed(
        token: String,heading:String, des:String,file: File,requestFile: RequestBody
    ): MutableLiveData<ExpensesList> {
        return Retrofithit().createFeed(
            token,heading,des,file,requestFile
        )
    }
    fun likedislikePost(token: String, post_comment: Int): MutableLiveData<LoginUser> {
        return Retrofithit().likedislikePost(token, post_comment)
    }
    fun likedislikeCommentPost(token: String, post_comment: Int): MutableLiveData<LoginUser> {
        return Retrofithit().likedislikeCommentPost(token, post_comment)
    }
    fun nestedcomment(token: String,nestedcommetn:Int, post_comment: String): MutableLiveData<PostComment> {
        return Retrofithit().nestedcomment(token,nestedcommetn, post_comment)
    }
    fun loadFeed(token: String): MutableLiveData<FeedData> {
        return Retrofithit().loadFeed(token)
    }
    fun getItemList(token: String): MutableLiveData<ItemList> {
        return Retrofithit().getItemList(token)
    }
    fun getItemDetail(token: String,id: String): MutableLiveData<ItemDetail> {
        return Retrofithit().getItemDetail(token,id)
    }
    fun getCatList(token: String): MutableLiveData<CatList> {
        return Retrofithit().getCatList(token)
    }
    fun getItemListbyid(token: String,id:Int): MutableLiveData<ItemList> {
        return Retrofithit().getItemListbyid(token,id)
    }
    fun getComment(token: String,id:Int): MutableLiveData<Comment> {
        return Retrofithit().getComment(token,id)
    }
    fun Consulting(token:String,message: String,title: String,file: File,requestFile: RequestBody,querytype:String): MutableLiveData<Comment> {
        return Retrofithit().Consulting(token,message,title,file,requestFile,querytype)
    }
    fun Consulting2(token:String,message: String,title: String,querytype:String): MutableLiveData<Comment> {
        return Retrofithit().Consulting2(token,message,title,querytype)
    }
    fun help(token:String,message: String): MutableLiveData<Comment> {
        return Retrofithit().help(token,message)
    }

    fun payBuynow(token:String,mid: Int,id: Int,qnt: Int,price: String,shipping_address:Int): MutableLiveData<CartBuy> {
        return Retrofithit().payBuynow(token,mid,id,qnt,price,shipping_address)
    }
    fun cartBuy(token:String,mid: Int,cart: List<RoomCart>,shipping_address:Int): MutableLiveData<CartBuy> {
        return Retrofithit().cartBuy(token,mid,cart,shipping_address)
    }
    fun addAddress(token:String, AddressName:String, Building:String, Street:String,Landmark:String,City:String,Pincode:String): MutableLiveData<Comment> {
        return Retrofithit().addAddress(token, AddressName, Building, Street,Landmark,City,Pincode)
    }
    fun getBanner(token: String): MutableLiveData<Banner> {
        return Retrofithit().getBanner(token)
    }
    fun popularProducts(token: String): MutableLiveData<PopularProducts> {
        return Retrofithit().popularProducts(token)
    }
    fun addDailyInputWithWeight(
        token: String,
        id: Int,
        date: String,
        mortality: Int,
        feed: Int,
        culling: Int,
        remark: String,medlistidpost:ArrayList<Int>,medlistqnt:ArrayList<Int>,
        Weight:Int
    ): MutableLiveData<AddDailyInput> {
        return Retrofithit().addDailyInputWithWeight(
            token,
            id,
            date,
            mortality,
            feed,
            culling,
            remark,medlistidpost,medlistqnt,Weight
        )
    }

    fun neccZone(
        token: String): MutableLiveData<ZoneList> {
        return Retrofithit().neccZone(token)
    }

    fun neccRate(token: String, id: Int): MutableLiveData<NeccRate> {
        return Retrofithit().neccRate(token, id)
    }

    fun wordpressFeed(token: String,language:String): MutableLiveData<WordpressFeed> {
        return Retrofithit().wordpressFeed(token,language)
    }
    fun getTemp(lat: Double, log: Double,token:String): MutableLiveData<WeatherData> {
        return Retrofithit().getTemp(lat,log,token)
    }

    fun getTemppincode(pincode: Int,token:String): MutableLiveData<WeatherDataByCode> {
        return Retrofithit().getTemppincode(pincode,token)
    }
    fun getVideo(token: String): MutableLiveData<VideoList> {
        return Retrofithit().getvideo(token)
    }

    fun profileImage(
        token: String,file: File,requestFile: RequestBody
    ): MutableLiveData<ExpensesList> {
        return Retrofithit().profileImage(
            token,file,requestFile
        )
    }
    fun neccRate(token: String): MutableLiveData<NeccRate> {
        return Retrofithit().neccRateall(token)
    }
    fun getIot( id: String): MutableLiveData<IotData> {
        return Retrofithit().getIot( id)
    }
    fun iotRequest(token: String): MutableLiveData<LoginUser> {
        return Retrofithit().requestiot(token)
    }
    fun getDailyInput(token: String,id:Int): MutableLiveData<DailInput> {
        return Retrofithit().getDailyInput(token,id)
    }
    fun addDailyInputWithWeightUpdate(
        token: String,
        date: String,
        mortality: Int,
        feed: Double,
        culling: Int,
        remark: String,
        Weight:Double,
        ids:Int,
        totalEgg:Int,
        brokenegg:String,
        brokeneggInoperation:String
    ): MutableLiveData<AddDailyInput> {
        return Retrofithit().addDailyInputWithWeightUpdate(
            token,
            date,
            mortality,
            feed,
            culling,
            remark,Weight,ids,
            totalEgg,
            brokenegg,
            brokeneggInoperation
        )
    }

}