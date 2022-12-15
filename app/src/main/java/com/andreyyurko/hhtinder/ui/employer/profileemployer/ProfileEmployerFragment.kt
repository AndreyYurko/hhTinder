package com.andreyyurko.hhtinder.ui.employer.profileemployer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentProfileEmployeeBinding
import com.andreyyurko.hhtinder.singleton.SharedPreferencesSingleton
import com.andreyyurko.hhtinder.structures.Profile
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import com.andreyyurko.hhtinder.utils.network.ProfileHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileEmployerFragment : Fragment(R.layout.fragment_profile_employer) {
    @Inject
    lateinit var authHandler: AuthHandler

    private val viewBinding by viewBinding(FragmentProfileEmployeeBinding::bind)

    private var profile: Profile? = null

    private val profileHandler = ProfileHandler()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.logOutButton.setOnClickListener {
            authHandler.logOut()
        }

        viewBinding.saveButton.setOnClickListener {
            Toast.makeText(requireContext(), "Ваш профиль сохранен! Откройте меню, чтобы начать.", Toast.LENGTH_LONG).show()
        }
        viewLifecycleOwner.lifecycleScope.launch { loadUserInfo() }

    }

    private suspend fun loadUserInfo() {
        val login = SharedPreferencesSingleton.instance.getSharedPreferences()!!
            .getString("login", "admin");
        profile = profileHandler.getUserInfo(login!!)
        drawUserInfo()
    }

    private fun drawUserInfo() {
        if (profile == null) {
            return
        }

        viewBinding.name.setText(profile!!.name)
        viewBinding.surname.setText(profile!!.surname)

    }
}