package com.antino.eggoz.ui.schedule

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentScheduleBinding

class  ScheduleFragment(val context: MainActivity, val token:String, val mid:Int) : Fragment(), AdditemDialog {

    private lateinit var binding: FragmentScheduleBinding


//    private var farmlist: List<Farm.Result> = ArrayList()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)

        binding = FragmentScheduleBinding.inflate(inflater, container, false)

 /*       farmData()
        init()

*/

        return binding.root
    }
  /*  private fun init(){
        binding.btnAddFlocks.setOnClickListener {
            context.loadProfile()
        }
    }


    private fun farmData(){

        val loadingdialog= CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getFarm(
            token,mid
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                Log.d("data","${it.count}")
                if (it.count==null){
                    if (it.errors?.get(0)?.message!=null|| it.errors?.get(0)?.message!=""){
                        Log.d("data","get farm error ${it.errors!![0].message}")
                    }else
                        Log.d("data","farm data ${it.results?.size} count ${it.count}")

                }else if(it.count==0){
                    binding.conAddflock.visibility=View.VISIBLE
                    binding.recycleSchedule.visibility=View.GONE

                }else{
                    binding.conAddflock.visibility=View.GONE
                    binding.recycleSchedule.visibility=View.VISIBLE

                        for (i in it.results?.indices!!){
                            farmlist=it.results!!

                        }
                        binding.recycleSchedule.layoutManager = LinearLayoutManager(
                            activity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        binding.recycleSchedule.setHasFixedSize(true)
//                        val adapter= FarmListAdapter(context,farmlist)
//                        binding.recycleSchedule.adapter = adapter


                }

            }
        )


    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_search).isVisible =false
        super.onCreateOptionsMenu(menu, inflater)
    }*/

}