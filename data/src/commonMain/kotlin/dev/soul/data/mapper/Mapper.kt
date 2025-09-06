package dev.soul.data.mapper

import dev.soul.data.remote.dto.auth.login.request.LoginRequest
import dev.soul.data.remote.dto.auth.login.response.LoginResponse
import dev.soul.data.remote.dto.auth.phone.request.RegPhoneRequest
import dev.soul.data.remote.dto.auth.register.request.RegisterRequest
import dev.soul.data.remote.dto.auth.verify.request.VerifyOtpRequest
import dev.soul.data.remote.dto.user.response.UserResponse
import dev.soul.data.remote.dto.user.search.response.StadiumDistanceResponse
import dev.soul.data.remote.dto.user.search.response.StadiumImageResponse
import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import dev.soul.data.remote.dto.user.search.response.StadiumLocationResponse
import dev.soul.domain.model.PagingModel
import dev.soul.domain.model.auth.login.request.LoginRequestModel
import dev.soul.domain.model.auth.login.response.LoginResponseModel
import dev.soul.domain.model.auth.phone.request.RegPhoneModel
import dev.soul.domain.model.auth.register.request.RegisterModel
import dev.soul.domain.model.auth.verify.request.VerifyOtpModel
import dev.soul.domain.model.user.response.UserModel
import dev.soul.domain.model.user.search.response.StadiumDistanceModel
import dev.soul.domain.model.user.search.response.StadiumImageModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.domain.model.user.search.response.StadiumLocationModel
import dev.soul.network.PagingResponse

fun LoginRequestModel.toDto(): LoginRequest {
    return LoginRequest(
        phone_number = phoneNumber,
        password = password
    )
}

fun LoginResponse.toModel(): LoginResponseModel {
    return LoginResponseModel(
        access = access,
        refresh = refresh
    )
}

fun RegPhoneModel.toDto(): RegPhoneRequest {
    return RegPhoneRequest(
        phone_number = phoneNumber
    )
}

fun VerifyOtpModel.toDto(): VerifyOtpRequest {
    return VerifyOtpRequest(
        phone_number = phoneNumber,
        otp = otp
    )
}

fun RegisterModel.toDto(): RegisterRequest {
    return RegisterRequest(
        phone_number = phoneNumber,
        first_name = firstName,
        last_name = lastName,
        password = password,
        repeat_password = repeatPassword
    )
}

fun UserResponse.toModel(): UserModel {
    return UserModel(
        firstName = first_name ?: "",
        lastName = last_name ?: "",
        phoneNumber = phone_number ?: "",
        dateJoined = date_joined ?: "",
        lastLogin = last_login ?: "",
        email = email ?: "",
        id = id ?: 0,
        isActive = is_active ?: false
    )
}

fun StadiumItemResponse.toModel(): StadiumItemModel {
    return StadiumItemModel(
        address = address ?: "",
        distance = distance.toModel(),
        id = id ?: 0,
        images = images?.map { it.toModel() } ?: emptyList(),
        liked = liked ?: false,
        location = location.toModel(),
        name = name ?: "",
        price = price ?: "",
        rating = rating ?: "",
    )
}

fun StadiumDistanceResponse?.toModel(): StadiumDistanceModel {
    return StadiumDistanceModel(
        unit = this?.unit ?: "",
        value = this?.value ?: 0.0
    )
}

fun StadiumImageResponse.toModel(): StadiumImageModel {
    return StadiumImageModel(
        image = image ?: "",
        name = name ?: "",
        uuid = uuid ?: ""
    )
}

fun StadiumLocationResponse?.toModel(): StadiumLocationModel {
    return StadiumLocationModel(
        coordinates = this?.coordinates ?: emptyList(),
        type = this?.type ?: ""
    )
}