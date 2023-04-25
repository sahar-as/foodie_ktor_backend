package ir.saharapps.repository

import ir.saharapps.data.model.FoodOrder
import ir.saharapps.data.model.FoodOrderTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class FoodOrderDaoInterfaceImp(private val db: Database): FoodOrderDaoInterface {
    override fun init() {
        SchemaUtils.create(FoodOrderTable)
    }

    override fun addFoodOrder(foodOrder: FoodOrder) {
        transaction(db) {
            FoodOrderTable.insert {
                it[phoneNumber] = foodOrder.phoneNumber
                it[orderId] = foodOrder.orderId
                it[foodName] = foodOrder.foodName
                it[foodCount] = foodOrder.foodCount
                it[foodCost] = foodOrder.foodCost
                it[date] = foodOrder.date
                it[time] = foodOrder.time
                it[deliveredTime] = foodOrder.deliveredTime
                it[addressId] = foodOrder.addressId
                it[paymentMethod] = foodOrder.paymentMethod
                it[totalCost] = foodOrder.totalCost

            }
            Unit
        }
    }

    override fun getAllFoodOrderByPhone(phone: String): List<FoodOrder> =
        transaction(db) {
            FoodOrderTable.select{FoodOrderTable.phoneNumber eq phone}.map{
               FoodOrder(
                   it[FoodOrderTable.phoneNumber], it[FoodOrderTable.orderId],
                   it[FoodOrderTable.foodName], it[FoodOrderTable.foodCount],
                   it[FoodOrderTable.foodCost], it[FoodOrderTable.date],
                   it[FoodOrderTable.time], it[FoodOrderTable.deliveredTime],
                   it[FoodOrderTable.addressId], it[FoodOrderTable.paymentMethod],
                   it[FoodOrderTable.totalCost]
               )
            }
        }

}