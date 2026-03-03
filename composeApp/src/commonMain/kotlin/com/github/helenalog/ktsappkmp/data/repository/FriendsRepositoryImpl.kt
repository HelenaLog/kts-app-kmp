package com.github.helenalog.ktsappkmp.data.repository

import com.github.helenalog.ktsappkmp.domain.model.Friend
import com.github.helenalog.ktsappkmp.domain.repository.FriendsRepository

class FriendsRepositoryImpl : FriendsRepository {
    override suspend fun getList(): List<Friend> {
        return listOf(
            Friend(
                1,
                "Иван",
                "Иванов",
                "https://static.vecteezy.com/system/resources/previews/036/463/807/non_2x/ai-generated-young-caucasian-man-in-business-attire-portrait-png.png",
                true
            ),
            Friend(
                2,
                "Мария",
                "Петрова",
                "https://static.vecteezy.com/system/resources/previews/036/121/731/non_2x/ai-generated-teenage-girl-on-transparent-background-ai-png.png",
                false
            ),
            Friend(
                3,
                "Алексей",
                "Сидоров",
                null,
                true
            )
        )
    }
}