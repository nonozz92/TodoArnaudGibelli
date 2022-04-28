package com.arnogibelli.todo.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class UserInfo {
    @Serializable
    data class UserInfo(
        @SerialName("email")
        val email: String,
        @SerialName("firstname")
        val firstName: String,
        @SerialName("lastname")
        val lastName: String
    )
}