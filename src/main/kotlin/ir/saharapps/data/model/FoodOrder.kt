package ir.saharapps.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FoodOrder(
    val phoneNumber: String,
    val orderId: Int,
    val foodName: String,
    val foodCount: String,
    val foodCost: String,
    val date: String,
    val time: Int,
    val deliveredTime: Int,
    val addressId: Int,
    val paymentMethod: String,
    val totalCost: Double
)