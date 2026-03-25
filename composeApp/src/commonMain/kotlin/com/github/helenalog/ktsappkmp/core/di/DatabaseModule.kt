package com.github.helenalog.ktsappkmp.core.di

import com.github.helenalog.ktsappkmp.core.data.local.AppDatabase
import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.CabinetDao
import com.github.helenalog.ktsappkmp.feature.conversation.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.ProjectDao
import org.koin.dsl.module

val databaseModule = module {
    single<ConversationDao> { get<AppDatabase>().conversationDao() }
    single<CabinetDao> { get<AppDatabase>().cabinetDao() }
    single<ProjectDao> { get<AppDatabase>().projectDao() }
}