package com.antino.eggoz.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.ActivityOtpverificationBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.CustomAlertLoadingActivity
import com.antino.eggoz.utils.PrefrenceUtils

class Signup2Otpverification : AppCompatActivity() {
    private lateinit var binding: ActivityOtpverificationBinding
    private lateinit var mobile:String
    private lateinit var otp:String
    private var farmer=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification)

        binding = ActivityOtpverificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mobile= intent.getStringExtra("mobile_no").toString()
        otp=intent.getStringExtra("otp").toString()

        binding.edtOtp.setText(otp)

        binding.btnSubmit.setOnClickListener {
            validate()
//            startActivity(Intent(this,Signup5deatails::class.java))
        }
        binding.imgBack.setOnClickListener {
            startActivity(Intent(this, Signup1::class.java))
            finish()
        }
    }
    private fun validate(){
        when {
            binding.edtOtp.text?.length==0 -> binding.edtOtpLayout.error="Please enter valid otp"
            binding.edtOtp.text?.length!! in 1..5 -> binding.edtOtpLayout.error="password length must be 6"
            else -> {
                binding.edtOtpLayout.isErrorEnabled = false
                confirm()
            }
        }
    }

    private fun confirm() {

        val loadingdialog = CustomAlertLoadingActivity(this)
        loadingdialog.stateLoading()
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.signup2otp(
            mobile,
            binding.edtOtp.text.toString()
        ).observe(this,
            Observer {
                loadingdialog.dismiss()
                if (it.success != null) {
                    /*  val sharedPreferences = getSharedPreferences("eggoz", MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("token", "Bearer ${it.token}")
                    editor.putString("temp_id", "${it.user.id}")*/

                    PrefrenceUtils.insertData(
                        this@Signup2Otpverification,
                        Constants.TEMPID,
                        "${it.user.id}"
                    )
                    PrefrenceUtils.insertData(
                        this@Signup2Otpverification,
                        Constants.ACCESS_TOKEN_PREFERENCE,
                        "Bearer ${it.token}"
                    )
                    val intent: Intent
                    if (it.user.userProfile.departments.isEmpty()) {
                        Log.d("data", "size 0")
                        intent = Intent(this, Signup5deatails::class.java)
                        intent.putExtra("id", "${it.user.id}")
                        intent.putExtra("token", "Bearer ${it.token}")
                    } else {
                        for (element in it.user.userProfile.departments) {
                            if (element == "Farmer") {
                                farmer = true
//                                editor.putBoolean("profile", true)
                            }
                        }
                        if (farmer) {
                            Log.d("data", "farmer id${it.user.userProfile.department_profiles[0].farmerProfile}")

                            PrefrenceUtils.insertData(
                                this@Signup2Otpverification,
                                Constants.ID,
                                "${it.user.userProfile.department_profiles[0].farmerProfile}"
                            )
                            intent = Intent(this, MainActivity::class.java)
                            intent.putExtra(
                                "id",
                                "${it.user.userProfile.department_profiles[0].farmerProfile}"
                            )
                            intent.putExtra("token", "Bearer ${it.token}")
                        } else {
                            intent = Intent(this, Signup5deatails::class.java)
                            intent.putExtra("id", "${it.user.id}")
                            intent.putExtra("token", "Bearer ${it.token}")

                        }
                    }
//                    intent.putExtra("pos",pos)
                    startActivity(intent)
                    finish()
                } else {

                    Toast.makeText(this, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                }


            })

    }

    override fun onBackPressed() {
        return
    }
}