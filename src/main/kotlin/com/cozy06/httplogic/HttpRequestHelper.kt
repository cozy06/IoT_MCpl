package httplogic

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import thingsPost

class HttpRequestHelper {
    companion object {
        private const val TAG = "RestFulRepository"
        const val base_url = "http://0.0.0.0:8080"
    }

    private val client: HttpClient

    private val json = Json {
        ignoreUnknownKeys = true // 모델에 없고, json에 있는경우 해당 key 무시
        prettyPrint = true
        isLenient = true // "" 따옴표 잘못된건 무시하고 처리
        encodeDefaults = true //null 인 값도 json에 포함 시킨다.
    }
    init {
        client = HttpClient(CIO) {
            defaultRequest {
                header("Accept", "application/json")
                header("Content-type", "application/json")
            }
            install(ContentNegotiation){
                json(json)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 10_000L
                connectTimeoutMillis = 10_000L
                socketTimeoutMillis = 10_000L
            }
            install(Logging) {
//                logger = object: Logger {
//                    override fun log(message: String) {
//                        Log.d(TAG, message)
//                    }
//                }
                level = LogLevel.ALL
            }
        }
    }

    @Throws
    suspend fun requestKtorIo_things(product_name: String) =
        client.post(base_url + "/thingsclick") {
            contentType(ContentType.Application.Json)
            setBody(thingsPost(product_name))
        }.bodyAsText()

    @Throws
    suspend fun requestKtorIo_IoTList() =
        client.post(base_url + "/IoTList"){
        }.bodyAsText()
}