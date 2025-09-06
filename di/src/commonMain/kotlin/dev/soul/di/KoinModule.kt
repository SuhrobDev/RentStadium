package dev.soul.di

import dev.soul.auth.login.LoginViewModel
import dev.soul.auth.otp.VerifyViewModel
import dev.soul.auth.register_info.RegisterViewModel
import dev.soul.auth.register_phone.RegisterPhoneViewModel
import dev.soul.data.di.datasourceModule
import dev.soul.data.di.repositoryModule
import dev.soul.datastore.datastore.di.dataStoreModule
import dev.soul.datastore.datastore.di.datastoreModule
import dev.soul.network.di.provideNetworkModule
import dev.soul.search.SearchViewModel
import dev.soul.validation.ValidationViewModel
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

fun sharedModule() = module {
    viewModel { LoginViewModel(get()) }
    viewModel { RegisterPhoneViewModel(get()) }
    viewModel { VerifyViewModel(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { ValidationViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}

expect fun targetModule(): Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        printLogger()
        modules(
            provideNetworkModule(),
            datasourceModule(),
            dataStoreModule(),
            datastoreModule(),
            repositoryModule(),
            sharedModule(),
            targetModule()
        )
    }
}