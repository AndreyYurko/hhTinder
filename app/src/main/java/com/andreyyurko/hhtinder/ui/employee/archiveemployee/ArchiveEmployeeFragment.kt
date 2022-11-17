package com.andreyyurko.hhtinder.ui.employee.archiveemployee

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentArchiveEmployeeBinding
import com.andreyyurko.hhtinder.structures.Archive
import com.andreyyurko.hhtinder.ui.employee.archiveemployee.ArchiveAdapterEmployee
import com.andreyyurko.hhtinder.ui.employee.archiveemployee.ArchiveEmployee
import com.andreyyurko.hhtinder.utils.network.ArchiveHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ArchiveEmployeeFragment : Fragment(R.layout.fragment_archive_employee), CoroutineScope {

    private var job: Job = Job()

    private val viewBinding by viewBinding(FragmentArchiveEmployeeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch { setupRecyclerView() }
    }


    private suspend fun getArchiveList(): List<Archive> {
        return ArchiveHandler().getArchiveList()
    }

    private suspend fun setupRecyclerView(): ArchiveAdapterEmployee {
        val recyclerView = viewBinding.recycleView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = ArchiveAdapterEmployee(getArchiveList())
        recyclerView.adapter = adapter

        return adapter
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
}