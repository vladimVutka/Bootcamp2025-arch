package ru.sicampus.bootcamp.domain.Centers

import ru.sicampus.bootcamp.data.list.CentersRepoImpl

class CenterGetDataUseCase (
    private val centersRepoImpl: CentersRepoImpl,
    private val name: String,
) {

    suspend operator fun invoke() = centersRepoImpl.findByNameAdress(name)
}
