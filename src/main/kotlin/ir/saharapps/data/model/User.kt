package ir.saharapps.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val phoneNumber: String,
    val phoneValidation: Boolean = false,
    val userName: String = "",
    val password: String = "",
    val userId: String = "",
    val userAddress: String = ""
)
