package com.example.mycustomview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.example.mycustomview.databinding.MyCustomViewBinding

class CustomView
@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding = MyCustomViewBinding.bind(
        View.inflate(context,R.layout.my_custom_view,this)
    )
    fun setText(firstLine: String,secondLine: String){
        binding.userName.text = firstLine
        binding.userSurname.text = secondLine
    }
}