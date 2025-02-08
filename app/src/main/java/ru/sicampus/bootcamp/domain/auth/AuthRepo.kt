package ru.sicampus.bootcamp.domain.auth

interface AuthRepo {
    suspend fun register(login: String, password: String, firstName: String, secondName: String, lastName: String, organizationName: String, info: String, phoneNumber: String): Result<Unit>
    suspend fun login(login: String, password: String): Result<Unit>

}
