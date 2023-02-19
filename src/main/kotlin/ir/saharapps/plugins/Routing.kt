package ir.saharapps.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import ir.saharapps.repository.FoodDaoInterfaceImp
import ir.saharapps.routes.addDish
import ir.saharapps.routes.getAllFood
import org.jetbrains.exposed.sql.Database

fun Application.configureRouting() {

    val foodDao = FoodDaoInterfaceImp(Database.connect("jdbc:h2:file:./build/db", driver = "org.h2.Driver"))
    foodDao.init()

    routing {
        getAllFood(foodDao)
        addDish(foodDao)
    }
}
