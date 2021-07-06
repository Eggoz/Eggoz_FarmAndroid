package com.antino.eggoz.ui.home

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentHomeBinding
import com.antino.eggoz.databinding.FragmentIotListBinding
import com.antino.eggoz.ui.home.adapter.IotListAdapter
import com.antino.eggoz.ui.profile.adapter.FarmAdapter


class IotListFragment(val context:MainActivity) : Fragment() {
    private lateinit var binding:FragmentIotListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { binding = FragmentIotListBinding.inflate(inflater, container, false)
        val view = binding.root
        initView()
        return view
    }

    private fun initView() {


        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                context.loadHome()
                true
            } else false
        }

        binding.recycyleIot.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recycyleIot.setHasFixedSize(true)
        binding.recycyleIot.adapter = IotListAdapter(context)
    }


}