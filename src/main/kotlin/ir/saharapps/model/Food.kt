package ir.saharapps.model

data class Food(
    val name: String,
    val ingredient: List<String>,
    val image: String,
    val cost: String,
    val rank: Double,
    val isAvailable: Boolean,
    val dishType: String
)
