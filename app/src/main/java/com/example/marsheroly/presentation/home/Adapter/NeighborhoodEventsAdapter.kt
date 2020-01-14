package com.gery.mobile.presentation.dashboard.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marsheroly.R
import com.example.marsheroly.common.extensions.loadRoundedPicture
import com.gery.mobile.data.entities.cards.medication.NeighborhoodEvent
import kotlinx.android.synthetic.main.home_neighborhood_events_card.view.*

class NeighborhoodEventsAdapter() : ListAdapter<NeighborhoodEvent, NeighborhoodEventsAdapter.NeighborhoodViewHolder>(DIFF_CALLBACK) {


    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NeighborhoodEvent>() {
            override fun areItemsTheSame(oldItem: NeighborhoodEvent, newItem: NeighborhoodEvent): Boolean {
                return oldItem.neighborhoodEventId == newItem.neighborhoodEventId
            }

            override fun areContentsTheSame(oldItem: NeighborhoodEvent, newItem: NeighborhoodEvent): Boolean {
                return checkEquality(oldItem, newItem)
            }

            private fun checkEquality(oldItem: NeighborhoodEvent, newItem: NeighborhoodEvent): Boolean {
                val titleMatch = oldItem.title == newItem.title
                val descriptionMatch = oldItem.description == newItem.description
                val imageSrcMatch = oldItem.imageSrc == newItem.imageSrc
                return titleMatch && descriptionMatch && imageSrcMatch
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NeighborhoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NeighborhoodViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: NeighborhoodViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }


    inner class NeighborhoodViewHolder(inflater: LayoutInflater, var parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.home_neighborhood_events_card, parent, false)) {


        @SuppressLint("DefaultLocale")
        fun bind(neighborhoodEvent: NeighborhoodEvent) {
            itemView.neighborhood_event_title.text = neighborhoodEvent.title
            itemView.neighborhood_event_description.text = neighborhoodEvent.description
            itemView.neighborhood_event_image_view.loadRoundedPicture(neighborhoodEvent.imageSrc, radius = 10)
        }

    }

}