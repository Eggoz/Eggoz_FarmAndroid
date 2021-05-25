package com.antino.eggoz.ui.helpsupport

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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



        return binding.root
    }
    private fun validate(){
        if (binding.edtMessage.text!!.isEmpty())
            Toast.makeText(context,"Enter some Message",Toast.LENGTH_SHORT).show()
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
                snackBarView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.green))
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
        /*
        val loadingdialog = CustomAlertLoading(this)
        loadingdialog.stateLoading()

        val viewModel = ViewModelProvider(this).get(ModelMain::class.java)
        viewModel.Consulting(
                token,binding.edtMessage.text.toString(),null, mediaImageFile!!, requestFile,"Consulting"
        ).observe(requireActivity(), androidx.lifecycle.Observer {
            loadingdialog.dismiss()
            if (it.errors == null) {
                val snackBar = Snackbar.make(
                        binding.root, "Our Team will Contact You Soon",
                        Snackbar.LENGTH_LONG
                ).setAction("Action", null).setActionTextColor(Color.WHITE)
                val snackBarView = snackBar.view
                snackBarView.setBackgroundColor(ContextCompat.getColor(mcontext, R.color.green))
                val textView =
                        snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
                textView.setTextColor(Color.WHITE)
                snackBar.show()
                mcontext.loadHome()
            } else {
                Toast.makeText(context, it.errors!![0].message, Toast.LENGTH_SHORT).show()
            }


        }
        )*/
    }
}