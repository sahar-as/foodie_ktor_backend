package ir.saharapps.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import ir.saharapps.repository.FoodDaoInterfaceImp
import ir.saharapps.repository.UserDaoInterfaceImp
import ir.saharapps.routes.*
import ir.saharapps.security.hashing.HashingInterface
import ir.saharapps.security.token.TokenConfig
import ir.saharapps.security.token.TokenInterface
import ir.saharapps.util.GenerateRandomString
import org.jetbrains.exposed.sql.Database

fun Application.configureRouting(
    hashing: HashingInterface,
    token: TokenInterface,
    tokenConfig: TokenConfig,
    randomString: GenerateRandomString
) {

    val foodDao = FoodDaoInterfaceImp(Database.connect("jdbc:h2:file:./build/db", driver = "org.h2.Driver"))
    foodDao.init()

    val userDao = UserDaoInterfaceImp(Database.connect("jdbc:h2:file:./build/db", driver = "org.h2.Driver"))
    userDao.init()

    routing {
        //FoodRoutes
        getAllFood(foodDao)
        addDish(foodDao)
        getFoodById(foodDao)
        deleteFoodById(foodDao)
        getFoodByDishType(foodDao)

        //UserRoutes
        signup(hashing, userDao, randomString)
        signIn(hashing, userDao,token, tokenConfig)
        authentication()
        getSecretInfo()
        getAllUsers(userDao)
        getUserInfo(userDao)

        static("/image") {
            resources("image")
        }
    }
}
