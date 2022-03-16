package br.com.seucaio.githubreposkotlin.presentation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoSearchKotlinUseCase
import kotlinx.coroutines.flow.Flow

class RepoListViewModel(
    private val useCasePaging: GetRepoSearchKotlinUseCase
) : ViewModel() {

    fun searchRepo(): Flow<PagingData<Repo>> {
        return useCasePaging.invoke().cachedIn(viewModelScope)
    }
}