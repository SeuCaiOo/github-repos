package br.com.seucaio.githubreposkotlin.data.repository

import androidx.paging.*
import br.com.seucaio.githubreposkotlin.data.api.GitHubService.Companion.GITHUB_STARTING_PAGE_INDEX
import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDatabase
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubRemoteMediator
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

class GitHubRepositoryImpl(
    private val remoteMediator: GitHubRemoteMediator,
    private val database: RepoDatabase,
) : GitHubRepository {

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
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 10
    }
}