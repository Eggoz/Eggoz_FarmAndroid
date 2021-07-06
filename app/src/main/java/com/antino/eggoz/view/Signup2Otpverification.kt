package com.antino.eggoz.view

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.ActivityOtpverificationBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.CustomAlertLoadingActivity
import com.antino.eggoz.utils.PrefrenceUtils
import com.antino.eggoz.utils.SmsReceiver
import com.google.android.gms.auth.api.phone.SmsRetriever

class Signup2Otpverification : AppCompatActivity(), SmsReceiver.OTPReceiveListener {
    private lateinit var binding: ActivityOtpverificationBinding
    private lateinit var mobile: String
    private lateinit var otp: String
    private var farmer = false
    lateinit var smsReceiver: SmsReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification)

        binding = ActivityOtpverificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mobile = intent.getStringExtra("mobile_no").toString()
        binding.edtOtp.setText(intent.getStringExtra("otp").toString())
        if (mobile == "")
            startActivity(Intent(this, Signup1::class.java))
        binding.term.setOnClickListener {
            val uri: Uri =
                Uri.parse(Constants.privacypolicyUrl)

            val intent = Intent(Intent.ACTION_VIEW, uri)

            startActivity(intent)
        }

        binding.btnSubmit.setOnClickListener {
            validate()
//            startActivity(Intent(this,Signup5deatails::class.java))
        }
        binding.imgBack.setOnClickListener {
            startActivity(Intent(this, Signup1::class.java))
            finish()
        }

        startSMSListener()
    }

    private fun validate() {
        when {
            binding.edtOtp.text?.length == 0 -> binding.edtOtpLayout.error =
                "Please enter valid otp"
            binding.edtOtp.text?.length!! in 1..5 -> binding.edtOtpLayout.error =
                "password length must be 6"
            else -> {
                binding.edtOtpLayout.isErrorEnabled = false
                confirm(binding.edtOtp.text.toString())
            }
        }
    }

    private fun confirm(otp: String) {

        val loadingdialog = CustomAlertLoadingActivity(this)
        loadingdialog.stateLoading()
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.signup2otp(
            mobile,
            otp
        ).observe(this,
            Observer {
                if (loadingdialog.isRuning())
                    loadingdialog.dismiss()
                if (it.success != null) {
                    if (smsReceiver != null) {
                        LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver)
                    }
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
                            Log.d(
                                "data",
                                "farmer id${it.user.userProfile.department_profiles[0].farmerProfile}"
                            )

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

                    if (loadingdialog.isRuning())
                        loadingdialog.dismiss()
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

    override fun onDestroy() {
        super.onDestroy()
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver)
        }
    }

    private fun startSMSListener() {
        try {
            smsReceiver = SmsReceiver()
            smsReceiver.setOTPListener(this)
            val intentFilter = IntentFilter()
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
            this.registerReceiver(smsReceiver, intentFilter)
            val client = SmsRetriever.getClient(this)
            val task = client.startSmsRetriever()
            task.addOnSuccessListener {
//                 Toast.makeText(this, "Successfully", Toast.LENGTH_LONG).show()
            }
            task.addOnFailureListener {
                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onOTPReceived(otp: String) {
        runOnUiThread {
            if (otp != null) {
                Log.d("data", otp)
                val otttp = otp.split(" ")
                val otpString = otttp[6].substring(0, 6)
                Log.d("data", "otpstring $otpString")
                binding.edtOtp.setText(otpString)
                confirm(otpString)
            }
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver)
    }

    override fun onOTPTimeOut() {
//        Toast.makeText(this, "TimeOut", Toast.LENGTH_LONG).show()
    }

    override fun onOTPReceivedError(error: String) {
        Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
    }
}