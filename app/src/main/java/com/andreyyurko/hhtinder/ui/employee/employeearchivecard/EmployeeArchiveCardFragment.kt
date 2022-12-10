package com.andreyyurko.hhtinder.ui.employee.employeearchivecard

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentCvEmployeeBinding
import com.andreyyurko.hhtinder.databinding.FragmentVacancyEmployerBinding
import com.andreyyurko.hhtinder.singleton.TransferSingleton
import com.andreyyurko.hhtinder.singleton.VocabsSingleton
import com.andreyyurko.hhtinder.utils.date.DatePicker

class EmployeeArchiveCardFragment : Fragment(R.layout.fragment_cv_employee) {
    private val viewBinding by viewBinding(FragmentCvEmployeeBinding::bind)

    private var cvId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("EmployeeInfo") { _, bundle ->
            viewBinding.cvName.setText(bundle.get("jobName").toString())
            viewBinding.cvText.setText(bundle.get("content").toString())
            cvId = bundle.getInt("id")
        }
        try {
            viewBinding.avatarImageView.setImageDrawable(TransferSingleton.instance.getTransferArchive()!!.image)
        } catch (e: Exception) {
            
        }
        initDatePicker()
        initCategoryPicker()
    }

    fun initDatePicker() {
        var datePicker = DatePicker(viewBinding.editTextDate, context);
        datePicker.initDatePicker()
    }

    fun initCategoryPicker() {
        viewBinding.categoriesList.adapter = ArrayAdapter(
            this.requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            VocabsSingleton.instance.getJobCatVocab()
        )
    }
}