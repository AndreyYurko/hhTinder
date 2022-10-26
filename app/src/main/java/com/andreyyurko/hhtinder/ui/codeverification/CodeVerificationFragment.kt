package com.andreyyurko.hhtinder.ui.codeverification

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.andreyyurko.hhtinder.R
import dagger.hilt.android.AndroidEntryPoint
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.databinding.FragmentCodeVerificationBinding

@AndroidEntryPoint
class CodeVerificationFragment : Fragment(R.layout.fragment_code_verification) {

    private val viewBinding by viewBinding(FragmentCodeVerificationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.verifyButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.roleVariantsFragment)
        }
    }
}