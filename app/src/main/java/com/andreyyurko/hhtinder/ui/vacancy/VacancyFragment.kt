package com.andreyyurko.hhtinder.ui.vacancy

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentVacancyBinding
import com.andreyyurko.hhtinder.utils.date.DatePicker

class VacancyFragment : Fragment(R.layout.fragment_vacancy) {

    private val viewBinding by viewBinding(FragmentVacancyBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDatePicker()
    }

    fun initDatePicker() {
        var datePicker = DatePicker(viewBinding.editTextDate, context);
        datePicker.initDatePicker()
    }
}