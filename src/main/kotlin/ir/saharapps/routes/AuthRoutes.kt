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
            call.respond(HttpStatusCode.OK, "$userId")
        }
    }
}

fun Route.getAllUsers(userDao: UserDaoInterface){
    get("/getAllUsers"){
        call.respond(mapOf("getAllUsers" to userDao.getAllUsers()))
    }
}

fun Route.getUserInfo(userDao: UserDaoInterface){
    get("/getUserInfo/{userId}"){
        val userId = call.parameters["userId"]
        if (userId == null){
            call.respond(HttpStatusCode.BadRequest, "User Id is not valid")
            return@get
        }

        val user = userDao.getUserByUserId(userId)
        if(user == null){
            call.respond(HttpStatusCode.NotFound, "There isn't any user with this Id")
            return@get
        }
        call.respond(user)
    }
}

fun Route.updateUser(userDao: UserDaoInterface){
    get("/updateUserPass/{phone}/{pass}"){
        val phone = call.parameters["phone"]
        val password = call.parameters["pass"]
        if(phone == null || password == null){
            call.respond(HttpStatusCode.BadRequest, "User Id is not valid")
            return@get
        }
        val result = userDao.updateUserPass(phone, password)
        if(result == null){
            call.respond(HttpStatusCode.NotFound, "Update failed, try later")
            return@get
        }
        call.respond(true)
    }
}

fun Route.getUserByPhone(userDao: UserDaoInterface){
    get("/getUserByPhone/{phone}"){
        val phone = call.parameters["phone"]
        if(phone == null){
            call.respond(HttpStatusCode.BadRequest, "User Id is not valid")
            return@get
        }
        val user = userDao.getUserByPhone(phone)
        if (user == null){
            call.respond(HttpStatusCode.NotFound, "There isn't any user with this Id")
            return@get
        }
        call.respond(user)
    }
}