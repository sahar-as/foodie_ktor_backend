package ir.saharapps.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.saharapps.data.model.Food
import ir.saharapps.repository.FoodDaoInterfaceImp

fun Route.addDish(foodDao: FoodDaoInterfaceImp){
    post("/food"){
        val newFood = call.receive<Food>()
        val insertedFood = foodDao.addFood(newFood)
        call.respond(insertedFood)
    }
}

fun Route.getAllFood(foodDao: FoodDaoInterfaceImp){
    get("/getAllFood"){
        call.respond(mapOf("getAllFood" to foodDao.getAllFood()))
    }
}

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

fun Route.deleteFoodById(dao: FoodDaoInterfaceImp){
    delete("/deleteFood/{foodId}"){
        val foodId = call.parameters["foodId"]?.toIntOrNull()
        if (foodId == null){
            call.respond(HttpStatusCode.BadRequest, "Food Id is not valid")
            return@delete
        }
        dao.deleteFood(foodId)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.getFoodByDishType(dao: FoodDaoInterfaceImp){
    get("/getFoodByDishType/{dishType}"){
        val dishType = call.parameters["dishType"]?: ""
        if (dishType.isEmpty()){
            call.respond(HttpStatusCode.BadRequest, "Dish type is not valid")
            return@get
        }
        call.respond(mapOf("getAllFood" to dao.getFoodByDishType(dishType)))
    }
}

fun Route.getFavFoodList(dao: FoodDaoInterfaceImp){
    get("/getFavFood/{rank}"){
        val rank = call.parameters["rank"]?.toDoubleOrNull()
        if (rank == null){
            call.respond(HttpStatusCode.BadRequest, "rank is not valid")
            return@get
        }
        call.respond(mapOf("getAllFood" to dao.getFavFood(rank)))
    }
}