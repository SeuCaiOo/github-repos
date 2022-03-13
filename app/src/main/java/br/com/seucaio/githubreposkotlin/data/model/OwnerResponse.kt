package br.com.seucaio.githubreposkotlin.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("login")
    val login: String? = null,
    @SerialName("avatar_url")
    val avatarUrl: String? = null
)