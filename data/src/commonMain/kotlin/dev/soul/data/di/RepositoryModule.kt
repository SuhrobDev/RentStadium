package dev.soul.data.di

import dev.soul.data.repository.AuthRepositoryImpl
import dev.soul.domain.repository.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get(), get(), get())
    }
}