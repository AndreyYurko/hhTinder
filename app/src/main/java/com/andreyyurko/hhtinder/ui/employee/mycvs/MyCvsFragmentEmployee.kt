package com.andreyyurko.hhtinder.ui.employee.mycvs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.andreyyurko.hhtinder.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.databinding.FragmentMyCvsBinding
import com.andreyyurko.hhtinder.structures.Card
import com.andreyyurko.hhtinder.ui.employee.archiveemployee.ArchiveAdapterEmployee
import com.andreyyurko.hhtinder.utils.network.ArchiveHandler
import kotlinx.coroutines.launch

class MyCvsFragmentEmployee : Fragment(R.layout.fragment_my_cvs) {

    private val viewBinding by viewBinding(FragmentMyCvsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            setupRecyclerView()
        }
    }

    private suspend fun getArchiveList(): List<Card> {
        return ArchiveHandler().getArchiveList()
    }

    private suspend fun setupRecyclerView(): ArchiveAdapterEmployee {
        val recyclerView = viewBinding.recycleView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        // TODO: переделать с архивов на что-то нормальное
        val adapter = ArchiveAdapterEmployee(getArchiveList())
        recyclerView.adapter = adapter

        return adapter
    }

}