package com.antino.eggoz.ui.profile

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentProfileBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.profile.Model.Farmdata
import com.antino.eggoz.ui.profile.adapter.FarmAdapter
import com.antino.eggoz.ui.profile.callback.ProfileCallback
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.FileUtils
import com.antino.eggoz.utils.PrefrenceUtils
import com.antino.eggoz.view.CustomAlertLoading
import com.antino.eggoz.view.Signup1
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File


class ProfileFragment(
    val context: MainActivity,
    private var farmdata: ArrayList<Farmdata?>,
    private var token: String,
    private var mid: Int
) : Fragment(), ProfileCallback {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: FragmentProfileBinding


    private val PICKFILE_REQUEST_CODE = 23
    private lateinit var mediaImageFile: File
    var mediaImageFilecom:File ?=null
    lateinit var loadingdialog:CustomAlertLoading

    private lateinit var uri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding = FragmentProfileBinding.inflate(inflater, container, false)
        init()
        loadProfile()

//        farmData()

        binding.imgPerson.setOnClickListener {
            getImg()
        }
        return binding.root
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



    private fun init() {

        loadingdialog= CustomAlertLoading(this)

        binding.btnAddFarm.setOnClickListener { context.addFarm() }
        binding.txtAddMore.setOnClickListener { context.addFarm() }
        binding.imgEdit.setOnClickListener {
            context.loadEditProfile()
        }

        val color: Drawable = ColorDrawable(
            ContextCompat.getColor(
                activity?.applicationContext!!,
                R.color.shimmerbackground
            )
        )
        val image = ContextCompat.getDrawable(
            activity?.applicationContext!!,
            R.drawable.ic_baseline_person_24
        )

        val ld = LayerDrawable(arrayOf(color, image))
        binding.imgPerson.setImageDrawable(ld)

    }

    private fun loadProfile(){


        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.farmerProfile(
            token, mid
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                if (it?.errors==null){


                    binding.txtName.text=it.farmer.name
                    binding.txtMobileNo.text=it.farmer.phoneNo
                    binding.txtEmailId.text=it.farmer.email

                    Glide.with(this)
                        .asBitmap()
                        .load(it.farmer.image)
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.logo1)
                        .into(binding.imgPerson)


                }else{
                    if (it.errors!![0].message=="Invalid signature.") {
                        startActivity(Intent(activity, Signup1::class.java))
                        PrefrenceUtils.insertData(
                                context,
                                Constants.TEMPID,
                                ""
                        )
                        PrefrenceUtils.insertData(
                                context,
                                Constants.ACCESS_TOKEN_PREFERENCE,
                                ""
                        )
                        PrefrenceUtils.insertData(
                                context,
                                Constants.ID,
                                ""
                        )
                    }
                    Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                    Log.d("data","${it.errors!![0].message}")
                }
            }
        )


    }
    private fun farmData(){
        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getFarm(
            token, mid
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                if (it.count == null) {
                    binding.btnAddFarm.visibility = View.VISIBLE
                    binding.txtFarmDetails.visibility = View.GONE
                    binding.txtAddMore.visibility = View.GONE


                    if (it.errors?.get(0)?.message != null || it.errors?.get(0)?.message != "") {

                        Log.d("data", "get farm error ${it.errors!![0].message}")
                    } else
                        Log.d("data", "farm data ${it.results?.size} count ${it.count}")

                } else {

                    Log.d("data", "count:- ${it.count}")

                    if (it?.count!! > 0) {


                        binding.btnAddFarm.visibility = View.GONE
                        binding.txtFarmDetails.visibility = View.VISIBLE
                        binding.txtAddMore.visibility = View.VISIBLE



                        if (it.results?.size!!>0) {
                            binding.txtFarmDetails.visibility = View.VISIBLE
                            binding.txtAddMore.visibility = View.VISIBLE

                            binding.recycleviewFarmDetail.layoutManager = LinearLayoutManager(
                                activity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            binding.recycleviewFarmDetail.setHasFixedSize(true)
                            val adapter = FarmAdapter(context, it.results)
                            binding.recycleviewFarmDetail.adapter = adapter
                        } else {
                            binding.txtFarmDetails.visibility = View.GONE
                            binding.txtAddMore.visibility = View.GONE
                        }

                    }
                    else{

                        binding.txtFarmDetails.visibility = View.GONE
                        binding.txtAddMore.visibility = View.GONE
                    }
                }

            }
        )



    }


    private fun com(){
        lifecycleScope.launch {
            mediaImageFilecom=
                Compressor.compress(context, mediaImageFile) {
                    default(width = Constants.ImageSize, height = Constants.ImageSize, format = Bitmap.CompressFormat.WEBP)
                }
            val bitmap= BitmapFactory.decodeFile(mediaImageFilecom?.path)
            binding.imgPerson.setImageBitmap(bitmap)
            submit()
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
        viewModel.profileImage(
            token,
            mediaImageFile, requestFile
        ).observe(this,
            Observer {
                loadingdialog.dismiss()
                if (it.errorType == null || it.errorType == "") {
                    Toast.makeText(context, "Image saved", Toast.LENGTH_SHORT).show()
                }
                Log.d("data", "$it")
            }
        )



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


}