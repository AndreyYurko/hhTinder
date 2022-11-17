package com.andreyyurko.hhtinder.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.ActivityMainBinding
import com.andreyyurko.hhtinder.singleton.SharedPreferencesSingleton
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        const val LOG_TAG = "Main Activity"
    }

    private val viewBinding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SharedPreferencesSingleton.instance.setSingletonContext(this.baseContext)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainActivityNavigationHost) as NavHostFragment
        val navController = navHostFragment.navController
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.authStateFlow().collect {
                    Log.d(LOG_TAG, "change log state $it")
                    when (it) {
                        AuthHandler.AuthState.Logged -> {
                            navController.navigate(R.id.action_RegisteredUserNavGraph)
                        }
                        AuthHandler.AuthState.NotLogged -> {
                            navController.navigate(R.id.action_guestNavGraph)
                        }
                    }
                }
            }
        }
    }
}