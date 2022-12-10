package com.andreyyurko.hhtinder.ui.employee.archiveemployee

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
import com.google.android.material.card.MaterialCardView

class ArchiveAdapterEmployee(private var archiveCardList: List<Card>) :
    RecyclerView.Adapter<ArchiveAdapterEmployee.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        val cardNameTextView: TextView = itemView.findViewById(R.id.cardName)
        val cardBodyTextView: TextView = itemView.findViewById(R.id.cardBody)
        val cardMain: MaterialCardView = itemView.findViewById(R.id.card)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_archive_employee, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardNameTextView.setText(archiveCardList.get(position).name)
        holder.cardBodyTextView.setText(archiveCardList.get(position).content)
        holder.avatarImageView.setImageDrawable(archiveCardList.get(position).image)
        holder.cardMain.setOnClickListener {
            TransferSingleton.instance.setTransferArchive(archiveCardList.get(position))
            it.findNavController().navigate(R.id.action_archiveFragment_to_employeeArchiveCardFragment)
            it.findFragment<ArchiveEmployeeFragment>().setFragmentResult(
                "EmployeeInfo", bundleOf(
                    "jobName" to archiveCardList[position].name,
                    "id" to archiveCardList[position].id,
                    "content" to archiveCardList[position].content
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return archiveCardList.size
    }
}