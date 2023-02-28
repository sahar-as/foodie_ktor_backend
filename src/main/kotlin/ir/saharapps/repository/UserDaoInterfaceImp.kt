package ir.saharapps.repository

import ir.saharapps.data.model.FoodTable
import ir.saharapps.data.model.User
import ir.saharapps.data.model.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserDaoInterfaceImp(private val db : Database): UserDaoInterface {
    override fun init()  = transaction(db) {
        SchemaUtils.create(UserTable)
    }

    override fun addUserPhone(user: User): User? {
        transaction(db) {
            UserTable.insert {
                it[phoneNumber] = user.phoneNumber
                it[phoneValidation] = user.phoneValidation
                it[userName] = user.userName
                it[password] = user.password
                it[userId] = user.userId
                it[userAddress] = user.userAddress
            }
        }
        return user
    }
    override fun updateUserInfo(user: User): User? {
        transaction(db) {
            UserTable.update ({UserTable.phoneNumber eq user.phoneNumber}) {
                it[phoneValidation] = user.phoneValidation
                it[userName] = user.userName
                it[password] = user.password
                it[userId] = user.userId
                it[userAddress] = user.userAddress
            }
        }
        return user
    }

    override fun getUserByPhone(phoneNumber: String): User? =
        transaction(db) {
            UserTable.select {UserTable.phoneNumber eq phoneNumber}.map {
                User(
                    it[UserTable.phoneNumber], it[UserTable.phoneValidation],
                    it[UserTable.userName], it[UserTable.password],
                    it[UserTable.userId], it[UserTable.userAddress]
                )
            }.singleOrNull()
        }
}