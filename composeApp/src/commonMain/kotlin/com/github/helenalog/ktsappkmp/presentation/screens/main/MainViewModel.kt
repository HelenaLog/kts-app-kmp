package com.github.helenalog.ktsappkmp.presentation.screens.main

import com.github.helenalog.ktsappkmp.domain.repository.FriendsRepository
import com.github.helenalog.ktsappkmp.presentation.common.BaseViewModel

class MainViewModel: BaseViewModel<MainUiState, Nothing>(MainUiState.Initial) {
    private val repository = FriendsRepository()

    fun loadFriends() {
        val friends = repository.getList()
        updateState { copy(friends = friends) }
    }
}