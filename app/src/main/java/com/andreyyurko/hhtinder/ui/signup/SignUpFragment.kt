package com.andreyyurko.hhtinder.ui.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentSignUpBinding
import com.andreyyurko.hhtinder.utils.handlers.ProfileHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

    @Inject
    lateinit var profileHandler: ProfileHandler

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.signUpButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.phoneVerificationFragment)
        }

        viewBinding.signInButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.signInFragment)
        }
    }
}