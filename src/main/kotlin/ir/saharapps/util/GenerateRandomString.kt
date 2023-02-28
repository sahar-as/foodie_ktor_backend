package ir.saharapps.util

class GenerateRandomString {
    private val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    fun generate(): String{
        return  List(20) { alphabet.random() }.joinToString("")
    }
}