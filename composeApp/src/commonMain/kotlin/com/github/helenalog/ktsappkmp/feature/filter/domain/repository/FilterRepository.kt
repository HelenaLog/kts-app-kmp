package com.github.helenalog.ktsappkmp.feature.filter.domain.repository

import com.github.helenalog.ktsappkmp.feature.filter.domain.model.UserList

interface FilterRepository {
    suspend fun getUserLists(): Result<List<UserList>>
}
