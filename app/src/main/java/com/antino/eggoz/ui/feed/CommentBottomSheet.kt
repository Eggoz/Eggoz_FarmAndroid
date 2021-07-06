package com.antino.eggoz.ui.feed

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.antino.eggoz.databinding.FragmentCommentbottomsheetBinding
import com.antino.eggoz.ui.feed.callback.BottomSheetcallback
import com.antino.eggoz.ui.feed.model.Comment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CommentBottomSheet(private val context:FeedFragment,private val mid:Int,private val like_no:Int,

                         var results: List<Comment.ResultComment>? = null
    /*private val status:Boolean,
                         private val img_pic: ArrayList<String>,
                         private val name: ArrayList<String>,
                         private val commentDetail: ArrayList<String>*/) : BottomSheetDialogFragment(),
    BottomSheetcallback {
    private lateinit var binding: FragmentCommentbottomsheetBinding
    private lateinit var mcontext:Context




    override fun onAttach(context: Context) {
        mcontext=context
        super.onAttach(context)
    }


    override fun setupDialog(dialog: Dialog, style: Int) {

        binding = FragmentCommentbottomsheetBinding.inflate(layoutInflater)
        binding.txtRef.visibility= View.GONE

        binding.recycleviewComment.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.recycleviewComment.setHasFixedSize(true)
//        binding.recycleviewComment.adapter = CommentAdapter(this,context,results)
        (binding.recycleviewComment.adapter as CommentAdapter).notifyDataSetChanged()

        if(results!=null && results?.size!!>2)
        binding.recycleviewComment.smoothScrollToPosition(results?.size!! -1)


        binding.edtComment.requestFocus()

        binding.txtLike.text=like_no.toString()

        binding.btnSend.setOnClickListener {
            validate()
        }




        getDialog()?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.setContentView(binding.root)
    }

    override fun reply(pos: Int, person: String) {
        super.reply(pos, person)
        binding.txtRef.visibility= View.VISIBLE
        binding.txtRef.text=person
    }

    private fun validate(){
        if (binding.edtComment.text!!.isEmpty())
            Toast.makeText(mcontext,"Please Add Some Comment",Toast.LENGTH_SHORT).show()
//            binding.edtCommentLayout.error="Please add some Comment"

        else{
            submit()
        }
    }
    private fun submit(){
//        context.nestedComment(mid,binding.edtComment.text.toString())
    }

    override fun onDismiss(dialog: DialogInterface) {
        getDialog()?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        super.onDismiss(dialog)
    }

}