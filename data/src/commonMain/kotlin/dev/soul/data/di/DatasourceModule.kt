package dev.soul.data.di

import dev.soul.data.remote.datasource.auth.AuthDatasource
import dev.soul.data.remote.datasource.auth.AuthDatasourceImpl
import dev.soul.data.remote.datasource.user.UserDatasource
import dev.soul.data.remote.datasource.user.UserDatasourceImpl
import dev.soul.data.remote.datasource.user.home.HomeDatasource
import dev.soul.data.remote.datasource.user.home.HomeDatasourceImpl
import dev.soul.data.remote.datasource.user.search.SearchDatasource
import dev.soul.data.remote.datasource.user.search.SearchDatasourceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun datasourceModule() = module {
    singleOf(::AuthDatasourceImpl) { bind<AuthDatasource>() }
    singleOf(::UserDatasourceImpl) { bind<UserDatasource>() }
    singleOf(::HomeDatasourceImpl) { bind<HomeDatasource>() }
    singleOf(::SearchDatasourceImpl) { bind<SearchDatasource>() }
}