package com.antino.eggoz.ui.daily_input

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentDailyInputBroilerBinding
import com.antino.eggoz.databinding.FragmentDailyInputDetailBinding

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