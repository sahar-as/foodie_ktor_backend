package ir.saharapps.repository

import ir.saharapps.data.model.User

interface UserDaoInterface {
    fun init()
    fun addUser(user: User): Boolean
    fun updateUserInfo(user: User): User?
    fun getUserByPhone(phoneNumber: String): User?
}