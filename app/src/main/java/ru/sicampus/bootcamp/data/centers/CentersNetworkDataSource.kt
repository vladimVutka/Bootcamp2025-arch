package ru.sicampus.bootcamp.data.centers

import android.util.Log
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.sicampus.bootcamp.data.Network
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

class CentersNetworkDataSource {
    suspend fun getCenters(
        token: String
    ): Result<List<CentersDto>> = withContext(Dispatchers.IO){
        runCatching {
            val result = Network.client.get("http://192.168.1.102:8080/api/1.0/organization/organizations"){
            headers {
                append(HttpHeaders.Authorization, token)
            }
            }
            if(result.status != HttpStatusCode.OK){
                error("Status ${result.status}")
            }
            Log.d("result.status", "${result}")
            result.body()
        }

    }
    suspend fun findByNameAdress(token: String, name: String) : Result<CentersDto> = withContext(Dispatchers.IO) {
        runCatching {
            Log.d("name", "${name}")
            val encodedName = URLEncoder.encode(name, StandardCharsets.UTF_8.toString()).replace("+", "%20")
            val result = Network.client.get("http://192.168.1.102:8080/api/1.0/organization/$encodedName") {
                headers {
                    append(HttpHeaders.Authorization, token)
                }
            }
            if(result.status != HttpStatusCode.OK){
                error("Status ${result.status}")
            }
            result.body()
        }
    }
}
