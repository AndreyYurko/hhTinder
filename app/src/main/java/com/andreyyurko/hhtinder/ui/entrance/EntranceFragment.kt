package com.andreyyurko.hhtinder.ui.entrance

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.andreyyurko.hhtinder.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.databinding.FragmentEntranceBinding
import com.andreyyurko.hhtinder.singleton.SharedPreferencesSingleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntranceFragment : Fragment(R.layout.fragment_entrance) {

    companion object {
        const val LOG_TAG = "Entrance Fragment"
    }

    private val viewBinding by viewBinding(FragmentEntranceBinding::bind)
    private lateinit var viewModel: EntranceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[EntranceViewModel::class.java]

        if (hasToken() && hasLogin()) {
            val controller = findNavController()
            controller.navigate(R.id.signInFragment)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.signInButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.signInFragment)
        }

        viewBinding.gettingStartedButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.signUpFragment)
        }
    }

    fun hasToken(): Boolean {
        SharedPreferencesSingleton.instance.setSingletonContext(this.requireContext())
        var token =
            SharedPreferencesSingleton.instance.getSharedPreferences()!!.getString("token", "")
        return !token.equals("")
    }

    fun hasLogin(): Boolean {
        SharedPreferencesSingleton.instance.setSingletonContext(this.requireContext())
        var login =
            SharedPreferencesSingleton.instance.getSharedPreferences()!!.getString("login", "")
        return !login.equals("")
    }
}