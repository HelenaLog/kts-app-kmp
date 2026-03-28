package com.github.helenalog.ktsappkmp.core.di

import com.github.helenalog.ktsappkmp.core.data.local.AppDatabase
import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.CabinetDao
import com.github.helenalog.ktsappkmp.feature.conversation.data.local.dao.ConversationDao
import com.github.helenalog.ktsappkmp.feature.profile.data.local.dao.ProjectDao
import org.koin.dsl.module

val databaseModule = module {
    factory<ConversationDao> { get<AppDatabase>().conversationDao() }
    factory<CabinetDao> { get<AppDatabase>().cabinetDao() }
    factory<ProjectDao> { get<AppDatabase>().projectDao() }
}