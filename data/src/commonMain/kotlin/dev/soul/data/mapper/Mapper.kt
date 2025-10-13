package dev.soul.data.mapper

import dev.soul.data.remote.dto.auth.login.request.LoginRequest
import dev.soul.data.remote.dto.auth.login.response.LoginResponse
import dev.soul.data.remote.dto.auth.phone.request.RegPhoneRequest
import dev.soul.data.remote.dto.auth.register.request.RegisterRequest
import dev.soul.data.remote.dto.auth.verify.request.VerifyOtpRequest
import dev.soul.data.remote.dto.user.StadiumDistanceResponse
import dev.soul.data.remote.dto.user.StadiumImageResponse
import dev.soul.data.remote.dto.user.StadiumLocationResponse
import dev.soul.data.remote.dto.user.available.response.AvailableDto
import dev.soul.data.remote.dto.user.book.request.BookRequestDto
import dev.soul.data.remote.dto.user.book.response.BookResponseDto
import dev.soul.data.remote.dto.user.like.response.LikedItemDto
import dev.soul.data.remote.dto.user.response.UserResponse
import dev.soul.data.remote.dto.user.schedule.detail.response.ScheduleDetailDto
import dev.soul.data.remote.dto.user.schedule.response.DatetimeRange
import dev.soul.data.remote.dto.user.schedule.response.ScheduleItemDto
import dev.soul.data.remote.dto.user.search.response.StadiumItemResponse
import dev.soul.data.remote.dto.user.stadium_detail.response.CommentResponse
import dev.soul.data.remote.dto.user.stadium_detail.response.ImageResponse
import dev.soul.data.remote.dto.user.stadium_detail.response.LocationResponse
import dev.soul.data.remote.dto.user.stadium_detail.response.StadiumDetailResponse
import dev.soul.data.remote.dto.user.upcoming_days.response.UpcomingDaysDto
import dev.soul.domain.model.auth.login.request.LoginRequestModel
import dev.soul.domain.model.auth.login.response.LoginResponseModel
import dev.soul.domain.model.auth.phone.request.RegPhoneModel
import dev.soul.domain.model.auth.register.request.RegisterModel
import dev.soul.domain.model.auth.verify.request.VerifyOtpModel
import dev.soul.domain.model.user.available.response.AvailableModel
import dev.soul.domain.model.user.book.request.BookRequestModel
import dev.soul.domain.model.user.book.response.BookResponseModel
import dev.soul.domain.model.user.response.UserModel
import dev.soul.domain.model.user.schedule.detail.response.ScheduleDetailModel
import dev.soul.domain.model.user.schedule.response.DatetimeRangeModel
import dev.soul.domain.model.user.schedule.response.ScheduleItemModel
import dev.soul.domain.model.user.schedule.response.toStatus
import dev.soul.domain.model.user.search.response.StadiumDistanceModel
import dev.soul.domain.model.user.search.response.StadiumImageModel
import dev.soul.domain.model.user.search.response.StadiumItemModel
import dev.soul.domain.model.user.search.response.StadiumLocationModel
import dev.soul.domain.model.user.stadium_detail.response.CommentModel
import dev.soul.domain.model.user.stadium_detail.response.ImageModel
import dev.soul.domain.model.user.stadium_detail.response.LocationModel
import dev.soul.domain.model.user.stadium_detail.response.StadiumDetailModel
import dev.soul.domain.model.user.upcoming_days.response.UpcomingDaysModel

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

fun LikedItemDto.toModel(): StadiumItemModel {
    return StadiumItemModel(
        address = stadium?.address ?: "",
        distance = stadium?.distance.toModel(),
        id = stadium?.id ?: 0,
        images = stadium?.images?.map { it.toModel() } ?: emptyList(),
        liked = stadium?.liked ?: false,
        location = stadium?.location.toModel(),
        name = stadium?.name ?: "",
        price = stadium?.price ?: "",
        rating = stadium?.rating ?: "",
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

fun StadiumDetailResponse.toModel(): StadiumDetailModel {
    return StadiumDetailModel(
        address = address ?: "",
        comments = comments?.map { it.toModel() } ?: emptyList(),
        distance = distance ?: "",
        id = id ?: 0,
        images = images?.map { it.toModel() } ?: emptyList(),
        liked = liked ?: "",
        location = location?.toModel() ?: LocationModel(emptyList(), ""),
        name = name ?: "",
        price = price ?: "",
        rating = rating ?: ""
    )
}

fun CommentResponse.toModel(): CommentModel {
    return CommentModel(
        created = created ?: "",
        id = id ?: 0,
        message = message ?: "",
        modified = modified ?: "",
        rating = rating ?: 0,
        stadium = stadium ?: 0,
        user = user?.toModel()!!
    )
}

fun ImageResponse.toModel(): ImageModel {
    return ImageModel(
        image = image ?: "",
        name = name ?: "",
        uuid = uuid ?: ""
    )
}

fun LocationResponse?.toModel(): LocationModel {
    return LocationModel(
        coordinates = this?.coordinates ?: emptyList(),
        type = this?.type ?: ""
    )
}

fun UpcomingDaysDto.toModel(): UpcomingDaysModel {
    return UpcomingDaysModel(
        date = date ?: "",
        weekday = weekday ?: ""
    )
}


fun AvailableDto.toModel(): AvailableModel {
    return AvailableModel(
        created = created ?: "",
        dayOfWeek = day_of_week ?: 0,
        dayOfWeekDisplay = day_of_week_display ?: "",
        endTime = end_time ?: "",
        id = id ?: 0,
        isActive = is_active ?: false,
        isBooked = is_booked ?: false,
        modified = modified ?: "",
        price = price ?: "",
        stadium = stadium ?: 0,
        startTime = start_time ?: ""
    )
}

fun ScheduleItemDto.toModel(): ScheduleItemModel {
    return ScheduleItemModel(
        created = created ?: "",
        datetimeRange = datetime_range?.toModel()!!,
        id = id ?: 0,
        modified = modified ?: "",
        notes = notes ?: "",
        stadium = stadium?.toModel(),
        status = (status ?: "").toStatus()
    )
}

fun DatetimeRange.toModel(): DatetimeRangeModel {
    return DatetimeRangeModel(
        lower = lower ?: "",
        upper = upper ?: ""
    )
}

fun DatetimeRangeModel.toDto(): DatetimeRange {
    return DatetimeRange(
        lower = lower,
        upper = upper
    )
}

fun BookResponseDto.toModel(): BookResponseModel {
    return BookResponseModel(
        created = created ?: "",
        datetimeRange = datetime_range?.toModel()!!,
        id = id ?: 0,
        modified = modified ?: "",
        notes = notes ?: "",
        stadium = stadium ?: 0,
        status = status ?: ""
    )
}

fun BookRequestModel.toDto(): BookRequestDto {
    return BookRequestDto(
        datetime_range = datetimeRange.toDto(),
        note = note,
        stadium = stadium,
        status = status
    )
}

fun ScheduleDetailDto.toModel(): ScheduleDetailModel{
    return ScheduleDetailModel(
        created = created ?: "",
        datetimeRange = datetime_range?.toModel(),
        id = id ?: 0,
        modified = modified ?: "",
        notes = notes ?: "",
        stadium = stadium?.toModel(),
        status = (status ?: "").toStatus()
    )
}