package br.com.seucaio.githubreposkotlin.presentation.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.seucaio.githubreposkotlin.domain.entity.Repositories
import br.com.seucaio.githubreposkotlin.domain.entity.Repository
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepositoryListKotlinUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RepositoryListViewModel(
    private val useCase: GetRepositoryListKotlinUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private var nextPage = 1
    private var getMore = false

    private val _uiState = MutableStateFlow(RepositoryListUiState())
    val uiState: StateFlow<RepositoryListUiState> = _uiState

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

    private fun handleSuccess(response: List<Repository>?) {
        if (response.isNullOrEmpty()) {
            setState(RepositoryListUiState().setError(true))
        } else {
            val list = if (getMore) {
                uiState.value.repositories?.toMutableList()?.let {
                    return response.forEach { response -> it.add(response) }
                } ?: emptyList()
            } else {
                response
            }
            setState(RepositoryListUiState().setRepos(list))
            nextPage += 1
            getMore = true
        }
    }

    private fun setState(state: RepositoryListUiState) {
        _uiState.value = state
    }
}