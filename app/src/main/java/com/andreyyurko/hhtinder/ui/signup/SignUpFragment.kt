package com.andreyyurko.hhtinder.ui.signup

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.andreyyurko.hhtinder.R
import dagger.hilt.android.AndroidEntryPoint
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.databinding.FragmentSignUpBinding

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private val viewBinding by viewBinding(FragmentSignUpBinding::bind)

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