package com.andreyyurko.hhtinder.ui.employee.likesemployee

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
import com.andreyyurko.hhtinder.ui.employee.showvacancy.ShowVacancyFragment
import com.google.android.material.card.MaterialCardView

class LikesAdapterEmployee(private var likesCardList: List<Card>) :
    RecyclerView.Adapter<LikesAdapterEmployee.ViewHolder>() {

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
        holder.cardNameTextView.setText(likesCardList.get(position).name)
        holder.cardBodyTextView.setText(likesCardList.get(position).content)
        holder.avatarImageView.setImageDrawable(likesCardList.get(position).image)
        holder.cardMain.setOnClickListener {
            TransferSingleton.instance.setTransferArchive(likesCardList.get(position))
            it.findNavController().navigate(R.id.action_likesFragment_to_showVacancyFragment)
            it.findFragment<ShowVacancyFragment>().setFragmentResult(
                "CVInfo", bundleOf(
                    "jobName" to likesCardList[position].name,
                    "id" to likesCardList[position].id,
                    "content" to likesCardList[position].content
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return likesCardList.size
    }
}