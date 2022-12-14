package com.andreyyurko.hhtinder.ui.employer.mainemployer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentMainEmployerBinding
import dev.chrisbanes.insetter.applyInsetter


class MainEmployerFragment : Fragment(R.layout.fragment_main_employer) {

    private val viewBinding by viewBinding(FragmentMainEmployerBinding::bind)

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

        viewBinding.toolbar.setBackgroundColor(resources.getColor(R.color.deep_blue))
        activity?.window?.statusBarColor = resources.getColor(R.color.deep_blue)

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
            navController.navigate(R.id.profileEmployerFragment)
            true
        }
/*
        viewBinding.menuImageButton.setOnClickListener() {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
        }*/
    }
}