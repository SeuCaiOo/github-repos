package br.com.seucaio.githubreposkotlin.domain.repository

import br.com.seucaio.githubreposkotlin.domain.entity.Repositories
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
    fun getRepositoryListKotlin(page: Int): Flow<Repositories>
}