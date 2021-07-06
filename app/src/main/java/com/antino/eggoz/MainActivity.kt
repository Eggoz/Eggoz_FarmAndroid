package com.antino.eggoz

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.pm.PackageInfoCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.network.NetworkConnect
import com.antino.eggoz.ui.Buy.BuyFragment
import com.antino.eggoz.ui.Buy.callback.BuyCallback
import com.antino.eggoz.ui.Summary.SummaryFragment
import com.antino.eggoz.ui.activity_log.ActivityLog
import com.antino.eggoz.ui.address.AddAddressFragment
import com.antino.eggoz.ui.consulting.ConsultingFragment
import com.antino.eggoz.ui.daily_input.*
import com.antino.eggoz.ui.daily_input.model.DailInput
import com.antino.eggoz.ui.edit.EditFarmFragment
import com.antino.eggoz.ui.edit.EditFlockFragment
import com.antino.eggoz.ui.edit.EditProfileFragment
import com.antino.eggoz.ui.edit.EditShedFragment
import com.antino.eggoz.ui.expenses.Expenses
import com.antino.eggoz.ui.faqs.FaqsFragment
import com.antino.eggoz.ui.feed.CommentFragment
import com.antino.eggoz.ui.feed.CreateFeedFragment
import com.antino.eggoz.ui.feed.FeedFragment
import com.antino.eggoz.ui.feed.callback.FeedCallback
import com.antino.eggoz.ui.helpsupport.HelpSupportFragment
import com.antino.eggoz.ui.home.ExoplayerFragment
import com.antino.eggoz.ui.home.HomeCallback
import com.antino.eggoz.ui.home.HomeFragment
import com.antino.eggoz.ui.home.IotListFragment
import com.antino.eggoz.ui.item.ItemDetailCallback
import com.antino.eggoz.ui.item.ItemDetailFragment
import com.antino.eggoz.ui.ledgers.Leadgers
import com.antino.eggoz.ui.notification.NotificationFragment
import com.antino.eggoz.ui.order.OrderSummaryFragment
import com.antino.eggoz.ui.order.TrackOrderFragment
import com.antino.eggoz.ui.profile.*
import com.antino.eggoz.ui.profile.Model.Farmdata
import com.antino.eggoz.ui.profile.Model.Grower
import com.antino.eggoz.ui.profile.Model.Shades
import com.antino.eggoz.ui.profile.callback.ProfileCallback
import com.antino.eggoz.ui.schedule.ScheduleDetailFragment
import com.antino.eggoz.ui.schedule.ScheduleFragment
import com.antino.eggoz.ui.sell_shop.ExploreProductsFragment
import com.antino.eggoz.ui.sell_shop.SellShopFragment
import com.antino.eggoz.ui.sell_shop.callback.SellShopCallback
import com.antino.eggoz.ui.termscondition.TermsAndConditionFragment
import com.antino.eggoz.ui.web.WebFragment
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.CustomAlertLoadingActivity
import com.antino.eggoz.utils.PrefrenceUtils
import com.antino.eggoz.view.Signup1
import com.antino.eggoz.view.otherfragment.CartCallback
import com.antino.eggoz.view.otherfragment.CartFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.location.LocationRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), SellShopCallback, ProfileCallback, DailyInputCallback,
    HomeCallback, FeedCallback, LoadMainActivity, CartCallback,
    BuyCallback, ItemDetailCallback {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomnav: BottomNavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var mDrawerToggle: ActionBarDrawerToggle
    private var mToolBarNavigationListenerIsRegistered = false
    private lateinit var navView: NavigationView
    private lateinit var navViewfooter: TextView
    private lateinit var farm: ArrayList<Farmdata?>
    private lateinit var farmdata: Farmdata
    private lateinit var shades: ArrayList<Shades>
    private lateinit var grower: ArrayList<Grower>
    private lateinit var farmName: String
    private lateinit var farmAddress: String
    private lateinit var token: String
    private lateinit var ids: String
    private var id: Int = 0

    private lateinit var img: ImageView
    private lateinit var txt_person_name: TextView
    private lateinit var txt_person_mobile: TextView
    private lateinit var navController: NavController

    private var internet = true
    private var internet1 = true
    private lateinit var snackBar: Snackbar


    private lateinit var locationRequest: LocationRequest
    private var requestchecksetting = 1001
    private val MY_REQUEST_CODE = 3001

    // Creates instance of the manager.
//    private var appUpdateManager:com.google.android.play.core.appupdate.AppUpdateManager ?=null
//    private lateinit var appUpdateManager: AppUpdateManager
//    private lateinit var installStateUpdatedListener: InstallStateUpdatedListener

    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inAppUpdate()


//        throw RuntimeException("Test Crash") // Force a crash

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        window.statusBarColor = ContextCompat.getColor(this, R.color.app_color)


        farm = ArrayList()
        shades = ArrayList()
        grower = ArrayList()

/*
        token = intent.getStringExtra("token").toString()
        ids = intent.getStringExtra("id").toString()*/

        token = PrefrenceUtils.retriveData(this@MainActivity, Constants.ACCESS_TOKEN_PREFERENCE)!!
        ids = PrefrenceUtils.retriveData(this@MainActivity, Constants.ID)!!


        id = Integer.parseInt(ids)

        Log.d("data", "id $id")


        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        navViewfooter = findViewById(R.id.footer_item_1)


        val manager: PackageManager = this.packageManager
        val info: PackageInfo =
            manager.getPackageInfo(this.packageName, PackageManager.GET_ACTIVITIES)

        navViewfooter.text = "Version: ${info.versionName}"
        bottomnav = findViewById(R.id.btm_nav_)

        networkObserver()

        navController = findNavController(R.id.nav_host_fragment)


        val headerLayout = navView.inflateHeaderView(R.layout.nav_header_main)

        img = headerLayout.findViewById(R.id.imageView_pic)
        txt_person_name = headerLayout.findViewById(R.id.txt_name)
        txt_person_mobile = headerLayout.findViewById(R.id.txt_person_mobile)

        loadProfileData()

        headerLayout.setOnClickListener {
            loadFragment(ProfileFragment(this, farm, token, this.id))
            navView.setCheckedItem(R.id.menu_none)
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_profile)
            bottomnav.visibility = View.GONE
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        mDrawerToggle =
            ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
            )
        mDrawerToggle.syncState()


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_schedule,
                R.id.nav_sell_shop,
                R.id.nav_daily_input,
                R.id.nav_feed
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


//        mobileNavigation()

        bottomnav()
        sidenave()

        supportActionBar?.customView?.setOnClickListener {
            if (toolbar.title == "Explore Products") {
                loadFragment(SellShopFragment(this, token))
                navView.setCheckedItem(R.id.menu_none)
                toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_bazaar)
                bottomnav.visibility = View.VISIBLE
            }
        }



        loadFragment(HomeFragment(this))
        navView.menu.getItem(0).isChecked = true
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_home)
        bottomnav.visibility = View.VISIBLE
        bottomnav.menu.findItem(R.id.nav_home).isChecked = true
        mDrawerToggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        bottomnav.visibility = View.VISIBLE
        navView.setCheckedItem(R.id.nav_side_home)
        mDrawerToggle.syncState()


        Log.d("data", "token $token")
    }

    private fun networkObserver() {
        snackBar = Snackbar.make(
            window.decorView.findViewById(android.R.id.content), "Eggoz",
            Snackbar.LENGTH_LONG
        )
        val networkConnections = NetworkConnect(applicationContext)
        networkConnections.observe(this, Observer {

            if (it) {
                if (!internet) {
                    if (snackBar.isShown)
                        snackBar.dismiss()
                    snackBar = Snackbar.make(
                        window.decorView.findViewById(android.R.id.content), "Welcome Back",
                        Snackbar.LENGTH_LONG
                    ).setAction("Action", null).setActionTextColor(Color.WHITE)
                    val snackBarView = snackBar.view
                    snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                    val textView =
                        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                    textView.setTextColor(Color.WHITE)
                    snackBar.show()
                }
                internet = true

            } else {
                internet = false
                //isNotConnected
                if (snackBar.isShown)
                    snackBar.dismiss()
                snackBar = Snackbar.make(
                    window.decorView.findViewById(android.R.id.content), "Network Lost",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).setActionTextColor(Color.WHITE)
                val snackBarView = snackBar.view
                snackBarView.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                val textView =
                    snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.WHITE)
                snackBar.show()


            }
        })
    }

    override fun loadWebview(url: String) {
        if (url != "") {
            bottomnav.visibility = View.GONE
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.blogs)
            Log.d("url", url)
            loadFragment(WebFragment(this, url))
        }
    }

    override fun loadEditFarm(farmid: Int) {


        loadFragment(EditFarmFragment(token, id, farmid, this))
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.edit_farm)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            loadFragment(DailyInputFragment(this, token, id))
            navView.setCheckedItem(R.id.menu_none)
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input)
            bottomnav.visibility = View.VISIBLE
            bottomnav.menu.findItem(R.id.nav_daily_input).isChecked = true

            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            mDrawerToggle.syncState()

        }
        mDrawerToggle.syncState()
    }

    override fun loadEditFlock(flockid: Int) {

        loadFragment(EditFlockFragment(token, flockid, this))
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.edit_farm)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            loadFragment(DailyInputFragment(this, token, id))
            navView.setCheckedItem(R.id.menu_none)
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input)
            bottomnav.visibility = View.VISIBLE
            bottomnav.menu.findItem(R.id.nav_daily_input).isChecked = true

            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            mDrawerToggle.syncState()

        }
        mDrawerToggle.syncState()
    }


    override fun loadEditShed(shedid: Int) {

        loadFragment(EditShedFragment(token, shedid, this))
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.edit_shed)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            loadFragment(DailyInputFragment(this, token, id))
            navView.setCheckedItem(R.id.menu_none)
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input)
            bottomnav.visibility = View.VISIBLE
            bottomnav.menu.findItem(R.id.nav_daily_input).isChecked = true

            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            mDrawerToggle.syncState()

        }
        mDrawerToggle.syncState()
    }

    private fun mobileNavigation() {

        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            when (it.itemId) {
                R.id.nav_side_home -> {


                    /*      StartFragmentArgs.fromBundle(requireActivity().intent?.extras)
                          val bundle = Bundle()
                          bundle.putString("products", this)
                          Navigation.findNavController(binding.getRoot())
                              .navigate(R.id.nav_addava, bundle)
      */
                    navController.navigate(R.id.nav_home)
                    bottomnav.selectedItemId = R.id.nav_home
                    bottomnav.visibility = View.VISIBLE
                }
                R.id.nav_side_Expenses -> {
                    navController.navigate(R.id.nav_Expenses)
                    bottomnav.visibility = View.GONE
                }
                R.id.nav_side_consulting -> {
                    navController.navigate(R.id.nav_consulting)
                    bottomnav.visibility = View.GONE
                }
                R.id.nav_side_Ledger -> {
                    navController.navigate(R.id.nav_side_Ledger)
                    bottomnav.visibility = View.GONE
                }
                R.id.nav_side_help_support -> {
                    navController.navigate(R.id.nav_side_help_support)
                    bottomnav.visibility = View.GONE
                }
                R.id.nav_side_Activity_Logs -> {
                    navController.navigate(R.id.nav_Activity_Logs)
                    bottomnav.visibility = View.GONE
                }
                R.id.nav_side_My_Cart -> {
                    navController.navigate(R.id.nav_My_Cart)
                }
                R.id.nav_side_Summary -> {
//                    navController.navigate(R.id.nav_side_consulting)
//                    toolbar.title = "Summary"
//                    loadFragment(SummaryFragment())
                }
                R.id.nav_order_summary -> {
                    navController.navigate(R.id.nav_OrderSummary)
                    /* toolbar.title = "My Orders"
                    bottomnav.visibility = View.VISIBLE
                    loadFragment(OrderSummaryFragment(this))*/
                }
                R.id.nav_side_FAQs -> {
                    navController.navigate(R.id.nav_faqs)
                    /* bottomnav.visibility = View.GONE
                    toolbar.title = "FAQs"
                    loadFragment(FaqsFragment())*/
                }
                R.id.nav_side_Setting -> {
                    navController.navigate(R.id.nav_terms_conditions)
                    /* bottomnav.selectedItemId = R.id.nav_daily_input
                    bottomnav.visibility = View.GONE
                    toolbar.title = "Daily Input"
                    loadFragment(TermsAndConditionFragment())*/
                }
                R.id.nav_side_Logout -> {
                    PrefrenceUtils.insertData(
                        this@MainActivity,
                        Constants.TEMPID,
                        ""
                    )
                    PrefrenceUtils.insertData(
                        this@MainActivity,
                        Constants.ACCESS_TOKEN_PREFERENCE,
                        ""
                    )
                    PrefrenceUtils.insertData(
                        this@MainActivity,
                        Constants.ID,
                        ""
                    )
                    PrefrenceUtils.insertData(
                        this@MainActivity,
                        Constants.LANG,
                        ""
                    )
                    bottomnav.visibility = View.GONE
                    startActivity(Intent(this, Signup1::class.java))
                }
            }
            return@OnNavigationItemSelectedListener true
        })

    }

    private fun sidenave() {


        navView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            when (it.itemId) {
                R.id.nav_side_home -> {
                    bottomnav.selectedItemId = R.id.nav_home
                    bottomnav.visibility = View.VISIBLE
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_home)
                    loadFragment(HomeFragment(this))
                }
                R.id.nav_side_Expenses -> {
                    bottomnav.visibility = View.GONE
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.expenses)
                    loadFragment(Expenses(this, token, id))
                }
                R.id.nav_side_consulting -> {
                    bottomnav.visibility = View.GONE
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.consulting)
                    loadFragment(ConsultingFragment(token, this))
                }
                R.id.nav_side_Ledger -> {
                    bottomnav.visibility = View.GONE
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.ledgers)
                    loadFragment(Leadgers())
                }
                R.id.nav_side_help_support -> {
                    bottomnav.visibility = View.GONE
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.helpsupport)
                    loadFragment(HelpSupportFragment(token, this))
                }
                R.id.nav_side_Activity_Logs -> {
                    bottomnav.visibility = View.GONE
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.activity_log)
                    loadFragment(ActivityLog(token, id))
                }
                R.id.nav_side_My_Cart -> {
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.my_cart)
                    loadFragment(CartFragment(token, this))
                    bottomnav.visibility = View.GONE
                }
                R.id.nav_side_Summary -> {
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.summary)
//                    loadFragment(SummaryFragment())
                }
                R.id.nav_order_summary -> {
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.my_Orders)
                    bottomnav.visibility = View.VISIBLE
                    loadFragment(OrderSummaryFragment(this))
                }
                R.id.nav_side_FAQs -> {
                    bottomnav.visibility = View.GONE
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_faqs)
                    loadFragment(FaqsFragment(this))
                }

                R.id.nav_side_Setting -> {
                    bottomnav.selectedItemId = R.id.nav_daily_input
                    bottomnav.visibility = View.GONE
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input)
                    loadFragment(TermsAndConditionFragment())
                }
                R.id.nav_side_Logout -> {
                    PrefrenceUtils.insertData(
                        this@MainActivity,
                        Constants.TEMPID,
                        ""
                    )
                    PrefrenceUtils.insertData(
                        this@MainActivity,
                        Constants.ACCESS_TOKEN_PREFERENCE,
                        ""
                    )
                    PrefrenceUtils.insertData(
                        this@MainActivity,
                        Constants.ID,
                        ""
                    )
                    PrefrenceUtils.insertData(
                        this@MainActivity,
                        Constants.LANG,
                        ""
                    )
                    bottomnav.visibility = View.GONE
                    startActivity(Intent(this, Signup1::class.java))
                }
            }
            return@OnNavigationItemSelectedListener true
        })

    }

    override fun loadIot() {


        loadFragment(IotListFragment(this))
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.iot_report)
        toolbar.visibility = View.GONE


        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {

            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_home)
            loadFragment(HomeFragment(this))
            toolbar.visibility = View.VISIBLE
            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            mDrawerToggle.syncState()

        }
        mDrawerToggle.syncState()
    }

    override fun loadDetailFrag(id: Int, from: String) {

        loadFragment(ItemDetailFragment(id.toString(), this, from))
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.edit_Profile)
        toolbar.visibility = View.GONE


        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            if (from == "sellshop") {
                loadFragment(
                    SellShopFragment(
                        this, token
                    )
                )
                toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_bazaar)
            } else if (from == "cart") {
                toolbar.title = this.resources.getString(com.antino.eggoz.R.string.my_cart)
                loadFragment(CartFragment(token, this))
            } else {
                onclick(null)
            }
            toolbar.visibility = View.VISIBLE
            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            mDrawerToggle.syncState()

        }
        mDrawerToggle.syncState()
    }


    override fun loadComment(mid: Int, comment: Int) {
        loadFragment(CommentFragment(this, mid, comment))
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.comment)


        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            toolbar.visibility = View.VISIBLE
            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            bottomnav.visibility = View.GONE
            mDrawerToggle.syncState()
            loadFeed()
        }
        mDrawerToggle.syncState()
    }

    override fun loadDetailFragback(from: String) {
        toolbar.visibility = View.VISIBLE
        mDrawerToggle.isDrawerIndicatorEnabled = true
        bottomnav.visibility = View.VISIBLE
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        mDrawerToggle.syncState()
        if (from == "sellshop") {
            loadFragment(
                SellShopFragment(
                    this, token
                )
            )
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_bazaar)
        } else if (from == "cart") {
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.my_cart)
            loadFragment(CartFragment(token, this))
        } else {
            onclick(null)
        }
    }

    override fun loadEditProfile() {

        loadFragment(EditProfileFragment(this, token, id))
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.edit_Profile)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            loadFragment(ProfileFragment(this, farm, token, this.id))
            toolbar.title = "Profile"
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            bottomnav.visibility = View.GONE
            mDrawerToggle.syncState()
        }
        mDrawerToggle.syncState()
    }

    override fun loadBuyFragment(
        from: String,
        id: Int,
        name: String,
        img: String,
        price: String,
        qnt: Int,
        des: String,
        tax: Boolean
    ) {


        loadFragment(
            BuyFragment(
                this,
                token,
                id,
                from,
                ids.toInt(),
                name,
                img,
                price,
                qnt,
                des,
                tax, this.id
            )
        )
        toolbar.visibility = View.VISIBLE
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.payment)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            if (from == "Cart") {
                loadFragment(CartFragment(token, this))
                toolbar.title = this.resources.getString(com.antino.eggoz.R.string.my_cart)
            } else {
                loadDetailFrag(id, from)
            }
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            bottomnav.visibility = View.GONE
            mDrawerToggle.syncState()
        }
        mDrawerToggle.syncState()
    }

    override fun loadAddAddress(
        from: String,
        id: Int,
        name: String,
        img: String,
        price: String,
        qnt: Int,
        des: String,
        tax: Boolean
    ) {
        loadFragment(
            AddAddressFragment(
                token, this,
                id,
                from,
                ids.toInt(),
                name,
                img,
                price,
                qnt,
                des,
                tax
            )
        )
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.add_address)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.payment)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            bottomnav.visibility = View.GONE
            mDrawerToggle.syncState()
            loadBuyFragment(
                from,
                id,
                name,
                img,
                price,
                qnt,
                des,
                tax
            )
        }
        mDrawerToggle.syncState()
    }


    private fun inAppUpdate() {


        val mAppUpdateManager = AppUpdateManagerFactory.create(this@MainActivity)
//        val appUpdateInfo=appUpdateManager.appUpdateInfo

//        mAppUpdateManager = AppUpdateManagerFactory.create(this@MainActivity)
        mAppUpdateManager.appUpdateInfo.let {
            it.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    try {
                        mAppUpdateManager.startUpdateFlowForResult(
                            // Pass the intent that is returned by 'getAppUpdateInfo()'.
                            appUpdateInfo,
                            // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                            AppUpdateType.IMMEDIATE,
                            // The current activity making the update request.
                            this,
                            // Include a request code to later monitor this update request.
                            MY_REQUEST_CODE
                        )
                    } catch (e: IntentSender.SendIntentException) {
                        e.printStackTrace()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }


    }


    override fun fetchProfile() {
        loadProfileData()
    }

    private fun loadProfileData() {

        val loadingdialog = CustomAlertLoadingActivity(this)
        loadingdialog.stateLoading()

        Log.e("data", "$token $id")

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.farmerProfile(
            token, id
        ).observe(this,
            Observer {
                loadingdialog.dismiss()
                if (it.errors == null) {

                    txt_person_mobile.text = it.farmer.phoneNo!!
                    txt_person_name.text = it.farmer.name.split(" ")[0]


                    Glide.with(this)
                        .asBitmap()
                        .load(it.farmer.image)
                        .centerInside()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.logo1)
                        .into(img)


                } else {
                    if (it.errors!![0].message == "Signature has expired." || it.errors!![0].message == "Invalid signature.") {
                        PrefrenceUtils.insertData(
                            this@MainActivity,
                            Constants.TEMPID,
                            ""
                        )
                        PrefrenceUtils.insertData(
                            this@MainActivity,
                            Constants.ACCESS_TOKEN_PREFERENCE,
                            ""
                        )
                        PrefrenceUtils.insertData(
                            this@MainActivity,
                            Constants.ID,
                            ""
                        )
                        startActivity(Intent(baseContext, Signup1::class.java))
                        Toast.makeText(this, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                        Log.d("data", "${it.errors!![0].message}")
                    }
                }
            }
        )


    }


    override fun loadConsulting() {
        bottomnav.visibility = View.GONE
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.consulting)
        loadFragment(ConsultingFragment(token, this))
        navView.menu.findItem(R.id.nav_side_consulting).isChecked = true

    }

    override fun loadSchedule() {

        mDrawerToggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadFragment(ScheduleFragment(this, token, id))
        navView.setCheckedItem(R.id.menu_none)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_schedule)
        bottomnav.menu.getItem(1).isChecked = true
        bottomnav.visibility = View.VISIBLE
        mDrawerToggle.syncState()
        super.loadSchedule()
    }

    override fun loadProfile() {
        mDrawerToggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadFragment(ProfileFragment(this, farm, token, this.id))
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_profile)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        bottomnav.visibility = View.GONE
        mDrawerToggle.syncState()

    }

    override fun AddFlock(id: Int, type: String) {
        loadFragment(AddFlockFragment(this, token, id, type))
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.add_Flock)


        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            loadDailyInput()
            mDrawerToggle.syncState()
        }
        mDrawerToggle.syncState()
    }


    override fun addFarm() {
        loadFragment(AddFarmFragment(this))
        bottomnav.visibility = View.GONE
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.add_farm)


        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            loadDailyInput()
            mDrawerToggle.syncState()
        }
        mDrawerToggle.syncState()
        super.addFarm()
    }


    override fun Farmdata(
        farmName: String,
        buildingno: String,
        landmark: String,
        city: String,
        state: String,
        pincode: String,
        NoOfBroiler: Int,
        number_of_layer_shed: Int,
        number_of_grower_shed: Int,
        farm_layer_type: String
    ) {

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.createFarm(
            token,
            id,
            farmName,
            buildingno,
            landmark,
            city,
            state,
            pincode,
            NoOfBroiler,
            number_of_layer_shed,
            number_of_grower_shed,
            farm_layer_type

        ).observe(this,
            Observer {
                if (it.success != "" || it.success != null) {
                    loadDailyInput()

                } else {
                    Log.d("data", "${it.errors!![0].message}")
                }
            }
        )


        super.Farmdata(
            farmName, buildingno, landmark, city, state,
            pincode, NoOfBroiler, number_of_layer_shed, number_of_grower_shed, farm_layer_type
        )
    }

    override fun HomeCallbackdailyInputMissed() {
        loadFragment(DailyInputFragment(this, token, id))
        navView.setCheckedItem(R.id.menu_none)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input)
        bottomnav.visibility = View.VISIBLE
        bottomnav.menu.findItem(R.id.nav_daily_input).isChecked = true

        super.HomeCallbackdailyInputMissed()
    }

    override fun HomeCallbackexoplayer(link: String, id: Int) {
        loadFragment(ExoplayerFragment(this, link, id))
        navView.setCheckedItem(R.id.menu_none)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.video)
        bottomnav.visibility = View.VISIBLE
        bottomnav.menu.findItem(R.id.nav_home).isChecked = true

        super.HomeCallbackexoplayer(link, id)
    }


    override fun FeedCallback() {

        loadFragment(CreateFeedFragment(this, token))
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.createfeed)
        bottomnav.visibility = View.GONE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            loadFragment(FeedFragment(this, token))
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_feed)
            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            bottomnav.visibility = View.VISIBLE
            mDrawerToggle.syncState()
        }
        mDrawerToggle.syncState()
        super.FeedCallback()
    }


    override fun addGrower(
        GrowerName: String,
        totalbirdcapacity: String,
        age: String,
        Vaccination: String,
        noOfShads: String,
        noOfGrower: String
    ) {


        val growerdata = Grower()
        growerdata.growerName = GrowerName
        growerdata.birdCapacity = totalbirdcapacity
        growerdata.age = age
        growerdata.Vaccination = Vaccination

        grower.add(growerdata)
        if (grower.size != noOfGrower.toInt() && noOfGrower.toInt() != 0) {

            val grower: ArrayList<Grower> = ArrayList()
            loadFragment(AddGrowerFragment(this, grower, noOfShads, noOfGrower))
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.addgrower)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            mDrawerToggle.isDrawerIndicatorEnabled = false
            mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
            mDrawerToggle.setToolbarNavigationClickListener {
                mDrawerToggle.isDrawerIndicatorEnabled = true
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                loadFragment(ProfileFragment(this, farm, token, this.id))
                toolbar.title = "Profile"
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                bottomnav.visibility = View.VISIBLE
                mDrawerToggle.syncState()
            }
            mDrawerToggle.syncState()


        } else {

            farmdata = Farmdata()
            farmdata.shadesSize = noOfShads
            farmdata.growerSize = "0 "
            farmdata.farmName = farmName
            farmdata.farmAddress = farmAddress
            farmdata.shades = shades
            farmdata.grower = grower
            farm.add(farmdata)

            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            loadFragment(ProfileFragment(this, farm, token, this.id))
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_profile)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            mDrawerToggle.syncState()
            navView.setCheckedItem(R.id.menu_none)
            bottomnav.visibility = View.GONE
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        super.addGrower(GrowerName, totalbirdcapacity, age, Vaccination, noOfShads, noOfGrower)
    }


    override fun loadDailyInput() {
        mDrawerToggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadFragment(DailyInputFragment(this, token, id))
        bottomnav.menu.findItem(R.id.nav_daily_input).isChecked = true
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        bottomnav.visibility = View.VISIBLE
        navView.setCheckedItem(R.id.menu_none)
        mDrawerToggle.syncState()
    }

    override fun loadSellShop() {
        toolbar.visibility = View.VISIBLE
        mDrawerToggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        loadFragment(SellShopFragment(this, token))
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_bazaar)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        bottomnav.visibility = View.VISIBLE
        mDrawerToggle.syncState()
    }

    override fun loadFeed() {
        loadFragment(FeedFragment(this, token))
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_feed)
        mDrawerToggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        bottomnav.visibility = View.VISIBLE
        mDrawerToggle.syncState()
    }

    override fun loadHome() {
        loadFragment(HomeFragment(this))
        navView.menu.getItem(0).isChecked = true
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_home)
        bottomnav.menu.getItem(0).isChecked = true
        navView.menu.getItem(0).isChecked = true

        mDrawerToggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        bottomnav.visibility = View.VISIBLE
        toolbar.visibility = View.VISIBLE
        mDrawerToggle.syncState()
    }

    override fun loadSummary(flock_id: Int) {
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.summary)
        bottomnav.visibility = View.GONE
        navView.menu.getItem(12).isChecked = true
        loadFragment(SummaryFragment(this, token, flock_id))
    }

    override fun loadDailyInputList(id: Int) {
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input_list)
        bottomnav.visibility = View.GONE
        navView.menu.getItem(12).isChecked = true
        loadFragment(DailyInputListFragment(this, id))
    }

    override fun loadUpdateDailtInput(data: DailInput.Result) {
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input_update)
        bottomnav.visibility = View.GONE
        navView.menu.getItem(12).isChecked = true
        loadFragment(DaiyInputUpdateFragment(this, data))
    }

    override fun dailyInputCallback(
        flockid: Int,
        flocktotalactivebird: Int,
        lastUpdate: String,
        type: String
    ) {

        loadFragment(
            DailyInputDetailFragment(
                this,
                token,
                flockid,
                flocktotalactivebird,
                lastUpdate,
                type
            )
        )
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false
        bottomnav.visibility = View.GONE


        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            loadFragment(DailyInputFragment(this, token, id))
            bottomnav.menu.findItem(R.id.nav_daily_input).isChecked = true
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            bottomnav.visibility = View.VISIBLE
            navView.setCheckedItem(R.id.menu_none)
            mDrawerToggle.syncState()
        }
        mDrawerToggle.syncState()

        super.dailyInputCallback(flockid, flocktotalactivebird, lastUpdate, type)
    }

    override fun scheduleDetail(id: Int) {
        loadFragment(
            ScheduleDetailFragment(
                this,
                token,
                id
            )
        )
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.summary)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false
        bottomnav.visibility = View.GONE

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            loadSchedule()

        }
        mDrawerToggle.syncState()

    }


    private fun bottomnav() {
        //bottom nav click listner
        bottomnav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    loadFragment(HomeFragment(this))
                    navView.menu.getItem(0).isChecked = true
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_home)
                    bottomnav.visibility = View.VISIBLE
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_schedule -> {
                    loadFragment(ScheduleFragment(this, token, id))
                    navView.setCheckedItem(R.id.menu_none)
                    toolbar.title =
                        this.resources.getString(com.antino.eggoz.R.string.menu_schedule)
                    bottomnav.visibility = View.VISIBLE
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_sell_shop -> {
                    loadFragment(SellShopFragment(this, token))
                    navView.setCheckedItem(R.id.menu_none)
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_bazaar)
                    bottomnav.visibility = View.VISIBLE
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_daily_input -> {
                    loadFragment(DailyInputFragment(this, token, id))
                    navView.setCheckedItem(R.id.menu_none)
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.daily_Input)
                    bottomnav.visibility = View.VISIBLE
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.nav_feed -> {
                    loadFragment(FeedFragment(this, token))
                    navView.setCheckedItem(R.id.menu_none)
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_feed)
                    bottomnav.visibility = View.VISIBLE
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    loadFragment(HomeFragment(this))
                    bottomnav.visibility = View.VISIBLE
                    return@setOnNavigationItemSelectedListener true
                }
            }
        }

    }


    override fun onclick(id: Int?) {
        loadFragment(ExploreProductsFragment(this, token, id))
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.product_List)
        bottomnav.visibility = View.GONE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            loadFragment(SellShopFragment(this, token))
            toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_bazaar)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            bottomnav.visibility = View.VISIBLE
            mDrawerToggle.syncState()
        }
        mDrawerToggle.syncState()

        super.onclick(id)
    }


    override fun loadAddLayer(mid: Int, from: String) {

        loadFragment(AddLayerFragment(this, mid, token, from))
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        val lang = PrefrenceUtils.retriveData(this, Constants.LANG)!!
        if (lang == "hi") {
            if (from == "broiler")
                toolbar.title = "जोड़ना भट्टी शेड"
            else if (from == "layer")
                toolbar.title = "जोड़ना परत शेड"
        } else {
            toolbar.title = "Add $from Shed"
        }
        bottomnav.visibility = View.GONE
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            loadDailyInput()
        }
        mDrawerToggle.syncState()
    }


    override fun loadTrackOrder() {
        loadFragment(TrackOrderFragment())
        bottomnav.visibility = View.GONE
        toolbar.title = "Track Order"
        navView.setCheckedItem(R.id.menu_none)

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mDrawerToggle.isDrawerIndicatorEnabled = false

        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
        mDrawerToggle.setToolbarNavigationClickListener {
            toolbar.title = "My Orders"
            loadFragment(OrderSummaryFragment(this))
            bottomnav.visibility = View.VISIBLE
            bottomnav.menu.getItem(0).isChecked = true
            navView.menu.getItem(0).isChecked = true

            mDrawerToggle.isDrawerIndicatorEnabled = true
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            bottomnav.visibility = View.VISIBLE
            mDrawerToggle.syncState()
        }
        mDrawerToggle.syncState()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_notification -> {
                loadFragment(NotificationFragment(this))
                bottomnav.visibility = View.GONE
                toolbar.title =
                    this.resources.getString(com.antino.eggoz.R.string.action_notification)
                navView.setCheckedItem(R.id.menu_none)

                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                mDrawerToggle.isDrawerIndicatorEnabled = false

                mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24)
                mDrawerToggle.setToolbarNavigationClickListener {
                    loadFragment(HomeFragment(this))
                    navView.menu.getItem(0).isChecked = true
                    toolbar.title = this.resources.getString(com.antino.eggoz.R.string.menu_home)
                    bottomnav.visibility = View.VISIBLE
                    bottomnav.menu.getItem(0).isChecked = true
                    navView.menu.getItem(0).isChecked = true

                    mDrawerToggle.isDrawerIndicatorEnabled = true
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                    drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                    bottomnav.visibility = View.VISIBLE
                    mDrawerToggle.syncState()
                }
                mDrawerToggle.syncState()

                return true
            }
            R.id.nav_top_My_Cart -> {
                toolbar.title = this.resources.getString(com.antino.eggoz.R.string.my_cart)
                loadFragment(CartFragment(token, this))
                bottomnav.visibility = View.GONE
                navView.menu.findItem(R.id.nav_side_My_Cart).isChecked = true
            }
            R.id.nav_lang -> {
                lang_check()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun lang_check() {
        if (PrefrenceUtils.retriveData(this, Constants.LANG)!!.toString() == "en") {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Change Language")
            builder.setMessage("Are you sure want to change language to हिन्दी")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { dialogInterface, which ->
                PrefrenceUtils.insertData(
                    this,
                    Constants.LANG,
                    "hi"
                )
                setLocale("hi")
            }
            builder.setNeutralButton("Cancel") { dialogInterface, which ->
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        } else {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Change Language")
            builder.setMessage("Are you sure want to change language to English")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { dialogInterface, which ->

                PrefrenceUtils.insertData(
                    this,
                    Constants.LANG,
                    "en"
                )
                setLocale("en")
            }
            builder.setNeutralButton("Cancel") { dialogInterface, which ->
//                Toast.makeText(applicationContext,"clicked cancel\n operation cancel",Toast.LENGTH_LONG).show()
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    private fun setLocale(lang: String) {
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val refresh = Intent(this, MainActivity::class.java)
        finish()
        startActivity(refresh)
    }

    override fun loadCart() {

        mDrawerToggle.isDrawerIndicatorEnabled = true
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)

        toolbar.title = this.resources.getString(com.antino.eggoz.R.string.my_cart)
        toolbar.visibility = View.VISIBLE
        loadFragment(CartFragment(token, this))
        bottomnav.visibility = View.GONE
        navView.menu.findItem(R.id.nav_side_My_Cart).isChecked = true

        mDrawerToggle.syncState()
    }


    private fun loadFragment(fragment: Fragment) {
        this.currentFocus?.let { view ->
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, "fragment_home")
            .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        val seach: MenuItem = menu.findItem(R.id.action_search)
        val searchview: SearchView = seach.actionView as SearchView
        searchview.queryHint = this.resources.getString(com.antino.eggoz.R.string.action_search)

        menu.findItem(R.id.action_search).isVisible = false

        searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.d("data", query.toString())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("data", newText.toString())
                return true
            }

        })
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d("data", "main $requestCode")
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val fragments =
            supportFragmentManager.fragments
        if (fragments != null) {
            for (fragment in fragments) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("data", "${requestCode}")
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
        if (requestCode == MY_REQUEST_CODE) {
            Toast.makeText(this, "start download", Toast.LENGTH_SHORT).show()
            if (resultCode != RESULT_OK) {
                Log.e("data", "in app update: $resultCode")
                // If the update is cancelled or fails,
                // you can request to start the update again.
            }
        }
    }

    override fun onResume() {
        super.onResume()
        inAppUpdate()
    }


    override fun onBackPressed() {
        Log.d("data2", "main back")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}