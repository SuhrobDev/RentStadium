package dev.soul.data.mapper

import dev.soul.data.remote.dto.auth.login.request.LoginRequest
import dev.soul.data.remote.dto.auth.login.response.LoginResponse
import dev.soul.data.remote.dto.auth.phone.request.RegPhoneRequest
import dev.soul.data.remote.dto.auth.register.request.RegisterRequest
import dev.soul.data.remote.dto.auth.verify.request.VerifyOtpRequest
import dev.soul.domain.model.auth.login.request.LoginRequestModel
import dev.soul.domain.model.auth.login.response.LoginResponseModel
import dev.soul.domain.model.auth.phone.request.RegPhoneModel
import dev.soul.domain.model.auth.register.request.RegisterModel
import dev.soul.domain.model.auth.verify.request.VerifyOtpModel

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