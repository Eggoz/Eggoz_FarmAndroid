package com.antino.eggoz.ui.consulting

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentConsultingBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.utils.FileUtils
import com.antino.eggoz.view.CustomAlertLoading
import com.google.android.material.snackbar.Snackbar
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File


class ConsultingFragment(private var token:String,private var mcontext:MainActivity) : Fragment() {
    private lateinit var binding: FragmentConsultingBinding

    private val PICKFILE_REQUEST_CODE = 23
    private lateinit var uri: Uri
    private var mediaImageFile: File?=null
    var mediaImageFilecom:File ?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentConsultingBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener { validate() }
        if (!hasStoragePermission(activity?.applicationContext!!))
        {
            ActivityCompat.requestPermissions(
                    (context as Activity?)!!,
                    arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    200
            )
        }
        init()

        return binding.root
    }
    private fun init(){
        binding.imgAtta.setOnClickListener {
            getImg()
        }
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                    mcontext.loadHome()
                true
            } else false
        }
    }
    private fun getImg(){
        try {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(
                    intent,
                    PICKFILE_REQUEST_CODE
            )
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

    }
    private fun com(){
        lifecycleScope.launch {
            mediaImageFilecom=
                    Compressor.compress(activity?.applicationContext!!, mediaImageFile!!) {
                        default(width = 640, height = 640, format = Bitmap.CompressFormat.WEBP)
                    }
            val bitmap= BitmapFactory.decodeFile(mediaImageFilecom?.path)
            binding.imgload.setImageBitmap(bitmap)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICKFILE_REQUEST_CODE && data != null && data.data != null) {
            try {
                mediaImageFile = FileUtils.from(activity?.applicationContext!!, data.data!!)!!
                uri = data.data!!
                com()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    private fun validate(){
        if (binding.edtIssue.text!!.isEmpty()||binding.edtMessageCon.text!!.isEmpty()||mediaImageFile==null){
            if (binding.edtIssue.text!!.isEmpty()) binding.edtIssueLayout.error="Enter Issue"
            else binding.edtIssueLayout.isErrorEnabled=false
            if (binding.edtMessageCon.text!!.isEmpty())
                Toast.makeText(context,"Enter some message",Toast.LENGTH_SHORT).show()
            if (mediaImageFile==null){
                Toast.makeText(context,"Select File",Toast.LENGTH_SHORT).show()
            }
        }
        else{
            binding.edtNameLayout.isErrorEnabled=false
            binding.edtIssueLayout.isErrorEnabled=false
            submit()
        }
    }
    fun hasStoragePermission(context: Context): Boolean {
        val write =
                ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val read =
                ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
    }
    private fun submit(){
        val requestFile: RequestBody = RequestBody.create(
                MediaType.parse(activity?.contentResolver?.getType(uri)),
                mediaImageFilecom!!
        )
        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.Consulting(
            token,binding.edtMessageCon.text.toString(),binding.edtIssue.text.toString(), mediaImageFile!!, requestFile,"Consulting"
        ).observe(requireActivity(), androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.errors == null) {
                val snackBar = Snackbar.make(
                    binding.root, "Our Team will Contact You Soon",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).setActionTextColor(Color.WHITE)
                val snackBarView = snackBar.view
                snackBarView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.green))
                val textView =
                    snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.WHITE)
                snackBar.show()
                mcontext.loadHome()
            } else {
                Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
            }


        }
        )

    }


}