package com.andreyyurko.hhtinder.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentLogInBinding
import com.andreyyurko.hhtinder.ui.registrationapplicant.RegistrationApplicantViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LogInFragment : Fragment(R.layout.fragment_log_in) {
    companion object {
        const val LOG_TAG = "LogIn Fragment"
    }

    private val viewBinding by viewBinding(FragmentLogInBinding::bind)
    private lateinit var viewModel: LogInViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[LogInViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.logInButton.setOnClickListener {
            viewModel.auth()
        }
    }
}