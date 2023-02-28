package ir.saharapps.data.model

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
    val phoneNumber = varchar("phone_number",13)
    val phoneValidation= bool("phone_validate")
    val userName = varchar("user_name", 50)
    val password = varchar("password", 100)
    val userId = varchar("user_id", 100)
    val userAddress = varchar("user_address",200)

    override val primaryKey = PrimaryKey(phoneNumber)
}