package ir.saharapps.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import ir.saharapps.routes.getAllFood

fun Application.configureRouting() {
    routing {
        getAllFood()
    }
}
