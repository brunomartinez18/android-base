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
import com.gery.mobile.data.entities.cards.medication.FriendRequest
import kotlinx.android.synthetic.main.home_friend_request.view.*
import kotlinx.android.synthetic.main.home_need_hero_alert.view.need_hero_text_view

class FriendRequestAdapter(
    val onAccept: (Int) -> Unit,
    val onDismiss: (Int) -> Unit
) : ListAdapter<FriendRequest, FriendRequestAdapter.FriendRequestViewHolder>(DIFF_CALLBACK) {


    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FriendRequest>() {
            override fun areItemsTheSame(oldItem: FriendRequest, newItem: FriendRequest): Boolean {
                return oldItem.requesterId == newItem.requesterId
            }

            override fun areContentsTheSame(oldItem: FriendRequest, newItem: FriendRequest): Boolean {
                return checkEquality(oldItem, newItem)
            }

            private fun checkEquality(oldItem: FriendRequest, newItem: FriendRequest): Boolean {
                val friendRequesterIdMatch = oldItem.friendRequestId == newItem.friendRequestId
                val requesterNameMatch = oldItem.requesterName == newItem.requesterName
                val statusMatch = oldItem.status == newItem.status
                return friendRequesterIdMatch && requesterNameMatch && statusMatch
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendRequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return FriendRequestViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: FriendRequestViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(it)
        }
    }


    inner class FriendRequestViewHolder(inflater: LayoutInflater, var parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.home_friend_request, parent, false)) {

        init {

            itemView.accept_button.setOnClickListener {
                onAccept.invoke(adapterPosition)
            }

            itemView.dismiss_button.setOnClickListener {
                onDismiss.invoke(adapterPosition)
            }

        }

        @SuppressLint("DefaultLocale")
        fun bind(request: FriendRequest) {
            itemView.home_friend_request_requester_name.text = request.requesterName
            val wantsToAddTextPartOne = SpannableString("Wants to add you as a")
            val wantsToAddTextPartTwo = SpannableString("Hero")
            wantsToAddTextPartTwo.setSpan(StyleSpan(Typeface.BOLD), 0, wantsToAddTextPartTwo.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
            itemView.need_hero_text_view.text = TextUtils.concat(wantsToAddTextPartOne, " ", wantsToAddTextPartTwo)
        }

    }

}