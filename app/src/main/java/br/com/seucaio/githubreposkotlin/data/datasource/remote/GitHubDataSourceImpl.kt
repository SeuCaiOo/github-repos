package br.com.seucaio.githubreposkotlin.data.datasource.remote

import br.com.seucaio.githubreposkotlin.data.api.GitHubService
import br.com.seucaio.githubreposkotlin.data.model.RepoSearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GitHubDataSourceImpl(
    private val service: GitHubService,
) : GitHubDataSource {
    override fun getRepositoryListKotlin(): Flow<RepoSearchResponse> {
        return flow { emit(service.getRepositoriesSearchKotlin()) }
    }
}