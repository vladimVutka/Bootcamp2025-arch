package ru.sicampus.bootcamp.domain.auth

class RegisterUserUseCase(
    private val authRepo: AuthRepo,
) {
    suspend operator fun invoke(login: String, password: String, firstName: String, secondName: String, lastName: String, organizationName: String, info: String, phoneNumber: String): Result<Unit> {
        return authRepo.register(login, password, firstName, secondName, lastName, organizationName, phoneNumber, info).mapCatching {
            authRepo.login(login, password)
        }
    }
}
