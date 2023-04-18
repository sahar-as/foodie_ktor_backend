package ir.saharapps.repository

import ir.saharapps.data.model.Food

interface FoodDaoInterface {
    fun init()
    fun addFood(food: Food)
    fun getFoodById(foodId: Int): Food?
    fun getFoodByDishType(dishType: String): List<Food>
    fun getFavFood(rank: Double): List<Food>
    fun getAllFood(): List<Food>
    fun updateFood(currentFoodId: Int, newFood: Food)
    fun deleteFood(foodId: Int)
}