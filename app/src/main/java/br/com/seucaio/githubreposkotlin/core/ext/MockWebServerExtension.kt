package br.com.seucaio.githubreposkotlin.core.ext

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import java.nio.charset.StandardCharsets

fun MockWebServer.enqueueResponse(code: Int, fileName: String? = null) {
    fileName?.let {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    } ?: run {
        enqueue(MockResponse().setResponseCode(code))
    }
}

fun MockWebServer.getResponse(code: Int, fileName: String? = null): MockResponse {
    return fileName?.let {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            MockResponse()
                .setResponseCode(code)
                .setBody(source.readString(StandardCharsets.UTF_8))
        }
    } ?: run {
        MockResponse().setResponseCode(code)
    }
}

fun String.readResponse(): String {
    val inputStream = javaClass.classLoader?.getResourceAsStream(this)
    val source = inputStream?.let { inputStream.source().buffer() }
    return source?.let { source.readString(StandardCharsets.UTF_8) } ?: return ""
}