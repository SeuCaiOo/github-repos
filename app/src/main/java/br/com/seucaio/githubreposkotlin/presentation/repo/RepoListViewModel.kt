package br.com.seucaio.githubreposkotlin.presentation.repo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoListUseCase
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoSearchKotlinUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RepoListViewModel(
    private val useCasePaging: GetRepoSearchKotlinUseCase,
    private val useCaseDataSource: GetRepoListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    var repoUiState by mutableStateOf(RepoUiState())
        private set

    fun getTempRepoList() {
        viewModelScope.launch {
            useCaseDataSource.invoke()
                .flowOn(dispatcher)
                .onStart { repoUiState = repoUiState.copy(isLoading = true) }
                .catch { repoUiState = repoUiState.copy(errorMessage = it.message) }
                .onCompletion { repoUiState = repoUiState.copy(isLoading = false) }
                .collect { repoUiState = repoUiState.copy(repoItems = it) }
        }
    }

    fun searchRepo(): Flow<PagingData<Repo>> {
        return useCasePaging.invoke().cachedIn(viewModelScope)
    }
}

data class RepoUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val repoItems: List<Repo> = listOf(),
)