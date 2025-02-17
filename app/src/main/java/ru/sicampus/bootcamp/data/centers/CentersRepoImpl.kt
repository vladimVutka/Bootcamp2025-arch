package ru.sicampus.bootcamp.data.list

import ru.sicampus.bootcamp.data.auth.AuthStorageDataSource
import ru.sicampus.bootcamp.data.centers.CentersDto
import ru.sicampus.bootcamp.data.centers.CentersNetworkDataSource
import ru.sicampus.bootcamp.domain.Centers.CentersRepo
import ru.sicampus.bootcamp.domain.list.CentersEntity
import ru.sicampus.bootcamp.domain.list.UserEntity
import ru.sicampus.bootcamp.domain.list.UserRepo

class CentersRepoImpl(
    private val centersNetworkDataSource: CentersNetworkDataSource,
    private val authStorageDataSource: AuthStorageDataSource
) : CentersRepo {
    override suspend fun getCenters(): Result<List<CentersEntity>> {
        val token = authStorageDataSource.token
            ?: return Result.failure(IllegalStateException("token is null"))
        return centersNetworkDataSource.getCenters(token).map { listDto ->
            listDto.mapNotNull { dto ->
                CentersEntity(
                    name = dto.name ?: return@mapNotNull null,
                    address = dto.address ?: return@mapNotNull null,
                    lat = dto.lat?: return@mapNotNull null,
                    lon = dto.lon?: return@mapNotNull null,
                    info = dto.info?: return@mapNotNull null,
                    image_photo = dto.image_photo?: return@mapNotNull null,
                )
            }
        }
    }
    override suspend fun findByNameAdress(name: String): Result<CentersEntity>{
        val token = authStorageDataSource.token?: return Result.failure(IllegalStateException("token is null"))
        return centersNetworkDataSource.findByNameAdress(token, name).map { dto ->
                CentersEntity(
                    name = dto.name,
                    address = dto.address,
                    lat = dto.lat,
                    lon = dto.lon,
                    info = dto.info,
                    image_photo = dto.image_photo
                )
            }
    }
}
