package br.com.seucaio.githubreposkotlin.data.repository

import androidx.paging.*
import br.com.seucaio.githubreposkotlin.data.api.GitHubService.Companion.GITHUB_STARTING_PAGE_INDEX
import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDatabase
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubPagingSource
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubRemoteMediator
import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapper
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import br.com.seucaio.githubreposkotlin.domain.repository.RepoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RepoRepositoryImpl(
    private val pagingSource: GitHubPagingSource,
    private val dataSource: GitHubDataSource,
    private val mapper: RepoMapper,
) : RepoRepository {

    override fun getRepoList(): Flow<List<Repo>> {
        return dataSource.getRepositoryListKotlin()
            .map { it.items?.map(mapper::map) ?: emptyList() }
    }

    override fun getRepoPaging(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            initialKey = GITHUB_STARTING_PAGE_INDEX,
            pagingSourceFactory = { pagingSource }
        ).flow.map { it.map { repoResponse -> mapper.map(repoResponse) } }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 5
    }
}