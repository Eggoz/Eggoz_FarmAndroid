package com.antino.eggoz.ui.faqs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.databinding.FragmentFaqsBinding


class FaqsFragment : Fragment() {
    private lateinit var binding: FragmentFaqsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFaqsBinding.inflate(inflater, container, false)
        val title:ArrayList<String> = ArrayList()
        val des:ArrayList<String> = ArrayList()

        title.add("How to add your farm in the app?")
        title.add(" How to add shed and flock in the farm?")
        title.add("Can I add more than one flock in a single shed and manage the farm?")
        title.add("How can I add more than one flock in a single shed?")
        title.add("Will I get notification of the farm activity?")
        des.add("You can go to My Farm Section from the menu provided at the bottom of the home screen and fill the form. You need to provide the below information:\n" +
                "A. Name of the farm\n" +
                "B. How many number of layer & grower sheds in your farm.\n" +
                "C. Address of the farm with City Name, State and PIN number.\n")
        des.add("After creating the farm, you need to add the sheds. \n" +
                "Go to My Farm option in the bottom of the homepage, then there is the option to add shed against you farm. \n" +
                "Click the “Add Shed” option and fill the details as per the sheds and flock available in your farm.\n" +
                "After filling the shed details, you will be asked to add the flock in your shed. \n" +
                "Then, you can select the type of flock breed, enter flock size, age, flock name and submit the details.\n")
        des.add("Yes")
        des.add("Go to My Farm, against the every layer shed you can find the option of “Add Flock”. Select the shed under which you want to add the flock.\n" +
                "Click on the option to “Add Flock” and fill the flock details in the form. \n" +
                "After submitting the form your flock will be added under that shed.\n")
        des.add("Yes, you will get notification of the farm activity against the particular flock after updating the flock details.")

        binding.recycleviewFaqs.layoutManager = LinearLayoutManager(context)
        binding.recycleviewFaqs.itemAnimator = DefaultItemAnimator()
        binding.recycleviewFaqs.isNestedScrollingEnabled = false
        binding.recycleviewFaqs.adapter= FaqsAdapter(title, des)

        return binding.root
    }

}