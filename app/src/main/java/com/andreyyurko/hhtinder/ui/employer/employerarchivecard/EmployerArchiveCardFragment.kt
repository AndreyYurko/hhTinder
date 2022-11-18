package com.andreyyurko.hhtinder.ui.employer.employerarchivecard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentEmployerArchiveCardBinding
import com.andreyyurko.hhtinder.databinding.FragmentVacancyEmployerBinding
import com.andreyyurko.hhtinder.singleton.TransferSingleton

class EmployerArchiveCardFragment : Fragment(R.layout.fragment_vacancy_employer) {
    private val viewBinding by viewBinding(FragmentVacancyEmployerBinding::bind)

    private var vacancyId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("EmployeeInfo") { _, bundle ->
            viewBinding.iVacancyName.setText(bundle.get("jobName").toString())
            viewBinding.iVacancyText.setText(bundle.get("content").toString())
            vacancyId = bundle.getInt("id")
        }
        try {
            viewBinding.avatarImageView.setImageDrawable(TransferSingleton.instance.getTransferArchive()!!.image)
        } catch (e: Exception) {
            
        }
    }
}