package com.andreyyurko.hhtinder.ui.employer.vacancyemployer

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentVacancyEmployerBinding
import com.andreyyurko.hhtinder.singleton.TransferSingleton
import com.andreyyurko.hhtinder.singleton.VocabsSingleton
import com.andreyyurko.hhtinder.structures.Vacancy
import com.andreyyurko.hhtinder.utils.date.DatePicker
import com.andreyyurko.hhtinder.utils.network.VacancyHandler
import kotlinx.coroutines.launch

class VacancyEmployerFragment : Fragment(R.layout.fragment_vacancy_employer) {

    private val viewBinding by viewBinding(FragmentVacancyEmployerBinding::bind)

    val vacancyHandler = VacancyHandler()
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

        viewBinding.button.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch { saveVacancy() }
        }

        viewBinding.btnDelete.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch { deleteVacancy() }
        }

        initDatePicker()
        initCategoryPicker()
    }

    fun getVac(): Vacancy {
        var res = Vacancy()
        res.id = vacID
        res.name = viewBinding.iVacancyName.text.toString()
        res.content = viewBinding.iVacancyText.text.toString()
        res.category = viewBinding.categoriesList.selectedItemId.toString()
        res.crUser = TransferSingleton.instance.getUserInfo()!!.userID
        return res
    }

    suspend fun deleteVacancy() {
        var vac = getVac()
        vacancyHandler.deleteVacancy(vac)
    }

    suspend fun saveVacancy() {
        var vac = getVac()
        if (vac.id == -1) {
            vacancyHandler.createVacancy(vac)
        } else {
            vacancyHandler.updateVacancy(vac)
        }
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