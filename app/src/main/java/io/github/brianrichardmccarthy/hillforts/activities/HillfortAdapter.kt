package io.github.brianrichardmccarthy.hillforts.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.brianrichardmccarthy.hillforts.R
import io.github.brianrichardmccarthy.hillforts.models.HillfortModel
import kotlinx.android.synthetic.main.card_hillfort.view.*

interface HillfortListener {
    fun onHillfortClick(placemark: HillfortModel)
}

class HillfortAdapter constructor(private var placemarks: List<HillfortModel>, private val listener: HillfortListener) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(LayoutInflater.from(parent?.context).inflate(R.layout.card_hillfort, parent, false))
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val placemark = placemarks[holder.adapterPosition]
        holder.bind(placemark, listener)
    }

    override fun getItemCount(): Int = placemarks.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(hillfort: HillfortModel, listener: HillfortListener) {
            itemView.hillfortTitle.text = hillfort.name
            itemView.hillfortDescription.text = hillfort.description
            itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
        }
    }
}