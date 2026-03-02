package com.github.helenalog.ktsappkmp.presentation.screens.main

import com.github.helenalog.ktsappkmp.data.repository.FriendsRepositoryImpl
import com.github.helenalog.ktsappkmp.presentation.common.BaseViewModel

class MainViewModel : BaseViewModel<MainUiState, Nothing>(MainUiState.Initial) {
    private val repository = FriendsRepositoryImpl()

    fun loadFriends() {
        val friends = repository.getList()
        updateState { copy(friends = friends) }
    }
}