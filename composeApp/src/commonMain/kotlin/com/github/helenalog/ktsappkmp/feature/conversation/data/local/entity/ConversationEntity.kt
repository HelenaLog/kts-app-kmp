package com.github.helenalog.ktsappkmp.feature.conversation.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "conversations")
data class ConversationEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "is_read")
    val isRead: Boolean,

    @ColumnInfo(name = "user_name")
    val userName: String,

    @ColumnInfo(name = "photo_url")
    val photoUrl: String?,

    @ColumnInfo(name = "channel_kind")
    val channelKind: String,

    @ColumnInfo(name = "last_message_text")
    val lastMessageText: String,

    @ColumnInfo(name = "last_message_kind")
    val lastMessageKind: String?,

    @ColumnInfo(name = "date_updated")
    val dateUpdated: String
)