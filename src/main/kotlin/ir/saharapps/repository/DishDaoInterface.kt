package ir.saharapps.repository

import ir.saharapps.model.Food

interface FoodDaoInterface {
    fun init()
    fun addFood(food: Food)
    fun getAllFood(): List<Food>
    fun updateFood()
    fun deleteFood()
}