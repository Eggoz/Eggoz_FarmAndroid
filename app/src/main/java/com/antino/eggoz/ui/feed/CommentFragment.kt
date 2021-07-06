package com.antino.eggoz.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.databinding.FragmentCommentBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.ui.feed.callback.CommentFragCallback
import com.antino.eggoz.utils.Constants
import com.antino.eggoz.utils.PrefrenceUtils
import com.antino.eggoz.view.CustomAlertLoading

class CommentFragment(
    val context:MainActivity,
    var mid: Int, var comment: Int
) : Fragment(), CommentFragCallback {

    private lateinit var binding: FragmentCommentBinding
    private lateinit var token: String


    private var loading: Boolean = false

    private lateinit var loadingdialog: CustomAlertLoading
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        binding.txtRef.visibility = View.GONE

        token = PrefrenceUtils.retriveData(
            activity?.applicationContext!!,
            Constants.ACCESS_TOKEN_PREFERENCE
        )!!


        init()
        binding.edtComment.requestFocus()
        loadComment()

        binding.btnSend.setOnClickListener {
            validate()
        }
        return binding.root
    }

    private fun validate() {
        if (binding.edtComment.text!!.isEmpty())
            Toast.makeText(requireContext(), "Please Add Some Comment", Toast.LENGTH_SHORT).show()
//            binding.edtCommentLayout.error="Please add some Comment"

        else {
            submit()
        }
    }

    private fun init() {
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadFeed()
                true
            } else false
        }



        loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()
    }

    private fun loadingCheck() {
        if (!loading) {
            loadingdialog.dismiss()
            loading = true
        }
    }

    private fun loadComment() {

        Log.d("data", "comment id $id")
        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.getComment(
            token, mid
        ).observe(viewLifecycleOwner,
            Observer {
                loadingCheck()
                if (it.errors == null) {
                    val layoutManager = LinearLayoutManager(requireContext())
                    layoutManager.stackFromEnd=true
                    layoutManager.reverseLayout=true

                    binding.recycleviewComment.layoutManager = layoutManager
                    binding.recycleviewComment.setHasFixedSize(true)
                    binding.recycleviewComment.adapter = CommentAdapter(this, it.results)
                    (binding.recycleviewComment.adapter as CommentAdapter).notifyDataSetChanged()

                    if (it.results != null && it.results?.size!! > 2)
                        binding.recycleviewComment.smoothScrollToPosition(0)
                } else {
                    Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                }


            }
        )
    }

    override fun likedislike(id: Int) {
        likedislikeapi(id)
    }

    private fun likedislikeapi(pos: Int) {

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.likedislikeCommentPost(
            token, pos
        ).observe(this,
            Observer {
//                loadingdialog.dismiss()
                Log.d("data", "$it")
                if (it?.message != null || it?.message != "") {
                    loadComment()


                } else {
                    if (it.errors!![0].message != null) {
                        Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
                        Log.d("data", "${it.errors!![0].message}")
                    }
                }
            }
        )
    }

    private fun submit() {

        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.nestedcomment(
            token, mid, binding.edtComment.text.toString()
        ).observe(viewLifecycleOwner,
            Observer {
                loadingdialog.dismiss()

                if (it?.errors == null) {
//                    getcomment(pos,pos)
                    loadComment()
                    binding.edtComment.setText("")
                } else {
                    if (it.errors!![0].message != null) {
                        Toast.makeText(requireContext(), it.errors!![0].message, Toast.LENGTH_SHORT)
                            .show()
                        Log.d("data", "${it.errors!![0].message}")
                    }
                }
            }
        )
    }

}