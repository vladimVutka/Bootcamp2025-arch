package ru.sicampus.bootcamp.data.auth

import ru.sicampus.bootcamp.domain.auth.AuthRepo

class AuthRepoImpl(
    private val authNetworkDataSource: AuthNetworkDataSource,
    private val authStorageDataSource: AuthStorageDataSource,
) : AuthRepo {

    override suspend fun register(login: String, password: String, name: String, secondName: String, lastName: String, organizationName: String, info: String, phoneNumber: String): Result<Unit> {
        return authNetworkDataSource.register(login, password, name, secondName, lastName, organizationName, info, phoneNumber)
    }

    override suspend fun login(login: String, password: String): Result<Unit> {
        val token = authStorageDataSource.updateToken(login, password)
        return authNetworkDataSource.login(token).onFailure {
            authStorageDataSource.clear()
        }
    }
}
