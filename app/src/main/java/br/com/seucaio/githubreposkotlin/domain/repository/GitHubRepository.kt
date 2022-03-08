package br.com.seucaio.githubreposkotlin.domain.repository

import br.com.seucaio.githubreposkotlin.domain.entity.RepoSearch
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
    fun getRepositoryListKotlin(page: Int): Flow<RepoSearch>
}