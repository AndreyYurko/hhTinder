package com.andreyyurko.hhtinder.ui.entrance

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.andreyyurko.hhtinder.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.databinding.FragmentEntranceBinding
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.gildor.coroutines.okhttp.await
import javax.inject.Inject

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

        viewBinding.logInButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.logInFragment)
        }

        viewBinding.registerInButton.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.registrationVariantsFragment)
        }
    }
}