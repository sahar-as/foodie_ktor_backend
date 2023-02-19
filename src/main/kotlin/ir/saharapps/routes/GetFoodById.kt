package ir.saharapps.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.saharapps.repository.FoodDaoInterfaceImp

fun Route.getFoodById(dao: FoodDaoInterfaceImp){
    get("/getFood/{foodId}"){
        val foodId = call.parameters["foodId"]?.toIntOrNull()
        if (foodId == null){
            call.respond(HttpStatusCode.BadRequest, "Food Id is not valid")
            return@get
        }

        val food = dao.getFoodById(foodId)
        if(food == null){
            call.respond(HttpStatusCode.NotFound, "There isn't any food with this Id")
            return@get
        }

        call.respond(food)
    }
}