package br.com.seucaio.githubreposkotlin.domain.repository

import br.com.seucaio.githubreposkotlin.data.model.RepositoriesResponse
import br.com.seucaio.githubreposkotlin.domain.entity.Repos
import kotlinx.coroutines.flow.Flow

interface GitHubRepository {
    fun getRepositoriesSearchKotlin() : Flow<Repos>
}