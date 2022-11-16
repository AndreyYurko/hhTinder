package com.andreyyurko.hhtinder.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentMainBinding
import dev.chrisbanes.insetter.applyInsetter


class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewBinding by viewBinding(FragmentMainBinding::bind)

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