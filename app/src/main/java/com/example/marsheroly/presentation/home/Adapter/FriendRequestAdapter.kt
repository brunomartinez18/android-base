package com.gery.mobile.presentation.dashboard.main.adapters

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
import com.gery.mobile.data.entities.cards.medication.FriendRequest
import kotlinx.android.synthetic.main.home_need_hero_alert.view.*

class FriendRequestAdapter() : ListAdapter<FriendRequest, FriendRequestAdapter.FriendRequestViewHolder>(DIFF_CALLBACK) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FriendRequest>() {
            override fun areItemsTheSame(oldItem: FriendRequest, newItem: FriendRequest): Boolean {
                return checkEquality(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItem: FriendRequest, newItem: FriendRequest): Boolean {
                return checkEquality(oldItem, newItem)
            }

            private fun checkEquality(oldItem: FriendRequest, newItem: FriendRequest): Boolean {
                val requesterIdMatch = oldItem.requesterId == newItem.requesterId
                val friendRequesterIdMatch = oldItem.friendRequestId == newItem.friendRequestId
                val requesterNameMatch = oldItem.requesterName == newItem.requesterName
                val statusMatch = oldItem.status == newItem.status
                return requesterIdMatch && friendRequesterIdMatch && requesterNameMatch && statusMatch
            }
        }
    }


    private var items = listOf<FriendRequest?>()


    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FriendRequestViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: FriendRequestViewHolder, position: Int) {
        val item = items[position]
        item?.let {
            holder.bind(it)
        }
    }

    fun updateItems(newItems: List<FriendRequest>) {
        items = if (newItems.isNotEmpty()) newItems else listOf()
        notifyDataSetChanged()
    }
    inner class FriendRequestViewHolder(inflater: LayoutInflater, var parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.home_friend_request, parent, false)) {

        init {
//            itemView.item_pill_checkbox.setOnClickListener {
//                onClick.invoke(adapterPosition)
//            }
        }

        @SuppressLint("DefaultLocale")
        fun bind(request: FriendRequest) {
            itemView.hero_name_text_view.text = request.requesterName
            val wantsToAddTextPartOne = SpannableString("Wants to add you as a")
            val wantsToAddTextPartTwo = SpannableString("Hero")
            wantsToAddTextPartTwo.setSpan(StyleSpan(Typeface.BOLD), 0, wantsToAddTextPartTwo.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            itemView.need_hero_text_view.text = TextUtils.concat(wantsToAddTextPartOne, " ", wantsToAddTextPartTwo)
        }

    }
}