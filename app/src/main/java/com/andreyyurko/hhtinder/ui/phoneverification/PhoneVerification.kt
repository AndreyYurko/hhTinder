package com.andreyyurko.hhtinder.ui.phoneverification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentPhoneVerificationBinding
import dagger.hilt.android.AndroidEntryPoint
import by.kirich1409.viewbindingdelegate.viewBinding

@AndroidEntryPoint
class PhoneVerification : Fragment(R.layout.fragment_phone_verification) {

    private val viewBinding by viewBinding(FragmentPhoneVerificationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.sendButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.codeVerificationFragment)
        }
    }
}