package com.andreyyurko.hhtinder.ui.employee.mainemployee

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentMainEmployeeBinding
import dev.chrisbanes.insetter.applyInsetter


class MainEmployeeFragment : Fragment(R.layout.fragment_main_employee) {

    private val viewBinding by viewBinding(FragmentMainEmployeeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.toolbar.setNavigationOnClickListener {
            viewBinding.drawerLayout.openDrawer(
                GravityCompat.START
            )
        }

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