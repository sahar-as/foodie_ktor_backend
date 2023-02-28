package ir.saharapps.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.saharapps.data.auth_request_response.AuthRequest
import ir.saharapps.data.auth_request_response.LoginRequest
import ir.saharapps.data.model.User
import ir.saharapps.repository.UserDaoInterface
import ir.saharapps.security.hashing.HashingInterface
import ir.saharapps.security.token.TokenConfig
import ir.saharapps.security.token.TokenInterface

fun Route.signup(
    hashing: HashingInterface,
    userData: UserDaoInterface
){
    post("/signup"){
        val request = call.receiveNullable<AuthRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldsBlank = request.phoneNumber.isBlank() || request.userName.isBlank() || request.password.isBlank()
        val isPassTooShort = request.password.length < 8
        if (areFieldsBlank || isPassTooShort){
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val saltedHash = hashing.generateSaltHash(request.password)
        val user = User(
            phoneNumber = request.phoneNumber,
            userName = request.userName,
            password = request.password,
            salt = saltedHash.salt
        )

        val insertStatus = userData.addUser(user)
        if(!insertStatus){
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }
}

