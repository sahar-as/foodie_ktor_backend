package ir.saharapps.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.saharapps.data.model.Food
import ir.saharapps.repository.FoodDaoInterfaceImp

//fun Route.addDish(foodDao: FoodDaoInterfaceImp){
//    post("/food"){
//        val newFood = call.receive<Food>()
//        val insertedFood = foodDao.addFood(newFood)
//        call.respond(insertedFood)
//    }
//}