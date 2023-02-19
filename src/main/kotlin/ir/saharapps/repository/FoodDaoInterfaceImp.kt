package ir.saharapps.repository

import ir.saharapps.model.Food
import ir.saharapps.model.FoodTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class FoodDaoInterfaceImp(val db: Database): FoodDaoInterface {
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



    override fun updateFood() {
        TODO("Not yet implemented")
    }

    override fun deleteFood() {
        TODO("Not yet implemented")
    }
}