package com.antino.eggoz.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.PrefrenceUtils
import java.util.*

class Splash : AppCompatActivity() {
    private lateinit var token: String
    private lateinit var id: String
    private lateinit var temp_id: String
    private lateinit var lang: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //for full screen
        @Suppress("DEPRECATION")
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {

            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        Handler(Looper.getMainLooper()).postDelayed({
            login()

        }, 3000)
    }

    fun login() {
        token = PrefrenceUtils.retriveData(this@Splash, Constants.ACCESS_TOKEN_PREFERENCE)!!
        id = PrefrenceUtils.retriveData(this@Splash, Constants.ID)!!
        temp_id= PrefrenceUtils.retriveData(this@Splash, Constants.TEMPID)!!
        lang= PrefrenceUtils.retriveData(this@Splash, Constants.LANG)!!

        Log.d("data","languge $lang")

        if (lang=="")
            setLocale("en")
        else
            setLocale(lang)


        Log.d("data", "token ${token} \n id $id \n tempid $temp_id ")


        if (token != "" && id != "" && temp_id!=""){
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("token", token)
            intent.putExtra("id", id)
            startActivity(intent)
            finish()

        }else if (token != "" && id == "" && temp_id !=""){

            intent = Intent(this, Signup5deatails::class.java)
            intent.putExtra("token", token)
            intent.putExtra("id",temp_id)
            startActivity(intent)
            finish()
        }else{
            invalidateOptionsMenu()
            intent = Intent(this, StarterScreen::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setLocale(lang:String){
        val locale= Locale(lang)
        Locale.setDefault(locale)
        val config= Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
    }
}