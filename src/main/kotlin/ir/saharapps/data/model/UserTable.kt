package ir.saharapps.data.model

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
    val phoneNumber = varchar("phone_number",13)
    val userName = varchar("user_name", 100)
    val password = varchar("password", 100)
    val userId = varchar("user_id", 100)
    val userAddress = varchar("user_address",200)
    val salt = varchar("salt", 100)

    override val primaryKey = PrimaryKey(phoneNumber)

}