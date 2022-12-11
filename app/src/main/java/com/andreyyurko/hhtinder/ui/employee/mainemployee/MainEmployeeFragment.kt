package com.andreyyurko.hhtinder.ui.employee.mainemployee

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentMainEmployeeBinding
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter

@AndroidEntryPoint
class MainEmployeeFragment : Fragment(R.layout.fragment_main_employee) {

    private val viewBinding by viewBinding(FragmentMainEmployeeBinding::bind)
    private lateinit var viewModel: MainEmployeeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainEmployeeViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.toolbar.setNavigationOnClickListener {
            if (viewBinding.drawerLayout.isOpen) {
                viewBinding.drawerLayout.closeDrawer(GravityCompat.START)
            }
            else {
                viewBinding.drawerLayout.openDrawer(
                    GravityCompat.START
                )
            }
        }

        viewBinding.appbar.applyInsetter {
            type(statusBars = true) { margin() }
        }
        viewBinding.drawerLayout.applyInsetter {
            type(statusBars = true) { margin() }
        }

        val navController =
            (childFragmentManager.findFragmentById(R.id.mainFragmentNavigationHost) as NavHostFragment).navController
        viewBinding.nvView.setupWithNavController(navController)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("Vacan", navController.backQueue.size.toString())
                    if (navController.backQueue.size > 4) navController.popBackStack()
                }
            }
        )

        viewBinding.toolbar.setOnMenuItemClickListener {
            navController.navigate(R.id.profileEmployeeFragment)
            true
        }
/*
        viewBinding.menuImageButton.setOnClickListener() {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
        }*/
    }
}