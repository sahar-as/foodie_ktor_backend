package ir.saharapps.model

import kotlinx.serialization.Serializable

@Serializable
data class Food(
    val id: Int = 0,
    val name: String,
    val ingredient: String,
    val image: String,
    val cost: String,
    val rank: Double,
    val isAvailable: Boolean,
    val dishType: String
)