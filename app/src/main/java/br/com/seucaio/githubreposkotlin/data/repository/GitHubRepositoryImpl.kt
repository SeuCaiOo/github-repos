package br.com.seucaio.githubreposkotlin.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import br.com.seucaio.githubreposkotlin.data.datasource.GitHubPagingSource
import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapper
import br.com.seucaio.githubreposkotlin.data.model.RepoResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitHubRepositoryImpl(
    private val pagingSource: GitHubPagingSource,
    private val mapper: RepoMapper,
) : GitHubRepository {
    override fun getRepositoryListKotlin(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagingSource }
        ).flow.map { it.map { repoResponse -> mapper.map(repoResponse) } }
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 5
    }
}