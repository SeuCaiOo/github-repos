package br.com.seucaio.githubreposkotlin.presentation.compose

import br.com.seucaio.githubreposkotlin.domain.entity.Repo

data class ListUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val repoItems: List<Repo> = listOf(),
)