package com.andreyyurko.hhtinder.ui.archive

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentArchiveBinding
import com.andreyyurko.hhtinder.databinding.FragmentMainBinding

class ArchiveFragment : Fragment(R.layout.fragment_archive) {

    private val viewBinding by viewBinding(FragmentArchiveBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }


    private fun getArchiveList(): List<Archive> {
        var list = arrayListOf<Archive>()

        list.add(Archive())
        list.add(Archive())

        return return list
    }

    private fun setupRecyclerView(): ArchiveAdapter {
        val recyclerView = viewBinding.recycleView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = ArchiveAdapter(getArchiveList())
        recyclerView.adapter = adapter

        return adapter
    }
}