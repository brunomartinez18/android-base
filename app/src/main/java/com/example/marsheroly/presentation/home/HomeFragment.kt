package com.example.marsheroly.presentation.home

import android.graphics.Typeface
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.marsheroly.R
import com.example.marsheroly.common.extensions.dpToPx
import com.example.marsheroly.common.utils.OffsetsItemDecoration
import com.example.marsheroly.common.utils.StartSnapHelper
import com.gery.mobile.presentation.dashboard.main.adapters.FriendRequestAdapter
import com.gery.mobile.presentation.dashboard.main.adapters.HerosNearToYouAdapter
import com.gery.mobile.presentation.dashboard.main.adapters.NeighborhoodEventsAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_need_hero_alert.*
import kotlinx.android.synthetic.main.home_need_hero_alert.need_hero_text_view
import kotlinx.android.synthetic.main.home_need_hero_alert.view.*
import kotlinx.android.synthetic.main.mars_heroly_of_the_month.view.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var friendRequestAdapter: FriendRequestAdapter
    private lateinit var neighborhoodEventsAdapter: NeighborhoodEventsAdapter
    private lateinit var herosNearToYouAdapter: HerosNearToYouAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        //ADAPTERS
        friendRequestAdapter = FriendRequestAdapter(
            onAccept = {
                viewModel.acceptFriendRequest(it)
                Toast.makeText(context, "Accepted invitation", Toast.LENGTH_SHORT).show()
            },
            onDismiss = {
                viewModel.dismissFriendRequest(it)
                Toast.makeText(context, "Dismiss invitation", Toast.LENGTH_SHORT).show()
            }
        )
        neighborhoodEventsAdapter = NeighborhoodEventsAdapter()
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        herosNearToYouAdapter = HerosNearToYouAdapter(screenWidth)
        //LISTS
        viewModel.friendRequestsList.observe(this, Observer { newListOfRequest ->
            friendRequestAdapter.submitList(newListOfRequest)
        })

        viewModel.neighborhoodEventList.observe(this, Observer { newNeighborhoodEvents ->
            neighborhoodEventsAdapter.submitList(newNeighborhoodEvents)
        })

        viewModel.herosNearToYouList.observe(this, Observer { newHerosNearToYou ->
            herosNearToYouAdapter.submitList(newHerosNearToYou)
        })

        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHeroAlert()
        setFriendRequestsRecycler()
        setNeighborhoodEventsRecycler()
        setHerosNearToYouRecycler()
        accept_invitation_button.setOnClickListener {
            mars_heroly_needed_alert.visibility = View.GONE
        }

        dismiss_invitation_button.setOnClickListener {
            mars_heroly_needed_alert.visibility = View.GONE
        }
    }

    private fun setHeroAlert() {
        val needsAheroTextFirstPart = SpannableString("Needs a hero for ${viewModel.petName.value}")
        val needsAheroTextSecondPart = SpannableString("for tomorrow between 10am and 12am")
        needsAheroTextSecondPart.setSpan(StyleSpan(Typeface.BOLD), 0, needsAheroTextSecondPart.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        need_hero_text_view.text = TextUtils.concat(needsAheroTextFirstPart, " ", needsAheroTextSecondPart)
        mars_heroly_of_the_month_view.mars_heroly_of_the_month_name.text = "${viewModel.userName.value}"
        mars_heroly_needed_alert.need_hero_name.text = "${viewModel.userName.value}"
    }

    private fun setNeighborhoodEventsRecycler() {
        val separation = 10.dpToPx()
        val offsetDecoration = OffsetsItemDecoration(rightOffset = separation, applyToLast = false)
        neighborhood_event_recycler_view.addItemDecoration(offsetDecoration)
        neighborhood_event_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        neighborhood_event_recycler_view.adapter = neighborhoodEventsAdapter
        val snapHelper = StartSnapHelper()
        snapHelper.attachToRecyclerView(neighborhood_event_recycler_view)
    }


    private fun setFriendRequestsRecycler() {
        home_friends_request_recycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        home_friends_request_recycler.adapter = friendRequestAdapter
        val snapHelper = StartSnapHelper()
        snapHelper.attachToRecyclerView(home_friends_request_recycler)

    }

    private fun setHerosNearToYouRecycler() {
        val separation = 20.dpToPx()
        val offsetDecoration = OffsetsItemDecoration(rightOffset = separation, applyToLast = false)
        heroes_near_to_you_recycler_view.addItemDecoration(offsetDecoration)
        heroes_near_to_you_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        heroes_near_to_you_recycler_view.adapter = herosNearToYouAdapter
        val snapHelper = StartSnapHelper()
        snapHelper.attachToRecyclerView(heroes_near_to_you_recycler_view)
    }


}
