package com.andreyyurko.hhtinder.ui.employee.archiveemployee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentArchiveEmployeeBinding
import com.andreyyurko.hhtinder.ui.employee.archiveemployee.ArchiveAdapterEmployee
import com.andreyyurko.hhtinder.ui.employee.archiveemployee.ArchiveEmployee

class ArchiveEmployeeFragment : Fragment(R.layout.fragment_archive_employee) {

    private val viewBinding by viewBinding(FragmentArchiveEmployeeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }


    private fun getArchiveList(): List<ArchiveEmployee> {
        var list = arrayListOf<ArchiveEmployee>()

        list.add(ArchiveEmployee())
        list.add(ArchiveEmployee())

        return return list
    }

    private fun setupRecyclerView(): ArchiveAdapterEmployee {
        val recyclerView = viewBinding.recycleView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = ArchiveAdapterEmployee(getArchiveList())
        recyclerView.adapter = adapter

        return adapter
    }
}