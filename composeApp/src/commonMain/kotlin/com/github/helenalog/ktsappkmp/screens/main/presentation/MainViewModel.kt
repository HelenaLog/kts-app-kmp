package com.github.helenalog.ktsappkmp.screens.main.presentation

import com.github.helenalog.ktsappkmp.common.BaseViewModel
import com.github.helenalog.ktsappkmp.screens.main.presentation.models.Friend
import com.github.helenalog.ktsappkmp.screens.main.presentation.models.MainUiState

class MainViewModel: BaseViewModel<MainUiState, Nothing>(MainUiState.Initial) {
    private val repository = FriendsRepository()
    
    fun loadFriends() {
        val friends = repository.getList()
        updateState { copy(friends = friends) }
    }
}