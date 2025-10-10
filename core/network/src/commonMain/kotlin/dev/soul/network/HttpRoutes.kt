package dev.soul.network

object HttpRoutes {
    const val LOGIN = "auth/token/"
    const val PHONE = "auth/register/phonenumber/"
    const val VERIFY_OTP = "auth/register/validate-otp/"
    const val REGISTER = "auth/register/"

    const val PROFILE = "auth/user/profile/"
    const val REFRESH_TOKEN = "auth/token/refresh/"
    const val STADIUM_LIST = "stadium/list/"
    const val STADIUM_DETAIL = "stadium/detail/"
    const val PERSONALIZED = "stadium/recommendations/personalized/"
    const val POPULAR = "stadium/recommendations/popular/"
    const val LIKE = "stadium/favorites/"
    const val AVAILABLE = "stadium/availability/"
    const val SCHEDULE = "stadium/user-bookings/list/"
    const val BOOK = "stadium/bookings/"
    const val UPCOMING_DAYS = "utility/upcoming-days/"

}