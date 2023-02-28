package ir.saharapps.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val phoneNumber: String,
    val phoneValidation: Boolean,
    val userName: String,
    val password: String,
    val userId: String
)
