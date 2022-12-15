package com.andreyyurko.hhtinder.ui.employer.myvacancies

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.databinding.FragmentMyVacanciesBinding
import com.andreyyurko.hhtinder.structures.Card
import com.andreyyurko.hhtinder.utils.network.ArchiveHandler
import kotlinx.coroutines.launch

class MyVacanciesFragment : Fragment(R.layout.fragment_my_vacancies) {
    private val viewBinding by viewBinding(FragmentMyVacanciesBinding::bind)

    var adapter: MyVacanciesAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            setupRecyclerView()
        }

        viewBinding.btnCreateVac.setOnClickListener {
            if (adapter != null) {
                adapter!!.addCard(Card(-1, "Name", "Content"))
                adapter!!.notifyDataSetChanged()
            }
        }
    }

    private suspend fun getArchiveList(): List<Card> {
        return ArchiveHandler().getArchiveList()
    }

    private suspend fun setupRecyclerView() {
        val recyclerView = viewBinding.recycleView
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        // TODO: переделать с архивов на что-то нормальное
        val res = getArchiveList().toMutableList()
        adapter = MyVacanciesAdapter(res)
        recyclerView.adapter = adapter
    }
}