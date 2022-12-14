package com.andreyyurko.hhtinder.ui.employee.profileemployee

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentProfileEmployeeBinding
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileEmployeeFragment : Fragment(R.layout.fragment_profile_employee) {

    @Inject
    lateinit var authHandler: AuthHandler

    private val viewBinding by viewBinding(FragmentProfileEmployeeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.logOutButton.setOnClickListener {
            authHandler.logOut()
        }

        viewBinding.saveButton.setOnClickListener {
            Toast.makeText(requireContext(), "Ваш профиль сохранен! Откройте меню, чтобы начать.", Toast.LENGTH_LONG).show()
        }
    }
}