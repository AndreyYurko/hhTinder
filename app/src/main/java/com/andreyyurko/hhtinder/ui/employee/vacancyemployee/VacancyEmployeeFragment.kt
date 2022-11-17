package com.andreyyurko.hhtinder.ui.employee.vacancyemployee

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentVacancyEmployeeBinding
import com.andreyyurko.hhtinder.singleton.VocabsSingleton
import com.andreyyurko.hhtinder.utils.date.DatePicker

class VacancyEmployeeFragment : Fragment(R.layout.fragment_vacancy_employee) {

    private val viewBinding by viewBinding(FragmentVacancyEmployeeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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