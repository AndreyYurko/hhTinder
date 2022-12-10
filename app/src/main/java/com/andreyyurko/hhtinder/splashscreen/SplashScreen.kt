package com.andreyyurko.hhtinder.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.singleton.SharedPreferencesSingleton
import com.andreyyurko.hhtinder.singleton.VocabsSingleton
import com.andreyyurko.hhtinder.ui.MainActivity
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreen : AppCompatActivity(R.layout.activity_splash_screen) {

    private val viewBinding by viewBinding(ActivitySplashScreenBinding::bind)
    private lateinit var viewModel: SplashScreenViewModel

    private val endSplashMills: Long = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SplashScreenViewModel::class.java]

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        lifecycleScope.launch {
            VocabsSingleton.instance.initAllVocabs()
        }

        SharedPreferencesSingleton.instance.setSingletonContext(this.baseContext)
        tryLoginViaToken()

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, endSplashMills)
    }

    private fun tryLoginViaToken() {
        val token =
            SharedPreferencesSingleton.instance.getSharedPreferences()!!.getString("token", "")
        val login =
            SharedPreferencesSingleton.instance.getSharedPreferences()!!.getString("login", "")

        viewModel.auth(login, "pass", token)
    }
}