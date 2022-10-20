package com.andreyyurko.hhtinder.ui.registrationapplicant

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentRegistrationApplicantBinding
import com.andreyyurko.hhtinder.ui.entrance.EntranceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationApplicantFragment : Fragment(R.layout.fragment_registration_applicant) {
    companion object {
        const val LOG_TAG = "RegistrationApplicant Fragment"
    }

    private val viewBinding by viewBinding(FragmentRegistrationApplicantBinding::bind)
    private lateinit var viewModel: RegistrationApplicantViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RegistrationApplicantViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.registerButton.setOnClickListener {
            viewModel.auth()
        }
    }
}