package br.com.seucaio.githubreposkotlin.presentation.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.seucaio.githubreposkotlin.domain.entity.Repos
import br.com.seucaio.githubreposkotlin.domain.usecase.GetReposKotlinUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RepositoriesViewModel(
    private val useCase: GetReposKotlinUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepositoriesUiState())
    val uiState: StateFlow<RepositoriesUiState> = _uiState

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

    private fun handleSuccess(response: Repos) {
        if (response.items.isNullOrEmpty()) {
            setState(RepositoriesUiState().setError(true))
        } else {
            setState(RepositoriesUiState().setRepos(response))
        }
    }

    private fun setState(state: RepositoriesUiState) {
        _uiState.value = state
    }

}