package ru.sicampus.bootcamp2026.core

//наверное лучше по другому, но так вроде хорошо
object Config {
    const val BASE_URL = "http://192.168.1.14:8080/api/v1"

    // Auth endp-s
    const val LOGIN_ENDPOINT = "/auth/login"
    const val REGISTER_ENDPOINT = "/auth/register"

    // User endp-s
    const val USER_BY_ID_ENDPOINT = "/users/{id}"
    const val SEARCH_USERS_ENDPOINT = "/users/search"
    const val UPDATE_USER_ENDPOINT = "/users/{id}"

    // Meeting endp-s
    const val CREATE_MEETING_ENDPOINT = "/meetings"
    const val GET_MEETING_BY_ID_ENDPOINT = "/meetings/{id}"
    const val GET_DAY_SCHEDULE_ENDPOINT = "/meetings/schedule/day"
    const val GET_WEEK_SCHEDULE_ENDPOINT = "/meetings/schedule/week"
    const val GET_MONTH_SCHEDULE_ENDPOINT = "/meetings/schedule/month"
    const val GET_INVITATIONS_ENDPOINT = "/meetings/invitations"
    const val RESPOND_TO_INVITATION_ENDPOINT = "/meetings/{id}/respond"
}