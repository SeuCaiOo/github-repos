package br.com.seucaio.githubreposkotlin.domain.entity

data class RepoSearch(
    val incompleteResults: Boolean = false,
    val items: List<Repo>? = null,
    val totalCount: Int? = null,
)