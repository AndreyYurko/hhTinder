package com.andreyyurko.hhtinder.ui.employee.employeearchivecard

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentEmployerArchiveCardBinding

class EmployerArchiveCardFragment : Fragment(R.layout.fragment_employer_archive_card) {
    private val viewBinding by viewBinding(FragmentEmployerArchiveCardBinding::bind)

    private var vacancyId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("EmployeeInfo") {_, bundle ->
            viewBinding.jobName.text = bundle.get("jobName").toString()
            vacancyId = bundle.getInt("id")
        }
    }
}