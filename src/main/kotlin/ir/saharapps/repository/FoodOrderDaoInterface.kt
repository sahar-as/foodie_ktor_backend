package ir.saharapps.repository

import ir.saharapps.data.model.FoodOrder

interface FoodOrderDaoInterface {
    fun init()
    fun addFoodOrder(foodOrder: FoodOrder)
    fun getAllFoodOrderByPhone(phone: String): List<FoodOrder>
}