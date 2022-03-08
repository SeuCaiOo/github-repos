package br.com.seucaio.githubreposkotlin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.datasource.GitHubPagingSource
import br.com.seucaio.githubreposkotlin.data.mapper.RepoSearchMapper
import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.domain.entity.RepoSearch
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitHubRepositoryImpl(
    private val dataSource: GitHubDataSource,
    private val pagingSource: GitHubPagingSource,
    private val mapper: RepoSearchMapper,
) : GitHubRepository {
    override fun getRepositoryListKotlin(page: Int): Flow<RepoSearch> {
        return dataSource.getRepositoryListKotlin(
            page = page
        ).map(mapper::map)
    }

   override fun getSearchResultStream(): Flow<PagingData<RepoResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagingSource }
        ).flow
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 5
    }
}