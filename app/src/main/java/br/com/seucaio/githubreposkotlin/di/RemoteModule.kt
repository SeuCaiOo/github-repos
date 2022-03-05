package br.com.seucaio.githubreposkotlin.di

import br.com.seucaio.githubreposkotlin.data.api.GitHubService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import br.com.seucaio.githubreposkotlin.core.ext.addCallAdapterFactories
import br.com.seucaio.githubreposkotlin.core.ext.addConverterFactories
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

private const val READ_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 10L
private const val CONNECT_TIMEOUT = 30L
private const val MEDIA_TYPE_JSON = "application/json"

@Suppress("EXPERIMENTAL_API_USAGE")
object RemoteModule {
    fun load() {
        loadKoinModules(module {
            single<Retrofit> {
                Retrofit.Builder()
                    .client(get<OkHttpClient>())
                    .baseUrl(GitHubService.BASE_URL)
                    .addCallAdapterFactories(getAll())
                    .addConverterFactories(getAll())
                    .build()
            }

            factory {
                OkHttpClient.Builder().apply {
                    readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                }
                    .addInterceptor(get<Interceptor>())
                    .build()
            }

            factory {
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            } bind Interceptor::class


            single {
                val contentType = MEDIA_TYPE_JSON.toMediaType()
                val json = Json {
                    encodeDefaults = true
                    ignoreUnknownKeys = true
                    isLenient = true
                }
                json.asConverterFactory(contentType)
            } bind Converter.Factory::class

            factory { get<Retrofit>().create(GitHubService::class.java) }
        })
    }
}