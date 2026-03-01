package com.github.helenalog.ktsappkmp.screens.main.presentation

import com.github.helenalog.ktsappkmp.common.BaseViewModel
import com.github.helenalog.ktsappkmp.screens.main.presentation.models.Friend
import com.github.helenalog.ktsappkmp.screens.main.presentation.models.MainUiState

class MainViewModel: BaseViewModel<MainUiState, Nothing>(MainUiState.Initial) {

    fun loadFriends() {
        val friends = listOf(
            Friend(1, "Иван", "Иванов", "https://static.vecteezy.com/system/resources/previews/036/463/807/non_2x/ai-generated-young-caucasian-man-in-business-attire-portrait-png.png", 1),
            Friend(2, "Мария", "Петрова", "https://static.vecteezy.com/system/resources/previews/036/121/731/non_2x/ai-generated-teenage-girl-on-transparent-background-ai-png.png", 0),
            Friend(3, "Алексей", "Сидоров", null, 1),
        )
        updateState { copy(friends = friends) }
    }
}