package ir.saharapps.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.saharapps.data.auth_request_response.AuthRequest
import ir.saharapps.data.auth_request_response.AuthResponse
import ir.saharapps.data.auth_request_response.LoginRequest
import ir.saharapps.data.model.User
import ir.saharapps.repository.UserDaoInterface
import ir.saharapps.security.hashing.HashingInterface
import ir.saharapps.security.hashing.SaltedHash
import ir.saharapps.security.token.TokenClaim
import ir.saharapps.security.token.TokenConfig
import ir.saharapps.security.token.TokenInterface
import ir.saharapps.util.GenerateRandomString

fun Route.signup(
    hashing: HashingInterface,
    userDao: UserDaoInterface,
    randomString: GenerateRandomString
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
            password = saltedHash.hash,
            salt = saltedHash.salt,
            userId = randomString.generate()
        )

        val insertStatus = userDao.addUser(user)
        if(!insertStatus){
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        call.respond(HttpStatusCode.OK)
    }
}

fun Route.signIn(
    hashing: HashingInterface,
    userDao: UserDaoInterface,
    tokenInterface: TokenInterface,
    tokenConfig: TokenConfig
){
    post("/signin"){
        val request = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val areFieldBlank = request.phoneNumber.isBlank() || request.password.isBlank()
        val isPassTooShort = request.password.length < 8
        if(areFieldBlank || isPassTooShort){
            call.respond(HttpStatusCode.Conflict)
            return@post
        }

        val findUser = userDao.getUserByPhone(request.phoneNumber)
        if (findUser == null){
            call.respond(HttpStatusCode.Conflict, "No user found with this phone number")
            return@post
        }

        val isValidPassword = hashing.verifyPassword(
            password = request.password,
            saltedHash = SaltedHash(findUser.password, findUser.salt)
        )
        if(!isValidPassword){
            call.respond(HttpStatusCode.Conflict, "Incorrect password")
            return@post
        }

        val token = tokenInterface.generate(
            config = tokenConfig,
            TokenClaim(name = "UserId", value = findUser.userId)
        )

        call.respond(status = HttpStatusCode.OK, message = AuthResponse(token))
    }
}

fun Route.authentication(){
    authenticate {
        get ("authenticate"){
            call.respond(HttpStatusCode.OK)
        }
    }
}

fun Route.getSecretInfo(){
    authenticate {
        get("secret"){
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("UserId", String::class)
            call.respond(HttpStatusCode.OK, "your user Id is $userId")
        }
    }
}

fun Route.getAllUsers(userDao: UserDaoInterface){
    get("/getAllUsers"){
        call.respond(mapOf("getAllUsers" to userDao.getAllUsers()))
    }
}