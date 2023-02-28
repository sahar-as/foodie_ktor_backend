package ir.saharapps.data.auth_request_response

data class AuthRequest(
    val phoneNumber: String,
    val userName: String,
    val password: String
)
