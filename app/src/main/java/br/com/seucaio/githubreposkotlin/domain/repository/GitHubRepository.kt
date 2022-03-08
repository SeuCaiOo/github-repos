package br.com.seucaio.githubreposkotlin.domain.repository

import androidx.paging.PagingData
import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.domain.entity.RepoSearch
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
    fun getRepositoryListKotlin(page: Int): Flow<RepoSearch>
    fun getSearchResultStream(): Flow<PagingData<RepoResponse>>
}