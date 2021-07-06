package com.antino.eggoz.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentFeedBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.feed.callback.CallbackforFeed
import com.antino.eggoz.view.CustomAlertLoading

class FeedFragment(val context:MainActivity,val token:String) : Fragment(), CallbackforFeed {
    private lateinit var binding: FragmentFeedBinding
    private var loading:Boolean=false
    private lateinit var adapter: FeedAdapter

    private lateinit var loadingdialog:CustomAlertLoading
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        init()
        loadFeed()

        binding.floatingAddFeed.setOnClickListener { context.FeedCallback() }
        return binding.root
    }

    private fun init(){
        loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()


        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadHome()
                true
            } else false
        }
    }

    private fun loadingCheck(){
        if (!loading) {
            loadingdialog.dismiss()
            loading=true
        }
    }

    override fun getcomment(id: Int,comment:Int) {
        super.getcomment(id,comment)


        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        Log.d("data","comment id $id")
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getComment(
            token,id
        ).observe(context,
            Observer {
                loadingdialog.dismiss()
                if (it.errors==null){
                  /*  val bottomSheet = CommentBottomSheet(this, id,comment,  it.results)
                    bottomSheet.show(this.childFragmentManager, "comment")*/
                }else{
                    Toast.makeText(context,it.errors!![0].message,Toast.LENGTH_SHORT).show()
                }


            }
        )

    }
    private fun loadFeed(){

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.loadFeed(
            token
        ).observe(context,
            Observer {
                loadingCheck()


                if (it.results!=null) {
                    binding.recycleFeed.layoutManager = LinearLayoutManager(
                        activity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    binding.recycleFeed.setHasFixedSize(true)
                    adapter=FeedAdapter(context,this, this.childFragmentManager, it.results)
                    binding.recycleFeed.adapter =adapter

                    (binding.recycleFeed.adapter as FeedAdapter).notifyDataSetChanged()
/*
                    if (it.results?.size!!>2)
                    binding.recycleFeed.scrollToPosition(it.results?.size?.minus(1)!!)*/
                }
            }
        )




    }



    override fun postlikedislike(pos: Int) {
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.likedislikePost(
            token, pos
        ).observe(this,
            Observer {
                if (it?.message!=null||it?.message!=""){
//                    loadFeed()
                }else{
                    if ( it.errors!![0].message!=null) {
                        Log.d("data", "${it.errors!![0].message}")
                    }
                }
            }
        )

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_search).isVisible =false
        super.onCreateOptionsMenu(menu, inflater)
    }


}