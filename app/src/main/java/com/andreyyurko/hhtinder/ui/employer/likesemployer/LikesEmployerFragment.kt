package com.andreyyurko.hhtinder.ui.employer.likesemployer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentCardsEmployeeBinding
import com.andreyyurko.hhtinder.structures.Card
import com.andreyyurko.hhtinder.utils.network.ArchiveHandler
import kotlinx.coroutines.launch

class LikesEmployerFragment : Fragment(R.layout.fragment_cards_employer) {

    private val viewBinding by viewBinding(FragmentCardsEmployeeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            setupRecyclerView()
        }
    }

    private suspend fun getArchiveList(): List<Card> {
        return ArchiveHandler().getArchiveList()
    }

    private suspend fun setupRecyclerView(): LikesEmployerAdapter {
        val recyclerView = viewBinding.recycleView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = LikesEmployerAdapter(getArchiveList())
        recyclerView.adapter = adapter

        return adapter
    }
}