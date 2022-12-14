package com.andreyyurko.hhtinder.ui.employer.likesemployer

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
import com.andreyyurko.hhtinder.ui.employer.showcv.ShowCvFragment
import com.google.android.material.card.MaterialCardView

class LikesEmployerAdapter(private val likesCardList: List<Card>) :
    RecyclerView.Adapter<LikesEmployerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView = itemView.findViewById<ImageView>(R.id.avatarImageView)
        val cardNameTextView = itemView.findViewById<TextView>(R.id.cardName)
        val cardBodyTextView = itemView.findViewById<TextView>(R.id.cardBody)
        val cardMain: MaterialCardView = itemView.findViewById(R.id.card)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_employee, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardNameTextView.setText(likesCardList.get(position).name)
        holder.cardBodyTextView.setText(likesCardList.get(position).content)
        holder.avatarImageView.setImageDrawable(likesCardList.get(position).image)
        holder.cardMain.setOnClickListener {
            TransferSingleton.instance.setTransferArchive(likesCardList[position])
            it.findNavController().navigate(R.id.action_likesFragment_to_showCvFragment)
            it.findFragment<ShowCvFragment>().setFragmentResult(
                "EmployeeInfo", bundleOf(
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