package dev.soul.data.di

import dev.soul.data.repository.UserRepositoryImpl
import dev.soul.data.repository.auth.AuthRepositoryImpl
import dev.soul.data.repository.user.HomeRepositoryImpl
import dev.soul.data.repository.user.MoreRepositoryImpl
import dev.soul.data.repository.user.SearchRepositoryImpl
import dev.soul.data.repository.user.StadiumDetailRepositoryImpl
import dev.soul.domain.repository.UserRepository
import dev.soul.domain.repository.auth.AuthRepository
import dev.soul.domain.repository.user.HomeRepository
import dev.soul.domain.repository.user.MoreRepository
import dev.soul.domain.repository.user.SearchRepository
import dev.soul.domain.repository.user.StadiumDetailRepository
import dev.soul.shared.utils.provideDispatcher
import org.koin.dsl.module

fun repositoryModule() = module {
    single<AuthRepository> {
        AuthRepositoryImpl(get(), get(), provideDispatcher())
    }
    single<UserRepository> {
        UserRepositoryImpl(get(), get(), provideDispatcher())
    }
    single<SearchRepository> {
        SearchRepositoryImpl(get(), get(), provideDispatcher())
    }
    single<StadiumDetailRepository> {
        StadiumDetailRepositoryImpl(get(), get(), provideDispatcher())
    }
    single<HomeRepository> {
        HomeRepositoryImpl(get(), get(), provideDispatcher())
    }
    single<MoreRepository> {
        MoreRepositoryImpl(get(), get(), provideDispatcher())
    }
}