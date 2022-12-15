package com.andreyyurko.hhtinder.ui.employer.myvacancies

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.findFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.singleton.TransferSingleton
import com.andreyyurko.hhtinder.structures.Card
import com.andreyyurko.hhtinder.ui.employer.vacancyemployer.VacancyEmployerFragment
import com.google.android.material.card.MaterialCardView

class MyVacanciesAdapter(private var vacanciesCardList: MutableList<Card>) :
    RecyclerView.Adapter<MyVacanciesAdapter.ViewHolder>() {

    val TAG = "VacancyAdapter"

    fun addCard(card: Card) {
        vacanciesCardList.add(card)
        Log.d(TAG, vacanciesCardList.size.toString())
    }

    fun getCards(): List<Card> {
        return vacanciesCardList
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        val cardNameTextView: TextView = itemView.findViewById(R.id.cardName)
        val cardBodyTextView: TextView = itemView.findViewById(R.id.cardBody)
        val cardMain: MaterialCardView = itemView.findViewById(R.id.card)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_employer, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardNameTextView.text = vacanciesCardList[position].name
        holder.cardBodyTextView.text = vacanciesCardList[position].content
        holder.avatarImageView.setImageDrawable(vacanciesCardList[position].image)
        holder.cardMain.setOnClickListener {
            TransferSingleton.instance.setTransferArchive(vacanciesCardList[position])
            it.findNavController().navigate(R.id.action_myVacanciesFragment_to_vacancyFragment)
            it.findFragment<VacancyEmployerFragment>().setFragmentResult(
                "EmployerInfo", bundleOf(
                    "jobName" to vacanciesCardList[position].name,
                    "id" to vacanciesCardList[position].id,
                    "content" to vacanciesCardList[position].content
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return vacanciesCardList.size
    }
}