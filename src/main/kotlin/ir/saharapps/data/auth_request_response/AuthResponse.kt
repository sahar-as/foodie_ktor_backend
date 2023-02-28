package ir.saharapps.data.auth_request_response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val token: String
)
