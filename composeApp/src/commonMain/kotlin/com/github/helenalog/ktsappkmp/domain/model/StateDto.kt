package com.github.helenalog.ktsappkmp.domain.model

data class StateDto(
    val stoppedByManager: Boolean,
    val operatorTagged: Boolean,
    val hasUnansweredOperatorMessage: Boolean
)