package com.gery.mobile.presentation.dashboard.main.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marsheroly.R
import com.gery.mobile.data.entities.cards.medication.HeroNearYou
import kotlinx.android.synthetic.main.home_hero_near_you.view.*

class HerosNearToYouAdapter(val width: Int) : ListAdapter<HeroNearYou, HerosNearToYouAdapter.HeroNearYouViewHolder>(DIFF_CALLBACK) {


    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HeroNearYou>() {
            override fun areItemsTheSame(oldItem: HeroNearYou, newItem: HeroNearYou): Boolean {
                return oldItem.heroId == newItem.heroId
            }

            override fun areContentsTheSame(oldItem: HeroNearYou, newItem: HeroNearYou): Boolean {
                return checkEquality(oldItem, newItem)
            }

            private fun checkEquality(oldItem: HeroNearYou, newItem: HeroNearYou): Boolean {
                val heroNameMatch = oldItem.heroName == newItem.heroName
                val heroLastnameMatch = oldItem.heroLastname == newItem.heroLastname
                return heroLastnameMatch && heroNameMatch
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroNearYouViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HeroNearYouViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: HeroNearYouViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }


    inner class HeroNearYouViewHolder(inflater: LayoutInflater, var parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.home_hero_near_you, parent, false)) {

        init {
            val cellSize = (width - 64) / 2.5
            itemView.layoutParams.width = cellSize.toInt()
        }

        @SuppressLint("DefaultLocale")
        fun bind(hero: HeroNearYou) {
            itemView.mars_hero_near_you_firstname.text = hero.heroName
            itemView.mars_hero_near_you_lastname.text = hero.heroLastname
        }

    }

}