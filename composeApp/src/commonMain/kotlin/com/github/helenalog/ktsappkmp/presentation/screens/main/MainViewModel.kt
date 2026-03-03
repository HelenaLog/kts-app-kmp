package com.github.helenalog.ktsappkmp.presentation.screens.main

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.data.repository.FriendsRepositoryImpl
import com.github.helenalog.ktsappkmp.presentation.common.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainUiState, Nothing>(MainUiState.Initial) {
    private val repository = FriendsRepositoryImpl()

    fun loadFriends() {
        viewModelScope.launch {
            try {
                val friends = repository.getList()
                updateState { copy(friends = friends) }
            } catch (e: Exception) {}
        }
    }
}