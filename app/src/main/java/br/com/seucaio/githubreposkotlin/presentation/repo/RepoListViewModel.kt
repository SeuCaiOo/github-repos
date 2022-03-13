package br.com.seucaio.githubreposkotlin.presentation.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoSearchKotlinUseCase
import kotlinx.coroutines.flow.Flow

class RepoListViewModel(
    private val useCase: GetRepoSearchKotlinUseCase,
) : ViewModel() {


    fun searchRepo(): Flow<PagingData<Repo>> {
        return useCase.invoke().cachedIn(viewModelScope)
    }

}
