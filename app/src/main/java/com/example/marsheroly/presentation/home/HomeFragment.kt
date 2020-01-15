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
import com.example.marsheroly.presentation.home.Adapter.FriendRequestAdapter
import com.example.marsheroly.presentation.home.Adapter.HeroesOfTheMonthAdapter
import com.example.marsheroly.presentation.home.Adapter.HerosNearToYouAdapter
import com.example.marsheroly.presentation.home.Adapter.NeighborhoodEventsAdapter
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.home_fragment.view.*
import kotlinx.android.synthetic.main.home_need_hero_alert.*
import kotlinx.android.synthetic.main.home_need_hero_alert.need_hero_text_view
import kotlinx.android.synthetic.main.home_need_hero_alert.view.*
import kotlinx.android.synthetic.main.mars_heroly_of_the_month.view.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var friendRequestAdapter: FriendRequestAdapter
    private lateinit var neighborhoodEventsAdapter: NeighborhoodEventsAdapter
    private lateinit var herosNearToYouAdapter: HerosNearToYouAdapter
    private lateinit var heroesOfTheMonthAdapter: HeroesOfTheMonthAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
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
        heroesOfTheMonthAdapter = HeroesOfTheMonthAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        viewModel.friendRequestsList.observe(this, Observer { newListOfRequest ->
            friendRequestAdapter.submitList(newListOfRequest)
        })

        viewModel.neighborhoodEventList.observe(this, Observer { newNeighborhoodEvents ->
            neighborhoodEventsAdapter.submitList(newNeighborhoodEvents)
        })

        viewModel.loading.observe(this, Observer {
            if (!it) {
                home_welcome_section.home_loader.visibility = View.GONE
                mars_heroly_of_the_month_view.visibility = View.VISIBLE
                mars_heroly_needed_alert.visibility = View.VISIBLE
                home_friends_request_recycler.visibility = View.VISIBLE
                heroes_near_to_you_recycler_view.visibility = View.VISIBLE
                heroes_of_the_month_recycler_view.visibility = View.VISIBLE
                heroes_of_the_month_title.visibility = View.VISIBLE
                heroes_near_to_you_title.visibility = View.VISIBLE
                neighborhood_event.visibility = View.VISIBLE
            }
        })

        viewModel.error.observe(this, Observer {
            // show some kind of error
        })

        viewModel.herosNearToYouList.observe(this, Observer { newHerosNearToYou ->
            herosNearToYouAdapter.submitList(newHerosNearToYou)
        })

        viewModel.heroesOfTheMontList.observe(this, Observer { newHeroesOfTheMonth ->
            heroesOfTheMonthAdapter.submitList(newHeroesOfTheMonth)
        })

        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHeroAlert()
        setFriendRequestsRecycler()
        setNeighborhoodEventsRecycler()
        setHerosNearToYouRecycler()
        setHeroesOfTheMonthRecycler()
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
        val separation = 10.dpToPx()
        val offsetDecoration = OffsetsItemDecoration(rightOffset = separation, applyToLast = false)
        home_friends_request_recycler.addItemDecoration(offsetDecoration)
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

    private fun setHeroesOfTheMonthRecycler() {
        val separation = 24.dpToPx()
        val offsetDecoration = OffsetsItemDecoration(bottomOffset = separation, applyToLast = false)
        heroes_of_the_month_recycler_view.addItemDecoration(offsetDecoration)
        heroes_of_the_month_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        heroes_of_the_month_recycler_view.adapter = heroesOfTheMonthAdapter
        val snapHelper = StartSnapHelper()
        snapHelper.attachToRecyclerView(heroes_of_the_month_recycler_view)
    }
}
