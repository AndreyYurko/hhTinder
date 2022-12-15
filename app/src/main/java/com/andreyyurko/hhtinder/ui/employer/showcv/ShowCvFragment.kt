package com.andreyyurko.hhtinder.ui.employer.showcv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentShowCvBinding
import com.andreyyurko.hhtinder.singleton.TransferSingleton

class ShowCvFragment : Fragment(R.layout.fragment_show_cv){
    private val viewBinding by viewBinding(FragmentShowCvBinding::bind)

    private var cvId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            viewBinding.cvName.text = TransferSingleton.instance.getTransferArchive()!!.name
            viewBinding.cvText.text = TransferSingleton.instance.getTransferArchive()!!.content
            cvId = TransferSingleton.instance.getTransferArchive()!!.id
            viewBinding.avatarImageView.setImageDrawable(TransferSingleton.instance.getTransferArchive()!!.image)
        } catch (e: Exception) {
            e.stackTrace
        }
    }
}