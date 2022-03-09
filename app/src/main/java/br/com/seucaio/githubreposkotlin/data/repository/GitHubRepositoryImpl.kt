package br.com.seucaio.githubreposkotlin.data.repository

import androidx.paging.*
import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDao
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubPagingSource
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubRemoteMediator
import br.com.seucaio.githubreposkotlin.data.mapper.RepoMapper
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitHubRepositoryImpl(
    private val remoteMediator: GitHubRemoteMediator,
    private val pagingSource:GitHubPagingSource,
    private val mapper: RepoMapper,
    private val dao: RepoDao,
) : GitHubRepository {
    override fun getRepositoryListKotlin(): Flow<PagingData<Repo>> {
//        val pagingSourceFactory: () -> PagingSource<Int, Repo> = { dao.repos() }

//      @OptIn(ExperimentalPagingApi::class)
//        return Pager(
//            config = PagingConfig(
//                pageSize = NETWORK_PAGE_SIZE,
//                enablePlaceholders = false
//            ),
//            remoteMediator = remoteMediator,
//            pagingSourceFactory = pagingSourceFactory
//        ).flow


                return Pager(
                    config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = false
                    ),
                    pagingSourceFactory = { pagingSource }
                ).flow.map { it.map { repoResponse -> mapper.map(repoResponse) } }
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}