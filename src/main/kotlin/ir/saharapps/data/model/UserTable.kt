package ir.saharapps.data.model

import org.jetbrains.exposed.sql.Table

object UserTable: Table() {
    private val phoneNumber = varchar("phone_number",13)
    val phoneValidation= bool("phone_validate")
    val userName = varchar("user_name", 50)
    val password = varchar("password", 100)
    val userId = varchar("user_id", 100)

    override val primaryKey = PrimaryKey(phoneNumber)
}