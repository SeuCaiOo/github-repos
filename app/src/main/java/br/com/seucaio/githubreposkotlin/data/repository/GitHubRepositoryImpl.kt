package br.com.seucaio.githubreposkotlin.data.repository

import androidx.paging.*
import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDatabase
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubRemoteMediator
import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapper
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitHubRepositoryImpl(
    private val remoteMediator: GitHubRemoteMediator,
//    private val pagingSource:GitHubPagingSource,
    private val database: RepoDatabase,
//    private val dao: RepoDao,
    private val dataSource: GitHubDataSource,
    private val mapper: RepoMapper
) : GitHubRepository {

    override fun getRepoList(): Flow<List<Repo>> {
        return dataSource.getRepositoryListKotlin()
            .map { it.items?.map(mapper::map) ?: emptyList() }
    }

    override fun getRepositoryListKotlin(): Flow<PagingData<Repo>> {
        val pagingSourceFactory: () -> PagingSource<Int, Repo> = { database.reposDao().repos() }
//        val pagingSourceFactory: () -> PagingSource<Int, Repo> = { dao.repos() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            initialKey = GITHUB_STARTING_PAGE_INDEX,
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow


                /*return Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false
                    ),
                    initialKey = GITHUB_STARTING_PAGE_INDEX,
                    pagingSourceFactory = { pagingSource }
                ).flow.map { it.map { repoResponse -> mapper.map(repoResponse) } }*/
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 10
        const val GITHUB_STARTING_PAGE_INDEX = 1
    }
}