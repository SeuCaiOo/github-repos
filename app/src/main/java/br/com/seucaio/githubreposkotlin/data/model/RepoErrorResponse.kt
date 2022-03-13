package br.com.seucaio.githubreposkotlin.data.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoErrorResponse(
    @SerialName("documentation_url")
    val documentationUrl: String,
    @SerialName("errors")
    val errors: List<ErrorResponse>?,
    @SerialName("message")
    val message: String
) {
    @Serializable
    data class ErrorResponse(
        @SerialName("code")
        val code: String,
        @SerialName("field")
        val `field`: String,
        @SerialName("resource")
        val resource: String
    )
}