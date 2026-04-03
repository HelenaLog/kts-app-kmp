package com.github.helenalog.ktsappkmp.feature.chat.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WsSubscriptionTokenResponse(
    @SerialName("subscription_token")
    val subscriptionToken: String
)