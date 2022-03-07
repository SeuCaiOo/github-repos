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

    private val _uiState = MutableStateFlow(RepoListUiState())
    val uiState: StateFlow<RepoListUiState> = _uiState

    fun getRepositories() {
        viewModelScope.launch {
            useCase.invoke(nextPage)
                .flowOn(dispatcher)
                .onStart { setState(uiState.value.setLoading(true)) }
                .catch { setState(uiState.value.setError(true)) }
                .onCompletion { setState(uiState.value.setLoading(false)) }
                .collect { handleSuccess(it.items) }
        }
    }

    private fun handleSuccess(response: List<Repo>?) {
        if (response.isNullOrEmpty()) {
            setState(RepoListUiState().setError(true))
        } else {
            setState(RepoListUiState().setRepos(response))
        }
    }

    private fun setState(state: RepoListUiState) {
        _uiState.value = state
    }
}