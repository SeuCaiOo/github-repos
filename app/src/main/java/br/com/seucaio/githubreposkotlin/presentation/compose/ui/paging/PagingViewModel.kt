package br.com.seucaio.githubreposkotlin.presentation.compose.ui.paging

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.usecase.GetRepoPagingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class PagingViewModel(
    private val useCase: GetRepoPagingUseCase
) : ViewModel(){

    fun getRepoPaging(): Flow<PagingData<Repo>> {
        return useCase.invoke().cachedIn(viewModelScope)
    }
}