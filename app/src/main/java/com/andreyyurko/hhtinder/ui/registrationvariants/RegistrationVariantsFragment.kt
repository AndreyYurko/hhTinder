package com.andreyyurko.hhtinder.ui.registrationvariants

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentRegistrationVariantsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationVariantsFragment : Fragment(R.layout.fragment_registration_variants) {
    companion object {
        const val LOG_TAG = "RegistrationVariants Fragment"
    }

    private val viewBinding by viewBinding(FragmentRegistrationVariantsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.registerAsApplicantButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.registrationApplicantFragment)
        }

        viewBinding.registerAsEmployerButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.registrationEmployerFragment)
        }
    }
}