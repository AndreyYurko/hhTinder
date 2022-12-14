package com.andreyyurko.hhtinder.ui.employee.settingsemployee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentSettingsEmployeeBinding
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider

class SettingsEmployeeFragment : Fragment(R.layout.fragment_settings_employee) {
    private val viewBinding by viewBinding(FragmentSettingsEmployeeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.cancelParamsButton.setOnClickListener {
            viewBinding.salaryRangeSlider.values = listOf(0F, 200F)
            viewBinding.workExperienceRangeSlider.value = 0F
            viewBinding.workingHoursRangeSlider.values = listOf(0F, 60F)
        }

        viewBinding.salaryRangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = viewBinding.salaryRangeSlider.values
                viewBinding.salaryText.text = "Salary, $ / h : ${values[0].toInt()} - ${values[1].toInt()}"
            }
        })

        viewBinding.workExperienceRangeSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {}

            override fun onStopTrackingTouch(slider: Slider) {
                val value = viewBinding.workExperienceRangeSlider.value
                viewBinding.workExperienceText.text = "Work experience, years : at least ${value.toInt()}"
            }
        })

        viewBinding.workingHoursRangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: RangeSlider) {}

            override fun onStopTrackingTouch(slider: RangeSlider) {
                val values = viewBinding.workingHoursRangeSlider.values
                viewBinding.workingHoursText.text = "Working hours, h : ${values[0].toInt()} - ${values[1].toInt()}"
            }
        })
    }
}