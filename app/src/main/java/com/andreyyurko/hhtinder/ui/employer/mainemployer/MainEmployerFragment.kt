package com.andreyyurko.hhtinder.ui.employer.mainemployer

import android.os.Bundle
import android.view.View
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
            viewBinding.drawerLayout.openDrawer(
                GravityCompat.START
            )
        }

        viewBinding.toolbar.setBackgroundColor(resources.getColor(R.color.deep_blue))
        activity?.window?.statusBarColor = resources.getColor(R.color.deep_blue)

        viewBinding.mainFragmentNavigationHost.applyInsetter {
            type(statusBars = true) { margin() }
        }

        val navController =
            (childFragmentManager.findFragmentById(R.id.mainFragmentNavigationHost) as NavHostFragment).navController
        viewBinding.nvView.setupWithNavController(navController)
/*
        viewBinding.menuImageButton.setOnClickListener() {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
        }*/
    }
}