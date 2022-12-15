package com.andreyyurko.hhtinder.ui.employee.cvemployee

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentCvEmployeeBinding
import com.andreyyurko.hhtinder.singleton.TransferSingleton
import com.andreyyurko.hhtinder.singleton.VocabsSingleton
import com.andreyyurko.hhtinder.structures.CV
import com.andreyyurko.hhtinder.utils.date.DatePicker
import com.andreyyurko.hhtinder.utils.network.CVHandler
import com.andreyyurko.hhtinder.utils.network.CVHandler_Factory
import kotlinx.coroutines.launch

class CVEmployeeFragment : Fragment(R.layout.fragment_cv_employee) {

    private val viewBinding by viewBinding(FragmentCvEmployeeBinding::bind)

    private val cvHandler = CVHandler()

    private var cvId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initDatePicker()
        initCategoryPicker()

        try {
            viewBinding.cvName.setText(TransferSingleton.instance.getTransferArchive()!!.name)
            viewBinding.cvText.setText(TransferSingleton.instance.getTransferArchive()!!.content)
            cvId = TransferSingleton.instance.getTransferArchive()!!.id
            viewBinding.avatarImageView.setImageDrawable(TransferSingleton.instance.getTransferArchive()!!.image)
        } catch (e: Exception) {
            e.stackTrace
        }

        viewBinding.button.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch { saveCV() }
        }
    }

    private fun getCV(): CV {
        var res = CV()

        res.id = cvId
        res.name = viewBinding.cvName.text.toString()
        res.content = viewBinding.cvText.text.toString()
        res.education = viewBinding.educationText.text.toString()
        res.experience = viewBinding.experienceText.text.toString()
        res.category = viewBinding.categoriesList.selectedItemId.toString()
        res.salary = Integer.parseInt(viewBinding.salary.text.toString())

        return res
    }

    private suspend fun saveCV() {
        var cv = getCV()
        if (cv.id == -1) {
            cvId = cvHandler.createCV(cv)
        } else {
            cvHandler.saveCV(cv)
        }
    }

    private fun initDatePicker() {
        val datePicker = DatePicker(viewBinding.editTextDate, context);
        datePicker.initDatePicker()
    }

    private fun initCategoryPicker() {
        viewBinding.categoriesList.adapter = ArrayAdapter(
            this.requireContext(),
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            VocabsSingleton.instance.getJobCatVocab()
        )
    }
}