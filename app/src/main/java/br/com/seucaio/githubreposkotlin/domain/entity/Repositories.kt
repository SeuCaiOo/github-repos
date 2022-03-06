package br.com.seucaio.githubreposkotlin.domain.entity

data class Repositories(
    val incompleteResults: Boolean = false,
    val items: List<Repository>? = null,
    val totalCount: Int? = null,
)