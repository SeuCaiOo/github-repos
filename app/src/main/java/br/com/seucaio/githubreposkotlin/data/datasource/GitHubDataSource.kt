package br.com.seucaio.githubreposkotlin.data.datasource

import br.com.seucaio.githubreposkotlin.data.model.RepositoriesResponse
import kotlinx.coroutines.flow.Flow

interface GitHubDataSource {
    fun getRepositoriesSearchKotlin() : Flow<RepositoriesResponse>
}