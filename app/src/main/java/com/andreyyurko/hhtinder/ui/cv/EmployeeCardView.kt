package com.andreyyurko.hhtinder.ui.cv

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.andreyyurko.hhtinder.databinding.ViewEmployeeCardBinding

class EmployeeCardView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr, defStyleRes) {

    val viewBinding = ViewEmployeeCardBinding.inflate(LayoutInflater.from(context), this, false)

}