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