package ir.saharapps.model

import org.jetbrains.exposed.sql.Table


object FoodTable: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val ingredient = varchar("ingredient", 500)
    val image = varchar("image", 150)
    val cost = varchar("cost", 100)
    val rank = double("rank")
    val isAvailable= bool("isAvailable")
    val dishType = varchar("dish_type", 100)

    override val primaryKey = PrimaryKey(id)
}

