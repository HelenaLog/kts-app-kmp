package com.github.helenalog.ktsappkmp.presentation.screens.main

import androidx.lifecycle.viewModelScope
import com.github.helenalog.ktsappkmp.data.repository.ConversationRepositoryImpl
import com.github.helenalog.ktsappkmp.presentation.common.BaseViewModel
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainUiState, Nothing>(MainUiState.Initial) {
    private val repository = ConversationRepositoryImpl()

    fun loadConversations() {
        viewModelScope.launch {
            try {
                val conversations = repository.getList()
                updateState { copy(conversations = conversations) }
            } catch (e: Exception) {}
        }
    }
}