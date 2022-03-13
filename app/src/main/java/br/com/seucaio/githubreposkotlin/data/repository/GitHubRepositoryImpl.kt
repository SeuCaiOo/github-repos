package br.com.seucaio.githubreposkotlin.data.repository

import androidx.paging.PagingConfig
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import br.com.seucaio.githubreposkotlin.data.datasource.local.RepoDao
import br.com.seucaio.githubreposkotlin.data.datasource.remote.GitHubRemoteMediator
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow

class GitHubRepositoryImpl(
    private val remoteMediator: GitHubRemoteMediator,
    private val dao: RepoDao,
) : GitHubRepository {
    override fun getRepositoryListKotlin(): Flow<PagingData<Repo>> {
        val pagingSourceFactory: () -> PagingSource<Int, Repo> = { dao.repos() }

      @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = remoteMediator,
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }
}