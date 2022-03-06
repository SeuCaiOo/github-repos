package br.com.seucaio.githubreposkotlin.presentation.repository

import br.com.seucaio.githubreposkotlin.domain.entity.Repositories

data class RepositoryListUiState(
    val repositories: Repositories? = null,
    var isLoading: Boolean = false,
    val hasError : Boolean = false,
) {

    fun setRepos(list: Repositories): RepositoryListUiState {
        return this.copy(repositories = list)
    }

    fun setLoading(loading: Boolean): RepositoryListUiState {
        return this.copy(isLoading = loading)
    }

    fun setError(error: Boolean): RepositoryListUiState {
        return this.copy(hasError = error)
    }
}