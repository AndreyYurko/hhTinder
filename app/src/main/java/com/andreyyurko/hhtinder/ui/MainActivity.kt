package com.andreyyurko.hhtinder.ui


import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.ActivityMainBinding
import com.andreyyurko.hhtinder.singleton.SharedPreferencesSingleton
import com.andreyyurko.hhtinder.singleton.VocabsSingleton
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), CoroutineScope {

    private var job: Job = Job()

    companion object {
        const val LOG_TAG = "Main Activity"
    }

    private val viewBinding by viewBinding(ActivityMainBinding::bind)
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        launch {
            VocabsSingleton.instance.initAllVocabs()
        }

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
                            val roleId =
                                SharedPreferencesSingleton.instance.getSharedPreferences()!!
                                    .getInt("role_id", 2);
                            if (roleId == 2) {
                                navController.navigate(R.id.action_EmployerNavGraph)
                            } else if(roleId == 3) {
                                navController.navigate(R.id.action_EmployeeNavGraph)
                            }
                        }
                        AuthHandler.AuthState.NotLogged -> {
                            navController.navigate(R.id.action_guestNavGraph)
                        }
                    }
                }
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}