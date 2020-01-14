package com.example.marsheroly.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gery.mobile.data.entities.cards.medication.FriendRequest

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

    init {
        val friendRequestOne = FriendRequest(1, 2, "Bruno", false)
        val friendRequestTwo = FriendRequest(2, 3, "Joaquin", false)
        val friendRequestThree = FriendRequest(3, 4, "German", false)
        val friendRequestFour = FriendRequest(4, 5, "Rodrigo", false)
        val friendRequestFive = FriendRequest(5, 6, "Manuel", false)
        _friendRequestsList.value = listOf(friendRequestOne, friendRequestTwo, friendRequestThree, friendRequestFour, friendRequestFive)
    }

}
