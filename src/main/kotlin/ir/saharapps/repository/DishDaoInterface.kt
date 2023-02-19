package ir.saharapps.repository

import ir.saharapps.model.Food

interface FoodDaoInterface {
    fun init()
    fun addFood(food: Food)
    fun getFoodById(foodId: Int): Food?
    fun getAllFood(): List<Food>
    fun updateFood(currentFoodId: Int, newFood: Food)
    fun deleteFood(foodId: Int)
}