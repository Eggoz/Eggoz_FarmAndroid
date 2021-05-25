package com.antino.eggoz.ui.notification

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentNotificationBinding


class NotificationFragment(val context:MainActivity) : Fragment() {
    private lateinit var binding: FragmentNotificationBinding


     override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

         binding = FragmentNotificationBinding.inflate(inflater, container, false)
         setHasOptionsMenu(true)

         val img:ArrayList<String> = ArrayList()
         val type:ArrayList<String> = ArrayList()
         val eggozprice:ArrayList<String> = ArrayList()

         img.add("https://3.imimg.com/data3/VE/QY/MY-7539096/white-egg-500x500.jpg")
         img.add("https://img2.mashed.com/img/gallery/how-are-brown-and-white-eggs-different/birds-of-a-feather-1497626048.jpg")
         img.add("https://3.imimg.com/data3/VE/QY/MY-7539096/white-egg-500x500.jpg")
         img.add("https://img2.mashed.com/img/gallery/how-are-brown-and-white-eggs-different/birds-of-a-feather-1497626048.jpg")
         type.add("white egg")
         type.add("brown egg")
         type.add("white egg")
         type.add("brown egg")
         eggozprice.add("1221")
         eggozprice.add("1221")
         eggozprice.add("1221")
         eggozprice.add("1221")

         binding.root.isFocusableInTouchMode = true
         binding.root.requestFocus()
         binding.root.setOnKeyListener { v, keyCode, event ->
             if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                 context.loadHome()
                 true
             } else false
         }

         binding.recycleviewNotification.layoutManager = LinearLayoutManager(requireContext())
         binding.recycleviewNotification.itemAnimator = DefaultItemAnimator()
         binding.recycleviewNotification.isNestedScrollingEnabled = false
         binding.recycleviewNotification.adapter= NotificationAdapter(img, type, eggozprice,eggozprice)


         return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.findItem(R.id.action_notification).isVisible =false
        super.onCreateOptionsMenu(menu, inflater)
    }

}