package br.com.seucaio.githubreposkotlin.presentation.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoSearchKotlinUseCase
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoStreamUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RepoListViewModel(
    private val useCase: GetRepoSearchKotlinUseCase,
    private val useCaseStream: GetRepoStreamUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    private var nextPage = 1

    private val _uiState = MutableStateFlow(RepoListUiState())
    val uiState: StateFlow<RepoListUiState> = _uiState

    private var currentSearchResult: Flow<PagingData<Repo>>? = null

    fun searchRepo(): Flow<PagingData<Repo>> {
        val newResult: Flow<PagingData<Repo>> = useCaseStream.invoke()
            .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

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
