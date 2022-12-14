package com.andreyyurko.hhtinder.ui.employer.settingsemployer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentSettingsEmployerBinding
import com.google.android.material.slider.RangeSlider

class SettingsEmployerFragment : Fragment(R.layout.fragment_settings_employer) {
    private val viewBinding by viewBinding(FragmentSettingsEmployerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.cancelParamsButton.setOnClickListener {
            viewBinding.salaryRangeSlider.values = listOf(0F, 200F)
            viewBinding.workExperienceRangeSlider.values = listOf(0F, 20F)
            viewBinding.workingHoursRangeSlider.values = listOf(0F, 60F)
            viewBinding.ageRangeSlider.values = listOf(18F, 60F)
        }

        viewBinding.salaryRangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = viewBinding.salaryRangeSlider.values
                viewBinding.salaryText.text = "Salary, $ / h : ${values[0].toInt()} - ${values[1].toInt()}"
            }
        })

        viewBinding.workExperienceRangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = viewBinding.workExperienceRangeSlider.values
                viewBinding.workExperienceText.text = "Work experience, years : ${values[0].toInt()} - ${values[1].toInt()}"
            }
        })

        viewBinding.workingHoursRangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = viewBinding.workingHoursRangeSlider.values
                viewBinding.workingHoursText.text = "Working hours, h : ${values[0].toInt()} - ${values[1].toInt()}"
            }
        })

        viewBinding.ageRangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = viewBinding.ageRangeSlider.values
                viewBinding.ageText.text = "Age, years old : ${values[0].toInt()} - ${values[1].toInt()}"
            }
        })
    }
}