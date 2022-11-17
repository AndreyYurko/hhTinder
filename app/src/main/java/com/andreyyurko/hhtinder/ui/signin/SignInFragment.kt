package com.andreyyurko.hhtinder.ui.signin

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentSignInBinding
import com.andreyyurko.hhtinder.singleton.SharedPreferencesSingleton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {
    companion object {
        const val LOG_TAG = "LogIn Fragment"
    }

    private val viewBinding by viewBinding(FragmentSignInBinding::bind)
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        tryLoginViaToken()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.signInButton.setOnClickListener {
            val login = viewBinding.emailEditText.text.toString()
            val pass = viewBinding.passwordEditText.text.toString()

            viewModel.auth(login, pass, "")
        }
    }

    fun tryLoginViaToken() {
        val token =
            SharedPreferencesSingleton.instance.getSharedPreferences()!!.getString("token", "")
        val login =
            SharedPreferencesSingleton.instance.getSharedPreferences()!!.getString("login", "")

        viewModel.auth(login, "pass", token)
    }
}