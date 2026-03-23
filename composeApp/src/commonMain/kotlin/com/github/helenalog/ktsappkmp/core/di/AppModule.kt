package com.github.helenalog.ktsappkmp.core.di

import com.github.helenalog.ktsappkmp.core.presentation.navigation.di.navigationModule
import com.github.helenalog.ktsappkmp.feature.conversation.di.conversationModule
import com.github.helenalog.ktsappkmp.feature.login.di.loginModule
import com.github.helenalog.ktsappkmp.feature.onboarding.di.onboardingModule
import com.github.helenalog.ktsappkmp.feature.profile.di.profileModule
import org.koin.core.module.Module

fun appModules(): List<Module> = listOf(
    navigationModule,
    networkModule,
    databaseModule,
    storageModule,
    platformModule,
    onboardingModule,
    loginModule,
    conversationModule,
    profileModule
)