package br.com.seucaio.githubreposkotlin.data.datasource.remote

import br.com.seucaio.githubreposkotlin.data.model.RepoSearchResponse
import kotlinx.coroutines.flow.Flow

interface GitHubDataSource {
    fun getRepositoryListKotlin() : Flow<RepoSearchResponse>
}