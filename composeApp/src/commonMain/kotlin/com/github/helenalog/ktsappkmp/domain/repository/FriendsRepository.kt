package com.github.helenalog.ktsappkmp.domain.repository

import com.github.helenalog.ktsappkmp.domain.model.Friend

interface FriendsRepository {
    suspend fun getList(): List<Friend>
}