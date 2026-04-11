package com.github.helenalog.ktsappkmp.feature.filter.data.repository

import com.github.helenalog.ktsappkmp.core.utils.suspendRunCatching
import com.github.helenalog.ktsappkmp.feature.filter.data.mapper.toDomain
import com.github.helenalog.ktsappkmp.feature.filter.data.remote.api.UserListApi
import com.github.helenalog.ktsappkmp.feature.filter.domain.model.UserList
import com.github.helenalog.ktsappkmp.feature.filter.domain.repository.FilterRepository

class FilterRepositoryImpl(
    private val api: UserListApi
) : FilterRepository {

    override suspend fun getUserList(): Result<List<UserList>> = suspendRunCatching {
        api.getUserLists().data.lists.map { it.toDomain() }
    }
}
