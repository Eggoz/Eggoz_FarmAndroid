package com.antino.eggoz.ui.feed

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentCreateFeedBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.FileUtils
import com.antino.eggoz.utils.PrefrenceUtils
import com.antino.eggoz.view.CustomAlertLoading
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.zelory.compressor.Compressor.compress
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File


class CreateFeedFragment(val context: MainActivity, val token: String) : Fragment() {
    private lateinit var binding: FragmentCreateFeedBinding
    private lateinit var uri: Uri
    private var mediaImageFile: File?=null
    private val PICKFILE_REQUEST_CODE = 23
    var mediaImageFilecom:File ?=null
    lateinit var loadingdialog:CustomAlertLoading

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreateFeedBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        if (!hasStoragePermission(context))
        {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE),
                200
            )
        }

        binding.layoutPhoto
//        binding.feedImg.visibility=View.GONE
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadFeed()
                true
            } else false
        }

        loadingdialog= CustomAlertLoading(this)

        binding.layoutPhoto.setOnClickListener { getImg() }

        loadProfile()
        return binding.root
    }


    private fun loadProfile(){


        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.farmerProfile(
            token, PrefrenceUtils.retriveData(context, Constants.ID)!!.toInt()
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                if (it?.id!=null){

                    Glide.with(this)
                        .asBitmap()
                        .load(it.farmer.image)
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.logo1)
                        .into(binding.imgPic)


                }else{
                    Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                    Log.d("data","${it.errors!![0].message}")
                }
            }
        )


    }
    fun hasStoragePermission(context: Context): Boolean {
        val write =
            ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val read =
            ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        return write == PackageManager.PERMISSION_GRANTED && read == PackageManager.PERMISSION_GRANTED
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICKFILE_REQUEST_CODE && data != null && data.data != null) {
            try {
                mediaImageFile = FileUtils.from(context, data.data!!)!!
                    uri = data.data!!
                com()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_search).isVisible = false
        menu.findItem(R.id.action_notification).isVisible =false
        inflater.inflate(R.menu.othermenu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    private fun validate(){
        if (binding.edtFeedinput.text!!.isEmpty() || mediaImageFile==null || binding.edtHeading.text!!.isEmpty()){
            if (binding.edtFeedinput.text!!.isEmpty())
                binding.edtFeedinputLayout.error="Enter message"
            else binding.edtFeedinputLayout.isErrorEnabled=false
            if (binding.edtHeading.text!!.isEmpty())
                binding.edtHeadingLayout.error="Enter Heading"
            else
                binding.edtHeadingLayout.isErrorEnabled=false
            if (mediaImageFile==null){
                Toast.makeText(context,"Select Image Frist",Toast.LENGTH_SHORT).show()
            }


        }else{
            binding.edtFeedinputLayout.isErrorEnabled=false
            binding.edtHeadingLayout.isErrorEnabled=false
            if (hasStoragePermission(context)) {
                submit()
            } else {
                ActivityCompat.requestPermissions(
                    (context as Activity?)!!,
                    arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    200
                )
            }
        }
    }
    private fun com(){
        lifecycleScope.launch {
            mediaImageFilecom=
                compress(context, mediaImageFile!!) {
                    default(width = Constants.ImageSize ,height = Constants.ImageSize , format = Bitmap.CompressFormat.WEBP)
                }
            val bitmap=BitmapFactory.decodeFile(mediaImageFilecom?.path)
            binding.feedImg.setImageBitmap(bitmap)
        }
    }
    private fun submit(){
        loadingdialog.stateLoading()

        if (mediaImageFilecom!=null)
            apicreate()
        else
            com()
    }

    fun apicreate(){

        val requestFile: RequestBody = RequestBody.create(
            MediaType.parse(activity?.contentResolver?.getType(uri)),
            mediaImageFilecom!!
        )
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.createFeed(
            token,
            binding.edtHeading.text.toString(),
            binding.edtFeedinput.text.toString(),
            mediaImageFile!!, requestFile
        ).observe(this,
            Observer {
                loadingdialog.dismiss()
                if (it.errorType == null || it.errorType == "") {
                    context.loadFeed()
                }else
                    Toast.makeText(context,"${it.errors!![0].message}",Toast.LENGTH_SHORT).show()
                Log.d("data", "$it")
            }
        )



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                validate()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}