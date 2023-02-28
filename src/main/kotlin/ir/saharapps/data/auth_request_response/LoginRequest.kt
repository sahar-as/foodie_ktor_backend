package ir.saharapps.data.auth_request_response

data class LoginRequest(
    val phoneNumber: String,
    val password: String
)
