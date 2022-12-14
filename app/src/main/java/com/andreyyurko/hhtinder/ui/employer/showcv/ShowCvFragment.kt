package com.andreyyurko.hhtinder.ui.employer.showcv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentShowCvBinding
import com.andreyyurko.hhtinder.singleton.TransferSingleton

class ShowCvFragment : Fragment(R.layout.fragment_show_cv){
    private val viewBinding by viewBinding(FragmentShowCvBinding::bind)

    private var vacancyId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("EmployeeInfo") { _, bundle ->
            viewBinding.cvName.text = bundle.get("jobName").toString()
            viewBinding.cvText.text = bundle.get("content").toString()
            vacancyId = bundle.getInt("id")
        }
        try {
            viewBinding.avatarImageView.setImageDrawable(TransferSingleton.instance.getTransferArchive()!!.image)
        } catch (e: Exception) {

        }
    }
}