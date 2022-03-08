package br.com.seucaio.githubreposkotlin.data.repository

import br.com.seucaio.githubreposkotlin.data.datasource.GitHubDataSource
import br.com.seucaio.githubreposkotlin.data.mapper.RepoSearchMapper
import br.com.seucaio.githubreposkotlin.domain.entity.RepoSearch
import br.com.seucaio.githubreposkotlin.domain.repository.GitHubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GitHubRepositoryImpl(
    private val dataSource: GitHubDataSource,
    private val mapper: RepoSearchMapper,
) : GitHubRepository {
    override fun getRepositoryListKotlin(page: Int): Flow<RepoSearch> {
        return dataSource.getRepositoryListKotlin(
            page = page
        ).map(mapper::map)
    }
}