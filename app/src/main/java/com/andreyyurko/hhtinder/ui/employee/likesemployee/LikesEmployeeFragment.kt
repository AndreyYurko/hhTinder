package com.andreyyurko.hhtinder.ui.employee.likesemployee

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
import com.andreyyurko.hhtinder.utils.network.AuthHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LikesEmployeeFragment : Fragment(R.layout.fragment_cards_employee) {

    private val viewBinding by viewBinding(FragmentCardsEmployeeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch { setupRecyclerView() }
    }


    private suspend fun getArchiveList(): List<Card> {
        return ArchiveHandler().getArchiveList()
    }

    private suspend fun setupRecyclerView(): LikesAdapterEmployee {
        val recyclerView = viewBinding.recycleView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val res = getArchiveList().toMutableList()
        res.add(Card(
            id = 10,
            name = "Имя",
            content = "Какой-то контент"
        ))
        val adapter = LikesAdapterEmployee(res)
        recyclerView.adapter = adapter

        return adapter
    }
}