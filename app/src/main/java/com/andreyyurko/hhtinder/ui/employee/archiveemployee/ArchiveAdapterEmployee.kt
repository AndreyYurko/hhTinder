package com.andreyyurko.hhtinder.ui.employee.archiveemployee

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.findFragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.andreyyurko.hhtinder.R
import com.andreyyurko.hhtinder.structures.Archive
import com.google.android.material.card.MaterialCardView

class ArchiveAdapterEmployee(archiveList: List<Archive>) :
    RecyclerView.Adapter<ArchiveAdapterEmployee.ViewHolder>() {

    var archiveList: List<Archive> = archiveList

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        val cardNameTextView: TextView = itemView.findViewById(R.id.cardName)
        val cardBodyTextView: TextView = itemView.findViewById(R.id.cardBody)
        val cardMain: MaterialCardView = itemView.findViewById(R.id.card)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_archive, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardNameTextView.setText(archiveList.get(position).name)
        holder.cardBodyTextView.setText(archiveList.get(position).content)
        holder.avatarImageView.setImageDrawable(archiveList.get(position).image)
        holder.cardMain.setOnClickListener {
            it.findNavController().navigate(R.id.action_archiveFragment_to_employeeArchiveCardFragment)

            it.findFragment<ArchiveEmployeeFragment>().setFragmentResult(
                "EmployeeInfo", bundleOf(
                    "jobName" to archiveList[position].name,
                    "id" to archiveList[position].id
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return archiveList.size
    }
}