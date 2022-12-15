package com.andreyyurko.hhtinder.ui.employer.vacancyemployer

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentVacancyEmployerBinding
import com.andreyyurko.hhtinder.singleton.TransferSingleton
import com.andreyyurko.hhtinder.singleton.VocabsSingleton
import com.andreyyurko.hhtinder.utils.date.DatePicker

class VacancyEmployerFragment : Fragment(R.layout.fragment_vacancy_employer) {

    private val viewBinding by viewBinding(FragmentVacancyEmployerBinding::bind)

    var vacID = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            viewBinding.iVacancyName.setText(TransferSingleton.instance.getTransferArchive()!!.name)
            viewBinding.iVacancyText.setText(TransferSingleton.instance.getTransferArchive()!!.content)
            vacID = TransferSingleton.instance.getTransferArchive()!!.id
            viewBinding.avatarImageView.setImageDrawable(TransferSingleton.instance.getTransferArchive()!!.image)
        } catch (e: Exception) {
            e.stackTrace
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