package com.antino.eggoz.view


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.antino.eggoz.R
import com.antino.eggoz.adapter.SliderAdapter
import com.antino.eggoz.databinding.ActivityStarterScreenBinding


class StarterScreen : AppCompatActivity() {

    private lateinit var binding: ActivityStarterScreenBinding
    private var dotscount = 0
    private var dots: Array<ImageView?>? = null
    private lateinit var viewPagerAdapter: SliderAdapter
    private var location = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStarterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        //adapter for images
        viewPagerAdapter = SliderAdapter(this)
        binding.staterViewPager.adapter = viewPagerAdapter


//        view dots linked with viewpager
        sliderviewpager()


        //skip btn
        binding.txtStarterSkipstep.setOnClickListener {
            startActivity(Intent(this, LanguageActivity::class.java))
            finish()
        }



        binding.btnNext.setOnClickListener {
            if (location == 2) startActivity(Intent(this, LanguageActivity::class.java))
            else {
                binding.staterViewPager.setCurrentItem(location + 1, true)
                location++
            }

            if(location==0){
                binding.txtStarterSkipstep.visibility= View.VISIBLE
                binding.txtStarterTitle.text ="Sell Fresh Eggs"
                binding.txtStarterTitleDes.text ="Providing farm fresh and high quality eggs in well monitored clean farms to ensure your better health"
            }else  if(location==1){
                binding.txtStarterSkipstep.visibility= View.VISIBLE
                binding.txtStarterTitle.text ="Procure Feed"
                binding.txtStarterTitleDes.text ="Quisque sit amet sagittis erat. Duis pharetra ornare venenatis. Nulla maximus porta velit ut molestie. Proin quis convallis mauris. In facilisis justo at mi pharetra lobortis."
            }else if(location==2){
                binding.txtStarterSkipstep.visibility= View.GONE
                binding.btnNext.text ="Done"
                binding.txtStarterTitle.text ="Farm Managment"
                binding.txtStarterTitleDes.text ="Quisque sit amet sagittis erat. Duis pharetra ornare venenatis. Nulla maximus porta velit ut molestie. Proin quis convallis mauris. In facilisis justo at mi pharetra lobortis. s."
            }
        }


    }

    private fun sliderviewpager() {
        dotscount = viewPagerAdapter.count
        dots = arrayOfNulls(dotscount)
        for (i in 0 until dotscount) {
            dots!![i] = ImageView(this)
            dots!![i]?.setImageDrawable(
                ContextCompat.getDrawable(
                    applicationContext,
                    R.drawable.non_active_dot
                )
            )
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            binding.SliderDots.addView(dots!![i], params)
        }
        dots!![0]!!.setImageDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.active_dot
            )
        )

        binding.staterViewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                location = position
                if(location==0){
                    binding.txtStarterSkipstep.visibility= View.VISIBLE
                    binding.txtStarterTitle.text ="Sell Fresh Eggs"
                    binding.txtStarterTitleDes.text ="Providing farm fresh and high quality eggs in well monitored clean farms to ensure your better health"
                }else  if(location==1){
                    binding.txtStarterSkipstep.visibility= View.VISIBLE
                    binding.txtStarterTitle.text ="Procure Feed"
                    binding.txtStarterTitleDes.text ="Quisque sit amet sagittis erat. Duis pharetra ornare venenatis. Nulla maximus porta velit ut molestie. Proin quis convallis mauris. In facilisis justo at mi pharetra lobortis."
                }else if(location==2){
                    binding.txtStarterSkipstep.visibility= View.GONE
                    binding.btnNext.text ="Done"
                    binding.txtStarterTitle.text ="Farm Managment"
                    binding.txtStarterTitleDes.text ="Quisque sit amet sagittis erat. Duis pharetra ornare venenatis. Nulla maximus porta velit ut molestie. Proin quis convallis mauris. In facilisis justo at mi pharetra lobortis. s."
                }
            }

            override fun onPageSelected(position: Int) {
                Log.d("data", "onPageSelected $position")
                for (i in 0 until dotscount) {
                    dots!![i]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.non_active_dot
                        )
                    )
                }
                dots!![position]?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.active_dot
                    )
                )
            }

            override fun onPageScrollStateChanged(state: Int) {
                Log.d("data", "onPageScrollStateChanged $state")

            }
        })
//        view dots linked with viewpager ends


    }
}