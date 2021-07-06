package com.antino.eggoz.ui.daily_input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.antino.eggoz.databinding.FragmentDailyInputBroilerBinding

class DailyInputBroilerFragment : Fragment() {
    private lateinit var binding: FragmentDailyInputBroilerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDailyInputBroilerBinding.inflate(inflater, container, false)
        return binding.root
    }

}