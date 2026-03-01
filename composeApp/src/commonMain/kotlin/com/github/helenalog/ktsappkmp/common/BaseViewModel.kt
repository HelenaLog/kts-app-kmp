package com.github.helenalog.ktsappkmp.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel<State, Event>(initialState: State): ViewModel() {
    val state: StateFlow<State>
        get() = mutableState.asStateFlow()

    private val mutableState = MutableStateFlow(initialState)

    fun updateState(block: State.() -> State) {
        mutableState.update { it.block() }
    }
}