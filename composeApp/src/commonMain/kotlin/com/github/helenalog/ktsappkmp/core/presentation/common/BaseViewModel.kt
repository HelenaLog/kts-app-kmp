package com.github.helenalog.ktsappkmp.core.presentation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<State, Event>(initialState: State) : ViewModel() {
    val state: StateFlow<State>
        get() = mutableState.asStateFlow()

    val event: SharedFlow<Event>
        get() = mutableEvent.asSharedFlow()

    private val mutableState = MutableStateFlow(initialState)
    private val mutableEvent = MutableSharedFlow<Event>()

    protected fun updateState(block: State.() -> State) {
        mutableState.update { it.block() }
    }

    protected fun sendEvent(event: Event) {
        viewModelScope.launch {
            mutableEvent.emit(event)
        }
    }
}
