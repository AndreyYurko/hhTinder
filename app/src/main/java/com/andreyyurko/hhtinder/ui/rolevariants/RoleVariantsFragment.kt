package com.andreyyurko.hhtinder.ui.rolevariants

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.andreyyurko.hhtinder.R
import dagger.hilt.android.AndroidEntryPoint
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.databinding.FragmentRoleVariantsBinding
import com.andreyyurko.hhtinder.ui.signin.SignInViewModel

@AndroidEntryPoint
class RoleVariantsFragment : Fragment(R.layout.fragment_role_variants) {

    private val viewBinding by viewBinding(FragmentRoleVariantsBinding::bind)
    private lateinit var viewModel: RoleVariantsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RoleVariantsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.employeeButton.setOnClickListener {
            viewModel.auth()
        }

        viewBinding.employerButton.setOnClickListener {
            viewModel.auth()
        }
    }
}