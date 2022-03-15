package br.com.seucaio.githubreposkotlin.presentation.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoListUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ListViewModel(
    private val useCase: GetRepoListUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(){

    var uiState by mutableStateOf(ListUiState())
        private set

    fun getTempRepoList() {
        viewModelScope.launch {
            useCase.invoke()
                .flowOn(dispatcher)
                .onStart { uiState = uiState.copy(isLoading = true) }
                .catch { uiState = uiState.copy(errorMessage = it.message) }
                .onCompletion { uiState = uiState.copy(isLoading = false) }
                .collect { uiState = uiState.copy(repoItems = it) }
        }
    }
}