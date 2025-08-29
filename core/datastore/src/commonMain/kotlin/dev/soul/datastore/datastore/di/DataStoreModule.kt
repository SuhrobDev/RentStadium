package dev.soul.datastore.datastore.di

import org.koin.dsl.module
import dev.soul.datastore.datastore.DataStoreRepository

val datastoreModule = module {
    single { DataStoreRepository(get()) }
}