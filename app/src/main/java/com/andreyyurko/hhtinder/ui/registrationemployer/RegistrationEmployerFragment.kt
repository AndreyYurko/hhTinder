package com.andreyyurko.hhtinder.ui.registrationemployer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentRegistrationEmployerBinding
import com.andreyyurko.hhtinder.ui.registrationapplicant.RegistrationApplicantViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationEmployerFragment : Fragment(R.layout.fragment_registration_employer) {
    companion object {
        const val LOG_TAG = "RegistrationEmployer Fragment"
    }

    private val viewBinding by viewBinding(FragmentRegistrationEmployerBinding::bind)
    private lateinit var viewModel: RegistrationEmployerViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegistrationEmployerViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.registerButton.setOnClickListener {
            viewModel.auth()
        }
    }

}