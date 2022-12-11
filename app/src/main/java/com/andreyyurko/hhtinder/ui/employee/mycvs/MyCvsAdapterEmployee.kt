package com.andreyyurko.hhtinder.ui.employee.mycvs

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
import com.andreyyurko.hhtinder.ui.employee.cvemployee.CVEmployeeFragment
import com.google.android.material.card.MaterialCardView

class MyCvsAdapterEmployee(private var cvsCardList: List<Card>) :
    RecyclerView.Adapter<MyCvsAdapterEmployee.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatarImageView: ImageView = itemView.findViewById(R.id.avatarImageView)
        val cardNameTextView: TextView = itemView.findViewById(R.id.cardName)
        val cardBodyTextView: TextView = itemView.findViewById(R.id.cardBody)
        val cardMain: MaterialCardView = itemView.findViewById(R.id.card)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_employee, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.cardNameTextView.text = cvsCardList[position].name
        holder.cardBodyTextView.text = cvsCardList[position].content
        holder.avatarImageView.setImageDrawable(cvsCardList[position].image)
        holder.cardMain.setOnClickListener {
            TransferSingleton.instance.setTransferArchive(cvsCardList[position])
            it.findNavController().navigate(R.id.action_myCVsFragment_to_cvFragment)
            it.findFragment<CVEmployeeFragment>().setFragmentResult(
                "EmployeeInfo", bundleOf(
                    "jobName" to cvsCardList[position].name,
                    "id" to cvsCardList[position].id,
                    "content" to cvsCardList[position].content
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return cvsCardList.size
    }
}