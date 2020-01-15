package com.example.marsheroly.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsheroly.common.extensions.removeAt
import com.example.marsheroly.data.entities.MarsProperty
import com.example.marsheroly.data.repositories.MarsRepository
import com.example.marsheroly.data.repositories.MarsRepositoryImpl
import com.gery.mobile.common.extensions.fetch
import com.gery.mobile.data.entities.cards.medication.FriendRequest
import com.gery.mobile.data.entities.cards.medication.HeroNearYou
import com.gery.mobile.data.entities.cards.medication.HeroeOfTheMonth
import com.gery.mobile.data.entities.cards.medication.NeighborhoodEvent

class HomeViewModel : ViewModel() {

    val userName: LiveData<String>
        get() = _userName
    private val _userName = MutableLiveData<String>("Juan Martinez Rodriguez Rimoldi Diaz Finozzi Maccio")

    val petName: LiveData<String>
        get() = _petName
    private val _petName = MutableLiveData<String>("Roy")


    private val _friendRequestsList = MutableLiveData<List<FriendRequest>>()
    val friendRequestsList: LiveData<List<FriendRequest>>
        get() = _friendRequestsList


    private val _neighborhoodEventsList = MutableLiveData<List<NeighborhoodEvent>>()
    val neighborhoodEventList: LiveData<List<NeighborhoodEvent>>
        get() = _neighborhoodEventsList

    private val _herosNearToYouList = MutableLiveData<List<HeroNearYou>>()
    val herosNearToYouList: LiveData<List<HeroNearYou>>
        get() = _herosNearToYouList

    private val _heroesOfTheMontList = MutableLiveData<List<HeroeOfTheMonth>>()
    val heroesOfTheMontList: LiveData<List<HeroeOfTheMonth>>
        get() = _heroesOfTheMontList

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        val friendRequestOne = FriendRequest(1, 2, "Bruno", false)
        val friendRequestTwo = FriendRequest(2, 3, "Joaquin", false)
        val friendRequestThree = FriendRequest(3, 4, "German", false)
        val friendRequestFour = FriendRequest(4, 5, "Rodrigo", false)
        val friendRequestFive = FriendRequest(5, 6, "Manuel", false)
        _friendRequestsList.value = listOf(friendRequestOne, friendRequestTwo, friendRequestThree, friendRequestFour, friendRequestFive)
        val heroNearYouOne = HeroNearYou(1, "Bruno", "Martinez")
        val heroNearYouTwo = HeroNearYou(2, "Joaquin", "Roldan")
        val heroNearYouThree = HeroNearYou(3, "Very", "very very long lastname")
        val heroNearYouFour = HeroNearYou(4, "Very very very very very very long name", "Martinez")
        val heroNearYouFive = HeroNearYou(5, "Pedro", "Os")
        _herosNearToYouList.value = listOf(heroNearYouOne, heroNearYouTwo, heroNearYouThree, heroNearYouFour, heroNearYouFive)
        val heroeOfTheMonthOne = HeroeOfTheMonth(1, "Bruno", "Martinez", 1500)
        val heroeOfTheMonthTwo = HeroeOfTheMonth(2, "Joaquin", "Roldan", 300)
        val heroeOfTheMonthThree = HeroeOfTheMonth(3, "Very long long name", "Last name", 1300000)
        _heroesOfTheMontList.value = listOf(heroeOfTheMonthOne, heroeOfTheMonthTwo, heroeOfTheMonthThree)
        _loading.value = true
        _error.value = null
        getMarsImages()
    }

    fun acceptFriendRequest(friendRequestIndex: Int) {
        // Make API call then remove it.
        _friendRequestsList.removeAt(friendRequestIndex)
    }

    fun dismissFriendRequest(friendRequestIndex: Int) {
        // Make API call then remove it.
        _friendRequestsList.removeAt(friendRequestIndex)
    }


    fun getMarsImages() {

        viewModelScope.fetch(
            call = { MarsRepositoryImpl.getMarsImages() },
            onSuccess = {
                var newNeighborhoodEventLists: MutableList<NeighborhoodEvent> = mutableListOf()
                var index = 0
                it.body()?.let {
                    it.forEach {
                        val event = NeighborhoodEvent(index, "Mars festival ${index}", "Mars festival ${index}", it.imgSrcUrl)
                        newNeighborhoodEventLists.add(event)
                        index += 1
                    }
                }
                _neighborhoodEventsList.value = newNeighborhoodEventLists
                _loading.value = false
            },
            onFailure = {
                _error.value = it.message.toString()
                _loading.value = false
            }
        )
    }
}
