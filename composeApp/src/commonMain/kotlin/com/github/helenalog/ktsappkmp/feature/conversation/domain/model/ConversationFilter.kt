package com.github.helenalog.ktsappkmp.feature.conversation.domain.model

data class ConversationFilter(
    val selectedChannelKinds: Set<ChannelKind> = emptySet(),
    val selectedChannelIds: Set<String> = emptySet(),
    val selectedListId: String? = null
) {
    val isEmpty: Boolean
        get() = selectedChannelKinds.isEmpty() &&
                selectedChannelIds.isEmpty() &&
                selectedListId == null

    fun normalized(
        allKinds: Set<ChannelKind>,
        allChannelIds: Set<String>,
    ): ConversationFilter = copy(
        selectedChannelKinds = if (selectedChannelKinds == allKinds) emptySet()
        else selectedChannelKinds,
        selectedChannelIds = if (selectedChannelIds == allChannelIds) emptySet()
        else selectedChannelIds,
    )
}
