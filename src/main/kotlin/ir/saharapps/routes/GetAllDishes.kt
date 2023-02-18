package ir.saharapps.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllFood(){
    get("/getAllFood"){
        call.respond("this is just for test")
    }
}