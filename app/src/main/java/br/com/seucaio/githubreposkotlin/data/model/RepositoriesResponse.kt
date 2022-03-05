package br.com.seucaio.githubreposkotlin.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoriesResponse(
    @SerialName("incomplete_results")
    val incompleteResults: Boolean = false,
    @SerialName("items")
    val items: List<RepositoryResponse>? = null,
    @SerialName("total_count")
    val totalCount: Int? = null,
)