package com.github.helenalog.ktsappkmp.feature.filter.domain.usecase

import com.github.helenalog.ktsappkmp.feature.filter.domain.model.UserList
import com.github.helenalog.ktsappkmp.feature.filter.domain.repository.FilterRepository

class GetUserListUseCase(
    private val repository: FilterRepository
) {
    suspend operator fun invoke(): Result<List<UserList>> = repository.getUserList()
}
