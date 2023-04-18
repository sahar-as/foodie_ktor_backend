package ir.saharapps.repository

import ir.saharapps.data.model.Food
import ir.saharapps.data.model.FoodTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class FoodDaoInterfaceImp(private val db: Database): FoodDaoInterface {
    override fun init() = transaction(db) {
        SchemaUtils.create(FoodTable)
    }

    override fun addFood(food: Food) {
        transaction(db) {
            FoodTable.insert {
                it[name] = food.name
                it[ingredient] = food.ingredient
                it[image] = food.image
                it[cost] = food.cost
                it[rank] = food.rank
                it[isAvailable] = food.isAvailable
                it[dishType] = food.dishType
            }
            Unit
        }
    }
    override fun getFoodById(foodId: Int): Food? =
        transaction(db){
            FoodTable.select{ FoodTable.id eq foodId}.map{
                Food(
                    it[FoodTable.id], it[FoodTable.name], it[FoodTable.ingredient],
                    it[FoodTable.image], it[FoodTable.cost], it[FoodTable.rank],
                    it[FoodTable.isAvailable], it[FoodTable.dishType]
                )
            }.singleOrNull()
        }

    override fun getFoodByDishType(dishType: String): List<Food> =
        transaction(db){
            FoodTable.select{ FoodTable.dishType eq dishType}.map{
                Food(
                    it[FoodTable.id], it[FoodTable.name], it[FoodTable.ingredient],
                    it[FoodTable.image], it[FoodTable.cost], it[FoodTable.rank],
                    it[FoodTable.isAvailable], it[FoodTable.dishType]
                )
            }
        }

    override fun getFavFood(rank: Double) :List<Food> =
        transaction(db){
            FoodTable.select{ FoodTable.rank eq rank}.map{
                Food(
                    it[FoodTable.id], it[FoodTable.name], it[FoodTable.ingredient],
                    it[FoodTable.image], it[FoodTable.cost], it[FoodTable.rank],
                    it[FoodTable.isAvailable], it[FoodTable.dishType]
                )
            }
        }

    override fun getAllFood(): List<Food> =
        transaction(db) {
            FoodTable.selectAll().map {
                Food(
                    it[FoodTable.id], it[FoodTable.name], it[FoodTable.ingredient],
                    it[FoodTable.image], it[FoodTable.cost], it[FoodTable.rank],
                    it[FoodTable.isAvailable], it[FoodTable.dishType]
                )
            }
        }


    override fun updateFood(currentFoodId: Int, newFood: Food) {
        transaction(db){
            FoodTable.update ({ FoodTable.id eq currentFoodId }) {
                it[name] = newFood.name
                it[ingredient] = newFood.ingredient
                it[image] = newFood.image
                it[cost] = newFood.cost
                it[rank] = newFood.rank
                it[isAvailable] = newFood.isAvailable
                it[dishType] = newFood.dishType
            }
        }
    }

    override fun deleteFood(foodId: Int) {
        transaction(db){
            FoodTable.deleteWhere{ FoodTable.id eq foodId}
        }
        Unit
    }
}