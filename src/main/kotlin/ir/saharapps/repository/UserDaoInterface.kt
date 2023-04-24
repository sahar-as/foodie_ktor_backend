package ir.saharapps.repository

import ir.saharapps.data.model.User

interface UserDaoInterface {
    fun init()
    fun addUser(user: User): Boolean
    fun updateUserInfo(user: User): User?
    fun updateUserPass(phone: String, userPass: String): Boolean?
    fun getUserByPhone(phoneNumber: String): User?
    fun getUserByUserId(userId: String): User?
    fun getAllUsers(): List<User>?
}