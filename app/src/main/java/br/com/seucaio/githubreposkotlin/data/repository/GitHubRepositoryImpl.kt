package br.com.seucaio.githubreposkotlin.data.repository

import androidx.paging.*
import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDatabase
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubRemoteMediator
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

class GitHubRepositoryImpl(
//    private val pagingSource: GitHubPagingSource,
    private val remoteMediator: GitHubRemoteMediator,
    private val database: RepoDatabase,
//    private val mapper: RepoMapper,
) : GitHubRepository {
    override fun getRepositoryListKotlin(): Flow<PagingData<Repo>> {
        val pagingSourceFactory: () -> PagingSource<Int, Repo> = { database.reposDao().repos() }

      @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow


//        return Pager(
//            config = PagingConfig(
//                pageSize = NETWORK_PAGE_SIZE,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { pagingSource }
//        ).flow.map { it.map { repoResponse -> mapper.map(repoResponse) } }
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}