package com.andreyyurko.hhtinder.ui.employee.showvacancy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentShowVacancyBinding
import com.andreyyurko.hhtinder.singleton.TransferSingleton

class ShowVacancyFragment : Fragment(R.layout.fragment_show_vacancy) {
    private val viewBinding by viewBinding(FragmentShowVacancyBinding::bind)

    private var cvId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("CVInfo") { _, bundle ->
            viewBinding.iVacancyName.text = bundle.get("jobName").toString()
            viewBinding.iVacancyText.text = bundle.get("content").toString()
            cvId = bundle.getInt("id")
        }
        try {
            viewBinding.avatarImageView.setImageDrawable(TransferSingleton.instance.getTransferArchive()!!.image)
        } catch (e: Exception) {
            
        }
    }
}