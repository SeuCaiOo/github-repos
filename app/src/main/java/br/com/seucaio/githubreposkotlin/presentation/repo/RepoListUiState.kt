package br.com.seucaio.githubreposkotlin.presentation.repo

import br.com.seucaio.githubreposkotlin.domain.entity.Repo

data class RepoListUiState(
    val repos: List<Repo>? = null,
    var isLoading: Boolean = false,
    val hasError : Boolean = false,
) {

    fun setRepos(list: List<Repo>): RepoListUiState {
        return this.copy(repos = list)
    }

    fun setLoading(loading: Boolean): RepoListUiState {
        return this.copy(isLoading = loading)
    }

    fun setError(error: Boolean): RepoListUiState {
        return this.copy(hasError = error)
    }
}