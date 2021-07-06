package com.antino.eggoz.ui.helpsupport

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.antino.eggoz.MainActivity
import com.antino.eggoz.R
import com.antino.eggoz.databinding.FragmentHelpSupportBinding
import com.antino.eggoz.modelvew.ModelMain
import com.antino.eggoz.view.CustomAlertLoading
import com.google.android.material.snackbar.Snackbar

class HelpSupportFragment(var token:String,var mcontext:MainActivity) : Fragment() {
    private lateinit var binding: FragmentHelpSupportBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHelpSupportBinding.inflate(inflater, container, false)

        binding.btnSubmit.setOnClickListener { validate() }

        init()
        binding.root.isFocusableInTouchMode = true
        binding.root.requestFocus()
        binding.root.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN) {
                mcontext.loadHome()
                true
            } else false
        }

        return binding.root
    }

    private fun init() {
        binding.edtMessage.setOnTouchListener { view, motionEvent ->
            if (binding.edtMessage.hasFocus()) {
                binding.root.getParent().requestDisallowInterceptTouchEvent(true)
                if (motionEvent.getAction() and MotionEvent.ACTION_MASK == MotionEvent.ACTION_SCROLL) {
                    binding.root.getParent().requestDisallowInterceptTouchEvent(false);
                    return@setOnTouchListener true
                }

            }
            return@setOnTouchListener false
        }
    }

    private fun validate(){
        if (binding.edtMessage.text!!.isEmpty())
            Toast.makeText(context,"Enter Some Message",Toast.LENGTH_SHORT).show()
        else submit()
    }
    private fun submit(){


        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.help(
            token,binding.edtMessage.text.toString()
        ).observe(requireActivity(), androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.errors == null) {
                val snackBar = Snackbar.make(
                    binding.root, "Our Team will Contact You Soon",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).setActionTextColor(Color.WHITE)
                val snackBarView = snackBar.view
                snackBarView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.dark_green))
                val textView =
                    snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.WHITE)
                snackBar.show()
                mcontext.loadHome()
            } else {
                Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
            }


        }
        )
    }
}