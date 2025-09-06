package dev.soul.datastore.datastore.di

import org.koin.dsl.module
import dev.soul.datastore.datastore.DataStoreRepository
import org.koin.core.module.Module

fun datastoreModule() = module {
    single { DataStoreRepository(get()) }
}

expect fun dataStoreModule(): Module
