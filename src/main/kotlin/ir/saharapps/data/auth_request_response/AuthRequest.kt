package ir.saharapps.data.auth_request_response

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val phoneNumber: String,
    val userName: String,
    val password: String
)
