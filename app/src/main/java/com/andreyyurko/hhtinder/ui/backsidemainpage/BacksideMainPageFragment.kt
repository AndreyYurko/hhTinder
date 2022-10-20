package com.andreyyurko.hhtinder.ui.backsidemainpage

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentBacksideMainPageBinding

class BacksideMainPageFragment : Fragment(R.layout.fragment_backside_main_page) {
    companion object {
        const val LOG_TAG = "BacksideMainPage Fragment"
    }

    private val viewBinding by viewBinding(FragmentBacksideMainPageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}