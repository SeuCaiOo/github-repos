package br.com.seucaio.githubreposkotlin.data.repository

import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.mapper.RepositoriesMapper
import br.com.seucaio.githubreposkotlin.domain.entity.Repositories
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitHubRepositoryImpl(
    private val dataSource: GitHubDataSource,
    private val mapper: RepositoriesMapper,
) : GitHubRepository {
    override fun getRepositoryListKotlin(): Flow<Repositories> {
        return dataSource.getRepositoryListKotlin().map(mapper::map)
    }
}