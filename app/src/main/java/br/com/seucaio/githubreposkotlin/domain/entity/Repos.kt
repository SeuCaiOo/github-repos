package br.com.seucaio.githubreposkotlin.domain.entity

data class Repos(
    val incompleteResults: Boolean = false,
    val items: List<Repo>? = null,
    val totalCount: Int? = null,
)