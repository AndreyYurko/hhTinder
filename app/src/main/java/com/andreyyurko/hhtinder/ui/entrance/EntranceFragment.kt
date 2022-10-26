package com.andreyyurko.hhtinder.ui.entrance

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.andreyyurko.hhtinder.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.databinding.FragmentEntranceBinding
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
}