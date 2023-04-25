package ir.saharapps.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ir.saharapps.data.model.FoodOrder
import ir.saharapps.repository.FoodOrderDaoInterfaceImp

fun Route.addFoodOrder(foodOrderDao: FoodOrderDaoInterfaceImp){
    post("/foodOrder"){
        val newFoodOrder = call.receive<FoodOrder>()
        foodOrderDao.addFoodOrder(newFoodOrder)
        call.respond(HttpStatusCode.OK)
    }
}

fun Route.getAllFoodOrderByPhone(foodOrderDao: FoodOrderDaoInterfaceImp){
    get("/getAllFoodOrder/{phone}"){
        val phone = call.parameters["phone"]
        if(phone == null){
            call.respond(HttpStatusCode.BadRequest, "phone number is not valid")
            return@get
        }
        val foodOrder = foodOrderDao.getAllFoodOrderByPhone(phone)
        if(foodOrder == null){
            call.respond(HttpStatusCode.NotFound, "There isn't any food order with this phone number")
            return@get
        }
        call.respond(foodOrder)
    }
}