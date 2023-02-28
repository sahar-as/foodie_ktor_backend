package ir.saharapps.security.hashing

interface HashingInterface {
    fun generateSaltHash(value: String, saltLength: Int = 32): SaltedHash
    fun verifyPassword(password: String, saltedHash: SaltedHash): Boolean
}