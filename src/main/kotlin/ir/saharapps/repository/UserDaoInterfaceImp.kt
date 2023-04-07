package ir.saharapps.repository

import ir.saharapps.data.model.User
import ir.saharapps.data.model.UserTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserDaoInterfaceImp(private val db : Database): UserDaoInterface {
    override fun init()  = transaction(db) {
        SchemaUtils.create(UserTable)
    }

    override fun addUser(user: User): Boolean {
        val insertCount = transaction(db) {
            UserTable.insert {
                it[phoneNumber] = user.phoneNumber
                it[userName] = user.userName
                it[password] = user.password
                it[salt] = user.salt
                it[userId] = user.userId
                it[userAddress] = "null"
            }
        }

        if (insertCount.insertedCount>0){
            return true
        }
        return false
    }
    override fun updateUserInfo(user: User): User? {
        transaction(db) {
            UserTable.update ({UserTable.phoneNumber eq user.phoneNumber}) {
                it[userName] = user.userName
                it[password] = user.password
                it[userAddress] = user.userAddress
            }
        }
        return user
    }

    override fun getUserByPhone(phoneNumber: String): User? =
        transaction(db) {
            UserTable.select {UserTable.phoneNumber eq phoneNumber}.map {
                User(
                    it[UserTable.phoneNumber], it[UserTable.userName], it[UserTable.password],
                    it[UserTable.userId], it[UserTable.userAddress], it[UserTable.salt]
                )
            }.singleOrNull()
        }

    override fun getUserByUserId(userId: String): User? =
        transaction(db) {
            UserTable.select {UserTable.userId eq userId}.map {
                User(
                    it[UserTable.phoneNumber], it[UserTable.userName], it[UserTable.password],
                    it[UserTable.userId], it[UserTable.userAddress], it[UserTable.salt]
                )
            }.singleOrNull()
        }

    override fun getAllUsers(): List<User>? =
        transaction(db){
            UserTable.selectAll().map{
                User(
                    it[UserTable.phoneNumber], it[UserTable.userName], it[UserTable.password],
                    it[UserTable.userId], it[UserTable.userAddress], it[UserTable.salt]
                )
            }
        }
}