package br.com.seucaio.githubreposkotlin.domain.entity

data class Repo(
    val id: Int? = null,
    val name: String? = null,
    val fullName: String? = null,
    val description: String? = null,
    val language: String? = null,
    val forksCount: Int? = null,
    val stargazersCount: Int? = null,
    val owner: Owner? = null
)