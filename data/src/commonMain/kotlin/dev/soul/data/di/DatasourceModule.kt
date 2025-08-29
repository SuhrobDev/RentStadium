package dev.soul.data.di

import dev.soul.data.remote.datasource.auth.AuthDatasource
import dev.soul.data.remote.datasource.auth.AuthDatasourceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val datasourceModule = module {
    singleOf(::AuthDatasourceImpl) { bind<AuthDatasource>() }
//    singleOf(::HomeDatasourceImpl) { bind<HomeDatasource>() }
//    singleOf(::ProductDatasourceImpl) { bind<ProductDatasource>() }
//    singleOf(::ShopsDatasourceImpl) { bind<ShopsDatasource>() }
//    singleOf(::HistoryDatasourceImpl) { bind<HistoryDatasource>() }
//    singleOf(::ProfileDatasourceImpl) { bind<ProfileDatasource>() }
}