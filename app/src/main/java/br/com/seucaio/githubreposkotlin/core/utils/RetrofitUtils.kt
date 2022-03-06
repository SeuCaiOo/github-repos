package br.com.seucaio.githubreposkotlin.core.utils

import androidx.annotation.VisibleForTesting
import br.com.seucaio.githubreposkotlin.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Modifier.PRIVATE

private const val JSON_MEDIA_TYPE = "application/json"

@Suppress("EXPERIMENTAL_API_USAGE")
fun getKSerializationConverterFactory(isDebug: Boolean): Converter.Factory {
    val contentType = JSON_MEDIA_TYPE.toMediaType()
    val json = buildJson(isDebug)

    return json.asConverterFactory(contentType)
}

private fun buildJson(isDebug: Boolean): Json {
    return if (isDebug) {
        Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
            isLenient = true
        }
    } else {
        Json {
            encodeDefaults = true
            ignoreUnknownKeys = true
        }
    }
}

@VisibleForTesting(otherwise = PRIVATE)
fun createRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
): Retrofit {
    val retrofitBuilder = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(getKSerializationConverterFactory(BuildConfig.DEBUG))
    okHttpClient?.let { retrofitBuilder.client(okHttpClient) }
    return retrofitBuilder.build()
}
