package br.com.seucaio.githubreposkotlin.presentation.repository

import br.com.seucaio.githubreposkotlin.domain.entity.Repository

data class RepositoryListUiState(
    val repositories: List<Repository>? = null,
    var isLoading: Boolean = false,
    val hasError : Boolean = false,
) {

    fun setRepos(list: List<Repository>): RepositoryListUiState {
        return this.copy(repositories = list)
    }

    fun setLoading(loading: Boolean): RepositoryListUiState {
        return this.copy(isLoading = loading)
    }

    fun setError(error: Boolean): RepositoryListUiState {
        return this.copy(hasError = error)
    }
}