package com.example.marsheroly.presentation.home

import android.graphics.Typeface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.marsheroly.R
import com.example.marsheroly.common.extensions.dpToPx
import com.example.marsheroly.common.utils.OffsetsItemDecoration
import com.example.marsheroly.databinding.HomeFragmentBinding
import com.gery.mobile.presentation.dashboard.main.adapters.FriendRequestAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_need_hero_alert.*
import kotlinx.android.synthetic.main.home_need_hero_alert.view.*
import kotlinx.android.synthetic.main.mars_heroly_of_the_month.view.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var friendRequestAdapter: FriendRequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        friendRequestAdapter = FriendRequestAdapter()
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHeroAlert()
        setFriendRequestsRecycler()
        viewModel.friendRequestsList.observe(this, Observer { newListOfRequest ->
            friendRequestAdapter.updateItems(newListOfRequest)
        })

    }

    private fun setFriendRequestsRecycler() {
        val separation = 10.dpToPx()
        val offsetDecoration = OffsetsItemDecoration(rightOffset = separation, applyToLast = false)
        home_friends_request_recycler.addItemDecoration(offsetDecoration)
        home_friends_request_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        home_friends_request_recycler.adapter = friendRequestAdapter

    }

    private fun setHeroAlert() {
        val needsAheroTextFirstPart = SpannableString("Needs a hero for ${viewModel.petName.value}")
        val needsAheroTextSecondPart = SpannableString("for tomorrow between 10am and 12am")
        needsAheroTextSecondPart.setSpan(StyleSpan(Typeface.BOLD), 0, needsAheroTextSecondPart.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        need_hero_text_view.text = TextUtils.concat(needsAheroTextFirstPart, " ", needsAheroTextSecondPart)
        mars_heroly_of_the_month_view.mars_heroly_of_the_month_name.text = "${viewModel.userName.value}"
        mars_heroly_needed_alert.hero_name_text_view.text = "${viewModel.userName.value}"
    }


}
