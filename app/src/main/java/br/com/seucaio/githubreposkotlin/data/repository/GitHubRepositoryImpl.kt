package br.com.seucaio.githubreposkotlin.data.repository

import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.mapper.ReposMapper
import br.com.seucaio.githubreposkotlin.data.model.RepositoriesResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Repos
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitHubRepositoryImpl(
    private val dataSource: GitHubDataSource,
    private val mapper: ReposMapper,
) : GitHubRepository {
    override fun getRepositoriesSearchKotlin(): Flow<Repos> {
        return dataSource.getRepositoriesSearchKotlin().map(mapper::map)
    }
}