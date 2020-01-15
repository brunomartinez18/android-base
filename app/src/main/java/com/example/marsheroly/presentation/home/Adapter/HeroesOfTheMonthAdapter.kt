package com.example.marsheroly.presentation.home.Adapter

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.marsheroly.R
import com.example.marsheroly.common.extensions.loadRoundedPicture
import com.example.marsheroly.common.extensions.loadRoundedPictureWithPlaceholder
import com.gery.mobile.data.entities.cards.medication.HeroeOfTheMonth
import kotlinx.android.synthetic.main.home_hero_near_you_cell.view.*
import kotlinx.android.synthetic.main.home_heroes_of_the_month_cell.view.*
import kotlinx.android.synthetic.main.home_need_hero_alert.view.*

class HeroesOfTheMonthAdapter() : ListAdapter<HeroeOfTheMonth, HeroesOfTheMonthAdapter.HeroeOfTheMonthViewHolder>(DIFF_CALLBACK) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HeroeOfTheMonth>() {
            override fun areItemsTheSame(oldItem: HeroeOfTheMonth, newItem: HeroeOfTheMonth): Boolean {
                return oldItem.heroId == newItem.heroId
            }

            override fun areContentsTheSame(oldItem: HeroeOfTheMonth, newItem: HeroeOfTheMonth): Boolean {
                return checkEquality(oldItem, newItem)
            }

            private fun checkEquality(oldItem: HeroeOfTheMonth, newItem: HeroeOfTheMonth): Boolean {
                val heroNameMatch = oldItem.heroName == newItem.heroName
                val heroLastnameMatch = oldItem.heroLastname == newItem.heroLastname
                val heroContributionMatch = oldItem.contributionToShelter == newItem.contributionToShelter
                return heroLastnameMatch && heroNameMatch && heroContributionMatch
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroeOfTheMonthViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HeroeOfTheMonthViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: HeroeOfTheMonthViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }


    inner class HeroeOfTheMonthViewHolder(inflater: LayoutInflater, var parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.home_heroes_of_the_month_cell, parent, false)) {

        @SuppressLint("DefaultLocale")
        fun bind(hero: HeroeOfTheMonth) {
            val adapterPositionStr = "#$adapterPosition"
            val heroName = "${hero.heroName}"
            val heroOfTheMonthName = SpannableString("#$adapterPosition ${hero.heroName} ${hero.heroLastname}")
            heroOfTheMonthName.setSpan(StyleSpan(Typeface.BOLD), adapterPositionStr.length, adapterPositionStr.length + heroName.length + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

            itemView.hero_of_the_month_name.text = heroOfTheMonthName
            itemView.hero_of_the_month_contribution_text_view.text = "Contribution to shelters: ${hero.contributionToShelter}"
            itemView.hero_of_the_month_image.getPictureImageView().loadRoundedPictureWithPlaceholder(R.drawable.ic_base_avatar, 10)
        }

    }

}