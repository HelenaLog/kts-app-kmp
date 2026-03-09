package com.github.helenalog.ktsappkmp.data.repository

import com.github.helenalog.ktsappkmp.domain.model.ChannelDto
import com.github.helenalog.ktsappkmp.domain.model.ConversationDto
import com.github.helenalog.ktsappkmp.domain.model.MessageDto
import com.github.helenalog.ktsappkmp.domain.model.MessageKind
import com.github.helenalog.ktsappkmp.domain.model.StateDto
import com.github.helenalog.ktsappkmp.domain.model.UserDto
import com.github.helenalog.ktsappkmp.domain.repository.ConversationRepository

class ConversationRepositoryImpl : ConversationRepository {
    override suspend fun getList(): List<ConversationDto> {
        return listOf(
            ConversationDto(
                id = 1L,
                isRead = false,
                dateUpdated = "2024-03-09T15:12:00",
                user = UserDto(
                    id = "u1",
                    firstName = "Borodinsky",
                    lastName = null,
                    username = "borodinsky",
                    photo = null
                ),
                channel = ChannelDto(id = "c1", kind = "telegram", name = "Telegram"),
                state = StateDto(
                    stoppedByManager = false,
                    operatorTagged= false,
                    hasUnansweredOperatorMessage = true
                ),
                lastMessage = MessageDto(
                    id = "m1",
                    text = "Напиши, пожалуйста, имя и фамилию...",
                    kind = MessageKind.BOT,
                    dateCreated = "2024-03-09T15:12:00"
                )
            ),
            ConversationDto(
                id = 2L,
                isRead = true,
                dateUpdated = "2024-03-09T15:09:00",
                user = UserDto(
                    id = "u2",
                    firstName = "Георгий",
                    lastName = "Л",
                    username = "georgiy_l",
                    photo = null
                ),
                channel = ChannelDto(id = "c2", kind = "telegram", name = "Telegram"),
                state = StateDto(
                    stoppedByManager = false,
                    operatorTagged= false,
                    hasUnansweredOperatorMessage = true
                ),
                lastMessage = MessageDto(
                    id = "m2",
                    text = "Ах да! И обязательно подписывайся на нас...",
                    kind = MessageKind.SERVICE,
                    dateCreated = "2024-03-09T15:09:00"
                )
            ),
            ConversationDto(
                id = 3L,
                isRead = true,
                dateUpdated = "2024-03-09T15:08:00",
                user = UserDto(
                    id = "u3",
                    firstName = "Sergey",
                    lastName = null,
                    username = "sergey",
                    photo = null
                ),
                channel = ChannelDto(id = "c3", kind = "telegram", name = "Telegram"),
                state = StateDto(
                    stoppedByManager = false,
                    operatorTagged= false,
                    hasUnansweredOperatorMessage = true
                ),
                lastMessage = MessageDto(
                    id = "m3",
                    text = "#отклик Новый чепчик хочет в команду!",
                    kind = MessageKind.MANAGER,
                    dateCreated = "2024-03-09T15:08:00"
                )
            )
        )
    }
}