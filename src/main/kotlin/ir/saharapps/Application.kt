package ir.saharapps

import io.ktor.server.application.*
import ir.saharapps.plugins.*
import ir.saharapps.security.hashing.HashingInterfaceImp
import ir.saharapps.security.token.TokenConfig
import ir.saharapps.security.token.TokenInterfaceImp

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    val tokenService = TokenInterfaceImp()
    val tokenConfig = TokenConfig(
        issuer = environment.config.property("jwt.issuer").getString(),
        audience = environment.config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60 * 60 * 24,
        secret = System.getenv("JWT_SECRET")
    )
    val hashingService = HashingInterfaceImp()


    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureSecurity(tokenConfig)
    configureRouting()
}
