package ir.saharapps.routes

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.saharapps.repository.FoodDaoInterfaceImp

//fun Route.getAllFood(foodDao: FoodDaoInterfaceImp){
//    get("/getAllFood"){
//        call.respond(mapOf("getAllFood" to foodDao.getAllFood()))
//    }
//}