package dev.soul.di

import dev.soul.data.di.datasourceModule
import dev.soul.data.di.repositoryModule
import dev.soul.datastore.datastore.di.datastoreModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

val sharedModule = module {
//    single<CustomerRepository> { CustomerRepositoryImpl() }
//    single<AdminRepository> { AdminRepositoryImpl() }
//    single<ProductRepository> { ProductRepositoryImpl() }
//    single<OrderRepository> { OrderRepositoryImpl(get()) }
//    single<IntentHandler> { IntentHandler() }
//    single<PaypalApi> { PaypalApi() }
//    viewModelOf(::AuthViewModel)
//    viewModelOf(::HomeGraphViewModel)
//    viewModelOf(::ProfileViewModel)
//    viewModelOf(::ManageProductViewModel)
//    viewModelOf(::AdminPanelViewModel)
//    viewModelOf(::ProductsOverviewViewModel)
//    viewModelOf(::DetailsViewModel)
//    viewModelOf(::CartViewModel)
//    viewModelOf(::CategorySearchViewModel)
//    viewModelOf(::CheckoutViewModel)
//    viewModelOf(::PaymentViewModel)
}

expect val targetModule: Module

fun initializeKoin(
    config: (KoinApplication.() -> Unit)? = null,
) {
    startKoin {
        config?.invoke(this)
        modules(
            datasourceModule,
            datastoreModule,
            repositoryModule,
            sharedModule,
            targetModule
        )
    }
}