package dev.soul.datastore.datastore.di

import dev.soul.datastore.datastore.dataStoreFileName
import dev.soul.datastore.datastore.getDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun dataStoreModule() = module {
    single {
        getDataStore {
            androidContext().filesDir?.resolve(dataStoreFileName)?.absolutePath
                ?: throw Exception("Couldn't get Android Datastore context.")
        }
    }

}