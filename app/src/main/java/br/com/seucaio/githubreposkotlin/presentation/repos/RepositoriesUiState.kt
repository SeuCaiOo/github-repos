package br.com.seucaio.githubreposkotlin.presentation.repos

import br.com.seucaio.githubreposkotlin.data.model.RepositoriesResponse

data class RepositoriesUiState(
    val repos: RepositoriesResponse? = null,
    var isLoading: Boolean = false,
    val hasError : Boolean = false,
) {

    fun setRepos(list: RepositoriesResponse): RepositoriesUiState {
        return this.copy(repos = list)
    }

    fun setLoading(loading: Boolean): RepositoriesUiState {
        return this.copy(isLoading = loading)
    }

    fun setError(error: Boolean): RepositoriesUiState {
        return this.copy(hasError = error)
    }
}