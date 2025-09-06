package dev.soul.data.remote.datasource.user.home

import io.ktor.client.HttpClient

class HomeDatasourceImpl(
    private val httpClient: HttpClient
) : HomeDatasource {

//    override suspend fun getStadiumList(
//        lat: Double,
//        lng: Double
//    ): MainResponse<List<StadiumItemResponse>> {
//        return httpClient.get(HttpRoutes.STADIUM_LIST) {
//            parameter(HttpParam.LAT, lat)
//            parameter(HttpParam.LNG, lng)
//        }.body()
//    }

}