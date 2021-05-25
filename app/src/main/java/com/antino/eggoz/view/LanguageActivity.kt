package com.antino.eggoz.view

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.ActivityLanguageBinding
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.PrefrenceUtils
import java.util.*

class LanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.radiogroupLanguage.setOnCheckedChangeListener { radioGroup: RadioGroup, i: Int ->
            if (i == R.id.radioBtn_english) {
                PrefrenceUtils.insertData(
                    this@LanguageActivity,
                    Constants.LANG,
                    "en"
                )
                Toast.makeText(this,"english",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,Signup1::class.java))

                setLocale("en")


            } else if (i == R.id.radioBtn_hindi) {
                PrefrenceUtils.insertData(
                    this@LanguageActivity,
                    Constants.LANG,
                    "hi"
                )
                setLocale("hi")
                Toast.makeText(this,"हिन्दी",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,Signup1::class.java))


            }

        }

    }
    private fun setLocale(lang:String){
        val locale=Locale(lang)
        Locale.setDefault(locale)
        val config=Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config,baseContext.resources.displayMetrics)
    }
}