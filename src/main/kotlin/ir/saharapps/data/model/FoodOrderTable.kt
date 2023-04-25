package ir.saharapps.data.model

import org.jetbrains.exposed.sql.Table
object FoodOrderTable: Table() {

    val phoneNumber = varchar("phone", 20)
    val orderId = integer("id")
    val foodName =  varchar("foodName", 10)
    val foodCount = varchar("foodCount", 5)
    val foodCost = varchar("foodCost", 10)
    val date = varchar("date", 15)
    val time = integer("time")
    val deliveredTime = integer("deliveredTime")
    val addressId = integer("addressId")
    val paymentMethod = varchar("paymentMethod", 10)
    val totalCost = double("totalCost")

    override val primaryKey = PrimaryKey(FoodOrderTable.orderId)
}