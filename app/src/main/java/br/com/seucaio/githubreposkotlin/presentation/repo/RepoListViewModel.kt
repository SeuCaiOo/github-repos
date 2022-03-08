package br.com.seucaio.githubreposkotlin.presentation.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoSearchKotlinUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RepoListViewModel(
    private val useCase: GetRepoSearchKotlinUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private var nextPage = 1
    private var getMore = false

    private val _uiState = MutableStateFlow(RepoListUiState())
    val uiState: StateFlow<RepoListUiState> = _uiState

    fun getRepositories() {
        getMore = false
        viewModelScope.launch {
            useCase.invoke(nextPage)
                .flowOn(dispatcher)
                .onStart { setState(uiState.value.setLoading(true)) }
                .catch { setState(uiState.value.setError(true)) }
                .onCompletion { setState(uiState.value.setLoading(false)) }
                .collect { handleSuccess(it.items) }
        }
    }

    fun getNextPage() {
        if (getMore) getRepositories()
    }

    private fun handleSuccess(response: List<Repo>?) {
        if (response.isNullOrEmpty()) {
            setState(RepoListUiState().setError(true))
        } else {
            val list = if (getMore) {
                uiState.value.repos?.toMutableList()?.let {
                    return response.forEach { response -> it.add(response) }
                } ?: emptyList()
            } else {
                response
            }
            setState(RepoListUiState().setRepos(list))
            nextPage += 1
            getMore = true
        }
    }

    private fun setState(state: RepoListUiState) {
        _uiState.value = state
    }
}