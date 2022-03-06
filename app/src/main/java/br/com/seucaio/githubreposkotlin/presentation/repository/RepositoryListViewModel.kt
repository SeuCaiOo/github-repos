package br.com.seucaio.githubreposkotlin.presentation.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.seucaio.githubreposkotlin.domain.entity.Repositories
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepositoryListKotlinUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RepositoryListViewModel(
    private val useCase: GetRepositoryListKotlinUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepositoryListUiState())
    val uiState: StateFlow<RepositoryListUiState> = _uiState

    fun getRepositories() {
        viewModelScope.launch {
            useCase.invoke()
                .flowOn(dispatcher)
                .onStart { setState(uiState.value.setLoading(true)) }
                .catch { setState(uiState.value.setError(true)) }
                .onCompletion { setState(uiState.value.setLoading(false)) }
                .collect { handleSuccess(it) }
        }
    }

    private fun handleSuccess(response: Repositories) {
        if (response.items.isNullOrEmpty()) {
            setState(RepositoryListUiState().setError(true))
        } else {
            setState(RepositoryListUiState().setRepos(response))
        }
    }

    private fun setState(state: RepositoryListUiState) {
        _uiState.value = state
    }

}