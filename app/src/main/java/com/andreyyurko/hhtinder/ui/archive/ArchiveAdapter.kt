package com.andreyyurko.hhtinder.ui.archive

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.andreyyurko.hhtinder.R

class ArchiveAdapter(archiveList: List<Archive>) :
    RecyclerView.Adapter<ArchiveAdapter.ViewHolder>() {

    var archiveList: List<Archive> = archiveList

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView = itemView.findViewById<ImageView>(R.id.avatarImageView)
        val cardNameTextView = itemView.findViewById<TextView>(R.id.cardName)
        val cardBodyTextView = itemView.findViewById<TextView>(R.id.cardBody)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_archive, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardNameTextView.setText(archiveList.get(position).name)
        holder.cardBodyTextView.setText(archiveList.get(position).body)
    }

    override fun getItemCount(): Int {
        return archiveList.size
    }
}