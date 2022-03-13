package br.com.seucaio.githubreposkotlin.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepoResponse(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("language")
    val language: String? = null,
    @SerialName("forks_count")
    val forksCount: Int? = null,
    @SerialName("stargazers_count")
    val stargazersCount: Int? = null,
    @SerialName("owner")
    val owner: OwnerResponse? = null
)